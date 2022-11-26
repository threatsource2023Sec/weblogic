package org.python.bouncycastle.cert.crmf;

public class CRMFException extends Exception {
   private Throwable cause;

   public CRMFException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
