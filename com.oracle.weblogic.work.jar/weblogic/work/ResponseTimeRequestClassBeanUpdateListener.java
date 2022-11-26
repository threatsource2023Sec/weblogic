package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.ResponseTimeRequestClassBean;
import weblogic.management.configuration.ResponseTimeRequestClassMBean;

class ResponseTimeRequestClassBeanUpdateListener implements BeanUpdateListener {
   private ResponseTimeRequestClass delegate;

   ResponseTimeRequestClassBeanUpdateListener(ResponseTimeRequestClass delegate) {
      this.delegate = delegate;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof ResponseTimeRequestClassBean) {
         this.delegate.setResponseTime(((ResponseTimeRequestClassBean)bean).getGoalMs());
      } else if (bean instanceof ResponseTimeRequestClassMBean) {
         this.delegate.setResponseTime(((ResponseTimeRequestClassMBean)bean).getGoalMs());
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
