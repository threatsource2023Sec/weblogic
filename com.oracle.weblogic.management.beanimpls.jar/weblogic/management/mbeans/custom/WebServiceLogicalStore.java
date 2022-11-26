package weblogic.management.mbeans.custom;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.WebServiceLogicalStoreMBean;
import weblogic.management.configuration.WebServicePersistenceMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class WebServiceLogicalStore extends ConfigurationExtension {
   public WebServiceLogicalStore(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void _preDestroy() {
      DescriptorBean mbean = this.getMbean();
      WebServiceLogicalStoreMBean lsmb = (WebServiceLogicalStoreMBean)mbean;
      WebServicePersistenceMBean wpmb = (WebServicePersistenceMBean)mbean.getParentBean();
      if (lsmb.getName().equals(wpmb.getDefaultLogicalStoreName())) {
         throw new RuntimeException("The store " + lsmb.getName() + " is the default store for this server and may not be deleted.");
      }
   }
}
