package org.glassfish.grizzly.compression.lzma.impl.rangecoder;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.MemoryManager;

public class RangeEncoder {
   static final int kTopMask = -16777216;
   static final int kNumBitModelTotalBits = 11;
   static final int kBitModelTotal = 2048;
   static final int kNumMoveBits = 5;
   Buffer dst;
   MemoryManager mm;
   long Low;
   int Range;
   int _cacheSize;
   int _cache;
   long _position;
   static final int kNumMoveReducingBits = 2;
   public static final int kNumBitPriceShiftBits = 6;
   private static final int[] ProbPrices = new int[512];

   public void setBuffer(Buffer dst, MemoryManager mm) {
      this.dst = dst;
      this.mm = mm;
   }

   public Buffer releaseBuffer() {
      this.mm = null;

      Buffer var1;
      try {
         var1 = this.dst;
      } finally {
         this.dst = null;
      }

      return var1;
   }

   public void init() {
      this._position = 0L;
      this.Low = 0L;
      this.Range = -1;
      this._cacheSize = 1;
      this._cache = 0;
   }

   public void flushData() throws IOException {
      for(int i = 0; i < 5; ++i) {
         this.shiftLow();
      }

   }

   public void shiftLow() throws IOException {
      int LowHi = (int)(this.Low >>> 32);
      if (LowHi != 0 || this.Low < 4278190080L) {
         this._position += (long)this._cacheSize;
         int temp = this._cache;

         do {
            if (!this.dst.hasRemaining()) {
               this.dst = resizeBuffer(this.mm, this.dst, 1);
            }

            this.dst.put((byte)(temp + LowHi));
            temp = 255;
         } while(--this._cacheSize != 0);

         this._cache = (int)this.Low >>> 24;
      }

      ++this._cacheSize;
      this.Low = (this.Low & 16777215L) << 8;
   }

   public void encodeDirectBits(int v, int numTotalBits) throws IOException {
      for(int i = numTotalBits - 1; i >= 0; --i) {
         this.Range >>>= 1;
         if ((v >>> i & 1) == 1) {
            this.Low += (long)this.Range;
         }

         if ((this.Range & -16777216) == 0) {
            this.Range <<= 8;
            this.shiftLow();
         }
      }

   }

   public long getProcessedSizeAdd() {
      return (long)this._cacheSize + this._position + 4L;
   }

   public static void initBitModels(short[] probs) {
      for(int i = 0; i < probs.length; ++i) {
         probs[i] = 1024;
      }

   }

   public void encode(short[] probs, int index, int symbol) throws IOException {
      int prob = probs[index];
      int newBound = (this.Range >>> 11) * prob;
      if (symbol == 0) {
         this.Range = newBound;
         probs[index] = (short)(prob + (2048 - prob >>> 5));
      } else {
         this.Low += (long)newBound & 4294967295L;
         this.Range -= newBound;
         probs[index] = (short)(prob - (prob >>> 5));
      }

      if ((this.Range & -16777216) == 0) {
         this.Range <<= 8;
         this.shiftLow();
      }

   }

   public static int getPrice(int Prob, int symbol) {
      return ProbPrices[((Prob - symbol ^ -symbol) & 2047) >>> 2];
   }

   public static int getPrice0(int Prob) {
      return ProbPrices[Prob >>> 2];
   }

   public static int getPrice1(int Prob) {
      return ProbPrices[2048 - Prob >>> 2];
   }

   private static Buffer resizeBuffer(MemoryManager memoryManager, Buffer headerBuffer, int grow) {
      return memoryManager.reallocate(headerBuffer, Math.max(headerBuffer.capacity() + grow, headerBuffer.capacity() * 3 / 2 + 1));
   }

   static {
      int kNumBits = 9;

      for(int i = kNumBits - 1; i >= 0; --i) {
         int start = 1 << kNumBits - i - 1;
         int end = 1 << kNumBits - i;

         for(int j = start; j < end; ++j) {
            ProbPrices[j] = (i << 6) + (end - j << 6 >>> kNumBits - i - 1);
         }
      }

   }
}
