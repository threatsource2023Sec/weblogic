package org.python.bouncycastle.cert.crmf;

public class CRMFRuntimeException extends RuntimeException {
   private Throwable cause;

   public CRMFRuntimeException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
