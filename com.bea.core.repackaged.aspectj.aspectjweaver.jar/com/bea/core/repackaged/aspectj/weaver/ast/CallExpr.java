package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;

public class CallExpr extends Expr {
   private final Member method;
   private final Expr[] args;
   private final ResolvedType returnType;

   public CallExpr(Member m, Expr[] args, ResolvedType returnType) {
      this.method = m;
      this.args = args;
      this.returnType = returnType;
   }

   public void accept(IExprVisitor v) {
      v.visit(this);
   }

   public Expr[] getArgs() {
      return this.args;
   }

   public Member getMethod() {
      return this.method;
   }

   public ResolvedType getType() {
      return this.returnType;
   }
}
