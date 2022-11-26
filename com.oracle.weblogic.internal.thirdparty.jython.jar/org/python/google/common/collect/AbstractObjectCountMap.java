package org.python.google.common.collect;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true,
   emulated = true
)
abstract class AbstractObjectCountMap {
   transient Object[] keys;
   transient int[] values;
   static final int UNSET = -1;
   transient int size;
   transient int modCount;
   private transient Set keySetView;
   private transient Set entrySetView;

   @CanIgnoreReturnValue
   abstract int put(@Nullable Object var1, int var2);

   abstract int get(@Nullable Object var1);

   @CanIgnoreReturnValue
   abstract int remove(@Nullable Object var1);

   abstract void clear();

   abstract boolean containsKey(@Nullable Object var1);

   Set keySet() {
      return this.keySetView == null ? (this.keySetView = this.createKeySet()) : this.keySetView;
   }

   int size() {
      return this.size;
   }

   boolean isEmpty() {
      return this.size == 0;
   }

   abstract int indexOf(@Nullable Object var1);

   @CanIgnoreReturnValue
   abstract int removeEntry(int var1);

   Set createKeySet() {
      return new KeySetView();
   }

   Object getKey(int index) {
      Preconditions.checkElementIndex(index, this.size);
      return this.keys[index];
   }

   int getValue(int index) {
      Preconditions.checkElementIndex(index, this.size);
      return this.values[index];
   }

   Multiset.Entry getEntry(int index) {
      Preconditions.checkElementIndex(index, this.size);
      return new MapEntry(index);
   }

   int firstIndex() {
      return 0;
   }

   int nextIndex(int index) {
      return index + 1 < this.size ? index + 1 : -1;
   }

   Set entrySet() {
      return this.entrySetView == null ? (this.entrySetView = this.createEntrySet()) : this.entrySetView;
   }

   abstract Set createEntrySet();

   class MapEntry extends Multisets.AbstractEntry {
      @Nullable
      final Object key;
      int lastKnownIndex;

      MapEntry(int index) {
         this.key = AbstractObjectCountMap.this.keys[index];
         this.lastKnownIndex = index;
      }

      public Object getElement() {
         return this.key;
      }

      void updateLastKnownIndex() {
         if (this.lastKnownIndex == -1 || this.lastKnownIndex >= AbstractObjectCountMap.this.size() || !Objects.equal(this.key, AbstractObjectCountMap.this.keys[this.lastKnownIndex])) {
            this.lastKnownIndex = AbstractObjectCountMap.this.indexOf(this.key);
         }

      }

      public int getCount() {
         this.updateLastKnownIndex();
         return this.lastKnownIndex == -1 ? 0 : AbstractObjectCountMap.this.values[this.lastKnownIndex];
      }

      @CanIgnoreReturnValue
      public int setCount(int count) {
         this.updateLastKnownIndex();
         if (this.lastKnownIndex == -1) {
            AbstractObjectCountMap.this.put(this.key, count);
            return 0;
         } else {
            int old = AbstractObjectCountMap.this.values[this.lastKnownIndex];
            AbstractObjectCountMap.this.values[this.lastKnownIndex] = count;
            return old;
         }
      }
   }

   abstract class EntrySetView extends Sets.ImprovedAbstractSet {
      public boolean contains(@Nullable Object o) {
         if (!(o instanceof Multiset.Entry)) {
            return false;
         } else {
            Multiset.Entry entry = (Multiset.Entry)o;
            int index = AbstractObjectCountMap.this.indexOf(entry.getElement());
            return index != -1 && AbstractObjectCountMap.this.values[index] == entry.getCount();
         }
      }

      public boolean remove(@Nullable Object o) {
         if (o instanceof Multiset.Entry) {
            Multiset.Entry entry = (Multiset.Entry)o;
            int index = AbstractObjectCountMap.this.indexOf(entry.getElement());
            if (index != -1 && AbstractObjectCountMap.this.values[index] == entry.getCount()) {
               AbstractObjectCountMap.this.removeEntry(index);
               return true;
            }
         }

         return false;
      }

      public int size() {
         return AbstractObjectCountMap.this.size;
      }
   }

   abstract class Itr implements Iterator {
      int expectedModCount;
      boolean nextCalled;
      int index;

      Itr() {
         this.expectedModCount = AbstractObjectCountMap.this.modCount;
         this.nextCalled = false;
         this.index = 0;
      }

      public boolean hasNext() {
         return this.index < AbstractObjectCountMap.this.size;
      }

      abstract Object getOutput(int var1);

      public Object next() {
         this.checkForConcurrentModification();
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.nextCalled = true;
            return this.getOutput(this.index++);
         }
      }

      public void remove() {
         this.checkForConcurrentModification();
         CollectPreconditions.checkRemove(this.nextCalled);
         ++this.expectedModCount;
         --this.index;
         AbstractObjectCountMap.this.removeEntry(this.index);
         this.nextCalled = false;
      }

      void checkForConcurrentModification() {
         if (AbstractObjectCountMap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }
   }

   class KeySetView extends Sets.ImprovedAbstractSet {
      public Object[] toArray() {
         return ObjectArrays.copyAsObjectArray(AbstractObjectCountMap.this.keys, 0, AbstractObjectCountMap.this.size);
      }

      public Object[] toArray(Object[] a) {
         return ObjectArrays.toArrayImpl(AbstractObjectCountMap.this.keys, 0, AbstractObjectCountMap.this.size, a);
      }

      public Iterator iterator() {
         return new Itr() {
            Object getOutput(int entry) {
               return AbstractObjectCountMap.this.keys[entry];
            }
         };
      }

      public int size() {
         return AbstractObjectCountMap.this.size;
      }
   }
}
