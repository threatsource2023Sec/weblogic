package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.LockSupport;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;

public class PooledMemoryManager implements MemoryManager, WrapperAware {
   public static final int DEFAULT_BASE_BUFFER_SIZE = 4096;
   public static final int DEFAULT_NUMBER_OF_POOLS = 3;
   public static final int DEFAULT_GROWTH_FACTOR = 2;
   public static final float DEFAULT_HEAP_USAGE_PERCENTAGE = 0.03F;
   public static final float DEFAULT_PREALLOCATED_BUFFERS_PERCENTAGE = 1.0F;
   private static final boolean FORCE_BYTE_BUFFER_BASED_BUFFERS = Boolean.getBoolean(PooledMemoryManager.class + ".force-byte-buffer-based-buffers");
   private static final long BACK_OFF_DELAY = Long.getLong(PooledMemoryManager.class + ".back-off-delay", 0L);
   protected final DefaultMonitoringConfig monitoringConfig;
   private final Pool[] pools;
   private final int maxPooledBufferSize;

   public PooledMemoryManager() {
      this(4096, 3, 2, Runtime.getRuntime().availableProcessors(), 0.03F, 1.0F, false);
   }

   public PooledMemoryManager(boolean isDirect) {
      this(4096, 3, 2, Runtime.getRuntime().availableProcessors(), 0.03F, 1.0F, isDirect);
   }

   public PooledMemoryManager(int baseBufferSize, int numberOfPools, int growthFactor, int numberOfPoolSlices, float percentOfHeap, float percentPreallocated, boolean isDirect) {
      this.monitoringConfig = new DefaultMonitoringConfig(MemoryProbe.class) {
         public Object createManagementObject() {
            return PooledMemoryManager.this.createJmxManagementObject();
         }
      };
      if (baseBufferSize <= 0) {
         throw new IllegalArgumentException("baseBufferSize must be greater than zero");
      } else if (numberOfPools <= 0) {
         throw new IllegalArgumentException("numberOfPools must be greater than zero");
      } else if (growthFactor == 0 && numberOfPools > 1) {
         throw new IllegalArgumentException("if numberOfPools is greater than 0 - growthFactor must be greater than zero");
      } else if (growthFactor < 0) {
         throw new IllegalArgumentException("growthFactor must be greater or equal to zero");
      } else if (numberOfPoolSlices <= 0) {
         throw new IllegalArgumentException("numberOfPoolSlices must be greater than zero");
      } else if (isPowerOfTwo(baseBufferSize) && isPowerOfTwo(growthFactor)) {
         if (!(percentOfHeap <= 0.0F) && !(percentOfHeap >= 1.0F)) {
            if (!(percentPreallocated < 0.0F) && !(percentPreallocated > 1.0F)) {
               long heapSize = Runtime.getRuntime().maxMemory();
               long memoryPerSubPool = (long)((float)heapSize * percentOfHeap / (float)numberOfPools);
               this.pools = new Pool[numberOfPools];
               int i = 0;

               for(int bufferSize = baseBufferSize; i < numberOfPools; bufferSize <<= growthFactor) {
                  this.pools[i] = new Pool(bufferSize, memoryPerSubPool, numberOfPoolSlices, percentPreallocated, isDirect, this.monitoringConfig);
                  ++i;
               }

               this.maxPooledBufferSize = this.pools[numberOfPools - 1].bufferSize;
            } else {
               throw new IllegalArgumentException("percentPreallocated must be greater or equal to zero and less or equal to 1");
            }
         } else {
            throw new IllegalArgumentException("percentOfHeap must be greater than zero and less than 1");
         }
      } else {
         throw new IllegalArgumentException("minBufferSize and growthFactor must be a power of two");
      }
   }

   public Buffer allocate(int size) {
      if (size < 0) {
         throw new IllegalArgumentException("Requested allocation size must be greater than or equal to zero.");
      } else {
         return this.allocateAtLeast(size).limit(size);
      }
   }

   public Buffer allocateAtLeast(int size) {
      if (size < 0) {
         throw new IllegalArgumentException("Requested allocation size must be greater than or equal to zero.");
      } else if (size == 0) {
         return Buffers.EMPTY_BUFFER;
      } else {
         return (Buffer)(size <= this.maxPooledBufferSize ? this.getPoolFor(size).allocate() : this.allocateToCompositeBuffer(this.newCompositeBuffer(), size));
      }
   }

