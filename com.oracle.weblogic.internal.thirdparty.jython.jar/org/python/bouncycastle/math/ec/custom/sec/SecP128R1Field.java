package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.raw.Nat;
import org.python.bouncycastle.math.raw.Nat128;
import org.python.bouncycastle.math.raw.Nat256;

public class SecP128R1Field {
   private static final long M = 4294967295L;
   static final int[] P = new int[]{-1, -1, -1, -3};
   static final int[] PExt = new int[]{1, 0, 0, 4, -2, -1, 3, -4};
   private static final int[] PExtInv = new int[]{-1, -1, -1, -5, 1, 0, -4, 3};
   private static final int P3 = -3;
   private static final int PExt7 = -4;

   public static void add(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat128.add(var0, var1, var2);
      if (var3 != 0 || var2[3] == -3 && Nat128.gte(var2, P)) {
         addPInvTo(var2);
      }

   }

   public static void addExt(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat256.add(var0, var1, var2);
      if (var3 != 0 || var2[7] == -4 && Nat256.gte(var2, PExt)) {
         Nat.addTo(PExtInv.length, PExtInv, var2);
      }

   }

   public static void addOne(int[] var0, int[] var1) {
      int var2 = Nat.inc(4, var0, var1);
      if (var2 != 0 || var1[3] == -3 && Nat128.gte(var1, P)) {
         addPInvTo(var1);
      }

   }

   public static int[] fromBigInteger(BigInteger var0) {
      int[] var1 = Nat128.fromBigInteger(var0);
      if (var1[3] == -3 && Nat128.gte(var1, P)) {
         Nat128.subFrom(P, var1);
      }

      return var1;
   }

   public static void half(int[] var0, int[] var1) {
      if ((var0[0] & 1) == 0) {
         Nat.shiftDownBit(4, var0, 0, var1);
      } else {
         int var2 = Nat128.add(var0, P, var1);
         Nat.shiftDownBit(4, var1, var2);
      }

   }

   public static void multiply(int[] var0, int[] var1, int[] var2) {
      int[] var3 = Nat128.createExt();
      Nat128.mul(var0, var1, var3);
      reduce(var3, var2);
   }

   public static void multiplyAddToExt(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat128.mulAddTo(var0, var1, var2);
      if (var3 != 0 || var2[7] == -4 && Nat256.gte(var2, PExt)) {
         Nat.addTo(PExtInv.length, PExtInv, var2);
      }

   }

   public static void negate(int[] var0, int[] var1) {
      if (Nat128.isZero(var0)) {
         Nat128.zero(var1);
      } else {
         Nat128.sub(P, var0, var1);
      }

   }

   public static void reduce(int[] var0, int[] var1) {
      long var2 = (long)var0[0] & 4294967295L;
      long var4 = (long)var0[1] & 4294967295L;
      long var6 = (long)var0[2] & 4294967295L;
      long var8 = (long)var0[3] & 4294967295L;
      long var10 = (long)var0[4] & 4294967295L;
      long var12 = (long)var0[5] & 4294967295L;
      long var14 = (long)var0[6] & 4294967295L;
      long var16 = (long)var0[7] & 4294967295L;
      var8 += var16;
      var14 += var16 << 1;
      var6 += var14;
      var12 += var14 << 1;
      var4 += var12;
      var10 += var12 << 1;
      var2 += var10;
      var8 += var10 << 1;
      var1[0] = (int)var2;
      var4 += var2 >>> 32;
      var1[1] = (int)var4;
      var6 += var4 >>> 32;
      var1[2] = (int)var6;
      var8 += var6 >>> 32;
      var1[3] = (int)var8;
      reduce32((int)(var8 >>> 32), var1);
   }

   public static void reduce32(int var0, int[] var1) {
      while(var0 != 0) {
         long var2 = (long)var0 & 4294967295L;
         long var4 = ((long)var1[0] & 4294967295L) + var2;
         var1[0] = (int)var4;
         var4 >>= 32;
         if (var4 != 0L) {
            var4 += (long)var1[1] & 4294967295L;
            var1[1] = (int)var4;
            var4 >>= 32;
            var4 += (long)var1[2] & 4294967295L;
            var1[2] = (int)var4;
            var4 >>= 32;
         }

         var4 += ((long)var1[3] & 4294967295L) + (var2 << 1);
         var1[3] = (int)var4;
         var4 >>= 32;
         var0 = (int)var4;
      }

   }

   public static void square(int[] var0, int[] var1) {
      int[] var2 = Nat128.createExt();
      Nat128.square(var0, var2);
      reduce(var2, var1);
   }

   public static void squareN(int[] var0, int var1, int[] var2) {
      int[] var3 = Nat128.createExt();
      Nat128.square(var0, var3);
      reduce(var3, var2);

      while(true) {
         --var1;
         if (var1 <= 0) {
            return;
         }

         Nat128.square(var2, var3);
         reduce(var3, var2);
      }
   }

   public static void subtract(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat128.sub(var0, var1, var2);
      if (var3 != 0) {
         subPInvFrom(var2);
      }

   }

   public static void subtractExt(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat.sub(10, var0, var1, var2);
      if (var3 != 0) {
         Nat.subFrom(PExtInv.length, PExtInv, var2);
      }

   }

   public static void twice(int[] var0, int[] var1) {
      int var2 = Nat.shiftUpBit(4, var0, 0, var1);
      if (var2 != 0 || var1[3] == -3 && Nat128.gte(var1, P)) {
         addPInvTo(var1);
      }

   }

   private static void addPInvTo(int[] var0) {
      long var1 = ((long)var0[0] & 4294967295L) + 1L;
      var0[0] = (int)var1;
      var1 >>= 32;
      if (var1 != 0L) {
         var1 += (long)var0[1] & 4294967295L;
         var0[1] = (int)var1;
         var1 >>= 32;
         var1 += (long)var0[2] & 4294967295L;
         var0[2] = (int)var1;
         var1 >>= 32;
      }

      var1 += ((long)var0[3] & 4294967295L) + 2L;
      var0[3] = (int)var1;
   }

   private static void subPInvFrom(int[] var0) {
      long var1 = ((long)var0[0] & 4294967295L) - 1L;
      var0[0] = (int)var1;
      var1 >>= 32;
      if (var1 != 0L) {
         var1 += (long)var0[1] & 4294967295L;
         var0[1] = (int)var1;
         var1 >>= 32;
         var1 += (long)var0[2] & 4294967295L;
         var0[2] = (int)var1;
         var1 >>= 32;
      }

      var1 += ((long)var0[3] & 4294967295L) - 2L;
      var0[3] = (int)var1;
   }
}
