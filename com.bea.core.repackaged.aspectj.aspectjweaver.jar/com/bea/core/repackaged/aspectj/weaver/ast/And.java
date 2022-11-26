package com.bea.core.repackaged.aspectj.weaver.ast;

public class And extends Test {
   Test left;
   Test right;

   public And(Test left, Test right) {
      this.left = left;
      this.right = right;
   }

   public void accept(ITestVisitor v) {
      v.visit(this);
   }

   public String toString() {
      return "(" + this.left + " && " + this.right + ")";
   }

   public boolean equals(Object other) {
      if (!(other instanceof And)) {
         return false;
      } else {
         And o = (And)other;
         return o.left.equals(this.left) && o.right.equals(this.right);
      }
   }

   public Test getLeft() {
      return this.left;
   }

   public Test getRight() {
      return this.right;
   }
}
