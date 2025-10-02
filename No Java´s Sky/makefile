# Variables
JAVAC = javac
JAVA = java
MAIN = NoJavaSky  
SOURCES = $(wildcard src/*.java)
CLASSES = $(patsubst src/%.java,bin/%.class,$(SOURCES))

# Default target: Compile all .java files into .class files in the bin directory
all: $(CLASSES)

# Rule to compile .java files into .class files in the bin directory
bin/%.class: src/%.java
	@mkdir -p bin
	$(JAVAC) -d bin $<

# Run the program
run: all
	$(JAVA) -cp bin $(MAIN)

# Clean up the .class files from the bin directory
clean:
	rm -rf bin


