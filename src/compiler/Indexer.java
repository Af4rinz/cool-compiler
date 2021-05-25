package compiler;

import compiler.symbols.*;
import compiler.symbols.Class;
import generator.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

class Indexer implements CoolListener {
    private Scope currentScope = new Scope("MAIN", null, 0, "MAIN");
    private int currentDepth = 0;
    private ScopeMap scopeMap = new ScopeMap();

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

    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {

    }

    @Override
    public void enterClassdef(CoolParser.ClassdefContext ctx) {
        Class freshClass;
        String name = ctx.TYPE().get(0).toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String key = checkScope(currentScope, name, "class")
                ? String.format("%s_class_%d_%d", name, row, column)
                : String.format("%s_class", name);
        freshClass = ctx.TYPE().size() == 2
                ? new Class(name, ctx.TYPE().get(1).toString(), row, column)
                : new Class(name, "Object", row, column);
        currentScope.insert(key, freshClass);
        currentDepth++;
        Scope freshScope = new Scope(key, currentScope, currentDepth, "class");
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        scopeMap.insertScope(key, currentScope);
    }

    @Override
    public void exitClassdef(CoolParser.ClassdefContext ctx) {
        currentScope = currentScope.getParent();
        currentDepth--;
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
        int row = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String name = String.format("while_%d_%d", row, col);
        Statement statement = new Statement(name, row, col);
        currentScope.insert(name, statement);
        currentDepth++;
        Scope freshScope = new Scope(name, currentScope, currentDepth, "statement");
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        scopeMap.insertScope(name, currentScope);
    }

    @Override
    public void exitWhile(CoolParser.WhileContext ctx) {
        currentScope = currentScope.getParent();
        currentDepth--;
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
        List<CoolParser.FieldDecContext> fieldList = ctx.fieldDec();
        for (CoolParser.FieldDecContext f : fieldList) {
            String name = f.fieldName.toString();
            int row = ctx.getStart().getLine();
            int col = ctx.getStart().getCharPositionInLine();
            String key = checkScope(currentScope, name, "class")
                    ? String.format("%s_variable_%d_%d", name, row, col)
                    : String.format("%s_variable", name);
            currentScope.insert(key, new Var(name, f.fieldType.toString(), row, col));
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
        int col = ctx.getStart().getCharPositionInLine();
        String name = String.format("if_%d_%d", row, col);
        Statement statement = new Statement(name, row, col);
        currentScope.insert(name, statement);
        currentDepth++;
        Scope freshScope = new Scope(name, currentScope, currentDepth, "if");
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        scopeMap.insertScope(name, currentScope);

    }

    @Override
    public void exitIf(CoolParser.IfContext ctx) {
        currentDepth--;
        currentScope = currentScope.getParent();
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
        String key;
        String name = ctx.ID().toString();
        String type = ctx.TYPE().toString();
        int row = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        key = checkScope(currentScope, name, "field")
                ? String.format("%s_field_%d_%d", name, row, col) : String.format("%s_parameter", name);
        currentScope.insert(key, new Property(name, type, row, col));
    }

    @Override
    public void exitFieldDec(CoolParser.FieldDecContext ctx) {

    }

    @Override
    public void enterMethodDec(CoolParser.MethodDecContext ctx) {
        List<TerminalNode> idList = ctx.ID(), typeList = ctx.TYPE();
        String name = idList.get(0).toString(), returnType = typeList.get(typeList.size() - 1).toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String key = checkScope(currentScope, name, "class")
                ? String.format("%s_class_%d_%d", name, row, column)
                : String.format("%s_class", name);
        Method method = new Method(name, returnType, row, column);
        for (int i = 1; i < idList.size(); i++)
            method.addParam(idList.get(i).toString());
        currentScope.insert(name, method);
        currentDepth++;
        Scope freshScope = new Scope(name, currentScope, currentDepth, "method");
        for (int i = 1, j = 0; i < idList.size(); j = (++i) - 1) {
            String paramName = idList.get(i).toString();
            String paramType = typeList.get(j).toString();
            String paramKey = checkScope(currentScope, paramName, "parameter")
                    ? String.format("%s_parameter_%d_%d", paramName, row, column)
                    : String.format("%s_parameter", paramName);
            Param p = new Param(paramName, paramType, row, column);
            freshScope.insert(paramKey, p);
        }
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        scopeMap.insertScope(String.format("%s_%s", name, currentScope.getParent().getName()), currentScope);
    }

    @Override
    public void exitMethodDec(CoolParser.MethodDecContext ctx) {
        currentDepth--;
        currentScope = currentScope.getParent();
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