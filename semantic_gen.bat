@ECHO off
ECHO Cleaning up existing .class files...
del /S *.class
ECHO.
ECHO Compiling...
javac -cp "./lib/*" -d bin *.java
ECHO Done compiling!
ECHO.
java -cp "./lib/*";bin; SemanticGen