package weblogic.common.resourcepool;

import java.util.List;

public interface IPooledResourceLinkedList extends List {
   int sizeHigh();

   void addFirst(PooledResource var1);

   void addLast(PooledResource var1);

   Object removeFirst();

   Object removeLast();

   PooledResource removeMatching(PooledResourceInfo var1);

   void resetStatistics();
}
