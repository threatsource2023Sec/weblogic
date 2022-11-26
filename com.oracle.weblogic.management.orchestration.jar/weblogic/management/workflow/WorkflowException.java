package weblogic.management.workflow;

import java.io.Serializable;

public class WorkflowException extends RuntimeException implements Serializable {
   private static final long serialVersionUID = 1L;

   public WorkflowException() {
   }

   public WorkflowException(String msg) {
      super(msg);
   }

   public WorkflowException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public WorkflowException(Throwable cause) {
      super(cause);
   }
}
