package weblogic.management.runtime;

import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RegistrationManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DomainRuntimeMBeanDelegate extends RuntimeMBeanDelegate {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public DomainRuntimeMBeanDelegate(String nameArg) throws ManagementException {
      super(nameArg, ManagementService.getDomainAccess(kernelId).getDomainRuntime());
   }

   public DomainRuntimeMBeanDelegate(String nameArg, RuntimeMBean parent) throws ManagementException {
      super(nameArg, parent);
   }

   public DomainRuntimeMBeanDelegate(String nameArg, boolean registerNow) throws ManagementException {
      super(nameArg, ManagementService.getDomainAccess(kernelId).getDomainRuntime(), registerNow);
   }

   public DomainRuntimeMBeanDelegate() throws ManagementException {
      this(ManagementService.getRuntimeAccess(kernelId).getDomainName());
   }

   public DomainRuntimeMBeanDelegate(String nameArg, RuntimeMBean parent, boolean registerNow) throws ManagementException {
      super(nameArg, parent, registerNow);
   }

   public DomainRuntimeMBeanDelegate(String nameArg, RuntimeMBean parent, boolean registerNow, String parentAttribute) throws ManagementException {
      super(nameArg, parent, registerNow, parentAttribute);
   }

   public RegistrationManager getRegistrationManager() {
      return (RegistrationManager)(this.parent != null ? this.parent.getRegistrationManager() : ManagementService.getDomainAccess(kernelId));
   }
}
