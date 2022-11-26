package weblogic.management.partition.admin;

import java.security.AccessController;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.PartitionUserFileSystemManager;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionUserFileSystemManagerMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Named
public final class PartitionUserFileSystemManagerService implements PartitionUserFileSystemManager {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public PartitionUserFileSystemManagerMBean createPartitionUserFileSystemManagerMBean(RuntimeMBean parent, String partitionName, AuthenticatedSubject sub) throws ManagementException {
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         return null;
      } else {
         SecurityHelper.assertIfNotKernel(sub);
         return new PartitionUserFileSystemManagerMBeanImpl(parent, partitionName);
      }
   }

   public void destroyPartitionUserFileSystemManagerMBean(String partitionName, AuthenticatedSubject sub) throws ManagementException {
   }
}
