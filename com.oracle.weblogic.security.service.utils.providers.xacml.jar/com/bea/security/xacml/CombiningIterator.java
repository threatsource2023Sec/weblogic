package com.bea.security.xacml;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CombiningIterator implements Iterator {
   private Iterator list;
   private Iterator current;

   public CombiningIterator(Collection members) {
      this.list = members.iterator();
      this.current = this.list.hasNext() ? (Iterator)this.list.next() : null;
   }

   public boolean hasNext() {
      return this.current != null && this.current.hasNext() || this.list.hasNext();
   }

   public Object next() {
      if (this.current != null && this.current.hasNext()) {
         return this.current.next();
      } else if (this.list.hasNext()) {
         this.current = (Iterator)this.list.next();
         return this.next();
      } else {
         throw new NoSuchElementException();
      }
   }

   public void remove() {
      if (this.current != null) {
         this.current.remove();
      }

   }
}
