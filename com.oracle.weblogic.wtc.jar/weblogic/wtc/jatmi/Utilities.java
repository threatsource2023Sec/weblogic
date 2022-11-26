package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import weblogic.wtc.WTCLogger;

public final class Utilities {
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
}
