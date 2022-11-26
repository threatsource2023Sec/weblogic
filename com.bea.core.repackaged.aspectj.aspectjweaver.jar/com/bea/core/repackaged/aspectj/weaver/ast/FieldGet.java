package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;

public class FieldGet extends Expr {
   Member field;
   ResolvedType resolvedType;

   public FieldGet(Member field, ResolvedType resolvedType) {
      this.field = field;
      this.resolvedType = resolvedType;
   }

   public ResolvedType getType() {
      return this.resolvedType;
   }

   public String toString() {
      return "(FieldGet " + this.field + ")";
   }

   public void accept(IExprVisitor v) {
      v.visit(this);
   }

   public Member getField() {
      return this.field;
   }
}
