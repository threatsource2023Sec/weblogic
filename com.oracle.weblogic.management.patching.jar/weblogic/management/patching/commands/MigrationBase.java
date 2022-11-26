package weblogic.management.patching.commands;

import java.lang.annotation.Annotation;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.cluster.singleton.ServerMigrationCoordinator;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.RolloutEditSession;
import weblogic.management.patching.model.JMSInfo;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;
import weblogic.management.runtime.MigrationTaskRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public abstract class MigrationBase extends AbstractCommand {
   private static final long serialVersionUID = -8381435508814656156L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String JNDI_NAME = "weblogic/cluster/singleton/ServerMigrationCoordinator";
   @SharedState
   public transient String serverName;

   public void initialize(WorkflowContext workFlowContext) {
      super.initialize(workFlowContext);
   }

   public boolean migrateJMS(JMSInfo jmsInfo, String destination, boolean failback) throws CommandException {
      boolean result = false;
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Server = " + this.serverName + " JMS Info =  " + jmsInfo);
      }

      Iterator var5 = jmsInfo.getMTList().entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry entry = (Map.Entry)var5.next();
         if (!failback) {
            if (destination == null) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().nullDestination());
            }
         } else {
            destination = jmsInfo.getUPS((String)entry.getKey());
         }

         String namedEditSession = null;
         RolloutEditSession editSession = null;
         DomainMBean domain = null;

         try {
            editSession = RolloutEditSession.getInstance();
            namedEditSession = editSession.createEditSession();
            domain = editSession.startEdit();
            MigratableServiceCoordinatorRuntimeMBean migratableServiceCoordinator = ManagementService.getDomainAccess(kernelId).getMigratableServiceCoordinatorRuntime();
            DomainMBean dom = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService().getDomainConfiguration();
            MigratableTargetMBean mt = dom.lookupMigratableTarget((String)entry.getKey());
            ServerMBean currentMBean = mt.getHostingServer();
            String current = null;
            if (currentMBean != null) {
               current = currentMBean.getName();
            }

            if (current != null && !destination.equals(current)) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  if (!failback) {
                     PatchingDebugLogger.debug("Migrating MT " + (String)entry.getKey() + " from " + this.serverName + " to destination " + destination);
                  } else {
                     PatchingDebugLogger.debug("Failing back MT " + (String)entry.getKey() + " from " + current + " to original hosting server " + destination);
                  }
               }

               migratableServiceCoordinator.migrate(mt, domain.lookupServer(destination), false, false);
               editSession.activate();
               if (PatchingDebugLogger.isDebugEnabled()) {
                  ServerMBean c3 = mt.getHostingServer();
                  PatchingDebugLogger.debug("Migration of MT " + (String)entry.getKey() + " from " + current + " to destination " + destination + " succeeded and changes were activated. Current server after activation = " + (c3 != null ? c3.getName() : "null"));
               }
            }

            result = true;
         } catch (Exception var22) {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().getMigrationFailed((String)entry.getKey(), var22));
         } finally {
            try {
               if (!result) {
                  editSession.cancelEdit();
               }

               if (namedEditSession != null) {
                  editSession.endEdit(namedEditSession);
               }
            } catch (Exception var23) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Exception while destroying edit session " + namedEditSession + ": " + var23.getMessage());
               }
            }

         }
      }

      return result;
   }

   public boolean migrateJTA(String destination) throws CommandException {
      boolean result = false;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = runtimeAccess.getDomain();
      ServerLifeCycleRuntimeMBean st = ManagementService.getDomainAccess(kernelId).lookupServerLifecycleRuntime(this.serverName);
      this.ensureShutdownState();

      try {
         MigratableServiceCoordinatorRuntimeMBean migratableServiceCoordinator = ManagementService.getDomainAccess(kernelId).getMigratableServiceCoordinatorRuntime();
         JTAMigratableTargetMBean jtaTarget = domain.lookupServer(this.serverName).getJTAMigratableTarget();
         MigrationTaskRuntimeMBean taskMBean = migratableServiceCoordinator.startMigrateTask(jtaTarget, domain.lookupServer(destination), true);
         this.waitForCompletion(taskMBean);
         if (taskMBean.getStatusCode() != 1) {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().getJTSMigrationFailed(String.valueOf(taskMBean.getStatusCode())));
         } else {
            result = true;
            return result;
         }
      } catch (CommandException var9) {
         throw var9;
      } catch (Exception var10) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getJTSMigrationError(var10));
      }
   }

   protected String getCurrentMachine() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = runtimeAccess.getDomain();
      ServerMBean serverMBean = domain.lookupServer(this.serverName);
      ServerRuntimeMBean serverRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService().lookupServerRuntime(serverMBean.getName());
      return serverRuntime != null ? serverRuntime.getCurrentMachine() : serverMBean.getMachine().getName();
   }

   public void migrateServerToAdminMode(String source, String destination, boolean failback) throws CommandException {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = runtimeAccess.getDomain();
      domain.lookupServer(this.serverName);
      InitialContext ctx = null;

      CommandException ce;
      try {
         ctx = new InitialContext();
         ServerMigrationCoordinator serverMigrationCoordinator = (ServerMigrationCoordinator)ctx.lookup("weblogic/cluster/singleton/ServerMigrationCoordinator");
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Invoking " + (failback ? " fail back " : "") + "server migration from " + source + " to destination " + destination);
         }

         serverMigrationCoordinator.migrate(this.serverName, source, destination, false, false, "ADMIN");
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Done invoking server migration for " + this.serverName + " from " + source + " to destination " + destination);
         }

      } catch (NamingException var10) {
         ce = new CommandException(PatchingMessageTextFormatter.getInstance().getWSMJNDIFailed("weblogic/cluster/singleton/ServerMigrationCoordinator"));
         ce.initCause(var10);
         throw ce;
      } catch (Exception var11) {
         ce = new CommandException(PatchingMessageTextFormatter.getInstance().getServerMigrationError(var11));
         ce.initCause(var11);
         throw ce;
      }
   }

   private void ensureShutdownState() throws CommandException {
      long timeout = 15000L;
      long interval = 1000L;
      final ServerLifeCycleRuntimeMBean slcrm = ManagementService.getDomainAccess(kernelId).lookupServerLifecycleRuntime(this.serverName);
      StatusPoller serverShutdownStatePoller = new StatusPoller() {
         public boolean checkStatus() {
            String st = slcrm.getState();
            if (!st.equals("SHUTDOWN") && !st.equals("FAILED_NOT_RESTARTABLE")) {
               return false;
            } else if (MigrationBase.this.getServerAdminURL(MigrationBase.this.serverName) != null) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("StatusPoller: Server " + MigrationBase.this.serverName + " endpoint exists even after " + st);
               }

               return false;
            } else {
               return true;
            }
         }

         public String getPollingDescription() {
            return "ServerShutdownState";
         }
      };
      if ((new TimedStatusPoller()).pollStatusWithTimeout(timeout, interval, serverShutdownStatePoller)) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getServerShutdownStateTimeout(this.serverName, slcrm.getState(), timeout));
      }
   }

   private String getServerAdminURL(String name) {
      try {
         return ((URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0])).findAdministrationURL(name);
      } catch (UnknownHostException var3) {
         return null;
      }
   }

   public void waitForCompletion(MigrationTaskRuntimeMBean taskBean) {
      if (taskBean != null && taskBean.isRunning()) {
         do {
            try {
               Thread.sleep(5000L);
            } catch (InterruptedException var3) {
            }
         } while(taskBean.isRunning());
      }

   }
}
