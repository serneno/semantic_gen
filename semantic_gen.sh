#!/bin/bash

echo "================================================="
echo "Cleaning up existing .class files..."
make clean
echo "Compiling..."
make java
echo "Done compiling!"
echo "================================================="
echo
java -cp "./jena_lib/*":bin: SemanticGen