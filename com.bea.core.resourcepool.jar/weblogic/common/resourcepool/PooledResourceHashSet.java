package weblogic.common.resourcepool;

import java.util.HashSet;

public class PooledResourceHashSet extends HashSet implements IPooledResourceSet {
   private static final long serialVersionUID = 5526870570278177974L;
   int numEntriesHigh = 0;

   public PooledResourceHashSet(int capacity) {
      super(capacity < 100 ? capacity : 100);
   }

   public int sizeHigh() {
      return this.numEntriesHigh;
   }

   public boolean add(PooledResource obj) {
      boolean r = super.add(obj);
      if (this.numEntriesHigh < this.size()) {
         this.numEntriesHigh = this.size();
      }

      return r;
   }

   public void resetStatistics() {
      this.numEntriesHigh = this.size();
   }
}
