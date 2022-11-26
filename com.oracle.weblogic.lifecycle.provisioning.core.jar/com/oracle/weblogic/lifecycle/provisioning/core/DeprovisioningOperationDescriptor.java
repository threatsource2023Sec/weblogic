package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperationDescriptor;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.DeprovisioningOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

@DeprovisioningOperation
@Service
@Singleton
public class DeprovisioningOperationDescriptor extends ProvisioningOperationDescriptor {
   private static final long serialVersionUID = 1L;
   private final InitialProvisioningOperationDescriptor initialProvisioningOperationDescriptor;

   @Inject
   public DeprovisioningOperationDescriptor(InitialProvisioningOperationDescriptor initialProvisioningOperationDescriptor) {
      this.initialProvisioningOperationDescriptor = initialProvisioningOperationDescriptor;
   }

   public Set getProvisioningSequence(Set componentNames) throws ProvisioningException {
      if (this.initialProvisioningOperationDescriptor == null) {
         throw new ProvisioningException("this.initialProvisioningOperationDescriptor == null");
      } else {
         ArrayList list = new ArrayList(this.initialProvisioningOperationDescriptor.getProvisioningSequence(componentNames));
         Collections.reverse(list);
         return new LinkedHashSet(list);
      }
   }
}
