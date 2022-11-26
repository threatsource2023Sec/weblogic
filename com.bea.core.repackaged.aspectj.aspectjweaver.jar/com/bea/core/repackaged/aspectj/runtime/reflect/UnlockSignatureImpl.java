package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.UnlockSignature;

class UnlockSignatureImpl extends SignatureImpl implements UnlockSignature {
   private Class parameterType;

   UnlockSignatureImpl(Class c) {
      super(8, "unlock", c);
      this.parameterType = c;
   }

   UnlockSignatureImpl(String stringRep) {
      super(stringRep);
   }

   protected String createToString(StringMaker sm) {
      if (this.parameterType == null) {
         this.parameterType = this.extractType(3);
      }

      return "unlock(" + sm.makeTypeName(this.parameterType) + ")";
   }

   public Class getParameterType() {
      if (this.parameterType == null) {
         this.parameterType = this.extractType(3);
      }

      return this.parameterType;
   }
}
