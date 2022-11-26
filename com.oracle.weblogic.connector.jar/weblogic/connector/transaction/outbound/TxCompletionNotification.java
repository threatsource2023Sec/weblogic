package weblogic.connector.transaction.outbound;

import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.connector.common.Debug;
import weblogic.connector.outbound.ConnectionHandler;

final class TxCompletionNotification implements Synchronization {
   private ConnectionHandler connHandler;
   private Transaction tx;
   private boolean deregistered;

   private TxCompletionNotification(ConnectionHandler connHandler, Transaction tx) {
      this.connHandler = connHandler;
      this.tx = tx;
      this.deregistered = false;
      this.debug("Registered object for transaction completion notification");
   }

   public static TxCompletionNotification register(Transaction tx, ConnectionHandler connHandler) throws SystemException, RollbackException {
      TxCompletionNotification notifObj = new TxCompletionNotification(connHandler, tx);
      tx.registerSynchronization(notifObj);
      return notifObj;
   }

   public void beforeCompletion() {
   }

   public void afterCompletion(int unused) {
      this.debug("Received afterCompletion notification, deregistered = " + this.deregistered);
      if (!this.deregistered) {
         ((TxConnectionHandler)((TxConnectionHandler)this.connHandler)).notifyConnPoolOfTransCompletion(this.tx);
      }

   }

   public void deregister() {
      this.deregistered = true;
   }

   private void debug(String message) {
      if (Debug.isXAoutEnabled()) {
         Debug.xaOut(this.connHandler.getPool(), "TransCompletionNotification ( " + this.toString() + " ) - " + message);
      }

   }
}
