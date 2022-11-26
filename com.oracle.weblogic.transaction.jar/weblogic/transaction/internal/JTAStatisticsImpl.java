package weblogic.transaction.internal;

import weblogic.management.MBeanCreationException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JTAStatisticsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public abstract class JTAStatisticsImpl extends RuntimeMBeanDelegate implements JTAStatisticsRuntimeMBean {
   public JTAStatisticsImpl(String name, RuntimeMBean parent) throws MBeanCreationException, ManagementException {
      super(name, parent);
   }

   public abstract long getTransactionTotalCount();

   public abstract long getTransactionCommittedTotalCount();

   public abstract long getTransactionRolledBackTotalCount();

   public abstract long getTransactionHeuristicsTotalCount();

   public String toString() {
      StringBuffer sb = new StringBuffer(64);
      sb.append("{");
      sb.append("transactionTotalCount=").append(this.getTransactionTotalCount());
      sb.append(", ");
      sb.append("transactionCommittedTotalCount=").append(this.getTransactionCommittedTotalCount());
      sb.append(", ");
      sb.append("transactionRolledBackTotalCount=").append(this.getTransactionRolledBackTotalCount());
      sb.append(", ");
      sb.append("transactionHeuristicsTotalCount=").append(this.getTransactionHeuristicsTotalCount());
      sb.append("}");
      return sb.toString();
   }
}
