package org.opensaml.saml.saml2.assertion;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Statement;

@ThreadSafe
public interface StatementValidator {
   @Nonnull
   QName getServicedStatement();

   @Nonnull
   ValidationResult validate(@Nonnull Statement var1, @Nonnull Assertion var2, @Nonnull ValidationContext var3) throws AssertionValidationException;
}
