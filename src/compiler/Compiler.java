package compiler;

import compiler.listeners.Handler;
import compiler.listeners.Indexer;
import generator.CoolLexer;
import generator.CoolListener;
import generator.CoolParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

public class Compiler {
    public static void main(String[] args){
        List<String> tests = List.of("sample1.txt", "sample2.txt", "sample3.txt");
        try {
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
            System.out.println("Something went wrong!!!");
            e.printStackTrace();
        }
    }
}
