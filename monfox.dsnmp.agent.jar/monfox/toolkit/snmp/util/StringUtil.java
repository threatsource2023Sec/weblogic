package monfox.toolkit.snmp.util;

import java.util.StringTokenizer;

public final class StringUtil {
   public static String[] split(String var0, String var1) {
      int var5 = WorkItem.d;
      StringTokenizer var2 = new StringTokenizer(var0, var1, false);
      String[] var3 = new String[var2.countTokens()];
      int var4 = 0;

      String[] var10000;
      while(true) {
         if (var2.hasMoreTokens()) {
            var10000 = var3;
            if (var5 != 0) {
               break;
            }

            var3[var4++] = var2.nextToken();
            if (var5 == 0) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public static String StripQuotes(String var0) {
      if (var0 == null) {
         return null;
      } else {
         var0 = var0.trim();
         return var0.length() < 2 || (!var0.startsWith("\"") || !var0.endsWith("\"")) && (!var0.startsWith("'") || !var0.endsWith("'")) ? var0 : var0.substring(1, var0.length() - 1);
      }
   }

   public static byte[] ASCIIStringToByteArray(String var0) {
      return ASCIICharArrayToByteArray(var0.toCharArray());
   }

   public static byte[] ASCIICharArrayToByteArray(char[] var0) {
      int var3 = WorkItem.d;
      byte[] var1 = new byte[var0.length];
      int var2 = 0;

      byte[] var10000;
      while(true) {
         if (var2 < var0.length) {
            var10000 = var1;
            if (var3 != 0) {
               break;
            }

            var1[var2] = (byte)var0[var2];
            ++var2;
            if (var3 == 0) {
               continue;
            }
         }

         var10000 = var1;
         break;
      }

      return var10000;
   }
}