   public Buffer reallocate(Buffer oldBuffer, int newSize) {
      if (newSize == 0) {
         oldBuffer.tryDispose();
         return Buffers.EMPTY_BUFFER;
      } else {
         int curBufSize = oldBuffer.capacity();
         if (oldBuffer instanceof PoolBuffer) {
            Pool newPool;
            if (curBufSize >= newSize) {
               PoolBuffer oldPoolBuffer = (PoolBuffer)oldBuffer;
               newPool = this.getPoolFor(newSize);
               if (newPool != oldPoolBuffer.owner().owner) {
                  int pos = Math.min(oldPoolBuffer.position(), newSize);
                  Buffer newPoolBuffer = newPool.allocate();
                  Buffers.setPositionLimit((Buffer)oldPoolBuffer, 0, newSize);
                  newPoolBuffer.put((Buffer)oldPoolBuffer);
                  Buffers.setPositionLimit(newPoolBuffer, pos, newSize);
                  oldPoolBuffer.tryDispose();
                  return newPoolBuffer;
               } else {
                  return oldPoolBuffer.limit(newSize);
               }
            } else {
               int pos = oldBuffer.position();
               Buffers.setPositionLimit((Buffer)oldBuffer, 0, curBufSize);
               if (newSize <= this.maxPooledBufferSize) {
                  newPool = this.getPoolFor(newSize);
                  Buffer newPoolBuffer = newPool.allocate();
                  newPoolBuffer.put(oldBuffer);
                  Buffers.setPositionLimit(newPoolBuffer, pos, newSize);
                  oldBuffer.tryDispose();
                  return newPoolBuffer;
               } else {
                  CompositeBuffer cb = this.newCompositeBuffer();
                  cb.append(oldBuffer);
                  this.allocateToCompositeBuffer(cb, newSize - curBufSize);
                  Buffers.setPositionLimit((Buffer)cb, pos, newSize);
                  return cb;
               }
            }
         } else {
            assert oldBuffer.isComposite();

            CompositeBuffer oldCompositeBuffer = (CompositeBuffer)oldBuffer;
            if (curBufSize > newSize) {
               int oldPos = oldCompositeBuffer.position();
               Buffers.setPositionLimit(oldBuffer, newSize, newSize);
               oldCompositeBuffer.trim();
               oldCompositeBuffer.position(Math.min(oldPos, newSize));
               return oldCompositeBuffer;
            } else {
               return this.allocateToCompositeBuffer(oldCompositeBuffer, newSize - curBufSize);
            }
         }
      }
   }

   public void release(Buffer buffer) {
      buffer.tryDispose();
   }

