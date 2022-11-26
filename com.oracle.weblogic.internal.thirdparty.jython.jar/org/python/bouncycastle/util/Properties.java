package org.python.bouncycastle.util;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Properties {
   public static boolean isOverrideSet(final String var0) {
      try {
         return "true".equals(AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               String var1 = System.getProperty(var0);
               return var1 == null ? null : Strings.toLowerCase(var1);
            }
         }));
      } catch (AccessControlException var2) {
         return false;
      }
   }

   public static Set asKeySet(String var0) {
      HashSet var1 = new HashSet();
      String var2 = System.getProperty(var0);
      if (var2 != null) {
         StringTokenizer var3 = new StringTokenizer(var2, ",");

         while(var3.hasMoreElements()) {
            var1.add(Strings.toLowerCase(var3.nextToken()).trim());
         }
      }

      return Collections.unmodifiableSet(var1);
   }
}
