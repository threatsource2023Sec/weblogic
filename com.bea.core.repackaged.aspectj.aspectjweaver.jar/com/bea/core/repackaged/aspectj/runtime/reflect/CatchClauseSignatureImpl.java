package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.CatchClauseSignature;

class CatchClauseSignatureImpl extends SignatureImpl implements CatchClauseSignature {
   Class parameterType;
   String parameterName;

   CatchClauseSignatureImpl(Class declaringType, Class parameterType, String parameterName) {
      super(0, "catch", declaringType);
      this.parameterType = parameterType;
      this.parameterName = parameterName;
   }

   CatchClauseSignatureImpl(String stringRep) {
      super(stringRep);
   }

   public Class getParameterType() {
      if (this.parameterType == null) {
         this.parameterType = this.extractType(3);
      }

      return this.parameterType;
   }

   public String getParameterName() {
      if (this.parameterName == null) {
         this.parameterName = this.extractString(4);
      }

      return this.parameterName;
   }

   protected String createToString(StringMaker sm) {
      return "catch(" + sm.makeTypeName(this.getParameterType()) + ")";
   }
}
