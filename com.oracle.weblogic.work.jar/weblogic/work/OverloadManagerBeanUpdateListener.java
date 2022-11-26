package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.CapacityBean;
import weblogic.management.configuration.CapacityMBean;

class OverloadManagerBeanUpdateListener implements BeanUpdateListener {
   private OverloadManager delegate;

   OverloadManagerBeanUpdateListener(OverloadManager delegate) {
      this.delegate = delegate;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof CapacityMBean) {
         this.delegate.setCapacity(((CapacityMBean)bean).getCount());
      } else if (bean instanceof CapacityBean) {
         this.delegate.setCapacity(((CapacityBean)bean).getCount());
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
