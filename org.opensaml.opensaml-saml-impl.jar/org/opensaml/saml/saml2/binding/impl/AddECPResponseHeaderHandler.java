package org.opensaml.saml.saml2.binding.impl;

import java.net.URI;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.saml2.ecp.Response;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.util.SOAPSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddECPResponseHeaderHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddECPResponseHeaderHandler.class);
   @Nullable
   private URI assertionConsumerURL;

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         try {
            this.assertionConsumerURL = SAMLBindingSupport.getEndpointURL(messageContext);
            return true;
         } catch (BindingException var3) {
            this.log.debug("{} No ACS location available in message context", this.getLogPrefix());
            return false;
         }
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SAMLObjectBuilder responseBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Response.DEFAULT_ELEMENT_NAME);
      Response header = (Response)responseBuilder.buildObject();
      header.setAssertionConsumerServiceURL(this.assertionConsumerURL.toString());
      SOAPSupport.addSOAP11MustUnderstandAttribute(header, true);
      SOAPSupport.addSOAP11ActorAttribute(header, "http://schemas.xmlsoap.org/soap/actor/next");

      try {
         SOAPMessagingSupport.addHeaderBlock(messageContext, header);
      } catch (Exception var5) {
         throw new MessageHandlerException(var5);
      }
   }
}
