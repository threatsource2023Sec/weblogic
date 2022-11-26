package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.binding.BindingDescriptor;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;

public class SAMLBindingContextBindingFunction implements ContextDataLookupFunction {
   private boolean useShortName;

   public void setUseShortName(boolean flag) {
      this.useShortName = flag;
   }

   @Nullable
   public String apply(@Nullable SAMLBindingContext input) {
      if (input != null) {
         if (this.useShortName) {
            BindingDescriptor descriptor = input.getBindingDescriptor();
            if (descriptor != null && descriptor.getShortName() != null) {
               return descriptor.getShortName();
            }
         }

         return input.getBindingUri();
      } else {
         return null;
      }
   }
}
