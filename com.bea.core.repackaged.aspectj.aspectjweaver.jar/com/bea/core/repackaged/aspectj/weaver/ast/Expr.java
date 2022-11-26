package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;

public abstract class Expr extends ASTNode {
   public static final Expr[] NONE = new Expr[0];

   public abstract void accept(IExprVisitor var1);

   public abstract ResolvedType getType();

   public static CallExpr makeCallExpr(Member member, Expr[] exprs, ResolvedType returnType) {
      return new CallExpr(member, exprs, returnType);
   }
}
