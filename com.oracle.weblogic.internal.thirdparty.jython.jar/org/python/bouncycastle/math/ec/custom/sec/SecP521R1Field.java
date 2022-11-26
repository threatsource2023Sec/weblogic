package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.raw.Nat;
import org.python.bouncycastle.math.raw.Nat512;

public class SecP521R1Field {
   static final int[] P = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 511};
   private static final int P16 = 511;

   public static void add(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat.add(16, var0, var1, var2) + var0[16] + var1[16];
      if (var3 > 511 || var3 == 511 && Nat.eq(16, var2, P)) {
         var3 += Nat.inc(16, var2);
         var3 &= 511;
      }

      var2[16] = var3;
   }

   public static void addOne(int[] var0, int[] var1) {
      int var2 = Nat.inc(16, var0, var1) + var0[16];
      if (var2 > 511 || var2 == 511 && Nat.eq(16, var1, P)) {
         var2 += Nat.inc(16, var1);
         var2 &= 511;
      }

      var1[16] = var2;
   }

   public static int[] fromBigInteger(BigInteger var0) {
      int[] var1 = Nat.fromBigInteger(521, var0);
      if (Nat.eq(17, var1, P)) {
         Nat.zero(17, var1);
      }

      return var1;
   }

   public static void half(int[] var0, int[] var1) {
      int var2 = var0[16];
      int var3 = Nat.shiftDownBit(16, var0, var2, var1);
      var1[16] = var2 >>> 1 | var3 >>> 23;
   }

   public static void multiply(int[] var0, int[] var1, int[] var2) {
      int[] var3 = Nat.create(33);
      implMultiply(var0, var1, var3);
      reduce(var3, var2);
   }

   public static void negate(int[] var0, int[] var1) {
      if (Nat.isZero(17, var0)) {
         Nat.zero(17, var1);
      } else {
         Nat.sub(17, P, var0, var1);
      }

   }

   public static void reduce(int[] var0, int[] var1) {
      int var2 = var0[32];
      int var3 = Nat.shiftDownBits(16, var0, 16, 9, var2, var1, 0) >>> 23;
      var3 += var2 >>> 9;
      var3 += Nat.addTo(16, var0, var1);
      if (var3 > 511 || var3 == 511 && Nat.eq(16, var1, P)) {
         var3 += Nat.inc(16, var1);
         var3 &= 511;
      }

      var1[16] = var3;
   }

   public static void reduce23(int[] var0) {
      int var1 = var0[16];
      int var2 = Nat.addWordTo(16, var1 >>> 9, var0) + (var1 & 511);
      if (var2 > 511 || var2 == 511 && Nat.eq(16, var0, P)) {
         var2 += Nat.inc(16, var0);
         var2 &= 511;
      }

      var0[16] = var2;
   }

   public static void square(int[] var0, int[] var1) {
      int[] var2 = Nat.create(33);
      implSquare(var0, var2);
      reduce(var2, var1);
   }

   public static void squareN(int[] var0, int var1, int[] var2) {
      int[] var3 = Nat.create(33);
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

   public static void subtract(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat.sub(16, var0, var1, var2) + var0[16] - var1[16];
      if (var3 < 0) {
         var3 += Nat.dec(16, var2);
         var3 &= 511;
      }

      var2[16] = var3;
   }

   public static void twice(int[] var0, int[] var1) {
      int var2 = var0[16];
      int var3 = Nat.shiftUpBit(16, var0, var2 << 23, var1) | var2 << 1;
      var1[16] = var3 & 511;
   }

   protected static void implMultiply(int[] var0, int[] var1, int[] var2) {
      Nat512.mul(var0, var1, var2);
      int var3 = var0[16];
      int var4 = var1[16];
      var2[32] = Nat.mul31BothAdd(16, var3, var1, var4, var0, var2, 16) + var3 * var4;
   }

   protected static void implSquare(int[] var0, int[] var1) {
      Nat512.square(var0, var1);
      int var2 = var0[16];
      var1[32] = Nat.mulWordAddTo(16, var2 << 1, var0, 0, var1, 16) + var2 * var2;
   }
}
