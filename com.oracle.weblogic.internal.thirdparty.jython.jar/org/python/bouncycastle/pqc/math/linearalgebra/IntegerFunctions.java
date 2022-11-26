package org.python.bouncycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class IntegerFunctions {
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private static final BigInteger FOUR = BigInteger.valueOf(4L);
   private static final int[] SMALL_PRIMES = new int[]{3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41};
   private static final long SMALL_PRIME_PRODUCT = 152125131763605L;
   private static SecureRandom sr = null;
   private static final int[] jacobiTable = new int[]{0, 1, 0, -1, 0, -1, 0, 1};

   private IntegerFunctions() {
   }

   public static int jacobi(BigInteger var0, BigInteger var1) {
      long var2 = 1L;
      var2 = 1L;
      BigInteger var4;
      if (var1.equals(ZERO)) {
         var4 = var0.abs();
         return var4.equals(ONE) ? 1 : 0;
      } else if (!var0.testBit(0) && !var1.testBit(0)) {
         return 0;
      } else {
         var4 = var0;
         BigInteger var5 = var1;
         if (var1.signum() == -1) {
            var5 = var1.negate();
            if (var0.signum() == -1) {
               var2 = -1L;
            }
         }

         BigInteger var6;
         for(var6 = ZERO; !var5.testBit(0); var5 = var5.divide(TWO)) {
            var6 = var6.add(ONE);
         }

         if (var6.testBit(0)) {
            var2 *= (long)jacobiTable[var0.intValue() & 7];
         }

         if (var0.signum() < 0) {
            if (var5.testBit(1)) {
               var2 = -var2;
            }

            var4 = var0.negate();
         }

         for(; var4.signum() != 0; var4 = var4.subtract(var5)) {
            for(var6 = ZERO; !var4.testBit(0); var4 = var4.divide(TWO)) {
               var6 = var6.add(ONE);
            }

            if (var6.testBit(0)) {
               var2 *= (long)jacobiTable[var5.intValue() & 7];
            }

            if (var4.compareTo(var5) < 0) {
               BigInteger var7 = var4;
               var4 = var5;
               var5 = var7;
               if (var4.testBit(1) && var7.testBit(1)) {
                  var2 = -var2;
               }
            }
         }

         return var5.equals(ONE) ? (int)var2 : 0;
      }
   }

   public static BigInteger ressol(BigInteger var0, BigInteger var1) throws IllegalArgumentException {
      BigInteger var2 = null;
      if (var0.compareTo(ZERO) < 0) {
         var0 = var0.add(var1);
      }

      if (var0.equals(ZERO)) {
         return ZERO;
      } else if (var1.equals(TWO)) {
         return var0;
      } else if (var1.testBit(0) && var1.testBit(1)) {
         if (jacobi(var0, var1) == 1) {
            var2 = var1.add(ONE);
            var2 = var2.shiftRight(2);
            return var0.modPow(var2, var1);
         } else {
            throw new IllegalArgumentException("No quadratic residue: " + var0 + ", " + var1);
         }
      } else {
         long var3 = 0L;
         BigInteger var5 = var1.subtract(ONE);

         long var6;
         for(var6 = 0L; !var5.testBit(0); var5 = var5.shiftRight(1)) {
            ++var6;
         }

         var5 = var5.subtract(ONE);
         var5 = var5.shiftRight(1);
         BigInteger var8 = var0.modPow(var5, var1);
         BigInteger var9 = var8.multiply(var8).remainder(var1);
         var9 = var9.multiply(var0).remainder(var1);
         var8 = var8.multiply(var0).remainder(var1);
         if (var9.equals(ONE)) {
            return var8;
         } else {
            BigInteger var10;
            for(var10 = TWO; jacobi(var10, var1) == 1; var10 = var10.add(ONE)) {
            }

            var2 = var5.multiply(TWO);
            var2 = var2.add(ONE);

            for(BigInteger var11 = var10.modPow(var2, var1); var9.compareTo(ONE) == 1; var9 = var9.multiply(var11).mod(var1)) {
               var5 = var9;
               var3 = var6;

               for(var6 = 0L; !var5.equals(ONE); ++var6) {
                  var5 = var5.multiply(var5).mod(var1);
               }

               var3 -= var6;
               if (var3 == 0L) {
                  throw new IllegalArgumentException("No quadratic residue: " + var0 + ", " + var1);
               }

               var2 = ONE;

               for(long var12 = 0L; var12 < var3 - 1L; ++var12) {
                  var2 = var2.shiftLeft(1);
               }

               var11 = var11.modPow(var2, var1);
               var8 = var8.multiply(var11).remainder(var1);
               var11 = var11.multiply(var11).remainder(var1);
            }

            return var8;
         }
      }
   }

   public static int gcd(int var0, int var1) {
      return BigInteger.valueOf((long)var0).gcd(BigInteger.valueOf((long)var1)).intValue();
   }

   public static int[] extGCD(int var0, int var1) {
      BigInteger var2 = BigInteger.valueOf((long)var0);
      BigInteger var3 = BigInteger.valueOf((long)var1);
      BigInteger[] var4 = extgcd(var2, var3);
      int[] var5 = new int[]{var4[0].intValue(), var4[1].intValue(), var4[2].intValue()};
      return var5;
   }

   public static BigInteger divideAndRound(BigInteger var0, BigInteger var1) {
      if (var0.signum() < 0) {
         return divideAndRound(var0.negate(), var1).negate();
      } else {
         return var1.signum() < 0 ? divideAndRound(var0, var1.negate()).negate() : var0.shiftLeft(1).add(var1).divide(var1.shiftLeft(1));
      }
   }

   public static BigInteger[] divideAndRound(BigInteger[] var0, BigInteger var1) {
      BigInteger[] var2 = new BigInteger[var0.length];

      for(int var3 = 0; var3 < var0.length; ++var3) {
         var2[var3] = divideAndRound(var0[var3], var1);
      }

      return var2;
   }

   public static int ceilLog(BigInteger var0) {
      int var1 = 0;

      for(BigInteger var2 = ONE; var2.compareTo(var0) < 0; var2 = var2.shiftLeft(1)) {
         ++var1;
      }

      return var1;
   }

   public static int ceilLog(int var0) {
      int var1 = 0;

      for(int var2 = 1; var2 < var0; ++var1) {
         var2 <<= 1;
      }

      return var1;
   }

   public static int ceilLog256(int var0) {
      if (var0 == 0) {
         return 1;
      } else {
         int var1;
         if (var0 < 0) {
            var1 = -var0;
         } else {
            var1 = var0;
         }

         int var2;
         for(var2 = 0; var1 > 0; var1 >>>= 8) {
            ++var2;
         }

         return var2;
      }
   }

   public static int ceilLog256(long var0) {
      if (var0 == 0L) {
         return 1;
      } else {
         long var2;
         if (var0 < 0L) {
            var2 = -var0;
         } else {
            var2 = var0;
         }

         int var4;
         for(var4 = 0; var2 > 0L; var2 >>>= 8) {
            ++var4;
         }

         return var4;
      }
   }

   public static int floorLog(BigInteger var0) {
      int var1 = -1;

      for(BigInteger var2 = ONE; var2.compareTo(var0) <= 0; var2 = var2.shiftLeft(1)) {
         ++var1;
      }

      return var1;
   }

   public static int floorLog(int var0) {
      int var1 = 0;
      if (var0 <= 0) {
         return -1;
      } else {
         for(int var2 = var0 >>> 1; var2 > 0; var2 >>>= 1) {
            ++var1;
         }

         return var1;
      }
   }

   public static int maxPower(int var0) {
      int var1 = 0;
      if (var0 != 0) {
         for(int var2 = 1; (var0 & var2) == 0; var2 <<= 1) {
            ++var1;
         }
      }

      return var1;
   }

   public static int bitCount(int var0) {
      int var1;
      for(var1 = 0; var0 != 0; var0 >>>= 1) {
         var1 += var0 & 1;
      }

      return var1;
   }

   public static int order(int var0, int var1) {
      int var2 = var0 % var1;
      int var3 = 1;
      if (var2 == 0) {
         throw new IllegalArgumentException(var0 + " is not an element of Z/(" + var1 + "Z)^*; it is not meaningful to compute its order.");
      } else {
         for(; var2 != 1; ++var3) {
            var2 *= var0;
            var2 %= var1;
            if (var2 < 0) {
               var2 += var1;
            }
         }

         return var3;
      }
   }

   public static BigInteger reduceInto(BigInteger var0, BigInteger var1, BigInteger var2) {
      return var0.subtract(var1).mod(var2.subtract(var1)).add(var1);
   }

   public static int pow(int var0, int var1) {
      int var2;
      for(var2 = 1; var1 > 0; var1 >>>= 1) {
         if ((var1 & 1) == 1) {
            var2 *= var0;
         }

         var0 *= var0;
      }

      return var2;
   }

   public static long pow(long var0, int var2) {
      long var3;
      for(var3 = 1L; var2 > 0; var2 >>>= 1) {
         if ((var2 & 1) == 1) {
            var3 *= var0;
         }

         var0 *= var0;
      }

      return var3;
   }

   public static int modPow(int var0, int var1, int var2) {
      if (var2 > 0 && var2 * var2 <= Integer.MAX_VALUE && var1 >= 0) {
         int var3 = 1;

         for(var0 = (var0 % var2 + var2) % var2; var1 > 0; var1 >>>= 1) {
            if ((var1 & 1) == 1) {
               var3 = var3 * var0 % var2;
            }

            var0 = var0 * var0 % var2;
         }

         return var3;
      } else {
         return 0;
      }
   }

   public static BigInteger[] extgcd(BigInteger var0, BigInteger var1) {
      BigInteger var2 = ONE;
      BigInteger var3 = ZERO;
      BigInteger var4 = var0;
      if (var1.signum() != 0) {
         BigInteger var5 = ZERO;

         BigInteger var9;
         for(BigInteger var6 = var1; var6.signum() != 0; var6 = var9) {
            BigInteger[] var7 = var4.divideAndRemainder(var6);
            BigInteger var8 = var7[0];
            var9 = var7[1];
            BigInteger var10 = var2.subtract(var8.multiply(var5));
            var2 = var5;
            var4 = var6;
            var5 = var10;
         }

         var3 = var4.subtract(var0.multiply(var2)).divide(var1);
      }

      return new BigInteger[]{var4, var2, var3};
   }

   public static BigInteger leastCommonMultiple(BigInteger[] var0) {
      int var1 = var0.length;
      BigInteger var2 = var0[0];

      for(int var3 = 1; var3 < var1; ++var3) {
         BigInteger var4 = var2.gcd(var0[var3]);
         var2 = var2.multiply(var0[var3]).divide(var4);
      }

      return var2;
   }

   public static long mod(long var0, long var2) {
      long var4 = var0 % var2;
      if (var4 < 0L) {
         var4 += var2;
      }

      return var4;
   }

   public static int modInverse(int var0, int var1) {
      return BigInteger.valueOf((long)var0).modInverse(BigInteger.valueOf((long)var1)).intValue();
   }

   public static long modInverse(long var0, long var2) {
      return BigInteger.valueOf(var0).modInverse(BigInteger.valueOf(var2)).longValue();
   }

   public static int isPower(int var0, int var1) {
      if (var0 <= 0) {
         return -1;
      } else {
         int var2 = 0;

         for(int var3 = var0; var3 > 1; ++var2) {
            if (var3 % var1 != 0) {
               return -1;
            }

            var3 /= var1;
         }

         return var2;
      }
   }

   public static int leastDiv(int var0) {
      if (var0 < 0) {
         var0 = -var0;
      }

      if (var0 == 0) {
         return 1;
      } else if ((var0 & 1) == 0) {
         return 2;
      } else {
         for(int var1 = 3; var1 <= var0 / var1; var1 += 2) {
            if (var0 % var1 == 0) {
               return var1;
            }
         }

         return var0;
      }
   }

   public static boolean isPrime(int var0) {
      if (var0 < 2) {
         return false;
      } else if (var0 == 2) {
         return true;
      } else if ((var0 & 1) == 0) {
         return false;
      } else {
         if (var0 < 42) {
            for(int var1 = 0; var1 < SMALL_PRIMES.length; ++var1) {
               if (var0 == SMALL_PRIMES[var1]) {
                  return true;
               }
            }
         }

         return var0 % 3 != 0 && var0 % 5 != 0 && var0 % 7 != 0 && var0 % 11 != 0 && var0 % 13 != 0 && var0 % 17 != 0 && var0 % 19 != 0 && var0 % 23 != 0 && var0 % 29 != 0 && var0 % 31 != 0 && var0 % 37 != 0 && var0 % 41 != 0 ? BigInteger.valueOf((long)var0).isProbablePrime(20) : false;
      }
   }

   public static boolean passesSmallPrimeTest(BigInteger var0) {
      int[] var1 = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499};

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var0.mod(BigInteger.valueOf((long)var1[var2])).equals(ZERO)) {
            return false;
         }
      }

      return true;
   }

   public static int nextSmallerPrime(int var0) {
      if (var0 <= 2) {
         return 1;
      } else if (var0 == 3) {
         return 2;
      } else {
         if ((var0 & 1) == 0) {
            --var0;
         } else {
            var0 -= 2;
         }

         while(var0 > 3 & !isPrime(var0)) {
            var0 -= 2;
         }

         return var0;
      }
   }

   public static BigInteger nextProbablePrime(BigInteger var0, int var1) {
      if (var0.signum() >= 0 && var0.signum() != 0 && !var0.equals(ONE)) {
         BigInteger var2 = var0.add(ONE);
         if (!var2.testBit(0)) {
            var2 = var2.add(ONE);
         }

         while(true) {
            while(true) {
               if (var2.bitLength() > 6) {
                  long var3 = var2.remainder(BigInteger.valueOf(152125131763605L)).longValue();
                  if (var3 % 3L == 0L || var3 % 5L == 0L || var3 % 7L == 0L || var3 % 11L == 0L || var3 % 13L == 0L || var3 % 17L == 0L || var3 % 19L == 0L || var3 % 23L == 0L || var3 % 29L == 0L || var3 % 31L == 0L || var3 % 37L == 0L || var3 % 41L == 0L) {
                     var2 = var2.add(TWO);
                     continue;
                  }
               }

               if (var2.bitLength() < 4) {
                  return var2;
               }

               if (var2.isProbablePrime(var1)) {
                  return var2;
               }

               var2 = var2.add(TWO);
            }
         }
      } else {
         return TWO;
      }
   }

   public static BigInteger nextProbablePrime(BigInteger var0) {
      return nextProbablePrime(var0, 20);
   }

   public static BigInteger nextPrime(long var0) {
      boolean var2 = false;
      long var3 = 0L;
      if (var0 <= 1L) {
         return BigInteger.valueOf(2L);
      } else if (var0 == 2L) {
         return BigInteger.valueOf(3L);
      } else {
         for(long var5 = var0 + 1L + (var0 & 1L); var5 <= var0 << 1 && !var2; var5 += 2L) {
            for(long var7 = 3L; var7 <= var5 >> 1 && !var2; var7 += 2L) {
               if (var5 % var7 == 0L) {
                  var2 = true;
               }
            }

            if (var2) {
               var2 = false;
            } else {
               var3 = var5;
               var2 = true;
            }
         }

         return BigInteger.valueOf(var3);
      }
   }

   public static BigInteger binomial(int var0, int var1) {
      BigInteger var2 = ONE;
      if (var0 == 0) {
         return var1 == 0 ? var2 : ZERO;
      } else {
         if (var1 > var0 >>> 1) {
            var1 = var0 - var1;
         }

         for(int var3 = 1; var3 <= var1; ++var3) {
            var2 = var2.multiply(BigInteger.valueOf((long)(var0 - (var3 - 1)))).divide(BigInteger.valueOf((long)var3));
         }

         return var2;
      }
   }

   public static BigInteger randomize(BigInteger var0) {
      if (sr == null) {
         sr = new SecureRandom();
      }

      return randomize(var0, sr);
   }

   public static BigInteger randomize(BigInteger var0, SecureRandom var1) {
      int var2 = var0.bitLength();
      BigInteger var3 = BigInteger.valueOf(0L);
      if (var1 == null) {
         var1 = sr != null ? sr : new SecureRandom();
      }

      for(int var4 = 0; var4 < 20; ++var4) {
         var3 = new BigInteger(var2, var1);
         if (var3.compareTo(var0) < 0) {
            return var3;
         }
      }

      return var3.mod(var0);
   }

   public static BigInteger squareRoot(BigInteger var0) {
      if (var0.compareTo(ZERO) < 0) {
         throw new ArithmeticException("cannot extract root of negative number" + var0 + ".");
      } else {
         int var1 = var0.bitLength();
         BigInteger var2 = ZERO;
         BigInteger var3 = ZERO;
         if ((var1 & 1) != 0) {
            var2 = var2.add(ONE);
            --var1;
         }

         while(var1 > 0) {
            var3 = var3.multiply(FOUR);
            --var1;
            int var10001 = var0.testBit(var1) ? 2 : 0;
            --var1;
            var3 = var3.add(BigInteger.valueOf((long)(var10001 + (var0.testBit(var1) ? 1 : 0))));
            BigInteger var4 = var2.multiply(FOUR).add(ONE);
            var2 = var2.multiply(TWO);
            if (var3.compareTo(var4) != -1) {
               var2 = var2.add(ONE);
               var3 = var3.subtract(var4);
            }
         }

         return var2;
      }
   }

   public static float intRoot(int var0, int var1) {
      float var2 = (float)(var0 / var1);
      float var3 = 0.0F;

      float var5;
      for(int var4 = 0; (double)Math.abs(var3 - var2) > 1.0E-4; var2 -= (var5 - (float)var0) / ((float)var1 * floatPow(var2, var1 - 1))) {
         for(var5 = floatPow(var2, var1); Float.isInfinite(var5); var5 = floatPow(var2, var1)) {
            var2 = (var2 + var3) / 2.0F;
         }

         ++var4;
         var3 = var2;
      }

      return var2;
   }

   public static float floatPow(float var0, int var1) {
      float var2;
      for(var2 = 1.0F; var1 > 0; --var1) {
         var2 *= var0;
      }

      return var2;
   }

   /** @deprecated */
   public static double log(double var0) {
      if (var0 > 0.0 && var0 < 1.0) {
         double var2 = 1.0 / var0;
         double var4 = -log(var2);
         return var4;
      } else {
         int var6 = 0;
         double var7 = 1.0;

         for(double var9 = var0; var9 > 2.0; var7 *= 2.0) {
            var9 /= 2.0;
            ++var6;
         }

         double var11 = var0 / var7;
         var11 = logBKM(var11);
         return (double)var6 + var11;
      }
   }

   /** @deprecated */
   public static double log(long var0) {
      int var2 = floorLog(BigInteger.valueOf(var0));
      long var3 = (long)(1 << var2);
      double var5 = (double)var0 / (double)var3;
      var5 = logBKM(var5);
      return (double)var2 + var5;
   }

   /** @deprecated */
   private static double logBKM(double var0) {
      double[] var2 = new double[]{1.0, 0.5849625007211562, 0.32192809488736235, 0.16992500144231237, 0.0874628412503394, 0.044394119358453436, 0.02236781302845451, 0.01122725542325412, 0.005624549193878107, 0.0028150156070540383, 0.0014081943928083889, 7.042690112466433E-4, 3.5217748030102726E-4, 1.7609948644250602E-4, 8.80524301221769E-5, 4.4026886827316716E-5, 2.2013611360340496E-5, 1.1006847667481442E-5, 5.503434330648604E-6, 2.751719789561283E-6, 1.375860550841138E-6, 6.879304394358497E-7, 3.4396526072176454E-7, 1.7198264061184464E-7, 8.599132286866321E-8, 4.299566207501687E-8, 2.1497831197679756E-8, 1.0748915638882709E-8, 5.374457829452062E-9, 2.687228917228708E-9, 1.3436144592400231E-9, 6.718072297764289E-10, 3.3590361492731876E-10, 1.6795180747343547E-10, 8.397590373916176E-11, 4.1987951870191886E-11, 2.0993975935248694E-11, 1.0496987967662534E-11, 5.2484939838408146E-12, 2.624246991922794E-12, 1.3121234959619935E-12, 6.56061747981146E-13, 3.2803087399061026E-13, 1.6401543699531447E-13, 8.200771849765956E-14, 4.1003859248830365E-14, 2.0501929624415328E-14, 1.02509648122077E-14, 5.1254824061038595E-15, 2.5627412030519317E-15, 1.2813706015259665E-15, 6.406853007629834E-16, 3.203426503814917E-16, 1.6017132519074588E-16, 8.008566259537294E-17, 4.004283129768647E-17, 2.0021415648843235E-17, 1.0010707824421618E-17, 5.005353912210809E-18, 2.5026769561054044E-18, 1.2513384780527022E-18, 6.256692390263511E-19, 3.1283461951317555E-19, 1.5641730975658778E-19, 7.820865487829389E-20, 3.9104327439146944E-20, 1.9552163719573472E-20, 9.776081859786736E-21, 4.888040929893368E-21, 2.444020464946684E-21, 1.222010232473342E-21, 6.11005116236671E-22, 3.055025581183355E-22, 1.5275127905916775E-22, 7.637563952958387E-23, 3.818781976479194E-23, 1.909390988239597E-23, 9.546954941197984E-24, 4.773477470598992E-24, 2.386738735299496E-24, 1.193369367649748E-24, 5.96684683824874E-25, 2.98342341912437E-25, 1.491711709562185E-25, 7.458558547810925E-26, 3.7292792739054626E-26, 1.8646396369527313E-26, 9.323198184763657E-27, 4.661599092381828E-27, 2.330799546190914E-27, 1.165399773095457E-27, 5.826998865477285E-28, 2.9134994327386427E-28, 1.4567497163693213E-28, 7.283748581846607E-29, 3.6418742909233034E-29, 1.8209371454616517E-29, 9.104685727308258E-30, 4.552342863654129E-30, 2.2761714318270646E-30};
      byte var3 = 53;
      double var4 = 1.0;
      double var6 = 0.0;
      double var8 = 1.0;

      for(int var10 = 0; var10 < var3; ++var10) {
         double var11 = var4 + var4 * var8;
         if (var11 <= var0) {
            var4 = var11;
            var6 += var2[var10];
         }

         var8 *= 0.5;
      }

      return var6;
   }

   public static boolean isIncreasing(int[] var0) {
      for(int var1 = 1; var1 < var0.length; ++var1) {
         if (var0[var1 - 1] >= var0[var1]) {
            System.out.println("a[" + (var1 - 1) + "] = " + var0[var1 - 1] + " >= " + var0[var1] + " = a[" + var1 + "]");
            return false;
         }
      }

      return true;
   }

   public static byte[] integerToOctets(BigInteger var0) {
      byte[] var1 = var0.abs().toByteArray();
      if ((var0.bitLength() & 7) != 0) {
         return var1;
      } else {
         byte[] var2 = new byte[var0.bitLength() >> 3];
         System.arraycopy(var1, 1, var2, 0, var2.length);
         return var2;
      }
   }

   public static BigInteger octetsToInteger(byte[] var0, int var1, int var2) {
      byte[] var3 = new byte[var2 + 1];
      var3[0] = 0;
      System.arraycopy(var0, var1, var3, 1, var2);
      return new BigInteger(var3);
   }

   public static BigInteger octetsToInteger(byte[] var0) {
      return octetsToInteger(var0, 0, var0.length);
   }
}
