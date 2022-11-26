package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.UnsupportedEncodingException;
import org.glassfish.grizzly.Buffer;

public class URLDecoder {
   public static void decode(DataChunk dataChunk) throws CharConversionException {
      decode(dataChunk, true);
   }

   public static void decode(DataChunk dataChunk, boolean allowEncodedSlash) throws CharConversionException {
      decodeAscii(dataChunk, dataChunk, allowEncodedSlash);
   }

   public static void decode(DataChunk srcDataChunk, DataChunk dstDataChunk, boolean allowEncodedSlash) throws CharConversionException {
      decodeAscii(srcDataChunk, dstDataChunk, allowEncodedSlash);
   }

   public static void decode(DataChunk srcDataChunk, DataChunk dstDataChunk, boolean allowEncodedSlash, String enc) throws CharConversionException {
      ByteChunk dstByteChunk;
      switch (srcDataChunk.getType()) {
         case Bytes:
            ByteChunk srcByteChunk = srcDataChunk.getByteChunk();
            dstByteChunk = dstDataChunk.getByteChunk();
            if (dstByteChunk != srcByteChunk) {
               dstByteChunk.allocate(srcByteChunk.getLength(), -1);
            }

            decode(srcByteChunk, dstByteChunk, allowEncodedSlash);
            return;
         case Buffer:
            BufferChunk srcBufferChunk = srcDataChunk.getBufferChunk();
            if (dstDataChunk != srcDataChunk) {
               dstByteChunk = dstDataChunk.getByteChunk();
               dstByteChunk.allocate(srcBufferChunk.getLength(), -1);
               decode(srcBufferChunk, dstByteChunk, allowEncodedSlash);
            } else {
               decode(srcBufferChunk, srcBufferChunk, allowEncodedSlash);
            }

            return;
         case String:
            dstDataChunk.setString(decode(srcDataChunk.toString(), allowEncodedSlash, enc));
            return;
         case Chars:
            CharChunk srcCharChunk = srcDataChunk.getCharChunk();
            CharChunk dstCharChunk = dstDataChunk.getCharChunk();
            if (dstDataChunk != srcDataChunk) {
               dstCharChunk.ensureCapacity(srcCharChunk.getLength());
               decode(srcCharChunk, dstCharChunk, allowEncodedSlash, enc);
            } else {
               decode(srcCharChunk, srcCharChunk, allowEncodedSlash, enc);
            }

            dstDataChunk.setChars(dstCharChunk.getChars(), dstCharChunk.getStart(), dstCharChunk.getEnd());
            return;
         default:
            throw new NullPointerException();
      }
   }

   public static void decodeAscii(DataChunk srcDataChunk, DataChunk dstDataChunk, boolean allowEncodedSlash) throws CharConversionException {
      ByteChunk dstByteChunk;
      switch (srcDataChunk.getType()) {
         case Bytes:
            ByteChunk srcByteChunk = srcDataChunk.getByteChunk();
            dstByteChunk = dstDataChunk.getByteChunk();
            if (dstByteChunk != srcByteChunk) {
               dstByteChunk.allocate(srcByteChunk.getLength(), -1);
            }

            decode(srcByteChunk, dstByteChunk, allowEncodedSlash);
            return;
         case Buffer:
            BufferChunk srcBufferChunk = srcDataChunk.getBufferChunk();
            if (dstDataChunk != srcDataChunk) {
               dstByteChunk = dstDataChunk.getByteChunk();
               dstByteChunk.allocate(srcBufferChunk.getLength(), -1);
               decode(srcBufferChunk, dstByteChunk, allowEncodedSlash);
            } else {
               decode(srcBufferChunk, srcBufferChunk, allowEncodedSlash);
            }

            return;
         case String:
            dstDataChunk.setString(decodeAscii(srcDataChunk.toString(), allowEncodedSlash));
            return;
         case Chars:
            CharChunk srcCharChunk = srcDataChunk.getCharChunk();
            CharChunk dstCharChunk = dstDataChunk.getCharChunk();
            if (dstDataChunk != srcDataChunk) {
               dstCharChunk.ensureCapacity(srcCharChunk.getLength());
               decodeAscii(srcCharChunk, dstCharChunk, allowEncodedSlash);
            } else {
               decodeAscii(srcCharChunk, srcCharChunk, allowEncodedSlash);
            }

            dstDataChunk.setChars(dstCharChunk.getChars(), dstCharChunk.getStart(), dstCharChunk.getEnd());
            return;
         default:
            throw new NullPointerException();
      }
   }

