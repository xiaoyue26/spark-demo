package com.xiaoyue26.www.steaming.utils

import java.util.concurrent.{LinkedBlockingQueue, TimeUnit}

import com.xiaoyue26.www.utils.ZookeeperIO
import org.apache.curator.framework.imps.CuratorFrameworkState
import org.slf4j.LoggerFactory

import scala.util.control.NonFatal

/**
  * Created by xiaoyue26 on 18/1/3.
  */
object ZKOffsetsPurgatory {}

class ZKOffsetsPurgatory(zkIO: ZookeeperIO, zookeeperConnect: String) extends Runnable {

  private val queue = new LinkedBlockingQueue[(String, Long)]()
  private val LOG = LoggerFactory.getLogger(ZKOffsetsPurgatory.getClass)

  def add(path: String, value: Long): Unit = {
    queue.put((path, value))
  }

  override def run(): Unit = {
    LOG.info("running ZKOffsetsPurgator")
    while (true) {
      if (zkIO.getClient.getState != CuratorFrameworkState.STARTED) {
        zkIO.connect(zookeeperConnect)
        if (zkIO.getClient.getState != CuratorFrameworkState.STARTED) {
          throw new RuntimeException("zookeeper initialize failed")
        }
      }
      val pathAndValue = queue.poll(1, TimeUnit.SECONDS)
      if (pathAndValue != null) {
        try {
          LOG.info("zkIO.setData path=" + pathAndValue._1 + "  value=" + pathAndValue._2)
          zkIO.setData(pathAndValue._1, new String(pathAndValue._2 + "").getBytes)
        } catch {
          case NonFatal(e) => LOG.error("", e)
        }
      }
      zkIO.close()
    }
  }
}
