package org.python.google.common.collect;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
abstract class AbstractMultiset extends AbstractCollection implements Multiset {
   private transient Set elementSet;
   private transient Set entrySet;

   public int size() {
      return Multisets.sizeImpl(this);
   }

   public boolean isEmpty() {
      return this.entrySet().isEmpty();
   }

   public boolean contains(@Nullable Object element) {
      return this.count(element) > 0;
   }

   public Iterator iterator() {
      return Multisets.iteratorImpl(this);
   }

   public int count(@Nullable Object element) {
      Iterator var2 = this.entrySet().iterator();

      Multiset.Entry entry;
      do {
         if (!var2.hasNext()) {
            return 0;
         }

         entry = (Multiset.Entry)var2.next();
      } while(!Objects.equal(entry.getElement(), element));

      return entry.getCount();
   }

   @CanIgnoreReturnValue
   public boolean add(@Nullable Object element) {
      this.add(element, 1);
      return true;
   }

   @CanIgnoreReturnValue
   public int add(@Nullable Object element, int occurrences) {
      throw new UnsupportedOperationException();
   }

   @CanIgnoreReturnValue
   public boolean remove(@Nullable Object element) {
      return this.remove(element, 1) > 0;
   }

   @CanIgnoreReturnValue
   public int remove(@Nullable Object element, int occurrences) {
      throw new UnsupportedOperationException();
   }

   @CanIgnoreReturnValue
   public int setCount(@Nullable Object element, int count) {
      return Multisets.setCountImpl(this, element, count);
   }

   @CanIgnoreReturnValue
   public boolean setCount(@Nullable Object element, int oldCount, int newCount) {
      return Multisets.setCountImpl(this, element, oldCount, newCount);
   }

   @CanIgnoreReturnValue
   public boolean addAll(Collection elementsToAdd) {
      return Multisets.addAllImpl(this, elementsToAdd);
   }

   @CanIgnoreReturnValue
   public boolean removeAll(Collection elementsToRemove) {
      return Multisets.removeAllImpl(this, elementsToRemove);
   }

   @CanIgnoreReturnValue
   public boolean retainAll(Collection elementsToRetain) {
      return Multisets.retainAllImpl(this, elementsToRetain);
   }

   public void clear() {
      Iterators.clear(this.entryIterator());
   }

   public Set elementSet() {
      Set result = this.elementSet;
      if (result == null) {
         this.elementSet = result = this.createElementSet();
      }

      return result;
   }

   Set createElementSet() {
      return new ElementSet();
   }

   abstract Iterator entryIterator();

   abstract int distinctElements();

   public Set entrySet() {
      Set result = this.entrySet;
      if (result == null) {
         this.entrySet = result = this.createEntrySet();
      }

      return result;
   }

   Set createEntrySet() {
      return new EntrySet();
   }

   public boolean equals(@Nullable Object object) {
      return Multisets.equalsImpl(this, object);
   }

   public int hashCode() {
      return this.entrySet().hashCode();
   }

   public String toString() {
      return this.entrySet().toString();
   }

   class EntrySet extends Multisets.EntrySet {
      Multiset multiset() {
         return AbstractMultiset.this;
      }

      public Iterator iterator() {
         return AbstractMultiset.this.entryIterator();
      }

      public int size() {
         return AbstractMultiset.this.distinctElements();
      }
   }

   class ElementSet extends Multisets.ElementSet {
      Multiset multiset() {
         return AbstractMultiset.this;
      }
   }
}
