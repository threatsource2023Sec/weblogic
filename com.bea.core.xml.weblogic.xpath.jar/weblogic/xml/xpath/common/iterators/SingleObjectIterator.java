package weblogic.xml.xpath.common.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SingleObjectIterator implements Iterator {
   private Object mNode;

   public SingleObjectIterator(Object o) {
      this.mNode = o;
   }

   public boolean hasNext() {
      return this.mNode != null;
   }

   public Object next() {
      if (this.mNode == null) {
         throw new NoSuchElementException();
      } else {
         Object out = this.mNode;
         this.mNode = null;
         return out;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
