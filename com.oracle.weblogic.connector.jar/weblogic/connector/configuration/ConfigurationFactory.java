package weblogic.connector.configuration;

import weblogic.connector.external.RAInfo;

public class ConfigurationFactory {
   public static final String SCHEMA_VERSION_1_0 = "1.0";
   public static final String CURRENT_CONFIGURATION_VERSION = "1.0";

   public static Configuration getConfiguration(RAInfo aRAInfo) {
      return new Configuration_1_0(aRAInfo);
   }

   public static Configuration getConfiguration(String version, RAInfo aRAInfo) {
      Configuration config = null;
      if (version != null && version.trim().length() > 0 && version.equals("1.0")) {
         config = new Configuration_1_0(aRAInfo);
      }

      return config;
   }
}
