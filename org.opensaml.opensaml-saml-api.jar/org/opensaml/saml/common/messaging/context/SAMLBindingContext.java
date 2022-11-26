package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.saml.common.binding.BindingDescriptor;

public class SAMLBindingContext extends BaseContext {
   @Nullable
   @NotEmpty
   private String relayState;
   @Nullable
   private BindingDescriptor bindingDescriptor;
   @Nullable
   @NotEmpty
   private String bindingUri;
   private boolean hasBindingSignature;
   private boolean isIntendedDestinationEndpointURIRequired;

   @Nullable
   @NotEmpty
   public String getRelayState() {
      return this.relayState;
   }

   public void setRelayState(@Nullable String state) {
      this.relayState = StringSupport.trimOrNull(state);
   }

   @Nullable
   @NotEmpty
   public String getBindingUri() {
      if (this.bindingUri != null) {
         return this.bindingUri;
      } else {
         return this.bindingDescriptor != null ? this.bindingDescriptor.getId() : null;
      }
   }

   public void setBindingUri(@Nullable String newBindingUri) {
      this.bindingUri = StringSupport.trimOrNull(newBindingUri);
   }

   @Nullable
   public BindingDescriptor getBindingDescriptor() {
      return this.bindingDescriptor;
   }

   public void setBindingDescriptor(@Nullable BindingDescriptor descriptor) {
      this.bindingDescriptor = descriptor;
   }

   public boolean hasBindingSignature() {
      return this.hasBindingSignature;
   }

   public void setHasBindingSignature(boolean flag) {
      this.hasBindingSignature = flag;
   }

   public boolean isIntendedDestinationEndpointURIRequired() {
      return this.isIntendedDestinationEndpointURIRequired;
   }

   public void setIntendedDestinationEndpointURIRequired(boolean flag) {
      this.isIntendedDestinationEndpointURIRequired = flag;
   }
}
