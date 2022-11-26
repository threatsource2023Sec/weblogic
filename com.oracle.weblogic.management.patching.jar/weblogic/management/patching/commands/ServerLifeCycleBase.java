package weblogic.management.patching.commands;

import java.security.AccessController;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.management.workflow.CommandFailedNoTraceException;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.LCMHelper;
import weblogic.server.ServerLifecycleException;

public abstract class ServerLifeCycleBase extends AbstractCommand {
   private static final long serialVersionUID = -617493746198810919L;
   private static final long DEFAULT_RESUME_TIMEOUT;
   private static final long DEFAULT_START_TIMEOUT;
   private static final long DEFAULT_SHUTDOWN_TIMEOUT;
   private static final long DEFAULT_SHUTDOWN_STATE_TIMEOUT;
   private static final long DEFAULT_READY_CHECK_APPS_TIMEOUT;
   private static final long DEFAULT_POLL_INTERVAL;
   public static final String ADMIN = "ADMIN";
   public static final String SHUTDOWN = "SHUTDOWN";
   public static final String FAILED_NOT_RESTARTABLE = "FAILED_NOT_RESTARTABLE";
   private static final AuthenticatedSubject kernelId;
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
   private transient long readyCheckAppsTimeoutInMin;

   public ServerLifeCycleBase() {
      this.readyCheckAppsTimeoutInMin = DEFAULT_READY_CHECK_APPS_TIMEOUT;
   }

   public void initialize(WorkflowContext workFlowContext) {
      super.initialize(workFlowContext);
   }

   public void shutdownServer() throws CommandException {
      ServerLifeCycleTaskRuntimeMBean taskBean = null;
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("shutdownServer called for server: " + this.serverName);
      }

