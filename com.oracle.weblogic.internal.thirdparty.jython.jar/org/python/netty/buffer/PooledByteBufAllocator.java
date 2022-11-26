package org.python.netty.buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.python.netty.util.NettyRuntime;
import org.python.netty.util.concurrent.FastThreadLocal;
import org.python.netty.util.concurrent.FastThreadLocalThread;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class PooledByteBufAllocator extends AbstractByteBufAllocator implements ByteBufAllocatorMetricProvider {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
   private static final int DEFAULT_NUM_HEAP_ARENA;
   private static final int DEFAULT_NUM_DIRECT_ARENA;
   private static final int DEFAULT_PAGE_SIZE;
   private static final int DEFAULT_MAX_ORDER;
   private static final int DEFAULT_TINY_CACHE_SIZE;
   private static final int DEFAULT_SMALL_CACHE_SIZE;
   private static final int DEFAULT_NORMAL_CACHE_SIZE;
   private static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
   private static final int DEFAULT_CACHE_TRIM_INTERVAL;
   private static final boolean DEFAULT_USE_CACHE_FOR_ALL_THREADS;
   private static final int DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT;
   private static final int MIN_PAGE_SIZE = 4096;
   private static final int MAX_CHUNK_SIZE = 1073741824;
   public static final PooledByteBufAllocator DEFAULT;
   private final PoolArena[] heapArenas;
   private final PoolArena[] directArenas;
   private final int tinyCacheSize;
   private final int smallCacheSize;
   private final int normalCacheSize;
   private final List heapArenaMetrics;
   private final List directArenaMetrics;
   private final PoolThreadLocalCache threadCache;
   private final int chunkSize;
   private final PooledByteBufAllocatorMetric metric;

   public PooledByteBufAllocator() {
      this(false);
   }

   public PooledByteBufAllocator(boolean preferDirect) {
      this(preferDirect, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
   }

   public PooledByteBufAllocator(int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
      this(false, nHeapArena, nDirectArena, pageSize, maxOrder);
   }

   /** @deprecated */
   @Deprecated
   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
      this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, DEFAULT_TINY_CACHE_SIZE, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
   }

   /** @deprecated */
   @Deprecated
   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize) {
      this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, tinyCacheSize, smallCacheSize, normalCacheSize, DEFAULT_USE_CACHE_FOR_ALL_THREADS, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
   }

   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize, boolean useCacheForAllThreads) {
      this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, tinyCacheSize, smallCacheSize, normalCacheSize, useCacheForAllThreads, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
   }

   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize, boolean useCacheForAllThreads, int directMemoryCacheAlignment) {
      super(preferDirect);
      this.threadCache = new PoolThreadLocalCache(useCacheForAllThreads);
      this.tinyCacheSize = tinyCacheSize;
      this.smallCacheSize = smallCacheSize;
      this.normalCacheSize = normalCacheSize;
      this.chunkSize = validateAndCalculateChunkSize(pageSize, maxOrder);
      if (nHeapArena < 0) {
         throw new IllegalArgumentException("nHeapArena: " + nHeapArena + " (expected: >= 0)");
      } else if (nDirectArena < 0) {
         throw new IllegalArgumentException("nDirectArea: " + nDirectArena + " (expected: >= 0)");
      } else if (directMemoryCacheAlignment < 0) {
         throw new IllegalArgumentException("directMemoryCacheAlignment: " + directMemoryCacheAlignment + " (expected: >= 0)");
      } else if (directMemoryCacheAlignment > 0 && !isDirectMemoryCacheAlignmentSupported()) {
         throw new IllegalArgumentException("directMemoryCacheAlignment is not supported");
      } else if ((directMemoryCacheAlignment & -directMemoryCacheAlignment) != directMemoryCacheAlignment) {
         throw new IllegalArgumentException("directMemoryCacheAlignment: " + directMemoryCacheAlignment + " (expected: power of two)");
      } else {
         int pageShifts = validateAndCalculatePageShifts(pageSize);
         ArrayList metrics;
         int i;
         if (nHeapArena > 0) {
            this.heapArenas = newArenaArray(nHeapArena);
            metrics = new ArrayList(this.heapArenas.length);

            for(i = 0; i < this.heapArenas.length; ++i) {
               PoolArena.HeapArena arena = new PoolArena.HeapArena(this, pageSize, maxOrder, pageShifts, this.chunkSize, directMemoryCacheAlignment);
               this.heapArenas[i] = arena;
               metrics.add(arena);
            }

            this.heapArenaMetrics = Collections.unmodifiableList(metrics);
         } else {
            this.heapArenas = null;
            this.heapArenaMetrics = Collections.emptyList();
         }

         if (nDirectArena > 0) {
            this.directArenas = newArenaArray(nDirectArena);
            metrics = new ArrayList(this.directArenas.length);

            for(i = 0; i < this.directArenas.length; ++i) {
               PoolArena.DirectArena arena = new PoolArena.DirectArena(this, pageSize, maxOrder, pageShifts, this.chunkSize, directMemoryCacheAlignment);
               this.directArenas[i] = arena;
               metrics.add(arena);
            }

            this.directArenaMetrics = Collections.unmodifiableList(metrics);
         } else {
            this.directArenas = null;
            this.directArenaMetrics = Collections.emptyList();
         }

         this.metric = new PooledByteBufAllocatorMetric(this);
      }
   }

   private static PoolArena[] newArenaArray(int size) {
      return new PoolArena[size];
   }

   private static int validateAndCalculatePageShifts(int pageSize) {
      if (pageSize < 4096) {
         throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: " + 4096 + ")");
      } else if ((pageSize & pageSize - 1) != 0) {
         throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: power of 2)");
      } else {
         return 31 - Integer.numberOfLeadingZeros(pageSize);
      }
   }

   private static int validateAndCalculateChunkSize(int pageSize, int maxOrder) {
      if (maxOrder > 14) {
         throw new IllegalArgumentException("maxOrder: " + maxOrder + " (expected: 0-14)");
      } else {
         int chunkSize = pageSize;

         for(int i = maxOrder; i > 0; --i) {
            if (chunkSize > 536870912) {
               throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", pageSize, maxOrder, 1073741824));
            }

            chunkSize <<= 1;
         }

         return chunkSize;
      }
   }

   protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
      PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
      PoolArena heapArena = cache.heapArena;
      Object buf;
      if (heapArena != null) {
         buf = heapArena.allocate(cache, initialCapacity, maxCapacity);
      } else {
         buf = PlatformDependent.hasUnsafe() ? new UnpooledUnsafeHeapByteBuf(this, initialCapacity, maxCapacity) : new UnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
      }

      return toLeakAwareBuffer((ByteBuf)buf);
   }

   protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
      PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
      PoolArena directArena = cache.directArena;
      Object buf;
      if (directArena != null) {
         buf = directArena.allocate(cache, initialCapacity, maxCapacity);
      } else {
         buf = PlatformDependent.hasUnsafe() ? UnsafeByteBufUtil.newUnsafeDirectByteBuf(this, initialCapacity, maxCapacity) : new UnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
      }

      return toLeakAwareBuffer((ByteBuf)buf);
   }

   public static int defaultNumHeapArena() {
      return DEFAULT_NUM_HEAP_ARENA;
   }

   public static int defaultNumDirectArena() {
      return DEFAULT_NUM_DIRECT_ARENA;
   }

   public static int defaultPageSize() {
      return DEFAULT_PAGE_SIZE;
   }

   public static int defaultMaxOrder() {
      return DEFAULT_MAX_ORDER;
   }

   public static int defaultTinyCacheSize() {
      return DEFAULT_TINY_CACHE_SIZE;
   }

   public static int defaultSmallCacheSize() {
      return DEFAULT_SMALL_CACHE_SIZE;
   }

   public static int defaultNormalCacheSize() {
      return DEFAULT_NORMAL_CACHE_SIZE;
   }

   public static boolean isDirectMemoryCacheAlignmentSupported() {
      return PlatformDependent.hasUnsafe();
   }

   public boolean isDirectBufferPooled() {
      return this.directArenas != null;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasThreadLocalCache() {
      return this.threadCache.isSet();
   }

   /** @deprecated */
   @Deprecated
   public void freeThreadLocalCache() {
      this.threadCache.remove();
   }

   public PooledByteBufAllocatorMetric metric() {
      return this.metric;
   }

   /** @deprecated */
   @Deprecated
   public int numHeapArenas() {
      return this.heapArenaMetrics.size();
   }

   /** @deprecated */
   @Deprecated
   public int numDirectArenas() {
      return this.directArenaMetrics.size();
   }

   /** @deprecated */
   @Deprecated
   public List heapArenas() {
      return this.heapArenaMetrics;
   }

   /** @deprecated */
   @Deprecated
   public List directArenas() {
      return this.directArenaMetrics;
   }

   /** @deprecated */
   @Deprecated
   public int numThreadLocalCaches() {
      PoolArena[] arenas = this.heapArenas != null ? this.heapArenas : this.directArenas;
      if (arenas == null) {
         return 0;
      } else {
         int total = 0;
         PoolArena[] var3 = arenas;
         int var4 = arenas.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PoolArena arena = var3[var5];
            total += arena.numThreadCaches.get();
         }

         return total;
      }
   }

   /** @deprecated */
   @Deprecated
   public int tinyCacheSize() {
      return this.tinyCacheSize;
   }

   /** @deprecated */
   @Deprecated
   public int smallCacheSize() {
      return this.smallCacheSize;
   }

   /** @deprecated */
   @Deprecated
   public int normalCacheSize() {
      return this.normalCacheSize;
   }

   /** @deprecated */
   @Deprecated
   public final int chunkSize() {
      return this.chunkSize;
   }

   final long usedHeapMemory() {
      return usedMemory(this.heapArenas);
   }

   final long usedDirectMemory() {
      return usedMemory(this.directArenas);
   }

   private static long usedMemory(PoolArena... arenas) {
      if (arenas == null) {
         return -1L;
      } else {
         long used = 0L;
         PoolArena[] var3 = arenas;
         int var4 = arenas.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PoolArena arena = var3[var5];
            used += arena.numActiveBytes();
            if (used < 0L) {
               return Long.MAX_VALUE;
            }
         }

         return used;
      }
   }

   final PoolThreadCache threadCache() {
      return (PoolThreadCache)this.threadCache.get();
   }

   public String dumpStats() {
      int heapArenasLen = this.heapArenas == null ? 0 : this.heapArenas.length;
      StringBuilder buf = (new StringBuilder(512)).append(heapArenasLen).append(" heap arena(s):").append(StringUtil.NEWLINE);
      int var5;
      if (heapArenasLen > 0) {
         PoolArena[] var3 = this.heapArenas;
         int var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            PoolArena a = var3[var5];
            buf.append(a);
         }
      }

      int directArenasLen = this.directArenas == null ? 0 : this.directArenas.length;
      buf.append(directArenasLen).append(" direct arena(s):").append(StringUtil.NEWLINE);
      if (directArenasLen > 0) {
         PoolArena[] var9 = this.directArenas;
         var5 = var9.length;

         for(int var10 = 0; var10 < var5; ++var10) {
            PoolArena a = var9[var10];
            buf.append(a);
         }
      }

      return buf.toString();
   }

   static {
      int defaultPageSize = SystemPropertyUtil.getInt("org.python.netty.allocator.pageSize", 8192);
      Throwable pageSizeFallbackCause = null;

      try {
         validateAndCalculatePageShifts(defaultPageSize);
      } catch (Throwable var8) {
         pageSizeFallbackCause = var8;
         defaultPageSize = 8192;
      }

      DEFAULT_PAGE_SIZE = defaultPageSize;
      int defaultMaxOrder = SystemPropertyUtil.getInt("org.python.netty.allocator.maxOrder", 11);
      Throwable maxOrderFallbackCause = null;

      try {
         validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, defaultMaxOrder);
      } catch (Throwable var7) {
         maxOrderFallbackCause = var7;
         defaultMaxOrder = 11;
      }

      DEFAULT_MAX_ORDER = defaultMaxOrder;
      Runtime runtime = Runtime.getRuntime();
      int defaultMinNumArena = NettyRuntime.availableProcessors() * 2;
      int defaultChunkSize = DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER;
      DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("org.python.netty.allocator.numHeapArenas", (int)Math.min((long)defaultMinNumArena, runtime.maxMemory() / (long)defaultChunkSize / 2L / 3L)));
      DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("org.python.netty.allocator.numDirectArenas", (int)Math.min((long)defaultMinNumArena, PlatformDependent.maxDirectMemory() / (long)defaultChunkSize / 2L / 3L)));
      DEFAULT_TINY_CACHE_SIZE = SystemPropertyUtil.getInt("org.python.netty.allocator.tinyCacheSize", 512);
      DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("org.python.netty.allocator.smallCacheSize", 256);
      DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("org.python.netty.allocator.normalCacheSize", 64);
      DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("org.python.netty.allocator.maxCachedBufferCapacity", 32768);
      DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("org.python.netty.allocator.cacheTrimInterval", 8192);
      DEFAULT_USE_CACHE_FOR_ALL_THREADS = SystemPropertyUtil.getBoolean("org.python.netty.allocator.useCacheForAllThreads", true);
      DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT = SystemPropertyUtil.getInt("org.python.netty.allocator.directMemoryCacheAlignment", 0);
      if (logger.isDebugEnabled()) {
         logger.debug("-Dio.netty.allocator.numHeapArenas: {}", (Object)DEFAULT_NUM_HEAP_ARENA);
         logger.debug("-Dio.netty.allocator.numDirectArenas: {}", (Object)DEFAULT_NUM_DIRECT_ARENA);
         if (pageSizeFallbackCause == null) {
            logger.debug("-Dio.netty.allocator.pageSize: {}", (Object)DEFAULT_PAGE_SIZE);
         } else {
            logger.debug("-Dio.netty.allocator.pageSize: {}", DEFAULT_PAGE_SIZE, pageSizeFallbackCause);
         }

         if (maxOrderFallbackCause == null) {
            logger.debug("-Dio.netty.allocator.maxOrder: {}", (Object)DEFAULT_MAX_ORDER);
         } else {
            logger.debug("-Dio.netty.allocator.maxOrder: {}", DEFAULT_MAX_ORDER, maxOrderFallbackCause);
         }

         logger.debug("-Dio.netty.allocator.chunkSize: {}", (Object)(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
         logger.debug("-Dio.netty.allocator.tinyCacheSize: {}", (Object)DEFAULT_TINY_CACHE_SIZE);
         logger.debug("-Dio.netty.allocator.smallCacheSize: {}", (Object)DEFAULT_SMALL_CACHE_SIZE);
         logger.debug("-Dio.netty.allocator.normalCacheSize: {}", (Object)DEFAULT_NORMAL_CACHE_SIZE);
         logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", (Object)DEFAULT_MAX_CACHED_BUFFER_CAPACITY);
         logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", (Object)DEFAULT_CACHE_TRIM_INTERVAL);
         logger.debug("-Dio.netty.allocator.useCacheForAllThreads: {}", (Object)DEFAULT_USE_CACHE_FOR_ALL_THREADS);
      }

      DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
   }

   final class PoolThreadLocalCache extends FastThreadLocal {
      private final boolean useCacheForAllThreads;

      PoolThreadLocalCache(boolean useCacheForAllThreads) {
         this.useCacheForAllThreads = useCacheForAllThreads;
      }

      protected synchronized PoolThreadCache initialValue() {
         PoolArena heapArena = this.leastUsedArena(PooledByteBufAllocator.this.heapArenas);
         PoolArena directArena = this.leastUsedArena(PooledByteBufAllocator.this.directArenas);
         return !this.useCacheForAllThreads && !(Thread.currentThread() instanceof FastThreadLocalThread) ? new PoolThreadCache(heapArena, directArena, 0, 0, 0, 0, 0) : new PoolThreadCache(heapArena, directArena, PooledByteBufAllocator.this.tinyCacheSize, PooledByteBufAllocator.this.smallCacheSize, PooledByteBufAllocator.this.normalCacheSize, PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL);
      }

      protected void onRemoval(PoolThreadCache threadCache) {
         threadCache.free();
      }

      private PoolArena leastUsedArena(PoolArena[] arenas) {
         if (arenas != null && arenas.length != 0) {
            PoolArena minArena = arenas[0];

            for(int i = 1; i < arenas.length; ++i) {
               PoolArena arena = arenas[i];
               if (arena.numThreadCaches.get() < minArena.numThreadCaches.get()) {
                  minArena = arena;
               }
            }

            return minArena;
         } else {
            return null;
         }
      }
   }
}
