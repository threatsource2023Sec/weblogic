package javax.resource.spi.work;

import javax.resource.NotSupportedException;
import javax.transaction.xa.Xid;

public class ExecutionContext {
   private Xid xid;
   private long transactionTimeout = -1L;

   public void setXid(Xid xid) {
      this.xid = xid;
   }

   public Xid getXid() {
      return this.xid;
   }

   public void setTransactionTimeout(long timeout) throws NotSupportedException {
      if (timeout > 0L) {
         this.transactionTimeout = timeout;
      } else {
         throw new NotSupportedException("Illegal timeout value");
      }
   }

   public long getTransactionTimeout() {
      return this.transactionTimeout;
   }
}
