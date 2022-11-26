package weblogic.servlet.http2.hpack;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Hpack {
   static final int DEFAULT_TABLE_SIZE = 4096;
   static final int HEADER_ENTRY_OVERHEAD = 32;

   protected static void encodeLiteral(HeaderEntry header, HeaderType literalType, int nameIndex, OutputStream out, boolean noHuffman) throws IOException {
      encodeInteger(literalType.getMask(), literalType.getPrefixBits(), nameIndex == -1 ? 0 : nameIndex, out);
      if (nameIndex == -1) {
         encodeStringLiteral(header.getName().getBytes(), out, noHuffman);
      }

      encodeStringLiteral(header.getValue(), out, noHuffman);
   }

   protected static void encodeInteger(int mask, int prefixBits, int index, OutputStream out) throws IOException {
      if (prefixBits >= 0 && prefixBits <= 8) {
         int nbits = 255 >>> 8 - prefixBits;
         if (index < nbits) {
            out.write(mask | index);
         } else {
            out.write(mask | nbits);

            int length;
            for(length = index - nbits; (length & -128) != 0; length >>>= 7) {
               out.write(length & 127 | 128);
            }

            out.write(length);
         }
      } else {
         throw new IllegalArgumentException("prefixBits[" + prefixBits + "] is not a valid value");
      }
   }

   public static int decodeInteger(ByteBuffer buffer, int n) {
      if (n > 0 && n < 32 && buffer.remaining() > 0) {
         buffer.mark();
         int mask = (1 << n) - 1;
         int index = mask & buffer.get();
         if (index < mask) {
            return index;
         } else {
            int m = 0;
            int b = false;

            while(buffer.remaining() != 0) {
               int b = buffer.get();
               index += (b & 127) * (1 << m);
               m += 7;
               if ((b & 128) != 128) {
                  return index;
               }
            }

            buffer.reset();
            return -1;
         }
      } else {
         return -1;
      }
   }

   protected static void encodeStringLiteral(byte[] bytes, OutputStream out, boolean noHuffman) throws IOException {
      int huffmanLength = HuffmanEncoder.countHuffmanCodeLength(bytes);
      if (huffmanLength < bytes.length && !noHuffman) {
         encodeInteger(128, 7, huffmanLength, out);
         out.write(HuffmanEncoder.encode(bytes));
      } else {
         encodeInteger(0, 7, bytes.length, out);
         out.write(bytes, 0, bytes.length);
      }

   }

   protected static byte[] decodeStringLiteral(ByteBuffer buffer) throws HpackException {
      if (buffer != null && buffer.hasRemaining()) {
         buffer.mark();
         byte firstByte = buffer.get();
         buffer.reset();
         int length = decodeInteger(buffer, 7);
         if (buffer.remaining() < length) {
            return null;
         } else {
            byte[] bytes = new byte[length];
            if ((firstByte & 128) != 0) {
               buffer.get(bytes);
               return HuffmanDecoder.decode(bytes);
            } else {
               buffer.get(bytes);
               return bytes;
            }
         }
      } else {
         return null;
      }
   }

   protected static String decodeStringLiteral(byte[] buf, int length, boolean noHuffman) throws HpackException {
      return !noHuffman ? new String(HuffmanDecoder.decode(buf)) : new String(buf);
   }
}
