#!/bin/bash


cp=$1
dest_cp_test_folder=$2
files_to_compile="${@:3}";


files_to_compile="${@:3}";


echo "${files_to_compile}";

compile_cmd="javac ${files_to_compile} -cp ${cp} -d ${dest_cp_test_folder}/"


echo $compile_cmd
eval $compile_cmd



