package weblogic.management.mbeanservers.partition;

import javax.management.MBeanServer;
import weblogic.management.provider.ManagementService;

public class PartitionedDomainRuntimeMbsRefObjFactory extends PartitionedMbsRefObjFactory {
   protected MBeanServer getDelegateMbs() {
      return ManagementService.getDomainRuntimeMBeanServer(KernelIdHelper.KERNEL_ID);
   }
}
