package com.xiaoyue26.www

import com.xiaoyue26.www.data.TestEntity
import com.xiaoyue26.www.service.ISparkJob
import com.xiaoyue26.www.storage.ITestDAO
import org.apache.spark.sql.SparkSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.math.random

/**
  * Created by xiaoyue26 on 17/12/20.
  */
@Component
class SparkPi extends ISparkJob {
  @Autowired
  var session: SparkSession = _
  @Autowired
  var testDao: ITestDAO = _

  def run(): Unit = {
    val spark = session.sparkContext
    val slices = 2
    val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
    val count = spark.parallelize(1 until n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x * x + y * y < 1) 1 else 0
    }.reduce(_ + _)
    val t: TestEntity = new TestEntity()
    t.setDt("2017-12-31")
    t.setCol1(count)
    t.setCol2("over")
    testDao.addTestEntity(t)
    //spark.stop()
  }
}
