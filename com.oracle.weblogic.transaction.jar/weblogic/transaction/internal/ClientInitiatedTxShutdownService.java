package weblogic.transaction.internal;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(20)
public final class ClientInitiatedTxShutdownService extends AbstractServerService {
   @Inject
   @Named("RMIShutdownService")
   private ServerService dependencyOnRMIShutdownService;
   private static int state = 0;
   private static final Object stateLock = new String("LifecycleState");
   private static final Object suspendLock = new String("LifecycleSuspend");
   private static final int LOG_INTERVAL = 15;

   public void stop() throws ServiceFailureException {
      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("ClientInitiatedTxShutdownService SUSPENDING ...");
      }

      setState(4);
      ServerTransactionManagerImpl stm = getTM();
      long shutdownGracePeriodMS = this.getShutdownGracePeriod();
      boolean allTransactionsDone = stm.isTxMapEmpty();
      int logCount = 15;
      synchronized(suspendLock) {
         if (!allTransactionsDone) {
            TXLogger.logPendingTxDuringShutdown();
         }

         try {
            if (TxDebug.JTALifecycle.isDebugEnabled()) {
               TxDebug.JTALifecycle.debug("ClientInitiatedTxShutdownService SUSPENDING with " + this.getPendingTxMap(stm) + " shutdownGracePeriodMS: " + shutdownGracePeriodMS);
            }

            while(!allTransactionsDone && shutdownGracePeriodMS > 0L) {
               shutdownGracePeriodMS -= 1000L;
               --logCount;
               suspendLock.wait(1000L);
               allTransactionsDone = stm.isTxMapEmpty();
               if (logCount <= 0) {
                  logCount = 15;
                  if (TxDebug.JTALifecycle.isDebugEnabled()) {
                     TxDebug.JTALifecycle.debug("ClientInitiatedTxShutdownService SUSPENDING with " + this.getPendingTxMap(stm) + " shutdownGracePeriodMS: " + shutdownGracePeriodMS);
                  }
               }
            }

            if (!allTransactionsDone) {
               TXLogger.logPendingTxAfterInitiatedTxShutdown();
            }
         } catch (InterruptedException var13) {
         } finally {
            setState(3);
            if (TxDebug.JTALifecycle.isDebugEnabled()) {
               TxDebug.JTALifecycle.debug("ClientInitiatedTxShutdownService suspendSuccessfullyCompleted with " + this.getPendingTxMap(stm));
            }

         }
      }

      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("ClientInitiatedTxShutdownService SUSPEND DONE");
      }

   }

   public static int getState() {
      synchronized(stateLock) {
         return state;
      }
   }

   private static void setState(int newState) {
      synchronized(stateLock) {
         state = newState;
      }
   }

   public void halt() throws ServiceFailureException {
      setState(3);
   }

   public void start() throws ServiceFailureException {
      setState(2);
   }

   static boolean isSuspending() {
      synchronized(stateLock) {
         return state == 4;
      }
   }

   static void suspendDone() {
      synchronized(suspendLock) {
         synchronized(stateLock) {
            if (state == 4) {
               state = 3;
            }
         }

         suspendLock.notify();
      }
   }

   private static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)TransactionManagerImpl.getTransactionManager();
   }

   public static boolean isTxMapEmpty() {
      return getTM().isTxMapEmpty();
   }

   public static int getTxTimeoutMillis() {
      return getTM().getTransactionTimeout() * 1000;
   }

   private String getPendingTxMap(ServerTransactionManagerImpl stm) {
      String pendingStr = "None";
      if (stm == null) {
         return pendingStr;
      } else {
         ConcurrentHashMap txMap = stm.getTxMap();
         int ct = 0;
         if (txMap != null && txMap.size() > 0) {
            pendingStr = "\n   ";

            StringBuilder var10000;
            TransactionImpl tx;
            for(Iterator it = txMap.values().iterator(); it.hasNext(); pendingStr = var10000.append(ct).append(": ").append(tx.toString()).append("\n   ").toString()) {
               tx = (TransactionImpl)it.next();
               var10000 = (new StringBuilder()).append(pendingStr).append(" ");
               ++ct;
            }

            pendingStr = ct + " pending transactions: " + pendingStr;
         }

         return pendingStr;
      }
   }

   private long getShutdownGracePeriod() {
      try {
         return Long.parseLong(System.getProperty("weblogic.transaction.ShutdownGracePeriod", Long.toString((long)getTM().getTransactionTimeout()))) * 1000L;
      } catch (Throwable var2) {
         if (TxDebug.JTALifecycle.isDebugEnabled()) {
            TxDebug.JTALifecycle.debug("ClientInitiatedTxShutdownService exception processing system property weblogic.transaction.ShutdownGracePeriod, " + var2.toString());
         }

         return (long)(getTM().getTransactionTimeout() * 1000);
      }
   }
}
