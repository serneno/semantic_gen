# CECS 571 Project 2 + 3

## Contributors
- Haydn Pang
- Michael Tran
- Kelvin Pham
- Rob Marquez
- Josue Crandall

## Run Instructions

**Project 3**
To run project 3, simply execute the "do.bat" batch script in the project_3 folder:

```
cd project_3
do.bat
```

**Linux Environment**
1. Run the bash script "semantic_gen.sh"

```./semantic_gen.sh```

2. If permission is denied, grant yourself permission:

```chmod +x semantic_gen```

3. If none of the above work, then you may manually compile and run as follows:

```
javac -cp "./jena_lib/*" -d bin -sourcepath src src/*.java
java -cp "./jena_lib/*":bin: SemanticGen
```

**Windows Environment**

1. Run the batch script "semantic_gen.bat" in command prompt

```semantic_gen.bat```

2. If the above does not work, then you may manually compile and run as follows:

```
javac -cp "./jena_lib/*" -d bin -sourcepath src src/*.java
java -cp "./jena_lib/*";bin; SemanticGen
```

## Dependencies
Jena API (For creating RDF/OWL, located in the jena_lib folder)
