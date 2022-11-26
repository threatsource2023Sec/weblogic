package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public class ProblemBinding extends Binding {
   public char[] name;
   public ReferenceBinding searchType;
   private int problemId;

   public ProblemBinding(char[][] compoundName, int problemId) {
      this(CharOperation.concatWith(compoundName, '.'), problemId);
   }

   public ProblemBinding(char[][] compoundName, ReferenceBinding searchType, int problemId) {
      this(CharOperation.concatWith(compoundName, '.'), searchType, problemId);
   }

   ProblemBinding(char[] name, int problemId) {
      this.name = name;
      this.problemId = problemId;
   }

   ProblemBinding(char[] name, ReferenceBinding searchType, int problemId) {
      this(name, problemId);
      this.searchType = searchType;
   }

   public final int kind() {
      return 7;
   }

   public final int problemId() {
      return this.problemId;
   }

   public char[] readableName() {
      return this.name;
   }
}
