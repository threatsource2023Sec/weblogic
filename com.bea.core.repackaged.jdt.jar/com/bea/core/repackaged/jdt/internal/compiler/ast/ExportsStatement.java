package com.bea.core.repackaged.jdt.internal.compiler.ast;

public class ExportsStatement extends PackageVisibilityStatement {
   public ExportsStatement(ImportReference pkgRef) {
      this(pkgRef, (ModuleReference[])null);
   }

   public ExportsStatement(ImportReference pkgRef, ModuleReference[] targets) {
      super(pkgRef, targets);
   }

   public StringBuffer print(int indent, StringBuffer output) {
      printIndent(indent, output);
      output.append("exports ");
      super.print(0, output);
      output.append(";");
      return output;
   }
}
