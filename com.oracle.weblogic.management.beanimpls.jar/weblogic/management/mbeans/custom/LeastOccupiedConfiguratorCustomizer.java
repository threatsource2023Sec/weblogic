package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class LeastOccupiedConfiguratorCustomizer extends ConfigurationMBeanCustomizer {
   public LeastOccupiedConfiguratorCustomizer(ConfigurationMBeanCustomized c) {
      super(c);
   }

   public String getPartitionConfiguratorServiceName() {
      return "least-occupied-target";
   }
}
