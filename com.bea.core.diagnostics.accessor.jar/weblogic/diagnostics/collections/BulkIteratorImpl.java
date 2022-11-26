package weblogic.diagnostics.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import weblogic.diagnostics.debug.DebugLogger;

public class BulkIteratorImpl implements BulkIterator {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private Iterator iterator;

   public BulkIteratorImpl(Iterator iter) {
      this.iterator = iter;
   }

   public Object[] fetchNext(int maxCount) {
      if (!this.iterator.hasNext()) {
         throw new NoSuchElementException();
      } else if (maxCount <= 0) {
         throw new IllegalArgumentException();
      } else {
         ArrayList items = new ArrayList();

         for(int i = 0; i < maxCount && this.iterator.hasNext(); ++i) {
            items.add(this.iterator.next());
         }

         return items.toArray();
      }
   }

   public boolean hasNext() {
      boolean result = this.iterator.hasNext();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Invoked hasNext() on Iterator " + this.iterator + " and got " + result);
      }

      return result;
   }
}
