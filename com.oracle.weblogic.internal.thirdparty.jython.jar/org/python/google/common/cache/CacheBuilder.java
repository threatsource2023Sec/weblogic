package org.python.google.common.cache;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Ascii;
import org.python.google.common.base.Equivalence;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Supplier;
import org.python.google.common.base.Suppliers;
import org.python.google.common.base.Ticker;

@GwtCompatible(
   emulated = true
)
public final class CacheBuilder {
   private static final int DEFAULT_INITIAL_CAPACITY = 16;
   private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
   private static final int DEFAULT_EXPIRATION_NANOS = 0;
   private static final int DEFAULT_REFRESH_NANOS = 0;
   static final Supplier NULL_STATS_COUNTER = Suppliers.ofInstance(new AbstractCache.StatsCounter() {
      public void recordHits(int count) {
      }

      public void recordMisses(int count) {
      }

      public void recordLoadSuccess(long loadTime) {
      }

      public void recordLoadException(long loadTime) {
      }

      public void recordEviction() {
      }

      public CacheStats snapshot() {
         return CacheBuilder.EMPTY_STATS;
      }
   });
   static final CacheStats EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
   static final Supplier CACHE_STATS_COUNTER = new Supplier() {
      public AbstractCache.StatsCounter get() {
         return new AbstractCache.SimpleStatsCounter();
      }
   };
   static final Ticker NULL_TICKER = new Ticker() {
      public long read() {
         return 0L;
      }
   };
   private static final Logger logger = Logger.getLogger(CacheBuilder.class.getName());
   static final int UNSET_INT = -1;
   boolean strictParsing = true;
   int initialCapacity = -1;
   int concurrencyLevel = -1;
   long maximumSize = -1L;
   long maximumWeight = -1L;
   Weigher weigher;
   LocalCache.Strength keyStrength;
   LocalCache.Strength valueStrength;
   long expireAfterWriteNanos = -1L;
   long expireAfterAccessNanos = -1L;
   long refreshNanos = -1L;
   Equivalence keyEquivalence;
   Equivalence valueEquivalence;
   RemovalListener removalListener;
   Ticker ticker;
   Supplier statsCounterSupplier;

   CacheBuilder() {
      this.statsCounterSupplier = NULL_STATS_COUNTER;
   }

   public static CacheBuilder newBuilder() {
      return new CacheBuilder();
   }

   @GwtIncompatible
   public static CacheBuilder from(CacheBuilderSpec spec) {
      return spec.toCacheBuilder().lenientParsing();
   }

   @GwtIncompatible
   public static CacheBuilder from(String spec) {
      return from(CacheBuilderSpec.parse(spec));
   }

   @GwtIncompatible
   CacheBuilder lenientParsing() {
      this.strictParsing = false;
      return this;
   }

   @GwtIncompatible
   CacheBuilder keyEquivalence(Equivalence equivalence) {
      Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", (Object)this.keyEquivalence);
      this.keyEquivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
      return this;
   }

   Equivalence getKeyEquivalence() {
      return (Equivalence)MoreObjects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
   }

   @GwtIncompatible
   CacheBuilder valueEquivalence(Equivalence equivalence) {
      Preconditions.checkState(this.valueEquivalence == null, "value equivalence was already set to %s", (Object)this.valueEquivalence);
      this.valueEquivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
      return this;
   }

   Equivalence getValueEquivalence() {
      return (Equivalence)MoreObjects.firstNonNull(this.valueEquivalence, this.getValueStrength().defaultEquivalence());
   }

   public CacheBuilder initialCapacity(int initialCapacity) {
      Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", this.initialCapacity);
      Preconditions.checkArgument(initialCapacity >= 0);
      this.initialCapacity = initialCapacity;
      return this;
   }

   int getInitialCapacity() {
      return this.initialCapacity == -1 ? 16 : this.initialCapacity;
   }

   public CacheBuilder concurrencyLevel(int concurrencyLevel) {
      Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", this.concurrencyLevel);
      Preconditions.checkArgument(concurrencyLevel > 0);
      this.concurrencyLevel = concurrencyLevel;
      return this;
   }

