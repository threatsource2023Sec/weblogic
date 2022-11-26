package weblogic.transaction;

import javax.naming.Context;
import javax.transaction.UserTransaction;
import javax.transaction.xa.Xid;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.Kernel;
import weblogic.transaction.internal.ClientTransactionManagerImpl;
import weblogic.transaction.internal.PlatformHelper;
import weblogic.transaction.internal.TransactionManagerImpl;

public class TxHelper extends ClientTxHelper {
   /** @deprecated */
   @Deprecated
   public static Transaction getTransaction() {
      return (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
   }

   /** @deprecated */
   @Deprecated
   public static UserTransaction getUserTransaction() {
      return TransactionHelper.getTransactionHelper().getUserTransaction();
   }

   /** @deprecated */
   @Deprecated
   public static ClientTransactionManager getClientTransactionManager() {
      return TransactionHelper.getTransactionHelper().getTransactionManager();
   }

   public static String getTransactionId() {
      if (TransactionManagerImpl.isInitialized()) {
         Transaction tx = getTransaction();
         if (tx != null) {
            Xid tranId = tx.getXID();
            if (tranId != null) {
               return tranId.toString();
            }
         }
      }

      return null;
   }

   public static InterposedTransactionManager getServerInterposedTransactionManager() {
      return Kernel.isServer() ? TransactionManagerImpl.getTransactionManager() : null;
   }

   public static InterposedTransactionManager getClientInterposedTransactionManager(Context initialContext, String serverName) {
      try {
         InterposedTransactionManager itm = (InterposedTransactionManager)initialContext.lookup("weblogic.transaction.coordinators." + serverName);
         PlatformHelper.getPlatformHelper().setSSLURLFromClientInfo((ClientTransactionManagerImpl)itm, initialContext);
         return itm;
      } catch (Exception var3) {
         return null;
      }
   }

   public static String status2String(int status) {
      switch (status) {
         case 0:
            return "Active";
         case 1:
            return "Marked Rollback";
         case 2:
            return "Prepared";
         case 3:
            return "Committed";
         case 4:
            return "Rolledback";
         case 5:
            return "Unknown";
         case 6:
            return "No Transaction";
         case 7:
            return "Preparing";
         case 8:
            return "Committing";
         case 9:
            return "Rolling Back";
         default:
            return "Unknown";
      }
   }

   public static Xid createXid(int aFormatId, byte[] aGlobalTransactionId, byte[] aBranchQualifier) {
      return XIDFactory.createXID(aFormatId, aGlobalTransactionId, aBranchQualifier);
   }

   public static Xid createXid(byte[] aGlobalTransactionId, byte[] aBranchQualifier) {
      return XIDFactory.createXID(aGlobalTransactionId, aBranchQualifier);
   }

   public static String xidToString(Xid xid, boolean includeBranchQualifier) {
      return XIDFactory.xidToString(xid, includeBranchQualifier);
   }

   @Service
   private static class TxHelperServiceImpl implements TxHelperService {
      public String getTransactionId() {
         return TxHelper.getTransactionId();
      }
   }
}
