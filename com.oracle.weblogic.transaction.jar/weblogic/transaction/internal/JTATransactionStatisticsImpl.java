package weblogic.transaction.internal;

import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import weblogic.management.MBeanCreationException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JTATransactionStatisticsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.transaction.AppSetRollbackOnlyException;
import weblogic.transaction.Transaction;

public abstract class JTATransactionStatisticsImpl extends JTAStatisticsImpl implements Constants, JTATransactionStatisticsRuntimeMBean {
   private static final long serialVersionUID = 7547754151703772920L;
   protected long transactionCommittedTotalCount;
   protected long transactionNoResourcesCommittedTotalCount;
   protected long transactionOneResourceOnePhaseCommittedTotalCount;
   protected long transactionReadOnlyOnePhaseCommittedTotalCount;
   protected long transactionTwoPhaseCommittedTotalCount;
   protected long transactionLLRCommittedTotalCount;
   protected long transactionRolledBackTimeoutTotalCount;
   protected long transactionRolledBackResourceTotalCount;
   protected long transactionRolledBackAppTotalCount;
   protected long transactionRolledBackSystemTotalCount;
   protected long transactionAbandonedTotalCount;
   protected long transactionHeuristicsTotalCount;
   protected long millisecondsActiveTotalCount;
   protected long transactionTwoPhaseCommittedLoggedTotalCount;
   protected long transactionTwoPhaseCommittedNotLoggedTotalCount;
   private Object tallyCompletionLock = new Object();
   private Object tallyCommitLock = new Object();
   private Object tallyTypeLock = new Object();

   public JTATransactionStatisticsImpl(String name, RuntimeMBean parent) throws MBeanCreationException, ManagementException {
      super(name, parent);
   }

   synchronized void tallyRollbackReason(Throwable aRBReason) {
      if (aRBReason == null) {
         ++this.transactionRolledBackAppTotalCount;
      } else if (aRBReason instanceof XAException) {
         ++this.transactionRolledBackResourceTotalCount;
      } else if (aRBReason instanceof TimedOutException) {
         ++this.transactionRolledBackTimeoutTotalCount;
      } else if (aRBReason instanceof SystemException) {
         ++this.transactionRolledBackSystemTotalCount;
      } else if (aRBReason instanceof AppSetRollbackOnlyException) {
         ++this.transactionRolledBackAppTotalCount;
      } else {
         ++this.transactionRolledBackAppTotalCount;
      }

   }

   void tallyCompletion(Transaction tx) {
      TransactionImpl txi = (TransactionImpl)tx;

      try {
         synchronized(this.tallyCompletionLock) {
            if (txi.isAbandoned()) {
               ++this.transactionAbandonedTotalCount;
               return;
            }

            if (txi.getHeuristicErrorMessage() != null) {
               if (txi.onePhase) {
                  this.tallyRollbackReason(new XAException(txi.getRollbackReason() == null ? null : txi.getRollbackReason().getMessage()));
                  return;
               }

               ++this.transactionHeuristicsTotalCount;
            }
         }

         switch (tx.getStatus()) {
            case 3:
               this.tallyCommitType(txi);
               break;
            case 4:
            case 9:
               Throwable rbr = txi.getRollbackReason();
               this.tallyRollbackReason(rbr);
         }
      } catch (SystemException var6) {
      }

   }

   public long getTransactionTotalCount() {
      return this.getTransactionCommittedTotalCount() + this.getTransactionRolledBackTotalCount() + this.getTransactionAbandonedTotalCount();
   }

   public long getTransactionCommittedTotalCount() {
      return this.transactionCommittedTotalCount;
   }

   public long getTransactionNoResourcesCommittedTotalCount() {
      return this.transactionNoResourcesCommittedTotalCount;
   }

   public long getTransactionOneResourceOnePhaseCommittedTotalCount() {
      return this.transactionOneResourceOnePhaseCommittedTotalCount;
   }

   public long getTransactionReadOnlyOnePhaseCommittedTotalCount() {
      return this.transactionReadOnlyOnePhaseCommittedTotalCount;
   }

   public long getTransactionTwoPhaseCommittedTotalCount() {
      return this.transactionTwoPhaseCommittedTotalCount;
   }

   public long getTransactionLLRCommittedTotalCount() {
      return this.transactionLLRCommittedTotalCount;
   }

