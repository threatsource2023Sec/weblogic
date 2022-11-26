package com.bea.core.jatmi.common;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.Decimal;

public final class Utilities {
   public static final int MAXTIDENT = 30;
   public static final String encode = getEncode();
   public static final boolean supportIllformedEncodedData = getIllformedDataSupport();

   public static String getEncode() {
      String retstr = null;

      try {
         retstr = System.getProperty("weblogic.wtc.encoding");
         if (retstr != null) {
            String teststr = new String("test");
            teststr.getBytes(retstr);
         }
      } catch (Exception var2) {
         WTCLogger.logErrorUnsupportedEncoding(retstr);
         retstr = null;
      }

      return retstr;
   }

   public static boolean getIllformedDataSupport() {
      boolean value = false;
      String flag = System.getProperty("weblogic.wtc.supportIllformedEncodedData");
      if (flag != null && "true".equals(flag)) {
         value = true;
      }

      return value;
   }

   public static byte[] getEncBytes(String str) {
      if (encode != null) {
         try {
            return str.getBytes(encode);
         } catch (UnsupportedEncodingException var2) {
            WTCLogger.logErrorUnsupportedEncoding(encode);
            return str.getBytes();
         }
      } else {
         return str.getBytes();
      }
   }

   public static String getEncString(byte[] buf) {
      String tmpstr;
      if (encode != null) {
         try {
            tmpstr = new String(buf, encode);
         } catch (UnsupportedEncodingException var5) {
            WTCLogger.logErrorUnsupportedEncoding(encode);
            return new String(buf);
         }
      } else {
         tmpstr = new String(buf);
      }

      if (supportIllformedEncodedData && tmpstr.length() == 0 && buf.length > 1) {
         for(int ix = 1; ix <= 4; ++ix) {
            if (encode != null) {
               try {
                  tmpstr = new String(buf, 0, buf.length - ix, encode);
               } catch (UnsupportedEncodingException var4) {
                  WTCLogger.logErrorUnsupportedEncoding(encode);
                  return new String(buf);
               }
            } else {
               tmpstr = new String(buf, 0, buf.length - ix);
            }

            if (tmpstr.length() > 0 || buf.length - ix <= 1) {
               break;
            }
         }
      }

      return tmpstr;
   }

   public static String getEncString(byte[] buf, int offset, int count) {
      if (encode != null) {
         try {
            return new String(buf, offset, count, encode);
         } catch (UnsupportedEncodingException var4) {
            WTCLogger.logErrorUnsupportedEncoding(encode);
            return new String(buf, offset, count);
         }
      } else {
         return new String(buf, offset, count);
      }
   }

   public static int roundup4(int a) {
      return a + 3 & -4;
   }

   public static int roundup8(int a) {
      return a + 7 & -8;
   }

   public static int xdr_length_string(String aString) {
      return aString == null ? 4 : 4 + roundup4(aString.length());
   }

   public static int xdr_encode_string_length(DataOutputStream encoder, String aString, int send_length) throws IOException {
      encoder.writeInt(send_length);
      int length;
      if (aString != null) {
         byte[] aStringBytes = getEncBytes(aString);
         length = aStringBytes.length;
         encoder.write(aStringBytes);
      } else {
         length = 0;
      }

      int pad_bytes = roundup4(send_length) - length;

      for(int lcv = 0; lcv < pad_bytes; ++lcv) {
         encoder.writeByte(0);
      }

      return 4 + send_length;
   }

   public static int xdr_encode_string_length(DataOutputStream encoder, String aString, int send_length, boolean addNull) throws IOException {
      return xdr_encode_string_length(encoder, aString, send_length);
   }

   public static int xdr_encode_string(DataOutputStream encoder, String aString) throws IOException {
      if (aString == null) {
         encoder.writeInt(0);
         return 4;
      } else {
         byte[] aStringBytes = getEncBytes(aString);
         int length = aStringBytes.length;
         int pad_bytes = roundup4(length) - length;
         encoder.writeInt(length);
         if (length == 0) {
            return 4;
         } else {
            encoder.write(aStringBytes);

            for(int lcv = 0; lcv < pad_bytes; ++lcv) {
               encoder.writeByte(0);
            }

            return 4 + length + pad_bytes;
         }
      }
   }

