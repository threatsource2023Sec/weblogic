package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ImportReference;

public class RecoveredImport extends RecoveredElement {
   public ImportReference importReference;

   public RecoveredImport(ImportReference importReference, RecoveredElement parent, int bracketBalance) {
      super(parent, bracketBalance);
      this.importReference = importReference;
   }

   public ASTNode parseTree() {
      return this.importReference;
   }

   public int sourceEnd() {
      return this.importReference.declarationSourceEnd;
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered import: " + this.importReference.toString();
   }

   public ImportReference updatedImportReference() {
      return this.importReference;
   }

   public void updateParseTree() {
      this.updatedImportReference();
   }

   public void updateSourceEndIfNecessary(int bodyStart, int bodyEnd) {
      if (this.importReference.declarationSourceEnd == 0) {
         this.importReference.declarationSourceEnd = bodyEnd;
         this.importReference.declarationEnd = bodyEnd;
      }

   }
}
