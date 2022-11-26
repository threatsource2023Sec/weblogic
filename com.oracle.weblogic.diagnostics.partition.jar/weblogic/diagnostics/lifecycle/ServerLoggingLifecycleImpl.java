package weblogic.diagnostics.lifecycle;

import java.security.AccessController;
import weblogic.logging.ServerLoggingInitializer;
import weblogic.management.ManagementException;
import weblogic.management.logging.LogBroadcaster;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ServerLoggingLifecycleImpl implements DiagnosticComponentLifecycle {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ServerLoggingLifecycleImpl singleton = new ServerLoggingLifecycleImpl();
   private int status = 4;

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return this.status;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      try {
         ServerLoggingInitializer.initializeServerLogging();
         LogBroadcaster.getLogBroadcaster();
         ServerLoggingInitializer.initializeDomainLogging();
      } catch (ManagementException var2) {
         throw new DiagnosticComponentLifecycleException(var2);
      }

      this.status = 1;
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }
}
