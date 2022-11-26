package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;

public class HexEncoder implements Encoder {
   private static final char[] LC_ENCODING_TABLE = new char[16];
   private static final char[] UC_ENCODING_TABLE = new char[16];
   private final boolean delimit;
   private final char[] table;

   public HexEncoder() {
      this(false, false);
   }

   public HexEncoder(boolean delimitBytes) {
      this(delimitBytes, false);
   }

   public HexEncoder(boolean delimitBytes, boolean uppercase) {
      this.delimit = delimitBytes;
      this.table = uppercase ? UC_ENCODING_TABLE : LC_ENCODING_TABLE;
   }

   public void encode(ByteBuffer input, CharBuffer output) throws EncodingException {
      while(input.hasRemaining()) {
         byte current = input.get();
         output.put(this.table[(current & 240) >> 4]);
         output.put(this.table[current & 15]);
         if (this.delimit && input.hasRemaining()) {
            output.put(':');
         }
      }

   }

   public void finalize(CharBuffer output) throws EncodingException {
   }

   public int outputSize(int inputSize) {
      int size = inputSize * 2;
      if (this.delimit) {
         size += inputSize - 1;
      }

      return size;
   }

   private static void initTable(String charset, char[] table) {
      for(int i = 0; i < charset.length(); ++i) {
         table[i] = charset.charAt(i);
      }

   }

   static {
      initTable("0123456789abcdef", LC_ENCODING_TABLE);
      initTable("0123456789ABCDEF", UC_ENCODING_TABLE);
   }
}
