package weblogic.management.workflow;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import weblogic.management.workflow.internal.Workflow;

public interface WorkflowProgress {
   void initialize(Workflow var1, String var2, Map var3) throws CorruptedStoreException;

   String getWorkflowId();

   String getName();

   String getServiceName();

   State getState();

   void waitFor() throws WorkflowException, InterruptedException;

   Collection getSharedState(String var1);

   Serializable getFirstSharedState(String var1);

   Date getStartTime();

   Date getEndTime();

   boolean isComplete();

   boolean isActive();

   int getNumTotalCommands();

   int getNumCompletedCommands();

   String getProgressString();

   CommandFailedException getFailureCause();

   void registerListener(WorkflowStateChangeListener var1, boolean var2);

   void cancel() throws WorkflowException, IllegalStateException;

   boolean isRunning();

   boolean canResume();

   String getStatusHistory();

   List getErrors();

   String getNextExecuteStep();

   String getNextRevertStep();

   Map getMeta();

   String getMeta(String var1);

   String getWorkflowType();

   String getWorkflowTarget();

   void setWorkflowType(String var1);

   void setWorkflowTargetType(String var1);

   void setWorkflowTarget(String var1);

   Workflow getWorkflow();

   public static enum State {
      NONE,
      INITIALIZED,
      STARTED,
      SUCCESS,
      RETRY,
      REVERTING,
      FAIL,
      REVERTED,
      REVERT_FAIL,
      DELETED,
      CANCELED,
      REVERT_CANCELED,
      CAUSE_FAILURE;
   }
}
