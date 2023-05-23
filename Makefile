.PHONY: all

# Default target that triggers other targets: clean, compile, and run.
all: clean compile run

# Remove build artifacts and dependencies.
clean:
	mvn clean

# Compile the project.
compile: clean
	mvn compile

# Run the project.
run: compile
	mvn javafx:run
