package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import java.util.Collection;
import java.util.Collections;

public class IllegalResourceGroupTargetingSpecificationException extends ProvisioningException {
   private static final long serialVersionUID = 1L;
   private final Collection targets;
   private final Boolean useDefaultTarget;

   public IllegalResourceGroupTargetingSpecificationException() {
      this.targets = null;
      this.useDefaultTarget = null;
   }

   public IllegalResourceGroupTargetingSpecificationException(Collection targets, Boolean useDefaultTarget) {
      if (targets == null) {
         this.targets = null;
      } else {
         this.targets = Collections.unmodifiableCollection(targets);
      }

      this.useDefaultTarget = useDefaultTarget;
   }

   public final Collection getTargets() {
      return this.targets;
   }

   public final Boolean getUseDefaultTarget() {
      return this.useDefaultTarget;
   }

   public final String getMessage() {
      return String.format("Targets: %s\nUse default target? %s", this.getTargets(), this.getUseDefaultTarget());
   }
}
