package com.oracle.weblogic.lifecycle.provisioning.core;

import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Self;

public class ProvisioningOperationPropertyValueDoubleFactory extends AbstractProvisioningOperationPropertyValueFactory {
   @Inject
   public ProvisioningOperationPropertyValueDoubleFactory(@Self ActiveDescriptor myDescriptor, ProvisioningOperationPropertyValueProvider provider) {
      super(myDescriptor, provider);
   }

   protected Double convert(String value) {
      Double returnValue;
      if (value == null) {
         returnValue = null;
      } else {
         returnValue = Double.valueOf(value);
      }

      return returnValue;
   }
}
