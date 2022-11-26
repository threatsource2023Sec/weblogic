package org.python.google.common.cache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Equivalence;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Stopwatch;
import org.python.google.common.base.Ticker;
import org.python.google.common.collect.AbstractSequentialIterator;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Iterators;
import org.python.google.common.collect.Maps;
import org.python.google.common.collect.Sets;
import org.python.google.common.primitives.Ints;
import org.python.google.common.util.concurrent.ExecutionError;
import org.python.google.common.util.concurrent.Futures;
import org.python.google.common.util.concurrent.ListenableFuture;
import org.python.google.common.util.concurrent.MoreExecutors;
import org.python.google.common.util.concurrent.SettableFuture;
import org.python.google.common.util.concurrent.UncheckedExecutionException;
import org.python.google.common.util.concurrent.Uninterruptibles;
import org.python.google.j2objc.annotations.Weak;

@GwtCompatible(
   emulated = true
)
class LocalCache extends AbstractMap implements ConcurrentMap {
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final int MAX_SEGMENTS = 65536;
   static final int CONTAINS_VALUE_RETRIES = 3;
   static final int DRAIN_THRESHOLD = 63;
   static final int DRAIN_MAX = 16;
   static final Logger logger = Logger.getLogger(LocalCache.class.getName());
   final int segmentMask;
   final int segmentShift;
   final Segment[] segments;
   final int concurrencyLevel;
   final Equivalence keyEquivalence;
   final Equivalence valueEquivalence;
   final Strength keyStrength;
   final Strength valueStrength;
   final long maxWeight;
   final Weigher weigher;
   final long expireAfterAccessNanos;
   final long expireAfterWriteNanos;
   final long refreshNanos;
   final Queue removalNotificationQueue;
   final RemovalListener removalListener;
   final Ticker ticker;
   final EntryFactory entryFactory;
   final AbstractCache.StatsCounter globalStatsCounter;
   @Nullable
   final CacheLoader defaultLoader;
   static final ValueReference UNSET = new ValueReference() {
      public Object get() {
         return null;
      }

      public int getWeight() {
         return 0;
      }

      public ReferenceEntry getEntry() {
         return null;
      }

      public ValueReference copyFor(ReferenceQueue queue, @Nullable Object value, ReferenceEntry entry) {
         return this;
      }

      public boolean isLoading() {
         return false;
      }

      public boolean isActive() {
         return false;
      }

      public Object waitForValue() {
         return null;
      }

      public void notifyNewValue(Object newValue) {
      }
   };
   static final Queue DISCARDING_QUEUE = new AbstractQueue() {
      public boolean offer(Object o) {
         return true;
      }

      public Object peek() {
         return null;
      }

      public Object poll() {
         return null;
      }

      public int size() {
         return 0;
      }

      public Iterator iterator() {
         return ImmutableSet.of().iterator();
      }
   };
   Set keySet;
   Collection values;
   Set entrySet;

   LocalCache(CacheBuilder builder, @Nullable CacheLoader loader) {
      this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
      this.keyStrength = builder.getKeyStrength();
      this.valueStrength = builder.getValueStrength();
      this.keyEquivalence = builder.getKeyEquivalence();
      this.valueEquivalence = builder.getValueEquivalence();
      this.maxWeight = builder.getMaximumWeight();
      this.weigher = builder.getWeigher();
      this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
      this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
      this.refreshNanos = builder.getRefreshNanos();
      this.removalListener = builder.getRemovalListener();
      this.removalNotificationQueue = (Queue)(this.removalListener == CacheBuilder.NullListener.INSTANCE ? discardingQueue() : new ConcurrentLinkedQueue());
      this.ticker = builder.getTicker(this.recordsTime());
      this.entryFactory = LocalCache.EntryFactory.getFactory(this.keyStrength, this.usesAccessEntries(), this.usesWriteEntries());
      this.globalStatsCounter = (AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get();
      this.defaultLoader = loader;
      int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
      if (this.evictsBySize() && !this.customWeigher()) {
         initialCapacity = Math.min(initialCapacity, (int)this.maxWeight);
      }

      int segmentShift = 0;

      int segmentCount;
      for(segmentCount = 1; segmentCount < this.concurrencyLevel && (!this.evictsBySize() || (long)(segmentCount * 20) <= this.maxWeight); segmentCount <<= 1) {
         ++segmentShift;
      }

      this.segmentShift = 32 - segmentShift;
      this.segmentMask = segmentCount - 1;
      this.segments = this.newSegmentArray(segmentCount);
      int segmentCapacity = initialCapacity / segmentCount;
      if (segmentCapacity * segmentCount < initialCapacity) {
         ++segmentCapacity;
      }

      int segmentSize;
      for(segmentSize = 1; segmentSize < segmentCapacity; segmentSize <<= 1) {
      }

      if (this.evictsBySize()) {
         long maxSegmentWeight = this.maxWeight / (long)segmentCount + 1L;
         long remainder = this.maxWeight % (long)segmentCount;

         for(int i = 0; i < this.segments.length; ++i) {
            if ((long)i == remainder) {
               --maxSegmentWeight;
            }

            this.segments[i] = this.createSegment(segmentSize, maxSegmentWeight, (AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get());
         }
      } else {
         for(int i = 0; i < this.segments.length; ++i) {
            this.segments[i] = this.createSegment(segmentSize, -1L, (AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get());
         }
      }

   }

   boolean evictsBySize() {
      return this.maxWeight >= 0L;
   }

   boolean customWeigher() {
      return this.weigher != CacheBuilder.OneWeigher.INSTANCE;
   }

   boolean expires() {
      return this.expiresAfterWrite() || this.expiresAfterAccess();
   }

   boolean expiresAfterWrite() {
      return this.expireAfterWriteNanos > 0L;
   }

   boolean expiresAfterAccess() {
      return this.expireAfterAccessNanos > 0L;
   }

   boolean refreshes() {
      return this.refreshNanos > 0L;
   }

   boolean usesAccessQueue() {
      return this.expiresAfterAccess() || this.evictsBySize();
   }

   boolean usesWriteQueue() {
      return this.expiresAfterWrite();
   }

   boolean recordsWrite() {
      return this.expiresAfterWrite() || this.refreshes();
   }

   boolean recordsAccess() {
      return this.expiresAfterAccess();
   }

   boolean recordsTime() {
      return this.recordsWrite() || this.recordsAccess();
   }

   boolean usesWriteEntries() {
      return this.usesWriteQueue() || this.recordsWrite();
   }

   boolean usesAccessEntries() {
      return this.usesAccessQueue() || this.recordsAccess();
   }

   boolean usesKeyReferences() {
      return this.keyStrength != LocalCache.Strength.STRONG;
   }

   boolean usesValueReferences() {
      return this.valueStrength != LocalCache.Strength.STRONG;
   }

   static ValueReference unset() {
      return UNSET;
   }

   static ReferenceEntry nullEntry() {
      return LocalCache.NullEntry.INSTANCE;
   }

   static Queue discardingQueue() {
      return DISCARDING_QUEUE;
   }

   static int rehash(int h) {
      h += h << 15 ^ -12931;
      h ^= h >>> 10;
      h += h << 3;
      h ^= h >>> 6;
      h += (h << 2) + (h << 14);
      return h ^ h >>> 16;
   }

   @VisibleForTesting
   ReferenceEntry newEntry(Object key, int hash, @Nullable ReferenceEntry next) {
      Segment segment = this.segmentFor(hash);
      segment.lock();

      ReferenceEntry var5;
      try {
         var5 = segment.newEntry(key, hash, next);
      } finally {
         segment.unlock();
      }

      return var5;
   }

   @VisibleForTesting
   ReferenceEntry copyEntry(ReferenceEntry original, ReferenceEntry newNext) {
      int hash = original.getHash();
      return this.segmentFor(hash).copyEntry(original, newNext);
   }

   @VisibleForTesting
   ValueReference newValueReference(ReferenceEntry entry, Object value, int weight) {
      int hash = entry.getHash();
      return this.valueStrength.referenceValue(this.segmentFor(hash), entry, Preconditions.checkNotNull(value), weight);
   }

   int hash(@Nullable Object key) {
      int h = this.keyEquivalence.hash(key);
      return rehash(h);
   }

   void reclaimValue(ValueReference valueReference) {
      ReferenceEntry entry = valueReference.getEntry();
      int hash = entry.getHash();
      this.segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
   }

   void reclaimKey(ReferenceEntry entry) {
      int hash = entry.getHash();
      this.segmentFor(hash).reclaimKey(entry, hash);
   }

   @VisibleForTesting
   boolean isLive(ReferenceEntry entry, long now) {
      return this.segmentFor(entry.getHash()).getLiveValue(entry, now) != null;
   }

   Segment segmentFor(int hash) {
      return this.segments[hash >>> this.segmentShift & this.segmentMask];
   }

   Segment createSegment(int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter) {
      return new Segment(this, initialCapacity, maxSegmentWeight, statsCounter);
   }

   @Nullable
   Object getLiveValue(ReferenceEntry entry, long now) {
      if (entry.getKey() == null) {
         return null;
      } else {
         Object value = entry.getValueReference().get();
         if (value == null) {
            return null;
         } else {
            return this.isExpired(entry, now) ? null : value;
         }
      }
   }

   boolean isExpired(ReferenceEntry entry, long now) {
      Preconditions.checkNotNull(entry);
      if (this.expiresAfterAccess() && now - entry.getAccessTime() >= this.expireAfterAccessNanos) {
         return true;
      } else {
         return this.expiresAfterWrite() && now - entry.getWriteTime() >= this.expireAfterWriteNanos;
      }
   }

   static void connectAccessOrder(ReferenceEntry previous, ReferenceEntry next) {
      previous.setNextInAccessQueue(next);
      next.setPreviousInAccessQueue(previous);
   }

   static void nullifyAccessOrder(ReferenceEntry nulled) {
      ReferenceEntry nullEntry = nullEntry();
      nulled.setNextInAccessQueue(nullEntry);
      nulled.setPreviousInAccessQueue(nullEntry);
   }

   static void connectWriteOrder(ReferenceEntry previous, ReferenceEntry next) {
      previous.setNextInWriteQueue(next);
      next.setPreviousInWriteQueue(previous);
   }

   static void nullifyWriteOrder(ReferenceEntry nulled) {
      ReferenceEntry nullEntry = nullEntry();
      nulled.setNextInWriteQueue(nullEntry);
      nulled.setPreviousInWriteQueue(nullEntry);
   }

   void processPendingNotifications() {
      RemovalNotification notification;
      while((notification = (RemovalNotification)this.removalNotificationQueue.poll()) != null) {
         try {
            this.removalListener.onRemoval(notification);
         } catch (Throwable var3) {
            logger.log(Level.WARNING, "Exception thrown by removal listener", var3);
         }
      }

   }

   final Segment[] newSegmentArray(int ssize) {
      return new Segment[ssize];
   }

   public void cleanUp() {
      Segment[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Segment segment = var1[var3];
         segment.cleanUp();
      }

   }

   public boolean isEmpty() {
      long sum = 0L;
      Segment[] segments = this.segments;

      int i;
      for(i = 0; i < segments.length; ++i) {
         if (segments[i].count != 0) {
            return false;
         }

         sum += (long)segments[i].modCount;
      }

      if (sum != 0L) {
         for(i = 0; i < segments.length; ++i) {
            if (segments[i].count != 0) {
               return false;
            }

            sum -= (long)segments[i].modCount;
         }

         if (sum != 0L) {
            return false;
         }
      }

      return true;
   }

   long longSize() {
      Segment[] segments = this.segments;
      long sum = 0L;

      for(int i = 0; i < segments.length; ++i) {
         sum += (long)Math.max(0, segments[i].count);
      }

      return sum;
   }

   public int size() {
      return Ints.saturatedCast(this.longSize());
   }

   @Nullable
   public Object get(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).get(key, hash);
      }
   }

   @Nullable
   public Object getIfPresent(Object key) {
      int hash = this.hash(Preconditions.checkNotNull(key));
      Object value = this.segmentFor(hash).get(key, hash);
      if (value == null) {
         this.globalStatsCounter.recordMisses(1);
      } else {
         this.globalStatsCounter.recordHits(1);
      }

      return value;
   }

   @Nullable
   public Object getOrDefault(@Nullable Object key, @Nullable Object defaultValue) {
      Object result = this.get(key);
      return result != null ? result : defaultValue;
   }

   Object get(Object key, CacheLoader loader) throws ExecutionException {
      int hash = this.hash(Preconditions.checkNotNull(key));
      return this.segmentFor(hash).get(key, hash, loader);
   }

   Object getOrLoad(Object key) throws ExecutionException {
      return this.get(key, this.defaultLoader);
   }

   ImmutableMap getAllPresent(Iterable keys) {
      int hits = 0;
      int misses = 0;
      Map result = Maps.newLinkedHashMap();
      Iterator var5 = keys.iterator();

      while(var5.hasNext()) {
         Object key = var5.next();
         Object value = this.get(key);
         if (value == null) {
            ++misses;
         } else {
            result.put(key, value);
            ++hits;
         }
      }

      this.globalStatsCounter.recordHits(hits);
      this.globalStatsCounter.recordMisses(misses);
      return ImmutableMap.copyOf((Map)result);
   }

   ImmutableMap getAll(Iterable keys) throws ExecutionException {
      int hits = 0;
      int misses = 0;
      Map result = Maps.newLinkedHashMap();
      Set keysToLoad = Sets.newLinkedHashSet();
      Iterator var6 = keys.iterator();

      Object key;
      while(var6.hasNext()) {
         Object key = var6.next();
         key = this.get(key);
         if (!result.containsKey(key)) {
            result.put(key, key);
            if (key == null) {
               ++misses;
               keysToLoad.add(key);
            } else {
               ++hits;
            }
         }
      }

      ImmutableMap var16;
      try {
         if (!keysToLoad.isEmpty()) {
            Iterator var17;
            try {
               Map newEntries = this.loadAll(keysToLoad, this.defaultLoader);
               var17 = keysToLoad.iterator();

               while(var17.hasNext()) {
                  key = var17.next();
                  Object value = newEntries.get(key);
                  if (value == null) {
                     throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + key);
                  }

                  result.put(key, value);
               }
            } catch (CacheLoader.UnsupportedLoadingOperationException var13) {
               var17 = keysToLoad.iterator();

               while(var17.hasNext()) {
                  key = var17.next();
                  --misses;
                  result.put(key, this.get(key, this.defaultLoader));
               }
            }
         }

         var16 = ImmutableMap.copyOf((Map)result);
      } finally {
         this.globalStatsCounter.recordHits(hits);
         this.globalStatsCounter.recordMisses(misses);
      }

      return var16;
   }

