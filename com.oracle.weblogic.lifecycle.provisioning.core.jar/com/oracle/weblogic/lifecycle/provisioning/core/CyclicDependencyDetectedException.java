package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;

public class CyclicDependencyDetectedException extends ProvisioningException {
   private static final long serialVersionUID = 1L;

   public CyclicDependencyDetectedException() {
   }

   public CyclicDependencyDetectedException(String message) {
      super(message);
   }

   public CyclicDependencyDetectedException(String message, Throwable cause) {
      super(message, cause);
   }

   public CyclicDependencyDetectedException(Throwable cause) {
      super(cause);
   }
}
