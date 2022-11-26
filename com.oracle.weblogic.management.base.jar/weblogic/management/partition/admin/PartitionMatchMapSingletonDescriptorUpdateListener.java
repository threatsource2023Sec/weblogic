package weblogic.management.partition.admin;

import java.security.AccessController;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateListener;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionMatchMapSingletonDescriptorUpdateListener implements DescriptorUpdateListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void prepareUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateRejectedException {
   }

   public void activateUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateFailedException {
      if (event.getSourceDescriptor().getRootBean() instanceof DomainMBean) {
         try {
            PartitionMatchMapSingleton.getInstance();
            PartitionMatchMapSingleton.createMatchMap((DomainMBean)event.getSourceDescriptor().getRootBean());
         } catch (Exception var3) {
            throw new DescriptorUpdateFailedException("Unexpected exception updating PartitionMatchMap singleton instance", var3);
         }
      }

   }

   public void rollbackUpdate(DescriptorUpdateEvent event) {
      RuntimeAccess config = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean currentDomainBean = config.getDomain();

      try {
         PartitionMatchMapSingleton.getInstance();
         PartitionMatchMapSingleton.createMatchMap(currentDomainBean);
      } catch (Exception var5) {
      }

   }
}
