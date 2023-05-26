.PHONY: all

# Default target that triggers other targets: clean, compile, and run.
all: clean compile run

# Remove build artifacts and dependencies.
clean:
	mvn clean

# Compile the project.
compile: format clean
	mvn compile

# Format the source code.
format:
	mvn spotless:apply

# Run the project.
run: compile
	mvn javafx:run