   public boolean willAllocateDirect(int size) {
      return false;
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   public Buffer wrap(byte[] data) {
      return this.wrap(ByteBuffer.wrap(data));
   }

   public Buffer wrap(byte[] data, int offset, int length) {
      return this.wrap(ByteBuffer.wrap(data, offset, length));
   }

   public Buffer wrap(String s) {
      return this.wrap(s.getBytes(Charset.defaultCharset()));
   }

   public Buffer wrap(String s, Charset charset) {
      return this.wrap(s.getBytes(charset));
   }

   public Buffer wrap(ByteBuffer byteBuffer) {
      return new ByteBufferWrapper(byteBuffer);
   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.memory.jmx.PooledMemoryManager", this, PooledMemoryManager.class);
   }

   Pool[] getPools() {
      return (Pool[])Arrays.copyOf(this.pools, this.pools.length);
   }

   private Pool getPoolFor(int size) {
      for(int i = 0; i < this.pools.length; ++i) {
         Pool pool = this.pools[i];
         if (pool.bufferSize >= size) {
            return pool;
         }
      }

      throw new IllegalStateException("There is no pool big enough to allocate " + size + " bytes");
   }

   private CompositeBuffer allocateToCompositeBuffer(CompositeBuffer cb, int size) {
      assert size >= 0;

      if (size >= this.maxPooledBufferSize) {
         Pool maxBufferSizePool = this.pools[this.pools.length - 1];

         do {
            cb.append(maxBufferSizePool.allocate());
            size -= this.maxPooledBufferSize;
         } while(size >= this.maxPooledBufferSize);
      }

      for(int i = 0; i < this.pools.length; ++i) {
         Pool pool = this.pools[i];
         if (pool.bufferSize >= size) {
            Buffer b = pool.allocate();
            cb.append(b.limit(size));
            break;
         }
      }

      return cb;
   }

   private CompositeBuffer newCompositeBuffer() {
      CompositeBuffer cb = CompositeBuffer.newBuffer(this);
      cb.allowInternalBuffersDispose(true);
      cb.allowBufferDispose(true);
      return cb;
   }

   private static boolean isPowerOfTwo(int valueToCheck) {
      return (valueToCheck & valueToCheck - 1) == 0;
   }

   private static int fillHighestOneBitRight(int value) {
      value |= value >> 1;
      value |= value >> 2;
      value |= value >> 4;
      value |= value >> 8;
      value |= value >> 16;
      return value;
   }

   private static final class PoolByteBufferWrapper extends ByteBufferWrapper implements PoolBuffer {
      private final PoolSlice owner;
      boolean free;
      protected final AtomicInteger shareCount;
      protected final PoolByteBufferWrapper source;
      private final ByteBuffer origVisible;

      private PoolByteBufferWrapper(ByteBuffer underlyingByteBuffer, PoolSlice owner) {
         this(underlyingByteBuffer, owner, (PoolByteBufferWrapper)null, new AtomicInteger());
      }

      private PoolByteBufferWrapper(ByteBuffer underlyingByteBuffer, PoolSlice owner, PoolByteBufferWrapper source, AtomicInteger shareCount) {
         super(underlyingByteBuffer);
         if (underlyingByteBuffer == null) {
            throw new IllegalArgumentException("underlyingByteBuffer cannot be null.");
         } else if (shareCount == null) {
            throw new IllegalArgumentException("shareCount cannot be null");
         } else {
            this.owner = owner;
            this.shareCount = shareCount;
            this.source = source != null ? source : this;
            this.origVisible = this.source.visible;
         }
      }

      public PoolBuffer prepare() {
         this.allowBufferDispose = true;
         this.free = false;
         return this;
      }

      public PoolSlice owner() {
         return this.owner;
      }

      public boolean free() {
         return this.free;
      }

      public PoolBuffer free(boolean free) {
         this.free = free;
         return this;
      }

      public void dispose() {
         if (!this.free) {
            this.free = true;
            this.dispose0();
         }
      }

      private void dispose0() {
         boolean isNotShared = this.shareCount.get() == 0;
         if (!isNotShared) {
            isNotShared = this.shareCount.getAndDecrement() == 0;
            if (isNotShared) {
               this.shareCount.set(0);
            }
         }

         if (isNotShared) {
            this.source.returnToPool();
         }

      }

      protected ByteBufferWrapper wrapByteBuffer(ByteBuffer buffer) {
         PoolByteBufferWrapper b = new PoolByteBufferWrapper(buffer, (PoolSlice)null, this.source, this.shareCount);
         b.allowBufferDispose(true);
         this.shareCount.incrementAndGet();
         return b;
      }

      protected final void checkDispose() {
         if (this.free) {
            throw new IllegalStateException("PoolBuffer has already been disposed", this.disposeStackTrace);
         }
      }

      private void returnToPool() {
         this.visible = this.origVisible;
         this.visible.clear();
         this.owner.offer(this);
      }

      // $FF: synthetic method
      PoolByteBufferWrapper(ByteBuffer x0, PoolSlice x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class PoolHeapBuffer extends HeapBuffer implements PoolBuffer {
      private final PoolSlice owner;
      boolean free;
      protected final AtomicInteger shareCount;
      protected final PoolHeapBuffer source;

      private PoolHeapBuffer(byte[] heap, PoolSlice owner) {
         this(heap, 0, heap.length, owner, (PoolHeapBuffer)null, new AtomicInteger());
      }

      private PoolHeapBuffer(byte[] heap, int offs, int cap, PoolSlice owner, PoolHeapBuffer source, AtomicInteger shareCount) {
         super(heap, offs, cap);
         if (heap == null) {
            throw new IllegalArgumentException("heap cannot be null.");
         } else if (shareCount == null) {
            throw new IllegalArgumentException("shareCount cannot be null");
         } else {
            this.owner = owner;
            this.shareCount = shareCount;
            this.source = source != null ? source : this;
         }
      }

      public PoolBuffer prepare() {
         this.allowBufferDispose = true;
         this.free = false;
         return this;
      }

      public PoolSlice owner() {
         return this.owner;
      }

      public boolean free() {
         return this.free;
      }

      public PoolBuffer free(boolean free) {
         this.free = free;
         return this;
      }

      public HeapBuffer asReadOnlyBuffer() {
         HeapBuffer b = this.asReadOnlyBuffer(this.offset, this.cap);
         b.pos = this.pos;
         b.lim = this.lim;
         return b;
      }

      private HeapBuffer asReadOnlyBuffer(int offset, int cap) {
         this.checkDispose();
         this.onShareHeap();
         HeapBuffer b = new ReadOnlyHeapBuffer(this.heap, offset, cap) {
            public void dispose() {
               super.dispose();
               PoolHeapBuffer.this.dispose0();
            }

            protected void onShareHeap() {
               PoolHeapBuffer.this.onShareHeap();
            }

            protected HeapBuffer createHeapBuffer(int offset, int capacity) {
               return PoolHeapBuffer.this.asReadOnlyBuffer(offset, capacity);
            }
         };
         b.allowBufferDispose(true);
         return b;
      }

      public void dispose() {
         if (!this.free) {
            this.free = true;
            this.dispose0();
         }
      }

      private void dispose0() {
         boolean isNotShared = this.shareCount.get() == 0;
         if (!isNotShared) {
            isNotShared = this.shareCount.getAndDecrement() == 0;
            if (isNotShared) {
               this.shareCount.set(0);
            }
         }

         if (isNotShared) {
            this.source.returnToPool();
         }

      }

      private void returnToPool() {
         this.cap = this.heap.length;
         this.clear();
         this.owner.offer(this);
      }

      protected final void checkDispose() {
         if (this.free) {
            throw new IllegalStateException("PoolBuffer has already been disposed", this.disposeStackTrace);
         }
      }

      protected HeapBuffer createHeapBuffer(int offs, int capacity) {
         this.onShareHeap();
         PoolHeapBuffer b = new PoolHeapBuffer(this.heap, offs + this.offset, capacity, (PoolSlice)null, this.source, this.shareCount);
         b.allowBufferDispose(true);
         return b;
      }

      protected void onShareHeap() {
         super.onShareHeap();
         this.shareCount.incrementAndGet();
      }

      // $FF: synthetic method
      PoolHeapBuffer(byte[] x0, PoolSlice x1, Object x2) {
         this(x0, x1);
      }
   }

   interface PoolBuffer extends Buffer {
      PoolBuffer prepare();

      boolean free();

      PoolBuffer free(boolean var1);

      PoolSlice owner();
   }

   static final class PoolSlice {
      private static final int LOG2_STRIDE = 4;
      private static final int STRIDE = 16;
      private static final int MASK = 1073741823;
      private static final int WRAP_BIT_MASK = 1073741824;
      private final PaddedAtomicReferenceArray pool1;
      private final PaddedAtomicReferenceArray pool2;
      private final PaddedAtomicInteger pollIdx;
      private final PaddedAtomicInteger offerIdx;
      private final Pool owner;
      private final int maxPoolSize;
      private final int stridesInPool;
      private final int bufferSize;
      private final boolean isDirect;
      private final DefaultMonitoringConfig monitoringConfig;

      PoolSlice(Pool owner, long totalPoolSize, int bufferSize, float percentPreallocated, boolean isDirect, DefaultMonitoringConfig monitoringConfig) {
         this.owner = owner;
         this.bufferSize = bufferSize;
         this.isDirect = isDirect;
         this.monitoringConfig = monitoringConfig;
         int initialSize = (int)(totalPoolSize / (long)bufferSize);
         this.maxPoolSize = initialSize + 15 & -16;
         this.stridesInPool = this.maxPoolSize >> 4;
         if (this.maxPoolSize >= 1073741824) {
            throw new IllegalStateException("Cannot manage a pool larger than 2^30-1");
         } else {
            this.pool1 = new PaddedAtomicReferenceArray(this.maxPoolSize);
            int preallocatedBufs = Math.min(this.maxPoolSize, (int)(percentPreallocated * (float)this.maxPoolSize));
            int idx = 0;

            for(int i = 0; i < preallocatedBufs; idx = this.nextIndex(idx)) {
               this.pool1.lazySet(idx, this.allocate().free(true));
               ++i;
            }

            this.pool2 = new PaddedAtomicReferenceArray(this.maxPoolSize);
            this.pollIdx = new PaddedAtomicInteger(0);
            this.offerIdx = new PaddedAtomicInteger(idx);
         }
      }

      public final PoolBuffer poll() {
         while(true) {
            int pollIdx = this.pollIdx.get();
            int unmaskedPollIdx = this.offerIdx.get();
            if (isEmpty(pollIdx, unmaskedPollIdx)) {
               return null;
            }

            int nextPollIdx = this.nextIndex(pollIdx);
            if (this.pollIdx.compareAndSet(pollIdx, nextPollIdx)) {
               unmaskedPollIdx = unmask(pollIdx);
               AtomicReferenceArray pool = this.pool(pollIdx);

               while(true) {
                  PoolBuffer pb = (PoolBuffer)pool.getAndSet(unmaskedPollIdx, (Object)null);
                  if (pb != null) {
                     ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, this.bufferSize);
                     return pb;
                  }

                  Thread.yield();
               }
            }

            LockSupport.parkNanos(PooledMemoryManager.BACK_OFF_DELAY);
         }
      }

      public final boolean offer(PoolBuffer b) {
         while(true) {
            int offerIdx = this.offerIdx.get();
            int unmaskedOfferIdx = this.pollIdx.get();
            if (isFull(unmaskedOfferIdx, offerIdx)) {
               return false;
            }

            int nextOfferIndex = this.nextIndex(offerIdx);
            if (this.offerIdx.compareAndSet(offerIdx, nextOfferIndex)) {
               unmaskedOfferIdx = unmask(offerIdx);
               AtomicReferenceArray pool = this.pool(offerIdx);

               while(!pool.compareAndSet(unmaskedOfferIdx, (Object)null, b)) {
                  Thread.yield();
               }

               ProbeNotifier.notifyBufferReleasedToPool(this.monitoringConfig, this.bufferSize);
               return true;
            }

            LockSupport.parkNanos(PooledMemoryManager.BACK_OFF_DELAY);
         }
      }

      public final int elementsCount() {
         return this.elementsCount(this.pollIdx.get(), this.offerIdx.get());
      }

      private int elementsCount(int ridx, int widx) {
         return this.unstride(unmask(widx)) - this.unstride(unmask(ridx)) + (this.maxPoolSize & PooledMemoryManager.fillHighestOneBitRight((ridx ^ widx) & 1073741824));
      }

      public int getMaxElementsCount() {
         return this.maxPoolSize;
      }

      public final long size() {
         return (long)this.elementsCount() * (long)this.bufferSize;
      }

      public void clear() {
         while(this.poll() != null) {
         }

      }

      public PoolBuffer allocate() {
         PoolBuffer buffer = !this.isDirect && !PooledMemoryManager.FORCE_BYTE_BUFFER_BASED_BUFFERS ? new PoolHeapBuffer(new byte[this.bufferSize], this) : new PoolByteBufferWrapper(this.isDirect ? ByteBuffer.allocateDirect(this.bufferSize) : ByteBuffer.allocate(this.bufferSize), this);
         ProbeNotifier.notifyBufferAllocated(this.monitoringConfig, this.bufferSize);
         return (PoolBuffer)buffer;
      }

      private static boolean isFull(int pollIdx, int offerIdx) {
         return (pollIdx ^ offerIdx) == 1073741824;
      }

      private static boolean isEmpty(int pollIdx, int offerIdx) {
         return pollIdx == offerIdx;
      }

      private AtomicReferenceArray pool(int idx) {
         return (idx & 1073741824) == 0 ? this.pool1 : this.pool2;
      }

      private int nextIndex(int currentIdx) {
         int arrayIndex = unmask(currentIdx);
         if (arrayIndex + 16 < this.maxPoolSize) {
            return currentIdx + 16;
         } else {
            int offset = arrayIndex - this.maxPoolSize + 16 + 1;
            return offset == 16 ? 1073741824 ^ currentIdx & 1073741824 : offset | currentIdx & 1073741824;
         }
      }

      private static int unmask(int val) {
         return val & 1073741823;
      }

      private static int getWrappingBit(int val) {
         return val & 1073741824;
      }

      private int unstride(int idx) {
         return (idx >> 4) + (idx & 15) * this.stridesInPool;
      }

      public String toString() {
         return this.toString(this.pollIdx.get(), this.offerIdx.get());
      }

      private String toString(int ridx, int widx) {
         return "BufferSlice[" + Integer.toHexString(this.hashCode()) + "] {buffer size=" + this.bufferSize + ", elements in pool=" + this.elementsCount(ridx, widx) + ", poll index=" + unmask(ridx) + ", poll wrap bit=" + (PooledMemoryManager.fillHighestOneBitRight(getWrappingBit(ridx)) & 1) + ", offer index=" + unmask(widx) + ", offer wrap bit=" + (PooledMemoryManager.fillHighestOneBitRight(getWrappingBit(widx)) & 1) + ", maxPoolSize=" + this.maxPoolSize + '}';
      }

      static final class PaddedAtomicReferenceArray extends AtomicReferenceArray {
         private long p0;
         private long p1;
         private long p2;
         private long p3;
         private long p4;
         private long p5;
         private long p6;
         private long p7 = 7L;

         PaddedAtomicReferenceArray(int length) {
            super(length);
         }
      }

      static final class PaddedAtomicInteger extends AtomicInteger {
         private long p0;
         private long p1;
         private long p2;
         private long p3;
         private long p4;
         private long p5;
         private long p6;
         private long p7 = 7L;

         PaddedAtomicInteger(int initialValue) {
            super(initialValue);
         }
      }
   }

   static final class Pool {
      private final PoolSlice[] slices;
      private final int bufferSize;

      public Pool(int bufferSize, long memoryPerSubPool, int numberOfPoolSlices, float percentPreallocated, boolean isDirect, DefaultMonitoringConfig monitoringConfig) {
         this.bufferSize = bufferSize;
         this.slices = new PoolSlice[numberOfPoolSlices];
         long memoryPerSlice = memoryPerSubPool / (long)numberOfPoolSlices;

         for(int i = 0; i < numberOfPoolSlices; ++i) {
            this.slices[i] = new PoolSlice(this, memoryPerSlice, bufferSize, percentPreallocated, isDirect, monitoringConfig);
         }

      }

      public int elementsCount() {
         int sum = 0;

         for(int i = 0; i < this.slices.length; ++i) {
            sum += this.slices[i].elementsCount();
         }

         return sum;
      }

      public long size() {
         return (long)this.elementsCount() * (long)this.bufferSize;
      }

      public int getBufferSize() {
         return this.bufferSize;
      }

      public PoolSlice[] getSlices() {
         return (PoolSlice[])Arrays.copyOf(this.slices, this.slices.length);
      }

      public Buffer allocate() {
         PoolSlice slice = this.getSlice();
         PoolBuffer b = slice.poll();
         if (b == null) {
            b = slice.allocate();
         }

         return b.prepare();
      }

      public String toString() {
         StringBuilder sb = new StringBuilder("Pool[" + Integer.toHexString(this.hashCode()) + "] {buffer size=" + this.bufferSize + ", slices count=" + this.slices.length);

         for(int i = 0; i < this.slices.length; ++i) {
            if (i == 0) {
               sb.append("\n");
            }

            sb.append("\t[").append(i).append("] ").append(this.slices[i].toString()).append('\n');
         }

         sb.append('}');
         return sb.toString();
      }

      private PoolSlice getSlice() {
         return this.slices[ThreadLocalRandom.current().nextInt(this.slices.length)];
      }
   }
}
