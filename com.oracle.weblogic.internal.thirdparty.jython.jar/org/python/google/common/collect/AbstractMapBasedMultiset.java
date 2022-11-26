package org.python.google.common.collect;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.primitives.Ints;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
abstract class AbstractMapBasedMultiset extends AbstractMultiset implements Serializable {
   transient AbstractObjectCountMap backingMap;
   private transient long size;
   @GwtIncompatible
   private static final long serialVersionUID = -2250766705698539974L;

   protected AbstractMapBasedMultiset(AbstractObjectCountMap backingMap) {
      this.backingMap = (AbstractObjectCountMap)Preconditions.checkNotNull(backingMap);
      this.size = (long)super.size();
   }

   void setBackingMap(AbstractObjectCountMap backingMap) {
      this.backingMap = backingMap;
   }

   Set createElementSet() {
      return this.backingMap.keySet();
   }

   public Set createEntrySet() {
      return new AbstractMultiset.EntrySet();
   }

   Iterator entryIterator() {
      final Iterator backingEntries = this.backingMap.entrySet().iterator();
      return new Iterator() {
         Multiset.Entry toRemove;
         boolean canRemove;

         public boolean hasNext() {
            return backingEntries.hasNext();
         }

         public Multiset.Entry next() {
            Multiset.Entry mapEntry = (Multiset.Entry)backingEntries.next();
            this.toRemove = mapEntry;
            this.canRemove = true;
            return mapEntry;
         }

         public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            AbstractMapBasedMultiset.this.size = AbstractMapBasedMultiset.this.size - (long)this.toRemove.getCount();
            backingEntries.remove();
            this.canRemove = false;
            this.toRemove = null;
         }
      };
   }

   public void clear() {
      this.backingMap.clear();
      this.size = 0L;
   }

   int distinctElements() {
      return this.backingMap.size();
   }

   public int size() {
      return Ints.saturatedCast(this.size);
   }

   public Iterator iterator() {
      return new MapBasedMultisetIterator();
   }

   public int count(@Nullable Object element) {
      return this.backingMap.get(element);
   }

   @CanIgnoreReturnValue
   public int add(@Nullable Object element, int occurrences) {
      if (occurrences == 0) {
         return this.count(element);
      } else {
         Preconditions.checkArgument(occurrences > 0, "occurrences cannot be negative: %s", occurrences);
         int oldCount = this.backingMap.get(element);
         long newCount = (long)oldCount + (long)occurrences;
         Preconditions.checkArgument(newCount <= 2147483647L, "too many occurrences: %s", newCount);
         this.backingMap.put(element, (int)newCount);
         this.size += (long)occurrences;
         return oldCount;
      }
   }

   @CanIgnoreReturnValue
   public int remove(@Nullable Object element, int occurrences) {
      if (occurrences == 0) {
         return this.count(element);
      } else {
         Preconditions.checkArgument(occurrences > 0, "occurrences cannot be negative: %s", occurrences);
         int oldCount = this.backingMap.get(element);
         int numberRemoved;
         if (oldCount > occurrences) {
            numberRemoved = occurrences;
            this.backingMap.put(element, oldCount - occurrences);
         } else {
            numberRemoved = oldCount;
            this.backingMap.remove(element);
         }

         this.size -= (long)numberRemoved;
         return oldCount;
      }
   }

   @CanIgnoreReturnValue
   public int setCount(@Nullable Object element, int count) {
      CollectPreconditions.checkNonnegative(count, "count");
      int oldCount = count == 0 ? this.backingMap.remove(element) : this.backingMap.put(element, count);
      this.size += (long)(count - oldCount);
      return oldCount;
   }

   @GwtIncompatible
   private void readObjectNoData() throws ObjectStreamException {
      throw new InvalidObjectException("Stream data required");
   }

   private class MapBasedMultisetIterator implements Iterator {
      final Iterator entryIterator;
      Multiset.Entry currentEntry;
      int occurrencesLeft = 0;
      boolean canRemove = false;

      MapBasedMultisetIterator() {
         this.entryIterator = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
      }

      public boolean hasNext() {
         return this.occurrencesLeft > 0 || this.entryIterator.hasNext();
      }

      public Object next() {
         if (this.occurrencesLeft == 0) {
            this.currentEntry = (Multiset.Entry)this.entryIterator.next();
            this.occurrencesLeft = this.currentEntry.getCount();
         }

         --this.occurrencesLeft;
         this.canRemove = true;
         return this.currentEntry.getElement();
      }

      public void remove() {
         CollectPreconditions.checkRemove(this.canRemove);
         int frequency = this.currentEntry.getCount();
         if (frequency <= 0) {
            throw new ConcurrentModificationException();
         } else {
            if (frequency == 1) {
               this.entryIterator.remove();
            } else {
               ((AbstractObjectCountMap.MapEntry)this.currentEntry).setCount(frequency - 1);
            }

            AbstractMapBasedMultiset.this.size--;
            this.canRemove = false;
         }
      }
   }
}
