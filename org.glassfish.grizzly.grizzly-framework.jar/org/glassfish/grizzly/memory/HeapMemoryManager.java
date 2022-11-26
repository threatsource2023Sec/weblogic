package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;

public class HeapMemoryManager extends AbstractMemoryManager implements WrapperAware {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(TrimmableHeapBuffer.class, Integer.getInteger(HeapMemoryManager.class.getName() + ".thb-cache-size", 8));
   private static final ThreadCache.CachedTypeIndex BBW_CACHE_IDX = ThreadCache.obtainIndex(RecyclableByteBufferWrapper.class, Integer.getInteger(HeapMemoryManager.class.getName() + ".rbbw-cache-size", 2));

   public HeapMemoryManager() {
   }

   public HeapMemoryManager(int maxBufferSize) {
      super(maxBufferSize);
   }

   public HeapBuffer allocate(int size) {
      return this.allocateHeapBuffer(size);
   }

   public HeapBuffer allocateAtLeast(int size) {
      return this.allocateHeapBufferAtLeast(size);
   }

   public HeapBuffer reallocate(HeapBuffer oldBuffer, int newSize) {
      return this.reallocateHeapBuffer(oldBuffer, newSize);
   }

   public void release(HeapBuffer buffer) {
      this.releaseHeapBuffer(buffer);
   }

   public boolean willAllocateDirect(int size) {
      return false;
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   public ThreadLocalPool createThreadLocalPool() {
      return new HeapBufferThreadLocalPool(this);
   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.memory.jmx.HeapMemoryManager", this, HeapMemoryManager.class);
   }

   public HeapBuffer wrap(byte[] data) {
      return this.createTrimAwareBuffer(data, 0, data.length);
   }

   public HeapBuffer wrap(byte[] data, int offset, int length) {
      return this.createTrimAwareBuffer(data, offset, length);
   }

   public HeapBuffer wrap(String s) {
      return this.wrap(s, Charset.defaultCharset());
   }

   public HeapBuffer wrap(String s, Charset charset) {
      return this.wrap(s.getBytes(charset));
   }

   public Buffer wrap(ByteBuffer byteBuffer) {
      return (Buffer)(byteBuffer.hasArray() ? this.wrap(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) : this.createByteBufferWrapper(byteBuffer));
   }

   protected HeapBuffer allocateHeapBuffer(int size) {
      if (size > this.maxBufferSize) {
         return this.createTrimAwareBuffer(size);
      } else {
         ThreadLocalPool threadLocalCache = getHeapBufferThreadLocalPool();
         if (threadLocalCache == null) {
            return this.createTrimAwareBuffer(size);
         } else {
            int remaining = threadLocalCache.remaining();
            if (remaining == 0 || remaining < size) {
               this.reallocatePoolBuffer();
            }

            return (HeapBuffer)this.allocateFromPool(threadLocalCache, size);
         }
      }
   }

   protected HeapBuffer allocateHeapBufferAtLeast(int size) {
      if (size > this.maxBufferSize) {
         return this.createTrimAwareBuffer(size);
      } else {
         ThreadLocalPool threadLocalCache = getHeapBufferThreadLocalPool();
         if (threadLocalCache == null) {
            return this.createTrimAwareBuffer(size);
         } else {
            int remaining = threadLocalCache.remaining();
            if (remaining == 0 || remaining < size) {
               this.reallocatePoolBuffer();
               remaining = threadLocalCache.remaining();
            }

            return (HeapBuffer)this.allocateFromPool(threadLocalCache, remaining);
         }
      }
   }

   protected HeapBuffer reallocateHeapBuffer(HeapBuffer oldHeapBuffer, int newSize) {
      if (oldHeapBuffer.capacity() >= newSize) {
         return oldHeapBuffer;
      } else {
         ThreadLocalPool memoryPool = getHeapBufferThreadLocalPool();
         HeapBuffer newBuffer;
         if (memoryPool != null) {
            newBuffer = (HeapBuffer)memoryPool.reallocate(oldHeapBuffer, newSize);
            if (newBuffer != null) {
               ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, newSize - oldHeapBuffer.capacity());
               return newBuffer;
            }
         }

         newBuffer = this.allocateHeapBuffer(newSize);
         oldHeapBuffer.flip();
         return newBuffer.put((Buffer)oldHeapBuffer);
      }
   }

   protected final void releaseHeapBuffer(HeapBuffer heapBuffer) {
      ThreadLocalPool memoryPool = getHeapBufferThreadLocalPool();
      if (memoryPool != null && memoryPool.release(heapBuffer.clear())) {
         ProbeNotifier.notifyBufferReleasedToPool(this.monitoringConfig, heapBuffer.capacity());
      }

   }

