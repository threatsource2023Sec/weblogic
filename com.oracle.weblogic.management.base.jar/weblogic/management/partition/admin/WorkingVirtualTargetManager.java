package weblogic.management.partition.admin;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.VirtualTargetMBean;

@Contract
public interface WorkingVirtualTargetManager {
   VirtualTargetMBean[] getWorkingVirtualTargets(PartitionMBean var1);

   VirtualTargetMBean lookupWorkingVirtualTarget(VirtualTargetMBean var1);
}
