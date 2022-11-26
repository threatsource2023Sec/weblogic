package com.bea.core.repackaged.aspectj.weaver.ast;

public class Not extends Test {
   Test test;

   public Not(Test test) {
      this.test = test;
   }

   public void accept(ITestVisitor v) {
      v.visit(this);
   }

   public Test getBody() {
      return this.test;
   }

   public String toString() {
      return "!" + this.test;
   }

   public boolean equals(Object other) {
      if (other instanceof Not) {
         Not o = (Not)other;
         return o.test.equals(this.test);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.test.hashCode();
   }
}
