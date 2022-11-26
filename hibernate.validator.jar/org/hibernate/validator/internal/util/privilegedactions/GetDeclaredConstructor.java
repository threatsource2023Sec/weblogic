package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.reflect.Constructor;
import java.security.PrivilegedAction;

public final class GetDeclaredConstructor implements PrivilegedAction {
   private final Class clazz;
   private final Class[] params;

   public static GetDeclaredConstructor action(Class clazz, Class... params) {
      return new GetDeclaredConstructor(clazz, params);
   }

   private GetDeclaredConstructor(Class clazz, Class... params) {
      this.clazz = clazz;
      this.params = params;
   }

   public Constructor run() {
      try {
         return this.clazz.getDeclaredConstructor(this.params);
      } catch (NoSuchMethodException var2) {
         return null;
      }
   }
}
