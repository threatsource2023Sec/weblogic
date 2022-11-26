package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class PolymorphicMethodBinding extends MethodBinding {
   protected MethodBinding polymorphicMethod;

   public PolymorphicMethodBinding(MethodBinding polymorphicMethod, TypeBinding[] parameterTypes) {
      super(polymorphicMethod.modifiers, polymorphicMethod.selector, polymorphicMethod.returnType, parameterTypes, polymorphicMethod.thrownExceptions, polymorphicMethod.declaringClass);
      this.polymorphicMethod = polymorphicMethod;
      this.tagBits = polymorphicMethod.tagBits;
   }

   public PolymorphicMethodBinding(MethodBinding polymorphicMethod, TypeBinding returnType, TypeBinding[] parameterTypes) {
      super(polymorphicMethod.modifiers, polymorphicMethod.selector, returnType, parameterTypes, polymorphicMethod.thrownExceptions, polymorphicMethod.declaringClass);
      this.polymorphicMethod = polymorphicMethod;
      this.tagBits = polymorphicMethod.tagBits;
   }

   public MethodBinding original() {
      return this.polymorphicMethod;
   }

   public boolean isPolymorphic() {
      return true;
   }

   public boolean matches(TypeBinding[] matchingParameters, TypeBinding matchingReturnType) {
      int cachedParametersLength = this.parameters == null ? 0 : this.parameters.length;
      int matchingParametersLength = matchingParameters == null ? 0 : matchingParameters.length;
      if (matchingParametersLength != cachedParametersLength) {
         return false;
      } else {
         for(int j = 0; j < cachedParametersLength; ++j) {
            if (TypeBinding.notEquals(this.parameters[j], matchingParameters[j])) {
               return false;
            }
         }

         TypeBinding cachedReturnType = this.returnType;
         if (matchingReturnType == null) {
            if (cachedReturnType != null) {
               return false;
            }
         } else {
            if (cachedReturnType == null) {
               return false;
            }

            if (TypeBinding.notEquals(matchingReturnType, cachedReturnType)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isVarargs() {
      return false;
   }
}
