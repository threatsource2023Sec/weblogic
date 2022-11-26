package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeLiteral;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentRepository;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConfigurableAttributes {
   private ConfigurableAttributes() {
   }

   public static final ConfigurableAttribute installDefaultValue(ConfigurableAttribute source, String componentName, ConfigurableAttributeValueProvider valueProvider) throws ProvisioningException {
      return installDefaultValue(source, componentName, source == null ? null : source.name(), valueProvider);
   }

   public static final ConfigurableAttribute installDefaultValue(ConfigurableAttribute source, String componentName, String configurableAttributeName, ConfigurableAttributeValueProvider valueProvider) throws ProvisioningException {
      Object returnValue;
      if (source != null && valueProvider != null && valueProvider.containsConfigurableAttributeValue(componentName, configurableAttributeName)) {
         returnValue = new ConfigurableAttributeLiteral(configurableAttributeName, source.description(), valueProvider.getConfigurableAttributeValue(componentName, configurableAttributeName), source.isSensitive());
      } else {
         returnValue = source;
      }

      return (ConfigurableAttribute)returnValue;
   }

   public static final String getValue(ProvisioningComponentRepository attributeProvider, ConfigurableAttributeValueProvider valueProvider, ProvisioningContext provisioningContext, String propertyName, String defaultValue) throws ProvisioningException {
      String className = ConfigurableAttributes.class.getName();
      String methodName = "getValue";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getValue", new Object[]{attributeProvider, valueProvider, provisioningContext, propertyName, defaultValue});
      }

      String returnValue = null;
      int colonIndex;
      if (propertyName == null) {
         colonIndex = -1;
      } else {
         colonIndex = propertyName.indexOf(58);
      }

      String namespace;
      String name;
      if (colonIndex < 0) {
         if (propertyName == null) {
            name = "";
         } else {
            name = propertyName.trim();
         }

         String provisioningComponentName;
         if (provisioningContext == null) {
            provisioningComponentName = null;
         } else {
            provisioningComponentName = provisioningContext.getCurrentProvisioningComponentName();
         }

         if (provisioningComponentName != null && !provisioningComponentName.isEmpty()) {
            namespace = provisioningComponentName.trim();
         } else {
            namespace = "";
         }
      } else {
         assert propertyName != null;

         namespace = propertyName.substring(0, colonIndex).trim();
         if (propertyName.length() > colonIndex + 1) {
            name = propertyName.substring(colonIndex + 1).trim();
         } else {
            name = "";
         }
      }

      assert namespace != null;

      assert name != null;

      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, className, "getValue", "Resolving configurable attribute with namespace \"{0}\" and name \"{1}\"", new Object[]{namespace, name});
      }

      if (valueProvider != null) {
         returnValue = valueProvider.getConfigurableAttributeValue(namespace, name);
      }

      if (returnValue == null) {
         if (attributeProvider == null) {
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "getValue", "No value found for configurable attribute named \"{0}:{1}\", and no way of testing whether there exists such a configurable attribute; using supplied default value \"{1}\" instead", new Object[]{namespace, name, defaultValue});
            }

            returnValue = defaultValue;
         } else {
            ConfigurableAttribute ca = attributeProvider.getConfigurableAttribute(namespace, name);
            if (ca == null) {
               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "getValue", "No configurable attribute named \"{0}:{1}\" found; using supplied default value \"{1}\" instead", new Object[]{namespace, name, defaultValue});
               }

               returnValue = defaultValue;
            } else {
               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "getValue", "No value found for configurable attribute named \"{0}:{1}\"; using its default value \"{1}\" instead", new Object[]{namespace, name, ca.defaultValue()});
               }

               returnValue = ca.defaultValue();
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, className, "getValue", "Resolved configurable attribute \"{0}:{1}\": {2}", new Object[]{namespace, name, returnValue});
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getValue", returnValue);
      }

      return returnValue;
   }
}
