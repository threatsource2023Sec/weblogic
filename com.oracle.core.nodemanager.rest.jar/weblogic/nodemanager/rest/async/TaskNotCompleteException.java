package weblogic.nodemanager.rest.async;

public class TaskNotCompleteException extends Exception {
   public TaskNotCompleteException(String errorMsg) {
      super(errorMsg);
   }
}
