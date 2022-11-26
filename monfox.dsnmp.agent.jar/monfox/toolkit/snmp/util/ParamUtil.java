package monfox.toolkit.snmp.util;

import java.lang.reflect.Method;
import monfox.log.Logger;

public final class ParamUtil {
   public static boolean setParameter(Logger var0, Object var1, String var2, Object var3) {
      int var11 = WorkItem.d;
      var0.debug(a("i!d$*h%}\u0011?\u007f68T") + var2 + a("6d") + var3 + a(":m"));
      if (var2 == null) {
         return false;
      } else {
         String var4 = null;
         String var5 = var2;
         int var6 = var2.indexOf(46);
         if (var6 > 0) {
            var4 = var2.substring(0, var6);
            var5 = var2.substring(var6 + 1);
         }

         boolean var10000;
         if (var4 == null || var1.getClass().getName().endsWith(var4)) {
            String var7 = a("i!d") + var5;
            if (var5.startsWith(a("i!d"))) {
               var7 = var5;
            }

            Method[] var8 = var1.getClass().getMethods();
            int var9 = 0;

            while(var9 < var8.length) {
               var10000 = var8[var9].getName().equalsIgnoreCase(var7);
               if (var11 != 0) {
                  return var10000;
               }

               if (var10000) {
                  if (var0.isDebugEnabled()) {
                     var0.debug(a("|+e\u001a/:)u\u0000#u 0\u0012$hd7") + var2 + a("=dc\u0011?n-~\u0013k'z0") + var3);
                  }

                  try {
                     var8[var9].invoke(var1, var3);
                     var0.debug(a("i!dTl") + var2 + a("=dc\u0001(y!u\u0010.~"));
                     return true;
                  } catch (Exception var12) {
                     var0.error(a("\u007f6b\u001b9:-~T\"t2\u007f\u001f\"t#0\u0007.nd}\u0011?r+tT-u60S") + var2 + "'", var12);
                  }
               }

               ++var9;
               if (var11 != 0) {
                  break;
               }
            }
         }

         var0.debug(a("t+0\u0019*n'x\u001d%}d}\u0011?r+tT%{)u\u0007k|+e\u001a/:\"\u007f\u0006k=") + var2 + "'");
         var10000 = false;
         return var10000;
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 26;
               break;
            case 1:
               var10003 = 68;
               break;
            case 2:
               var10003 = 16;
               break;
            case 3:
               var10003 = 116;
               break;
            default:
               var10003 = 75;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
