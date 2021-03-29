# COOL Compiler
A basic compiler for COOL, currently in development and supporting lexical and syntactic parsing, a listener (CoolPrinter) and inheritance tree walker.

## Getting Started
- make sure you have Javac installed, and add antlr4-8.jar to project libraries. If needed, add necessary ANTLR commands to shell/PATH.
- run the following command in `./generator` directory:
    ```
    antlr4 Cool.g4 && javac Cool*.java
    ```
- now you can run the main class in `./compiler`, enter the code snippet path with `.txt` extension. Some samples can be found in `./samples`.
- the program will print an outline of the code in both stdout and a file in the input directory. The class inheritance tree will also be printed to stdout.

## Under the Hood
- the `Cool.g4` file includes a grammar based on COOL syntax. Some rule elements are labelled for use in the listener, and expr rules are given alt names to help with nested statements.
- the `CoolPrinter.java` in `./compiler` implements the CoolListener interface, which allows for customised functions while traversing a parse tree.
- `Cool Printer (String path)` initialises the fileWriter for output, while `ind` maintains indentation level throughout the listening process.
- after the COOL program has been traversed, the method called upon exiting the program node will call the inheritance handler, which assigns parents to orphan classes (Object class) and then prints the tree in depth first manner.