   public static String xdr_decode_string(DataInputStream decoder, byte[] scratch) throws IOException {
      int length = decoder.readInt();
      if (length == 0) {
         return null;
      } else {
         int length_padded = roundup4(length);
         byte[] usechar;
         if (scratch != null && scratch.length >= length_padded) {
            usechar = scratch;
         } else {
            usechar = new byte[length_padded];
         }

         for(int lcv = 0; lcv < length_padded; ++lcv) {
            usechar[lcv] = decoder.readByte();
         }

         return getEncString(usechar, 0, length);
      }
   }

   public static int xdr_encode_string(DataOutputStream encoder, String aString, boolean addNull) throws IOException {
      if (addNull) {
         char[] nullChar = new char[]{'\u0000'};
         if (aString == null) {
            aString = new String(nullChar);
         } else {
            aString = aString + new String(nullChar);
         }
      } else if (aString == null) {
         encoder.writeInt(0);
         return 4;
      }

      byte[] aStringBytes = getEncBytes(aString);
      int length = aStringBytes.length;
      int pad_bytes = roundup4(length) - length;
      encoder.writeInt(length);
      if (length == 0) {
         return 4;
      } else {
         encoder.write(aStringBytes);

         for(int lcv = 0; lcv < pad_bytes; ++lcv) {
            encoder.writeByte(0);
         }

         return 4 + length + pad_bytes;
      }
   }

   public static String xdr_decode_string(DataInputStream decoder, byte[] scratch, boolean truncate) throws IOException {
      int length = decoder.readInt();
      if (length == 0) {
         return null;
      } else {
         int length_padded = roundup4(length);
         byte[] usechar;
         if (scratch != null && scratch.length >= length_padded) {
            usechar = scratch;
         } else {
            usechar = new byte[length_padded];
         }

         for(int lcv = 0; lcv < length_padded; ++lcv) {
            usechar[lcv] = decoder.readByte();
         }

         String finalString = getEncString(usechar, 0, length);
         if (truncate) {
            int ndx = finalString.indexOf(0);
            if (ndx >= 0) {
               if (ndx == 0) {
                  finalString = null;
               } else {
                  finalString = finalString.substring(0, ndx);
               }
            }
         }

         return finalString;
      }
   }

   public static int xdr_encode_vector_string(DataOutputStream encoder, String aString, int vectorSize) throws IOException {
      int length;
      char[] domid_char;
      if (aString == null) {
         length = 0;
         domid_char = null;
      } else {
         length = aString.length();
         domid_char = aString.toCharArray();
      }

      if (length > vectorSize) {
         length = vectorSize;
      }

      int lcv;
      for(lcv = 0; lcv < length; ++lcv) {
         encoder.writeInt(domid_char[lcv]);
      }

      while(lcv < vectorSize) {
         encoder.writeInt(0);
         ++lcv;
      }

      return 4 * vectorSize;
   }

   public static String xdr_decode_vector_string(DataInputStream decoder, int vectorSize, char[] vector) throws IOException {
      int newLength = -1;

      for(int lcv = 0; lcv < vectorSize; ++lcv) {
         vector[lcv] = (char)(decoder.readInt() & 255);
         if (newLength == -1 && vector[lcv] == 0) {
            newLength = lcv;
         }
      }

      if (newLength == -1) {
         return new String(vector, 0, vectorSize);
      } else if (newLength == 0) {
         return null;
      } else {
         return new String(vector, 0, newLength);
      }
   }

   public static int xdr_length_bstring(byte[] bArray) {
      return bArray == null ? 4 : 4 + roundup4(bArray.length);
   }

   public static int xdr_encode_bstring(DataOutputStream encoder, byte[] bArray) throws IOException {
      if (bArray == null) {
         encoder.writeInt(0);
         return 4;
      } else {
         int length = bArray.length;
         int pad_bytes = roundup4(length) - length;
         encoder.writeInt(length);
         if (length == 0) {
            return 4;
         } else {
            encoder.write(bArray, 0, length);

            for(int lcv = 0; lcv < pad_bytes; ++lcv) {
               encoder.writeByte(0);
            }

            return 4 + length + pad_bytes;
         }
      }
   }

