package org.python.netty.buffer;

import org.python.netty.util.Recycler;
import org.python.netty.util.internal.PlatformDependent;

final class PooledUnsafeHeapByteBuf extends PooledHeapByteBuf {
   private static final Recycler RECYCLER = new Recycler() {
      protected PooledUnsafeHeapByteBuf newObject(Recycler.Handle handle) {
         return new PooledUnsafeHeapByteBuf(handle, 0);
      }
   };

   static PooledUnsafeHeapByteBuf newUnsafeInstance(int maxCapacity) {
      PooledUnsafeHeapByteBuf buf = (PooledUnsafeHeapByteBuf)RECYCLER.get();
      buf.reuse(maxCapacity);
      return buf;
   }

   private PooledUnsafeHeapByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
      super(recyclerHandle, maxCapacity);
   }

   protected byte _getByte(int index) {
      return UnsafeByteBufUtil.getByte((byte[])this.memory, this.idx(index));
   }

   protected short _getShort(int index) {
      return UnsafeByteBufUtil.getShort((byte[])this.memory, this.idx(index));
   }

   protected short _getShortLE(int index) {
      return UnsafeByteBufUtil.getShortLE((byte[])this.memory, this.idx(index));
   }

   protected int _getUnsignedMedium(int index) {
      return UnsafeByteBufUtil.getUnsignedMedium((byte[])this.memory, this.idx(index));
   }

   protected int _getUnsignedMediumLE(int index) {
      return UnsafeByteBufUtil.getUnsignedMediumLE((byte[])this.memory, this.idx(index));
   }

   protected int _getInt(int index) {
      return UnsafeByteBufUtil.getInt((byte[])this.memory, this.idx(index));
   }

   protected int _getIntLE(int index) {
      return UnsafeByteBufUtil.getIntLE((byte[])this.memory, this.idx(index));
   }

   protected long _getLong(int index) {
      return UnsafeByteBufUtil.getLong((byte[])this.memory, this.idx(index));
   }

   protected long _getLongLE(int index) {
      return UnsafeByteBufUtil.getLongLE((byte[])this.memory, this.idx(index));
   }

   protected void _setByte(int index, int value) {
      UnsafeByteBufUtil.setByte((byte[])this.memory, this.idx(index), value);
   }

   protected void _setShort(int index, int value) {
      UnsafeByteBufUtil.setShort((byte[])this.memory, this.idx(index), value);
   }

   protected void _setShortLE(int index, int value) {
      UnsafeByteBufUtil.setShortLE((byte[])this.memory, this.idx(index), value);
   }

   protected void _setMedium(int index, int value) {
      UnsafeByteBufUtil.setMedium((byte[])this.memory, this.idx(index), value);
   }

   protected void _setMediumLE(int index, int value) {
      UnsafeByteBufUtil.setMediumLE((byte[])this.memory, this.idx(index), value);
   }

   protected void _setInt(int index, int value) {
      UnsafeByteBufUtil.setInt((byte[])this.memory, this.idx(index), value);
   }

   protected void _setIntLE(int index, int value) {
      UnsafeByteBufUtil.setIntLE((byte[])this.memory, this.idx(index), value);
   }

   protected void _setLong(int index, long value) {
      UnsafeByteBufUtil.setLong((byte[])this.memory, this.idx(index), value);
   }

   protected void _setLongLE(int index, long value) {
      UnsafeByteBufUtil.setLongLE((byte[])this.memory, this.idx(index), value);
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
      UnsafeByteBufUtil.setZero((byte[])this.memory, this.idx(index), length);
   }

   /** @deprecated */
   @Deprecated
   protected SwappedByteBuf newSwappedByteBuf() {
      return (SwappedByteBuf)(PlatformDependent.isUnaligned() ? new UnsafeHeapSwappedByteBuf(this) : super.newSwappedByteBuf());
   }

   // $FF: synthetic method
   PooledUnsafeHeapByteBuf(Recycler.Handle x0, int x1, Object x2) {
      this(x0, x1);
   }
}
