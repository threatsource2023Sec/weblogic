package org.opensaml.security.x509;

import java.util.Collection;
import javax.annotation.Nullable;

public interface PKIXValidationInformation {
   @Nullable
   Integer getVerificationDepth();

   @Nullable
   Collection getCertificates();

   @Nullable
   Collection getCRLs();
}
