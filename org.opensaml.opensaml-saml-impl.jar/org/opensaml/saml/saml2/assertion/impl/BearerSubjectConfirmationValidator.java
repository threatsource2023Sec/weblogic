package org.opensaml.saml.saml2.assertion.impl;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

@ThreadSafe
public class BearerSubjectConfirmationValidator extends AbstractSubjectConfirmationValidator {
   @Nonnull
   public String getServicedMethod() {
      return "urn:oasis:names:tc:SAML:2.0:cm:bearer";
   }

   @Nonnull
   protected ValidationResult doValidate(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      return Objects.equals(confirmation.getMethod(), "urn:oasis:names:tc:SAML:2.0:cm:bearer") ? ValidationResult.VALID : ValidationResult.INDETERMINATE;
   }
}
