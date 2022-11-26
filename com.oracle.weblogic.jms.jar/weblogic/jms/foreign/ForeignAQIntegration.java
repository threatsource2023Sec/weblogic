package weblogic.jms.foreign;

import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;
import weblogic.kernel.KernelStatus;
import weblogic.transaction.TxHelper;

public final class ForeignAQIntegration {
   public static boolean isServer() {
      return KernelStatus.isServer();
   }

   public static XAResource createIgnoredXAResource() {
      return new IgnoreXAResourceImpl();
   }

   public static TransactionManager getTransactionManager() {
      return TxHelper.getTransactionManager();
   }
}
