package org.python.apache.commons.compress.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

public class BitInputStream implements Closeable {
   private static final int MAXIMUM_CACHE_SIZE = 63;
   private static final long[] MASKS = new long[64];
   private final InputStream in;
   private final ByteOrder byteOrder;
   private long bitsCached = 0L;
   private int bitsCachedSize = 0;

   public BitInputStream(InputStream in, ByteOrder byteOrder) {
      this.in = in;
      this.byteOrder = byteOrder;
   }

   public void close() throws IOException {
      this.in.close();
   }

   public void clearBitCache() {
      this.bitsCached = 0L;
      this.bitsCachedSize = 0;
   }

   public long readBits(int count) throws IOException {
      if (count >= 0 && count <= 63) {
         if (this.ensureCache(count)) {
            return -1L;
         } else if (this.bitsCachedSize < count) {
            return this.processBitsGreater57(count);
         } else {
            long bitsOut;
            if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
               bitsOut = this.bitsCached & MASKS[count];
               this.bitsCached >>>= count;
            } else {
               bitsOut = this.bitsCached >> this.bitsCachedSize - count & MASKS[count];
            }

            this.bitsCachedSize -= count;
            return bitsOut;
         }
      } else {
         throw new IllegalArgumentException("count must not be negative or greater than 63");
      }
   }

   private long processBitsGreater57(int count) throws IOException {
      int overflowBits = false;
      long overflow = 0L;
      int bitsToAddCount = count - this.bitsCachedSize;
      int overflowBits = 8 - bitsToAddCount;
      long nextByte = (long)this.in.read();
      if (nextByte < 0L) {
         return nextByte;
      } else {
         long bitsToAdd;
         if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            bitsToAdd = nextByte & MASKS[bitsToAddCount];
            this.bitsCached |= bitsToAdd << this.bitsCachedSize;
            overflow = nextByte >>> bitsToAddCount & MASKS[overflowBits];
         } else {
            this.bitsCached <<= bitsToAddCount;
            bitsToAdd = nextByte >>> overflowBits & MASKS[bitsToAddCount];
            this.bitsCached |= bitsToAdd;
            overflow = nextByte & MASKS[overflowBits];
         }

         long bitsOut = this.bitsCached & MASKS[count];
         this.bitsCached = overflow;
         this.bitsCachedSize = overflowBits;
         return bitsOut;
      }
   }

   private boolean ensureCache(int count) throws IOException {
      for(; this.bitsCachedSize < count && this.bitsCachedSize < 57; this.bitsCachedSize += 8) {
         long nextByte = (long)this.in.read();
         if (nextByte < 0L) {
            return true;
         }

         if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            this.bitsCached |= nextByte << this.bitsCachedSize;
         } else {
            this.bitsCached <<= 8;
            this.bitsCached |= nextByte;
         }
      }

      return false;
   }

   static {
      for(int i = 1; i <= 63; ++i) {
         MASKS[i] = (MASKS[i - 1] << 1) + 1L;
      }

   }
}
