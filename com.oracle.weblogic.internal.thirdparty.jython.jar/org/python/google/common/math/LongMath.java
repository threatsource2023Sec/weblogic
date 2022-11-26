package org.python.google.common.math;

import java.math.RoundingMode;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.common.primitives.UnsignedLongs;

@GwtCompatible(
   emulated = true
)
public final class LongMath {
   @VisibleForTesting
   static final long MAX_SIGNED_POWER_OF_TWO = 4611686018427387904L;
   @VisibleForTesting
   static final long MAX_POWER_OF_SQRT2_UNSIGNED = -5402926248376769404L;
   @VisibleForTesting
   static final byte[] maxLog10ForLeadingZeros = new byte[]{19, 18, 18, 18, 18, 17, 17, 17, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 13, 13, 13, 12, 12, 12, 12, 11, 11, 11, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0};
   @GwtIncompatible
   @VisibleForTesting
   static final long[] powersOf10 = new long[]{1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};
   @GwtIncompatible
   @VisibleForTesting
   static final long[] halfPowersOf10 = new long[]{3L, 31L, 316L, 3162L, 31622L, 316227L, 3162277L, 31622776L, 316227766L, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
   @VisibleForTesting
   static final long FLOOR_SQRT_MAX_LONG = 3037000499L;
   static final long[] factorials = new long[]{1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
   static final int[] biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, 169, 143, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
   @VisibleForTesting
   static final int[] biggestSimpleBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2642246, 86251, 11724, 3218, 1313, 684, 419, 287, 214, 169, 139, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
   private static final int SIEVE_30 = -545925251;
   private static final long[][] millerRabinBaseSets = new long[][]{{291830L, 126401071349994536L}, {885594168L, 725270293939359937L, 3569819667048198375L}, {273919523040L, 15L, 7363882082L, 992620450144556L}, {47636622961200L, 2L, 2570940L, 211991001L, 3749873356L}, {7999252175582850L, 2L, 4130806001517L, 149795463772692060L, 186635894390467037L, 3967304179347715805L}, {585226005592931976L, 2L, 123635709730000L, 9233062284813009L, 43835965440333360L, 761179012939631437L, 1263739024124850375L}, {Long.MAX_VALUE, 2L, 325L, 9375L, 28178L, 450775L, 9780504L, 1795265022L}};

   @Beta
   public static long ceilingPowerOfTwo(long x) {
      MathPreconditions.checkPositive("x", x);
      if (x > 4611686018427387904L) {
         throw new ArithmeticException("ceilingPowerOfTwo(" + x + ") is not representable as a long");
      } else {
         return 1L << -Long.numberOfLeadingZeros(x - 1L);
      }
   }

   @Beta
   public static long floorPowerOfTwo(long x) {
      MathPreconditions.checkPositive("x", x);
      return 1L << 63 - Long.numberOfLeadingZeros(x);
   }

   public static boolean isPowerOfTwo(long x) {
      return x > 0L & (x & x - 1L) == 0L;
   }

   @VisibleForTesting
   static int lessThanBranchFree(long x, long y) {
      return (int)(~(~(x - y)) >>> 63);
   }

   public static int log2(long x, RoundingMode mode) {
      MathPreconditions.checkPositive("x", x);
      switch (mode) {
         case UNNECESSARY:
            MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
         case DOWN:
         case FLOOR:
            return 63 - Long.numberOfLeadingZeros(x);
         case UP:
         case CEILING:
            return 64 - Long.numberOfLeadingZeros(x - 1L);
         case HALF_DOWN:
         case HALF_UP:
         case HALF_EVEN:
            int leadingZeros = Long.numberOfLeadingZeros(x);
            long cmp = -5402926248376769404L >>> leadingZeros;
            int logFloor = 63 - leadingZeros;
            return logFloor + lessThanBranchFree(cmp, x);
         default:
            throw new AssertionError("impossible");
      }
   }

   @GwtIncompatible
   public static int log10(long x, RoundingMode mode) {
      MathPreconditions.checkPositive("x", x);
      int logFloor = log10Floor(x);
      long floorPow = powersOf10[logFloor];
      switch (mode) {
         case UNNECESSARY:
            MathPreconditions.checkRoundingUnnecessary(x == floorPow);
         case DOWN:
         case FLOOR:
            return logFloor;
         case UP:
         case CEILING:
            return logFloor + lessThanBranchFree(floorPow, x);
         case HALF_DOWN:
         case HALF_UP:
         case HALF_EVEN:
            return logFloor + lessThanBranchFree(halfPowersOf10[logFloor], x);
         default:
            throw new AssertionError();
      }
   }

   @GwtIncompatible
   static int log10Floor(long x) {
      int y = maxLog10ForLeadingZeros[Long.numberOfLeadingZeros(x)];
      return y - lessThanBranchFree(x, powersOf10[y]);
   }

   @GwtIncompatible
   public static long pow(long b, int k) {
      MathPreconditions.checkNonNegative("exponent", k);
      if (-2L <= b && b <= 2L) {
         switch ((int)b) {
            case -2:
               if (k < 64) {
                  return (k & 1) == 0 ? 1L << k : -(1L << k);
               }

               return 0L;
            case -1:
               return (k & 1) == 0 ? 1L : -1L;
            case 0:
               return k == 0 ? 1L : 0L;
            case 1:
               return 1L;
            case 2:
               return k < 64 ? 1L << k : 0L;
            default:
               throw new AssertionError();
         }
      } else {
         long accum = 1L;

         while(true) {
            switch (k) {
               case 0:
                  return accum;
               case 1:
                  return accum * b;
            }

            accum *= (k & 1) == 0 ? 1L : b;
            b *= b;
            k >>= 1;
         }
      }
   }

   @GwtIncompatible
   public static long sqrt(long x, RoundingMode mode) {
      MathPreconditions.checkNonNegative("x", x);
      if (fitsInInt(x)) {
         return (long)IntMath.sqrt((int)x, mode);
      } else {
         long guess = (long)Math.sqrt((double)x);
         long guessSquared = guess * guess;
         switch (mode) {
            case UNNECESSARY:
               MathPreconditions.checkRoundingUnnecessary(guessSquared == x);
               return guess;
            case DOWN:
            case FLOOR:
               if (x < guessSquared) {
                  return guess - 1L;
               }

               return guess;
            case UP:
            case CEILING:
               if (x > guessSquared) {
                  return guess + 1L;
               }

               return guess;
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN:
               long sqrtFloor = guess - (long)(x < guessSquared ? 1 : 0);
               long halfSquare = sqrtFloor * sqrtFloor + sqrtFloor;
               return sqrtFloor + (long)lessThanBranchFree(halfSquare, x);
            default:
               throw new AssertionError();
         }
      }
   }

   @GwtIncompatible
   public static long divide(long p, long q, RoundingMode mode) {
      Preconditions.checkNotNull(mode);
      long div = p / q;
      long rem = p - q * div;
      if (rem == 0L) {
         return div;
      } else {
         int signum = 1 | (int)((p ^ q) >> 63);
         boolean increment;
         switch (mode) {
            case UNNECESSARY:
               MathPreconditions.checkRoundingUnnecessary(rem == 0L);
            case DOWN:
               increment = false;
               break;
            case FLOOR:
               increment = signum < 0;
               break;
            case UP:
               increment = true;
               break;
            case CEILING:
               increment = signum > 0;
               break;
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN:
               long absRem = Math.abs(rem);
               long cmpRemToHalfDivisor = absRem - (Math.abs(q) - absRem);
               if (cmpRemToHalfDivisor == 0L) {
                  increment = mode == RoundingMode.HALF_UP | mode == RoundingMode.HALF_EVEN & (div & 1L) != 0L;
               } else {
                  increment = cmpRemToHalfDivisor > 0L;
               }
               break;
            default:
               throw new AssertionError();
         }

         return increment ? div + (long)signum : div;
      }
   }

   @GwtIncompatible
   public static int mod(long x, int m) {
      return (int)mod(x, (long)m);
   }

   @GwtIncompatible
   public static long mod(long x, long m) {
      if (m <= 0L) {
         throw new ArithmeticException("Modulus must be positive");
      } else {
         long result = x % m;
         return result >= 0L ? result : result + m;
      }
   }

   public static long gcd(long a, long b) {
      MathPreconditions.checkNonNegative("a", a);
      MathPreconditions.checkNonNegative("b", b);
      if (a == 0L) {
         return b;
      } else if (b == 0L) {
         return a;
      } else {
         int aTwos = Long.numberOfTrailingZeros(a);
         a >>= aTwos;
         int bTwos = Long.numberOfTrailingZeros(b);

         for(b >>= bTwos; a != b; a >>= Long.numberOfTrailingZeros(a)) {
            long delta = a - b;
            long minDeltaOrZero = delta & delta >> 63;
            a = delta - minDeltaOrZero - minDeltaOrZero;
            b += minDeltaOrZero;
         }

         return a << Math.min(aTwos, bTwos);
      }
   }

   @GwtIncompatible
   public static long checkedAdd(long a, long b) {
      long result = a + b;
      MathPreconditions.checkNoOverflow((a ^ b) < 0L | (a ^ result) >= 0L);
      return result;
   }

   @GwtIncompatible
   public static long checkedSubtract(long a, long b) {
      long result = a - b;
      MathPreconditions.checkNoOverflow((a ^ b) >= 0L | (a ^ result) >= 0L);
      return result;
   }

   @GwtIncompatible
   public static long checkedMultiply(long a, long b) {
      int leadingZeros = Long.numberOfLeadingZeros(a) + Long.numberOfLeadingZeros(~a) + Long.numberOfLeadingZeros(b) + Long.numberOfLeadingZeros(~b);
      if (leadingZeros > 65) {
         return a * b;
      } else {
         MathPreconditions.checkNoOverflow(leadingZeros >= 64);
         MathPreconditions.checkNoOverflow(a >= 0L | b != Long.MIN_VALUE);
         long result = a * b;
         MathPreconditions.checkNoOverflow(a == 0L || result / a == b);
         return result;
      }
   }

   @GwtIncompatible
   public static long checkedPow(long b, int k) {
      MathPreconditions.checkNonNegative("exponent", k);
      if (b >= -2L & b <= 2L) {
         switch ((int)b) {
            case -2:
               MathPreconditions.checkNoOverflow(k < 64);
               return (k & 1) == 0 ? 1L << k : -1L << k;
            case -1:
               return (k & 1) == 0 ? 1L : -1L;
            case 0:
               return k == 0 ? 1L : 0L;
            case 1:
               return 1L;
            case 2:
               MathPreconditions.checkNoOverflow(k < 63);
               return 1L << k;
            default:
               throw new AssertionError();
         }
      } else {
         long accum = 1L;

         while(true) {
            do {
               switch (k) {
                  case 0:
                     return accum;
                  case 1:
                     return checkedMultiply(accum, b);
               }

               if ((k & 1) != 0) {
                  accum = checkedMultiply(accum, b);
               }

               k >>= 1;
            } while(k <= 0);

            MathPreconditions.checkNoOverflow(-3037000499L <= b && b <= 3037000499L);
            b *= b;
         }
      }
   }

   @Beta
   public static long saturatedAdd(long a, long b) {
      long naiveSum = a + b;
      return (a ^ b) < 0L | (a ^ naiveSum) >= 0L ? naiveSum : Long.MAX_VALUE + (naiveSum >>> 63 ^ 1L);
   }

   @Beta
   public static long saturatedSubtract(long a, long b) {
      long naiveDifference = a - b;
      return (a ^ b) >= 0L | (a ^ naiveDifference) >= 0L ? naiveDifference : Long.MAX_VALUE + (naiveDifference >>> 63 ^ 1L);
   }

   @Beta
   public static long saturatedMultiply(long a, long b) {
      int leadingZeros = Long.numberOfLeadingZeros(a) + Long.numberOfLeadingZeros(~a) + Long.numberOfLeadingZeros(b) + Long.numberOfLeadingZeros(~b);
      if (leadingZeros > 65) {
         return a * b;
      } else {
         long limit = Long.MAX_VALUE + ((a ^ b) >>> 63);
         if (leadingZeros < 64 | a < 0L & b == Long.MIN_VALUE) {
            return limit;
         } else {
            long result = a * b;
            return a != 0L && result / a != b ? limit : result;
         }
      }
   }

   @Beta
   public static long saturatedPow(long b, int k) {
      MathPreconditions.checkNonNegative("exponent", k);
      if (b >= -2L & b <= 2L) {
         switch ((int)b) {
            case -2:
               if (k >= 64) {
                  return Long.MAX_VALUE + (long)(k & 1);
               }

               return (k & 1) == 0 ? 1L << k : -1L << k;
            case -1:
               return (k & 1) == 0 ? 1L : -1L;
            case 0:
               return k == 0 ? 1L : 0L;
            case 1:
               return 1L;
            case 2:
               if (k >= 63) {
                  return Long.MAX_VALUE;
               }

               return 1L << k;
            default:
               throw new AssertionError();
         }
      } else {
         long accum = 1L;
         long limit = Long.MAX_VALUE + (b >>> 63 & (long)(k & 1));

         while(true) {
            switch (k) {
               case 0:
                  return accum;
               case 1:
                  return saturatedMultiply(accum, b);
            }

            if ((k & 1) != 0) {
               accum = saturatedMultiply(accum, b);
            }

            k >>= 1;
            if (k > 0) {
               if (-3037000499L > b | b > 3037000499L) {
                  return limit;
               }

               b *= b;
            }
         }
      }
   }

   @GwtIncompatible
   public static long factorial(int n) {
      MathPreconditions.checkNonNegative("n", n);
      return n < factorials.length ? factorials[n] : Long.MAX_VALUE;
   }

   public static long binomial(int n, int k) {
      MathPreconditions.checkNonNegative("n", n);
      MathPreconditions.checkNonNegative("k", k);
      Preconditions.checkArgument(k <= n, "k (%s) > n (%s)", k, n);
      if (k > n >> 1) {
         k = n - k;
      }

      switch (k) {
         case 0:
            return 1L;
         case 1:
            return (long)n;
         default:
            if (n < factorials.length) {
               return factorials[n] / (factorials[k] * factorials[n - k]);
            } else if (k < biggestBinomials.length && n <= biggestBinomials[k]) {
               if (k < biggestSimpleBinomials.length && n <= biggestSimpleBinomials[k]) {
                  long result = (long)(n--);

                  for(int i = 2; i <= k; ++i) {
                     result *= (long)n;
                     result /= (long)i;
                     --n;
                  }

                  return result;
               } else {
                  int nBits = log2((long)n, RoundingMode.CEILING);
                  long result = 1L;
                  long numerator = (long)(n--);
                  long denominator = 1L;
                  int numeratorBits = nBits;

                  for(int i = 2; i <= k; --n) {
                     if (numeratorBits + nBits < 63) {
                        numerator *= (long)n;
                        denominator *= (long)i;
                        numeratorBits += nBits;
                     } else {
                        result = multiplyFraction(result, numerator, denominator);
                        numerator = (long)n;
                        denominator = (long)i;
                        numeratorBits = nBits;
                     }

                     ++i;
                  }

                  return multiplyFraction(result, numerator, denominator);
               }
            } else {
               return Long.MAX_VALUE;
            }
      }
   }

   static long multiplyFraction(long x, long numerator, long denominator) {
      if (x == 1L) {
         return numerator / denominator;
      } else {
         long commonDivisor = gcd(x, denominator);
         x /= commonDivisor;
         denominator /= commonDivisor;
         return x * (numerator / denominator);
      }
   }

   static boolean fitsInInt(long x) {
      return (long)((int)x) == x;
   }

   public static long mean(long x, long y) {
      return (x & y) + ((x ^ y) >> 1);
   }

   @GwtIncompatible
   @Beta
   public static boolean isPrime(long n) {
      if (n < 2L) {
         MathPreconditions.checkNonNegative("n", n);
         return false;
      } else if (n != 2L && n != 3L && n != 5L && n != 7L && n != 11L && n != 13L) {
         if ((-545925251 & 1 << (int)(n % 30L)) != 0) {
            return false;
         } else if (n % 7L != 0L && n % 11L != 0L && n % 13L != 0L) {
            if (n < 289L) {
               return true;
            } else {
               long[][] var2 = millerRabinBaseSets;
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  long[] baseSet = var2[var4];
                  if (n <= baseSet[0]) {
                     for(int i = 1; i < baseSet.length; ++i) {
                        if (!LongMath.MillerRabinTester.test(baseSet[i], n)) {
                           return false;
                        }
                     }

                     return true;
                  }
               }

               throw new AssertionError();
            }
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   private LongMath() {
   }

   private static enum MillerRabinTester {
      SMALL {
         long mulMod(long a, long b, long m) {
            return a * b % m;
         }

         long squareMod(long a, long m) {
            return a * a % m;
         }
      },
      LARGE {
         private long plusMod(long a, long b, long m) {
            return a >= m - b ? a + b - m : a + b;
         }

         private long times2ToThe32Mod(long a, long m) {
            int remainingPowersOf2 = 32;

            do {
               int shift = Math.min(remainingPowersOf2, Long.numberOfLeadingZeros(a));
               a = UnsignedLongs.remainder(a << shift, m);
               remainingPowersOf2 -= shift;
            } while(remainingPowersOf2 > 0);

            return a;
         }

         long mulMod(long a, long b, long m) {
            long aHi = a >>> 32;
            long bHi = b >>> 32;
            long aLo = a & 4294967295L;
            long bLo = b & 4294967295L;
            long result = this.times2ToThe32Mod(aHi * bHi, m);
            result += aHi * bLo;
            if (result < 0L) {
               result = UnsignedLongs.remainder(result, m);
            }

            result += aLo * bHi;
            result = this.times2ToThe32Mod(result, m);
            return this.plusMod(result, UnsignedLongs.remainder(aLo * bLo, m), m);
         }

         long squareMod(long a, long m) {
            long aHi = a >>> 32;
            long aLo = a & 4294967295L;
            long result = this.times2ToThe32Mod(aHi * aHi, m);
            long hiLo = aHi * aLo * 2L;
            if (hiLo < 0L) {
               hiLo = UnsignedLongs.remainder(hiLo, m);
            }

            result += hiLo;
            result = this.times2ToThe32Mod(result, m);
            return this.plusMod(result, UnsignedLongs.remainder(aLo * aLo, m), m);
         }
      };

      private MillerRabinTester() {
      }

      static boolean test(long base, long n) {
         return (n <= 3037000499L ? SMALL : LARGE).testWitness(base, n);
      }

      abstract long mulMod(long var1, long var3, long var5);

      abstract long squareMod(long var1, long var3);

      private long powMod(long a, long p, long m) {
         long res;
         for(res = 1L; p != 0L; p >>= 1) {
            if ((p & 1L) != 0L) {
               res = this.mulMod(res, a, m);
            }

            a = this.squareMod(a, m);
         }

         return res;
      }

      private boolean testWitness(long base, long n) {
         int r = Long.numberOfTrailingZeros(n - 1L);
         long d = n - 1L >> r;
         base %= n;
         if (base == 0L) {
            return true;
         } else {
            long a = this.powMod(base, d, n);
            if (a == 1L) {
               return true;
            } else {
               for(int j = 0; a != n - 1L; a = this.squareMod(a, n)) {
                  ++j;
                  if (j == r) {
                     return false;
                  }
               }

               return true;
            }
         }
      }

      // $FF: synthetic method
      MillerRabinTester(Object x2) {
         this();
      }
   }
}
