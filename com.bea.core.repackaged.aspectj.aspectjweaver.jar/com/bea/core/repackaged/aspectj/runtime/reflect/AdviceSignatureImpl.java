package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AdviceSignature;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

class AdviceSignatureImpl extends CodeSignatureImpl implements AdviceSignature {
   Class returnType;
   private Method adviceMethod = null;

   AdviceSignatureImpl(int modifiers, String name, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes, Class returnType) {
      super(modifiers, name, declaringType, parameterTypes, parameterNames, exceptionTypes);
      this.returnType = returnType;
   }

   AdviceSignatureImpl(String stringRep) {
      super(stringRep);
   }

   public Class getReturnType() {
      if (this.returnType == null) {
         this.returnType = this.extractType(6);
      }

      return this.returnType;
   }

   protected String createToString(StringMaker sm) {
      StringBuffer buf = new StringBuffer();
      if (sm.includeArgs) {
         buf.append(sm.makeTypeName(this.getReturnType()));
      }

      if (sm.includeArgs) {
         buf.append(" ");
      }

      buf.append(sm.makePrimaryTypeName(this.getDeclaringType(), this.getDeclaringTypeName()));
      buf.append(".");
      buf.append(this.toAdviceName(this.getName()));
      sm.addSignature(buf, this.getParameterTypes());
      sm.addThrows(buf, this.getExceptionTypes());
      return buf.toString();
   }

   private String toAdviceName(String methodName) {
      if (methodName.indexOf(36) == -1) {
         return methodName;
      } else {
         StringTokenizer strTok = new StringTokenizer(methodName, "$");

         String token;
         do {
            if (!strTok.hasMoreTokens()) {
               return methodName;
            }

            token = strTok.nextToken();
         } while(!token.startsWith("before") && !token.startsWith("after") && !token.startsWith("around"));

         return token;
      }
   }

   public Method getAdvice() {
      if (this.adviceMethod == null) {
         try {
            this.adviceMethod = this.getDeclaringType().getDeclaredMethod(this.getName(), this.getParameterTypes());
         } catch (Exception var2) {
         }
      }

      return this.adviceMethod;
   }
}
