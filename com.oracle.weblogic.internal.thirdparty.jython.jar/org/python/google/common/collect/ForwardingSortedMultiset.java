package org.python.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible(
   emulated = true
)
public abstract class ForwardingSortedMultiset extends ForwardingMultiset implements SortedMultiset {
   protected ForwardingSortedMultiset() {
   }

   protected abstract SortedMultiset delegate();

   public NavigableSet elementSet() {
      return this.delegate().elementSet();
   }

   public Comparator comparator() {
      return this.delegate().comparator();
   }

   public SortedMultiset descendingMultiset() {
      return this.delegate().descendingMultiset();
   }

   public Multiset.Entry firstEntry() {
      return this.delegate().firstEntry();
   }

   protected Multiset.Entry standardFirstEntry() {
      Iterator entryIterator = this.entrySet().iterator();
      if (!entryIterator.hasNext()) {
         return null;
      } else {
         Multiset.Entry entry = (Multiset.Entry)entryIterator.next();
         return Multisets.immutableEntry(entry.getElement(), entry.getCount());
      }
   }

   public Multiset.Entry lastEntry() {
      return this.delegate().lastEntry();
   }

   protected Multiset.Entry standardLastEntry() {
      Iterator entryIterator = this.descendingMultiset().entrySet().iterator();
      if (!entryIterator.hasNext()) {
         return null;
      } else {
         Multiset.Entry entry = (Multiset.Entry)entryIterator.next();
         return Multisets.immutableEntry(entry.getElement(), entry.getCount());
      }
   }

   public Multiset.Entry pollFirstEntry() {
      return this.delegate().pollFirstEntry();
   }

   protected Multiset.Entry standardPollFirstEntry() {
      Iterator entryIterator = this.entrySet().iterator();
      if (!entryIterator.hasNext()) {
         return null;
      } else {
         Multiset.Entry entry = (Multiset.Entry)entryIterator.next();
         entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
         entryIterator.remove();
         return entry;
      }
   }

   public Multiset.Entry pollLastEntry() {
      return this.delegate().pollLastEntry();
   }

   protected Multiset.Entry standardPollLastEntry() {
      Iterator entryIterator = this.descendingMultiset().entrySet().iterator();
      if (!entryIterator.hasNext()) {
         return null;
      } else {
         Multiset.Entry entry = (Multiset.Entry)entryIterator.next();
         entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
         entryIterator.remove();
         return entry;
      }
   }

   public SortedMultiset headMultiset(Object upperBound, BoundType boundType) {
      return this.delegate().headMultiset(upperBound, boundType);
   }

   public SortedMultiset subMultiset(Object lowerBound, BoundType lowerBoundType, Object upperBound, BoundType upperBoundType) {
      return this.delegate().subMultiset(lowerBound, lowerBoundType, upperBound, upperBoundType);
   }

   protected SortedMultiset standardSubMultiset(Object lowerBound, BoundType lowerBoundType, Object upperBound, BoundType upperBoundType) {
      return this.tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
   }

   public SortedMultiset tailMultiset(Object lowerBound, BoundType boundType) {
      return this.delegate().tailMultiset(lowerBound, boundType);
   }

   protected abstract class StandardDescendingMultiset extends DescendingMultiset {
      public StandardDescendingMultiset() {
      }

      SortedMultiset forwardMultiset() {
         return ForwardingSortedMultiset.this;
      }
   }

   protected class StandardElementSet extends SortedMultisets.NavigableElementSet {
      public StandardElementSet() {
         super(ForwardingSortedMultiset.this);
      }
   }
}
