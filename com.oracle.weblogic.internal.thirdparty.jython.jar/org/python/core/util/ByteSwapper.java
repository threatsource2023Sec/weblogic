package org.python.core.util;

import java.lang.reflect.Array;

public class ByteSwapper {
   public static void swap(Object array) {
      Class arrayType = array.getClass().getComponentType();
      if (arrayType.isPrimitive()) {
         if (arrayType == Boolean.TYPE) {
            return;
         }

         if (arrayType == Byte.TYPE) {
            return;
         }

         if (arrayType == Character.TYPE) {
            return;
         }

         if (arrayType == Short.TYPE) {
            swapShortArray(array);
         } else if (arrayType == Integer.TYPE) {
            swapIntegerArray(array);
         } else if (arrayType == Long.TYPE) {
            swapLongArray(array);
         } else if (arrayType == Float.TYPE) {
            swapFloatArray(array);
         } else if (arrayType == Double.TYPE) {
            swapDoubleArray(array);
         }
      }

   }

   private static void swapDoubleArray(Object array) {
      int len = Array.getLength(array);

      for(int i = 0; i < len; ++i) {
         double dtmp = Array.getDouble(array, i);
         long tmp = Double.doubleToLongBits(dtmp);
         long b1 = tmp >> 0 & 255L;
         long b2 = tmp >> 8 & 255L;
         long b3 = tmp >> 16 & 255L;
         long b4 = tmp >> 24 & 255L;
         long b5 = tmp >> 32 & 255L;
         long b6 = tmp >> 40 & 255L;
         long b7 = tmp >> 48 & 255L;
         long b8 = tmp >> 56 & 255L;
         tmp = b1 << 56 | b2 << 48 | b3 << 40 | b4 << 32 | b5 << 24 | b6 << 16 | b7 << 8 | b8 << 0;
         dtmp = Double.longBitsToDouble(tmp);
         Array.setDouble(array, i, dtmp);
      }

   }

   private static void swapFloatArray(Object array) {
      int len = Array.getLength(array);

      for(int i = 0; i < len; ++i) {
         float ftmp = Array.getFloat(array, i);
         int tmp = Float.floatToIntBits(ftmp);
         int b1 = tmp >> 0 & 255;
         int b2 = tmp >> 8 & 255;
         int b3 = tmp >> 16 & 255;
         int b4 = tmp >> 24 & 255;
         tmp = b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
         ftmp = Float.intBitsToFloat(tmp);
         Array.setFloat(array, i, ftmp);
      }

   }

   private static void swapIntegerArray(Object array) {
      int len = Array.getLength(array);

      for(int i = 0; i < len; ++i) {
         int tmp = Array.getInt(array, i);
         int b1 = tmp >> 0 & 255;
         int b2 = tmp >> 8 & 255;
         int b3 = tmp >> 16 & 255;
         int b4 = tmp >> 24 & 255;
         tmp = b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
         Array.setInt(array, i, tmp);
      }

   }

   private static void swapLongArray(Object array) {
      int len = Array.getLength(array);

      for(int i = 0; i < len; ++i) {
         long tmp = Array.getLong(array, i);
         long b1 = tmp >> 0 & 255L;
         long b2 = tmp >> 8 & 255L;
         long b3 = tmp >> 16 & 255L;
         long b4 = tmp >> 24 & 255L;
         long b5 = tmp >> 32 & 255L;
         long b6 = tmp >> 40 & 255L;
         long b7 = tmp >> 48 & 255L;
         long b8 = tmp >> 56 & 255L;
         tmp = b1 << 56 | b2 << 48 | b3 << 40 | b4 << 32 | b5 << 24 | b6 << 16 | b7 << 8 | b8 << 0;
         Array.setLong(array, i, tmp);
      }

   }

   private static void swapShortArray(Object array) {
      int len = Array.getLength(array);

      for(int i = 0; i < len; ++i) {
         short tmp = Array.getShort(array, i);
         int b1 = tmp >> 0 & 255;
         int b2 = tmp >> 8 & 255;
         tmp = (short)(b1 << 8 | b2 << 0);
         Array.setShort(array, i, tmp);
      }

   }
}
