package com.oracle.weblogic.lifecycle.provisioning.api;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import java.lang.reflect.Method;
import java.net.URI;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;

@ContractsProvided({Selector.class, URIEndsWithSelector.class})
@PerLookup
@Service
public class URIEndsWithSelector extends AbstractSelector {
   public boolean select(Method handlerMethod, ProvisioningResource provisioningResource) {
      boolean returnValue = false;
      if (handlerMethod != null && provisioningResource != null) {
         URI provisioningResourceURI = provisioningResource.getResource();
         if (provisioningResourceURI != null) {
            String provisioningResourceURIString = provisioningResourceURI.toString();

            assert provisioningResourceURIString != null;

            HandlesResources handlesResources = ProvisioningEventMethods.getHandlesResources(handlerMethod);
            if (handlesResources != null) {
               String[] resourceNames = handlesResources.value();
               if (resourceNames != null && resourceNames.length > 0) {
                  String[] var8 = resourceNames;
                  int var9 = resourceNames.length;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     String resourceName = var8[var10];
                     if (resourceName != null && provisioningResourceURIString.endsWith(resourceName)) {
                        returnValue = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      return returnValue;
   }
}
