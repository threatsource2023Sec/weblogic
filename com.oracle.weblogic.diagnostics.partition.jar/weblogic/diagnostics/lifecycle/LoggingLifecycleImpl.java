package weblogic.diagnostics.lifecycle;

import java.security.AccessController;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.gathering.DataGatheringManager;
import weblogic.logging.DomainLogBroadcasterClient;
import weblogic.management.ManagementException;
import weblogic.management.configuration.LogMBean;
import weblogic.management.logging.DomainLogHandlerException;
import weblogic.management.logging.DomainLogHandlerImpl;
import weblogic.management.logging.LogRuntime;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.protocol.ConnectMonitorFactory;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.ConnectListener;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class LoggingLifecycleImpl implements DiagnosticComponentLifecycle {
   private static LoggingLifecycleImpl singleton = new LoggingLifecycleImpl();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int status = 4;
   private DomainLogBroadcasterClient client = null;

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return this.status;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      try {
         RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
         if (runtime.isAdminServer()) {
            DomainLogHandlerImpl domainLogHandler = (DomainLogHandlerImpl)DomainLogHandlerImpl.getInstance();
            DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
            LogMBean log = runtime.getDomain().getLog();
            LogRuntime logRuntime = new LogRuntime(log, domainRuntime);
            domainRuntime.setLogRuntime(logRuntime);
         }
      } catch (DomainLogHandlerException var7) {
         DiagnosticsLogger.logErrorCreatingDomainLogHandler(var7);
      } catch (ManagementException var8) {
         throw new DiagnosticComponentLifecycleException(var8.toString());
      }

      try {
         this.client = DomainLogBroadcasterClient.getInstance();
         this.initDomainLogHandler(false);
         ConnectMonitorFactory.getConnectMonitor().addConnectListener(new ConnectListener() {
            public void onConnect(ConnectEvent event) {
               if (event.getServerName().equals(ManagementService.getRuntimeAccess(LoggingLifecycleImpl.kernelId).getAdminServerName())) {
                  if (LoggingLifecycleImpl.this.client != null) {
                     LoggingLifecycleImpl.this.initDomainLogHandler(true);
                  }
               }
            }
         });
      } catch (Exception var6) {
         throw new DiagnosticComponentLifecycleException(var6.toString());
      }

      DataGatheringManager.initializeLogging();
      this.status = 1;
   }

   private void initDomainLogHandler(boolean pingFirst) {
      this.client.initDomainLogHandler(pingFirst);
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
      DomainLogBroadcasterClient client = DomainLogBroadcasterClient.getInstance();
      client.flush();
      client.close();
   }
}
