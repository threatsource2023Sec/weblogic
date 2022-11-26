package org.glassfish.grizzly.memory;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;

public class ByteBufferManager extends AbstractMemoryManager implements WrapperAware, ByteBufferAware {
   public static final int DEFAULT_SMALL_BUFFER_SIZE = 32;
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(TrimAwareWrapper.class, Integer.getInteger(ByteBufferManager.class.getName() + ".taw-cache-size", 2));
   private final ThreadCache.CachedTypeIndex SMALL_BUFFER_CACHE_IDX;
   protected boolean isDirect;
   protected final int maxSmallBufferSize;

   public ByteBufferManager() {
      this(false, 65536, 32);
   }

   public ByteBufferManager(boolean isDirect) {
      this(isDirect, 65536, 32);
   }

   public ByteBufferManager(boolean isDirect, int maxBufferSize, int maxSmallBufferSize) {
      super(maxBufferSize);
      this.SMALL_BUFFER_CACHE_IDX = ThreadCache.obtainIndex(SmallByteBufferWrapper.class.getName() + '.' + System.identityHashCode(this), SmallByteBufferWrapper.class, Integer.getInteger(ByteBufferManager.class.getName() + ".sbbw-cache-size", 16));
      this.maxSmallBufferSize = maxSmallBufferSize;
      this.isDirect = isDirect;
   }

   public int getMaxSmallBufferSize() {
      return this.maxSmallBufferSize;
   }

   public ByteBufferWrapper allocate(int size) {
      if (size <= this.maxSmallBufferSize) {
         SmallByteBufferWrapper buffer = this.createSmallBuffer();
         buffer.limit(size);
         return buffer;
      } else {
         return this.wrap(this.allocateByteBuffer(size));
      }
   }

   public ByteBufferWrapper allocateAtLeast(int size) {
      if (size <= this.maxSmallBufferSize) {
         SmallByteBufferWrapper buffer = this.createSmallBuffer();
         buffer.limit(size);
         return buffer;
      } else {
         return this.wrap(this.allocateByteBufferAtLeast(size));
      }
   }

   public ByteBufferWrapper reallocate(ByteBufferWrapper oldBuffer, int newSize) {
      return this.wrap(this.reallocateByteBuffer(oldBuffer.underlying(), newSize));
   }

   public void release(ByteBufferWrapper buffer) {
      this.releaseByteBuffer(buffer.underlying());
   }

   public boolean isDirect() {
      return this.isDirect;
   }

   public void setDirect(boolean isDirect) {
      this.isDirect = isDirect;
   }

   public boolean willAllocateDirect(int size) {
      return this.isDirect;
   }

   public ByteBufferWrapper wrap(byte[] data) {
      return this.wrap(data, 0, data.length);
   }

   public ByteBufferWrapper wrap(byte[] data, int offset, int length) {
      return this.wrap(ByteBuffer.wrap(data, offset, length));
   }

   public ByteBufferWrapper wrap(String s) {
      return this.wrap(s, Charset.defaultCharset());
   }

   public ByteBufferWrapper wrap(String s, Charset charset) {
      try {
         byte[] byteRepresentation = s.getBytes(charset.name());
         return this.wrap(ByteBuffer.wrap(byteRepresentation));
      } catch (UnsupportedEncodingException var4) {
         throw new IllegalStateException(var4);
      }
   }

   public ThreadLocalPool createThreadLocalPool() {
      return new ByteBufferThreadLocalPool();
   }

   public ByteBufferWrapper wrap(ByteBuffer byteBuffer) {
      return this.createTrimAwareBuffer(byteBuffer);
   }

   public ByteBuffer allocateByteBuffer(int size) {
      if (size > this.maxBufferSize) {
         return this.allocateByteBuffer0(size);
      } else {
         ThreadLocalPool threadLocalCache = getByteBufferThreadLocalPool();
         if (threadLocalCache == null) {
            return this.allocateByteBuffer0(size);
         } else {
            int remaining = threadLocalCache.remaining();
            if (remaining == 0 || remaining < size) {
               this.reallocatePoolBuffer();
            }

            return (ByteBuffer)this.allocateFromPool(threadLocalCache, size);
         }
      }
   }

   public ByteBuffer allocateByteBufferAtLeast(int size) {
      if (size > this.maxBufferSize) {
         return this.allocateByteBuffer0(size);
      } else {
         ThreadLocalPool threadLocalCache = getByteBufferThreadLocalPool();
         if (threadLocalCache == null) {
            return this.allocateByteBuffer0(size);
         } else {
            int remaining = threadLocalCache.remaining();
            if (remaining == 0 || remaining < size) {
               this.reallocatePoolBuffer();
               remaining = threadLocalCache.remaining();
            }

            return (ByteBuffer)this.allocateFromPool(threadLocalCache, remaining);
         }
      }
   }

