package org.python.bouncycastle.dvcs;

public class DVCSException extends Exception {
   private static final long serialVersionUID = 389345256020131488L;
   private Throwable cause;

   public DVCSException(String var1) {
      super(var1);
   }

   public DVCSException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
