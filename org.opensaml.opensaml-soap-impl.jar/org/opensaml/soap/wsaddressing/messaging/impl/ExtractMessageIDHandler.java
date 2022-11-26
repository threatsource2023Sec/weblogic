package org.opensaml.soap.wsaddressing.messaging.impl;

import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.wsaddressing.MessageID;
import org.opensaml.soap.wsaddressing.messaging.WSAddressingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractMessageIDHandler extends AbstractMessageHandler {
   private Logger log = LoggerFactory.getLogger(ExtractMessageIDHandler.class);

   protected void doInvoke(MessageContext messageContext) throws MessageHandlerException {
      MessageID header = this.getMessageID(messageContext);
      String headerValue = header != null ? StringSupport.trimOrNull(header.getValue()) : null;
      this.log.debug("Extracted inbound WS-Addressing MessageID value: {}", headerValue);
      if (header != null && headerValue != null) {
         ((WSAddressingContext)messageContext.getSubcontext(WSAddressingContext.class, true)).setMessageIDURI(headerValue);
         SOAPMessagingSupport.registerUnderstoodHeader(messageContext, header);
      }

   }

   protected MessageID getMessageID(@Nonnull MessageContext messageContext) {
      List messageIDs = SOAPMessagingSupport.getInboundHeaderBlock(messageContext, MessageID.ELEMENT_NAME);
      return messageIDs != null && !messageIDs.isEmpty() ? (MessageID)messageIDs.get(0) : null;
   }
}
