package weblogic.management.mbeans.custom;

import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public final class Log extends LogFile {
   private static final long serialVersionUID = 7988658427199784266L;

   public Log() {
      this((ConfigurationMBeanCustomized)null);
   }

   public Log(ConfigurationMBeanCustomized base) {
      super(base);
   }
}
