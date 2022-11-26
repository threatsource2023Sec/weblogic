package com.oracle.weblogic.diagnostics.expressions;

import java.util.Collection;

public abstract class AbstractTrackedItemCollection implements Collection {
   private Traceable parent;

   public AbstractTrackedItemCollection() {
      this((Traceable)null);
   }

   public AbstractTrackedItemCollection(Traceable parent) {
      this.parent = parent;
   }

   public Traceable getTraceableParent() {
      return this.parent;
   }

   public boolean add(Object e) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean contains(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean containsAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty() {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      throw new UnsupportedOperationException();
   }

   public Object[] toArray() {
      throw new UnsupportedOperationException();
   }

   public Object[] toArray(Object[] a) {
      throw new UnsupportedOperationException();
   }
}
