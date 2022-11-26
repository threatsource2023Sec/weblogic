package weblogic.transaction.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JTAPartitionRuntimeMBean;
import weblogic.management.runtime.JTATransaction;
import weblogic.management.runtime.NonXAResourceRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.TransactionNameRuntimeMBean;
import weblogic.management.runtime.TransactionResourceRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.Transaction;

public final class JTAPartitionRuntimeImpl extends JTATransactionStatisticsImpl implements Constants, JTAPartitionRuntimeMBean, JTAPartitionRuntime {
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final long serialVersionUID = -3773752601197689481L;
   public static final String OVERFLOW_NAME = "weblogic.transaction.statistics.namedOverflow";
   private long namedInstanceCounter;
   private HashMap namedStats = new HashMap();
   private TransactionNameRuntimeImpl namedOverflow = null;
   private ServerTransactionManagerImpl tm;
   private ArrayList registeredResources = new ArrayList(5);
   private ArrayList registeredNonXAResources = new ArrayList(5);
   private volatile Set txNameRuntimes = new HashSet();
   private Set txResourceRuntimes = new HashSet();
   private Set nonXAResourceRuntimes = new HashSet();
   private int timeoutSeconds;
   private String[] determiners;

   public JTAPartitionRuntimeImpl(String name, PartitionRuntimeMBean partitionRuntime, ServerTransactionManagerImpl atm) throws ManagementException {
      super(name, partitionRuntime);
      partitionRuntime.setJTAPartitionRuntime(this);
      this.tm = atm;
   }

   JTAPartitionRuntimeImpl(String name, ServerTransactionManagerImpl atm, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
      this.tm = atm;
   }

   JTAPartitionRuntimeImpl(String name, ServerTransactionManagerImpl atm, ServerRuntimeMBean parent) throws ManagementException {
      super(name, parent);
      this.tm = atm;
   }

   public void reset() {
      this.transactionCommittedTotalCount = 0L;
      this.transactionNoResourcesCommittedTotalCount = 0L;
      this.transactionOneResourceOnePhaseCommittedTotalCount = 0L;
      this.transactionReadOnlyOnePhaseCommittedTotalCount = 0L;
      this.transactionTwoPhaseCommittedTotalCount = 0L;
      this.transactionLLRCommittedTotalCount = 0L;
      this.transactionRolledBackTimeoutTotalCount = 0L;
      this.transactionRolledBackResourceTotalCount = 0L;
      this.transactionRolledBackAppTotalCount = 0L;
      this.transactionRolledBackSystemTotalCount = 0L;
      this.transactionAbandonedTotalCount = 0L;
      this.transactionHeuristicsTotalCount = 0L;
      this.millisecondsActiveTotalCount = 0L;
      this.transactionTwoPhaseCommittedLoggedTotalCount = 0L;
      this.transactionTwoPhaseCommittedNotLoggedTotalCount = 0L;
   }

   public void setTimeoutSeconds(int to) {
      this.timeoutSeconds = to;
   }

   public int getTimeoutSeconds() {
      return this.timeoutSeconds;
   }

   public void setDeterminers(String[] determiners) {
      if (determiners != null) {
         this.determiners = (String[])determiners.clone();
      }

   }

   public String[] getDeterminers() {
      return this.determiners;
   }

   public void tallyCompletion(Transaction tx) {
      super.tallyCompletion(tx);
      String txname = tx.getName();
      TransactionNameRuntimeImpl named = null;
      if (txname != null) {
         synchronized(this.namedStats) {
            named = (TransactionNameRuntimeImpl)this.namedStats.get(txname);
            if (named == null) {
               if (this.namedStats.size() >= this.tm.getMaxUniqueNameStatistics()) {
                  if (this.namedOverflow == null) {
                     try {
                        this.namedOverflow = (TransactionNameRuntimeImpl)SecurityServiceManager.runAs(kernelID, kernelID, new CreateNamedMBeanAction("weblogic.transaction.statistics.namedOverflow", (long)(this.namedInstanceCounter++), this));
                     } catch (Exception var8) {
                     }
                  }

                  named = this.namedOverflow;
               } else {
                  try {
                     named = (TransactionNameRuntimeImpl)SecurityServiceManager.runAs(kernelID, kernelID, new CreateNamedMBeanAction(txname, (long)(this.namedInstanceCounter++), this));
                     this.namedStats.put(txname, named);
                  } catch (Exception var7) {
                  }
               }
            }
         }

         if (named != null) {
            named.tallyCompletion(tx);
         }
      }

   }

   public TransactionNameRuntimeMBean[] getTransactionNameRuntimeMBeans() throws RemoteException {
      return (TransactionNameRuntimeMBean[])((TransactionNameRuntimeMBean[])this.txNameRuntimes.toArray(new TransactionNameRuntimeMBean[this.txNameRuntimes.size()]));
   }

   public boolean addTransactionNameRuntimeMBean(TransactionNameRuntimeMBean a) {
      return this.txNameRuntimes.add(a);
   }

   public boolean removeTransactionNameRuntimeMBean(TransactionNameRuntimeMBean a) {
      return this.txNameRuntimes.remove(a);
   }

