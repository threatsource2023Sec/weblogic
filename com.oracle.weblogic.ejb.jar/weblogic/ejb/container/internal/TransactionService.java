package weblogic.ejb.container.internal;

import java.security.AccessController;
import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TransactionHelper;

public final class TransactionService {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private TransactionService() {
   }

   public static Transaction getTransaction() {
      return TransactionHelper.getTransactionHelper().getTransaction();
   }

   public static weblogic.transaction.Transaction getWeblogicTransaction() {
      return (weblogic.transaction.Transaction)TransactionHelper.getTransactionHelper().getTransaction();
   }

   public static TransactionManager getTransactionManager() {
      return TransactionHelper.getTransactionHelper().getTransactionManager();
   }

   public static weblogic.transaction.TransactionManager getWeblogicTransactionManager() {
      return (weblogic.transaction.TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
   }

   public static UserTransaction getUserTransaction() {
      return TransactionHelper.getTransactionHelper().getUserTransaction();
   }

   public static boolean isRolledback(weblogic.transaction.Transaction tx) {
      if (tx != null) {
         try {
            int status = tx.getStatus();
            return status == 1 || status == 9 || status == 4;
         } catch (SystemException var2) {
         }
      }

      return false;
   }

   public static void resumeCallersTransaction(Transaction callerTx, Transaction invokeTx) throws InternalException {
      try {
         if (callerTx != null && !callerTx.equals(invokeTx)) {
            try {
               getWeblogicTransactionManager().resume(callerTx);
            } catch (InvalidTransactionException var3) {
               getWeblogicTransactionManager().forceResume(callerTx);
            }
         }

      } catch (SystemException var4) {
         EJBLogger.logErrorResumingTx(var4);
         throw new InternalException("Error resuming caller's transaction", var4);
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

   public static ConfigurationMBean getJTAConfigMBean(ComponentInvocationContext cic) {
      return getJTAConfigMBean(getDomain(), cic);
   }

   public static ConfigurationMBean getJTAConfigMBean(DomainMBean domain, ComponentInvocationContext cic) {
      if (cic.isGlobalRuntime()) {
         ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getCluster();
         return (ConfigurationMBean)(clusterMBean == null ? domain.getJTA() : clusterMBean.getJTACluster());
      } else {
         return domain.lookupPartition(cic.getPartitionName()).getJTAPartition();
      }
   }

   public static int getJTAConfigTimeout(ComponentInvocationContext cic) {
      return getJTAConfigTimeout(getDomain(), cic);
   }

   public static int getJTAConfigTimeout(DomainMBean domain, ComponentInvocationContext cic) {
      if (cic.isGlobalRuntime()) {
         ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getCluster();
         return clusterMBean == null ? domain.getJTA().getTimeoutSeconds() : clusterMBean.getJTACluster().getTimeoutSeconds();
      } else {
         return domain.lookupPartition(cic.getPartitionName()).getJTAPartition().getTimeoutSeconds();
      }
   }

   private static DomainMBean getDomain() {
      return ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
   }
}
