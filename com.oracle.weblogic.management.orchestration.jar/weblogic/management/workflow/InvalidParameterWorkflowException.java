package weblogic.management.workflow;

public class InvalidParameterWorkflowException extends WorkflowException {
   public InvalidParameterWorkflowException() {
   }

   public InvalidParameterWorkflowException(String msg) {
      super(msg);
   }

   public InvalidParameterWorkflowException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