   public TransactionResourceRuntimeMBean[] getTransactionResourceRuntimeMBeans() throws RemoteException {
      int len = this.txResourceRuntimes.size();
      return (TransactionResourceRuntimeMBean[])((TransactionResourceRuntimeMBean[])this.txResourceRuntimes.toArray(new TransactionResourceRuntimeMBean[len]));
   }

   public boolean addTransactionResourceRuntimeMBean(TransactionResourceRuntimeMBean a) {
      return this.txResourceRuntimes.add(a);
   }

   public boolean removeTransactionResourceRuntimeMBean(TransactionResourceRuntimeMBean a) {
      return this.txResourceRuntimes.remove(a);
   }

   public NonXAResourceRuntimeMBean[] getNonXAResourceRuntimeMBeans() throws RemoteException {
      int len = this.nonXAResourceRuntimes.size();
      return (NonXAResourceRuntimeMBean[])((NonXAResourceRuntimeMBean[])this.nonXAResourceRuntimes.toArray(new NonXAResourceRuntimeMBean[len]));
   }

   public boolean addNonXAResourceRuntimeMBean(NonXAResourceRuntimeMBean a) {
      return this.nonXAResourceRuntimes.add(a);
   }

   public boolean removeNonXAResourceRuntimeMBean(NonXAResourceRuntimeMBean a) {
      return this.nonXAResourceRuntimes.remove(a);
   }

   public TransactionResourceRuntime registerResource(String aName) throws Exception {
      return (TransactionResourceRuntime)SecurityServiceManager.runAs(kernelID, kernelID, new CreateResourceMBeanAction(this, aName, this.registeredResources));
   }

   public void unregisterResource(TransactionResourceRuntime aMBean) throws Exception {
      SecurityServiceManager.runAs(kernelID, kernelID, new UnregisterResourceMBeanAction(this, (TransactionResourceRuntimeMBean)aMBean, this.registeredResources));
   }

   public NonXAResourceRuntime registerNonXAResource(String aName) throws Exception {
      return (NonXAResourceRuntime)SecurityServiceManager.runAs(kernelID, kernelID, new CreateNonXAResourceMBeanAction(this, aName, this.registeredNonXAResources));
   }

   public void unregisterNonXAResource(NonXAResourceRuntime aMBean) throws Exception {
      SecurityServiceManager.runAs(kernelID, kernelID, new UnregisterNonXAResourceMBeanAction(this, (NonXAResourceRuntimeMBean)aMBean, this.registeredNonXAResources));
   }

   public JTATransaction[] getJTATransactions() {
      return this.getTransactionsOlderThan(new Integer(0));
   }

   public JTATransaction[] getTransactionsOlderThan(Integer seconds) {
      ArrayList al = new ArrayList();
      if (this.tm == null) {
         return new JTATransaction[0];
      } else {
         PartitionRuntimeMBean prmb = (PartitionRuntimeMBean)this.getParent();
         if (prmb == null) {
            return new JTATransaction[0];
         } else {
            String partitionName = prmb.getName();
            Iterator txs = this.tm.getTransactions(partitionName);
            if (txs == null) {
               return new JTATransaction[0];
            } else {
               while(txs.hasNext()) {
                  ServerTransactionImpl tx = (ServerTransactionImpl)txs.next();
                  if (tx.getMillisSinceBegin() >= (long)(seconds * 1000)) {
                     al.add(new JTATransactionImpl(tx));
                  }
               }

               JTATransaction[] ret;
               if (al.size() > 0) {
                  al.trimToSize();
                  ret = new JTATransaction[al.size()];

                  for(int i = 0; i < al.size(); ++i) {
                     ret[i] = (JTATransaction)al.get(i);
                  }
               } else {
                  ret = new JTATransaction[0];
               }

               return ret;
            }
         }
      }
   }

