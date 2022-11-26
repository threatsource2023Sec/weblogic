package com.oracle.weblogic.lifecycle.provisioning.api;

public class TransformationException extends ProvisioningException {
   private static final long serialVersionUID = 1L;

   public TransformationException() {
      this((String)null, (Throwable)null);
   }

   public TransformationException(Throwable cause) {
      this((String)null, cause);
   }

   public TransformationException(String message, Throwable cause) {
      super(message, cause);
   }
}
