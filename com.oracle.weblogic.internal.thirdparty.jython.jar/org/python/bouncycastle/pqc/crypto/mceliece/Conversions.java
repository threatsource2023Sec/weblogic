package org.python.bouncycastle.pqc.crypto.mceliece;

import java.math.BigInteger;
import org.python.bouncycastle.pqc.math.linearalgebra.BigIntUtils;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Vector;
import org.python.bouncycastle.pqc.math.linearalgebra.IntegerFunctions;

final class Conversions {
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private static final BigInteger ONE = BigInteger.valueOf(1L);

   private Conversions() {
   }

   public static GF2Vector encode(int var0, int var1, byte[] var2) {
      if (var0 < var1) {
         throw new IllegalArgumentException("n < t");
      } else {
         BigInteger var3 = IntegerFunctions.binomial(var0, var1);
         BigInteger var4 = new BigInteger(1, var2);
         if (var4.compareTo(var3) >= 0) {
            throw new IllegalArgumentException("Encoded number too large.");
         } else {
            GF2Vector var5 = new GF2Vector(var0);
            int var6 = var0;
            int var7 = var1;

            for(int var8 = 0; var8 < var0; ++var8) {
               var3 = var3.multiply(BigInteger.valueOf((long)(var6 - var7))).divide(BigInteger.valueOf((long)var6));
               --var6;
               if (var3.compareTo(var4) <= 0) {
                  var5.setBit(var8);
                  var4 = var4.subtract(var3);
                  --var7;
                  if (var6 == var7) {
                     var3 = ONE;
                  } else {
                     var3 = var3.multiply(BigInteger.valueOf((long)(var7 + 1))).divide(BigInteger.valueOf((long)(var6 - var7)));
                  }
               }
            }

            return var5;
         }
      }
   }

   public static byte[] decode(int var0, int var1, GF2Vector var2) {
      if (var2.getLength() == var0 && var2.getHammingWeight() == var1) {
         int[] var3 = var2.getVecArray();
         BigInteger var4 = IntegerFunctions.binomial(var0, var1);
         BigInteger var5 = ZERO;
         int var6 = var0;
         int var7 = var1;

         for(int var8 = 0; var8 < var0; ++var8) {
            var4 = var4.multiply(BigInteger.valueOf((long)(var6 - var7))).divide(BigInteger.valueOf((long)var6));
            --var6;
            int var9 = var8 >> 5;
            int var10 = var3[var9] & 1 << (var8 & 31);
            if (var10 != 0) {
               var5 = var5.add(var4);
               --var7;
               if (var6 == var7) {
                  var4 = ONE;
               } else {
                  var4 = var4.multiply(BigInteger.valueOf((long)(var7 + 1))).divide(BigInteger.valueOf((long)(var6 - var7)));
               }
            }
         }

         return BigIntUtils.toMinimalByteArray(var5);
      } else {
         throw new IllegalArgumentException("vector has wrong length or hamming weight");
      }
   }

   public static byte[] signConversion(int var0, int var1, byte[] var2) {
      if (var0 < var1) {
         throw new IllegalArgumentException("n < t");
      } else {
         BigInteger var3 = IntegerFunctions.binomial(var0, var1);
         int var4 = var3.bitLength() - 1;
         int var5 = var4 >> 3;
         int var6 = var4 & 7;
         if (var6 == 0) {
            --var5;
            var6 = 8;
         }

         int var7 = var0 >> 3;
         int var8 = var0 & 7;
         if (var8 == 0) {
            --var7;
            var8 = 8;
         }

         byte[] var9 = new byte[var7 + 1];
         int var10;
         if (var2.length < var9.length) {
            System.arraycopy(var2, 0, var9, 0, var2.length);

            for(var10 = var2.length; var10 < var9.length; ++var10) {
               var9[var10] = 0;
            }
         } else {
            System.arraycopy(var2, 0, var9, 0, var7);
            var10 = (1 << var8) - 1;
            var9[var7] = (byte)(var10 & var2[var7]);
         }

         BigInteger var17 = ZERO;
         int var11 = var0;
         int var12 = var1;

         int var15;
         for(int var13 = 0; var13 < var0; ++var13) {
            var3 = var3.multiply(new BigInteger(Integer.toString(var11 - var12))).divide(new BigInteger(Integer.toString(var11)));
            --var11;
            int var14 = var13 >>> 3;
            var15 = var13 & 7;
            var15 = 1 << var15;
            byte var16 = (byte)(var15 & var9[var14]);
            if (var16 != 0) {
               var17 = var17.add(var3);
               --var12;
               if (var11 == var12) {
                  var3 = ONE;
               } else {
                  var3 = var3.multiply(new BigInteger(Integer.toString(var12 + 1))).divide(new BigInteger(Integer.toString(var11 - var12)));
               }
            }
         }

         byte[] var18 = new byte[var5 + 1];
         byte[] var19 = var17.toByteArray();
         if (var19.length < var18.length) {
            System.arraycopy(var19, 0, var18, 0, var19.length);

            for(var15 = var19.length; var15 < var18.length; ++var15) {
               var18[var15] = 0;
            }
         } else {
            System.arraycopy(var19, 0, var18, 0, var5);
            var18[var5] = (byte)((1 << var6) - 1 & var19[var5]);
         }

         return var18;
      }
   }
}
