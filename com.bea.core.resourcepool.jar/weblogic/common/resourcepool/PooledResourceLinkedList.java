package weblogic.common.resourcepool;

import java.util.LinkedList;
import java.util.ListIterator;

public class PooledResourceLinkedList extends LinkedList implements IPooledResourceLinkedList {
   static final long serialVersionUID = 4407222224731484937L;
   int numEntriesHigh = 0;

   public int sizeHigh() {
      return this.numEntriesHigh;
   }

   public void addFirst(PooledResource obj) {
      super.addFirst(obj);
      if (this.numEntriesHigh < this.size()) {
         this.numEntriesHigh = this.size();
      }

   }

   public void addLast(PooledResource obj) {
      super.addLast(obj);
      if (this.numEntriesHigh < this.size()) {
         this.numEntriesHigh = this.size();
      }

   }

   public PooledResource removeFirst() {
      return this.size() > 0 ? (PooledResource)super.removeFirst() : null;
   }

   public PooledResource removeLast() {
      return this.size() > 0 ? (PooledResource)super.removeLast() : null;
   }

   public PooledResource removeMatching(PooledResourceInfo info) {
      ListIterator iter = this.listIterator(0);

      PooledResource res;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         res = (PooledResource)iter.next();
      } while(!info.equals(res.getPooledResourceInfo()));

      iter.remove();
      return res;
   }

   public void resetStatistics() {
      this.numEntriesHigh = this.size();
   }
}
