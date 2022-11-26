package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ExportsStatement;

public class RecoveredExportsStatement extends RecoveredPackageVisibilityStatement {
   public RecoveredExportsStatement(ExportsStatement exportsStatement, RecoveredElement parent, int bracketBalance) {
      super(exportsStatement, parent, bracketBalance);
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered exports stmt: " + super.toString();
   }
}
