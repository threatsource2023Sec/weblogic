package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ImportReference;

public class ImportConflictBinding extends ImportBinding {
   public ReferenceBinding conflictingTypeBinding;

   public ImportConflictBinding(char[][] compoundName, Binding methodBinding, ReferenceBinding conflictingTypeBinding, ImportReference reference) {
      super(compoundName, false, methodBinding, reference);
      this.conflictingTypeBinding = conflictingTypeBinding;
   }

   public char[] readableName() {
      return CharOperation.concatWith(this.compoundName, '.');
   }

   public String toString() {
      return "method import : " + new String(this.readableName());
   }
}