   private void reallocatePoolBuffer() {
      byte[] heap = new byte[this.maxBufferSize];
      ProbeNotifier.notifyBufferAllocated(this.monitoringConfig, this.maxBufferSize);
      HeapBufferThreadLocalPool threadLocalCache = getHeapBufferThreadLocalPool();
      if (threadLocalCache != null) {
         threadLocalCache.reset(heap, 0, this.maxBufferSize);
      }

   }

   TrimmableHeapBuffer createTrimAwareBuffer(int length) {
      byte[] heap = new byte[length];
      ProbeNotifier.notifyBufferAllocated(this.monitoringConfig, length);
      return this.createTrimAwareBuffer(heap, 0, length);
   }

   TrimmableHeapBuffer createTrimAwareBuffer(byte[] heap, int offset, int length) {
      TrimmableHeapBuffer buffer = (TrimmableHeapBuffer)ThreadCache.takeFromCache(CACHE_IDX);
      if (buffer != null) {
         buffer.initialize(this, heap, offset, length);
         return buffer;
      } else {
         return new TrimmableHeapBuffer(this, heap, offset, length);
      }
   }

   private ByteBufferWrapper createByteBufferWrapper(ByteBuffer underlyingByteBuffer) {
      RecyclableByteBufferWrapper buffer = (RecyclableByteBufferWrapper)ThreadCache.takeFromCache(BBW_CACHE_IDX);
      if (buffer != null) {
         buffer.initialize(underlyingByteBuffer);
         return buffer;
      } else {
         return new RecyclableByteBufferWrapper(underlyingByteBuffer);
      }
   }

   private static HeapBufferThreadLocalPool getHeapBufferThreadLocalPool() {
      ThreadLocalPool pool = getThreadLocalPool();
      return pool instanceof HeapBufferThreadLocalPool ? (HeapBufferThreadLocalPool)pool : null;
   }

   private static final class RecyclableByteBufferWrapper extends ByteBufferWrapper implements Cacheable {
      private RecyclableByteBufferWrapper(ByteBuffer underlyingByteBuffer) {
         super(underlyingByteBuffer);
      }

      public void recycle() {
         this.allowBufferDispose = false;
         ThreadCache.putToCache(HeapMemoryManager.BBW_CACHE_IDX, this);
      }

      public void dispose() {
         super.dispose();
         this.recycle();
      }

      private void initialize(ByteBuffer underlyingByteBuffer) {
         this.visible = underlyingByteBuffer;
         this.disposeStackTrace = null;
      }

      // $FF: synthetic method
      RecyclableByteBufferWrapper(ByteBuffer x0, Object x1) {
         this(x0);
      }
   }

   private static final class TrimmableHeapBuffer extends HeapBuffer implements AbstractMemoryManager.TrimAware {
      private HeapMemoryManager mm;

      private TrimmableHeapBuffer(HeapMemoryManager mm, byte[] heap, int offset, int capacity) {
         super(heap, offset, capacity);
         this.mm = mm;
      }

      public void trim() {
         this.checkDispose();
         int sizeToReturn = this.cap - this.pos;
         if (sizeToReturn > 0) {
            HeapBufferThreadLocalPool threadLocalCache = HeapMemoryManager.getHeapBufferThreadLocalPool();
            if (threadLocalCache != null) {
               if (threadLocalCache.isLastAllocated((HeapBuffer)this)) {
                  this.flip();
                  this.cap = this.lim;
                  threadLocalCache.reduceLastAllocated((HeapBuffer)this);
                  return;
               }

               if (threadLocalCache.wantReset(sizeToReturn)) {
                  this.flip();
                  this.cap = this.lim;
                  threadLocalCache.reset(this.heap, this.offset + this.cap, sizeToReturn);
                  return;
               }
            }
         }

         super.trim();
      }

      public void recycle() {
         this.allowBufferDispose = false;
         ThreadCache.putToCache(HeapMemoryManager.CACHE_IDX, this);
      }

      public void dispose() {
         this.prepareDispose();
         this.mm.release((HeapBuffer)this);
         this.mm = null;
         this.byteBuffer = null;
         this.heap = null;
         this.pos = 0;
         this.offset = 0;
         this.lim = 0;
         this.cap = 0;
         this.order = ByteOrder.BIG_ENDIAN;
         this.bigEndian = true;
         this.recycle();
      }

      protected HeapBuffer createHeapBuffer(int offs, int capacity) {
         return this.mm.createTrimAwareBuffer(this.heap, offs + this.offset, capacity);
      }

