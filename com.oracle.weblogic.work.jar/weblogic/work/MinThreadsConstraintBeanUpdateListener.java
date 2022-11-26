package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.MinThreadsConstraintBean;
import weblogic.management.configuration.MinThreadsConstraintMBean;

public class MinThreadsConstraintBeanUpdateListener implements BeanUpdateListener {
   private final MinThreadsConstraint delegate;

   MinThreadsConstraintBeanUpdateListener(MinThreadsConstraint c) {
      this.delegate = c;
   }

   public void prepareUpdate(BeanUpdateEvent event) {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof MinThreadsConstraintMBean) {
         this.delegate.setCount(((MinThreadsConstraintMBean)bean).getCount());
      } else if (bean instanceof MinThreadsConstraintBean) {
         this.delegate.setCount(((MinThreadsConstraintBean)bean).getCount());
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
