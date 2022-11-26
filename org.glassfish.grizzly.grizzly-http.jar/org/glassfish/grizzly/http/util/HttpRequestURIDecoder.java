package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.Charsets;

public class HttpRequestURIDecoder {
   protected static final boolean ALLOW_BACKSLASH = false;
   private static final boolean COLLAPSE_ADJACENT_SLASHES = Boolean.valueOf(System.getProperty("com.sun.enterprise.web.collapseAdjacentSlashes", "true"));
   private static final Logger LOGGER = Grizzly.logger(HttpRequestURIDecoder.class);
   private static final int STATE_CHAR = 0;
   private static final int STATE_SLASH = 1;
   private static final int STATE_PERCENT = 2;
   private static final int STATE_SLASHDOT = 3;
   private static final int STATE_SLASHDOTDOT = 4;

   public static void decode(MessageBytes decodedURI, UDecoder urlDecoder) throws Exception {
      decode(decodedURI, urlDecoder, (String)null, (B2CConverter)null);
   }

   public static void decode(MessageBytes decodedURI, UDecoder urlDecoder, String encoding, B2CConverter b2cConverter) throws Exception {
      urlDecoder.convert(decodedURI, false);
      if (!normalize(decodedURI)) {
         throw new IOException("Invalid URI character encoding");
      } else {
         if (encoding == null) {
            encoding = "utf-8";
         }

         convertURI(decodedURI, encoding, b2cConverter);
         if (!checkNormalize(decodedURI.getCharChunk())) {
            throw new IOException("Invalid URI character encoding");
         }
      }
   }

   public static void decode(DataChunk decodedURI) throws CharConversionException {
      decode(decodedURI, false, Charsets.UTF8_CHARSET);
   }

   public static void decode(DataChunk decodedURI, boolean isSlashAllowed) throws CharConversionException {
      decode(decodedURI, isSlashAllowed, Charsets.UTF8_CHARSET);
   }

   public static void decode(DataChunk decodedURI, boolean isSlashAllowed, Charset encoding) throws CharConversionException {
      decode(decodedURI, decodedURI, isSlashAllowed, encoding);
   }

   public static void decode(DataChunk originalURI, DataChunk targetDecodedURI, boolean isSlashAllowed, Charset encoding) throws CharConversionException {
      URLDecoder.decode(originalURI, targetDecodedURI, isSlashAllowed);
      if (!normalize(targetDecodedURI)) {
         throw new CharConversionException("Invalid URI character encoding");
      } else {
         convertToChars(targetDecodedURI, encoding);
      }
   }

   public static void convertToChars(DataChunk decodedURI, Charset encoding) throws CharConversionException {
      if (encoding == null) {
         encoding = Charsets.UTF8_CHARSET;
      }

      decodedURI.toChars(encoding);
      if (!checkNormalize(decodedURI.getCharChunk())) {
         throw new CharConversionException("Invalid URI character encoding");
      }
   }

   private static void convertURI(MessageBytes uri, String encoding, B2CConverter b2cConverter) throws Exception {
      ByteChunk bc = uri.getByteChunk();
      CharChunk cc = uri.getCharChunk();
      cc.allocate(bc.getLength(), -1);
      if (encoding != null && encoding.trim().length() != 0 && !"ISO-8859-1".equalsIgnoreCase(encoding)) {
         try {
            if (b2cConverter == null) {
               b2cConverter = new B2CConverter(encoding);
            }
         } catch (IOException var9) {
            LOGGER.severe("Invalid URI encoding; using HTTP default");
         }

         if (b2cConverter != null) {
            try {
               b2cConverter.convert(bc, cc);
               uri.setChars(cc.getBuffer(), cc.getStart(), cc.getLength());
               return;
            } catch (IOException var10) {
               LOGGER.severe("Invalid URI character encoding; trying ascii");
               cc.recycle();
            }
         }
      }

      byte[] bbuf = bc.getBuffer();
      char[] cbuf = cc.getBuffer();
      int start = bc.getStart();

      for(int i = 0; i < bc.getLength(); ++i) {
         cbuf[i] = (char)(bbuf[i + start] & 255);
      }

      uri.setChars(cbuf, 0, bc.getLength());
   }

   public static boolean normalize(MessageBytes uriMB) {
      int type = uriMB.getType();
      return type == 3 ? normalizeChars(uriMB.getCharChunk()) : normalizeBytes(uriMB.getByteChunk());
   }

