package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class ProblemPackageBinding extends PackageBinding {
   private int problemId;

   ProblemPackageBinding(char[][] compoundName, int problemId, LookupEnvironment environment) {
      this.compoundName = compoundName;
      this.problemId = problemId;
      this.environment = environment;
   }

   ProblemPackageBinding(char[] name, int problemId, LookupEnvironment environment) {
      this(new char[][]{name}, problemId, environment);
   }

   public final int problemId() {
      return this.problemId;
   }
}
