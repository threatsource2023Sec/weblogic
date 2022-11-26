package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;

public abstract class AbstractBaseNDecoder implements Decoder {
   private final char[] block = new char[this.getBlockLength() / this.getBitsPerChar()];
   private final byte[] table;
   private int blockPos;
   private boolean paddedInput = true;

   public AbstractBaseNDecoder(byte[] decodingTable) {
      this.table = decodingTable;
   }

   public boolean isPaddedInput() {
      return this.paddedInput;
   }

   public void setPaddedInput(boolean enabled) {
      this.paddedInput = enabled;
   }

   public void decode(CharBuffer input, ByteBuffer output) throws EncodingException {
      while(input.hasRemaining()) {
         char current = input.get();
         if (!Character.isWhitespace(current) && current != '=') {
            this.block[this.blockPos++] = current;
            if (this.blockPos == this.block.length) {
               this.writeOutput(output, this.block.length);
            }
         }
      }

   }

   public void finalize(ByteBuffer output) throws EncodingException {
      if (this.blockPos > 0) {
         this.writeOutput(output, this.blockPos);
      }

   }

   public int outputSize(int inputSize) {
      int size;
      if (this.paddedInput) {
         size = inputSize;
      } else {
         size = inputSize + this.getBlockLength() / 8 - 1;
      }

      return size * this.getBitsPerChar() / 8;
   }

   protected abstract int getBlockLength();

   protected abstract int getBitsPerChar();

   protected static byte[] decodingTable(String alphabet, int n) {
      if (alphabet.length() != n) {
         throw new IllegalArgumentException("Alphabet must be exactly " + n + " characters long");
      } else {
         byte[] decodingTable = new byte[128];

         for(int i = 0; i < n; ++i) {
            decodingTable[alphabet.charAt(i)] = (byte)i;
         }

         return decodingTable;
      }
   }

   private void writeOutput(ByteBuffer output, int len) {
      long value = 0L;
      int shift = this.getBlockLength();

      int i;
      for(i = 0; i < len; ++i) {
         long b = (long)this.table[this.block[i] & 127];
         if (b < 0L) {
            throw new EncodingException("Invalid character " + this.block[i]);
         }

         shift -= this.getBitsPerChar();
         value |= b << shift;
      }

      i = shift + this.getBitsPerChar() - 1;
      int offset = this.getBlockLength();

      while(offset > i) {
         offset -= 8;
         output.put((byte)((int)((value & 255L << offset) >> offset)));
      }

      this.blockPos = 0;
   }
}
