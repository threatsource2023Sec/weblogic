package weblogic.transaction.internal;

import javax.transaction.xa.XAException;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.management.MBeanCreationException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.TransactionResourceRuntimeMBean;

public final class TransactionResourceRuntimeImpl extends JTAStatisticsImpl implements TransactionResourceRuntimeMBean, TransactionResourceRuntime {
   private static final long serialVersionUID = -2736453126861335594L;
   private JTARuntimeImpl jtaRuntime;
   private JTAPartitionRuntimeImpl jtaPartitionRuntime;
   private String resourceName;
   protected long transactionCommittedTotalCount;
   protected long transactionRolledBackTotalCount;
   protected long transactionHeuristicCommitTotalCount;
   protected long transactionHeuristicRollbackTotalCount;
   protected long transactionHeuristicMixedTotalCount;
   protected long transactionHeuristicHazardTotalCount;
   private boolean healthy;
   private HealthState healthState;

   public TransactionResourceRuntimeImpl(String name, JTARuntimeImpl jtaRuntime) throws MBeanCreationException, ManagementException {
      super(name, jtaRuntime);
      jtaRuntime.addTransactionResourceRuntimeMBean(this);
      this.jtaRuntime = jtaRuntime;
      this.resourceName = name;
      this.healthy = true;
      this.healthState = new HealthState(0);
   }

   public TransactionResourceRuntimeImpl(String name, JTAPartitionRuntimeImpl jtaPartitionRuntime) throws MBeanCreationException, ManagementException {
      super(name, jtaPartitionRuntime);
      jtaPartitionRuntime.addTransactionResourceRuntimeMBean(this);
      this.jtaPartitionRuntime = jtaPartitionRuntime;
      this.resourceName = name;
      this.healthy = true;
      this.healthState = new HealthState(0);
   }

   public String getResourceName() {
      return this.resourceName;
   }

   public HealthState getHealthState() {
      return this.healthState;
   }

   public void tallyCompletion(ServerResourceInfo aResourceInfo, XAException aXAExc) {
      if (aXAExc != null) {
         switch (aXAExc.errorCode) {
            case 5:
               this.tallyHeuristicMixedCompletion();
               break;
            case 6:
               this.tallyHeuristicRollbackCompletion();
               break;
            case 7:
               this.tallyHeuristicCommitCompletion();
               break;
            case 8:
               this.tallyHeuristicHazardCompletion();
         }
      }

      if (aResourceInfo.isCommitted()) {
         ++this.transactionCommittedTotalCount;
      } else if (aResourceInfo.isRolledBack()) {
         ++this.transactionRolledBackTotalCount;
      }

   }

   public void setHealthy(boolean flag) {
      if (this.healthy != flag) {
         HealthState oldHealthState = this.healthState;
         this.healthy = flag;
         HealthEvent healthEvent = null;
         String reason;
         Symptom symptom;
         if (!flag) {
            reason = "Resource " + this.resourceName + " declared unhealthy";
            symptom = new Symptom(SymptomType.TRANSACTION, Severity.HIGH, this.resourceName, reason);
            this.healthState = new HealthState(3, symptom);
            healthEvent = new HealthEvent(5, this.resourceName, reason);
         } else {
            reason = "Resource " + this.resourceName + " declared healthy";
            symptom = new Symptom(SymptomType.TRANSACTION, Severity.LOW, this.resourceName, reason);
            this.healthState = new HealthState(0, symptom);
            healthEvent = new HealthEvent(6, this.resourceName, reason);
         }

         if (this.jtaRuntime != null) {
            this.jtaRuntime.healthEvent(healthEvent);
         }

      }
   }

   public void tallyHeuristicCommitCompletion() {
      ++this.transactionHeuristicCommitTotalCount;
   }

   public void tallyHeuristicRollbackCompletion() {
      ++this.transactionHeuristicRollbackTotalCount;
   }

   public void tallyHeuristicMixedCompletion() {
      ++this.transactionHeuristicMixedTotalCount;
   }

   public void tallyHeuristicHazardCompletion() {
      ++this.transactionHeuristicHazardTotalCount;
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
      return this.getTransactionHeuristicCommitTotalCount() + this.getTransactionHeuristicRollbackTotalCount() + this.getTransactionHeuristicMixedTotalCount() + this.getTransactionHeuristicHazardTotalCount();
   }

   public long getTransactionHeuristicCommitTotalCount() {
      return this.transactionHeuristicCommitTotalCount;
   }

   public long getTransactionHeuristicRollbackTotalCount() {
      return this.transactionHeuristicRollbackTotalCount;
   }

   public long getTransactionHeuristicMixedTotalCount() {
      return this.transactionHeuristicMixedTotalCount;
   }

   public long getTransactionHeuristicHazardTotalCount() {
      return this.transactionHeuristicHazardTotalCount;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(128);
      sb.append("{");
      sb.append("resourceName=").append(this.resourceName);
      sb.append(",");
      sb.append("transactionHeuristicCommitTotalCount=").append(this.getTransactionHeuristicCommitTotalCount());
      sb.append(",");
      sb.append("transactionHeuristicRollbackTotalCount=").append(this.getTransactionHeuristicRollbackTotalCount());
      sb.append(",");
      sb.append("transactionHeuristicMixedTotalCount=").append(this.getTransactionHeuristicMixedTotalCount());
      sb.append(",");
      sb.append("transactionHeuristicHazardTotalCount=").append(this.getTransactionHeuristicHazardTotalCount());
      sb.append(",");
      sb.append(super.toString());
      sb.append("}");
      return sb.toString();
   }
}