   public static byte[] xdr_decode_bstring(DataInputStream decoder) throws IOException {
      int length = decoder.readInt();
      if (length == 0) {
         return null;
      } else {
         int pad_bytes = roundup4(length) - length;
         byte[] barray = new byte[length];
         int total = 0;

         do {
            int bytes_read = decoder.read(barray, total, length - total);
            if (bytes_read == -1) {
               break;
            }

            total += bytes_read;
         } while(total < length);

         if (pad_bytes > 0) {
            decoder.skipBytes(pad_bytes);
         }

         return barray;
      }
   }

   public static int xdr_encode_decimal(DataOutputStream encoder, Decimal decimal) throws IOException {
      if (decimal == null) {
         return 0;
      } else {
         encoder.writeInt(decimal.exponent());
         encoder.writeInt(decimal.sign());
         encoder.writeInt(decimal.numDigits());
         byte[] digits = decimal.digits();
         encoder.writeInt(16);
         int length;
         if (digits.length > 16) {
            length = 16;
         } else {
            length = digits.length;
         }

         encoder.write(digits, 0, length);
         int pad_bytes = roundup4(16) - length;

         for(int lcv = 0; lcv < pad_bytes; ++lcv) {
            encoder.writeByte(0);
         }

         return 16 + length + pad_bytes;
      }
   }

   public static Decimal xdr_decode_decimal(DataInputStream decoder) throws IOException {
      int exponent = decoder.readInt();
      int sign = decoder.readInt();
      int numDigits = decoder.readInt();
      int length = decoder.readInt();
      if (length == 0) {
         return new Decimal();
      } else {
         int pad_bytes = roundup4(length) - length;
         byte[] digits = new byte[length];
         int total = 0;

         do {
            int bytes_read = decoder.read(digits, total, length - total);
            if (bytes_read == -1) {
               break;
            }

            total += bytes_read;
         } while(total < length);

         if (pad_bytes > 0) {
            decoder.skipBytes(pad_bytes);
         }

         try {
            return new Decimal(sign, exponent, numDigits, digits);
         } catch (NumberFormatException var9) {
            return new Decimal();
         }
      }
   }

   public static int xdr_length_opaque(byte[] bArray) {
      return bArray == null ? 0 : roundup4(bArray.length);
   }

   public static int xdr_encode_opaque(DataOutputStream encoder, byte[] bArray) throws IOException {
      if (bArray == null) {
         return 0;
      } else {
         int length;
         if ((length = bArray.length) == 0) {
            return 0;
         } else {
            int pad_length = roundup4(length);
            encoder.write(bArray, 0, length);

            for(int lcv = length; lcv < pad_length; ++lcv) {
               encoder.writeByte(0);
            }

            return pad_length;
         }
      }
   }

   public static byte[] encodeByteVector(byte[] iba) throws IOException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(bout);

      for(int i = 0; i < iba.length; ++i) {
         out.writeInt(iba[i]);
      }

