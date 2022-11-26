package org.opensaml.saml.saml2.assertion.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.ConditionValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class AudienceRestrictionConditionValidator implements ConditionValidator {
   private Logger log = LoggerFactory.getLogger(AudienceRestrictionConditionValidator.class);

   @Nonnull
   public QName getServicedCondition() {
      return AudienceRestriction.DEFAULT_ELEMENT_NAME;
   }

   @Nonnull
   public ValidationResult validate(@Nonnull Condition condition, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      if (!(condition instanceof AudienceRestriction)) {
         this.log.warn("Condition '{}' of type '{}' in assertion '{}' was not an '{}' condition.  Unable to process.", new Object[]{condition.getElementQName(), condition.getSchemaType(), assertion.getID(), this.getServicedCondition()});
         return ValidationResult.INDETERMINATE;
      } else {
         Set validAudiences;
         try {
            validAudiences = (Set)context.getStaticParameters().get("saml2.Conditions.ValidAudiences");
         } catch (ClassCastException var10) {
            this.log.warn("The value of the static validation parameter '{}' was not java.util.Set<String>", "saml2.Conditions.ValidAudiences");
            context.setValidationFailureMessage("Unable to determine list of valid audiences");
            return ValidationResult.INDETERMINATE;
         }

         if (validAudiences != null && !validAudiences.isEmpty()) {
            this.log.debug("Evaluating the Assertion's AudienceRestriction/Audience values against the list of valid audiences: {}", validAudiences.toString());
            AudienceRestriction audienceRestriction = (AudienceRestriction)condition;
            List audiences = audienceRestriction.getAudiences();
            if (audiences != null && !audiences.isEmpty()) {
               Iterator var7 = audiences.iterator();

               String audienceURI;
               do {
                  if (!var7.hasNext()) {
                     String msg = String.format("None of the audiences within Assertion '%s' matched the list of valid audiances", assertion.getID());
                     this.log.debug(msg);
                     context.setValidationFailureMessage(msg);
                     return ValidationResult.INVALID;
                  }

                  Audience audience = (Audience)var7.next();
                  audienceURI = StringSupport.trimOrNull(audience.getAudienceURI());
               } while(!validAudiences.contains(audienceURI));

               this.log.debug("Matched valid audience: {}", audienceURI);
               return ValidationResult.VALID;
            } else {
               context.setValidationFailureMessage(String.format("'%s' condition in assertion '%s' is malformed as it does not contain any audiences", this.getServicedCondition(), assertion.getID()));
               return ValidationResult.INVALID;
            }
         } else {
            this.log.warn("Set of valid audiences was not available from the validation context, unable to evaluate AudienceRestriction Condition");
            context.setValidationFailureMessage("Unable to determine list of valid audiences");
            return ValidationResult.INDETERMINATE;
         }
      }
   }
}
