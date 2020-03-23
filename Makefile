JAVA = javac
FLAGS = -cp "./jena_lib/*" 
OUTPUT = -d bin
SOURCE = -sourcepath src src/*.java

java: 
	$(JAVA) $(FLAGS) $(OUTPUT) $(SOURCE)

.PHONY: clean
clean:
	rm bin/*.class