package compiler.listeners;

import compiler.dataStructures.Scope;
import compiler.dataStructures.ScopeMap;
import compiler.symbols.ClassSymbol;
import compiler.symbols.Param;
import compiler.symbols.Property;
import compiler.symbols.Var;
import generator.CoolListener;
import generator.CoolParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class Handler implements CoolListener {
    private Scope currentScope = new Scope("MAIN", null, 0, "MAIN");
    private final ScopeMap scopeMap = new ScopeMap();

    public Handler() {
        ClassSymbol baseClass = new ClassSymbol("Object", 0, 0);
        currentScope.insert("Object_class", baseClass);
    }

    public void redefineError(String name, String type, int row, int column) {
        String errorNumber;
        switch (type) {
            case "class":
                errorNumber = "Error101";
                break;
            case "method":
                errorNumber = "Error102";
                break;
            case "field":
                errorNumber = "Error103";
                break;
            case "variable":
                errorNumber = "Error104";
                break;
            default:
                errorNumber = "";
                break;
        }
        String errorString = String.format("%s : in line[%d:%d], %s [%s] has been already defined",
                errorNumber, row, column, type, name);
        System.out.println(errorString);
    }

    public void undefinedError(String name, String type, int row, int column) {
        String errorNumber;
        switch (type) {
            case "class":
                errorNumber = "Error105";
                break;
            case "method":
                errorNumber = "Error106";
                break;
            case "field":
                errorNumber = "Error107";
                break;
            case "variable":
                errorNumber = "Error108";
                break;
            default:
                errorNumber = "";
                break;
        }
        String errorString = String.format("%s : in line[%d:%d],can't find %s [%s]",
                errorNumber, row, column, type, name);
        System.out.println(errorString);
    }

    public void typeNotFoundError(String type, int row, int column) {
        String errorString = String.format("Error109 : in line[%d:%d],type [%s] does not exist", row, column, type);
        System.out.println(errorString);
    }

    public boolean lookUpScopes(Scope scope, String key) {
        if (scope == null) {
            return false;
        }
        if (scope.lookup(key)) {
            return true;
        }
        if (scope.getType().equals("class")) {
            ClassSymbol symbol = (ClassSymbol) scope.getParent().getTable().getSymbolTable().get(scope.getName());
            if (!List.of("Object", "IO", "Int", "String", "Bool").contains(symbol.getParent()))
                return lookUpScopes(scopeMap.getScope(symbol.getParent() + "_class"), key);
        }
        return lookUpScopes(scope.getParent(), key);
    }

    public String findClassName(Scope scope, String objectName) {
        if (scope == null) {
            return null;
        }
        if (scope.lookup(objectName + "_variable")) {
            Var symbol = (Var) scope.getTable().getSymbolTable().get(objectName + "_variable");
            return symbol.getType();
        } else if (scope.lookup(objectName + "_parameter")) {
            Param symbol = (Param) scope.getTable().getSymbolTable().get(objectName + "_parameter");
            return symbol.getType();
        } else if (scope.lookup(objectName + "_field")) {
            Property symbol = (Property) scope.getTable().getSymbolTable().get(objectName + "_field");
            return symbol.getType();
        }
        if (scope.getType().equals("class")) {
            ClassSymbol symbol = (ClassSymbol) scope.getParent().getTable().getSymbolTable().get(scope.getName());
            if (!List.of("Object", "IO", "Int", "String", "Bool").contains(symbol.getParent()))
                return findClassName(scopeMap.getScope(symbol.getParent() + "_class"), objectName);
        }
        return findClassName(scope.getParent(), objectName);
    }


    @Override
    public void enterProgram(CoolParser.ProgramContext ctx) {

    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {

    }

    @Override
    public void enterClassdef(CoolParser.ClassdefContext ctx) {
        String className = ctx.TYPE().get(0).toString() + "_class";
        String parentName = ctx.TYPE().size() == 2 ? ctx.TYPE().get(1).toString() + "_class" : "Object_class";
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String redef = String.format("%s_%d_%d", className, row, column);
        if (!List.of("IO", "Object", "Int", "String", "Bool").contains(parentName) && !currentScope.lookup(parentName))
            undefinedError(parentName, "class", row, column);
        if (currentScope.lookup(redef)) {
            redefineError(className, "class", row, column);
            currentScope = scopeMap.getScope(redef);
            return;
        }
        currentScope = scopeMap.getScope(className);
    }

    @Override
    public void exitClassdef(CoolParser.ClassdefContext ctx) {
        currentScope = currentScope.getParent();
    }

    @Override
    public void enterObjMethodCall(CoolParser.ObjMethodCallContext ctx) {
        String newed = "";
        String parentName = "null";
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        if (ctx.children.get(0).getChildCount() == 3)
            newed = ctx.children.get(0).getChild(0).toString();
        if (ctx.children.get(0).getChildCount() == 1) {
            String objName = ctx.children.get(0).getChild(0).toString();
            newed = findClassName(currentScope, objName);
            if (newed == null)
                return;
        }
        if (ctx.TYPE() != null)
            parentName = ctx.TYPE().toString();
        String methodName = ctx.ID().toString();
        if (parentName == null) {
            if (!lookUpScopes(scopeMap.getScope(newed + "_class"), newed + "_method"))
                undefinedError(methodName, "method", row, column);
        } else if (!lookUpScopes(scopeMap.getScope(parentName + "_class"), methodName + "_method"))
            undefinedError(methodName, "method", row, column);
    }

    @Override
    public void exitObjMethodCall(CoolParser.ObjMethodCallContext ctx) {

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
    public void enterLet(CoolParser.LetContext ctx) {
        for (CoolParser.FieldDecContext f : ctx.fieldDec()) {
            String name = f.ID().toString();
            String type = f.TYPE().toString();
            int row = ctx.getStart().getLine();
            int column = ctx.getStart().getCharPositionInLine();
            String redef = String.format("%s_variable_%d_%d", name, row, column);
            if (currentScope.lookup(redef))
                redefineError(name, "variable", row, column);
            if (!List.of("Int", "String", "Bool", "Object").contains(type)
                    && !lookUpScopes(scopeMap.getScope("program"), type + "_class"))
                typeNotFoundError(type, row, column);
        }
    }

    @Override
    public void exitLet(CoolParser.LetContext ctx) {

    }

    @Override
    public void enterOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {
        String methodName = ctx.ID().toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        if (!lookUpScopes(currentScope, methodName + "_method"))
            undefinedError(methodName, "method", row, column);
    }

    @Override
    public void exitOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {

    }

    @Override
    public void enterNew(CoolParser.NewContext ctx) {
        String type = ctx.TYPE().toString();
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        if (!List.of("Int", "String", "Bool", "Object").contains(type) && !lookUpScopes(scopeMap.getScope("program"), type + "_class"))
            undefinedError(type, "class", row, column);
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
            undefinedError(name, "variable", row, column);
    }

    @Override
    public void exitAssignment(CoolParser.AssignmentContext ctx) {

    }

    @Override
    public void enterFieldDec(CoolParser.FieldDecContext ctx) {
        String fieldName = ctx.ID().toString() + "_field";
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String redef = String.format("%s_%d_%d", fieldName, row, column);
        if (currentScope.lookup(redef)) {
            redefineError(fieldName, "field", row, column);
        }
    }

    @Override
    public void exitFieldDec(CoolParser.FieldDecContext ctx) {

    }

    @Override
    public void enterMethodDec(CoolParser.MethodDecContext ctx) {
        List<TerminalNode> idList = ctx.ID();
        List<TerminalNode> typeList = ctx.TYPE();
        String methodName = idList.get(0).toString() + "_method";
        int row = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String redef = String.format("%s_%d_%d", methodName, row, column);
        if (currentScope.lookup(redef)) {
            redefineError(methodName, "method", row, column);
            currentScope = scopeMap.getScope(redef + "_" + currentScope.getName());
        } else {
            currentScope = scopeMap.getScope(methodName + "_" + currentScope.getName());
        }
        for (int i = 1; i < idList.size(); i++) {
            String paramName = idList.get(i).toString() + "_parameter";
            redef = String.format("%s_%d_%d", paramName, row, column);
            if (currentScope.lookup(redef))
                redefineError(paramName, "parameter", row, column);
        }
    }

    @Override
    public void exitMethodDec(CoolParser.MethodDecContext ctx) {
        currentScope = currentScope.getParent();
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
