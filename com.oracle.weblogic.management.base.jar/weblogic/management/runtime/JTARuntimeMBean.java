package weblogic.management.runtime;

import java.rmi.RemoteException;
import javax.transaction.xa.Xid;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;

public interface JTARuntimeMBean extends JTATransactionStatisticsRuntimeMBean, HealthFeedback {
   TransactionNameRuntimeMBean[] getTransactionNameRuntimeMBeans() throws RemoteException;

   boolean addTransactionNameRuntimeMBean(TransactionNameRuntimeMBean var1);

   boolean removeTransactionNameRuntimeMBean(TransactionNameRuntimeMBean var1);

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

   int getActiveTransactionsTotalCount();

   JTARecoveryRuntimeMBean[] getRecoveryRuntimeMBeans();

   JTARecoveryRuntimeMBean getRecoveryRuntimeMBean(String var1);

   HealthState getHealthState();

   void forceLocalRollback(Xid var1) throws RemoteException;

   void forceGlobalRollback(Xid var1) throws RemoteException;

   void forceLocalCommit(Xid var1) throws RemoteException;

   void forceGlobalCommit(Xid var1) throws RemoteException;

   JTATransaction getJTATransaction(String var1) throws RemoteException;

   PersistentStoreRuntimeMBean getTransactionLogStoreRuntimeMBean();

   String getTransactionServiceState();

   String getDBPassiveModeState();
}
