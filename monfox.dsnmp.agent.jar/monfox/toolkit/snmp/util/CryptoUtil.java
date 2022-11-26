package monfox.toolkit.snmp.util;

import java.math.BigInteger;

public class CryptoUtil {
   public static byte[] encodeUnsigned(BigInteger var0) {
      int var4 = WorkItem.d;
      byte[] var1 = var0.toByteArray();
      int var2 = 0;

      int var10000;
      while(true) {
         if (var2 < var1.length) {
            var10000 = var1[var2];
            if (var4 != 0) {
               break;
            }

            if (var10000 == 0) {
               ++var2;
               if (var4 == 0) {
                  continue;
               }
            }
         }

         var10000 = var2;
         break;
      }

      if (var10000 == 0) {
         return var1;
      } else {
         byte[] var3 = new byte[var1.length - var2];
         System.arraycopy(var1, var2, var3, 0, var3.length);
         return var3;
      }
   }
}
