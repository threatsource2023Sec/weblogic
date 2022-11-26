package weblogic.work.concurrent.transaction;

import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.work.concurrent.ConcurrencyLogger;

public class TransactionProcessor {
   private static final ClientTransactionManager transactionManager;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentTransaction");

   public static Transaction suspendTransaction() {
      if (transactionManager == null) {
         return null;
      } else {
         try {
            Transaction tr = transactionManager.forceSuspend();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("suspended transaction" + tr);
            }

            return tr;
         } catch (Throwable var1) {
            ConcurrencyLogger.logTransactionFail(var1);
            return null;
         }
      }
   }

   public static void resumeTransaction(Transaction suspendedTxn) {
      if (transactionManager != null) {
         if (suspendedTxn != null) {
            try {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("resumed transaction" + suspendedTxn);
               }

               transactionManager.forceResume(suspendedTxn);
            } catch (Throwable var2) {
               ConcurrencyLogger.logTransactionFail(var2);
            }
         }

      }
   }

   public static void rollbackIfExist(String taskName) {
      if (transactionManager != null) {
         try {
            if (transactionManager.getTransaction() == null) {
               return;
            }

            ConcurrencyLogger.logRollbackTransaction(transactionManager.getTransaction(), taskName != null ? taskName : "UNKNOWN");
            transactionManager.rollback();
         } catch (Throwable var2) {
            ConcurrencyLogger.logTransactionFail(var2);
         }

      }
   }

   static {
      ClientTransactionManager tm;
      try {
         tm = TransactionHelper.getTransactionHelper().getTransactionManager();
      } catch (Throwable var2) {
         ConcurrencyLogger.logTransactionFail(var2);
         tm = null;
      }

      transactionManager = tm;
   }
}
