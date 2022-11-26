package weblogic.management.workflow;

import java.io.Serializable;

public class CommandFailedNoTraceException extends WorkflowException implements Serializable {
   private static final long serialVersionUID = 1L;

   public CommandFailedNoTraceException() {
   }

   public CommandFailedNoTraceException(String msg) {
      super(msg);
   }

   public CommandFailedNoTraceException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public CommandFailedNoTraceException(Throwable cause) {
      super(cause);
   }
}