   public ByteBuffer reallocateByteBuffer(ByteBuffer oldByteBuffer, int newSize) {
      if (oldByteBuffer.capacity() >= newSize) {
         return oldByteBuffer;
      } else {
         ThreadLocalPool memoryPool = getByteBufferThreadLocalPool();
         ByteBuffer newBuffer;
         if (memoryPool != null) {
            newBuffer = (ByteBuffer)memoryPool.reallocate(oldByteBuffer, newSize);
            if (newBuffer != null) {
               ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, newSize - oldByteBuffer.capacity());
               return newBuffer;
            }
         }

         newBuffer = this.allocateByteBuffer(newSize);
         oldByteBuffer.flip();
         return newBuffer.put(oldByteBuffer);
      }
   }

   public void releaseByteBuffer(ByteBuffer byteBuffer) {
      ThreadLocalPool memoryPool = getByteBufferThreadLocalPool();
      if (memoryPool != null && memoryPool.release((ByteBuffer)byteBuffer.clear())) {
         ProbeNotifier.notifyBufferReleasedToPool(this.monitoringConfig, byteBuffer.capacity());
      }

   }

   protected SmallByteBufferWrapper createSmallBuffer() {
      SmallByteBufferWrapper buffer = (SmallByteBufferWrapper)ThreadCache.takeFromCache(this.SMALL_BUFFER_CACHE_IDX);
      if (buffer != null) {
         ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, this.maxSmallBufferSize);
         return buffer;
      } else {
         return new SmallByteBufferWrapper(this.allocateByteBuffer0(this.maxSmallBufferSize));
      }
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.memory.jmx.ByteBufferManager", this, ByteBufferManager.class);
   }

   protected final ByteBuffer allocateByteBuffer0(int size) {
      ProbeNotifier.notifyBufferAllocated(this.monitoringConfig, size);
      return this.isDirect ? ByteBuffer.allocateDirect(size) : ByteBuffer.allocate(size);
   }

   private TrimAwareWrapper createTrimAwareBuffer(ByteBuffer underlyingByteBuffer) {
      TrimAwareWrapper buffer = (TrimAwareWrapper)ThreadCache.takeFromCache(CACHE_IDX);
      if (buffer != null) {
         buffer.visible = underlyingByteBuffer;
         return buffer;
      } else {
         return new TrimAwareWrapper(underlyingByteBuffer);
      }
   }

   private void reallocatePoolBuffer() {
      ByteBuffer byteBuffer = this.allocateByteBuffer0(this.maxBufferSize);
      ThreadLocalPool threadLocalCache = getByteBufferThreadLocalPool();
      if (threadLocalCache != null) {
         threadLocalCache.reset(byteBuffer);
      }

   }

   private static ByteBufferThreadLocalPool getByteBufferThreadLocalPool() {
      ThreadLocalPool pool = getThreadLocalPool();
      return pool instanceof ByteBufferThreadLocalPool ? (ByteBufferThreadLocalPool)pool : null;
   }

   protected final class SmallByteBufferWrapper extends ByteBufferWrapper implements Cacheable {
      private SmallByteBufferWrapper(ByteBuffer underlyingByteBuffer) {
         super(underlyingByteBuffer);
      }

      public void dispose() {
         super.prepareDispose();
         this.visible.clear();
         this.recycle();
      }

      public void recycle() {
         if (this.visible.remaining() == ByteBufferManager.this.maxSmallBufferSize) {
            this.allowBufferDispose = false;
            this.disposeStackTrace = null;
            if (ThreadCache.putToCache(ByteBufferManager.this.SMALL_BUFFER_CACHE_IDX, this)) {
               ProbeNotifier.notifyBufferReleasedToPool(ByteBufferManager.this.monitoringConfig, ByteBufferManager.this.maxSmallBufferSize);
            }
         }

      }

      protected ByteBufferWrapper wrapByteBuffer(ByteBuffer byteBuffer) {
         return ByteBufferManager.this.wrap(byteBuffer);
      }

      // $FF: synthetic method
      SmallByteBufferWrapper(ByteBuffer x1, Object x2) {
         this(x1);
      }
   }

   private final class TrimAwareWrapper extends ByteBufferWrapper implements AbstractMemoryManager.TrimAware {
      private TrimAwareWrapper(ByteBuffer underlyingByteBuffer) {
         super(underlyingByteBuffer);
      }

      public void trim() {
         int sizeToReturn = this.visible.capacity() - this.visible.position();
         if (sizeToReturn > 0) {
            ThreadLocalPool threadLocalCache = ByteBufferManager.getByteBufferThreadLocalPool();
            if (threadLocalCache != null) {
               if (threadLocalCache.isLastAllocated(this.visible)) {
                  this.visible.flip();
                  this.visible = this.visible.slice();
                  threadLocalCache.reduceLastAllocated(this.visible);
                  return;
               }

               if (threadLocalCache.wantReset(sizeToReturn)) {
                  this.visible.flip();
                  ByteBuffer originalByteBuffer = this.visible;
                  this.visible = this.visible.slice();
                  originalByteBuffer.position(originalByteBuffer.limit());
                  originalByteBuffer.limit(originalByteBuffer.capacity());
                  threadLocalCache.reset(originalByteBuffer);
                  return;
               }
            }
         }

         super.trim();
      }

      public void recycle() {
         this.allowBufferDispose = false;
         ThreadCache.putToCache(ByteBufferManager.CACHE_IDX, this);
      }

      public void dispose() {
         this.prepareDispose();
         ByteBufferManager.this.release((ByteBufferWrapper)this);
         this.visible = null;
         this.recycle();
      }

      protected ByteBufferWrapper wrapByteBuffer(ByteBuffer byteBuffer) {
         return ByteBufferManager.this.wrap(byteBuffer);
      }

      // $FF: synthetic method
      TrimAwareWrapper(ByteBuffer x1, Object x2) {
         this(x1);
      }
   }

   private static final class ByteBufferThreadLocalPool implements ThreadLocalPool {
      private ByteBuffer pool;
      private Object[] allocationHistory = new Object[8];
      private int lastAllocatedIndex;

      public ByteBufferThreadLocalPool() {
      }

      public void reset(ByteBuffer pool) {
         Arrays.fill(this.allocationHistory, 0, this.lastAllocatedIndex, (Object)null);
         this.lastAllocatedIndex = 0;
         this.pool = pool;
      }

      public ByteBuffer allocate(int size) {
         ByteBuffer allocated = Buffers.slice(this.pool, size);
         return this.addHistory(allocated);
      }

      public ByteBuffer reallocate(ByteBuffer oldByteBuffer, int newSize) {
         if (this.isLastAllocated(oldByteBuffer) && this.remaining() + oldByteBuffer.capacity() >= newSize) {
            --this.lastAllocatedIndex;
            this.pool.position(this.pool.position() - oldByteBuffer.capacity());
            ByteBuffer newByteBuffer = Buffers.slice(this.pool, newSize);
            newByteBuffer.position(oldByteBuffer.position());
            return this.addHistory(newByteBuffer);
         } else {
            return null;
         }
      }

      public boolean release(ByteBuffer underlyingBuffer) {
         if (this.isLastAllocated(underlyingBuffer)) {
            this.pool.position(this.pool.position() - underlyingBuffer.capacity());
            this.allocationHistory[--this.lastAllocatedIndex] = null;
            return true;
         } else if (this.wantReset(underlyingBuffer.capacity())) {
            this.reset(underlyingBuffer);
            return true;
         } else {
            return false;
         }
      }

      public boolean wantReset(int size) {
         return !this.hasRemaining() || this.lastAllocatedIndex == 0 && this.pool.remaining() < size;
      }

      public boolean isLastAllocated(ByteBuffer oldByteBuffer) {
         return this.lastAllocatedIndex > 0 && this.allocationHistory[this.lastAllocatedIndex - 1] == oldByteBuffer;
      }

      public ByteBuffer reduceLastAllocated(ByteBuffer byteBuffer) {
         ByteBuffer oldLastAllocated = (ByteBuffer)this.allocationHistory[this.lastAllocatedIndex - 1];
         this.pool.position(this.pool.position() - (oldLastAllocated.capacity() - byteBuffer.capacity()));
         this.allocationHistory[this.lastAllocatedIndex - 1] = byteBuffer;
         return oldLastAllocated;
      }

      public int remaining() {
         return this.pool != null ? this.pool.remaining() : 0;
      }

      public boolean hasRemaining() {
         return this.remaining() > 0;
      }

      private ByteBuffer addHistory(ByteBuffer allocated) {
         if (this.lastAllocatedIndex >= this.allocationHistory.length) {
            this.allocationHistory = Arrays.copyOf(this.allocationHistory, this.allocationHistory.length * 3 / 2 + 1);
         }

         this.allocationHistory[this.lastAllocatedIndex++] = allocated;
         return allocated;
      }

      public String toString() {
         return "(pool=" + this.pool + " last-allocated-index=" + (this.lastAllocatedIndex - 1) + " allocation-history=" + Arrays.toString(this.allocationHistory) + ')';
      }
   }
}
