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
        currentScope = currentScope.getParent();
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
        String newed = "", parent = "";
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        if (ctx.children.get(0).getChildCount() == 3)
            newed = ctx.children.get(0).getChild(0).toString();
        if (ctx.children.get(0).getChildCount() == 1) {
            String objName = ctx.children.get(0).getChild(0).toString();
            newed = FindClassName(currentScope, objName);
            if (newed == null)
                return;
        }
        if (ctx.TYPE() != null)
            parent = ctx.TYPE().toString();
        String name = ctx.ID().toString();
        if (parent.equals("")) {
            if (!lookUpScopes(scopeMap.getScope(newed + "_class"), newed + "_method"))
                printUndefinedError(name, "method", row, column);
        } else if (!lookUpScopes(scopeMap.getScope(parent + "_class"), name + "_method"))
            printUndefinedError(name, "method", row, column);
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
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String name = String.format("while_%d_%d", row, column);
        currentScope = scopeMap.getScope(name);
    }

    @Override
    public void exitWhile(CoolParser.WhileContext ctx) {
        currentScope = currentScope.getParent();
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
        for (CoolParser.FieldDecContext f : ctx.fieldDec()) {
            String name = f.ID().toString();
            String type = f.TYPE().toString();
            int row = ctx.getStart().getLine();
            int column = ctx.getStart().getCharPositionInLine();
            String reDefinedName = name + "_variable_" + row + "_" + column;
            String redef = String.format("%s_%d_%d", name, row, column);
            if (currentScope.lookup(redef))
                printRedefineError(name, "variable", row, column);
            if (!type.equals("Int") && !type.equals("String") && !type.equals("Bool") && !type.equals("Object"))
                if (!lookUpScopes(scopeMap.getScope("program"), type + "_class"))
                    printTypeNotFoundError(type, row, column);
        }
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
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String name = String.format("if_%d_%d", row, column);
        currentScope = scopeMap.getScope(name);
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
        String methodName = ctx.ID().toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        if (!lookUpScopes(currentScope, methodName + "_method"))
            printUndefinedError(methodName, "method", row, column);
    }

    @Override
    public void exitOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {

    }

    @Override
    public void enterNew(CoolParser.NewContext ctx) {
        String type = ctx.TYPE().toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        if (!type.equals("Int") && !type.equals("String") && !type.equals("Bool") && !type.equals("Object"))
            if (!lookUpScopes(scopeMap.getScope("program"), type + "_class"))
                printUndefinedError(type, "class", row, column);
    }

    @Override
    public void exitNew(CoolParser.NewContext ctx) {

    }

    @Override
    public void enterAssignment(CoolParser.AssignmentContext ctx) {
        String name = ctx.ID().toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        if (!lookUpScopes(currentScope, name + "_variable") && !lookUpScopes(currentScope, name + "_field")
                && !lookUpScopes(currentScope, name + "_parameter"))
            printUndefinedError(name, "variable", row, column);
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
        String name = ctx.ID().toString() + "_field";
        String type = ctx.TYPE().toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String redef = String.format("%s_%d_%d", name, row, column);
        if (currentScope.lookup(redef)) {
            printRedefineError(name, "field", row, column);
        }
    }

    @Override
    public void exitFieldDec(CoolParser.FieldDecContext ctx) {

    }

    @Override
    public void enterMethodDec(CoolParser.MethodDecContext ctx) {
        List<TerminalNode> idList = ctx.ID();
        List<TerminalNode> typeList = ctx.TYPE();
        String name = idList.get(0).toString() + "_method";
        String type = typeList.get(0).toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String redef = String.format("%s_%d_%d", name, row, column);
        if (currentScope.lookup(redef)) {
            printRedefineError(name, "method", row, column);
            currentScope = scopeMap.getScope(redef + "_" + currentScope.getName());
        } else {
            currentScope = scopeMap.getScope(name + "_" + currentScope.getName());
        }
        for (int i = 1, j = 0; i < idList.size(); j = (++i) - 1) {
            String paramName = idList.get(i).toString() + "_parameter";
            String paramType = typeList.get(j).toString();
            redef = String.format("%s_%d_%d", paramName, row, column);
            if (currentScope.lookup(redef))
                printRedefineError(paramName, "parameter", row, column);
        }
    }

    @Override
    public void exitMethodDec(CoolParser.MethodDecContext ctx) {
        currentScope = currentScope.getParent();
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
