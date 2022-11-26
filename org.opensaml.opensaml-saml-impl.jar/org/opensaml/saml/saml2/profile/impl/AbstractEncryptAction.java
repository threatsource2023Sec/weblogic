package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.FunctionSupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.saml2.encryption.Encrypter;
import org.opensaml.saml.saml2.encryption.Encrypter.KeyPlacement;
import org.opensaml.saml.saml2.profile.context.EncryptionContext;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.encryption.support.DataEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.KeyEncryptionParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEncryptAction extends AbstractConditionalProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractEncryptAction.class);
   @Nonnull
   private Function encryptionCtxLookupStrategy = Functions.compose(new ChildContextLookup(EncryptionContext.class), new OutboundMessageContextLookup());
   @Nullable
   private Function recipientLookupStrategy;
   @Nonnull
   private Function keyPlacementLookupStrategy;
   @Nonnull
   private Predicate encryptToSelf;
   @Nullable
   private Function encryptToSelfParametersStrategy;
   @Nullable
   private Function selfRecipientLookupStrategy;
   @Nullable
   private Encrypter encrypter;

   public AbstractEncryptAction() {
      this.keyPlacementLookupStrategy = FunctionSupport.constant(KeyPlacement.INLINE);
      this.encryptToSelf = Predicates.alwaysFalse();
   }

   public void setEncryptionContextLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.encryptionCtxLookupStrategy = (Function)Constraint.isNotNull(strategy, "EncryptionContext lookup strategy cannot be null");
   }

   public void setRecipientLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.recipientLookupStrategy = (Function)Constraint.isNotNull(strategy, "Recipient lookup strategy cannot be null");
   }

   public void setKeyPlacementLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.keyPlacementLookupStrategy = (Function)Constraint.isNotNull(strategy, "Key placement lookup strategy cannot be null");
   }

   public void setEncryptToSelf(@Nonnull Predicate predicate) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.encryptToSelf = (Predicate)Constraint.isNotNull(predicate, "Encrypt-to-self predicate cannot be null");
   }

   public void setEncryptToSelfParametersStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.encryptToSelfParametersStrategy = strategy;
   }

   public void setSelfRecipientLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.selfRecipientLookupStrategy = strategy;
   }

   @Nullable
   public Encrypter getEncrypter() {
      return this.encrypter;
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      if (!super.doPreExecute(profileRequestContext)) {
         return false;
      } else {
         EncryptionParameters params = this.getApplicableParameters((EncryptionContext)this.encryptionCtxLookupStrategy.apply(profileRequestContext));
         if (params == null) {
            this.log.debug("{} No encryption parameters, nothing to do", this.getLogPrefix());
            return false;
         } else {
            String recipient = this.recipientLookupStrategy != null ? (String)this.recipientLookupStrategy.apply(profileRequestContext) : null;
            DataEncryptionParameters dataParams = new DataEncryptionParameters(params);
            List keyParams = new ArrayList();
            keyParams.add(new KeyEncryptionParameters(params, recipient));
            if (this.encryptToSelf.apply(profileRequestContext)) {
               this.log.debug("{} Encryption to self was indicated", this.getLogPrefix());
               String selfRecipient = null;
               if (this.selfRecipientLookupStrategy != null) {
                  selfRecipient = (String)this.selfRecipientLookupStrategy.apply(profileRequestContext);
                  this.log.debug("{} Resolved self-encryption recipient value: {}", this.getLogPrefix(), selfRecipient);
               }

               if (this.encryptToSelfParametersStrategy == null) {
                  this.log.error("{} Self-encryption was indicated, but no parameters strategy was supplied", this.getLogPrefix());
                  ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
                  return false;
               }

               List selfParams = (List)this.encryptToSelfParametersStrategy.apply(new Pair(profileRequestContext, params));
               if (selfParams == null || selfParams.isEmpty()) {
                  this.log.error("{} Self-encryption self was indicated, but no parameters were resolved", this.getLogPrefix());
                  ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
                  return false;
               }

               this.log.debug("{} Saw {} self-encryption parameters", this.getLogPrefix(), selfParams.size());
               Iterator var8 = selfParams.iterator();

               while(var8.hasNext()) {
                  EncryptionParameters selfParam = (EncryptionParameters)var8.next();
                  keyParams.add(new KeyEncryptionParameters(selfParam, selfRecipient));
               }
            }

            this.encrypter = new Encrypter(dataParams, keyParams);
            this.encrypter.setKeyPlacement((Encrypter.KeyPlacement)this.keyPlacementLookupStrategy.apply(profileRequestContext));
            return true;
         }
      }
   }

   @Nullable
   protected abstract EncryptionParameters getApplicableParameters(@Nullable EncryptionContext var1);
}
