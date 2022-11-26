package com.bea.core.repackaged.jdt.internal.compiler.ast;

public class OpensStatement extends PackageVisibilityStatement {
   public OpensStatement(ImportReference pkgRef) {
      this(pkgRef, (ModuleReference[])null);
   }

   public OpensStatement(ImportReference pkgRef, ModuleReference[] targets) {
      super(pkgRef, targets);
   }

   protected int computeSeverity(int problemId) {
      return 0;
   }

   public StringBuffer print(int indent, StringBuffer output) {
      printIndent(indent, output);
      output.append("opens ");
      super.print(0, output);
      output.append(";");
      return output;
   }
}
