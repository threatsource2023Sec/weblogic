package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ImportReference;

public class ImportBinding extends Binding {
   public char[][] compoundName;
   public boolean onDemand;
   public ImportReference reference;
   public Binding resolvedImport;

   public ImportBinding(char[][] compoundName, boolean isOnDemand, Binding binding, ImportReference reference) {
      this.compoundName = compoundName;
      this.onDemand = isOnDemand;
      this.resolvedImport = binding;
      this.reference = reference;
   }

   public final int kind() {
      return 32;
   }

   public boolean isStatic() {
      return this.reference != null && this.reference.isStatic();
   }

   public char[] readableName() {
      return this.onDemand ? CharOperation.concat(CharOperation.concatWith(this.compoundName, '.'), ".*".toCharArray()) : CharOperation.concatWith(this.compoundName, '.');
   }

   public String toString() {
      return "import : " + new String(this.readableName());
   }
}
