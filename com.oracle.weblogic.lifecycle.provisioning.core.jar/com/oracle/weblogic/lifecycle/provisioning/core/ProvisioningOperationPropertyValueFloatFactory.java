package com.oracle.weblogic.lifecycle.provisioning.core;

import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Self;

public class ProvisioningOperationPropertyValueFloatFactory extends AbstractProvisioningOperationPropertyValueFactory {
   @Inject
   public ProvisioningOperationPropertyValueFloatFactory(@Self ActiveDescriptor myDescriptor, ProvisioningOperationPropertyValueProvider provider) {
      super(myDescriptor, provider);
   }

   protected Float convert(String value) {
      Float returnValue;
      if (value == null) {
         returnValue = null;
      } else {
         returnValue = Float.valueOf(value);
      }

      return returnValue;
   }
}
