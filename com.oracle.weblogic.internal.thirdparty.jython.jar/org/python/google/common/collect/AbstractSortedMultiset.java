package org.python.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   emulated = true
)
abstract class AbstractSortedMultiset extends AbstractMultiset implements SortedMultiset {
   @GwtTransient
   final Comparator comparator;
   private transient SortedMultiset descendingMultiset;

   AbstractSortedMultiset() {
      this(Ordering.natural());
   }

   AbstractSortedMultiset(Comparator comparator) {
      this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
   }

   public NavigableSet elementSet() {
      return (NavigableSet)super.elementSet();
   }

   NavigableSet createElementSet() {
      return new SortedMultisets.NavigableElementSet(this);
   }

   public Comparator comparator() {
      return this.comparator;
   }

   public Multiset.Entry firstEntry() {
      Iterator entryIterator = this.entryIterator();
      return entryIterator.hasNext() ? (Multiset.Entry)entryIterator.next() : null;
   }

   public Multiset.Entry lastEntry() {
      Iterator entryIterator = this.descendingEntryIterator();
      return entryIterator.hasNext() ? (Multiset.Entry)entryIterator.next() : null;
   }

   public Multiset.Entry pollFirstEntry() {
      Iterator entryIterator = this.entryIterator();
      if (entryIterator.hasNext()) {
         Multiset.Entry result = (Multiset.Entry)entryIterator.next();
         result = Multisets.immutableEntry(result.getElement(), result.getCount());
         entryIterator.remove();
         return result;
      } else {
         return null;
      }
   }

   public Multiset.Entry pollLastEntry() {
      Iterator entryIterator = this.descendingEntryIterator();
      if (entryIterator.hasNext()) {
         Multiset.Entry result = (Multiset.Entry)entryIterator.next();
         result = Multisets.immutableEntry(result.getElement(), result.getCount());
         entryIterator.remove();
         return result;
      } else {
         return null;
      }
   }

   public SortedMultiset subMultiset(@Nullable Object fromElement, BoundType fromBoundType, @Nullable Object toElement, BoundType toBoundType) {
      Preconditions.checkNotNull(fromBoundType);
      Preconditions.checkNotNull(toBoundType);
      return this.tailMultiset(fromElement, fromBoundType).headMultiset(toElement, toBoundType);
   }

   abstract Iterator descendingEntryIterator();

   Iterator descendingIterator() {
      return Multisets.iteratorImpl(this.descendingMultiset());
   }

   public SortedMultiset descendingMultiset() {
      SortedMultiset result = this.descendingMultiset;
      return result == null ? (this.descendingMultiset = this.createDescendingMultiset()) : result;
   }

   SortedMultiset createDescendingMultiset() {
      class DescendingMultisetImpl extends DescendingMultiset {
         SortedMultiset forwardMultiset() {
            return AbstractSortedMultiset.this;
         }

         Iterator entryIterator() {
            return AbstractSortedMultiset.this.descendingEntryIterator();
         }

         public Iterator iterator() {
            return AbstractSortedMultiset.this.descendingIterator();
         }
      }

      return new DescendingMultisetImpl();
   }
}
