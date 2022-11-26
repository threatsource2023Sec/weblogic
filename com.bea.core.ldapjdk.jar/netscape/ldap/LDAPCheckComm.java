package netscape.ldap;

import java.lang.reflect.Method;

class LDAPCheckComm {
   static Method getMethod(String var0, String var1) throws LDAPException {
      SecurityManager var2 = System.getSecurityManager();
      if (var2 == null) {
         return null;
      } else if (var2.toString().startsWith("java.lang.NullSecurityManager")) {
         return null;
      } else if (var2.toString().startsWith("netscape.security.AppletSecurity")) {
         try {
            Class var3 = Class.forName(var0);
            Method[] var4 = var3.getMethods();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               if (var4[var5].getName().equals(var1)) {
                  return var4[var5];
               }
            }

            throw new LDAPException("no enable privilege in " + var0);
         } catch (ClassNotFoundException var6) {
            throw new LDAPException("Class not found");
         }
      } else {
         return null;
      }
   }
}
