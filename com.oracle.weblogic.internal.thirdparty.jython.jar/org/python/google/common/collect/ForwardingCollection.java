package org.python.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingCollection extends ForwardingObject implements Collection {
   protected ForwardingCollection() {
   }

   protected abstract Collection delegate();

   public Iterator iterator() {
      return this.delegate().iterator();
   }

   public int size() {
      return this.delegate().size();
   }

   @CanIgnoreReturnValue
   public boolean removeAll(Collection collection) {
      return this.delegate().removeAll(collection);
   }

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   public boolean contains(Object object) {
      return this.delegate().contains(object);
   }

   @CanIgnoreReturnValue
   public boolean add(Object element) {
      return this.delegate().add(element);
   }

   @CanIgnoreReturnValue
   public boolean remove(Object object) {
      return this.delegate().remove(object);
   }

   public boolean containsAll(Collection collection) {
      return this.delegate().containsAll(collection);
   }

   @CanIgnoreReturnValue
   public boolean addAll(Collection collection) {
      return this.delegate().addAll(collection);
   }

   @CanIgnoreReturnValue
   public boolean retainAll(Collection collection) {
      return this.delegate().retainAll(collection);
   }

   public void clear() {
      this.delegate().clear();
   }

   public Object[] toArray() {
      return this.delegate().toArray();
   }

   @CanIgnoreReturnValue
   public Object[] toArray(Object[] array) {
      return this.delegate().toArray(array);
   }

   protected boolean standardContains(@Nullable Object object) {
      return Iterators.contains(this.iterator(), object);
   }

   protected boolean standardContainsAll(Collection collection) {
      return Collections2.containsAllImpl(this, collection);
   }

   protected boolean standardAddAll(Collection collection) {
      return Iterators.addAll(this, collection.iterator());
   }

   protected boolean standardRemove(@Nullable Object object) {
      Iterator iterator = this.iterator();

      do {
         if (!iterator.hasNext()) {
            return false;
         }
      } while(!Objects.equal(iterator.next(), object));

      iterator.remove();
      return true;
   }

   protected boolean standardRemoveAll(Collection collection) {
      return Iterators.removeAll(this.iterator(), collection);
   }

   protected boolean standardRetainAll(Collection collection) {
      return Iterators.retainAll(this.iterator(), collection);
   }

   protected void standardClear() {
      Iterators.clear(this.iterator());
   }

   protected boolean standardIsEmpty() {
      return !this.iterator().hasNext();
   }

   protected String standardToString() {
      return Collections2.toStringImpl(this);
   }

   protected Object[] standardToArray() {
      Object[] newArray = new Object[this.size()];
      return this.toArray(newArray);
   }

   protected Object[] standardToArray(Object[] array) {
      return ObjectArrays.toArrayImpl(this, array);
   }
}