   public String[] getRegisteredResourceNames() {
      return (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
   }

   public String[] getRegisteredNonXAResourceNames() {
      return (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
   }

   public int getActiveTransactionsTotalCount() {
      if (this.tm == null) {
         return 0;
      } else {
         PartitionRuntimeMBean prmb = (PartitionRuntimeMBean)this.getParent();
         if (prmb == null) {
            return 0;
         } else {
            String partitionName = prmb.getName();
            ConcurrentHashMap map = this.tm.txMap;
            Iterator itr = map.values().iterator();
            int mapSize = 0;

            while(itr.hasNext()) {
               ServerTransactionImpl stx = (ServerTransactionImpl)itr.next();
               Map global = stx.getProperties();
               if (partitionName != null && partitionName.equals(global.get("weblogic.transaction.partitionName"))) {
                  ++mapSize;
               }
            }

            return mapSize;
         }
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(512);
      sb.append("{");
      sb.append(super.toString());
      if (this.namedStats != null) {
         HashMap namedStatsClone = (HashMap)this.namedStats.clone();
         Iterator namedIt = namedStatsClone.values().iterator();

         while(namedIt.hasNext()) {
            TransactionNameRuntimeImpl nrt = (TransactionNameRuntimeImpl)namedIt.next();
            sb.append("\n");
            sb.append(nrt.toString());
         }

         sb.append("\n");
         sb.append(this.namedOverflow);
      }

      sb.append("}");
      return sb.toString();
   }

   private class UnregisterNonXAResourceMBeanAction implements PrivilegedExceptionAction {
      private JTAPartitionRuntimeImpl jtaPartitionRuntime;
      private NonXAResourceRuntimeMBean mbean;
      private ArrayList registeredNonXAResources;

      UnregisterNonXAResourceMBeanAction(JTAPartitionRuntimeImpl aJTAPartitionRuntime, NonXAResourceRuntimeMBean aMBean, ArrayList aRegisteredNonXAResources) {
         this.jtaPartitionRuntime = aJTAPartitionRuntime;
         this.mbean = aMBean;
         this.registeredNonXAResources = aRegisteredNonXAResources;
      }

      public Object run() throws Exception {
         String name = this.mbean.getNonXAResourceName();
         this.registeredNonXAResources.remove(name);
         String[] oldResources = (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
         this.registeredNonXAResources.remove(name);
         this.jtaPartitionRuntime.removeNonXAResourceRuntimeMBean(this.mbean);
         ((NonXAResourceRuntimeImpl)this.mbean).unregister();
         String[] newResources = (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
         JTAPartitionRuntimeImpl.this._postSet("RegisteredNonXAResourceNames", oldResources, newResources);
         return null;
      }
   }

   private class CreateNonXAResourceMBeanAction implements PrivilegedExceptionAction {
      private JTAPartitionRuntimeImpl jtaPartitionRuntime;
      private String name;
      private ArrayList registeredNonXAResources;

      CreateNonXAResourceMBeanAction(JTAPartitionRuntimeImpl aJTAPartitionRuntime, String aName, ArrayList aRegisteredNonXAResources) {
         this.jtaPartitionRuntime = aJTAPartitionRuntime;
         this.name = aName;
         this.registeredNonXAResources = aRegisteredNonXAResources;
      }

      public Object run() throws Exception {
         NonXAResourceRuntimeImpl mbean = new NonXAResourceRuntimeImpl(this.name, this.jtaPartitionRuntime);
         String[] oldResources = (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
         this.registeredNonXAResources.add(this.name);
         String[] newResources = (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
         JTAPartitionRuntimeImpl.this._postSet("RegisteredNonXAResourceNames", oldResources, newResources);
         return mbean;
      }
   }

   private class UnregisterResourceMBeanAction implements PrivilegedExceptionAction {
      private JTAPartitionRuntimeImpl jtaPartitionRuntime;
      private TransactionResourceRuntimeMBean mbean;
      private ArrayList registeredResources;

      UnregisterResourceMBeanAction(JTAPartitionRuntimeImpl aJTAPartitionRuntime, TransactionResourceRuntimeMBean aMBean, ArrayList aRegisteredResources) {
         this.jtaPartitionRuntime = aJTAPartitionRuntime;
         this.mbean = aMBean;
         this.registeredResources = aRegisteredResources;
      }

      public Object run() throws Exception {
         this.jtaPartitionRuntime.removeTransactionResourceRuntimeMBean(this.mbean);
         ((TransactionResourceRuntimeImpl)this.mbean).unregister();
         String name = this.mbean.getResourceName();
         String[] oldResources = (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
         this.registeredResources.remove(name);
         String[] newResources = (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
         JTAPartitionRuntimeImpl.this._postSet("RegisteredResourceNames", oldResources, newResources);
         return null;
      }
   }

   private class CreateResourceMBeanAction implements PrivilegedExceptionAction {
      private JTAPartitionRuntimeImpl jtaPartitionRuntime;
      private String name;
      private ArrayList registeredResources;

      CreateResourceMBeanAction(JTAPartitionRuntimeImpl aJTAPartitionRuntime, String aName, ArrayList aRegisteredResources) {
         this.jtaPartitionRuntime = aJTAPartitionRuntime;
         this.name = aName;
         this.registeredResources = aRegisteredResources;
      }

      public Object run() throws Exception {
         TransactionResourceRuntimeImpl mbean = new TransactionResourceRuntimeImpl(this.name, this.jtaPartitionRuntime);
         String[] oldResources = (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
         this.registeredResources.add(this.name);
         String[] newResources = (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
         JTAPartitionRuntimeImpl.this._postSet("RegisteredResourceNames", oldResources, newResources);
         return mbean;
      }
   }

   private static class CreateNamedMBeanAction implements PrivilegedExceptionAction {
      private String name;
      private long instanceCounter;
      private JTAPartitionRuntimeImpl jtaPartitionRuntime;

      CreateNamedMBeanAction(String aName, long aInstanceCounter, JTAPartitionRuntimeImpl aJTAPartitionRuntime) {
         this.name = aName;
         this.instanceCounter = aInstanceCounter;
         this.jtaPartitionRuntime = aJTAPartitionRuntime;
      }

      public Object run() throws Exception {
         return new TransactionNameRuntimeImpl(this.name, this.instanceCounter, this.jtaPartitionRuntime);
      }
   }
}
