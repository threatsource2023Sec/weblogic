package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.OneTimeUse;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddOneTimeUseConditionToAssertions extends AbstractConditionalProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddOneTimeUseConditionToAssertions.class);
   @Nonnull
   private Function responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
   @Nullable
   private Response response;

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.log.debug("{} Attempting to add OneTimeUse condition to every Assertion in Response", this.getLogPrefix());
      this.response = (Response)this.responseLookupStrategy.apply(profileRequestContext);
      if (this.response == null) {
         this.log.debug("{} No SAML response located in current profile request context", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else if (this.response.getAssertions().isEmpty()) {
         this.log.debug("{} No assertions in response message, nothing to do", this.getLogPrefix());
         return false;
      } else {
         return super.doPreExecute(profileRequestContext);
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      SAMLObjectBuilder conditionBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(OneTimeUse.DEFAULT_ELEMENT_NAME);
      Iterator var3 = this.response.getAssertions().iterator();

      while(var3.hasNext()) {
         Assertion assertion = (Assertion)var3.next();
         Conditions conditions = SAML2ActionSupport.addConditionsToAssertion(this, assertion);
         if (conditions.getOneTimeUse() == null) {
            conditions.getConditions().add(conditionBuilder.buildObject());
            this.log.debug("{} Added OneTimeUse condition to Assertion {}", this.getLogPrefix(), assertion.getID());
         } else {
            this.log.debug("{} Assertion {} already contained OneTimeUse condition, another was not added", this.getLogPrefix(), assertion.getID());
         }
      }

   }
}
