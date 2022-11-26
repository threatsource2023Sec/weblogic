package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.criterion.RoleDescriptorCriterion;
import org.opensaml.xmlsec.SecurityConfigurationSupport;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.SignatureSigningParametersResolver;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.criterion.SignatureSigningConfigurationCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopulateSignatureSigningParameters extends AbstractConditionalProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(PopulateSignatureSigningParameters.class);
   @Nonnull
   private Function securityParametersContextLookupStrategy = Functions.compose(new ChildContextLookup(SecurityParametersContext.class, true), new OutboundMessageContextLookup());
   @Nullable
   private Function existingParametersContextLookupStrategy;
   @NonnullAfterInit
   private Function configurationLookupStrategy;
   @Nullable
   private Function metadataContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLMetadataContext.class), Functions.compose(new ChildContextLookup(SAMLPeerEntityContext.class), new OutboundMessageContextLookup()));
   @NonnullAfterInit
   private SignatureSigningParametersResolver resolver;

   public void setSecurityParametersContextLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.securityParametersContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "SecurityParametersContext lookup strategy cannot be null");
   }

   public void setExistingParametersContextLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.existingParametersContextLookupStrategy = strategy;
   }

   public void setMetadataContextLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.metadataContextLookupStrategy = strategy;
   }

   public void setConfigurationLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.configurationLookupStrategy = (Function)Constraint.isNotNull(strategy, "SignatureSigningConfiguration lookup strategy cannot be null");
   }

   public void setSignatureSigningParametersResolver(@Nonnull SignatureSigningParametersResolver newResolver) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.resolver = (SignatureSigningParametersResolver)Constraint.isNotNull(newResolver, "SignatureSigningParametersResolver cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.resolver == null) {
         throw new ComponentInitializationException("SignatureSigningParametersResolver cannot be null");
      } else {
         if (this.configurationLookupStrategy == null) {
            this.configurationLookupStrategy = new Function() {
               public List apply(ProfileRequestContext input) {
                  return Collections.singletonList(SecurityConfigurationSupport.getGlobalSignatureSigningConfiguration());
               }
            };
         }

      }
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      if (super.doPreExecute(profileRequestContext)) {
         this.log.debug("{} Signing enabled", this.getLogPrefix());
         return true;
      } else {
         this.log.debug("{} Signing not enabled", this.getLogPrefix());
         return false;
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.log.debug("{} Resolving SignatureSigningParameters for request", this.getLogPrefix());
      SecurityParametersContext paramsCtx = (SecurityParametersContext)this.securityParametersContextLookupStrategy.apply(profileRequestContext);
      if (paramsCtx == null) {
         this.log.debug("{} No SecurityParametersContext returned by lookup strategy", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidProfileContext");
      } else {
         if (this.existingParametersContextLookupStrategy != null) {
            SecurityParametersContext existingCtx = (SecurityParametersContext)this.existingParametersContextLookupStrategy.apply(profileRequestContext);
            if (existingCtx != null && existingCtx.getSignatureSigningParameters() != null) {
               this.log.debug("{} Found existing SecurityParametersContext to copy from", this.getLogPrefix());
               paramsCtx.setSignatureSigningParameters(existingCtx.getSignatureSigningParameters());
               return;
            }
         }

         List configs = (List)this.configurationLookupStrategy.apply(profileRequestContext);
         if (configs != null && !configs.isEmpty()) {
            CriteriaSet criteria = new CriteriaSet(new Criterion[]{new SignatureSigningConfigurationCriterion(configs)});
            if (this.metadataContextLookupStrategy != null) {
               SAMLMetadataContext metadataCtx = (SAMLMetadataContext)this.metadataContextLookupStrategy.apply(profileRequestContext);
               if (metadataCtx != null && metadataCtx.getRoleDescriptor() != null) {
                  this.log.debug("{} Adding metadata to resolution criteria for signing/digest algorithms", this.getLogPrefix());
                  criteria.add(new RoleDescriptorCriterion(metadataCtx.getRoleDescriptor()));
               }
            }

            try {
               SignatureSigningParameters params = (SignatureSigningParameters)this.resolver.resolveSingle(criteria);
               paramsCtx.setSignatureSigningParameters(params);
               this.log.debug("{} {} SignatureSigningParameters", this.getLogPrefix(), params != null ? "Resolved" : "Failed to resolve");
            } catch (ResolverException var6) {
               this.log.error("{} Error resolving SignatureSigningParameters", this.getLogPrefix(), var6);
               ActionSupport.buildEvent(profileRequestContext, "InvalidSecurityConfiguration");
            }

         } else {
            this.log.error("{} No SignatureSigningConfiguration returned by lookup strategy", this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidSecurityConfiguration");
         }
      }
   }
}
