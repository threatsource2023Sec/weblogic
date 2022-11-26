package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.PartitionWorkManagerMBean;

public class PartitionOverloadBeanUpdateListener implements BeanUpdateListener {
   private final OverloadManager delegate;

   public PartitionOverloadBeanUpdateListener(OverloadManager delegate) {
      this.delegate = delegate;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof PartitionWorkManagerMBean) {
         this.delegate.setPercentage(((PartitionWorkManagerMBean)bean).getSharedCapacityPercent());
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
