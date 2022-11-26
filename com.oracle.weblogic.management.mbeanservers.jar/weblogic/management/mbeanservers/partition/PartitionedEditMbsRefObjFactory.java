package weblogic.management.mbeanservers.partition;

import javax.management.MBeanServer;
import weblogic.management.provider.ManagementService;

public class PartitionedEditMbsRefObjFactory extends PartitionedMbsRefObjFactory {
   protected MBeanServer getDelegateMbs() {
      return ManagementService.getEditMBeanServer(KernelIdHelper.KERNEL_ID);
   }
}
