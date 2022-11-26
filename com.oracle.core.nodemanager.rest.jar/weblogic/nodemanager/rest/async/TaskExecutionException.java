package weblogic.nodemanager.rest.async;

public class TaskExecutionException extends Exception {
   public TaskExecutionException(Throwable throwable) {
      super(throwable);
   }
}
