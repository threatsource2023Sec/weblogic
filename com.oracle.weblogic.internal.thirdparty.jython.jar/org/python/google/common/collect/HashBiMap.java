package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.j2objc.annotations.RetainedWith;

@GwtCompatible(
   emulated = true
)
public final class HashBiMap extends Maps.IteratorBasedAbstractMap implements BiMap, Serializable {
   private static final double LOAD_FACTOR = 1.0;
   private transient BiEntry[] hashTableKToV;
   private transient BiEntry[] hashTableVToK;
   private transient BiEntry firstInKeyInsertionOrder;
   private transient BiEntry lastInKeyInsertionOrder;
   private transient int size;
   private transient int mask;
   private transient int modCount;
   @RetainedWith
   private transient BiMap inverse;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static HashBiMap create() {
      return create(16);
   }

   public static HashBiMap create(int expectedSize) {
      return new HashBiMap(expectedSize);
   }

   public static HashBiMap create(Map map) {
      HashBiMap bimap = create(map.size());
      bimap.putAll(map);
      return bimap;
   }

   private HashBiMap(int expectedSize) {
      this.init(expectedSize);
   }

   private void init(int expectedSize) {
      CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
      int tableSize = Hashing.closedTableSize(expectedSize, 1.0);
      this.hashTableKToV = this.createTable(tableSize);
      this.hashTableVToK = this.createTable(tableSize);
      this.firstInKeyInsertionOrder = null;
      this.lastInKeyInsertionOrder = null;
      this.size = 0;
      this.mask = tableSize - 1;
      this.modCount = 0;
   }

   private void delete(BiEntry entry) {
      int keyBucket = entry.keyHash & this.mask;
      BiEntry prevBucketEntry = null;

      for(BiEntry bucketEntry = this.hashTableKToV[keyBucket]; bucketEntry != entry; bucketEntry = bucketEntry.nextInKToVBucket) {
         prevBucketEntry = bucketEntry;
      }

      if (prevBucketEntry == null) {
         this.hashTableKToV[keyBucket] = entry.nextInKToVBucket;
      } else {
         prevBucketEntry.nextInKToVBucket = entry.nextInKToVBucket;
      }

      int valueBucket = entry.valueHash & this.mask;
      prevBucketEntry = null;

      for(BiEntry bucketEntry = this.hashTableVToK[valueBucket]; bucketEntry != entry; bucketEntry = bucketEntry.nextInVToKBucket) {
         prevBucketEntry = bucketEntry;
      }

      if (prevBucketEntry == null) {
         this.hashTableVToK[valueBucket] = entry.nextInVToKBucket;
      } else {
         prevBucketEntry.nextInVToKBucket = entry.nextInVToKBucket;
      }

      if (entry.prevInKeyInsertionOrder == null) {
         this.firstInKeyInsertionOrder = entry.nextInKeyInsertionOrder;
      } else {
         entry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = entry.nextInKeyInsertionOrder;
      }

      if (entry.nextInKeyInsertionOrder == null) {
         this.lastInKeyInsertionOrder = entry.prevInKeyInsertionOrder;
      } else {
         entry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = entry.prevInKeyInsertionOrder;
      }

      --this.size;
      ++this.modCount;
   }

   private void insert(BiEntry entry, @Nullable BiEntry oldEntryForKey) {
      int keyBucket = entry.keyHash & this.mask;
      entry.nextInKToVBucket = this.hashTableKToV[keyBucket];
      this.hashTableKToV[keyBucket] = entry;
      int valueBucket = entry.valueHash & this.mask;
      entry.nextInVToKBucket = this.hashTableVToK[valueBucket];
      this.hashTableVToK[valueBucket] = entry;
      if (oldEntryForKey == null) {
         entry.prevInKeyInsertionOrder = this.lastInKeyInsertionOrder;
         entry.nextInKeyInsertionOrder = null;
         if (this.lastInKeyInsertionOrder == null) {
            this.firstInKeyInsertionOrder = entry;
         } else {
            this.lastInKeyInsertionOrder.nextInKeyInsertionOrder = entry;
         }

         this.lastInKeyInsertionOrder = entry;
      } else {
         entry.prevInKeyInsertionOrder = oldEntryForKey.prevInKeyInsertionOrder;
         if (entry.prevInKeyInsertionOrder == null) {
            this.firstInKeyInsertionOrder = entry;
         } else {
            entry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = entry;
         }

         entry.nextInKeyInsertionOrder = oldEntryForKey.nextInKeyInsertionOrder;
         if (entry.nextInKeyInsertionOrder == null) {
            this.lastInKeyInsertionOrder = entry;
         } else {
            entry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = entry;
         }
      }

      ++this.size;
      ++this.modCount;
   }

