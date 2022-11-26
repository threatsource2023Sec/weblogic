package weblogic.management.patching.commands;

import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;

public class DelayBetweenNodesCommand extends AbstractCommand {
   private static final long serialVersionUID = 3450650878468242186L;
   public static final String MACHINE_NAME_KEY = "machineName";
   public static final String DELAY_TIME_KEY = "delayTimeMillis";
   @SharedState
   protected String machineName;
   @SharedState
   protected long delayTimeMillis;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.pause();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.pause();
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }

   private void pause() {
      long timeRemaining = this.delayTimeMillis;
      long beginPause = System.currentTimeMillis();

      for(long endPause = beginPause + this.delayTimeMillis; timeRemaining > 0L; timeRemaining = endPause - System.currentTimeMillis()) {
         try {
            Thread.sleep(timeRemaining);
         } catch (InterruptedException var8) {
         }
      }

   }
}