      ServerLifeCycleRuntimeMBean slcrm = ServerUtils.getServerLifeCycleRuntimeMBean(this.serverName);
      if (slcrm == null) {
         throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getNullServerRuntimeMBeanError(this.serverName));
      } else {
         this.lastServerState.put(this.serverName, slcrm.getState());
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("shutdownServer last server state set to " + (String)this.lastServerState.get(this.serverName));
         }

         this.shutdownServerWithFailureHandling();
      }
   }

   public void performQuiesce() {
      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ServerLifeCycleBase.performQuiesce about to call quiesce for server " + this.serverName);
         }

         LCMHelper.performManagedServerQuiesce(this.serverName);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ServerLifeCycleBase.performQuiesce quiesce completed successfully for server " + this.serverName);
         }
      } catch (Exception var2) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ServerLifeCycleBase.performQuiesce caught an exception calling quiesce: " + var2);
         }
      }

   }

   public void shutdownServerWithFailureHandling() throws CommandException {
      ServerLifeCycleTaskRuntimeMBean taskBean = null;

      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("shutdownServerWithFailureHandling called for server: " + this.serverName);
         }

         ServerLifeCycleRuntimeMBean slcrm = ServerUtils.getServerLifeCycleRuntimeMBean(this.serverName);
         if (slcrm == null) {
            throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getNullServerRuntimeMBeanError(this.serverName));
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("shutdownServerWithFailureHandling last server state set to " + (String)this.lastServerState.get(this.serverName));
            }

            List initialStates = Arrays.asList("ADMIN", "FAILED", "RUNNING", "STANDBY");
            String currentServerState = slcrm.getState();
            if (!"SHUTDOWN".equals(this.lastServerState.get(this.serverName)) && !"FAILED_NOT_RESTARTABLE".equals(this.lastServerState.get(this.serverName)) && !"SHUTDOWN".equals(currentServerState) && !"FAILED_NOT_RESTARTABLE".equals(currentServerState)) {
               if (!initialStates.contains(currentServerState)) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug(" Server " + this.serverName + " not in RUNNING or STANDBY or ADMIN state");
                  }

                  throw new CommandException(PatchingMessageTextFormatter.getInstance().getStateInvalid(this.serverName, "shutdownServerWithoutStateSave", slcrm.getState(), "RUNNING | STANDBY | ADMIN"));
               }

               this.performQuiesce();
               if (!"FAILED".equals(slcrm.getState())) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("shutdownServerWithFailureHandling calling shutdown on ServerLifeCycleRuntimeMBean, ignoreSessions: " + this.ignoreSessions + ", waitForAllSessions: " + this.waitForAllSessions);
                  }

                  taskBean = slcrm.shutdown(this.sessionTimeout, this.ignoreSessions, this.waitForAllSessions);
               } else {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("shutdownServerWithFailureHandling calling shutdown on ServerLifeCycleRuntimeMBean for state " + slcrm.getState());
                  }

                  taskBean = slcrm.shutdown();
               }

               if (this.sessionTimeout == 0 && this.waitForAllSessions) {
                  this.waitForCompletion(taskBean);
               } else {
                  this.waitForCompletion(taskBean, TimeUnit.MILLISECONDS.convert((long)this.sessionTimeout, TimeUnit.SECONDS) + DEFAULT_SHUTDOWN_TIMEOUT);
               }

               if (taskBean.getStatus().equals("FAILED")) {
                  throw new CommandException(PatchingMessageTextFormatter.getInstance().getFailedToShutdownServer(this.serverName, taskBean.getError()));
               }

               this.ensureShutdownState(slcrm);
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug(" Server " + this.serverName + " already in shutdown state");
            }

         }
      } catch (ServerLifecycleException var5) {
         PatchingLogger.logServerLifecycleOperationError(this.serverName, var5);
         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getServerLifecycleOperationError(this.serverName, var5.getClass() + ":" + var5.getMessage()));
         ce.initCause(var5);
         throw ce;
      }
   }

   public void startServerAdminMode(String currentMachine) throws CommandException {
      this.startServerAdminMode(currentMachine, true);
   }

   public void startServerAdminMode(String currentMachine, boolean isStrict) throws CommandException {
      ServerLifeCycleTaskRuntimeMBean taskMBean = null;
      boolean ret = false;

      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("startServerAdminMode called for server: " + this.serverName);
         }

         ServerLifeCycleRuntimeMBean srm = ServerUtils.getServerLifeCycleRuntimeMBean(this.serverName);
         if (srm == null) {
            throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getNullServerRuntimeMBeanError(this.serverName));
         } else {
            if (!"SHUTDOWN".equals(this.lastServerState.get(this.serverName)) && !"FAILED_NOT_RESTARTABLE".equals(this.lastServerState.get(this.serverName))) {
               if (!"SHUTDOWN".equals(srm.getState()) && !"FAILED_NOT_RESTARTABLE".equals(srm.getState())) {
                  if (isStrict || !"ADMIN".equals(srm.getState())) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("startServer not starting " + this.serverName + " because it is not in valid shutdown state");
                     }

                     throw new CommandException(PatchingMessageTextFormatter.getInstance().getStateInvalid(this.serverName, "startServerAdminMode", srm.getState(), "SHUTDOWN | FAILED_NOT_RESTARTABLE"));
                  }

                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("startServer not starting " + this.serverName + " because it was already in admin state");
                  }
               } else {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("startServerInAdmin calling start on ServerLifeCycleRuntimeMBean with currentMachine: " + currentMachine);
                  }

                  taskMBean = srm.startInAdmin(currentMachine);
                  this.waitForCompletion(taskMBean, DEFAULT_START_TIMEOUT);
                  if (taskMBean.getStatus().equals("FAILED")) {
                     throw new CommandException(PatchingMessageTextFormatter.getInstance().getFailedToStartServer(this.serverName, taskMBean.getError()));
                  }

                  this.ensureAdminState(srm);
               }
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("startServer not starting " + this.serverName + " because it was already in shutdown state");
            }

         }
      } catch (ServerLifecycleException var7) {
         PatchingLogger.logServerLifecycleOperationError(this.serverName, var7);
         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getServerLifecycleOperationError(this.serverName, var7.getClass() + ":" + var7.getMessage()));
         ce.initCause(var7);
         throw ce;
      }
   }

   public void retryStartOnFailure(String currentMachine) throws CommandException {
      ServerLifeCycleTaskRuntimeMBean taskMBean = null;

      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("retryStartOnFailure called for server: " + this.serverName);
         }

         ServerLifeCycleRuntimeMBean srm = ServerUtils.getServerLifeCycleRuntimeMBean(this.serverName);
         if (srm == null) {
            throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getNullServerRuntimeMBeanError(this.serverName));
         } else {
            if (!"FAILED".equals(srm.getState()) && !"RUNNING".equals(srm.getState())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("server " + this.serverName + " is in Non failure state " + srm.getState());
               }
            } else {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("retryStartOnFailure calling shutdown on ServerLifeCycleRuntimeMBean with currentMachine: " + currentMachine);
               }

               this.shutdownServerWithFailureHandling();
            }

         }
      } catch (CommandException var4) {
         PatchingLogger.logServerLifecycleOperationError(this.serverName, var4);
         throw var4;
      }
   }

   public void restoreServerStateForStartCommand(String currentMachine) throws CommandException {
      ServerLifeCycleTaskRuntimeMBean taskMBean = null;

      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("restoreServerState called for server: " + this.serverName);
         }

         ServerLifeCycleRuntimeMBean srm = ServerUtils.getServerLifeCycleRuntimeMBean(this.serverName);
         if (srm == null) {
            throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getNullServerRuntimeMBeanError(this.serverName));
         } else {
            if (!"SHUTDOWN".equals(srm.getState()) && !"FAILED_NOT_RESTARTABLE".equals(srm.getState())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("retryStartOnFailure calling shutdown on ServerLifeCycleRuntimeMBean with currentMachine: " + currentMachine);
               }

               this.shutdownServerWithFailureHandling();
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("server " + this.serverName + " is in shutdown state");
            }

         }
      } catch (CommandException var4) {
         PatchingLogger.logServerLifecycleOperationError(this.serverName, var4);
         throw var4;
      }
   }

   public void resumeServer() throws CommandException {
      this.resumeServer(true);
   }

   public void resumeServer(boolean isStrict) throws CommandException {
      ServerLifeCycleTaskRuntimeMBean taskMBean = null;

      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("startServer called for server: " + this.serverName);
         }

         ServerLifeCycleRuntimeMBean srm = ServerUtils.getServerLifeCycleRuntimeMBean(this.serverName);
         if (srm == null) {
            throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getNullServerRuntimeMBeanError(this.serverName));
         } else {
            if (!"SHUTDOWN".equals(this.lastServerState.get(this.serverName))) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("startServer calling start on ServerLifeCycleRuntimeMBean");
               }

               if ("ADMIN".equals(srm.getState())) {
                  taskMBean = srm.resume();
               } else if (!isStrict && "SHUTDOWN".equals(srm.getState())) {
                  taskMBean = srm.start();
               }

               if (taskMBean == null) {
                  String expectedState = "ADMIN";
                  if (!isStrict) {
                     expectedState = expectedState + " | " + "SHUTDOWN";
                  }

                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("resumeServer not starting " + this.serverName + " because it is not in valid state. Current state is " + srm.getState() + ", expected state is " + expectedState);
                  }

                  throw new CommandException(PatchingMessageTextFormatter.getInstance().getStateInvalid(this.serverName, "resumeServer", srm.getState(), expectedState));
               }

               this.waitForCompletion(taskMBean, DEFAULT_RESUME_TIMEOUT);
               if (taskMBean.getStatus().equals("FAILED")) {
                  throw new CommandException(PatchingMessageTextFormatter.getInstance().getFailedToStartServer(this.serverName, taskMBean.getError()));
               }

               ServerUtils.pollReadyApp(this.serverName, this.readyCheckAppsTimeoutInMin);

               try {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("ServerLifeCycleBase.resumeServer about to call lcm start for server " + this.serverName);
                  }

                  LCMHelper.performManagedServerStart(this.serverName);
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("ServerLifeCycleBase.resumeServer lcm start completed successfully for server " + this.serverName);
                  }
               } catch (Exception var5) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("ServerLifeCycleBase.resumeServer caught an exception calling lcm start: " + var5);
                  }
               }
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("startServer not starting " + this.serverName + " because it was already in shutdown state");
            }

         }
      } catch (ServerLifecycleException var6) {
         PatchingLogger.logServerLifecycleOperationError(this.serverName, var6);
         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getServerLifecycleOperationError(this.serverName, var6.getClass() + ":" + var6.getMessage()));
         ce.initCause(var6);
         throw ce;
      }
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

   public void waitForCompletion(final ServerLifeCycleTaskRuntimeMBean taskBean, long timeout) throws CommandException {
      TimeoutUtils timeoutUtils = new TimeoutUtils();
      timeout = timeoutUtils.convertTimeoutByPercentageFactor(timeout);
      long pollInterval = timeoutUtils.convertIntervalByFactorIfLarger(DEFAULT_POLL_INTERVAL);
      StatusPoller lifeCycleTaskPoller = new StatusPoller() {
         public boolean checkStatus() {
            return taskBean == null || !taskBean.isRunning();
         }

         public String getPollingDescription() {
            return "ServerLifeCycleTask";
         }
      };
      if ((new TimedStatusPoller()).pollStatusWithTimeout(timeout, pollInterval, lifeCycleTaskPoller)) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getServerLifeCycleTaskTimeout(this.serverName, taskBean.getStatus(), timeout, taskBean.getOperation()));
      }
   }

   private void ensureShutdownState(final ServerLifeCycleRuntimeMBean slcrm) throws CommandException {
      TimeoutUtils timeoutUtils = new TimeoutUtils();
      long timeout = timeoutUtils.convertTimeoutByPercentageFactor(DEFAULT_SHUTDOWN_STATE_TIMEOUT);
      long pollInterval = timeoutUtils.convertIntervalByFactorIfLarger(DEFAULT_POLL_INTERVAL);
      StatusPoller serverShutdownStatePoller = new StatusPoller() {
         public boolean checkStatus() {
            List FinalStates = Arrays.asList("ADMIN", "FAILED", "RUNNING", "SHUTDOWN", "FAILED_NOT_RESTARTABLE");
            String state = slcrm.getState();
            return FinalStates.contains(state);
         }

         public String getPollingDescription() {
            return "ServerShutdownState";
         }
      };
      boolean pollResult = (new TimedStatusPoller()).pollStatusWithTimeout(timeout, pollInterval, serverShutdownStatePoller);
      if (pollResult) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getServerShutdownStateTimeout(this.serverName, slcrm.getState(), timeout));
      } else if (!slcrm.getState().equals("SHUTDOWN") && !slcrm.getState().equals("FAILED_NOT_RESTARTABLE")) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getStateInvalid(this.serverName, "shutdownServer", slcrm.getState(), "SHUTDOWN | FAILED_NOT_RESTARTABLE"));
      }
   }

   private void ensureAdminState(final ServerLifeCycleRuntimeMBean slcrm) throws CommandException {
      TimeoutUtils timeoutUtils = new TimeoutUtils();
      long timeout = timeoutUtils.convertTimeoutByPercentageFactor(DEFAULT_START_TIMEOUT);
      long pollInterval = timeoutUtils.convertIntervalByFactorIfLarger(DEFAULT_POLL_INTERVAL);
      StatusPoller serverAdminStatePoller = new StatusPoller() {
         public boolean checkStatus() {
            List FinalStates = Arrays.asList("ADMIN", "FAILED", "RUNNING", "SHUTDOWN", "FAILED_NOT_RESTARTABLE");
            return FinalStates.contains(slcrm.getState());
         }

         public String getPollingDescription() {
            return "ServerAdminState";
         }
      };
      boolean pollResult = (new TimedStatusPoller()).pollStatusWithTimeout(timeout, pollInterval, serverAdminStatePoller);
      if (pollResult) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getServerAdminStateTimeout(this.serverName, slcrm.getState(), timeout));
      } else if (!slcrm.getState().equals("ADMIN")) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getStateInvalid(this.serverName, "startServerAdminMode", slcrm.getState(), "ADMIN"));
      }
   }

   static {
      DEFAULT_RESUME_TIMEOUT = TimeUnit.MILLISECONDS.convert(4L, TimeUnit.HOURS);
      DEFAULT_START_TIMEOUT = TimeUnit.MILLISECONDS.convert(4L, TimeUnit.HOURS);
      DEFAULT_SHUTDOWN_TIMEOUT = TimeUnit.MILLISECONDS.convert(1L, TimeUnit.HOURS);
      DEFAULT_SHUTDOWN_STATE_TIMEOUT = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.MINUTES);
      DEFAULT_READY_CHECK_APPS_TIMEOUT = TimeUnit.MINUTES.convert(2L, TimeUnit.HOURS);
      DEFAULT_POLL_INTERVAL = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.SECONDS);
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
