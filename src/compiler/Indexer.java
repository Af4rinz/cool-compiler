package compiler;

import compiler.symbols.Class;
import compiler.symbols.Method;
import compiler.symbols.Param;
import compiler.symbols.Symbol;
import generator.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

class Indexer implements CoolListener {
    private Scope currentScope = new Scope("MAIN", null, 0);
    private int currentDepth = 0;

    @Override
    public void enterProgram(CoolParser.ProgramContext ctx) {

    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {

    }

    @Override
    public void enterClassdef(CoolParser.ClassdefContext ctx) {
        Symbol freshClass;
        List typeList = ctx.TYPE();
        String name = typeList.get(0).toString();
        if (typeList.size() == 1)
            freshClass = new Class(name);
        else
            freshClass = new Class(name, typeList.get(1).toString());

        currentScope.insert(name, freshClass);
        Scope freshScope = new Scope(name, currentScope, currentDepth + 1);
        currentScope.addChild(freshScope);
        currentScope = freshScope;
        currentDepth++;
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
        List<TerminalNode> idList = ctx.ID(), typeList = ctx.TYPE();
        String name = idList.get(0).toString(), returnType = typeList.get(typeList.size() - 1).toString();
        Method method = new Method(name, returnType);
        Scope freshScope = new Scope(name, currentScope, currentDepth + 1);
        for (int i = 1, j = 0; i < idList.size(); j = (++i) - 1) {
            String paramName = idList.get(i).toString(), paramType = typeList.get(j).toString();
            method.addParam(paramName);
            freshScope.insert(paramName, new Param(paramName, paramType));
        }
        currentScope.insert(name, method);
        currentScope.addChild(freshScope);
        currentDepth++;
        currentScope = freshScope;
    }

    @Override
    public void exitMethodDec(CoolParser.MethodDecContext ctx) {

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