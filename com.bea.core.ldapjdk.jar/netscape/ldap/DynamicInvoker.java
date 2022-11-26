package netscape.ldap;

import java.lang.reflect.Method;
import java.util.Hashtable;

class DynamicInvoker {
   private static Hashtable m_methodLookup = new Hashtable();

   static Object invokeMethod(Object var0, String var1, String var2, Object[] var3, String[] var4) throws LDAPException {
      try {
         Method var5 = getMethod(var1, var2, var4);
         return var5 != null ? var5.invoke(var0, var3) : null;
      } catch (Exception var6) {
         throw new LDAPException("Invoking " + var2 + ": " + var6.toString(), 89);
      }
   }

   static Method getMethod(String var0, String var1, String[] var2) throws LDAPException {
      try {
         Method var3 = null;
         String var4 = "";
         if (var2 != null) {
            for(int var5 = 0; var5 < var2.length; ++var5) {
               var4 = var4 + var2[var5].getClass().getName();
            }
         }

         String var11 = var0 + "." + var1 + "." + var4;
         if ((var3 = (Method)((Method)m_methodLookup.get(var11))) != null) {
            return var3;
         } else {
            Class var6 = Class.forName(var0);
            Method[] var7 = var6.getMethods();

            for(int var8 = 0; var8 < var7.length; ++var8) {
               Class[] var9 = var7[var8].getParameterTypes();
               if (var7[var8].getName().equals(var1) && signatureCorrect(var9, var2)) {
                  m_methodLookup.put(var11, var7[var8]);
                  return var7[var8];
               }
            }

            throw new LDAPException("Method " + var1 + " not found in " + var0);
         }
      } catch (ClassNotFoundException var10) {
         throw new LDAPException("Class " + var0 + " not found");
      }
   }

   private static boolean signatureCorrect(Class[] var0, String[] var1) {
      if (var1 == null) {
         return true;
      } else if (var0.length != var1.length) {
         return false;
      } else {
         for(int var2 = 0; var2 < var0.length; ++var2) {
            if (!var0[var2].getName().equals(var1[var2])) {
               return false;
            }
         }

         return true;
      }
   }
}
