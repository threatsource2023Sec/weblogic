package javax.enterprise.event;

public enum TransactionPhase {
   IN_PROGRESS,
   BEFORE_COMPLETION,
   AFTER_COMPLETION,
   AFTER_FAILURE,
   AFTER_SUCCESS;
}
