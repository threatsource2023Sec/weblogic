package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentRepository;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.core.ConfigurableAttributeValueProvider;
import com.oracle.weblogic.lifecycle.provisioning.core.ConfigurableAttributes;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Provider;
import org.apache.tools.ant.PropertyHelper;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;

@ContractsProvided({PropertyHelper.PropertyEvaluator.class})
@PerLookup
@Rank(1)
@Service
public final class ConfigurableAttributePropertyEvaluator implements PropertyHelper.PropertyEvaluator {
   private static final String PREFIX = "config:";
   private final Provider provisioningContextProvider;
   private final ProvisioningComponentRepository provisioningComponentRepository;
   private final ConfigurableAttributeValueProvider configurableAttributeValueProvider;

   @Inject
   public ConfigurableAttributePropertyEvaluator(Provider provisioningContextProvider, @Optional ConfigurableAttributeValueProvider configurableAttributeValueProvider, @Optional ProvisioningComponentRepository provisioningComponentRepository) {
      Objects.requireNonNull(provisioningContextProvider, "provisioningContextProvider == null");
      this.provisioningContextProvider = provisioningContextProvider;
      this.configurableAttributeValueProvider = configurableAttributeValueProvider;
      this.provisioningComponentRepository = provisioningComponentRepository;
   }

   public final Object evaluate(String propertyName, PropertyHelper ignoredCallingPropertyHelper) {
      Object returnValue = null;
      if (propertyName != null && this.configurableAttributeValueProvider != null && propertyName.startsWith("config:") && propertyName.length() > "config:".length()) {
         try {
            returnValue = ConfigurableAttributes.getValue(this.provisioningComponentRepository, this.configurableAttributeValueProvider, (ProvisioningContext)this.provisioningContextProvider.get(), propertyName.substring("config:".length()), (String)null);
         } catch (ProvisioningException var5) {
            throw new IllegalStateException(var5);
         }
      }

      return returnValue;
   }
}
