package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Objects;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public final class LinkedHashMultimap extends LinkedHashMultimapGwtSerializationDependencies {
   private static final int DEFAULT_KEY_CAPACITY = 16;
   private static final int DEFAULT_VALUE_SET_CAPACITY = 2;
   @VisibleForTesting
   static final double VALUE_SET_LOAD_FACTOR = 1.0;
   @VisibleForTesting
   transient int valueSetCapacity = 2;
   private transient ValueEntry multimapHeaderEntry;
   @GwtIncompatible
   private static final long serialVersionUID = 1L;

   public static LinkedHashMultimap create() {
      return new LinkedHashMultimap(16, 2);
   }

   public static LinkedHashMultimap create(int expectedKeys, int expectedValuesPerKey) {
      return new LinkedHashMultimap(Maps.capacity(expectedKeys), Maps.capacity(expectedValuesPerKey));
   }

   public static LinkedHashMultimap create(Multimap multimap) {
      LinkedHashMultimap result = create(multimap.keySet().size(), 2);
      result.putAll(multimap);
      return result;
   }

   private static void succeedsInValueSet(ValueSetLink pred, ValueSetLink succ) {
      pred.setSuccessorInValueSet(succ);
      succ.setPredecessorInValueSet(pred);
   }

   private static void succeedsInMultimap(ValueEntry pred, ValueEntry succ) {
      pred.setSuccessorInMultimap(succ);
      succ.setPredecessorInMultimap(pred);
   }

   private static void deleteFromValueSet(ValueSetLink entry) {
      succeedsInValueSet(entry.getPredecessorInValueSet(), entry.getSuccessorInValueSet());
   }

   private static void deleteFromMultimap(ValueEntry entry) {
      succeedsInMultimap(entry.getPredecessorInMultimap(), entry.getSuccessorInMultimap());
   }

   private LinkedHashMultimap(int keyCapacity, int valueSetCapacity) {
      super(new LinkedHashMap(keyCapacity));
      CollectPreconditions.checkNonnegative(valueSetCapacity, "expectedValuesPerKey");
      this.valueSetCapacity = valueSetCapacity;
      this.multimapHeaderEntry = new ValueEntry((Object)null, (Object)null, 0, (ValueEntry)null);
      succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
   }

   Set createCollection() {
      return new LinkedHashSet(this.valueSetCapacity);
   }

   Collection createCollection(Object key) {
      return new ValueSet(key, this.valueSetCapacity);
   }

   @CanIgnoreReturnValue
   public Set replaceValues(@Nullable Object key, Iterable values) {
      return super.replaceValues(key, values);
   }

   public Set entries() {
      return super.entries();
   }

   public Set keySet() {
      return super.keySet();
   }

   public Collection values() {
      return super.values();
   }

   Iterator entryIterator() {
      return new Iterator() {
         ValueEntry nextEntry;
         ValueEntry toRemove;

         {
            this.nextEntry = LinkedHashMultimap.this.multimapHeaderEntry.successorInMultimap;
         }

         public boolean hasNext() {
            return this.nextEntry != LinkedHashMultimap.this.multimapHeaderEntry;
         }

         public Map.Entry next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               ValueEntry result = this.nextEntry;
               this.toRemove = result;
               this.nextEntry = this.nextEntry.successorInMultimap;
               return result;
            }
         }

         public void remove() {
            CollectPreconditions.checkRemove(this.toRemove != null);
            LinkedHashMultimap.this.remove(this.toRemove.getKey(), this.toRemove.getValue());
            this.toRemove = null;
         }
      };
   }

   Iterator valueIterator() {
      return Maps.valueIterator(this.entryIterator());
   }

   public void clear() {
      super.clear();
      succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeInt(this.keySet().size());
      Iterator var2 = this.keySet().iterator();

      while(var2.hasNext()) {
         Object key = var2.next();
         stream.writeObject(key);
      }

      stream.writeInt(this.size());
      var2 = this.entries().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         stream.writeObject(entry.getKey());
         stream.writeObject(entry.getValue());
      }

   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.multimapHeaderEntry = new ValueEntry((Object)null, (Object)null, 0, (ValueEntry)null);
      succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
      this.valueSetCapacity = 2;
      int distinctKeys = stream.readInt();
      Map map = new LinkedHashMap();

      int entries;
      for(entries = 0; entries < distinctKeys; ++entries) {
         Object key = stream.readObject();
         map.put(key, this.createCollection(key));
      }

      entries = stream.readInt();

      for(int i = 0; i < entries; ++i) {
         Object key = stream.readObject();
         Object value = stream.readObject();
         ((Collection)map.get(key)).add(value);
      }

      this.setMap(map);
   }

   @VisibleForTesting
   final class ValueSet extends Sets.ImprovedAbstractSet implements ValueSetLink {
      private final Object key;
      @VisibleForTesting
      ValueEntry[] hashTable;
      private int size = 0;
      private int modCount = 0;
      private ValueSetLink firstEntry;
      private ValueSetLink lastEntry;

      ValueSet(Object key, int expectedValues) {
         this.key = key;
         this.firstEntry = this;
         this.lastEntry = this;
         int tableSize = Hashing.closedTableSize(expectedValues, 1.0);
         ValueEntry[] hashTable = new ValueEntry[tableSize];
         this.hashTable = hashTable;
      }

      private int mask() {
         return this.hashTable.length - 1;
      }

      public ValueSetLink getPredecessorInValueSet() {
         return this.lastEntry;
      }

      public ValueSetLink getSuccessorInValueSet() {
         return this.firstEntry;
      }

      public void setPredecessorInValueSet(ValueSetLink entry) {
         this.lastEntry = entry;
      }

      public void setSuccessorInValueSet(ValueSetLink entry) {
         this.firstEntry = entry;
      }

      public Iterator iterator() {
         return new Iterator() {
            ValueSetLink nextEntry;
            ValueEntry toRemove;
            int expectedModCount;

            {
               this.nextEntry = ValueSet.this.firstEntry;
               this.expectedModCount = ValueSet.this.modCount;
            }

            private void checkForComodification() {
               if (ValueSet.this.modCount != this.expectedModCount) {
                  throw new ConcurrentModificationException();
               }
            }

            public boolean hasNext() {
               this.checkForComodification();
               return this.nextEntry != ValueSet.this;
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  ValueEntry entry = (ValueEntry)this.nextEntry;
                  Object result = entry.getValue();
                  this.toRemove = entry;
                  this.nextEntry = entry.getSuccessorInValueSet();
                  return result;
               }
            }

            public void remove() {
               this.checkForComodification();
               CollectPreconditions.checkRemove(this.toRemove != null);
               ValueSet.this.remove(this.toRemove.getValue());
               this.expectedModCount = ValueSet.this.modCount;
               this.toRemove = null;
            }
         };
      }

      public int size() {
         return this.size;
      }

      public boolean contains(@Nullable Object o) {
         int smearedHash = Hashing.smearedHash(o);

         for(ValueEntry entry = this.hashTable[smearedHash & this.mask()]; entry != null; entry = entry.nextInValueBucket) {
            if (entry.matchesValue(o, smearedHash)) {
               return true;
            }
         }

         return false;
      }

      public boolean add(@Nullable Object value) {
         int smearedHash = Hashing.smearedHash(value);
         int bucket = smearedHash & this.mask();
         ValueEntry rowHead = this.hashTable[bucket];

         ValueEntry entry;
         for(entry = rowHead; entry != null; entry = entry.nextInValueBucket) {
            if (entry.matchesValue(value, smearedHash)) {
               return false;
            }
         }

         entry = new ValueEntry(this.key, value, smearedHash, rowHead);
         LinkedHashMultimap.succeedsInValueSet(this.lastEntry, entry);
         LinkedHashMultimap.succeedsInValueSet(entry, this);
         LinkedHashMultimap.succeedsInMultimap(LinkedHashMultimap.this.multimapHeaderEntry.getPredecessorInMultimap(), entry);
         LinkedHashMultimap.succeedsInMultimap(entry, LinkedHashMultimap.this.multimapHeaderEntry);
         this.hashTable[bucket] = entry;
         ++this.size;
         ++this.modCount;
         this.rehashIfNecessary();
         return true;
      }

      private void rehashIfNecessary() {
         if (Hashing.needsResizing(this.size, this.hashTable.length, 1.0)) {
            ValueEntry[] hashTable = new ValueEntry[this.hashTable.length * 2];
            this.hashTable = hashTable;
            int mask = hashTable.length - 1;

            for(ValueSetLink entry = this.firstEntry; entry != this; entry = entry.getSuccessorInValueSet()) {
               ValueEntry valueEntry = (ValueEntry)entry;
               int bucket = valueEntry.smearedValueHash & mask;
               valueEntry.nextInValueBucket = hashTable[bucket];
               hashTable[bucket] = valueEntry;
            }
         }

      }

      @CanIgnoreReturnValue
      public boolean remove(@Nullable Object o) {
         int smearedHash = Hashing.smearedHash(o);
         int bucket = smearedHash & this.mask();
         ValueEntry prev = null;

         for(ValueEntry entry = this.hashTable[bucket]; entry != null; entry = entry.nextInValueBucket) {
            if (entry.matchesValue(o, smearedHash)) {
               if (prev == null) {
                  this.hashTable[bucket] = entry.nextInValueBucket;
               } else {
                  prev.nextInValueBucket = entry.nextInValueBucket;
               }

               LinkedHashMultimap.deleteFromValueSet(entry);
               LinkedHashMultimap.deleteFromMultimap(entry);
               --this.size;
               ++this.modCount;
               return true;
            }

            prev = entry;
         }

         return false;
      }

      public void clear() {
         Arrays.fill(this.hashTable, (Object)null);
         this.size = 0;

         for(ValueSetLink entry = this.firstEntry; entry != this; entry = entry.getSuccessorInValueSet()) {
            ValueEntry valueEntry = (ValueEntry)entry;
            LinkedHashMultimap.deleteFromMultimap(valueEntry);
         }

         LinkedHashMultimap.succeedsInValueSet(this, this);
         ++this.modCount;
      }
   }

   @VisibleForTesting
   static final class ValueEntry extends ImmutableEntry implements ValueSetLink {
      final int smearedValueHash;
      @Nullable
      ValueEntry nextInValueBucket;
      ValueSetLink predecessorInValueSet;
      ValueSetLink successorInValueSet;
      ValueEntry predecessorInMultimap;
      ValueEntry successorInMultimap;

      ValueEntry(@Nullable Object key, @Nullable Object value, int smearedValueHash, @Nullable ValueEntry nextInValueBucket) {
         super(key, value);
         this.smearedValueHash = smearedValueHash;
         this.nextInValueBucket = nextInValueBucket;
      }

      boolean matchesValue(@Nullable Object v, int smearedVHash) {
         return this.smearedValueHash == smearedVHash && Objects.equal(this.getValue(), v);
      }

      public ValueSetLink getPredecessorInValueSet() {
         return this.predecessorInValueSet;
      }

      public ValueSetLink getSuccessorInValueSet() {
         return this.successorInValueSet;
      }

      public void setPredecessorInValueSet(ValueSetLink entry) {
         this.predecessorInValueSet = entry;
      }

      public void setSuccessorInValueSet(ValueSetLink entry) {
         this.successorInValueSet = entry;
      }

      public ValueEntry getPredecessorInMultimap() {
         return this.predecessorInMultimap;
      }

      public ValueEntry getSuccessorInMultimap() {
         return this.successorInMultimap;
      }

      public void setSuccessorInMultimap(ValueEntry multimapSuccessor) {
         this.successorInMultimap = multimapSuccessor;
      }

      public void setPredecessorInMultimap(ValueEntry multimapPredecessor) {
         this.predecessorInMultimap = multimapPredecessor;
      }
   }

   private interface ValueSetLink {
      ValueSetLink getPredecessorInValueSet();

      ValueSetLink getSuccessorInValueSet();

      void setPredecessorInValueSet(ValueSetLink var1);

      void setSuccessorInValueSet(ValueSetLink var1);
   }
}
