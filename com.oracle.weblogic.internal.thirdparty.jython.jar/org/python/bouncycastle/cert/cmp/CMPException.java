package org.python.bouncycastle.cert.cmp;

public class CMPException extends Exception {
   private Throwable cause;

   public CMPException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public CMPException(String var1) {
      super(var1);
   }

   public Throwable getCause() {
      return this.cause;
   }
}
