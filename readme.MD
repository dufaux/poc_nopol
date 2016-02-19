Johan Dufaux

This program try to find patch in bugs of defects4j using nopol and evosuite.


to launch pocnopol:
```
Usage: java -jar pocnopol.jar
                          [-typeOfBug <typeOfBug>] [-bugFolder <bugFolder>] [-numberOfBug <numberOfBug>] [-cpClassFolder <cpClassFolder>] [-cpTestFolder <cpTestFolder>] [-destSrcTestDir <destSrcTestDir>] [-newTestDir <newTestDir>] [-classPath <classPath>]

    [-projectFolder <bugFolder>]
        project folder (ex: bugs/Math_85)
    [-cpClassFolder <cpClassFolder>]
        classes files (ex: target/classes)
    [-cpTestFolder <cpTestFolder>] 
        classes test files (ex: target/test-classes)
    [-srcClassFolder <srcClassFolder>] 
        location of project files (ex: src/java)
    [-srcTestFolder <srcTestFolder>] 
        location of tests files (ex: src/test)
    [-destSrcTestFolder <destSrcTestFolder>]
        java files where new tests will be added. (ex: src/test) default:srcTestFolder
    [-newTestFolder <newTestFolder>] 
        location of news tests files to add
    [-dependencies <dependencies>]
        all other class or jar required for the news tests. cpClassFolde & cpTestFolder are not necessary. (ex: junit, hamcrest, evosuite)
```

example of command: 
java -jar pocnopol.jar \
-projectFolder bugs/Math_85 \
-cpClassFolder bugs/Math_85/target/classes \
-cpTestFolder bugs/Math_85/target/test-classes \
-srcClassFolder bugs/Math_85/src/java \
-srcTestFolder bugs/Math_85/src/test \
-destSrcTestFolder bugs/Math_85/src/test \
-newTestFolder evotests/Math/85/Math/evosuite-branch/0 \
-dependencies evosuite-standalone-runtime-1.0.2-SNAPSHOT.jar:junit-4.8.2.jar:hamcrest-core-1.3.jar


-projectFolder bugs/Math_85 -cpClassFolder bugs/Math_85/target/classes -cpTestFolder bugs/Math_85/target/test-classes -srcClassFolder bugs/Math_85/src/java -srcTestFolder bugs/Math_85/src/test -destSrcTestFolder bugs/Math_85/src/test -newTestFolder generatededed -classPath bugs/Math_85/target/classes:evosuite-standalone-runtime-1.0.3-SNAPSHOT.jar:junit-4.8.2.jar:hamcrest-core-1.3.jar





