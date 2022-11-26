package org.python.bouncycastle.math.raw;

import java.math.BigInteger;
import org.python.bouncycastle.util.Pack;

public abstract class Nat320 {
   public static void copy64(long[] var0, long[] var1) {
      var1[0] = var0[0];
      var1[1] = var0[1];
      var1[2] = var0[2];
      var1[3] = var0[3];
      var1[4] = var0[4];
   }

   public static long[] create64() {
      return new long[5];
   }

   public static long[] createExt64() {
      return new long[10];
   }

   public static boolean eq64(long[] var0, long[] var1) {
      for(int var2 = 4; var2 >= 0; --var2) {
         if (var0[var2] != var1[var2]) {
            return false;
         }
      }

      return true;
   }

   public static long[] fromBigInteger64(BigInteger var0) {
      if (var0.signum() >= 0 && var0.bitLength() <= 320) {
         long[] var1 = create64();

         for(int var2 = 0; var0.signum() != 0; var0 = var0.shiftRight(64)) {
            var1[var2++] = var0.longValue();
         }

         return var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static boolean isOne64(long[] var0) {
      if (var0[0] != 1L) {
         return false;
      } else {
         for(int var1 = 1; var1 < 5; ++var1) {
            if (var0[var1] != 0L) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean isZero64(long[] var0) {
      for(int var1 = 0; var1 < 5; ++var1) {
         if (var0[var1] != 0L) {
            return false;
         }
      }

      return true;
   }

   public static BigInteger toBigInteger64(long[] var0) {
      byte[] var1 = new byte[40];

      for(int var2 = 0; var2 < 5; ++var2) {
         long var3 = var0[var2];
         if (var3 != 0L) {
            Pack.longToBigEndian(var3, var1, 4 - var2 << 3);
         }
      }

      return new BigInteger(1, var1);
   }
}
