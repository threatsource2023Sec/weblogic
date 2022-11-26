package weblogic.management.patching.commands;

import java.security.AccessController;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.workflow.MutableBoolean;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.CommandResumeInterface;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RestartAdminServerCommand extends AbstractCommand implements CommandResumeInterface, CommandRevertInterface {
   private static final long serialVersionUID = 1633010594437035979L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @SharedState
   protected transient MutableString serverActivationTime;
   @SharedState
   protected transient MutableBoolean directorySwitchPerformed;

   public boolean revert() throws Exception {
      boolean success = true;
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.getAdminServerName();
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
         PatchingDebugLogger.debug("RestartAdminServerCommand.resume called for : " + this.getAdminServerName());
      }

      return this.execute();
   }

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.getAdminServerName();
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RestartAdminServerCommand.execute called for : " + this.getAdminServerName());
      }

      String currentServerActivationTime = "" + ServerUtils.getServerActivationTime();
      if (currentServerActivationTime.equals(this.serverActivationTime.getValue())) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RestartAdminServerCommand.execute called on AdminServer with matching activationTimes. ActivationTime was: " + currentServerActivationTime);
         }

         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
         System.exit(86);
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RestartAdminServerCommand.execute called on AdminServer with unmatched activationTimes. ActivationTimes were: " + currentServerActivationTime + ", and " + this.serverActivationTime);
         }

         this.waitForAdminState();
         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      }

      return true;
   }

   private void waitForAdminState() {
      ServerUtils serverUtils = new ServerUtils();
      String adminServerName = this.getAdminServerName();

      while(!serverUtils.isAtLeastAdminState(adminServerName)) {
         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var4) {
         }
      }

      ManagementService.getDomainRuntimeMBeanServer(kernelId).getDefaultDomain();
   }

   private String getAdminServerName() {
      ServerUtils serverUtils = new ServerUtils();
      String adminServerName = serverUtils.getServerMBean().getName();
      return adminServerName;
   }
}
