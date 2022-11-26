package org.opensaml.soap.soap11.decoder.http.impl;

import javax.annotation.Nonnull;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Envelope;

public class EnvelopeBodyHandler extends AbstractMessageHandler {
   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      Envelope env = ((SOAP11Context)messageContext.getSubcontext(SOAP11Context.class)).getEnvelope();
      if (env == null) {
         throw new MessageHandlerException("MessageContext did not contain a SOAP Envelope");
      } else {
         messageContext.setMessage(env);
      }
   }
}
