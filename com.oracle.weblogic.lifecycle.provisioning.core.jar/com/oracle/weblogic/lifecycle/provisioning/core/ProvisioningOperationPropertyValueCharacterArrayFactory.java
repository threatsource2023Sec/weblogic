package com.oracle.weblogic.lifecycle.provisioning.core;

import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Self;

public class ProvisioningOperationPropertyValueCharacterArrayFactory extends AbstractProvisioningOperationPropertyValueFactory {
   @Inject
   public ProvisioningOperationPropertyValueCharacterArrayFactory(@Self ActiveDescriptor myDescriptor, ProvisioningOperationPropertyValueProvider provider) {
      super(myDescriptor, provider);
   }

   protected Character[] convert(String value) {
      Character[] returnValue;
      if (value == null) {
         returnValue = null;
      } else {
         char[] chars = value.toCharArray();

         assert chars != null;

         assert chars.length > 0;

         returnValue = new Character[chars.length];

         for(int i = 0; i < chars.length; ++i) {
            returnValue[i] = chars[i];
         }
      }

      return returnValue;
   }
}
