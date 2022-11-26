package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.ConstructorSignature;
import java.lang.reflect.Constructor;

class ConstructorSignatureImpl extends CodeSignatureImpl implements ConstructorSignature {
   private Constructor constructor;

   ConstructorSignatureImpl(int modifiers, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes) {
      super(modifiers, "<init>", declaringType, parameterTypes, parameterNames, exceptionTypes);
   }

   ConstructorSignatureImpl(String stringRep) {
      super(stringRep);
   }

   public String getName() {
      return "<init>";
   }

   protected String createToString(StringMaker sm) {
      StringBuffer buf = new StringBuffer();
      buf.append(sm.makeModifiersString(this.getModifiers()));
      buf.append(sm.makePrimaryTypeName(this.getDeclaringType(), this.getDeclaringTypeName()));
      sm.addSignature(buf, this.getParameterTypes());
      sm.addThrows(buf, this.getExceptionTypes());
      return buf.toString();
   }

   public Constructor getConstructor() {
      if (this.constructor == null) {
         try {
            this.constructor = this.getDeclaringType().getDeclaredConstructor(this.getParameterTypes());
         } catch (Exception var2) {
         }
      }

      return this.constructor;
   }
}
