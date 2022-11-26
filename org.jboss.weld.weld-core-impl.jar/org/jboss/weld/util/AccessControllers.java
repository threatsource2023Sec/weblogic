package org.jboss.weld.util;

import java.security.PrivilegedAction;

public class AccessControllers {
   private AccessControllers() {
   }

   public static PrivilegedAction action(PrivilegedAction action) {
      return action;
   }
}
