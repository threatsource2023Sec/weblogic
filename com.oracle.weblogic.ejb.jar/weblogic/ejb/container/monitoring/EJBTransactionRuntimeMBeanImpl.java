package weblogic.ejb.container.monitoring;

import java.util.concurrent.atomic.AtomicLong;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.EJBTransactionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class EJBTransactionRuntimeMBeanImpl extends RuntimeMBeanDelegate implements EJBTransactionRuntimeMBean {
   private static final long serialVersionUID = 2146981779811962481L;
   private final AtomicLong txCommitted = new AtomicLong(0L);
   private final AtomicLong txAborted = new AtomicLong(0L);
   private final AtomicLong txTimedOut = new AtomicLong(0L);

   public EJBTransactionRuntimeMBeanImpl(String name, EJBRuntimeMBean runtime) throws ManagementException {
      super(name, runtime, true, "TransactionRuntime");
   }

   public long getTransactionsCommittedTotalCount() {
      return this.txCommitted.get();
   }

   public void incrementTransactionsCommitted() {
      this.txCommitted.incrementAndGet();
   }

   public long getTransactionsRolledBackTotalCount() {
      return this.txAborted.get();
   }

   public void incrementTransactionsRolledBack() {
      this.txAborted.incrementAndGet();
   }

   public long getTransactionsTimedOutTotalCount() {
      return this.txTimedOut.get();
   }

   public void incrementTransactionsTimedOut() {
      this.txTimedOut.incrementAndGet();
   }
}
