package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleStatement;

public abstract class RecoveredModuleStatement extends RecoveredElement {
   public ModuleStatement moduleStatement;

   public RecoveredModuleStatement(ModuleStatement moduleStmt, RecoveredElement parent, int bracketBalance) {
      super(parent, bracketBalance);
      this.moduleStatement = moduleStmt;
   }

   public ASTNode parseTree() {
      return this.moduleStatement;
   }

   public int sourceEnd() {
      return this.moduleStatement.declarationSourceEnd;
   }

   public String toString(int tab) {
      return this.moduleStatement.toString();
   }

   protected ModuleStatement updatedModuleStatement() {
      return this.moduleStatement;
   }

   public void updateParseTree() {
      this.updatedModuleStatement();
   }

   public void updateSourceEndIfNecessary(int bodyStart, int bodyEnd) {
      if (this.moduleStatement.declarationSourceEnd == 0) {
         this.moduleStatement.declarationSourceEnd = bodyEnd;
         this.moduleStatement.declarationEnd = bodyEnd;
      }

   }
}
