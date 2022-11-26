package weblogic.transaction.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.transaction.xa.Xid;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TransactionLogJDBCStoreMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.JTARecoveryRuntimeMBean;
import weblogic.management.runtime.JTARuntimeMBean;
import weblogic.management.runtime.JTATransaction;
import weblogic.management.runtime.NonXAResourceRuntimeMBean;
import weblogic.management.runtime.PersistentStoreRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.ServerStates;
import weblogic.management.runtime.TransactionNameRuntimeMBean;
import weblogic.management.runtime.TransactionResourceRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.store.PersistentStore;
import weblogic.store.admin.PersistentStoreRuntimeMBeanImpl;
import weblogic.transaction.Transaction;

public final class JTARuntimeImpl extends JTATransactionStatisticsImpl implements Constants, JTARuntimeMBean, JTAHealthListener, JTARuntime {
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final long serialVersionUID = -3773752601197689481L;
   public static final String OVERFLOW_NAME = "weblogic.transaction.statistics.namedOverflow";
   private long namedInstanceCounter;
   private HashMap namedStats = new HashMap();
   private TransactionNameRuntimeImpl namedOverflow = null;
   private ServerTransactionManagerImpl tm;
   private ArrayList registeredResources = new ArrayList(5);
   private ArrayList registeredNonXAResources = new ArrayList(5);
   private Health health = new Health();
   private HealthFeedback jdbcStoreHealthFeedback;
   private Health jdbcStoreHealth = new Health();
   private boolean isJDBCStoreHealthRegistered;
   private boolean isAutomaticMigrationMode = true;
   private static final String HEALTH_JTA_SUBSYSTEM_NAME = "JTA";
   private static final String HEALTH_JTA_JDBCSTORE_SUBSYSTEM_NAME = "JTA_JDBCSTORE";
   private volatile Set txNameRuntimes = new HashSet();
   private Set txResourceRuntimes = new HashSet();
   private Set nonXAResourceRuntimes = new HashSet();
   private PersistentStoreRuntimeMBeanImpl persistentStoreRuntimeMBean;

   public JTARuntimeImpl(String name, ServerTransactionManagerImpl atm) throws ManagementException {
      super(name, ManagementService.getRuntimeAccess(kernelID).getServerRuntime());
      ManagementService.getRuntimeAccess(kernelID).getServerRuntime().setJTARuntime(this);
      this.tm = atm;
   }

   JTARuntimeImpl(String name, ServerTransactionManagerImpl atm, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
      this.tm = atm;
   }

