package weblogic.cluster.messaging.internal;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public final class Hex {
   private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final int COLS_PER_ROW = 8;
   private static final int BYTES_PER_ROW = 16;

   private Hex() {
   }

   public static String asHex(byte[] barray, int len) {
      return asHex(barray, len, true);
   }

   public static String asUnicode(String s) {
      String unicode = "";

      for(int i = 0; i < s.length(); ++i) {
         unicode = unicode + "\\u" + asHex(s.charAt(i) >> 8) + asHex(s.charAt(i));
      }

      return unicode;
   }

   public static String asUnicode(char c) {
      StringBuffer sb = new StringBuffer();
      sb.append("\\u");
      sb.append(asHex(c >> 8) + asHex(c));
      return sb.toString();
   }

   public static String asHex(String s, String encoding) throws UnsupportedEncodingException {
      byte[] b = s.getBytes(encoding);
      return asHex(b);
   }

   public static byte[] asHexBytes(byte[] barray, int len, boolean prefix) {
      int loopLen = Math.min(len, barray.length);
      int j = 0;
      byte[] buf;
      if (prefix) {
         buf = new byte[loopLen * 2 + 2];
         buf[0] = 48;
         buf[1] = 120;
         j += 2;
      } else {
         buf = new byte[loopLen * 2];
      }

      for(int i = 0; i < loopLen; ++i) {
         buf[j++] = (byte)HEX_CHARS[(barray[i] & 240) >> 4];
         buf[j++] = (byte)HEX_CHARS[(barray[i] & 15) >> 0];
      }

      return buf;
   }

   public static String asHex(byte[] barray, int len, boolean prefix) {
      return new String(asHexBytes(barray, len, prefix), 0);
   }

   public static byte[] fromHexString(byte[] barray, int len) {
      int i = 0;
      if (barray[0] == 48 && (barray[1] == 120 || barray[1] == 88)) {
         i += 2;
         len -= 2;
      }

      int outlen = len / 2;
      byte[] out = new byte[outlen];

      for(int j = 0; j < outlen; ++j) {
         out[j] = (byte)(hexValueOf(barray[i++]) << 4 | hexValueOf(barray[i++]));
      }

      return out;
   }

   public static byte[] fromHexString(String hexString) {
      byte[] bytes;
      try {
         bytes = hexString.getBytes("US-ASCII");
      } catch (UnsupportedEncodingException var4) {
         bytes = new byte[hexString.length()];

         for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte)hexString.charAt(i);
         }
      }

      return fromHexString(bytes, bytes.length);
   }

   public static String asHex(int b) {
      char[] buf = new char[]{HEX_CHARS[(b & 240) >> 4], HEX_CHARS[(b & 15) >> 0]};
      return new String(buf);
   }

   public static String asHex(byte[] barray) {
      return asHex(barray, barray.length);
   }

   public static int hexValueOf(int c) {
      if (c >= 48 && c <= 57) {
         return c - 48;
      } else if (c >= 97 && c <= 102) {
         return c - 97 + 10;
      } else {
         return c >= 65 && c <= 70 ? c - 65 + 10 : 0;
      }
   }

   public static String dump(byte[] bytes) {
      return bytes == null ? "" + Arrays.toString(bytes) : dump(bytes, 0, bytes.length);
   }

   public static String dump(byte[] bytes, int offset, int len) {
      if (offset < 0) {
         offset = 0;
      }

      int end = Math.min(bytes.length, offset + len);
      int displayStart = offset & -16;
      int displayEnd = end + 15 & -16;
      StringBuffer out = new StringBuffer();
      int rowStart = displayStart;

      for(int i = displayStart; i < displayEnd; ++i) {
         if (i % 16 == 0) {
            lineLabel(out, i);
            rowStart = i;
         }

         if (i >= offset && i < end) {
            out.append(asHex(bytes[i]));
         } else {
            out.append("  ");
         }

         if (i % 2 == 1) {
            out.append(' ');
         }

         if (i % 16 == 15) {
            out.append("  ");

            for(int j = rowStart; j < rowStart + 16; ++j) {
               if (j >= offset && j < end) {
                  out.append(toPrint(bytes[j]));
               } else {
                  out.append(' ');
               }
            }

            out.append('\n');
         }
      }

      return out.toString();
   }

   public static final boolean isHexChar(int c) {
      switch (c) {
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
            return true;
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         default:
            return false;
      }
   }

   private static char toPrint(byte b) {
      return b >= 32 && b <= 126 ? (char)b : '.';
   }

   private static void lineLabel(StringBuffer out, int i) {
      String istring = (new Integer(i)).toString();
      StringBuffer sb;
      if (istring.length() <= 5) {
         sb = new StringBuffer("    ");
         sb.insert(5 - istring.length(), istring);
         sb.setLength(5);
      } else {
         sb = new StringBuffer(istring);
      }

      out.append(sb);
      out.append(": ");
   }
}
