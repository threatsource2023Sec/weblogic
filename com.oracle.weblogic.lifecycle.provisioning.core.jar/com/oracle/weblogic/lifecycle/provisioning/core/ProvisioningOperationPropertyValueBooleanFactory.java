package com.oracle.weblogic.lifecycle.provisioning.core;

import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Self;

public class ProvisioningOperationPropertyValueBooleanFactory extends AbstractProvisioningOperationPropertyValueFactory {
   @Inject
   public ProvisioningOperationPropertyValueBooleanFactory(@Self ActiveDescriptor myDescriptor, ProvisioningOperationPropertyValueProvider provider) {
      super(myDescriptor, provider);
   }

   protected Boolean convert(String value) {
      Boolean returnValue;
      if (value == null) {
         returnValue = null;
      } else {
         returnValue = Boolean.valueOf(value);
      }

      return returnValue;
   }
}
