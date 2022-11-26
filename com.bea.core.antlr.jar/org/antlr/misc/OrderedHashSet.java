package org.antlr.misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class OrderedHashSet extends LinkedHashSet {
   protected List elements = new ArrayList();

   public Object get(int i) {
      return this.elements.get(i);
   }

   public Object set(int i, Object value) {
      Object oldElement = this.elements.get(i);
      this.elements.set(i, value);
      super.remove(oldElement);
      super.add(value);
      return oldElement;
   }

   public boolean add(Object value) {
      boolean result = super.add(value);
      if (result) {
         this.elements.add(value);
      }

      return result;
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      this.elements.clear();
      super.clear();
   }

   public List elements() {
      return this.elements;
   }

   public Iterator iterator() {
      return this.elements.iterator();
   }

   public Object[] toArray() {
      return this.elements.toArray();
   }

   public int size() {
      return this.elements.size();
   }

   public String toString() {
      return this.elements.toString();
   }
}
