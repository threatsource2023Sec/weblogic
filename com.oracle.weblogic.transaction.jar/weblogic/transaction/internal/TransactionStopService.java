package weblogic.transaction.internal;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public class TransactionStopService extends AbstractServerService {
   public String getName() {
      return "Transaction Stop Service";
   }

   public String getVersion() {
      return "JTA 1.1";
   }

   public void start() throws ServiceFailureException {
   }

   public void stop() throws ServiceFailureException {
      if (!getTM().getJdbcTLogEnabled()) {
         if (TxDebug.JTALifecycle.isDebugEnabled()) {
            TxDebug.JTALifecycle.debug("TransactionStopService stop skipped(JdbcTlog Disabled)...");
         }

      } else {
         if (TxDebug.JTALifecycle.isDebugEnabled()) {
            TxDebug.JTALifecycle.debug("TransactionStopService SUSPENDING ...");
         }

         TransactionService.runForceSuspend();
         if (TxDebug.JTALifecycle.isDebugEnabled()) {
            TxDebug.JTALifecycle.debug("TransactionStopService SUSPEND DONE");
         }

      }
   }

   public void halt() throws ServiceFailureException {
      if (!getTM().getJdbcTLogEnabled()) {
         if (TxDebug.JTALifecycle.isDebugEnabled()) {
            TxDebug.JTALifecycle.debug("TransactionStopService halt skipped(JdbcTlog Disabled)...");
         }

      } else {
         if (TxDebug.JTALifecycle.isDebugEnabled()) {
            TxDebug.JTALifecycle.debug("TransactionStopService FORCE SUSPENDING ...");
         }

         PlatformHelper.getPlatformHelper().setTransactionServiceHalt(System.getProperty("weblogic.transactionservice.halt", "true"));
         TransactionService.performForceSuspend();
         if (TxDebug.JTALifecycle.isDebugEnabled()) {
            TxDebug.JTALifecycle.debug("TransactionStopService FORCE SUSPEND DONE");
         }

      }
   }

   private static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)TransactionManagerImpl.getTransactionManager();
   }
}