      void initialize(HeapMemoryManager mm, byte[] heap, int offset, int length) {
         this.mm = mm;
         this.heap = heap;
         this.offset = offset;
         this.pos = 0;
         this.cap = length;
         this.lim = length;
         this.disposeStackTrace = null;
      }

      // $FF: synthetic method
      TrimmableHeapBuffer(HeapMemoryManager x0, byte[] x1, int x2, int x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private static final class HeapBufferThreadLocalPool implements ThreadLocalPool {
      private byte[] pool;
      private int leftPos;
      private int rightPos;
      private int start;
      private int end;
      private final ByteBuffer[] byteBufferCache;
      private int byteBufferCacheSize;
      private final HeapMemoryManager mm;

      public HeapBufferThreadLocalPool(HeapMemoryManager mm) {
         this(mm, 8);
      }

      public HeapBufferThreadLocalPool(HeapMemoryManager mm, int maxByteBufferCacheSize) {
         this.byteBufferCacheSize = 0;
         this.byteBufferCache = new ByteBuffer[maxByteBufferCacheSize];
         this.mm = mm;
      }

      public HeapBuffer allocate(int size) {
         HeapBuffer allocated = this.mm.createTrimAwareBuffer(this.pool, this.rightPos, size);
         if (this.byteBufferCacheSize > 0) {
            allocated.byteBuffer = this.byteBufferCache[--this.byteBufferCacheSize];
            this.byteBufferCache[this.byteBufferCacheSize] = null;
         }

         this.rightPos += size;
         return allocated;
      }

      public HeapBuffer reallocate(HeapBuffer heapBuffer, int newSize) {
         int diff;
         if (this.isLastAllocated(heapBuffer) && this.remaining() >= (diff = newSize - heapBuffer.cap)) {
            this.rightPos += diff;
            heapBuffer.cap = newSize;
            heapBuffer.lim = newSize;
            return heapBuffer;
         } else {
            return null;
         }
      }

      public boolean release(HeapBuffer heapBuffer) {
         boolean canCacheByteBuffer = heapBuffer.byteBuffer != null && this.byteBufferCacheSize < this.byteBufferCache.length;
         boolean result;
         if (this.isLastAllocated(heapBuffer)) {
            this.rightPos -= heapBuffer.cap;
            if (this.leftPos == this.rightPos) {
               this.leftPos = this.rightPos = this.start;
            }

            result = true;
         } else if (this.isReleasableLeft(heapBuffer)) {
            this.leftPos += heapBuffer.cap;
            if (this.leftPos == this.rightPos) {
               this.leftPos = this.rightPos = this.start;
            }

            result = true;
         } else if (this.wantReset(heapBuffer.cap)) {
            this.reset(heapBuffer);
            result = true;
         } else {
            canCacheByteBuffer = canCacheByteBuffer && this.pool == heapBuffer.heap;
            result = false;
         }

         if (canCacheByteBuffer) {
            this.byteBufferCache[this.byteBufferCacheSize++] = heapBuffer.byteBuffer;
         }

         return result;
      }

      public void reset(HeapBuffer heapBuffer) {
         this.reset(heapBuffer.heap, heapBuffer.offset, heapBuffer.cap);
      }

      public void reset(byte[] heap, int offset, int capacity) {
         if (this.pool != heap) {
            this.clearByteBufferCache();
            this.pool = heap;
         }

         this.leftPos = this.rightPos = this.start = offset;
         this.end = offset + capacity;
      }

      public boolean wantReset(int size) {
         return size - this.remaining() > 1024;
      }

      public boolean isLastAllocated(HeapBuffer oldHeapBuffer) {
         return oldHeapBuffer.heap == this.pool && oldHeapBuffer.offset + oldHeapBuffer.cap == this.rightPos;
      }

      private boolean isReleasableLeft(HeapBuffer oldHeapBuffer) {
         return oldHeapBuffer.heap == this.pool && oldHeapBuffer.offset == this.leftPos;
      }

      public HeapBuffer reduceLastAllocated(HeapBuffer heapBuffer) {
         int newPos = heapBuffer.offset + heapBuffer.cap;
         ProbeNotifier.notifyBufferReleasedToPool(this.mm.monitoringConfig, this.rightPos - newPos);
         this.rightPos = newPos;
         return null;
      }

      public int remaining() {
         return this.end - this.rightPos;
      }

      public boolean hasRemaining() {
         return this.rightPos < this.end;
      }

      public String toString() {
         return "(pool=" + this.pool.length + " pos=" + this.rightPos + " cap=" + this.end + ')';
      }

      private void clearByteBufferCache() {
         Arrays.fill(this.byteBufferCache, 0, this.byteBufferCacheSize, (Object)null);
         this.byteBufferCacheSize = 0;
      }
   }
}
