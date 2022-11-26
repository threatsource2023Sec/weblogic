package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class DataSourceLogFile extends LogFile {
   public DataSourceLogFile() {
      this((ConfigurationMBeanCustomized)null);
   }

   public DataSourceLogFile(ConfigurationMBeanCustomized base) {
      super(base);
   }
}