      byte[] oba = bout.toByteArray();
      out.close();
      return oba;
   }

   public static byte[] decodeByteVector(int len, byte[] iba) {
      byte[] oba = new byte[len];
      if (iba.length < len * 4) {
         return null;
      } else {
         int i = 0;
         int j = 0;

         while(i < len * 4) {
            switch (i % 4) {
               case 3:
                  oba[j++] = iba[i];
               case 0:
               case 1:
               case 2:
               default:
                  ++i;
            }
         }

         return oba;
      }
   }

   public static boolean baEqualBytes(byte[] ba1, int offset1, byte[] ba2, int offset2, int len) {
      try {
         int i = len;

         do {
            --i;
            if (i < 0) {
               return true;
            }
         } while(ba1[i + offset1] == ba2[i + offset2]);

         return false;
      } catch (ArrayIndexOutOfBoundsException var6) {
         return false;
      }
   }

   public static int baWriteInt(int value, byte[] ba, int offset) {
      if (ba.length < offset + 4) {
         return 0;
      } else {
         ba[offset + 3] = (byte)value;
         value >>= 8;
         ba[offset + 2] = (byte)value;
         value >>= 8;
         ba[offset + 1] = (byte)value;
         value >>= 8;
         ba[offset] = (byte)value;
         return 4;
      }
   }

   public static int baReadInt(byte[] ba, int offset) {
      int i = ba[offset++] & 255;
      i <<= 8;
      i |= ba[offset++] & 255;
      i <<= 8;
      i |= ba[offset++] & 255;
      i <<= 8;
      i |= ba[offset++] & 255;
      return i;
   }

   public static int baWriteXdrString(byte[] ba, int offset, String s) {
      if (s != null && s.length() != 0) {
         int len = s.length();
         int pad = roundup4(len);
         offset += baWriteInt(len, ba, offset);
         System.arraycopy(s.getBytes(), 0, ba, offset, len);
         offset += len;

         for(int i = len; i < pad; ++i) {
            ba[offset] = 0;
            ++offset;
         }

         return pad + 4;
      } else {
         return baWriteInt(0, ba, offset);
      }
   }

   public static String baReadXdrString(byte[] ba, int offset) {
      int len = baReadInt(ba, offset);
      offset += 4;
      if (len > 0) {
         byte[] t = new byte[len];
         System.arraycopy(ba, offset, t, 0, len);
         String s = new String(t);
         return s;
      } else {
         return null;
      }
   }

   public static int baWriteXdrBVector(byte[] out, int o_offset, byte[] in, int i_offset, int length) {
      for(int i = 0; i < length; ++i) {
         o_offset += baWriteInt(in[i_offset++], out, o_offset);
      }

      return length * 4;
   }

   public static int baReadXdrBVector(byte[] out, int o_offset, byte[] in, int i_offset, int length) {
      for(int i = 0; i < length; ++i) {
         out[o_offset++] = (byte)baReadInt(in, i_offset);
         i_offset += 4;
      }

      return length * 4;
   }

   public static int baWriteXdrBOpaque(byte[] out, int o_offset, byte[] in, int i_offset, int length) {
      int pad = roundup4(length);
      System.arraycopy(in, i_offset, out, o_offset, length);

      for(int i = length; i < pad; ++i) {
         out[o_offset + length + i] = 0;
      }

      return pad;
   }

   public static int baReadXdrBOpaque(byte[] out, int o_offset, byte[] in, int i_offset, int length) {
      System.arraycopy(in, i_offset, out, o_offset, length);
      return roundup4(length);
   }

   public static int baWriteXdrBString(byte[] out, int o_offset, byte[] in, int i_offset, int length) {
      int pad = 0;
      o_offset += baWriteInt(length, out, o_offset);
      if (length != 0) {
         pad = roundup4(length);
         System.arraycopy(in, i_offset, out, o_offset, length);
         o_offset += length;

         for(int i = length; i < pad; ++i) {
            out[o_offset++] = 0;
         }
      }

      return pad + 4;
   }

   public static int baReadXdrBString(byte[] out, int o_offset, byte[] in, int i_offset) {
      int length = baReadInt(in, i_offset);
      i_offset += 4;
      if (length != 0) {
         System.arraycopy(in, i_offset, out, o_offset, length);
      }

      return roundup4(length) + 4;
   }

   public static int readByteArray(DataInputStream decoder, byte[] ba, int offset, int n) {
      if (ba != null && ba.length - offset >= n) {
         int total = 0;

         int length;
         try {
            while((length = decoder.read(ba, total, n - total)) != -1 && (total += length) < n) {
            }
         } catch (IOException var7) {
            return -1;
         }

         return length < 0 ? -1 : total;
      } else {
         return -1;
      }
   }

   public static String prettyByteArray(byte[] writeMe) {
      StringBuffer sb = new StringBuffer("[");
      if (writeMe == null) {
         writeMe = new byte[0];
      }

      boolean firstTime = true;
      byte[] var3 = writeMe;
      int var4 = writeMe.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Byte b = var3[var5];
         if (firstTime) {
            firstTime = false;
            sb.append(b.toString());
         } else {
            sb.append("," + b.toString());
         }
      }

      sb.append("]");
      return sb.toString();
   }
}
