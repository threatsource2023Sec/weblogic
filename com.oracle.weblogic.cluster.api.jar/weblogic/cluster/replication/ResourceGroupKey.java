package weblogic.cluster.replication;

import java.io.Externalizable;

public interface ResourceGroupKey extends Externalizable {
   String getPartitionName();

   String getResourceGroupName();
}
