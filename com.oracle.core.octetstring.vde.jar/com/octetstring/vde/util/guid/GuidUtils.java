package com.octetstring.vde.util.guid;

public class GuidUtils {
   private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final int NUM_BITS_IN_BYTE = 8;
   private static final int NUM_BYTES_IN_LONG = 8;

   public static short toShort(byte[] bytes) {
      return (short)(bytes[0] << 8 | bytes[1] & 255);
   }

   public static String toHexString(byte[] byteArray) {
      return new String(toCharArray(byteArray));
   }

   private static char[] toCharArray(byte[] byteArray) {
      int arrayLength = byteArray.length;
      char[] charArray = new char[2 * arrayLength];
      int i = 0;

      for(int j = 0; i < arrayLength; ++i) {
         charArray[j++] = HEX_DIGITS[(byteArray[i] & 240) >>> 4];
         charArray[j++] = HEX_DIGITS[byteArray[i] & 15];
      }

      return charArray;
   }

   public static byte[] toByteArray(String hexString) {
      char[] hexCharacters = hexString.toCharArray();
      int numBytes = hexCharacters.length / 2;
      byte[] byteArray = new byte[numBytes];
      int hexCharIndex = 0;
      int MSHexDigit = false;
      int LSHexDigit = false;

      for(int i = 0; i < numBytes; ++i) {
         int MSHexDigit = Character.digit(hexCharacters[hexCharIndex++], 16) << 4;
         int LSHexDigit = Character.digit(hexCharacters[hexCharIndex++], 16);
         byteArray[i] = (byte)(MSHexDigit | LSHexDigit);
      }

      return byteArray;
   }

   static byte[] toByteArray(long longValue) {
      byte[] byteArray = new byte[8];
      int i = 0;

      for(int j = 7; i < 8; --j) {
         byteArray[j] = (byte)((int)longValue);
         longValue >>= 8;
         ++i;
      }

      return byteArray;
   }
}
