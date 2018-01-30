package com.xiaoyue26.www.steaming

import com.xiaoyue26.www.service.ISparkJob
import com.xiaoyue26.www.steaming.utils.{StreamConf, ZKOffsetsPurgatory}
import com.xiaoyue26.www.utils.{LogParser, TimeUtils, ZookeeperIO}
import kafka.serializer.StringDecoder
import org.apache.curator.framework.imps.CuratorFrameworkState
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * Created by xiaoyue26 on 18/1/2.
  * // java.io.Serializable
  * // Serializable
  */
@Service
class LeoReader extends ISparkJob {
  @Autowired
  var session: SparkSession = _
  var ssc: StreamingContext = _
  var kafkaStream: InputDStream[(String, String)] = _

  def getConf: StreamConf = {
    val conf = new StreamConf()
    conf.gap = Seconds(60)
    conf.topic = "leo"
    conf.groupId = "groupmy"
    conf.num_partitions = 1
    conf.zkList = "pipe-zk1:2181"
    conf.broker_list = "dx-pipe-sata11-pm:9092,dx-pipe-sata12-pm:9092,dx-pipe-sata13-pm:9092,dx-pipe-sata14-pm:9092,dx-pipe-sata15-pm:9092"
    conf.insertFields = List("userid", "min_time", "vendor") // TODO
    conf.DB_CONNECT = "jdbc:mysql://pipe-writer:3306/pipe_solar?user=pipe&password=pipe123"
    conf.table = "pipe_solar.leo_user_vendor"
    return conf
  }

  def init(conf: StreamConf): Unit = {
    // init zk
    conf.LOG.info("initializing ZookeperIO")
    conf.zkIO = new ZookeeperIO()
    val zkIO = conf.zkIO
    val zkPurgatory = conf.zkPurgatory
    zkIO.connect(conf.zkList)
    if (conf.zkIO.getClient.getState != CuratorFrameworkState.STARTED) {
      conf.LOG.info("######### 1 init")
      zkIO.connect(conf.zkList)
      if (zkIO.getClient.getState != CuratorFrameworkState.STARTED) {
        throw new RuntimeException("zookeeper initialize failed")
      }
    }
    conf.zkPurgatory = new ZKOffsetsPurgatory(zkIO, conf.zkList)
    new Thread(zkPurgatory).start()

    // init stream
    ssc = new StreamingContext(session.sparkContext, conf.gap)
    val topic_info = List((conf.topic, conf.num_partitions)).toMap
    val broker_list = conf.broker_list
    kafkaStream = KafkaUtils.createDirectStream[
      String, String // K V
      , StringDecoder, StringDecoder // KD的反序列化类
      , (String, String)]( // messageHandler的返回值,也是InputDStream的类型.
      ssc
      , Map("metadata.broker.list" -> broker_list, "groupid" -> conf.groupId) // 连kafka的参数
      , conf.getPartitionOffsetFromZookeeper // fromOffsets: Map[TopicAndPartition, Long] 从哪里开始读.某个topic的某个partition的某个位置
      , conf.messageHandler) // 每个消息怎么处理. // 把每个消息转化成(String,String)类型,作为DStream的元素.


  }


  override def run(): Unit = {
    val conf = getConf
    init(conf)

    kafkaStream.foreachRDD(
      rdd => {
        rdd.foreachPartition(
          iter => {
            val buckets = scala.collection.mutable.Map[Long, (Long, String)]()
            iter.foreach(

              keyAndValue => {
                try {
                  val line = keyAndValue._2
                  val rowMap = LogParser.parseLine(line, true)
                  if (rowMap != null) {
                    val ts = rowMap.getOrDefault("timestamp", "0").toLong
                    val userid = rowMap.getOrDefault("userid", "0").toLong
                    val productid = rowMap.getOrDefault("productid", "0").toLong
                    var vendor = rowMap.getOrDefault("vendor", "unset")
                    if (productid == 503 || productid == 523) {
                      vendor = "appleAppStore"
                    }
                    if (ts != 0 && userid != 0 && productid / 100 == 5 && productid % 10 == 3) {
                      var old = buckets.getOrElse(userid, (ts, vendor))
                      if (old._1 > ts) { // 更新ts
                        old = (ts, old._2)
                      }
                      if (old._2 == "unset") { // 更新vendor
                        old = (old._1, vendor)
                      }
                      buckets(userid) = old
                    }
                  }
                } catch {
                  case e: Exception => println(e)
                }
              }
            )
            println("buckets calculated and start to insertDB at:" + TimeUtils.getCurrentTimestamp)
            try {
              conf.insertBucketConan(buckets)
            } catch {
              case e: Exception => println(e)
            }
            println("finished inserteDB at:" + TimeUtils.getCurrentTimestamp)
          }
        )
        // 所有partition成功以后才commit,因此不会丢数据,只可能重复消费,因此计算需要是幂等的.
        conf.updatePartitionsOffsets(rdd)
      }
    )
    ssc.start()
    ssc.awaitTermination()
    // ssc.awaitTerminationOrTimeout(60 * 1000)

  }


}
