package weblogic.work;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.MaxThreadsConstraintBean;
import weblogic.management.configuration.MaxThreadsConstraintMBean;

public class MaxThreadsConstraintBeanUpdateListener implements BeanUpdateListener {
   private final MaxThreadsConstraint delegate;

   MaxThreadsConstraintBeanUpdateListener(MaxThreadsConstraint c) {
      this.delegate = c;
   }

   public final void prepareUpdate(BeanUpdateEvent event) {
   }

   public final void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof MaxThreadsConstraintMBean) {
         this.delegate.setCountInternal(((MaxThreadsConstraintMBean)bean).getCount());
      } else if (bean instanceof MaxThreadsConstraintBean) {
         this.delegate.setCountInternal(((MaxThreadsConstraintBean)bean).getCount());
      }

   }

   public final void rollbackUpdate(BeanUpdateEvent event) {
   }
}
