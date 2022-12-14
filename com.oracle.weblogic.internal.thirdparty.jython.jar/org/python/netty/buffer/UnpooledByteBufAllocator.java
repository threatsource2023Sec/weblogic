package org.python.netty.buffer;

import java.nio.ByteBuffer;
import org.python.netty.util.internal.LongCounter;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.StringUtil;

public final class UnpooledByteBufAllocator extends AbstractByteBufAllocator implements ByteBufAllocatorMetricProvider {
   private final UnpooledByteBufAllocatorMetric metric;
   private final boolean disableLeakDetector;
   public static final UnpooledByteBufAllocator DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());

   public UnpooledByteBufAllocator(boolean preferDirect) {
      this(preferDirect, false);
   }

   public UnpooledByteBufAllocator(boolean preferDirect, boolean disableLeakDetector) {
      super(preferDirect);
      this.metric = new UnpooledByteBufAllocatorMetric();
      this.disableLeakDetector = disableLeakDetector;
   }

   protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
      return (ByteBuf)(PlatformDependent.hasUnsafe() ? new InstrumentedUnpooledUnsafeHeapByteBuf(this, initialCapacity, maxCapacity) : new InstrumentedUnpooledHeapByteBuf(this, initialCapacity, maxCapacity));
   }

   protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
      Object buf;
      if (PlatformDependent.hasUnsafe()) {
         buf = PlatformDependent.useDirectBufferNoCleaner() ? new InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(this, initialCapacity, maxCapacity) : new InstrumentedUnpooledUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
      } else {
         buf = new InstrumentedUnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
      }

      return (ByteBuf)(this.disableLeakDetector ? buf : toLeakAwareBuffer((ByteBuf)buf));
   }

   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
      CompositeByteBuf buf = new CompositeByteBuf(this, false, maxNumComponents);
      return this.disableLeakDetector ? buf : toLeakAwareBuffer(buf);
   }

   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
      CompositeByteBuf buf = new CompositeByteBuf(this, true, maxNumComponents);
      return this.disableLeakDetector ? buf : toLeakAwareBuffer(buf);
   }

   public boolean isDirectBufferPooled() {
      return false;
   }

   public ByteBufAllocatorMetric metric() {
      return this.metric;
   }

   void incrementDirect(int amount) {
      this.metric.directCounter.add((long)amount);
   }

   void decrementDirect(int amount) {
      this.metric.directCounter.add((long)(-amount));
   }

   void incrementHeap(int amount) {
      this.metric.heapCounter.add((long)amount);
   }

   void decrementHeap(int amount) {
      this.metric.heapCounter.add((long)(-amount));
   }

   private static final class UnpooledByteBufAllocatorMetric implements ByteBufAllocatorMetric {
      final LongCounter directCounter;
      final LongCounter heapCounter;

      private UnpooledByteBufAllocatorMetric() {
         this.directCounter = PlatformDependent.newLongCounter();
         this.heapCounter = PlatformDependent.newLongCounter();
      }

      public long usedHeapMemory() {
         return this.heapCounter.value();
      }

      public long usedDirectMemory() {
         return this.directCounter.value();
      }

      public String toString() {
         return StringUtil.simpleClassName((Object)this) + "(usedHeapMemory: " + this.usedHeapMemory() + "; usedDirectMemory: " + this.usedDirectMemory() + ')';
      }

      // $FF: synthetic method
      UnpooledByteBufAllocatorMetric(Object x0) {
         this();
      }
   }

   private static final class InstrumentedUnpooledDirectByteBuf extends UnpooledDirectByteBuf {
      InstrumentedUnpooledDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
         super(alloc, initialCapacity, maxCapacity);
      }

      protected ByteBuffer allocateDirect(int initialCapacity) {
         ByteBuffer buffer = super.allocateDirect(initialCapacity);
         ((UnpooledByteBufAllocator)this.alloc()).incrementDirect(buffer.capacity());
         return buffer;
      }

      protected void freeDirect(ByteBuffer buffer) {
         int capacity = buffer.capacity();
         super.freeDirect(buffer);
         ((UnpooledByteBufAllocator)this.alloc()).decrementDirect(capacity);
      }
   }

   private static final class InstrumentedUnpooledUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
      InstrumentedUnpooledUnsafeDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
         super(alloc, initialCapacity, maxCapacity);
      }

      protected ByteBuffer allocateDirect(int initialCapacity) {
         ByteBuffer buffer = super.allocateDirect(initialCapacity);
         ((UnpooledByteBufAllocator)this.alloc()).incrementDirect(buffer.capacity());
         return buffer;
      }

      protected void freeDirect(ByteBuffer buffer) {
         int capacity = buffer.capacity();
         super.freeDirect(buffer);
         ((UnpooledByteBufAllocator)this.alloc()).decrementDirect(capacity);
      }
   }

   private static final class InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf extends UnpooledUnsafeNoCleanerDirectByteBuf {
      InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
         super(alloc, initialCapacity, maxCapacity);
      }

      protected ByteBuffer allocateDirect(int initialCapacity) {
         ByteBuffer buffer = super.allocateDirect(initialCapacity);
         ((UnpooledByteBufAllocator)this.alloc()).incrementDirect(buffer.capacity());
         return buffer;
      }

      ByteBuffer reallocateDirect(ByteBuffer oldBuffer, int initialCapacity) {
         int capacity = oldBuffer.capacity();
         ByteBuffer buffer = super.reallocateDirect(oldBuffer, initialCapacity);
         ((UnpooledByteBufAllocator)this.alloc()).incrementDirect(buffer.capacity() - capacity);
         return buffer;
      }

      protected void freeDirect(ByteBuffer buffer) {
         int capacity = buffer.capacity();
         super.freeDirect(buffer);
         ((UnpooledByteBufAllocator)this.alloc()).decrementDirect(capacity);
      }
   }

   private static final class InstrumentedUnpooledHeapByteBuf extends UnpooledHeapByteBuf {
      InstrumentedUnpooledHeapByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
         super(alloc, initialCapacity, maxCapacity);
      }

      byte[] allocateArray(int initialCapacity) {
         byte[] bytes = super.allocateArray(initialCapacity);
         ((UnpooledByteBufAllocator)this.alloc()).incrementHeap(bytes.length);
         return bytes;
      }

      void freeArray(byte[] array) {
         int length = array.length;
         super.freeArray(array);
         ((UnpooledByteBufAllocator)this.alloc()).decrementHeap(length);
      }
   }

   private static final class InstrumentedUnpooledUnsafeHeapByteBuf extends UnpooledUnsafeHeapByteBuf {
      InstrumentedUnpooledUnsafeHeapByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
         super(alloc, initialCapacity, maxCapacity);
      }

      byte[] allocateArray(int initialCapacity) {
         byte[] bytes = super.allocateArray(initialCapacity);
         ((UnpooledByteBufAllocator)this.alloc()).incrementHeap(bytes.length);
         return bytes;
      }

      void freeArray(byte[] array) {
         int length = array.length;
         super.freeArray(array);
         ((UnpooledByteBufAllocator)this.alloc()).decrementHeap(length);
      }
   }
}
