package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.CodeSignature;

abstract class CodeSignatureImpl extends MemberSignatureImpl implements CodeSignature {
   Class[] parameterTypes;
   String[] parameterNames;
   Class[] exceptionTypes;

   CodeSignatureImpl(int modifiers, String name, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes) {
      super(modifiers, name, declaringType);
      this.parameterTypes = parameterTypes;
      this.parameterNames = parameterNames;
      this.exceptionTypes = exceptionTypes;
   }

   CodeSignatureImpl(String stringRep) {
      super(stringRep);
   }

   public Class[] getParameterTypes() {
      if (this.parameterTypes == null) {
         this.parameterTypes = this.extractTypes(3);
      }

      return this.parameterTypes;
   }

   public String[] getParameterNames() {
      if (this.parameterNames == null) {
         this.parameterNames = this.extractStrings(4);
      }

      return this.parameterNames;
   }

   public Class[] getExceptionTypes() {
      if (this.exceptionTypes == null) {
         this.exceptionTypes = this.extractTypes(5);
      }

      return this.exceptionTypes;
   }
}
