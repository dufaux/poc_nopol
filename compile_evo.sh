#!/bin/bash


testfolder=$1
cpclassfolder=$2
cptestfolder=$3
files_to_compile="${@:4}";


files_to_compile="${@:4}";
#for file in "${@:4}";
#do
#  files_to_compile="$files_to_compile ${testfolder}/${file}"
#done

echo "${files_to_compile}";

compile_cmd="javac ${files_to_compile} -cp ${cpclassfolder}${cp}:evosuite-standalone-runtime-0.2.1-SNAPSHOT.jar:junit-4.8.2.jar:hamcrest-core-1.3.jar -d ${cptestfolder}/"



echo $compile_cmd
eval $compile_cmd


#
#echo "Array size: ${#BUGS_FOR_NOPOL[*]}"

#for index in ${!BUGS_FOR_NOPOL[*]}
#do
  #echo $index
#  cmd="defects4j checkout -p ${TYPE_OF_BUG[$index]} -v ${BUGS_FOR_NOPOL[$index]}b -w ${TYPE_OF_BUG[$index]}${BUGS_FOR_NOPOL[$index]}_buggy"
#  eval $cmd
#done
