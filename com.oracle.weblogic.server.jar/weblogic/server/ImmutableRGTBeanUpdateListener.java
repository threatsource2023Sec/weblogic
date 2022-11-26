package weblogic.server;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.ResourceGroupTemplateMBean;

public class ImmutableRGTBeanUpdateListener implements BeanUpdateListener {
   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] propUpdated = event.getUpdateList();
      if (event.getSource() instanceof ResourceGroupTemplateMBean && ((ResourceGroupTemplateMBean)event.getSource()).isImMutable() && propUpdated.length > 0) {
         throw new BeanUpdateRejectedException("ResourceGroupTemplate " + ((ResourceGroupTemplateMBean)event.getSourceBean()).getName() + " is having imMutable flag set to false no changes are allowed");
      }
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
