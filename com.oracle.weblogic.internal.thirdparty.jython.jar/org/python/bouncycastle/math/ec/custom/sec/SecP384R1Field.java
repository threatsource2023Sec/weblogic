package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.raw.Nat;
import org.python.bouncycastle.math.raw.Nat384;

public class SecP384R1Field {
   private static final long M = 4294967295L;
   static final int[] P = new int[]{-1, 0, 0, -1, -2, -1, -1, -1, -1, -1, -1, -1};
   static final int[] PExt = new int[]{1, -2, 0, 2, 0, -2, 0, 2, 1, 0, 0, 0, -2, 1, 0, -2, -3, -1, -1, -1, -1, -1, -1, -1};
   private static final int[] PExtInv = new int[]{-1, 1, -1, -3, -1, 1, -1, -3, -2, -1, -1, -1, 1, -2, -1, 1, 2};
   private static final int P11 = -1;
   private static final int PExt23 = -1;

   public static void add(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat.add(12, var0, var1, var2);
      if (var3 != 0 || var2[11] == -1 && Nat.gte(12, var2, P)) {
         addPInvTo(var2);
      }

   }

   public static void addExt(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat.add(24, var0, var1, var2);
      if ((var3 != 0 || var2[23] == -1 && Nat.gte(24, var2, PExt)) && Nat.addTo(PExtInv.length, PExtInv, var2) != 0) {
         Nat.incAt(24, var2, PExtInv.length);
      }

   }

   public static void addOne(int[] var0, int[] var1) {
      int var2 = Nat.inc(12, var0, var1);
      if (var2 != 0 || var1[11] == -1 && Nat.gte(12, var1, P)) {
         addPInvTo(var1);
      }

   }

   public static int[] fromBigInteger(BigInteger var0) {
      int[] var1 = Nat.fromBigInteger(384, var0);
      if (var1[11] == -1 && Nat.gte(12, var1, P)) {
         Nat.subFrom(12, P, var1);
      }

      return var1;
   }

   public static void half(int[] var0, int[] var1) {
      if ((var0[0] & 1) == 0) {
         Nat.shiftDownBit(12, var0, 0, var1);
      } else {
         int var2 = Nat.add(12, var0, P, var1);
         Nat.shiftDownBit(12, var1, var2);
      }

   }

   public static void multiply(int[] var0, int[] var1, int[] var2) {
      int[] var3 = Nat.create(24);
      Nat384.mul(var0, var1, var3);
      reduce(var3, var2);
   }

   public static void negate(int[] var0, int[] var1) {
      if (Nat.isZero(12, var0)) {
         Nat.zero(12, var1);
      } else {
         Nat.sub(12, P, var0, var1);
      }

   }

