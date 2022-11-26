package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentReferenceHashMap extends AbstractMap implements ConcurrentMap {
   private static final int DEFAULT_INITIAL_CAPACITY = 16;
   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
   private static final ReferenceType DEFAULT_REFERENCE_TYPE;
   private static final int MAXIMUM_CONCURRENCY_LEVEL = 65536;
   private static final int MAXIMUM_SEGMENT_SIZE = 1073741824;
   private final Segment[] segments;
   private final float loadFactor;
   private final ReferenceType referenceType;
   private final int shift;
   @Nullable
   private volatile Set entrySet;

   public ConcurrentReferenceHashMap() {
      this(16, 0.75F, 16, DEFAULT_REFERENCE_TYPE);
   }

   public ConcurrentReferenceHashMap(int initialCapacity) {
      this(initialCapacity, 0.75F, 16, DEFAULT_REFERENCE_TYPE);
   }

   public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor) {
      this(initialCapacity, loadFactor, 16, DEFAULT_REFERENCE_TYPE);
   }

   public ConcurrentReferenceHashMap(int initialCapacity, int concurrencyLevel) {
      this(initialCapacity, 0.75F, concurrencyLevel, DEFAULT_REFERENCE_TYPE);
   }

   public ConcurrentReferenceHashMap(int initialCapacity, ReferenceType referenceType) {
      this(initialCapacity, 0.75F, 16, referenceType);
   }

   public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
      this(initialCapacity, loadFactor, concurrencyLevel, DEFAULT_REFERENCE_TYPE);
   }

   public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor, int concurrencyLevel, ReferenceType referenceType) {
      Assert.isTrue(initialCapacity >= 0, "Initial capacity must not be negative");
      Assert.isTrue(loadFactor > 0.0F, "Load factor must be positive");
      Assert.isTrue(concurrencyLevel > 0, "Concurrency level must be positive");
      Assert.notNull(referenceType, (String)"Reference type must not be null");
      this.loadFactor = loadFactor;
      this.shift = calculateShift(concurrencyLevel, 65536);
      int size = 1 << this.shift;
      this.referenceType = referenceType;
      int roundedUpSegmentCapacity = (int)(((long)(initialCapacity + size) - 1L) / (long)size);
      int initialSize = 1 << calculateShift(roundedUpSegmentCapacity, 1073741824);
      Segment[] segments = (Segment[])((Segment[])Array.newInstance(Segment.class, size));
      int resizeThreshold = (int)((float)initialSize * this.getLoadFactor());

      for(int i = 0; i < segments.length; ++i) {
         segments[i] = new Segment(initialSize, resizeThreshold);
      }

      this.segments = segments;
   }

   protected final float getLoadFactor() {
      return this.loadFactor;
   }

   protected final int getSegmentsSize() {
      return this.segments.length;
   }

   protected final Segment getSegment(int index) {
      return this.segments[index];
   }

   protected ReferenceManager createReferenceManager() {
      return new ReferenceManager();
   }

   protected int getHash(@Nullable Object o) {
      int hash = o != null ? o.hashCode() : 0;
      hash += hash << 15 ^ -12931;
      hash ^= hash >>> 10;
      hash += hash << 3;
      hash ^= hash >>> 6;
      hash += (hash << 2) + (hash << 14);
      hash ^= hash >>> 16;
      return hash;
   }

   @Nullable
   public Object get(@Nullable Object key) {
      Entry entry = this.getEntryIfAvailable(key);
      return entry != null ? entry.getValue() : null;
   }

   @Nullable
   public Object getOrDefault(@Nullable Object key, @Nullable Object defaultValue) {
      Entry entry = this.getEntryIfAvailable(key);
      return entry != null ? entry.getValue() : defaultValue;
   }

   public boolean containsKey(@Nullable Object key) {
      Entry entry = this.getEntryIfAvailable(key);
      return entry != null && ObjectUtils.nullSafeEquals(entry.getKey(), key);
   }

   @Nullable
   private Entry getEntryIfAvailable(@Nullable Object key) {
      Reference ref = this.getReference(key, ConcurrentReferenceHashMap.Restructure.WHEN_NECESSARY);
      return ref != null ? ref.get() : null;
   }

   @Nullable
   protected final Reference getReference(@Nullable Object key, Restructure restructure) {
      int hash = this.getHash(key);
      return this.getSegmentForHash(hash).getReference(key, hash, restructure);
   }

   @Nullable
   public Object put(@Nullable Object key, @Nullable Object value) {
      return this.put(key, value, true);
   }

   @Nullable
   public Object putIfAbsent(@Nullable Object key, @Nullable Object value) {
      return this.put(key, value, false);
   }

   @Nullable
   private Object put(@Nullable Object key, @Nullable final Object value, final boolean overwriteExisting) {
      return this.doTask(key, new Task(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_BEFORE, ConcurrentReferenceHashMap.TaskOption.RESIZE}) {
         @Nullable
         protected Object execute(@Nullable Reference ref, @Nullable Entry entry, @Nullable Entries entries) {
            if (entry != null) {
               Object oldValue = entry.getValue();
               if (overwriteExisting) {
                  entry.setValue(value);
               }

               return oldValue;
            } else {
               Assert.state(entries != null, "No entries segment");
               entries.add(value);
               return null;
            }
         }
      });
   }

   @Nullable
   public Object remove(Object key) {
      return this.doTask(key, new Task(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_AFTER, ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY}) {
         @Nullable
         protected Object execute(@Nullable Reference ref, @Nullable Entry entry) {
            if (entry != null) {
               if (ref != null) {
                  ref.release();
               }

               return entry.value;
            } else {
               return null;
            }
         }
      });
   }

   public boolean remove(Object key, final Object value) {
      Boolean result = (Boolean)this.doTask(key, new Task(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_AFTER, ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY}) {
         protected Boolean execute(@Nullable Reference ref, @Nullable Entry entry) {
            if (entry != null && ObjectUtils.nullSafeEquals(entry.getValue(), value)) {
               if (ref != null) {
                  ref.release();
               }

               return true;
            } else {
               return false;
            }
         }
      });
      return result == Boolean.TRUE;
   }

   public boolean replace(Object key, final Object oldValue, final Object newValue) {
      Boolean result = (Boolean)this.doTask(key, new Task(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_BEFORE, ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY}) {
         protected Boolean execute(@Nullable Reference ref, @Nullable Entry entry) {
            if (entry != null && ObjectUtils.nullSafeEquals(entry.getValue(), oldValue)) {
               entry.setValue(newValue);
               return true;
            } else {
               return false;
            }
         }
      });
      return result == Boolean.TRUE;
   }

   @Nullable
   public Object replace(Object key, final Object value) {
      return this.doTask(key, new Task(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_BEFORE, ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY}) {
         @Nullable
         protected Object execute(@Nullable Reference ref, @Nullable Entry entry) {
            if (entry != null) {
               Object oldValue = entry.getValue();
               entry.setValue(value);
               return oldValue;
            } else {
               return null;
            }
         }
      });
   }

   public void clear() {
      Segment[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Segment segment = var1[var3];
         segment.clear();
      }

   }

   public void purgeUnreferencedEntries() {
      Segment[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Segment segment = var1[var3];
         segment.restructureIfNecessary(false);
      }

   }

   public int size() {
      int size = 0;
      Segment[] var2 = this.segments;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Segment segment = var2[var4];
         size += segment.getCount();
      }

      return size;
   }

   public boolean isEmpty() {
      Segment[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Segment segment = var1[var3];
         if (segment.getCount() > 0) {
            return false;
         }
      }

      return true;
   }

   public Set entrySet() {
      Set entrySet = this.entrySet;
      if (entrySet == null) {
         entrySet = new EntrySet();
         this.entrySet = (Set)entrySet;
      }

      return (Set)entrySet;
   }

   @Nullable
   private Object doTask(@Nullable Object key, Task task) {
      int hash = this.getHash(key);
      return this.getSegmentForHash(hash).doTask(hash, key, task);
   }

   private Segment getSegmentForHash(int hash) {
      return this.segments[hash >>> 32 - this.shift & this.segments.length - 1];
   }

   protected static int calculateShift(int minimumValue, int maximumValue) {
      int shift = 0;

      for(int value = 1; value < minimumValue && value < maximumValue; ++shift) {
         value <<= 1;
      }

      return shift;
   }

   static {
      DEFAULT_REFERENCE_TYPE = ConcurrentReferenceHashMap.ReferenceType.SOFT;
   }

   private static final class WeakEntryReference extends WeakReference implements Reference {
      private final int hash;
      @Nullable
      private final Reference nextReference;

      public WeakEntryReference(Entry entry, int hash, @Nullable Reference next, ReferenceQueue queue) {
         super(entry, queue);
         this.hash = hash;
         this.nextReference = next;
      }

      public int getHash() {
         return this.hash;
      }

      @Nullable
      public Reference getNext() {
         return this.nextReference;
      }

      public void release() {
         this.enqueue();
         this.clear();
      }
   }

   private static final class SoftEntryReference extends SoftReference implements Reference {
      private final int hash;
      @Nullable
      private final Reference nextReference;

      public SoftEntryReference(Entry entry, int hash, @Nullable Reference next, ReferenceQueue queue) {
         super(entry, queue);
         this.hash = hash;
         this.nextReference = next;
      }

      public int getHash() {
         return this.hash;
      }

      @Nullable
      public Reference getNext() {
         return this.nextReference;
      }

      public void release() {
         this.enqueue();
         this.clear();
      }
   }

   protected class ReferenceManager {
      private final ReferenceQueue queue = new ReferenceQueue();

      public Reference createReference(Entry entry, int hash, @Nullable Reference next) {
         return (Reference)(ConcurrentReferenceHashMap.this.referenceType == ConcurrentReferenceHashMap.ReferenceType.WEAK ? new WeakEntryReference(entry, hash, next, this.queue) : new SoftEntryReference(entry, hash, next, this.queue));
      }

      @Nullable
      public Reference pollForPurge() {
         return (Reference)this.queue.poll();
      }
   }

   protected static enum Restructure {
      WHEN_NECESSARY,
      NEVER;
   }

   private class EntryIterator implements Iterator {
      private int segmentIndex;
      private int referenceIndex;
      @Nullable
      private Reference[] references;
      @Nullable
      private Reference reference;
      @Nullable
      private Entry next;
      @Nullable
      private Entry last;

      public EntryIterator() {
         this.moveToNextSegment();
      }

      public boolean hasNext() {
         this.getNextIfNecessary();
         return this.next != null;
      }

      public Entry next() {
         this.getNextIfNecessary();
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            this.last = this.next;
            this.next = null;
            return this.last;
         }
      }

      private void getNextIfNecessary() {
         while(this.next == null) {
            this.moveToNextReference();
            if (this.reference == null) {
               return;
            }

            this.next = this.reference.get();
         }

      }

      private void moveToNextReference() {
         if (this.reference != null) {
            this.reference = this.reference.getNext();
         }

         while(this.reference == null && this.references != null) {
            if (this.referenceIndex >= this.references.length) {
               this.moveToNextSegment();
               this.referenceIndex = 0;
            } else {
               this.reference = this.references[this.referenceIndex];
               ++this.referenceIndex;
            }
         }

      }

      private void moveToNextSegment() {
         this.reference = null;
         this.references = null;
         if (this.segmentIndex < ConcurrentReferenceHashMap.this.segments.length) {
            this.references = ConcurrentReferenceHashMap.this.segments[this.segmentIndex].references;
            ++this.segmentIndex;
         }

      }

      public void remove() {
         Assert.state(this.last != null, "No element to remove");
         ConcurrentReferenceHashMap.this.remove(this.last.getKey());
      }
   }

   private class EntrySet extends AbstractSet {
      private EntrySet() {
      }

      public Iterator iterator() {
         return ConcurrentReferenceHashMap.this.new EntryIterator();
      }

      public boolean contains(@Nullable Object o) {
         if (o instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry)o;
            Reference ref = ConcurrentReferenceHashMap.this.getReference(entry.getKey(), ConcurrentReferenceHashMap.Restructure.NEVER);
            Entry otherEntry = ref != null ? ref.get() : null;
            if (otherEntry != null) {
               return ObjectUtils.nullSafeEquals(otherEntry.getValue(), otherEntry.getValue());
            }
         }

         return false;
      }

      public boolean remove(Object o) {
         if (o instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry)o;
            return ConcurrentReferenceHashMap.this.remove(entry.getKey(), entry.getValue());
         } else {
            return false;
         }
      }

      public int size() {
         return ConcurrentReferenceHashMap.this.size();
      }

      public void clear() {
         ConcurrentReferenceHashMap.this.clear();
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }

   private abstract class Entries {
      private Entries() {
      }

      public abstract void add(@Nullable Object var1);

      // $FF: synthetic method
      Entries(Object x1) {
         this();
      }
   }

   private static enum TaskOption {
      RESTRUCTURE_BEFORE,
      RESTRUCTURE_AFTER,
      SKIP_IF_EMPTY,
      RESIZE;
   }

   private abstract class Task {
      private final EnumSet options;

      public Task(TaskOption... options) {
         this.options = options.length == 0 ? EnumSet.noneOf(TaskOption.class) : EnumSet.of(options[0], options);
      }

      public boolean hasOption(TaskOption option) {
         return this.options.contains(option);
      }

      @Nullable
      protected Object execute(@Nullable Reference ref, @Nullable Entry entry, @Nullable Entries entries) {
         return this.execute(ref, entry);
      }

      @Nullable
      protected Object execute(@Nullable Reference ref, @Nullable Entry entry) {
         return null;
      }
   }

   protected static final class Entry implements Map.Entry {
      @Nullable
      private final Object key;
      @Nullable
      private volatile Object value;

      public Entry(@Nullable Object key, @Nullable Object value) {
         this.key = key;
         this.value = value;
      }

      @Nullable
      public Object getKey() {
         return this.key;
      }

      @Nullable
      public Object getValue() {
         return this.value;
      }

      @Nullable
      public Object setValue(@Nullable Object value) {
         Object previous = this.value;
         this.value = value;
         return previous;
      }

      public String toString() {
         return this.key + "=" + this.value;
      }

      public final boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry otherEntry = (Map.Entry)other;
            return ObjectUtils.nullSafeEquals(this.getKey(), otherEntry.getKey()) && ObjectUtils.nullSafeEquals(this.getValue(), otherEntry.getValue());
         }
      }

      public final int hashCode() {
         return ObjectUtils.nullSafeHashCode(this.key) ^ ObjectUtils.nullSafeHashCode(this.value);
      }
   }

   protected interface Reference {
      @Nullable
      Entry get();

      int getHash();

      @Nullable
      Reference getNext();

      void release();
   }

   protected final class Segment extends ReentrantLock {
      private final ReferenceManager referenceManager = ConcurrentReferenceHashMap.this.createReferenceManager();
      private final int initialSize;
      private volatile Reference[] references;
      private volatile int count = 0;
      private int resizeThreshold;

      public Segment(int initialSize, int resizeThreshold) {
         this.initialSize = initialSize;
         this.references = this.createReferenceArray(initialSize);
         this.resizeThreshold = resizeThreshold;
      }

      @Nullable
      public Reference getReference(@Nullable Object key, int hash, Restructure restructure) {
         if (restructure == ConcurrentReferenceHashMap.Restructure.WHEN_NECESSARY) {
            this.restructureIfNecessary(false);
         }

         if (this.count == 0) {
            return null;
         } else {
            Reference[] references = this.references;
            int index = this.getIndex(hash, references);
            Reference head = references[index];
            return this.findInChain(head, key, hash);
         }
      }

      @Nullable
      public Object doTask(final int hash, @Nullable final Object key, Task task) {
         boolean resize = task.hasOption(ConcurrentReferenceHashMap.TaskOption.RESIZE);
         if (task.hasOption(ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_BEFORE)) {
            this.restructureIfNecessary(resize);
         }

         if (task.hasOption(ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY) && this.count == 0) {
            return task.execute((Reference)null, (Entry)null, (Entries)null);
         } else {
            this.lock();

            Object var10;
            try {
               final int index = this.getIndex(hash, this.references);
               final Reference head = this.references[index];
               Reference ref = this.findInChain(head, key, hash);
               Entry entry = ref != null ? ref.get() : null;
               Entries entries = new Entries() {
                  public void add(@Nullable Object value) {
                     Entry newEntry = new Entry(key, value);
                     Reference newReference = Segment.this.referenceManager.createReference(newEntry, hash, head);
                     Segment.this.references[index] = newReference;
                     Segment.this.count++;
                  }
               };
               var10 = task.execute(ref, entry, entries);
            } finally {
               this.unlock();
               if (task.hasOption(ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_AFTER)) {
                  this.restructureIfNecessary(resize);
               }

            }

            return var10;
         }
      }

      public void clear() {
         if (this.count != 0) {
            this.lock();

            try {
               this.references = this.createReferenceArray(this.initialSize);
               this.resizeThreshold = (int)((float)this.references.length * ConcurrentReferenceHashMap.this.getLoadFactor());
               this.count = 0;
            } finally {
               this.unlock();
            }

         }
      }

      protected final void restructureIfNecessary(boolean allowResize) {
         int currCount = this.count;
         boolean needsResize = currCount > 0 && currCount >= this.resizeThreshold;
         Reference ref = this.referenceManager.pollForPurge();
         if (ref != null || needsResize && allowResize) {
            this.lock();

            try {
               int countAfterRestructure = this.count;
               Set toPurge = Collections.emptySet();
               if (ref != null) {
                  for(toPurge = new HashSet(); ref != null; ref = this.referenceManager.pollForPurge()) {
                     ((Set)toPurge).add(ref);
                  }
               }

               countAfterRestructure -= ((Set)toPurge).size();
               needsResize = countAfterRestructure > 0 && countAfterRestructure >= this.resizeThreshold;
               boolean resizing = false;
               int restructureSize = this.references.length;
               if (allowResize && needsResize && restructureSize < 1073741824) {
                  restructureSize <<= 1;
                  resizing = true;
               }

               Reference[] restructured = resizing ? this.createReferenceArray(restructureSize) : this.references;

               for(int i = 0; i < this.references.length; ++i) {
                  ref = this.references[i];
                  if (!resizing) {
                     restructured[i] = null;
                  }

                  for(; ref != null; ref = ref.getNext()) {
                     if (!((Set)toPurge).contains(ref)) {
                        Entry entry = ref.get();
                        if (entry != null) {
                           int index = this.getIndex(ref.getHash(), restructured);
                           restructured[index] = this.referenceManager.createReference(entry, ref.getHash(), restructured[index]);
                        }
                     }
                  }
               }

               if (resizing) {
                  this.references = restructured;
                  this.resizeThreshold = (int)((float)this.references.length * ConcurrentReferenceHashMap.this.getLoadFactor());
               }

               this.count = Math.max(countAfterRestructure, 0);
            } finally {
               this.unlock();
            }
         }

      }

      @Nullable
      private Reference findInChain(Reference ref, @Nullable Object key, int hash) {
         for(Reference currRef = ref; currRef != null; currRef = currRef.getNext()) {
            if (currRef.getHash() == hash) {
               Entry entry = currRef.get();
               if (entry != null) {
                  Object entryKey = entry.getKey();
                  if (ObjectUtils.nullSafeEquals(entryKey, key)) {
                     return currRef;
                  }
               }
            }
         }

         return null;
      }

      private Reference[] createReferenceArray(int size) {
         return new Reference[size];
      }

      private int getIndex(int hash, Reference[] references) {
         return hash & references.length - 1;
      }

      public final int getSize() {
         return this.references.length;
      }

      public final int getCount() {
         return this.count;
      }
   }

   public static enum ReferenceType {
      SOFT,
      WEAK;
   }
}
