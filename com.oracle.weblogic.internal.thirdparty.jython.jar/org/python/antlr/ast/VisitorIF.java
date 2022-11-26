package org.python.antlr.ast;

public interface VisitorIF {
   Object visitModule(Module var1) throws Exception;

   Object visitInteractive(Interactive var1) throws Exception;

   Object visitExpression(Expression var1) throws Exception;

   Object visitSuite(Suite var1) throws Exception;

   Object visitFunctionDef(FunctionDef var1) throws Exception;

   Object visitClassDef(ClassDef var1) throws Exception;

   Object visitReturn(Return var1) throws Exception;

   Object visitDelete(Delete var1) throws Exception;

   Object visitAssign(Assign var1) throws Exception;

   Object visitAugAssign(AugAssign var1) throws Exception;

   Object visitPrint(Print var1) throws Exception;

   Object visitFor(For var1) throws Exception;

   Object visitWhile(While var1) throws Exception;

   Object visitIf(If var1) throws Exception;

   Object visitWith(With var1) throws Exception;

   Object visitRaise(Raise var1) throws Exception;

   Object visitTryExcept(TryExcept var1) throws Exception;

   Object visitTryFinally(TryFinally var1) throws Exception;

   Object visitAssert(Assert var1) throws Exception;

   Object visitImport(Import var1) throws Exception;

   Object visitImportFrom(ImportFrom var1) throws Exception;

   Object visitExec(Exec var1) throws Exception;

   Object visitGlobal(Global var1) throws Exception;

   Object visitExpr(Expr var1) throws Exception;

   Object visitPass(Pass var1) throws Exception;

   Object visitBreak(Break var1) throws Exception;

   Object visitContinue(Continue var1) throws Exception;

   Object visitBoolOp(BoolOp var1) throws Exception;

   Object visitBinOp(BinOp var1) throws Exception;

   Object visitUnaryOp(UnaryOp var1) throws Exception;

   Object visitLambda(Lambda var1) throws Exception;

   Object visitIfExp(IfExp var1) throws Exception;

   Object visitDict(Dict var1) throws Exception;

   Object visitSet(Set var1) throws Exception;

   Object visitListComp(ListComp var1) throws Exception;

   Object visitSetComp(SetComp var1) throws Exception;

   Object visitDictComp(DictComp var1) throws Exception;

   Object visitGeneratorExp(GeneratorExp var1) throws Exception;

   Object visitYield(Yield var1) throws Exception;

   Object visitCompare(Compare var1) throws Exception;

   Object visitCall(Call var1) throws Exception;

   Object visitRepr(Repr var1) throws Exception;

   Object visitNum(Num var1) throws Exception;

   Object visitStr(Str var1) throws Exception;

   Object visitAttribute(Attribute var1) throws Exception;

   Object visitSubscript(Subscript var1) throws Exception;

   Object visitName(Name var1) throws Exception;

   Object visitList(List var1) throws Exception;

   Object visitTuple(Tuple var1) throws Exception;

   Object visitEllipsis(Ellipsis var1) throws Exception;

   Object visitSlice(Slice var1) throws Exception;

   Object visitExtSlice(ExtSlice var1) throws Exception;

   Object visitIndex(Index var1) throws Exception;

   Object visitExceptHandler(ExceptHandler var1) throws Exception;
}
