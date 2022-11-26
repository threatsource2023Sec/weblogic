package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;

public abstract class AbstractBaseNEncoder implements Encoder {
   private static final String NEWLINE = System.lineSeparator();
   protected final int lineLength;
   private final char[] charset;
   private final int blockLength = this.getBlockLength();
   private final int bitsPerChar = this.getBitsPerChar();
   private final long initialBitMask;
   private long block;
   private int remaining;
   private int outCount;
   private boolean paddedOutput;

   public AbstractBaseNEncoder(char[] characterSet, int charactersPerLine) {
      this.remaining = this.blockLength;
      this.paddedOutput = true;
      this.charset = characterSet;
      long mask = 0L;

      for(int i = 1; i <= this.bitsPerChar; ++i) {
         mask |= 1L << this.blockLength - i;
      }

      this.initialBitMask = mask;
      this.lineLength = charactersPerLine;
   }

   public boolean isPaddedOutput() {
      return this.paddedOutput;
   }

   public void setPaddedOutput(boolean enabled) {
      this.paddedOutput = enabled;
   }

   public void encode(ByteBuffer input, CharBuffer output) throws EncodingException {
      while(input.hasRemaining()) {
         this.remaining -= 8;
         this.block |= ((long)input.get() & 255L) << this.remaining;
         if (this.remaining == 0) {
            this.writeOutput(output, 0);
         }
      }

   }

   public void finalize(CharBuffer output) throws EncodingException {
      if (this.remaining < this.blockLength) {
         int stop = this.remaining / this.bitsPerChar * this.bitsPerChar;
         this.writeOutput(output, stop);
         if (this.paddedOutput) {
            for(int i = stop; i > 0; i -= this.bitsPerChar) {
               output.put('=');
            }
         }
      }

      if (this.lineLength > 0 && output.position() > 0) {
         output.append(NEWLINE);
      }

      this.outCount = 0;
   }

   public int outputSize(int inputSize) {
      int len = (inputSize + this.blockLength / 8 - 1) * 8 / this.bitsPerChar;
      if (this.lineLength > 0) {
         len += (len / this.lineLength + 1) * NEWLINE.length();
      }

      return len;
   }

   protected abstract int getBlockLength();

   protected abstract int getBitsPerChar();

   protected static char[] encodingTable(String alphabet, int n) {
      if (alphabet.length() != n) {
         throw new IllegalArgumentException("Alphabet must be exactly " + n + " characters long");
      } else {
         char[] encodingTable = new char[n];

         for(int i = 0; i < n; ++i) {
            encodingTable[i] = alphabet.charAt(i);
         }

         return encodingTable;
      }
   }

   private void writeOutput(CharBuffer output, int stop) {
      int shift = this.blockLength;

      for(long mask = this.initialBitMask; shift > stop; mask >>= this.bitsPerChar) {
         shift -= this.bitsPerChar;
         int index = (int)((this.block & mask) >> shift);
         output.put(this.charset[index]);
         ++this.outCount;
         if (this.lineLength > 0 && this.outCount % this.lineLength == 0) {
            output.put(NEWLINE);
         }
      }

      this.block = 0L;
      this.remaining = this.blockLength;
   }
}
