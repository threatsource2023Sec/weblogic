package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class ProblemFieldBinding extends FieldBinding {
   private int problemId;
   public FieldBinding closestMatch;

   public ProblemFieldBinding(ReferenceBinding declaringClass, char[] name, int problemId) {
      this((FieldBinding)null, declaringClass, name, problemId);
   }

   public ProblemFieldBinding(FieldBinding closestMatch, ReferenceBinding declaringClass, char[] name, int problemId) {
      this.closestMatch = closestMatch;
      this.declaringClass = declaringClass;
      this.name = name;
      this.problemId = problemId;
   }

   public final int problemId() {
      return this.problemId;
   }
}
