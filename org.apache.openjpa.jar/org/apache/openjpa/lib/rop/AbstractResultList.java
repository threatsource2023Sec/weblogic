package org.apache.openjpa.lib.rop;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.openjpa.lib.util.Localizer;

public abstract class AbstractResultList implements ResultList {
   private static final Localizer _loc = Localizer.forPackage(AbstractResultList.class);

   public void add(int index, Object element) {
      throw this.readOnly();
   }

   private UnsupportedOperationException readOnly() {
      return new UnsupportedOperationException(_loc.get("read-only").getMessage());
   }

   public boolean add(Object o) {
      throw this.readOnly();
   }

   public boolean addAll(Collection c) {
      throw this.readOnly();
   }

   public boolean addAll(int index, Collection c) {
      throw this.readOnly();
   }

   public Object remove(int index) {
      throw this.readOnly();
   }

   public boolean remove(Object o) {
      throw this.readOnly();
   }

   public boolean removeAll(Collection c) {
      throw this.readOnly();
   }

   public boolean retainAll(Collection c) {
      throw this.readOnly();
   }

   public Object set(int index, Object element) {
      throw this.readOnly();
   }

   public void clear() {
      throw this.readOnly();
   }

   public List subList(int from, int to) {
      throw new UnsupportedOperationException();
   }

   protected void assertOpen() {
      if (this.isClosed()) {
         throw new NoSuchElementException(_loc.get("closed").getMessage());
      }
   }
}
