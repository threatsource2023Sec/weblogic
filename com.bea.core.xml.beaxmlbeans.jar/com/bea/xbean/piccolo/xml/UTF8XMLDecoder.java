package com.bea.xbean.piccolo.xml;

import com.bea.xbean.piccolo.io.CharsetDecoder;
import com.bea.xbean.piccolo.io.IllegalCharException;
import java.io.CharConversionException;

public final class UTF8XMLDecoder implements XMLDecoder {
   private boolean sawCR = false;

   public CharsetDecoder newCharsetDecoder() {
      return this.newXMLDecoder();
   }

   public XMLDecoder newXMLDecoder() {
      return new UTF8XMLDecoder();
   }

   public int minBytesPerChar() {
      return 1;
   }

   public int maxBytesPerChar() {
      return 3;
   }

   public void reset() {
      this.sawCR = false;
   }

   public void decode(byte[] in_buf, int in_off, int in_len, char[] out_buf, int out_off, int out_len, int[] result) throws CharConversionException {
      int o = 0;

      int i;
      for(i = 0; i < in_len && o < out_len; ++i) {
         int c = in_buf[in_off + i];
         if ((c & 128) != 0) {
            ++i;
            if (i >= in_len) {
               result[0] = i - 1;
               result[1] = o;
               return;
            }

            int c2 = in_buf[in_off + i];
            if ((c & 224) == 192) {
               if ((c2 & 128) != 128) {
                  throw new CharConversionException("Malformed UTF-8 character: 0x" + Integer.toHexString(c & 255) + " 0x" + Integer.toHexString(c2 & 255));
               }

               c = (c & 31) << 6 | c2 & 63;
               if ((c & 1920) == 0) {
                  throw new CharConversionException("2-byte UTF-8 character is overlong: 0x" + Integer.toHexString(in_buf[in_off + i - 1] & 255) + " 0x" + Integer.toHexString(c2 & 255));
               }
            } else {
               byte c3;
               if ((c & 240) != 224) {
                  if ((c & 240) == 240) {
                     if (i + 2 >= in_len) {
                        result[0] = i - 2;
                        result[1] = o;
                        return;
                     }

                     ++i;
                     c3 = in_buf[in_off + i];
                     ++i;
                     int c4 = in_buf[in_off + i];
                     if ((c2 & 128) == 128 && (c3 & 128) == 128 && (c4 & 128) == 128) {
                        c = (c & 7) << 18 | (c2 & 63) << 12 | (c3 & 63) << 6 | c4 & 63;
                        if (c < 65536 || c > 1114111) {
                           throw new IllegalCharException("Illegal XML character: 0x" + Integer.toHexString(c));
                        }

                        c -= 65536;
                        out_buf[out_off + o++] = (char)(c >> 10 | '\ud800');
                        out_buf[out_off + o++] = (char)(c & 1023 | '\udc00');
                        this.sawCR = false;
                        continue;
                     }

                     throw new CharConversionException("Malformed UTF-8 character: 0x" + Integer.toHexString(c & 255) + " 0x" + Integer.toHexString(c2 & 255) + " 0x" + Integer.toHexString(c3 & 255) + " 0x" + Integer.toHexString(c4 & 255));
                  }

                  throw new CharConversionException("Characters larger than 4 bytes are not supported: byte 0x" + Integer.toHexString(c & 255) + " implies a length of more than 4 bytes");
               }

               ++i;
               if (i >= in_len) {
                  result[0] = i - 2;
                  result[1] = o;
                  return;
               }

               c3 = in_buf[in_off + i];
               if ((c2 & 128) != 128 || (c3 & 128) != 128) {
                  throw new CharConversionException("Malformed UTF-8 character: 0x" + Integer.toHexString(c & 255) + " 0x" + Integer.toHexString(c2 & 255) + " 0x" + Integer.toHexString(c3 & 255));
               }

               c = (c & 15) << 12 | (c2 & 63) << 6 | c3 & 63;
               if ((c & '\uf800') == 0) {
                  throw new CharConversionException("3-byte UTF-8 character is overlong: 0x" + Integer.toHexString(in_buf[in_off + i - 2] & 255) + " 0x" + Integer.toHexString(c2 & 255) + " 0x" + Integer.toHexString(c3 & 255));
               }
            }

            if (c >= 55296 && c < 57344 || c == 65534 || c == 65535) {
               throw new IllegalCharException("Illegal XML character: 0x" + Integer.toHexString(c));
            }
         }

         if (c >= 32) {
            this.sawCR = false;
            out_buf[out_off + o++] = (char)c;
         } else {
            switch (c) {
               case 9:
                  out_buf[out_off + o++] = '\t';
                  break;
               case 10:
                  if (this.sawCR) {
                     this.sawCR = false;
                  } else {
                     out_buf[out_off + o++] = '\n';
                  }
                  break;
               case 11:
               case 12:
               default:
                  throw new IllegalCharException("Illegal XML character: 0x" + Integer.toHexString(c));
               case 13:
                  this.sawCR = true;
                  out_buf[out_off + o++] = '\n';
            }
         }
      }

      result[0] = i;
      result[1] = o;
   }

   public void decodeXMLDecl(byte[] in_buf, int in_off, int in_len, char[] out_buf, int out_off, int out_len, int[] result) throws CharConversionException {
      int o = 0;

      int i;
      label35:
      for(i = 0; i < in_len && o < out_len; ++i) {
         int c = in_buf[in_off + i];
         if ((c & 128) != 0) {
            break;
         }

         if (c >= 32) {
            this.sawCR = false;
            out_buf[out_off + o++] = (char)c;
            if (c == 62) {
               ++i;
               break;
            }
         } else {
            switch (c) {
               case 9:
                  out_buf[out_off + o++] = '\t';
                  break;
               case 10:
                  if (this.sawCR) {
                     this.sawCR = false;
                  } else {
                     out_buf[out_off + o++] = '\n';
                  }
                  break;
               case 11:
               case 12:
               default:
                  break label35;
               case 13:
                  this.sawCR = true;
                  out_buf[out_off + o++] = '\n';
            }
         }
      }

      result[0] = i;
      result[1] = o;
   }
}
