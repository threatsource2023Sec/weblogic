package weblogic.security.providers.utils;

import java.util.Collection;
import java.util.Iterator;

public class CollectionLister implements ListerManager.Lister {
   private Iterator items = null;
   private Object currentItem = null;
   private int maxToReturn;
   private int numReturned;

   public CollectionLister(Collection c, int maxToReturn) {
      this.items = c != null && !c.isEmpty() ? c.iterator() : null;
      this.maxToReturn = maxToReturn;
      this.numReturned = 0;
      this.advance();
   }

   public boolean haveCurrent() {
      return this.currentItem != null;
   }

   public Object getCurrent() {
      return this.currentItem;
   }

   public void advance() {
      if (this.items != null) {
         if ((this.maxToReturn == 0 || this.numReturned < this.maxToReturn) && this.items.hasNext()) {
            this.currentItem = this.items.next();
            ++this.numReturned;
         } else {
            this.close();
         }
      }

   }

   public void close() {
      this.items = null;
      this.currentItem = null;
   }
}
