package org.apache.xmlbeans.impl.piccolo.xml;

import java.io.CharConversionException;
import org.apache.xmlbeans.impl.piccolo.io.CharsetDecoder;
import org.apache.xmlbeans.impl.piccolo.io.IllegalCharException;

public final class ASCIIXMLDecoder implements XMLDecoder {
   private boolean sawCR = false;

   public CharsetDecoder newCharsetDecoder() {
      return this.newXMLDecoder();
   }

   public XMLDecoder newXMLDecoder() {
      return new ASCIIXMLDecoder();
   }

   public int minBytesPerChar() {
      return 1;
   }

   public int maxBytesPerChar() {
      return 1;
   }

   public void reset() {
      this.sawCR = false;
   }

   public void decode(byte[] in_buf, int in_off, int in_len, char[] out_buf, int out_off, int out_len, int[] result) throws CharConversionException {
      this.internalDecode(in_buf, in_off, in_len, out_buf, out_off, out_len, result, false);
   }

   public void decodeXMLDecl(byte[] in_buf, int in_off, int in_len, char[] out_buf, int out_off, int out_len, int[] result) throws CharConversionException {
      this.internalDecode(in_buf, in_off, in_len, out_buf, out_off, out_len, result, true);
   }

   private void internalDecode(byte[] in_buf, int in_off, int in_len, char[] out_buf, int out_off, int out_len, int[] result, boolean decodeDecl) throws CharConversionException {
      int o = 0;

      int i;
      label30:
      for(i = 0; i < in_len && o < out_len; ++i) {
         char c = (char)(127 & in_buf[in_off + i]);
         if (c >= ' ') {
            this.sawCR = false;
            out_buf[out_off + o++] = c;
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
                  if (!decodeDecl) {
                     throw new IllegalCharException("Illegal XML character: 0x" + Integer.toHexString(c));
                  }
                  break label30;
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
