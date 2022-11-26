package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.raw.Interleave;
import org.python.bouncycastle.math.raw.Nat256;

public class SecT193Field {
   private static final long M01 = 1L;
   private static final long M49 = 562949953421311L;

   public static void add(long[] var0, long[] var1, long[] var2) {
      var2[0] = var0[0] ^ var1[0];
      var2[1] = var0[1] ^ var1[1];
      var2[2] = var0[2] ^ var1[2];
      var2[3] = var0[3] ^ var1[3];
   }

   public static void addExt(long[] var0, long[] var1, long[] var2) {
      var2[0] = var0[0] ^ var1[0];
      var2[1] = var0[1] ^ var1[1];
      var2[2] = var0[2] ^ var1[2];
      var2[3] = var0[3] ^ var1[3];
      var2[4] = var0[4] ^ var1[4];
      var2[5] = var0[5] ^ var1[5];
      var2[6] = var0[6] ^ var1[6];
   }

   public static void addOne(long[] var0, long[] var1) {
      var1[0] = var0[0] ^ 1L;
      var1[1] = var0[1];
      var1[2] = var0[2];
      var1[3] = var0[3];
   }

   public static long[] fromBigInteger(BigInteger var0) {
      long[] var1 = Nat256.fromBigInteger64(var0);
      reduce63(var1, 0);
      return var1;
   }

   public static void invert(long[] var0, long[] var1) {
      if (Nat256.isZero64(var0)) {
         throw new IllegalStateException();
      } else {
         long[] var2 = Nat256.create64();
         long[] var3 = Nat256.create64();
         square(var0, var2);
         squareN(var2, 1, var3);
         multiply(var2, var3, var2);
         squareN(var3, 1, var3);
         multiply(var2, var3, var2);
         squareN(var2, 3, var3);
         multiply(var2, var3, var2);
         squareN(var2, 6, var3);
         multiply(var2, var3, var2);
         squareN(var2, 12, var3);
         multiply(var2, var3, var2);
         squareN(var2, 24, var3);
         multiply(var2, var3, var2);
         squareN(var2, 48, var3);
         multiply(var2, var3, var2);
         squareN(var2, 96, var3);
         multiply(var2, var3, var1);
      }
   }

   public static void multiply(long[] var0, long[] var1, long[] var2) {
      long[] var3 = Nat256.createExt64();
      implMultiply(var0, var1, var3);
      reduce(var3, var2);
   }

   public static void multiplyAddToExt(long[] var0, long[] var1, long[] var2) {
      long[] var3 = Nat256.createExt64();
      implMultiply(var0, var1, var3);
      addExt(var2, var3, var2);
   }

   public static void reduce(long[] var0, long[] var1) {
      long var2 = var0[0];
      long var4 = var0[1];
      long var6 = var0[2];
      long var8 = var0[3];
      long var10 = var0[4];
      long var12 = var0[5];
      long var14 = var0[6];
      var6 ^= var14 << 63;
      var8 ^= var14 >>> 1 ^ var14 << 14;
      var10 ^= var14 >>> 50;
      var4 ^= var12 << 63;
      var6 ^= var12 >>> 1 ^ var12 << 14;
      var8 ^= var12 >>> 50;
      var2 ^= var10 << 63;
      var4 ^= var10 >>> 1 ^ var10 << 14;
      var6 ^= var10 >>> 50;
      long var16 = var8 >>> 1;
      var1[0] = var2 ^ var16 ^ var16 << 15;
      var1[1] = var4 ^ var16 >>> 49;
      var1[2] = var6;
      var1[3] = var8 & 1L;
   }

   public static void reduce63(long[] var0, int var1) {
      long var2 = var0[var1 + 3];
      long var4 = var2 >>> 1;
      var0[var1] ^= var4 ^ var4 << 15;
      var0[var1 + 1] ^= var4 >>> 49;
      var0[var1 + 3] = var2 & 1L;
   }

   public static void sqrt(long[] var0, long[] var1) {
      long var2 = Interleave.unshuffle(var0[0]);
      long var4 = Interleave.unshuffle(var0[1]);
      long var6 = var2 & 4294967295L | var4 << 32;
      long var8 = var2 >>> 32 | var4 & -4294967296L;
      var2 = Interleave.unshuffle(var0[2]);
      long var10 = var2 & 4294967295L ^ var0[3] << 32;
      long var12 = var2 >>> 32;
      var1[0] = var6 ^ var8 << 8;
      var1[1] = var10 ^ var12 << 8 ^ var8 >>> 56 ^ var8 << 33;
      var1[2] = var12 >>> 56 ^ var12 << 33 ^ var8 >>> 31;
      var1[3] = var12 >>> 31;
   }

   public static void square(long[] var0, long[] var1) {
      long[] var2 = Nat256.createExt64();
      implSquare(var0, var2);
      reduce(var2, var1);
   }

   public static void squareAddToExt(long[] var0, long[] var1) {
      long[] var2 = Nat256.createExt64();
      implSquare(var0, var2);
      addExt(var1, var2, var1);
   }

