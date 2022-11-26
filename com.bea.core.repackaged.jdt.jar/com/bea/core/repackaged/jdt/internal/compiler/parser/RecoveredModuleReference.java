package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleReference;

public class RecoveredModuleReference extends RecoveredElement {
   public ModuleReference moduleReference;

   public RecoveredModuleReference(ModuleReference moduleReference, RecoveredElement parent, int bracketBalance) {
      super(parent, bracketBalance);
      this.moduleReference = moduleReference;
   }

   public ASTNode parseTree() {
      return this.moduleReference;
   }

   public int sourceEnd() {
      return this.moduleReference.sourceEnd;
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered ModuleReference: " + this.moduleReference.toString();
   }

   public ModuleReference updatedModuleReference() {
      return this.moduleReference;
   }

   public void updateParseTree() {
      this.updatedModuleReference();
   }
}
