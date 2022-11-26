package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentRepository;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.URIResolver;

public class ConfigurableAttributeURIResolver extends AbstractConfigurationURIResolver {
   private final ProvisioningComponentRepository provisioningComponentRepository;
   private ConfigurableAttributeValueProvider configurableAttributeValueProvider;
   private final ProvisioningContext provisioningContext;

   public ConfigurableAttributeURIResolver(URIResolver delegate, String scheme, boolean cache, ProvisioningContext provisioningContext, ConfigurableAttributeValueProvider configurableAttributeValueProvider) {
      this(delegate, scheme, cache, provisioningContext, (ProvisioningComponentRepository)null, configurableAttributeValueProvider);
   }

   public ConfigurableAttributeURIResolver(URIResolver delegate, String scheme, boolean cache, ProvisioningContext provisioningContext, ProvisioningComponentRepository provisioningComponentRepository, ConfigurableAttributeValueProvider configurableAttributeValueProvider) {
      super(delegate, scheme, cache);
      this.provisioningContext = provisioningContext;
      this.provisioningComponentRepository = provisioningComponentRepository;
      this.setConfigurableAttributeValueProvider(configurableAttributeValueProvider);
   }

   protected Logger getLogger() {
      return Logger.getLogger(ConfigurableAttributeURIResolver.class.getName());
   }

   public ConfigurableAttributeValueProvider getConfigurableAttributeValueProvider() {
      return this.configurableAttributeValueProvider;
   }

   public void setConfigurableAttributeValueProvider(ConfigurableAttributeValueProvider configurableAttributeValueProvider) {
      this.configurableAttributeValueProvider = configurableAttributeValueProvider;
   }

   final ProvisioningContext getProvisioningContext() {
      return this.provisioningContext;
   }

   public String getProperty(String propertyName, String defaultValue) {
      String className = ConfigurableAttributeURIResolver.class.getName();
      String methodName = "getProperty";
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getProperty", new Object[]{propertyName, defaultValue});
      }

      Objects.requireNonNull(propertyName);
      ConfigurableAttributeValueProvider configurableAttributeValueProvider = this.getConfigurableAttributeValueProvider();
      if (configurableAttributeValueProvider == null) {
         throw new IllegalStateException("this.getConfigurableAttributeValueProvider() == null");
      } else {
         String returnValue = null;

         try {
            returnValue = ConfigurableAttributes.getValue(this.provisioningComponentRepository, configurableAttributeValueProvider, this.getProvisioningContext(), propertyName, defaultValue);
         } catch (ProvisioningException var9) {
            throw new IllegalArgumentException(propertyName, var9);
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "getProperty", returnValue);
         }

         return returnValue;
      }
   }

   protected final String getDefaultPropertyValue(String name) {
      return "";
   }
}
