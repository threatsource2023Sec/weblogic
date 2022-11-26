package weblogic.transaction.internal;

import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JTARecoveryRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JTARecoveryRuntimeMBeanImpl extends RuntimeMBeanDelegate implements JTARecoveryRuntimeMBean, JTARecoveryRuntime {
   private TransactionRecoveryService trs;
   private volatile int initialTotalCount = -1;
   private volatile int initialUnloggedTotalCount = -1;
   private volatile int finalCompletionCount = -1;
   private volatile int finalUnloggedCompletionCount = -1;
   private static AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public JTARecoveryRuntimeMBeanImpl(TransactionRecoveryService trs) throws ManagementException {
      super(trs.getServerName(), ManagementService.getRuntimeAccess(kernelID).getServerRuntime().getJTARuntime(), true, "RecoveryRuntimeMBeans");
      this.trs = trs;
   }

   public void reset(int initialCount) {
      this.initialTotalCount = initialCount;
      this.finalCompletionCount = -1;
   }

   public void resetUnlogged(int initialCount) {
      this.initialUnloggedTotalCount = initialCount;
      this.finalUnloggedCompletionCount = -1;
   }

   public void setFinalTransactionCompletionCount(int count) {
      this.finalCompletionCount = count;
   }

   public void setFinalUnloggedTransactionCompletionCount(int count) {
      this.finalUnloggedCompletionCount = this.initialUnloggedTotalCount - count;
   }

   public boolean isActive() {
      return this.trs.isActive();
   }

   public int getInitialRecoveredTransactionTotalCount() {
      return this.initialTotalCount;
   }

   public int getInitialRecoveredUnloggedTransactionTotalCount() {
      return this.initialUnloggedTotalCount;
   }

   public int getRecoveredTransactionCompletionPercent() {
      int completionCount = this.finalCompletionCount;
      if (completionCount == -1) {
         completionCount = getTM().getRecoveredTransactionCompletionCount(this.trs.getServerName());
      }

      if (completionCount == -1) {
         completionCount = this.finalCompletionCount;
      }

      if (completionCount == -1) {
         return -1;
      } else {
         double fraction = this.initialTotalCount == 0 ? 0.0 : (double)completionCount / (double)this.initialTotalCount;
         return (int)(fraction * 100.0);
      }
   }

   public int getRecoveredUnloggedTransactionCompletionPercent() {
      int unloggedCompletionCount = this.finalUnloggedCompletionCount;
      if (this.trs.getSiteName() != null) {
         unloggedCompletionCount = this.initialUnloggedTotalCount - getTM().getRecoveredUnloggedTransactionCount(this.trs.getServerName(), this.trs.getSiteName());
      }

      if (unloggedCompletionCount == -1) {
         unloggedCompletionCount = this.initialUnloggedTotalCount - getTM().getRecoveredUnloggedTransactionCount(this.trs.getServerName());
      }

      if (unloggedCompletionCount == -1) {
         unloggedCompletionCount = this.finalUnloggedCompletionCount;
      }

      if (unloggedCompletionCount == -1) {
         return -1;
      } else {
         double fraction = this.initialUnloggedTotalCount == 0 ? 0.0 : (double)unloggedCompletionCount / (double)this.initialUnloggedTotalCount;
         return (int)(fraction * 100.0);
      }
   }

   private static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }
}
