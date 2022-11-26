package org.opensaml.saml.saml2.assertion.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.SAML20AssertionValidator;
import org.opensaml.saml.saml2.assertion.SubjectConfirmationValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public abstract class AbstractSubjectConfirmationValidator implements SubjectConfirmationValidator {
   private Logger log = LoggerFactory.getLogger(AbstractSubjectConfirmationValidator.class);

   @Nonnull
   public ValidationResult validate(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      if (confirmation.getSubjectConfirmationData() != null) {
         ValidationResult result = this.validateNotBefore(confirmation, assertion, context);
         if (result != ValidationResult.VALID) {
            return result;
         }

         result = this.validateNotOnOrAfter(confirmation, assertion, context);
         if (result != ValidationResult.VALID) {
            return result;
         }

         result = this.validateRecipient(confirmation, assertion, context);
         if (result != ValidationResult.VALID) {
            return result;
         }

         result = this.validateAddress(confirmation, assertion, context);
         if (result != ValidationResult.VALID) {
            return result;
         }
      }

      return this.doValidate(confirmation, assertion, context);
   }

   @Nonnull
   protected ValidationResult validateNotBefore(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      DateTime skewedNow = (new DateTime(ISOChronology.getInstanceUTC())).plus(SAML20AssertionValidator.getClockSkew(context));
      DateTime notBefore = confirmation.getSubjectConfirmationData().getNotBefore();
      this.log.debug("Evaluating SubjectConfirmationData NotBefore '{}' against 'skewed now' time '{}'", notBefore, skewedNow);
      if (notBefore != null && notBefore.isAfter(skewedNow)) {
         context.setValidationFailureMessage(String.format("Subject confirmation, in assertion '%s', with NotBefore condition of '%s' is not yet valid", assertion.getID(), notBefore));
         return ValidationResult.INVALID;
      } else {
         return ValidationResult.VALID;
      }
   }

   @Nonnull
   protected ValidationResult validateNotOnOrAfter(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      DateTime skewedNow = (new DateTime(ISOChronology.getInstanceUTC())).minus(SAML20AssertionValidator.getClockSkew(context));
      DateTime notOnOrAfter = confirmation.getSubjectConfirmationData().getNotOnOrAfter();
      this.log.debug("Evaluating SubjectConfirmationData NotOnOrAfter '{}' against 'skewed now' time '{}'", notOnOrAfter, skewedNow);
      if (notOnOrAfter != null && notOnOrAfter.isBefore(skewedNow)) {
         context.setValidationFailureMessage(String.format("Subject confirmation, in assertion '%s', with NotOnOrAfter condition of '%s' is no longer valid", assertion.getID(), notOnOrAfter));
         return ValidationResult.INVALID;
      } else {
         return ValidationResult.VALID;
      }
   }

   @Nonnull
   protected ValidationResult validateRecipient(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      String recipient = StringSupport.trimOrNull(confirmation.getSubjectConfirmationData().getRecipient());
      if (recipient == null) {
         return ValidationResult.VALID;
      } else {
         this.log.debug("Evaluating SubjectConfirmationData@Recipient of : {}", recipient);

         Set validRecipients;
         try {
            validRecipients = (Set)context.getStaticParameters().get("saml2.SubjectConfirmation.ValidRecipients");
         } catch (ClassCastException var7) {
            this.log.warn("The value of the static validation parameter '{}' was not java.util.Set<String>", "saml2.SubjectConfirmation.ValidRecipients");
            context.setValidationFailureMessage("Unable to determine list of valid subject confirmation recipient endpoints");
            return ValidationResult.INDETERMINATE;
         }

         if (validRecipients != null && !validRecipients.isEmpty()) {
            if (validRecipients.contains(recipient)) {
               this.log.debug("Matched valid recipient: {}", recipient);
               return ValidationResult.VALID;
            } else {
               this.log.debug("Failed to match SubjectConfirmationData@Recipient to any supplied valid recipients: {}", validRecipients);
               context.setValidationFailureMessage(String.format("Subject confirmation recipient for asertion '%s' did not match any valid recipients", assertion.getID()));
               return ValidationResult.INVALID;
            }
         } else {
            this.log.warn("Set of valid recipient URI's was not available from the validation context, unable to evaluate SubjectConfirmationData@Recipient");
            context.setValidationFailureMessage("Unable to determine list of valid subject confirmation recipient endpoints");
            return ValidationResult.INDETERMINATE;
         }
      }
   }

   @Nonnull
   protected ValidationResult validateAddress(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      String address = StringSupport.trimOrNull(confirmation.getSubjectConfirmationData().getAddress());
      if (address == null) {
         return ValidationResult.VALID;
      } else {
         this.log.debug("Evaluating SubjectConfirmationData@Address of : {}", address);

         InetAddress[] confirmingAddresses;
         try {
            confirmingAddresses = InetAddress.getAllByName(address);
         } catch (UnknownHostException var12) {
            this.log.warn("The subject confirmation address '{}' in assetion '{}' can not be resolved to a valid set of IP address(s)", address, assertion.getID());
            context.setValidationFailureMessage(String.format("Subject confirmation address '%s' is not resolvable hostname or IP address", address));
            return ValidationResult.INDETERMINATE;
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug("SubjectConfirmationData/@Address was resolved to addresses: {}", Arrays.asList(confirmingAddresses));
         }

         Set validAddresses;
         try {
            validAddresses = (Set)context.getStaticParameters().get("saml2.SubjectConfirmation.ValidAddresses");
         } catch (ClassCastException var11) {
            this.log.warn("The value of the static validation parameter '{}' was not java.util.Set<InetAddress>", "saml2.SubjectConfirmation.ValidAddresses");
            context.setValidationFailureMessage("Unable to determine list of valid subject confirmation addresses");
            return ValidationResult.INDETERMINATE;
         }

         if (validAddresses != null && !validAddresses.isEmpty()) {
            InetAddress[] var7 = confirmingAddresses;
            int var8 = confirmingAddresses.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               InetAddress confirmingAddress = var7[var9];
               if (validAddresses.contains(confirmingAddress)) {
                  this.log.debug("Matched SubjectConfirmationData address '{}' to valid address", confirmingAddress.getHostAddress());
                  return ValidationResult.VALID;
               }
            }

            this.log.debug("Failed to match SubjectConfirmationData@Address to any supplied valid addresses", validAddresses);
            context.setValidationFailureMessage(String.format("Subject confirmation address for asertion '%s' did not match any valid addresses", assertion.getID()));
            return ValidationResult.INVALID;
         } else {
            this.log.warn("Set of valid addresses was not available from the validation context, unable to evaluate SubjectConfirmationData@Address");
            context.setValidationFailureMessage("Unable to determine list of valid subject confirmation addresses");
            return ValidationResult.INDETERMINATE;
         }
      }
   }

   @Nonnull
   protected abstract ValidationResult doValidate(@Nonnull SubjectConfirmation var1, @Nonnull Assertion var2, @Nonnull ValidationContext var3) throws AssertionValidationException;
}
