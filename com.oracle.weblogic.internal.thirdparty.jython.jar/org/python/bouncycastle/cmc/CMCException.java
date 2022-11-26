package org.python.bouncycastle.cmc;

public class CMCException extends Exception {
   private final Throwable cause;

   public CMCException(String var1) {
      this(var1, (Throwable)null);
   }

   public CMCException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
