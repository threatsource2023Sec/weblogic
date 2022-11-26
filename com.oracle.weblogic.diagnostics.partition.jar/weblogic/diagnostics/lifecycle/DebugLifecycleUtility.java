package weblogic.diagnostics.lifecycle;

import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

final class DebugLifecycleUtility {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticLifecycleHandlers");

   private DebugLifecycleUtility() {
   }

   static void debugHandlerStates(DiagnosticComponentLifecycle[] components) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Displaying server state and diagnostic component states:");
         debugLogger.debug("Server state: " + ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getState());

         for(int i = 0; i < components.length; ++i) {
            DiagnosticComponentLifecycle component = components[i];
            debugLogger.debug("Name: " + ComponentRegistry.getWLDFComponentName(component));
            debugLogger.debug("Status: " + DiagnosticLifecycleConstants.STATES[component.getStatus()]);
         }
      }

   }
}
