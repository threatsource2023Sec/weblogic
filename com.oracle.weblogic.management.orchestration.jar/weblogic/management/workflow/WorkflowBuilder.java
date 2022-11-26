package weblogic.management.workflow;

import java.util.Map;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.internal.WorkflowBuilderImpl;

public abstract class WorkflowBuilder {
   public static final String META_KEY_TYPE = "TYPE";
   public static final String META_KEY_TARGETS = "TARGETS";
   public static final String META_KEY_TARGET_TYPE = "TARGET_TYPE";

   public abstract WorkflowBuilder add(Class var1) throws WorkflowException;

   public abstract WorkflowBuilder add(Class var1, FailurePlan var2) throws WorkflowException;

   public abstract WorkflowBuilder add(Class var1, Map var2, FailurePlan var3) throws WorkflowException;

   public abstract WorkflowBuilder add(Class var1, Map var2) throws WorkflowException;

   public abstract WorkflowBuilder add(CommandInterface var1) throws WorkflowException;

   public abstract WorkflowBuilder add(CommandInterface var1, FailurePlan var2) throws WorkflowException;

   public abstract WorkflowBuilder add(WorkflowBuilder var1);

   public abstract WorkflowBuilder add(WorkflowBuilder var1, FailurePlan var2);

   public abstract WorkflowBuilder add(Map var1);

   public abstract WorkflowBuilder name(String var1);

   public abstract WorkflowBuilder failurePlan(FailurePlan var1);

   public abstract WorkflowBuilder meta(String var1, String var2);

   public static WorkflowBuilder newInstance() {
      return new WorkflowBuilderImpl();
   }
}
