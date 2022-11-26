package weblogic.diagnostics.lifecycle;

import java.security.AccessController;
import java.util.HashMap;
import weblogic.diagnostics.accessor.AccessRuntime;
import weblogic.diagnostics.accessor.AccessorConfigurationProvider;
import weblogic.diagnostics.accessor.AccessorEnvironment;
import weblogic.diagnostics.accessor.AccessorMBeanFactory;
import weblogic.diagnostics.accessor.AccessorSecurityProvider;
import weblogic.diagnostics.accessor.AccessorUtils;
import weblogic.diagnostics.accessor.WLSAccessorConfigurationProviderImpl;
import weblogic.diagnostics.accessor.WLSAccessorMBeanFactoryImpl;
import weblogic.diagnostics.accessor.WLSAccessorSecurityProviderImpl;
import weblogic.diagnostics.archive.DiagnosticStoreRepository;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.admin.RuntimeHandlerImpl;
import weblogic.t3.srvr.ServerRuntime;

public class ArchiveLifecycleImpl implements DiagnosticComponentLifecycle {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ArchiveLifecycleImpl singleton = new ArchiveLifecycleImpl();

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return 4;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      try {
         RuntimeMBean parent = ServerRuntime.theOne().getWLDFRuntime();
         AccessorMBeanFactory accessorMBeanFactory = new WLSAccessorMBeanFactoryImpl();
         AccessorConfigurationProvider confProvider = new WLSAccessorConfigurationProviderImpl(accessorMBeanFactory);
         AccessorSecurityProvider secProvider = new WLSAccessorSecurityProviderImpl();
         AccessorEnvironment accEnv = new AccessorEnvironment(confProvider, secProvider, accessorMBeanFactory);
         AccessRuntime.initialize(accEnv, parent);
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         String storeDir = AccessorUtils.getDiagnosticStoreDirectory();
         WLDFServerDiagnosticMBean wldf = server.getServerDiagnosticConfig();
         HashMap config = new HashMap();
         config.put("FileLockingEnabled", wldf.isDiagnosticStoreFileLockingEnabled());
         config.put("IoBufferSize", wldf.getDiagnosticStoreIoBufferSize());
         config.put("BlockSize", wldf.getDiagnosticStoreBlockSize());
         config.put("MaxFileSize", wldf.getDiagnosticStoreMaxFileSize());
         config.put("MinWindowBufferSize", wldf.getDiagnosticStoreMinWindowBufferSize());
         config.put("MaxWindowBufferSize", wldf.getDiagnosticStoreMaxWindowBufferSize());
         DiagnosticStoreRepository.getInstance().getStore(storeDir, config, KernelStatus.isServer() ? new RuntimeHandlerImpl() : null);
      } catch (Exception var10) {
         throw new DiagnosticComponentLifecycleException(var10);
      }
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
      try {
         DiagnosticStoreRepository.getInstance().close();
      } catch (Exception var2) {
         DiagnosticsLogger.logFailureToCloseDiagnosticStore(var2);
      }

   }
}
