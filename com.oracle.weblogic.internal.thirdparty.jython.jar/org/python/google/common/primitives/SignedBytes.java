package org.python.google.common.primitives;

import java.util.Comparator;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
public final class SignedBytes {
   public static final byte MAX_POWER_OF_TWO = 64;

   private SignedBytes() {
   }

   public static byte checkedCast(long value) {
      byte result = (byte)((int)value);
      Preconditions.checkArgument((long)result == value, "Out of range: %s", value);
      return result;
   }

   public static byte saturatedCast(long value) {
      if (value > 127L) {
         return 127;
      } else {
         return value < -128L ? -128 : (byte)((int)value);
      }
   }

   public static int compare(byte a, byte b) {
      return a - b;
   }

   public static byte min(byte... array) {
      Preconditions.checkArgument(array.length > 0);
      byte min = array[0];

      for(int i = 1; i < array.length; ++i) {
         if (array[i] < min) {
            min = array[i];
         }
      }

      return min;
   }

   public static byte max(byte... array) {
      Preconditions.checkArgument(array.length > 0);
      byte max = array[0];

      for(int i = 1; i < array.length; ++i) {
         if (array[i] > max) {
            max = array[i];
         }
      }

      return max;
   }

   public static String join(String separator, byte... array) {
      Preconditions.checkNotNull(separator);
      if (array.length == 0) {
         return "";
      } else {
         StringBuilder builder = new StringBuilder(array.length * 5);
         builder.append(array[0]);

         for(int i = 1; i < array.length; ++i) {
            builder.append(separator).append(array[i]);
         }

         return builder.toString();
      }
   }

   public static Comparator lexicographicalComparator() {
      return SignedBytes.LexicographicalComparator.INSTANCE;
   }

   private static enum LexicographicalComparator implements Comparator {
      INSTANCE;

      public int compare(byte[] left, byte[] right) {
         int minLength = Math.min(left.length, right.length);

         for(int i = 0; i < minLength; ++i) {
            int result = SignedBytes.compare(left[i], right[i]);
            if (result != 0) {
               return result;
            }
         }

         return left.length - right.length;
      }

      public String toString() {
         return "SignedBytes.lexicographicalComparator()";
      }
   }
}
