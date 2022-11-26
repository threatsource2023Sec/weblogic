package org.opensaml.saml.saml2.binding.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.ECPContext;
import org.opensaml.saml.ext.samlec.GeneratedKey;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.util.SOAPSupport;

public class AddGeneratedKeyHeaderHandler extends AbstractMessageHandler {
   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         ECPContext ctx = (ECPContext)messageContext.getSubcontext(ECPContext.class);
         return ctx != null && ctx.getSessionKey() != null;
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SAMLObjectBuilder builder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(GeneratedKey.DEFAULT_ELEMENT_NAME);
      GeneratedKey header = (GeneratedKey)builder.buildObject();
      header.setValue(Base64Support.encode(((ECPContext)messageContext.getSubcontext(ECPContext.class)).getSessionKey(), false));
      SOAPSupport.addSOAP11ActorAttribute(header, "http://schemas.xmlsoap.org/soap/actor/next");

      try {
         SOAPMessagingSupport.addHeaderBlock(messageContext, header);
      } catch (Exception var5) {
         throw new MessageHandlerException(var5);
      }
   }
}
