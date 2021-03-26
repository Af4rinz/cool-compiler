package compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import generator.CoolListener;
import generator.CoolParser.ClassdefContext;
import generator.CoolParser.ExprContext;
import generator.CoolParser.FeatureContext;
import generator.CoolParser.FormalContext;
import generator.CoolParser.ProgramContext;

public class CoolPrinter implements CoolListener{
	public int ind = 0;

	public void printIndent (int ind){
		for (int i = 0; i < ind; i++){
			System.out.print(" ");
		}
	}

	@Override
	public void enterEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitTerminal(TerminalNode arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterProgram(ProgramContext ctx) {
		System.out.print("program start{\n");
		printIndent(ind);
        ind += 4;
		
	}

	@Override
	public void exitProgram(ProgramContext ctx) {
		ind -= 4;
		printIndent(ind);
		System.out.print("}\n");
	}

	@Override
	public void enterClassdef(ClassdefContext ctx) {
		printIndent(ind);
		ind += 4;
		String classString = "class: %s/ class parents: %s, {\n";
		if (ctx.classParent == null){
			System.out.print(String.format(classString, ctx.className.getText(), "object"));
		}
		else{
			System.out.print(String.format(classString, ctx.className.getText(), ctx.classParent.getText()));
		}
	}

	@Override
	public void exitClassdef(ClassdefContext ctx) {
		ind -= 4;
		printIndent(ind);
		System.out.print("}\n");		
	}

	@Override
	public void enterFeature(FeatureContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFeature(FeatureContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterExpr(ExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitExpr(ExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterFormal(FormalContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFormal(FormalContext ctx) {
		// TODO Auto-generated method stub
		
	}
   
    
}
