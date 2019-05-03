#!/bin/bash

export HADOOP_CLASSPATH=/usr/lib/jvm/jdk1.7.0_80/lib/tools.jar
hadoop fs -rm -r -f /user/hduser/output/
start-yarn.sh
start-dfs.sh
