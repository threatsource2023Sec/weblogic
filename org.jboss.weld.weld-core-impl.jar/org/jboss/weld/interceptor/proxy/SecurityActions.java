package org.jboss.weld.interceptor.proxy;

import java.lang.reflect.AccessibleObject;
import java.security.AccessController;
import org.jboss.weld.security.SetAccessibleAction;

final class SecurityActions {
   private SecurityActions() {
   }

   static void ensureAccessible(AccessibleObject accessibleObject) {
      if (accessibleObject != null && !accessibleObject.isAccessible()) {
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(SetAccessibleAction.of(accessibleObject));
         } else {
            accessibleObject.setAccessible(true);
         }
      }

   }
}
