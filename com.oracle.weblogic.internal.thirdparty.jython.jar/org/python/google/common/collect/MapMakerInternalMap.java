package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Equivalence;
import org.python.google.common.base.Preconditions;
import org.python.google.common.primitives.Ints;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.j2objc.annotations.Weak;

@GwtIncompatible
class MapMakerInternalMap extends AbstractMap implements ConcurrentMap, Serializable {
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final int MAX_SEGMENTS = 65536;
   static final int CONTAINS_VALUE_RETRIES = 3;
   static final int DRAIN_THRESHOLD = 63;
   static final int DRAIN_MAX = 16;
   static final long CLEANUP_EXECUTOR_DELAY_SECS = 60L;
   final transient int segmentMask;
   final transient int segmentShift;
   final transient Segment[] segments;
   final int concurrencyLevel;
   final Equivalence keyEquivalence;
   final transient InternalEntryHelper entryHelper;
   static final WeakValueReference UNSET_WEAK_VALUE_REFERENCE = new WeakValueReference() {
      public DummyInternalEntry getEntry() {
         return null;
      }

      public void clear() {
      }

      public Object get() {
         return null;
      }

      public WeakValueReference copyFor(ReferenceQueue queue, DummyInternalEntry entry) {
         return this;
      }
   };
   transient Set keySet;
   transient Collection values;
   transient Set entrySet;
   private static final long serialVersionUID = 5L;

   private MapMakerInternalMap(MapMaker builder, InternalEntryHelper entryHelper) {
      this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
      this.keyEquivalence = builder.getKeyEquivalence();
      this.entryHelper = entryHelper;
      int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
      int segmentShift = 0;

      int segmentCount;
      for(segmentCount = 1; segmentCount < this.concurrencyLevel; segmentCount <<= 1) {
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

      for(int i = 0; i < this.segments.length; ++i) {
         this.segments[i] = this.createSegment(segmentSize, -1);
      }

   }

   static MapMakerInternalMap create(MapMaker builder) {
      if (builder.getKeyStrength() == MapMakerInternalMap.Strength.STRONG && builder.getValueStrength() == MapMakerInternalMap.Strength.STRONG) {
         return new MapMakerInternalMap(builder, MapMakerInternalMap.StrongKeyStrongValueEntry.Helper.instance());
      } else if (builder.getKeyStrength() == MapMakerInternalMap.Strength.STRONG && builder.getValueStrength() == MapMakerInternalMap.Strength.WEAK) {
         return new MapMakerInternalMap(builder, MapMakerInternalMap.StrongKeyWeakValueEntry.Helper.instance());
      } else if (builder.getKeyStrength() == MapMakerInternalMap.Strength.WEAK && builder.getValueStrength() == MapMakerInternalMap.Strength.STRONG) {
         return new MapMakerInternalMap(builder, MapMakerInternalMap.WeakKeyStrongValueEntry.Helper.instance());
      } else if (builder.getKeyStrength() == MapMakerInternalMap.Strength.WEAK && builder.getValueStrength() == MapMakerInternalMap.Strength.WEAK) {
         return new MapMakerInternalMap(builder, MapMakerInternalMap.WeakKeyWeakValueEntry.Helper.instance());
      } else {
         throw new AssertionError();
      }
   }

   static MapMakerInternalMap createWithDummyValues(MapMaker builder) {
      if (builder.getKeyStrength() == MapMakerInternalMap.Strength.STRONG && builder.getValueStrength() == MapMakerInternalMap.Strength.STRONG) {
         return new MapMakerInternalMap(builder, MapMakerInternalMap.StrongKeyDummyValueEntry.Helper.instance());
      } else if (builder.getKeyStrength() == MapMakerInternalMap.Strength.WEAK && builder.getValueStrength() == MapMakerInternalMap.Strength.STRONG) {
         return new MapMakerInternalMap(builder, MapMakerInternalMap.WeakKeyDummyValueEntry.Helper.instance());
      } else if (builder.getValueStrength() == MapMakerInternalMap.Strength.WEAK) {
         throw new IllegalArgumentException("Map cannot have both weak and dummy values");
      } else {
         throw new AssertionError();
      }
   }

   static WeakValueReference unsetWeakValueReference() {
      return UNSET_WEAK_VALUE_REFERENCE;
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
   InternalEntry copyEntry(InternalEntry original, InternalEntry newNext) {
      int hash = original.getHash();
      return this.segmentFor(hash).copyEntry(original, newNext);
   }

   int hash(Object key) {
      int h = this.keyEquivalence.hash(key);
      return rehash(h);
   }

   void reclaimValue(WeakValueReference valueReference) {
      InternalEntry entry = valueReference.getEntry();
      int hash = entry.getHash();
      this.segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
   }

   void reclaimKey(InternalEntry entry) {
      int hash = entry.getHash();
      this.segmentFor(hash).reclaimKey(entry, hash);
   }

   @VisibleForTesting
   boolean isLiveForTesting(InternalEntry entry) {
      return this.segmentFor(entry.getHash()).getLiveValueForTesting(entry) != null;
   }

   Segment segmentFor(int hash) {
      return this.segments[hash >>> this.segmentShift & this.segmentMask];
   }

   Segment createSegment(int initialCapacity, int maxSegmentSize) {
      return this.entryHelper.newSegment(this, initialCapacity, maxSegmentSize);
   }

   Object getLiveValue(InternalEntry entry) {
      if (entry.getKey() == null) {
         return null;
      } else {
         Object value = entry.getValue();
         return value == null ? null : value;
      }
   }

   final Segment[] newSegmentArray(int ssize) {
      return new Segment[ssize];
   }

   @VisibleForTesting
   Strength keyStrength() {
      return this.entryHelper.keyStrength();
   }

   @VisibleForTesting
   Strength valueStrength() {
      return this.entryHelper.valueStrength();
   }

   @VisibleForTesting
   Equivalence valueEquivalence() {
      return this.entryHelper.valueStrength().defaultEquivalence();
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

   public int size() {
      Segment[] segments = this.segments;
      long sum = 0L;

      for(int i = 0; i < segments.length; ++i) {
         sum += (long)segments[i].count;
      }

      return Ints.saturatedCast(sum);
   }

   public Object get(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).get(key, hash);
      }
   }

   InternalEntry getEntry(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).getEntry(key, hash);
      }
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
         Segment[] segments = this.segments;
         long last = -1L;

