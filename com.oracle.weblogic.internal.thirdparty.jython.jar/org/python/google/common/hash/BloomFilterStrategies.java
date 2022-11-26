package org.python.google.common.hash;

import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.math.LongMath;
import org.python.google.common.primitives.Ints;
import org.python.google.common.primitives.Longs;

enum BloomFilterStrategies implements BloomFilter.Strategy {
   MURMUR128_MITZ_32 {
      public boolean put(Object object, Funnel funnel, int numHashFunctions, BitArray bits) {
         long bitSize = bits.bitSize();
         long hash64 = Hashing.murmur3_128().hashObject(object, funnel).asLong();
         int hash1 = (int)hash64;
         int hash2 = (int)(hash64 >>> 32);
         boolean bitsChanged = false;

         for(int i = 1; i <= numHashFunctions; ++i) {
            int combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
               combinedHash = ~combinedHash;
            }

            bitsChanged |= bits.set((long)combinedHash % bitSize);
         }

         return bitsChanged;
      }

      public boolean mightContain(Object object, Funnel funnel, int numHashFunctions, BitArray bits) {
         long bitSize = bits.bitSize();
         long hash64 = Hashing.murmur3_128().hashObject(object, funnel).asLong();
         int hash1 = (int)hash64;
         int hash2 = (int)(hash64 >>> 32);

         for(int i = 1; i <= numHashFunctions; ++i) {
            int combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
               combinedHash = ~combinedHash;
            }

            if (!bits.get((long)combinedHash % bitSize)) {
               return false;
            }
         }

         return true;
      }
   },
   MURMUR128_MITZ_64 {
      public boolean put(Object object, Funnel funnel, int numHashFunctions, BitArray bits) {
         long bitSize = bits.bitSize();
         byte[] bytes = Hashing.murmur3_128().hashObject(object, funnel).getBytesInternal();
         long hash1 = this.lowerEight(bytes);
         long hash2 = this.upperEight(bytes);
         boolean bitsChanged = false;
         long combinedHash = hash1;

         for(int i = 0; i < numHashFunctions; ++i) {
            bitsChanged |= bits.set((combinedHash & Long.MAX_VALUE) % bitSize);
            combinedHash += hash2;
         }

         return bitsChanged;
      }

      public boolean mightContain(Object object, Funnel funnel, int numHashFunctions, BitArray bits) {
         long bitSize = bits.bitSize();
         byte[] bytes = Hashing.murmur3_128().hashObject(object, funnel).getBytesInternal();
         long hash1 = this.lowerEight(bytes);
         long hash2 = this.upperEight(bytes);
         long combinedHash = hash1;

         for(int i = 0; i < numHashFunctions; ++i) {
            if (!bits.get((combinedHash & Long.MAX_VALUE) % bitSize)) {
               return false;
            }

            combinedHash += hash2;
         }

         return true;
      }

      private long lowerEight(byte[] bytes) {
         return Longs.fromBytes(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);
      }

      private long upperEight(byte[] bytes) {
         return Longs.fromBytes(bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]);
      }
   };

   private BloomFilterStrategies() {
   }

   // $FF: synthetic method
   BloomFilterStrategies(Object x2) {
      this();
   }

   static final class BitArray {
      final long[] data;
      long bitCount;

      BitArray(long bits) {
         this(new long[Ints.checkedCast(LongMath.divide(bits, 64L, RoundingMode.CEILING))]);
      }

      BitArray(long[] data) {
         Preconditions.checkArgument(data.length > 0, "data length is zero!");
         this.data = data;
         long bitCount = 0L;
         long[] var4 = data;
         int var5 = data.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            long value = var4[var6];
            bitCount += (long)Long.bitCount(value);
         }

         this.bitCount = bitCount;
      }

      boolean set(long index) {
         if (!this.get(index)) {
            long[] var10000 = this.data;
            var10000[(int)(index >>> 6)] |= 1L << (int)index;
            ++this.bitCount;
            return true;
         } else {
            return false;
         }
      }

      boolean get(long index) {
         return (this.data[(int)(index >>> 6)] & 1L << (int)index) != 0L;
      }

      long bitSize() {
         return (long)this.data.length * 64L;
      }

      long bitCount() {
         return this.bitCount;
      }

      BitArray copy() {
         return new BitArray((long[])this.data.clone());
      }

      void putAll(BitArray array) {
         Preconditions.checkArgument(this.data.length == array.data.length, "BitArrays must be of equal length (%s != %s)", this.data.length, array.data.length);
         this.bitCount = 0L;

         for(int i = 0; i < this.data.length; ++i) {
            long[] var10000 = this.data;
            var10000[i] |= array.data[i];
            this.bitCount += (long)Long.bitCount(this.data[i]);
         }

      }

      public boolean equals(@Nullable Object o) {
         if (o instanceof BitArray) {
            BitArray bitArray = (BitArray)o;
            return Arrays.equals(this.data, bitArray.data);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Arrays.hashCode(this.data);
      }
   }
}
