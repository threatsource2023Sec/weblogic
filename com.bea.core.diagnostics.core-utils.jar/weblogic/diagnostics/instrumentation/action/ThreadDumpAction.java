package weblogic.diagnostics.instrumentation.action;

import java.security.AccessController;
import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.EventQueue;
import weblogic.diagnostics.instrumentation.InstrumentationEvent;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JVMRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class ThreadDumpAction extends AbstractDiagnosticAction implements StatelessDiagnosticAction {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ServerRuntimeMBean serverRuntime;

   public ThreadDumpAction() {
      this.setType("ThreadDumpAction");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public void process(JoinPoint jp) {
      InstrumentationEvent event = this.createInstrumentationEvent(jp, false);
      if (event != null) {
         String threadDump = null;

         try {
            if (this.serverRuntime == null) {
               this.serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
               if (this.serverRuntime != null) {
                  JVMRuntimeMBean jvmRuntime = this.serverRuntime.getJVMRuntime();
                  if (jvmRuntime != null) {
                     threadDump = jvmRuntime.getThreadStackDump();
                  }
               }
            }

            if (threadDump == null) {
               threadDump = "UNAVAILABLE";
            }

            event.setPayload(threadDump);
            EventQueue.getInstance().enqueue(event);
         } catch (Exception var5) {
         }
      }

   }
}
