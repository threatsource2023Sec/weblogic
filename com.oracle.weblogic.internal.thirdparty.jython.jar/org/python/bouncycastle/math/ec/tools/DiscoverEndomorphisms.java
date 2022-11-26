package org.python.bouncycastle.math.ec.tools;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.math.ec.ECAlgorithms;
import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.BigIntegers;

public class DiscoverEndomorphisms {
   private static final int radix = 16;

   public static void main(String[] var0) {
      if (var0.length < 1) {
         System.err.println("Expected a list of curve names as arguments");
      } else {
         for(int var1 = 0; var1 < var0.length; ++var1) {
            discoverEndomorphisms(var0[var1]);
         }

      }
   }

   public static void discoverEndomorphisms(X9ECParameters var0) {
      if (var0 == null) {
         throw new NullPointerException("x9");
      } else {
         ECCurve var1 = var0.getCurve();
         if (ECAlgorithms.isFpCurve(var1)) {
            BigInteger var2 = var1.getField().getCharacteristic();
            if (var1.getA().isZero() && var2.mod(ECConstants.THREE).equals(ECConstants.ONE)) {
               System.out.println("Curve has a 'GLV Type B' endomorphism with these parameters:");
               printGLVTypeBParameters(var0);
            }
         }

      }
   }

   private static void discoverEndomorphisms(String var0) {
      X9ECParameters var1 = ECNamedCurveTable.getByName(var0);
      if (var1 == null) {
         System.err.println("Unknown curve: " + var0);
      } else {
         ECCurve var2 = var1.getCurve();
         if (ECAlgorithms.isFpCurve(var2)) {
            BigInteger var3 = var2.getField().getCharacteristic();
            if (var2.getA().isZero() && var3.mod(ECConstants.THREE).equals(ECConstants.ONE)) {
               System.out.println("Curve '" + var0 + "' has a 'GLV Type B' endomorphism with these parameters:");
               printGLVTypeBParameters(var1);
            }
         }

      }
   }

   private static void printGLVTypeBParameters(X9ECParameters var0) {
      BigInteger[] var1 = solveQuadraticEquation(var0.getN(), ECConstants.ONE, ECConstants.ONE);
      ECFieldElement[] var2 = findBetaValues(var0.getCurve());
      printGLVTypeBParameters(var0, var1[0], var2);
      System.out.println("OR");
      printGLVTypeBParameters(var0, var1[1], var2);
   }

   private static void printGLVTypeBParameters(X9ECParameters var0, BigInteger var1, ECFieldElement[] var2) {
      ECPoint var3 = var0.getG().normalize();
      ECPoint var4 = var3.multiply(var1).normalize();
      if (!var3.getYCoord().equals(var4.getYCoord())) {
         throw new IllegalStateException("Derivation of GLV Type B parameters failed unexpectedly");
      } else {
         ECFieldElement var5 = var2[0];
         if (!var3.getXCoord().multiply(var5).equals(var4.getXCoord())) {
            var5 = var2[1];
            if (!var3.getXCoord().multiply(var5).equals(var4.getXCoord())) {
               throw new IllegalStateException("Derivation of GLV Type B parameters failed unexpectedly");
            }
         }

         BigInteger var6 = var0.getN();
         BigInteger[] var7 = null;
         BigInteger[] var8 = null;
         BigInteger[] var9 = extEuclidGLV(var6, var1);
         var7 = new BigInteger[]{var9[2], var9[3].negate()};
         var8 = chooseShortest(new BigInteger[]{var9[0], var9[1].negate()}, new BigInteger[]{var9[4], var9[5].negate()});
         BigInteger var10;
         BigInteger var12;
         if (!isVectorBoundedBySqrt(var8, var6) && areRelativelyPrime(var7[0], var7[1])) {
            var10 = var7[0];
            BigInteger var11 = var7[1];
            var12 = var10.add(var11.multiply(var1)).divide(var6);
            BigInteger[] var13 = extEuclidBezout(new BigInteger[]{var12.abs(), var11.abs()});
            if (var13 != null) {
               BigInteger var14 = var13[0];
               BigInteger var15 = var13[1];
               if (var12.signum() < 0) {
                  var14 = var14.negate();
               }

               if (var11.signum() > 0) {
                  var15 = var15.negate();
               }

               BigInteger var16 = var12.multiply(var14).subtract(var11.multiply(var15));
               if (!var16.equals(ECConstants.ONE)) {
                  throw new IllegalStateException();
               }

               BigInteger var17 = var15.multiply(var6).subtract(var14.multiply(var1));
               BigInteger var18 = var14.negate();
               BigInteger var19 = var17.negate();
               BigInteger var20 = isqrt(var6.subtract(ECConstants.ONE)).add(ECConstants.ONE);
               BigInteger[] var21 = calculateRange(var18, var20, var11);
               BigInteger[] var22 = calculateRange(var19, var20, var10);
               BigInteger[] var23 = intersect(var21, var22);
               if (var23 != null) {
                  for(BigInteger var24 = var23[0]; var24.compareTo(var23[1]) <= 0; var24 = var24.add(ECConstants.ONE)) {
                     BigInteger[] var25 = new BigInteger[]{var17.add(var24.multiply(var10)), var14.add(var24.multiply(var11))};
                     if (isShorter(var25, var8)) {
                        var8 = var25;
                     }
                  }
               }
            }
         }

         var10 = var7[0].multiply(var8[1]).subtract(var7[1].multiply(var8[0]));
         int var26 = var6.bitLength() + 16 - (var6.bitLength() & 7);
         var12 = roundQuotient(var8[1].shiftLeft(var26), var10);
         BigInteger var27 = roundQuotient(var7[1].shiftLeft(var26), var10).negate();
         printProperty("Beta", var5.toBigInteger().toString(16));
         printProperty("Lambda", var1.toString(16));
         printProperty("v1", "{ " + var7[0].toString(16) + ", " + var7[1].toString(16) + " }");
         printProperty("v2", "{ " + var8[0].toString(16) + ", " + var8[1].toString(16) + " }");
         printProperty("d", var10.toString(16));
         printProperty("(OPT) g1", var12.toString(16));
         printProperty("(OPT) g2", var27.toString(16));
         printProperty("(OPT) bits", Integer.toString(var26));
      }
   }