   int getConcurrencyLevel() {
      return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
   }

   public CacheBuilder maximumSize(long maximumSize) {
      Preconditions.checkState(this.maximumSize == -1L, "maximum size was already set to %s", this.maximumSize);
      Preconditions.checkState(this.maximumWeight == -1L, "maximum weight was already set to %s", this.maximumWeight);
      Preconditions.checkState(this.weigher == null, "maximum size can not be combined with weigher");
      Preconditions.checkArgument(maximumSize >= 0L, "maximum size must not be negative");
      this.maximumSize = maximumSize;
      return this;
   }

   @GwtIncompatible
   public CacheBuilder maximumWeight(long maximumWeight) {
      Preconditions.checkState(this.maximumWeight == -1L, "maximum weight was already set to %s", this.maximumWeight);
      Preconditions.checkState(this.maximumSize == -1L, "maximum size was already set to %s", this.maximumSize);
      this.maximumWeight = maximumWeight;
      Preconditions.checkArgument(maximumWeight >= 0L, "maximum weight must not be negative");
      return this;
   }

   @GwtIncompatible
   public CacheBuilder weigher(Weigher weigher) {
      Preconditions.checkState(this.weigher == null);
      if (this.strictParsing) {
         Preconditions.checkState(this.maximumSize == -1L, "weigher can not be combined with maximum size", this.maximumSize);
      }

      this.weigher = (Weigher)Preconditions.checkNotNull(weigher);
      return this;
   }

   long getMaximumWeight() {
      if (this.expireAfterWriteNanos != 0L && this.expireAfterAccessNanos != 0L) {
         return this.weigher == null ? this.maximumSize : this.maximumWeight;
      } else {
         return 0L;
      }
   }

   Weigher getWeigher() {
      return (Weigher)MoreObjects.firstNonNull(this.weigher, CacheBuilder.OneWeigher.INSTANCE);
   }

   @GwtIncompatible
   public CacheBuilder weakKeys() {
      return this.setKeyStrength(LocalCache.Strength.WEAK);
   }

   CacheBuilder setKeyStrength(LocalCache.Strength strength) {
      Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", (Object)this.keyStrength);
      this.keyStrength = (LocalCache.Strength)Preconditions.checkNotNull(strength);
      return this;
   }

   LocalCache.Strength getKeyStrength() {
      return (LocalCache.Strength)MoreObjects.firstNonNull(this.keyStrength, LocalCache.Strength.STRONG);
   }

   @GwtIncompatible
   public CacheBuilder weakValues() {
      return this.setValueStrength(LocalCache.Strength.WEAK);
   }

   @GwtIncompatible
   public CacheBuilder softValues() {
      return this.setValueStrength(LocalCache.Strength.SOFT);
   }

   CacheBuilder setValueStrength(LocalCache.Strength strength) {
      Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", (Object)this.valueStrength);
      this.valueStrength = (LocalCache.Strength)Preconditions.checkNotNull(strength);
      return this;
   }

   LocalCache.Strength getValueStrength() {
      return (LocalCache.Strength)MoreObjects.firstNonNull(this.valueStrength, LocalCache.Strength.STRONG);
   }