         for(int i = 0; i < 3; ++i) {
            long sum = 0L;
            Segment[] var8 = segments;
            int var9 = segments.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Segment segment = var8[var10];
               int unused = segment.count;
               AtomicReferenceArray table = segment.table;

               for(int j = 0; j < table.length(); ++j) {
                  for(InternalEntry e = (InternalEntry)table.get(j); e != null; e = e.getNext()) {
                     Object v = segment.getLiveValue(e);
                     if (v != null && this.valueEquivalence().equivalent(value, v)) {
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

   @CanIgnoreReturnValue
   public Object put(Object key, Object value) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
      int hash = this.hash(key);
      return this.segmentFor(hash).put(key, hash, value, false);
   }

   @CanIgnoreReturnValue
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

   @CanIgnoreReturnValue
   public Object remove(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).remove(key, hash);
      }
   }

   @CanIgnoreReturnValue
   public boolean remove(@Nullable Object key, @Nullable Object value) {
      if (key != null && value != null) {
         int hash = this.hash(key);
         return this.segmentFor(hash).remove(key, hash, value);
      } else {
         return false;
      }
   }

   @CanIgnoreReturnValue
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

   @CanIgnoreReturnValue
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

   public Set keySet() {
      Set ks = this.keySet;
      return ks != null ? ks : (this.keySet = new KeySet());
   }

   public Collection values() {
      Collection vs = this.values;
      return vs != null ? vs : (this.values = new Values());
   }

   public Set entrySet() {
      Set es = this.entrySet;
      return es != null ? es : (this.entrySet = new EntrySet());
   }

   private static ArrayList toArrayList(Collection c) {
      ArrayList result = new ArrayList(c.size());
      Iterators.addAll(result, c.iterator());
      return result;
   }

   Object writeReplace() {
      return new SerializationProxy(this.entryHelper.keyStrength(), this.entryHelper.valueStrength(), this.keyEquivalence, this.entryHelper.valueStrength().defaultEquivalence(), this.concurrencyLevel, this);
   }

   private static final class SerializationProxy extends AbstractSerializationProxy {
      private static final long serialVersionUID = 3L;

      SerializationProxy(Strength keyStrength, Strength valueStrength, Equivalence keyEquivalence, Equivalence valueEquivalence, int concurrencyLevel, ConcurrentMap delegate) {
         super(keyStrength, valueStrength, keyEquivalence, valueEquivalence, concurrencyLevel, delegate);
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.defaultWriteObject();
         this.writeMapTo(out);
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         MapMaker mapMaker = this.readMapMaker(in);
         this.delegate = mapMaker.makeMap();
         this.readEntries(in);
      }

      private Object readResolve() {
         return this.delegate;
      }
   }

   abstract static class AbstractSerializationProxy extends ForwardingConcurrentMap implements Serializable {
      private static final long serialVersionUID = 3L;
      final Strength keyStrength;
      final Strength valueStrength;
      final Equivalence keyEquivalence;
      final Equivalence valueEquivalence;
      final int concurrencyLevel;
      transient ConcurrentMap delegate;

      AbstractSerializationProxy(Strength keyStrength, Strength valueStrength, Equivalence keyEquivalence, Equivalence valueEquivalence, int concurrencyLevel, ConcurrentMap delegate) {
         this.keyStrength = keyStrength;
         this.valueStrength = valueStrength;
         this.keyEquivalence = keyEquivalence;
         this.valueEquivalence = valueEquivalence;
         this.concurrencyLevel = concurrencyLevel;
         this.delegate = delegate;
      }

      protected ConcurrentMap delegate() {
         return this.delegate;
      }

      void writeMapTo(ObjectOutputStream out) throws IOException {
         out.writeInt(this.delegate.size());
         Iterator var2 = this.delegate.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            out.writeObject(entry.getKey());
            out.writeObject(entry.getValue());
         }

         out.writeObject((Object)null);
      }

      MapMaker readMapMaker(ObjectInputStream in) throws IOException {
         int size = in.readInt();
         return (new MapMaker()).initialCapacity(size).setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).concurrencyLevel(this.concurrencyLevel);
      }

