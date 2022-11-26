package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public class HasAnnotation extends Test {
   private Var v;
   private ResolvedType annType;

   public HasAnnotation(Var v, ResolvedType annType) {
      this.v = v;
      this.annType = annType;
   }

   public void accept(ITestVisitor v) {
      v.visit(this);
   }

   public String toString() {
      return "(" + this.v + " has annotation @" + this.annType + ")";
   }

   public boolean equals(Object other) {
      if (!(other instanceof HasAnnotation)) {
         return false;
      } else {
         HasAnnotation o = (HasAnnotation)other;
         return o.v.equals(this.v) && o.annType.equals(this.annType);
      }
   }

   public int hashCode() {
      return this.v.hashCode() * 37 + this.annType.hashCode();
   }

   public Var getVar() {
      return this.v;
   }

   public UnresolvedType getAnnotationType() {
      return this.annType;
   }
}
