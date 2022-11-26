package org.jboss.weld.config;

public final class SystemPropertiesConfiguration {
   public static final SystemPropertiesConfiguration INSTANCE = new SystemPropertiesConfiguration();
   private boolean xmlValidationDisabled;

   private SystemPropertiesConfiguration() {
      this.xmlValidationDisabled = (Boolean)this.initSystemProperty(ConfigurationKey.DISABLE_XML_VALIDATION, Boolean.class);
   }

   public boolean isXmlValidationDisabled() {
      return this.xmlValidationDisabled;
   }

   private Object initSystemProperty(ConfigurationKey key, Class requiredType) {
      WeldConfiguration.checkRequiredType(key, requiredType);
      String property = WeldConfiguration.getSystemProperty(key.get());
      return property != null ? key.convertValue(property) : key.getDefaultValue();
   }
}
