package weblogic.management.workflow.command;

public abstract class AbstractCommand implements CommandInterface {
   private WorkflowContext context;

   public void initialize(WorkflowContext context) {
      this.context = context;
   }

   protected WorkflowContext getContext() {
      return this.context;
   }
}
