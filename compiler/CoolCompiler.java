package compiler;

import generator.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class CoolCompiler {

    public static void main(String[] args) throws Exception {
        String input = "../samples/sample2.txt";
        CharStream stream = CharStreams.fromFileName(input);
        CoolLexer lexer = new CoolLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        CoolParser parser = new CoolParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        String output = input.split(".txt")[0] + "_out.txt";
        CoolListener  listener = new CoolPrinter(output);
        walker.walk(listener, tree);
    }
}
