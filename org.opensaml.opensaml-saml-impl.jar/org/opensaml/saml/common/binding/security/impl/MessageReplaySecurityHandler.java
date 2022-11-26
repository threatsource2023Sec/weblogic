package org.opensaml.saml.common.binding.security.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonNegative;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.SAMLMessageInfoContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.storage.ReplayCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReplaySecurityHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(MessageReplaySecurityHandler.class);
   @NonnullAfterInit
   private ReplayCache replayCache;
   private boolean requiredRule = true;
   @Duration
   @NonNegative
   private long expires = 180000L;

   @NonnullAfterInit
   public ReplayCache getReplayCache() {
      return this.replayCache;
   }

   public void setReplayCache(@Nonnull ReplayCache cache) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.replayCache = (ReplayCache)Constraint.isNotNull(cache, "ReplayCache cannot be null");
   }

   public void setRequiredRule(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requiredRule = flag;
   }

   @Duration
   @NonNegative
   public long getExpires() {
      return this.expires;
   }

   @Duration
   public void setExpires(@Duration @NonNegative long exp) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.expires = Constraint.isGreaterThanOrEqual(0L, exp, "Expiration must be greater than or equal to 0");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      Constraint.isNotNull(this.getReplayCache(), "ReplayCache cannot be null");
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SAMLPeerEntityContext peerContext = (SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class, true);
      String entityID = StringSupport.trimOrNull(peerContext.getEntityId());
      if (entityID == null) {
         entityID = "(unknown)";
      }

      SAMLMessageInfoContext msgInfoContext = (SAMLMessageInfoContext)messageContext.getSubcontext(SAMLMessageInfoContext.class, true);
      String messageId = StringSupport.trimOrNull(msgInfoContext.getMessageId());
      if (messageId == null) {
         if (this.requiredRule) {
            this.log.warn("{} Message contained no ID, replay check not possible", this.getLogPrefix());
            throw new MessageHandlerException("SAML message from issuer " + entityID + " did not contain an ID");
         } else {
            this.log.debug("{} Message contained no ID, rule is optional, skipping further processing", this.getLogPrefix());
         }
      } else {
         DateTime issueInstant = msgInfoContext.getMessageIssueInstant();
         if (issueInstant == null) {
            issueInstant = new DateTime();
         }

         this.log.debug("{} Evaluating message replay for message ID '{}', issue instant '{}', entityID '{}'", new Object[]{this.getLogPrefix(), messageId, issueInstant, entityID});
         if (!this.getReplayCache().check(this.getClass().getName(), messageId, issueInstant.getMillis() + this.expires)) {
            this.log.warn("{} Replay detected of message '{}' from issuer '{}'", new Object[]{this.getLogPrefix(), messageId, entityID});
            throw new MessageHandlerException("Rejecting replayed message ID '" + messageId + "' from issuer " + entityID);
         }
      }
   }
}
