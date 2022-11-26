package weblogic.transaction.internal;

import java.security.AccessController;
import weblogic.management.MBeanCreationException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.NonXAResourceRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.nonxa.NonXAException;

public final class NonXAResourceRuntimeImpl extends JTAStatisticsImpl implements NonXAResourceRuntimeMBean, NonXAResourceRuntime {
   private String nonXAResourceName;
   long transactionCommittedTotalCount;
   long transactionRolledBackTotalCount;
   long transactionHeuristicsTotalCount;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public NonXAResourceRuntimeImpl(String name, JTARuntimeImpl jtaRuntime) throws MBeanCreationException, ManagementException {
      super(name, jtaRuntime);
      jtaRuntime.addNonXAResourceRuntimeMBean(this);
      this.nonXAResourceName = name;
   }

   public NonXAResourceRuntimeImpl(String name, JTAPartitionRuntimeImpl jtaPartitionRuntime) throws MBeanCreationException, ManagementException {
      super(name, jtaPartitionRuntime);
      jtaPartitionRuntime.addNonXAResourceRuntimeMBean(this);
      this.nonXAResourceName = name;
   }

   public String getNonXAResourceName() {
      return this.nonXAResourceName;
   }

   public long getTransactionTotalCount() {
      return this.getTransactionCommittedTotalCount() + this.getTransactionRolledBackTotalCount();
   }

   public long getTransactionCommittedTotalCount() {
      return this.transactionCommittedTotalCount;
   }

   public long getTransactionRolledBackTotalCount() {
      return this.transactionRolledBackTotalCount;
   }

   public long getTransactionHeuristicsTotalCount() {
      return 0L;
   }

   public void tallyCompletion(ServerResourceInfo aResourceInfo, NonXAException aNonXAExc) {
      if (aResourceInfo.isCommitted()) {
         ++this.transactionCommittedTotalCount;
      } else if (aResourceInfo.isRolledBack()) {
         ++this.transactionRolledBackTotalCount;
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer(128);
      sb.append("NonXAResourceRuntimeMBean {");
      sb.append("nonXAResourceName=").append(this.nonXAResourceName);
      sb.append(",");
      sb.append(super.toString());
      sb.append("}");
      return sb.toString();
   }
}
