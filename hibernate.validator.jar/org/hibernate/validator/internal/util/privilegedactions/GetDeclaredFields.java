package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.reflect.Field;
import java.security.PrivilegedAction;

public final class GetDeclaredFields implements PrivilegedAction {
   private final Class clazz;

   public static GetDeclaredFields action(Class clazz) {
      return new GetDeclaredFields(clazz);
   }

   private GetDeclaredFields(Class clazz) {
      this.clazz = clazz;
   }

   public Field[] run() {
      return this.clazz.getDeclaredFields();
   }
}
