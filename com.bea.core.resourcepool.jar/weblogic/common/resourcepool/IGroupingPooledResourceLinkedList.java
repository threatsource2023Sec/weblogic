package weblogic.common.resourcepool;

import java.util.List;

public interface IGroupingPooledResourceLinkedList extends IPooledResourceLinkedList {
   int getSize(PooledResourceInfo var1);

   List getSubList(PooledResourceInfo var1);
}
