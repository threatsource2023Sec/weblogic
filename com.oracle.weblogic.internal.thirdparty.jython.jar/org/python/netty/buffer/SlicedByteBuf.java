package org.python.netty.buffer;

/** @deprecated */
@Deprecated
public class SlicedByteBuf extends AbstractUnpooledSlicedByteBuf {
   private int length;

   public SlicedByteBuf(ByteBuf buffer, int index, int length) {
      super(buffer, index, length);
   }

   final void initLength(int length) {
      this.length = length;
   }

   final int length() {
      return this.length;
   }

   public int capacity() {
      return this.length;
   }
}
