package com.oracle.pitchfork.interfaces;

public class MetadataParseException extends RuntimeException {
   private Throwable cause;
   static final long serialVersionUID = -2644378898775642534L;

   public MetadataParseException(String msg) {
      super(msg);
   }

   public MetadataParseException(String msg, Throwable cause) {
      super(msg);
      this.cause = cause;
   }

   public synchronized Throwable getCause() {
      return this.cause == null ? null : this.cause;
   }

   public String getMessage() {
      return super.getMessage();
   }
}
