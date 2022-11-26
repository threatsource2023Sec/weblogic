package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class PolyParameterizedGenericMethodBinding extends ParameterizedGenericMethodBinding {
   private ParameterizedGenericMethodBinding wrappedBinding;

   public PolyParameterizedGenericMethodBinding(ParameterizedGenericMethodBinding applicableMethod) {
      super(applicableMethod.originalMethod, applicableMethod.typeArguments, applicableMethod.environment, applicableMethod.inferredWithUncheckedConversion, false, applicableMethod.targetType);
      this.wrappedBinding = applicableMethod;
   }

   public boolean equals(Object other) {
      if (other instanceof PolyParameterizedGenericMethodBinding) {
         PolyParameterizedGenericMethodBinding ppgmb = (PolyParameterizedGenericMethodBinding)other;
         return this.wrappedBinding.equals(ppgmb.wrappedBinding);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.wrappedBinding.hashCode();
   }
}
