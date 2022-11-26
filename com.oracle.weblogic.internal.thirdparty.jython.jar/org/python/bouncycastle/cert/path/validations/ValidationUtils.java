package org.python.bouncycastle.cert.path.validations;

import org.python.bouncycastle.cert.X509CertificateHolder;

class ValidationUtils {
   static boolean isSelfIssued(X509CertificateHolder var0) {
      return var0.getSubject().equals(var0.getIssuer());
   }
}
