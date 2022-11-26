package com.bea.core.repackaged.aspectj.weaver.ast;

public class Or extends Test {
   Test left;
   Test right;

   public Or(Test left, Test right) {
      this.left = left;
      this.right = right;
   }

   public void accept(ITestVisitor v) {
      v.visit(this);
   }

   public String toString() {
      return "(" + this.left + " || " + this.right + ")";
   }

   public boolean equals(Object other) {
      if (!(other instanceof Or)) {
         return false;
      } else {
         Or o = (Or)other;
         return o.left.equals(this.left) && o.right.equals(this.right);
      }
   }

   public int hashCode() {
      int result = 19;
      result = 37 * result + this.left.hashCode();
      result = 37 * result + this.right.hashCode();
      return result;
   }

   public Test getLeft() {
      return this.left;
   }

   public Test getRight() {
      return this.right;
   }
}
