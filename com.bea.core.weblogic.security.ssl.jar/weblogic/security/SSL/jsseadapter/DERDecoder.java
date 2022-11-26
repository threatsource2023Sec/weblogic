package weblogic.security.SSL.jsseadapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

class DERDecoder {
   static final int UNIVERSAL = 0;
   static final int APPLICATION = 64;
   static final int PRIVATE = 192;
   static final int CONTEXT_SPECIFIC = 128;
   static final int TYPE_CONSTRUCTED = 32;
   static final int BOOLEAN = 1;
   static final int INTEGER = 2;
   static final int BIT_STRING = 3;
   static final int OCTET_STRING = 4;
   static final int SEQUENCE_AND_SEQUENCE_OF = 16;
   static final int SET_AND_SET_OF = 17;
   static final int BMP_STRING = 30;
   static final int IA5_STRING = 22;
   static final int GENERAL_STRING = 27;
   static final int GRAPHIC_STRING = 25;
   static final int NUMERIC_STRING = 18;
   static final int PRINTABLE_STRING = 19;
   static final int TELETEX_STRING = 20;
   static final int UNIVERSAL_STRING = 28;
   static final int UTF8_STRING = 12;
   static final int VIDEO_TEX_STRING = 21;
   static final int VISIBLE_STRING = 26;
   private InputStream is;

   public DERDecoder(InputStream inputStream) {
      this.is = inputStream;
   }

   public DERDecoder(byte[] derBytes) {
      this((InputStream)(new ByteArrayInputStream(derBytes)));
   }

   public ASN1Object readObject() throws IOException {
      int tag = this.is.read();
      if (tag == -1) {
         throw new IOException("Unexpected end of stream while reading tag");
      } else {
         int length = this.getLength();
         byte[] value = new byte[length];
         int n = this.is.read(value);
         if (n < length) {
            throw new IOException("The actual number of bytes readObject is less than that of the value.");
         } else {
            ASN1Object ao = new ASN1Object(tag, length, value);
            return ao;
         }
      }
   }

   private int getLength() throws IOException {
      int length = this.is.read();
      if (length == -1) {
         throw new IOException("Unexpected end of stream.");
      } else if ((length & -128) == 0) {
         return length;
      } else {
         int numOctets = length & 127;
         if (length < 255 && numOctets <= 4) {
            byte[] bytes = new byte[numOctets];
            int n = this.is.read(bytes);
            if (n < numOctets) {
               throw new IOException("Length shorter than expected: " + n);
            } else {
               return (new BigInteger(1, bytes)).intValue();
            }
         } else {
            throw new IOException("Unexpected too big length: " + length);
         }
      }
   }
}
