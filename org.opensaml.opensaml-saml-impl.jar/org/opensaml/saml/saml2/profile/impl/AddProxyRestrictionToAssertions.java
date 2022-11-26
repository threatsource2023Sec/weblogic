package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
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
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.ProxyRestriction;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddProxyRestrictionToAssertions extends AbstractConditionalProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddProxyRestrictionToAssertions.class);
   @Nonnull
   private Function responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
   @Nullable
   private Function proxyAudiencesLookupStrategy;
   @Nullable
   private Function proxyCountLookupStrategy;
   @Nullable
   private Response response;
   @Nullable
   private Collection audiences;

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   public void setProxyAudiencesLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.proxyAudiencesLookupStrategy = (Function)Constraint.isNotNull(strategy, "Proxy restriction audiences lookup strategy cannot be null");
   }

   public void setProxyCountLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.proxyCountLookupStrategy = (Function)Constraint.isNotNull(strategy, "Proxy count lookup strategy cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.proxyAudiencesLookupStrategy == null) {
         throw new ComponentInitializationException("Proxy restriction audience lookup strategy cannot be null");
      } else if (this.proxyCountLookupStrategy == null) {
         throw new ComponentInitializationException("Proxy count lookup strategy cannot be null");
      }
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.audiences = (Collection)this.proxyAudiencesLookupStrategy.apply(profileRequestContext);
      if (this.audiences != null && !this.audiences.isEmpty()) {
         this.log.debug("{} Attempting to add an ProxyRestriction to every Assertion in Response", this.getLogPrefix());
         this.response = (Response)this.responseLookupStrategy.apply(profileRequestContext);
         if (this.response == null) {
            this.log.debug("{} No response located", this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
         } else if (this.response.getAssertions().isEmpty()) {
            this.log.debug("{} No assertions found in response, nothing to do", this.getLogPrefix());
            return false;
         } else {
            return super.doPreExecute(profileRequestContext);
         }
      } else {
         this.log.debug("{} No audiences to add, nothing to do", this.getLogPrefix());
         return false;
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      Iterator var2 = this.response.getAssertions().iterator();

      while(var2.hasNext()) {
         Assertion assertion = (Assertion)var2.next();
         this.addProxyRestriction(profileRequestContext, SAML2ActionSupport.addConditionsToAssertion(this, assertion));
         this.log.debug("{} Added ProxyRestriction to Assertion {}", this.getLogPrefix(), assertion.getID());
      }

   }

   private void addProxyRestriction(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Conditions conditions) {
      ProxyRestriction condition = this.getProxyRestriction(conditions);
      SAMLObjectBuilder audienceBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Audience.DEFAULT_ELEMENT_NAME);
      Iterator var5 = this.audiences.iterator();

      while(var5.hasNext()) {
         String audienceId = (String)var5.next();
         this.log.debug("{} Adding {} as an Audience of the ProxyRestriction", this.getLogPrefix(), audienceId);
         Audience audience = (Audience)audienceBuilder.buildObject();
         audience.setAudienceURI(audienceId);
         condition.getAudiences().add(audience);
      }

      Long count = (Long)this.proxyCountLookupStrategy.apply(profileRequestContext);
      condition.setProxyCount(count != null ? count.intValue() : 0);
   }

   @Nonnull
   private ProxyRestriction getProxyRestriction(@Nonnull Conditions conditions) {
      ProxyRestriction condition;
      if (conditions.getProxyRestriction() == null) {
         SAMLObjectBuilder conditionBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(ProxyRestriction.DEFAULT_ELEMENT_NAME);
         this.log.debug("{} Adding new ProxyRestriction", this.getLogPrefix());
         condition = (ProxyRestriction)conditionBuilder.buildObject();
         conditions.getConditions().add(condition);
      } else {
         this.log.debug("{} Conditions already contained an ProxyRestriction, using it", this.getLogPrefix());
         condition = conditions.getProxyRestriction();
      }

      return condition;
   }
}
