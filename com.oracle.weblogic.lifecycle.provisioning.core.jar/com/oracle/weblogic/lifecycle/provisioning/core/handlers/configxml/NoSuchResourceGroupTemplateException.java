package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;

public class NoSuchResourceGroupTemplateException extends ProvisioningException {
   private static final long serialVersionUID = 1L;

   public NoSuchResourceGroupTemplateException() {
   }

   public NoSuchResourceGroupTemplateException(String message) {
      super(message);
   }
}
