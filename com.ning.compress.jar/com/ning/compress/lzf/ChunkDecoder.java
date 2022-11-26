package com.ning.compress.lzf;

import java.io.IOException;
import java.io.InputStream;

public abstract class ChunkDecoder {
   protected static final byte BYTE_NULL = 0;
   protected static final int HEADER_BYTES = 5;

   public final byte[] decode(byte[] inputBuffer) throws LZFException {
      byte[] result = new byte[calculateUncompressedSize(inputBuffer, 0, inputBuffer.length)];
      this.decode(inputBuffer, 0, inputBuffer.length, result);
      return result;
   }

   public final byte[] decode(byte[] inputBuffer, int inputPtr, int inputLen) throws LZFException {
      byte[] result = new byte[calculateUncompressedSize(inputBuffer, inputPtr, inputLen)];
      this.decode(inputBuffer, inputPtr, inputLen, result);
      return result;
   }

   public final int decode(byte[] inputBuffer, byte[] targetBuffer) throws LZFException {
      return this.decode(inputBuffer, 0, inputBuffer.length, targetBuffer);
   }

   public int decode(byte[] sourceBuffer, int inPtr, int inLength, byte[] targetBuffer) throws LZFException {
      int outPtr = 0;
      int blockNr = 0;

      for(int end = inPtr + inLength - 1; inPtr < end; ++blockNr) {
         if (sourceBuffer[inPtr] != 90 || sourceBuffer[inPtr + 1] != 86) {
            throw new LZFException("Corrupt input data, block #" + blockNr + " (at offset " + inPtr + "): did not start with 'ZV' signature bytes");
         }

         inPtr += 2;
         int type = sourceBuffer[inPtr++];
         int len = uint16(sourceBuffer, inPtr);
         inPtr += 2;
         if (type == 0) {
            if (outPtr + len > targetBuffer.length) {
               this._reportArrayOverflow(targetBuffer, outPtr, len);
            }

            System.arraycopy(sourceBuffer, inPtr, targetBuffer, outPtr, len);
            outPtr += len;
         } else {
            int uncompLen = uint16(sourceBuffer, inPtr);
            if (outPtr + uncompLen > targetBuffer.length) {
               this._reportArrayOverflow(targetBuffer, outPtr, uncompLen);
            }

            inPtr += 2;
            this.decodeChunk(sourceBuffer, inPtr, targetBuffer, outPtr, outPtr + uncompLen);
            outPtr += uncompLen;
         }

         inPtr += len;
      }

      return outPtr;
   }

   public abstract int decodeChunk(InputStream var1, byte[] var2, byte[] var3) throws IOException;

   public abstract void decodeChunk(byte[] var1, int var2, byte[] var3, int var4, int var5) throws LZFException;

   public abstract int skipOrDecodeChunk(InputStream var1, byte[] var2, byte[] var3, long var4) throws IOException;

   public static int calculateUncompressedSize(byte[] data, int ptr, int length) throws LZFException {
      int uncompressedSize = 0;
      int blockNr = 0;
      int end = ptr + length;

      while(true) {
         if (ptr < end) {
            if (ptr != data.length + 1 || data[ptr] != 0) {
               try {
                  if (data[ptr] != 90 || data[ptr + 1] != 86) {
                     throw new LZFException("Corrupt input data, block #" + blockNr + " (at offset " + ptr + "): did not start with 'ZV' signature bytes");
                  }

                  int type = data[ptr + 2];
                  int blockLen = uint16(data, ptr + 3);
                  if (type == 0) {
                     ptr += 5;
                     uncompressedSize += blockLen;
                  } else {
                     if (type != 1) {
                        throw new LZFException("Corrupt input data, block #" + blockNr + " (at offset " + ptr + "): unrecognized block type " + (type & 255));
                     }

                     uncompressedSize += uint16(data, ptr + 5);
                     ptr += 7;
                  }

                  ptr += blockLen;
               } catch (ArrayIndexOutOfBoundsException var8) {
                  throw new LZFException("Corrupt input data, block #" + blockNr + " (at offset " + ptr + "): truncated block header");
               }

               ++blockNr;
               continue;
            }

            ++ptr;
         }

         if (ptr != end) {
            throw new LZFException("Corrupt input data: block #" + blockNr + " extends " + (data.length - ptr) + " beyond end of input");
         }

         return uncompressedSize;
      }
   }

   protected static final int uint16(byte[] data, int ptr) {
      return ((data[ptr] & 255) << 8) + (data[ptr + 1] & 255);
   }

   protected static final int readHeader(InputStream is, byte[] inputBuffer) throws IOException {
      int needed = 5;
      int count = is.read(inputBuffer, 0, needed);
      if (count == needed) {
         return count;
      } else if (count <= 0) {
         return 0;
      } else {
         int offset = count;
         needed -= count;

         do {
            count = is.read(inputBuffer, offset, needed);
            if (count <= 0) {
               break;
            }

            offset += count;
            needed -= count;
         } while(needed > 0);

         return offset;
      }
   }

   protected static final void readFully(InputStream is, boolean compressed, byte[] outputBuffer, int offset, int len) throws IOException {
      int count;
      for(int left = len; left > 0; left -= count) {
         count = is.read(outputBuffer, offset, left);
         if (count < 0) {
            throw new LZFException("EOF in " + len + " byte (" + (compressed ? "" : "un") + "compressed) block: could only read " + (len - left) + " bytes");
         }

         offset += count;
      }

   }

   protected static final void skipFully(InputStream is, int amount) throws IOException {
      long skipped;
      for(int orig = amount; amount > 0; amount -= (int)skipped) {
         skipped = is.skip((long)amount);
         if (skipped <= 0L) {
            throw new LZFException("Input problem: failed to skip " + orig + " bytes in input stream, only skipped " + (orig - amount));
         }
      }

   }

   protected void _reportCorruptHeader() throws LZFException {
      throw new LZFException("Corrupt input data, block did not start with 2 byte signature ('ZV') followed by type byte, 2-byte length)");
   }

   protected void _reportArrayOverflow(byte[] targetBuffer, int outPtr, int dataLen) throws LZFException {
      throw new LZFException("Target buffer too small (" + targetBuffer.length + "): can not copy/uncompress " + dataLen + " bytes to offset " + outPtr);
   }
}
