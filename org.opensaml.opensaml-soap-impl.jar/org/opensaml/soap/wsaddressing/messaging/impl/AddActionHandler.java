package org.opensaml.soap.wsaddressing.messaging.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.AbstractHeaderGeneratingMessageHandler;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.wsaddressing.Action;
import org.opensaml.soap.wsaddressing.WSAddressingConstants;
import org.opensaml.soap.wsaddressing.messaging.WSAddressingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddActionHandler extends AbstractHeaderGeneratingMessageHandler {
   private Logger log = LoggerFactory.getLogger(AddActionHandler.class);
   private String actionURI;
   private String faultActionURI;
   private String sendURI;

   @Nullable
   public String getActionURI() {
      return this.actionURI;
   }

   public void setActionURI(@Nullable String uri) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.actionURI = StringSupport.trimOrNull(uri);
   }

   @Nullable
   public String getFaultActionURI() {
      return this.faultActionURI;
   }

   public void setFaultActionURI(@Nullable String uri) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.faultActionURI = StringSupport.trimOrNull(uri);
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         WSAddressingContext addressingContext = (WSAddressingContext)messageContext.getSubcontext(WSAddressingContext.class);
         Fault fault = SOAPMessagingSupport.getSOAP11Fault(messageContext);
         if (fault != null) {
            this.log.debug("Saw SOAP Fault registered in message context, selecting Fault Action URI");
            if (addressingContext != null && addressingContext.getFaultActionURI() != null) {
               this.sendURI = addressingContext.getFaultActionURI();
            } else if (this.faultActionURI != null) {
               this.sendURI = this.faultActionURI;
            } else {
               this.sendURI = this.selectActionURIForFault(fault);
            }
         } else if (addressingContext != null && addressingContext.getActionURI() != null) {
            this.sendURI = addressingContext.getActionURI();
         } else {
            this.sendURI = this.actionURI;
         }

         if (this.sendURI == null) {
            this.log.debug("No WS-Addressing Action URI found locally or in message context, skipping further processing");
            return false;
         } else {
            return true;
         }
      }
   }

   @Nonnull
   protected String selectActionURIForFault(@Nonnull Fault fault) {
      QName faultCode = null;
      if (fault.getCode() != null && fault.getCode().getValue() != null) {
         faultCode = fault.getCode().getValue();
      }

      return faultCode != null && WSAddressingConstants.WS_ADDRESSING_FAULTS.contains(faultCode) ? "http://www.w3.org/2005/08/addressing/fault" : "http://www.w3.org/2005/08/addressing/soap/fault";
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      this.log.debug("Issuing WS-Addressing Action header with URI value: {}", this.sendURI);
      Action action = (Action)XMLObjectSupport.buildXMLObject(Action.ELEMENT_NAME);
      action.setValue(this.sendURI);
      this.decorateGeneratedHeader(messageContext, action);
      SOAPMessagingSupport.addHeaderBlock(messageContext, action);
   }
}
