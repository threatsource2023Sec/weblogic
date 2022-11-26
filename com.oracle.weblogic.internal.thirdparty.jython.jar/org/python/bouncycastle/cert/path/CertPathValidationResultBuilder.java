package org.python.bouncycastle.cert.path;

class CertPathValidationResultBuilder {
   public CertPathValidationResult build() {
      return new CertPathValidationResult((CertPathValidationContext)null, 0, 0, (CertPathValidationException)null);
   }

   public void addException(CertPathValidationException var1) {
   }
}
