package com.xiaoyue26.www

import java.net.URL

import org.apache.spark.Partitioner

/**
  * Created by xiaoyue26 on 18/1/30.
  */
class DomainNamePartitioner(numParts: Int) extends Partitioner {
  override def numPartitions: Int = numParts

  override def getPartition(key: Any): Int = {
    val domain = new URL(key.toString).getHost
    val code = domain.hashCode % numPartitions
    if (code < 0) {
      code + numPartitions
    } else {
      code
    }
  }

  // 判断俩分区器是否一样.(换句话说,分区方式是否一样)
  override def equals(other: scala.Any): Boolean = other match {
    case dnp: DomainNamePartitioner =>
      dnp.numPartitions == numPartitions // 类型相同,且分区数一样.
    case _ => // 类型都不同,肯定不一样.
      false
  }
}
