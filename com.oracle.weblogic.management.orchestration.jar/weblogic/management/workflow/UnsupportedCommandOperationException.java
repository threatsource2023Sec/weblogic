package weblogic.management.workflow;

import java.io.Serializable;

public class UnsupportedCommandOperationException extends WorkflowException implements Serializable {
   private static final long serialVersionUID = 1L;

   public UnsupportedCommandOperationException() {
   }

   public UnsupportedCommandOperationException(String msg) {
      super(msg);
   }
}
