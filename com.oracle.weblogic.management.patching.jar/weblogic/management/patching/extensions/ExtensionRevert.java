package weblogic.management.patching.extensions;

import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.WorkflowContext;

public abstract class ExtensionRevert implements CommandRevertInterface {
   private static final long serialVersionUID = 8301176708446156447L;
   private WorkflowContext context;

   public void initialize(WorkflowContext context) {
      this.context = context;
   }

   protected WorkflowContext getContext() {
      return this.context;
   }
}
