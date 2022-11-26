package com.oracle.weblogic.lifecycle.config.database;

import java.util.Properties;
import org.glassfish.hk2.configuration.api.Configured;

public abstract class ResourceConfigService extends LifecycleConfigService {
   public static final String PROXY_DATASOURCE = "proxy-datasource";
   public static final String DATASOURCE = "datasource";
   public static final String JDBC_PROPERTIES = "jdbc-properties";
   @Configured
   private String type;
   @Configured
   private String name;
   @Configured
   private Properties properties;

   public String getName() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public Properties getProperties() {
      return this.properties;
   }

   public String getPropertyValue(String name) {
      return this.properties != null ? this.properties.getProperty(name) : null;
   }
}
