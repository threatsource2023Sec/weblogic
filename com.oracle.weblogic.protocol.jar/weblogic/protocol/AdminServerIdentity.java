package weblogic.protocol;

import java.security.AccessController;
import weblogic.management.internal.AbstractAdminServerIdentity;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class AdminServerIdentity extends AbstractAdminServerIdentity {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static ServerIdentity getIdentity() {
      return ManagementService.getRuntimeAccess(kernelId).isAdminServer() ? LocalServerIdentity.getIdentity() : ServerIdentityManager.findServerIdentity(ManagementService.getRuntimeAccess(kernelId).getDomainName(), ManagementService.getRuntimeAccess(kernelId).getAdminServerName());
   }

   public static ServerIdentity getBootstrapIdentity() {
      return ManagementService.getRuntimeAccess(kernelId).isAdminServer() ? LocalServerIdentity.getIdentity() : adminIdentity;
   }
}
