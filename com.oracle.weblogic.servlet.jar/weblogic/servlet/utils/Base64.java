package weblogic.servlet.utils;

public class Base64 {
   private static final char[] ENCODE_TABLE = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
   private static final char[] SAFE_URL_ENCODE_TABLE = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
   private static final byte[] DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0};

   public static String encode(byte[] a) {
      return encode(a, false);
   }

   public static String urlSafeEncode(byte[] a) {
      return encode(a, true);
   }

   private static String encode(byte[] a, boolean urlsafe) {
      int aLen = a.length;
      int numFullGroups = aLen / 3;
      int numBytesInPartialGroup = aLen - 3 * numFullGroups;
      int resultLen = 4 * ((aLen + 2) / 3);
      StringBuilder result = new StringBuilder(resultLen);
      char[] encodeTable = urlsafe ? SAFE_URL_ENCODE_TABLE : ENCODE_TABLE;
      int inCursor = 0;

      int byte0;
      int byte1;
      for(byte0 = 0; byte0 < numFullGroups; ++byte0) {
         byte1 = a[inCursor++] & 255;
         int byte1 = a[inCursor++] & 255;
         int byte2 = a[inCursor++] & 255;
         result.append(encodeTable[byte1 >> 2]);
         result.append(encodeTable[byte1 << 4 & 63 | byte1 >> 4]);
         result.append(encodeTable[byte1 << 2 & 63 | byte2 >> 6]);
         result.append(encodeTable[byte2 & 63]);
      }

      if (numBytesInPartialGroup != 0) {
         byte0 = a[inCursor++] & 255;
         result.append(encodeTable[byte0 >> 2]);
         if (numBytesInPartialGroup == 1) {
            result.append(encodeTable[byte0 << 4 & 63]);
            if (!urlsafe) {
               result.append("==");
            }
         } else {
            byte1 = a[inCursor] & 255;
            result.append(encodeTable[byte0 << 4 & 63 | byte1 >> 4]);
            result.append(encodeTable[byte1 << 2 & 63]);
            if (!urlsafe) {
               result.append('=');
            }
         }
      }

      return result.toString();
   }

   public static byte[] decode(String s) {
      int sLen = 0;

      int numGroups;
      for(numGroups = s.length(); numGroups > -1; --numGroups) {
         if (s.charAt(numGroups - 1) != '=') {
            sLen = numGroups;
            break;
         }
      }

      numGroups = sLen / 4;
      int missingBytesInLastGroup = 0;
      if (sLen != 0 && sLen % 4 != 0) {
         missingBytesInLastGroup = 4 - sLen % 4;
      }

      int partialLength = 0;
      if (missingBytesInLastGroup > 0) {
         partialLength = 3 - missingBytesInLastGroup;
      }

      byte[] result = new byte[3 * numGroups + partialLength];
      int inCursor = 0;
      int outCursor = 0;

      int ch0;
      int ch1;
      int ch2;
      for(ch0 = 0; ch0 < numGroups; ++ch0) {
         ch1 = encode(s.charAt(inCursor++));
         ch2 = encode(s.charAt(inCursor++));
         int ch2 = encode(s.charAt(inCursor++));
         int ch3 = encode(s.charAt(inCursor++));
         result[outCursor++] = (byte)(ch1 << 2 | ch2 >> 4);
         result[outCursor++] = (byte)(ch2 << 4 | ch2 >> 2);
         result[outCursor++] = (byte)(ch2 << 6 | ch3);
      }

      if (missingBytesInLastGroup != 0) {
         ch0 = encode(s.charAt(inCursor++));
         ch1 = encode(s.charAt(inCursor++));
         result[outCursor++] = (byte)(ch0 << 2 | ch1 >> 4);
         if (missingBytesInLastGroup == 1) {
            ch2 = encode(s.charAt(inCursor++));
            result[outCursor] = (byte)(ch1 << 4 | ch2 >> 2);
         }
      }

      return result;
   }

   private static int encode(char c) {
      int result = DECODE_TABLE[c];
      if (result < 0) {
         throw new IllegalArgumentException("Illegal character " + c);
      } else {
         return result;
      }
   }
}
