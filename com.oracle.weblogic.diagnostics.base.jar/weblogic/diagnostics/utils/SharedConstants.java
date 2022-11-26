package weblogic.diagnostics.utils;

import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class SharedConstants {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String DOMAIN_NAME;
   public static final String SERVER_NAME;

   static {
      DOMAIN_NAME = ManagementService.getRuntimeAccess(KERNEL_ID) != null ? ManagementService.getRuntimeAccess(KERNEL_ID).getDomainName() : null;
      SERVER_NAME = ManagementService.getRuntimeAccess(KERNEL_ID) != null ? ManagementService.getRuntimeAccess(KERNEL_ID).getServerName() : null;
   }
}
