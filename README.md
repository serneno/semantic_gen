# CECS 571 Project 2 Semantic Generator

## Contributors
- Haydn Pang
- Michael Tran
- Kelvin Pham
- Rob Marquez
- Josue Crandall

## Run Instructions
In a Windows environment:

1. Run the batch script "semantic_gen.bat" in command prompt

```>semantic_gen.bat```

If the above does not work, you may have to compile and run manually:

1. Compile all files and including the jena library in the classpath

```>javac -cp "./lib/*" *.java```

2. Run the main class

```>java -cp "./lib/*";.; SemanticGen```

## Dependencies
Jena API (For creating RDF)
