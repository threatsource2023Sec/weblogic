package org.opensaml.soap.wsaddressing.messaging.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.AbstractHeaderGeneratingMessageHandler;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.wsaddressing.RelatesTo;
import org.opensaml.soap.wsaddressing.messaging.WSAddressingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddRelatesToHandler extends AbstractHeaderGeneratingMessageHandler {
   private Logger log = LoggerFactory.getLogger(AddRelatesToHandler.class);
   private ContextDataLookupFunction relatesToURILookup;
   private String relatesToURI;
   private String relationshipType;

   public ContextDataLookupFunction getRelatesToURILookup() {
      return this.relatesToURILookup;
   }

   public void setRelatesToURILookup(ContextDataLookupFunction lookup) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.relatesToURILookup = lookup;
   }

   public String getRelationshipType() {
      return this.relationshipType;
   }

   public void setRelationshipType(String value) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.relationshipType = StringSupport.trimOrNull(value);
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         WSAddressingContext addressing = (WSAddressingContext)messageContext.getSubcontext(WSAddressingContext.class, false);
         if (addressing != null) {
            this.relatesToURI = addressing.getRelatesToURI();
            if (this.relationshipType == null) {
               this.relationshipType = addressing.getRelatesToRelationshipType();
            }
         }

         if (this.relatesToURI == null && this.getRelatesToURILookup() != null) {
            this.relatesToURI = (String)this.getRelatesToURILookup().apply(messageContext);
         }

         if (this.relatesToURI == null) {
            this.log.debug("No WS-Addressing RelatesTo value found in message context, skipping further processing");
            return false;
         } else {
            return true;
         }
      }
   }

   protected void doInvoke(MessageContext messageContext) throws MessageHandlerException {
      this.log.debug("Issuing WS-Addressing RelatesTo header with URI '{}' and RelationshipType '{}'", this.relatesToURI, this.relationshipType);
      RelatesTo relatesTo = (RelatesTo)XMLObjectSupport.buildXMLObject(RelatesTo.ELEMENT_NAME);
      relatesTo.setValue(this.relatesToURI);
      relatesTo.setRelationshipType(this.relationshipType);
      this.decorateGeneratedHeader(messageContext, relatesTo);
      SOAPMessagingSupport.addHeaderBlock(messageContext, relatesTo);
   }
}
