package weblogic.common.resourcepool;

import java.util.List;

public interface IGroupingPooledResourceSet extends IPooledResourceSet {
   int getSize(PooledResourceInfo var1);

   List getSubList(PooledResourceInfo var1);

   PooledResource removeMatching(PooledResourceInfo var1);
}
