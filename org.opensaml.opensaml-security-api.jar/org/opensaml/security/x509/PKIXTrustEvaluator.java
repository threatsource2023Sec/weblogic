package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import org.opensaml.security.SecurityException;

public interface PKIXTrustEvaluator {
   boolean validate(@Nonnull PKIXValidationInformation var1, @Nonnull X509Credential var2) throws SecurityException;

   @Nonnull
   PKIXValidationOptions getPKIXValidationOptions();
}
