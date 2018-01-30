#!/usr/bin/env bash
rm -f spsg.jar
wget http://upload.zhenguanyu.com/uploads/spsg.jar


spark-submit --master yarn-cluster --queue default \
--class com.xiaoyue26.www.Application --name test --num-executors 2 --executor-memory 4g \
spsg.jar


spark-submit --master yarn-cluster --queue realtime \
--class com.xiaoyue26.www.Application --num-executors 12 --executor-memory 4g --name conan_newuv_from_ape \
spsg2.jar zhan_wei_fu
