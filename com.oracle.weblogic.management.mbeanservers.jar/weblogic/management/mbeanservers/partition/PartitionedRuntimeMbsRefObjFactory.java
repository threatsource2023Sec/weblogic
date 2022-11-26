package weblogic.management.mbeanservers.partition;

import javax.management.MBeanServer;
import weblogic.management.provider.ManagementService;

public class PartitionedRuntimeMbsRefObjFactory extends PartitionedMbsRefObjFactory {
   protected MBeanServer getDelegateMbs() {
      return ManagementService.getRuntimeMBeanServer(KernelIdHelper.KERNEL_ID);
   }
}
