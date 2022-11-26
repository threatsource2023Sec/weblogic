package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import javax.inject.Singleton;
import org.glassfish.hk2.extras.operation.OperationContext;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public final class ProvisioningOperationScopedContext extends OperationContext {
   public final Class getScope() {
      return ProvisioningOperationScoped.class;
   }
}
