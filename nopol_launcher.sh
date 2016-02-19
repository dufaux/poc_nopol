#!/bin/bash

class_path=$1
source_path=$2
#java="/usr/lib/jvm/java-8-oracle/jre/bin/java"
java="java"
nopol_jar="nopol-0.0.3-SNAPSHOT-jar-with-dependencies.jar"
z3_for_linux="/home/dufaux/workspace/nopol/nopol/lib/z3/z3_for_linux";

cmd_nopol="${java} -Xmx3072m -jar ${nopol_jar} \
-p ${z3_for_linux} \
-s ${source_path} \
-c ${class_path} \
--maxTime 60
"

echo $cmd_nopol
eval $cmd_nopol


