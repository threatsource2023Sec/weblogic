package com.bea.core.repackaged.aspectj.weaver.ast;

public interface IExprVisitor {
   void visit(Var var1);

   void visit(FieldGet var1);

   void visit(CallExpr var1);
}