   public static void reduce(int[] var0, int[] var1) {
      long var2 = (long)var0[16] & 4294967295L;
      long var4 = (long)var0[17] & 4294967295L;
      long var6 = (long)var0[18] & 4294967295L;
      long var8 = (long)var0[19] & 4294967295L;
      long var10 = (long)var0[20] & 4294967295L;
      long var12 = (long)var0[21] & 4294967295L;
      long var14 = (long)var0[22] & 4294967295L;
      long var16 = (long)var0[23] & 4294967295L;
      long var18 = ((long)var0[12] & 4294967295L) + var10 - 1L;
      long var20 = ((long)var0[13] & 4294967295L) + var14;
      long var22 = ((long)var0[14] & 4294967295L) + var14 + var16;
      long var24 = ((long)var0[15] & 4294967295L) + var16;
      long var26 = var4 + var12;
      long var28 = var12 - var16;
      long var30 = var14 - var16;
      long var32 = var18 + var28;
      long var34 = 0L;
      var34 += ((long)var0[0] & 4294967295L) + var32;
      var1[0] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[1] & 4294967295L) + var16 - var18 + var20;
      var1[1] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[2] & 4294967295L) - var12 - var20 + var22;
      var1[2] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[3] & 4294967295L) - var22 + var24 + var32;
      var1[3] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[4] & 4294967295L) + var2 + var12 + var20 - var24 + var32;
      var1[4] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[5] & 4294967295L) - var2 + var20 + var22 + var26;
      var1[5] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[6] & 4294967295L) + var6 - var4 + var22 + var24;
      var1[6] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[7] & 4294967295L) + var2 + var8 - var6 + var24;
      var1[7] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[8] & 4294967295L) + var2 + var4 + var10 - var8;
      var1[8] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[9] & 4294967295L) + var6 - var10 + var26;
      var1[9] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[10] & 4294967295L) + var6 + var8 - var28 + var30;
      var1[10] = (int)var34;
      var34 >>= 32;
      var34 += ((long)var0[11] & 4294967295L) + var8 + var10 - var30;
      var1[11] = (int)var34;
      var34 >>= 32;
      ++var34;
      reduce32((int)var34, var1);
   }

   public static void reduce32(int var0, int[] var1) {
      long var2 = 0L;
      if (var0 != 0) {
         long var4 = (long)var0 & 4294967295L;
         var2 += ((long)var1[0] & 4294967295L) + var4;
         var1[0] = (int)var2;
         var2 >>= 32;
         var2 += ((long)var1[1] & 4294967295L) - var4;
         var1[1] = (int)var2;
         var2 >>= 32;
         if (var2 != 0L) {
            var2 += (long)var1[2] & 4294967295L;
            var1[2] = (int)var2;
            var2 >>= 32;
         }

         var2 += ((long)var1[3] & 4294967295L) + var4;
         var1[3] = (int)var2;
         var2 >>= 32;
         var2 += ((long)var1[4] & 4294967295L) + var4;
         var1[4] = (int)var2;
         var2 >>= 32;
      }

      if (var2 != 0L && Nat.incAt(12, var1, 5) != 0 || var1[11] == -1 && Nat.gte(12, var1, P)) {
         addPInvTo(var1);
      }

   }

   public static void square(int[] var0, int[] var1) {
      int[] var2 = Nat.create(24);
      Nat384.square(var0, var2);
      reduce(var2, var1);
   }

   public static void squareN(int[] var0, int var1, int[] var2) {
      int[] var3 = Nat.create(24);
      Nat384.square(var0, var3);
      reduce(var3, var2);

      while(true) {
         --var1;
         if (var1 <= 0) {
            return;
         }

         Nat384.square(var2, var3);
         reduce(var3, var2);
      }
   }

   public static void subtract(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat.sub(12, var0, var1, var2);
      if (var3 != 0) {
         subPInvFrom(var2);
      }

   }

   public static void subtractExt(int[] var0, int[] var1, int[] var2) {
      int var3 = Nat.sub(24, var0, var1, var2);
      if (var3 != 0 && Nat.subFrom(PExtInv.length, PExtInv, var2) != 0) {
         Nat.decAt(24, var2, PExtInv.length);
      }

   }

   public static void twice(int[] var0, int[] var1) {
      int var2 = Nat.shiftUpBit(12, var0, 0, var1);
      if (var2 != 0 || var1[11] == -1 && Nat.gte(12, var1, P)) {
         addPInvTo(var1);
      }

   }

   private static void addPInvTo(int[] var0) {
      long var1 = ((long)var0[0] & 4294967295L) + 1L;
      var0[0] = (int)var1;
      var1 >>= 32;
      var1 += ((long)var0[1] & 4294967295L) - 1L;
      var0[1] = (int)var1;
      var1 >>= 32;
      if (var1 != 0L) {
         var1 += (long)var0[2] & 4294967295L;
         var0[2] = (int)var1;
         var1 >>= 32;
      }

      var1 += ((long)var0[3] & 4294967295L) + 1L;
      var0[3] = (int)var1;
      var1 >>= 32;
      var1 += ((long)var0[4] & 4294967295L) + 1L;
      var0[4] = (int)var1;
      var1 >>= 32;
      if (var1 != 0L) {
         Nat.incAt(12, var0, 5);
      }

   }

   private static void subPInvFrom(int[] var0) {
      long var1 = ((long)var0[0] & 4294967295L) - 1L;
      var0[0] = (int)var1;
      var1 >>= 32;
      var1 += ((long)var0[1] & 4294967295L) + 1L;
      var0[1] = (int)var1;
      var1 >>= 32;
      if (var1 != 0L) {
         var1 += (long)var0[2] & 4294967295L;
         var0[2] = (int)var1;
         var1 >>= 32;
      }

      var1 += ((long)var0[3] & 4294967295L) - 1L;
      var0[3] = (int)var1;
      var1 >>= 32;
      var1 += ((long)var0[4] & 4294967295L) - 1L;
      var0[4] = (int)var1;
      var1 >>= 32;
      if (var1 != 0L) {
         Nat.decAt(12, var0, 5);
      }

   }
}
