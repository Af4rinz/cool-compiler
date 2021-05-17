package compiler;

import generator.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) throws IOException {
        CharStream stream = CharStreams.fromFileName("./samples/sample1.cl");
        CoolLexer lexer = new CoolLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        CoolParser parser  = new CoolParser(tokens);
        parser.setBuildParseTree(true);
        CoolParser.ProgramContext tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
    }
}
