package org.opensaml.soap.wssecurity.messaging.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.AbstractHeaderGeneratingMessageHandler;
import org.opensaml.soap.wssecurity.Created;
import org.opensaml.soap.wssecurity.Expires;
import org.opensaml.soap.wssecurity.Timestamp;
import org.opensaml.soap.wssecurity.messaging.WSSecurityContext;
import org.opensaml.soap.wssecurity.messaging.WSSecurityMessagingSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddTimestampHandler extends AbstractHeaderGeneratingMessageHandler {
   private Logger log = LoggerFactory.getLogger(AddTimestampHandler.class);
   private ContextDataLookupFunction createdLookup;
   private ContextDataLookupFunction expiresLookup;
   private boolean useCurrentTimeAsDefaultCreated;
   private Long expiresOffsetFromCreated;
   private DateTime createdValue;
   private DateTime expiresValue;

   @Nullable
   public ContextDataLookupFunction getCreatedLookup() {
      return this.createdLookup;
   }

   public void setCreatedLookup(@Nullable ContextDataLookupFunction lookup) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.createdLookup = lookup;
   }

   @Nullable
   public ContextDataLookupFunction getExpiresLookup() {
      return this.expiresLookup;
   }

   public void setExpiresLookup(@Nullable ContextDataLookupFunction lookup) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.expiresLookup = lookup;
   }

   public boolean isUseCurrentTimeAsDefaultCreated() {
      return this.useCurrentTimeAsDefaultCreated;
   }

   public void setUseCurrentTimeAsDefaultCreated(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.useCurrentTimeAsDefaultCreated = flag;
   }

   @Nullable
   public Long getExpiresOffsetFromCreated() {
      return this.expiresOffsetFromCreated;
   }

   public void setExpiresOffsetFromCreated(@Nullable Long value) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.expiresOffsetFromCreated = value;
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         this.createdValue = this.getCreatedValue(messageContext);
         this.expiresValue = this.getExpiresValue(messageContext, this.createdValue);
         if (this.createdValue == null && this.expiresValue == null) {
            this.log.debug("No WS-Security Timestamp Created or Expires values available, skipping further processing");
            return false;
         } else {
            return true;
         }
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      this.log.debug("Processing addition of outbound WS-Security Timestamp");
      Timestamp timestamp = (Timestamp)XMLObjectSupport.buildXMLObject(Timestamp.ELEMENT_NAME);
      if (this.createdValue != null) {
         this.log.debug("WS-Security Timestamp Created value added was: {}", this.createdValue);
         Created created = (Created)XMLObjectSupport.buildXMLObject(Created.ELEMENT_NAME);
         created.setDateTime(this.createdValue);
         timestamp.setCreated(created);
      }

      if (this.expiresValue != null) {
         this.log.debug("WS-Security Timestamp Expires value added was: {}", this.createdValue);
         Expires expires = (Expires)XMLObjectSupport.buildXMLObject(Expires.ELEMENT_NAME);
         expires.setDateTime(this.expiresValue);
         timestamp.setExpires(expires);
      }

      WSSecurityMessagingSupport.addSecurityHeaderBlock(messageContext, timestamp, this.isEffectiveMustUnderstand(), this.getEffectiveTargetNode(), true);
   }

   @Nullable
   protected DateTime getCreatedValue(@Nonnull MessageContext messageContext) {
      DateTime value = null;
      WSSecurityContext security = (WSSecurityContext)messageContext.getSubcontext(WSSecurityContext.class, false);
      if (security != null) {
         value = security.getTimestampCreated();
      }

      if (value == null && this.getCreatedLookup() != null) {
         value = (DateTime)this.getCreatedLookup().apply(messageContext);
      }

      if (value == null && this.isUseCurrentTimeAsDefaultCreated()) {
         value = new DateTime();
      }

      return value;
   }

   @Nullable
   protected DateTime getExpiresValue(@Nonnull MessageContext messageContext, @Nullable DateTime created) {
      DateTime value = null;
      WSSecurityContext security = (WSSecurityContext)messageContext.getSubcontext(WSSecurityContext.class, false);
      if (security != null) {
         value = security.getTimestampExpires();
      }

      if (value == null && this.getExpiresLookup() != null) {
         value = (DateTime)this.getExpiresLookup().apply(messageContext);
      }

      return value == null && this.getExpiresOffsetFromCreated() != null && created != null ? created.plus(this.getExpiresOffsetFromCreated()) : value;
   }
}
