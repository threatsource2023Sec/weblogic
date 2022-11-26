package weblogic.management.mbeanservers.internal;

import java.security.AccessController;
import weblogic.management.mbeanservers.Service;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DomainServiceImpl extends ServiceImpl {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public DomainServiceImpl(String name, String type, Service parent) {
      this(name, type, parent, true);
   }

   public DomainServiceImpl(String name, String type, Service parent, boolean register) {
      super(name, type, parent);
      if (register) {
         this.register();
      }

   }

   public void register() {
      ManagementService.getDomainAccess(kernelId).registerService(this);
   }

   public void unregister() {
      ManagementService.getDomainAccess(kernelId).unregisterService(this);
   }
}
