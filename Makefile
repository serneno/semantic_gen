# Define makefile variable for the java compiler
JAVA = javac
FLAGS = -cp "./lib/*" -d bin

all: java

java: SemanticGen.java CSVReader.java
	$(JAVA) $(FLAGS) *.java