   public CacheBuilder expireAfterWrite(long duration, TimeUnit unit) {
      Preconditions.checkState(this.expireAfterWriteNanos == -1L, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
      Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
      this.expireAfterWriteNanos = unit.toNanos(duration);
      return this;
   }

   long getExpireAfterWriteNanos() {
      return this.expireAfterWriteNanos == -1L ? 0L : this.expireAfterWriteNanos;
   }

   public CacheBuilder expireAfterAccess(long duration, TimeUnit unit) {
      Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
      Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
      this.expireAfterAccessNanos = unit.toNanos(duration);
      return this;
   }

   long getExpireAfterAccessNanos() {
      return this.expireAfterAccessNanos == -1L ? 0L : this.expireAfterAccessNanos;
   }

   @GwtIncompatible
   public CacheBuilder refreshAfterWrite(long duration, TimeUnit unit) {
      Preconditions.checkNotNull(unit);
      Preconditions.checkState(this.refreshNanos == -1L, "refresh was already set to %s ns", this.refreshNanos);
      Preconditions.checkArgument(duration > 0L, "duration must be positive: %s %s", duration, unit);
      this.refreshNanos = unit.toNanos(duration);
      return this;
   }

   long getRefreshNanos() {
      return this.refreshNanos == -1L ? 0L : this.refreshNanos;
   }

   public CacheBuilder ticker(Ticker ticker) {
      Preconditions.checkState(this.ticker == null);
      this.ticker = (Ticker)Preconditions.checkNotNull(ticker);
      return this;
   }

   Ticker getTicker(boolean recordsTime) {
      if (this.ticker != null) {
         return this.ticker;
      } else {
         return recordsTime ? Ticker.systemTicker() : NULL_TICKER;
      }
   }

   @CheckReturnValue
   public CacheBuilder removalListener(RemovalListener listener) {
      Preconditions.checkState(this.removalListener == null);
      this.removalListener = (RemovalListener)Preconditions.checkNotNull(listener);
      return this;
   }

   RemovalListener getRemovalListener() {
      return (RemovalListener)MoreObjects.firstNonNull(this.removalListener, CacheBuilder.NullListener.INSTANCE);
   }

   public CacheBuilder recordStats() {
      this.statsCounterSupplier = CACHE_STATS_COUNTER;
      return this;
   }

   boolean isRecordingStats() {
      return this.statsCounterSupplier == CACHE_STATS_COUNTER;
   }

   Supplier getStatsCounterSupplier() {
      return this.statsCounterSupplier;
   }

   public LoadingCache build(CacheLoader loader) {
      this.checkWeightWithWeigher();
      return new LocalCache.LocalLoadingCache(this, loader);
   }

   public Cache build() {
      this.checkWeightWithWeigher();
      this.checkNonLoadingCache();
      return new LocalCache.LocalManualCache(this);
   }

   private void checkNonLoadingCache() {
      Preconditions.checkState(this.refreshNanos == -1L, "refreshAfterWrite requires a LoadingCache");
   }

   private void checkWeightWithWeigher() {
      if (this.weigher == null) {
         Preconditions.checkState(this.maximumWeight == -1L, "maximumWeight requires weigher");
      } else if (this.strictParsing) {
         Preconditions.checkState(this.maximumWeight != -1L, "weigher requires maximumWeight");
      } else if (this.maximumWeight == -1L) {
         logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
      }

   }

   public String toString() {
      MoreObjects.ToStringHelper s = MoreObjects.toStringHelper((Object)this);
      if (this.initialCapacity != -1) {
         s.add("initialCapacity", this.initialCapacity);
      }

      if (this.concurrencyLevel != -1) {
         s.add("concurrencyLevel", this.concurrencyLevel);
      }

      if (this.maximumSize != -1L) {
         s.add("maximumSize", this.maximumSize);
      }

      if (this.maximumWeight != -1L) {
         s.add("maximumWeight", this.maximumWeight);
      }

      if (this.expireAfterWriteNanos != -1L) {
         s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
      }

      if (this.expireAfterAccessNanos != -1L) {
         s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
      }

      if (this.keyStrength != null) {
         s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
      }

      if (this.valueStrength != null) {
         s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
      }

      if (this.keyEquivalence != null) {
         s.addValue("keyEquivalence");
      }

      if (this.valueEquivalence != null) {
         s.addValue("valueEquivalence");
      }

      if (this.removalListener != null) {
         s.addValue("removalListener");
      }

      return s.toString();
   }

   static enum OneWeigher implements Weigher {
      INSTANCE;

      public int weigh(Object key, Object value) {
         return 1;
      }
   }

   static enum NullListener implements RemovalListener {
      INSTANCE;

      public void onRemoval(RemovalNotification notification) {
      }
   }
}
