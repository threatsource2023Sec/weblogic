package weblogic.net.http;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class SecurityHelper {
   public static String getSystemProperty(final String key) {
      return System.getSecurityManager() != null ? (String)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return System.getProperty(key);
         }
      }) : System.getProperty(key);
   }

   public static String getSystemProperty(final String key, final String def) {
      return System.getSecurityManager() != null ? (String)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return System.getProperty(key, def);
         }
      }) : System.getProperty(key, def);
   }

   public static Integer getInteger(final String nm, final int val) {
      return System.getSecurityManager() != null ? (Integer)AccessController.doPrivileged(new PrivilegedAction() {
         public Integer run() {
            return Integer.getInteger(nm, val);
         }
      }) : Integer.getInteger(nm, val);
   }

   public static Boolean getBoolean(final String name) {
      return System.getSecurityManager() != null ? (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
         public Boolean run() {
            return Boolean.getBoolean(name);
         }
      }) : Boolean.getBoolean(name);
   }
}