   private BiEntry seekByKey(@Nullable Object key, int keyHash) {
      for(BiEntry entry = this.hashTableKToV[keyHash & this.mask]; entry != null; entry = entry.nextInKToVBucket) {
         if (keyHash == entry.keyHash && Objects.equal(key, entry.key)) {
            return entry;
         }
      }

      return null;
   }

   private BiEntry seekByValue(@Nullable Object value, int valueHash) {
      for(BiEntry entry = this.hashTableVToK[valueHash & this.mask]; entry != null; entry = entry.nextInVToKBucket) {
         if (valueHash == entry.valueHash && Objects.equal(value, entry.value)) {
            return entry;
         }
      }

      return null;
   }

   public boolean containsKey(@Nullable Object key) {
      return this.seekByKey(key, Hashing.smearedHash(key)) != null;
   }

   public boolean containsValue(@Nullable Object value) {
      return this.seekByValue(value, Hashing.smearedHash(value)) != null;
   }

   @Nullable
   public Object get(@Nullable Object key) {
      return Maps.valueOrNull(this.seekByKey(key, Hashing.smearedHash(key)));
   }

   @CanIgnoreReturnValue
   public Object put(@Nullable Object key, @Nullable Object value) {
      return this.put(key, value, false);
   }

   @CanIgnoreReturnValue
   public Object forcePut(@Nullable Object key, @Nullable Object value) {
      return this.put(key, value, true);
   }

   private Object put(@Nullable Object key, @Nullable Object value, boolean force) {
      int keyHash = Hashing.smearedHash(key);
      int valueHash = Hashing.smearedHash(value);
      BiEntry oldEntryForKey = this.seekByKey(key, keyHash);
      if (oldEntryForKey != null && valueHash == oldEntryForKey.valueHash && Objects.equal(value, oldEntryForKey.value)) {
         return value;
      } else {
         BiEntry oldEntryForValue = this.seekByValue(value, valueHash);
         if (oldEntryForValue != null) {
            if (!force) {
               throw new IllegalArgumentException("value already present: " + value);
            }

            this.delete(oldEntryForValue);
         }

         BiEntry newEntry = new BiEntry(key, keyHash, value, valueHash);
         if (oldEntryForKey != null) {
            this.delete(oldEntryForKey);
            this.insert(newEntry, oldEntryForKey);
            oldEntryForKey.prevInKeyInsertionOrder = null;
            oldEntryForKey.nextInKeyInsertionOrder = null;
            this.rehashIfNecessary();
            return oldEntryForKey.value;
         } else {
            this.insert(newEntry, (BiEntry)null);
            this.rehashIfNecessary();
            return null;
         }
      }
   }

   @Nullable
   private Object putInverse(@Nullable Object value, @Nullable Object key, boolean force) {
      int valueHash = Hashing.smearedHash(value);
      int keyHash = Hashing.smearedHash(key);
      BiEntry oldEntryForValue = this.seekByValue(value, valueHash);
      if (oldEntryForValue != null && keyHash == oldEntryForValue.keyHash && Objects.equal(key, oldEntryForValue.key)) {
         return key;
      } else {
         BiEntry oldEntryForKey = this.seekByKey(key, keyHash);
         if (oldEntryForKey != null) {
            if (!force) {
               throw new IllegalArgumentException("value already present: " + key);
            }

            this.delete(oldEntryForKey);
         }

         if (oldEntryForValue != null) {
            this.delete(oldEntryForValue);
         }

         BiEntry newEntry = new BiEntry(key, keyHash, value, valueHash);
         this.insert(newEntry, oldEntryForKey);
         if (oldEntryForKey != null) {
            oldEntryForKey.prevInKeyInsertionOrder = null;
            oldEntryForKey.nextInKeyInsertionOrder = null;
         }

         this.rehashIfNecessary();
         return Maps.keyOrNull(oldEntryForValue);
      }
   }

