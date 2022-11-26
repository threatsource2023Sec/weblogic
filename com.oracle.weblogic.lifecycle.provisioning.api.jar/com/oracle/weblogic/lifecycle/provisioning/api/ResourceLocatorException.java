package com.oracle.weblogic.lifecycle.provisioning.api;

public class ResourceLocatorException extends ProvisioningException {
   private static final long serialVersionUID = 1L;

   public ResourceLocatorException() {
      this((String)null, (Throwable)null);
   }

   public ResourceLocatorException(Throwable cause) {
      this((String)null, cause);
   }

   public ResourceLocatorException(String message) {
      this(message, (Throwable)null);
   }

   public ResourceLocatorException(String message, Throwable cause) {
      super(message, cause);
   }
}
