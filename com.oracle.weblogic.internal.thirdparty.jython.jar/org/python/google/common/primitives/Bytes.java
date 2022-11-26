package org.python.google.common.primitives;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
public final class Bytes {
   private Bytes() {
   }

   public static int hashCode(byte value) {
      return value;
   }

   public static boolean contains(byte[] array, byte target) {
      byte[] var2 = array;
      int var3 = array.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte value = var2[var4];
         if (value == target) {
            return true;
         }
      }

      return false;
   }

   public static int indexOf(byte[] array, byte target) {
      return indexOf(array, target, 0, array.length);
   }

   private static int indexOf(byte[] array, byte target, int start, int end) {
      for(int i = start; i < end; ++i) {
         if (array[i] == target) {
            return i;
         }
      }

      return -1;
   }

   public static int indexOf(byte[] array, byte[] target) {
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

   public static int lastIndexOf(byte[] array, byte target) {
      return lastIndexOf(array, target, 0, array.length);
   }

   private static int lastIndexOf(byte[] array, byte target, int start, int end) {
      for(int i = end - 1; i >= start; --i) {
         if (array[i] == target) {
            return i;
         }
      }

      return -1;
   }

   public static byte[] concat(byte[]... arrays) {
      int length = 0;
      byte[][] var2 = arrays;
      int pos = arrays.length;

      for(int var4 = 0; var4 < pos; ++var4) {
         byte[] array = var2[var4];
         length += array.length;
      }

      byte[] result = new byte[length];
      pos = 0;
      byte[][] var9 = arrays;
      int var10 = arrays.length;

      for(int var6 = 0; var6 < var10; ++var6) {
         byte[] array = var9[var6];
         System.arraycopy(array, 0, result, pos, array.length);
         pos += array.length;
      }

      return result;
   }

   public static byte[] ensureCapacity(byte[] array, int minLength, int padding) {
      Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", minLength);
      Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", padding);
      return array.length < minLength ? Arrays.copyOf(array, minLength + padding) : array;
   }

   public static byte[] toArray(Collection collection) {
      if (collection instanceof ByteArrayAsList) {
         return ((ByteArrayAsList)collection).toByteArray();
      } else {
         Object[] boxedArray = collection.toArray();
         int len = boxedArray.length;
         byte[] array = new byte[len];

         for(int i = 0; i < len; ++i) {
            array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).byteValue();
         }

         return array;
      }
   }

   public static List asList(byte... backingArray) {
      return (List)(backingArray.length == 0 ? Collections.emptyList() : new ByteArrayAsList(backingArray));
   }

   @GwtCompatible
   private static class ByteArrayAsList extends AbstractList implements RandomAccess, Serializable {
      final byte[] array;
      final int start;
      final int end;
      private static final long serialVersionUID = 0L;

      ByteArrayAsList(byte[] array) {
         this(array, 0, array.length);
      }

      ByteArrayAsList(byte[] array, int start, int end) {
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

      public Byte get(int index) {
         Preconditions.checkElementIndex(index, this.size());
         return this.array[this.start + index];
      }

      public boolean contains(Object target) {
         return target instanceof Byte && Bytes.indexOf(this.array, (Byte)target, this.start, this.end) != -1;
      }

      public int indexOf(Object target) {
         if (target instanceof Byte) {
            int i = Bytes.indexOf(this.array, (Byte)target, this.start, this.end);
            if (i >= 0) {
               return i - this.start;
            }
         }

         return -1;
      }

      public int lastIndexOf(Object target) {
         if (target instanceof Byte) {
            int i = Bytes.lastIndexOf(this.array, (Byte)target, this.start, this.end);
            if (i >= 0) {
               return i - this.start;
            }
         }

         return -1;
      }

      public Byte set(int index, Byte element) {
         Preconditions.checkElementIndex(index, this.size());
         byte oldValue = this.array[this.start + index];
         this.array[this.start + index] = (Byte)Preconditions.checkNotNull(element);
         return oldValue;
      }

      public List subList(int fromIndex, int toIndex) {
         int size = this.size();
         Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
         return (List)(fromIndex == toIndex ? Collections.emptyList() : new ByteArrayAsList(this.array, this.start + fromIndex, this.start + toIndex));
      }

      public boolean equals(@Nullable Object object) {
         if (object == this) {
            return true;
         } else if (object instanceof ByteArrayAsList) {
            ByteArrayAsList that = (ByteArrayAsList)object;
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
            result = 31 * result + Bytes.hashCode(this.array[i]);
         }

         return result;
      }

      public String toString() {
         StringBuilder builder = new StringBuilder(this.size() * 5);
         builder.append('[').append(this.array[this.start]);

         for(int i = this.start + 1; i < this.end; ++i) {
            builder.append(", ").append(this.array[i]);
         }

         return builder.append(']').toString();
      }

      byte[] toByteArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }
   }
}
