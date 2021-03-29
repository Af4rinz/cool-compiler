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
	}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {
	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {
	}

	@Override
	public void visitTerminal(TerminalNode arg0) {
		
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
	}

	@Override
	public void exitFeature(FeatureContext ctx) {
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
		System.out.print(ctx.parameterType.getText() + " " + ctx.parameterName.getText());
	}

	@Override
	public void exitFormal(FormalContext ctx) {
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
	}

	@Override
	public void enterAssignment(AssignmentContext ctx) {
	}

	@Override
	public void exitAssignment(AssignmentContext ctx) {
	}

	@Override
	public void enterNew(NewContext ctx) {
	}

	@Override
	public void exitNew(NewContext ctx) {
	}

	@Override
	public void enterMinus(MinusContext ctx) {
	}

	@Override
	public void exitMinus(MinusContext ctx) {
	}

	@Override
	public void enterString(StringContext ctx) {		
	}

	@Override
	public void exitString(StringContext ctx) {
	}

	@Override
	public void enterIsvoid(IsvoidContext ctx) {
	}

	@Override
	public void exitIsvoid(IsvoidContext ctx) {
	}

	@Override
	public void enterParantheses(ParanthesesContext ctx) {
	}

	@Override
	public void exitParantheses(ParanthesesContext ctx) {
	}

	@Override
	public void enterFalse(FalseContext ctx) {
	}

	@Override
	public void exitFalse(FalseContext ctx) {
	}

	@Override
	public void enterLess(LessContext ctx) {
	}

	@Override
	public void exitLess(LessContext ctx) {
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
	}

	@Override
	public void exitInt(IntContext ctx) {
	}

	@Override
	public void enterPlus(PlusContext ctx) {
	}

	@Override
	public void exitPlus(PlusContext ctx) {
	}

	@Override
	public void enterEqual(EqualContext ctx) {
	}

	@Override
	public void exitEqual(EqualContext ctx) {
	}

	@Override
	public void enterNot(NotContext ctx) {
	}

	@Override
	public void exitNot(NotContext ctx) {
	}

	@Override
	public void enterNegate(NegateContext ctx) {
	}

	@Override
	public void exitNegate(NegateContext ctx) {
	}

	@Override
	public void enterTrue(TrueContext ctx) {
	}

	@Override
	public void exitTrue(TrueContext ctx) {
	}

	@Override
	public void enterLessequal(LessequalContext ctx) {

	@Override
	public void exitLessequal(LessequalContext ctx) {		
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
	}

	@Override
	public void exitLet(LetContext ctx) {
	}

	@Override
	public void enterDivide(DivideContext ctx) {
	}

	@Override
	public void exitDivide(DivideContext ctx) {
	}

	@Override
	public void enterId(IdContext ctx) {
	}

	@Override
	public void exitId(IdContext ctx) {		
	}

	@Override
	public void enterMultiply(MultiplyContext ctx) {		
	}

	@Override
	public void exitMultiply(MultiplyContext ctx) {
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
	}

	@Override
	public void exitMethodCall(MethodCallContext ctx) {
		
	}
   
    
}
