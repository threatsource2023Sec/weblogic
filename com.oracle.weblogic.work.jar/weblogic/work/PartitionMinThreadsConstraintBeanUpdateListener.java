package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.PartitionWorkManagerMBean;

public class PartitionMinThreadsConstraintBeanUpdateListener implements BeanUpdateListener {
   private final PartitionMinThreadsConstraint delegate;

   PartitionMinThreadsConstraintBeanUpdateListener(PartitionMinThreadsConstraint c) {
      this.delegate = c;
   }

   public void prepareUpdate(BeanUpdateEvent event) {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof PartitionWorkManagerMBean) {
         this.delegate.setCount(((PartitionWorkManagerMBean)bean).getMinThreadsConstraintCap());
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
