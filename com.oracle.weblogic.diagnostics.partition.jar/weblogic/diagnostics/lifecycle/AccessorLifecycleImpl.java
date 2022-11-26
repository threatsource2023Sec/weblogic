package weblogic.diagnostics.lifecycle;

import weblogic.diagnostics.accessor.DiagnosticAccessRuntime;
import weblogic.diagnostics.collections.IteratorCollector;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.management.ManagementException;
import weblogic.t3.srvr.ServerRuntime;

public class AccessorLifecycleImpl implements DiagnosticComponentLifecycle {
   private static AccessorLifecycleImpl singleton = new AccessorLifecycleImpl();

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return 4;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      try {
         ServerRuntime.theOne().getWLDFRuntime().setWLDFAccessRuntime(DiagnosticAccessRuntime.getInstance());
         IteratorCollector.getInstance().initialize();

         try {
            DiagnosticAccessRuntime.getInstance().getWLDFDataAccessRuntimes();
         } catch (Exception var2) {
            DiagnosticsLogger.logAccessorInstantiationError(var2);
         }

         DiagnosticsLogger.logInitializedAccessor();
      } catch (ManagementException var3) {
         DiagnosticsLogger.logAccessorInitializationError(var3);
         throw new DiagnosticComponentLifecycleException(var3);
      }
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }
}