      void readEntries(ObjectInputStream in) throws IOException, ClassNotFoundException {
         while(true) {
            Object key = in.readObject();
            if (key == null) {
               return;
            }

            Object value = in.readObject();
            this.delegate.put(key, value);
         }
      }
   }

   private abstract static class SafeToArraySet extends AbstractSet {
      private SafeToArraySet() {
      }

      public Object[] toArray() {
         return MapMakerInternalMap.toArrayList(this).toArray();
      }

      public Object[] toArray(Object[] a) {
         return MapMakerInternalMap.toArrayList(this).toArray(a);
      }

      // $FF: synthetic method
      SafeToArraySet(Object x0) {
         this();
      }
   }

   final class EntrySet extends SafeToArraySet {
      EntrySet() {
         super(null);
      }

      public Iterator iterator() {
         return MapMakerInternalMap.this.new EntryIterator();
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
               Object v = MapMakerInternalMap.this.get(key);
               return v != null && MapMakerInternalMap.this.valueEquivalence().equivalent(e.getValue(), v);
            }
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Object key = e.getKey();
            return key != null && MapMakerInternalMap.this.remove(key, e.getValue());
         }
      }

      public int size() {
         return MapMakerInternalMap.this.size();
      }

      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      public void clear() {
         MapMakerInternalMap.this.clear();
      }
   }

   final class Values extends AbstractCollection {
      public Iterator iterator() {
         return MapMakerInternalMap.this.new ValueIterator();
      }

      public int size() {
         return MapMakerInternalMap.this.size();
      }

      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      public boolean contains(Object o) {
         return MapMakerInternalMap.this.containsValue(o);
      }

      public void clear() {
         MapMakerInternalMap.this.clear();
      }

      public Object[] toArray() {
         return MapMakerInternalMap.toArrayList(this).toArray();
      }

      public Object[] toArray(Object[] a) {
         return MapMakerInternalMap.toArrayList(this).toArray(a);
      }
   }

   final class KeySet extends SafeToArraySet {
      KeySet() {
         super(null);
      }

      public Iterator iterator() {
         return MapMakerInternalMap.this.new KeyIterator();
      }

      public int size() {
         return MapMakerInternalMap.this.size();
      }

      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      public boolean contains(Object o) {
         return MapMakerInternalMap.this.containsKey(o);
      }

      public boolean remove(Object o) {
         return MapMakerInternalMap.this.remove(o) != null;
      }

      public void clear() {
         MapMakerInternalMap.this.clear();
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

   final class WriteThroughEntry extends AbstractMapEntry {
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
         Object oldValue = MapMakerInternalMap.this.put(this.key, newValue);
         this.value = newValue;
         return oldValue;
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
      InternalEntry nextEntry;
      WriteThroughEntry nextExternal;
      WriteThroughEntry lastReturned;

      HashIterator() {
         this.nextSegmentIndex = MapMakerInternalMap.this.segments.length - 1;
         this.nextTableIndex = -1;
         this.advance();
      }

      public abstract Object next();

      final void advance() {
         this.nextExternal = null;
         if (!this.nextInChain()) {
            if (!this.nextInTable()) {
               while(this.nextSegmentIndex >= 0) {
                  this.currentSegment = MapMakerInternalMap.this.segments[this.nextSegmentIndex--];
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
               if ((this.nextEntry = (InternalEntry)this.currentTable.get(this.nextTableIndex--)) == null || !this.advanceTo(this.nextEntry) && !this.nextInChain()) {
                  continue;
               }

               return true;
            }

            return false;
         }
      }

      boolean advanceTo(InternalEntry entry) {
         boolean var4;
         try {
            Object key = entry.getKey();
            Object value = MapMakerInternalMap.this.getLiveValue(entry);
            if (value == null) {
               var4 = false;
               return var4;
            }

            this.nextExternal = MapMakerInternalMap.this.new WriteThroughEntry(key, value);
            var4 = true;
         } finally {
            this.currentSegment.postReadCleanup();
         }

         return var4;
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
         CollectPreconditions.checkRemove(this.lastReturned != null);
         MapMakerInternalMap.this.remove(this.lastReturned.getKey());
         this.lastReturned = null;
      }
   }

   static final class CleanupMapTask implements Runnable {
      final WeakReference mapReference;

      public CleanupMapTask(MapMakerInternalMap map) {
         this.mapReference = new WeakReference(map);
      }

      public void run() {
         MapMakerInternalMap map = (MapMakerInternalMap)this.mapReference.get();
         if (map == null) {
            throw new CancellationException();
         } else {
            Segment[] var2 = map.segments;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Segment segment = var2[var4];
               segment.runCleanup();
            }

         }
      }
   }

   static final class WeakKeyDummyValueSegment extends Segment {
      private final ReferenceQueue queueForKeys = new ReferenceQueue();

      WeakKeyDummyValueSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
         super(map, initialCapacity, maxSegmentSize);
      }

      WeakKeyDummyValueSegment self() {
         return this;
      }

      ReferenceQueue getKeyReferenceQueueForTesting() {
         return this.queueForKeys;
      }

      public WeakKeyDummyValueEntry castForTesting(InternalEntry entry) {
         return (WeakKeyDummyValueEntry)entry;
      }

      void maybeDrainReferenceQueues() {
         this.drainKeyReferenceQueue(this.queueForKeys);
      }

      void maybeClearReferenceQueues() {
         this.clearReferenceQueue(this.queueForKeys);
      }
   }

   static final class WeakKeyWeakValueSegment extends Segment {
      private final ReferenceQueue queueForKeys = new ReferenceQueue();
      private final ReferenceQueue queueForValues = new ReferenceQueue();

      WeakKeyWeakValueSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
         super(map, initialCapacity, maxSegmentSize);
      }

      WeakKeyWeakValueSegment self() {
         return this;
      }

      ReferenceQueue getKeyReferenceQueueForTesting() {
         return this.queueForKeys;
      }

      ReferenceQueue getValueReferenceQueueForTesting() {
         return this.queueForValues;
      }

      public WeakKeyWeakValueEntry castForTesting(InternalEntry entry) {
         return (WeakKeyWeakValueEntry)entry;
      }

      public WeakValueReference getWeakValueReferenceForTesting(InternalEntry e) {
         return this.castForTesting(e).getValueReference();
      }

      public WeakValueReference newWeakValueReferenceForTesting(InternalEntry e, Object value) {
         return new WeakValueReferenceImpl(this.queueForValues, value, this.castForTesting(e));
      }

      public void setWeakValueReferenceForTesting(InternalEntry e, WeakValueReference valueReference) {
         WeakKeyWeakValueEntry entry = this.castForTesting(e);
         WeakValueReference previous = entry.valueReference;
         entry.valueReference = valueReference;
         previous.clear();
      }

      void maybeDrainReferenceQueues() {
         this.drainKeyReferenceQueue(this.queueForKeys);
         this.drainValueReferenceQueue(this.queueForValues);
      }

      void maybeClearReferenceQueues() {
         this.clearReferenceQueue(this.queueForKeys);
      }
   }

   static final class WeakKeyStrongValueSegment extends Segment {
      private final ReferenceQueue queueForKeys = new ReferenceQueue();

      WeakKeyStrongValueSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
         super(map, initialCapacity, maxSegmentSize);
      }

      WeakKeyStrongValueSegment self() {
         return this;
      }

      ReferenceQueue getKeyReferenceQueueForTesting() {
         return this.queueForKeys;
      }

      public WeakKeyStrongValueEntry castForTesting(InternalEntry entry) {
         return (WeakKeyStrongValueEntry)entry;
      }

      void maybeDrainReferenceQueues() {
         this.drainKeyReferenceQueue(this.queueForKeys);
      }

      void maybeClearReferenceQueues() {
         this.clearReferenceQueue(this.queueForKeys);
      }
   }

   static final class StrongKeyDummyValueSegment extends Segment {
      StrongKeyDummyValueSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
         super(map, initialCapacity, maxSegmentSize);
      }

      StrongKeyDummyValueSegment self() {
         return this;
      }

      public StrongKeyDummyValueEntry castForTesting(InternalEntry entry) {
         return (StrongKeyDummyValueEntry)entry;
      }
   }

   static final class StrongKeyWeakValueSegment extends Segment {
      private final ReferenceQueue queueForValues = new ReferenceQueue();

      StrongKeyWeakValueSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
         super(map, initialCapacity, maxSegmentSize);
      }

      StrongKeyWeakValueSegment self() {
         return this;
      }

      ReferenceQueue getValueReferenceQueueForTesting() {
         return this.queueForValues;
      }

      public StrongKeyWeakValueEntry castForTesting(InternalEntry entry) {
         return (StrongKeyWeakValueEntry)entry;
      }

      public WeakValueReference getWeakValueReferenceForTesting(InternalEntry e) {
         return this.castForTesting(e).getValueReference();
      }

      public WeakValueReference newWeakValueReferenceForTesting(InternalEntry e, Object value) {
         return new WeakValueReferenceImpl(this.queueForValues, value, this.castForTesting(e));
      }

      public void setWeakValueReferenceForTesting(InternalEntry e, WeakValueReference valueReference) {
         StrongKeyWeakValueEntry entry = this.castForTesting(e);
         WeakValueReference previous = entry.valueReference;
         entry.valueReference = valueReference;
         previous.clear();
      }

      void maybeDrainReferenceQueues() {
         this.drainValueReferenceQueue(this.queueForValues);
      }

      void maybeClearReferenceQueues() {
         this.clearReferenceQueue(this.queueForValues);
      }
   }

   static final class StrongKeyStrongValueSegment extends Segment {
      StrongKeyStrongValueSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
         super(map, initialCapacity, maxSegmentSize);
      }

      StrongKeyStrongValueSegment self() {
         return this;
      }

      public StrongKeyStrongValueEntry castForTesting(InternalEntry entry) {
         return (StrongKeyStrongValueEntry)entry;
      }
   }

   abstract static class Segment extends ReentrantLock {
      @Weak
      final MapMakerInternalMap map;
      volatile int count;
      int modCount;
      int threshold;
      volatile AtomicReferenceArray table;
      final int maxSegmentSize;
      final AtomicInteger readCount = new AtomicInteger();

      Segment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
         this.map = map;
         this.maxSegmentSize = maxSegmentSize;
         this.initTable(this.newEntryArray(initialCapacity));
      }

      abstract Segment self();

      @GuardedBy("this")
      void maybeDrainReferenceQueues() {
      }

      void maybeClearReferenceQueues() {
      }

      void setValue(InternalEntry entry, Object value) {
         this.map.entryHelper.setValue(this.self(), entry, value);
      }

      InternalEntry copyEntry(InternalEntry original, InternalEntry newNext) {
         return this.map.entryHelper.copy(this.self(), original, newNext);
      }

      AtomicReferenceArray newEntryArray(int size) {
         return new AtomicReferenceArray(size);
      }

      void initTable(AtomicReferenceArray newTable) {
         this.threshold = newTable.length() * 3 / 4;
         if (this.threshold == this.maxSegmentSize) {
            ++this.threshold;
         }

         this.table = newTable;
      }

      abstract InternalEntry castForTesting(InternalEntry var1);

      ReferenceQueue getKeyReferenceQueueForTesting() {
         throw new AssertionError();
      }

      ReferenceQueue getValueReferenceQueueForTesting() {
         throw new AssertionError();
      }

      WeakValueReference getWeakValueReferenceForTesting(InternalEntry entry) {
         throw new AssertionError();
      }

      WeakValueReference newWeakValueReferenceForTesting(InternalEntry entry, Object value) {
         throw new AssertionError();
      }

      void setWeakValueReferenceForTesting(InternalEntry entry, WeakValueReference valueReference) {
         throw new AssertionError();
      }

      void setTableEntryForTesting(int i, InternalEntry entry) {
         this.table.set(i, this.castForTesting(entry));
      }

      InternalEntry copyForTesting(InternalEntry entry, @Nullable InternalEntry newNext) {
         return this.map.entryHelper.copy(this.self(), this.castForTesting(entry), this.castForTesting(newNext));
      }

      void setValueForTesting(InternalEntry entry, Object value) {
         this.map.entryHelper.setValue(this.self(), this.castForTesting(entry), value);
      }

      InternalEntry newEntryForTesting(Object key, int hash, @Nullable InternalEntry next) {
         return this.map.entryHelper.newEntry(this.self(), key, hash, this.castForTesting(next));
      }

      @CanIgnoreReturnValue
      boolean removeTableEntryForTesting(InternalEntry entry) {
         return this.removeEntryForTesting(this.castForTesting(entry));
      }

      InternalEntry removeFromChainForTesting(InternalEntry first, InternalEntry entry) {
         return this.removeFromChain(this.castForTesting(first), this.castForTesting(entry));
      }

      @Nullable
      Object getLiveValueForTesting(InternalEntry entry) {
         return this.getLiveValue(this.castForTesting(entry));
      }

      void tryDrainReferenceQueues() {
         if (this.tryLock()) {
            try {
               this.maybeDrainReferenceQueues();
            } finally {
               this.unlock();
            }
         }

      }

      @GuardedBy("this")
      void drainKeyReferenceQueue(ReferenceQueue keyReferenceQueue) {
         int i = 0;

         Reference ref;
         while((ref = keyReferenceQueue.poll()) != null) {
            InternalEntry entry = (InternalEntry)ref;
            this.map.reclaimKey(entry);
            ++i;
            if (i == 16) {
               break;
            }
         }

      }

      @GuardedBy("this")
      void drainValueReferenceQueue(ReferenceQueue valueReferenceQueue) {
         int i = 0;

         Reference ref;
         while((ref = valueReferenceQueue.poll()) != null) {
            WeakValueReference valueReference = (WeakValueReference)ref;
            this.map.reclaimValue(valueReference);
            ++i;
            if (i == 16) {
               break;
            }
         }

      }

      void clearReferenceQueue(ReferenceQueue referenceQueue) {
         while(referenceQueue.poll() != null) {
         }

      }

      InternalEntry getFirst(int hash) {
         AtomicReferenceArray table = this.table;
         return (InternalEntry)table.get(hash & table.length() - 1);
      }

      InternalEntry getEntry(Object key, int hash) {
         if (this.count != 0) {
            for(InternalEntry e = this.getFirst(hash); e != null; e = e.getNext()) {
               if (e.getHash() == hash) {
                  Object entryKey = e.getKey();
                  if (entryKey == null) {
                     this.tryDrainReferenceQueues();
                  } else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                     return e;
                  }
               }
            }
         }

         return null;
      }

      InternalEntry getLiveEntry(Object key, int hash) {
         return this.getEntry(key, hash);
      }

      Object get(Object key, int hash) {
         Object var5;
         try {
            InternalEntry e = this.getLiveEntry(key, hash);
            Object value;
            if (e == null) {
               value = null;
               return value;
            }

            value = e.getValue();
            if (value == null) {
               this.tryDrainReferenceQueues();
            }

            var5 = value;
         } finally {
            this.postReadCleanup();
         }

         return var5;
      }

      boolean containsKey(Object key, int hash) {
         boolean var3;
         try {
            if (this.count != 0) {
               InternalEntry e = this.getLiveEntry(key, hash);
               boolean var4 = e != null && e.getValue() != null;
               return var4;
            }

            var3 = false;
         } finally {
            this.postReadCleanup();
         }

         return var3;
      }

      @VisibleForTesting
      boolean containsValue(Object value) {
         try {
            if (this.count != 0) {
               AtomicReferenceArray table = this.table;
               int length = table.length();

               for(int i = 0; i < length; ++i) {
                  for(InternalEntry e = (InternalEntry)table.get(i); e != null; e = e.getNext()) {
                     Object entryValue = this.getLiveValue(e);
                     if (entryValue != null && this.map.valueEquivalence().equivalent(value, entryValue)) {
                        boolean var7 = true;
                        return var7;
                     }
                  }
               }
            }

            boolean var11 = false;
            return var11;
         } finally {
            this.postReadCleanup();
         }
      }

      Object put(Object key, int hash, Object value, boolean onlyIfAbsent) {
         this.lock();

         try {
            this.preWriteCleanup();
            int newCount = this.count + 1;
            if (newCount > this.threshold) {
               this.expand();
               newCount = this.count + 1;
            }

            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            InternalEntry first = (InternalEntry)table.get(index);

            InternalEntry e;
            Object entryKey;
            for(e = first; e != null; e = e.getNext()) {
               entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  Object entryValue = e.getValue();
                  Object var12;
                  if (entryValue == null) {
                     ++this.modCount;
                     this.setValue(e, value);
                     newCount = this.count;
                     this.count = newCount;
                     var12 = null;
                     return var12;
                  }

                  if (!onlyIfAbsent) {
                     ++this.modCount;
                     this.setValue(e, value);
                     var12 = entryValue;
                     return var12;
                  }

                  var12 = entryValue;
                  return var12;
               }
            }

            ++this.modCount;
            e = this.map.entryHelper.newEntry(this.self(), key, hash, first);
            this.setValue(e, value);
            table.set(index, e);
            this.count = newCount;
            entryKey = null;
            return entryKey;
         } finally {
            this.unlock();
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
               InternalEntry head = (InternalEntry)oldTable.get(oldIndex);
               if (head != null) {
                  InternalEntry next = head.getNext();
                  int headIndex = head.getHash() & newMask;
                  if (next == null) {
                     newTable.set(headIndex, head);
                  } else {
                     InternalEntry tail = head;
                     int tailIndex = headIndex;

                     InternalEntry e;
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
                        InternalEntry newNext = (InternalEntry)newTable.get(newIndex);
                        InternalEntry newFirst = this.copyEntry(e, newNext);
                        if (newFirst != null) {
                           newTable.set(newIndex, newFirst);
                        } else {
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
            this.preWriteCleanup();
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            InternalEntry first = (InternalEntry)table.get(index);

            for(InternalEntry e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  Object entryValue = e.getValue();
                  boolean var17;
                  if (entryValue != null) {
                     if (this.map.valueEquivalence().equivalent(oldValue, entryValue)) {
                        ++this.modCount;
                        this.setValue(e, newValue);
                        var17 = true;
                        return var17;
                     }

                     var17 = false;
                     return var17;
                  }

                  if (isCollected(e)) {
                     int newCount = this.count - 1;
                     ++this.modCount;
                     InternalEntry newFirst = this.removeFromChain(first, e);
                     newCount = this.count - 1;
                     table.set(index, newFirst);
                     this.count = newCount;
                  }

                  var17 = false;
                  return var17;
               }
            }

            boolean var16 = false;
            return var16;
         } finally {
            this.unlock();
         }
      }

      Object replace(Object key, int hash, Object newValue) {
         this.lock();

         try {
            this.preWriteCleanup();
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            InternalEntry first = (InternalEntry)table.get(index);

            InternalEntry e;
            for(e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  Object entryValue = e.getValue();
                  Object var15;
                  if (entryValue != null) {
                     ++this.modCount;
                     this.setValue(e, newValue);
                     var15 = entryValue;
                     return var15;
                  }

                  if (isCollected(e)) {
                     int newCount = this.count - 1;
                     ++this.modCount;
                     InternalEntry newFirst = this.removeFromChain(first, e);
                     newCount = this.count - 1;
                     table.set(index, newFirst);
                     this.count = newCount;
                  }

                  var15 = null;
                  return var15;
               }
            }

            e = null;
            return e;
         } finally {
            this.unlock();
         }
      }

      @CanIgnoreReturnValue
      Object remove(Object key, int hash) {
         this.lock();

         try {
            this.preWriteCleanup();
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            InternalEntry first = (InternalEntry)table.get(index);

            InternalEntry e;
            for(e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  Object entryValue = e.getValue();
                  InternalEntry newFirst;
                  if (entryValue == null && !isCollected(e)) {
                     newFirst = null;
                     return newFirst;
                  }

                  ++this.modCount;
                  newFirst = this.removeFromChain(first, e);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  Object var11 = entryValue;
                  return var11;
               }
            }

            e = null;
            return e;
         } finally {
            this.unlock();
         }
      }

      boolean remove(Object key, int hash, Object value) {
         this.lock();

         boolean var17;
         try {
            this.preWriteCleanup();
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            InternalEntry first = (InternalEntry)table.get(index);

            for(InternalEntry e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  Object entryValue = e.getValue();
                  boolean explicitRemoval = false;
                  if (this.map.valueEquivalence().equivalent(value, entryValue)) {
                     explicitRemoval = true;
                  } else if (!isCollected(e)) {
                     boolean var18 = false;
                     return var18;
                  }

                  ++this.modCount;
                  InternalEntry newFirst = this.removeFromChain(first, e);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  boolean var13 = explicitRemoval;
                  return var13;
               }
            }

            var17 = false;
         } finally {
            this.unlock();
         }

         return var17;
      }

      void clear() {
         if (this.count != 0) {
            this.lock();

            try {
               AtomicReferenceArray table = this.table;

               for(int i = 0; i < table.length(); ++i) {
                  table.set(i, (Object)null);
               }

               this.maybeClearReferenceQueues();
               this.readCount.set(0);
               ++this.modCount;
               this.count = 0;
            } finally {
               this.unlock();
            }
         }

      }

      @GuardedBy("this")
      InternalEntry removeFromChain(InternalEntry first, InternalEntry entry) {
         int newCount = this.count;
         InternalEntry newFirst = entry.getNext();

         for(InternalEntry e = first; e != entry; e = e.getNext()) {
            InternalEntry next = this.copyEntry(e, newFirst);
            if (next != null) {
               newFirst = next;
            } else {
               --newCount;
            }
         }

         this.count = newCount;
         return newFirst;
      }

      @CanIgnoreReturnValue
      boolean reclaimKey(InternalEntry entry, int hash) {
         this.lock();

         boolean var13;
         try {
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            InternalEntry first = (InternalEntry)table.get(index);

            for(InternalEntry e = first; e != null; e = e.getNext()) {
               if (e == entry) {
                  ++this.modCount;
                  InternalEntry newFirst = this.removeFromChain(first, e);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  boolean var9 = true;
                  return var9;
               }
            }

            var13 = false;
         } finally {
            this.unlock();
         }

         return var13;
      }

      @CanIgnoreReturnValue
      boolean reclaimValue(Object key, int hash, WeakValueReference valueReference) {
         this.lock();

         boolean var17;
         try {
            int newCount = this.count - 1;
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            InternalEntry first = (InternalEntry)table.get(index);

            for(InternalEntry e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  WeakValueReference v = ((WeakValueEntry)e).getValueReference();
                  if (v == valueReference) {
                     ++this.modCount;
                     InternalEntry newFirst = this.removeFromChain(first, e);
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

            var17 = false;
         } finally {
            this.unlock();
         }

         return var17;
      }

      @CanIgnoreReturnValue
      boolean clearValueForTesting(Object key, int hash, WeakValueReference valueReference) {
         this.lock();

         try {
            AtomicReferenceArray table = this.table;
            int index = hash & table.length() - 1;
            InternalEntry first = (InternalEntry)table.get(index);

            for(InternalEntry e = first; e != null; e = e.getNext()) {
               Object entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  WeakValueReference v = ((WeakValueEntry)e).getValueReference();
                  if (v == valueReference) {
                     InternalEntry newFirst = this.removeFromChain(first, e);
                     table.set(index, newFirst);
                     boolean var11 = true;
                     return var11;
                  }

                  boolean var10 = false;
                  return var10;
               }
            }

            boolean var16 = false;
            return var16;
         } finally {
            this.unlock();
         }
      }

      @GuardedBy("this")
      boolean removeEntryForTesting(InternalEntry entry) {
         int hash = entry.getHash();
         int newCount = this.count - 1;
         AtomicReferenceArray table = this.table;
         int index = hash & table.length() - 1;
         InternalEntry first = (InternalEntry)table.get(index);

         for(InternalEntry e = first; e != null; e = e.getNext()) {
            if (e == entry) {
               ++this.modCount;
               InternalEntry newFirst = this.removeFromChain(first, e);
               newCount = this.count - 1;
               table.set(index, newFirst);
               this.count = newCount;
               return true;
            }
         }

         return false;
      }

      static boolean isCollected(InternalEntry entry) {
         return entry.getValue() == null;
      }

      @Nullable
      Object getLiveValue(InternalEntry entry) {
         if (entry.getKey() == null) {
            this.tryDrainReferenceQueues();
            return null;
         } else {
            Object value = entry.getValue();
            if (value == null) {
               this.tryDrainReferenceQueues();
               return null;
            } else {
               return value;
            }
         }
      }

      void postReadCleanup() {
         if ((this.readCount.incrementAndGet() & 63) == 0) {
            this.runCleanup();
         }

      }

      @GuardedBy("this")
      void preWriteCleanup() {
         this.runLockedCleanup();
      }

      void runCleanup() {
         this.runLockedCleanup();
      }

      void runLockedCleanup() {
         if (this.tryLock()) {
            try {
               this.maybeDrainReferenceQueues();
               this.readCount.set(0);
            } finally {
               this.unlock();
            }
         }

      }
   }

   static final class WeakValueReferenceImpl extends WeakReference implements WeakValueReference {
      @Weak
      final InternalEntry entry;

      WeakValueReferenceImpl(ReferenceQueue queue, Object referent, InternalEntry entry) {
         super(referent, queue);
         this.entry = entry;
      }

      public InternalEntry getEntry() {
         return this.entry;
      }

      public WeakValueReference copyFor(ReferenceQueue queue, InternalEntry entry) {
         return new WeakValueReferenceImpl(queue, this.get(), entry);
      }
   }

   static final class DummyInternalEntry implements InternalEntry {
      private DummyInternalEntry() {
         throw new AssertionError();
      }

      public DummyInternalEntry getNext() {
         throw new AssertionError();
      }

      public int getHash() {
         throw new AssertionError();
      }

      public Object getKey() {
         throw new AssertionError();
      }

      public Object getValue() {
         throw new AssertionError();
      }
   }

   interface WeakValueReference {
      @Nullable
      Object get();

      InternalEntry getEntry();

      void clear();

      WeakValueReference copyFor(ReferenceQueue var1, InternalEntry var2);
   }

   static final class WeakKeyWeakValueEntry extends AbstractWeakKeyEntry implements WeakValueEntry {
      private volatile WeakValueReference valueReference = MapMakerInternalMap.unsetWeakValueReference();

      WeakKeyWeakValueEntry(ReferenceQueue queue, Object key, int hash, @Nullable WeakKeyWeakValueEntry next) {
         super(queue, key, hash, next);
      }

      public Object getValue() {
         return this.valueReference.get();
      }

      WeakKeyWeakValueEntry copy(ReferenceQueue queueForKeys, ReferenceQueue queueForValues, WeakKeyWeakValueEntry newNext) {
         WeakKeyWeakValueEntry newEntry = new WeakKeyWeakValueEntry(queueForKeys, this.getKey(), this.hash, newNext);
         newEntry.valueReference = this.valueReference.copyFor(queueForValues, newEntry);
         return newEntry;
      }

      public void clearValue() {
         this.valueReference.clear();
      }

      void setValue(Object value, ReferenceQueue queueForValues) {
         WeakValueReference previous = this.valueReference;
         this.valueReference = new WeakValueReferenceImpl(queueForValues, value, this);
         previous.clear();
      }

      public WeakValueReference getValueReference() {
         return this.valueReference;
      }

      static final class Helper implements InternalEntryHelper {
         private static final Helper INSTANCE = new Helper();

         static Helper instance() {
            return INSTANCE;
         }

         public Strength keyStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }

         public Strength valueStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }

         public WeakKeyWeakValueSegment newSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
            return new WeakKeyWeakValueSegment(map, initialCapacity, maxSegmentSize);
         }

         public WeakKeyWeakValueEntry copy(WeakKeyWeakValueSegment segment, WeakKeyWeakValueEntry entry, @Nullable WeakKeyWeakValueEntry newNext) {
            if (entry.getKey() == null) {
               return null;
            } else {
               return MapMakerInternalMap.Segment.isCollected(entry) ? null : entry.copy(segment.queueForKeys, segment.queueForValues, newNext);
            }
         }

         public void setValue(WeakKeyWeakValueSegment segment, WeakKeyWeakValueEntry entry, Object value) {
            entry.setValue(value, segment.queueForValues);
         }

         public WeakKeyWeakValueEntry newEntry(WeakKeyWeakValueSegment segment, Object key, int hash, @Nullable WeakKeyWeakValueEntry next) {
            return new WeakKeyWeakValueEntry(segment.queueForKeys, key, hash, next);
         }
      }
   }

   static final class WeakKeyStrongValueEntry extends AbstractWeakKeyEntry implements StrongValueEntry {
      @Nullable
      private volatile Object value = null;

      WeakKeyStrongValueEntry(ReferenceQueue queue, Object key, int hash, @Nullable WeakKeyStrongValueEntry next) {
         super(queue, key, hash, next);
      }

      @Nullable
      public Object getValue() {
         return this.value;
      }

      void setValue(Object value) {
         this.value = value;
      }

      WeakKeyStrongValueEntry copy(ReferenceQueue queueForKeys, WeakKeyStrongValueEntry newNext) {
         WeakKeyStrongValueEntry newEntry = new WeakKeyStrongValueEntry(queueForKeys, this.getKey(), this.hash, newNext);
         newEntry.setValue(this.value);
         return newEntry;
      }

      static final class Helper implements InternalEntryHelper {
         private static final Helper INSTANCE = new Helper();

         static Helper instance() {
            return INSTANCE;
         }

         public Strength keyStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }

         public Strength valueStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public WeakKeyStrongValueSegment newSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
            return new WeakKeyStrongValueSegment(map, initialCapacity, maxSegmentSize);
         }

         public WeakKeyStrongValueEntry copy(WeakKeyStrongValueSegment segment, WeakKeyStrongValueEntry entry, @Nullable WeakKeyStrongValueEntry newNext) {
            return entry.getKey() == null ? null : entry.copy(segment.queueForKeys, newNext);
         }

         public void setValue(WeakKeyStrongValueSegment segment, WeakKeyStrongValueEntry entry, Object value) {
            entry.setValue(value);
         }

         public WeakKeyStrongValueEntry newEntry(WeakKeyStrongValueSegment segment, Object key, int hash, @Nullable WeakKeyStrongValueEntry next) {
            return new WeakKeyStrongValueEntry(segment.queueForKeys, key, hash, next);
         }
      }
   }

   static final class WeakKeyDummyValueEntry extends AbstractWeakKeyEntry implements StrongValueEntry {
      WeakKeyDummyValueEntry(ReferenceQueue queue, Object key, int hash, @Nullable WeakKeyDummyValueEntry next) {
         super(queue, key, hash, next);
      }

      public MapMaker.Dummy getValue() {
         return MapMaker.Dummy.VALUE;
      }

      void setValue(MapMaker.Dummy value) {
      }

      WeakKeyDummyValueEntry copy(ReferenceQueue queueForKeys, WeakKeyDummyValueEntry newNext) {
         return new WeakKeyDummyValueEntry(queueForKeys, this.getKey(), this.hash, newNext);
      }

      static final class Helper implements InternalEntryHelper {
         private static final Helper INSTANCE = new Helper();

         static Helper instance() {
            return INSTANCE;
         }

         public Strength keyStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }

         public Strength valueStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public WeakKeyDummyValueSegment newSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
            return new WeakKeyDummyValueSegment(map, initialCapacity, maxSegmentSize);
         }

         public WeakKeyDummyValueEntry copy(WeakKeyDummyValueSegment segment, WeakKeyDummyValueEntry entry, @Nullable WeakKeyDummyValueEntry newNext) {
            return entry.getKey() == null ? null : entry.copy(segment.queueForKeys, newNext);
         }

         public void setValue(WeakKeyDummyValueSegment segment, WeakKeyDummyValueEntry entry, MapMaker.Dummy value) {
         }

         public WeakKeyDummyValueEntry newEntry(WeakKeyDummyValueSegment segment, Object key, int hash, @Nullable WeakKeyDummyValueEntry next) {
            return new WeakKeyDummyValueEntry(segment.queueForKeys, key, hash, next);
         }
      }
   }

   abstract static class AbstractWeakKeyEntry extends WeakReference implements InternalEntry {
      final int hash;
      final InternalEntry next;

      AbstractWeakKeyEntry(ReferenceQueue queue, Object key, int hash, @Nullable InternalEntry next) {
         super(key, queue);
         this.hash = hash;
         this.next = next;
      }

      public Object getKey() {
         return this.get();
      }

      public int getHash() {
         return this.hash;
      }

      public InternalEntry getNext() {
         return this.next;
      }
   }

   static final class StrongKeyDummyValueEntry extends AbstractStrongKeyEntry implements StrongValueEntry {
      StrongKeyDummyValueEntry(Object key, int hash, @Nullable StrongKeyDummyValueEntry next) {
         super(key, hash, next);
      }

      public MapMaker.Dummy getValue() {
         return MapMaker.Dummy.VALUE;
      }

      void setValue(MapMaker.Dummy value) {
      }

      StrongKeyDummyValueEntry copy(StrongKeyDummyValueEntry newNext) {
         return new StrongKeyDummyValueEntry(this.key, this.hash, newNext);
      }

      static final class Helper implements InternalEntryHelper {
         private static final Helper INSTANCE = new Helper();

         static Helper instance() {
            return INSTANCE;
         }

         public Strength keyStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public Strength valueStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public StrongKeyDummyValueSegment newSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
            return new StrongKeyDummyValueSegment(map, initialCapacity, maxSegmentSize);
         }

         public StrongKeyDummyValueEntry copy(StrongKeyDummyValueSegment segment, StrongKeyDummyValueEntry entry, @Nullable StrongKeyDummyValueEntry newNext) {
            return entry.copy(newNext);
         }

         public void setValue(StrongKeyDummyValueSegment segment, StrongKeyDummyValueEntry entry, MapMaker.Dummy value) {
         }

         public StrongKeyDummyValueEntry newEntry(StrongKeyDummyValueSegment segment, Object key, int hash, @Nullable StrongKeyDummyValueEntry next) {
            return new StrongKeyDummyValueEntry(key, hash, next);
         }
      }
   }

   static final class StrongKeyWeakValueEntry extends AbstractStrongKeyEntry implements WeakValueEntry {
      private volatile WeakValueReference valueReference = MapMakerInternalMap.unsetWeakValueReference();

      StrongKeyWeakValueEntry(Object key, int hash, @Nullable StrongKeyWeakValueEntry next) {
         super(key, hash, next);
      }

      public Object getValue() {
         return this.valueReference.get();
      }

      public void clearValue() {
         this.valueReference.clear();
      }

      void setValue(Object value, ReferenceQueue queueForValues) {
         WeakValueReference previous = this.valueReference;
         this.valueReference = new WeakValueReferenceImpl(queueForValues, value, this);
         previous.clear();
      }

      StrongKeyWeakValueEntry copy(ReferenceQueue queueForValues, StrongKeyWeakValueEntry newNext) {
         StrongKeyWeakValueEntry newEntry = new StrongKeyWeakValueEntry(this.key, this.hash, newNext);
         newEntry.valueReference = this.valueReference.copyFor(queueForValues, newEntry);
         return newEntry;
      }

      public WeakValueReference getValueReference() {
         return this.valueReference;
      }

      static final class Helper implements InternalEntryHelper {
         private static final Helper INSTANCE = new Helper();

         static Helper instance() {
            return INSTANCE;
         }

         public Strength keyStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public Strength valueStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }

         public StrongKeyWeakValueSegment newSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
            return new StrongKeyWeakValueSegment(map, initialCapacity, maxSegmentSize);
         }

         public StrongKeyWeakValueEntry copy(StrongKeyWeakValueSegment segment, StrongKeyWeakValueEntry entry, @Nullable StrongKeyWeakValueEntry newNext) {
            return MapMakerInternalMap.Segment.isCollected(entry) ? null : entry.copy(segment.queueForValues, newNext);
         }

         public void setValue(StrongKeyWeakValueSegment segment, StrongKeyWeakValueEntry entry, Object value) {
            entry.setValue(value, segment.queueForValues);
         }

         public StrongKeyWeakValueEntry newEntry(StrongKeyWeakValueSegment segment, Object key, int hash, @Nullable StrongKeyWeakValueEntry next) {
            return new StrongKeyWeakValueEntry(key, hash, next);
         }
      }
   }

   static final class StrongKeyStrongValueEntry extends AbstractStrongKeyEntry implements StrongValueEntry {
      @Nullable
      private volatile Object value = null;

      StrongKeyStrongValueEntry(Object key, int hash, @Nullable StrongKeyStrongValueEntry next) {
         super(key, hash, next);
      }

      @Nullable
      public Object getValue() {
         return this.value;
      }

      void setValue(Object value) {
         this.value = value;
      }

      StrongKeyStrongValueEntry copy(StrongKeyStrongValueEntry newNext) {
         StrongKeyStrongValueEntry newEntry = new StrongKeyStrongValueEntry(this.key, this.hash, newNext);
         newEntry.value = this.value;
         return newEntry;
      }

      static final class Helper implements InternalEntryHelper {
         private static final Helper INSTANCE = new Helper();

         static Helper instance() {
            return INSTANCE;
         }

         public Strength keyStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public Strength valueStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public StrongKeyStrongValueSegment newSegment(MapMakerInternalMap map, int initialCapacity, int maxSegmentSize) {
            return new StrongKeyStrongValueSegment(map, initialCapacity, maxSegmentSize);
         }

         public StrongKeyStrongValueEntry copy(StrongKeyStrongValueSegment segment, StrongKeyStrongValueEntry entry, @Nullable StrongKeyStrongValueEntry newNext) {
            return entry.copy(newNext);
         }

         public void setValue(StrongKeyStrongValueSegment segment, StrongKeyStrongValueEntry entry, Object value) {
            entry.setValue(value);
         }

         public StrongKeyStrongValueEntry newEntry(StrongKeyStrongValueSegment segment, Object key, int hash, @Nullable StrongKeyStrongValueEntry next) {
            return new StrongKeyStrongValueEntry(key, hash, next);
         }
      }
   }

   interface WeakValueEntry extends InternalEntry {
      WeakValueReference getValueReference();

      void clearValue();
   }

   interface StrongValueEntry extends InternalEntry {
   }

   abstract static class AbstractStrongKeyEntry implements InternalEntry {
      final Object key;
      final int hash;
      final InternalEntry next;

      AbstractStrongKeyEntry(Object key, int hash, @Nullable InternalEntry next) {
         this.key = key;
         this.hash = hash;
         this.next = next;
      }

      public Object getKey() {
         return this.key;
      }

      public int getHash() {
         return this.hash;
      }

      public InternalEntry getNext() {
         return this.next;
      }
   }

   interface InternalEntry {
      InternalEntry getNext();

      int getHash();

      Object getKey();

      Object getValue();
   }

   interface InternalEntryHelper {
      Strength keyStrength();

      Strength valueStrength();

      Segment newSegment(MapMakerInternalMap var1, int var2, int var3);

      InternalEntry newEntry(Segment var1, Object var2, int var3, @Nullable InternalEntry var4);

      InternalEntry copy(Segment var1, InternalEntry var2, @Nullable InternalEntry var3);

      void setValue(Segment var1, InternalEntry var2, Object var3);
   }

   static enum Strength {
      STRONG {
         Equivalence defaultEquivalence() {
            return Equivalence.equals();
         }
      },
      WEAK {
         Equivalence defaultEquivalence() {
            return Equivalence.identity();
         }
      };

      private Strength() {
      }

      abstract Equivalence defaultEquivalence();

      // $FF: synthetic method
      Strength(Object x2) {
         this();
      }
   }
}
