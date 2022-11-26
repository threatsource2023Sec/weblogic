package org.python.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.j2objc.annotations.Weak;

@GwtCompatible(
   emulated = true
)
final class SortedMultisets {
   private SortedMultisets() {
   }

   private static Object getElementOrThrow(Multiset.Entry entry) {
      if (entry == null) {
         throw new NoSuchElementException();
      } else {
         return entry.getElement();
      }
   }

   private static Object getElementOrNull(@Nullable Multiset.Entry entry) {
      return entry == null ? null : entry.getElement();
   }

   @GwtIncompatible
   static class NavigableElementSet extends ElementSet implements NavigableSet {
      NavigableElementSet(SortedMultiset multiset) {
         super(multiset);
      }

      public Object lower(Object e) {
         return SortedMultisets.getElementOrNull(this.multiset().headMultiset(e, BoundType.OPEN).lastEntry());
      }

      public Object floor(Object e) {
         return SortedMultisets.getElementOrNull(this.multiset().headMultiset(e, BoundType.CLOSED).lastEntry());
      }

      public Object ceiling(Object e) {
         return SortedMultisets.getElementOrNull(this.multiset().tailMultiset(e, BoundType.CLOSED).firstEntry());
      }

      public Object higher(Object e) {
         return SortedMultisets.getElementOrNull(this.multiset().tailMultiset(e, BoundType.OPEN).firstEntry());
      }

      public NavigableSet descendingSet() {
         return new NavigableElementSet(this.multiset().descendingMultiset());
      }

      public Iterator descendingIterator() {
         return this.descendingSet().iterator();
      }

      public Object pollFirst() {
         return SortedMultisets.getElementOrNull(this.multiset().pollFirstEntry());
      }

      public Object pollLast() {
         return SortedMultisets.getElementOrNull(this.multiset().pollLastEntry());
      }

      public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
         return new NavigableElementSet(this.multiset().subMultiset(fromElement, BoundType.forBoolean(fromInclusive), toElement, BoundType.forBoolean(toInclusive)));
      }

      public NavigableSet headSet(Object toElement, boolean inclusive) {
         return new NavigableElementSet(this.multiset().headMultiset(toElement, BoundType.forBoolean(inclusive)));
      }

      public NavigableSet tailSet(Object fromElement, boolean inclusive) {
         return new NavigableElementSet(this.multiset().tailMultiset(fromElement, BoundType.forBoolean(inclusive)));
      }
   }

   static class ElementSet extends Multisets.ElementSet implements SortedSet {
      @Weak
      private final SortedMultiset multiset;

      ElementSet(SortedMultiset multiset) {
         this.multiset = multiset;
      }

      final SortedMultiset multiset() {
         return this.multiset;
      }

      public Comparator comparator() {
         return this.multiset().comparator();
      }

      public SortedSet subSet(Object fromElement, Object toElement) {
         return this.multiset().subMultiset(fromElement, BoundType.CLOSED, toElement, BoundType.OPEN).elementSet();
      }

      public SortedSet headSet(Object toElement) {
         return this.multiset().headMultiset(toElement, BoundType.OPEN).elementSet();
      }

      public SortedSet tailSet(Object fromElement) {
         return this.multiset().tailMultiset(fromElement, BoundType.CLOSED).elementSet();
      }

      public Object first() {
         return SortedMultisets.getElementOrThrow(this.multiset().firstEntry());
      }

      public Object last() {
         return SortedMultisets.getElementOrThrow(this.multiset().lastEntry());
      }
   }
}
