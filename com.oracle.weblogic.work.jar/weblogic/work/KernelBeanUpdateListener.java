package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.KernelMBean;

public class KernelBeanUpdateListener implements BeanUpdateListener {
   public void prepareUpdate(BeanUpdateEvent event) {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof KernelMBean) {
         ExecuteThread.setUseDetailedThreadName(((KernelMBean)bean).isUseDetailedThreadName());
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
