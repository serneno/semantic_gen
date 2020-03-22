@ECHO off
ECHO =================================================
ECHO Cleaning up existing .class files...
del /S *.class >nul
ECHO Compiling...
javac -cp "./lib/*" -d bin -sourcepath src src/*.java
ECHO Done compiling!
ECHO =================================================
ECHO.
java -cp "./lib/*";bin; SemanticGen