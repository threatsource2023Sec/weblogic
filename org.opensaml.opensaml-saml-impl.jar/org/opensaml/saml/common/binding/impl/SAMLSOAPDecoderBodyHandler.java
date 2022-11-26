package org.opensaml.saml.common.binding.impl;

import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLSOAPDecoderBodyHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAMLSOAPDecoderBodyHandler.class);

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SOAP11Context soap11Context = (SOAP11Context)messageContext.getSubcontext(SOAP11Context.class);
      if (soap11Context == null) {
         throw new MessageHandlerException("SOAP 1.1 context was not present in message context");
      } else {
         Envelope soapMessage = soap11Context.getEnvelope();
         if (soapMessage == null) {
            throw new MessageHandlerException("SOAP 1.1 envelope was not present in SOAP context");
         } else {
            List soapBodyChildren = soapMessage.getBody().getUnknownXMLObjects();
            if (soapBodyChildren.size() >= 1 && soapBodyChildren.size() <= 1) {
               XMLObject incommingMessage = (XMLObject)soapBodyChildren.get(0);
               if (!(incommingMessage instanceof SAMLObject)) {
                  this.log.error("Unexpected SOAP body content.  Expected a SAML request but recieved {}", incommingMessage.getElementQName());
                  throw new MessageHandlerException("Unexpected SOAP body content.  Expected a SAML request but recieved " + incommingMessage.getElementQName());
               } else {
                  messageContext.setMessage(incommingMessage);
               }
            } else {
               this.log.error("Unexpected number of children in the SOAP body, " + soapBodyChildren.size() + ".  Unable to extract SAML message");
               throw new MessageHandlerException("Unexpected number of children in the SOAP body, unable to extract SAML message");
            }
         }
      }
   }
}
