.PHONY: all

all: clean compile run

clean:
	mvn clean

compile: clean
	mvn compile

run: compile
	mvn javafx:run
