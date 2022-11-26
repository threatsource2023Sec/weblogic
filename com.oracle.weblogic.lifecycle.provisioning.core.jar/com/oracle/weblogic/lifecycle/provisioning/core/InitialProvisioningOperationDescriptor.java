package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentRepository;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperationDescriptor;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.InitialProvisioningOperation;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

@InitialProvisioningOperation
@Service
@Singleton
public class InitialProvisioningOperationDescriptor extends ProvisioningOperationDescriptor {
   private static final long serialVersionUID = 1L;
   private transient Provider provisioningComponentRepositoryProvider;

   public InitialProvisioningOperationDescriptor() {
      this((Provider)null);
   }

   @Inject
   public InitialProvisioningOperationDescriptor(Provider provisioningComponentRepositoryProvider) {
      this.setProvisioningComponentRepositoryProvider(provisioningComponentRepositoryProvider);
   }

   public Provider getProvisioningComponentRepositoryProvider() {
      return this.provisioningComponentRepositoryProvider;
   }

   public void setProvisioningComponentRepositoryProvider(Provider provisioningComponentRepositoryProvider) {
      this.provisioningComponentRepositoryProvider = provisioningComponentRepositoryProvider;
   }

   public Set getProvisioningSequence(Set componentNames) throws ProvisioningException {
      Provider provisioningComponentRepositoryProvider = this.getProvisioningComponentRepositoryProvider();
      if (provisioningComponentRepositoryProvider == null) {
         throw new ProvisioningException("this.getProvisioningComponentRepositoryProvider() == null");
      } else {
         ProvisioningComponentRepository provisioningComponentRepository = (ProvisioningComponentRepository)provisioningComponentRepositoryProvider.get();
         if (provisioningComponentRepository == null) {
            throw new ProvisioningException("this.getProvisioningComponentRepositoryProvider().get() == null");
         } else {
            Set returnValue = new LinkedHashSet(provisioningComponentRepository.computeDependentAndAffiliatedProvisioningComponentNames(componentNames));
            return returnValue;
         }
      }
   }
}
