package weblogic.transaction.internal;

import weblogic.management.ManagementException;
import weblogic.management.runtime.JTAPartitionRuntimeMBean;
import weblogic.management.runtime.JTARuntimeMBean;
import weblogic.management.runtime.TransactionNameRuntimeMBean;

public final class TransactionNameRuntimeImpl extends JTATransactionStatisticsImpl implements TransactionNameRuntimeMBean {
   private String transactionName;

   public TransactionNameRuntimeImpl(String name, long instanceCounter, JTARuntimeMBean jtaRuntime) throws ManagementException {
      super("JTANamed_" + instanceCounter, jtaRuntime);
      jtaRuntime.addTransactionNameRuntimeMBean(this);
      this.transactionName = name;
   }

   public TransactionNameRuntimeImpl(String name, long instanceCounter, JTAPartitionRuntimeMBean jtaPartitionRuntime) throws ManagementException {
      super("JTANamed_" + instanceCounter, jtaPartitionRuntime);
      jtaPartitionRuntime.addTransactionNameRuntimeMBean(this);
      this.transactionName = name;
   }

   public String getTransactionName() {
      return this.transactionName;
   }

   public String toString() {
      return new String("{transactionName=" + this.transactionName + ", " + super.toString() + "}");
   }
}
