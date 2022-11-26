package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;

public final class GetMethod implements PrivilegedAction {
   private final Class clazz;
   private final String methodName;

   public static GetMethod action(Class clazz, String methodName) {
      return new GetMethod(clazz, methodName);
   }

   private GetMethod(Class clazz, String methodName) {
      this.clazz = clazz;
      this.methodName = methodName;
   }

   public Method run() {
      try {
         return this.clazz.getMethod(this.methodName);
      } catch (NoSuchMethodException var2) {
         return null;
      }
   }
}
