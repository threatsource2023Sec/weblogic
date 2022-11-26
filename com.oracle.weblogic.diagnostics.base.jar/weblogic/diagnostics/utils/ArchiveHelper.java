package weblogic.diagnostics.utils;

import java.security.AccessController;
import weblogic.diagnostics.archive.DiagnosticStoreRepository;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ArchiveHelper {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final RuntimeAccess runtimeAccess;

   public static boolean isFilestoreNeededAndNotAvailable() {
      ServerMBean serverConfig = runtimeAccess.getServer();
      WLDFServerDiagnosticMBean wldfConfig = serverConfig.getServerDiagnosticConfig();
      return "FileStoreArchive".equals(wldfConfig.getDiagnosticDataArchiveType()) && !DiagnosticStoreRepository.getInstance().isValid();
   }

   static {
      runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
   }
}
