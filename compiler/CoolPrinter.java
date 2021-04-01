package compiler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

// import generator.CoolLexer;
import generator.CoolListener;
import generator.CoolParser.*;

public class CoolPrinter implements CoolListener{
	FileWriter fw;
	// indentation level (multiples of 4)
	public int ind = 0;
	// hashmap of all classes in program and their children
	public HashMap<String, List<String>> classes = new HashMap<String, List<String>>();

	// initialise filewriter for output
	public CoolPrinter (String path){
		try {
			fw = new FileWriter(path);
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	// writer function to generate output
	public void writeToFile(String str) {
        try {
            fw.write(str);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

	// handler for inheritance tree
	public void classInheritance(){
		for (String par: classes.keySet()){
			if (!classes.values().stream().anyMatch(l -> l.contains(par)) && par != "Object" ){
				classes.putIfAbsent("Object", new ArrayList<String>());
            	classes.get("Object").add(par);
			}			
		}
		System.out.println("\n -------------------------------------------- "
		 					+ "\n\n\t\tInheritance Tree:\n");
		ind = 0;
		printInheritance("Object");
	}

	// recursive method to print class names
	public void printInheritance (String node) {
		System.out.print('\u2515');
		printIndent(ind, '\u2501', false);
		System.out.println(" " + node);
		ind += 4;
		if (classes.containsKey(node)){
			for (String child : classes.get(node)){
				printInheritance(child);			
			}
		}
		ind -= 4;
	}

	// prints indents at beginning of each line
	public void printIndent (int ind, char ch, boolean file){
		for (int i = 0; i < ind; i++){
			System.out.print(ch);
			if (file)
				writeToFile(Character.toString(ch));
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
		writeToFile("program start{\n");
		printIndent(ind, ' ', true);
        ind += 4;
	}

	@Override
	public void exitProgram(ProgramContext ctx) {
		ind -= 4;
		printIndent(ind, ' ', true);
		System.out.print("}\n");
		writeToFile("}\n");
		try{
			fw.close();
		}
		catch (IOException err){
			err.printStackTrace();
		}
		classInheritance();
	}

	@Override
	public void enterClassdef(ClassdefContext ctx) {
		printIndent(ind, ' ', true);
		String classString = "class: %s/ class parents: %s, {\n";
		if (ctx.classParent == null){
			System.out.print(String.format(classString, ctx.className.getText(), "object"));
			writeToFile(String.format(classString, ctx.className.getText(), "object"));
			classes.putIfAbsent("Object", new ArrayList<String>());
            classes.get("Object").add(ctx.className.getText());
		}
		else{
			System.out.print(String.format(classString, ctx.className.getText(), ctx.classParent.getText()));
			writeToFile(String.format(classString, ctx.className.getText(), ctx.classParent.getText()));
			classes.putIfAbsent(ctx.classParent.getText(), new ArrayList<String>());
            classes.get(ctx.classParent.getText()).add(ctx.className.getText());
		}
		ind += 4;
	}

	@Override
	public void exitClassdef(ClassdefContext ctx) {
		ind -= 4;
		printIndent(ind, ' ', true);
		System.out.print("}\n");		
		writeToFile("}\n");		
	}

	@Override
	public void enterFeature(FeatureContext ctx) {
	}

	@Override
	public void exitFeature(FeatureContext ctx) {
	}

	@Override
	public void enterMethodDec(MethodDecContext ctx) {
		printIndent(ind, ' ', true);
		String methodStr = "class method: %s/ return type=%s{\n";
		System.out.print(
				String.format(methodStr, ctx.methodName.getText(), ctx.returnType.getText())
			);
		writeToFile(
				String.format(methodStr, ctx.methodName.getText(), ctx.returnType.getText())
			);
		ind+= 4;
		int argCount = ctx.parameterName.size();
		if(argCount > 0){
			printIndent(ind, ' ', true);
			System.out.print("parameters list= [");
			writeToFile("parameters list= [");
			String params = "";
			for (int i=0; i < argCount; i++){
				params += ctx.parameterType.get(i).getText() + " " + ctx.parameterName.get(i).getText() + ", ";
			}
			if (argCount >= 1)
				params = params.substring(0, params.length()-2);
			System.out.print(params + " ]\n");
			writeToFile(params + " ]\n");
		}
		
	}
	@Override
	public void exitMethodDec(MethodDecContext ctx) {
		ind -= 4;
		printIndent(ind, ' ', true);
		System.out.print("}\n");
		writeToFile("}\n");
	}

	@Override
	public void enterFormal(FormalContext ctx) {
		System.out.print(ctx.parameterType.getText() + " " + ctx.parameterName.getText());
		writeToFile (ctx.parameterType.getText() + " " + ctx.parameterName.getText());
	}

	@Override
	public void exitFormal(FormalContext ctx) {
	}

	@Override
	public void enterFieldDec(FieldDecContext ctx) {
		
		printIndent(ind, ' ', true);
		String fieldStr = "field: %s/ type=%s\n";
		System.out.print(
			String.format(fieldStr, ctx.fieldName.getText(), ctx.fieldType.getText())
		);
		writeToFile(
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
				printIndent(ind, ' ', true);
				ind += 4;
				System.out.print("nested statement{\n");
				writeToFile("nested statement{\n");
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
				printIndent(ind, ' ', true);
				System.out.print("}\n");
				writeToFile("}\n");
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
	}

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
				printIndent(ind, ' ', true);
				ind += 4;
				System.out.print("nested statement{\n");
				writeToFile("nested statement{\n");
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
				printIndent(ind, ' ', true);
				System.out.print("}\n");
				writeToFile("}\n");
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
				printIndent(ind, ' ', true);
				ind += 4;
				System.out.print("nested statement{\n");
				writeToFile("nested statement{\n");
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
				printIndent(ind, ' ', true);
				System.out.print("}\n");
				writeToFile("}\n");
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
				printIndent(ind, ' ', true);
				ind += 4;
				System.out.print("nested statement{\n");
				writeToFile("nested statement{\n");
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
				printIndent(ind, ' ', true);
				System.out.print("}\n");
				writeToFile("}\n");
		}
		
	}

	@Override
	public void enterObjMethodCall(ObjMethodCallContext ctx) {
		
	}

	@Override
	public void exitObjMethodCall(ObjMethodCallContext ctx) {
		
	}

	@Override
	public void enterOwnMethodCall(OwnMethodCallContext ctx) {
		
	}

	@Override
	public void exitOwnMethodCall(OwnMethodCallContext ctx) {
		
	}
   
    
}
