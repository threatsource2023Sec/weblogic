package weblogic.transaction.cdi;

import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.TransactionScoped;

public class TransactionScopedCDIEventHelperImpl implements TransactionScopedCDIEventHelper {
   @Inject
   @Initialized(TransactionScoped.class)
   Event trxScopeInitializedEvent;
   @Inject
   @Destroyed(TransactionScoped.class)
   Event trxScopeDestroyedEvent;

   public void fireInitializedEvent(TransactionScopedCDIEventPayload payload) {
      this.trxScopeInitializedEvent.fire(payload);
   }

   public void fireDestroyedEvent(TransactionScopedCDIEventPayload payload) {
      this.trxScopeDestroyedEvent.fire(payload);
   }
}
