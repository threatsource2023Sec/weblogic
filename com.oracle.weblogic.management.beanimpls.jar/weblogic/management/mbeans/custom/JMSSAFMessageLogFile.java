package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class JMSSAFMessageLogFile extends LogFile {
   public JMSSAFMessageLogFile() {
      this((ConfigurationMBeanCustomized)null);
   }

   public JMSSAFMessageLogFile(ConfigurationMBeanCustomized base) {
      super(base);
   }
}