   public static void decode(ByteChunk byteChunk, boolean allowEncodedSlash) throws CharConversionException {
      decode(byteChunk, byteChunk, allowEncodedSlash);
   }

   public static void decode(ByteChunk srcByteChunk, ByteChunk dstByteChunk, boolean allowEncodedSlash) throws CharConversionException {
      byte[] srcBuffer = srcByteChunk.getBuffer();
      int srcStart = srcByteChunk.getStart();
      int srcEnd = srcByteChunk.getEnd();
      byte[] dstBuffer = dstByteChunk.getBuffer();
      int idx = dstByteChunk.getStart();

      for(int j = srcStart; j < srcEnd; ++idx) {
         byte b = srcBuffer[j];
         if (b == 43) {
            dstBuffer[idx] = 32;
         } else if (b != 37) {
            dstBuffer[idx] = b;
         } else {
            if (j + 2 >= srcEnd) {
               throw new IllegalStateException("Unexpected termination");
            }

            byte b1 = srcBuffer[j + 1];
            byte b2 = srcBuffer[j + 2];
            if (!HexUtils.isHexDigit(b1) || !HexUtils.isHexDigit(b2)) {
               throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + (char)b1 + "" + (char)b2);
            }

            j += 2;
            int res = x2c(b1, b2);
            if (!allowEncodedSlash && res == 47) {
               throw new CharConversionException("Encoded slashes are not allowed");
            }

            dstBuffer[idx] = (byte)res;
         }

         ++j;
      }

