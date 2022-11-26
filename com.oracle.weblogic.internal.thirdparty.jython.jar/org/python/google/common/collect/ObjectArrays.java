package org.python.google.common.collect;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public final class ObjectArrays {
   private ObjectArrays() {
   }

   @GwtIncompatible
   public static Object[] newArray(Class type, int length) {
      return (Object[])((Object[])Array.newInstance(type, length));
   }

   public static Object[] newArray(Object[] reference, int length) {
      return Platform.newArray(reference, length);
   }

   @GwtIncompatible
   public static Object[] concat(Object[] first, Object[] second, Class type) {
      Object[] result = newArray(type, first.length + second.length);
      System.arraycopy(first, 0, result, 0, first.length);
      System.arraycopy(second, 0, result, first.length, second.length);
      return result;
   }

   public static Object[] concat(@Nullable Object element, Object[] array) {
      Object[] result = newArray(array, array.length + 1);
      result[0] = element;
      System.arraycopy(array, 0, result, 1, array.length);
      return result;
   }

   public static Object[] concat(Object[] array, @Nullable Object element) {
      Object[] result = Arrays.copyOf(array, array.length + 1);
      result[array.length] = element;
      return result;
   }

   static Object[] toArrayImpl(Collection c, Object[] array) {
      int size = c.size();
      if (array.length < size) {
         array = newArray(array, size);
      }

      fillArray(c, array);
      if (array.length > size) {
         array[size] = null;
      }

      return array;
   }

   static Object[] toArrayImpl(Object[] src, int offset, int len, Object[] dst) {
      Preconditions.checkPositionIndexes(offset, offset + len, src.length);
      if (dst.length < len) {
         dst = newArray(dst, len);
      } else if (dst.length > len) {
         dst[len] = null;
      }

      System.arraycopy(src, offset, dst, 0, len);
      return dst;
   }

   static Object[] toArrayImpl(Collection c) {
      return fillArray(c, new Object[c.size()]);
   }

   static Object[] copyAsObjectArray(Object[] elements, int offset, int length) {
      Preconditions.checkPositionIndexes(offset, offset + length, elements.length);
      if (length == 0) {
         return new Object[0];
      } else {
         Object[] result = new Object[length];
         System.arraycopy(elements, offset, result, 0, length);
         return result;
      }
   }

   @CanIgnoreReturnValue
   private static Object[] fillArray(Iterable elements, Object[] array) {
      int i = 0;

      Object element;
      for(Iterator var3 = elements.iterator(); var3.hasNext(); array[i++] = element) {
         element = var3.next();
      }

      return array;
   }

   static void swap(Object[] array, int i, int j) {
      Object temp = array[i];
      array[i] = array[j];
      array[j] = temp;
   }

   @CanIgnoreReturnValue
   static Object[] checkElementsNotNull(Object... array) {
      return checkElementsNotNull(array, array.length);
   }

   @CanIgnoreReturnValue
   static Object[] checkElementsNotNull(Object[] array, int length) {
      for(int i = 0; i < length; ++i) {
         checkElementNotNull(array[i], i);
      }

      return array;
   }

   @CanIgnoreReturnValue
   static Object checkElementNotNull(Object element, int index) {
      if (element == null) {
         throw new NullPointerException("at index " + index);
      } else {
         return element;
      }
   }
}
