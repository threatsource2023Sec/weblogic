package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;

final class Util {
   private Util() {
   }

   static List asArrayList(int length) {
      List list = new ArrayList(length);

      for(int i = 0; i < length; ++i) {
         list.add((Object)null);
      }

      return list;
   }

   static List asArrayList(Object[] array) {
      if (array == null) {
         return new ArrayList();
      } else {
         ArrayList list = new ArrayList(array.length);
         Object[] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object t = var2[var4];
            list.add(t);
         }

         return list;
      }
   }

   static List asArrayList(byte[] byteArray) {
      if (byteArray == null) {
         return new ArrayList();
      } else {
         ArrayList byteList = new ArrayList(byteArray.length);
         byte[] var2 = byteArray;
         int var3 = byteArray.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            byteList.add(b);
         }

         return byteList;
      }
   }

   static List asArrayList(boolean[] booleanArray) {
      if (booleanArray == null) {
         return new ArrayList();
      } else {
         ArrayList booleanList = new ArrayList(booleanArray.length);
         boolean[] var2 = booleanArray;
         int var3 = booleanArray.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            boolean b = var2[var4];
            booleanList.add(b);
         }

         return booleanList;
      }
   }

   static List asArrayList(short[] shortArray) {
      if (shortArray == null) {
         return new ArrayList();
      } else {
         ArrayList shortList = new ArrayList(shortArray.length);
         short[] var2 = shortArray;
         int var3 = shortArray.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            short s = var2[var4];
            shortList.add(s);
         }

         return shortList;
      }
   }

   static List asArrayList(char[] charArray) {
      if (charArray == null) {
         return new ArrayList();
      } else {
         ArrayList charList = new ArrayList(charArray.length);
         char[] var2 = charArray;
         int var3 = charArray.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            charList.add(c);
         }

         return charList;
      }
   }

   static List asArrayList(int[] intArray) {
      if (intArray == null) {
         return new ArrayList();
      } else {
         ArrayList intList = new ArrayList(intArray.length);
         int[] var2 = intArray;
         int var3 = intArray.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int i = var2[var4];
            intList.add(i);
         }

         return intList;
      }
   }

   static List asArrayList(float[] floatArray) {
      if (floatArray == null) {
         return new ArrayList();
      } else {
         ArrayList floatList = new ArrayList(floatArray.length);
         float[] var2 = floatArray;
         int var3 = floatArray.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            float f = var2[var4];
            floatList.add(f);
         }

         return floatList;
      }
   }

   static List asArrayList(long[] longArray) {
      if (longArray == null) {
         return new ArrayList();
      } else {
         ArrayList longList = new ArrayList(longArray.length);
         long[] var2 = longArray;
         int var3 = longArray.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            long l = var2[var4];
            longList.add(l);
         }

         return longList;
      }
   }

   static List asArrayList(double[] doubleArray) {
      if (doubleArray == null) {
         return new ArrayList();
      } else {
         ArrayList doubleList = new ArrayList(doubleArray.length);
         double[] var2 = doubleArray;
         int var3 = doubleArray.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            double d = var2[var4];
            doubleList.add(d);
         }

         return doubleList;
      }
   }

   static List asArrayList(int length, Object[] array) {
      List list = new ArrayList(length);

      for(int i = 0; i < length; ++i) {
         list.add(array[i]);
      }

      return list;
   }
}
