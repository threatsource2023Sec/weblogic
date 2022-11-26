package org.jboss.weld.util;

import java.security.AccessControlException;
import java.security.Permission;

public class Permissions {
   public static final Permission MODIFY_THREAD_GROUP = new RuntimePermission("modifyThreadGroup");

   private Permissions() {
   }

   public static boolean hasPermission(Permission permission) {
      SecurityManager security = System.getSecurityManager();
      if (security != null) {
         try {
            security.checkPermission(permission);
         } catch (AccessControlException var3) {
            return false;
         }
      }

      return true;
   }
}
