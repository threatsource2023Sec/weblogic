package weblogic.server;

import java.security.AccessController;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.internal.PartitionFileSystemHelper;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionFileSystemBeanUpdateListener implements BeanUpdateListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private PartitionMBean partition;

   public PartitionFileSystemBeanUpdateListener(PartitionMBean partition) {
      this.partition = partition;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      if (this.rootChanged(event)) {
         RuntimeAccess config = ManagementService.getRuntimeAccess(kernelId);

         try {
            if (config.isAdminServer()) {
               PartitionFileSystemHelper.checkDomainContentFile();
               PartitionFileSystemHelper.checkPartitionFileSystem(this.partition);
            }
         } catch (ManagementException var4) {
            throw new Error("Unexpected exception creating partition file system: " + var4.getMessage(), var4);
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private boolean rootChanged(BeanUpdateEvent event) {
      BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
      BeanUpdateEvent.PropertyUpdate[] var3 = updated;
      int var4 = updated.length;
      int var5 = 0;

      while(var5 < var4) {
         BeanUpdateEvent.PropertyUpdate propertyUpdate = var3[var5];
         switch (propertyUpdate.getUpdateType()) {
            case 1:
               String name = propertyUpdate.getPropertyName();
               if (name.equals("Root")) {
                  return true;
               }
            default:
               ++var5;
         }
      }

      return false;
   }

   public void cleanup() {
      this.partition.getSystemFileSystem().removeBeanUpdateListener(this);
   }
}
