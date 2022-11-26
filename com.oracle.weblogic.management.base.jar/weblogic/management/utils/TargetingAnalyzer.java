package weblogic.management.utils;

import java.util.Collection;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;

@Contract
public interface TargetingAnalyzer {
   void init(DomainMBean var1, DomainMBean var2);

   boolean isAddedToServer(PartitionMBean var1, String var2);

   boolean isAddedToServer(ResourceGroupMBean var1, String var2);

   boolean isRemovedFromServer(PartitionMBean var1, String var2);

   boolean isRemovedFromServer(ResourceGroupMBean var1, String var2);

   boolean isAdded(PartitionMBean var1);

   boolean isAdded(ResourceGroupMBean var1);

   boolean isRemoved(PartitionMBean var1);

   boolean isRemoved(ResourceGroupMBean var1);

   Collection getResourceGroupNamesLeavingAndJoiningServers();
}
