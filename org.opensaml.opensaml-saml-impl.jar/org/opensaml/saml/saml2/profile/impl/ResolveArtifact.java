package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.saml.common.messaging.context.navigate.SAMLMessageContextIssuerFunction;
import org.opensaml.saml.saml2.core.ArtifactResolve;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolveArtifact extends AbstractProfileAction {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(ResolveArtifact.class);
   @Nonnull
   private Function requestLookupStrategy = Functions.compose(new MessageLookup(ArtifactResolve.class), new InboundMessageContextLookup());
   @Nonnull
   private Function responseLookupStrategy = Functions.compose(new MessageLookup(ArtifactResponse.class), new OutboundMessageContextLookup());
   @NonnullAfterInit
   private Function issuerLookupStrategy;
   @Nonnull
   private Function requesterLookupStrategy = Functions.compose(new SAMLMessageContextIssuerFunction(), new InboundMessageContextLookup());
   @NonnullAfterInit
   private SAMLArtifactMap artifactMap;
   @Nullable
   private ArtifactResolve request;
   @Nullable
   private ArtifactResponse response;
   @Nullable
   private String issuerId;
   @Nullable
   private String requesterId;

   public void setRequestLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requestLookupStrategy = (Function)Constraint.isNotNull(strategy, "Request lookup strategy cannot be null");
   }

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   public void setIssuerLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.issuerLookupStrategy = (Function)Constraint.isNotNull(strategy, "Issuer lookup strategy cannot be null");
   }

   public void setRequesterLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requesterLookupStrategy = (Function)Constraint.isNotNull(strategy, "Requester lookup strategy cannot be null");
   }

   public void setArtifactMap(@Nonnull SAMLArtifactMap map) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.artifactMap = (SAMLArtifactMap)Constraint.isNotNull(map, "SAMLArtifactMap cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.artifactMap == null) {
         throw new ComponentInitializationException("SAMLArtifactMap cannot be null");
      } else if (this.issuerLookupStrategy == null) {
         throw new ComponentInitializationException("Issuer lookup strategy cannot be null");
      }
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.request = (ArtifactResolve)this.requestLookupStrategy.apply(profileRequestContext);
      if (this.request == null) {
         this.log.debug("{} No request located", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else if (this.request.getArtifact() != null && this.request.getArtifact().getArtifact() != null) {
         this.response = (ArtifactResponse)this.responseLookupStrategy.apply(profileRequestContext);
         if (this.response == null) {
            this.log.debug("{} No response located", this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
         } else {
            this.issuerId = (String)this.issuerLookupStrategy.apply(profileRequestContext);
            if (this.issuerId == null) {
               this.log.debug("{} No issuer identity located", this.getLogPrefix());
               ActionSupport.buildEvent(profileRequestContext, "InvalidProfileContext");
               return false;
            } else {
               this.requesterId = (String)this.requesterLookupStrategy.apply(profileRequestContext);
               if (this.requesterId == null) {
                  this.log.debug("{} No requester identity located", this.getLogPrefix());
                  ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
                  return false;
               } else {
                  return super.doPreExecute(profileRequestContext);
               }
            }
         }
      } else {
         this.log.debug("{} No Artifact element found in request, nothing to do", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      String artifact = this.request.getArtifact().getArtifact();
      SAMLArtifactMap.SAMLArtifactMapEntry entry = null;

      try {
         entry = this.artifactMap.get(artifact);
      } catch (IOException var6) {
         this.log.error("{} Error resolving artifact", this.getLogPrefix(), var6);
         ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
         return;
      }

      if (entry == null) {
         this.log.warn("{} Unresolvable Artifact '{}' from relying party '{}'", new Object[]{this.getLogPrefix(), artifact, this.requesterId});
         ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
      } else {
         try {
            this.artifactMap.remove(artifact);
         } catch (IOException var5) {
            this.log.error("{} Error removing artifact from map", this.getLogPrefix(), var5);
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
            return;
         }

         if (!entry.getIssuerId().equals(this.issuerId)) {
            this.log.warn("{} Artifact issuer mismatch, issued by '{}' but IdP has entityID of '{}'", new Object[]{this.getLogPrefix(), entry.getIssuerId(), this.issuerId});
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
         } else if (!entry.getRelyingPartyId().equals(this.requesterId)) {
            this.log.warn("{} Artifact relying party mismatch, issued to '{}' but requested by '{}'", new Object[]{this.getLogPrefix(), entry.getRelyingPartyId(), this.requesterId});
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
         } else {
            this.response.setMessage(entry.getSamlMessage());
         }

      }
   }
}
