package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.context.ECPContext;
import org.opensaml.saml.saml2.profile.context.EncryptionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopulateECPContext extends AbstractConditionalProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(PopulateECPContext.class);
   @Nonnull
   private Function ecpContextCreationStrategy = Functions.compose(new ChildContextLookup(ECPContext.class, true), new OutboundMessageContextLookup());
   @Nonnull
   private Function encryptionContextLookupStrategy = Functions.compose(new ChildContextLookup(EncryptionContext.class), new OutboundMessageContextLookup());
   @Nullable
   private SecureRandom randomGenerator;
   private boolean requireEncryption;

   public PopulateECPContext() throws NoSuchAlgorithmException {
      try {
         this.randomGenerator = SecureRandom.getInstance("SHA1PRNG");
      } catch (NoSuchAlgorithmException var2) {
         throw new RuntimeException("SHA1PRNG is required to be supported by the JVM but is not", var2);
      }

      this.requireEncryption = true;
   }

   public void setECPContextCreationStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.ecpContextCreationStrategy = (Function)Constraint.isNotNull(strategy, "ECPContext creation strategy cannot be null");
   }

   public void setEncryptionContextLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.encryptionContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "EncryptionContext lookup strategy cannot be null");
   }

   public void setRandomGenerator(@Nullable SecureRandom generator) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.randomGenerator = generator;
   }

   public void setRequireEncryption(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requireEncryption = flag;
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      ECPContext ecpContext = (ECPContext)this.ecpContextCreationStrategy.apply(profileRequestContext);
      if (ecpContext == null) {
         this.log.error("{} Error creating ECPContext", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
      } else {
         ecpContext.setRequestAuthenticated(SAMLBindingSupport.isMessageSigned(profileRequestContext.getInboundMessageContext()));
         this.log.debug("{} RequestAuthenticated: {}", this.getLogPrefix(), ecpContext.isRequestAuthenticated());
         boolean generateKey = true;
         if (this.requireEncryption) {
            generateKey = false;
            EncryptionContext encryptionCtx = (EncryptionContext)this.encryptionContextLookupStrategy.apply(profileRequestContext);
            if (encryptionCtx != null) {
               generateKey = encryptionCtx.getAssertionEncryptionParameters() != null;
            }
         }

         if (generateKey) {
            this.log.debug("{} Generating session key for use by ECP peers", this.getLogPrefix());
            byte[] key = new byte[32];
            this.randomGenerator.nextBytes(key);
            ecpContext.setSessionKey(key);
         } else {
            this.log.debug("{} Assertion encryption is not enabled, skipping session key generation", this.getLogPrefix());
            ecpContext.setSessionKey((byte[])null);
         }

      }
   }
}
