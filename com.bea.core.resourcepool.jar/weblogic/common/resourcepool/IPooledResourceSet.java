package weblogic.common.resourcepool;

import java.util.Set;

public interface IPooledResourceSet extends Set {
   int sizeHigh();

   boolean add(PooledResource var1);

   void resetStatistics();
}
