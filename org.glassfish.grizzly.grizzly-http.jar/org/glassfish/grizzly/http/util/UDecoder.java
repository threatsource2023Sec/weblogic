package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.IOException;

public final class UDecoder {
   private static final String ALLOW_ENCODED_SLASH_NAME = "org.glassfish.grizzly.util.buf.UDecoder.ALLOW_ENCODED_SLASH";
   public static final boolean ALLOW_ENCODED_SLASH = Boolean.valueOf(System.getProperty("org.glassfish.grizzly.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "false"));
   private boolean allowEncodedSlash;

   public UDecoder() {
      this(ALLOW_ENCODED_SLASH);
   }

   public UDecoder(boolean allowEncodedSlash) {
      this.allowEncodedSlash = allowEncodedSlash;
   }

   public void convert(ByteChunk mb) throws IOException {
      this.convert(mb, true);
   }

   public void convert(ByteChunk mb, boolean query) throws IOException {
      convert(mb, query, this.allowEncodedSlash);
   }

   public static void convert(ByteChunk mb, boolean query, boolean allowEncodedSlash) throws IOException {
      int start = mb.getStart();
      byte[] buff = mb.getBytes();
      int end = mb.getEnd();
      int idx = ByteChunk.indexOf(buff, start, end, '%');
      int idx2 = -1;
      if (query) {
         idx2 = ByteChunk.indexOf(buff, start, end, '+');
      }

      if (idx >= 0 || idx2 >= 0) {
         if (idx2 >= 0 && idx2 < idx) {
            idx = idx2;
         }

         if (idx < 0) {
            idx = idx2;
         }

         boolean noSlash = !allowEncodedSlash && !query;

         for(int j = idx; j < end; ++idx) {
            if (buff[j] == 43 && query) {
               buff[idx] = 32;
            } else if (buff[j] != 37) {
               buff[idx] = buff[j];
            } else {
               if (j + 2 >= end) {
                  throw new CharConversionException("EOF");
               }

               byte b1 = buff[j + 1];
               byte b2 = buff[j + 2];
               if (!isHexDigit(b1) || !isHexDigit(b2)) {
                  throw new CharConversionException("isHexDigit");
               }

               j += 2;
               int res = x2c(b1, b2);
               if (noSlash && res == 47) {
                  throw new CharConversionException("Encoded slashes are not allowed by default.  To enable encodedslashes, set the property org.glassfish.grizzly.util.buf.UDecoder.ALLOW_ENCODED_SLASH to true.");
               }

               buff[idx] = (byte)res;
            }

            ++j;
         }

         mb.setEnd(idx);
      }
   }

   public void convert(CharChunk mb) throws IOException {
      convert(mb, true);
   }

   public static void convert(CharChunk mb, boolean query) throws IOException {
      int start = mb.getStart();
      char[] buff = mb.getBuffer();
      int cend = mb.getEnd();
      int idx = CharChunk.indexOf(buff, start, cend, '%');
      int idx2 = -1;
      if (query) {
         idx2 = CharChunk.indexOf(buff, start, cend, '+');
      }

      if (idx >= 0 || idx2 >= 0) {
         if (idx2 >= 0 && idx2 < idx) {
            idx = idx2;
         }

         if (idx < 0) {
            idx = idx2;
         }

         for(int j = idx; j < cend; ++idx) {
            char c = buff[j];
            if (c == '+' && query) {
               buff[idx] = ' ';
            } else if (c != '%') {
               buff[idx] = c;
            } else {
               if (j + 2 >= cend) {
                  throw new CharConversionException("EOF");
               }

               char b1 = buff[j + 1];
               char b2 = buff[j + 2];
               if (!isHexDigit(b1) || !isHexDigit(b2)) {
                  throw new CharConversionException("isHexDigit");
               }

               j += 2;
               int res = x2c(b1, b2);
               buff[idx] = (char)res;
            }

            ++j;
         }

         mb.setEnd(idx);
      }
   }

   public void convert(MessageBytes mb) throws IOException {
      this.convert(mb, true);
   }

   public void convert(MessageBytes mb, boolean query) throws IOException {
      convert(mb, query, this.allowEncodedSlash);
   }

   public static void convert(MessageBytes mb, boolean query, boolean allowEncodingSlash) throws IOException {
      switch (mb.getType()) {
         case 1:
            String strValue = mb.toString();
            if (strValue == null) {
               return;
            }

            mb.setString(convert(strValue, query));
            break;
         case 2:
            ByteChunk bytesC = mb.getByteChunk();
            convert(bytesC, query, allowEncodingSlash);
            break;
         case 3:
            CharChunk charC = mb.getCharChunk();
            convert(charC, query);
      }

   }

   public static String convert(String str) {
      return convert(str, true);
   }

   public static String convert(String str, boolean query) {
      if (str == null) {
         return null;
      } else if ((!query || str.indexOf(43) < 0) && str.indexOf(37) < 0) {
         return str;
      } else {
         StringBuilder dec = new StringBuilder();
         int strPos = 0;
         int strLen = str.length();
         dec.ensureCapacity(str.length());

         while(strPos < strLen) {
            int laPos;
            char metaChar;
            for(laPos = strPos; laPos < strLen; ++laPos) {
               metaChar = str.charAt(laPos);
               if (metaChar == '+' && query || metaChar == '%') {
                  break;
               }
            }

            if (laPos > strPos) {
               dec.append(str.substring(strPos, laPos));
               strPos = laPos;
            }

            if (strPos >= strLen) {
               break;
            }

            metaChar = str.charAt(strPos);
            if (metaChar == '+') {
               dec.append(' ');
               ++strPos;
            } else if (metaChar == '%') {
               dec.append((char)Integer.parseInt(str.substring(strPos + 1, strPos + 3), 16));
               strPos += 3;
            }
         }

         return dec.toString();
      }
   }

   private static boolean isHexDigit(int c) {
      return c >= 48 && c <= 57 || c >= 97 && c <= 102 || c >= 65 && c <= 70;
   }

   private static int x2c(byte b1, byte b2) {
      int digit = b1 >= 65 ? (b1 & 223) - 65 + 10 : b1 - 48;
      digit *= 16;
      digit += b2 >= 65 ? (b2 & 223) - 65 + 10 : b2 - 48;
      return digit;
   }

   private static int x2c(char b1, char b2) {
      int digit = b1 >= 'A' ? (b1 & 223) - 65 + 10 : b1 - 48;
      digit *= 16;
      digit += b2 >= 'A' ? (b2 & 223) - 65 + 10 : b2 - 48;
      return digit;
   }

   public boolean isAllowEncodedSlash() {
      return this.allowEncodedSlash;
   }

   public void setAllowEncodedSlash(boolean allowEncodedSlash) {
      this.allowEncodedSlash = allowEncodedSlash;
   }
}
