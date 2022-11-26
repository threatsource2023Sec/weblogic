package org.python.google.common.primitives;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Converter;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   emulated = true
)
public final class Floats {
   public static final int BYTES = 4;

   private Floats() {
   }

   public static int hashCode(float value) {
      return Float.valueOf(value).hashCode();
   }

   public static int compare(float a, float b) {
      return Float.compare(a, b);
   }

   public static boolean isFinite(float value) {
      return Float.NEGATIVE_INFINITY < value && value < Float.POSITIVE_INFINITY;
   }

   public static boolean contains(float[] array, float target) {
      float[] var2 = array;
      int var3 = array.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         float value = var2[var4];
         if (value == target) {
            return true;
         }
      }

      return false;
   }

   public static int indexOf(float[] array, float target) {
      return indexOf(array, target, 0, array.length);
   }

   private static int indexOf(float[] array, float target, int start, int end) {
      for(int i = start; i < end; ++i) {
         if (array[i] == target) {
            return i;
         }
      }

      return -1;
   }

   public static int indexOf(float[] array, float[] target) {
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

   public static int lastIndexOf(float[] array, float target) {
      return lastIndexOf(array, target, 0, array.length);
   }

   private static int lastIndexOf(float[] array, float target, int start, int end) {
      for(int i = end - 1; i >= start; --i) {
         if (array[i] == target) {
            return i;
         }
      }

      return -1;
   }

   public static float min(float... array) {
      Preconditions.checkArgument(array.length > 0);
      float min = array[0];

      for(int i = 1; i < array.length; ++i) {
         min = Math.min(min, array[i]);
      }

      return min;
   }

   public static float max(float... array) {
      Preconditions.checkArgument(array.length > 0);
      float max = array[0];

      for(int i = 1; i < array.length; ++i) {
         max = Math.max(max, array[i]);
      }

      return max;
   }

   @Beta
   public static float constrainToRange(float value, float min, float max) {
      Preconditions.checkArgument(min <= max, "min (%s) must be less than or equal to max (%s)", min, max);
      return Math.min(Math.max(value, min), max);
   }

   public static float[] concat(float[]... arrays) {
      int length = 0;
      float[][] var2 = arrays;
      int pos = arrays.length;

      for(int var4 = 0; var4 < pos; ++var4) {
         float[] array = var2[var4];
         length += array.length;
      }

      float[] result = new float[length];
      pos = 0;
      float[][] var9 = arrays;
      int var10 = arrays.length;

      for(int var6 = 0; var6 < var10; ++var6) {
         float[] array = var9[var6];
         System.arraycopy(array, 0, result, pos, array.length);
         pos += array.length;
      }

      return result;
   }

   @Beta
   public static Converter stringConverter() {
      return Floats.FloatConverter.INSTANCE;
   }

   public static float[] ensureCapacity(float[] array, int minLength, int padding) {
      Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", minLength);
      Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", padding);
      return array.length < minLength ? Arrays.copyOf(array, minLength + padding) : array;
   }

   public static String join(String separator, float... array) {
      Preconditions.checkNotNull(separator);
      if (array.length == 0) {
         return "";
      } else {
         StringBuilder builder = new StringBuilder(array.length * 12);
         builder.append(array[0]);

         for(int i = 1; i < array.length; ++i) {
            builder.append(separator).append(array[i]);
         }

         return builder.toString();
      }
   }

   public static Comparator lexicographicalComparator() {
      return Floats.LexicographicalComparator.INSTANCE;
   }

   public static float[] toArray(Collection collection) {
      if (collection instanceof FloatArrayAsList) {
         return ((FloatArrayAsList)collection).toFloatArray();
      } else {
         Object[] boxedArray = collection.toArray();
         int len = boxedArray.length;
         float[] array = new float[len];

         for(int i = 0; i < len; ++i) {
            array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).floatValue();
         }

         return array;
      }
   }

   public static List asList(float... backingArray) {
      return (List)(backingArray.length == 0 ? Collections.emptyList() : new FloatArrayAsList(backingArray));
   }

   @Nullable
   @CheckForNull
   @Beta
   @GwtIncompatible
   public static Float tryParse(String string) {
      if (Doubles.FLOATING_POINT_PATTERN.matcher(string).matches()) {
         try {
            return Float.parseFloat(string);
         } catch (NumberFormatException var2) {
         }
      }

      return null;
   }

   @GwtCompatible
   private static class FloatArrayAsList extends AbstractList implements RandomAccess, Serializable {
      final float[] array;
      final int start;
      final int end;
      private static final long serialVersionUID = 0L;

      FloatArrayAsList(float[] array) {
         this(array, 0, array.length);
      }

      FloatArrayAsList(float[] array, int start, int end) {
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

      public Float get(int index) {
         Preconditions.checkElementIndex(index, this.size());
         return this.array[this.start + index];
      }

      public boolean contains(Object target) {
         return target instanceof Float && Floats.indexOf(this.array, (Float)target, this.start, this.end) != -1;
      }

      public int indexOf(Object target) {
         if (target instanceof Float) {
            int i = Floats.indexOf(this.array, (Float)target, this.start, this.end);
            if (i >= 0) {
               return i - this.start;
            }
         }

         return -1;
      }

      public int lastIndexOf(Object target) {
         if (target instanceof Float) {
            int i = Floats.lastIndexOf(this.array, (Float)target, this.start, this.end);
            if (i >= 0) {
               return i - this.start;
            }
         }

         return -1;
      }

      public Float set(int index, Float element) {
         Preconditions.checkElementIndex(index, this.size());
         float oldValue = this.array[this.start + index];
         this.array[this.start + index] = (Float)Preconditions.checkNotNull(element);
         return oldValue;
      }

      public List subList(int fromIndex, int toIndex) {
         int size = this.size();
         Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
         return (List)(fromIndex == toIndex ? Collections.emptyList() : new FloatArrayAsList(this.array, this.start + fromIndex, this.start + toIndex));
      }

      public boolean equals(@Nullable Object object) {
         if (object == this) {
            return true;
         } else if (object instanceof FloatArrayAsList) {
            FloatArrayAsList that = (FloatArrayAsList)object;
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
            result = 31 * result + Floats.hashCode(this.array[i]);
         }

         return result;
      }

      public String toString() {
         StringBuilder builder = new StringBuilder(this.size() * 12);
         builder.append('[').append(this.array[this.start]);

         for(int i = this.start + 1; i < this.end; ++i) {
            builder.append(", ").append(this.array[i]);
         }

         return builder.append(']').toString();
      }

      float[] toFloatArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }
   }

   private static enum LexicographicalComparator implements Comparator {
      INSTANCE;

      public int compare(float[] left, float[] right) {
         int minLength = Math.min(left.length, right.length);

         for(int i = 0; i < minLength; ++i) {
            int result = Float.compare(left[i], right[i]);
            if (result != 0) {
               return result;
            }
         }

         return left.length - right.length;
      }

      public String toString() {
         return "Floats.lexicographicalComparator()";
      }
   }

   private static final class FloatConverter extends Converter implements Serializable {
      static final FloatConverter INSTANCE = new FloatConverter();
      private static final long serialVersionUID = 1L;

      protected Float doForward(String value) {
         return Float.valueOf(value);
      }

      protected String doBackward(Float value) {
         return value.toString();
      }

      public String toString() {
         return "Floats.stringConverter()";
      }

      private Object readResolve() {
         return INSTANCE;
      }
   }
}
