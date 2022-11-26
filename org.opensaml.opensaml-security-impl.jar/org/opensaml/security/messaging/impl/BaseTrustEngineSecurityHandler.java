package org.opensaml.security.messaging.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.trust.TrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTrustEngineSecurityHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(BaseTrustEngineSecurityHandler.class);
   @Nullable
   private TrustEngine trustEngine;

   @Nullable
   protected TrustEngine getTrustEngine() {
      return this.trustEngine;
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         TrustEngine engine = this.resolveTrustEngine(messageContext);
         if (engine == null) {
            throw new MessageHandlerException("TrustEngine could not be resolved from MessageContext");
         } else {
            this.trustEngine = engine;
            return true;
         }
      }
   }

   @Nullable
   protected abstract TrustEngine resolveTrustEngine(@Nonnull MessageContext var1);

   @Nullable
   protected abstract CriteriaSet buildCriteriaSet(@Nullable String var1, @Nonnull MessageContext var2) throws MessageHandlerException;

   protected boolean evaluate(@Nonnull Object token, @Nullable String entityID, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      CriteriaSet criteriaSet = this.buildCriteriaSet(entityID, messageContext);
      if (criteriaSet == null) {
         this.log.error("{} Returned criteria set was null, can not perform trust engine evaluation of token", this.getLogPrefix());
         throw new MessageHandlerException("Returned criteria set was null");
      } else {
         return this.evaluate(token, criteriaSet);
      }
   }

   protected boolean evaluate(@Nonnull Object token, @Nullable CriteriaSet criteriaSet) throws MessageHandlerException {
      try {
         return this.getTrustEngine().validate(token, criteriaSet);
      } catch (SecurityException var4) {
         this.log.error("{} There was an error evaluating the request's token using the trust engine", this.getLogPrefix(), var4);
         throw new MessageHandlerException("Error during trust engine evaluation of the token", var4);
      }
   }
}
