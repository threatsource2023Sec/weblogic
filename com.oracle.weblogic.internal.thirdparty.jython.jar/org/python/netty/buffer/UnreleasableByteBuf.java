package org.python.netty.buffer;

import java.nio.ByteOrder;

final class UnreleasableByteBuf extends WrappedByteBuf {
   private SwappedByteBuf swappedBuf;

   UnreleasableByteBuf(ByteBuf buf) {
      super(buf instanceof UnreleasableByteBuf ? buf.unwrap() : buf);
   }

   public ByteBuf order(ByteOrder endianness) {
      if (endianness == null) {
         throw new NullPointerException("endianness");
      } else if (endianness == this.order()) {
         return this;
      } else {
         SwappedByteBuf swappedBuf = this.swappedBuf;
         if (swappedBuf == null) {
            this.swappedBuf = swappedBuf = new SwappedByteBuf(this);
         }

         return swappedBuf;
      }
   }

   public ByteBuf asReadOnly() {
      return this.buf.isReadOnly() ? this : new UnreleasableByteBuf(this.buf.asReadOnly());
   }

   public ByteBuf readSlice(int length) {
      return new UnreleasableByteBuf(this.buf.readSlice(length));
   }

   public ByteBuf readRetainedSlice(int length) {
      return this.readSlice(length);
   }

   public ByteBuf slice() {
      return new UnreleasableByteBuf(this.buf.slice());
   }

   public ByteBuf retainedSlice() {
      return this.slice();
   }

   public ByteBuf slice(int index, int length) {
      return new UnreleasableByteBuf(this.buf.slice(index, length));
   }

   public ByteBuf retainedSlice(int index, int length) {
      return this.slice(index, length);
   }

   public ByteBuf duplicate() {
      return new UnreleasableByteBuf(this.buf.duplicate());
   }

   public ByteBuf retainedDuplicate() {
      return this.duplicate();
   }

   public ByteBuf retain(int increment) {
      return this;
   }

   public ByteBuf retain() {
      return this;
   }

   public ByteBuf touch() {
      return this;
   }

   public ByteBuf touch(Object hint) {
      return this;
   }

   public boolean release() {
      return false;
   }

   public boolean release(int decrement) {
      return false;
   }
}
