package weblogic.nodemanager.common;

import java.util.Properties;

public class SystemComponentStartupConfig extends StartupConfig {
   public SystemComponentStartupConfig(Properties props) throws ConfigException {
      super(props);
   }

   public SystemComponentStartupConfig(ValuesHolder holder) {
      super((StartupConfig.ValuesHolder)holder);
   }

   public static class ValuesHolder extends StartupConfig.ValuesHolder {
      public SystemComponentStartupConfig toStartupConfig() {
         return new SystemComponentStartupConfig(this);
      }
   }
}