   private void rehashIfNecessary() {
      BiEntry[] oldKToV = this.hashTableKToV;
      if (Hashing.needsResizing(this.size, oldKToV.length, 1.0)) {
         int newTableSize = oldKToV.length * 2;
         this.hashTableKToV = this.createTable(newTableSize);
         this.hashTableVToK = this.createTable(newTableSize);
         this.mask = newTableSize - 1;
         this.size = 0;

         for(BiEntry entry = this.firstInKeyInsertionOrder; entry != null; entry = entry.nextInKeyInsertionOrder) {
            this.insert(entry, entry);
         }

         ++this.modCount;
      }

   }

   private BiEntry[] createTable(int length) {
      return new BiEntry[length];
   }

   @CanIgnoreReturnValue
   public Object remove(@Nullable Object key) {
      BiEntry entry = this.seekByKey(key, Hashing.smearedHash(key));
      if (entry == null) {
         return null;
      } else {
         this.delete(entry);
         entry.prevInKeyInsertionOrder = null;
         entry.nextInKeyInsertionOrder = null;
         return entry.value;
      }
   }

   public void clear() {
      this.size = 0;
      Arrays.fill(this.hashTableKToV, (Object)null);
      Arrays.fill(this.hashTableVToK, (Object)null);
      this.firstInKeyInsertionOrder = null;
      this.lastInKeyInsertionOrder = null;
      ++this.modCount;
   }

   public int size() {
      return this.size;
   }

   public Set keySet() {
      return new KeySet();
   }

   public Set values() {
      return this.inverse().keySet();
   }

   Iterator entryIterator() {
      return new Itr() {
         Map.Entry output(BiEntry entry) {
            return new null.MapEntry(entry);
         }

         class MapEntry extends AbstractMapEntry {
            BiEntry delegate;

            MapEntry(BiEntry entry) {
               this.delegate = entry;
            }

            public Object getKey() {
               return this.delegate.key;
            }

            public Object getValue() {
               return this.delegate.value;
            }

            public Object setValue(Object value) {
               Object oldValue = this.delegate.value;
               int valueHash = Hashing.smearedHash(value);
               if (valueHash == this.delegate.valueHash && Objects.equal(value, oldValue)) {
                  return value;
               } else {
                  Preconditions.checkArgument(HashBiMap.this.seekByValue(value, valueHash) == null, "value already present: %s", value);
                  HashBiMap.this.delete(this.delegate);
                  BiEntry newEntry = new BiEntry(this.delegate.key, this.delegate.keyHash, value, valueHash);
                  HashBiMap.this.insert(newEntry, this.delegate);
                  this.delegate.prevInKeyInsertionOrder = null;
                  this.delegate.nextInKeyInsertionOrder = null;
                  expectedModCount = HashBiMap.this.modCount;
                  if (toRemove == this.delegate) {
                     toRemove = newEntry;
                  }

                  this.delegate = newEntry;
                  return oldValue;
               }
            }
         }
      };
   }

