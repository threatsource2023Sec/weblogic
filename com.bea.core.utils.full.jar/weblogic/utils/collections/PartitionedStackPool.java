package weblogic.utils.collections;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class PartitionedStackPool extends AbstractCollection implements Pool {
   private static final int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
   private static final int MAX_TRY = 2;
   private final StackPool[] pools;
   private AtomicInteger nextAdd;
   private AtomicInteger nextRemove;

   public PartitionedStackPool(int size) {
      this(size, DEFAULT_POOL_SIZE);
   }

   public PartitionedStackPool(int size, int subpools) {
      this.nextAdd = new AtomicInteger();
      this.nextRemove = new AtomicInteger();
      int noOfSubPools;
      if (subpools < DEFAULT_POOL_SIZE) {
         noOfSubPools = DEFAULT_POOL_SIZE;
      } else {
         noOfSubPools = subpools;
      }

      this.pools = new StackPool[noOfSubPools];
      int poolSize = size / noOfSubPools + 1;

      for(int i = 0; i < noOfSubPools; ++i) {
         this.pools[i] = new StackPool(poolSize);
      }

   }

   public int size() {
      int size = 0;

      for(int i = 0; i < this.pools.length; ++i) {
         size += this.pools[i].size();
      }

      return size;
   }

   public Iterator iterator() {
      Iterator[] iterators = new Iterator[this.pools.length];

      for(int i = 0; i < this.pools.length; ++i) {
         iterators[i] = this.pools[i].iterator();
      }

      return new CombinedIterator(iterators);
   }

   private int advanceAddPosition() {
      return (this.nextAdd.incrementAndGet() & Integer.MAX_VALUE) % this.pools.length;
   }

   private int advanceRemovePosition() {
      return (this.nextRemove.incrementAndGet() & Integer.MAX_VALUE) % this.pools.length;
   }

   public boolean add(Object o) {
      int startIndex = this.advanceAddPosition();

      for(int i = 0; i < this.pools.length; ++i) {
         if (this.pools[startIndex].size() != this.pools[startIndex].capacity() && this.pools[startIndex].add(o)) {
            return true;
         }

         startIndex = (startIndex + 1) % this.pools.length;
      }

      return false;
   }

   public Object remove() {
      int startIndex = this.advanceRemovePosition();

      for(int i = 0; i < 2 && i < this.pools.length; ++i) {
         if (this.pools[startIndex].size() > 0) {
            Object obj = this.pools[startIndex].remove();
            if (obj != null) {
               return obj;
            }
         }

         startIndex = (startIndex + 1) % this.pools.length;
      }

      return null;
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }
}
