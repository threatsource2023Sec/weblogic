package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.FairShareRequestClassBean;
import weblogic.management.configuration.FairShareRequestClassMBean;

class FairShareRequestClassBeanUpdateListener implements BeanUpdateListener {
   private final FairShareRequestClass delegate;

   FairShareRequestClassBeanUpdateListener(FairShareRequestClass delegate) {
      this.delegate = delegate;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof FairShareRequestClassBean) {
         this.delegate.setShare(((FairShareRequestClassBean)bean).getFairShare());
      } else if (bean instanceof FairShareRequestClassMBean) {
         this.delegate.setShare(((FairShareRequestClassMBean)bean).getFairShare());
      }
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
