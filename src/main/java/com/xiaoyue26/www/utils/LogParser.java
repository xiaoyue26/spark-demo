package com.xiaoyue26.www.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by xiaoyue26 on 17/9/17.
 */
public class LogParser implements Serializable{
    private static Pattern numPat = Pattern.compile("[0-9]+");

    public static void tryFindTp(String[] tuple, Map<String, String> logInfo) {
        for (int i = 2; i < 5; ++i) {// 尝试获取request的timestamp
            if (i >= tuple.length) {
                return;
            }
            String str = tuple[i];
            Matcher m = numPat.matcher(str);
            if (m.matches()) {
                logInfo.put("timestamp", str);
            }
        }
    }

    public static Map<String, String> parseLine(String line, boolean keyLower) {
        String[] tuple = line.split("\\s+");
        if (tuple.length < 3) {
            return null;
        }
        Map<String, String> logInfo = new HashMap<String, String>();
        logInfo.put("fdate", tuple[0].trim());
        logInfo.put("ftime", tuple[1].trim());
        tryFindTp(tuple, logInfo);
        String lastKey = "";
        for (int i = 2; i < tuple.length; i++) {
            String str = tuple[i];
            if (str.contains("=")) {
                String[] data = str.split("=", 2);
                if (data.length >= 2) {
                    String keyStr;
                    if (keyLower) {
                        keyStr = data[0].trim().replaceAll("[\\[@_$]", "").toLowerCase();
                    } else {
                        keyStr = data[0].trim().replaceAll("[\\[@_$]", "");
                    }

                    String valStr = StringUtils.stripEnd(data[1].trim(), "] ");
                    if (logInfo.containsKey(keyStr)) {
                        logInfo.put(keyStr + "2", valStr);
                    } else {
                        logInfo.put(keyStr, valStr);
                    }
                    lastKey = keyStr;
                }
            } else {
                String val = logInfo.get(lastKey);
                if (val != null && !lastKey.equals("fdate") && !lastKey.equals("ftime")) {
                    logInfo.put(lastKey, val + " " + str);
                }
            }
        }
        return logInfo;
    }

    public static void main(String[] args) {
        //String line = "2017-09-06 12:37:56 frogVersion=v2=2=1 device=Android deviceId=6672054664384523833 osVersion=5.0.2 appVersion=1.0.0 model=YuanTiKu/7.0.2 manufacturer=MI 2 screenWidth=360.0 screenHeight=640.0 productId=131 timestamp=1504672696562 seqId=0 url=/click/Discovery/promotion net=wifi id=10 gradeIds=null";
        //String line = "2017-10-08 02:21:14.034 @@@traceId=-2b4703b8fa9aafe3@@@ \tfilter\t1507400474034\t[url=/conan-english-picbook-report/android/picbook-reports]\t[method=POST]\t[sc=200]\t[$duration=8]\t_deviceId=177129525036866852\t_productId=513\tav=11\tvendor=tencent\tversion=1.0.1\tplatform=android24\t[@__cookie_domain__=Tutor]\t[@__ip__=202.168.163.26]\t[@__user_id__=88342118]\t[@__sid__=1135547727244942799]\t[ip=202.168.163.26]\t[referer=NULL]\t[useragent=YuanTiKu]\t[logVersion=0]";
        //String line = "2017-12-04 21:03:49.959 @@@traceId=-7dd808f453dbcba2@@@ \tfilter\t1512392629959\t[url=/ape-predict-report/api/courses/77/detail-report]\t[method=GET]\t[sc=200]\t[$duration=5]\tphaseId=1\t[@__cookie_domain__=Tutor]\t[@__ip__=117.179.171.194]\t[@__user_id__=104352336]\t[@__sid__=6164071479615916546]\t[ip=117.179.171.194]\t[referer=https://ytk.yuanfudao.com/h5/ape-web-activity/predictReport.html?phaseId=1&courseId=77&examYear=2020&quizName=%E9%BE%99%E4%B8%9C%E5%9C%B0%E5%8C%BA&_productId=111&phaseId=1&_deviceId=-9194500371362209429]\t[useragent=Mozilla/5.0 (Linux; Android 6.0; HUAWEI VNS-TL00 Build/HUAWEIVNS-TL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36 YuanTiKu/7.4.1]\t[logVersion=0]";
        //String line = "2017-12-04 00:03:40.700 @@@traceId=-495bc64ab56672a3@@@ \tfilter\t1512317020700\t[url=/ape-predict-report/api/simple-report]\t[method=GET]\t[sc=200]\t[$duration=4]\tphaseId=2\t[@__cookie_domain__=Tutor]\t[@__ip__=42.89.23.231]\t[@__user_id__=45071565]\t[@__sid__=6195368319018377022]\t[ip=42.89.23.231]\t[referer=https://ytk.yuanfudao.com/h5/ape-web-activity/predictMain.html?_productId=111&phaseId=2&_deviceId=8230996481613510886]\t[useragent=Mozilla/5.0 (Linux; Android 6.0; 1505-A01 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.82 Mobile Safari/537.36 YuanTiKu/7.4.1]\t[logVersion=0]";
        String line = "2018-01-19 13:09:59 \tfilter\t1516338599505\t[url=/ape-news/android/news]\t[method=GET]\t[sc=200]\t[$duration=14]\tphaseId=2\t_productId=211\tcursorTime=0\tlimit=10\twithPin=true\tcategoryId=0\tplatform=android24\tversion=6.10.0\tvendor=huawei\tav=5\tsign=5cfa985fe3fa3b85331ad7b2776b5dcf\t[@__cookie_domain__=Tutor]\t[@__ip__=42.48.99.31]\t[ip=42.48.99.31]\t[referer=NULL]\t[useragent=Solar/6.10.0 (HONORDLI-AL10; Android 7.0; Scale/2.00)]\t[logVersion=0]";
        Map<String, String> res = parseLine(line, false);
        System.out.println(res);
        /*res.remove("ftime");
        System.out.println(res);
        */
        String a = (String) null;
        System.out.println("test:" + (null == a));
        List<String> abc = new ArrayList<>();
        abc.add(null);
        String value = "userid ] ";
        value = StringUtils.stripEnd(value, "] ");
        System.out.println(value + "===");


    }
}
