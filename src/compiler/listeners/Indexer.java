package compiler.listeners;

import compiler.dataStructures.Scope;
import compiler.dataStructures.ScopeMap;
import compiler.symbols.*;
import generator.CoolListener;
import generator.CoolParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class Indexer implements CoolListener {
    private Scope currentScope = new Scope("MAIN", null, 0, "MAIN");
    private int currentDepth = 0;
    private final ScopeMap scopeMap = new ScopeMap();

    public Indexer() {
        ClassSymbol baseClass = new ClassSymbol("Object", 0, 0);
        currentScope.insert("Object_class", baseClass);
    }

    private boolean checkScope(Scope scope, String key, String type) {
        if (scope == null) return false;
        switch (type) {
            case "class":
                if (scope.lookup(key + "_class")) return true;
                break;
            case "method":
                if (scope.lookup(key + "_method")) return true;
                break;
            case "field":
                if (scope.lookup(key + "_field")) return true;
                break;
        }
        return checkScope(scope.getParent(), key, type);
    }

    @Override
    public void enterProgram(CoolParser.ProgramContext ctx) {
        scopeMap.insertScope("MAIN", currentScope);
    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {

    }

    @Override
    public void enterClassdef(CoolParser.ClassdefContext ctx) {
        String className = ctx.TYPE().get(0).toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String classKey = checkScope(currentScope, className, "class")
                ? String.format("%s_class_%d_%d", className, row, column)
                : String.format("%s_class", className);
        ClassSymbol freshClassSymbol = ctx.TYPE().size() == 2
                ? new ClassSymbol(className, ctx.TYPE().get(1).toString(), row, column)
                : new ClassSymbol(className, "Object", row, column);
        currentScope.insert(classKey, freshClassSymbol);
        currentDepth++;
        Scope freshScope = new Scope(classKey, currentScope, currentDepth, "class");
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        scopeMap.insertScope(classKey, currentScope);
    }

    @Override
    public void exitClassdef(CoolParser.ClassdefContext ctx) {
        currentScope = currentScope.getParent();
        currentDepth--;
    }

    @Override
    public void enterWhile(CoolParser.WhileContext ctx) {
        int row = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String whileName = String.format("while_%d_%d", row, col);
        Statement statement = new Statement("while", row, col);
        currentScope.insert(whileName, statement);
        currentDepth++;
        Scope freshScope = new Scope(whileName, currentScope, currentDepth, "statement");
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        scopeMap.insertScope(whileName, currentScope);
    }

    @Override
    public void exitWhile(CoolParser.WhileContext ctx) {
        currentScope = currentScope.getParent();
        currentDepth--;
    }

    @Override
    public void enterLet(CoolParser.LetContext ctx) {
        List<CoolParser.FieldDecContext> fieldList = ctx.fieldDec();
        for (CoolParser.FieldDecContext f : fieldList) {
            String fieldName = f.fieldName.toString();
            String fieldType = f.fieldType.toString();
            int row = ctx.getStart().getLine();
            int col = ctx.getStart().getCharPositionInLine();
            String fieldKey = checkScope(currentScope, fieldName, "variable")
                    ? String.format("%s_variable_%d_%d", fieldName, row, col)
                    : String.format("%s_variable", fieldName);
            currentScope.insert(fieldKey, new Var(fieldName, fieldType, row, col));
        }
    }

    @Override
    public void exitLet(CoolParser.LetContext ctx) {

    }

    @Override
    public void enterIf(CoolParser.IfContext ctx) {
        int row = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String ifName = String.format("if_%d_%d", row, col);
        Statement statement = new Statement("if", row, col);
        currentScope.insert(ifName, statement);
        currentDepth++;
        Scope freshScope = new Scope(ifName, currentScope, currentDepth, "statement");
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        scopeMap.insertScope(ifName, currentScope);

    }

    @Override
    public void exitIf(CoolParser.IfContext ctx) {
        currentDepth--;
        currentScope = currentScope.getParent();
    }

    @Override
    public void enterFieldDec(CoolParser.FieldDecContext ctx) {
        String fieldName = ctx.ID().toString();
        String fieldType = ctx.TYPE().toString();
        int row = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String fieldKey = checkScope(currentScope, fieldName, "field")
                ? String.format("%s_field_%d_%d", fieldName, row, col) : String.format("%s_field", fieldName);
        currentScope.insert(fieldKey, new Property(fieldName, fieldType, row, col));
    }

    @Override
    public void exitFieldDec(CoolParser.FieldDecContext ctx) {

    }

    @Override
    public void enterMethodDec(CoolParser.MethodDecContext ctx) {
        List<TerminalNode> idList = ctx.ID();
        List<TerminalNode> typeList = ctx.TYPE();
        String methodName = idList.get(0).toString();
        String returnType = typeList.get(typeList.size() - 1).toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String methodKey = checkScope(currentScope, methodName, "method")
                ? String.format("%s_method_%d_%d", methodName, row, column)
                : String.format("%s_method", methodName);
        Method method = new Method(methodName, returnType, row, column);
        for (int i = 1; i < idList.size(); i++)
            method.addParam(idList.get(i).toString());
        currentScope.insert(methodKey, method);
        currentDepth++;
        Scope freshScope = new Scope(methodKey, currentScope, currentDepth, "method");
        for (int i = 1, j = 0; i < idList.size(); j = (++i) - 1) {
            String paramName = idList.get(i).toString();
            String paramType = typeList.get(j).toString();
            String paramKey = checkScope(currentScope, paramName, "parameter")
                    ? String.format("%s_parameter_%d_%d", paramName, row, column)
                    : String.format("%s_parameter", paramName);
            Param param = new Param(paramName, paramType, row, column);
            freshScope.insert(paramKey, param);
        }
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        scopeMap.insertScope(String.format("%s_%s", methodKey, currentScope.getParent().getName()), currentScope);
    }

    @Override
    public void exitMethodDec(CoolParser.MethodDecContext ctx) {
        currentDepth--;
        currentScope = currentScope.getParent();
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
    public void exitField(CoolParser.FieldContext ctx) {

    }

    @Override
    public void enterField(CoolParser.FieldContext ctx) {

    }

    @Override
    public void exitMethod(CoolParser.MethodContext ctx) {

    }

    @Override
    public void enterMethod(CoolParser.MethodContext ctx) {

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