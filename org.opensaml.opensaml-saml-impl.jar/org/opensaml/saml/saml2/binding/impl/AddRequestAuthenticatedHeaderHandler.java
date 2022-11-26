package org.opensaml.saml.saml2.binding.impl;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.ECPContext;
import org.opensaml.saml.saml2.ecp.RequestAuthenticated;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.util.SOAPSupport;

public class AddRequestAuthenticatedHeaderHandler extends AbstractMessageHandler {
   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         ECPContext ctx = (ECPContext)messageContext.getSubcontext(ECPContext.class);
         return ctx != null && ctx.isRequestAuthenticated();
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SAMLObjectBuilder builder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(RequestAuthenticated.DEFAULT_ELEMENT_NAME);
      RequestAuthenticated header = (RequestAuthenticated)builder.buildObject();
      SOAPSupport.addSOAP11ActorAttribute(header, "http://schemas.xmlsoap.org/soap/actor/next");

      try {
         SOAPMessagingSupport.addHeaderBlock(messageContext, header);
      } catch (Exception var5) {
         throw new MessageHandlerException(var5);
      }
   }
}
