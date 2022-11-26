package weblogic.servlet.http2.hpack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HuffmanEncoder {
   public static byte[] encode(byte[] bytes) throws IOException {
      if (bytes == null) {
         throw new NullPointerException("Huffman Encoder got an Empty data!");
      } else {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         long tmpCode = 0L;
         int bytesRemain = 0;
         int index = false;
         byte[] var6 = bytes;
         int var7 = bytes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            byte b = var6[var8];
            int index = b & 255;
            tmpCode <<= HuffmanTable.getHuffmanCode(index).getLenInBits();
            tmpCode |= (long)HuffmanTable.getHuffmanCode(index).getHexCode();
            bytesRemain += HuffmanTable.getHuffmanCode(index).getLenInBits();

            while(bytesRemain >= 8) {
               bytesRemain -= 8;
               out.write((int)(tmpCode >> bytesRemain));
            }
         }

         if (bytesRemain > 0) {
            tmpCode <<= 8 - bytesRemain;
            tmpCode |= (long)(255 >>> bytesRemain);
            out.write((int)tmpCode);
         }

         return out.toByteArray();
      }
   }

   protected static int countHuffmanCodeLength(byte[] data) {
      if (data == null) {
         throw new NullPointerException("Huffman Encoder got an Empty data!");
      } else {
         long huffmanCodeLength = 0L;
         byte[] var3 = data;
         int var4 = data.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            huffmanCodeLength += (long)HuffmanTable.getHuffmanCode(b & 255).getLenInBits();
         }

         return (int)(huffmanCodeLength + 7L >> 3);
      }
   }
}
