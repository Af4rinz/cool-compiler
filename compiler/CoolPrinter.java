package compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import generator.CoolLexer;
import generator.CoolListener;
import generator.CoolParser.*;

public class CoolPrinter implements CoolListener{
	public int ind = 0;
	// prints indents at beginning of each line
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
		String classString = "class: %s/ class parents: %s, {\n";
		if (ctx.classParent == null){
			System.out.print(String.format(classString, ctx.className.getText(), "object"));
		}
		else{
			System.out.print(String.format(classString, ctx.className.getText(), ctx.classParent.getText()));
		}
		ind += 4;
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
	public void enterMethodDec(MethodDecContext ctx) {
		printIndent(ind);
		String methodStr = "class method: %s/ return type=%s{\n";
		System.out.print(
				String.format(methodStr, ctx.methodName.getText(), ctx.returnType.getText())
			);
		ind+= 4;
		int argCount = ctx.parameterName.size();
		if(argCount > 0){
			printIndent(ind);
			System.out.print("parameters list= [");
			for (int i=0; i < argCount; i++){
				System.out.print(ctx.parameterType.get(i).getText() + " " + ctx.parameterName.get(i).getText());
				if(i != argCount - 1){
					System.out.print(", ");
				}
			}
			System.out.print(" ]\n");
		}
		
	}
	@Override
	public void exitMethodDec(MethodDecContext ctx) {
		ind -= 4;
		printIndent(ind);
		System.out.print("}\n");
		
	}

	@Override
	public void enterFormal(FormalContext ctx) {
		// TODO Auto-generated method stub
		System.out.print(ctx.parameterType.getText() + " " + ctx.parameterName.getText());
	}

	@Override
	public void exitFormal(FormalContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterFieldDec(FieldDecContext ctx) {
		
		printIndent(ind);
		String fieldStr = "field: %s/ type=%s\n";
		System.out.print(
			String.format(fieldStr, ctx.fieldName.getText(), ctx.fieldType.getText())
		);
		
	}

	@Override
	public void exitFieldDec(FieldDecContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAssignment(AssignmentContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAssignment(AssignmentContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNew(NewContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNew(NewContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterMinus(MinusContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitMinus(MinusContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterString(StringContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitString(StringContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterIsvoid(IsvoidContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitIsvoid(IsvoidContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterParantheses(ParanthesesContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitParantheses(ParanthesesContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterFalse(FalseContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFalse(FalseContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterLess(LessContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitLess(LessContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterWhile(WhileContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		if (parent != null && ( 
			parent instanceof IfContext
			|| parent instanceof WhileContext
			|| parent instanceof BlockContext
			|| parent instanceof CaseContext 
			)){
				printIndent(ind);
				ind += 4;
				System.out.print("nested statement{\n");
		}
	}

	@Override
	public void exitWhile(WhileContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		if (parent != null && ( 
			parent instanceof IfContext
			|| parent instanceof WhileContext
			|| parent instanceof BlockContext
			|| parent instanceof CaseContext 
			)){
				ind -= 4;
				printIndent(ind);
				System.out.print("}\n");
		}
		
	}

	@Override
	public void enterInt(IntContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitInt(IntContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterPlus(PlusContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitPlus(PlusContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterEqual(EqualContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitEqual(EqualContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNot(NotContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNot(NotContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNegate(NegateContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNegate(NegateContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTrue(TrueContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTrue(TrueContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterLessequal(LessequalContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitLessequal(LessequalContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBlock(BlockContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		if (parent != null && ( 
			parent instanceof IfContext
			|| parent instanceof WhileContext
			|| parent instanceof BlockContext
			|| parent instanceof CaseContext 
			)){
				printIndent(ind);
				ind += 4;
				System.out.print("nested statement{\n");
		}
	}

	@Override
	public void exitBlock(BlockContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		if (parent != null && ( 
			parent instanceof IfContext
			|| parent instanceof WhileContext
			|| parent instanceof BlockContext
			|| parent instanceof CaseContext 
			)){
				ind -= 4;
				printIndent(ind);
				System.out.print("}\n");
		}
		
	}

	@Override
	public void enterLet(LetContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitLet(LetContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDivide(DivideContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDivide(DivideContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterId(IdContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitId(IdContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterMultiply(MultiplyContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitMultiply(MultiplyContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterIf(IfContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		if (parent != null && ( 
			parent instanceof IfContext
			|| parent instanceof WhileContext
			|| parent instanceof BlockContext
			|| parent instanceof CaseContext 
			)){
				printIndent(ind);
				ind += 4;
				System.out.print("nested statement{\n");
		}
		
	}

	@Override
	public void exitIf(IfContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		if (parent != null && ( 
			parent instanceof IfContext
			|| parent instanceof WhileContext
			|| parent instanceof BlockContext
			|| parent instanceof CaseContext 
			)){
				ind -= 4;
				printIndent(ind);
				System.out.print("}\n");
		}
		
	}

	@Override
	public void enterCase(CaseContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		if (parent != null && ( 
			parent instanceof IfContext
			|| parent instanceof WhileContext
			|| parent instanceof BlockContext
			|| parent instanceof CaseContext 
			)){
				printIndent(ind);
				ind += 4;
				System.out.print("nested statement{\n");
		}
	}

	@Override
	public void exitCase(CaseContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		if (parent != null && ( 
			parent instanceof IfContext
			|| parent instanceof WhileContext
			|| parent instanceof BlockContext
			|| parent instanceof CaseContext 
			)){
				ind -= 4;
				printIndent(ind);
				System.out.print("}\n");
		}
		
	}

	@Override
	public void enterMethodCall(MethodCallContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitMethodCall(MethodCallContext ctx) {
		// TODO Auto-generated method stub
		
	}
   
    
}
