package ErrorHandler;

import compiler.*;
import generator.CoolListener;
import generator.CoolParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Handler implements CoolListener, Detector {
    private Scope currentScope;
    private ScopeMap scopeMap = new ScopeMap();

    @Override
    public void printRedefineError(String name, String type, int row, int column) {

    }

    @Override
    public void printUndefinedError(String name, String type, int row, int column) {

    }

    @Override
    public void printTypeNotFoundError(String type, int row, int column) {

    }

    @Override
    public boolean lookUpScopes(Scope scope, String key) {
        return false;
    }

    @Override
    public String FindClassName(Scope scope, String objectName) {
        return null;
    }

    @Override
    public void enterProgram(CoolParser.ProgramContext ctx) {

    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {

    }

    @Override
    public void enterClassdef(CoolParser.ClassdefContext ctx) {
        String name = ctx.TYPE().get(0).toString() + "_class";
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String parentName = ctx.TYPE().size() == 2 ? ctx.TYPE().get(1).toString() : "Object";
        String redef = String.format("%s_%d_%d", name, row, column);
        List<String> types = Arrays.asList("IO", "Object", "Int", "String", "Bool");
        if (types.contains(parentName)) {
            parentName += "_class";
            if (!currentScope.lookup(parentName))
                printUndefinedError(parentName, "class", row, column);
        }
        if (currentScope.lookup(redef)) {
            printRedefineError(name, "class", row, column);
            currentScope = scopeMap.getScope(redef);
        } else
            currentScope = scopeMap.getScope(name);
    }

    @Override
    public void exitClassdef(CoolParser.ClassdefContext ctx) {
        currentScope=currentScope.getParent();
    }

    @Override
    public void enterMethod(CoolParser.MethodContext ctx) {

    }

    @Override
    public void exitMethod(CoolParser.MethodContext ctx) {

    }

    @Override
    public void enterField(CoolParser.FieldContext ctx) {

    }

    @Override
    public void exitField(CoolParser.FieldContext ctx) {

    }

    @Override
    public void enterMinus(CoolParser.MinusContext ctx) {

    }

    @Override
    public void exitMinus(CoolParser.MinusContext ctx) {

    }

    @Override
    public void enterObjMethodCall(CoolParser.ObjMethodCallContext ctx) {

    }

    @Override
    public void exitObjMethodCall(CoolParser.ObjMethodCallContext ctx) {

    }

    @Override
    public void enterString(CoolParser.StringContext ctx) {

    }

    @Override
    public void exitString(CoolParser.StringContext ctx) {

    }

    @Override
    public void enterIsvoid(CoolParser.IsvoidContext ctx) {

    }

    @Override
    public void exitIsvoid(CoolParser.IsvoidContext ctx) {

    }

    @Override
    public void enterWhile(CoolParser.WhileContext ctx) {

    }

    @Override
    public void exitWhile(CoolParser.WhileContext ctx) {

    }

    @Override
    public void enterNot(CoolParser.NotContext ctx) {

    }

    @Override
    public void exitNot(CoolParser.NotContext ctx) {

    }

    @Override
    public void enterLessequal(CoolParser.LessequalContext ctx) {

    }

    @Override
    public void exitLessequal(CoolParser.LessequalContext ctx) {

    }

    @Override
    public void enterBlock(CoolParser.BlockContext ctx) {

    }

    @Override
    public void exitBlock(CoolParser.BlockContext ctx) {

    }

    @Override
    public void enterLet(CoolParser.LetContext ctx) {

    }

    @Override
    public void exitLet(CoolParser.LetContext ctx) {

    }

    @Override
    public void enterDivide(CoolParser.DivideContext ctx) {

    }

    @Override
    public void exitDivide(CoolParser.DivideContext ctx) {

    }

    @Override
    public void enterId(CoolParser.IdContext ctx) {

    }

    @Override
    public void exitId(CoolParser.IdContext ctx) {

    }

    @Override
    public void enterMultiply(CoolParser.MultiplyContext ctx) {

    }

    @Override
    public void exitMultiply(CoolParser.MultiplyContext ctx) {

    }

    @Override
    public void enterIf(CoolParser.IfContext ctx) {

    }

    @Override
    public void exitIf(CoolParser.IfContext ctx) {

    }

    @Override
    public void enterCase(CoolParser.CaseContext ctx) {

    }

    @Override
    public void exitCase(CoolParser.CaseContext ctx) {

    }

    @Override
    public void enterOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {

    }

    @Override
    public void exitOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {

    }

    @Override
    public void enterNew(CoolParser.NewContext ctx) {

    }

    @Override
    public void exitNew(CoolParser.NewContext ctx) {

    }

    @Override
    public void enterAssignment(CoolParser.AssignmentContext ctx) {

    }

    @Override
    public void exitAssignment(CoolParser.AssignmentContext ctx) {

    }

    @Override
    public void enterParantheses(CoolParser.ParanthesesContext ctx) {

    }

    @Override
    public void exitParantheses(CoolParser.ParanthesesContext ctx) {

    }

    @Override
    public void enterFalse(CoolParser.FalseContext ctx) {

    }

    @Override
    public void exitFalse(CoolParser.FalseContext ctx) {

    }

    @Override
    public void enterLess(CoolParser.LessContext ctx) {

    }

    @Override
    public void exitLess(CoolParser.LessContext ctx) {

    }

    @Override
    public void enterInt(CoolParser.IntContext ctx) {

    }

    @Override
    public void exitInt(CoolParser.IntContext ctx) {

    }

    @Override
    public void enterPlus(CoolParser.PlusContext ctx) {

    }

    @Override
    public void exitPlus(CoolParser.PlusContext ctx) {

    }

    @Override
    public void enterEqual(CoolParser.EqualContext ctx) {

    }

    @Override
    public void exitEqual(CoolParser.EqualContext ctx) {

    }

    @Override
    public void enterNegate(CoolParser.NegateContext ctx) {

    }

    @Override
    public void exitNegate(CoolParser.NegateContext ctx) {

    }

    @Override
    public void enterTrue(CoolParser.TrueContext ctx) {

    }

    @Override
    public void exitTrue(CoolParser.TrueContext ctx) {

    }

    @Override
    public void enterFormal(CoolParser.FormalContext ctx) {

    }

    @Override
    public void exitFormal(CoolParser.FormalContext ctx) {

    }

    @Override
    public void enterFieldDec(CoolParser.FieldDecContext ctx) {

    }

    @Override
    public void exitFieldDec(CoolParser.FieldDecContext ctx) {

    }

    @Override
    public void enterMethodDec(CoolParser.MethodDecContext ctx) {

    }

    @Override
    public void exitMethodDec(CoolParser.MethodDecContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
