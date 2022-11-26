package weblogic.apache.org.apache.velocity.util;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator implements Iterator {
   private Enumeration enum = null;

   public EnumerationIterator(Enumeration enum) {
      this.enum = enum;
   }

   public Object next() {
      return this.enum.nextElement();
   }

   public boolean hasNext() {
      return this.enum.hasMoreElements();
   }

   public void remove() {
   }
}
