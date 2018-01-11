package com.xiaoyue26.www.steaming.utils

import java.util.regex.Pattern

import kafka.message.MessageAndMetadata

/**
  * Created by xiaoyue26 on 18/1/5.
  */
object TestClassType {

  def test1(): Unit = {
    val messageHandler //: Function1[MessageAndMetadata[String, String], (String, String)] // 非常复杂的类型..
    = (mmd: MessageAndMetadata[String, String]) => (mmd.key, mmd.message)
    println(messageHandler.getClass)
  }

  def test2: Unit = {
    val patStr = "\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}(?:.[\\d]+)?\\s+(?:@@@traceId\\S+)?\\s+\\S+\\s+(\\d+)"
    val pat = Pattern.compile(patStr)
    val log = "2018-01-03 00:00:46.098 @@@traceId=-7e76d59d7432b0f8@@@ filter    1514908846098    [url=/leo-oral/iphone/oral-evaluations]    [method=POST]    [sc=200]    [$duration=402]    sign=0800d19aff805d46ea9fb7bf6fdfa67d    _productId=201 av=5    platform=11.1    version=6.7.0    [@__ks_device_id__=2]    [@__cookie_domain__=Tutor]    [@__ip__=10.11.41.169]    [@__user_id__=16090809]    [@__sid__=813049242134843586]    [ip=10.11.41.169]    [referer=NULL] [useragent=Leo/6.7.0 (iPhone; iOS 11.1; Scale/3.00)]    [logVersion=0]"

    val matcher = pat.matcher(log)
    if (matcher.find()) {
      println(matcher.group(1))
    }
  }

  def main(args: Array[String]): Unit = {
    test2
  }
}
