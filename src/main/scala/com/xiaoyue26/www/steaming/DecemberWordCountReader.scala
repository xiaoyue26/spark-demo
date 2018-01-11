package com.xiaoyue26.www.steaming

import com.xiaoyue26.www.service.ISparkJob
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.springframework.beans.factory.annotation.Autowired
import org.apache.spark.streaming.kafka._
import org.springframework.stereotype.Service

/**
  * Created by xiaoyue26 on 17/12/30.
  */
@Service
class DecemberWordCountReader extends ISparkJob {
  @Autowired
  var session: SparkSession = _

  override def run(): Unit = {
    val ssc: StreamingContext = new StreamingContext(session.sparkContext, Seconds(3))
    val topics = List(("december", 1)).toMap
    val kafkaStream = KafkaUtils.createStream(ssc, "pipe-zk1:2181", "groupid"
      , topics, StorageLevel.MEMORY_AND_DISK_SER_2)
    // message key,message value
    val words = kafkaStream.flatMapValues(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()
    ssc.start() // Start the computation

    ssc.awaitTermination() // Wait for the computation to terminate

  }
}
