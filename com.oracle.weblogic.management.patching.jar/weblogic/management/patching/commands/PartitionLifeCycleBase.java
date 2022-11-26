package weblogic.management.patching.commands;

import java.security.AccessController;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.TopologyInspector;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.PartitionUtils;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.PartitionLifeCycleTaskRuntime.Status;

public abstract class PartitionLifeCycleBase extends AbstractCommand {
   private static final long serialVersionUID = -4146720928588753754L;
   private static final long DEFAULT_POLL_INTERVAL;
   private static final long DEFAULT_START_TIMEOUT;
   private static final long DEFAULT_SHUTDOWN_TIMEOUT;
   private static final AuthenticatedSubject kernelId;
   @SharedState
   public transient String serverName;
   @SharedState
   public transient String partitionName;
   @SharedState
   public transient int sessionTimeout = 0;
   @SharedState
   public transient boolean waitForAllSessions = true;
   @SharedState
   public transient Map lastPartitionState;

   public void initialize(WorkflowContext workFlowContext) {
      super.initialize(workFlowContext);
   }

   protected void startPartition() throws CommandException {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("startPartition called for partition: " + this.partitionName + " on server: " + this.serverName);
      }

      String stateMapKey = this.getStateMapKey();
      String lastState = (String)this.lastPartitionState.get(stateMapKey);
      if (State.isShutdownHalted(lastState)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("startPartition not starting " + this.partitionName + " on server: " + this.serverName + " because it was already in shutdown state");
         }

      } else {
         TimeoutUtils timeoutUtils = new TimeoutUtils();
         long timeout = timeoutUtils.convertTimeoutByPercentageFactor(DEFAULT_START_TIMEOUT);
         ServerMBean serverMBean = TopologyInspector.getDomainMBean().lookupServer(this.serverName);
         PartitionLifeCycleRuntimeMBean lcr = this.getPartitionLCR();
         timeout = this.waitForAllTasksComplete(lcr, timeout);
         String currentState = this.getCurrentState(lcr, this.serverName);
         PartitionLifeCycleTaskRuntimeMBean task;
         if (!State.isShutdownHalted(currentState) && !State.isShutdownBooted(currentState)) {
            if (State.isAdmin(currentState)) {
               try {
                  task = lcr.resume(new TargetMBean[]{serverMBean});
                  this.waitForCompletion(task, timeout);
                  if (task.getStatus().equals(Status.FAILED.toString())) {
                     throw new CommandException(PatchingMessageTextFormatter.getInstance().partitionStartError(this.partitionName, task.getError()));
                  }
               } catch (PartitionLifeCycleException var10) {
                  throw new CommandException(PatchingMessageTextFormatter.getInstance().failedToStartPartition(this.partitionName, this.serverName, var10));
               }
            } else {
               if (!State.isRunning(currentState)) {
                  throw new CommandException(PatchingMessageTextFormatter.getInstance().invalidPartitionState(this.partitionName, currentState, this.serverName, "start"));
               }

               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Skip starting " + this.partitionName + " on server " + this.serverName + " because it was already in running state");
               }
            }
         } else {
            try {
               task = lcr.start(new TargetMBean[]{serverMBean});
               this.waitForCompletion(task, timeout);
               if (task.getStatus().equals(Status.FAILED.toString())) {
                  throw new CommandException(PatchingMessageTextFormatter.getInstance().partitionStartError(this.partitionName, task.getError()));
               }
            } catch (PartitionLifeCycleException var11) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().failedToStartPartition(this.partitionName, this.serverName, var11));
            }
         }

      }
   }

   protected void shutdownPartition() throws CommandException {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("shutdownPartition called for partition: " + this.partitionName + " on server: " + this.serverName);
      }

      TimeoutUtils timeoutUtils = new TimeoutUtils();
      long timeout = timeoutUtils.convertTimeoutByPercentageFactor(DEFAULT_SHUTDOWN_TIMEOUT);
      DomainMBean domainMBean = TopologyInspector.getDomainMBean();
      ServerMBean serverMBean = domainMBean.lookupServer(this.serverName);
      PartitionLifeCycleRuntimeMBean lcr = this.getPartitionLCR();
      timeout = this.waitForAllTasksComplete(lcr, timeout);
      String currentState = this.getCurrentState(lcr, this.serverName);
      String stateMapKey = this.getStateMapKey();
      this.lastPartitionState.put(stateMapKey, currentState);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("shutdownPartition last partition state set to " + (String)this.lastPartitionState.get(stateMapKey));
      }

      if (State.isRunning(currentState)) {
         long gracefulTimeout = 0L;
         if (this.sessionTimeout != 0 || !this.waitForAllSessions) {
            gracefulTimeout = TimeUnit.MILLISECONDS.convert((long)this.sessionTimeout, TimeUnit.SECONDS) + timeout;
         }

         timeout -= this.gracefulShutdownPartition(lcr, serverMBean, gracefulTimeout);
         boolean isAdminServer = this.serverName.equals(domainMBean.getAdminServerName());
         if (isAdminServer || PartitionUtils.hasAdministrativeRG(this.partitionName)) {
            this.haltPartitionInShutdown(lcr, serverMBean, timeout);
         }
      } else if (!State.isAdmin(currentState) && !State.isShutdownBooted(currentState)) {
         if (!State.isShutdownHalted(currentState)) {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().invalidPartitionState(this.partitionName, currentState, this.serverName, "shutdown"));
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Skip shutting down " + this.partitionName + " on server " + this.serverName + " because it was already in shutdown state");
         }
      } else {
         this.haltPartitionInShutdown(lcr, serverMBean, timeout);
      }

   }

   private String getStateMapKey() {
      String stateMapKey = this.partitionName + ":" + this.serverName;
      return stateMapKey;
   }

   private long gracefulShutdownPartition(PartitionLifeCycleRuntimeMBean lcr, ServerMBean serverMBean, long timeout) throws CommandException {
      long start = System.currentTimeMillis();

      try {
         PartitionLifeCycleTaskRuntimeMBean task = lcr.shutdown(this.sessionTimeout, false, this.waitForAllSessions, new TargetMBean[]{serverMBean});
         if (this.sessionTimeout == 0 && this.waitForAllSessions) {
            this.waitForCompletion(task);
         } else {
            this.waitForCompletion(task, timeout);
         }

         if (task.getStatus().equals(Status.FAILED.toString())) {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().partitionShutdownError(this.partitionName, task.getError()));
         }
      } catch (PartitionLifeCycleException var9) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().failedToShutdownPartition(this.partitionName, this.serverName, var9));
      }

      long duration = System.currentTimeMillis() - start;
      return duration;
   }

   private void haltPartitionInShutdown(PartitionLifeCycleRuntimeMBean lcr, ServerMBean serverMBean, long timeout) throws CommandException {
      try {
         PartitionLifeCycleTaskRuntimeMBean task = lcr.halt(new TargetMBean[]{serverMBean});
         this.waitForCompletion(task, timeout);
         if (task.getStatus().equals(Status.FAILED.toString())) {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().partitionShutdownError(this.partitionName, task.getError()));
         }
      } catch (PartitionLifeCycleException var6) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().failedToShutdownPartition(this.partitionName, this.serverName, var6));
      }
   }

   private String getCurrentState(PartitionLifeCycleRuntimeMBean lcr, String server) throws CommandException {
      String currentState = null;

      try {
         currentState = lcr.getState(server);
         if (State.isShutdown(currentState)) {
            currentState = lcr.getSubState(server);
         }

         return currentState;
      } catch (PartitionLifeCycleException var5) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Failed to get current state of partition " + this.partitionName + " on server: " + server, var5);
         }

         throw new CommandException(PatchingMessageTextFormatter.getInstance().failedToGetPartitionState(this.partitionName, server));
      }
   }

   private PartitionLifeCycleRuntimeMBean getPartitionLCR() throws CommandException {
      DomainRuntimeServiceMBean domainRuntimeService = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
      PartitionLifeCycleRuntimeMBean partitionLCR = null;
      DomainPartitionRuntimeMBean partitionRuntimeMBean = domainRuntimeService.getDomainRuntime().lookupDomainPartitionRuntime(this.partitionName);
      if (partitionRuntimeMBean != null) {
         partitionLCR = partitionRuntimeMBean.getPartitionLifeCycleRuntime();
      }

      if (partitionLCR == null) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().unableToGetPartitionMBean(this.partitionName));
      } else {
         return partitionLCR;
      }
   }

   private long waitForAllTasksComplete(PartitionLifeCycleRuntimeMBean lcr, long timeout) throws CommandException {
      PartitionLifeCycleTaskRuntimeMBean[] tasks = lcr.getTasks();

      for(int i = 0; i < tasks.length; ++i) {
         long start = System.currentTimeMillis();
         this.waitForCompletion(tasks[i], timeout);
         long end = System.currentTimeMillis();
         timeout -= end - start;
      }

      return timeout;
   }

   private void waitForCompletion(PartitionLifeCycleTaskRuntimeMBean taskBean) {
      if (taskBean != null && taskBean.isRunning()) {
         do {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var3) {
            }
         } while(taskBean.isRunning());
      }

   }

   private void waitForCompletion(final PartitionLifeCycleTaskRuntimeMBean taskBean, long timeout) throws CommandException {
      TimeoutUtils timeoutUtils = new TimeoutUtils();
      long pollInterval = timeoutUtils.convertIntervalByFactorIfLarger(DEFAULT_POLL_INTERVAL);
      StatusPoller lifeCycleTaskPoller = new StatusPoller() {
         public boolean checkStatus() {
            return taskBean == null || !taskBean.isRunning();
         }

         public String getPollingDescription() {
            return "PartitionLifeCycleTask";
         }
      };
      if ((new TimedStatusPoller()).pollStatusWithTimeout(timeout, pollInterval, lifeCycleTaskPoller)) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getPartitionLifeCycleTaskTimeout(this.partitionName, taskBean.getStatus(), timeout, taskBean.getOperation()));
      }
   }

   static {
      DEFAULT_POLL_INTERVAL = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.SECONDS);
      DEFAULT_START_TIMEOUT = TimeUnit.MILLISECONDS.convert(4L, TimeUnit.HOURS);
      DEFAULT_SHUTDOWN_TIMEOUT = TimeUnit.MILLISECONDS.convert(1L, TimeUnit.HOURS);
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
