package weblogic.management.runtime;

import java.rmi.RemoteException;

public interface JTAPartitionRuntimeMBean extends JTATransactionStatisticsRuntimeMBean {
   TransactionNameRuntimeMBean[] getTransactionNameRuntimeMBeans() throws RemoteException;

   boolean addTransactionNameRuntimeMBean(TransactionNameRuntimeMBean var1);

   boolean removeTransactionNameRuntimeMBean(TransactionNameRuntimeMBean var1);

   int getActiveTransactionsTotalCount();

   TransactionResourceRuntimeMBean[] getTransactionResourceRuntimeMBeans() throws RemoteException;

   boolean addTransactionResourceRuntimeMBean(TransactionResourceRuntimeMBean var1);

   boolean removeTransactionResourceRuntimeMBean(TransactionResourceRuntimeMBean var1);

   NonXAResourceRuntimeMBean[] getNonXAResourceRuntimeMBeans() throws RemoteException;

   boolean addNonXAResourceRuntimeMBean(NonXAResourceRuntimeMBean var1);

   boolean removeNonXAResourceRuntimeMBean(NonXAResourceRuntimeMBean var1);

   JTATransaction[] getTransactionsOlderThan(Integer var1);

   JTATransaction[] getJTATransactions();

   String[] getRegisteredResourceNames();

   String[] getRegisteredNonXAResourceNames();
}
