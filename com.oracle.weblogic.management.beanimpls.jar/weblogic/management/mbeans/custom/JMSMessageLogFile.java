package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class JMSMessageLogFile extends LogFile {
   public JMSMessageLogFile() {
      this((ConfigurationMBeanCustomized)null);
   }

   public JMSMessageLogFile(ConfigurationMBeanCustomized base) {
      super(base);
   }
}
