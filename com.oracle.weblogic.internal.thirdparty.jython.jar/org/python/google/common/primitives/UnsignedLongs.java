package org.python.google.common.primitives;

import java.math.BigInteger;
import java.util.Comparator;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible
public final class UnsignedLongs {
   public static final long MAX_VALUE = -1L;
   private static final long[] maxValueDivs = new long[37];
   private static final int[] maxValueMods = new int[37];
   private static final int[] maxSafeDigits = new int[37];

   private UnsignedLongs() {
   }

   private static long flip(long a) {
      return a ^ Long.MIN_VALUE;
   }

   public static int compare(long a, long b) {
      return Longs.compare(flip(a), flip(b));
   }

   public static long min(long... array) {
      Preconditions.checkArgument(array.length > 0);
      long min = flip(array[0]);

      for(int i = 1; i < array.length; ++i) {
         long next = flip(array[i]);
         if (next < min) {
            min = next;
         }
      }

      return flip(min);
   }

   public static long max(long... array) {
      Preconditions.checkArgument(array.length > 0);
      long max = flip(array[0]);

      for(int i = 1; i < array.length; ++i) {
         long next = flip(array[i]);
         if (next > max) {
            max = next;
         }
      }

      return flip(max);
   }

   public static String join(String separator, long... array) {
      Preconditions.checkNotNull(separator);
      if (array.length == 0) {
         return "";
      } else {
         StringBuilder builder = new StringBuilder(array.length * 5);
         builder.append(toString(array[0]));

         for(int i = 1; i < array.length; ++i) {
            builder.append(separator).append(toString(array[i]));
         }

         return builder.toString();
      }
   }

   public static Comparator lexicographicalComparator() {
      return UnsignedLongs.LexicographicalComparator.INSTANCE;
   }

   public static long divide(long dividend, long divisor) {
      if (divisor < 0L) {
         return compare(dividend, divisor) < 0 ? 0L : 1L;
      } else if (dividend >= 0L) {
         return dividend / divisor;
      } else {
         long quotient = (dividend >>> 1) / divisor << 1;
         long rem = dividend - quotient * divisor;
         return quotient + (long)(compare(rem, divisor) >= 0 ? 1 : 0);
      }
   }

   public static long remainder(long dividend, long divisor) {
      if (divisor < 0L) {
         return compare(dividend, divisor) < 0 ? dividend : dividend - divisor;
      } else if (dividend >= 0L) {
         return dividend % divisor;
      } else {
         long quotient = (dividend >>> 1) / divisor << 1;
         long rem = dividend - quotient * divisor;
         return rem - (compare(rem, divisor) >= 0 ? divisor : 0L);
      }
   }

   @CanIgnoreReturnValue
   public static long parseUnsignedLong(String string) {
      return parseUnsignedLong(string, 10);
   }

   @CanIgnoreReturnValue
   public static long decode(String stringValue) {
      ParseRequest request = ParseRequest.fromString(stringValue);

      try {
         return parseUnsignedLong(request.rawValue, request.radix);
      } catch (NumberFormatException var4) {
         NumberFormatException decodeException = new NumberFormatException("Error parsing value: " + stringValue);
         decodeException.initCause(var4);
         throw decodeException;
      }
   }

   @CanIgnoreReturnValue
   public static long parseUnsignedLong(String string, int radix) {
      Preconditions.checkNotNull(string);
      if (string.length() == 0) {
         throw new NumberFormatException("empty string");
      } else if (radix >= 2 && radix <= 36) {
         int maxSafePos = maxSafeDigits[radix] - 1;
         long value = 0L;

         for(int pos = 0; pos < string.length(); ++pos) {
            int digit = Character.digit(string.charAt(pos), radix);
            if (digit == -1) {
               throw new NumberFormatException(string);
            }

            if (pos > maxSafePos && overflowInParse(value, digit, radix)) {
               throw new NumberFormatException("Too large for unsigned long: " + string);
            }

            value = value * (long)radix + (long)digit;
         }

         return value;
      } else {
         throw new NumberFormatException("illegal radix: " + radix);
      }
   }

   private static boolean overflowInParse(long current, int digit, int radix) {
      if (current >= 0L) {
         if (current < maxValueDivs[radix]) {
            return false;
         } else if (current > maxValueDivs[radix]) {
            return true;
         } else {
            return digit > maxValueMods[radix];
         }
      } else {
         return true;
      }
   }

   public static String toString(long x) {
      return toString(x, 10);
   }

   public static String toString(long x, int radix) {
      Preconditions.checkArgument(radix >= 2 && radix <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", radix);
      if (x == 0L) {
         return "0";
      } else if (x > 0L) {
         return Long.toString(x, radix);
      } else {
         char[] buf = new char[64];
         int i = buf.length;
         if ((radix & radix - 1) == 0) {
            int shift = Integer.numberOfTrailingZeros(radix);
            int mask = radix - 1;

            do {
               --i;
               buf[i] = Character.forDigit((int)x & mask, radix);
               x >>>= shift;
            } while(x != 0L);
         } else {
            long quotient;
            if ((radix & 1) == 0) {
               quotient = (x >>> 1) / (long)(radix >>> 1);
            } else {
               quotient = divide(x, (long)radix);
            }

            long rem = x - quotient * (long)radix;
            --i;
            buf[i] = Character.forDigit((int)rem, radix);

            for(x = quotient; x > 0L; x /= (long)radix) {
               --i;
               buf[i] = Character.forDigit((int)(x % (long)radix), radix);
            }
         }

         return new String(buf, i, buf.length - i);
      }
   }

   static {
      BigInteger overflow = new BigInteger("10000000000000000", 16);

      for(int i = 2; i <= 36; ++i) {
         maxValueDivs[i] = divide(-1L, (long)i);
         maxValueMods[i] = (int)remainder(-1L, (long)i);
         maxSafeDigits[i] = overflow.toString(i).length() - 1;
      }

   }

   static enum LexicographicalComparator implements Comparator {
      INSTANCE;

      public int compare(long[] left, long[] right) {
         int minLength = Math.min(left.length, right.length);

         for(int i = 0; i < minLength; ++i) {
            if (left[i] != right[i]) {
               return UnsignedLongs.compare(left[i], right[i]);
            }
         }

         return left.length - right.length;
      }

      public String toString() {
         return "UnsignedLongs.lexicographicalComparator()";
      }
   }
}
