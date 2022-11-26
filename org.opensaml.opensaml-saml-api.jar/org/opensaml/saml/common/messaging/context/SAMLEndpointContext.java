package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.saml.saml2.metadata.Endpoint;

public class SAMLEndpointContext extends BaseContext {
   @Nullable
   private Endpoint endpoint;

   @Nullable
   public Endpoint getEndpoint() {
      return this.endpoint;
   }

   public void setEndpoint(@Nullable Endpoint newEndpoint) {
      this.endpoint = newEndpoint;
   }
}
