package com.bea.xbean.util;

public final class Base64 {
   private static final int BASELENGTH = 255;
   private static final int LOOKUPLENGTH = 64;
   private static final int TWENTYFOURBITGROUP = 24;
   private static final int EIGHTBIT = 8;
   private static final int SIXTEENBIT = 16;
   private static final int FOURBYTE = 4;
   private static final int SIGN = -128;
   private static final byte PAD = 61;
   private static final boolean fDebug = false;
   private static byte[] base64Alphabet = new byte[255];
   private static byte[] lookUpBase64Alphabet = new byte[64];

   protected static boolean isWhiteSpace(byte octect) {
      return octect == 32 || octect == 13 || octect == 10 || octect == 9;
   }

   protected static boolean isPad(byte octect) {
      return octect == 61;
   }

   protected static boolean isData(byte octect) {
      return base64Alphabet[octect] != -1;
   }

   protected static boolean isBase64(byte octect) {
      return isWhiteSpace(octect) || isPad(octect) || isData(octect);
   }

   public static byte[] encode(byte[] binaryData) {
      if (binaryData == null) {
         return null;
      } else {
         int lengthDataBits = binaryData.length * 8;
         int fewerThan24bits = lengthDataBits % 24;
         int numberTriplets = lengthDataBits / 24;
         byte[] encodedData = null;
         byte[] encodedData;
         if (fewerThan24bits != 0) {
            encodedData = new byte[(numberTriplets + 1) * 4];
         } else {
            encodedData = new byte[numberTriplets * 4];
         }

         byte k = false;
         byte l = false;
         byte b1 = false;
         byte b2 = false;
         byte b3 = false;
         int encodedIndex = false;
         int dataIndex = false;
         int i = false;

         byte val1;
         byte val2;
         byte k;
         byte l;
         byte b1;
         byte b2;
         int encodedIndex;
         int dataIndex;
         int i;
         for(i = 0; i < numberTriplets; ++i) {
            dataIndex = i * 3;
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            byte b3 = binaryData[dataIndex + 2];
            l = (byte)(b2 & 15);
            k = (byte)(b1 & 3);
            encodedIndex = i * 4;
            val1 = (b1 & -128) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 192);
            val2 = (b2 & -128) == 0 ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 240);
            byte val3 = (b3 & -128) == 0 ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 252);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | k << 4];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2 | val3];
            encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 63];
         }

         dataIndex = i * 3;
         encodedIndex = i * 4;
         if (fewerThan24bits == 8) {
            b1 = binaryData[dataIndex];
            k = (byte)(b1 & 3);
            val1 = (b1 & -128) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 192);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
            encodedData[encodedIndex + 2] = 61;
            encodedData[encodedIndex + 3] = 61;
         } else if (fewerThan24bits == 16) {
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            l = (byte)(b2 & 15);
            k = (byte)(b1 & 3);
            val1 = (b1 & -128) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 192);
            val2 = (b2 & -128) == 0 ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 240);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | k << 4];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
            encodedData[encodedIndex + 3] = 61;
         }

         return encodedData;
      }
   }

   public static byte[] decode(byte[] base64Data) {
      if (base64Data == null) {
         return null;
      } else {
         base64Data = removeWhiteSpace(base64Data);
         if (base64Data.length % 4 != 0) {
            return null;
         } else {
            int numberQuadruple = base64Data.length / 4;
            if (numberQuadruple == 0) {
               return new byte[0];
            } else {
               byte[] decodedData = null;
               byte b1 = false;
               byte b2 = false;
               byte b3 = false;
               byte b4 = false;
               byte d1 = false;
               byte d2 = false;
               byte d3 = false;
               byte d4 = false;
               int i = 0;
               int encodedIndex = 0;
               int dataIndex = 0;

               byte[] decodedData;
               byte b1;
               byte b2;
               byte b3;
               byte b4;
               byte d1;
               byte d2;
               byte d3;
               byte d4;
               for(decodedData = new byte[numberQuadruple * 3]; i < numberQuadruple - 1; ++i) {
                  if (!isData(d1 = base64Data[dataIndex++]) || !isData(d2 = base64Data[dataIndex++]) || !isData(d3 = base64Data[dataIndex++]) || !isData(d4 = base64Data[dataIndex++])) {
                     return null;
                  }

                  b1 = base64Alphabet[d1];
                  b2 = base64Alphabet[d2];
                  b3 = base64Alphabet[d3];
                  b4 = base64Alphabet[d4];
                  decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
                  decodedData[encodedIndex++] = (byte)((b2 & 15) << 4 | b3 >> 2 & 15);
                  decodedData[encodedIndex++] = (byte)(b3 << 6 | b4);
               }

               if (isData(d1 = base64Data[dataIndex++]) && isData(d2 = base64Data[dataIndex++])) {
                  b1 = base64Alphabet[d1];
                  b2 = base64Alphabet[d2];
                  d3 = base64Data[dataIndex++];
                  d4 = base64Data[dataIndex++];
                  if (isData(d3) && isData(d4)) {
                     b3 = base64Alphabet[d3];
                     b4 = base64Alphabet[d4];
                     decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
                     decodedData[encodedIndex++] = (byte)((b2 & 15) << 4 | b3 >> 2 & 15);
                     decodedData[encodedIndex++] = (byte)(b3 << 6 | b4);
                     return decodedData;
                  } else {
                     byte[] tmp;
                     if (isPad(d3) && isPad(d4)) {
                        if ((b2 & 15) != 0) {
                           return null;
                        } else {
                           tmp = new byte[i * 3 + 1];
                           System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                           tmp[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
                           return tmp;
                        }
                     } else if (!isPad(d3) && isPad(d4)) {
                        b3 = base64Alphabet[d3];
                        if ((b3 & 3) != 0) {
                           return null;
                        } else {
                           tmp = new byte[i * 3 + 2];
                           System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                           tmp[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
                           tmp[encodedIndex] = (byte)((b2 & 15) << 4 | b3 >> 2 & 15);
                           return tmp;
                        }
                     } else {
                        return null;
                     }
                  }
               } else {
                  return null;
               }
            }
         }
      }
   }

   protected static byte[] removeWhiteSpace(byte[] data) {
      if (data == null) {
         return null;
      } else {
         int newSize = 0;
         int len = data.length;

         for(int i = 0; i < len; ++i) {
            if (!isWhiteSpace(data[i])) {
               ++newSize;
            }
         }

         if (newSize == len) {
            return data;
         } else {
            byte[] newArray = new byte[newSize];
            int j = 0;

            for(int i = 0; i < len; ++i) {
               if (!isWhiteSpace(data[i])) {
                  newArray[j++] = data[i];
               }
            }

            return newArray;
         }
      }
   }

   static {
      int i;
      for(i = 0; i < 255; ++i) {
         base64Alphabet[i] = -1;
      }

      for(i = 90; i >= 65; --i) {
         base64Alphabet[i] = (byte)(i - 65);
      }

      for(i = 122; i >= 97; --i) {
         base64Alphabet[i] = (byte)(i - 97 + 26);
      }

      for(i = 57; i >= 48; --i) {
         base64Alphabet[i] = (byte)(i - 48 + 52);
      }

      base64Alphabet[43] = 62;
      base64Alphabet[47] = 63;

      for(i = 0; i <= 25; ++i) {
         lookUpBase64Alphabet[i] = (byte)(65 + i);
      }

      i = 26;

      int j;
      for(j = 0; i <= 51; ++j) {
         lookUpBase64Alphabet[i] = (byte)(97 + j);
         ++i;
      }

      i = 52;

      for(j = 0; i <= 61; ++j) {
         lookUpBase64Alphabet[i] = (byte)(48 + j);
         ++i;
      }

      lookUpBase64Alphabet[62] = 43;
      lookUpBase64Alphabet[63] = 47;
   }
}
