package netscape.ldap;

import netscape.ldap.util.DN;
import netscape.ldap.util.RDN;

public class LDAPDN {
   public static String[] explodeDN(String var0, boolean var1) {
      DN var2 = new DN(var0);
      return var2.explodeDN(var1);
   }

   public static String[] explodeRDN(String var0, boolean var1) {
      RDN var2 = new RDN(var0);
      if (var1) {
         return var2.getValues();
      } else {
         String[] var3 = new String[]{var2.toString()};
         return var3;
      }
   }

   public static String escapeRDN(String var0) {
      RDN var1 = new RDN(var0);
      String[] var2 = var1.getValues();
      if (var2 == null) {
         return var0;
      } else {
         StringBuffer[] var3 = new StringBuffer[var2.length];
         StringBuffer var4 = new StringBuffer();
         String[] var5 = var1.getTypes();

         for(int var6 = 0; var6 < var2.length; ++var6) {
            var3[var6] = new StringBuffer(var2[var6]);

            for(int var7 = 0; var7 < var3[var6].length(); ++var7) {
               if (isEscape(var3[var6].charAt(var7))) {
                  var3[var6].insert(var7, '\\');
                  ++var7;
               }
            }

            var4.append((var4.length() > 0 ? " + " : "") + var5[var6] + "=" + new String(var3[var6]));
         }

         return new String(var4);
      }
   }

   public static String unEscapeRDN(String var0) {
      RDN var1 = new RDN(var0);
      String[] var2 = var1.getValues();
      if (var2 != null && var2.length >= 1) {
         StringBuffer var3 = new StringBuffer(var2[0]);
         StringBuffer var4 = new StringBuffer();
         int var5 = 0;

         while(var5 < var3.length()) {
            char var6 = var3.charAt(var5++);
            if (var6 != '\\') {
               var4.append(var6);
            } else if (var5 < var3.length()) {
               var4.append(var3.charAt(var5++));
            }
         }

         return var1.getTypes()[0] + "=" + new String(var4);
      } else {
         return var0;
      }
   }

   public static String normalize(String var0) {
      return (new DN(var0)).toString();
   }

   public static boolean equals(String var0, String var1) {
      return normalize(var0).equals(normalize(var1));
   }

   private static boolean isEscape(char var0) {
      for(int var1 = 0; var1 < DN.ESCAPED_CHAR.length; ++var1) {
         if (var0 == DN.ESCAPED_CHAR[var1]) {
            return true;
         }
      }

      return false;
   }
}
