package org.python.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.OutputStream;
import org.python.apache.commons.compress.compressors.CompressorOutputStream;
import org.python.apache.commons.compress.compressors.lz77support.LZ77Compressor;
import org.python.apache.commons.compress.compressors.lz77support.Parameters;
import org.python.apache.commons.compress.utils.ByteUtils;

public class SnappyCompressorOutputStream extends CompressorOutputStream {
   private final LZ77Compressor compressor;
   private final OutputStream os;
   private final ByteUtils.ByteConsumer consumer;
   private final byte[] oneByte;
   private boolean finished;
   private static final int MAX_LITERAL_SIZE_WITHOUT_SIZE_BYTES = 60;
   private static final int MAX_LITERAL_SIZE_WITH_ONE_SIZE_BYTE = 256;
   private static final int MAX_LITERAL_SIZE_WITH_TWO_SIZE_BYTES = 65536;
   private static final int MAX_LITERAL_SIZE_WITH_THREE_SIZE_BYTES = 16777216;
   private static final int ONE_SIZE_BYTE_MARKER = 240;
   private static final int TWO_SIZE_BYTE_MARKER = 244;
   private static final int THREE_SIZE_BYTE_MARKER = 248;
   private static final int FOUR_SIZE_BYTE_MARKER = 252;
   private static final int MIN_MATCH_LENGTH_WITH_ONE_OFFSET_BYTE = 4;
   private static final int MAX_MATCH_LENGTH_WITH_ONE_OFFSET_BYTE = 11;
   private static final int MAX_OFFSET_WITH_ONE_OFFSET_BYTE = 1024;
   private static final int MAX_OFFSET_WITH_TWO_OFFSET_BYTES = 32768;
   private static final int ONE_BYTE_COPY_TAG = 1;
   private static final int TWO_BYTE_COPY_TAG = 2;
   private static final int FOUR_BYTE_COPY_TAG = 3;
   private static final int MIN_MATCH_LENGTH = 4;
   private static final int MAX_MATCH_LENGTH = 64;

   public SnappyCompressorOutputStream(OutputStream os, long uncompressedSize) throws IOException {
      this(os, uncompressedSize, 32768);
   }

   public SnappyCompressorOutputStream(OutputStream os, long uncompressedSize, int blockSize) throws IOException {
      this(os, uncompressedSize, createParameterBuilder(blockSize).build());
   }

   public SnappyCompressorOutputStream(OutputStream os, long uncompressedSize, Parameters params) throws IOException {
      this.oneByte = new byte[1];
      this.finished = false;
      this.os = os;
      this.consumer = new ByteUtils.OutputStreamByteConsumer(os);
      this.compressor = new LZ77Compressor(params, new LZ77Compressor.Callback() {
         public void accept(LZ77Compressor.Block block) throws IOException {
            if (block instanceof LZ77Compressor.LiteralBlock) {
               SnappyCompressorOutputStream.this.writeLiteralBlock((LZ77Compressor.LiteralBlock)block);
            } else if (block instanceof LZ77Compressor.BackReference) {
               SnappyCompressorOutputStream.this.writeBackReference((LZ77Compressor.BackReference)block);
            }

         }
      });
      this.writeUncompressedSize(uncompressedSize);
   }

   public void write(int b) throws IOException {
      this.oneByte[0] = (byte)(b & 255);
      this.write(this.oneByte);
   }

   public void write(byte[] data, int off, int len) throws IOException {
      this.compressor.compress(data, off, len);
   }

   public void close() throws IOException {
      this.finish();
      this.os.close();
   }

   public void finish() throws IOException {
      if (!this.finished) {
         this.compressor.finish();
         this.finished = true;
      }

   }

   private void writeUncompressedSize(long uncompressedSize) throws IOException {
      boolean more = false;

      do {
         int currentByte = (int)(uncompressedSize & 127L);
         more = uncompressedSize > (long)currentByte;
         if (more) {
            currentByte |= 128;
         }

         this.os.write(currentByte);
         uncompressedSize >>= 7;
      } while(more);

   }

