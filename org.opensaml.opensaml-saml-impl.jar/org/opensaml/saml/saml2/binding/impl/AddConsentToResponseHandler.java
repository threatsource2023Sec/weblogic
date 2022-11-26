package org.opensaml.saml.saml2.binding.impl;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.SAMLConsentContext;
import org.opensaml.saml.saml2.core.StatusResponseType;

public class AddConsentToResponseHandler extends AbstractMessageHandler {
   @Nonnull
   private Function consentContextStrategy = new ChildContextLookup(SAMLConsentContext.class);

   public void setConsentContextLookupStrategy(@Nonnull Function strategy) {
      this.consentContextStrategy = (Function)Constraint.isNotNull(strategy, "SAMLConsentContext lookup strategy cannot be null");
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      Object response = messageContext.getMessage();
      if (response == null) {
         throw new MessageHandlerException("Message not found");
      } else if (!(response instanceof StatusResponseType)) {
         throw new MessageHandlerException("Message was not a StatusResponseType");
      } else {
         SAMLConsentContext consentContext = (SAMLConsentContext)this.consentContextStrategy.apply(messageContext);
         if (consentContext != null && consentContext.getConsent() != null) {
            ((StatusResponseType)response).setConsent(consentContext.getConsent());
         } else {
            throw new MessageHandlerException("Consent value not found");
         }
      }
   }
}
