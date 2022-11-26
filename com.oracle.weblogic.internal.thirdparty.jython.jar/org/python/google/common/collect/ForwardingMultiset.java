package org.python.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingMultiset extends ForwardingCollection implements Multiset {
   protected ForwardingMultiset() {
   }

   protected abstract Multiset delegate();

   public int count(Object element) {
      return this.delegate().count(element);
   }

   @CanIgnoreReturnValue
   public int add(Object element, int occurrences) {
      return this.delegate().add(element, occurrences);
   }

   @CanIgnoreReturnValue
   public int remove(Object element, int occurrences) {
      return this.delegate().remove(element, occurrences);
   }

   public Set elementSet() {
      return this.delegate().elementSet();
   }

   public Set entrySet() {
      return this.delegate().entrySet();
   }

   public boolean equals(@Nullable Object object) {
      return object == this || this.delegate().equals(object);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   @CanIgnoreReturnValue
   public int setCount(Object element, int count) {
      return this.delegate().setCount(element, count);
   }

   @CanIgnoreReturnValue
   public boolean setCount(Object element, int oldCount, int newCount) {
      return this.delegate().setCount(element, oldCount, newCount);
   }

   protected boolean standardContains(@Nullable Object object) {
      return this.count(object) > 0;
   }

   protected void standardClear() {
      Iterators.clear(this.entrySet().iterator());
   }

   @Beta
   protected int standardCount(@Nullable Object object) {
      Iterator var2 = this.entrySet().iterator();

      Multiset.Entry entry;
      do {
         if (!var2.hasNext()) {
            return 0;
         }

         entry = (Multiset.Entry)var2.next();
      } while(!Objects.equal(entry.getElement(), object));

      return entry.getCount();
   }

   protected boolean standardAdd(Object element) {
      this.add(element, 1);
      return true;
   }

   @Beta
   protected boolean standardAddAll(Collection elementsToAdd) {
      return Multisets.addAllImpl(this, elementsToAdd);
   }

   protected boolean standardRemove(Object element) {
      return this.remove(element, 1) > 0;
   }

   protected boolean standardRemoveAll(Collection elementsToRemove) {
      return Multisets.removeAllImpl(this, elementsToRemove);
   }

   protected boolean standardRetainAll(Collection elementsToRetain) {
      return Multisets.retainAllImpl(this, elementsToRetain);
   }

   protected int standardSetCount(Object element, int count) {
      return Multisets.setCountImpl(this, element, count);
   }

   protected boolean standardSetCount(Object element, int oldCount, int newCount) {
      return Multisets.setCountImpl(this, element, oldCount, newCount);
   }

   protected Iterator standardIterator() {
      return Multisets.iteratorImpl(this);
   }

   protected int standardSize() {
      return Multisets.sizeImpl(this);
   }

   protected boolean standardEquals(@Nullable Object object) {
      return Multisets.equalsImpl(this, object);
   }

   protected int standardHashCode() {
      return this.entrySet().hashCode();
   }

   protected String standardToString() {
      return this.entrySet().toString();
   }

   @Beta
   protected class StandardElementSet extends Multisets.ElementSet {
      public StandardElementSet() {
      }

      Multiset multiset() {
         return ForwardingMultiset.this;
      }
   }
}
