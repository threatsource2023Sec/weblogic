package weblogic.management.mbeans.custom;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class SystemResource extends ConfigurationExtension {
   public SystemResource(ConfigurationMBeanCustomized customized) {
      super(customized);
   }

   public DescriptorBean getResource() {
      throw new AssertionError("Derived System Resource should implement getResource");
   }
}
