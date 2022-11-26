package org.python.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class RandUtils {
   static int nextInt(SecureRandom var0, int var1) {
      if ((var1 & -var1) == var1) {
         return (int)((long)var1 * (long)(var0.nextInt() >>> 1) >> 31);
      } else {
         int var2;
         int var3;
         do {
            var2 = var0.nextInt() >>> 1;
            var3 = var2 % var1;
         } while(var2 - var3 + (var1 - 1) < 0);

         return var3;
      }
   }
}
