package weblogic.work.concurrent.future;

public enum TaskState {
   SCHEDULED,
   CANCELING,
   RUNNING,
   CANCELED,
   SUCCESS,
   FAILED;
}
