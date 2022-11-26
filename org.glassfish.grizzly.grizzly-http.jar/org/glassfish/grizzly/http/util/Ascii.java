package org.glassfish.grizzly.http.util;

import org.glassfish.grizzly.Buffer;

public final class Ascii {
   private static final long INT_OVERFLOW_LIMIT = 214748364L;
   private static final long LONG_OVERFLOW_LIMIT = 922337203685477580L;
   static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

   public static int toUpper(int c) {
      return c >= 97 && c <= 122 ? c + 65 - 97 : c & 255;
   }

   public static void toUpper(byte[] bytes) {
      for(int i = 0; i < bytes.length; ++i) {
         byte b = bytes[i];
         bytes[i] = (byte)(b >= 97 && b <= 122 ? b + 65 - 97 : b);
      }

   }

   public static int toLower(int c) {
      return c >= 65 && c <= 90 ? c - 65 + 97 : c & 255;
   }

   public static void toLower(byte[] bytes) {
      for(int i = 0; i < bytes.length; ++i) {
         byte b = bytes[i];
         bytes[i] = (byte)(b >= 65 && b <= 90 ? b - 65 + 97 : b);
      }

   }

   public static boolean isAlpha(int c) {
      return c >= 97 && c <= 122 || c >= 65 && c <= 90;
   }

   public static boolean isUpper(int c) {
      return c >= 65 && c <= 90;
   }

   public static boolean isLower(int c) {
      return c >= 97 && c <= 122;
   }

   public static boolean isWhite(int c) {
      return c == 32 || c == 9 || c == 13 || c == 10 || c == 12 || c == 8;
   }

   public static boolean isDigit(int c) {
      return c >= 48 && c <= 57;
   }

   public static int parseInt(DataChunk dataChunk) {
      switch (dataChunk.getType()) {
         case Buffer:
            BufferChunk bc = dataChunk.getBufferChunk();
            return parseInt(bc.getBuffer(), bc.getStart(), bc.getLength());
         case String:
            return Integer.parseInt(dataChunk.toString());
         case Chars:
            CharChunk cc = dataChunk.getCharChunk();
            return parseInt(cc.getBuffer(), cc.getStart(), cc.getLength());
         default:
            throw new NullPointerException();
      }
   }

   public static int parseInt(DataChunk dataChunk, int offset, int length) {
      switch (dataChunk.getType()) {
         case Buffer:
            BufferChunk bc = dataChunk.getBufferChunk();
            return parseInt(bc.getBuffer(), bc.getStart() + offset, length);
         case String:
            return parseInt(dataChunk.toString(), offset, length);
         case Chars:
            CharChunk cc = dataChunk.getCharChunk();
            return parseInt(cc.getBuffer(), cc.getStart() + offset, cc.getLength());
         default:
            throw new NullPointerException();
      }
   }

