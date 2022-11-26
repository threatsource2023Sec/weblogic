package org.python.apache.commons.compress.compressors.lz4;

import java.io.IOException;
import java.io.InputStream;
import org.python.apache.commons.compress.compressors.lz77support.AbstractLZ77CompressorInputStream;
import org.python.apache.commons.compress.utils.ByteUtils;

public class BlockLZ4CompressorInputStream extends AbstractLZ77CompressorInputStream {
   static final int WINDOW_SIZE = 65536;
   static final int SIZE_BITS = 4;
   static final int BACK_REFERENCE_SIZE_MASK = 15;
   static final int LITERAL_SIZE_MASK = 240;
   private int nextBackReferenceSize;
   private State state;

   public BlockLZ4CompressorInputStream(InputStream is) throws IOException {
      super(is, 65536);
      this.state = BlockLZ4CompressorInputStream.State.NO_BLOCK;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      switch (this.state) {
         case EOF:
            return -1;
         case NO_BLOCK:
            this.readSizes();
         case IN_LITERAL:
            int litLen = this.readLiteral(b, off, len);
            if (!this.hasMoreDataInBlock()) {
               this.state = BlockLZ4CompressorInputStream.State.LOOKING_FOR_BACK_REFERENCE;
            }

            return litLen > 0 ? litLen : this.read(b, off, len);
         case LOOKING_FOR_BACK_REFERENCE:
            if (!this.initializeBackReference()) {
               this.state = BlockLZ4CompressorInputStream.State.EOF;
               return -1;
            }
         case IN_BACK_REFERENCE:
            int backReferenceLen = this.readBackReference(b, off, len);
            if (!this.hasMoreDataInBlock()) {
               this.state = BlockLZ4CompressorInputStream.State.NO_BLOCK;
            }

            return backReferenceLen > 0 ? backReferenceLen : this.read(b, off, len);
         default:
            throw new IOException("Unknown stream state " + this.state);
      }
   }

   private void readSizes() throws IOException {
      int nextBlock = this.readOneByte();
      if (nextBlock == -1) {
         throw new IOException("Premature end of stream while looking for next block");
      } else {
         this.nextBackReferenceSize = nextBlock & 15;
         long literalSizePart = (long)((nextBlock & 240) >> 4);
         if (literalSizePart == 15L) {
            literalSizePart += this.readSizeBytes();
         }

         this.startLiteral(literalSizePart);
         this.state = BlockLZ4CompressorInputStream.State.IN_LITERAL;
      }
   }

   private long readSizeBytes() throws IOException {
      long accum = 0L;

      int nextByte;
      do {
         nextByte = this.readOneByte();
         if (nextByte == -1) {
            throw new IOException("Premature end of stream while parsing length");
         }

         accum += (long)nextByte;
      } while(nextByte == 255);

      return accum;
   }

   private boolean initializeBackReference() throws IOException {
      int backReferenceOffset = false;

      int backReferenceOffset;
      try {
         backReferenceOffset = (int)ByteUtils.fromLittleEndian((ByteUtils.ByteSupplier)this.supplier, 2);
      } catch (IOException var5) {
         if (this.nextBackReferenceSize == 0) {
            return false;
         }

         throw var5;
      }

      long backReferenceSize = (long)this.nextBackReferenceSize;
      if (this.nextBackReferenceSize == 15) {
         backReferenceSize += this.readSizeBytes();
      }

      this.startBackReference(backReferenceOffset, backReferenceSize + 4L);
      this.state = BlockLZ4CompressorInputStream.State.IN_BACK_REFERENCE;
      return true;
   }

   private static enum State {
      NO_BLOCK,
      IN_LITERAL,
      LOOKING_FOR_BACK_REFERENCE,
      IN_BACK_REFERENCE,
      EOF;
   }
}