   public static void squareN(long[] var0, int var1, long[] var2) {
      long[] var3 = Nat256.createExt64();
      implSquare(var0, var3);
      reduce(var3, var2);

      while(true) {
         --var1;
         if (var1 <= 0) {
            return;
         }

         implSquare(var2, var3);
         reduce(var3, var2);
      }
   }

   public static int trace(long[] var0) {
      return (int)var0[0] & 1;
   }

   protected static void implCompactExt(long[] var0) {
      long var1 = var0[0];
      long var3 = var0[1];
      long var5 = var0[2];
      long var7 = var0[3];
      long var9 = var0[4];
      long var11 = var0[5];
      long var13 = var0[6];
      long var15 = var0[7];
      var0[0] = var1 ^ var3 << 49;
      var0[1] = var3 >>> 15 ^ var5 << 34;
      var0[2] = var5 >>> 30 ^ var7 << 19;
      var0[3] = var7 >>> 45 ^ var9 << 4 ^ var11 << 53;
      var0[4] = var9 >>> 60 ^ var13 << 38 ^ var11 >>> 11;
      var0[5] = var13 >>> 26 ^ var15 << 23;
      var0[6] = var15 >>> 41;
      var0[7] = 0L;
   }

   protected static void implExpand(long[] var0, long[] var1) {
      long var2 = var0[0];
      long var4 = var0[1];
      long var6 = var0[2];
      long var8 = var0[3];
      var1[0] = var2 & 562949953421311L;
      var1[1] = (var2 >>> 49 ^ var4 << 15) & 562949953421311L;
      var1[2] = (var4 >>> 34 ^ var6 << 30) & 562949953421311L;
      var1[3] = var6 >>> 19 ^ var8 << 45;
   }

   protected static void implMultiply(long[] var0, long[] var1, long[] var2) {
      long[] var3 = new long[4];
      long[] var4 = new long[4];
      implExpand(var0, var3);
      implExpand(var1, var4);
      implMulwAcc(var3[0], var4[0], var2, 0);
      implMulwAcc(var3[1], var4[1], var2, 1);
      implMulwAcc(var3[2], var4[2], var2, 2);
      implMulwAcc(var3[3], var4[3], var2, 3);

      int var5;
      for(var5 = 5; var5 > 0; --var5) {
         var2[var5] ^= var2[var5 - 1];
      }

      implMulwAcc(var3[0] ^ var3[1], var4[0] ^ var4[1], var2, 1);
      implMulwAcc(var3[2] ^ var3[3], var4[2] ^ var4[3], var2, 3);

      for(var5 = 7; var5 > 1; --var5) {
         var2[var5] ^= var2[var5 - 2];
      }

      long var6 = var3[0] ^ var3[2];
      long var8 = var3[1] ^ var3[3];
      long var10 = var4[0] ^ var4[2];
      long var12 = var4[1] ^ var4[3];
      implMulwAcc(var6 ^ var8, var10 ^ var12, var2, 3);
      long[] var14 = new long[3];
      implMulwAcc(var6, var10, var14, 0);
      implMulwAcc(var8, var12, var14, 1);
      long var15 = var14[0];
      long var17 = var14[1];
      long var19 = var14[2];
      var2[2] ^= var15;
      var2[3] ^= var15 ^ var17;
      var2[4] ^= var19 ^ var17;
      var2[5] ^= var19;
      implCompactExt(var2);
   }

   protected static void implMulwAcc(long var0, long var2, long[] var4, int var5) {
      long[] var6 = new long[8];
      var6[1] = var2;
      var6[2] = var6[1] << 1;
      var6[3] = var6[2] ^ var2;
      var6[4] = var6[2] << 1;
      var6[5] = var6[4] ^ var2;
      var6[6] = var6[3] << 1;
      var6[7] = var6[6] ^ var2;
      int var7 = (int)var0;
      long var8 = 0L;
      long var10 = var6[var7 & 7] ^ var6[var7 >>> 3 & 7] << 3;
      int var12 = 36;

      do {
         var7 = (int)(var0 >>> var12);
         long var13 = var6[var7 & 7] ^ var6[var7 >>> 3 & 7] << 3 ^ var6[var7 >>> 6 & 7] << 6 ^ var6[var7 >>> 9 & 7] << 9 ^ var6[var7 >>> 12 & 7] << 12;
         var10 ^= var13 << var12;
         var8 ^= var13 >>> -var12;
         var12 -= 15;
      } while(var12 > 0);

      var4[var5] ^= var10 & 562949953421311L;
      var4[var5 + 1] ^= var10 >>> 49 ^ var8 << 15;
   }

   protected static void implSquare(long[] var0, long[] var1) {
      Interleave.expand64To128(var0[0], var1, 0);
      Interleave.expand64To128(var0[1], var1, 2);
      Interleave.expand64To128(var0[2], var1, 4);
      var1[6] = var0[3] & 1L;
   }
}
