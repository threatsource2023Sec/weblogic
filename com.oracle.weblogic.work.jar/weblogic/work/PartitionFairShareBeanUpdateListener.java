package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.PartitionWorkManagerMBean;
import weblogic.management.configuration.SelfTuningMBean;

public class PartitionFairShareBeanUpdateListener implements BeanUpdateListener {
   private final PartitionFairShare delegate;

   PartitionFairShareBeanUpdateListener(PartitionFairShare c) {
      this.delegate = c;
   }

   public void prepareUpdate(BeanUpdateEvent event) {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof SelfTuningMBean) {
         this.delegate.setFairShare(((SelfTuningMBean)bean).getPartitionFairShare());
      } else if (bean instanceof PartitionWorkManagerMBean) {
         this.delegate.setFairShare(((PartitionWorkManagerMBean)bean).getFairShare());
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
