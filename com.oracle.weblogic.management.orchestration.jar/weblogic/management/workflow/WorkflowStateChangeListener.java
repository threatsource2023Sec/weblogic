package weblogic.management.workflow;

public interface WorkflowStateChangeListener {
   void workflowStateChanged(WorkflowProgress.State var1, WorkflowProgress.State var2, String var3, String var4);
}
