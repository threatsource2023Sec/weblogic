package org.python.netty.buffer;

import java.util.Queue;
import org.python.netty.util.Recycler;
import org.python.netty.util.ThreadDeathWatcher;
import org.python.netty.util.internal.MathUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

final class PoolThreadCache {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PoolThreadCache.class);
   final PoolArena heapArena;
   final PoolArena directArena;
   private final MemoryRegionCache[] tinySubPageHeapCaches;
   private final MemoryRegionCache[] smallSubPageHeapCaches;
   private final MemoryRegionCache[] tinySubPageDirectCaches;
   private final MemoryRegionCache[] smallSubPageDirectCaches;
   private final MemoryRegionCache[] normalHeapCaches;
   private final MemoryRegionCache[] normalDirectCaches;
   private final int numShiftsNormalDirect;
   private final int numShiftsNormalHeap;
   private final int freeSweepAllocationThreshold;
   private final Thread deathWatchThread;
   private final Runnable freeTask;
   private int allocations;

   PoolThreadCache(PoolArena heapArena, PoolArena directArena, int tinyCacheSize, int smallCacheSize, int normalCacheSize, int maxCachedBufferCapacity, int freeSweepAllocationThreshold) {
      if (maxCachedBufferCapacity < 0) {
         throw new IllegalArgumentException("maxCachedBufferCapacity: " + maxCachedBufferCapacity + " (expected: >= 0)");
      } else if (freeSweepAllocationThreshold < 1) {
         throw new IllegalArgumentException("freeSweepAllocationThreshold: " + freeSweepAllocationThreshold + " (expected: > 0)");
      } else {
         this.freeSweepAllocationThreshold = freeSweepAllocationThreshold;
         this.heapArena = heapArena;
         this.directArena = directArena;
         if (directArena != null) {
            this.tinySubPageDirectCaches = createSubPageCaches(tinyCacheSize, 32, PoolArena.SizeClass.Tiny);
            this.smallSubPageDirectCaches = createSubPageCaches(smallCacheSize, directArena.numSmallSubpagePools, PoolArena.SizeClass.Small);
            this.numShiftsNormalDirect = log2(directArena.pageSize);
            this.normalDirectCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, directArena);
            directArena.numThreadCaches.getAndIncrement();
         } else {
            this.tinySubPageDirectCaches = null;
            this.smallSubPageDirectCaches = null;
            this.normalDirectCaches = null;
            this.numShiftsNormalDirect = -1;
         }

         if (heapArena != null) {
            this.tinySubPageHeapCaches = createSubPageCaches(tinyCacheSize, 32, PoolArena.SizeClass.Tiny);
            this.smallSubPageHeapCaches = createSubPageCaches(smallCacheSize, heapArena.numSmallSubpagePools, PoolArena.SizeClass.Small);
            this.numShiftsNormalHeap = log2(heapArena.pageSize);
            this.normalHeapCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, heapArena);
            heapArena.numThreadCaches.getAndIncrement();
         } else {
            this.tinySubPageHeapCaches = null;
            this.smallSubPageHeapCaches = null;
            this.normalHeapCaches = null;
            this.numShiftsNormalHeap = -1;
         }

         if (this.tinySubPageDirectCaches == null && this.smallSubPageDirectCaches == null && this.normalDirectCaches == null && this.tinySubPageHeapCaches == null && this.smallSubPageHeapCaches == null && this.normalHeapCaches == null) {
            this.freeTask = null;
            this.deathWatchThread = null;
         } else {
            this.freeTask = new Runnable() {
               public void run() {
                  PoolThreadCache.this.free0();
               }
            };
            this.deathWatchThread = Thread.currentThread();
            ThreadDeathWatcher.watch(this.deathWatchThread, this.freeTask);
         }

      }
   }

   private static MemoryRegionCache[] createSubPageCaches(int cacheSize, int numCaches, PoolArena.SizeClass sizeClass) {
      if (cacheSize <= 0) {
         return null;
      } else {
         MemoryRegionCache[] cache = new MemoryRegionCache[numCaches];

         for(int i = 0; i < cache.length; ++i) {
            cache[i] = new SubPageMemoryRegionCache(cacheSize, sizeClass);
         }

         return cache;
      }
   }

   private static MemoryRegionCache[] createNormalCaches(int cacheSize, int maxCachedBufferCapacity, PoolArena area) {
      if (cacheSize <= 0) {
         return null;
      } else {
         int max = Math.min(area.chunkSize, maxCachedBufferCapacity);
         int arraySize = Math.max(1, log2(max / area.pageSize) + 1);
         MemoryRegionCache[] cache = new MemoryRegionCache[arraySize];

         for(int i = 0; i < cache.length; ++i) {
            cache[i] = new NormalMemoryRegionCache(cacheSize);
         }

         return cache;
      }
   }

   private static int log2(int val) {
      int res;
      for(res = 0; val > 1; ++res) {
         val >>= 1;
      }

      return res;
   }

   boolean allocateTiny(PoolArena area, PooledByteBuf buf, int reqCapacity, int normCapacity) {
      return this.allocate(this.cacheForTiny(area, normCapacity), buf, reqCapacity);
   }

   boolean allocateSmall(PoolArena area, PooledByteBuf buf, int reqCapacity, int normCapacity) {
      return this.allocate(this.cacheForSmall(area, normCapacity), buf, reqCapacity);
   }

   boolean allocateNormal(PoolArena area, PooledByteBuf buf, int reqCapacity, int normCapacity) {
      return this.allocate(this.cacheForNormal(area, normCapacity), buf, reqCapacity);
   }

   private boolean allocate(MemoryRegionCache cache, PooledByteBuf buf, int reqCapacity) {
      if (cache == null) {
         return false;
      } else {
         boolean allocated = cache.allocate(buf, reqCapacity);
         if (++this.allocations >= this.freeSweepAllocationThreshold) {
            this.allocations = 0;
            this.trim();
         }

         return allocated;
      }
   }

   boolean add(PoolArena area, PoolChunk chunk, long handle, int normCapacity, PoolArena.SizeClass sizeClass) {
      MemoryRegionCache cache = this.cache(area, normCapacity, sizeClass);
      return cache == null ? false : cache.add(chunk, handle);
   }

   private MemoryRegionCache cache(PoolArena area, int normCapacity, PoolArena.SizeClass sizeClass) {
      switch (sizeClass) {
         case Normal:
            return this.cacheForNormal(area, normCapacity);
         case Small:
            return this.cacheForSmall(area, normCapacity);
         case Tiny:
            return this.cacheForTiny(area, normCapacity);
         default:
            throw new Error();
      }
   }

   void free() {
      if (this.freeTask != null) {
         assert this.deathWatchThread != null;

         ThreadDeathWatcher.unwatch(this.deathWatchThread, this.freeTask);
      }

      this.free0();
   }

   private void free0() {
      int numFreed = free(this.tinySubPageDirectCaches) + free(this.smallSubPageDirectCaches) + free(this.normalDirectCaches) + free(this.tinySubPageHeapCaches) + free(this.smallSubPageHeapCaches) + free(this.normalHeapCaches);
      if (numFreed > 0 && logger.isDebugEnabled()) {
         logger.debug("Freed {} thread-local buffer(s) from thread: {}", numFreed, Thread.currentThread().getName());
      }

      if (this.directArena != null) {
         this.directArena.numThreadCaches.getAndDecrement();
      }

      if (this.heapArena != null) {
         this.heapArena.numThreadCaches.getAndDecrement();
      }

   }

   private static int free(MemoryRegionCache[] caches) {
      if (caches == null) {
         return 0;
      } else {
         int numFreed = 0;
         MemoryRegionCache[] var2 = caches;
         int var3 = caches.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MemoryRegionCache c = var2[var4];
            numFreed += free(c);
         }

         return numFreed;
      }
   }

   private static int free(MemoryRegionCache cache) {
      return cache == null ? 0 : cache.free();
   }

   void trim() {
      trim(this.tinySubPageDirectCaches);
      trim(this.smallSubPageDirectCaches);
      trim(this.normalDirectCaches);
      trim(this.tinySubPageHeapCaches);
      trim(this.smallSubPageHeapCaches);
      trim(this.normalHeapCaches);
   }

   private static void trim(MemoryRegionCache[] caches) {
      if (caches != null) {
         MemoryRegionCache[] var1 = caches;
         int var2 = caches.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            MemoryRegionCache c = var1[var3];
            trim(c);
         }

      }
   }

   private static void trim(MemoryRegionCache cache) {
      if (cache != null) {
         cache.trim();
      }
   }

   private MemoryRegionCache cacheForTiny(PoolArena area, int normCapacity) {
      int idx = PoolArena.tinyIdx(normCapacity);
      return area.isDirect() ? cache(this.tinySubPageDirectCaches, idx) : cache(this.tinySubPageHeapCaches, idx);
   }

   private MemoryRegionCache cacheForSmall(PoolArena area, int normCapacity) {
      int idx = PoolArena.smallIdx(normCapacity);
      return area.isDirect() ? cache(this.smallSubPageDirectCaches, idx) : cache(this.smallSubPageHeapCaches, idx);
   }

   private MemoryRegionCache cacheForNormal(PoolArena area, int normCapacity) {
      int idx;
      if (area.isDirect()) {
         idx = log2(normCapacity >> this.numShiftsNormalDirect);
         return cache(this.normalDirectCaches, idx);
      } else {
         idx = log2(normCapacity >> this.numShiftsNormalHeap);
         return cache(this.normalHeapCaches, idx);
      }
   }

   private static MemoryRegionCache cache(MemoryRegionCache[] cache, int idx) {
      return cache != null && idx <= cache.length - 1 ? cache[idx] : null;
   }

   private abstract static class MemoryRegionCache {
      private final int size;
      private final Queue queue;
      private final PoolArena.SizeClass sizeClass;
      private int allocations;
      private static final Recycler RECYCLER = new Recycler() {
         protected Entry newObject(Recycler.Handle handle) {
            return new Entry(handle);
         }
      };

      MemoryRegionCache(int size, PoolArena.SizeClass sizeClass) {
         this.size = MathUtil.safeFindNextPositivePowerOfTwo(size);
         this.queue = PlatformDependent.newFixedMpscQueue(this.size);
         this.sizeClass = sizeClass;
      }

      protected abstract void initBuf(PoolChunk var1, long var2, PooledByteBuf var4, int var5);

      public final boolean add(PoolChunk chunk, long handle) {
         Entry entry = newEntry(chunk, handle);
         boolean queued = this.queue.offer(entry);
         if (!queued) {
            entry.recycle();
         }

         return queued;
      }

      public final boolean allocate(PooledByteBuf buf, int reqCapacity) {
         Entry entry = (Entry)this.queue.poll();
         if (entry == null) {
            return false;
         } else {
            this.initBuf(entry.chunk, entry.handle, buf, reqCapacity);
            entry.recycle();
            ++this.allocations;
            return true;
         }
      }

      public final int free() {
         return this.free(Integer.MAX_VALUE);
      }

      private int free(int max) {
         int numFreed;
         for(numFreed = 0; numFreed < max; ++numFreed) {
            Entry entry = (Entry)this.queue.poll();
            if (entry == null) {
               return numFreed;
            }

            this.freeEntry(entry);
         }

         return numFreed;
      }

      public final void trim() {
         int free = this.size - this.allocations;
         this.allocations = 0;
         if (free > 0) {
            this.free(free);
         }

      }

      private void freeEntry(Entry entry) {
         PoolChunk chunk = entry.chunk;
         long handle = entry.handle;
         entry.recycle();
         chunk.arena.freeChunk(chunk, handle, this.sizeClass);
      }

      private static Entry newEntry(PoolChunk chunk, long handle) {
         Entry entry = (Entry)RECYCLER.get();
         entry.chunk = chunk;
         entry.handle = handle;
         return entry;
      }

      static final class Entry {
         final Recycler.Handle recyclerHandle;
         PoolChunk chunk;
         long handle = -1L;

         Entry(Recycler.Handle recyclerHandle) {
            this.recyclerHandle = recyclerHandle;
         }

         void recycle() {
            this.chunk = null;
            this.handle = -1L;
            this.recyclerHandle.recycle(this);
         }
      }
   }

   private static final class NormalMemoryRegionCache extends MemoryRegionCache {
      NormalMemoryRegionCache(int size) {
         super(size, PoolArena.SizeClass.Normal);
      }

      protected void initBuf(PoolChunk chunk, long handle, PooledByteBuf buf, int reqCapacity) {
         chunk.initBuf(buf, handle, reqCapacity);
      }
   }

   private static final class SubPageMemoryRegionCache extends MemoryRegionCache {
      SubPageMemoryRegionCache(int size, PoolArena.SizeClass sizeClass) {
         super(size, sizeClass);
      }

      protected void initBuf(PoolChunk chunk, long handle, PooledByteBuf buf, int reqCapacity) {
         chunk.initBufWithSubpage(buf, handle, reqCapacity);
      }
   }
}
