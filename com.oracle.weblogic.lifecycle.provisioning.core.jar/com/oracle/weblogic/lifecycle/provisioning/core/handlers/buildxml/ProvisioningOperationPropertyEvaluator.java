package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import com.oracle.weblogic.lifecycle.provisioning.core.ProvisioningOperationPropertyValueProvider;
import javax.inject.Inject;
import org.apache.tools.ant.PropertyHelper;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;

@ContractsProvided({PropertyHelper.PropertyEvaluator.class})
@PerLookup
@Rank(3)
@Service
public final class ProvisioningOperationPropertyEvaluator implements PropertyHelper.PropertyEvaluator {
   private final ProvisioningOperationPropertyValueProvider provisioningOperationPropertyValueProvider;

   @Inject
   public ProvisioningOperationPropertyEvaluator(@Optional ProvisioningOperationPropertyValueProvider provisioningOperationPropertyValueProvider) {
      this.provisioningOperationPropertyValueProvider = provisioningOperationPropertyValueProvider;
   }

   public final Object evaluate(String propertyName, PropertyHelper ignoredCallingPropertyHelper) {
      Object returnValue = null;
      if (propertyName != null && this.provisioningOperationPropertyValueProvider != null) {
         returnValue = this.provisioningOperationPropertyValueProvider.getProvisioningOperationPropertyValue(propertyName);
      }

      return returnValue;
   }
}
