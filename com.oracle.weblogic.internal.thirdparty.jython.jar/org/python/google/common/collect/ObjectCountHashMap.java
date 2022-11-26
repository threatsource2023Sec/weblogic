package org.python.google.common.collect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true,
   emulated = true
)
class ObjectCountHashMap extends AbstractObjectCountMap {
   private static final int MAXIMUM_CAPACITY = 1073741824;
   static final float DEFAULT_LOAD_FACTOR = 1.0F;
   private static final long NEXT_MASK = 4294967295L;
   private static final long HASH_MASK = -4294967296L;
   static final int DEFAULT_SIZE = 3;
   static final int UNSET = -1;
   private transient int[] table;
   @VisibleForTesting
   transient long[] entries;
   private transient float loadFactor;
   private transient int threshold;

   public static ObjectCountHashMap create() {
      return new ObjectCountHashMap();
   }

   public static ObjectCountHashMap createWithExpectedSize(int expectedSize) {
      return new ObjectCountHashMap(expectedSize);
   }

   ObjectCountHashMap() {
      this.init(3, 1.0F);
   }

   ObjectCountHashMap(AbstractObjectCountMap map) {
      this.init(map.size(), 1.0F);

      for(int i = map.firstIndex(); i != -1; i = map.nextIndex(i)) {
         this.put(map.getKey(i), map.getValue(i));
      }

   }

   ObjectCountHashMap(int capacity) {
      this(capacity, 1.0F);
   }

   ObjectCountHashMap(int expectedSize, float loadFactor) {
      this.init(expectedSize, loadFactor);
   }

   void init(int expectedSize, float loadFactor) {
      Preconditions.checkArgument(expectedSize >= 0, "Initial capacity must be non-negative");
      Preconditions.checkArgument(loadFactor > 0.0F, "Illegal load factor");
      int buckets = Hashing.closedTableSize(expectedSize, (double)loadFactor);
      this.table = newTable(buckets);
      this.loadFactor = loadFactor;
      this.keys = new Object[expectedSize];
      this.values = new int[expectedSize];
      this.entries = newEntries(expectedSize);
      this.threshold = Math.max(1, (int)((float)buckets * loadFactor));
   }

   private static int[] newTable(int size) {
      int[] array = new int[size];
      Arrays.fill(array, -1);
      return array;
   }

   private static long[] newEntries(int size) {
      long[] array = new long[size];
      Arrays.fill(array, -1L);
      return array;
   }

   private int hashTableMask() {
      return this.table.length - 1;
   }

   private static int getHash(long entry) {
      return (int)(entry >>> 32);
   }

   private static int getNext(long entry) {
      return (int)entry;
   }

   private static long swapNext(long entry, int newNext) {
      return -4294967296L & entry | 4294967295L & (long)newNext;
   }

   @CanIgnoreReturnValue
   public int put(@Nullable Object key, int value) {
      CollectPreconditions.checkPositive(value, "count");
      long[] entries = this.entries;
      Object[] keys = this.keys;
      int[] values = this.values;
      int hash = Hashing.smearedHash(key);
      int tableIndex = hash & this.hashTableMask();
      int newEntryIndex = this.size;
      int next = this.table[tableIndex];
      int last;
      if (next == -1) {
         this.table[tableIndex] = newEntryIndex;
      } else {
         long entry;
         do {
            last = next;
            entry = entries[next];
            if (getHash(entry) == hash && Objects.equal(key, keys[next])) {
               int oldValue = values[next];
               values[next] = value;
               return oldValue;
            }

            next = getNext(entry);
         } while(next != -1);

         entries[last] = swapNext(entry, newEntryIndex);
      }

      if (newEntryIndex == Integer.MAX_VALUE) {
         throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
      } else {
         last = newEntryIndex + 1;
         this.resizeMeMaybe(last);
         this.insertEntry(newEntryIndex, key, value, hash);
         this.size = last;
         if (newEntryIndex >= this.threshold) {
            this.resizeTable(2 * this.table.length);
         }

         ++this.modCount;
         return 0;
      }
   }

   void insertEntry(int entryIndex, @Nullable Object key, int value, int hash) {
      this.entries[entryIndex] = (long)hash << 32 | 4294967295L;
      this.keys[entryIndex] = key;
      this.values[entryIndex] = value;
   }

   private void resizeMeMaybe(int newSize) {
      int entriesSize = this.entries.length;
      if (newSize > entriesSize) {
         int newCapacity = entriesSize + Math.max(1, entriesSize >>> 1);
         if (newCapacity < 0) {
            newCapacity = Integer.MAX_VALUE;
         }

         if (newCapacity != entriesSize) {
            this.resizeEntries(newCapacity);
         }
      }

   }