   public static int parseInt(byte[] b, int off, int len) throws NumberFormatException {
      byte c;
      if (b != null && len > 0 && isDigit(c = b[off++])) {
         int n = c - 48;

         while(true) {
            --len;
            if (len <= 0) {
               return n;
            }

            if (!isDigit(c = b[off++])) {
               throw new NumberFormatException();
            }

            n = n * 10 + c - 48;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static int parseInt(char[] b, int off, int len) throws NumberFormatException {
      char c;
      if (b != null && len > 0 && isDigit(c = b[off++])) {
         int n = c - 48;

         while(true) {
            --len;
            if (len <= 0) {
               return n;
            }

            if (!isDigit(c = b[off++])) {
               throw new NumberFormatException();
            }

            n = n * 10 + c - 48;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static int parseInt(Buffer b, int off, int len) throws NumberFormatException {
      byte c;
      if (b != null && len > 0 && isDigit(c = b.get(off++))) {
         int n = c - 48;

         while(true) {
            --len;
            if (len <= 0) {
               return n;
            }

            if (!isDigit(c = b.get(off++))) {
               throw new NumberFormatException();
            }

            n = n * 10 + c - 48;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static int parseInt(String s, int off, int len) throws NumberFormatException {
      char c;
      if (s != null && len > 0 && isDigit(c = s.charAt(off++))) {
         int n = c - 48;

         while(true) {
            --len;
            if (len <= 0) {
               return n;
            }

            if (!isDigit(c = s.charAt(off++)) || (long)n >= 214748364L && ((long)n != 214748364L || c - 48 >= 8)) {
               throw new NumberFormatException();
            }

            n = n * 10 + c - 48;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static long parseLong(byte[] b, int off, int len) throws NumberFormatException {
      byte c;
      if (b != null && len > 0 && isDigit(c = b[off++])) {
         long n = (long)(c - 48);

         while(true) {
            --len;
            if (len <= 0) {
               return n;
            }

            if (!isDigit(c = b[off++]) || n >= 922337203685477580L && (n != 922337203685477580L || c - 48 >= 8)) {
               throw new NumberFormatException();
            }

            n = n * 10L + (long)c - 48L;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static long parseLong(char[] b, int off, int len) throws NumberFormatException {
      char c;
      if (b != null && len > 0 && isDigit(c = b[off++])) {
         long n = (long)(c - 48);

         while(true) {
            --len;
            if (len <= 0) {
               return n;
            }

            if (!isDigit(c = b[off++]) || n >= 922337203685477580L && (n != 922337203685477580L || c - 48 >= 8)) {
               throw new NumberFormatException();
            }

            n = n * 10L + (long)c - 48L;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static long parseLong(String s, int off, int len) throws NumberFormatException {
      char c;
      if (s != null && len > 0 && isDigit(c = s.charAt(off++))) {
         long n = (long)(c - 48);

         while(true) {
            --len;
            if (len <= 0) {
               return n;
            }

            if (!isDigit(c = s.charAt(off++)) || n >= 922337203685477580L && (n != 922337203685477580L || c - 48 >= 8)) {
               throw new NumberFormatException();
            }

            n = n * 10L + (long)c - 48L;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static long parseLong(Buffer b, int off, int len) throws NumberFormatException {
      byte c;
      if (b != null && len > 0 && isDigit(c = b.get(off++))) {
         long n = (long)(c - 48);

         while(true) {
            --len;
            if (len <= 0) {
               return n;
            }

            if (!isDigit(c = b.get(off++)) || n >= 922337203685477580L && (n != 922337203685477580L || c - 48 >= 8)) {
               throw new NumberFormatException();
            }

            n = n * 10L + (long)c - 48L;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static long parseLong(DataChunk dataChunk) {
      switch (dataChunk.getType()) {
         case Buffer:
            BufferChunk bc = dataChunk.getBufferChunk();
            return parseLong(bc.getBuffer(), bc.getStart(), bc.getLength());
         case String:
            return Long.parseLong(dataChunk.toString());
         case Chars:
            CharChunk cc = dataChunk.getCharChunk();
            return parseLong(cc.getBuffer(), cc.getStart(), cc.getLength());
         default:
            throw new NullPointerException();
      }
   }

   public static long parseLong(DataChunk dataChunk, int offset, int length) {
      switch (dataChunk.getType()) {
         case Buffer:
            BufferChunk bc = dataChunk.getBufferChunk();
            return parseLong(bc.getBuffer(), bc.getStart() + offset, length);
         case String:
            return parseLong(dataChunk.toString(), offset, length);
         case Chars:
            CharChunk cc = dataChunk.getCharChunk();
            return parseLong(cc.getBuffer(), cc.getStart() + offset, cc.getLength());
         default:
            throw new NullPointerException();
      }
   }

   public static void intToHexString(Buffer buffer, int i) {
      intToUnsignedString(buffer, i, 4);
   }

   public static void intToUnsignedString(Buffer buffer, int value, int shift) {
      if (value == 0) {
         buffer.put((byte)48);
      } else {
         int currentShift = 32 - shift;
         int radix = 1 << shift;
         int mask = radix - 1 << currentShift;

         for(boolean initialZeros = true; mask != 0; currentShift -= shift) {
            int digit = (value & mask) >>> currentShift;
            if (digit != 0 || !initialZeros) {
               buffer.put((byte)digits[digit]);
               initialZeros = false;
            }

            mask >>>= shift;
         }

      }
   }
}
