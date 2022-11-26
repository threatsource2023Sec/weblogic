package org.jboss.weld.util.collections;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator implements Iterator {
   private final Enumeration enumeration;

   public EnumerationIterator(Enumeration enumeration) {
      this.enumeration = enumeration;
   }

   public boolean hasNext() {
      return this.enumeration.hasMoreElements();
   }

   public Object next() {
      return this.enumeration.nextElement();
   }
}
