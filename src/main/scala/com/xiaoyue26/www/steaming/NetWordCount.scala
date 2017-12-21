package com.xiaoyue26.www.steaming

import com.xiaoyue26.www.service.ISparkJob
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * Created by xiaoyue26 on 17/12/21.
  */
@Service
class NetWordCount extends ISparkJob {
  @Autowired
  var session: SparkSession = _

  override def run(): Unit = {
    val ssc: StreamingContext = new StreamingContext(session.sparkContext, Seconds(3))
    //ssc.checkpoint("hdfs://f04/user/fengmq01/checkpoint") // 加了checkpoint的话,由于"exit"常量无法序列化,会出错.
    val lines = ssc.socketTextStream("dx-pipe-cpu1-pm", 9092)
    val EXIT = "exit"
    // check exit begin
    lines.foreachRDD(
      rdd => {
        if (!rdd.isEmpty() && EXIT.equals(rdd.first())) {
          ssc.stop(false)
        }
      }
    )
    // check exit end

    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()
    ssc.start() // Start the computation

    ssc.awaitTermination() // Wait for the computation to terminate
    //ssc.stop(false) // 用完归还 sparkContext. 别人还能用.
  }
}
