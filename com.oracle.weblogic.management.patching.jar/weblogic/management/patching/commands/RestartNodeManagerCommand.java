package weblogic.management.patching.commands;

import java.security.AccessController;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.workflow.command.CommandResumeInterface;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.nodemanager.mbean.NodeManagerRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RestartNodeManagerCommand extends AbstractRestartNodeManagerCommand implements CommandResumeInterface, CommandRevertInterface {
   private static final long serialVersionUID = 2256838564540552328L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @SharedState
   protected Boolean isRestart = false;

   public boolean revert() throws Exception {
      boolean success = true;
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      if (this.directorySwitchPerformed.getValue()) {
         PatchingLogger.logRevertingStep(workflowId, className, logTarget);
         success = this.execute();
         if (success) {
            PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
         } else {
            PatchingLogger.logFailedRevertStepNoError(workflowId, className, logTarget);
         }
      } else {
         PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      }

      return success;
   }

   public boolean resume() throws Exception {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RestartNodeManagerCommand.resume called for machine: " + this.machineName);
      }

      return this.execute();
   }

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);

      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RestartNodeManagerCommand.execute called for machine: " + this.machineName);
         }

         MachineMBean machine = (new MachineBasedUtils()).getMachineMBean(this.machineName);
         NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machine);
         if (this.isAdminServer) {
            String currentServerActivationTime = "" + ServerUtils.getServerActivationTime();
            if (currentServerActivationTime.equals(this.serverActivationTime.getValue())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("RestartNodeManagerCommand.execute called on AdminServer with matching activationTimes. Calling NM.restartAll.  ActivationTime was: " + currentServerActivationTime);
               }

               TimerTaskRunner r = new TimerTaskRunner(() -> {
                  nmr.restartAllAndUpdate((new TimeoutUtils()).convertTimeoutByPercentageFactor(this.timeoutMillis));
                  return 0;
               });
               r.execTask();
            } else {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("RestartNodeManagerCommand.execute called on AdminServer with unmatched activationTimes. Skipping NM.restartAll.  ActivationTimes were: " + currentServerActivationTime + ", and " + this.serverActivationTime);
               }

               this.waitForAdminState();
               this.checkNMReconnect(nmr);
            }
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("RestartNodeManagerCommand.execute called on ManagedServer, ignoring activationTime for machine: " + this.machineName);
            }

            TimerTaskRunner r;
            if (this.isRestart) {
               r = new TimerTaskRunner(() -> {
                  nmr.restartNM((new TimeoutUtils()).convertTimeoutByPercentageFactor(this.timeoutMillis));
                  return 0;
               });
               r.execTask();
            } else {
               r = new TimerTaskRunner(() -> {
                  nmr.restartNMAndUpdate((new TimeoutUtils()).convertTimeoutByPercentageFactor(this.timeoutMillis));
                  return 0;
               });
               r.execTask();
            }
         }

         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
         return true;
      } catch (Exception var8) {
         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getRestartNodeManagerFailure(this.machineName, var8.getClass() + ":" + var8.getMessage()));
         ce.initCause(var8);
         throw ce;
      }
   }

   private void waitForAdminState() {
      ServerUtils serverUtils = new ServerUtils();
      String adminServerName = serverUtils.getServerMBean().getName();

      while(!serverUtils.isAtLeastAdminState(adminServerName)) {
         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var4) {
         }
      }

      ManagementService.getDomainRuntimeMBeanServer(kernelId).getDefaultDomain();
   }
}
