package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;

public class NoSuchPartitionException extends ProvisioningException {
   private static final long serialVersionUID = 1L;

   public NoSuchPartitionException() {
   }

   public NoSuchPartitionException(String message) {
      super(message);
   }
}
