package weblogic.management.provider.internal;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.VirtualTargetMBean;

public class ImmutablePartitionAdminVT implements BeanUpdateListener {
   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] propertyUpdated = event.getUpdateList();
      if (event.getSource() instanceof VirtualTargetMBean) {
         DescriptorBean proposedBean = event.getProposedBean();
         if (proposedBean.getParentBean() instanceof PartitionMBean) {
            BeanUpdateEvent.PropertyUpdate[] var4 = propertyUpdated;
            int var5 = propertyUpdated.length;
            int var6 = 0;

            while(var6 < var5) {
               BeanUpdateEvent.PropertyUpdate propUpdate = var4[var6];
               switch (propUpdate.getUpdateType()) {
                  case 1:
                     throw new BeanUpdateRejectedException("Cannot modify the implicitly created VirtualTarget");
                  default:
                     ++var6;
               }
            }
         }
      }

   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