   public BiMap inverse() {
      return this.inverse == null ? (this.inverse = new Inverse()) : this.inverse;
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      Serialization.writeMap(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.init(16);
      int size = Serialization.readCount(stream);
      Serialization.populateMap(this, stream, size);
   }

   private static final class InverseSerializedForm implements Serializable {
      private final HashBiMap bimap;

      InverseSerializedForm(HashBiMap bimap) {
         this.bimap = bimap;
      }

      Object readResolve() {
         return this.bimap.inverse();
      }
   }

   private final class Inverse extends AbstractMap implements BiMap, Serializable {
      private Inverse() {
      }

      BiMap forward() {
         return HashBiMap.this;
      }

      public int size() {
         return HashBiMap.this.size;
      }

      public void clear() {
         this.forward().clear();
      }

      public boolean containsKey(@Nullable Object value) {
         return this.forward().containsValue(value);
      }

      public Object get(@Nullable Object value) {
         return Maps.keyOrNull(HashBiMap.this.seekByValue(value, Hashing.smearedHash(value)));
      }

      public Object put(@Nullable Object value, @Nullable Object key) {
         return HashBiMap.this.putInverse(value, key, false);
      }

      public Object forcePut(@Nullable Object value, @Nullable Object key) {
         return HashBiMap.this.putInverse(value, key, true);
      }

      public Object remove(@Nullable Object value) {
         BiEntry entry = HashBiMap.this.seekByValue(value, Hashing.smearedHash(value));
         if (entry == null) {
            return null;
         } else {
            HashBiMap.this.delete(entry);
            entry.prevInKeyInsertionOrder = null;
            entry.nextInKeyInsertionOrder = null;
            return entry.key;
         }
      }

      public BiMap inverse() {
         return this.forward();
      }

      public Set keySet() {
         return new InverseKeySet();
      }

      public Set values() {
         return this.forward().keySet();
      }

      public Set entrySet() {
         return new Maps.EntrySet() {
            Map map() {
               return Inverse.this;
            }

            public Iterator iterator() {
               return new Itr() {
                  Map.Entry output(BiEntry entry) {
                     return new null.InverseEntry(entry);
                  }

                  class InverseEntry extends AbstractMapEntry {
                     BiEntry delegate;

                     InverseEntry(BiEntry entry) {
                        this.delegate = entry;
                     }

                     public Object getKey() {
                        return this.delegate.value;
                     }

                     public Object getValue() {
                        return this.delegate.key;
                     }

                     public Object setValue(Object key) {
                        Object oldKey = this.delegate.key;
                        int keyHash = Hashing.smearedHash(key);
                        if (keyHash == this.delegate.keyHash && Objects.equal(key, oldKey)) {
                           return key;
                        } else {
                           Preconditions.checkArgument(HashBiMap.this.seekByKey(key, keyHash) == null, "value already present: %s", key);
                           HashBiMap.this.delete(this.delegate);
                           BiEntry newEntry = new BiEntry(key, keyHash, this.delegate.value, this.delegate.valueHash);
                           this.delegate = newEntry;
                           HashBiMap.this.insert(newEntry, (BiEntry)null);
                           expectedModCount = HashBiMap.this.modCount;
                           return oldKey;
                        }
                     }
                  }
               };
            }
         };
      }

      Object writeReplace() {
         return new InverseSerializedForm(HashBiMap.this);
      }

      // $FF: synthetic method
      Inverse(Object x1) {
         this();
      }

      private final class InverseKeySet extends Maps.KeySet {
         InverseKeySet() {
            super(Inverse.this);
         }

         public boolean remove(@Nullable Object o) {
            BiEntry entry = HashBiMap.this.seekByValue(o, Hashing.smearedHash(o));
            if (entry == null) {
               return false;
            } else {
               HashBiMap.this.delete(entry);
               return true;
            }
         }

         public Iterator iterator() {
            return new Itr() {
               Object output(BiEntry entry) {
                  return entry.value;
               }
            };
         }
      }
   }

   private final class KeySet extends Maps.KeySet {
      KeySet() {
         super(HashBiMap.this);
      }

      public Iterator iterator() {
         return new Itr() {
            Object output(BiEntry entry) {
               return entry.key;
            }
         };
      }

      public boolean remove(@Nullable Object o) {
         BiEntry entry = HashBiMap.this.seekByKey(o, Hashing.smearedHash(o));
         if (entry == null) {
            return false;
         } else {
            HashBiMap.this.delete(entry);
            entry.prevInKeyInsertionOrder = null;
            entry.nextInKeyInsertionOrder = null;
            return true;
         }
      }
   }

   abstract class Itr implements Iterator {
      BiEntry next;
      BiEntry toRemove;
      int expectedModCount;

      Itr() {
         this.next = HashBiMap.this.firstInKeyInsertionOrder;
         this.toRemove = null;
         this.expectedModCount = HashBiMap.this.modCount;
      }

      public boolean hasNext() {
         if (HashBiMap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         } else {
            return this.next != null;
         }
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            BiEntry entry = this.next;
            this.next = entry.nextInKeyInsertionOrder;
            this.toRemove = entry;
            return this.output(entry);
         }
      }

      public void remove() {
         if (HashBiMap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         } else {
            CollectPreconditions.checkRemove(this.toRemove != null);
            HashBiMap.this.delete(this.toRemove);
            this.expectedModCount = HashBiMap.this.modCount;
            this.toRemove = null;
         }
      }

      abstract Object output(BiEntry var1);
   }

   private static final class BiEntry extends ImmutableEntry {
      final int keyHash;
      final int valueHash;
      @Nullable
      BiEntry nextInKToVBucket;
      @Nullable
      BiEntry nextInVToKBucket;
      @Nullable
      BiEntry nextInKeyInsertionOrder;
      @Nullable
      BiEntry prevInKeyInsertionOrder;

      BiEntry(Object key, int keyHash, Object value, int valueHash) {
         super(key, value);
         this.keyHash = keyHash;
         this.valueHash = valueHash;
      }
   }
}
