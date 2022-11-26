package org.python.netty.handler.codec.compression;

import org.python.netty.buffer.ByteBuf;

class Bzip2BitReader {
   private static final int MAX_COUNT_OF_READABLE_BYTES = 268435455;
   private ByteBuf in;
   private long bitBuffer;
   private int bitCount;

   void setByteBuf(ByteBuf in) {
      this.in = in;
   }

   int readBits(int count) {
      if (count >= 0 && count <= 32) {
         int bitCount = this.bitCount;
         long bitBuffer = this.bitBuffer;
         if (bitCount < count) {
            long readData;
            byte offset;
            switch (this.in.readableBytes()) {
               case 1:
                  readData = (long)this.in.readUnsignedByte();
                  offset = 8;
                  break;
               case 2:
                  readData = (long)this.in.readUnsignedShort();
                  offset = 16;
                  break;
               case 3:
                  readData = (long)this.in.readUnsignedMedium();
                  offset = 24;
                  break;
               default:
                  readData = this.in.readUnsignedInt();
                  offset = 32;
            }

            bitBuffer = bitBuffer << offset | readData;
            bitCount += offset;
            this.bitBuffer = bitBuffer;
         }

         this.bitCount = bitCount -= count;
         return (int)(bitBuffer >>> bitCount & (count != 32 ? (long)((1 << count) - 1) : 4294967295L));
      } else {
         throw new IllegalArgumentException("count: " + count + " (expected: 0-32 )");
      }
   }

   boolean readBoolean() {
      return this.readBits(1) != 0;
   }

   int readInt() {
      return this.readBits(32);
   }

   void refill() {
      int readData = this.in.readUnsignedByte();
      this.bitBuffer = this.bitBuffer << 8 | (long)readData;
      this.bitCount += 8;
   }

   boolean isReadable() {
      return this.bitCount > 0 || this.in.isReadable();
   }

   boolean hasReadableBits(int count) {
      if (count < 0) {
         throw new IllegalArgumentException("count: " + count + " (expected value greater than 0)");
      } else {
         return this.bitCount >= count || (this.in.readableBytes() << 3 & Integer.MAX_VALUE) >= count - this.bitCount;
      }
   }

   boolean hasReadableBytes(int count) {
      if (count >= 0 && count <= 268435455) {
         return this.hasReadableBits(count << 3);
      } else {
         throw new IllegalArgumentException("count: " + count + " (expected: 0-" + 268435455 + ')');
      }
   }
}
