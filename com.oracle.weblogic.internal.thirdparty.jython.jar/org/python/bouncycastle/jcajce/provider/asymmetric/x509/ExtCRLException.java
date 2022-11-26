package org.python.bouncycastle.jcajce.provider.asymmetric.x509;

import java.security.cert.CRLException;

class ExtCRLException extends CRLException {
   Throwable cause;

   ExtCRLException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
