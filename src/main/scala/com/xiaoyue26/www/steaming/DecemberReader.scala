package com.xiaoyue26.www.steaming

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util.regex.Pattern

import com.xiaoyue26.www.service.ISparkJob
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.xiaoyue26.www.steaming.utils.{StreamConf, ZKOffsetsPurgatory}
import com.xiaoyue26.www.utils.{TimeUtils, ZookeeperIO}
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.curator.framework.imps.CuratorFrameworkState
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{InputDStream, ReceiverInputDStream}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConversions._

/**
  * Created by xiaoyue26 on 18/1/2.
  * // java.io.Serializable
  * // Serializable
  */
@Service
class DecemberReader extends ISparkJob{
  @Autowired
  var session: SparkSession = _
  var ssc: StreamingContext = _
  var kafkaStream: InputDStream[(String, String)] = _

  def getConf: StreamConf = {
    val conf = new StreamConf()
    conf.gap = Seconds(3)
    conf.topic_name = "december"
    conf.num_partitions = 1
    conf.zkList = "pipe-zk1:2181"
    conf.groupid = "groupid"
    return conf
  }

  def init(conf: StreamConf): Unit = {
    // init zk
    conf.LOG.info("initializing ZookeperIO")
    conf.zkIO = new ZookeeperIO()
    val zkIO = conf.zkIO
    val zkPurgatory=conf.zkPurgatory
    zkIO.connect(conf.zkList)
    if (conf.zkIO.getClient.getState != CuratorFrameworkState.STARTED) {
      zkIO.connect(conf.zkList)
      if (zkIO.getClient.getState != CuratorFrameworkState.STARTED) {
        throw new RuntimeException("zookeeper initialize failed")
      }
    }
    conf.zkPurgatory = new ZKOffsetsPurgatory(zkIO, conf.zkList)
    new Thread(zkPurgatory).start()

    // init stream
    ssc = new StreamingContext(session.sparkContext, conf.gap)
    val topic_info = List((conf.topic_name, conf.num_partitions)).toMap
    val broker_list = "dx-pipe-sata11-pm:9092,dx-pipe-sata12-pm:9092,dx-pipe-sata13-pm:9092,dx-pipe-sata14-pm:9092,dx-pipe-sata15-pm:9092"
    kafkaStream = KafkaUtils.createDirectStream[
      String, String // K V
      , StringDecoder, StringDecoder // KD的反序列化类
      , (String, String)]( // messageHandler的返回值,也是InputDStream的类型.
      ssc
      , Map("metadata.broker.list" -> broker_list, "groupid" -> "groupid") // 连kafka的参数
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
            val buckets = scala.collection.mutable.Map[(Long, List[String]), Long]()
            iter.foreach(
              keyAndValue => {
                val line = keyAndValue._2
                val otherValues = scala.collection.mutable.ListBuffer[String]()
                val matcher = conf.pat.matcher(line)
                if (matcher.find()) {
                  var ts = matcher.group(1).toLong
                  conf.interval match {
                    case "SEC" => ts = ts - ts % 1000
                    case "MIN" => ts = ts - ts % 60000
                    case "HOU" => ts = ts - ts % 3600000
                  }
                  buckets((ts, otherValues.toList)) = buckets.getOrElse((ts, otherValues.toList), 0L) + 1
                }
              }
            )
            println("buckets calculated and start to insertDB at:" + TimeUtils.getCurrentTimestamp)
            try {
              conf.insertIntoDB(buckets)
            } catch {
              case e: Exception => println(e)
            }
            println("finished inserteDB at:" + TimeUtils.getCurrentTimestamp)
          }
        )
        conf.updatePartitionsOffsets(rdd)
      }
    )
    ssc.start()
    ssc.awaitTermination()

  }



}
