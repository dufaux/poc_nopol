Johan Dufaux

This program try to find patch in bugs of defects4j using nopol and evosuite.


to launch pocnopol:
```
Usage: java -jar pocnopol.jar
                          [-typeOfBug <typeOfBug>] [-bugFolder <bugFolder>] [-numberOfBug <numberOfBug>] [-cpClassFolder <cpClassFolder>] [-cpTestFolder <cpTestFolder>] [-destSrcTestDir <destSrcTestDir>] [-newTestDir <newTestDir>] [-classPath <classPath>]

    [-typeOfBug <typeOfBug>] 
        define the type of bug (ex: Math for the bug Math 85)
    [-numberOfBug <numberOfBug>] 
        the bug number (ex: 85 for the bug Math 85)
    [-bugFolder <bugFolder>] 
        the folder of bug
    [-cpClassFolder <cpClassFolder>] 

    [-cpTestFolder <cpTestFolder>] 

    [-destSrcTestDir <destSrcTestDir>]

    [-newTestDir <newTestDir>] 
    
    [-classPath <classPath>]

```

example of command: 
java -jar pocnopol.jar
-typeOfBug Math -numberOfBug 85 -bugFolder bugs/Math_85 -cpClassFolder bugs/Math_85/target/classes -cpTestFolder bugs/Math_85/target/test-classes -destSrcTestDir bugs/Math_85/src/test -newTestDir evotests/Math/85/Math/evosuite-branch/0 -classPath bugs/Math_85/target/classes:evosuite-standalone-runtime-1.0.2-SNAPSHOT.jar:junit-4.8.2.jar:hamcrest-core-1.3.jar
