package com.xiaoyue26.www.steaming.utils

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util.regex.Pattern

import com.xiaoyue26.www.utils.ZookeeperIO
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.HasOffsetRanges
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConversions._

/**
  * Created by xiaoyue26 on 18/1/2.
  */
class StreamConf extends java.io.Serializable {
  var gap: Duration = _
  var topic_name: String = _
  var num_partitions: Int = _
  var zkList: String = _
  var groupid: String = _

  val LOG: Logger = LoggerFactory.getLogger(getClass)
  val messageHandler = (mmd: MessageAndMetadata[String, String]) => (mmd.key, mmd.message)
  val DB_CONNECT = "jdbc:mysql://pipe-writer:3306/pipe_solar?user=pipe&password=pipe123"
  val insertFields: List[String] = List("timestamp", "count")
  val table = "leo_request"
  val patStr = "\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}(?:.[\\d]+)?\\s+(?:@@@traceId\\S+)?\\s+\\S+\\s+(\\d+)"
  val pat: Pattern = Pattern.compile(patStr)
  val interval = "HOU"

  @transient var zkIO: ZookeeperIO = _
  @transient var zkPurgatory: ZKOffsetsPurgatory = _

  def getPartitionOffsetFromZookeeper: Map[TopicAndPartition, Long] = {
    // 自动识别kafka的分区变化
    var partitionsOffsets = Map[TopicAndPartition, Long]()
    val groupId = "groupid"
    val topic = "december"
    val partitions = zkIO.getChildren(f"/consumers/$groupId/offsets/$topic")
    partitions.foreach(partitionId => {
      val offset = new String(zkIO.getData(f"/consumers/$groupId/offsets/$topic/$partitionId")).toLong
      partitionsOffsets += (TopicAndPartition(topic, partitionId.toInt) -> offset)
    })
    partitionsOffsets
  }

  def updatePartitionsOffsets(rdd: RDD[(String, String)]): Unit = {

    rdd.asInstanceOf[HasOffsetRanges].offsetRanges.foreach {
      offsetRange => {
        commitOffsetToZookeeper(offsetRange.partition + "", offsetRange.untilOffset)
      }
    }
  }

  private def commitOffsetToZookeeper(partitionId: String, latestOffset: Long): Unit = {
    val groupId = "groupid"
    val topic = "december"
    zkPurgatory.add(f"/consumers/$groupId/offsets/$topic/$partitionId", latestOffset)
  }

  def insertIntoDB(buckets: scala.collection.mutable.Map[(Long, List[String]), Long]): Unit = {
    var conn: Connection = DriverManager.getConnection(DB_CONNECT)
    println("buckets size=" + buckets.size)
    var sql = f"insert into $table ("
    insertFields.foreach(field => sql += field + ",")
    sql = sql.substring(0, sql.length - 1) + ") values ("
    insertFields.foreach(field => sql += "?,")
    sql = sql.substring(0, sql.length - 1) + ")"
    println("sql = " + sql)
    val stmt: PreparedStatement = conn.prepareStatement(sql)
    buckets.foreach(unit => {
      val key = unit._1
      val count = unit._2
      val timestamp = key._1
      val otherValues = key._2
      stmt.setObject(1, timestamp) // 设置ts
      for (i <- otherValues.indices) {
        stmt.setObject(i + 2, otherValues(i)) // 设置其他值
      }
      stmt.setObject(otherValues.length + 2, count) // 设置count
      stmt.addBatch()
    })
    stmt.executeBatch()
    conn.close()
  }
}
