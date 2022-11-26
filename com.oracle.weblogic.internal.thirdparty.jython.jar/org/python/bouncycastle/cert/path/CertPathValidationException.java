package org.python.bouncycastle.cert.path;

public class CertPathValidationException extends Exception {
   private final Exception cause;

   public CertPathValidationException(String var1) {
      this(var1, (Exception)null);
   }

   public CertPathValidationException(String var1, Exception var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
