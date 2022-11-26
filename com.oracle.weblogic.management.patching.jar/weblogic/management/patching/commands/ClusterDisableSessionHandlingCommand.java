package weblogic.management.patching.commands;

import weblogic.management.workflow.command.SharedState;

public class ClusterDisableSessionHandlingCommand extends ClusterSessionHandlingBase {
   private static final long serialVersionUID = 7189295589819724721L;
   public static final int DEFAULT_SESSION_HANDLING_TIMEOUT = 7200;
   @SharedState
   public boolean disableAfterTime = false;
   @SharedState
   public transient int sessionTimeout = 7200;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      if (this.disableAfterTime && this.sessionTimeout == 0) {
         this.sessionTimeout = 7200;
      }

      this.applySessionHandlingSetting(false, this.castFailoverGroups(this.failoverGroups), this.disableAfterTime, this.sessionTimeout);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.applySessionHandlingSetting(true, this.castFailoverGroups(this.origFailoverGroups));
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
