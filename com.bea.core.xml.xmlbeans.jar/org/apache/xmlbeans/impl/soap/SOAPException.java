package org.apache.xmlbeans.impl.soap;

public class SOAPException extends Exception {
   private Throwable cause;

   public SOAPException() {
      this.cause = null;
   }

   public SOAPException(String reason) {
      super(reason);
      this.cause = null;
   }

   public SOAPException(String reason, Throwable cause) {
      super(reason);
      this.initCause(cause);
   }

   public SOAPException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public String getMessage() {
      String s = super.getMessage();
      return s == null && this.cause != null ? this.cause.getMessage() : s;
   }

   public Throwable getCause() {
      return this.cause;
   }

   public synchronized Throwable initCause(Throwable cause) {
      if (this.cause != null) {
         throw new IllegalStateException("Can't override cause");
      } else if (cause == this) {
         throw new IllegalArgumentException("Self-causation not permitted");
      } else {
         this.cause = cause;
         return this;
      }
   }
}
