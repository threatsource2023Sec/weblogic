package weblogic.diagnostics.lifecycle;

import com.bea.logging.LoggingService;
import java.security.AccessController;
import weblogic.diagnostics.debug.ServerDebugService;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DebugLifecycleImpl implements DiagnosticComponentLifecycle {
   private static DebugLifecycleImpl singleton = new DebugLifecycleImpl();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int status = 4;

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return this.status;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      ServerMBean config = ManagementService.getRuntimeAccess(kernelId).getServer();

      try {
         ServerDebugService.getInstance().initializeServerDebug(LoggingService.getInstance().getDebugDelegateLogger());
      } catch (Exception var3) {
         throw new DiagnosticComponentLifecycleException(var3);
      }

      DiagnosticsLogger.logServerDebugInitialized();
      this.status = 1;
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }
}
