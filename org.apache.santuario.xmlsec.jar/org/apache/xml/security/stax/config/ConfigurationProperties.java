package org.apache.xml.security.stax.config;

import java.util.List;
import java.util.Properties;
import org.apache.xml.security.configuration.PropertiesType;
import org.apache.xml.security.configuration.PropertyType;

public class ConfigurationProperties {
   private static Properties properties;
   private static Class callingClass;

   private ConfigurationProperties() {
   }

   protected static synchronized void init(PropertiesType propertiesType, Class callingClass) throws Exception {
      properties = new Properties();
      List handlerList = propertiesType.getProperty();

      for(int i = 0; i < handlerList.size(); ++i) {
         PropertyType propertyType = (PropertyType)handlerList.get(i);
         properties.setProperty(propertyType.getNAME(), propertyType.getVAL());
      }

      ConfigurationProperties.callingClass = callingClass;
   }

   public static String getProperty(String key) {
      return properties.getProperty(key);
   }

   public static Class getCallingClass() {
      return callingClass;
   }
}
