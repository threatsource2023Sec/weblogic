package weblogic.jdbc.jta;

import javax.transaction.Synchronization;
import weblogic.jdbc.wrapper.TxInfo;
import weblogic.jdbc.wrapper.XAConnection;
import weblogic.transaction.Transaction;

public class CallbackHandler implements Synchronization {
   private TxInfo txInfo;
   private Transaction tx;

   public CallbackHandler(Transaction tx, TxInfo txinfo) {
      this.tx = tx;
      this.txInfo = txinfo;
   }

   public void beforeCompletion() {
   }

   public void afterCompletion(int status) {
      XAConnection xaConnToRelease = null;
      synchronized(this.txInfo) {
         if (this.txInfo.getXAConnection() != null) {
            Transaction currTx = this.txInfo.getXAConnection().getTransaction();
            if (currTx != null && this.tx != null && this.tx.equals(currTx)) {
               xaConnToRelease = this.txInfo.getXAConnection();
               this.txInfo.setXAConnection((XAConnection)null);
            }
         }
      }

      if (xaConnToRelease != null) {
         xaConnToRelease.releaseToPool();
      }

   }
}
