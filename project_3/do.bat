@ECHO OFF
javac -cp "./jena/*";. *.java
java -cp "./jena/*";. Main
DEL *.class