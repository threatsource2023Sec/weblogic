package weblogic.management.workflow.command;

import java.io.Serializable;

public interface CommandInterface extends Serializable {
   void initialize(WorkflowContext var1);

   boolean execute() throws Exception;
}
