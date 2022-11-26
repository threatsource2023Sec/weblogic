package org.apache.xml.security.c14n.implementations;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.Map;

public final class UtfHelpper {
   private static final boolean OLD_UTF8 = (Boolean)AccessController.doPrivileged(() -> {
      return Boolean.getBoolean("org.apache.xml.security.c14n.oldUtf8");
   });

   private UtfHelpper() {
   }

   public static void writeByte(String str, OutputStream out, Map cache) throws IOException {
      byte[] result = (byte[])cache.get(str);
      if (result == null) {
         result = getStringInUtf8(str);
         cache.put(str, result);
      }

      out.write(result);
   }

   public static void writeCodePointToUtf8(int c, OutputStream out) throws IOException {
      if (Character.isValidCodePoint(c) && (c < 55296 || c > 56319) && (c < 56320 || c > 57343)) {
         if (OLD_UTF8 && c >= 65536) {
            out.write(63);
            out.write(63);
         } else if (c < 128) {
            out.write(c);
         } else {
            byte extraByte = false;
            byte extraByte;
            if (c < 2048) {
               extraByte = 1;
            } else if (c < 65536) {
               extraByte = 2;
            } else if (c < 2097152) {
               extraByte = 3;
            } else if (c < 67108864) {
               extraByte = 4;
            } else {
               if (c > Integer.MAX_VALUE) {
                  out.write(63);
                  return;
               }

               extraByte = 5;
            }

            int shift = 6 * extraByte;
            byte write = (byte)(254 << 6 - extraByte | c >>> shift);
            out.write(write);

            for(int i = extraByte - 1; i >= 0; --i) {
               shift -= 6;
               write = (byte)(128 | c >>> shift & 63);
               out.write(write);
            }

         }
      } else {
         out.write(63);
      }
   }

   /** @deprecated */
   @Deprecated
   public static void writeCharToUtf8(char c, OutputStream out) throws IOException {
      if (c < 128) {
         out.write(c);
      } else if ((c < '\ud800' || c > '\udbff') && (c < '\udc00' || c > '\udfff')) {
         byte bias;
         int write;
         char ch;
         if (c > 2047) {
            ch = (char)(c >>> 12);
            write = 224;
            if (ch > 0) {
               write |= ch & 15;
            }

            out.write(write);
            write = 128;
            bias = 63;
         } else {
            write = 192;
            bias = 31;
         }

         ch = (char)(c >>> 6);
         if (ch > 0) {
            write |= ch & bias;
         }

         out.write(write);
         out.write(128 | c & 63);
      } else {
         out.write(63);
      }
   }

   public static void writeStringToUtf8(String str, OutputStream out) throws IOException {
      int length = str.length();
      int i = 0;

      while(true) {
         while(true) {
            while(i < length) {
               int c = str.codePointAt(i);
               i += Character.charCount(c);
               if (Character.isValidCodePoint(c) && (c < 55296 || c > 56319) && (c < 56320 || c > 57343)) {
                  if (OLD_UTF8 && c >= 65536) {
                     out.write(63);
                     out.write(63);
                  } else if (c < 128) {
                     out.write(c);
                  } else {
                     byte extraByte = false;
                     byte extraByte;
                     if (c < 2048) {
                        extraByte = 1;
                     } else if (c < 65536) {
                        extraByte = 2;
                     } else if (c < 2097152) {
                        extraByte = 3;
                     } else if (c < 67108864) {
                        extraByte = 4;
                     } else {
                        if (c > Integer.MAX_VALUE) {
                           out.write(63);
                           continue;
                        }

                        extraByte = 5;
                     }

                     int shift = 6 * extraByte;
                     byte write = (byte)(254 << 6 - extraByte | c >>> shift);
                     out.write(write);

                     for(int j = extraByte - 1; j >= 0; --j) {
                        shift -= 6;
                        write = (byte)(128 | c >>> shift & 63);
                        out.write(write);
                     }
                  }
               } else {
                  out.write(63);
               }
            }

            return;
         }
      }
   }

   public static byte[] getStringInUtf8(String str) {
      int length = str.length();
      boolean expanded = false;
      byte[] result = new byte[length];
      int i = 0;
      int out = 0;

      while(true) {
         while(true) {
            byte[] newResult;
            while(i < length) {
               int c = str.codePointAt(i);
               i += Character.charCount(c);
               if (Character.isValidCodePoint(c) && (c < 55296 || c > 56319) && (c < 56320 || c > 57343)) {
                  if (OLD_UTF8 && c >= 65536) {
                     result[out++] = 63;
                     result[out++] = 63;
                  } else if (c < 128) {
                     result[out++] = (byte)c;
                  } else {
                     if (!expanded) {
                        newResult = new byte[6 * length];
                        System.arraycopy(result, 0, newResult, 0, out);
                        result = newResult;
                        expanded = true;
                     }

                     byte extraByte = false;
                     byte extraByte;
                     if (c < 2048) {
                        extraByte = 1;
                     } else if (c < 65536) {
                        extraByte = 2;
                     } else if (c < 2097152) {
                        extraByte = 3;
                     } else if (c < 67108864) {
                        extraByte = 4;
                     } else {
                        if (c > Integer.MAX_VALUE) {
                           result[out++] = 63;
                           continue;
                        }

                        extraByte = 5;
                     }

                     int shift = 6 * extraByte;
                     byte write = (byte)(254 << 6 - extraByte | c >>> shift);
                     result[out++] = write;

                     for(int j = extraByte - 1; j >= 0; --j) {
                        shift -= 6;
                        write = (byte)(128 | c >>> shift & 63);
                        result[out++] = write;
                     }
                  }
               } else {
                  result[out++] = 63;
               }
            }

            if (expanded) {
               newResult = new byte[out];
               System.arraycopy(result, 0, newResult, 0, out);
               result = newResult;
            }

            return result;
         }
      }
   }
}
