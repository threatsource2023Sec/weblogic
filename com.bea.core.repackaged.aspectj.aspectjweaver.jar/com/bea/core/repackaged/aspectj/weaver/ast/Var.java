package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.ResolvedType;

public class Var extends Expr {
   public static final Var[] NONE = new Var[0];
   ResolvedType variableType;

   public Var(ResolvedType variableType) {
      this.variableType = variableType;
   }

   public ResolvedType getType() {
      return this.variableType;
   }

   public String toString() {
      return "(Var " + this.variableType + ")";
   }

   public void accept(IExprVisitor v) {
      v.visit(this);
   }

   public Var getAccessorForValue(ResolvedType formalType, String formalName) {
      throw new IllegalStateException("Only makes sense for annotation variables");
   }
}
