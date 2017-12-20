#!/usr/bin/env bash
cd ..
gradle clean shadowJar
mupload -f build/libs/spark-spring-gradle-1.0-SNAPSHOT-all.jar -o spsg.jar
