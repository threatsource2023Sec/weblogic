package org.python.bouncycastle.cert.cmp;

public class CMPRuntimeException extends RuntimeException {
   private Throwable cause;

   public CMPRuntimeException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
