package org.glassfish.grizzly.http.util;

import java.io.ByteArrayOutputStream;

public final class HexUtils {
   static final boolean[] IS_HEX_DIGIT = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
   static final int[] DEC = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
   static final byte[] HEX = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

   public static byte[] convert(String digits) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      for(int i = 0; i < digits.length(); i += 2) {
         char c1 = digits.charAt(i);
         if (i + 1 >= digits.length()) {
            throw new IllegalArgumentException("hexUtil.odd");
         }

         char c2 = digits.charAt(i + 1);
         byte b = 0;
         byte b;
         if (c1 >= '0' && c1 <= '9') {
            b = (byte)(b + (c1 - 48) * 16);
         } else if (c1 >= 'a' && c1 <= 'f') {
            b = (byte)(b + (c1 - 97 + 10) * 16);
         } else {
            if (c1 < 'A' || c1 > 'F') {
               throw new IllegalArgumentException("hexUtil.bad");
            }

            b = (byte)(b + (c1 - 65 + 10) * 16);
         }

         if (c2 >= '0' && c2 <= '9') {
            b = (byte)(b + (c2 - 48));
         } else if (c2 >= 'a' && c2 <= 'f') {
            b = (byte)(b + c2 - 97 + 10);
         } else {
            if (c2 < 'A' || c2 > 'F') {
               throw new IllegalArgumentException("hexUtil.bad");
            }

            b = (byte)(b + c2 - 65 + 10);
         }

         baos.write(b);
      }

      return baos.toByteArray();
   }

   public static String convert(byte[] bytes) {
      StringBuilder sb = new StringBuilder(bytes.length * 2);

      for(int i = 0; i < bytes.length; ++i) {
         sb.append(convertDigit(bytes[i] >> 4));
         sb.append(convertDigit(bytes[i] & 15));
      }

      return sb.toString();
   }

   public static int convert2Int(byte[] hex) {
      if (hex.length < 4) {
         return 0;
      } else if (DEC[hex[0]] < 0) {
         throw new IllegalArgumentException("hexUtil.bad");
      } else {
         int len = DEC[hex[0]];
         len <<= 4;
         if (DEC[hex[1]] < 0) {
            throw new IllegalArgumentException("hexUtil.bad");
         } else {
            len += DEC[hex[1]];
            len <<= 4;
            if (DEC[hex[2]] < 0) {
               throw new IllegalArgumentException("hexUtil.bad");
            } else {
               len += DEC[hex[2]];
               len <<= 4;
               if (DEC[hex[3]] < 0) {
                  throw new IllegalArgumentException("hexUtil.bad");
               } else {
                  len += DEC[hex[3]];
                  return len;
               }
            }
         }
      }
   }

   public static int[] getDecBytes() {
      return (int[])DEC.clone();
   }

   private static char convertDigit(int value) {
      value &= 15;
      return value >= 10 ? (char)(value - 10 + 97) : (char)(value + 48);
   }

   public static boolean isHexDigit(byte c) {
      return IS_HEX_DIGIT[c];
   }

   public static boolean isHexDigit(int c) {
      return IS_HEX_DIGIT[c];
   }

   public static int hexDigit2Dec(byte hexDigit) {
      return DEC[hexDigit];
   }

   public static int hexDigit2Dec(int hexDigit) {
      return DEC[hexDigit];
   }
}