   private static void printProperty(String var0, Object var1) {
      StringBuffer var2 = new StringBuffer("  ");
      var2.append(var0);

      while(var2.length() < 20) {
         var2.append(' ');
      }

      var2.append("= ");
      var2.append(var1.toString());
      System.out.println(var2.toString());
   }

   private static boolean areRelativelyPrime(BigInteger var0, BigInteger var1) {
      return var0.gcd(var1).equals(ECConstants.ONE);
   }

   private static BigInteger[] calculateRange(BigInteger var0, BigInteger var1, BigInteger var2) {
      BigInteger var3 = var0.subtract(var1).divide(var2);
      BigInteger var4 = var0.add(var1).divide(var2);
      return order(var3, var4);
   }

   private static BigInteger[] extEuclidBezout(BigInteger[] var0) {
      boolean var1 = var0[0].compareTo(var0[1]) < 0;
      if (var1) {
         swap(var0);
      }

      BigInteger var2 = var0[0];
      BigInteger var3 = var0[1];
      BigInteger var4 = ECConstants.ONE;
      BigInteger var5 = ECConstants.ZERO;
      BigInteger var6 = ECConstants.ZERO;

      BigInteger var7;
      BigInteger[] var8;
      BigInteger var12;
      for(var7 = ECConstants.ONE; var3.compareTo(ECConstants.ONE) > 0; var7 = var12) {
         var8 = var2.divideAndRemainder(var3);
         BigInteger var9 = var8[0];
         BigInteger var10 = var8[1];
         BigInteger var11 = var4.subtract(var9.multiply(var5));
         var12 = var6.subtract(var9.multiply(var7));
         var2 = var3;
         var3 = var10;
         var4 = var5;
         var5 = var11;
         var6 = var7;
      }

      if (var3.signum() <= 0) {
         return null;
      } else {
         var8 = new BigInteger[]{var5, var7};
         if (var1) {
            swap(var8);
         }

         return var8;
      }
   }

   private static BigInteger[] extEuclidGLV(BigInteger var0, BigInteger var1) {
      BigInteger var2 = var0;
      BigInteger var3 = var1;
      BigInteger var4 = ECConstants.ZERO;
      BigInteger var5 = ECConstants.ONE;

      while(true) {
         BigInteger[] var6 = var2.divideAndRemainder(var3);
         BigInteger var7 = var6[0];
         BigInteger var8 = var6[1];
         BigInteger var9 = var4.subtract(var7.multiply(var5));
         if (isLessThanSqrt(var3, var0)) {
            return new BigInteger[]{var2, var4, var3, var5, var8, var9};
         }

         var2 = var3;
         var3 = var8;
         var4 = var5;
         var5 = var9;
      }
   }

