package weblogic.deploy.service.internal;

import weblogic.deploy.service.internal.targetserver.TargetCalloutManager;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.utils.LocatorUtilities;

public class CalloutsUpdateListener implements BeanUpdateListener {
   private final TargetCalloutManager calloutManager = (TargetCalloutManager)LocatorUtilities.getService(TargetCalloutManager.class);

   public void prepareUpdate(BeanUpdateEvent event) {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof DomainMBean) {
         this.calloutManager.setupCalloutsList((DomainMBean)bean);
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