      dstByteChunk.setEnd(idx);
   }

   public static void decode(BufferChunk srcBufferChunk, ByteChunk dstByteChunk, boolean allowEncodedSlash) throws CharConversionException {
      Buffer srcBuffer = srcBufferChunk.getBuffer();
      int srcStart = srcBufferChunk.getStart();
      int srcEnd = srcBufferChunk.getEnd();
      byte[] dstBuffer = dstByteChunk.getBuffer();
      int idx = dstByteChunk.getStart();

      for(int j = srcStart; j < srcEnd; ++idx) {
         byte b = srcBuffer.get(j);
         if (b == 43) {
            dstBuffer[idx] = 32;
         } else if (b != 37) {
            dstBuffer[idx] = b;
         } else {
            if (j + 2 >= srcEnd) {
               throw new IllegalStateException("Unexpected termination");
            }

            byte b1 = srcBuffer.get(j + 1);
            byte b2 = srcBuffer.get(j + 2);
            if (!HexUtils.isHexDigit(b1) || !HexUtils.isHexDigit(b2)) {
               throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + (char)b1 + "" + (char)b2);
            }

            j += 2;
            int res = x2c(b1, b2);
            if (!allowEncodedSlash && res == 47) {
               throw new CharConversionException("Encoded slashes are not allowed");
            }

            dstBuffer[idx] = (byte)res;
         }

         ++j;
      }

      dstByteChunk.setEnd(idx);
   }

   public static void decode(ByteChunk srcByteChunk, BufferChunk dstBufferChunk, boolean allowEncodedSlash) throws CharConversionException {
      byte[] srcBuffer = srcByteChunk.getBuffer();
      int srcStart = srcByteChunk.getStart();
      int srcEnd = srcByteChunk.getEnd();
      Buffer dstBuffer = dstBufferChunk.getBuffer();
      int idx = dstBufferChunk.getStart();

      for(int j = srcStart; j < srcEnd; ++idx) {
         byte b = srcBuffer[j];
         if (b == 43) {
            dstBuffer.put(idx, (byte)32);
         } else if (b != 37) {
            dstBuffer.put(idx, b);
         } else {
            if (j + 2 >= srcEnd) {
               throw new IllegalStateException("Unexpected termination");
            }

            byte b1 = srcBuffer[j + 1];
            byte b2 = srcBuffer[j + 2];
            if (!HexUtils.isHexDigit(b1) || !HexUtils.isHexDigit(b2)) {
               throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + (char)b1 + "" + (char)b2);
            }

            j += 2;
            int res = x2c(b1, b2);
            if (!allowEncodedSlash && res == 47) {
               throw new CharConversionException("Encoded slashes are not allowed");
            }

            dstBuffer.put(idx, (byte)res);
         }

         ++j;
      }

      dstBufferChunk.setEnd(idx);
   }

   public static void decode(BufferChunk bufferChunk, boolean allowEncodedSlash) throws CharConversionException {
      decode(bufferChunk, bufferChunk, allowEncodedSlash);
   }

   public static void decode(BufferChunk srcBufferChunk, BufferChunk dstBufferChunk, boolean allowEncodedSlash) throws CharConversionException {
      Buffer srcBuffer = srcBufferChunk.getBuffer();
      int srcStart = srcBufferChunk.getStart();
      int srcEnd = srcBufferChunk.getEnd();
      Buffer dstBuffer = dstBufferChunk.getBuffer();
      int idx = dstBufferChunk.getStart();

      for(int j = srcStart; j < srcEnd; ++idx) {
         byte b = srcBuffer.get(j);
         if (b == 43) {
            dstBuffer.put(idx, (byte)32);
         } else if (b != 37) {
            dstBuffer.put(idx, b);
         } else {
            if (j + 2 >= srcEnd) {
               throw new IllegalStateException("Unexpected termination");
            }

            byte b1 = srcBuffer.get(j + 1);
            byte b2 = srcBuffer.get(j + 2);
            if (!HexUtils.isHexDigit(b1) || !HexUtils.isHexDigit(b2)) {
               throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + (char)b1 + "" + (char)b2);
            }

            j += 2;
            int res = x2c(b1, b2);
            if (!allowEncodedSlash && res == 47) {
               throw new CharConversionException("Encoded slashes are not allowed");
            }

            dstBuffer.put(idx, (byte)res);
         }

         ++j;
      }

      dstBufferChunk.setEnd(idx);
   }

   public static void decode(CharChunk charChunk, boolean allowEncodedSlash) throws CharConversionException {
      decodeAscii(charChunk, charChunk, allowEncodedSlash);
   }

   public static void decode(CharChunk srcCharChunk, CharChunk dstCharChunk, boolean allowEncodedSlash) throws CharConversionException {
      decodeAscii(srcCharChunk, dstCharChunk, allowEncodedSlash);
   }

   public static void decode(CharChunk srcCharChunk, CharChunk dstCharChunk, boolean allowEncodedSlash, String enc) throws CharConversionException {
      byte[] bytes = null;
      char[] srcBuffer = srcCharChunk.getBuffer();
      int srcStart = srcCharChunk.getStart();
      int srcEnd = srcCharChunk.getEnd();
      int srcLen = srcEnd - srcStart;
      char[] dstBuffer = dstCharChunk.getBuffer();
      int idx = dstCharChunk.getStart();
      int j = srcStart;

      while(true) {
         while(true) {
            while(j < srcEnd) {
               char c = srcBuffer[j];
               if (c != '+') {
                  if (c == '%') {
                     try {
                        if (bytes == null) {
                           bytes = new byte[(srcLen - j) / 3];
                        }

                        int pos = 0;

                        while(true) {
                           if (j + 2 < srcLen && c == '%') {
                              char c1 = srcBuffer[j + 1];
                              char c2 = srcBuffer[j + 2];
                              if (HexUtils.isHexDigit((int)c1) && HexUtils.isHexDigit((int)c2)) {
                                 int v = x2c((int)c1, (int)c2);
                                 if (v < 0) {
                                    throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                                 }

                                 bytes[pos++] = (byte)v;
                                 j += 3;
                                 if (j < srcLen) {
                                    c = srcBuffer[j];
                                 }
                                 continue;
                              }

                              throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + c1 + "" + c2);
                           }

                           if (j < srcLen && c == '%') {
                              throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
                           }

                           String decodedChunk = new String(bytes, 0, pos, enc);
                           if (!allowEncodedSlash && decodedChunk.indexOf(47) != -1) {
                              throw new CharConversionException("Encoded slashes are not allowed");
                           }

                           int chunkLen = decodedChunk.length();
                           decodedChunk.getChars(0, chunkLen, dstBuffer, idx);
                           idx += chunkLen;
                           break;
                        }
                     } catch (NumberFormatException var17) {
                        throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + var17.getMessage());
                     } catch (UnsupportedEncodingException var18) {
                     }
                  } else {
                     dstBuffer[idx++] = c;
                     ++j;
                  }
               } else {
                  dstBuffer[idx++] = ' ';
                  ++j;
               }
            }

            dstCharChunk.setEnd(idx);
            return;
         }
      }
   }

   public static void decodeAscii(CharChunk srcCharChunk, CharChunk dstCharChunk, boolean allowEncodedSlash) throws CharConversionException {
      char[] srcBuffer = srcCharChunk.getBuffer();
      int srcStart = srcCharChunk.getStart();
      int srcEnd = srcCharChunk.getEnd();
      char[] dstBuffer = dstCharChunk.getBuffer();
      int idx = dstCharChunk.getStart();

      for(int j = srcStart; j < srcEnd; ++idx) {
         char c = srcBuffer[j];
         if (c == '+') {
            dstBuffer[idx] = ' ';
         } else if (c != '%') {
            dstBuffer[idx] = c;
         } else {
            if (j + 2 >= srcEnd) {
               throw new IllegalStateException("Unexpected termination");
            }

            char c1 = srcBuffer[j + 1];
            char c2 = srcBuffer[j + 2];
            if (!HexUtils.isHexDigit((int)c1) || !HexUtils.isHexDigit((int)c2)) {
               throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + c1 + "" + c2);
            }

            j += 2;
            int res = x2c((int)c1, (int)c2);
            if (!allowEncodedSlash && res == 47) {
               throw new CharConversionException("Encoded slashes are not allowed");
            }

            dstBuffer[idx] = (char)res;
         }

         ++j;
      }

      dstCharChunk.setEnd(idx);
   }

   public static String decode(String str) throws CharConversionException {
      return decodeAscii(str, true);
   }

   public static String decode(String str, boolean allowEncodedSlash) throws CharConversionException {
      return decodeAscii(str, allowEncodedSlash);
   }

   public static String decode(String s, boolean allowEncodedSlash, String enc) throws CharConversionException {
      boolean needToChange = false;
      int numChars = s.length();
      StringBuilder sb = new StringBuilder(numChars > 500 ? numChars / 2 : numChars);
      int i = 0;
      byte[] bytes = null;

      while(true) {
         while(i < numChars) {
            char c = s.charAt(i);
            switch (c) {
               case '%':
                  try {
                     if (bytes == null) {
                        bytes = new byte[(numChars - i) / 3];
                     }

                     int pos = 0;

                     while(i + 2 < numChars && c == '%') {
                        int v = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                        if (v < 0) {
                           throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                        }

                        bytes[pos++] = (byte)v;
                        i += 3;
                        if (i < numChars) {
                           c = s.charAt(i);
                        }
                     }

                     if (i < numChars && c == '%') {
                        throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
                     }

                     String decodedChunk = new String(bytes, 0, pos, enc);
                     if (!allowEncodedSlash && decodedChunk.indexOf(47) != -1) {
                        throw new CharConversionException("Encoded slashes are not allowed");
                     }

                     sb.append(decodedChunk);
                  } catch (NumberFormatException var11) {
                     throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + var11.getMessage());
                  } catch (UnsupportedEncodingException var12) {
                  }

                  needToChange = true;
                  break;
               case '+':
                  sb.append(' ');
                  ++i;
                  needToChange = true;
                  break;
               default:
                  sb.append(c);
                  ++i;
            }
         }

         return needToChange ? sb.toString() : s;
      }
   }

   public static String decodeAscii(String str, boolean allowEncodedSlash) throws CharConversionException {
      if (str == null) {
         return null;
      } else {
         int mPos = 0;
         int strPos = 0;
         int strLen = str.length();
         StringBuilder dec = null;

         while(strPos < strLen) {
            char metaChar = str.charAt(strPos);
            boolean isPlus = metaChar == '+';
            boolean isNorm = !isPlus && metaChar != '%';
            if (isNorm) {
               ++strPos;
            } else {
               if (dec == null) {
                  dec = new StringBuilder(strLen);
               }

               if (mPos < strPos) {
                  dec.append(str, mPos, strPos);
               }

               if (isPlus) {
                  dec.append(' ');
                  ++strPos;
               } else {
                  char res = (char)Integer.parseInt(str.substring(strPos + 1, strPos + 3), 16);
                  if (!allowEncodedSlash && res == '/') {
                     throw new CharConversionException("Encoded slashes are not allowed");
                  }

                  dec.append(res);
                  strPos += 3;
               }

               mPos = strPos;
            }
         }

         if (dec != null) {
            if (mPos < strPos) {
               dec.append(str, mPos, strPos);
            }

            return dec.toString();
         } else {
            return str;
         }
      }
   }

   private static int x2c(byte b1, byte b2) {
      return (HexUtils.hexDigit2Dec(b1) << 4) + HexUtils.hexDigit2Dec(b2);
   }

   private static int x2c(int c1, int c2) {
      return (HexUtils.hexDigit2Dec(c1) << 4) + HexUtils.hexDigit2Dec(c2);
   }
}
