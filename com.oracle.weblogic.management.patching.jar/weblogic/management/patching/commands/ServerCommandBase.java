package weblogic.management.patching.commands;

import java.security.AccessController;
import java.util.Map;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;

public abstract class ServerCommandBase extends AbstractCommand {
   private static final long serialVersionUID = 2628899666169112589L;
   public static final String SHUTDOWN = "SHUTDOWN";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @SharedState
   public transient String serverName;
   @SharedState
   public transient int sessionTimeout = 0;
   @SharedState
   public transient boolean ignoreSessions = false;
   @SharedState
   public transient boolean waitForAllSessions = true;
   @SharedState
   public transient Map lastServerState;
   @SharedState
   private transient long readyCheckAppsTimeoutInMin = 30L;
   @SharedState
   public transient String[] updatingServers;
   @SharedState
   public transient String[] pendingServers;

   public void initialize(WorkflowContext workFlowContext) {
      super.initialize(workFlowContext);
   }

   public boolean shutdownServer() throws CommandException {
      ServerLifeCycleTaskRuntimeMBean taskBean = null;
      boolean ret = false;

      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("shutdownServer called for server: " + this.serverName);
         }

         ServerLifeCycleRuntimeMBean slcrm = ServerUtils.getServerLifeCycleRuntimeMBean(this.serverName);
         if (slcrm == null) {
            PatchingLogger.logNullServerRuntimeMBeanError(this.serverName);
            return ret;
         }

         this.lastServerState.put(this.serverName, slcrm.getState());
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("shutdownServer last server state set to " + (String)this.lastServerState.get(this.serverName));
         }

         if (!"SHUTDOWN".equals(this.lastServerState.get(this.serverName))) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("shutdownServer calling shutdown on ServerLifeCycleRuntimeMBean, ignoreSessions: " + this.ignoreSessions + ", waitForAllSessions: " + this.waitForAllSessions);
            }

            taskBean = slcrm.shutdown(this.sessionTimeout, this.ignoreSessions, this.waitForAllSessions);
            this.waitForCompletion(taskBean);
            if (taskBean.getStatus().equals("FAILED")) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().getFailedToShutdownServer(this.serverName, taskBean.getError()));
            }
         }

         ret = true;
      } catch (ServerLifecycleException var4) {
         PatchingLogger.logServerLifecycleOperationError(this.serverName, var4);
      }

      return ret;
   }

   public boolean startServer() throws CommandException {
      ServerLifeCycleTaskRuntimeMBean taskMBean = null;
      boolean ret = false;

      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("startServer called for server: " + this.serverName);
         }

         ServerLifeCycleRuntimeMBean srm = ServerUtils.getServerLifeCycleRuntimeMBean(this.serverName);
         if (srm == null) {
            PatchingLogger.logNullServerRuntimeMBeanError(this.serverName);
            return ret;
         }

         if ("SHUTDOWN".equals(this.lastServerState.get(this.serverName))) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("startServer not starting " + this.serverName + " because it was already in shutdown state");
            }
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("startServer calling start on ServerLifeCycleRuntimeMBean");
            }

            taskMBean = srm.start();

            while(srm.getState() != "ADMIN" && taskMBean.isRunning()) {
               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException var8) {
               }
            }

            ServerUtils utils = new ServerUtils();
            boolean sessionHandlingEnabled = true;

            try {
               utils.enableSessionHandling(this.serverName, utils.createFailoverGroups(this.pendingServers, this.updatingServers));
            } catch (Exception var7) {
               sessionHandlingEnabled = false;
            }

            this.waitForCompletion(taskMBean);
            if (taskMBean.getStatus().equals("FAILED")) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().getFailedToStartServer(this.serverName, taskMBean.getError()));
            }

            if (!sessionHandlingEnabled) {
               try {
                  utils.enableSessionHandling(this.serverName, utils.createFailoverGroups(this.pendingServers, this.updatingServers));
               } catch (Exception var9) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("startServer was unable to apply session handilng: ", var9);
                  }

                  PatchingLogger.logNoSessionHandlingApplied(this.serverName);
               }
            }

            ServerUtils.pollReadyApp(this.serverName, this.readyCheckAppsTimeoutInMin);
         }

         ret = true;
      } catch (ServerLifecycleException var10) {
         PatchingLogger.logServerLifecycleOperationError(this.serverName, var10);
      }

      return ret;
   }

   public void waitForCompletion(ServerLifeCycleTaskRuntimeMBean taskBean) {
      if (taskBean != null && taskBean.isRunning()) {
         do {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var3) {
            }
         } while(taskBean.isRunning());
      }

   }
}
