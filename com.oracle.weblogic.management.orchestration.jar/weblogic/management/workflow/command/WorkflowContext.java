package weblogic.management.workflow.command;

import java.io.Serializable;
import java.util.NoSuchElementException;

public interface WorkflowContext {
   void store();

   Serializable getSharedState(String var1) throws NoSuchElementException;

   String getWorkflowName();

   String getId();

   String getWorkflowId();

   boolean isCancel();
}
