package org.python.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   emulated = true
)
abstract class DescendingMultiset extends ForwardingMultiset implements SortedMultiset {
   private transient Comparator comparator;
   private transient NavigableSet elementSet;
   private transient Set entrySet;

   abstract SortedMultiset forwardMultiset();

   public Comparator comparator() {
      Comparator result = this.comparator;
      return result == null ? (this.comparator = Ordering.from(this.forwardMultiset().comparator()).reverse()) : result;
   }

   public NavigableSet elementSet() {
      NavigableSet result = this.elementSet;
      return result == null ? (this.elementSet = new SortedMultisets.NavigableElementSet(this)) : result;
   }

   public Multiset.Entry pollFirstEntry() {
      return this.forwardMultiset().pollLastEntry();
   }

   public Multiset.Entry pollLastEntry() {
      return this.forwardMultiset().pollFirstEntry();
   }

   public SortedMultiset headMultiset(Object toElement, BoundType boundType) {
      return this.forwardMultiset().tailMultiset(toElement, boundType).descendingMultiset();
   }

   public SortedMultiset subMultiset(Object fromElement, BoundType fromBoundType, Object toElement, BoundType toBoundType) {
      return this.forwardMultiset().subMultiset(toElement, toBoundType, fromElement, fromBoundType).descendingMultiset();
   }

   public SortedMultiset tailMultiset(Object fromElement, BoundType boundType) {
      return this.forwardMultiset().headMultiset(fromElement, boundType).descendingMultiset();
   }

   protected Multiset delegate() {
      return this.forwardMultiset();
   }

   public SortedMultiset descendingMultiset() {
      return this.forwardMultiset();
   }

   public Multiset.Entry firstEntry() {
      return this.forwardMultiset().lastEntry();
   }

   public Multiset.Entry lastEntry() {
      return this.forwardMultiset().firstEntry();
   }

   abstract Iterator entryIterator();

   public Set entrySet() {
      Set result = this.entrySet;
      return result == null ? (this.entrySet = this.createEntrySet()) : result;
   }

   Set createEntrySet() {
      class EntrySetImpl extends Multisets.EntrySet {
         Multiset multiset() {
            return DescendingMultiset.this;
         }

         public Iterator iterator() {
            return DescendingMultiset.this.entryIterator();
         }

         public int size() {
            return DescendingMultiset.this.forwardMultiset().entrySet().size();
         }
      }

      return new EntrySetImpl();
   }

   public Iterator iterator() {
      return Multisets.iteratorImpl(this);
   }

   public Object[] toArray() {
      return this.standardToArray();
   }

   public Object[] toArray(Object[] array) {
      return this.standardToArray(array);
   }

   public String toString() {
      return this.entrySet().toString();
   }
}
