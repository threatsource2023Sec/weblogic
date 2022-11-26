package org.glassfish.soteria.identitystores.hash;

public class PasswordHashCompare {
   public static boolean compareBytes(byte[] array1, byte[] array2) {
      int diff = array1.length ^ array2.length;

      for(int i = 0; i < array1.length; ++i) {
         diff |= array1[i] ^ array2[i % array2.length];
      }

      return diff == 0;
   }

   public static boolean compareChars(char[] array1, char[] array2) {
      int diff = array1.length ^ array2.length;

      for(int i = 0; i < array1.length; ++i) {
         diff |= array1[i] ^ array2[i % array2.length];
      }

      return diff == 0;
   }
}
