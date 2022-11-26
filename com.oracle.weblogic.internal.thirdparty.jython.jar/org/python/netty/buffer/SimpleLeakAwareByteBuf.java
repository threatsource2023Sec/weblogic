package org.python.netty.buffer;

import java.nio.ByteOrder;
import org.python.netty.util.ResourceLeakTracker;
import org.python.netty.util.internal.ObjectUtil;

class SimpleLeakAwareByteBuf extends WrappedByteBuf {
   private final ByteBuf trackedByteBuf;
   final ResourceLeakTracker leak;

   SimpleLeakAwareByteBuf(ByteBuf wrapped, ByteBuf trackedByteBuf, ResourceLeakTracker leak) {
      super(wrapped);
      this.trackedByteBuf = (ByteBuf)ObjectUtil.checkNotNull(trackedByteBuf, "trackedByteBuf");
      this.leak = (ResourceLeakTracker)ObjectUtil.checkNotNull(leak, "leak");
   }

   SimpleLeakAwareByteBuf(ByteBuf wrapped, ResourceLeakTracker leak) {
      this(wrapped, wrapped, leak);
   }

   public ByteBuf slice() {
      return this.newSharedLeakAwareByteBuf(super.slice());
   }

   public ByteBuf retainedSlice() {
      return this.unwrappedDerived(super.retainedSlice());
   }

   public ByteBuf retainedSlice(int index, int length) {
      return this.unwrappedDerived(super.retainedSlice(index, length));
   }

   public ByteBuf retainedDuplicate() {
      return this.unwrappedDerived(super.retainedDuplicate());
   }

   public ByteBuf readRetainedSlice(int length) {
      return this.unwrappedDerived(super.readRetainedSlice(length));
   }

   public ByteBuf slice(int index, int length) {
      return this.newSharedLeakAwareByteBuf(super.slice(index, length));
   }

   public ByteBuf duplicate() {
      return this.newSharedLeakAwareByteBuf(super.duplicate());
   }

   public ByteBuf readSlice(int length) {
      return this.newSharedLeakAwareByteBuf(super.readSlice(length));
   }

   public ByteBuf asReadOnly() {
      return this.newSharedLeakAwareByteBuf(super.asReadOnly());
   }

   public ByteBuf touch() {
      return this;
   }

   public ByteBuf touch(Object hint) {
      return this;
   }

   public final boolean release() {
      if (super.release()) {
         this.closeLeak();
         return true;
      } else {
         return false;
      }
   }

   public final boolean release(int decrement) {
      if (super.release(decrement)) {
         this.closeLeak();
         return true;
      } else {
         return false;
      }
   }

   private void closeLeak() {
      boolean closed = this.leak.close(this.trackedByteBuf);

      assert closed;

   }

   public ByteBuf order(ByteOrder endianness) {
      return this.order() == endianness ? this : this.newSharedLeakAwareByteBuf(super.order(endianness));
   }

   private ByteBuf unwrappedDerived(ByteBuf derived) {
      ByteBuf unwrappedDerived = unwrapSwapped(derived);
      if (unwrappedDerived instanceof AbstractPooledDerivedByteBuf) {
         ((AbstractPooledDerivedByteBuf)unwrappedDerived).parent(this);
         ResourceLeakTracker newLeak = AbstractByteBuf.leakDetector.track(derived);
         return (ByteBuf)(newLeak == null ? derived : this.newLeakAwareByteBuf(derived, newLeak));
      } else {
         return this.newSharedLeakAwareByteBuf(derived);
      }
   }

   private static ByteBuf unwrapSwapped(ByteBuf buf) {
      if (!(buf instanceof SwappedByteBuf)) {
         return buf;
      } else {
         do {
            buf = buf.unwrap();
         } while(buf instanceof SwappedByteBuf);

         return buf;
      }
   }

   private SimpleLeakAwareByteBuf newSharedLeakAwareByteBuf(ByteBuf wrapped) {
      return this.newLeakAwareByteBuf(wrapped, this.trackedByteBuf, this.leak);
   }

   private SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf wrapped, ResourceLeakTracker leakTracker) {
      return this.newLeakAwareByteBuf(wrapped, wrapped, leakTracker);
   }

   protected SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf buf, ByteBuf trackedByteBuf, ResourceLeakTracker leakTracker) {
      return new SimpleLeakAwareByteBuf(buf, trackedByteBuf, leakTracker);
   }
}
