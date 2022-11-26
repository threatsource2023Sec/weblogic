package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.encryption.Decrypter;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDecryptAction extends AbstractProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractDecryptAction.class);
   private boolean errorFatal = true;
   @Nonnull
   private Function securityParamsLookupStrategy = Functions.compose(new ChildContextLookup(SecurityParametersContext.class), new InboundMessageContextLookup());
   @Nonnull
   private Function messageLookupStrategy = Functions.compose(new MessageLookup(Object.class), new InboundMessageContextLookup());
   @Nonnull
   private Predicate decryptionPredicate = Predicates.alwaysTrue();
   @Nullable
   private Decrypter decrypter;
   @Nullable
   private SAMLObject message;

   public boolean isErrorFatal() {
      return this.errorFatal;
   }

   public void setErrorFatal(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.errorFatal = flag;
   }

   public void setSecurityParametersContextLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.securityParamsLookupStrategy = (Function)Constraint.isNotNull(strategy, "SecurityParametersContext lookup strategy cannot be null");
   }

   public void setMessageLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.messageLookupStrategy = (Function)Constraint.isNotNull(strategy, "Message lookup strategy cannot be null");
   }

   @Nonnull
   public Predicate getDecryptionPredicate() {
      return this.decryptionPredicate;
   }

   public void setDecryptionPredicate(@Nonnull Predicate predicate) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.decryptionPredicate = (Predicate)Constraint.isNotNull(predicate, "Decryption predicate cannot be null");
   }

   @Nullable
   public Decrypter getDecrypter() {
      return this.decrypter;
   }

   @Nullable
   public SAMLObject getSAMLObject() {
      return this.message;
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      Object theMessage = this.messageLookupStrategy.apply(profileRequestContext);
      if (theMessage == null) {
         this.log.debug("{} No message was returned by lookup strategy", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else if (!(theMessage instanceof SAMLObject)) {
         this.log.debug("{} Message was not a SAML construct, nothing to do", this.getLogPrefix());
         return false;
      } else {
         this.message = (SAMLObject)theMessage;
         SecurityParametersContext paramsCtx = (SecurityParametersContext)this.securityParamsLookupStrategy.apply(profileRequestContext);
         if (paramsCtx != null && paramsCtx.getDecryptionParameters() != null) {
            DecryptionParameters params = paramsCtx.getDecryptionParameters();
            this.decrypter = new Decrypter(params.getDataKeyInfoCredentialResolver(), params.getKEKKeyInfoCredentialResolver(), params.getEncryptedKeyResolver());
         } else {
            this.log.debug("{} No security parameter context or decryption parameters", this.getLogPrefix());
         }

         return super.doPreExecute(profileRequestContext);
      }
   }
}
