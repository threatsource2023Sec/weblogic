package org.python.google.common.primitives;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Converter;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   emulated = true
)
public final class Shorts {
   public static final int BYTES = 2;
   public static final short MAX_POWER_OF_TWO = 16384;

   private Shorts() {
   }

   public static int hashCode(short value) {
      return value;
   }

   public static short checkedCast(long value) {
      short result = (short)((int)value);
      Preconditions.checkArgument((long)result == value, "Out of range: %s", value);
      return result;
   }

   public static short saturatedCast(long value) {
      if (value > 32767L) {
         return 32767;
      } else {
         return value < -32768L ? Short.MIN_VALUE : (short)((int)value);
      }
   }

   public static int compare(short a, short b) {
      return a - b;
   }

   public static boolean contains(short[] array, short target) {
      short[] var2 = array;
      int var3 = array.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         short value = var2[var4];
         if (value == target) {
            return true;
         }
      }

      return false;
   }

   public static int indexOf(short[] array, short target) {
      return indexOf(array, target, 0, array.length);
   }

   private static int indexOf(short[] array, short target, int start, int end) {
      for(int i = start; i < end; ++i) {
         if (array[i] == target) {
            return i;
         }
      }

      return -1;
   }

   public static int indexOf(short[] array, short[] target) {
      Preconditions.checkNotNull(array, "array");
      Preconditions.checkNotNull(target, "target");
      if (target.length == 0) {
         return 0;
      } else {
         label28:
         for(int i = 0; i < array.length - target.length + 1; ++i) {
            for(int j = 0; j < target.length; ++j) {
               if (array[i + j] != target[j]) {
                  continue label28;
               }
            }

            return i;
         }

         return -1;
      }
   }

   public static int lastIndexOf(short[] array, short target) {
      return lastIndexOf(array, target, 0, array.length);
   }

   private static int lastIndexOf(short[] array, short target, int start, int end) {
      for(int i = end - 1; i >= start; --i) {
         if (array[i] == target) {
            return i;
         }
      }

      return -1;
   }

   public static short min(short... array) {
      Preconditions.checkArgument(array.length > 0);
      short min = array[0];

      for(int i = 1; i < array.length; ++i) {
         if (array[i] < min) {
            min = array[i];
         }
      }

      return min;
   }

   public static short max(short... array) {
      Preconditions.checkArgument(array.length > 0);
      short max = array[0];

      for(int i = 1; i < array.length; ++i) {
         if (array[i] > max) {
            max = array[i];
         }
      }

      return max;
   }

   @Beta
   public static short constrainToRange(short value, short min, short max) {
      Preconditions.checkArgument(min <= max, "min (%s) must be less than or equal to max (%s)", (int)min, (int)max);
      return value < min ? min : (value < max ? value : max);
   }

   public static short[] concat(short[]... arrays) {
      int length = 0;
      short[][] var2 = arrays;
      int pos = arrays.length;

      for(int var4 = 0; var4 < pos; ++var4) {
         short[] array = var2[var4];
         length += array.length;
      }

      short[] result = new short[length];
      pos = 0;
      short[][] var9 = arrays;
      int var10 = arrays.length;

      for(int var6 = 0; var6 < var10; ++var6) {
         short[] array = var9[var6];
         System.arraycopy(array, 0, result, pos, array.length);
         pos += array.length;
      }

      return result;
   }

   @GwtIncompatible
   public static byte[] toByteArray(short value) {
      return new byte[]{(byte)(value >> 8), (byte)value};
   }

   @GwtIncompatible
   public static short fromByteArray(byte[] bytes) {
      Preconditions.checkArgument(bytes.length >= 2, "array too small: %s < %s", (int)bytes.length, (int)2);
      return fromBytes(bytes[0], bytes[1]);
   }

   @GwtIncompatible
   public static short fromBytes(byte b1, byte b2) {
      return (short)(b1 << 8 | b2 & 255);
   }

   @Beta
   public static Converter stringConverter() {
      return Shorts.ShortConverter.INSTANCE;
   }

   public static short[] ensureCapacity(short[] array, int minLength, int padding) {
      Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", minLength);
      Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", padding);
      return array.length < minLength ? Arrays.copyOf(array, minLength + padding) : array;
   }

   public static String join(String separator, short... array) {
      Preconditions.checkNotNull(separator);
      if (array.length == 0) {
         return "";
      } else {
         StringBuilder builder = new StringBuilder(array.length * 6);
         builder.append(array[0]);

         for(int i = 1; i < array.length; ++i) {
            builder.append(separator).append(array[i]);
         }

         return builder.toString();
      }
   }

   public static Comparator lexicographicalComparator() {
      return Shorts.LexicographicalComparator.INSTANCE;
   }

   public static short[] toArray(Collection collection) {
      if (collection instanceof ShortArrayAsList) {
         return ((ShortArrayAsList)collection).toShortArray();
      } else {
         Object[] boxedArray = collection.toArray();
         int len = boxedArray.length;
         short[] array = new short[len];

         for(int i = 0; i < len; ++i) {
            array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).shortValue();
         }

         return array;
      }
   }

   public static List asList(short... backingArray) {
      return (List)(backingArray.length == 0 ? Collections.emptyList() : new ShortArrayAsList(backingArray));
   }

   @GwtCompatible
   private static class ShortArrayAsList extends AbstractList implements RandomAccess, Serializable {
      final short[] array;
      final int start;
      final int end;
      private static final long serialVersionUID = 0L;

      ShortArrayAsList(short[] array) {
         this(array, 0, array.length);
      }

      ShortArrayAsList(short[] array, int start, int end) {
         this.array = array;
         this.start = start;
         this.end = end;
      }

      public int size() {
         return this.end - this.start;
      }

      public boolean isEmpty() {
         return false;
      }

      public Short get(int index) {
         Preconditions.checkElementIndex(index, this.size());
         return this.array[this.start + index];
      }

      public boolean contains(Object target) {
         return target instanceof Short && Shorts.indexOf(this.array, (Short)target, this.start, this.end) != -1;
      }

      public int indexOf(Object target) {
         if (target instanceof Short) {
            int i = Shorts.indexOf(this.array, (Short)target, this.start, this.end);
            if (i >= 0) {
               return i - this.start;
            }
         }

         return -1;
      }

      public int lastIndexOf(Object target) {
         if (target instanceof Short) {
            int i = Shorts.lastIndexOf(this.array, (Short)target, this.start, this.end);
            if (i >= 0) {
               return i - this.start;
            }
         }

         return -1;
      }

      public Short set(int index, Short element) {
         Preconditions.checkElementIndex(index, this.size());
         short oldValue = this.array[this.start + index];
         this.array[this.start + index] = (Short)Preconditions.checkNotNull(element);
         return oldValue;
      }

      public List subList(int fromIndex, int toIndex) {
         int size = this.size();
         Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
         return (List)(fromIndex == toIndex ? Collections.emptyList() : new ShortArrayAsList(this.array, this.start + fromIndex, this.start + toIndex));
      }

      public boolean equals(Object object) {
         if (object == this) {
            return true;
         } else if (object instanceof ShortArrayAsList) {
            ShortArrayAsList that = (ShortArrayAsList)object;
            int size = this.size();
            if (that.size() != size) {
               return false;
            } else {
               for(int i = 0; i < size; ++i) {
                  if (this.array[this.start + i] != that.array[that.start + i]) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return super.equals(object);
         }
      }

      public int hashCode() {
         int result = 1;

         for(int i = this.start; i < this.end; ++i) {
            result = 31 * result + Shorts.hashCode(this.array[i]);
         }

         return result;
      }

      public String toString() {
         StringBuilder builder = new StringBuilder(this.size() * 6);
         builder.append('[').append(this.array[this.start]);

         for(int i = this.start + 1; i < this.end; ++i) {
            builder.append(", ").append(this.array[i]);
         }

         return builder.append(']').toString();
      }

      short[] toShortArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }
   }

   private static enum LexicographicalComparator implements Comparator {
      INSTANCE;

      public int compare(short[] left, short[] right) {
         int minLength = Math.min(left.length, right.length);

         for(int i = 0; i < minLength; ++i) {
            int result = Shorts.compare(left[i], right[i]);
            if (result != 0) {
               return result;
            }
         }

         return left.length - right.length;
      }

      public String toString() {
         return "Shorts.lexicographicalComparator()";
      }
   }

   private static final class ShortConverter extends Converter implements Serializable {
      static final ShortConverter INSTANCE = new ShortConverter();
      private static final long serialVersionUID = 1L;

      protected Short doForward(String value) {
         return Short.decode(value);
      }

      protected String doBackward(Short value) {
         return value.toString();
      }

      public String toString() {
         return "Shorts.stringConverter()";
      }

      private Object readResolve() {
         return INSTANCE;
      }
   }
}
