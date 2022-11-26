package com.oracle.weblogic.lifecycle.provisioning.core;

import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Self;

public class ProvisioningOperationPropertyValueStringFactory extends AbstractProvisioningOperationPropertyValueFactory {
   @Inject
   public ProvisioningOperationPropertyValueStringFactory(@Self ActiveDescriptor myDescriptor, ProvisioningOperationPropertyValueProvider provider) {
      super(myDescriptor, provider);
   }

   protected String convert(String value) {
      return value;
   }
}
