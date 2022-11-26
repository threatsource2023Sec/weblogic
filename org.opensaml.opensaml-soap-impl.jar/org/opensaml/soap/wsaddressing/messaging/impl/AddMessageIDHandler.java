package org.opensaml.soap.wsaddressing.messaging.impl;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.AbstractHeaderGeneratingMessageHandler;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.wsaddressing.MessageID;
import org.opensaml.soap.wsaddressing.messaging.WSAddressingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddMessageIDHandler extends AbstractHeaderGeneratingMessageHandler {
   private Logger log = LoggerFactory.getLogger(AddMessageIDHandler.class);
   private IdentifierGenerationStrategy identifierGenerationStrategy;

   @Nullable
   public IdentifierGenerationStrategy getIdentifierGenerationStrategy() {
      return this.identifierGenerationStrategy;
   }

   public void setIdentifierGenerationStrategy(@Nullable IdentifierGenerationStrategy strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.identifierGenerationStrategy = strategy;
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      String id = this.getMessageID(messageContext);
      this.log.debug("Issuing WS-Addressing MessageID: {}", id);
      MessageID messageID = (MessageID)XMLObjectSupport.buildXMLObject(MessageID.ELEMENT_NAME);
      messageID.setValue(id);
      this.decorateGeneratedHeader(messageContext, messageID);
      SOAPMessagingSupport.addHeaderBlock(messageContext, messageID);
   }

   @Nonnull
   protected String getMessageID(MessageContext messageContext) {
      WSAddressingContext addressing = (WSAddressingContext)messageContext.getSubcontext(WSAddressingContext.class, false);
      if (addressing != null && addressing.getMessageIDURI() != null) {
         return addressing.getMessageIDURI();
      } else {
         return this.getIdentifierGenerationStrategy() != null ? this.getIdentifierGenerationStrategy().generateIdentifier(false) : "urn:uuid:" + UUID.randomUUID().toString();
      }
   }
}
