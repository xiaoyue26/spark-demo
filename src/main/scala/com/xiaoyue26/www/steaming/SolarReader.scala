package com.xiaoyue26.www.steaming

import com.xiaoyue26.www.service.ISparkJob
import com.xiaoyue26.www.steaming.utils.{StreamConf, ZKOffsetsPurgatory}
import com.xiaoyue26.www.utils.{TimeUtils, ZookeeperIO}
import kafka.serializer.StringDecoder
import org.apache.curator.framework.imps.CuratorFrameworkState
import org.apache.spark.TaskContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * Created by xiaoyue26 on 18/1/2.
  * // java.io.Serializable
  * // Serializable
  */
@Service
class SolarReader extends ISparkJob {
  @Autowired
  var session: SparkSession = _
  var ssc: StreamingContext = _
  var kafkaStream: InputDStream[(String, String)] = _

  def getConf: StreamConf = {
    val conf = new StreamConf()
    conf.gap = Seconds(3)
    conf.topic = "solar"
    conf.groupId = "groupmy"
    conf.num_partitions = 12
    conf.zkList = "pipe-zk1:2181"
    conf.broker_list = "dx-pipe-sata11-pm:9092,dx-pipe-sata12-pm:9092,dx-pipe-sata13-pm:9092,dx-pipe-sata14-pm:9092,dx-pipe-sata15-pm:9092"
    conf.insertFields = List("timestamp", "line")
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
        val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        val words = rdd.flatMapValues(line => line.split("\\s+"))

        conf.updatePartitionsOffsets(rdd)
      }
    )
    ssc.start()
    ssc.awaitTermination()
    //ssc.awaitTerminationOrTimeout(60 * 1000)

  }


}