   public long getTransactionRolledBackTotalCount() {
      return this.getTransactionRolledBackAppTotalCount() + this.getTransactionRolledBackResourceTotalCount() + this.getTransactionRolledBackTimeoutTotalCount() + this.getTransactionRolledBackSystemTotalCount();
   }

   public long getTransactionRolledBackTimeoutTotalCount() {
      return this.transactionRolledBackTimeoutTotalCount;
   }

   public long getTransactionRolledBackResourceTotalCount() {
      return this.transactionRolledBackResourceTotalCount;
   }

   public long getTransactionRolledBackAppTotalCount() {
      return this.transactionRolledBackAppTotalCount;
   }

   public long getTransactionRolledBackSystemTotalCount() {
      return this.transactionRolledBackSystemTotalCount;
   }

   public long getTransactionAbandonedTotalCount() {
      return this.transactionAbandonedTotalCount;
   }

   public long getTransactionHeuristicsTotalCount() {
      return this.transactionHeuristicsTotalCount;
   }

   public long getSecondsActiveTotalCount() {
      return this.millisecondsActiveTotalCount / 1000L;
   }

   protected void tallySecondsActive(Transaction tx) {
      try {
         if (tx.getStatus() != 3) {
            return;
         }
      } catch (SystemException var3) {
         return;
      }

      this.millisecondsActiveTotalCount += tx.getMillisSinceBegin();
   }

   public long getTransactionTwoPhaseCommittedLoggedTotalCount() {
      return this.transactionTwoPhaseCommittedLoggedTotalCount;
   }

   public long getTransactionTwoPhaseCommittedNotLoggedTotalCount() {
      return this.transactionTwoPhaseCommittedNotLoggedTotalCount;
   }

   protected void tallyCommitType(TransactionImpl tx) {
      synchronized(this.tallyCommitLock) {
         ++this.transactionCommittedTotalCount;
         this.tallySecondsActive(tx);
         if (tx.isNoResourceInTx) {
            ++this.transactionNoResourcesCommittedTotalCount;
            return;
         }
      }

      boolean isServer = PlatformHelper.getPlatformHelper().isServer();
      boolean isLogWriteNecessary = ((ServerTransactionImpl)tx).isLogWriteNecessary();
      synchronized(this.tallyTypeLock) {
         if (isServer) {
            if (isLogWriteNecessary) {
               ++this.transactionTwoPhaseCommittedLoggedTotalCount;
            } else if (((ServerTransactionImpl)tx).getDeterminer() != null) {
               ++this.transactionTwoPhaseCommittedNotLoggedTotalCount;
            }
         }

         if (tx.onePhase) {
            if (tx.isOnlyOneResourceInTx) {
               ++this.transactionOneResourceOnePhaseCommittedTotalCount;
            } else {
               ++this.transactionReadOnlyOnePhaseCommittedTotalCount;
            }
         } else if (tx.isLLR) {
            ++this.transactionLLRCommittedTotalCount;
         } else {
            ++this.transactionTwoPhaseCommittedTotalCount;
         }

      }
   }

   public int getAverageCommitTimeSeconds() {
      return this.transactionCommittedTotalCount == 0L ? 0 : (int)(this.millisecondsActiveTotalCount / this.transactionCommittedTotalCount);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(64);
      sb.append("{");
      sb.append(super.toString());
      sb.append(",");
      sb.append("transactionRolledBackTimeoutTotalCount=").append(this.getTransactionRolledBackTimeoutTotalCount());
      sb.append(", ");
      sb.append("transactionRolledBackResourceTotalCount=").append(this.getTransactionRolledBackResourceTotalCount());
      sb.append(", ");
      sb.append("transactionRolledBackAppTotalCount=").append(this.getTransactionRolledBackAppTotalCount());
      sb.append(", ");
      sb.append("transactionRolledBackSystemTotalCount=").append(this.getTransactionRolledBackSystemTotalCount());
      sb.append(", ");
      sb.append("transactionAbandonedTotalCount=").append(this.getTransactionAbandonedTotalCount());
      sb.append(", ");
      sb.append("millisecondsActiveTotalCount=").append(this.millisecondsActiveTotalCount);
      sb.append(", ");
      sb.append("averageCommitTimeSeconds=").append(this.getAverageCommitTimeSeconds());
      sb.append("}");
      return sb.toString();
   }
}
