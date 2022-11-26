package weblogic.transaction.internal;

import java.util.LinkedList;
import javax.transaction.xa.Xid;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.loggingresource.LoggingResource;

final class LoggingResourceRetry implements NakedTimerListener {
   private Timer pingTimer;
   private TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
   private LinkedList failedTXList = new LinkedList();
   private static LoggingResourceRetry loggingResourceRetry;

   private LoggingResourceRetry() {
   }

   static void init() {
      loggingResourceRetry = new LoggingResourceRetry();
   }

   private void schedule() {
      this.pingTimer = this.timerManager.schedule(this, 5000L);
   }

   private synchronized void registerFailedLLRTransactionInternal(Object transaction) {
      ServerTransactionImpl tx = (ServerTransactionImpl)transaction;
      tx.wakeUpAfterSeconds(60);
      this.failedTXList.add(tx);
      if (this.pingTimer == null) {
         this.schedule();
      }

      debug(tx.getLoggingResource(), (XidImpl)tx.getXID(), "LLR TX registered for retry.", (Exception)null);
   }

   static void registerFailedLLRTransaction(Object tx) {
      loggingResourceRetry.registerFailedLLRTransactionInternal(tx);
   }

   public void timerExpired(Timer timer) {
      int numChecked = false;
      LinkedList checkThese;
      synchronized(this) {
         checkThese = this.failedTXList;
         this.failedTXList = new LinkedList();
      }

      while(true) {
         while(checkThese.size() > 0) {
            ServerTransactionImpl tx = (ServerTransactionImpl)checkThese.removeFirst();
            debug(tx.getLoggingResource(), (XidImpl)tx.getXID(), "LLR TX retry, begin status check", (Exception)null);
            if (!tx.isPrepared()) {
               debug(tx.getLoggingResource(), (XidImpl)tx.getXID(), "LLR TX retry, tx resolved, perhaps manually?", (Exception)null);
            } else {
               XidImpl xid = (XidImpl)tx.getXID();
               Xid xidWithBranch = tx.getLoggingResourceInfo().getXIDwithBranch(xid);

               byte[] rec;
               LoggingResource lr;
               try {
                  tx.checkLLRRetry();
                  lr = tx.getLoggingResource();
                  rec = lr.getXARecord(xidWithBranch);
               } catch (Throwable var16) {
                  debug(tx.getLoggingResource(), (XidImpl)tx.getXID(), "LLR TX retry, status check failed" + var16, (Exception)null);
                  synchronized(this) {
                     this.failedTXList.add(tx);
                     continue;
                  }
               }

               if (rec == null) {
                  tx.wakeUpAfterSeconds(60);
                  tx.asyncRollback();
                  TXLogger.logResolvedLLRTwoPhaseCommit(lr.toString(), xid.toString(false), 0);
               } else {
                  NonXAServerResourceInfo lri = tx.getLoggingResourceInfo();
                  ResourceDescriptor rd = lri.getResourceDescriptor();
                  lri.setCommitted();
                  rd.tallyCompletion(lri, (Exception)null);
                  tx.wakeUpAfterSeconds(60);
                  tx.asyncRetryCommit();
                  TXLogger.logResolvedLLRTwoPhaseCommit(lr.toString(), xid.toString(false), 1);
               }
            }
         }

         synchronized(this) {
            if (this.failedTXList.size() == 0) {
               this.pingTimer = null;
            } else {
               this.schedule();
            }

            return;
         }
      }
   }

   private static boolean debugEnabled() {
      return TxDebug.JTA2PC.isDebugEnabled() || TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled();
   }

   private static void debug(LoggingResource lr, XidImpl xid, String s, Exception e) {
      s = "LoggingResourceRetry[" + lr + "] Xid[" + xid.toString(false) + "] " + s;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug((String)s, (Throwable)e);
      } else if (TxDebug.JTANonXA.isDebugEnabled()) {
         TxDebug.JTANonXA.debug((String)s, (Throwable)e);
      } else if (TxDebug.JTALLR.isDebugEnabled()) {
         TxDebug.JTALLR.debug((String)s, (Throwable)e);
      }

   }
}
