package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.profile.SAML1ActionSupport;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNotBeforeConditionToAssertions extends AbstractConditionalProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddNotBeforeConditionToAssertions.class);
   @Nonnull
   private Function responseLookupStrategy = Functions.compose(new MessageLookup(SAMLObject.class), new OutboundMessageContextLookup());
   @Nullable
   private SAMLObject response;

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.log.debug("{} Attempting to add NotBefore condition to every Assertion in outgoing Response", this.getLogPrefix());
      this.response = (SAMLObject)this.responseLookupStrategy.apply(profileRequestContext);
      if (this.response == null) {
         this.log.debug("{} No SAML Response located in current profile request context", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else {
         if (this.response instanceof Response) {
            if (((Response)this.response).getAssertions().isEmpty()) {
               this.log.debug("{} No assertions available, nothing to do", this.getLogPrefix());
               return false;
            }
         } else {
            if (!(this.response instanceof org.opensaml.saml.saml2.core.Response)) {
               this.log.debug("{} Message returned by lookup strategy was not a SAML Response", this.getLogPrefix());
               ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
               return false;
            }

            if (((org.opensaml.saml.saml2.core.Response)this.response).getAssertions().isEmpty()) {
               this.log.debug("{} No assertions available, nothing to do", this.getLogPrefix());
               return false;
            }
         }

         return super.doPreExecute(profileRequestContext);
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      Iterator var2;
      if (this.response instanceof Response) {
         var2 = ((Response)this.response).getAssertions().iterator();

         while(var2.hasNext()) {
            Assertion assertion = (Assertion)var2.next();
            this.log.debug("{} Added NotBefore condition to Assertion {}", this.getLogPrefix(), assertion.getID());
            SAML1ActionSupport.addConditionsToAssertion(this, assertion).setNotBefore(((Response)this.response).getIssueInstant());
         }
      } else if (this.response instanceof org.opensaml.saml.saml2.core.Response) {
         var2 = ((org.opensaml.saml.saml2.core.Response)this.response).getAssertions().iterator();

         while(var2.hasNext()) {
            org.opensaml.saml.saml2.core.Assertion assertion = (org.opensaml.saml.saml2.core.Assertion)var2.next();
            this.log.debug("{} Added NotBefore condition to Assertion {}", this.getLogPrefix(), assertion.getID());
            SAML2ActionSupport.addConditionsToAssertion(this, assertion).setNotBefore(((org.opensaml.saml.saml2.core.Response)this.response).getIssueInstant());
         }
      }

   }
}
