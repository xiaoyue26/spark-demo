#!/usr/bin/env bash
rm -f spsg.jar
wget http://upload.zhenguanyu.com/uploads/spsg.jar


spark-submit --master yarn-cluster --queue default \
--class com.xiaoyue26.www.Application --name test --num-executors 1 --executor-memory 4g \
spsg.jar


java -jar spsg.jar


spark-submit --master yarn-client --queue default \
--class com.xiaoyue26.www.Application --name test --num-executors 4 --executor-memory 4g \
spsg.jar
