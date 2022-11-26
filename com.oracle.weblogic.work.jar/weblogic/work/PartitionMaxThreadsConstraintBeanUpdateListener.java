package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.PartitionWorkManagerMBean;

public class PartitionMaxThreadsConstraintBeanUpdateListener implements BeanUpdateListener {
   private final PartitionMaxThreadsConstraint delegate;

   public PartitionMaxThreadsConstraintBeanUpdateListener(PartitionMaxThreadsConstraint delegate) {
      this.delegate = delegate;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof PartitionWorkManagerMBean) {
         this.delegate.setCount(((PartitionWorkManagerMBean)bean).getMaxThreadsConstraint());
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
