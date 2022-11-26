package weblogic.management.workflow.mbean;

import java.io.PrintWriter;
import java.util.List;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.runtime.WorkflowTaskRuntimeMBean;
import weblogic.management.workflow.CommandFailedException;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.management.workflow.WorkflowProgress.State;

public class WorkflowProgressMBeanDelegate extends RuntimeMBeanDelegate implements WorkflowTaskRuntimeMBean {
   protected final WorkflowProgress progress;
   private final String description;
   private boolean systemTask;

   public WorkflowProgressMBeanDelegate(WorkflowProgress progress, RuntimeMBean parentArg) throws ManagementException {
      this(progress, (String)null, parentArg);
   }

   public WorkflowProgressMBeanDelegate(WorkflowProgress progress, String description, RuntimeMBean parentArg) throws ManagementException {
      super(progress.getWorkflowId(), parentArg);
      this.progress = progress;
      if (description == null) {
         description = progress.getName() == null ? progress.getWorkflowId() : progress.getName();
      }

      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }

   public String getStatus() {
      return this.progress.getState().toString();
   }

   public long getBeginTime() {
      return this.progress.getStartTime() == null ? -1L : this.progress.getStartTime().getTime();
   }

   public long getEndTime() {
      return this.progress.getEndTime() == null ? -1L : this.progress.getEndTime().getTime();
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return new TaskRuntimeMBean[0];
   }

   public TaskRuntimeMBean getParentTask() {
      return null;
   }

   public void cancel() throws IllegalStateException {
      this.progress.cancel();
   }

   public void printLog(PrintWriter out) {
      out.println(this.progress.getProgressString());
   }

   public Exception getError() {
      return this.progress.getFailureCause();
   }

   public boolean isSystemTask() {
      return this.systemTask;
   }

   public void setSystemTask(boolean sys) {
      this.systemTask = sys;
   }

   public String getWorkflowId() {
      return this.progress.getWorkflowId();
   }

   public String getWorkflowName() {
      return this.progress.getName();
   }

   public void waitFor() throws InterruptedException {
      this.progress.waitFor();
   }

   public int getNumTotalCommands() {
      return this.progress.getNumTotalCommands();
   }

   public int getNumCompletedCommands() {
      return this.progress.getNumCompletedCommands();
   }

   public String getProgressString() {
      return this.progress.getProgressString();
   }

   public boolean isRunning() {
      return this.progress.isRunning();
   }

   public boolean canResume() {
      return this.progress.canResume();
   }

   public String getStatusHistory() {
      return this.progress.getStatusHistory();
   }

   public CommandFailedException[] getErrors() {
      List errors = this.progress.getErrors();
      return errors != null && !errors.isEmpty() ? (CommandFailedException[])errors.toArray(new CommandFailedException[errors.size()]) : new CommandFailedException[0];
   }

   public String showNextExecuteStep() {
      return this.progress.getNextExecuteStep();
   }

   public String showNextRevertStep() {
      return this.progress.getNextRevertStep();
   }

   public String getWorkflowType() {
      return this.progress.getWorkflowType();
   }

   public String getWorkflowTarget() {
      return this.progress.getWorkflowTarget();
   }

   WorkflowProgress getWorkflowProgress() {
      return this.progress;
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         return this.progress.getState() == State.SUCCESS ? "success" : "failed";
      }
   }
}
