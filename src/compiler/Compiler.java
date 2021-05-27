package compiler;

import compiler.listeners.*;
import generator.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.List;

public class Compiler {
    public static void main(String[] args) throws IOException {
        try {
            List<String> tests = List.of("sample1.txt", "sample2.txt", "sample3.txt");
            for (String test : tests) {
                CharStream stream = CharStreams.fromFileName("./samples/" + test);
                System.out.println("running test " + test);
                CoolLexer lexer = new CoolLexer(stream);
                TokenStream tokens = new CommonTokenStream(lexer);
                CoolParser parser = new CoolParser(tokens);
                parser.setBuildParseTree(true);
                CoolParser.ProgramContext tree = parser.program();
                ParseTreeWalker walker = new ParseTreeWalker();
                CoolListener indexer = new Indexer();
                walker.walk(indexer, tree);
                CoolListener handler = new Handler();
                walker.walk(handler, tree);
            }
        } catch (Exception e) {
            System.out.println("well shit");
            e.printStackTrace();
        }
    }
}
