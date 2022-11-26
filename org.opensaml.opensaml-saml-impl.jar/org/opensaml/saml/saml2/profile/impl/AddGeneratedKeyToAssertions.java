package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.ECPContext;
import org.opensaml.saml.ext.samlec.GeneratedKey;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddGeneratedKeyToAssertions extends AbstractConditionalProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddGeneratedKeyToAssertions.class);
   @Nonnull
   private Function ecpContextLookupStrategy = Functions.compose(new ChildContextLookup(ECPContext.class), new OutboundMessageContextLookup());
   @Nonnull
   private Function responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
   @Nullable
   private ECPContext ecpContext;
   @Nullable
   private Response response;

   public void setECPContextLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.ecpContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "ECPContext lookup strategy cannot be null");
   }

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      if (!super.doPreExecute(profileRequestContext)) {
         return false;
      } else {
         this.ecpContext = (ECPContext)this.ecpContextLookupStrategy.apply(profileRequestContext);
         if (this.ecpContext != null && this.ecpContext.getSessionKey() != null) {
            this.log.debug("{} Attempting to add GeneratedKey to every Assertion in Response", this.getLogPrefix());
            this.response = (Response)this.responseLookupStrategy.apply(profileRequestContext);
            if (this.response == null) {
               this.log.debug("{} No SAML response located in current profile request context", this.getLogPrefix());
               ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
               return false;
            } else if (this.response.getAssertions().isEmpty()) {
               this.log.debug("{} No assertions in response message, nothing to do", this.getLogPrefix());
               return false;
            } else {
               return true;
            }
         } else {
            this.log.debug("{} No session key to add, nothing to do", this.getLogPrefix());
            return false;
         }
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      SAMLObjectBuilder keyBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(GeneratedKey.DEFAULT_ELEMENT_NAME);
      String key = Base64Support.encode(this.ecpContext.getSessionKey(), false);
      Iterator var4 = this.response.getAssertions().iterator();

      while(var4.hasNext()) {
         Assertion assertion = (Assertion)var4.next();
         Advice advice = SAML2ActionSupport.addAdviceToAssertion(this, assertion);
         GeneratedKey gk = (GeneratedKey)keyBuilder.buildObject();
         gk.setValue(key);
         advice.getChildren().add(gk);
      }

      this.log.debug("{} Added GeneratedKey to Advice", this.getLogPrefix());
   }
}
