package org.opensaml.saml.common.binding.security.impl;

import com.google.common.base.Strings;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.opensaml.xmlsec.signature.support.SignatureValidationParametersCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSAMLSimpleSignatureSecurityHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(BaseSAMLSimpleSignatureSecurityHandler.class);
   @NonnullAfterInit
   private HttpServletRequest httpServletRequest;
   @Nullable
   private SAMLPeerEntityContext peerContext;
   @Nullable
   private SAMLProtocolContext samlProtocolContext;
   @Nullable
   private SignatureTrustEngine trustEngine;

   @Nullable
   protected SignatureTrustEngine getTrustEngine() {
      return this.trustEngine;
   }

   @NonnullAfterInit
   public HttpServletRequest getHttpServletRequest() {
      return this.httpServletRequest;
   }

   public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.httpServletRequest = (HttpServletRequest)Constraint.isNotNull(request, "HttpServletRequest cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.httpServletRequest == null) {
         throw new ComponentInitializationException("HttpServletRequest cannot be null");
      }
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         this.peerContext = (SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class);
         if (this.peerContext != null && this.peerContext.getRole() != null) {
            this.samlProtocolContext = (SAMLProtocolContext)messageContext.getSubcontext(SAMLProtocolContext.class);
            if (this.samlProtocolContext != null && this.samlProtocolContext.getProtocol() != null) {
               SecurityParametersContext secParams = (SecurityParametersContext)messageContext.getSubcontext(SecurityParametersContext.class);
               if (secParams != null && secParams.getSignatureValidationParameters() != null && secParams.getSignatureValidationParameters().getSignatureTrustEngine() != null) {
                  this.trustEngine = secParams.getSignatureValidationParameters().getSignatureTrustEngine();
                  return true;
               } else {
                  throw new MessageHandlerException("No SignatureTrustEngine was available from the MessageContext");
               }
            } else {
               throw new MessageHandlerException("SAMLProtocolContext was missing or unpopulated");
            }
         } else {
            throw new MessageHandlerException("SAMLPeerEntityContext was missing or unpopulated");
         }
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      this.log.debug("{} Evaluating simple signature rule of type: {}", this.getLogPrefix(), this.getClass().getName());
      if (!this.ruleHandles(messageContext)) {
         this.log.debug("{} Handler can not handle this request, skipping processing", this.getLogPrefix());
      } else {
         byte[] signature = this.getSignature();
         if (signature != null && signature.length != 0) {
            String sigAlg = this.getSignatureAlgorithm();
            if (Strings.isNullOrEmpty(sigAlg)) {
               this.log.warn("{} Signature algorithm could not be extracted from request, cannot validate simple signature", this.getLogPrefix());
            } else {
               byte[] signedContent = this.getSignedContent();
               if (signedContent != null && signedContent.length != 0) {
                  this.doEvaluate(signature, signedContent, sigAlg, messageContext);
               } else {
                  this.log.warn("{} Signed content could not be extracted from HTTP request, cannot validate", this.getLogPrefix());
               }
            }
         } else {
            this.log.debug("{} HTTP request was not signed via simple signature mechanism, skipping", this.getLogPrefix());
         }
      }
   }

   private void doEvaluate(@Nonnull @NotEmpty byte[] signature, @Nonnull @NotEmpty byte[] signedContent, @Nonnull @NotEmpty String algorithmURI, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      List candidateCredentials = this.getRequestCredentials(messageContext);
      String contextEntityID = this.peerContext.getEntityId();
      if (contextEntityID != null) {
         this.log.debug("{} Attempting to validate SAML protocol message simple signature using context entityID: {}", this.getLogPrefix(), contextEntityID);
         CriteriaSet criteriaSet = this.buildCriteriaSet(contextEntityID, messageContext);
         if (this.validateSignature(signature, signedContent, algorithmURI, criteriaSet, candidateCredentials)) {
            this.log.debug("{} Validation of request simple signature succeeded", this.getLogPrefix());
            if (!this.peerContext.isAuthenticated()) {
               this.log.debug("{} Authentication via request simple signature succeeded for context issuer entity ID {}", this.getLogPrefix(), contextEntityID);
               this.peerContext.setAuthenticated(true);
            }

         } else {
            this.log.warn("{} Validation of request simple signature failed for context issuer: {}", this.getLogPrefix(), contextEntityID);
            throw new MessageHandlerException("Validation of request simple signature failed for context issuer");
         }
      } else {
         String derivedEntityID = this.deriveSignerEntityID(messageContext);
         if (derivedEntityID != null) {
            this.log.debug("{} Attempting to validate SAML protocol message simple signature using derived entityID: {}", this.getLogPrefix(), derivedEntityID);
            CriteriaSet criteriaSet = this.buildCriteriaSet(derivedEntityID, messageContext);
            if (this.validateSignature(signature, signedContent, algorithmURI, criteriaSet, candidateCredentials)) {
               this.log.debug("{} Validation of request simple signature succeeded", this.getLogPrefix());
               if (!this.peerContext.isAuthenticated()) {
                  this.log.debug("{} Authentication via request simple signature succeeded for derived issuer {}", this.getLogPrefix(), derivedEntityID);
                  this.peerContext.setEntityId(derivedEntityID);
                  this.peerContext.setAuthenticated(true);
               }

            } else {
               this.log.warn("{} Validation of request simple signature failed for derived issuer: {}", this.getLogPrefix(), derivedEntityID);
               throw new MessageHandlerException("Validation of request simple signature failed for derived issuer");
            }
         } else {
            this.log.warn("{} Neither context nor derived issuer available, cannot attempt SAML simple signature validation", this.getLogPrefix());
            throw new MessageHandlerException("No message issuer available, cannot attempt simple signature validation");
         }
      }
   }

   protected boolean validateSignature(@Nonnull @NotEmpty byte[] signature, @Nonnull @NotEmpty byte[] signedContent, @Nonnull @NotEmpty String algorithmURI, @Nonnull CriteriaSet criteriaSet, @Nonnull @NonnullElements List candidateCredentials) throws MessageHandlerException {
      SignatureTrustEngine engine = this.getTrustEngine();

      try {
         if (candidateCredentials != null && !candidateCredentials.isEmpty()) {
            Iterator var7 = candidateCredentials.iterator();

            Credential cred;
            do {
               if (!var7.hasNext()) {
                  this.log.warn("{} Signature validation using request-derived credentials failed", this.getLogPrefix());
                  return false;
               }

               cred = (Credential)var7.next();
            } while(!engine.validate(signature, signedContent, algorithmURI, criteriaSet, cred));

            this.log.debug("{} Simple signature validation succeeded with a request-derived credential", this.getLogPrefix());
            return true;
         } else if (engine.validate(signature, signedContent, algorithmURI, criteriaSet, (Credential)null)) {
            this.log.debug("{} Simple signature validation (with no request-derived credentials) was successful", this.getLogPrefix());
            return true;
         } else {
            this.log.warn("{} Simple signature validation (with no request-derived credentials) failed", this.getLogPrefix());
            return false;
         }
      } catch (SecurityException var9) {
         this.log.warn("{} Error evaluating the request's simple signature using the trust engine", this.getLogPrefix(), var9);
         throw new MessageHandlerException("Error during trust engine evaluation of the simple signature", var9);
      }
   }

   @Nonnull
   @NonnullElements
   protected List getRequestCredentials(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      return Collections.emptyList();
   }

   @Nullable
   protected byte[] getSignature() throws MessageHandlerException {
      String signature = this.getHttpServletRequest().getParameter("Signature");
      return Strings.isNullOrEmpty(signature) ? null : Base64Support.decode(signature);
   }

   @Nullable
   protected String getSignatureAlgorithm() throws MessageHandlerException {
      return this.getHttpServletRequest().getParameter("SigAlg");
   }

   @Nullable
   protected String deriveSignerEntityID(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      return null;
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

   @Nullable
   protected abstract byte[] getSignedContent() throws MessageHandlerException;

   protected abstract boolean ruleHandles(@Nonnull MessageContext var1) throws MessageHandlerException;
}
