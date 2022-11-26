package org.python.netty.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.python.netty.util.Recycler;
import org.python.netty.util.ReferenceCounted;

abstract class AbstractPooledDerivedByteBuf extends AbstractReferenceCountedByteBuf {
   private final Recycler.Handle recyclerHandle;
   private AbstractByteBuf rootParent;
   private ByteBuf parent;

   AbstractPooledDerivedByteBuf(Recycler.Handle recyclerHandle) {
      super(0);
      this.recyclerHandle = recyclerHandle;
   }

   final void parent(ByteBuf newParent) {
      assert newParent instanceof SimpleLeakAwareByteBuf;

      this.parent = newParent;
   }

   public final AbstractByteBuf unwrap() {
      return this.rootParent;
   }

   final AbstractPooledDerivedByteBuf init(AbstractByteBuf unwrapped, ByteBuf wrapped, int readerIndex, int writerIndex, int maxCapacity) {
      wrapped.retain();
      this.parent = wrapped;
      this.rootParent = unwrapped;

      AbstractPooledDerivedByteBuf var7;
      try {
         this.maxCapacity(maxCapacity);
         this.setIndex0(readerIndex, writerIndex);
         this.setRefCnt(1);
         wrapped = null;
         var7 = this;
      } finally {
         if (wrapped != null) {
            this.parent = this.rootParent = null;
            wrapped.release();
         }

      }

      return var7;
   }

   protected final void deallocate() {
      ByteBuf parent = this.parent;
      this.recyclerHandle.recycle(this);
      parent.release();
   }

   public final ByteBufAllocator alloc() {
      return this.unwrap().alloc();
   }

   /** @deprecated */
   @Deprecated
   public final ByteOrder order() {
      return this.unwrap().order();
   }

   public boolean isReadOnly() {
      return this.unwrap().isReadOnly();
   }

   public final boolean isDirect() {
      return this.unwrap().isDirect();
   }

   public boolean hasArray() {
      return this.unwrap().hasArray();
   }

   public byte[] array() {
      return this.unwrap().array();
   }

   public boolean hasMemoryAddress() {
      return this.unwrap().hasMemoryAddress();
   }

   public final int nioBufferCount() {
      return this.unwrap().nioBufferCount();
   }

   public final ByteBuffer internalNioBuffer(int index, int length) {
      return this.nioBuffer(index, length);
   }

   public final ByteBuf retainedSlice() {
      int index = this.readerIndex();
      return this.retainedSlice(index, this.writerIndex() - index);
   }

   public ByteBuf slice(int index, int length) {
      return new PooledNonRetainedSlicedByteBuf(this, this.unwrap(), index, length);
   }

   final ByteBuf duplicate0() {
      return new PooledNonRetainedDuplicateByteBuf(this, this.unwrap());
   }

   private static final class PooledNonRetainedSlicedByteBuf extends UnpooledSlicedByteBuf {
      private final ReferenceCounted referenceCountDelegate;

      PooledNonRetainedSlicedByteBuf(ReferenceCounted referenceCountDelegate, AbstractByteBuf buffer, int index, int length) {
         super(buffer, index, length);
         this.referenceCountDelegate = referenceCountDelegate;
      }

      int refCnt0() {
         return this.referenceCountDelegate.refCnt();
      }

      ByteBuf retain0() {
         this.referenceCountDelegate.retain();
         return this;
      }

      ByteBuf retain0(int increment) {
         this.referenceCountDelegate.retain(increment);
         return this;
      }

      ByteBuf touch0() {
         this.referenceCountDelegate.touch();
         return this;
      }

      ByteBuf touch0(Object hint) {
         this.referenceCountDelegate.touch(hint);
         return this;
      }

      boolean release0() {
         return this.referenceCountDelegate.release();
      }

      boolean release0(int decrement) {
         return this.referenceCountDelegate.release(decrement);
      }

      public ByteBuf duplicate() {
         return (new PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, this.unwrap())).setIndex(this.idx(this.readerIndex()), this.idx(this.writerIndex()));
      }

      public ByteBuf retainedDuplicate() {
         return PooledDuplicatedByteBuf.newInstance(this.unwrap(), this, this.idx(this.readerIndex()), this.idx(this.writerIndex()));
      }

      public ByteBuf slice(int index, int length) {
         this.checkIndex0(index, length);
         return new PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, this.unwrap(), this.idx(index), length);
      }

      public ByteBuf retainedSlice() {
         return this.retainedSlice(0, this.capacity());
      }

      public ByteBuf retainedSlice(int index, int length) {
         return PooledSlicedByteBuf.newInstance(this.unwrap(), this, this.idx(index), length);
      }
   }

   private static final class PooledNonRetainedDuplicateByteBuf extends UnpooledDuplicatedByteBuf {
      private final ReferenceCounted referenceCountDelegate;

      PooledNonRetainedDuplicateByteBuf(ReferenceCounted referenceCountDelegate, AbstractByteBuf buffer) {
         super(buffer);
         this.referenceCountDelegate = referenceCountDelegate;
      }

      int refCnt0() {
         return this.referenceCountDelegate.refCnt();
      }

      ByteBuf retain0() {
         this.referenceCountDelegate.retain();
         return this;
      }

      ByteBuf retain0(int increment) {
         this.referenceCountDelegate.retain(increment);
         return this;
      }

      ByteBuf touch0() {
         this.referenceCountDelegate.touch();
         return this;
      }

      ByteBuf touch0(Object hint) {
         this.referenceCountDelegate.touch(hint);
         return this;
      }

      boolean release0() {
         return this.referenceCountDelegate.release();
      }

      boolean release0(int decrement) {
         return this.referenceCountDelegate.release(decrement);
      }

      public ByteBuf duplicate() {
         return new PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, this);
      }

      public ByteBuf retainedDuplicate() {
         return PooledDuplicatedByteBuf.newInstance(this.unwrap(), this, this.readerIndex(), this.writerIndex());
      }

      public ByteBuf slice(int index, int length) {
         this.checkIndex0(index, length);
         return new PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, this.unwrap(), index, length);
      }

      public ByteBuf retainedSlice() {
         return this.retainedSlice(this.readerIndex(), this.capacity());
      }

      public ByteBuf retainedSlice(int index, int length) {
         return PooledSlicedByteBuf.newInstance(this.unwrap(), this, index, length);
      }
   }
}
