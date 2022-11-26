package weblogic.management.mbeanservers.internal;

import java.security.AccessController;
import weblogic.management.mbeanservers.Service;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RuntimeServiceImpl extends ServiceImpl {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public RuntimeServiceImpl(String name, String type, Service parent) {
      this(name, type, parent, (String)null, true);
   }

   public RuntimeServiceImpl(String name, String type, Service parent, boolean register) {
      this(name, type, parent, (String)null, true);
   }

   public RuntimeServiceImpl(String name, String type, Service parent, String parentAttribute, boolean register) {
      super(name, type, parent, parentAttribute);
      if (register) {
         this.register();
      }

   }

   public void register() {
      ManagementService.getRuntimeAccess(kernelId).registerService(this);
   }

   public void unregister() {
      ManagementService.getRuntimeAccess(kernelId).unregisterService(this);
   }
}
