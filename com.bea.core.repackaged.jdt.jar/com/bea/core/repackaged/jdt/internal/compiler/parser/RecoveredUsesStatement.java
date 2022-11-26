package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.UsesStatement;

public class RecoveredUsesStatement extends RecoveredModuleStatement {
   public RecoveredUsesStatement(UsesStatement usesStatement, RecoveredElement parent, int bracketBalance) {
      super(usesStatement, parent, bracketBalance);
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered Uses: " + super.toString();
   }

   public UsesStatement updatedUsesStatement() {
      return (UsesStatement)this.moduleStatement;
   }

   public void updateParseTree() {
      this.updatedUsesStatement();
   }
}
