package org.opensaml.saml.saml2.binding.impl;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.SAMLConsentContext;
import org.opensaml.saml.saml2.core.RequestAbstractType;

public class ExtractConsentFromRequestHandler extends AbstractMessageHandler {
   @Nonnull
   private Function consentContextStrategy = new ChildContextLookup(SAMLConsentContext.class, true);

   public void setConsentContextLookupStrategy(@Nonnull Function strategy) {
      this.consentContextStrategy = (Function)Constraint.isNotNull(strategy, "SAMLConsentContext lookup strategy cannot be null");
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      Object request = messageContext.getMessage();
      if (request == null) {
         throw new MessageHandlerException("Message not found");
      } else if (!(request instanceof RequestAbstractType)) {
         throw new MessageHandlerException("Message was not a RequestAbstractType");
      } else {
         SAMLConsentContext consentContext = (SAMLConsentContext)this.consentContextStrategy.apply(messageContext);
         if (consentContext == null) {
            throw new MessageHandlerException("SAMLConsentContext to populate not found");
         } else {
            consentContext.setConsent(((RequestAbstractType)request).getConsent());
         }
      }
   }
}
