package org.apache.xml.security.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/** @deprecated */
@Deprecated
public class Base64 {
   public static final int BASE64DEFAULTLENGTH = 76;
   private static final int BASELENGTH = 255;
   private static final int LOOKUPLENGTH = 64;
   private static final int TWENTYFOURBITGROUP = 24;
   private static final int EIGHTBIT = 8;
   private static final int SIXTEENBIT = 16;
   private static final int FOURBYTE = 4;
   private static final int SIGN = -128;
   private static final char PAD = '=';
   private static final byte[] base64Alphabet = new byte[255];
   private static final char[] lookUpBase64Alphabet = new char[64];

   private Base64() {
   }

   static final byte[] getBytes(BigInteger big, int bitlen) {
      bitlen = bitlen + 7 >> 3 << 3;
      if (bitlen < big.bitLength()) {
         throw new IllegalArgumentException(I18n.translate("utils.Base64.IllegalBitlength"));
      } else {
         byte[] bigBytes = big.toByteArray();
         if (big.bitLength() % 8 != 0 && big.bitLength() / 8 + 1 == bitlen / 8) {
            return bigBytes;
         } else {
            int startSrc = 0;
            int bigLen = bigBytes.length;
            if (big.bitLength() % 8 == 0) {
               startSrc = 1;
               --bigLen;
            }

            int startDst = bitlen / 8 - bigLen;
            byte[] resizedBytes = new byte[bitlen / 8];
            System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, bigLen);
            return resizedBytes;
         }
      }
   }

   public static final String encode(BigInteger big) {
      byte[] bytes = XMLUtils.getBytes(big, big.bitLength());
      return XMLUtils.encodeToString(bytes);
   }

   public static final byte[] encode(BigInteger big, int bitlen) {
      bitlen = bitlen + 7 >> 3 << 3;
      if (bitlen < big.bitLength()) {
         throw new IllegalArgumentException(I18n.translate("utils.Base64.IllegalBitlength"));
      } else {
         byte[] bigBytes = big.toByteArray();
         if (big.bitLength() % 8 != 0 && big.bitLength() / 8 + 1 == bitlen / 8) {
            return bigBytes;
         } else {
            int startSrc = 0;
            int bigLen = bigBytes.length;
            if (big.bitLength() % 8 == 0) {
               startSrc = 1;
               --bigLen;
            }

            int startDst = bitlen / 8 - bigLen;
            byte[] resizedBytes = new byte[bitlen / 8];
            System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, bigLen);
            return resizedBytes;
         }
      }
   }

   public static final BigInteger decodeBigIntegerFromElement(Element element) throws Base64DecodingException {
      return new BigInteger(1, decode(element));
   }

   public static final BigInteger decodeBigIntegerFromText(Text text) throws Base64DecodingException {
      return new BigInteger(1, decode(text.getData()));
   }

   public static final void fillElementWithBigInteger(Element element, BigInteger biginteger) {
      String encodedInt = encode(biginteger);
      if (!XMLUtils.ignoreLineBreaks() && encodedInt.length() > 76) {
         encodedInt = "\n" + encodedInt + "\n";
      }

      Document doc = element.getOwnerDocument();
      Text text = doc.createTextNode(encodedInt);
      element.appendChild(text);
   }

   public static final byte[] decode(Element element) throws Base64DecodingException {
      Node sibling = element.getFirstChild();

      StringBuilder sb;
      for(sb = new StringBuilder(); sibling != null; sibling = sibling.getNextSibling()) {
         if (sibling.getNodeType() == 3) {
            Text t = (Text)sibling;
            sb.append(t.getData());
         }
      }

      return decode(sb.toString());
   }

   public static final Element encodeToElement(Document doc, String localName, byte[] bytes) {
      Element el = XMLUtils.createElementInSignatureSpace(doc, localName);
      Text text = doc.createTextNode(encode(bytes));
      el.appendChild(text);
      return el;
   }

   public static final byte[] decode(byte[] base64) throws Base64DecodingException {
      return decodeInternal(base64, -1);
   }

   public static final String encode(byte[] binaryData) {
      return XMLUtils.ignoreLineBreaks() ? encode(binaryData, Integer.MAX_VALUE) : encode((byte[])binaryData, 76);
   }

   public static final byte[] decode(BufferedReader reader) throws IOException, Base64DecodingException {
      byte[] retBytes = null;
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();

      byte[] retBytes;
      try {
         String line;
         while(null != (line = reader.readLine())) {
            byte[] bytes = decode(line);
            baos.write(bytes);
         }

         retBytes = baos.toByteArray();
      } finally {
         baos.close();
      }

      return retBytes;
   }

   protected static final boolean isWhiteSpace(byte octet) {
      return octet == 32 || octet == 13 || octet == 10 || octet == 9;
   }

   protected static final boolean isPad(byte octet) {
      return octet == 61;
   }

   public static final String encode(byte[] binaryData, int length) {
      if (length < 4) {
         length = Integer.MAX_VALUE;
      }

      if (binaryData == null) {
         return null;
      } else {
         long lengthDataBits = (long)binaryData.length * 8L;
         if (lengthDataBits == 0L) {
            return "";
         } else {
            long fewerThan24bits = lengthDataBits % 24L;
            int numberTriplets = (int)(lengthDataBits / 24L);
            int numberQuartet = fewerThan24bits != 0L ? numberTriplets + 1 : numberTriplets;
            int quartesPerLine = length / 4;
            int numberLines = (numberQuartet - 1) / quartesPerLine;
            char[] encodedData = null;
            char[] encodedData = new char[numberQuartet * 4 + numberLines * 2];
            byte k = false;
            byte l = false;
            byte b1 = false;
            byte b2 = false;
            byte b3 = false;
            int encodedIndex = 0;
            int dataIndex = 0;
            int i = 0;

            byte val1;
            byte k;
            byte l;
            byte b1;
            byte b2;
            byte b3;
            for(int line = 0; line < numberLines; ++line) {
               for(int quartet = 0; quartet < 19; ++quartet) {
                  b1 = binaryData[dataIndex++];
                  b2 = binaryData[dataIndex++];
                  b3 = binaryData[dataIndex++];
                  l = (byte)(b2 & 15);
                  k = (byte)(b1 & 3);
                  val1 = (b1 & -128) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 192);
                  byte val2 = (b2 & -128) == 0 ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 240);
                  byte val3 = (b3 & -128) == 0 ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 252);
                  encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
                  encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
                  encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2 | val3];
                  encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 63];
                  ++i;
               }

               encodedData[encodedIndex++] = '\r';
               encodedData[encodedIndex++] = '\n';
            }

            byte val1;
            byte val2;
            while(i < numberTriplets) {
               b1 = binaryData[dataIndex++];
               b2 = binaryData[dataIndex++];
               b3 = binaryData[dataIndex++];
               l = (byte)(b2 & 15);
               k = (byte)(b1 & 3);
               val1 = (b1 & -128) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 192);
               val2 = (b2 & -128) == 0 ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 240);
               val1 = (b3 & -128) == 0 ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 252);
               encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
               encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
               encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2 | val1];
               encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 63];
               ++i;
            }

            if (fewerThan24bits == 8L) {
               b1 = binaryData[dataIndex];
               k = (byte)(b1 & 3);
               val1 = (b1 & -128) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 192);
               encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
               encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
               encodedData[encodedIndex++] = '=';
               encodedData[encodedIndex++] = '=';
            } else if (fewerThan24bits == 16L) {
               b1 = binaryData[dataIndex];
               b2 = binaryData[dataIndex + 1];
               l = (byte)(b2 & 15);
               k = (byte)(b1 & 3);
               val1 = (b1 & -128) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 192);
               val2 = (b2 & -128) == 0 ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 240);
               encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
               encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
               encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
               encodedData[encodedIndex++] = '=';
            }

            return new String(encodedData);
         }
      }
   }

   public static final byte[] decode(String encoded) throws Base64DecodingException {
      if (encoded == null) {
         return null;
      } else {
         byte[] bytes = new byte[encoded.length()];
         int len = getBytesInternal(encoded, bytes);
         return decodeInternal(bytes, len);
      }
   }

   protected static final int getBytesInternal(String s, byte[] result) {
      int length = s.length();
      int newSize = 0;

      for(int i = 0; i < length; ++i) {
         byte dataS = (byte)s.charAt(i);
         if (!isWhiteSpace(dataS)) {
            result[newSize++] = dataS;
         }
      }

      return newSize;
   }

   protected static final byte[] decodeInternal(byte[] base64Data, int len) throws Base64DecodingException {
      if (len == -1) {
         len = removeWhiteSpace(base64Data);
      }

      if (len % 4 != 0) {
         throw new Base64DecodingException("decoding.divisible.four");
      } else {
         int numberQuadruple = len / 4;
         if (numberQuadruple == 0) {
            return new byte[0];
         } else {
            byte[] decodedData = null;
            byte b1 = false;
            byte b2 = false;
            byte b3 = false;
            byte b4 = false;
            int i = false;
            int encodedIndex = false;
            int dataIndex = false;
            int dataIndex = (numberQuadruple - 1) * 4;
            int encodedIndex = (numberQuadruple - 1) * 3;
            byte b1 = base64Alphabet[base64Data[dataIndex++]];
            byte b2 = base64Alphabet[base64Data[dataIndex++]];
            if (b1 != -1 && b2 != -1) {
               byte d3;
               byte b3 = base64Alphabet[d3 = base64Data[dataIndex++]];
               byte d4;
               byte b4 = base64Alphabet[d4 = base64Data[dataIndex++]];
               byte[] decodedData;
               if (b3 != -1 && b4 != -1) {
                  decodedData = new byte[encodedIndex + 3];
                  decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
                  decodedData[encodedIndex++] = (byte)((b2 & 15) << 4 | b3 >> 2 & 15);
                  decodedData[encodedIndex++] = (byte)(b3 << 6 | b4);
               } else if (isPad(d3) && isPad(d4)) {
                  if ((b2 & 15) != 0) {
                     throw new Base64DecodingException("decoding.general");
                  }

                  decodedData = new byte[encodedIndex + 1];
                  decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
               } else {
                  if (isPad(d3) || !isPad(d4)) {
                     throw new Base64DecodingException("decoding.general");
                  }

                  if ((b3 & 3) != 0) {
                     throw new Base64DecodingException("decoding.general");
                  }

                  decodedData = new byte[encodedIndex + 2];
                  decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
                  decodedData[encodedIndex] = (byte)((b2 & 15) << 4 | b3 >> 2 & 15);
               }

               encodedIndex = 0;
               dataIndex = 0;

               for(int i = numberQuadruple - 1; i > 0; --i) {
                  b1 = base64Alphabet[base64Data[dataIndex++]];
                  b2 = base64Alphabet[base64Data[dataIndex++]];
                  b3 = base64Alphabet[base64Data[dataIndex++]];
                  b4 = base64Alphabet[base64Data[dataIndex++]];
                  if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1) {
                     throw new Base64DecodingException("decoding.general");
                  }

                  decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
                  decodedData[encodedIndex++] = (byte)((b2 & 15) << 4 | b3 >> 2 & 15);
                  decodedData[encodedIndex++] = (byte)(b3 << 6 | b4);
               }

               return decodedData;
            } else {
               throw new Base64DecodingException("decoding.general");
            }
         }
      }
   }

   public static final void decode(String base64Data, OutputStream os) throws Base64DecodingException, IOException {
      byte[] bytes = new byte[base64Data.length()];
      int len = getBytesInternal(base64Data, bytes);
      decode(bytes, os, len);
   }

   public static final void decode(byte[] base64Data, OutputStream os) throws Base64DecodingException, IOException {
      decode(base64Data, os, -1);
   }

   protected static final void decode(byte[] base64Data, OutputStream os, int len) throws Base64DecodingException, IOException {
      if (len == -1) {
         len = removeWhiteSpace(base64Data);
      }

      if (len % 4 != 0) {
         throw new Base64DecodingException("decoding.divisible.four");
      } else {
         int numberQuadruple = len / 4;
         if (numberQuadruple != 0) {
            byte b1 = false;
            byte b2 = false;
            byte b3 = false;
            byte b4 = false;
            int i = false;
            int dataIndex = 0;

            byte b1;
            byte b2;
            byte b3;
            byte b4;
            for(int i = numberQuadruple - 1; i > 0; --i) {
               b1 = base64Alphabet[base64Data[dataIndex++]];
               b2 = base64Alphabet[base64Data[dataIndex++]];
               b3 = base64Alphabet[base64Data[dataIndex++]];
               b4 = base64Alphabet[base64Data[dataIndex++]];
               if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1) {
                  throw new Base64DecodingException("decoding.general");
               }

               os.write((byte)(b1 << 2 | b2 >> 4));
               os.write((byte)((b2 & 15) << 4 | b3 >> 2 & 15));
               os.write((byte)(b3 << 6 | b4));
            }

            b1 = base64Alphabet[base64Data[dataIndex++]];
            b2 = base64Alphabet[base64Data[dataIndex++]];
            if (b1 != -1 && b2 != -1) {
               byte d3;
               b3 = base64Alphabet[d3 = base64Data[dataIndex++]];
               byte d4;
               b4 = base64Alphabet[d4 = base64Data[dataIndex++]];
               if (b3 != -1 && b4 != -1) {
                  os.write((byte)(b1 << 2 | b2 >> 4));
                  os.write((byte)((b2 & 15) << 4 | b3 >> 2 & 15));
                  os.write((byte)(b3 << 6 | b4));
               } else if (isPad(d3) && isPad(d4)) {
                  if ((b2 & 15) != 0) {
                     throw new Base64DecodingException("decoding.general");
                  }

                  os.write((byte)(b1 << 2 | b2 >> 4));
               } else {
                  if (isPad(d3) || !isPad(d4)) {
                     throw new Base64DecodingException("decoding.general");
                  }

                  if ((b3 & 3) != 0) {
                     throw new Base64DecodingException("decoding.general");
                  }

                  os.write((byte)(b1 << 2 | b2 >> 4));
                  os.write((byte)((b2 & 15) << 4 | b3 >> 2 & 15));
               }

            } else {
               throw new Base64DecodingException("decoding.general");
            }
         }
      }
   }

   public static final void decode(InputStream is, OutputStream os) throws Base64DecodingException, IOException {
      byte b1 = false;
      byte b2 = false;
      byte b3 = false;
      byte b4 = false;
      int index = 0;
      byte[] data = new byte[4];

      int read;
      byte readed;
      byte b1;
      byte b2;
      byte b3;
      byte b4;
      while((read = is.read()) > 0) {
         readed = (byte)read;
         if (!isWhiteSpace(readed)) {
            if (isPad(readed)) {
               data[index++] = readed;
               if (index == 3) {
                  data[index++] = (byte)is.read();
               }
               break;
            }

            if ((data[index++] = readed) == -1) {
               throw new Base64DecodingException("decoding.general");
            }

            if (index == 4) {
               index = 0;
               b1 = base64Alphabet[data[0]];
               b2 = base64Alphabet[data[1]];
               b3 = base64Alphabet[data[2]];
               b4 = base64Alphabet[data[3]];
               os.write((byte)(b1 << 2 | b2 >> 4));
               os.write((byte)((b2 & 15) << 4 | b3 >> 2 & 15));
               os.write((byte)(b3 << 6 | b4));
            }
         }
      }

      readed = data[0];
      byte d2 = data[1];
      byte d3 = data[2];
      byte d4 = data[3];
      b1 = base64Alphabet[readed];
      b2 = base64Alphabet[d2];
      b3 = base64Alphabet[d3];
      b4 = base64Alphabet[d4];
      if (b3 != -1 && b4 != -1) {
         os.write((byte)(b1 << 2 | b2 >> 4));
         os.write((byte)((b2 & 15) << 4 | b3 >> 2 & 15));
         os.write((byte)(b3 << 6 | b4));
      } else if (isPad(d3) && isPad(d4)) {
         if ((b2 & 15) != 0) {
            throw new Base64DecodingException("decoding.general");
         }

         os.write((byte)(b1 << 2 | b2 >> 4));
      } else {
         if (isPad(d3) || !isPad(d4)) {
            throw new Base64DecodingException("decoding.general");
         }

         b3 = base64Alphabet[d3];
         if ((b3 & 3) != 0) {
            throw new Base64DecodingException("decoding.general");
         }

         os.write((byte)(b1 << 2 | b2 >> 4));
         os.write((byte)((b2 & 15) << 4 | b3 >> 2 & 15));
      }

   }

   protected static final int removeWhiteSpace(byte[] data) {
      if (data == null) {
         return 0;
      } else {
         int newSize = 0;
         int len = data.length;

         for(int i = 0; i < len; ++i) {
            byte dataS = data[i];
            if (!isWhiteSpace(dataS)) {
               data[newSize++] = dataS;
            }
         }

         return newSize;
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
         lookUpBase64Alphabet[i] = (char)(65 + i);
      }

      i = 26;

      int j;
      for(j = 0; i <= 51; ++j) {
         lookUpBase64Alphabet[i] = (char)(97 + j);
         ++i;
      }

      i = 52;

      for(j = 0; i <= 61; ++j) {
         lookUpBase64Alphabet[i] = (char)(48 + j);
         ++i;
      }

      lookUpBase64Alphabet[62] = '+';
      lookUpBase64Alphabet[63] = '/';
   }
}
