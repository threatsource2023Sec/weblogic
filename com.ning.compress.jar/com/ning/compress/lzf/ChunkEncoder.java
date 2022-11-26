package com.ning.compress.lzf;

import com.ning.compress.BufferRecycler;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public abstract class ChunkEncoder implements Closeable {
   protected static final int MIN_BLOCK_TO_COMPRESS = 16;
   protected static final int MIN_HASH_SIZE = 256;
   protected static final int MAX_HASH_SIZE = 16384;
   protected static final int MAX_OFF = 8192;
   protected static final int MAX_REF = 264;
   protected static final int TAIL_LENGTH = 4;
   protected final BufferRecycler _recycler;
   protected int[] _hashTable;
   protected final int _hashModulo;
   protected byte[] _encodeBuffer;
   protected byte[] _headerBuffer;

   protected ChunkEncoder(int totalLength) {
      this(totalLength, BufferRecycler.instance());
   }

   protected ChunkEncoder(int totalLength, BufferRecycler bufferRecycler) {
      int largestChunkLen = Math.min(totalLength, 65535);
      int suggestedHashLen = calcHashLen(largestChunkLen);
      this._recycler = bufferRecycler;
      this._hashTable = bufferRecycler.allocEncodingHash(suggestedHashLen);
      this._hashModulo = this._hashTable.length - 1;
      int bufferLen = largestChunkLen + (largestChunkLen + 31 >> 5) + 7;
      this._encodeBuffer = bufferRecycler.allocEncodingBuffer(bufferLen);
   }

   protected ChunkEncoder(int totalLength, boolean bogus) {
      this(totalLength, BufferRecycler.instance(), bogus);
   }

   protected ChunkEncoder(int totalLength, BufferRecycler bufferRecycler, boolean bogus) {
      int largestChunkLen = Math.max(totalLength, 65535);
      int suggestedHashLen = calcHashLen(largestChunkLen);
      this._recycler = bufferRecycler;
      this._hashTable = bufferRecycler.allocEncodingHash(suggestedHashLen);
      this._hashModulo = this._hashTable.length - 1;
      this._encodeBuffer = null;
   }

   private static int calcHashLen(int chunkSize) {
      chunkSize += chunkSize;
      if (chunkSize >= 16384) {
         return 16384;
      } else {
         int hashLen;
         for(hashLen = 256; hashLen < chunkSize; hashLen += hashLen) {
         }

         return hashLen;
      }
   }

   public final void close() {
      byte[] buf = this._encodeBuffer;
      if (buf != null) {
         this._encodeBuffer = null;
         this._recycler.releaseEncodeBuffer(buf);
      }

      int[] ibuf = this._hashTable;
      if (ibuf != null) {
         this._hashTable = null;
         this._recycler.releaseEncodingHash(ibuf);
      }

   }

   public LZFChunk encodeChunk(byte[] data, int offset, int len) {
      if (len >= 16) {
         int compLen = this.tryCompress(data, offset, offset + len, this._encodeBuffer, 0);
         if (compLen < len - 2) {
            return LZFChunk.createCompressed(len, this._encodeBuffer, 0, compLen);
         }
      }

      return LZFChunk.createNonCompressed(data, offset, len);
   }

   public LZFChunk encodeChunkIfCompresses(byte[] data, int offset, int inputLen, double maxResultRatio) {
      if (inputLen >= 16) {
         int maxSize = (int)(maxResultRatio * (double)inputLen + 7.0 + 0.5);
         int compLen = this.tryCompress(data, offset, offset + inputLen, this._encodeBuffer, 0);
         if (compLen <= maxSize) {
            return LZFChunk.createCompressed(inputLen, this._encodeBuffer, 0, compLen);
         }
      }

      return null;
   }

   public int appendEncodedChunk(byte[] input, int inputPtr, int inputLen, byte[] outputBuffer, int outputPos) {
      if (inputLen >= 16) {
         int compStart = outputPos + 7;
         int end = this.tryCompress(input, inputPtr, inputPtr + inputLen, outputBuffer, compStart);
         int uncompEnd = outputPos + 5 + inputLen;
         if (end < uncompEnd) {
            int compLen = end - compStart;
            LZFChunk.appendCompressedHeader(inputLen, compLen, outputBuffer, outputPos);
            return end;
         }
      }

      return LZFChunk.appendNonCompressed(input, inputPtr, inputLen, outputBuffer, outputPos);
   }

   public int appendEncodedIfCompresses(byte[] input, double maxResultRatio, int inputPtr, int inputLen, byte[] outputBuffer, int outputPos) {
      if (inputLen >= 16) {
         int compStart = outputPos + 7;
         int end = this.tryCompress(input, inputPtr, inputPtr + inputLen, outputBuffer, compStart);
         int maxSize = (int)(maxResultRatio * (double)inputLen + 7.0 + 0.5);
         if (end <= outputPos + maxSize) {
            int compLen = end - compStart;
            LZFChunk.appendCompressedHeader(inputLen, compLen, outputBuffer, outputPos);
            return end;
         }
      }

      return -1;
   }

   public void encodeAndWriteChunk(byte[] data, int offset, int len, OutputStream out) throws IOException {
      if (len >= 16) {
         int compEnd = this.tryCompress(data, offset, offset + len, this._encodeBuffer, 7);
         int compLen = compEnd - 7;
         if (compLen < len - 2) {
            LZFChunk.appendCompressedHeader(len, compLen, this._encodeBuffer, 0);
            out.write(this._encodeBuffer, 0, compEnd);
            return;
         }
      }

      byte[] headerBuf = this._headerBuffer;
      if (headerBuf == null) {
         this._headerBuffer = headerBuf = new byte[7];
      }

      LZFChunk.writeNonCompressedHeader(len, out, headerBuf);
      out.write(data, offset, len);
   }

   public boolean encodeAndWriteChunkIfCompresses(byte[] data, int offset, int inputLen, OutputStream out, double resultRatio) throws IOException {
      if (inputLen >= 16) {
         int compEnd = this.tryCompress(data, offset, offset + inputLen, this._encodeBuffer, 7);
         int maxSize = (int)(resultRatio * (double)inputLen + 7.0 + 0.5);
         if (compEnd <= maxSize) {
            LZFChunk.appendCompressedHeader(inputLen, compEnd - 7, this._encodeBuffer, 0);
            out.write(this._encodeBuffer, 0, compEnd);
            return true;
         }
      }

      return false;
   }

   public BufferRecycler getBufferRecycler() {
      return this._recycler;
   }

   protected abstract int tryCompress(byte[] var1, int var2, int var3, byte[] var4, int var5);

   protected final int hash(int h) {
      return h * '\udfe9' >> 9 & this._hashModulo;
   }
}
