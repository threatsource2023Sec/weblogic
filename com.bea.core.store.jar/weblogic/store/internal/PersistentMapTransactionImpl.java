package weblogic.store.internal;

import java.util.HashMap;
import java.util.Set;
import weblogic.common.CompletionRequest;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreTransaction;

public class PersistentMapTransactionImpl extends PersistentTransactionImpl {
   private final PersistentMapImpl map;
   private final HashMap pending = new HashMap();

   PersistentMapTransactionImpl(PersistentMapImpl map) {
      this.map = map;
   }

   synchronized Object put(Object key, Object value) {
      Object old = this.pending.put(key, value);
      return old;
   }

   synchronized Object get(Object key) {
      return this.pending.get(key);
   }

   synchronized Set entrySet() {
      return this.pending.entrySet();
   }

   synchronized boolean hasPendingWorkInternal() {
      return this.pending.size() != 0;
   }

   void commitInternal(CompletionRequest userCompReq) {
      this.map.commit(this, userCompReq);
   }

   void rollbackInternal(CompletionRequest userCompReq) {
      this.map.rollback(this, userCompReq);
      userCompReq.setResult(Boolean.TRUE);
   }

   static PersistentMapTransactionImpl check(PersistentStoreTransaction ptx) throws PersistentStoreException {
      if (ptx instanceof PersistentMapTransactionImpl) {
         return (PersistentMapTransactionImpl)ptx;
      } else if (ptx instanceof PersistentStoreTransactionImpl) {
         throw new PersistentStoreException("Cannot use transaction started from a PersistentStoreConnection on a PersistentMap");
      } else {
         throw new PersistentStoreException("Invalid transaction");
      }
   }
}
