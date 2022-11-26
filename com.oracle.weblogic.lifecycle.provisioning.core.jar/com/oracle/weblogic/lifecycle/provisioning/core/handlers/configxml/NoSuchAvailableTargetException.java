package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;

public class NoSuchAvailableTargetException extends ProvisioningException {
   private static final long serialVersionUID = 1L;

   public NoSuchAvailableTargetException() {
   }

   public NoSuchAvailableTargetException(String message) {
      super(message);
   }
}
