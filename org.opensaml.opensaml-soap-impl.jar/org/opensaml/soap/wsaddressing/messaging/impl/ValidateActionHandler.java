package org.opensaml.soap.wsaddressing.messaging.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.wsaddressing.Action;
import org.opensaml.soap.wsaddressing.WSAddressingConstants;
import org.opensaml.soap.wsaddressing.messaging.WSAddressingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateActionHandler extends AbstractMessageHandler {
   private Logger log = LoggerFactory.getLogger(ValidateActionHandler.class);
   private String expectedActionURI;

   @Nullable
   public String getExpectedActionURI() {
      return this.expectedActionURI;
   }

   public void setExpectedActionURI(@Nullable String uri) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.expectedActionURI = StringSupport.trimOrNull(uri);
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         WSAddressingContext addressing = (WSAddressingContext)messageContext.getSubcontext(WSAddressingContext.class, false);
         if (addressing != null && addressing.getActionURI() != null) {
            this.expectedActionURI = addressing.getActionURI();
         }

         if (this.expectedActionURI == null) {
            this.log.debug("No expected WS-Addressing Action URI found locally or in message context, skipping evaluation");
            return false;
         } else {
            return true;
         }
      }
   }

   protected void doInvoke(MessageContext messageContext) throws MessageHandlerException {
      Action header = this.getAction(messageContext);
      String headerValue = header != null ? StringSupport.trimOrNull(header.getValue()) : null;
      this.log.debug("Checking inbound message WS-Addressing Action URI value: {}", headerValue);
      if (Objects.equals(this.getExpectedActionURI(), headerValue)) {
         this.log.debug("Inbound WS-Addressing Action URI matched expected value");
         SOAPMessagingSupport.registerUnderstoodHeader(messageContext, header);
      } else {
         this.log.warn("Inbound WS-Addressing Action URI '{}' did not match the expected value '{}'", headerValue, this.getExpectedActionURI());
         SOAPMessagingSupport.registerSOAP11Fault(messageContext, WSAddressingConstants.SOAP_FAULT_ACTION_NOT_SUPPORTED, "Action URI not supported: " + headerValue, (String)null, (List)null, (Map)null);
         throw new MessageHandlerException("Inbound WS-Addressing Action URI did not match the expected value");
      }
   }

   protected Action getAction(@Nonnull MessageContext messageContext) {
      List actions = SOAPMessagingSupport.getInboundHeaderBlock(messageContext, Action.ELEMENT_NAME);
      return actions != null && !actions.isEmpty() ? (Action)actions.get(0) : null;
   }
}
