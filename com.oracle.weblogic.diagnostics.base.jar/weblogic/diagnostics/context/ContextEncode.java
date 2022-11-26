package weblogic.diagnostics.context;

class ContextEncode {
   private static final char[] Base64 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '^', '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
   private static final char Encode_0_Begin = '0';
   private static final char Encode_0_End = '9';
   private static final char Encode_0_Base = '\u0000';
   private static final char Encode_0_Limit = '\t';
   private static final char Encode_A_Begin = 'A';
   private static final char Encode_A_End = 'Z';
   private static final char Encode_A_Base = '\n';
   private static final char Encode_A_Limit = '#';
   private static final char Encode___Begin = '^';
   private static final char Encode___End = '_';
   private static final char Encode___Base = '$';
   private static final char Encode___Limit = '%';
   private static final char Encode_a_Begin = 'a';
   private static final char Encode_a_End = 'z';
   private static final char Encode_a_Base = '&';
   private static final char Encode_a_Limit = '?';
   static final int EncodeByteValue = 63;
   static final int EncodeDoubleByteValue = 4095;
   static final int EncodeShortSize = 3;
   static final int EncodeIntSize = 6;
   static final int EncodeLongSize = 11;
   static final int EncodeBytes = 4;
   static final int EncodeString = 3;

   private ContextEncode() {
   }

   static char byteEncode(int data) {
      return Base64[data];
   }

   static byte byteDecode(char data) {
      byte value;
      if (data >= 'a') {
         value = (byte)(data - 59);
      } else if (data >= '^') {
         value = (byte)(data - 58);
      } else if (data >= 'A') {
         value = (byte)(data - 55);
      } else {
         value = (byte)(data - 48);
      }

      return value;
   }

   static void encodeInt64(int value, char[] encode, int start, int length) {
      int index = start + length;

      do {
         --index;
         int digit = value & 63;
         encode[index] = byteEncode(digit);
         value >>>= 6;
      } while(value != 0 && index > start);

      while(index > start) {
         --index;
         encode[index] = '0';
      }

   }

   static void encodeInt64Byte(int value, byte[] encode, int start, int length) {
      int index = start + length;

      do {
         --index;
         int digit = value & 63;
         encode[index] = (byte)byteEncode(digit);
         value >>>= 6;
      } while(value != 0 && index > start);

      while(index > start) {
         --index;
         encode[index] = 48;
      }

   }

   static int encodeInt64var(int value, char[] encode, int start) {
      int shift = 36;

      int digit;
      do {
         shift -= 6;
         digit = value >>> shift & 63;
      } while(shift >= 0 && digit == 0);

      encode[start] = byteEncode(digit);

      int index;
      for(index = start + 1; shift >= 6; ++index) {
         shift -= 6;
         digit = value >>> shift & 63;
         encode[index] = byteEncode(digit);
      }

      return index - start;
   }

   static int decodeInt64(byte[] encode, int start, int length) {
      int index = start;
      int end = start + length;

      int value;
      for(value = 0; index < end; ++index) {
         value <<= 6;
         byte digit = byteDecode((char)encode[index]);
         value += digit;
      }

      return value;
   }

   static void encodeLong64(long value, char[] encode, int start, int length) {
      int index = start + length;

      do {
         --index;
         long digit = value & 63L;
         encode[index] = byteEncode((int)digit);
         value >>>= 6;
      } while(value != 0L && index > start);

      while(index > start) {
         --index;
         encode[index] = '0';
      }

   }

   static String encodeBytes(byte[] data) {
      int size = (data.length * 4 + 2) / 3;
      char[] encode = new char[size];
      int eIndex = 0;
      int dIndex = 0;

      int length;
      byte digit;
      for(length = data.length; length >= 3; length -= 3) {
         digit = (byte)(data[dIndex + 0] & 63);
         encode[eIndex + 0] = byteEncode(digit);
         digit = (byte)((data[dIndex + 0] & 192) >>> 2 | data[dIndex + 1] & 15);
         encode[eIndex + 1] = byteEncode(digit);
         digit = (byte)((data[dIndex + 1] & 240) >>> 2 | data[dIndex + 2] & 3);
         encode[eIndex + 2] = byteEncode(digit);
         digit = (byte)((data[dIndex + 2] & 252) >>> 2);
         encode[eIndex + 3] = byteEncode(digit);
         eIndex += 4;
         dIndex += 3;
      }

      if (length != 0) {
         digit = (byte)(data[dIndex + 0] & 63);
         encode[eIndex + 0] = byteEncode(digit);
         if (length != 1) {
            digit = (byte)((data[dIndex + 0] & 192) >>> 2 | data[dIndex + 1] & 15);
            encode[eIndex + 1] = byteEncode(digit);
            digit = (byte)((data[dIndex + 1] & 240) >>> 2);
            encode[eIndex + 2] = byteEncode(digit);
            eIndex += 3;
         } else {
            digit = (byte)((data[dIndex + 0] & 192) >>> 2);
            encode[eIndex + 1] = byteEncode(digit);
            eIndex += 2;
         }
      }

      while(eIndex < size) {
         encode[eIndex] = '0';
         ++eIndex;
      }

      return new String(encode);
   }

   static byte[] decodeBytes(String string) {
      char[] encode = string.toCharArray();
      int size = (encode.length * 3 + 3) / 4;
      byte[] decode = new byte[size];
      int length = encode.length;

      int eIndex;
      byte digit;
      for(eIndex = 0; eIndex < length; ++eIndex) {
         digit = byteDecode(encode[eIndex]);
         encode[eIndex] = (char)digit;
      }

      eIndex = 0;

      int dIndex;
      for(dIndex = 0; length >= 4; length -= 4) {
         digit = (byte)(encode[eIndex + 0] & 63 | (encode[eIndex + 1] & 48) << 2);
         decode[dIndex + 0] = digit;
         digit = (byte)(encode[eIndex + 1] & 15 | (encode[eIndex + 2] & 60) << 2);
         decode[dIndex + 1] = digit;
         digit = (byte)(encode[eIndex + 2] & 3 | (encode[eIndex + 3] & 63) << 2);
         decode[dIndex + 2] = digit;
         dIndex += 3;
         eIndex += 4;
      }

      if (length > 1) {
         digit = (byte)(encode[eIndex + 0] & 63 | (encode[eIndex + 1] & 48) << 2);
         decode[dIndex + 0] = digit;
         if (length > 2) {
            digit = (byte)(encode[eIndex + 1] & 15 | (encode[eIndex + 2] & 60) << 2);
            decode[dIndex + 1] = digit;
            dIndex += 2;
         } else {
            ++dIndex;
         }
      }

      while(dIndex < size) {
         decode[dIndex] = 0;
         ++dIndex;
      }

      return decode;
   }

   public static int byteCountToCharCount(int byteCount) {
      int remainder3 = byteCount % 3;
      int invRem3 = remainder3 == 0 ? 0 : 3 - remainder3;
      int retVal = (byteCount + invRem3) / 3 * 4 - invRem3;
      return retVal;
   }

   public static int charCountToByteCount(int charCount) {
      int remainder4 = charCount % 4;
      int invRem4 = remainder4 == 0 ? 0 : 4 - remainder4;
      int retVal = (charCount + invRem4) / 4 * 3 - invRem4;
      return retVal;
   }
}
