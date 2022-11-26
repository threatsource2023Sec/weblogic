package org.python.bouncycastle.math.raw;

import java.math.BigInteger;
import org.python.bouncycastle.util.Pack;

public abstract class Nat256 {
   private static final long M = 4294967295L;

   public static int add(int[] var0, int[] var1, int[] var2) {
      long var3 = 0L;
      var3 += ((long)var0[0] & 4294967295L) + ((long)var1[0] & 4294967295L);
      var2[0] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[1] & 4294967295L) + ((long)var1[1] & 4294967295L);
      var2[1] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[2] & 4294967295L) + ((long)var1[2] & 4294967295L);
      var2[2] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[3] & 4294967295L) + ((long)var1[3] & 4294967295L);
      var2[3] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[4] & 4294967295L) + ((long)var1[4] & 4294967295L);
      var2[4] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[5] & 4294967295L) + ((long)var1[5] & 4294967295L);
      var2[5] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[6] & 4294967295L) + ((long)var1[6] & 4294967295L);
      var2[6] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[7] & 4294967295L) + ((long)var1[7] & 4294967295L);
      var2[7] = (int)var3;
      var3 >>>= 32;
      return (int)var3;
   }

   public static int add(int[] var0, int var1, int[] var2, int var3, int[] var4, int var5) {
      long var6 = 0L;
      var6 += ((long)var0[var1 + 0] & 4294967295L) + ((long)var2[var3 + 0] & 4294967295L);
      var4[var5 + 0] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 1] & 4294967295L) + ((long)var2[var3 + 1] & 4294967295L);
      var4[var5 + 1] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 2] & 4294967295L) + ((long)var2[var3 + 2] & 4294967295L);
      var4[var5 + 2] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 3] & 4294967295L) + ((long)var2[var3 + 3] & 4294967295L);
      var4[var5 + 3] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 4] & 4294967295L) + ((long)var2[var3 + 4] & 4294967295L);
      var4[var5 + 4] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 5] & 4294967295L) + ((long)var2[var3 + 5] & 4294967295L);
      var4[var5 + 5] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 6] & 4294967295L) + ((long)var2[var3 + 6] & 4294967295L);
      var4[var5 + 6] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 7] & 4294967295L) + ((long)var2[var3 + 7] & 4294967295L);
      var4[var5 + 7] = (int)var6;
      var6 >>>= 32;
      return (int)var6;
   }

   public static int addBothTo(int[] var0, int[] var1, int[] var2) {
      long var3 = 0L;
      var3 += ((long)var0[0] & 4294967295L) + ((long)var1[0] & 4294967295L) + ((long)var2[0] & 4294967295L);
      var2[0] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[1] & 4294967295L) + ((long)var1[1] & 4294967295L) + ((long)var2[1] & 4294967295L);
      var2[1] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[2] & 4294967295L) + ((long)var1[2] & 4294967295L) + ((long)var2[2] & 4294967295L);
      var2[2] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[3] & 4294967295L) + ((long)var1[3] & 4294967295L) + ((long)var2[3] & 4294967295L);
      var2[3] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[4] & 4294967295L) + ((long)var1[4] & 4294967295L) + ((long)var2[4] & 4294967295L);
      var2[4] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[5] & 4294967295L) + ((long)var1[5] & 4294967295L) + ((long)var2[5] & 4294967295L);
      var2[5] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[6] & 4294967295L) + ((long)var1[6] & 4294967295L) + ((long)var2[6] & 4294967295L);
      var2[6] = (int)var3;
      var3 >>>= 32;
      var3 += ((long)var0[7] & 4294967295L) + ((long)var1[7] & 4294967295L) + ((long)var2[7] & 4294967295L);
      var2[7] = (int)var3;
      var3 >>>= 32;
      return (int)var3;
   }

   public static int addBothTo(int[] var0, int var1, int[] var2, int var3, int[] var4, int var5) {
      long var6 = 0L;
      var6 += ((long)var0[var1 + 0] & 4294967295L) + ((long)var2[var3 + 0] & 4294967295L) + ((long)var4[var5 + 0] & 4294967295L);
      var4[var5 + 0] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 1] & 4294967295L) + ((long)var2[var3 + 1] & 4294967295L) + ((long)var4[var5 + 1] & 4294967295L);
      var4[var5 + 1] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 2] & 4294967295L) + ((long)var2[var3 + 2] & 4294967295L) + ((long)var4[var5 + 2] & 4294967295L);
      var4[var5 + 2] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 3] & 4294967295L) + ((long)var2[var3 + 3] & 4294967295L) + ((long)var4[var5 + 3] & 4294967295L);
      var4[var5 + 3] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 4] & 4294967295L) + ((long)var2[var3 + 4] & 4294967295L) + ((long)var4[var5 + 4] & 4294967295L);
      var4[var5 + 4] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 5] & 4294967295L) + ((long)var2[var3 + 5] & 4294967295L) + ((long)var4[var5 + 5] & 4294967295L);
      var4[var5 + 5] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 6] & 4294967295L) + ((long)var2[var3 + 6] & 4294967295L) + ((long)var4[var5 + 6] & 4294967295L);
      var4[var5 + 6] = (int)var6;
      var6 >>>= 32;
      var6 += ((long)var0[var1 + 7] & 4294967295L) + ((long)var2[var3 + 7] & 4294967295L) + ((long)var4[var5 + 7] & 4294967295L);
      var4[var5 + 7] = (int)var6;
      var6 >>>= 32;
      return (int)var6;
   }

   public static int addTo(int[] var0, int[] var1) {
      long var2 = 0L;
      var2 += ((long)var0[0] & 4294967295L) + ((long)var1[0] & 4294967295L);
      var1[0] = (int)var2;
      var2 >>>= 32;
      var2 += ((long)var0[1] & 4294967295L) + ((long)var1[1] & 4294967295L);
      var1[1] = (int)var2;
      var2 >>>= 32;
      var2 += ((long)var0[2] & 4294967295L) + ((long)var1[2] & 4294967295L);
      var1[2] = (int)var2;
      var2 >>>= 32;
      var2 += ((long)var0[3] & 4294967295L) + ((long)var1[3] & 4294967295L);
      var1[3] = (int)var2;
      var2 >>>= 32;
      var2 += ((long)var0[4] & 4294967295L) + ((long)var1[4] & 4294967295L);
      var1[4] = (int)var2;
      var2 >>>= 32;
      var2 += ((long)var0[5] & 4294967295L) + ((long)var1[5] & 4294967295L);
      var1[5] = (int)var2;
      var2 >>>= 32;
      var2 += ((long)var0[6] & 4294967295L) + ((long)var1[6] & 4294967295L);
      var1[6] = (int)var2;
      var2 >>>= 32;
      var2 += ((long)var0[7] & 4294967295L) + ((long)var1[7] & 4294967295L);
      var1[7] = (int)var2;
      var2 >>>= 32;
      return (int)var2;
   }

   public static int addTo(int[] var0, int var1, int[] var2, int var3, int var4) {
      long var5 = (long)var4 & 4294967295L;
      var5 += ((long)var0[var1 + 0] & 4294967295L) + ((long)var2[var3 + 0] & 4294967295L);
      var2[var3 + 0] = (int)var5;
      var5 >>>= 32;
      var5 += ((long)var0[var1 + 1] & 4294967295L) + ((long)var2[var3 + 1] & 4294967295L);
      var2[var3 + 1] = (int)var5;
      var5 >>>= 32;
      var5 += ((long)var0[var1 + 2] & 4294967295L) + ((long)var2[var3 + 2] & 4294967295L);
      var2[var3 + 2] = (int)var5;
      var5 >>>= 32;
      var5 += ((long)var0[var1 + 3] & 4294967295L) + ((long)var2[var3 + 3] & 4294967295L);
      var2[var3 + 3] = (int)var5;
      var5 >>>= 32;
      var5 += ((long)var0[var1 + 4] & 4294967295L) + ((long)var2[var3 + 4] & 4294967295L);
      var2[var3 + 4] = (int)var5;
      var5 >>>= 32;
      var5 += ((long)var0[var1 + 5] & 4294967295L) + ((long)var2[var3 + 5] & 4294967295L);
      var2[var3 + 5] = (int)var5;
      var5 >>>= 32;
      var5 += ((long)var0[var1 + 6] & 4294967295L) + ((long)var2[var3 + 6] & 4294967295L);
      var2[var3 + 6] = (int)var5;
      var5 >>>= 32;
      var5 += ((long)var0[var1 + 7] & 4294967295L) + ((long)var2[var3 + 7] & 4294967295L);
      var2[var3 + 7] = (int)var5;
      var5 >>>= 32;
      return (int)var5;
   }

   public static int addToEachOther(int[] var0, int var1, int[] var2, int var3) {
      long var4 = 0L;
      var4 += ((long)var0[var1 + 0] & 4294967295L) + ((long)var2[var3 + 0] & 4294967295L);
      var0[var1 + 0] = (int)var4;
      var2[var3 + 0] = (int)var4;
      var4 >>>= 32;
      var4 += ((long)var0[var1 + 1] & 4294967295L) + ((long)var2[var3 + 1] & 4294967295L);
      var0[var1 + 1] = (int)var4;
      var2[var3 + 1] = (int)var4;
      var4 >>>= 32;
      var4 += ((long)var0[var1 + 2] & 4294967295L) + ((long)var2[var3 + 2] & 4294967295L);
      var0[var1 + 2] = (int)var4;
      var2[var3 + 2] = (int)var4;
      var4 >>>= 32;
      var4 += ((long)var0[var1 + 3] & 4294967295L) + ((long)var2[var3 + 3] & 4294967295L);
      var0[var1 + 3] = (int)var4;
      var2[var3 + 3] = (int)var4;
      var4 >>>= 32;
      var4 += ((long)var0[var1 + 4] & 4294967295L) + ((long)var2[var3 + 4] & 4294967295L);
      var0[var1 + 4] = (int)var4;
      var2[var3 + 4] = (int)var4;
      var4 >>>= 32;
      var4 += ((long)var0[var1 + 5] & 4294967295L) + ((long)var2[var3 + 5] & 4294967295L);
      var0[var1 + 5] = (int)var4;
      var2[var3 + 5] = (int)var4;
      var4 >>>= 32;
      var4 += ((long)var0[var1 + 6] & 4294967295L) + ((long)var2[var3 + 6] & 4294967295L);
      var0[var1 + 6] = (int)var4;
      var2[var3 + 6] = (int)var4;
      var4 >>>= 32;
      var4 += ((long)var0[var1 + 7] & 4294967295L) + ((long)var2[var3 + 7] & 4294967295L);
      var0[var1 + 7] = (int)var4;
      var2[var3 + 7] = (int)var4;
      var4 >>>= 32;
      return (int)var4;
   }

   public static void copy(int[] var0, int[] var1) {
      var1[0] = var0[0];
      var1[1] = var0[1];
      var1[2] = var0[2];
      var1[3] = var0[3];
      var1[4] = var0[4];
      var1[5] = var0[5];
      var1[6] = var0[6];
      var1[7] = var0[7];
   }

   public static void copy64(long[] var0, long[] var1) {
      var1[0] = var0[0];
      var1[1] = var0[1];
      var1[2] = var0[2];
      var1[3] = var0[3];
   }

   public static int[] create() {
      return new int[8];
   }

   public static long[] create64() {
      return new long[4];
   }

   public static int[] createExt() {
      return new int[16];
   }

   public static long[] createExt64() {
      return new long[8];
   }

   public static boolean diff(int[] var0, int var1, int[] var2, int var3, int[] var4, int var5) {
      boolean var6 = gte(var0, var1, var2, var3);
      if (var6) {
         sub(var0, var1, var2, var3, var4, var5);
      } else {
         sub(var2, var3, var0, var1, var4, var5);
      }

      return var6;
   }

   public static boolean eq(int[] var0, int[] var1) {
      for(int var2 = 7; var2 >= 0; --var2) {
         if (var0[var2] != var1[var2]) {
            return false;
         }
      }

      return true;
   }

   public static boolean eq64(long[] var0, long[] var1) {
      for(int var2 = 3; var2 >= 0; --var2) {
         if (var0[var2] != var1[var2]) {
            return false;
         }
      }

      return true;
   }

   public static int[] fromBigInteger(BigInteger var0) {
      if (var0.signum() >= 0 && var0.bitLength() <= 256) {
         int[] var1 = create();

         for(int var2 = 0; var0.signum() != 0; var0 = var0.shiftRight(32)) {
            var1[var2++] = var0.intValue();
         }

         return var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static long[] fromBigInteger64(BigInteger var0) {
      if (var0.signum() >= 0 && var0.bitLength() <= 256) {
         long[] var1 = create64();

         for(int var2 = 0; var0.signum() != 0; var0 = var0.shiftRight(64)) {
            var1[var2++] = var0.longValue();
         }

         return var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static int getBit(int[] var0, int var1) {
      if (var1 == 0) {
         return var0[0] & 1;
      } else if ((var1 & 255) != var1) {
         return 0;
      } else {
         int var2 = var1 >>> 5;
         int var3 = var1 & 31;
         return var0[var2] >>> var3 & 1;
      }
   }

   public static boolean gte(int[] var0, int[] var1) {
      for(int var2 = 7; var2 >= 0; --var2) {
         int var3 = var0[var2] ^ Integer.MIN_VALUE;
         int var4 = var1[var2] ^ Integer.MIN_VALUE;
         if (var3 < var4) {
            return false;
         }

         if (var3 > var4) {
            return true;
         }
      }

      return true;
   }

   public static boolean gte(int[] var0, int var1, int[] var2, int var3) {
      for(int var4 = 7; var4 >= 0; --var4) {
         int var5 = var0[var1 + var4] ^ Integer.MIN_VALUE;
         int var6 = var2[var3 + var4] ^ Integer.MIN_VALUE;
         if (var5 < var6) {
            return false;
         }

         if (var5 > var6) {
            return true;
         }
      }

      return true;
   }

   public static boolean isOne(int[] var0) {
      if (var0[0] != 1) {
         return false;
      } else {
         for(int var1 = 1; var1 < 8; ++var1) {
            if (var0[var1] != 0) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean isOne64(long[] var0) {
      if (var0[0] != 1L) {
         return false;
      } else {
         for(int var1 = 1; var1 < 4; ++var1) {
            if (var0[var1] != 0L) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean isZero(int[] var0) {
      for(int var1 = 0; var1 < 8; ++var1) {
         if (var0[var1] != 0) {
            return false;
         }
      }

      return true;
   }

   public static boolean isZero64(long[] var0) {
      for(int var1 = 0; var1 < 4; ++var1) {
         if (var0[var1] != 0L) {
            return false;
         }
      }

      return true;
   }

   public static void mul(int[] var0, int[] var1, int[] var2) {
      long var3 = (long)var1[0] & 4294967295L;
      long var5 = (long)var1[1] & 4294967295L;
      long var7 = (long)var1[2] & 4294967295L;
      long var9 = (long)var1[3] & 4294967295L;
      long var11 = (long)var1[4] & 4294967295L;
      long var13 = (long)var1[5] & 4294967295L;
      long var15 = (long)var1[6] & 4294967295L;
      long var17 = (long)var1[7] & 4294967295L;
      long var19 = 0L;
      long var21 = (long)var0[0] & 4294967295L;
      var19 += var21 * var3;
      var2[0] = (int)var19;
      var19 >>>= 32;
      var19 += var21 * var5;
      var2[1] = (int)var19;
      var19 >>>= 32;
      var19 += var21 * var7;
      var2[2] = (int)var19;
      var19 >>>= 32;
      var19 += var21 * var9;
      var2[3] = (int)var19;
      var19 >>>= 32;
      var19 += var21 * var11;
      var2[4] = (int)var19;
      var19 >>>= 32;
      var19 += var21 * var13;
      var2[5] = (int)var19;
      var19 >>>= 32;
      var19 += var21 * var15;
      var2[6] = (int)var19;
      var19 >>>= 32;
      var19 += var21 * var17;
      var2[7] = (int)var19;
      var19 >>>= 32;
      var2[8] = (int)var19;

      for(int var23 = 1; var23 < 8; ++var23) {
         long var24 = 0L;
         long var26 = (long)var0[var23] & 4294967295L;
         var24 += var26 * var3 + ((long)var2[var23 + 0] & 4294967295L);
         var2[var23 + 0] = (int)var24;
         var24 >>>= 32;
         var24 += var26 * var5 + ((long)var2[var23 + 1] & 4294967295L);
         var2[var23 + 1] = (int)var24;
         var24 >>>= 32;
         var24 += var26 * var7 + ((long)var2[var23 + 2] & 4294967295L);
         var2[var23 + 2] = (int)var24;
         var24 >>>= 32;
         var24 += var26 * var9 + ((long)var2[var23 + 3] & 4294967295L);
         var2[var23 + 3] = (int)var24;
         var24 >>>= 32;
         var24 += var26 * var11 + ((long)var2[var23 + 4] & 4294967295L);
         var2[var23 + 4] = (int)var24;
         var24 >>>= 32;
         var24 += var26 * var13 + ((long)var2[var23 + 5] & 4294967295L);
         var2[var23 + 5] = (int)var24;
         var24 >>>= 32;
         var24 += var26 * var15 + ((long)var2[var23 + 6] & 4294967295L);
         var2[var23 + 6] = (int)var24;
         var24 >>>= 32;
         var24 += var26 * var17 + ((long)var2[var23 + 7] & 4294967295L);
         var2[var23 + 7] = (int)var24;
         var24 >>>= 32;
         var2[var23 + 8] = (int)var24;
      }

   }

   public static void mul(int[] var0, int var1, int[] var2, int var3, int[] var4, int var5) {
      long var6 = (long)var2[var3 + 0] & 4294967295L;
      long var8 = (long)var2[var3 + 1] & 4294967295L;
      long var10 = (long)var2[var3 + 2] & 4294967295L;
      long var12 = (long)var2[var3 + 3] & 4294967295L;
      long var14 = (long)var2[var3 + 4] & 4294967295L;
      long var16 = (long)var2[var3 + 5] & 4294967295L;
      long var18 = (long)var2[var3 + 6] & 4294967295L;
      long var20 = (long)var2[var3 + 7] & 4294967295L;
      long var22 = 0L;
      long var24 = (long)var0[var1 + 0] & 4294967295L;
      var22 += var24 * var6;
      var4[var5 + 0] = (int)var22;
      var22 >>>= 32;
      var22 += var24 * var8;
      var4[var5 + 1] = (int)var22;
      var22 >>>= 32;
      var22 += var24 * var10;
      var4[var5 + 2] = (int)var22;
      var22 >>>= 32;
      var22 += var24 * var12;
      var4[var5 + 3] = (int)var22;
      var22 >>>= 32;
      var22 += var24 * var14;
      var4[var5 + 4] = (int)var22;
      var22 >>>= 32;
      var22 += var24 * var16;
      var4[var5 + 5] = (int)var22;
      var22 >>>= 32;
      var22 += var24 * var18;
      var4[var5 + 6] = (int)var22;
      var22 >>>= 32;
      var22 += var24 * var20;
      var4[var5 + 7] = (int)var22;
      var22 >>>= 32;
      var4[var5 + 8] = (int)var22;

      for(int var26 = 1; var26 < 8; ++var26) {
         ++var5;
         long var27 = 0L;
         long var29 = (long)var0[var1 + var26] & 4294967295L;
         var27 += var29 * var6 + ((long)var4[var5 + 0] & 4294967295L);
         var4[var5 + 0] = (int)var27;
         var27 >>>= 32;
         var27 += var29 * var8 + ((long)var4[var5 + 1] & 4294967295L);
         var4[var5 + 1] = (int)var27;
         var27 >>>= 32;
         var27 += var29 * var10 + ((long)var4[var5 + 2] & 4294967295L);
         var4[var5 + 2] = (int)var27;
         var27 >>>= 32;
         var27 += var29 * var12 + ((long)var4[var5 + 3] & 4294967295L);
         var4[var5 + 3] = (int)var27;
         var27 >>>= 32;
         var27 += var29 * var14 + ((long)var4[var5 + 4] & 4294967295L);
         var4[var5 + 4] = (int)var27;
         var27 >>>= 32;
         var27 += var29 * var16 + ((long)var4[var5 + 5] & 4294967295L);
         var4[var5 + 5] = (int)var27;
         var27 >>>= 32;
         var27 += var29 * var18 + ((long)var4[var5 + 6] & 4294967295L);
         var4[var5 + 6] = (int)var27;
         var27 >>>= 32;
         var27 += var29 * var20 + ((long)var4[var5 + 7] & 4294967295L);
         var4[var5 + 7] = (int)var27;
         var27 >>>= 32;
         var4[var5 + 8] = (int)var27;
      }

   }

   public static int mulAddTo(int[] var0, int[] var1, int[] var2) {
      long var3 = (long)var1[0] & 4294967295L;
      long var5 = (long)var1[1] & 4294967295L;
      long var7 = (long)var1[2] & 4294967295L;
      long var9 = (long)var1[3] & 4294967295L;
      long var11 = (long)var1[4] & 4294967295L;
      long var13 = (long)var1[5] & 4294967295L;
      long var15 = (long)var1[6] & 4294967295L;
      long var17 = (long)var1[7] & 4294967295L;
      long var19 = 0L;

      for(int var21 = 0; var21 < 8; ++var21) {
         long var22 = 0L;
         long var24 = (long)var0[var21] & 4294967295L;
         var22 += var24 * var3 + ((long)var2[var21 + 0] & 4294967295L);
         var2[var21 + 0] = (int)var22;
         var22 >>>= 32;
         var22 += var24 * var5 + ((long)var2[var21 + 1] & 4294967295L);
         var2[var21 + 1] = (int)var22;
         var22 >>>= 32;
         var22 += var24 * var7 + ((long)var2[var21 + 2] & 4294967295L);
         var2[var21 + 2] = (int)var22;
         var22 >>>= 32;
         var22 += var24 * var9 + ((long)var2[var21 + 3] & 4294967295L);
         var2[var21 + 3] = (int)var22;
         var22 >>>= 32;
         var22 += var24 * var11 + ((long)var2[var21 + 4] & 4294967295L);
         var2[var21 + 4] = (int)var22;
         var22 >>>= 32;
         var22 += var24 * var13 + ((long)var2[var21 + 5] & 4294967295L);
         var2[var21 + 5] = (int)var22;
         var22 >>>= 32;
         var22 += var24 * var15 + ((long)var2[var21 + 6] & 4294967295L);
         var2[var21 + 6] = (int)var22;
         var22 >>>= 32;
         var22 += var24 * var17 + ((long)var2[var21 + 7] & 4294967295L);
         var2[var21 + 7] = (int)var22;
         var22 >>>= 32;
         var22 += var19 + ((long)var2[var21 + 8] & 4294967295L);
         var2[var21 + 8] = (int)var22;
         var19 = var22 >>> 32;
      }

      return (int)var19;
   }

   public static int mulAddTo(int[] var0, int var1, int[] var2, int var3, int[] var4, int var5) {
      long var6 = (long)var2[var3 + 0] & 4294967295L;
      long var8 = (long)var2[var3 + 1] & 4294967295L;
      long var10 = (long)var2[var3 + 2] & 4294967295L;
      long var12 = (long)var2[var3 + 3] & 4294967295L;
      long var14 = (long)var2[var3 + 4] & 4294967295L;
      long var16 = (long)var2[var3 + 5] & 4294967295L;
      long var18 = (long)var2[var3 + 6] & 4294967295L;
      long var20 = (long)var2[var3 + 7] & 4294967295L;
      long var22 = 0L;

      for(int var24 = 0; var24 < 8; ++var24) {
         long var25 = 0L;
         long var27 = (long)var0[var1 + var24] & 4294967295L;
         var25 += var27 * var6 + ((long)var4[var5 + 0] & 4294967295L);
         var4[var5 + 0] = (int)var25;
         var25 >>>= 32;
         var25 += var27 * var8 + ((long)var4[var5 + 1] & 4294967295L);
         var4[var5 + 1] = (int)var25;
         var25 >>>= 32;
         var25 += var27 * var10 + ((long)var4[var5 + 2] & 4294967295L);
         var4[var5 + 2] = (int)var25;
         var25 >>>= 32;
         var25 += var27 * var12 + ((long)var4[var5 + 3] & 4294967295L);
         var4[var5 + 3] = (int)var25;
         var25 >>>= 32;
         var25 += var27 * var14 + ((long)var4[var5 + 4] & 4294967295L);
         var4[var5 + 4] = (int)var25;
         var25 >>>= 32;
         var25 += var27 * var16 + ((long)var4[var5 + 5] & 4294967295L);
         var4[var5 + 5] = (int)var25;
         var25 >>>= 32;
         var25 += var27 * var18 + ((long)var4[var5 + 6] & 4294967295L);
         var4[var5 + 6] = (int)var25;
         var25 >>>= 32;
         var25 += var27 * var20 + ((long)var4[var5 + 7] & 4294967295L);
         var4[var5 + 7] = (int)var25;
         var25 >>>= 32;
         var25 += var22 + ((long)var4[var5 + 8] & 4294967295L);
         var4[var5 + 8] = (int)var25;
         var22 = var25 >>> 32;
         ++var5;
      }

      return (int)var22;
   }

   public static long mul33Add(int var0, int[] var1, int var2, int[] var3, int var4, int[] var5, int var6) {
      long var7 = 0L;
      long var9 = (long)var0 & 4294967295L;
      long var11 = (long)var1[var2 + 0] & 4294967295L;
      var7 += var9 * var11 + ((long)var3[var4 + 0] & 4294967295L);
      var5[var6 + 0] = (int)var7;
      var7 >>>= 32;
      long var13 = (long)var1[var2 + 1] & 4294967295L;
      var7 += var9 * var13 + var11 + ((long)var3[var4 + 1] & 4294967295L);
      var5[var6 + 1] = (int)var7;
      var7 >>>= 32;
      long var15 = (long)var1[var2 + 2] & 4294967295L;
      var7 += var9 * var15 + var13 + ((long)var3[var4 + 2] & 4294967295L);
      var5[var6 + 2] = (int)var7;
      var7 >>>= 32;
      long var17 = (long)var1[var2 + 3] & 4294967295L;
      var7 += var9 * var17 + var15 + ((long)var3[var4 + 3] & 4294967295L);
      var5[var6 + 3] = (int)var7;
      var7 >>>= 32;
      long var19 = (long)var1[var2 + 4] & 4294967295L;
      var7 += var9 * var19 + var17 + ((long)var3[var4 + 4] & 4294967295L);
      var5[var6 + 4] = (int)var7;
      var7 >>>= 32;
      long var21 = (long)var1[var2 + 5] & 4294967295L;
      var7 += var9 * var21 + var19 + ((long)var3[var4 + 5] & 4294967295L);
      var5[var6 + 5] = (int)var7;
      var7 >>>= 32;
      long var23 = (long)var1[var2 + 6] & 4294967295L;
      var7 += var9 * var23 + var21 + ((long)var3[var4 + 6] & 4294967295L);
      var5[var6 + 6] = (int)var7;
      var7 >>>= 32;
      long var25 = (long)var1[var2 + 7] & 4294967295L;
      var7 += var9 * var25 + var23 + ((long)var3[var4 + 7] & 4294967295L);
      var5[var6 + 7] = (int)var7;
      var7 >>>= 32;
      var7 += var25;
      return var7;
   }

   public static int mulByWord(int var0, int[] var1) {
      long var2 = 0L;
      long var4 = (long)var0 & 4294967295L;
      var2 += var4 * ((long)var1[0] & 4294967295L);
      var1[0] = (int)var2;
      var2 >>>= 32;
      var2 += var4 * ((long)var1[1] & 4294967295L);
      var1[1] = (int)var2;
      var2 >>>= 32;
      var2 += var4 * ((long)var1[2] & 4294967295L);
      var1[2] = (int)var2;
      var2 >>>= 32;
      var2 += var4 * ((long)var1[3] & 4294967295L);
      var1[3] = (int)var2;
      var2 >>>= 32;
      var2 += var4 * ((long)var1[4] & 4294967295L);
      var1[4] = (int)var2;
      var2 >>>= 32;
      var2 += var4 * ((long)var1[5] & 4294967295L);
      var1[5] = (int)var2;
      var2 >>>= 32;
      var2 += var4 * ((long)var1[6] & 4294967295L);
      var1[6] = (int)var2;
      var2 >>>= 32;
      var2 += var4 * ((long)var1[7] & 4294967295L);
      var1[7] = (int)var2;
      var2 >>>= 32;
      return (int)var2;
   }

   public static int mulByWordAddTo(int var0, int[] var1, int[] var2) {
      long var3 = 0L;
      long var5 = (long)var0 & 4294967295L;
      var3 += var5 * ((long)var2[0] & 4294967295L) + ((long)var1[0] & 4294967295L);
      var2[0] = (int)var3;
      var3 >>>= 32;
      var3 += var5 * ((long)var2[1] & 4294967295L) + ((long)var1[1] & 4294967295L);
      var2[1] = (int)var3;
      var3 >>>= 32;
      var3 += var5 * ((long)var2[2] & 4294967295L) + ((long)var1[2] & 4294967295L);
      var2[2] = (int)var3;
      var3 >>>= 32;
      var3 += var5 * ((long)var2[3] & 4294967295L) + ((long)var1[3] & 4294967295L);
      var2[3] = (int)var3;
      var3 >>>= 32;
      var3 += var5 * ((long)var2[4] & 4294967295L) + ((long)var1[4] & 4294967295L);
      var2[4] = (int)var3;
      var3 >>>= 32;
      var3 += var5 * ((long)var2[5] & 4294967295L) + ((long)var1[5] & 4294967295L);
      var2[5] = (int)var3;
      var3 >>>= 32;
      var3 += var5 * ((long)var2[6] & 4294967295L) + ((long)var1[6] & 4294967295L);
      var2[6] = (int)var3;
      var3 >>>= 32;
      var3 += var5 * ((long)var2[7] & 4294967295L) + ((long)var1[7] & 4294967295L);
      var2[7] = (int)var3;
      var3 >>>= 32;
      return (int)var3;
   }

   public static int mulWordAddTo(int var0, int[] var1, int var2, int[] var3, int var4) {
      long var5 = 0L;
      long var7 = (long)var0 & 4294967295L;
      var5 += var7 * ((long)var1[var2 + 0] & 4294967295L) + ((long)var3[var4 + 0] & 4294967295L);
      var3[var4 + 0] = (int)var5;
      var5 >>>= 32;
      var5 += var7 * ((long)var1[var2 + 1] & 4294967295L) + ((long)var3[var4 + 1] & 4294967295L);
      var3[var4 + 1] = (int)var5;
      var5 >>>= 32;
      var5 += var7 * ((long)var1[var2 + 2] & 4294967295L) + ((long)var3[var4 + 2] & 4294967295L);
      var3[var4 + 2] = (int)var5;
      var5 >>>= 32;
      var5 += var7 * ((long)var1[var2 + 3] & 4294967295L) + ((long)var3[var4 + 3] & 4294967295L);
      var3[var4 + 3] = (int)var5;
      var5 >>>= 32;
      var5 += var7 * ((long)var1[var2 + 4] & 4294967295L) + ((long)var3[var4 + 4] & 4294967295L);
      var3[var4 + 4] = (int)var5;
      var5 >>>= 32;
      var5 += var7 * ((long)var1[var2 + 5] & 4294967295L) + ((long)var3[var4 + 5] & 4294967295L);
      var3[var4 + 5] = (int)var5;
      var5 >>>= 32;
      var5 += var7 * ((long)var1[var2 + 6] & 4294967295L) + ((long)var3[var4 + 6] & 4294967295L);
      var3[var4 + 6] = (int)var5;
      var5 >>>= 32;
      var5 += var7 * ((long)var1[var2 + 7] & 4294967295L) + ((long)var3[var4 + 7] & 4294967295L);
      var3[var4 + 7] = (int)var5;
      var5 >>>= 32;
      return (int)var5;
   }

   public static int mul33DWordAdd(int var0, long var1, int[] var3, int var4) {
      long var5 = 0L;
      long var7 = (long)var0 & 4294967295L;
      long var9 = var1 & 4294967295L;
      var5 += var7 * var9 + ((long)var3[var4 + 0] & 4294967295L);
      var3[var4 + 0] = (int)var5;
      var5 >>>= 32;
      long var11 = var1 >>> 32;
      var5 += var7 * var11 + var9 + ((long)var3[var4 + 1] & 4294967295L);
      var3[var4 + 1] = (int)var5;
      var5 >>>= 32;
      var5 += var11 + ((long)var3[var4 + 2] & 4294967295L);
      var3[var4 + 2] = (int)var5;
      var5 >>>= 32;
      var5 += (long)var3[var4 + 3] & 4294967295L;
      var3[var4 + 3] = (int)var5;
      var5 >>>= 32;
      return var5 == 0L ? 0 : Nat.incAt(8, var3, var4, 4);
   }

   public static int mul33WordAdd(int var0, int var1, int[] var2, int var3) {
      long var4 = 0L;
      long var6 = (long)var0 & 4294967295L;
      long var8 = (long)var1 & 4294967295L;
      var4 += var8 * var6 + ((long)var2[var3 + 0] & 4294967295L);
      var2[var3 + 0] = (int)var4;
      var4 >>>= 32;
      var4 += var8 + ((long)var2[var3 + 1] & 4294967295L);
      var2[var3 + 1] = (int)var4;
      var4 >>>= 32;
      var4 += (long)var2[var3 + 2] & 4294967295L;
      var2[var3 + 2] = (int)var4;
      var4 >>>= 32;
      return var4 == 0L ? 0 : Nat.incAt(8, var2, var3, 3);
   }

   public static int mulWordDwordAdd(int var0, long var1, int[] var3, int var4) {
      long var5 = 0L;
      long var7 = (long)var0 & 4294967295L;
      var5 += var7 * (var1 & 4294967295L) + ((long)var3[var4 + 0] & 4294967295L);
      var3[var4 + 0] = (int)var5;
      var5 >>>= 32;
      var5 += var7 * (var1 >>> 32) + ((long)var3[var4 + 1] & 4294967295L);
      var3[var4 + 1] = (int)var5;
      var5 >>>= 32;
      var5 += (long)var3[var4 + 2] & 4294967295L;
      var3[var4 + 2] = (int)var5;
      var5 >>>= 32;
      return var5 == 0L ? 0 : Nat.incAt(8, var3, var4, 3);
   }

   public static int mulWord(int var0, int[] var1, int[] var2, int var3) {
      long var4 = 0L;
      long var6 = (long)var0 & 4294967295L;
      int var8 = 0;

      do {
         var4 += var6 * ((long)var1[var8] & 4294967295L);
         var2[var3 + var8] = (int)var4;
         var4 >>>= 32;
         ++var8;
      } while(var8 < 8);

      return (int)var4;
   }

   public static void square(int[] var0, int[] var1) {
      long var2 = (long)var0[0] & 4294967295L;
      int var4 = 0;
      int var5 = 7;
      int var6 = 16;

      long var7;
      long var9;
      do {
         var7 = (long)var0[var5--] & 4294967295L;
         var9 = var7 * var7;
         --var6;
         var1[var6] = var4 << 31 | (int)(var9 >>> 33);
         --var6;
         var1[var6] = (int)(var9 >>> 1);
         var4 = (int)var9;
      } while(var5 > 0);

      var7 = var2 * var2;
      long var11 = (long)(var4 << 31) & 4294967295L | var7 >>> 33;
      var1[0] = (int)var7;
      var4 = (int)(var7 >>> 32) & 1;
      long var13 = (long)var0[1] & 4294967295L;
      var7 = (long)var1[2] & 4294967295L;
      var11 += var13 * var2;
      int var15 = (int)var11;
      var1[1] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var7 += var11 >>> 32;
      var9 = (long)var0[2] & 4294967295L;
      long var16 = (long)var1[3] & 4294967295L;
      long var18 = (long)var1[4] & 4294967295L;
      var7 += var9 * var2;
      var15 = (int)var7;
      var1[2] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var16 += (var7 >>> 32) + var9 * var13;
      var18 += var16 >>> 32;
      var16 &= 4294967295L;
      long var20 = (long)var0[3] & 4294967295L;
      long var22 = ((long)var1[5] & 4294967295L) + (var18 >>> 32);
      var18 &= 4294967295L;
      long var24 = ((long)var1[6] & 4294967295L) + (var22 >>> 32);
      var22 &= 4294967295L;
      var16 += var20 * var2;
      var15 = (int)var16;
      var1[3] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var18 += (var16 >>> 32) + var20 * var13;
      var22 += (var18 >>> 32) + var20 * var9;
      var18 &= 4294967295L;
      var24 += var22 >>> 32;
      var22 &= 4294967295L;
      long var26 = (long)var0[4] & 4294967295L;
      long var28 = ((long)var1[7] & 4294967295L) + (var24 >>> 32);
      var24 &= 4294967295L;
      long var30 = ((long)var1[8] & 4294967295L) + (var28 >>> 32);
      var28 &= 4294967295L;
      var18 += var26 * var2;
      var15 = (int)var18;
      var1[4] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var22 += (var18 >>> 32) + var26 * var13;
      var24 += (var22 >>> 32) + var26 * var9;
      var22 &= 4294967295L;
      var28 += (var24 >>> 32) + var26 * var20;
      var24 &= 4294967295L;
      var30 += var28 >>> 32;
      var28 &= 4294967295L;
      long var32 = (long)var0[5] & 4294967295L;
      long var34 = ((long)var1[9] & 4294967295L) + (var30 >>> 32);
      var30 &= 4294967295L;
      long var36 = ((long)var1[10] & 4294967295L) + (var34 >>> 32);
      var34 &= 4294967295L;
      var22 += var32 * var2;
      var15 = (int)var22;
      var1[5] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var24 += (var22 >>> 32) + var32 * var13;
      var28 += (var24 >>> 32) + var32 * var9;
      var24 &= 4294967295L;
      var30 += (var28 >>> 32) + var32 * var20;
      var28 &= 4294967295L;
      var34 += (var30 >>> 32) + var32 * var26;
      var30 &= 4294967295L;
      var36 += var34 >>> 32;
      var34 &= 4294967295L;
      long var38 = (long)var0[6] & 4294967295L;
      long var40 = ((long)var1[11] & 4294967295L) + (var36 >>> 32);
      var36 &= 4294967295L;
      long var42 = ((long)var1[12] & 4294967295L) + (var40 >>> 32);
      var40 &= 4294967295L;
      var24 += var38 * var2;
      var15 = (int)var24;
      var1[6] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var28 += (var24 >>> 32) + var38 * var13;
      var30 += (var28 >>> 32) + var38 * var9;
      var28 &= 4294967295L;
      var34 += (var30 >>> 32) + var38 * var20;
      var30 &= 4294967295L;
      var36 += (var34 >>> 32) + var38 * var26;
      var34 &= 4294967295L;
      var40 += (var36 >>> 32) + var38 * var32;
      var36 &= 4294967295L;
      var42 += var40 >>> 32;
      var40 &= 4294967295L;
      long var44 = (long)var0[7] & 4294967295L;
      long var46 = ((long)var1[13] & 4294967295L) + (var42 >>> 32);
      var42 &= 4294967295L;
      long var48 = ((long)var1[14] & 4294967295L) + (var46 >>> 32);
      var46 &= 4294967295L;
      var28 += var44 * var2;
      var15 = (int)var28;
      var1[7] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var30 += (var28 >>> 32) + var44 * var13;
      var34 += (var30 >>> 32) + var44 * var9;
      var36 += (var34 >>> 32) + var44 * var20;
      var40 += (var36 >>> 32) + var44 * var26;
      var42 += (var40 >>> 32) + var44 * var32;
      var46 += (var42 >>> 32) + var44 * var38;
      var48 += var46 >>> 32;
      var15 = (int)var30;
      var1[8] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var15 = (int)var34;
      var1[9] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var15 = (int)var36;
      var1[10] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var15 = (int)var40;
      var1[11] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var15 = (int)var42;
      var1[12] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var15 = (int)var46;
      var1[13] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var15 = (int)var48;
      var1[14] = var15 << 1 | var4;
      var4 = var15 >>> 31;
      var15 = var1[15] + (int)(var48 >>> 32);
      var1[15] = var15 << 1 | var4;
   }

   public static void square(int[] var0, int var1, int[] var2, int var3) {
      long var4 = (long)var0[var1 + 0] & 4294967295L;
      int var6 = 0;
      int var7 = 7;
      int var8 = 16;

      long var9;
      long var11;
      do {
         var9 = (long)var0[var1 + var7--] & 4294967295L;
         var11 = var9 * var9;
         --var8;
         var2[var3 + var8] = var6 << 31 | (int)(var11 >>> 33);
         --var8;
         var2[var3 + var8] = (int)(var11 >>> 1);
         var6 = (int)var11;
      } while(var7 > 0);

      var9 = var4 * var4;
      long var13 = (long)(var6 << 31) & 4294967295L | var9 >>> 33;
      var2[var3 + 0] = (int)var9;
      var6 = (int)(var9 >>> 32) & 1;
      long var15 = (long)var0[var1 + 1] & 4294967295L;
      var9 = (long)var2[var3 + 2] & 4294967295L;
      var13 += var15 * var4;
      int var17 = (int)var13;
      var2[var3 + 1] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var9 += var13 >>> 32;
      var11 = (long)var0[var1 + 2] & 4294967295L;
      long var18 = (long)var2[var3 + 3] & 4294967295L;
      long var20 = (long)var2[var3 + 4] & 4294967295L;
      var9 += var11 * var4;
      var17 = (int)var9;
      var2[var3 + 2] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var18 += (var9 >>> 32) + var11 * var15;
      var20 += var18 >>> 32;
      var18 &= 4294967295L;
      long var22 = (long)var0[var1 + 3] & 4294967295L;
      long var24 = ((long)var2[var3 + 5] & 4294967295L) + (var20 >>> 32);
      var20 &= 4294967295L;
      long var26 = ((long)var2[var3 + 6] & 4294967295L) + (var24 >>> 32);
      var24 &= 4294967295L;
      var18 += var22 * var4;
      var17 = (int)var18;
      var2[var3 + 3] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var20 += (var18 >>> 32) + var22 * var15;
      var24 += (var20 >>> 32) + var22 * var11;
      var20 &= 4294967295L;
      var26 += var24 >>> 32;
      var24 &= 4294967295L;
      long var28 = (long)var0[var1 + 4] & 4294967295L;
      long var30 = ((long)var2[var3 + 7] & 4294967295L) + (var26 >>> 32);
      var26 &= 4294967295L;
      long var32 = ((long)var2[var3 + 8] & 4294967295L) + (var30 >>> 32);
      var30 &= 4294967295L;
      var20 += var28 * var4;
      var17 = (int)var20;
      var2[var3 + 4] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var24 += (var20 >>> 32) + var28 * var15;
      var26 += (var24 >>> 32) + var28 * var11;
      var24 &= 4294967295L;
      var30 += (var26 >>> 32) + var28 * var22;
      var26 &= 4294967295L;
      var32 += var30 >>> 32;
      var30 &= 4294967295L;
      long var34 = (long)var0[var1 + 5] & 4294967295L;
      long var36 = ((long)var2[var3 + 9] & 4294967295L) + (var32 >>> 32);
      var32 &= 4294967295L;
      long var38 = ((long)var2[var3 + 10] & 4294967295L) + (var36 >>> 32);
      var36 &= 4294967295L;
      var24 += var34 * var4;
      var17 = (int)var24;
      var2[var3 + 5] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var26 += (var24 >>> 32) + var34 * var15;
      var30 += (var26 >>> 32) + var34 * var11;
      var26 &= 4294967295L;
      var32 += (var30 >>> 32) + var34 * var22;
      var30 &= 4294967295L;
      var36 += (var32 >>> 32) + var34 * var28;
      var32 &= 4294967295L;
      var38 += var36 >>> 32;
      var36 &= 4294967295L;
      long var40 = (long)var0[var1 + 6] & 4294967295L;
      long var42 = ((long)var2[var3 + 11] & 4294967295L) + (var38 >>> 32);
      var38 &= 4294967295L;
      long var44 = ((long)var2[var3 + 12] & 4294967295L) + (var42 >>> 32);
      var42 &= 4294967295L;
      var26 += var40 * var4;
      var17 = (int)var26;
      var2[var3 + 6] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var30 += (var26 >>> 32) + var40 * var15;
      var32 += (var30 >>> 32) + var40 * var11;
      var30 &= 4294967295L;
      var36 += (var32 >>> 32) + var40 * var22;
      var32 &= 4294967295L;
      var38 += (var36 >>> 32) + var40 * var28;
      var36 &= 4294967295L;
      var42 += (var38 >>> 32) + var40 * var34;
      var38 &= 4294967295L;
      var44 += var42 >>> 32;
      var42 &= 4294967295L;
      long var46 = (long)var0[var1 + 7] & 4294967295L;
      long var48 = ((long)var2[var3 + 13] & 4294967295L) + (var44 >>> 32);
      var44 &= 4294967295L;
      long var50 = ((long)var2[var3 + 14] & 4294967295L) + (var48 >>> 32);
      var48 &= 4294967295L;
      var30 += var46 * var4;
      var17 = (int)var30;
      var2[var3 + 7] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var32 += (var30 >>> 32) + var46 * var15;
      var36 += (var32 >>> 32) + var46 * var11;
      var38 += (var36 >>> 32) + var46 * var22;
      var42 += (var38 >>> 32) + var46 * var28;
      var44 += (var42 >>> 32) + var46 * var34;
      var48 += (var44 >>> 32) + var46 * var40;
      var50 += var48 >>> 32;
      var17 = (int)var32;
      var2[var3 + 8] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var17 = (int)var36;
      var2[var3 + 9] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var17 = (int)var38;
      var2[var3 + 10] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var17 = (int)var42;
      var2[var3 + 11] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var17 = (int)var44;
      var2[var3 + 12] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var17 = (int)var48;
      var2[var3 + 13] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var17 = (int)var50;
      var2[var3 + 14] = var17 << 1 | var6;
      var6 = var17 >>> 31;
      var17 = var2[var3 + 15] + (int)(var50 >>> 32);
      var2[var3 + 15] = var17 << 1 | var6;
   }

   public static int sub(int[] var0, int[] var1, int[] var2) {
      long var3 = 0L;
      var3 += ((long)var0[0] & 4294967295L) - ((long)var1[0] & 4294967295L);
      var2[0] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var0[1] & 4294967295L) - ((long)var1[1] & 4294967295L);
      var2[1] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var0[2] & 4294967295L) - ((long)var1[2] & 4294967295L);
      var2[2] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var0[3] & 4294967295L) - ((long)var1[3] & 4294967295L);
      var2[3] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var0[4] & 4294967295L) - ((long)var1[4] & 4294967295L);
      var2[4] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var0[5] & 4294967295L) - ((long)var1[5] & 4294967295L);
      var2[5] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var0[6] & 4294967295L) - ((long)var1[6] & 4294967295L);
      var2[6] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var0[7] & 4294967295L) - ((long)var1[7] & 4294967295L);
      var2[7] = (int)var3;
      var3 >>= 32;
      return (int)var3;
   }

   public static int sub(int[] var0, int var1, int[] var2, int var3, int[] var4, int var5) {
      long var6 = 0L;
      var6 += ((long)var0[var1 + 0] & 4294967295L) - ((long)var2[var3 + 0] & 4294967295L);
      var4[var5 + 0] = (int)var6;
      var6 >>= 32;
      var6 += ((long)var0[var1 + 1] & 4294967295L) - ((long)var2[var3 + 1] & 4294967295L);
      var4[var5 + 1] = (int)var6;
      var6 >>= 32;
      var6 += ((long)var0[var1 + 2] & 4294967295L) - ((long)var2[var3 + 2] & 4294967295L);
      var4[var5 + 2] = (int)var6;
      var6 >>= 32;
      var6 += ((long)var0[var1 + 3] & 4294967295L) - ((long)var2[var3 + 3] & 4294967295L);
      var4[var5 + 3] = (int)var6;
      var6 >>= 32;
      var6 += ((long)var0[var1 + 4] & 4294967295L) - ((long)var2[var3 + 4] & 4294967295L);
      var4[var5 + 4] = (int)var6;
      var6 >>= 32;
      var6 += ((long)var0[var1 + 5] & 4294967295L) - ((long)var2[var3 + 5] & 4294967295L);
      var4[var5 + 5] = (int)var6;
      var6 >>= 32;
      var6 += ((long)var0[var1 + 6] & 4294967295L) - ((long)var2[var3 + 6] & 4294967295L);
      var4[var5 + 6] = (int)var6;
      var6 >>= 32;
      var6 += ((long)var0[var1 + 7] & 4294967295L) - ((long)var2[var3 + 7] & 4294967295L);
      var4[var5 + 7] = (int)var6;
      var6 >>= 32;
      return (int)var6;
   }

   public static int subBothFrom(int[] var0, int[] var1, int[] var2) {
      long var3 = 0L;
      var3 += ((long)var2[0] & 4294967295L) - ((long)var0[0] & 4294967295L) - ((long)var1[0] & 4294967295L);
      var2[0] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var2[1] & 4294967295L) - ((long)var0[1] & 4294967295L) - ((long)var1[1] & 4294967295L);
      var2[1] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var2[2] & 4294967295L) - ((long)var0[2] & 4294967295L) - ((long)var1[2] & 4294967295L);
      var2[2] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var2[3] & 4294967295L) - ((long)var0[3] & 4294967295L) - ((long)var1[3] & 4294967295L);
      var2[3] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var2[4] & 4294967295L) - ((long)var0[4] & 4294967295L) - ((long)var1[4] & 4294967295L);
      var2[4] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var2[5] & 4294967295L) - ((long)var0[5] & 4294967295L) - ((long)var1[5] & 4294967295L);
      var2[5] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var2[6] & 4294967295L) - ((long)var0[6] & 4294967295L) - ((long)var1[6] & 4294967295L);
      var2[6] = (int)var3;
      var3 >>= 32;
      var3 += ((long)var2[7] & 4294967295L) - ((long)var0[7] & 4294967295L) - ((long)var1[7] & 4294967295L);
      var2[7] = (int)var3;
      var3 >>= 32;
      return (int)var3;
   }

   public static int subFrom(int[] var0, int[] var1) {
      long var2 = 0L;
      var2 += ((long)var1[0] & 4294967295L) - ((long)var0[0] & 4294967295L);
      var1[0] = (int)var2;
      var2 >>= 32;
      var2 += ((long)var1[1] & 4294967295L) - ((long)var0[1] & 4294967295L);
      var1[1] = (int)var2;
      var2 >>= 32;
      var2 += ((long)var1[2] & 4294967295L) - ((long)var0[2] & 4294967295L);
      var1[2] = (int)var2;
      var2 >>= 32;
      var2 += ((long)var1[3] & 4294967295L) - ((long)var0[3] & 4294967295L);
      var1[3] = (int)var2;
      var2 >>= 32;
      var2 += ((long)var1[4] & 4294967295L) - ((long)var0[4] & 4294967295L);
      var1[4] = (int)var2;
      var2 >>= 32;
      var2 += ((long)var1[5] & 4294967295L) - ((long)var0[5] & 4294967295L);
      var1[5] = (int)var2;
      var2 >>= 32;
      var2 += ((long)var1[6] & 4294967295L) - ((long)var0[6] & 4294967295L);
      var1[6] = (int)var2;
      var2 >>= 32;
      var2 += ((long)var1[7] & 4294967295L) - ((long)var0[7] & 4294967295L);
      var1[7] = (int)var2;
      var2 >>= 32;
      return (int)var2;
   }

   public static int subFrom(int[] var0, int var1, int[] var2, int var3) {
      long var4 = 0L;
      var4 += ((long)var2[var3 + 0] & 4294967295L) - ((long)var0[var1 + 0] & 4294967295L);
      var2[var3 + 0] = (int)var4;
      var4 >>= 32;
      var4 += ((long)var2[var3 + 1] & 4294967295L) - ((long)var0[var1 + 1] & 4294967295L);
      var2[var3 + 1] = (int)var4;
      var4 >>= 32;
      var4 += ((long)var2[var3 + 2] & 4294967295L) - ((long)var0[var1 + 2] & 4294967295L);
      var2[var3 + 2] = (int)var4;
      var4 >>= 32;
      var4 += ((long)var2[var3 + 3] & 4294967295L) - ((long)var0[var1 + 3] & 4294967295L);
      var2[var3 + 3] = (int)var4;
      var4 >>= 32;
      var4 += ((long)var2[var3 + 4] & 4294967295L) - ((long)var0[var1 + 4] & 4294967295L);
      var2[var3 + 4] = (int)var4;
      var4 >>= 32;
      var4 += ((long)var2[var3 + 5] & 4294967295L) - ((long)var0[var1 + 5] & 4294967295L);
      var2[var3 + 5] = (int)var4;
      var4 >>= 32;
      var4 += ((long)var2[var3 + 6] & 4294967295L) - ((long)var0[var1 + 6] & 4294967295L);
      var2[var3 + 6] = (int)var4;
      var4 >>= 32;
      var4 += ((long)var2[var3 + 7] & 4294967295L) - ((long)var0[var1 + 7] & 4294967295L);
      var2[var3 + 7] = (int)var4;
      var4 >>= 32;
      return (int)var4;
   }

   public static BigInteger toBigInteger(int[] var0) {
      byte[] var1 = new byte[32];

      for(int var2 = 0; var2 < 8; ++var2) {
         int var3 = var0[var2];
         if (var3 != 0) {
            Pack.intToBigEndian(var3, var1, 7 - var2 << 2);
         }
      }

      return new BigInteger(1, var1);
   }

   public static BigInteger toBigInteger64(long[] var0) {
      byte[] var1 = new byte[32];

      for(int var2 = 0; var2 < 4; ++var2) {
         long var3 = var0[var2];
         if (var3 != 0L) {
            Pack.longToBigEndian(var3, var1, 3 - var2 << 3);
         }
      }

      return new BigInteger(1, var1);
   }

   public static void zero(int[] var0) {
      var0[0] = 0;
      var0[1] = 0;
      var0[2] = 0;
      var0[3] = 0;
      var0[4] = 0;
      var0[5] = 0;
      var0[6] = 0;
      var0[7] = 0;
   }
}