   @Nullable
   Map loadAll(Set keys, CacheLoader loader) throws ExecutionException {
      Preconditions.checkNotNull(loader);
      Preconditions.checkNotNull(keys);
      Stopwatch stopwatch = Stopwatch.createStarted();
      boolean success = false;

      Map result;
      try {
         Map map = loader.loadAll(keys);
         result = map;
         success = true;
      } catch (CacheLoader.UnsupportedLoadingOperationException var17) {
         success = true;
         throw var17;
      } catch (InterruptedException var18) {
         Thread.currentThread().interrupt();
         throw new ExecutionException(var18);
      } catch (RuntimeException var19) {
         throw new UncheckedExecutionException(var19);
      } catch (Exception var20) {
         throw new ExecutionException(var20);
      } catch (Error var21) {
         throw new ExecutionError(var21);
      } finally {
         if (!success) {
            this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
         }

      }

      if (result == null) {
         this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
         throw new CacheLoader.InvalidCacheLoadException(loader + " returned null map from loadAll");
      } else {
         stopwatch.stop();
         boolean nullsPresent = false;
         Iterator var7 = result.entrySet().iterator();

         while(true) {
            while(var7.hasNext()) {
               Map.Entry entry = (Map.Entry)var7.next();
               Object key = entry.getKey();
               Object value = entry.getValue();
               if (key != null && value != null) {
                  this.put(key, value);
               } else {
                  nullsPresent = true;
               }
            }

            if (nullsPresent) {
               this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
               throw new CacheLoader.InvalidCacheLoadException(loader + " returned null keys or values from loadAll");
            }

            this.globalStatsCounter.recordLoadSuccess(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            return result;
         }
      }
   }

   ReferenceEntry getEntry(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).getEntry(key, hash);
      }
   }

   void refresh(Object key) {
      int hash = this.hash(Preconditions.checkNotNull(key));
      this.segmentFor(hash).refresh(key, hash, this.defaultLoader, false);
   }

   public boolean containsKey(@Nullable Object key) {
      if (key == null) {
         return false;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).containsKey(key, hash);
      }
   }

   public boolean containsValue(@Nullable Object value) {
      if (value == null) {
         return false;
      } else {
         long now = this.ticker.read();
         Segment[] segments = this.segments;
         long last = -1L;

         for(int i = 0; i < 3; ++i) {
            long sum = 0L;
            Segment[] var10 = segments;
            int var11 = segments.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               Segment segment = var10[var12];
               int unused = segment.count;
               AtomicReferenceArray table = segment.table;

               for(int j = 0; j < table.length(); ++j) {
                  for(ReferenceEntry e = (ReferenceEntry)table.get(j); e != null; e = e.getNext()) {
                     Object v = segment.getLiveValue(e, now);
                     if (v != null && this.valueEquivalence.equivalent(value, v)) {
                        return true;
                     }
                  }
               }

               sum += (long)segment.modCount;
            }

            if (sum == last) {
               break;
            }

            last = sum;
         }

         return false;
      }
   }

   public Object put(Object key, Object value) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
      int hash = this.hash(key);
      return this.segmentFor(hash).put(key, hash, value, false);
   }

   public Object putIfAbsent(Object key, Object value) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
      int hash = this.hash(key);
      return this.segmentFor(hash).put(key, hash, value, true);
   }

   public void putAll(Map m) {
      Iterator var2 = m.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         this.put(e.getKey(), e.getValue());
      }

   }

   public Object remove(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).remove(key, hash);
      }
   }

   public boolean remove(@Nullable Object key, @Nullable Object value) {
      if (key != null && value != null) {
         int hash = this.hash(key);
         return this.segmentFor(hash).remove(key, hash, value);
      } else {
         return false;
      }
   }

   public boolean replace(Object key, @Nullable Object oldValue, Object newValue) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(newValue);
      if (oldValue == null) {
         return false;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).replace(key, hash, oldValue, newValue);
      }
   }

   public Object replace(Object key, Object value) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
      int hash = this.hash(key);
      return this.segmentFor(hash).replace(key, hash, value);
   }

   public void clear() {
      Segment[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Segment segment = var1[var3];
         segment.clear();
      }

   }

   void invalidateAll(Iterable keys) {
      Iterator var2 = keys.iterator();

      while(var2.hasNext()) {
         Object key = var2.next();
         this.remove(key);
      }

   }

   public Set keySet() {
      Set ks = this.keySet;
      return ks != null ? ks : (this.keySet = new KeySet(this));
   }

   public Collection values() {
      Collection vs = this.values;
      return vs != null ? vs : (this.values = new Values(this));
   }

   @GwtIncompatible
   public Set entrySet() {
      Set es = this.entrySet;
      return es != null ? es : (this.entrySet = new EntrySet(this));
   }

   private static ArrayList toArrayList(Collection c) {
      ArrayList result = new ArrayList(c.size());
      Iterators.addAll(result, c.iterator());
      return result;
   }

   static class LocalLoadingCache extends LocalManualCache implements LoadingCache {
      private static final long serialVersionUID = 1L;

      LocalLoadingCache(CacheBuilder builder, CacheLoader loader) {
         super(new LocalCache(builder, (CacheLoader)Preconditions.checkNotNull(loader)), null);
      }

      public Object get(Object key) throws ExecutionException {
         return this.localCache.getOrLoad(key);
      }

      public Object getUnchecked(Object key) {
         try {
            return this.get(key);
         } catch (ExecutionException var3) {
            throw new UncheckedExecutionException(var3.getCause());
         }
      }

      public ImmutableMap getAll(Iterable keys) throws ExecutionException {
         return this.localCache.getAll(keys);
      }

      public void refresh(Object key) {
         this.localCache.refresh(key);
      }

      public final Object apply(Object key) {
         return this.getUnchecked(key);
      }

      Object writeReplace() {
         return new LoadingSerializationProxy(this.localCache);
      }
   }

   static class LocalManualCache implements Cache, Serializable {
      final LocalCache localCache;
      private static final long serialVersionUID = 1L;

      LocalManualCache(CacheBuilder builder) {
         this(new LocalCache(builder, (CacheLoader)null));
      }

      private LocalManualCache(LocalCache localCache) {
         this.localCache = localCache;
      }

      @Nullable
      public Object getIfPresent(Object key) {
         return this.localCache.getIfPresent(key);
      }

      public Object get(Object key, final Callable valueLoader) throws ExecutionException {
         Preconditions.checkNotNull(valueLoader);
         return this.localCache.get(key, new CacheLoader() {
            public Object load(Object key) throws Exception {
               return valueLoader.call();
            }
         });
      }

      public ImmutableMap getAllPresent(Iterable keys) {
         return this.localCache.getAllPresent(keys);
      }

      public void put(Object key, Object value) {
         this.localCache.put(key, value);
      }

      public void putAll(Map m) {
         this.localCache.putAll(m);
      }

      public void invalidate(Object key) {
         Preconditions.checkNotNull(key);
         this.localCache.remove(key);
      }

      public void invalidateAll(Iterable keys) {
         this.localCache.invalidateAll(keys);
      }

      public void invalidateAll() {
         this.localCache.clear();
      }

      public long size() {
         return this.localCache.longSize();
      }

      public ConcurrentMap asMap() {
         return this.localCache;
      }

      public CacheStats stats() {
         AbstractCache.SimpleStatsCounter aggregator = new AbstractCache.SimpleStatsCounter();
         aggregator.incrementBy(this.localCache.globalStatsCounter);
         Segment[] var2 = this.localCache.segments;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Segment segment = var2[var4];
            aggregator.incrementBy(segment.statsCounter);
         }

         return aggregator.snapshot();
      }

      public void cleanUp() {
         this.localCache.cleanUp();
      }

      Object writeReplace() {
         return new ManualSerializationProxy(this.localCache);
      }

      // $FF: synthetic method
      LocalManualCache(LocalCache x0, Object x1) {
         this(x0);
      }
   }

   static final class LoadingSerializationProxy extends ManualSerializationProxy implements LoadingCache, Serializable {
      private static final long serialVersionUID = 1L;
      transient LoadingCache autoDelegate;

      LoadingSerializationProxy(LocalCache cache) {
         super(cache);
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         CacheBuilder builder = this.recreateCacheBuilder();
         this.autoDelegate = builder.build(this.loader);
      }

      public Object get(Object key) throws ExecutionException {
         return this.autoDelegate.get(key);
      }

      public Object getUnchecked(Object key) {
         return this.autoDelegate.getUnchecked(key);
      }

      public ImmutableMap getAll(Iterable keys) throws ExecutionException {
         return this.autoDelegate.getAll(keys);
      }

      public final Object apply(Object key) {
         return this.autoDelegate.apply(key);
      }

      public void refresh(Object key) {
         this.autoDelegate.refresh(key);
      }

      private Object readResolve() {
         return this.autoDelegate;
      }
   }

   static class ManualSerializationProxy extends ForwardingCache implements Serializable {
      private static final long serialVersionUID = 1L;
      final Strength keyStrength;
      final Strength valueStrength;
      final Equivalence keyEquivalence;
      final Equivalence valueEquivalence;
      final long expireAfterWriteNanos;
      final long expireAfterAccessNanos;
      final long maxWeight;
      final Weigher weigher;
      final int concurrencyLevel;
      final RemovalListener removalListener;
      final Ticker ticker;
      final CacheLoader loader;
      transient Cache delegate;

      ManualSerializationProxy(LocalCache cache) {
         this(cache.keyStrength, cache.valueStrength, cache.keyEquivalence, cache.valueEquivalence, cache.expireAfterWriteNanos, cache.expireAfterAccessNanos, cache.maxWeight, cache.weigher, cache.concurrencyLevel, cache.removalListener, cache.ticker, cache.defaultLoader);
      }

      private ManualSerializationProxy(Strength keyStrength, Strength valueStrength, Equivalence keyEquivalence, Equivalence valueEquivalence, long expireAfterWriteNanos, long expireAfterAccessNanos, long maxWeight, Weigher weigher, int concurrencyLevel, RemovalListener removalListener, Ticker ticker, CacheLoader loader) {
         this.keyStrength = keyStrength;
         this.valueStrength = valueStrength;
         this.keyEquivalence = keyEquivalence;
         this.valueEquivalence = valueEquivalence;
         this.expireAfterWriteNanos = expireAfterWriteNanos;
         this.expireAfterAccessNanos = expireAfterAccessNanos;
         this.maxWeight = maxWeight;
         this.weigher = weigher;
         this.concurrencyLevel = concurrencyLevel;
         this.removalListener = removalListener;
         this.ticker = ticker != Ticker.systemTicker() && ticker != CacheBuilder.NULL_TICKER ? ticker : null;
         this.loader = loader;
      }

      CacheBuilder recreateCacheBuilder() {
         CacheBuilder builder = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
         builder.strictParsing = false;
         if (this.expireAfterWriteNanos > 0L) {
            builder.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
         }

         if (this.expireAfterAccessNanos > 0L) {
            builder.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
         }

         if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
            builder.weigher(this.weigher);
            if (this.maxWeight != -1L) {
               builder.maximumWeight(this.maxWeight);
            }
         } else if (this.maxWeight != -1L) {
            builder.maximumSize(this.maxWeight);
         }

         if (this.ticker != null) {
            builder.ticker(this.ticker);
         }

         return builder;
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         CacheBuilder builder = this.recreateCacheBuilder();
         this.delegate = builder.build();
      }

      private Object readResolve() {
         return this.delegate;
      }

      protected Cache delegate() {
         return this.delegate;
      }
   }

   final class EntrySet extends AbstractCacheSet {
      EntrySet(ConcurrentMap map) {
         super(map);
      }

      public Iterator iterator() {
         return LocalCache.this.new EntryIterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Object key = e.getKey();
            if (key == null) {
               return false;
            } else {
               Object v = LocalCache.this.get(key);
               return v != null && LocalCache.this.valueEquivalence.equivalent(e.getValue(), v);
            }
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Object key = e.getKey();
            return key != null && LocalCache.this.remove(key, e.getValue());
         }
      }
   }

   final class Values extends AbstractCollection {
      private final ConcurrentMap map;

      Values(ConcurrentMap map) {
         this.map = map;
      }

      public int size() {
         return this.map.size();
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public void clear() {
         this.map.clear();
      }

      public Iterator iterator() {
         return LocalCache.this.new ValueIterator();
      }

      public boolean contains(Object o) {
         return this.map.containsValue(o);
      }

      public Object[] toArray() {
         return LocalCache.toArrayList(this).toArray();
      }

      public Object[] toArray(Object[] a) {
         return LocalCache.toArrayList(this).toArray(a);
      }
   }

   final class KeySet extends AbstractCacheSet {
      KeySet(ConcurrentMap map) {
         super(map);
      }

      public Iterator iterator() {
         return LocalCache.this.new KeyIterator();
      }

      public boolean contains(Object o) {
         return this.map.containsKey(o);
      }

      public boolean remove(Object o) {
         return this.map.remove(o) != null;
      }
   }

   abstract class AbstractCacheSet extends AbstractSet {
      @Weak
      final ConcurrentMap map;

      AbstractCacheSet(ConcurrentMap map) {
         this.map = map;
      }

      public int size() {
         return this.map.size();
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public void clear() {
         this.map.clear();
      }

      public Object[] toArray() {
         return LocalCache.toArrayList(this).toArray();
      }

      public Object[] toArray(Object[] a) {
         return LocalCache.toArrayList(this).toArray(a);
      }
   }

   final class EntryIterator extends HashIterator {
      EntryIterator() {
         super();
      }

      public Map.Entry next() {
         return this.nextEntry();
      }
   }

   final class WriteThroughEntry implements Map.Entry {
      final Object key;
      Object value;

      WriteThroughEntry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public boolean equals(@Nullable Object object) {
         if (!(object instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry that = (Map.Entry)object;
            return this.key.equals(that.getKey()) && this.value.equals(that.getValue());
         }
      }

      public int hashCode() {
         return this.key.hashCode() ^ this.value.hashCode();
      }

      public Object setValue(Object newValue) {
         Object oldValue = LocalCache.this.put(this.key, newValue);
         this.value = newValue;
         return oldValue;
      }

      public String toString() {
         return this.getKey() + "=" + this.getValue();
      }
   }

   final class ValueIterator extends HashIterator {
      ValueIterator() {
         super();
      }

      public Object next() {
         return this.nextEntry().getValue();
      }
   }

   final class KeyIterator extends HashIterator {
      KeyIterator() {
         super();
      }

      public Object next() {
         return this.nextEntry().getKey();
      }
   }

   abstract class HashIterator implements Iterator {
      int nextSegmentIndex;
      int nextTableIndex;
      Segment currentSegment;
      AtomicReferenceArray currentTable;
      ReferenceEntry nextEntry;
      WriteThroughEntry nextExternal;
      WriteThroughEntry lastReturned;

      HashIterator() {
         this.nextSegmentIndex = LocalCache.this.segments.length - 1;
         this.nextTableIndex = -1;
         this.advance();
      }

      public abstract Object next();

      final void advance() {
         this.nextExternal = null;
         if (!this.nextInChain()) {
            if (!this.nextInTable()) {
               while(this.nextSegmentIndex >= 0) {
                  this.currentSegment = LocalCache.this.segments[this.nextSegmentIndex--];
                  if (this.currentSegment.count != 0) {
                     this.currentTable = this.currentSegment.table;
                     this.nextTableIndex = this.currentTable.length() - 1;
                     if (this.nextInTable()) {
                        return;
                     }
                  }
               }

            }
         }
      }

      boolean nextInChain() {
         if (this.nextEntry != null) {
            for(this.nextEntry = this.nextEntry.getNext(); this.nextEntry != null; this.nextEntry = this.nextEntry.getNext()) {
               if (this.advanceTo(this.nextEntry)) {
                  return true;
               }
            }
         }

         return false;
      }

      boolean nextInTable() {
         while(true) {
            if (this.nextTableIndex >= 0) {
               if ((this.nextEntry = (ReferenceEntry)this.currentTable.get(this.nextTableIndex--)) == null || !this.advanceTo(this.nextEntry) && !this.nextInChain()) {
                  continue;
               }

               return true;
            }

            return false;
         }
      }

      boolean advanceTo(ReferenceEntry entry) {
         boolean var6;
         try {
            long now = LocalCache.this.ticker.read();
            Object key = entry.getKey();
            Object value = LocalCache.this.getLiveValue(entry, now);
            if (value != null) {
               this.nextExternal = LocalCache.this.new WriteThroughEntry(key, value);
               var6 = true;
               return var6;
            }

            var6 = false;
         } finally {
            this.currentSegment.postReadCleanup();
         }

         return var6;
      }

      public boolean hasNext() {
         return this.nextExternal != null;
      }

      WriteThroughEntry nextEntry() {
         if (this.nextExternal == null) {
            throw new NoSuchElementException();
         } else {
            this.lastReturned = this.nextExternal;
            this.advance();
            return this.lastReturned;
         }
      }

      public void remove() {
         Preconditions.checkState(this.lastReturned != null);
         LocalCache.this.remove(this.lastReturned.getKey());
         this.lastReturned = null;
      }
   }

   static final class AccessQueue extends AbstractQueue {
      final ReferenceEntry head = new AbstractReferenceEntry() {
         ReferenceEntry nextAccess = this;
         ReferenceEntry previousAccess = this;

         public long getAccessTime() {
            return Long.MAX_VALUE;
         }

         public void setAccessTime(long time) {
         }

         public ReferenceEntry getNextInAccessQueue() {
            return this.nextAccess;
         }

         public void setNextInAccessQueue(ReferenceEntry next) {
            this.nextAccess = next;
         }

         public ReferenceEntry getPreviousInAccessQueue() {
            return this.previousAccess;
         }

         public void setPreviousInAccessQueue(ReferenceEntry previous) {
            this.previousAccess = previous;
         }
      };

      public boolean offer(ReferenceEntry entry) {
         LocalCache.connectAccessOrder(entry.getPreviousInAccessQueue(), entry.getNextInAccessQueue());
         LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), entry);
         LocalCache.connectAccessOrder(entry, this.head);
         return true;
      }

      public ReferenceEntry peek() {
         ReferenceEntry next = this.head.getNextInAccessQueue();
         return next == this.head ? null : next;
      }

      public ReferenceEntry poll() {
         ReferenceEntry next = this.head.getNextInAccessQueue();
         if (next == this.head) {
            return null;
         } else {
            this.remove(next);
            return next;
         }
      }

      public boolean remove(Object o) {
         ReferenceEntry e = (ReferenceEntry)o;
         ReferenceEntry previous = e.getPreviousInAccessQueue();
         ReferenceEntry next = e.getNextInAccessQueue();
         LocalCache.connectAccessOrder(previous, next);
         LocalCache.nullifyAccessOrder(e);
         return next != LocalCache.NullEntry.INSTANCE;
      }

      public boolean contains(Object o) {
         ReferenceEntry e = (ReferenceEntry)o;
         return e.getNextInAccessQueue() != LocalCache.NullEntry.INSTANCE;
      }

      public boolean isEmpty() {
         return this.head.getNextInAccessQueue() == this.head;
      }

      public int size() {
         int size = 0;

         for(ReferenceEntry e = this.head.getNextInAccessQueue(); e != this.head; e = e.getNextInAccessQueue()) {
            ++size;
         }

         return size;
      }

      public void clear() {
         ReferenceEntry next;
         for(ReferenceEntry e = this.head.getNextInAccessQueue(); e != this.head; e = next) {
            next = e.getNextInAccessQueue();
            LocalCache.nullifyAccessOrder(e);
         }

         this.head.setNextInAccessQueue(this.head);
         this.head.setPreviousInAccessQueue(this.head);
      }

      public Iterator iterator() {
         return new AbstractSequentialIterator(this.peek()) {
            protected ReferenceEntry computeNext(ReferenceEntry previous) {
               ReferenceEntry next = previous.getNextInAccessQueue();
               return next == AccessQueue.this.head ? null : next;
            }
         };
      }
   }

   static final class WriteQueue extends AbstractQueue {
      final ReferenceEntry head = new AbstractReferenceEntry() {
         ReferenceEntry nextWrite = this;
         ReferenceEntry previousWrite = this;

         public long getWriteTime() {
            return Long.MAX_VALUE;
         }

         public void setWriteTime(long time) {
         }

         public ReferenceEntry getNextInWriteQueue() {
            return this.nextWrite;
         }

         public void setNextInWriteQueue(ReferenceEntry next) {
            this.nextWrite = next;
         }

         public ReferenceEntry getPreviousInWriteQueue() {
            return this.previousWrite;
         }

         public void setPreviousInWriteQueue(ReferenceEntry previous) {
            this.previousWrite = previous;
         }
      };

      public boolean offer(ReferenceEntry entry) {
         LocalCache.connectWriteOrder(entry.getPreviousInWriteQueue(), entry.getNextInWriteQueue());
         LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), entry);
         LocalCache.connectWriteOrder(entry, this.head);
         return true;
      }

      public ReferenceEntry peek() {
         ReferenceEntry next = this.head.getNextInWriteQueue();
         return next == this.head ? null : next;
      }

      public ReferenceEntry poll() {
         ReferenceEntry next = this.head.getNextInWriteQueue();
         if (next == this.head) {
            return null;
         } else {
            this.remove(next);
            return next;
         }
      }

      public boolean remove(Object o) {
         ReferenceEntry e = (ReferenceEntry)o;
         ReferenceEntry previous = e.getPreviousInWriteQueue();
         ReferenceEntry next = e.getNextInWriteQueue();
         LocalCache.connectWriteOrder(previous, next);
         LocalCache.nullifyWriteOrder(e);
         return next != LocalCache.NullEntry.INSTANCE;
      }

      public boolean contains(Object o) {
         ReferenceEntry e = (ReferenceEntry)o;
         return e.getNextInWriteQueue() != LocalCache.NullEntry.INSTANCE;
      }

      public boolean isEmpty() {
         return this.head.getNextInWriteQueue() == this.head;
      }

      public int size() {
         int size = 0;

         for(ReferenceEntry e = this.head.getNextInWriteQueue(); e != this.head; e = e.getNextInWriteQueue()) {
            ++size;
         }

         return size;
      }

      public void clear() {
         ReferenceEntry next;
         for(ReferenceEntry e = this.head.getNextInWriteQueue(); e != this.head; e = next) {
            next = e.getNextInWriteQueue();
            LocalCache.nullifyWriteOrder(e);
         }

         this.head.setNextInWriteQueue(this.head);
         this.head.setPreviousInWriteQueue(this.head);
      }

      public Iterator iterator() {
         return new AbstractSequentialIterator(this.peek()) {
            protected ReferenceEntry computeNext(ReferenceEntry previous) {
               ReferenceEntry next = previous.getNextInWriteQueue();
               return next == WriteQueue.this.head ? null : next;
            }
         };
      }
   }

   static class LoadingValueReference implements ValueReference {
      volatile ValueReference oldValue;
      final SettableFuture futureValue;
      final Stopwatch stopwatch;

      public LoadingValueReference() {
         this(LocalCache.unset());
      }

      public LoadingValueReference(ValueReference oldValue) {
         this.futureValue = SettableFuture.create();
         this.stopwatch = Stopwatch.createUnstarted();
         this.oldValue = oldValue;
      }

      public boolean isLoading() {
         return true;
      }

      public boolean isActive() {
         return this.oldValue.isActive();
      }

      public int getWeight() {
         return this.oldValue.getWeight();
      }

      public boolean set(@Nullable Object newValue) {
         return this.futureValue.set(newValue);
      }

      public boolean setException(Throwable t) {
         return this.futureValue.setException(t);
      }

      private ListenableFuture fullyFailedFuture(Throwable t) {
         return Futures.immediateFailedFuture(t);
      }

      public void notifyNewValue(@Nullable Object newValue) {
         if (newValue != null) {
            this.set(newValue);
         } else {
            this.oldValue = LocalCache.unset();
         }

      }

      public ListenableFuture loadFuture(Object key, CacheLoader loader) {
         Object result;
         try {
            this.stopwatch.start();
            Object previousValue = this.oldValue.get();
            if (previousValue == null) {
               result = loader.load(key);
               return (ListenableFuture)(this.set(result) ? this.futureValue : Futures.immediateFuture(result));
            } else {
               ListenableFuture newValue = loader.reload(key, previousValue);
               return newValue == null ? Futures.immediateFuture((Object)null) : Futures.transform(newValue, new Function() {
                  public Object apply(Object newValue) {
                     LoadingValueReference.this.set(newValue);
                     return newValue;
                  }
               }, MoreExecutors.directExecutor());
            }
         } catch (Throwable var5) {
            result = this.setException(var5) ? this.futureValue : this.fullyFailedFuture(var5);
            if (var5 instanceof InterruptedException) {
               Thread.currentThread().interrupt();
            }

            return (ListenableFuture)result;
         }
      }

      public long elapsedNanos() {
         return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
      }

      public Object waitForValue() throws ExecutionException {
         return Uninterruptibles.getUninterruptibly(this.futureValue);
      }

      public Object get() {
         return this.oldValue.get();
      }

      public ValueReference getOldValue() {
         return this.oldValue;
      }

      public ReferenceEntry getEntry() {
         return null;
      }

      public ValueReference copyFor(ReferenceQueue queue, @Nullable Object value, ReferenceEntry entry) {
         return this;
      }
   }

   static class Segment extends ReentrantLock {
      @Weak
      final LocalCache map;
      volatile int count;
      @GuardedBy("this")
      long totalWeight;
      int modCount;
      int threshold;
      volatile AtomicReferenceArray table;
      final long maxSegmentWeight;
      final ReferenceQueue keyReferenceQueue;
      final ReferenceQueue valueReferenceQueue;
      final Queue recencyQueue;
      final AtomicInteger readCount = new AtomicInteger();
      @GuardedBy("this")
      final Queue writeQueue;
      @GuardedBy("this")
      final Queue accessQueue;
      final AbstractCache.StatsCounter statsCounter;

      Segment(LocalCache map, int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter) {
         this.map = map;
         this.maxSegmentWeight = maxSegmentWeight;
         this.statsCounter = (AbstractCache.StatsCounter)Preconditions.checkNotNull(statsCounter);
         this.initTable(this.newEntryArray(initialCapacity));
         this.keyReferenceQueue = map.usesKeyReferences() ? new ReferenceQueue() : null;
         this.valueReferenceQueue = map.usesValueReferences() ? new ReferenceQueue() : null;
         this.recencyQueue = (Queue)(map.usesAccessQueue() ? new ConcurrentLinkedQueue() : LocalCache.discardingQueue());
         this.writeQueue = (Queue)(map.usesWriteQueue() ? new WriteQueue() : LocalCache.discardingQueue());
         this.accessQueue = (Queue)(map.usesAccessQueue() ? new AccessQueue() : LocalCache.discardingQueue());
      }

      AtomicReferenceArray newEntryArray(int size) {
         return new AtomicReferenceArray(size);
      }

      void initTable(AtomicReferenceArray newTable) {
         this.threshold = newTable.length() * 3 / 4;
         if (!this.map.customWeigher() && (long)this.threshold == this.maxSegmentWeight) {
            ++this.threshold;
         }

         this.table = newTable;
      }

      @GuardedBy("this")
      ReferenceEntry newEntry(Object key, int hash, @Nullable ReferenceEntry next) {
         return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(key), hash, next);
      }

      @GuardedBy("this")
      ReferenceEntry copyEntry(ReferenceEntry original, ReferenceEntry newNext) {
         if (original.getKey() == null) {
            return null;
         } else {
            ValueReference valueReference = original.getValueReference();
            Object value = valueReference.get();
            if (value == null && valueReference.isActive()) {
               return null;
            } else {
               ReferenceEntry newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
               newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
               return newEntry;
            }
         }
      }

      @GuardedBy("this")
      void setValue(ReferenceEntry entry, Object key, Object value, long now) {
         ValueReference previous = entry.getValueReference();
         int weight = this.map.weigher.weigh(key, value);
         Preconditions.checkState(weight >= 0, "Weights must be non-negative");
         ValueReference valueReference = this.map.valueStrength.referenceValue(this, entry, value, weight);
         entry.setValueReference(valueReference);
         this.recordWrite(entry, weight, now);
         previous.notifyNewValue(value);
      }

      Object get(Object key, int hash, CacheLoader loader) throws ExecutionException {
         Preconditions.checkNotNull(key);
         Preconditions.checkNotNull(loader);

         Object var16;
         try {
            if (this.count != 0) {
               ReferenceEntry e = this.getEntry(key, hash);
               if (e != null) {
                  long now = this.map.ticker.read();
                  Object value = this.getLiveValue(e, now);
                  if (value != null) {
                     this.recordRead(e, now);
                     this.statsCounter.recordHits(1);
                     Object var17 = this.scheduleRefresh(e, key, hash, value, now, loader);
                     return var17;
                  }

                  ValueReference valueReference = e.getValueReference();
                  if (valueReference.isLoading()) {
                     Object var9 = this.waitForLoadingValue(e, key, valueReference);
                     return var9;
                  }
               }
            }

            var16 = this.lockedGetOrLoad(key, hash, loader);
         } catch (ExecutionException var14) {
            Throwable cause = var14.getCause();
            if (cause instanceof Error) {
               throw new ExecutionError((Error)cause);
            }

            if (cause instanceof RuntimeException) {
               throw new UncheckedExecutionException(cause);
            }

            throw var14;
         } finally {
            this.postReadCleanup();
         }

         return var16;
      }

      Object lockedGetOrLoad(Object key, int hash, CacheLoader loader) throws ExecutionException {
         ValueReference valueReference = null;
         LoadingValueReference loadingValueReference = null;
         boolean createNewEntry = true;
         this.lock();

         ReferenceEntry e;
         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            for(e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  valueReference = e.getValueReference();
                  if (valueReference.isLoading()) {
                     createNewEntry = false;
                  } else {
                     Object value = valueReference.get();
                     if (value == null) {
                        this.enqueueNotification(entryKey, hash, value, valueReference.getWeight(), RemovalCause.COLLECTED);
                     } else {
                        if (!this.map.isExpired(e, now)) {
                           this.recordLockedRead(e, now);
                           this.statsCounter.recordHits(1);
                           Object var16 = value;
                           return var16;
                        }

                        this.enqueueNotification(entryKey, hash, value, valueReference.getWeight(), RemovalCause.EXPIRED);
                     }

                     this.writeQueue.remove(e);
                     this.accessQueue.remove(e);
                     this.count = newCount;
                  }
                  break;
               }
            }

            if (createNewEntry) {
               loadingValueReference = new LoadingValueReference();
               if (e == null) {
                  e = this.newEntry(key, hash, first);
                  e.setValueReference(loadingValueReference);
                  table.set(index, e);
               } else {
                  e.setValueReference(loadingValueReference);
               }
            }
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }

         if (createNewEntry) {
            Object var19;
            try {
               synchronized(e) {
                  var19 = this.loadSync(key, hash, loadingValueReference, loader);
               }
            } finally {
               this.statsCounter.recordMisses(1);
            }

            return var19;
         } else {
            return this.waitForLoadingValue(e, key, valueReference);
         }
      }

      Object waitForLoadingValue(ReferenceEntry e, Object key, ValueReference valueReference) throws ExecutionException {
         if (!valueReference.isLoading()) {
            throw new AssertionError();
         } else {
            Preconditions.checkState(!Thread.holdsLock(e), "Recursive load of: %s", key);

            Object var7;
            try {
               Object value = valueReference.waitForValue();
               if (value == null) {
                  throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
               }

               long now = this.map.ticker.read();
               this.recordRead(e, now);
               var7 = value;
            } finally {
               this.statsCounter.recordMisses(1);
            }

            return var7;
         }
      }

      Object loadSync(Object key, int hash, LoadingValueReference loadingValueReference, CacheLoader loader) throws ExecutionException {
         ListenableFuture loadingFuture = loadingValueReference.loadFuture(key, loader);
         return this.getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
      }

      ListenableFuture loadAsync(final Object key, final int hash, final LoadingValueReference loadingValueReference, CacheLoader loader) {
         final ListenableFuture loadingFuture = loadingValueReference.loadFuture(key, loader);
         loadingFuture.addListener(new Runnable() {
            public void run() {
               try {
                  Segment.this.getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
               } catch (Throwable var2) {
                  LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", var2);
                  loadingValueReference.setException(var2);
               }

            }
         }, MoreExecutors.directExecutor());
         return loadingFuture;
      }

      Object getAndRecordStats(Object key, int hash, LoadingValueReference loadingValueReference, ListenableFuture newValue) throws ExecutionException {
         Object value = null;

         Object var6;
         try {
            value = Uninterruptibles.getUninterruptibly(newValue);
            if (value == null) {
               throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
            }

            this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
            this.storeLoadedValue(key, hash, loadingValueReference, value);
            var6 = value;
         } finally {
            if (value == null) {
               this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
               this.removeLoadingValue(key, hash, loadingValueReference);
            }

         }

         return var6;
      }

      Object scheduleRefresh(ReferenceEntry entry, Object key, int hash, Object oldValue, long now, CacheLoader loader) {
         if (this.map.refreshes() && now - entry.getWriteTime() > this.map.refreshNanos && !entry.getValueReference().isLoading()) {
            Object newValue = this.refresh(key, hash, loader, true);
            if (newValue != null) {
               return newValue;
            }
         }

         return oldValue;
      }

      @Nullable
      Object refresh(Object key, int hash, CacheLoader loader, boolean checkTime) {
         LoadingValueReference loadingValueReference = this.insertLoadingValueReference(key, hash, checkTime);
         if (loadingValueReference == null) {
            return null;
         } else {
            ListenableFuture result = this.loadAsync(key, hash, loadingValueReference, loader);
            if (result.isDone()) {
               try {
                  return Uninterruptibles.getUninterruptibly(result);
               } catch (Throwable var8) {
               }
            }

            return null;
         }
      }

      @Nullable
      LoadingValueReference insertLoadingValueReference(Object key, int hash, boolean checkTime) {
         ReferenceEntry e = null;
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            for(e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference valueReference = e.getValueReference();
                  LoadingValueReference loadingValueReference;
                  if (!valueReference.isLoading() && (!checkTime || now - e.getWriteTime() >= this.map.refreshNanos)) {
                     ++this.modCount;
                     loadingValueReference = new LoadingValueReference(valueReference);
                     e.setValueReference(loadingValueReference);
                     LoadingValueReference var13 = loadingValueReference;
                     return var13;
                  }

                  loadingValueReference = null;
                  return loadingValueReference;
               }
            }

            ++this.modCount;
            LoadingValueReference loadingValueReference = new LoadingValueReference();
            e = this.newEntry(key, hash, first);
            e.setValueReference(loadingValueReference);
            table.set(index, e);
            LoadingValueReference var18 = loadingValueReference;
            return var18;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      void tryDrainReferenceQueues() {
         if (this.tryLock()) {
            try {
               this.drainReferenceQueues();
            } finally {
               this.unlock();
            }
         }

      }

      @GuardedBy("this")
      void drainReferenceQueues() {
         if (this.map.usesKeyReferences()) {
            this.drainKeyReferenceQueue();
         }

         if (this.map.usesValueReferences()) {
            this.drainValueReferenceQueue();
         }

      }

      @GuardedBy("this")
      void drainKeyReferenceQueue() {
         int i = 0;

         Reference ref;
         while((ref = this.keyReferenceQueue.poll()) != null) {
            ReferenceEntry entry = (ReferenceEntry)ref;
            this.map.reclaimKey(entry);
            ++i;
            if (i == 16) {
               break;
            }
         }

      }

      @GuardedBy("this")
      void drainValueReferenceQueue() {
         int i = 0;

         Reference ref;
         while((ref = this.valueReferenceQueue.poll()) != null) {
            ValueReference valueReference = (ValueReference)ref;
            this.map.reclaimValue(valueReference);
            ++i;
            if (i == 16) {
               break;
            }
         }

      }

      void clearReferenceQueues() {
         if (this.map.usesKeyReferences()) {
            this.clearKeyReferenceQueue();
         }

         if (this.map.usesValueReferences()) {
            this.clearValueReferenceQueue();
         }

      }

      void clearKeyReferenceQueue() {
         while(this.keyReferenceQueue.poll() != null) {
         }

      }

      void clearValueReferenceQueue() {
         while(this.valueReferenceQueue.poll() != null) {
         }

      }

      void recordRead(ReferenceEntry entry, long now) {
         if (this.map.recordsAccess()) {
            entry.setAccessTime(now);
         }

         this.recencyQueue.add(entry);
      }

      @GuardedBy("this")
      void recordLockedRead(ReferenceEntry entry, long now) {
         if (this.map.recordsAccess()) {
            entry.setAccessTime(now);
         }

         this.accessQueue.add(entry);
      }

      @GuardedBy("this")
      void recordWrite(ReferenceEntry entry, int weight, long now) {
         this.drainRecencyQueue();
         this.totalWeight += (long)weight;
         if (this.map.recordsAccess()) {
            entry.setAccessTime(now);
         }

         if (this.map.recordsWrite()) {
            entry.setWriteTime(now);
         }

         this.accessQueue.add(entry);
         this.writeQueue.add(entry);
      }

      @GuardedBy("this")
      void drainRecencyQueue() {
         ReferenceEntry e;
         while((e = (ReferenceEntry)this.recencyQueue.poll()) != null) {
            if (this.accessQueue.contains(e)) {
               this.accessQueue.add(e);
            }
         }

      }

      void tryExpireEntries(long now) {
         if (this.tryLock()) {
            try {
               this.expireEntries(now);
            } finally {
               this.unlock();
            }
         }

      }

      @GuardedBy("this")
      void expireEntries(long now) {
         this.drainRecencyQueue();

         ReferenceEntry e;
         while((e = (ReferenceEntry)this.writeQueue.peek()) != null && this.map.isExpired(e, now)) {
            if (!this.removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
               throw new AssertionError();
            }
         }

         while((e = (ReferenceEntry)this.accessQueue.peek()) != null && this.map.isExpired(e, now)) {
            if (!this.removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
               throw new AssertionError();
            }
         }

      }

      @GuardedBy("this")
      void enqueueNotification(@Nullable Object key, int hash, @Nullable Object value, int weight, RemovalCause cause) {
         this.totalWeight -= (long)weight;
         if (cause.wasEvicted()) {
            this.statsCounter.recordEviction();
         }

         if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
            RemovalNotification notification = RemovalNotification.create(key, value, cause);
            this.map.removalNotificationQueue.offer(notification);
         }

      }

      @GuardedBy("this")
      void evictEntries(ReferenceEntry newest) {
         if (this.map.evictsBySize()) {
            this.drainRecencyQueue();
            if ((long)newest.getValueReference().getWeight() > this.maxSegmentWeight && !this.removeEntry(newest, newest.getHash(), RemovalCause.SIZE)) {
               throw new AssertionError();
            } else {
               ReferenceEntry e;
               do {
                  if (this.totalWeight <= this.maxSegmentWeight) {
                     return;
                  }

                  e = this.getNextEvictable();
               } while(this.removeEntry(e, e.getHash(), RemovalCause.SIZE));

               throw new AssertionError();
            }
         }
      }

      @GuardedBy("this")
      ReferenceEntry getNextEvictable() {
         Iterator var1 = this.accessQueue.iterator();

         ReferenceEntry e;
         int weight;
         do {
            if (!var1.hasNext()) {
               throw new AssertionError();
            }

            e = (ReferenceEntry)var1.next();
            weight = e.getValueReference().getWeight();
         } while(weight <= 0);

         return e;
      }

      ReferenceEntry getFirst(int hash) {
         AtomicReferenceArray table = this.table;
         return (ReferenceEntry)table.get(hash & table.length() - 1);
      }

      @Nullable
      ReferenceEntry getEntry(Object key, int hash) {
         for(ReferenceEntry e = this.getFirst(hash); e != null; e = e.getNext()) {
            if (e.getHash() == hash) {
               Object entryKey = e.getKey();
               if (entryKey == null) {
                  this.tryDrainReferenceQueues();
               } else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                  return e;
               }
            }
         }

         return null;
      }

      @Nullable
      ReferenceEntry getLiveEntry(Object key, int hash, long now) {
         ReferenceEntry e = this.getEntry(key, hash);
         if (e == null) {
            return null;
         } else if (this.map.isExpired(e, now)) {
            this.tryExpireEntries(now);
            return null;
         } else {
            return e;
         }
      }

      Object getLiveValue(ReferenceEntry entry, long now) {
         if (entry.getKey() == null) {
            this.tryDrainReferenceQueues();
            return null;
         } else {
            Object value = entry.getValueReference().get();
            if (value == null) {
               this.tryDrainReferenceQueues();
               return null;
            } else if (this.map.isExpired(entry, now)) {
               this.tryExpireEntries(now);
               return null;
            } else {
               return value;
            }
         }
      }

      @Nullable
      Object get(Object key, int hash) {
         try {
            if (this.count != 0) {
               long now = this.map.ticker.read();
               ReferenceEntry e = this.getLiveEntry(key, hash, now);
               Object value;
               if (e == null) {
                  value = null;
                  return value;
               }

               value = e.getValueReference().get();
               if (value != null) {
                  this.recordRead(e, now);
                  Object var7 = this.scheduleRefresh(e, e.getKey(), hash, value, now, this.map.defaultLoader);
                  return var7;
               }

               this.tryDrainReferenceQueues();
            }

            Object var8 = null;
            return var8;
         } finally {
            this.postReadCleanup();
         }
      }

      boolean containsKey(Object key, int hash) {
         boolean var6;
         try {
            if (this.count == 0) {
               boolean var7 = false;
               return var7;
            }

            long now = this.map.ticker.read();
            ReferenceEntry e = this.getLiveEntry(key, hash, now);
            if (e == null) {
               var6 = false;
               return var6;
            }

            var6 = e.getValueReference().get() != null;
         } finally {
            this.postReadCleanup();
         }

         return var6;
      }

      @VisibleForTesting
      boolean containsValue(Object value) {
         boolean var10;
         try {
            if (this.count != 0) {
               long now = this.map.ticker.read();
               AtomicReferenceArray table = this.table;
               int length = table.length();

               for(int i = 0; i < length; ++i) {
                  for(ReferenceEntry e = (ReferenceEntry)table.get(i); e != null; e = e.getNext()) {
                     Object entryValue = this.getLiveValue(e, now);
                     if (entryValue != null && this.map.valueEquivalence.equivalent(value, entryValue)) {
                        boolean var9 = true;
                        return var9;
                     }
                  }
               }
            }

            var10 = false;
         } finally {
            this.postReadCleanup();
         }

         return var10;
      }

      @Nullable
      Object put(Object key, int hash, Object value, boolean onlyIfAbsent) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count + 1;
            if (newCount > this.threshold) {
               this.expand();
               newCount = this.count + 1;
            }

            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            ReferenceEntry e;
            Object entryKey;
            for(e = first; e != null; e = e.getNext()) {
               entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference valueReference = e.getValueReference();
                  Object entryValue = valueReference.get();
                  Object var15;
                  if (entryValue == null) {
                     ++this.modCount;
                     if (valueReference.isActive()) {
                        this.enqueueNotification(key, hash, entryValue, valueReference.getWeight(), RemovalCause.COLLECTED);
                        this.setValue(e, key, value, now);
                        newCount = this.count;
                     } else {
                        this.setValue(e, key, value, now);
                        newCount = this.count + 1;
                     }

                     this.count = newCount;
                     this.evictEntries(e);
                     var15 = null;
                     return var15;
                  }

                  if (onlyIfAbsent) {
                     this.recordLockedRead(e, now);
                     var15 = entryValue;
                     return var15;
                  }

                  ++this.modCount;
                  this.enqueueNotification(key, hash, entryValue, valueReference.getWeight(), RemovalCause.REPLACED);
                  this.setValue(e, key, value, now);
                  this.evictEntries(e);
                  var15 = entryValue;
                  return var15;
               }
            }

            ++this.modCount;
            e = this.newEntry(key, hash, first);
            this.setValue(e, key, value, now);
            table.set(index, e);
            newCount = this.count + 1;
            this.count = newCount;
            this.evictEntries(e);
            entryKey = null;
            return entryKey;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      @GuardedBy("this")
      void expand() {
         AtomicReferenceArray oldTable = this.table;
         int oldCapacity = oldTable.length();
         if (oldCapacity < 1073741824) {
            int newCount = this.count;
            AtomicReferenceArray newTable = this.newEntryArray(oldCapacity << 1);
            this.threshold = newTable.length() * 3 / 4;
            int newMask = newTable.length() - 1;

            for(int oldIndex = 0; oldIndex < oldCapacity; ++oldIndex) {
               ReferenceEntry head = (ReferenceEntry)oldTable.get(oldIndex);
               if (head != null) {
                  ReferenceEntry next = head.getNext();
                  int headIndex = head.getHash() & newMask;
                  if (next == null) {
                     newTable.set(headIndex, head);
                  } else {
                     ReferenceEntry tail = head;
                     int tailIndex = headIndex;

                     ReferenceEntry e;
                     int newIndex;
                     for(e = next; e != null; e = e.getNext()) {
                        newIndex = e.getHash() & newMask;
                        if (newIndex != tailIndex) {
                           tailIndex = newIndex;
                           tail = e;
                        }
                     }

                     newTable.set(tailIndex, tail);

                     for(e = head; e != tail; e = e.getNext()) {
                        newIndex = e.getHash() & newMask;
                        ReferenceEntry newNext = (ReferenceEntry)newTable.get(newIndex);
                        ReferenceEntry newFirst = this.copyEntry(e, newNext);
                        if (newFirst != null) {
                           newTable.set(newIndex, newFirst);
                        } else {
                           this.removeCollectedEntry(e);
                           --newCount;
                        }
                     }
                  }
               }
            }

            this.table = newTable;
            this.count = newCount;
         }
      }

      boolean replace(Object key, int hash, Object oldValue, Object newValue) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            for(ReferenceEntry e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference valueReference = e.getValueReference();
                  Object entryValue = valueReference.get();
                  boolean var14;
                  if (entryValue == null) {
                     if (valueReference.isActive()) {
                        int newCount = this.count - 1;
                        ++this.modCount;
                        ReferenceEntry newFirst = this.removeValueFromChain(first, e, entryKey, hash, entryValue, valueReference, RemovalCause.COLLECTED);
                        newCount = this.count - 1;
                        table.set(index, newFirst);
                        this.count = newCount;
                     }

                     var14 = false;
                     return var14;
                  }

                  if (!this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
                     this.recordLockedRead(e, now);
                     var14 = false;
                     return var14;
                  }

                  ++this.modCount;
                  this.enqueueNotification(key, hash, entryValue, valueReference.getWeight(), RemovalCause.REPLACED);
                  this.setValue(e, key, newValue, now);
                  this.evictEntries(e);
                  var14 = true;
                  return var14;
               }
            }

            boolean var19 = false;
            return var19;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      @Nullable
      Object replace(Object key, int hash, Object newValue) {
         this.lock();

         ReferenceEntry e;
         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            for(e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference valueReference = e.getValueReference();
                  Object entryValue = valueReference.get();
                  Object var13;
                  if (entryValue == null) {
                     if (valueReference.isActive()) {
                        int newCount = this.count - 1;
                        ++this.modCount;
                        ReferenceEntry newFirst = this.removeValueFromChain(first, e, entryKey, hash, entryValue, valueReference, RemovalCause.COLLECTED);
                        newCount = this.count - 1;
                        table.set(index, newFirst);
                        this.count = newCount;
                     }

                     var13 = null;
                     return var13;
                  }

                  ++this.modCount;
                  this.enqueueNotification(key, hash, entryValue, valueReference.getWeight(), RemovalCause.REPLACED);
                  this.setValue(e, key, newValue, now);
                  this.evictEntries(e);
                  var13 = entryValue;
                  return var13;
               }
            }

            e = null;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }

         return e;
      }

      @Nullable
      Object remove(Object key, int hash) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            ReferenceEntry e;
            for(e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference valueReference = e.getValueReference();
                  Object entryValue = valueReference.get();
                  RemovalCause cause;
                  ReferenceEntry newFirst;
                  if (entryValue != null) {
                     cause = RemovalCause.EXPLICIT;
                  } else {
                     if (!valueReference.isActive()) {
                        newFirst = null;
                        return newFirst;
                     }

                     cause = RemovalCause.COLLECTED;
                  }

                  ++this.modCount;
                  newFirst = this.removeValueFromChain(first, e, entryKey, hash, entryValue, valueReference, cause);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  Object var15 = entryValue;
                  return var15;
               }
            }

            e = null;
            return e;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      boolean storeLoadedValue(Object key, int hash, LoadingValueReference oldValueReference, Object newValue) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count + 1;
            if (newCount > this.threshold) {
               this.expand();
               newCount = this.count + 1;
            }

            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            ReferenceEntry e;
            for(e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference valueReference = e.getValueReference();
                  Object entryValue = valueReference.get();
                  boolean var15;
                  if (oldValueReference == valueReference || entryValue == null && valueReference != LocalCache.UNSET) {
                     ++this.modCount;
                     if (oldValueReference.isActive()) {
                        RemovalCause cause = entryValue == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED;
                        this.enqueueNotification(key, hash, entryValue, oldValueReference.getWeight(), cause);
                        --newCount;
                     }

                     this.setValue(e, key, newValue, now);
                     this.count = newCount;
                     this.evictEntries(e);
                     var15 = true;
                     return var15;
                  }

                  this.enqueueNotification(key, hash, newValue, 0, RemovalCause.REPLACED);
                  var15 = false;
                  return var15;
               }
            }

            ++this.modCount;
            e = this.newEntry(key, hash, first);
            this.setValue(e, key, newValue, now);
            table.set(index, e);
            this.count = newCount;
            this.evictEntries(e);
            boolean var19 = true;
            return var19;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      boolean remove(Object key, int hash, Object value) {
         this.lock();

         boolean var20;
         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            for(ReferenceEntry e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference valueReference = e.getValueReference();
                  Object entryValue = valueReference.get();
                  RemovalCause cause;
                  if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                     cause = RemovalCause.EXPLICIT;
                  } else {
                     if (entryValue != null || !valueReference.isActive()) {
                        boolean var15 = false;
                        return var15;
                     }

                     cause = RemovalCause.COLLECTED;
                  }

                  ++this.modCount;
                  ReferenceEntry newFirst = this.removeValueFromChain(first, e, entryKey, hash, entryValue, valueReference, cause);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  boolean var16 = cause == RemovalCause.EXPLICIT;
                  return var16;
               }
            }

            var20 = false;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }

         return var20;
      }

      void clear() {
         if (this.count != 0) {
            this.lock();

            try {
               long now = this.map.ticker.read();
               this.preWriteCleanup(now);
               AtomicReferenceArray table = this.table;

               int i;
               for(i = 0; i < table.length(); ++i) {
                  for(ReferenceEntry e = (ReferenceEntry)table.get(i); e != null; e = e.getNext()) {
                     if (e.getValueReference().isActive()) {
                        Object key = e.getKey();
                        Object value = e.getValueReference().get();
                        RemovalCause cause = key != null && value != null ? RemovalCause.EXPLICIT : RemovalCause.COLLECTED;
                        this.enqueueNotification(key, e.getHash(), value, e.getValueReference().getWeight(), cause);
                     }
                  }
               }

               for(i = 0; i < table.length(); ++i) {
                  table.set(i, (Object)null);
               }

               this.clearReferenceQueues();
               this.writeQueue.clear();
               this.accessQueue.clear();
               this.readCount.set(0);
               ++this.modCount;
               this.count = 0;
            } finally {
               this.unlock();
               this.postWriteCleanup();
            }
         }

      }

      @Nullable
      @GuardedBy("this")
      ReferenceEntry removeValueFromChain(ReferenceEntry first, ReferenceEntry entry, @Nullable Object key, int hash, Object value, ValueReference valueReference, RemovalCause cause) {
         this.enqueueNotification(key, hash, value, valueReference.getWeight(), cause);
         this.writeQueue.remove(entry);
         this.accessQueue.remove(entry);
         if (valueReference.isLoading()) {
            valueReference.notifyNewValue((Object)null);
            return first;
         } else {
            return this.removeEntryFromChain(first, entry);
         }
      }

      @Nullable
      @GuardedBy("this")
      ReferenceEntry removeEntryFromChain(ReferenceEntry first, ReferenceEntry entry) {
         int newCount = this.count;
         ReferenceEntry newFirst = entry.getNext();

         for(ReferenceEntry e = first; e != entry; e = e.getNext()) {
            ReferenceEntry next = this.copyEntry(e, newFirst);
            if (next != null) {
               newFirst = next;
            } else {
               this.removeCollectedEntry(e);
               --newCount;
            }
         }

         this.count = newCount;
         return newFirst;
      }

      @GuardedBy("this")
      void removeCollectedEntry(ReferenceEntry entry) {
         this.enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference().get(), entry.getValueReference().getWeight(), RemovalCause.COLLECTED);
         this.writeQueue.remove(entry);
         this.accessQueue.remove(entry);
      }

      boolean reclaimKey(ReferenceEntry entry, int hash) {
         this.lock();

         try {
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            for(ReferenceEntry e = first; e != null; e = e.getNext()) {
               if (e == entry) {
                  ++this.modCount;
                  ReferenceEntry newFirst = this.removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference().get(), e.getValueReference(), RemovalCause.COLLECTED);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  boolean var9 = true;
                  return var9;
               }
            }

            boolean var13 = false;
            return var13;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      boolean reclaimValue(Object key, int hash, ValueReference valueReference) {
         this.lock();

         boolean var16;
         try {
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            for(ReferenceEntry e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference v = e.getValueReference();
                  if (v == valueReference) {
                     ++this.modCount;
                     ReferenceEntry newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference.get(), valueReference, RemovalCause.COLLECTED);
                     newCount = this.count - 1;
                     table.set(index, newFirst);
                     this.count = newCount;
                     boolean var12 = true;
                     return var12;
                  }

                  boolean var11 = false;
                  return var11;
               }
            }

            var16 = false;
         } finally {
            this.unlock();
            if (!this.isHeldByCurrentThread()) {
               this.postWriteCleanup();
            }

         }

         return var16;
      }

      boolean removeLoadingValue(Object key, int hash, LoadingValueReference valueReference) {
         this.lock();

         boolean var15;
         try {
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            ReferenceEntry first = (ReferenceEntry)table.get(index);

            for(ReferenceEntry e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  ValueReference v = e.getValueReference();
                  boolean var10;
                  if (v == valueReference) {
                     if (valueReference.isActive()) {
                        e.setValueReference(valueReference.getOldValue());
                     } else {
                        ReferenceEntry newFirst = this.removeEntryFromChain(first, e);
                        table.set(index, newFirst);
                     }

                     var10 = true;
                     return var10;
                  }

                  var10 = false;
                  return var10;
               }
            }

            var15 = false;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }

         return var15;
      }

      @VisibleForTesting
      @GuardedBy("this")
      boolean removeEntry(ReferenceEntry entry, int hash, RemovalCause cause) {
         int newCount = this.count - 1;
         AtomicReferenceArray table = this.table;
         int index = hash & table.length() - 1;
         ReferenceEntry first = (ReferenceEntry)table.get(index);

         for(ReferenceEntry e = first; e != null; e = e.getNext()) {
            if (e == entry) {
               ++this.modCount;
               ReferenceEntry newFirst = this.removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference().get(), e.getValueReference(), cause);
               newCount = this.count - 1;
               table.set(index, newFirst);
               this.count = newCount;
               return true;
            }
         }

         return false;
      }

      void postReadCleanup() {
         if ((this.readCount.incrementAndGet() & 63) == 0) {
            this.cleanUp();
         }

      }

      @GuardedBy("this")
      void preWriteCleanup(long now) {
         this.runLockedCleanup(now);
      }

      void postWriteCleanup() {
         this.runUnlockedCleanup();
      }

      void cleanUp() {
         long now = this.map.ticker.read();
         this.runLockedCleanup(now);
         this.runUnlockedCleanup();
      }

      void runLockedCleanup(long now) {
         if (this.tryLock()) {
            try {
               this.drainReferenceQueues();
               this.expireEntries(now);
               this.readCount.set(0);
            } finally {
               this.unlock();
            }
         }

      }

      void runUnlockedCleanup() {
         if (!this.isHeldByCurrentThread()) {
            this.map.processPendingNotifications();
         }

      }
   }

   static final class WeightedStrongValueReference extends StrongValueReference {
      final int weight;

      WeightedStrongValueReference(Object referent, int weight) {
         super(referent);
         this.weight = weight;
      }

      public int getWeight() {
         return this.weight;
      }
   }

   static final class WeightedSoftValueReference extends SoftValueReference {
      final int weight;

      WeightedSoftValueReference(ReferenceQueue queue, Object referent, ReferenceEntry entry, int weight) {
         super(queue, referent, entry);
         this.weight = weight;
      }

      public int getWeight() {
         return this.weight;
      }

      public ValueReference copyFor(ReferenceQueue queue, Object value, ReferenceEntry entry) {
         return new WeightedSoftValueReference(queue, value, entry, this.weight);
      }
   }

   static final class WeightedWeakValueReference extends WeakValueReference {
      final int weight;

      WeightedWeakValueReference(ReferenceQueue queue, Object referent, ReferenceEntry entry, int weight) {
         super(queue, referent, entry);
         this.weight = weight;
      }

      public int getWeight() {
         return this.weight;
      }

      public ValueReference copyFor(ReferenceQueue queue, Object value, ReferenceEntry entry) {
         return new WeightedWeakValueReference(queue, value, entry, this.weight);
      }
   }

   static class StrongValueReference implements ValueReference {
      final Object referent;

      StrongValueReference(Object referent) {
         this.referent = referent;
      }

      public Object get() {
         return this.referent;
      }

      public int getWeight() {
         return 1;
      }

      public ReferenceEntry getEntry() {
         return null;
      }

      public ValueReference copyFor(ReferenceQueue queue, Object value, ReferenceEntry entry) {
         return this;
      }

      public boolean isLoading() {
         return false;
      }

      public boolean isActive() {
         return true;
      }

      public Object waitForValue() {
         return this.get();
      }

      public void notifyNewValue(Object newValue) {
      }
   }

   static class SoftValueReference extends SoftReference implements ValueReference {
      final ReferenceEntry entry;

      SoftValueReference(ReferenceQueue queue, Object referent, ReferenceEntry entry) {
         super(referent, queue);
         this.entry = entry;
      }

      public int getWeight() {
         return 1;
      }

      public ReferenceEntry getEntry() {
         return this.entry;
      }

      public void notifyNewValue(Object newValue) {
      }

      public ValueReference copyFor(ReferenceQueue queue, Object value, ReferenceEntry entry) {
         return new SoftValueReference(queue, value, entry);
      }

      public boolean isLoading() {
         return false;
      }

      public boolean isActive() {
         return true;
      }

      public Object waitForValue() {
         return this.get();
      }
   }

   static class WeakValueReference extends WeakReference implements ValueReference {
      final ReferenceEntry entry;

      WeakValueReference(ReferenceQueue queue, Object referent, ReferenceEntry entry) {
         super(referent, queue);
         this.entry = entry;
      }

      public int getWeight() {
         return 1;
      }

      public ReferenceEntry getEntry() {
         return this.entry;
      }

      public void notifyNewValue(Object newValue) {
      }

      public ValueReference copyFor(ReferenceQueue queue, Object value, ReferenceEntry entry) {
         return new WeakValueReference(queue, value, entry);
      }

      public boolean isLoading() {
         return false;
      }

      public boolean isActive() {
         return true;
      }

      public Object waitForValue() {
         return this.get();
      }
   }

   static final class WeakAccessWriteEntry extends WeakEntry {
      volatile long accessTime = Long.MAX_VALUE;
      ReferenceEntry nextAccess = LocalCache.nullEntry();
      ReferenceEntry previousAccess = LocalCache.nullEntry();
      volatile long writeTime = Long.MAX_VALUE;
      ReferenceEntry nextWrite = LocalCache.nullEntry();
      ReferenceEntry previousWrite = LocalCache.nullEntry();

      WeakAccessWriteEntry(ReferenceQueue queue, Object key, int hash, @Nullable ReferenceEntry next) {
         super(queue, key, hash, next);
      }

      public long getAccessTime() {
         return this.accessTime;
      }

      public void setAccessTime(long time) {
         this.accessTime = time;
      }

      public ReferenceEntry getNextInAccessQueue() {
         return this.nextAccess;
      }

      public void setNextInAccessQueue(ReferenceEntry next) {
         this.nextAccess = next;
      }

      public ReferenceEntry getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      public void setPreviousInAccessQueue(ReferenceEntry previous) {
         this.previousAccess = previous;
      }

      public long getWriteTime() {
         return this.writeTime;
      }

      public void setWriteTime(long time) {
         this.writeTime = time;
      }

      public ReferenceEntry getNextInWriteQueue() {
         return this.nextWrite;
      }

      public void setNextInWriteQueue(ReferenceEntry next) {
         this.nextWrite = next;
      }

      public ReferenceEntry getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      public void setPreviousInWriteQueue(ReferenceEntry previous) {
         this.previousWrite = previous;
      }
   }

   static final class WeakWriteEntry extends WeakEntry {
      volatile long writeTime = Long.MAX_VALUE;
      ReferenceEntry nextWrite = LocalCache.nullEntry();
      ReferenceEntry previousWrite = LocalCache.nullEntry();

      WeakWriteEntry(ReferenceQueue queue, Object key, int hash, @Nullable ReferenceEntry next) {
         super(queue, key, hash, next);
      }

      public long getWriteTime() {
         return this.writeTime;
      }

      public void setWriteTime(long time) {
         this.writeTime = time;
      }

      public ReferenceEntry getNextInWriteQueue() {
         return this.nextWrite;
      }

      public void setNextInWriteQueue(ReferenceEntry next) {
         this.nextWrite = next;
      }

      public ReferenceEntry getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      public void setPreviousInWriteQueue(ReferenceEntry previous) {
         this.previousWrite = previous;
      }
   }

   static final class WeakAccessEntry extends WeakEntry {
      volatile long accessTime = Long.MAX_VALUE;
      ReferenceEntry nextAccess = LocalCache.nullEntry();
      ReferenceEntry previousAccess = LocalCache.nullEntry();

      WeakAccessEntry(ReferenceQueue queue, Object key, int hash, @Nullable ReferenceEntry next) {
         super(queue, key, hash, next);
      }

      public long getAccessTime() {
         return this.accessTime;
      }

      public void setAccessTime(long time) {
         this.accessTime = time;
      }

      public ReferenceEntry getNextInAccessQueue() {
         return this.nextAccess;
      }

      public void setNextInAccessQueue(ReferenceEntry next) {
         this.nextAccess = next;
      }

      public ReferenceEntry getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      public void setPreviousInAccessQueue(ReferenceEntry previous) {
         this.previousAccess = previous;
      }
   }

   static class WeakEntry extends WeakReference implements ReferenceEntry {
      final int hash;
      final ReferenceEntry next;
      volatile ValueReference valueReference = LocalCache.unset();

      WeakEntry(ReferenceQueue queue, Object key, int hash, @Nullable ReferenceEntry next) {
         super(key, queue);
         this.hash = hash;
         this.next = next;
      }

      public Object getKey() {
         return this.get();
      }

      public long getAccessTime() {
         throw new UnsupportedOperationException();
      }

      public void setAccessTime(long time) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getNextInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      public void setNextInAccessQueue(ReferenceEntry next) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getPreviousInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      public void setPreviousInAccessQueue(ReferenceEntry previous) {
         throw new UnsupportedOperationException();
      }

      public long getWriteTime() {
         throw new UnsupportedOperationException();
      }

      public void setWriteTime(long time) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getNextInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      public void setNextInWriteQueue(ReferenceEntry next) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getPreviousInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      public void setPreviousInWriteQueue(ReferenceEntry previous) {
         throw new UnsupportedOperationException();
      }

      public ValueReference getValueReference() {
         return this.valueReference;
      }

      public void setValueReference(ValueReference valueReference) {
         this.valueReference = valueReference;
      }

      public int getHash() {
         return this.hash;
      }

      public ReferenceEntry getNext() {
         return this.next;
      }
   }

   static final class StrongAccessWriteEntry extends StrongEntry {
      volatile long accessTime = Long.MAX_VALUE;
      ReferenceEntry nextAccess = LocalCache.nullEntry();
      ReferenceEntry previousAccess = LocalCache.nullEntry();
      volatile long writeTime = Long.MAX_VALUE;
      ReferenceEntry nextWrite = LocalCache.nullEntry();
      ReferenceEntry previousWrite = LocalCache.nullEntry();

      StrongAccessWriteEntry(Object key, int hash, @Nullable ReferenceEntry next) {
         super(key, hash, next);
      }

      public long getAccessTime() {
         return this.accessTime;
      }

      public void setAccessTime(long time) {
         this.accessTime = time;
      }

      public ReferenceEntry getNextInAccessQueue() {
         return this.nextAccess;
      }

      public void setNextInAccessQueue(ReferenceEntry next) {
         this.nextAccess = next;
      }

      public ReferenceEntry getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      public void setPreviousInAccessQueue(ReferenceEntry previous) {
         this.previousAccess = previous;
      }

      public long getWriteTime() {
         return this.writeTime;
      }

      public void setWriteTime(long time) {
         this.writeTime = time;
      }

      public ReferenceEntry getNextInWriteQueue() {
         return this.nextWrite;
      }

      public void setNextInWriteQueue(ReferenceEntry next) {
         this.nextWrite = next;
      }

      public ReferenceEntry getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      public void setPreviousInWriteQueue(ReferenceEntry previous) {
         this.previousWrite = previous;
      }
   }

   static final class StrongWriteEntry extends StrongEntry {
      volatile long writeTime = Long.MAX_VALUE;
      ReferenceEntry nextWrite = LocalCache.nullEntry();
      ReferenceEntry previousWrite = LocalCache.nullEntry();

      StrongWriteEntry(Object key, int hash, @Nullable ReferenceEntry next) {
         super(key, hash, next);
      }

      public long getWriteTime() {
         return this.writeTime;
      }

      public void setWriteTime(long time) {
         this.writeTime = time;
      }

      public ReferenceEntry getNextInWriteQueue() {
         return this.nextWrite;
      }

      public void setNextInWriteQueue(ReferenceEntry next) {
         this.nextWrite = next;
      }

      public ReferenceEntry getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      public void setPreviousInWriteQueue(ReferenceEntry previous) {
         this.previousWrite = previous;
      }
   }

   static final class StrongAccessEntry extends StrongEntry {
      volatile long accessTime = Long.MAX_VALUE;
      ReferenceEntry nextAccess = LocalCache.nullEntry();
      ReferenceEntry previousAccess = LocalCache.nullEntry();

      StrongAccessEntry(Object key, int hash, @Nullable ReferenceEntry next) {
         super(key, hash, next);
      }

      public long getAccessTime() {
         return this.accessTime;
      }

      public void setAccessTime(long time) {
         this.accessTime = time;
      }

      public ReferenceEntry getNextInAccessQueue() {
         return this.nextAccess;
      }

      public void setNextInAccessQueue(ReferenceEntry next) {
         this.nextAccess = next;
      }

      public ReferenceEntry getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      public void setPreviousInAccessQueue(ReferenceEntry previous) {
         this.previousAccess = previous;
      }
   }

   static class StrongEntry extends AbstractReferenceEntry {
      final Object key;
      final int hash;
      final ReferenceEntry next;
      volatile ValueReference valueReference = LocalCache.unset();

      StrongEntry(Object key, int hash, @Nullable ReferenceEntry next) {
         this.key = key;
         this.hash = hash;
         this.next = next;
      }

      public Object getKey() {
         return this.key;
      }

      public ValueReference getValueReference() {
         return this.valueReference;
      }

      public void setValueReference(ValueReference valueReference) {
         this.valueReference = valueReference;
      }

      public int getHash() {
         return this.hash;
      }

      public ReferenceEntry getNext() {
         return this.next;
      }
   }

   abstract static class AbstractReferenceEntry implements ReferenceEntry {
      public ValueReference getValueReference() {
         throw new UnsupportedOperationException();
      }

      public void setValueReference(ValueReference valueReference) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getNext() {
         throw new UnsupportedOperationException();
      }

      public int getHash() {
         throw new UnsupportedOperationException();
      }

      public Object getKey() {
         throw new UnsupportedOperationException();
      }

      public long getAccessTime() {
         throw new UnsupportedOperationException();
      }

      public void setAccessTime(long time) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getNextInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      public void setNextInAccessQueue(ReferenceEntry next) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getPreviousInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      public void setPreviousInAccessQueue(ReferenceEntry previous) {
         throw new UnsupportedOperationException();
      }

      public long getWriteTime() {
         throw new UnsupportedOperationException();
      }

      public void setWriteTime(long time) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getNextInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      public void setNextInWriteQueue(ReferenceEntry next) {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry getPreviousInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      public void setPreviousInWriteQueue(ReferenceEntry previous) {
         throw new UnsupportedOperationException();
      }
   }

   private static enum NullEntry implements ReferenceEntry {
      INSTANCE;

      public ValueReference getValueReference() {
         return null;
      }

      public void setValueReference(ValueReference valueReference) {
      }

      public ReferenceEntry getNext() {
         return null;
      }

      public int getHash() {
         return 0;
      }

      public Object getKey() {
         return null;
      }

      public long getAccessTime() {
         return 0L;
      }

      public void setAccessTime(long time) {
      }

      public ReferenceEntry getNextInAccessQueue() {
         return this;
      }

      public void setNextInAccessQueue(ReferenceEntry next) {
      }

      public ReferenceEntry getPreviousInAccessQueue() {
         return this;
      }

      public void setPreviousInAccessQueue(ReferenceEntry previous) {
      }

      public long getWriteTime() {
         return 0L;
      }

      public void setWriteTime(long time) {
      }

      public ReferenceEntry getNextInWriteQueue() {
         return this;
      }

      public void setNextInWriteQueue(ReferenceEntry next) {
      }

      public ReferenceEntry getPreviousInWriteQueue() {
         return this;
      }

      public void setPreviousInWriteQueue(ReferenceEntry previous) {
      }
   }

   interface ReferenceEntry {
      ValueReference getValueReference();

      void setValueReference(ValueReference var1);

      @Nullable
      ReferenceEntry getNext();

      int getHash();

      @Nullable
      Object getKey();

      long getAccessTime();

      void setAccessTime(long var1);

      ReferenceEntry getNextInAccessQueue();

      void setNextInAccessQueue(ReferenceEntry var1);

      ReferenceEntry getPreviousInAccessQueue();

      void setPreviousInAccessQueue(ReferenceEntry var1);

      long getWriteTime();

      void setWriteTime(long var1);

      ReferenceEntry getNextInWriteQueue();

      void setNextInWriteQueue(ReferenceEntry var1);

      ReferenceEntry getPreviousInWriteQueue();

      void setPreviousInWriteQueue(ReferenceEntry var1);
   }

   interface ValueReference {
      @Nullable
      Object get();

      Object waitForValue() throws ExecutionException;

      int getWeight();

      @Nullable
      ReferenceEntry getEntry();

      ValueReference copyFor(ReferenceQueue var1, @Nullable Object var2, ReferenceEntry var3);

      void notifyNewValue(@Nullable Object var1);

      boolean isLoading();

      boolean isActive();
   }

   static enum EntryFactory {
      STRONG {
         ReferenceEntry newEntry(Segment segment, Object key, int hash, @Nullable ReferenceEntry next) {
            return new StrongEntry(key, hash, next);
         }
      },
      STRONG_ACCESS {
         ReferenceEntry newEntry(Segment segment, Object key, int hash, @Nullable ReferenceEntry next) {
            return new StrongAccessEntry(key, hash, next);
         }

         ReferenceEntry copyEntry(Segment segment, ReferenceEntry original, ReferenceEntry newNext) {
            ReferenceEntry newEntry = super.copyEntry(segment, original, newNext);
            this.copyAccessEntry(original, newEntry);
            return newEntry;
         }
      },
      STRONG_WRITE {
         ReferenceEntry newEntry(Segment segment, Object key, int hash, @Nullable ReferenceEntry next) {
            return new StrongWriteEntry(key, hash, next);
         }

         ReferenceEntry copyEntry(Segment segment, ReferenceEntry original, ReferenceEntry newNext) {
            ReferenceEntry newEntry = super.copyEntry(segment, original, newNext);
            this.copyWriteEntry(original, newEntry);
            return newEntry;
         }
      },
      STRONG_ACCESS_WRITE {
         ReferenceEntry newEntry(Segment segment, Object key, int hash, @Nullable ReferenceEntry next) {
            return new StrongAccessWriteEntry(key, hash, next);
         }

         ReferenceEntry copyEntry(Segment segment, ReferenceEntry original, ReferenceEntry newNext) {
            ReferenceEntry newEntry = super.copyEntry(segment, original, newNext);
            this.copyAccessEntry(original, newEntry);
            this.copyWriteEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK {
         ReferenceEntry newEntry(Segment segment, Object key, int hash, @Nullable ReferenceEntry next) {
            return new WeakEntry(segment.keyReferenceQueue, key, hash, next);
         }
      },
      WEAK_ACCESS {
         ReferenceEntry newEntry(Segment segment, Object key, int hash, @Nullable ReferenceEntry next) {
            return new WeakAccessEntry(segment.keyReferenceQueue, key, hash, next);
         }

         ReferenceEntry copyEntry(Segment segment, ReferenceEntry original, ReferenceEntry newNext) {
            ReferenceEntry newEntry = super.copyEntry(segment, original, newNext);
            this.copyAccessEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK_WRITE {
         ReferenceEntry newEntry(Segment segment, Object key, int hash, @Nullable ReferenceEntry next) {
            return new WeakWriteEntry(segment.keyReferenceQueue, key, hash, next);
         }

         ReferenceEntry copyEntry(Segment segment, ReferenceEntry original, ReferenceEntry newNext) {
            ReferenceEntry newEntry = super.copyEntry(segment, original, newNext);
            this.copyWriteEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK_ACCESS_WRITE {
         ReferenceEntry newEntry(Segment segment, Object key, int hash, @Nullable ReferenceEntry next) {
            return new WeakAccessWriteEntry(segment.keyReferenceQueue, key, hash, next);
         }

         ReferenceEntry copyEntry(Segment segment, ReferenceEntry original, ReferenceEntry newNext) {
            ReferenceEntry newEntry = super.copyEntry(segment, original, newNext);
            this.copyAccessEntry(original, newEntry);
            this.copyWriteEntry(original, newEntry);
            return newEntry;
         }
      };

      static final int ACCESS_MASK = 1;
      static final int WRITE_MASK = 2;
      static final int WEAK_MASK = 4;
      static final EntryFactory[] factories = new EntryFactory[]{STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE};

      private EntryFactory() {
      }

      static EntryFactory getFactory(Strength keyStrength, boolean usesAccessQueue, boolean usesWriteQueue) {
         int flags = (keyStrength == LocalCache.Strength.WEAK ? 4 : 0) | (usesAccessQueue ? 1 : 0) | (usesWriteQueue ? 2 : 0);
         return factories[flags];
      }

      abstract ReferenceEntry newEntry(Segment var1, Object var2, int var3, @Nullable ReferenceEntry var4);

      ReferenceEntry copyEntry(Segment segment, ReferenceEntry original, ReferenceEntry newNext) {
         return this.newEntry(segment, original.getKey(), original.getHash(), newNext);
      }

      void copyAccessEntry(ReferenceEntry original, ReferenceEntry newEntry) {
         newEntry.setAccessTime(original.getAccessTime());
         LocalCache.connectAccessOrder(original.getPreviousInAccessQueue(), newEntry);
         LocalCache.connectAccessOrder(newEntry, original.getNextInAccessQueue());
         LocalCache.nullifyAccessOrder(original);
      }

      void copyWriteEntry(ReferenceEntry original, ReferenceEntry newEntry) {
         newEntry.setWriteTime(original.getWriteTime());
         LocalCache.connectWriteOrder(original.getPreviousInWriteQueue(), newEntry);
         LocalCache.connectWriteOrder(newEntry, original.getNextInWriteQueue());
         LocalCache.nullifyWriteOrder(original);
      }

      // $FF: synthetic method
      EntryFactory(Object x2) {
         this();
      }
   }

   static enum Strength {
      STRONG {
         ValueReference referenceValue(Segment segment, ReferenceEntry entry, Object value, int weight) {
            return (ValueReference)(weight == 1 ? new StrongValueReference(value) : new WeightedStrongValueReference(value, weight));
         }

         Equivalence defaultEquivalence() {
            return Equivalence.equals();
         }
      },
      SOFT {
         ValueReference referenceValue(Segment segment, ReferenceEntry entry, Object value, int weight) {
            return (ValueReference)(weight == 1 ? new SoftValueReference(segment.valueReferenceQueue, value, entry) : new WeightedSoftValueReference(segment.valueReferenceQueue, value, entry, weight));
         }

         Equivalence defaultEquivalence() {
            return Equivalence.identity();
         }
      },
      WEAK {
         ValueReference referenceValue(Segment segment, ReferenceEntry entry, Object value, int weight) {
            return (ValueReference)(weight == 1 ? new WeakValueReference(segment.valueReferenceQueue, value, entry) : new WeightedWeakValueReference(segment.valueReferenceQueue, value, entry, weight));
         }

         Equivalence defaultEquivalence() {
            return Equivalence.identity();
         }
      };

      private Strength() {
      }

      abstract ValueReference referenceValue(Segment var1, ReferenceEntry var2, Object var3, int var4);

      abstract Equivalence defaultEquivalence();

      // $FF: synthetic method
      Strength(Object x2) {
         this();
      }
   }
}
