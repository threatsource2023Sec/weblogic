package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.OpensStatement;

public class RecoveredOpensStatement extends RecoveredPackageVisibilityStatement {
   public RecoveredOpensStatement(OpensStatement opensStatement, RecoveredElement parent, int bracketBalance) {
      super(opensStatement, parent, bracketBalance);
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered opens stmt: " + super.toString();
   }
}