   public static boolean normalize(DataChunk dataChunk) {
      switch (dataChunk.getType()) {
         case Bytes:
            return normalizeBytes(dataChunk.getByteChunk());
         case Buffer:
            return normalizeBuffer(dataChunk.getBufferChunk());
         case String:
            try {
               dataChunk.toChars((Charset)null);
            } catch (CharConversionException var2) {
               throw new IllegalStateException("Unexpected exception", var2);
            }
         case Chars:
            return normalizeChars(dataChunk.getCharChunk());
         default:
            throw new NullPointerException();
      }
   }

   public static boolean checkNormalize(CharChunk uriCC) {
      char[] c = uriCC.getChars();
      int start = uriCC.getStart();
      int end = uriCC.getEnd();

      int pos;
      for(pos = start; pos < end; ++pos) {
         if (c[pos] == '\\') {
            return false;
         }

         if (c[pos] == 0) {
            return false;
         }
      }

      if (COLLAPSE_ADJACENT_SLASHES) {
         for(pos = start; pos < end - 1; ++pos) {
            if (c[pos] == '/' && c[pos + 1] == '/') {
               return false;
            }
         }
      }

      if (end - start < 2 || c[end - 1] != '.' || c[end - 2] != '/' && (c[end - 2] != '.' || c[end - 3] != '/')) {
         return uriCC.indexOf((String)"/./", 0, 3, (int)0) < 0;
      } else {
         return false;
      }
   }

   public static boolean normalizeChars(CharChunk uriCC) {
      char[] c = uriCC.getChars();
      int start = uriCC.getStart();
      int end = uriCC.getEnd();
      if (end - start == 1 && c[start] == '*') {
         return true;
      } else {
         int pos;
         for(pos = start; pos < end; ++pos) {
            if (c[pos] == '\\') {
               return false;
            }

            if (c[pos] == 0) {
               return false;
            }
         }

         if (c[start] != '/') {
            return false;
         } else {
            if (COLLAPSE_ADJACENT_SLASHES) {
               for(pos = start; pos < end - 1; ++pos) {
                  if (c[pos] == '/') {
                     while(pos + 1 < end && c[pos + 1] == '/') {
                        copyChars(c, pos, pos + 1, end - pos - 1);
                        --end;
                     }
                  }
               }
            }

            if (end - start > 2 && c[end - 1] == '.' && (c[end - 2] == '/' || c[end - 2] == '.' && c[end - 3] == '/')) {
               c[end] = '/';
               ++end;
            }

            uriCC.setEnd(end);
            int index = 0;

            while(true) {
               index = uriCC.indexOf((String)"/./", 0, 3, (int)index);
               if (index < 0) {
                  index = 0;

                  while(true) {
                     index = uriCC.indexOf((String)"/../", 0, 4, (int)index);
                     if (index < 0) {
                        uriCC.setChars(c, start, end);
                        return true;
                     }

                     if (index == 0) {
                        return false;
                     }

                     int index2 = -1;

                     for(pos = start + index - 1; pos >= 0 && index2 < 0; --pos) {
                        if (c[pos] == '/') {
                           index2 = pos;
                        }
                     }

                     copyChars(c, start + index2, start + index + 3, end - start - index - 3);
                     end = end + index2 - index - 3;
                     uriCC.setEnd(end);
                     index = index2;
                  }
               }

               copyChars(c, start + index, start + index + 2, end - start - index - 2);
               end -= 2;
               uriCC.setEnd(end);
            }
         }
      }
   }

   protected static void copyBytes(byte[] b, int dest, int src, int len) {
      System.arraycopy(b, src, b, dest, len);
   }

   private static void copyChars(char[] c, int dest, int src, int len) {
      System.arraycopy(c, src, c, dest, len);
   }

   protected void log(String message) {
      LOGGER.info(message);
   }

   protected void log(String message, Throwable throwable) {
      LOGGER.log(Level.SEVERE, message, throwable);
   }

   protected void convertMB(MessageBytes mb) {
      if (mb.getType() == 2) {
         ByteChunk bc = mb.getByteChunk();
         CharChunk cc = mb.getCharChunk();
         cc.allocate(bc.getLength(), -1);
         byte[] bbuf = bc.getBuffer();
         char[] cbuf = cc.getBuffer();
         int start = bc.getStart();

         for(int i = 0; i < bc.getLength(); ++i) {
            cbuf[i] = (char)(bbuf[i + start] & 255);
         }

         mb.setChars(cbuf, 0, bc.getLength());
      }
   }

