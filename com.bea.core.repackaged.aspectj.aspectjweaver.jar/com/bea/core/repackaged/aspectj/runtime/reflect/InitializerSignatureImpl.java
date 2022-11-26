package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.InitializerSignature;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class InitializerSignatureImpl extends CodeSignatureImpl implements InitializerSignature {
   private Constructor constructor;

   InitializerSignatureImpl(int modifiers, Class declaringType) {
      super(modifiers, Modifier.isStatic(modifiers) ? "<clinit>" : "<init>", declaringType, SignatureImpl.EMPTY_CLASS_ARRAY, SignatureImpl.EMPTY_STRING_ARRAY, SignatureImpl.EMPTY_CLASS_ARRAY);
   }

   InitializerSignatureImpl(String stringRep) {
      super(stringRep);
   }

   public String getName() {
      return Modifier.isStatic(this.getModifiers()) ? "<clinit>" : "<init>";
   }

   protected String createToString(StringMaker sm) {
      StringBuffer buf = new StringBuffer();
      buf.append(sm.makeModifiersString(this.getModifiers()));
      buf.append(sm.makePrimaryTypeName(this.getDeclaringType(), this.getDeclaringTypeName()));
      buf.append(".");
      buf.append(this.getName());
      return buf.toString();
   }

   public Constructor getInitializer() {
      if (this.constructor == null) {
         try {
            this.constructor = this.getDeclaringType().getDeclaredConstructor(this.getParameterTypes());
         } catch (Exception var2) {
         }
      }

      return this.constructor;
   }
}
