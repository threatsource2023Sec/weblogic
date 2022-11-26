package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.ast.LocalDeclaration;

public class CatchParameterBinding extends LocalVariableBinding {
   TypeBinding[] preciseTypes;

   public CatchParameterBinding(LocalDeclaration declaration, TypeBinding type, int modifiers, boolean isArgument) {
      super(declaration, type, modifiers, isArgument);
      this.preciseTypes = Binding.NO_EXCEPTIONS;
   }

   public TypeBinding[] getPreciseTypes() {
      return this.preciseTypes;
   }

   public void setPreciseType(TypeBinding raisedException) {
      int length = this.preciseTypes.length;

      for(int i = 0; i < length; ++i) {
         if (TypeBinding.equalsEquals(this.preciseTypes[i], raisedException)) {
            return;
         }
      }

      System.arraycopy(this.preciseTypes, 0, this.preciseTypes = new TypeBinding[length + 1], 0, length);
      this.preciseTypes[length] = raisedException;
   }

   public boolean isCatchParameter() {
      return true;
   }
}
