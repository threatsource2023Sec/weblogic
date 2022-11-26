package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.Member;

public class FieldGetCall extends Test {
   private final Member field;
   private final Member method;
   private final Expr[] args;

   public FieldGetCall(Member f, Member m, Expr[] args) {
      this.field = f;
      this.method = m;
      this.args = args;
   }

   public void accept(ITestVisitor v) {
      v.visit(this);
   }

   public Expr[] getArgs() {
      return this.args;
   }

   public Member getMethod() {
      return this.method;
   }

   public Member getField() {
      return this.field;
   }
}
