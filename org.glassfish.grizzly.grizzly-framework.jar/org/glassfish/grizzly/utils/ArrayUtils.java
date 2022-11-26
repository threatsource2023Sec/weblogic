package org.glassfish.grizzly.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

public final class ArrayUtils {
   public static int binarySearch(int[] a, int fromIndex, int toIndex, int key) {
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         int midVal = a[mid];
         if (midVal < key) {
            low = mid + 1;
         } else {
            if (midVal <= key) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return low;
   }

   public static Object[] addUnique(Object[] array, Object element) {
      return addUnique(array, element, true);
   }

   public static Object[] addUnique(Object[] array, Object element, boolean replaceElementIfEquals) {
      int idx = indexOf(array, element);
      if (idx == -1) {
         int length = array.length;
         Object[] newArray = Arrays.copyOf(array, length + 1);
         newArray[length] = element;
         return newArray;
      } else {
         if (replaceElementIfEquals) {
            array[idx] = element;
         }

         return array;
      }
   }

   public static Object[] remove(Object[] array, Object element) {
      int idx = indexOf(array, element);
      if (idx != -1) {
         int length = array.length;
         if (length == 1) {
            return null;
         } else {
            Object[] newArray = (Object[])((Object[])Array.newInstance(array.getClass().getComponentType(), length - 1));
            if (idx > 0) {
               System.arraycopy(array, 0, newArray, 0, idx);
            }

            if (idx < length - 1) {
               System.arraycopy(array, idx + 1, newArray, idx, length - idx - 1);
            }

            return newArray;
         }
      } else {
         return array;
      }
   }

   public static int indexOf(Object[] array, Object element) {
      for(int i = 0; i < array.length; ++i) {
         if (element.equals(array[i])) {
            return i;
         }
      }

      return -1;
   }
}
