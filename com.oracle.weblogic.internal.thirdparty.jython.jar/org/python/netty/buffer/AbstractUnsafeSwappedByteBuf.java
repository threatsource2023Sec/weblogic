package org.python.netty.buffer;

import java.nio.ByteOrder;
import org.python.netty.util.internal.PlatformDependent;

abstract class AbstractUnsafeSwappedByteBuf extends SwappedByteBuf {
   private final boolean nativeByteOrder;
   private final AbstractByteBuf wrapped;

   AbstractUnsafeSwappedByteBuf(AbstractByteBuf buf) {
      super(buf);

      assert PlatformDependent.isUnaligned();

      this.wrapped = buf;
      this.nativeByteOrder = PlatformDependent.BIG_ENDIAN_NATIVE_ORDER == (this.order() == ByteOrder.BIG_ENDIAN);
   }

   public final long getLong(int index) {
      this.wrapped.checkIndex(index, 8);
      long v = this._getLong(this.wrapped, index);
      return this.nativeByteOrder ? v : Long.reverseBytes(v);
   }

   public final float getFloat(int index) {
      return Float.intBitsToFloat(this.getInt(index));
   }

   public final double getDouble(int index) {
      return Double.longBitsToDouble(this.getLong(index));
   }

   public final char getChar(int index) {
      return (char)this.getShort(index);
   }

   public final long getUnsignedInt(int index) {
      return (long)this.getInt(index) & 4294967295L;
   }

   public final int getInt(int index) {
      this.wrapped.checkIndex0(index, 4);
      int v = this._getInt(this.wrapped, index);
      return this.nativeByteOrder ? v : Integer.reverseBytes(v);
   }

   public final int getUnsignedShort(int index) {
      return this.getShort(index) & '\uffff';
   }

   public final short getShort(int index) {
      this.wrapped.checkIndex0(index, 2);
      short v = this._getShort(this.wrapped, index);
      return this.nativeByteOrder ? v : Short.reverseBytes(v);
   }

   public final ByteBuf setShort(int index, int value) {
      this.wrapped.checkIndex0(index, 2);
      this._setShort(this.wrapped, index, this.nativeByteOrder ? (short)value : Short.reverseBytes((short)value));
      return this;
   }

   public final ByteBuf setInt(int index, int value) {
      this.wrapped.checkIndex0(index, 4);
      this._setInt(this.wrapped, index, this.nativeByteOrder ? value : Integer.reverseBytes(value));
      return this;
   }

   public final ByteBuf setLong(int index, long value) {
      this.wrapped.checkIndex(index, 8);
      this._setLong(this.wrapped, index, this.nativeByteOrder ? value : Long.reverseBytes(value));
      return this;
   }

   public final ByteBuf setChar(int index, int value) {
      this.setShort(index, value);
      return this;
   }

   public final ByteBuf setFloat(int index, float value) {
      this.setInt(index, Float.floatToRawIntBits(value));
      return this;
   }

   public final ByteBuf setDouble(int index, double value) {
      this.setLong(index, Double.doubleToRawLongBits(value));
      return this;
   }

   public final ByteBuf writeShort(int value) {
      this.wrapped.ensureWritable(2);
      this._setShort(this.wrapped, this.wrapped.writerIndex, this.nativeByteOrder ? (short)value : Short.reverseBytes((short)value));
      AbstractByteBuf var10000 = this.wrapped;
      var10000.writerIndex += 2;
      return this;
   }

   public final ByteBuf writeInt(int value) {
      this.wrapped.ensureWritable(4);
      this._setInt(this.wrapped, this.wrapped.writerIndex, this.nativeByteOrder ? value : Integer.reverseBytes(value));
      AbstractByteBuf var10000 = this.wrapped;
      var10000.writerIndex += 4;
      return this;
   }

   public final ByteBuf writeLong(long value) {
      this.wrapped.ensureWritable(8);
      this._setLong(this.wrapped, this.wrapped.writerIndex, this.nativeByteOrder ? value : Long.reverseBytes(value));
      AbstractByteBuf var10000 = this.wrapped;
      var10000.writerIndex += 8;
      return this;
   }

   public final ByteBuf writeChar(int value) {
      this.writeShort(value);
      return this;
   }

   public final ByteBuf writeFloat(float value) {
      this.writeInt(Float.floatToRawIntBits(value));
      return this;
   }

   public final ByteBuf writeDouble(double value) {
      this.writeLong(Double.doubleToRawLongBits(value));
      return this;
   }

   protected abstract short _getShort(AbstractByteBuf var1, int var2);

   protected abstract int _getInt(AbstractByteBuf var1, int var2);

   protected abstract long _getLong(AbstractByteBuf var1, int var2);

   protected abstract void _setShort(AbstractByteBuf var1, int var2, short var3);

   protected abstract void _setInt(AbstractByteBuf var1, int var2, int var3);

   protected abstract void _setLong(AbstractByteBuf var1, int var2, long var3);
}
