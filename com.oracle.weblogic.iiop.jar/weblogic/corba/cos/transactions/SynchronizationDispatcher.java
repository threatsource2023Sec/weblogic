package weblogic.corba.cos.transactions;

import javax.transaction.Synchronization;

public class SynchronizationDispatcher implements Synchronization {
   org.omg.CosTransactions.Synchronization sync;

   public SynchronizationDispatcher(org.omg.CosTransactions.Synchronization sync) {
      this.sync = sync;
   }

   public void afterCompletion(int status) {
      this.sync.after_completion(OTSHelper.jta2otsStatus(status));
   }

   public void beforeCompletion() {
      this.sync.before_completion();
   }
}
