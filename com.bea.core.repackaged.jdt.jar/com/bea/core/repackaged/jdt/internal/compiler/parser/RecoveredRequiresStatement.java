package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.RequiresStatement;

public class RecoveredRequiresStatement extends RecoveredModuleStatement {
   public RecoveredRequiresStatement(RequiresStatement requiresStatement, RecoveredElement parent, int bracketBalance) {
      super(requiresStatement, parent, bracketBalance);
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered requires: " + super.toString();
   }

   public RequiresStatement updatedRequiresStatement() {
      return (RequiresStatement)this.moduleStatement;
   }
}
