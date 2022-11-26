package org.apache.openjpa.lib.util;

public class Base16Encoder {
   private static final char[] HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

   public static String encode(byte[] byteArray) {
      StringBuffer hexBuffer = new StringBuffer(byteArray.length * 2);

      for(int i = 0; i < byteArray.length; ++i) {
         for(int j = 1; j >= 0; --j) {
            hexBuffer.append(HEX[byteArray[i] >> j * 4 & 15]);
         }
      }

      return hexBuffer.toString();
   }

   public static byte[] decode(String s) {
      int len = s.length();
      byte[] r = new byte[len / 2];

      for(int i = 0; i < r.length; ++i) {
         int digit1 = s.charAt(i * 2);
         int digit2 = s.charAt(i * 2 + 1);
         if (digit1 >= 48 && digit1 <= 57) {
            digit1 -= 48;
         } else if (digit1 >= 65 && digit1 <= 70) {
            digit1 -= 55;
         }

         if (digit2 >= 48 && digit2 <= 57) {
            digit2 -= 48;
         } else if (digit2 >= 65 && digit2 <= 70) {
            digit2 -= 55;
         }

         r[i] = (byte)((digit1 << 4) + digit2);
      }

      return r;
   }
}
