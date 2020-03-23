# CECS 571 Project 2 Semantic Generator

## Contributors
- Haydn Pang
- Michael Tran
- Kelvin Pham
- Rob Marquez
- Josue Crandall

## Run Instructions
**Linux Environment**
1. Run the bash script "semantic_gen.sh"

```$./semantic_gen.sh```

If permission is denied, grant yourself permission:

```$chmod +x semantic_gen```

If none of the above work, then you may manually compile and run as follows:

```
$make java
$java -cp "./jena_lib/*":bin: SemanticGen
```

**Windows Environment**

1. Run the batch script "semantic_gen.bat" in command prompt

```>semantic_gen.bat```

If the above does not work, then you may manually compile and run as follows:

```
>javac -cp "./lib/*" *.java
>java -cp "./lib/*";.; SemanticGen
```

## Dependencies
Jena API (For creating RDF)
