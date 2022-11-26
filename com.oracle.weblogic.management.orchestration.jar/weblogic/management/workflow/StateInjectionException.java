package weblogic.management.workflow;

import java.io.Serializable;

public class StateInjectionException extends WorkflowException implements Serializable {
   private static final long serialVersionUID = 1L;

   public StateInjectionException() {
   }

   public StateInjectionException(String msg) {
      super(msg);
   }

   public StateInjectionException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public StateInjectionException(Throwable cause) {
      super(cause);
   }
}
