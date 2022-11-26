package org.opensaml.soap.soap11.decoder.http.impl;

import java.util.List;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePayloadBodyHandler extends AbstractMessageHandler {
   private Logger log = LoggerFactory.getLogger(SimplePayloadBodyHandler.class);

   protected void doInvoke(MessageContext messageContext) throws MessageHandlerException {
      Envelope env = ((SOAP11Context)messageContext.getSubcontext(SOAP11Context.class)).getEnvelope();
      List bodyChildren = env.getBody().getUnknownXMLObjects();
      if (bodyChildren != null && !bodyChildren.isEmpty()) {
         if (bodyChildren.size() > 1) {
            this.log.warn("SOAP Envelope Body contained more than one child.  Returning the first as the message");
         }

         messageContext.setMessage(env.getBody().getUnknownXMLObjects().get(0));
      } else {
         throw new MessageHandlerException("SOAP Envelope Body contained no children");
      }
   }
}
