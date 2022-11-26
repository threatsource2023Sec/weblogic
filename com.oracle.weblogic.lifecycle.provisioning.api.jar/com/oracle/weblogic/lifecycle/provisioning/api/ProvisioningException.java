package com.oracle.weblogic.lifecycle.provisioning.api;

public class ProvisioningException extends Exception {
   private static final long serialVersionUID = 1L;

   public ProvisioningException() {
      this((String)null, (Throwable)null);
   }

   public ProvisioningException(Throwable cause) {
      super(cause);
   }

   public ProvisioningException(String message) {
      this(message, (Throwable)null);
   }

   public ProvisioningException(String message, Throwable cause) {
      super(message, cause);
   }
}
