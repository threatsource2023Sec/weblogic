package org.python.netty.buffer;

class UnpooledSlicedByteBuf extends AbstractUnpooledSlicedByteBuf {
   UnpooledSlicedByteBuf(AbstractByteBuf buffer, int index, int length) {
      super(buffer, index, length);
   }

   public int capacity() {
      return this.maxCapacity();
   }

   public AbstractByteBuf unwrap() {
      return (AbstractByteBuf)super.unwrap();
   }

   protected byte _getByte(int index) {
      return this.unwrap()._getByte(this.idx(index));
   }

   protected short _getShort(int index) {
      return this.unwrap()._getShort(this.idx(index));
   }

   protected short _getShortLE(int index) {
      return this.unwrap()._getShortLE(this.idx(index));
   }

   protected int _getUnsignedMedium(int index) {
      return this.unwrap()._getUnsignedMedium(this.idx(index));
   }

   protected int _getUnsignedMediumLE(int index) {
      return this.unwrap()._getUnsignedMediumLE(this.idx(index));
   }

   protected int _getInt(int index) {
      return this.unwrap()._getInt(this.idx(index));
   }

   protected int _getIntLE(int index) {
      return this.unwrap()._getIntLE(this.idx(index));
   }

   protected long _getLong(int index) {
      return this.unwrap()._getLong(this.idx(index));
   }

   protected long _getLongLE(int index) {
      return this.unwrap()._getLongLE(this.idx(index));
   }

   protected void _setByte(int index, int value) {
      this.unwrap()._setByte(this.idx(index), value);
   }

   protected void _setShort(int index, int value) {
      this.unwrap()._setShort(this.idx(index), value);
   }

   protected void _setShortLE(int index, int value) {
      this.unwrap()._setShortLE(this.idx(index), value);
   }

   protected void _setMedium(int index, int value) {
      this.unwrap()._setMedium(this.idx(index), value);
   }

   protected void _setMediumLE(int index, int value) {
      this.unwrap()._setMediumLE(this.idx(index), value);
   }

   protected void _setInt(int index, int value) {
      this.unwrap()._setInt(this.idx(index), value);
   }

   protected void _setIntLE(int index, int value) {
      this.unwrap()._setIntLE(this.idx(index), value);
   }

   protected void _setLong(int index, long value) {
      this.unwrap()._setLong(this.idx(index), value);
   }

   protected void _setLongLE(int index, long value) {
      this.unwrap()._setLongLE(this.idx(index), value);
   }
}
