package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;

public final class GetDeclaredMethods implements PrivilegedAction {
   private final Class clazz;

   public static GetDeclaredMethods action(Class clazz) {
      return new GetDeclaredMethods(clazz);
   }

   private GetDeclaredMethods(Class clazz) {
      this.clazz = clazz;
   }

   public Method[] run() {
      return this.clazz.getDeclaredMethods();
   }
}
