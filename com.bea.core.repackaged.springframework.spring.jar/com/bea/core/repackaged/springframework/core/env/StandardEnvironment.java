package com.bea.core.repackaged.springframework.core.env;

public class StandardEnvironment extends AbstractEnvironment {
   public static final String SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME = "systemEnvironment";
   public static final String SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME = "systemProperties";

   protected void customizePropertySources(MutablePropertySources propertySources) {
      propertySources.addLast(new PropertiesPropertySource("systemProperties", this.getSystemProperties()));
      propertySources.addLast(new SystemEnvironmentPropertySource("systemEnvironment", this.getSystemEnvironment()));
   }
}