   void resizeEntries(int newCapacity) {
      this.keys = Arrays.copyOf(this.keys, newCapacity);
      this.values = Arrays.copyOf(this.values, newCapacity);
      long[] entries = this.entries;
      int oldCapacity = entries.length;
      entries = Arrays.copyOf(entries, newCapacity);
      if (newCapacity > oldCapacity) {
         Arrays.fill(entries, oldCapacity, newCapacity, -1L);
      }

      this.entries = entries;
   }

   private void resizeTable(int newCapacity) {
      int[] oldTable = this.table;
      int oldCapacity = oldTable.length;
      if (oldCapacity >= 1073741824) {
         this.threshold = Integer.MAX_VALUE;
      } else {
         int newThreshold = 1 + (int)((float)newCapacity * this.loadFactor);
         int[] newTable = newTable(newCapacity);
         long[] entries = this.entries;
         int mask = newTable.length - 1;

         for(int i = 0; i < this.size; ++i) {
            long oldEntry = entries[i];
            int hash = getHash(oldEntry);
            int tableIndex = hash & mask;
            int next = newTable[tableIndex];
            newTable[tableIndex] = i;
            entries[i] = (long)hash << 32 | 4294967295L & (long)next;
         }

         this.threshold = newThreshold;
         this.table = newTable;
      }
   }

   int indexOf(@Nullable Object key) {
      int hash = Hashing.smearedHash(key);

      long entry;
      for(int next = this.table[hash & this.hashTableMask()]; next != -1; next = getNext(entry)) {
         entry = this.entries[next];
         if (getHash(entry) == hash && Objects.equal(key, this.keys[next])) {
            return next;
         }
      }

      return -1;
   }

   public boolean containsKey(@Nullable Object key) {
      return this.indexOf(key) != -1;
   }

   public int get(@Nullable Object key) {
      int index = this.indexOf(key);
      return index == -1 ? 0 : this.values[index];
   }

   @CanIgnoreReturnValue
   public int remove(@Nullable Object key) {
      return this.remove(key, Hashing.smearedHash(key));
   }

   @CanIgnoreReturnValue
   int removeEntry(int entryIndex) {
      return this.remove(this.keys[entryIndex], getHash(this.entries[entryIndex]));
   }

   private int remove(@Nullable Object key, int hash) {
      int tableIndex = hash & this.hashTableMask();
      int next = this.table[tableIndex];
      if (next == -1) {
         return 0;
      } else {
         int last = -1;

         do {
            if (getHash(this.entries[next]) == hash && Objects.equal(key, this.keys[next])) {
               int oldValue = this.values[next];
               if (last == -1) {
                  this.table[tableIndex] = getNext(this.entries[next]);
               } else {
                  this.entries[last] = swapNext(this.entries[last], getNext(this.entries[next]));
               }

               this.moveLastEntry(next);
               --this.size;
               ++this.modCount;
               return oldValue;
            }

            last = next;
            next = getNext(this.entries[next]);
         } while(next != -1);

         return 0;
      }
   }

   void moveLastEntry(int dstIndex) {
      int srcIndex = this.size() - 1;
      if (dstIndex < srcIndex) {
         this.keys[dstIndex] = this.keys[srcIndex];
         this.values[dstIndex] = this.values[srcIndex];
         this.keys[srcIndex] = null;
         this.values[srcIndex] = 0;
         long lastEntry = this.entries[srcIndex];
         this.entries[dstIndex] = lastEntry;
         this.entries[srcIndex] = -1L;
         int tableIndex = getHash(lastEntry) & this.hashTableMask();
         int lastNext = this.table[tableIndex];
         if (lastNext == srcIndex) {
            this.table[tableIndex] = dstIndex;
         } else {
            int previous;
            long entry;
            do {
               previous = lastNext;
               lastNext = getNext(entry = this.entries[lastNext]);
            } while(lastNext != srcIndex);

            this.entries[previous] = swapNext(entry, dstIndex);
         }
      } else {
         this.keys[dstIndex] = null;
         this.values[dstIndex] = 0;
         this.entries[dstIndex] = -1L;
      }

   }

   Set createEntrySet() {
      return new HashEntrySetView();
   }

   public void clear() {
      ++this.modCount;
      Arrays.fill(this.keys, 0, this.size, (Object)null);
      Arrays.fill(this.values, 0, this.size, 0);
      Arrays.fill(this.table, -1);
      Arrays.fill(this.entries, -1L);
      this.size = 0;
   }

   class HashEntrySetView extends AbstractObjectCountMap.EntrySetView {
      HashEntrySetView() {
         super();
      }

      public Iterator iterator() {
         return new AbstractObjectCountMap.Itr() {
            Multiset.Entry getOutput(int entry) {
               return ObjectCountHashMap.this.new MapEntry(entry);
            }
         };
      }
   }
}
