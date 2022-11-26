package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.ValueMapping;

class PrimitiveWrapperArrays {
   public static Object toObjectValue(ValueMapping vm, char[] array) {
      if (array == null) {
         return null;
      } else if (vm.getType().getComponentType() != Character.class) {
         return array;
      } else {
         Character[] objectArray = new Character[array.length];

         for(int i = 0; i < array.length; ++i) {
            objectArray[i] = new Character(array[i]);
         }

         return objectArray;
      }
   }

   public static char[] toCharArray(Object ob) {
      if (ob instanceof Character[]) {
         Character[] charOb = (Character[])((Character[])ob);
         char[] chars = new char[charOb.length];

         for(int i = 0; i < charOb.length; ++i) {
            chars[i] = charOb[i] == null ? 0 : charOb[i];
         }

         return chars;
      } else {
         return (char[])((char[])ob);
      }
   }

   public static Object toObjectValue(ValueMapping vm, byte[] array) {
      if (array == null) {
         return null;
      } else if (vm.getType().getComponentType() != Byte.class) {
         return array;
      } else {
         Byte[] objectArray = new Byte[array.length];

         for(int i = 0; i < array.length; ++i) {
            objectArray[i] = new Byte(array[i]);
         }

         return objectArray;
      }
   }

   public static byte[] toByteArray(Object ob) {
      if (ob instanceof Byte[]) {
         Byte[] byteOb = (Byte[])((Byte[])ob);
         byte[] bytes = new byte[byteOb.length];

         for(int i = 0; i < byteOb.length; ++i) {
            bytes[i] = byteOb[i] == null ? 0 : byteOb[i];
         }

         return bytes;
      } else {
         return (byte[])((byte[])ob);
      }
   }
}
