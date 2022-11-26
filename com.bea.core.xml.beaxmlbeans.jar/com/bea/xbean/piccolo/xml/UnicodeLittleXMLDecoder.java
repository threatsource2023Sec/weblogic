package com.bea.xbean.piccolo.xml;

import com.bea.xbean.piccolo.io.CharsetDecoder;
import com.bea.xbean.piccolo.io.IllegalCharException;
import java.io.CharConversionException;

public final class UnicodeLittleXMLDecoder implements XMLDecoder {
   private boolean sawCR = false;

   public CharsetDecoder newCharsetDecoder() {
      return this.newXMLDecoder();
   }

   public XMLDecoder newXMLDecoder() {
      return new UnicodeLittleXMLDecoder();
   }

   public int minBytesPerChar() {
      return 2;
   }

   public int maxBytesPerChar() {
      return 2;
   }

   public void reset() {
      this.sawCR = false;
   }

   public void decode(byte[] in_buf, int in_off, int in_len, char[] out_buf, int out_off, int out_len, int[] result) throws CharConversionException {
      int o = 0;

      int i;
      for(i = 0; i + 1 < in_len && o < out_len; i += 2) {
         char c = (char)((255 & in_buf[in_off + i + 1]) << 8 | 255 & in_buf[in_off + i]);
         if (c < ' ') {
            switch (c) {
               case '\t':
                  out_buf[out_off + o++] = '\t';
                  break;
               case '\n':
                  if (this.sawCR) {
                     this.sawCR = false;
                  } else {
                     out_buf[out_off + o++] = '\n';
                  }
                  break;
               case '\u000b':
               case '\f':
               default:
                  throw new IllegalCharException("Illegal XML character: 0x" + Integer.toHexString(c));
               case '\r':
                  this.sawCR = true;
                  out_buf[out_off + o++] = '\n';
            }
         } else {
            if (c > '\ud7ff' && (c < '\ue000' || c > '�') && (c < 65536 || c > 1114111)) {
               throw new IllegalCharException("Illegal XML Character: 0x" + Integer.toHexString(c));
            }

            this.sawCR = false;
            out_buf[out_off + o++] = c;
         }
      }

      result[0] = i;
      result[1] = o;
   }

   public void decodeXMLDecl(byte[] in_buf, int in_off, int in_len, char[] out_buf, int out_off, int out_len, int[] result) throws CharConversionException {
      int o = 0;

      int i;
      label46:
      for(i = 0; i + 1 < in_len && o < out_len; i += 2) {
         char c = (char)((255 & in_buf[in_off + i + 1]) << 8 | 255 & in_buf[in_off + i]);
         if (c >= ' ') {
            if (c > '\ud7ff' && (c < '\ue000' || c > '�') && (c < 65536 || c > 1114111)) {
               break;
            }

            this.sawCR = false;
            out_buf[out_off + o++] = c;
            if (c == '>') {
               i += 2;
               break;
            }
         } else {
            switch (c) {
               case '\t':
                  out_buf[out_off + o++] = '\t';
                  break;
               case '\n':
                  if (this.sawCR) {
                     this.sawCR = false;
                  } else {
                     out_buf[out_off + o++] = '\n';
                  }
                  break;
               case '\u000b':
               case '\f':
               default:
                  break label46;
               case '\r':
                  this.sawCR = true;
                  out_buf[out_off + o++] = '\n';
            }
         }
      }

      result[0] = i;
      result[1] = o;
   }
}
