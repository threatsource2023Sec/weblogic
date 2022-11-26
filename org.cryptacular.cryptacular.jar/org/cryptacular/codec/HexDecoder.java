package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;
import org.cryptacular.EncodingException;

public class HexDecoder implements Decoder {
   private static final byte[] DECODING_TABLE = new byte[128];
   private int count;

   public void decode(CharBuffer input, ByteBuffer output) throws EncodingException {
      if (input.get(0) == '0' && input.get(1) == 'x') {
         input.position(input.position() + 2);
      }

      byte hi = 0;

      while(input.hasRemaining()) {
         char current = input.get();
         if (current != ':' && !Character.isWhitespace(current)) {
            if ((this.count++ & 1) == 0) {
               hi = lookup(current);
            } else {
               byte lo = lookup(current);
               output.put((byte)(hi << 4 | lo));
            }
         }
      }

   }

   public void finalize(ByteBuffer output) throws EncodingException {
      this.count = 0;
   }

   public int outputSize(int inputSize) {
      return inputSize / 2;
   }

   private static byte lookup(char c) {
      byte b = DECODING_TABLE[c & 127];
      if (b < 0) {
         throw new EncodingException("Invalid hex character " + c);
      } else {
         return b;
      }
   }

   static {
      Arrays.fill(DECODING_TABLE, (byte)-1);

      int i;
      for(i = 0; i < 10; ++i) {
         DECODING_TABLE[i + 48] = (byte)i;
      }

      for(i = 0; i < 6; ++i) {
         DECODING_TABLE[i + 65] = (byte)(10 + i);
         DECODING_TABLE[i + 97] = (byte)(10 + i);
      }

   }
}
