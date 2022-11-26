package com.oracle.weblogic.lifecycle.provisioning.api;

public class DocumentLocatorException extends ResourceLocatorException {
   private static final long serialVersionUID = 1L;

   public DocumentLocatorException() {
      this((String)null, (Throwable)null);
   }

   public DocumentLocatorException(Throwable cause) {
      this((String)null, cause);
   }

   public DocumentLocatorException(String message) {
      this(message, (Throwable)null);
   }

   public DocumentLocatorException(String message, Throwable cause) {
      super(message, cause);
   }
}
