package org.python.netty.buffer;

import org.python.netty.util.internal.PlatformDependent;

class UnpooledUnsafeHeapByteBuf extends UnpooledHeapByteBuf {
   UnpooledUnsafeHeapByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
      super(alloc, initialCapacity, maxCapacity);
   }

   byte[] allocateArray(int initialCapacity) {
      return PlatformDependent.allocateUninitializedArray(initialCapacity);
   }

   public byte getByte(int index) {
      this.checkIndex(index);
      return this._getByte(index);
   }

   protected byte _getByte(int index) {
      return UnsafeByteBufUtil.getByte(this.array, index);
   }

   public short getShort(int index) {
      this.checkIndex(index, 2);
      return this._getShort(index);
   }

   protected short _getShort(int index) {
      return UnsafeByteBufUtil.getShort(this.array, index);
   }

   public short getShortLE(int index) {
      this.checkIndex(index, 2);
      return this._getShortLE(index);
   }

   protected short _getShortLE(int index) {
      return UnsafeByteBufUtil.getShortLE(this.array, index);
   }

   public int getUnsignedMedium(int index) {
      this.checkIndex(index, 3);
      return this._getUnsignedMedium(index);
   }

   protected int _getUnsignedMedium(int index) {
      return UnsafeByteBufUtil.getUnsignedMedium(this.array, index);
   }

   public int getUnsignedMediumLE(int index) {
      this.checkIndex(index, 3);
      return this._getUnsignedMediumLE(index);
   }

   protected int _getUnsignedMediumLE(int index) {
      return UnsafeByteBufUtil.getUnsignedMediumLE(this.array, index);
   }

   public int getInt(int index) {
      this.checkIndex(index, 4);
      return this._getInt(index);
   }

   protected int _getInt(int index) {
      return UnsafeByteBufUtil.getInt(this.array, index);
   }

   public int getIntLE(int index) {
      this.checkIndex(index, 4);
      return this._getIntLE(index);
   }

   protected int _getIntLE(int index) {
      return UnsafeByteBufUtil.getIntLE(this.array, index);
   }

   public long getLong(int index) {
      this.checkIndex(index, 8);
      return this._getLong(index);
   }

   protected long _getLong(int index) {
      return UnsafeByteBufUtil.getLong(this.array, index);
   }

   public long getLongLE(int index) {
      this.checkIndex(index, 8);
      return this._getLongLE(index);
   }

   protected long _getLongLE(int index) {
      return UnsafeByteBufUtil.getLongLE(this.array, index);
   }

   public ByteBuf setByte(int index, int value) {
      this.checkIndex(index);
      this._setByte(index, value);
      return this;
   }

   protected void _setByte(int index, int value) {
      UnsafeByteBufUtil.setByte(this.array, index, value);
   }

   public ByteBuf setShort(int index, int value) {
      this.checkIndex(index, 2);
      this._setShort(index, value);
      return this;
   }

   protected void _setShort(int index, int value) {
      UnsafeByteBufUtil.setShort(this.array, index, value);
   }

   public ByteBuf setShortLE(int index, int value) {
      this.checkIndex(index, 2);
      this._setShortLE(index, value);
      return this;
   }

   protected void _setShortLE(int index, int value) {
      UnsafeByteBufUtil.setShortLE(this.array, index, value);
   }

   public ByteBuf setMedium(int index, int value) {
      this.checkIndex(index, 3);
      this._setMedium(index, value);
      return this;
   }

   protected void _setMedium(int index, int value) {
      UnsafeByteBufUtil.setMedium(this.array, index, value);
   }

   public ByteBuf setMediumLE(int index, int value) {
      this.checkIndex(index, 3);
      this._setMediumLE(index, value);
      return this;
   }

   protected void _setMediumLE(int index, int value) {
      UnsafeByteBufUtil.setMediumLE(this.array, index, value);
   }

   public ByteBuf setInt(int index, int value) {
      this.checkIndex(index, 4);
      this._setInt(index, value);
      return this;
   }

   protected void _setInt(int index, int value) {
      UnsafeByteBufUtil.setInt(this.array, index, value);
   }

   public ByteBuf setIntLE(int index, int value) {
      this.checkIndex(index, 4);
      this._setIntLE(index, value);
      return this;
   }

   protected void _setIntLE(int index, int value) {
      UnsafeByteBufUtil.setIntLE(this.array, index, value);
   }

   public ByteBuf setLong(int index, long value) {
      this.checkIndex(index, 8);
      this._setLong(index, value);
      return this;
   }

   protected void _setLong(int index, long value) {
      UnsafeByteBufUtil.setLong(this.array, index, value);
   }

   public ByteBuf setLongLE(int index, long value) {
      this.checkIndex(index, 8);
      this._setLongLE(index, value);
      return this;
   }

   protected void _setLongLE(int index, long value) {
      UnsafeByteBufUtil.setLongLE(this.array, index, value);
   }

   public ByteBuf setZero(int index, int length) {
      if (PlatformDependent.javaVersion() >= 7) {
         this._setZero(index, length);
         return this;
      } else {
         return super.setZero(index, length);
      }
   }

   public ByteBuf writeZero(int length) {
      if (PlatformDependent.javaVersion() >= 7) {
         this.ensureWritable(length);
         int wIndex = this.writerIndex;
         this._setZero(wIndex, length);
         this.writerIndex = wIndex + length;
         return this;
      } else {
         return super.writeZero(length);
      }
   }

   private void _setZero(int index, int length) {
      this.checkIndex(index, length);
      UnsafeByteBufUtil.setZero(this.array, index, length);
   }

   /** @deprecated */
   @Deprecated
   protected SwappedByteBuf newSwappedByteBuf() {
      return (SwappedByteBuf)(PlatformDependent.isUnaligned() ? new UnsafeHeapSwappedByteBuf(this) : super.newSwappedByteBuf());
   }
}
