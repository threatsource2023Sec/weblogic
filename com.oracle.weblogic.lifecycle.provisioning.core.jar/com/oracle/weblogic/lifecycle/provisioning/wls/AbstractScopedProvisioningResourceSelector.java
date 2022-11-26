package com.oracle.weblogic.lifecycle.provisioning.wls;

import com.oracle.weblogic.lifecycle.provisioning.api.AbstractSelector;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEventMethods;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningResource;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Properties;

public abstract class AbstractScopedProvisioningResourceSelector extends AbstractSelector {
   private final String scopeValue;

   protected AbstractScopedProvisioningResourceSelector(String scopeValue) {
      Objects.requireNonNull(scopeValue);
      this.scopeValue = scopeValue;
   }

   public boolean select(Method handlerMethod, ProvisioningResource provisioningResource) {
      boolean returnValue = false;
      if (handlerMethod != null && provisioningResource != null) {
         Object provisioningResourceURI = provisioningResource.getResource();
         if (provisioningResourceURI != null) {
            Properties provisioningResourceProperties = provisioningResource.getProperties();
            if (provisioningResourceProperties != null) {
               String provisioningResourceScope = provisioningResourceProperties.getProperty("scope");
               if (provisioningResourceScope != null && provisioningResourceScope.equals(this.scopeValue)) {
                  String provisioningResourceURIString = provisioningResourceURI.toString();

                  assert provisioningResourceURIString != null;

                  HandlesResources handlesResources = ProvisioningEventMethods.getHandlesResources(handlerMethod);
                  if (handlesResources != null) {
                     String[] resourceNames = handlesResources.value();
                     if (resourceNames != null && resourceNames.length > 0) {
                        String[] var10 = resourceNames;
                        int var11 = resourceNames.length;

                        for(int var12 = 0; var12 < var11; ++var12) {
                           String resourceName = var10[var12];
                           if (resourceName != null && this.select(provisioningResourceURIString, resourceName)) {
                              returnValue = true;
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return returnValue;
   }

   protected boolean select(String provisioningResourceURIString, String resourceName) {
      return provisioningResourceURIString != null && resourceName != null && provisioningResourceURIString.endsWith(resourceName);
   }
}