   public static boolean normalizeBytes(ByteChunk bc) {
      byte[] bs = bc.getBytes();
      int start = bc.getStart();
      int end = bc.getEnd();
      if (start == end) {
         return false;
      } else if (end - start == 1 && bs[start] == 42) {
         return true;
      } else {
         if (end - start > 2 && bs[end - 1] == 46 && (bs[end - 2] == 47 || bs[end - 2] == 46 && bs[end - 3] == 47)) {
            bs[end] = 47;
            ++end;
         }

         int state = 0;
         int srcPos = start;
         int lastSlash = -1;
         int parentSlash = -1;

         for(int pos = start; pos < end; ++pos) {
            if (bs[pos] == 0) {
               return false;
            }

            if (bs[pos] == 92) {
               return false;
            }

            if (bs[pos] == 47) {
               if (state == 0) {
                  state = 1;
                  bs[srcPos] = bs[pos];
                  parentSlash = lastSlash;
                  lastSlash = srcPos++;
               } else if (state == 1) {
                  if (!COLLAPSE_ADJACENT_SLASHES) {
                     ++srcPos;
                  }
               } else if (state == 3) {
                  --srcPos;
               } else if (state == 4) {
                  if (parentSlash == -1) {
                     return false;
                  }

                  lastSlash = parentSlash;
                  srcPos = parentSlash;
                  parentSlash = -1;

                  for(int i = lastSlash - 1; i >= start; --i) {
                     if (bs[i] == 47) {
                        parentSlash = i;
                        break;
                     }
                  }

                  state = 1;
                  bs[srcPos++] = bs[pos];
               }
            } else if (bs[pos] == 46) {
               if (state == 0) {
                  bs[srcPos++] = bs[pos];
               } else if (state == 1) {
                  state = 3;
                  bs[srcPos++] = bs[pos];
               } else if (state == 3) {
                  state = 4;
                  bs[srcPos++] = bs[pos];
               }
            } else {
               state = 0;
               bs[srcPos++] = bs[pos];
            }
         }

         bc.setEnd(srcPos);
         return true;
      }
   }

   public static boolean normalizeBuffer(BufferChunk bc) {
      Buffer bs = bc.getBuffer();
      int start = bc.getStart();
      int end = bc.getEnd();
      if (start == end) {
         return false;
      } else if (end - start == 1 && bs.get(start) == 42) {
         return true;
      } else {
         byte state;
         if (end - start > 2 && bs.get(end - 1) == 46) {
            state = bs.get(end - 2);
            if (state == 47 || state == 46 && bs.get(end - 3) == 47) {
               bs.put(end, (byte)47);
               ++end;
            }
         }

         state = 0;
         int srcPos = start;
         int lastSlash = -1;
         int parentSlash = -1;

         for(int pos = start; pos < end; ++pos) {
            byte b = bs.get(pos);
            if (b == 0) {
               return false;
            }

            if (b == 92) {
               return false;
            }

            if (b == 47) {
               if (state == 0) {
                  state = 1;
                  bs.put(srcPos, b);
                  parentSlash = lastSlash;
                  lastSlash = srcPos++;
               } else if (state == 1) {
                  if (!COLLAPSE_ADJACENT_SLASHES) {
                     ++srcPos;
                  }
               } else if (state == 3) {
                  --srcPos;
               } else if (state == 4) {
                  if (parentSlash == -1) {
                     return false;
                  }

                  lastSlash = parentSlash;
                  srcPos = parentSlash;
                  parentSlash = -1;

                  for(int i = lastSlash - 1; i >= start; --i) {
                     if (bs.get(i) == 47) {
                        parentSlash = i;
                        break;
                     }
                  }

                  state = 1;
                  bs.put(srcPos++, b);
               }
            } else if (b == 46) {
               if (state == 0) {
                  bs.put(srcPos++, b);
               } else if (state == 1) {
                  state = 3;
                  bs.put(srcPos++, b);
               } else if (state == 3) {
                  state = 4;
                  bs.put(srcPos++, b);
               }
            } else {
               state = 0;
               bs.put(srcPos++, b);
            }
         }

         bc.setEnd(srcPos);
         return true;
      }
   }
}
