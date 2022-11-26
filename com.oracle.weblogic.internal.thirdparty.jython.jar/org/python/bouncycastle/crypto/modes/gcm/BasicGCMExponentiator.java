package org.python.bouncycastle.crypto.modes.gcm;

import org.python.bouncycastle.util.Arrays;

public class BasicGCMExponentiator implements GCMExponentiator {
   private int[] x;

   public void init(byte[] var1) {
      this.x = GCMUtil.asInts(var1);
   }

   public void exponentiateX(long var1, byte[] var3) {
      int[] var4 = GCMUtil.oneAsInts();
      if (var1 > 0L) {
         int[] var5 = Arrays.clone(this.x);

         do {
            if ((var1 & 1L) != 0L) {
               GCMUtil.multiply(var4, var5);
            }

            GCMUtil.multiply(var5, var5);
            var1 >>>= 1;
         } while(var1 > 0L);
      }

      GCMUtil.asBytes(var4, var3);
   }
}
