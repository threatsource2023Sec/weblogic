package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public class Instanceof extends Test {
   Var var;
   UnresolvedType type;

   public Instanceof(Var left, UnresolvedType right) {
      this.var = left;
      this.type = right;
   }

   public void accept(ITestVisitor v) {
      v.visit(this);
   }

   public String toString() {
      return "(" + this.var + " instanceof " + this.type + ")";
   }

   public boolean equals(Object other) {
      if (!(other instanceof Instanceof)) {
         return false;
      } else {
         Instanceof o = (Instanceof)other;
         return o.var.equals(this.var) && o.type.equals(this.type);
      }
   }

   public int hashCode() {
      return this.var.hashCode() * 37 + this.type.hashCode();
   }

   public Var getVar() {
      return this.var;
   }

   public UnresolvedType getType() {
      return this.type;
   }
}
