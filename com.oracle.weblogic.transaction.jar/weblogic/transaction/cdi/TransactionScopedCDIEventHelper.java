package weblogic.transaction.cdi;

public interface TransactionScopedCDIEventHelper {
   void fireInitializedEvent(TransactionScopedCDIEventPayload var1);

   void fireDestroyedEvent(TransactionScopedCDIEventPayload var1);
}
