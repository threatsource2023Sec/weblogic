package org.opensaml.security.x509.impl;

import java.util.Collection;
import javax.annotation.Nullable;
import org.opensaml.security.x509.PKIXValidationInformation;

public class BasicPKIXValidationInformation implements PKIXValidationInformation {
   private final Collection trustAnchors;
   private final Collection trustedCRLs;
   private final Integer verificationDepth;

   public BasicPKIXValidationInformation(@Nullable Collection anchors, @Nullable Collection crls, @Nullable Integer depth) {
      this.verificationDepth = depth;
      this.trustAnchors = anchors;
      this.trustedCRLs = crls;
   }

   @Nullable
   public Collection getCRLs() {
      return this.trustedCRLs;
   }

   @Nullable
   public Collection getCertificates() {
      return this.trustAnchors;
   }

   @Nullable
   public Integer getVerificationDepth() {
      return this.verificationDepth;
   }
}
