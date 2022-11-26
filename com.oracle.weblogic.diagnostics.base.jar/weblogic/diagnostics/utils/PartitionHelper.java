package weblogic.diagnostics.utils;

import java.security.AccessController;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionHelper {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static String getPartitionId(String partitionName) {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      PartitionMBean partitionMBean = runtimeAccess.getDomain().lookupPartition(partitionName);
      String partitionId = "";
      if (partitionMBean != null) {
         partitionId = partitionMBean.getPartitionID();
      }

      return partitionId;
   }
}
