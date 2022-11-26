package org.opensaml.saml.common.binding.security.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.messaging.impl.BaseTrustEngineSecurityHandler;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.signature.support.SignatureValidationParametersCriterion;

public abstract class BaseSAMLXMLSignatureSecurityHandler extends BaseTrustEngineSecurityHandler {
   @Nullable
   private SAMLPeerEntityContext peerContext;
   @Nullable
   private SAMLProtocolContext samlProtocolContext;

   @Nullable
   protected SAMLPeerEntityContext getSAMLPeerEntityContext() {
      return this.peerContext;
   }

   @Nullable
   protected SAMLProtocolContext getSAMLProtocolContext() {
      return this.samlProtocolContext;
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         this.peerContext = (SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class);
         if (this.peerContext != null && this.peerContext.getRole() != null) {
            this.samlProtocolContext = (SAMLProtocolContext)messageContext.getSubcontext(SAMLProtocolContext.class);
            if (this.samlProtocolContext != null && this.samlProtocolContext.getProtocol() != null) {
               return true;
            } else {
               throw new MessageHandlerException("SAMLProtocolContext was missing or unpopulated");
            }
         } else {
            throw new MessageHandlerException("SAMLPeerEntityContext was missing or unpopulated");
         }
      }
   }

   @Nullable
   protected TrustEngine resolveTrustEngine(@Nonnull MessageContext messageContext) {
      SecurityParametersContext secParams = (SecurityParametersContext)messageContext.getSubcontext(SecurityParametersContext.class);
      return secParams != null && secParams.getSignatureValidationParameters() != null ? secParams.getSignatureValidationParameters().getSignatureTrustEngine() : null;
   }

   @Nonnull
   protected CriteriaSet buildCriteriaSet(@Nullable String entityID, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      CriteriaSet criteriaSet = new CriteriaSet();
      if (!Strings.isNullOrEmpty(entityID)) {
         criteriaSet.add(new EntityIdCriterion(entityID));
      }

      criteriaSet.add(new EntityRoleCriterion(this.peerContext.getRole()));
      criteriaSet.add(new ProtocolCriterion(this.samlProtocolContext.getProtocol()));
      criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
      SecurityParametersContext secParamsContext = (SecurityParametersContext)messageContext.getSubcontext(SecurityParametersContext.class);
      if (secParamsContext != null && secParamsContext.getSignatureValidationParameters() != null) {
         criteriaSet.add(new SignatureValidationParametersCriterion(secParamsContext.getSignatureValidationParameters()));
      }

      return criteriaSet;
   }
}
