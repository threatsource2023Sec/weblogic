package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ProvidesStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;

public class RecoveredProvidesStatement extends RecoveredModuleStatement {
   SingleTypeReference impl;

   public RecoveredProvidesStatement(ProvidesStatement providesStatement, RecoveredElement parent, int bracketBalance) {
      super(providesStatement, parent, bracketBalance);
   }

   public RecoveredElement add(SingleTypeReference impl1, int bracketBalance1) {
      this.impl = impl1;
      return this;
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered Provides: " + super.toString();
   }

   public ProvidesStatement updatedProvidesStatement() {
      ProvidesStatement providesStatement = (ProvidesStatement)this.moduleStatement;
      if (providesStatement.implementations == null) {
         providesStatement.implementations = this.impl != null ? new TypeReference[]{this.impl} : new TypeReference[0];
      }

      return providesStatement;
   }

   public void updateParseTree() {
      this.updatedProvidesStatement();
   }
}
