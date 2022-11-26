package com.googlecode.cqengine.index.support;

import com.googlecode.cqengine.resultset.iterator.UnmodifiableIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

public class UnmodifiableNavigableSet implements NavigableSet {
   private final NavigableSet delegate;
   private transient UnmodifiableNavigableSet descendingSet;

   public UnmodifiableNavigableSet(NavigableSet delegate) {
      this.delegate = delegate;
   }

   public Object lower(Object e) {
      return this.delegate.lower(e);
   }

   public Object floor(Object e) {
      return this.delegate.floor(e);
   }

   public Object ceiling(Object e) {
      return this.delegate.ceiling(e);
   }

   public Object higher(Object e) {
      return this.delegate.higher(e);
   }

   public Object pollFirst() {
      throw new UnsupportedOperationException();
   }

   public Object pollLast() {
      throw new UnsupportedOperationException();
   }

   public NavigableSet descendingSet() {
      UnmodifiableNavigableSet result = this.descendingSet;
      if (result == null) {
         result = this.descendingSet = new UnmodifiableNavigableSet(this.delegate.descendingSet());
         result.descendingSet = this;
      }

      return result;
   }

   public Iterator descendingIterator() {
      return new UnmodifiableIterator() {
         Iterator i;

         {
            this.i = UnmodifiableNavigableSet.this.delegate.descendingIterator();
         }

         public boolean hasNext() {
            return this.i.hasNext();
         }

         public Object next() {
            return this.i.next();
         }
      };
   }

   public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
      return new UnmodifiableNavigableSet(this.delegate.subSet(fromElement, fromInclusive, toElement, toInclusive));
   }

   public NavigableSet headSet(Object toElement, boolean inclusive) {
      return new UnmodifiableNavigableSet(this.delegate.headSet(toElement, inclusive));
   }

   public NavigableSet tailSet(Object fromElement, boolean inclusive) {
      return new UnmodifiableNavigableSet(this.delegate.tailSet(fromElement, inclusive));
   }

   public Iterator iterator() {
      return new UnmodifiableIterator() {
         Iterator i;

         {
            this.i = UnmodifiableNavigableSet.this.delegate.iterator();
         }

         public boolean hasNext() {
            return this.i.hasNext();
         }

         public Object next() {
            return this.i.next();
         }
      };
   }

   public SortedSet subSet(Object fromElement, Object toElement) {
      return new UnmodifiableNavigableSet(this.delegate.subSet(fromElement, true, toElement, false));
   }

   public SortedSet headSet(Object toElement) {
      return new UnmodifiableNavigableSet(this.delegate.headSet(toElement, false));
   }

   public SortedSet tailSet(Object fromElement) {
      return new UnmodifiableNavigableSet(this.delegate.tailSet(fromElement, true));
   }

   public Comparator comparator() {
      return this.delegate.comparator();
   }

   public Object first() {
      return this.delegate.first();
   }

   public Object last() {
      return this.delegate.last();
   }

   public int size() {
      return this.delegate.size();
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public boolean contains(Object o) {
      return this.delegate.contains(o);
   }

   public Object[] toArray() {
      return this.delegate.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.delegate.toArray(a);
   }

   public boolean add(Object e) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean containsAll(Collection c) {
      return this.delegate.containsAll(c);
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public int hashCode() {
      return this.delegate.hashCode();
   }

   public boolean equals(Object obj) {
      return this.delegate.equals(obj);
   }

   public String toString() {
      return this.delegate.toString();
   }
}
