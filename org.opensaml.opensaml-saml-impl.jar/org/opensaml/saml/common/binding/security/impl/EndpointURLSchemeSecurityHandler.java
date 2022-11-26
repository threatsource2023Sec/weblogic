package org.opensaml.saml.common.binding.security.impl;

import java.net.URI;
import javax.annotation.Nonnull;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.SAMLMessageSecuritySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointURLSchemeSecurityHandler extends AbstractMessageHandler {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(EndpointURLSchemeSecurityHandler.class);

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      URI endpointUrl;
      try {
         endpointUrl = SAMLBindingSupport.getEndpointURL(messageContext);
      } catch (BindingException var4) {
         throw new MessageHandlerException("Could not obtain message endpoint URL", var4);
      }

      this.log.debug("{} Checking outbound endpoint for allowed URL scheme: {}", this.getLogPrefix(), endpointUrl);
      if (!SAMLMessageSecuritySupport.checkURLScheme(endpointUrl.getScheme())) {
         throw new MessageHandlerException("Relying party endpoint used the untrusted URL scheme " + endpointUrl.getScheme());
      }
   }
}
