package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.Member;

public class Call extends Test {
   private final Member method;
   private final Expr[] args;

   public Call(Member m, Expr[] args) {
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
}
