package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;

public class RecoveredTypeReference extends RecoveredElement {
   public TypeReference typeReference;

   public RecoveredTypeReference(TypeReference typeReference, RecoveredElement parent, int bracketBalance) {
      super(parent, bracketBalance);
      this.typeReference = typeReference;
   }

   public ASTNode parseTree() {
      return this.typeReference;
   }

   public TypeReference updateTypeReference() {
      return this.typeReference;
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered typereference: " + this.typeReference.toString();
   }

   public TypeReference updatedImportReference() {
      return this.typeReference;
   }

   public void updateParseTree() {
      this.updatedImportReference();
   }
}