   private static BigInteger[] chooseShortest(BigInteger[] var0, BigInteger[] var1) {
      return isShorter(var0, var1) ? var0 : var1;
   }

   private static BigInteger[] intersect(BigInteger[] var0, BigInteger[] var1) {
      BigInteger var2 = var0[0].max(var1[0]);
      BigInteger var3 = var0[1].min(var1[1]);
      return var2.compareTo(var3) > 0 ? null : new BigInteger[]{var2, var3};
   }

   private static boolean isLessThanSqrt(BigInteger var0, BigInteger var1) {
      var0 = var0.abs();
      var1 = var1.abs();
      int var2 = var1.bitLength();
      int var3 = var0.bitLength() * 2;
      int var4 = var3 - 1;
      return var4 <= var2 && (var3 < var2 || var0.multiply(var0).compareTo(var1) < 0);
   }

   private static boolean isShorter(BigInteger[] var0, BigInteger[] var1) {
      BigInteger var2 = var0[0].abs();
      BigInteger var3 = var0[1].abs();
      BigInteger var4 = var1[0].abs();
      BigInteger var5 = var1[1].abs();
      boolean var6 = var2.compareTo(var4) < 0;
      boolean var7 = var3.compareTo(var5) < 0;
      if (var6 == var7) {
         return var6;
      } else {
         BigInteger var8 = var2.multiply(var2).add(var3.multiply(var3));
         BigInteger var9 = var4.multiply(var4).add(var5.multiply(var5));
         return var8.compareTo(var9) < 0;
      }
   }

   private static boolean isVectorBoundedBySqrt(BigInteger[] var0, BigInteger var1) {
      BigInteger var2 = var0[0].abs().max(var0[1].abs());
      return isLessThanSqrt(var2, var1);
   }

   private static BigInteger[] order(BigInteger var0, BigInteger var1) {
      return var0.compareTo(var1) <= 0 ? new BigInteger[]{var0, var1} : new BigInteger[]{var1, var0};
   }

   private static BigInteger roundQuotient(BigInteger var0, BigInteger var1) {
      boolean var2 = var0.signum() != var1.signum();
      var0 = var0.abs();
      var1 = var1.abs();
      BigInteger var3 = var0.add(var1.shiftRight(1)).divide(var1);
      return var2 ? var3.negate() : var3;
   }

   private static BigInteger[] solveQuadraticEquation(BigInteger var0, BigInteger var1, BigInteger var2) {
      BigInteger var3 = var1.multiply(var1).subtract(var2.shiftLeft(2)).mod(var0);
      BigInteger var4 = (new ECFieldElement.Fp(var0, var3)).sqrt().toBigInteger();
      BigInteger var5 = var0.subtract(var4);
      if (var4.testBit(0)) {
         var5 = var5.add(var0);
      } else {
         var4 = var4.add(var0);
      }

      return new BigInteger[]{var4.shiftRight(1), var5.shiftRight(1)};
   }

   private static ECFieldElement[] findBetaValues(ECCurve var0) {
      BigInteger var1 = var0.getField().getCharacteristic();
      BigInteger var2 = var1.divide(ECConstants.THREE);
      SecureRandom var3 = new SecureRandom();

      BigInteger var5;
      do {
         BigInteger var4 = BigIntegers.createRandomInRange(ECConstants.TWO, var1.subtract(ECConstants.TWO), var3);
         var5 = var4.modPow(var2, var1);
      } while(var5.equals(ECConstants.ONE));

      ECFieldElement var6 = var0.fromBigInteger(var5);
      return new ECFieldElement[]{var6, var6.square()};
   }

   private static BigInteger isqrt(BigInteger var0) {
      BigInteger var1 = var0.shiftRight(var0.bitLength() / 2);

      while(true) {
         BigInteger var2 = var1.add(var0.divide(var1)).shiftRight(1);
         if (var2.equals(var1)) {
            return var2;
         }

         var1 = var2;
      }
   }

   private static void swap(BigInteger[] var0) {
      BigInteger var1 = var0[0];
      var0[0] = var0[1];
      var0[1] = var1;
   }
}
