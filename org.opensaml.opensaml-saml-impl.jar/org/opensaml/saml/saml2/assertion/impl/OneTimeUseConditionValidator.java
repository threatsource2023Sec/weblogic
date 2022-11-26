package org.opensaml.saml.saml2.assertion.impl;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.ConditionValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.OneTimeUse;
import org.opensaml.storage.ReplayCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class OneTimeUseConditionValidator implements ConditionValidator {
   public static final String CACHE_CONTEXT = OneTimeUseConditionValidator.class.getName();
   public static final Long DEFAULT_CACHE_EXPIRES = 28800000L;
   private Logger log = LoggerFactory.getLogger(OneTimeUseConditionValidator.class);
   private ReplayCache replayCache;
   private Long replayCacheExpires;

   public OneTimeUseConditionValidator(@Nonnull ReplayCache replay, @Nullable Long expires) {
      this.replayCache = (ReplayCache)Constraint.isNotNull(replay, "Replay cache was null");
      this.replayCacheExpires = expires;
      if (this.replayCacheExpires == null) {
         this.replayCacheExpires = DEFAULT_CACHE_EXPIRES;
      } else if (this.replayCacheExpires < 0L) {
         this.log.warn("Supplied value for replay cache expires '{}' was negative, using default expiration", this.replayCacheExpires);
         this.replayCacheExpires = DEFAULT_CACHE_EXPIRES;
      }

   }

   @Nonnull
   public QName getServicedCondition() {
      return OneTimeUse.DEFAULT_ELEMENT_NAME;
   }

   @Nonnull
   public ValidationResult validate(@Nonnull Condition condition, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      if (!(condition instanceof OneTimeUse) && !Objects.equals(condition.getElementQName(), this.getServicedCondition())) {
         this.log.warn("Condition '{}' of type '{}' in assertion '{}' was not an '{}' condition.  Unable to process.", new Object[]{condition.getElementQName(), condition.getSchemaType(), assertion.getID(), this.getServicedCondition()});
         return ValidationResult.INDETERMINATE;
      } else if (!this.replayCache.check(CACHE_CONTEXT, this.getCacheValue(assertion), this.getExpires(assertion, context))) {
         context.setValidationFailureMessage(String.format("Assertion '%s' has a one time use condition and has been used before", assertion.getID()));
         return ValidationResult.INVALID;
      } else {
         return ValidationResult.VALID;
      }
   }

   @Nonnull
   protected Long getReplayCacheExpires() {
      return this.replayCacheExpires;
   }

   protected long getExpires(Assertion assertion, ValidationContext context) {
      Long expires = null;

      try {
         expires = (Long)context.getStaticParameters().get("saml2.Conditions.OneTimeUseExpires");
      } catch (ClassCastException var6) {
         this.log.warn("Value of param was not a Long: {}", "saml2.Conditions.OneTimeUseExpires");
      }

      this.log.debug("Saw one-time use cache expires context param: {}", expires);
      if (expires == null) {
         expires = this.getReplayCacheExpires();
      } else if (expires < 0L) {
         this.log.warn("Supplied context param for replay cache expires '{}' was negative, using configured expiration", expires);
         expires = this.getReplayCacheExpires();
      }

      this.log.debug("Effective one-time use cache expires of: {}", expires);
      long computedExpiration = System.currentTimeMillis() + expires;
      this.log.debug("Computed one-time use cache effective expiration time of: {}", computedExpiration);
      return computedExpiration;
   }

   @Nonnull
   protected String getCacheValue(@Nonnull Assertion assertion) throws AssertionValidationException {
      String issuer = null;
      if (assertion.getIssuer() != null && assertion.getIssuer().getValue() != null) {
         issuer = StringSupport.trimOrNull(assertion.getIssuer().getValue());
      }

      if (issuer == null) {
         issuer = "NoIssuer";
      }

      String id = StringSupport.trimOrNull(assertion.getID());
      if (id == null) {
         id = "NoID";
      }

      String value = String.format("%s--%s", issuer, id);
      this.log.debug("Generated one-time use cache value of: {}", value);
      return value;
   }
}
