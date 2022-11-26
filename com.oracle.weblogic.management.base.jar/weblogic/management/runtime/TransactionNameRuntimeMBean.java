package weblogic.management.runtime;

public interface TransactionNameRuntimeMBean extends JTATransactionStatisticsRuntimeMBean {
   String getTransactionName();
}
