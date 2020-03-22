# CECS 571 Project 2 Semantic Generator

## Contributors
- Haydn Pang
- Michael Tran
- Kelvin Pham
- Rob Marquez
- Josue Crandall

## Run Instructions
Assuming a unix environment:

1. Run the Makefile

```$make```

2. Run the main class, SemanticGen.java, followed by the input dataset file

```$java SemanticGen <file-name>```

In a windows environment (Jena implementation):

1. Compile all files and including the jena library in the classpath

```javac -cp "./lib/*" *.java```

2. Run the main class, Main.java (In JC_WIP)

```java -cp "./lib/*";.; Main```

## Dependencies
Jena API (For creating RDF)