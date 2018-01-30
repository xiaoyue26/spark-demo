package com.xiaoyue26.www

import com.xiaoyue26.www.data.TestEntity
import com.xiaoyue26.www.service.ISparkJob
import com.xiaoyue26.www.storage.ITestDAO
import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkContext}
import org.apache.spark.sql.SparkSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.{Component, Service}


/**
  * Created by xiaoyue26 on 17/12/20.
  */
@Service
class CrazyRdd extends ISparkJob {
  @Autowired
  var session: SparkSession = _


  def run(): Unit = {
    val sc = session.sparkContext

    // 1. reduceByKey:
    reduceKeyTest(sc)

    // 2. aggregate:
    aggregateTest(sc)

    // 3. combinerByKey:
    combinerTest(sc)

    // 4. left join / right join / cogroup
    val kvs1 = Array((1, 2), (3, 4), (3, 6))
    val kvs2 = Array((3, 9))
    val rdd1 = sc.parallelize(kvs1)
    val rdd2 = sc.parallelize(kvs2)
    joinTest(rdd1, rdd2)

    // Option测试:
    optionTest()

    // 重新分区测试
    rePartitionTest(rdd1)

    pageRankTest(sc)
    //spark.stop()
  }

  def reduceKeyTest(sc: SparkContext): Unit = {
    val textFile = sc.textFile("hdfs://f04/ods/request/ods_conan_request_di/dt=2017-10-08/*")
    val words = textFile.flatMap(line => line.split("\\s+"))
    val counts =
      words.map(
        word => {
          (word, 1) // k,v
        }
      ).reduceByKey(
        (x, y) => { // v1,v2
          x + y
        }
      )
  }

  def aggregateTest(sc: SparkContext): Unit = {
    val input = sc.parallelize(List(1, 2))
    val res = input.aggregate((0, 0))( // 求均值
      (acc, v) => (acc._1 + v, acc._2 + 1) // (pv,uv)
      , (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2) // 合并pv,uv
    )
    val avg = res._1 / res._2
  }

  def combinerTest(sc: SparkContext): Unit = {
    val kvs = Array(("Fred", 88.0), ("Fred", 95.0), ("Fred", 91.0), ("Wilma", 93.0), ("Wilma", 95.0), ("Wilma", 98.0))

    val kvsRDD = sc.parallelize(kvs)
    type ACCType = (Int, Double) //定义一个元组类型(科目计数器,分数)

    kvsRDD.combineByKey(
      score => (1, score) // 用value创建累加器
      , (acc: ACCType, newScore) => (acc._1 + 1, acc._2 + newScore)
      , (acc1: ACCType, acc2: ACCType) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
    ).map { case (name, (num, sumScore)) => (name, sumScore / num) }.collect
  }

  def joinTest(rdd1: RDD[(Int, Int)], rdd2: RDD[(Int, Int)]): Unit = {
    // 1. left join
    rdd1.leftOuterJoin(rdd2).collect()
    // 返回值类型:  Array[(Int, (Int, Option[Int]))]
    /* Array(
      (1,(2,None))
    , (3,(4,Some(9))))
    , (3,(6,Some(9)))
    */

    // 2. right join
    rdd1.rightOuterJoin(rdd2).collect()

    // 返回值类型:  Array[(Int, (Option[Int], Int))]
    /* Array(
    , (3,(Some(4),9)))
    , (3,(Some(6),9))
    */

    // 3. cogroup
    rdd1.cogroup(rdd2).collect()
    // 返回值类型: Array[(Int, (Iterable[Int], Iterable[Int]))]
    /*
    Array(
        (1,(CompactBuffer(2),CompactBuffer()))
      , (3,(CompactBuffer(4, 6),CompactBuffer(9))))
    */
  }


  def optionTest(): Unit = {
    val map1 = Map("key1" -> "value1")
    val value1 = map1.get("key1")
    val value2 = map1.get("key2")
    val value3 = Some("value3")
    for (c <- value1) {
      println(c.length)
    }
  }

  def rePartitionTest(rdd1: RDD[(Int, Int)]): Unit = {
    val partitioner = new HashPartitioner(10)
    val rdd3 = rdd1.partitionBy(partitioner)
  }

  def pageRankTest(sc: SparkContext): Unit = {
    // page rank:
    val data = Seq((1, Seq(2, 3)), (2, Seq(3)), (3, Seq(1)))
    val links = sc.parallelize(data)
    var ranks = links.mapValues(v => 1.0)


    for (i <- 0 until 1) {
      val contrib = links.join(ranks)
      val next = contrib.flatMap { // 输出每个dest获得的rank增益
        case (pageid, value) => // pageid其实用不着
          try {
            val target = value.asInstanceOf[Tuple2[Seq[Int], Double]]
            val link = target._1
            val rank = target._2
            link.map(dest => (dest, rank / link.length))
          }
          finally {}
        //List(1)
      }
      ranks = next.reduceByKey(// 求个和
        (x, y) => x + y
      ).mapValues( // 变换一下
        v => 0.15 + 0.85 * v
      )
    }
  }
}
