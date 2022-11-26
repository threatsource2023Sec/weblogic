package javax.enterprise.util;

import java.lang.reflect.Method;
import java.security.AccessController;

final class SecurityActions {
   private SecurityActions() {
   }

   static void setAccessible(Method method) {
      if (System.getSecurityManager() != null) {
         AccessController.doPrivileged(() -> {
            method.setAccessible(true);
            return null;
         });
      } else {
         method.setAccessible(true);
      }

   }

   static Method[] getDeclaredMethods(Class clazz) {
      return System.getSecurityManager() != null ? (Method[])AccessController.doPrivileged(() -> {
         return clazz.getDeclaredMethods();
      }) : clazz.getDeclaredMethods();
   }
}
