package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class DataSource extends ConfigurationMBeanCustomizer {
   public DataSource() {
      this((ConfigurationMBeanCustomized)null);
   }

   public DataSource(ConfigurationMBeanCustomized base) {
      super(base);
   }
}
