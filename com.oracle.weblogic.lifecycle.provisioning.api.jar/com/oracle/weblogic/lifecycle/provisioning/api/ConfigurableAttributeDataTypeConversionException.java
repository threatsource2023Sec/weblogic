package com.oracle.weblogic.lifecycle.provisioning.api;

import java.lang.reflect.Type;

public class ConfigurableAttributeDataTypeConversionException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private final String provisioningComponentName;
   private final String configurableAttributeName;
   private final String invalidValue;
   private final Type targetType;

   public ConfigurableAttributeDataTypeConversionException(String provisioningComponentName, String configurableAttributeName, String invalidValue, Type targetType, Throwable cause) {
      this(provisioningComponentName, configurableAttributeName, invalidValue, targetType, cause, "The value supplied for the configurable attribute named " + provisioningComponentName + ":" + configurableAttributeName + ", " + invalidValue + ", could not be converted to " + targetType);
   }

   public ConfigurableAttributeDataTypeConversionException(String provisioningComponentName, String configurableAttributeName, String invalidValue, Type targetType, Throwable cause, String message) {
      super(message, cause);
      this.provisioningComponentName = provisioningComponentName;
      this.configurableAttributeName = configurableAttributeName;
      this.invalidValue = invalidValue;
      this.targetType = targetType;
   }

   public final String getProvisioningComponentName() {
      return this.provisioningComponentName;
   }

   public final String getConfigurableAttributeName() {
      return this.configurableAttributeName;
   }

   public final String getInvalidValue() {
      return this.invalidValue;
   }

   public Type getTargetType() {
      return this.targetType;
   }
}
