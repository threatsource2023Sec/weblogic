package org.python.google.common.primitives;

import java.util.Comparator;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible
public final class UnsignedInts {
   static final long INT_MASK = 4294967295L;

   private UnsignedInts() {
   }

   static int flip(int value) {
      return value ^ Integer.MIN_VALUE;
   }

   public static int compare(int a, int b) {
      return Ints.compare(flip(a), flip(b));
   }

   public static long toLong(int value) {
      return (long)value & 4294967295L;
   }

   public static int checkedCast(long value) {
      Preconditions.checkArgument(value >> 32 == 0L, "out of range: %s", value);
      return (int)value;
   }

   public static int saturatedCast(long value) {
      if (value <= 0L) {
         return 0;
      } else {
         return value >= 4294967296L ? -1 : (int)value;
      }
   }

   public static int min(int... array) {
      Preconditions.checkArgument(array.length > 0);
      int min = flip(array[0]);

      for(int i = 1; i < array.length; ++i) {
         int next = flip(array[i]);
         if (next < min) {
            min = next;
         }
      }

      return flip(min);
   }

   public static int max(int... array) {
      Preconditions.checkArgument(array.length > 0);
      int max = flip(array[0]);

      for(int i = 1; i < array.length; ++i) {
         int next = flip(array[i]);
         if (next > max) {
            max = next;
         }
      }

      return flip(max);
   }

   public static String join(String separator, int... array) {
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
      return UnsignedInts.LexicographicalComparator.INSTANCE;
   }

   public static int divide(int dividend, int divisor) {
      return (int)(toLong(dividend) / toLong(divisor));
   }

   public static int remainder(int dividend, int divisor) {
      return (int)(toLong(dividend) % toLong(divisor));
   }

   @CanIgnoreReturnValue
   public static int decode(String stringValue) {
      ParseRequest request = ParseRequest.fromString(stringValue);

      try {
         return parseUnsignedInt(request.rawValue, request.radix);
      } catch (NumberFormatException var4) {
         NumberFormatException decodeException = new NumberFormatException("Error parsing value: " + stringValue);
         decodeException.initCause(var4);
         throw decodeException;
      }
   }

   @CanIgnoreReturnValue
   public static int parseUnsignedInt(String s) {
      return parseUnsignedInt(s, 10);
   }

   @CanIgnoreReturnValue
   public static int parseUnsignedInt(String string, int radix) {
      Preconditions.checkNotNull(string);
      long result = Long.parseLong(string, radix);
      if ((result & 4294967295L) != result) {
         throw new NumberFormatException("Input " + string + " in base " + radix + " is not in the range of an unsigned integer");
      } else {
         return (int)result;
      }
   }

   public static String toString(int x) {
      return toString(x, 10);
   }

   public static String toString(int x, int radix) {
      long asLong = (long)x & 4294967295L;
      return Long.toString(asLong, radix);
   }

   static enum LexicographicalComparator implements Comparator {
      INSTANCE;

      public int compare(int[] left, int[] right) {
         int minLength = Math.min(left.length, right.length);

         for(int i = 0; i < minLength; ++i) {
            if (left[i] != right[i]) {
               return UnsignedInts.compare(left[i], right[i]);
            }
         }

         return left.length - right.length;
      }

      public String toString() {
         return "UnsignedInts.lexicographicalComparator()";
      }
   }
}
