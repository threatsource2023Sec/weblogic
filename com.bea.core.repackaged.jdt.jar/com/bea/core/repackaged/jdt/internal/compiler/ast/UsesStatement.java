package com.bea.core.repackaged.jdt.internal.compiler.ast;

public class UsesStatement extends ModuleStatement {
   public TypeReference serviceInterface;

   public UsesStatement(TypeReference serviceInterface) {
      this.serviceInterface = serviceInterface;
   }

   public StringBuffer print(int indent, StringBuffer output) {
      printIndent(indent, output);
      output.append("uses ");
      this.serviceInterface.print(0, output);
      output.append(";");
      return output;
   }
}
