package com.xiaoyue26.www.hbase

import com.xiaoyue26.www.hbase.parse.ApeQuestionStatParser
import org.springframework.stereotype.Component

/**
  * Created by xiaoyue26 on 17/12/21.
  */
@Component
class ApeQuestionStatJob extends AbstractApeHbaseJob {
  import scala.collection.JavaConverters._

  override def run(): Unit = {
    super.clearOutPath(ApeQuestionStatJob.HDFS_OUT_PATH)
    val rdd = super.initRdd(ApeQuestionStatJob.HTABLE_NAME)
    val parseResult = rdd.map {
      case (_, result) => {
        ApeQuestionStatParser.parse(result).asScala.toList
      }
    }.flatMap(x => x)
    parseResult.saveAsTextFile(ApeQuestionStatJob.HDFS_OUT_PATH)

  }
}

object ApeQuestionStatJob {

  val HDFS_OUT_PATH = "/user/fengmq01/test_question"
  val HTABLE_NAME = "ape_question_stat"

  def main(args: Array[String]): Unit = {
    val job = new ApeQuestionStatJob()
    job.run()
  }

}