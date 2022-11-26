package weblogic.management.runtime;

public interface WorkflowTaskRuntimeMBean extends TaskRuntimeMBean {
   String getWorkflowId();

   String getWorkflowName();

   void waitFor() throws InterruptedException;

   int getNumTotalCommands();

   int getNumCompletedCommands();

   String getProgressString();

   boolean isRunning();

   boolean canResume();

   void cancel() throws IllegalStateException;

   String getStatusHistory();

   Exception[] getErrors();

   String showNextExecuteStep();

   String showNextRevertStep();

   String getWorkflowType();

   String getWorkflowTarget();
}