   JTARuntimeImpl(String name, ServerTransactionManagerImpl atm, ServerRuntimeMBean parent) throws ManagementException {
      super(name, parent);
      this.tm = atm;
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

   public JTATransaction[] getJTATransactions() {
      return this.getTransactionsOlderThan(new Integer(0));
   }

   public JTATransaction[] getTransactionsOlderThan(Integer seconds) {
      ArrayList al = new ArrayList();
      Iterator txs = this.tm.getTransactions();
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

   public String[] getRegisteredResourceNames() {
      return (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
   }

   public String[] getRegisteredNonXAResourceNames() {
      return (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
   }

   public int getActiveTransactionsTotalCount() {
      return this.tm == null ? 0 : this.tm.getNumTransactions();
   }

   public JTARecoveryRuntimeMBean[] getRecoveryRuntimeMBeans() {
      return TransactionRecoveryService.getAllRuntimeMBeans();
   }

   public JTARecoveryRuntimeMBean getRecoveryRuntimeMBean(String serverName) {
      return (JTARecoveryRuntimeMBean)TransactionRecoveryService.getRuntimeMBean(serverName);
   }

   public HealthState getHealthState() {
      return this.health.getState();
   }

   public TransactionResourceRuntime registerResource(String aName) throws Exception {
      return (TransactionResourceRuntime)SecurityServiceManager.runAs(kernelID, kernelID, new CreateResourceMBeanAction(this, aName, this.registeredResources));
   }

   public void unregisterResource(TransactionResourceRuntime aMBean) throws Exception {
      SecurityServiceManager.runAs(kernelID, kernelID, new UnregisterResourceMBeanAction(this, (TransactionResourceRuntimeMBean)aMBean, this.registeredResources));
      this.healthEvent(new HealthEvent(7, aMBean.getResourceName(), "Resource " + aMBean.getResourceName() + " has been unregistered"));
   }

   public NonXAResourceRuntime registerNonXAResource(String aName) throws Exception {
      return (NonXAResourceRuntime)SecurityServiceManager.runAs(kernelID, kernelID, new CreateNonXAResourceMBeanAction(this, aName, this.registeredNonXAResources));
   }

   public void unregisterNonXAResource(NonXAResourceRuntime aMBean) throws Exception {
      SecurityServiceManager.runAs(kernelID, kernelID, new UnregisterNonXAResourceMBeanAction(this, (NonXAResourceRuntimeMBean)aMBean, this.registeredNonXAResources));
   }

   public void healthEvent(HealthEvent aEvent) {
      this.health.healthEvent(aEvent);
   }

   public void forceLocalRollback(Xid xid) throws RemoteException {
      TXLogger.logForceLocalRollbackInvoked(xid.toString());
      ServerTransactionImpl tx = (ServerTransactionImpl)this.tm.getTransaction(xid);
      if (tx == null) {
         TXLogger.logForceLocalRollbackNoTx(xid.toString());
         throw new RemoteException("forceLocalRollback invoked on unknown transaction '" + xid + "'");
      } else {
         try {
            tx.forceLocalRollback();
         } catch (Exception var4) {
            TXLogger.logForceLocalRollbackFailed(xid.toString(), var4);
            throw new RemoteException("Unable to perform local rollback", var4);
         }
      }
   }

   public void forceGlobalRollback(Xid xid) throws RemoteException {
      TXLogger.logForceGlobalRollbackInvoked(xid.toString());
      ServerTransactionImpl tx = (ServerTransactionImpl)this.tm.getTransaction(xid);
      if (tx == null) {
         TXLogger.logForceGlobalRollbackNoTx(xid.toString());
         throw new RemoteException("forceLocalRollback invoked on unknown transaction '" + xid + "'");
      } else {
         try {
            tx.forceGlobalRollback();
         } catch (Exception var4) {
            TXLogger.logForceGlobalRollbackFailed(xid.toString(), var4);
            throw new RemoteException("Unable to perform global rollback", var4);
         }
      }
   }

   public void forceLocalCommit(Xid xid) throws RemoteException {
      TXLogger.logForceLocalCommitInvoked(xid.toString());
      ServerTransactionImpl tx = (ServerTransactionImpl)this.tm.getTransaction(xid);
      if (tx == null) {
         TXLogger.logForceLocalCommitNoTx(xid.toString());
         throw new RemoteException("forceLocalCommit invoked on unknown transaction '" + xid + "'");
      } else {
         try {
            tx.forceLocalCommit();
         } catch (Exception var4) {
            TXLogger.logForceLocalCommitFailed(xid.toString(), var4);
            throw new RemoteException("Unable to perform local commit", var4);
         }
      }
   }

   public void forceGlobalCommit(Xid xid) throws RemoteException {
      TXLogger.logForceGlobalCommitInvoked(xid.toString());
      ServerTransactionImpl tx = (ServerTransactionImpl)this.tm.getTransaction(xid);
      if (tx == null) {
         TXLogger.logForceGlobalCommitNoTx(xid.toString());
         throw new RemoteException("forceLocalCommit invoked on unknown transaction '" + xid + "'");
      } else {
         try {
            tx.forceGlobalCommit();
         } catch (Exception var4) {
            TXLogger.logForceGlobalCommitFailed(xid.toString(), var4);
            throw new RemoteException("Unable to perform global commit", var4);
         }
      }
   }

   public JTATransaction getJTATransaction(String xidstr) throws RemoteException {
      Xid xid = XidImpl.create(xidstr);
      if (xid == null) {
         return null;
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.tm.getTransaction(xid);
         return tx == null ? null : new JTATransactionImpl(tx);
      }
   }

   void registerWithHealthService() {
      if (TxDebug.JTAHealth.isDebugEnabled()) {
         TxDebug.JTAHealth.debug("Registering JTARuntimeMBean with Health Monitoring Service");
      }

      HealthMonitorService.register("JTA", this, true);
      if (this.isJDBCTLogEnabled()) {
         TxDebug.JTAHealth.debug("Registering JTA JDBC Store with Health Monitoring Service");
         HealthMonitorService.register("JTA_JDBCSTORE", this.getOrCreateJDBCStoreHealthState(), false);
         this.health.setJDBCStoreHealth(this.jdbcStoreHealth);
         this.isJDBCStoreHealthRegistered = true;
      }

   }

   void unregisterFromHealthService() {
      if (TxDebug.JTAHealth.isDebugEnabled()) {
         TxDebug.JTAHealth.debug("Unregistering JTARuntimeMBean from Health Monitoring Service");
      }

      HealthMonitorService.unregister("JTA");
      if (this.isJDBCStoreHealthRegistered) {
         if (TxDebug.JTAHealth.isDebugEnabled()) {
            TxDebug.JTAHealth.debug("Unregistering JTA JDBC Store from Health Monitoring Service");
         }

         HealthMonitorService.unregister("JTA_JDBCSTORE");
      }

   }

   private HealthFeedback getOrCreateJDBCStoreHealthState() {
      if (this.jdbcStoreHealthFeedback == null) {
         this.jdbcStoreHealth = new Health();
         this.jdbcStoreHealthFeedback = new HealthFeedback() {
            public HealthState getHealthState() {
               return JTARuntimeImpl.this.jdbcStoreHealth.getState();
            }
         };
      }

      return this.jdbcStoreHealthFeedback;
   }

   public PersistentStoreRuntimeMBean getTransactionLogStoreRuntimeMBean() {
      ServerRuntimeMBean serverRuntime = (ServerRuntimeMBean)this.parent;
      String serverName = serverRuntime.getName();
      String storeName = this.tm.getJdbcTLogEnabled() ? ((PersistentStore)((PersistentStore)PlatformHelper.getPlatformHelper().getPrimaryStore())).getName() : "_WLS_" + serverName;
      return serverRuntime.lookupPersistentStoreRuntime(storeName);
   }

   private boolean isJDBCTLogEnabled() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelID);
      if (runtimeAccess != null) {
         ServerMBean serverMBean = ManagementService.getRuntimeAccess(kernelID).getServer();
         if (serverMBean != null) {
            TransactionLogJDBCStoreMBean jdbclogMb = serverMBean.getTransactionLogJDBCStore();
            if (jdbclogMb != null) {
               return jdbclogMb.isEnabled();
            }
         }
      }

      return false;
   }

   private boolean checkAutomaticMigrationMode() {
      this.isAutomaticMigrationMode = ManagementService.getRuntimeAccess(kernelID) == null ? false : TransactionRecoveryService.isAutomaticMigrationMode();
      return this.isAutomaticMigrationMode;
   }

   public boolean isAutomaticMigrationMode() {
      return this.isAutomaticMigrationMode;
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

   public String getHealthStateString() {
      return HealthState.mapToString(this.health.getState().getState());
   }

   public String[] getHealthStateReasonCodes() {
      return this.health.getState().getReasonCode();
   }

   public String getTransactionServiceState() {
      return ServerStates.SERVERSTATES[TransactionService.getState()];
   }

   public String getDBPassiveModeState() {
      return this.tm.getDBPassiveModeStateName();
   }

   private class UnregisterNonXAResourceMBeanAction implements PrivilegedExceptionAction {
      private JTARuntimeImpl jtaRuntime;
      private NonXAResourceRuntimeMBean mbean;
      private ArrayList registeredNonXAResources;

      UnregisterNonXAResourceMBeanAction(JTARuntimeImpl aJTARuntime, NonXAResourceRuntimeMBean aMBean, ArrayList aRegisteredNonXAResources) {
         this.jtaRuntime = aJTARuntime;
         this.mbean = aMBean;
         this.registeredNonXAResources = aRegisteredNonXAResources;
      }

      public Object run() throws Exception {
         String name = this.mbean.getNonXAResourceName();
         this.registeredNonXAResources.remove(name);
         String[] oldResources = (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
         this.registeredNonXAResources.remove(name);
         this.jtaRuntime.removeNonXAResourceRuntimeMBean(this.mbean);
         ((NonXAResourceRuntimeImpl)this.mbean).unregister();
         String[] newResources = (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
         JTARuntimeImpl.this._postSet("RegisteredNonXAResourceNames", oldResources, newResources);
         return null;
      }
   }

   private class CreateNonXAResourceMBeanAction implements PrivilegedExceptionAction {
      private JTARuntimeImpl jtaRuntime;
      private String name;
      private ArrayList registeredNonXAResources;

      CreateNonXAResourceMBeanAction(JTARuntimeImpl aJTARuntime, String aName, ArrayList aRegisteredNonXAResources) {
         this.jtaRuntime = aJTARuntime;
         this.name = aName;
         this.registeredNonXAResources = aRegisteredNonXAResources;
      }

      public Object run() throws Exception {
         NonXAResourceRuntimeImpl mbean = new NonXAResourceRuntimeImpl(this.name, this.jtaRuntime);
         String[] oldResources = (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
         this.registeredNonXAResources.add(this.name);
         String[] newResources = (String[])((String[])this.registeredNonXAResources.toArray(new String[this.registeredNonXAResources.size()]));
         JTARuntimeImpl.this._postSet("RegisteredNonXAResourceNames", oldResources, newResources);
         return mbean;
      }
   }

   private class UnregisterResourceMBeanAction implements PrivilegedExceptionAction {
      private JTARuntimeImpl jtaRuntime;
      private TransactionResourceRuntimeMBean mbean;
      private ArrayList registeredResources;

      UnregisterResourceMBeanAction(JTARuntimeImpl aJTARuntime, TransactionResourceRuntimeMBean aMBean, ArrayList aRegisteredResources) {
         this.jtaRuntime = aJTARuntime;
         this.mbean = aMBean;
         this.registeredResources = aRegisteredResources;
      }

      public Object run() throws Exception {
         this.jtaRuntime.removeTransactionResourceRuntimeMBean(this.mbean);
         ((TransactionResourceRuntimeImpl)this.mbean).unregister();
         String name = this.mbean.getResourceName();
         String[] oldResources = (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
         this.registeredResources.remove(name);
         String[] newResources = (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
         JTARuntimeImpl.this._postSet("RegisteredResourceNames", oldResources, newResources);
         return null;
      }
   }

   private class CreateResourceMBeanAction implements PrivilegedExceptionAction {
      private JTARuntimeImpl jtaRuntime;
      private String name;
      private ArrayList registeredResources;

      CreateResourceMBeanAction(JTARuntimeImpl aJTARuntime, String aName, ArrayList aRegisteredResources) {
         this.jtaRuntime = aJTARuntime;
         this.name = aName;
         this.registeredResources = aRegisteredResources;
      }

      public Object run() throws Exception {
         TransactionResourceRuntimeImpl mbean = new TransactionResourceRuntimeImpl(this.name, this.jtaRuntime);
         String[] oldResources = (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
         this.registeredResources.add(this.name);
         String[] newResources = (String[])((String[])this.registeredResources.toArray(new String[this.registeredResources.size()]));
         JTARuntimeImpl.this._postSet("RegisteredResourceNames", oldResources, newResources);
         return mbean;
      }
   }

   private static class CreateNamedMBeanAction implements PrivilegedExceptionAction {
      private String name;
      private long instanceCounter;
      private JTARuntimeImpl jtaRuntime;

      CreateNamedMBeanAction(String aName, long aInstanceCounter, JTARuntimeImpl aJTARuntime) {
         this.name = aName;
         this.instanceCounter = aInstanceCounter;
         this.jtaRuntime = aJTARuntime;
      }

      public Object run() throws Exception {
         return new TransactionNameRuntimeImpl(this.name, this.instanceCounter, this.jtaRuntime);
      }
   }

   class Health {
      private HealthEvent tlogEvent;
      private HealthEvent txmapEvent;
      private HashMap resourceEvents = new HashMap();
      private HealthState state = new HealthState(0);
      private Health jdbcStoreHealth;

      void setJDBCStoreHealth(Health health) {
         this.jdbcStoreHealth = health;
      }

      void healthEvent(HealthEvent aEvent) {
         this.healthEvent(aEvent, false);
      }

      void healthEvent(HealthEvent aEvent, boolean isJDBC) {
         if (aEvent != null) {
            synchronized(this) {
               if (!isJDBC && ("JDBCSTORE_FAILURE".equals(aEvent.getName()) || "JDBCSTORE_RECOVERED".equals(aEvent.getName()))) {
                  if (this.jdbcStoreHealth != null) {
                     this.jdbcStoreHealth.healthEvent(aEvent, true);
                  }

               } else {
                  switch (aEvent.getType()) {
                     case 1:
                        if (TxDebug.JTAHealth.isDebugEnabled()) {
                           TxDebug.JTAHealth.debug("JTA Health: health event TLOG_FAILURE");
                        }

                        this.tlogEvent = aEvent;
                        this.updateState();
                        break;
                     case 2:
                        if (TxDebug.JTAHealth.isDebugEnabled()) {
                           TxDebug.JTAHealth.debug("JTA Health: health event TLOG_OK");
                        }

                        if (this.tlogEvent != null) {
                           this.tlogEvent = null;
                           this.updateState();
                        }
                        break;
                     case 3:
                        if (TxDebug.JTAHealth.isDebugEnabled()) {
                           TxDebug.JTAHealth.debug("JTA Health: health event TXMAP_FULL");
                        }

                        if (this.txmapEvent == null) {
                           this.txmapEvent = aEvent;
                        }

                        this.updateState();
                        break;
                     case 4:
                        if (TxDebug.JTAHealth.isDebugEnabled()) {
                           TxDebug.JTAHealth.debug("JTA Health: health event TXMAP_OK");
                        }

                        if (this.txmapEvent != null) {
                           this.txmapEvent = null;
                           this.updateState();
                        }
                        break;
                     case 5:
                        if (TxDebug.JTAHealth.isDebugEnabled()) {
                           TxDebug.JTAHealth.debug("JTA Health: health event RESOURCE_UNHEALTHY (" + aEvent.getName() + ")");
                        }

                        this.resourceEvents.put(aEvent.getName(), aEvent);
                        this.updateState();
                        break;
                     case 6:
                        if (TxDebug.JTAHealth.isDebugEnabled()) {
                           TxDebug.JTAHealth.debug("JTA Health: health event RESOURCE_HEALTHY (" + aEvent.getName() + ")");
                        }

                        if (this.resourceEvents.remove(aEvent.getName()) != null) {
                           this.updateState();
                        }
                        break;
                     case 7:
                        if (TxDebug.JTAHealth.isDebugEnabled()) {
                           TxDebug.JTAHealth.debug("JTA Health: health event RESOURCE_UNREGISTERED (" + aEvent.getName() + ")");
                        }

                        if (this.resourceEvents.remove(aEvent.getName()) != null) {
                           this.updateState();
                        }
                        break;
                     default:
                        if (TxDebug.JTAHealth.isDebugEnabled()) {
                           TxDebug.JTAHealth.debug("JTA Health: unknown event type: " + aEvent.getType());
                        }
                  }

                  if (!isJDBC && this.state.getState() == 3 && JTARuntimeImpl.this.checkAutomaticMigrationMode()) {
                     TXExceptionLogger.logJTAFailedAndForceShutdown();
                     HealthMonitorService.subsystemFailedForceShutdown("JTA", TXExceptionLogger.logJTAFailedAndForceShutdownLoggable().getMessage());
                  }

               }
            }
         }
      }

      void updateState() {
         int newState = 0;
         ArrayList symptoms = new ArrayList();
         if (this.tlogEvent != null && this.tlogEvent.getType() == 1) {
            newState = 3;
            symptoms.add(new Symptom(SymptomType.TRANSACTION, Severity.HIGH, this.tlogEvent.getName(), this.tlogEvent.getDescription()));
         }

         if (this.txmapEvent != null && this.txmapEvent.getType() == 3) {
            long timeSinceFirstFullEvent = System.currentTimeMillis() - this.txmapEvent.getTimestamp();
            if (timeSinceFirstFullEvent > JTARuntimeImpl.this.tm.getMaxTransactionsHealthIntervalMillis()) {
               if (newState != 3 && newState != 4) {
                  newState = 4;
               }

               symptoms.add(new Symptom(SymptomType.TRANSACTION, Symptom.healthStateSeverity(newState), this.txmapEvent.getName(), this.txmapEvent.getDescription() + " (full for " + timeSinceFirstFullEvent + " ms which is longer than the failure threshold of " + JTARuntimeImpl.this.tm.getMaxTransactionsHealthIntervalMillis() + " ms)"));
            } else {
               if (newState == 0) {
                  newState = 1;
               }

               symptoms.add(new Symptom(SymptomType.TRANSACTION, Symptom.healthStateSeverity(newState), this.txmapEvent.getName(), this.txmapEvent.getDescription()));
            }
         }

         if (this.resourceEvents.size() > 0) {
            if (newState == 0) {
               newState = 1;
            }

            Iterator it = this.resourceEvents.values().iterator();

            while(it.hasNext()) {
               HealthEvent resEvent = (HealthEvent)it.next();
               symptoms.add(new Symptom(SymptomType.TRANSACTION, Symptom.healthStateSeverity(newState), resEvent.getName(), resEvent.getDescription()));
            }
         }

         HealthState oldState = this.state;
         this.state = new HealthState(newState, (Symptom[])symptoms.toArray(new Symptom[symptoms.size()]));
         JTARuntimeImpl.this._postSet("HealthState", oldState, this.state);
         if (TxDebug.JTAHealth.isDebugEnabled()) {
            TxDebug.JTAHealth.debug("JTA Health: new state = " + this.state);
         }

         if (this.state.getState() != oldState.getState() || this.state.getReasonCode().length != oldState.getReasonCode().length) {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < symptoms.size(); ++i) {
               if (i > 0) {
                  sb.append("; ");
               }

               sb.append(symptoms.get(i));
            }

            switch (this.state.getState()) {
               case 0:
                  TXLogger.logHealthOK(HealthState.mapToString(oldState.getState()), HealthState.mapToString(this.state.getState()));
                  break;
               case 1:
               case 4:
                  TXLogger.logHealthWarning(HealthState.mapToString(oldState.getState()), HealthState.mapToString(this.state.getState()), sb.toString());
               case 2:
               default:
                  break;
               case 3:
                  TXLogger.logHealthError(HealthState.mapToString(oldState.getState()), HealthState.mapToString(this.state.getState()), sb.toString());
            }
         }

      }

      HealthState getState() {
         return this.state;
      }
   }
}
