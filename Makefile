JAVA = javac

all: java

java: SemanticGen.java CSVReader.java
	$(JAVA) SemanticGen.java CSVReader.java
