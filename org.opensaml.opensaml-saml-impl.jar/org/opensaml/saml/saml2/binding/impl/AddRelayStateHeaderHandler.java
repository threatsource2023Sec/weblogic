package org.opensaml.saml.saml2.binding.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.saml2.ecp.RelayState;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.util.SOAPSupport;

public class AddRelayStateHeaderHandler extends AbstractMessageHandler {
   @Nullable
   private String relayState;

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         this.relayState = SAMLBindingSupport.getRelayState(messageContext);
         return this.relayState != null;
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SAMLObjectBuilder builder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(RelayState.DEFAULT_ELEMENT_NAME);
      RelayState header = (RelayState)builder.buildObject();
      if (SAMLBindingSupport.checkRelayState(this.relayState)) {
         header.setValue(this.relayState);
      }

      SOAPSupport.addSOAP11MustUnderstandAttribute(header, true);
      SOAPSupport.addSOAP11ActorAttribute(header, "http://schemas.xmlsoap.org/soap/actor/next");

      try {
         SOAPMessagingSupport.addHeaderBlock(messageContext, header);
      } catch (Exception var5) {
         throw new MessageHandlerException(var5);
      }
   }
}
