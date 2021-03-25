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
        CharStream stream = CharStreams.fromFileName("D:\\Afarin\\Edu\\Year 3\\Compiler Design\\Project\\Phase 1\\compiler\\sample1.txt");
        CoolLexer lexer = new CoolLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        CoolParser parser = new CoolParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        CoolListener  listener = new CoolPrinter();
        walker.walk(listener, tree);
    }
}
