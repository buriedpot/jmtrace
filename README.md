[TOC]

# Compilation Instructions



## System Environment

* **Operating System** : Linux (ubuntu 18.04); Windows 10
* **Java Environment** : Java 1.8 (e.g., java-1.8.0_221, java-1.8.0_144) (Including JDK version and JRE version)
* **Maven Environment** : Maven 3 (maven-3.6.1)



## Compilation Steps

1. Open the terminal and enter the source code root directory "jmtrace".

```shell
cd jmtrace
```

2. Execute `mvn clean package`. 

```shell
mvn clean package
```

3. After step 2, you will get a jar under the Maven output directory "target" called "jmtrace-1.0.jar". You can also get a jar under the "target" directory called "jmtrace-1.0-jar-with-dependencies.jar". Execute `java -javaagent:<jmtrace jar path> -jar <your jar> <your arg1> <your arg2> ...` command to execute the ".jar" file to get the result. You can also enter directory "jmtrace" and execute `./jmtrace -jar <your jar> <your arg1> <your arg2> ...`.

​	**step 3 example**:

* client jar

```java
/* Hello.java */
public class Hello {
    public static double[] dd;
    public static void main(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            System.out.println(args[i]);
        }
        Hello.dd = new double[3];
        dd[2] = 1.0;
    }
}
```

* execute command

```shell
java -javaagent:"C:\Works\javaworks\ISER_PA2\jmtrace\target\jmtrace-1.0-jar-with-dependencies.jar" -jar C:\Works\javaworks\ISER_PA2\myTest\target\Hello.jar hello world
# In this case, Hello.jar is your jar and it should have a main class declared.
```

​	or enter folder "jmtrace" and execute (if your system is Linux)

```java
./jmtrace -jar C:\Works\javaworks\ISER_PA2\myTest\target\Hello.jar hello world
```

​	if you get `Permission denied` error, please enter "jmtrace" folder and execute `chmod +x jmtrace` first.

* output

```shell
R 1 0000000008efb846 java.lang.String[0]
Hello
R 1 0000000008efb846 java.lang.String[1]
World
W 1 0000000063961c42 Hello.dd
R 1 0000000063961c42 Hello.dd
W 1 0000000065b54208 double[2]
```
