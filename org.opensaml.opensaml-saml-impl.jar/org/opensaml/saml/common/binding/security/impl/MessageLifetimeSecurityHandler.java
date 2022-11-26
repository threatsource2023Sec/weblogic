package org.opensaml.saml.common.binding.security.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonNegative;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.SAMLMessageInfoContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageLifetimeSecurityHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(MessageLifetimeSecurityHandler.class);
   @Duration
   @NonNegative
   private long clockSkew = 180000L;
   @Duration
   @NonNegative
   private long messageLifetime = 180000L;
   private boolean requiredRule = true;

   @Duration
   @NonNegative
   public long getClockSkew() {
      return this.clockSkew;
   }

   @Duration
   public void setClockSkew(@Duration @NonNegative long skew) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.clockSkew = Constraint.isGreaterThanOrEqual(0L, skew, "Clock skew must be greater than or equal to 0");
   }

   @Duration
   @NonNegative
   public long getMessageLifetime() {
      return this.messageLifetime;
   }

   @Duration
   public synchronized void setMessageLifetime(@Duration @NonNegative long lifetime) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.messageLifetime = Constraint.isGreaterThanOrEqual(0L, lifetime, "Message lifetime must be greater than or equal to 0");
   }

   public boolean isRequiredRule() {
      return this.requiredRule;
   }

   public void setRequiredRule(boolean required) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requiredRule = required;
   }

   public void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SAMLMessageInfoContext msgInfoContext = (SAMLMessageInfoContext)messageContext.getSubcontext(SAMLMessageInfoContext.class, true);
      if (msgInfoContext.getMessageIssueInstant() == null) {
         if (this.requiredRule) {
            this.log.warn("{} Inbound SAML message issue instant not present in message context", this.getLogPrefix());
            throw new MessageHandlerException("Inbound SAML message issue instant not present in message context");
         }
      } else {
         DateTime issueInstant = msgInfoContext.getMessageIssueInstant();
         DateTime now = new DateTime(DateTimeZone.UTC);
         DateTime latestValid = now.plus(this.getClockSkew());
         DateTime expiration = issueInstant.plus(this.getClockSkew() + this.getMessageLifetime());
         if (issueInstant.isAfter(latestValid)) {
            this.log.warn("{} Message was not yet valid: message time was {}, latest valid is: {}", new Object[]{this.getLogPrefix(), issueInstant, latestValid});
            throw new MessageHandlerException("Message was rejected because it was issued in the future");
         } else if (expiration.isBefore(now)) {
            this.log.warn("{} Message was expired: message time was '{}', message expired at: '{}', current time: '{}'", new Object[]{this.getLogPrefix(), issueInstant, expiration, now});
            throw new MessageHandlerException("Message was rejected due to issue instant expiration");
         }
      }
   }
}