   private void writeLiteralBlock(LZ77Compressor.LiteralBlock block) throws IOException {
      int len = block.getLength();
      if (len <= 60) {
         this.writeLiteralBlockNoSizeBytes(block, len);
      } else if (len <= 256) {
         this.writeLiteralBlockOneSizeByte(block, len);
      } else if (len <= 65536) {
         this.writeLiteralBlockTwoSizeBytes(block, len);
      } else if (len <= 16777216) {
         this.writeLiteralBlockThreeSizeBytes(block, len);
      } else {
         this.writeLiteralBlockFourSizeBytes(block, len);
      }

   }

   private void writeLiteralBlockNoSizeBytes(LZ77Compressor.LiteralBlock block, int len) throws IOException {
      this.writeLiteralBlockWithSize(len - 1 << 2, 0, len, block);
   }

   private void writeLiteralBlockOneSizeByte(LZ77Compressor.LiteralBlock block, int len) throws IOException {
      this.writeLiteralBlockWithSize(240, 1, len, block);
   }

   private void writeLiteralBlockTwoSizeBytes(LZ77Compressor.LiteralBlock block, int len) throws IOException {
      this.writeLiteralBlockWithSize(244, 2, len, block);
   }

   private void writeLiteralBlockThreeSizeBytes(LZ77Compressor.LiteralBlock block, int len) throws IOException {
      this.writeLiteralBlockWithSize(248, 3, len, block);
   }

   private void writeLiteralBlockFourSizeBytes(LZ77Compressor.LiteralBlock block, int len) throws IOException {
      this.writeLiteralBlockWithSize(252, 4, len, block);
   }

   private void writeLiteralBlockWithSize(int tagByte, int sizeBytes, int len, LZ77Compressor.LiteralBlock block) throws IOException {
      this.os.write(tagByte);
      this.writeLittleEndian(sizeBytes, len - 1);
      this.os.write(block.getData(), block.getOffset(), len);
   }

   private void writeLittleEndian(int numBytes, int num) throws IOException {
      ByteUtils.toLittleEndian(this.consumer, (long)num, numBytes);
   }

   private void writeBackReference(LZ77Compressor.BackReference block) throws IOException {
      int len = block.getLength();
      int offset = block.getOffset();
      if (len >= 4 && len <= 11 && offset <= 1024) {
         this.writeBackReferenceWithOneOffsetByte(len, offset);
      } else if (offset < 32768) {
         this.writeBackReferenceWithTwoOffsetBytes(len, offset);
      } else {
         this.writeBackReferenceWithFourOffsetBytes(len, offset);
      }

   }

   private void writeBackReferenceWithOneOffsetByte(int len, int offset) throws IOException {
      this.os.write(1 | len - 4 << 2 | (offset & 1792) >> 3);
      this.os.write(offset & 255);
   }

   private void writeBackReferenceWithTwoOffsetBytes(int len, int offset) throws IOException {
      this.writeBackReferenceWithLittleEndianOffset(2, 2, len, offset);
   }

   private void writeBackReferenceWithFourOffsetBytes(int len, int offset) throws IOException {
      this.writeBackReferenceWithLittleEndianOffset(3, 4, len, offset);
   }

   private void writeBackReferenceWithLittleEndianOffset(int tag, int offsetBytes, int len, int offset) throws IOException {
      this.os.write(tag | len - 1 << 2);
      this.writeLittleEndian(offsetBytes, offset);
   }

   public static Parameters.Builder createParameterBuilder(int blockSize) {
      return Parameters.builder(blockSize).withMinBackReferenceLength(4).withMaxBackReferenceLength(64).withMaxOffset(blockSize).withMaxLiteralLength(blockSize);
   }
}
