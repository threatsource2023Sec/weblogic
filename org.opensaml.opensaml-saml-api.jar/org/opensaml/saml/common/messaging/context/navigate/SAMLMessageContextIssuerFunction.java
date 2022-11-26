package org.opensaml.saml.common.messaging.context.navigate;

import com.google.common.base.Function;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;

public class SAMLMessageContextIssuerFunction implements Function {
   @Nullable
   public String apply(@Nullable MessageContext input) {
      if (input != null) {
         SAMLPeerEntityContext peerCtx = (SAMLPeerEntityContext)input.getSubcontext(SAMLPeerEntityContext.class);
         if (peerCtx != null) {
            return peerCtx.getEntityId();
         }

         SAMLSelfEntityContext selfCtx = (SAMLSelfEntityContext)input.getSubcontext(SAMLSelfEntityContext.class);
         if (selfCtx != null) {
            return selfCtx.getEntityId();
         }
      }

      return null;
   }
}
