package org.opensaml.saml.common.binding.impl;

import java.net.URI;
import javax.annotation.Nonnull;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLOutboundDestinationHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAMLOutboundDestinationHandler.class);

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (messageContext.getMessage() != null && messageContext.getMessage() instanceof SAMLObject) {
         SAMLObject samlMessage = (SAMLObject)messageContext.getMessage();

         try {
            URI endpointURI = SAMLBindingSupport.getEndpointURL(messageContext);
            String endpointURL = endpointURI.toString();
            if (samlMessage instanceof ResponseAbstractType) {
               this.log.debug("Adding recipient to outbound SAML 1 protocol message: {}", endpointURL);
               SAMLBindingSupport.setSAML1ResponseRecipient(samlMessage, endpointURL);
            } else if (samlMessage instanceof RequestAbstractType || samlMessage instanceof StatusResponseType) {
               this.log.debug("Adding destination to outbound SAML 2 protocol message: {}", endpointURL);
               SAMLBindingSupport.setSAML2Destination(samlMessage, endpointURL);
            }

         } catch (BindingException var5) {
            throw new MessageHandlerException("Could not obtain SAML destination endpoint URL from message context", var5);
         }
      } else {
         throw new MessageHandlerException("SAML message was not present in message context");
      }
   }
}
