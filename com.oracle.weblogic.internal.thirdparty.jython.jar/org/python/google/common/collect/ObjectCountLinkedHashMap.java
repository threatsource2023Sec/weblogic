package org.python.google.common.collect;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;

@GwtCompatible(
   serializable = true,
   emulated = true
)
class ObjectCountLinkedHashMap extends ObjectCountHashMap {
   private static final int ENDPOINT = -2;
   @VisibleForTesting
   transient long[] links;
   private transient int firstEntry;
   private transient int lastEntry;

   public static ObjectCountLinkedHashMap create() {
      return new ObjectCountLinkedHashMap();
   }

   public static ObjectCountLinkedHashMap createWithExpectedSize(int expectedSize) {
      return new ObjectCountLinkedHashMap(expectedSize);
   }

   ObjectCountLinkedHashMap() {
      this(3);
   }

   ObjectCountLinkedHashMap(int expectedSize) {
      this(expectedSize, 1.0F);
   }

   ObjectCountLinkedHashMap(int expectedSize, float loadFactor) {
      super(expectedSize, loadFactor);
   }

   ObjectCountLinkedHashMap(AbstractObjectCountMap map) {
      this.init(map.size(), 1.0F);

      for(int i = map.firstIndex(); i != -1; i = map.nextIndex(i)) {
         this.put(map.getKey(i), map.getValue(i));
      }

   }

   void init(int expectedSize, float loadFactor) {
      super.init(expectedSize, loadFactor);
      this.firstEntry = -2;
      this.lastEntry = -2;
      this.links = new long[expectedSize];
      Arrays.fill(this.links, -1L);
   }

   int firstIndex() {
      return this.firstEntry == -2 ? -1 : this.firstEntry;
   }

   int nextIndex(int index) {
      int result = this.getSuccessor(index);
      return result == -2 ? -1 : result;
   }

   private int getPredecessor(int entry) {
      return (int)(this.links[entry] >>> 32);
   }

   private int getSuccessor(int entry) {
      return (int)this.links[entry];
   }

   private void setSuccessor(int entry, int succ) {
      long succMask = 4294967295L;
      this.links[entry] = this.links[entry] & ~succMask | (long)succ & succMask;
   }

   private void setPredecessor(int entry, int pred) {
      long predMask = -4294967296L;
      this.links[entry] = this.links[entry] & ~predMask | (long)pred << 32;
   }

   private void setSucceeds(int pred, int succ) {
      if (pred == -2) {
         this.firstEntry = succ;
      } else {
         this.setSuccessor(pred, succ);
      }

      if (succ == -2) {
         this.lastEntry = pred;
      } else {
         this.setPredecessor(succ, pred);
      }

   }

   void insertEntry(int entryIndex, Object key, int value, int hash) {
      super.insertEntry(entryIndex, key, value, hash);
      this.setSucceeds(this.lastEntry, entryIndex);
      this.setSucceeds(entryIndex, -2);
   }

   void moveLastEntry(int dstIndex) {
      int srcIndex = this.size() - 1;
      this.setSucceeds(this.getPredecessor(dstIndex), this.getSuccessor(dstIndex));
      if (dstIndex < srcIndex) {
         this.setSucceeds(this.getPredecessor(srcIndex), dstIndex);
         this.setSucceeds(dstIndex, this.getSuccessor(srcIndex));
      }

      super.moveLastEntry(dstIndex);
   }

   void resizeEntries(int newCapacity) {
      super.resizeEntries(newCapacity);
      this.links = Arrays.copyOf(this.links, newCapacity);
   }

   Set createKeySet() {
      return new AbstractObjectCountMap.KeySetView() {
         public Object[] toArray() {
            return ObjectArrays.toArrayImpl(this);
         }

         public Object[] toArray(Object[] a) {
            return ObjectArrays.toArrayImpl(this, a);
         }

         public Iterator iterator() {
            return new LinkedItr() {
               Object getOutput(int entry) {
                  return ObjectCountLinkedHashMap.this.keys[entry];
               }
            };
         }
      };
   }

   Set createEntrySet() {
      return new AbstractObjectCountMap.EntrySetView() {
         public Iterator iterator() {
            return new LinkedItr() {
               Multiset.Entry getOutput(int entry) {
                  return ObjectCountLinkedHashMap.this.new MapEntry(entry);
               }
            };
         }
      };
   }

   public void clear() {
      super.clear();
      this.firstEntry = -2;
      this.lastEntry = -2;
   }

   private abstract class LinkedItr implements Iterator {
      private int nextEntry;
      private int toRemove;
      private int expectedModCount;

      private LinkedItr() {
         this.nextEntry = ObjectCountLinkedHashMap.this.firstEntry;
         this.toRemove = -1;
         this.expectedModCount = ObjectCountLinkedHashMap.this.modCount;
      }

      private void checkForConcurrentModification() {
         if (ObjectCountLinkedHashMap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      public boolean hasNext() {
         return this.nextEntry != -2;
      }

      abstract Object getOutput(int var1);

      public Object next() {
         this.checkForConcurrentModification();
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Object result = this.getOutput(this.nextEntry);
            this.toRemove = this.nextEntry;
            this.nextEntry = ObjectCountLinkedHashMap.this.getSuccessor(this.nextEntry);
            return result;
         }
      }

      public void remove() {
         this.checkForConcurrentModification();
         CollectPreconditions.checkRemove(this.toRemove != -1);
         ObjectCountLinkedHashMap.this.remove(ObjectCountLinkedHashMap.this.keys[this.toRemove]);
         if (this.nextEntry >= ObjectCountLinkedHashMap.this.size()) {
            this.nextEntry = this.toRemove;
         }

         this.expectedModCount = ObjectCountLinkedHashMap.this.modCount;
         this.toRemove = -1;
      }

      // $FF: synthetic method
      LinkedItr(Object x1) {
         this();
      }
   }
}
