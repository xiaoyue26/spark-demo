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
    val patStr = "\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}(?:.[\\d]+)?\\s+(?:@@@traceId\\S+\\s+)?\\S+\\s+(\\d+)"
    val pat = Pattern.compile(patStr)
    //val log = "2018-01-03 00:00:46.098 @@@traceId=-7e76d59d7432b0f8@@@ filter    1514908846098    [url=/leo-oral/iphone/oral-evaluations]    [method=POST]    [sc=200]    [$duration=402]    sign=0800d19aff805d46ea9fb7bf6fdfa67d    _productId=201 av=5    platform=11.1    version=6.7.0    [@__ks_device_id__=2]    [@__cookie_domain__=Tutor]    [@__ip__=10.11.41.169]    [@__user_id__=16090809]    [@__sid__=813049242134843586]    [ip=10.11.41.169]    [referer=NULL] [useragent=Leo/6.7.0 (iPhone; iOS 11.1; Scale/3.00)]    [logVersion=0]"
    val log = "2018-01-20 20:11:57.254 filter\t1516450317254\t[url=/android/register/device]\t[method=POST]\t[sc=201]\t[$duration=9]\t_productId=211\tplatform=android24\tversion=6.3.1\tvendor=tencent\tav=5\tsign=9781a83beb8c4257665811f89090f73e device=P7T66Ahe5FigIohFGen+pD/OqR/rKmyeIESCnGIyoDhwaicWxcCuzFAkuoX+yD2HtkJQs7zDg7eV8eYR0AUSrDqXBFdvEwcOHQ3t9oJNEJIkNngZ7dEI6QppkA4b96vWzOjEJNeJrRHSh4WlxbkAg2TVWz38GIeiC32RGcu2eYo=\tdeviceInfo={\"id\":\"PZzs\\\\/2KmuqKln23CqRsVSYQl7Zk06SwjWJ4svJgOuBYgf6AkWIWfDGHh5Syk4hB3fKacRyRg+l0ca4YWUIdnuagYzRoG9LqEy1Iwt5Rp6IF9RftViKMEL7rhTpaRJvvaFUGxvlFirm\\\\/7Pv6Qmu1qJVHVjvBfTRC8iXnjG0KJF7Y=\",\"brand\":\"xiaomi\",\"device\":\"mido\",\"model\":\"Redmi Note 4X\",\"sdk\":24,\"host\":\"c3-miui-ota-bd00.bj\",\"rom\":\"AL1512-mido-build-20171207190732\",\"release\":\"7.0\"}\t[@__st_user_id__=372672785]\t[@__ip__=106.17.17.79]\t[@__user_id__=372672785]\t[@__st_cookie_domain__=Tutor]\t[ip=106.17.17.79]\t[referer=NULL]\t[useragent=fenbi-android]\t[logVersion=0] "
    val matcher = pat.matcher(log)
    if (matcher.find()) {
      println(matcher.group(1))
    }
  }

  def main(args: Array[String]): Unit = {
    test2
  }
}
