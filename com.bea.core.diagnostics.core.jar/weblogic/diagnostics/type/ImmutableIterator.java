package weblogic.diagnostics.type;

import java.util.Iterator;

public class ImmutableIterator implements Iterator {
   private Iterator iterator;

   public ImmutableIterator(Iterator it) {
      this.iterator = it;
   }

   public boolean hasNext() {
      return this.iterator.hasNext();
   }

   public Object next() {
      return this.iterator.next();
   }

   public void remove() {
      throw new UnsupportedOperationException("Remove not allowed on this iterator.");
   }
}
