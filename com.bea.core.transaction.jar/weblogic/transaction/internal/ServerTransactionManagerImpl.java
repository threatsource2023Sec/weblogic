package weblogic.transaction.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.BeginNotificationListener;
import weblogic.transaction.PeerExchangeTransaction;
import weblogic.transaction.ServerTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionLogger;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.loggingresource.LoggingResourceException;
import weblogic.transaction.loggingresource.MigratableLoggingResource;
import weblogic.transaction.nonxa.NonXAResource;

@Service
public class ServerTransactionManagerImpl extends TransactionManagerImpl implements ServerTransactionManager, FastThreadLocalMarker {
   private static final long serialVersionUID = 1094759388539309609L;
   static final int LLR_RECORD_VERSION = 90;
   private static boolean forgetHeuristics = true;
   private static int beforeCompletionIterationLimit = 10;
   private static int maxTransactions = 10000;
   private static int maxUniqueNameStatistics = 1000;
   private static long maxTransactionsHealthIntervalMillis = 60000L;
   private static int interopMode = 0;
   private JTARuntime runtime = null;
   private volatile ConcurrentHashMap jtaPartitionRuntimes = null;
   private String serverName = null;
   private CoordinatorDescriptor localCoordinatorDescriptor = null;
   private TransactionLogger tlog;
   private TransactionLogger hlog;
   private long lastCheckpointTime;
   private long checkpointIntervalMillis = 300000L;
   private long unregisterResourceGracePeriodMillis = 30000L;
   private long lastMigrationCheckpointTime;
   private static final long MIGRATION_CHECKPOINT_INTERVAL_SECONDS = 60L;
   private static long migrationCheckpointIntervalMillis = 60000L;
   private boolean txmapFull = false;
   private boolean allCheckpointedLLRsRegistered = !PlatformHelper.getPlatformHelper().isCheckpointLLR();
   private static boolean parallelXAEnabled;
   private static boolean twoPhaseEnabled;
   private static boolean memberOfCluster = false;
   private JTAClusterConfig jtaClusterConfig = null;
   private static String parallelXADispatchPolicy;
   private ArrayList beginNotificationListeners = new ArrayList(1);
   private int numRegisteredBeginNotificationListeners;
   private static Hashtable coordinatorServices = new Hashtable();
   private Throwable llrBootException;
   private Throwable primaryStoreBootException;
   private int llrCurrentRecoveredTransactionCount = 0;
   private int llrCompletedRecoveredTransactionCount;
   private HashSet checkpointedLLRs = new HashSet();
   private Object checkpointedLLRsLock = new Object();
   final Object addToListOfAckCommitsLock = new Object();
   private Coordinator localCoordinator;
   private static final boolean INSTR_ENABLED;
   List registeredLoggingResourceTransactionsList;
   private int m_overideTransactionTimeout = -1;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean isLLRInPartitionAllowed;
   private AtomicInteger passiveModeState = new AtomicInteger(0);
   private AtomicInteger passiveModeGracePeriodSeconds = new AtomicInteger();
   private Set usePublicAddressesForRemoteDomains;
   private Set useNonSecureAddressesForRemoteDomains;
   private volatile boolean isDetererminersProcessed = false;
   static MigratableRM m_migratableRM;

   ServerTransactionManagerImpl(JTAConfig aConfig, String aServerName) {
      this.serverName = aServerName;
      this.configure(aConfig);
      replaceTransactionManager(this);
      PlatformHelper.getPlatformHelper().initLoggingResourceRetry();
   }

   void setJTARuntime(JTARuntime jtaRuntime) {
      this.runtime = jtaRuntime;
   }

   void setJTAPartitionRuntime(String partitionName, JTAPartitionRuntime jtaPartitionRuntime) {
      if (this.jtaPartitionRuntimes == null) {
         synchronized(this) {
            if (this.jtaPartitionRuntimes == null) {
               this.jtaPartitionRuntimes = new ConcurrentHashMap();
            }
         }
      }

      this.jtaPartitionRuntimes.put(partitionName, jtaPartitionRuntime);
   }

   final ServerTransactionImpl getOrCreateTransaction(Xid xid, int aTimeoutSec, int aTimeToLiveSec) throws SystemException {
      ServerTransactionImpl tx = (ServerTransactionImpl)this.getTransaction(xid);
      return tx == null ? (ServerTransactionImpl)this.createTransaction(xid, aTimeToLiveSec, aTimeToLiveSec) : tx;
   }

   public boolean isLocalCoordinator(CoordinatorDescriptor aCoDesc) {
      return aCoDesc == null ? false : aCoDesc.equals(this.getLocalCoordinatorDescriptor());
   }

   public boolean isLocalCoordinator(String scURL) {
      if (scURL == null) {
         return false;
      } else {
         CoordinatorDescriptor cd = this.getLocalCoordinatorDescriptor();
         return cd == null ? false : cd.representsCoordinatorURL(scURL);
      }
   }

   private void setLLRBootException(Throwable t) {
      this.llrBootException = t;
      TXLogger.logFailedLLRBoot(t);
   }

   Throwable getLLRBootException() {
      return this.llrBootException;
   }

   private void setPrimaryStoreBootException(Throwable t) {
      this.primaryStoreBootException = t;
      TXLogger.logFailedPrimaryStoreBoot(t);
   }

   Throwable getPrimaryStoreBootException() {
      return this.primaryStoreBootException;
   }

   public String toString() {
      StringBuffer sb = (new StringBuffer()).append("ServerTM");
      if (this.getLocalCoordinatorDescriptor() != null) {
         sb.append("[").append(this.getLocalCoordinatorDescriptor()).append("]");
      }

      return sb.toString();
   }

   public final void begin() throws NotSupportedException, SystemException {
      this.internalBegin((String)null, 0);
   }

   public final void begin(String name) throws NotSupportedException, SystemException {
      this.internalBegin(name, 0);
   }

   public final void begin(String name, int timeoutseconds) throws NotSupportedException, SystemException {
      this.internalBegin(name, timeoutseconds);
   }

   protected final void internalBegin(String name, int timeoutseconds) throws NotSupportedException, SystemException {
      if (this.m_overideTransactionTimeout != -1) {
         timeoutseconds = this.m_overideTransactionTimeout;
      }

      if (!this.isDetererminersProcessed) {
         this.setDeterminers(this.getDeterminers());
         this.isDetererminersProcessed = true;
      }

      if (this != getTransactionManager()) {
         getTransactionManager().begin(name, timeoutseconds);
      } else {
         if (this.numRegisteredBeginNotificationListeners > 0) {
            synchronized(this.beginNotificationListeners) {
               for(int i = 0; i < this.numRegisteredBeginNotificationListeners; ++i) {
                  BeginListener listener = (BeginListener)this.beginNotificationListeners.get(i);
                  if (listener.componentInvocationContext == null) {
                     listener.listener.beginNotification(listener.handback);
                  } else {
                     this.partitionBeginNotification(listener);
                  }
               }
            }
         }

         super.internalBegin(name, timeoutseconds);
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTransaction();
         SystemException se = tx.enlistStaticallyEnlistedResources(false);
         if (se != null) {
            throw se;
         }
      }
   }

   public final void resume(Transaction atx) throws InvalidTransactionException, IllegalStateException, SystemException {
      if (this != getTransactionManager()) {
         getTransactionManager().resume(atx);
      } else {
         super.resume(atx);
         ServerTransactionImpl tx = (ServerTransactionImpl)atx;
         if (tx != null && tx.isActive()) {
            tx.enlistStaticallyEnlistedResources(true);
         }

      }
   }

   public final Transaction suspend() throws SystemException {
      if (this != getTransactionManager()) {
         return getTransactionManager().suspend();
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)getTransactionManager().getTransaction();

         try {
            if (tx != null) {
               int status = tx.getStatus();
               if (status == 0 || status == 1 || status == 9 || status == 4) {
                  try {
                     tx.delistAll(33554432);
                  } catch (AbortRequestedException var7) {
                     throw new SystemException("Failed to delist resources while suspending: " + tx.getRollbackReason() + " AbortRequestedException: " + var7);
                  }
               }
            }
         } finally {
            this.internalSuspend();
         }

         return tx;
      }
   }

   public final Transaction forceSuspend() {
      if (this != getTransactionManager()) {
         return getTransactionManager().forceSuspend();
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)getTransactionManager().getTransaction();

         try {
            if (tx != null && (tx.getStatus() == 0 || tx.getStatus() == 1)) {
               try {
                  tx.delistAllThreadAffinityResources(67108864, true);
               } catch (AbortRequestedException var6) {
               }
            }
         } finally {
            this.internalSuspend();
         }

         return tx;
      }
   }

   public void internalForceSuspend() {
      this.forceSuspend();
   }

   public void forceResume(Transaction atx) {
      super.forceResume(atx);
      if (this != getTransactionManager()) {
         getTransactionManager().forceResume(atx);
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)atx;
         if (tx != null && tx.isActive()) {
            tx.enlistThreadAffinityResources(false);
         }

      }
   }

   public void suspend(Transaction tx) {
      if (this != getTransactionManager()) {
         getTransactionManager().suspend(tx);
      } else {
         Transaction txThread = this.getTransaction();
         if (txThread != null && txThread == tx) {
            try {
               if (tx != null) {
                  try {
                     ((ServerTransactionImpl)tx).delistAll(33554432);
                  } catch (AbortRequestedException var7) {
                  }
               }
            } finally {
               this.internalSuspend();
            }

         }
      }
   }

   public final void receiveAsyncResponse(Object aTxContext) throws RemoteException {
      super.receiveAsyncResponse(aTxContext);
      if (aTxContext != null) {
         ServerTransactionImpl tx = (ServerTransactionImpl)getTransactionFromContext(aTxContext);
         if (tx.isActive()) {
            tx.enlistNonThreadAffinityStaticResources(true);
         }
      }

   }

   public final void receiveResponse(Object aTxContext) throws RemoteException {
      super.receiveResponse(aTxContext);
      if (aTxContext != null) {
         ServerTransactionImpl tx = (ServerTransactionImpl)getTransactionFromContext(aTxContext);
         if (tx.isActive()) {
            tx.enlistStaticallyEnlistedResources(true);
         }
      }

   }

   public void dispatchRequest(Object aTxContext) throws RemoteException {
      super.dispatchRequest(aTxContext);
      if (aTxContext != null) {
         ServerTransactionImpl tx = (ServerTransactionImpl)getTransactionFromContext(aTxContext);
         if (tx != null) {
            if (aTxContext instanceof PropagationContext) {
               PlatformHelper.getPlatformHelper().checkForSSLOnlyServerRetriction((PropagationContext)aTxContext, tx);
            }

            if (tx.isActive()) {
               tx.enlistStaticallyEnlistedResources(true);
            }
         }
      }

   }

   public void registerStaticResource(String name, XAResource xar) throws SystemException {
      Hashtable properties = new Hashtable();
      properties.put("weblogic.transaction.resource.type", "other");
      this.registerStaticResource(name, xar, properties);
   }

   public void registerStaticResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      XAResourceDescriptor.registerResource(name, xar, 1);
      ResourceDescriptor rd = XAResourceDescriptor.get(name);
      if (initialized) {
         this.setDeterminers(this.getDeterminers());
      }

      if (rd.getResourceDescriptorNamingConflict() != null) {
         throw rd.getResourceDescriptorNamingConflict();
      } else {
         if (properties.get("weblogic.transaction.resource.type") != null) {
            rd.setDeterminerResourceType((String)properties.get("weblogic.transaction.resource.type"));
         }

      }
   }

   public void registerDynamicResource(String name, XAResource xar) throws SystemException {
      Hashtable properties = new Hashtable();
      properties.put("weblogic.transaction.resource.type", "other");
      this.registerDynamicResource(name, xar, properties);
   }

   public void registerDynamicResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      XAResourceDescriptor.registerResource(name, xar, 2);
      if (initialized) {
         this.setDeterminers(this.getDeterminers());
      }

      ResourceDescriptor rd = XAResourceDescriptor.get(name);
      if (rd.getResourceDescriptorNamingConflict() != null) {
         throw rd.getResourceDescriptorNamingConflict();
      } else {
         if (properties.get("weblogic.transaction.resource.type") != null) {
            rd.setDeterminerResourceType((String)properties.get("weblogic.transaction.resource.type"));
         }

      }
   }

   public void registerDynamicResource(String name, NonXAResource nxar) throws SystemException {
      NonXAResourceDescriptor.registerResource(name, nxar, 2);
      ResourceDescriptor rd = NonXAResourceDescriptor.get(name);
      if (rd.getResourceDescriptorNamingConflict() != null) {
         throw rd.getResourceDescriptorNamingConflict();
      }
   }

   public void registerResource(String name, XAResource xar) throws SystemException {
      Hashtable properties = new Hashtable();
      properties.put("weblogic.transaction.resource.type", "other");
      properties.put("weblogic.transaction.registration.type", "standard");
      this.registerResource(name, xar, properties);
   }

   public void registerResource(String name, XAResource xar, boolean localResourceAssignment) throws SystemException {
      Hashtable properties = new Hashtable();
      properties.put("weblogic.transaction.resource.type", "other");
      properties.put("weblogic.transaction.registration.type", "dynamic");
      this.registerResource(name, xar, properties, localResourceAssignment);
   }

   public void registerResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      this.registerResource(name, xar, properties, false);
   }

   public void registerResource(String name, XAResource xar, Hashtable properties, boolean localResourceAssignment) throws SystemException {
      if (!(xar instanceof IgnoreXAResource)) {
         if (properties == null) {
            if (localResourceAssignment) {
               XAResourceDescriptor.registerResource(name, xar, 2, localResourceAssignment);
            } else {
               XAResourceDescriptor.registerResource(name, xar, 2);
            }

            ResourceDescriptor rd = XAResourceDescriptor.get(name);
            if (rd.getResourceDescriptorNamingConflict() != null) {
               throw rd.getResourceDescriptorNamingConflict();
            }
         } else {
            int enlistType = false;
            String type = (String)properties.get("weblogic.transaction.registration.type");
            byte enlistType;
            if (type != null && !type.equals("dynamic")) {
               if (type.equals("static")) {
                  enlistType = 1;
               } else {
                  if (!type.equals("standard")) {
                     throw new SystemException("Invalid enlistment type specified '" + type + "' for registerResource");
                  }

                  enlistType = 3;
               }
            } else {
               enlistType = 2;
            }

            if (localResourceAssignment) {
               XAResourceDescriptor.registerResource(name, xar, enlistType, localResourceAssignment);
            } else {
               XAResourceDescriptor.registerResource(name, xar, enlistType);
            }

            if (initialized) {
               this.setDeterminers(this.getDeterminers());
            }

            ResourceDescriptor rd = XAResourceDescriptor.get(name);
            if (!(rd instanceof XAResourceDescriptor) && rd.getResourceDescriptorNamingConflict() != null) {
               throw rd.getResourceDescriptorNamingConflict();
            } else {
               if (properties.get("weblogic.transaction.resource.type") != null) {
                  rd.setDeterminerResourceType((String)properties.get("weblogic.transaction.resource.type"));
               }

               XAResourceDescriptor xard = (XAResourceDescriptor)rd;
               String interleavingEnlistments = (String)properties.get("weblogic.transaction.registration.interleaving");
               if (interleavingEnlistments != null && interleavingEnlistments.equals("false")) {
                  xard.setSerializeEnlistmentsEnabled(true);
               }

               String callSetTransactionTimeout = (String)properties.get("weblogic.transaction.registration.settransactiontimeout");
               if (callSetTransactionTimeout != null && callSetTransactionTimeout.equals("true")) {
                  xard.setCallSetTransactionTimeout(true);
               }

               String localAssignment = (String)properties.get("weblogic.transaction.registration.localassignment");
               if (localAssignment != null && localAssignment.equals("false") || localResourceAssignment) {
                  rd.setAssignableOnlyToEnlistingSCs(true);
               }

               String asyncTimeoutDelist = (String)properties.get("weblogic.transaction.registration.asynctimeoutdelist");
               if (asyncTimeoutDelist != null) {
                  if (asyncTimeoutDelist.equals("false")) {
                     xard.setAsyncTimeoutDelist(false);
                  } else if (asyncTimeoutDelist.equals("true")) {
                     xard.setAsyncTimeoutDelist(true);
                  }
               }

               String threadAffinity = (String)properties.get("weblogic.transaction.registration.threadAffinity");
               if (threadAffinity != null && threadAffinity.equals("true")) {
                  xard.setThreadAffinity(true);
               }

               Integer recoverRetryDuration = (Integer)properties.get("weblogic.transaction.registration.recoverRetryDurationSeconds");
               if (recoverRetryDuration != null) {
                  xard.setRecoverRetryDurationMillis(recoverRetryDuration.longValue() * 1000L);
                  ResourceCheckpoint rc = XAResourceDescriptor.getLatestResourceCheckpoint();
                  if (rc != null && rc.contains(name)) {
                     xard.setNeedsRecovery(((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getLocalCoordinatorDescriptor());
                  }
               }

               Integer recoverRetryInterval = (Integer)properties.get("weblogic.transaction.registration.recoverRetryIntervalSeconds");
               if (recoverRetryInterval != null) {
                  xard.setRecoverRetryIntervalMillis(recoverRetryInterval.longValue() * 1000L);
               }

               String callSetDelistTMSUCCESSAlways = (String)properties.get("weblogic.transaction.registration.setdelistTMSUCCESSAlways");
               if (callSetDelistTMSUCCESSAlways != null && callSetDelistTMSUCCESSAlways.equals("true")) {
                  xard.setDelistTMSUCCESSAlways(true);
               }

               String callSetDelistTMSUCCESSInsteadOfTMSUSPEND = (String)properties.get("weblogic.transaction.registration.setdelistTMSUCCESSInsteadOfTMSUSPEND");
               if (callSetDelistTMSUCCESSInsteadOfTMSUSPEND != null && callSetDelistTMSUCCESSInsteadOfTMSUSPEND.equals("true")) {
                  xard.setDelistTMSUCCESSInsteadOfTMSUSPEND(true);
               }

               String firstResourceCommit = (String)properties.get("weblogic.transaction.first.resource.commit");
               if (firstResourceCommit != null && firstResourceCommit.equals("true")) {
                  xard.setFirstResourceCommit(true);
               }

               String vendorName = (String)properties.get("weblogic.transaction.resource.manager.name");
               if (vendorName != null && (vendorName.contains("DB2") || vendorName.contains("db2"))) {
                  TxDebug.JTARecovery.debug("registerResource name = [" + name + "], xar = [" + xar + "], properties = [" + properties + "], localResourceAssignment = [" + localResourceAssignment + "] vendorName = " + vendorName + " setDB2");
                  xard.setDB2(true);
               }

            }
         }
      }
   }

   public void setResourceHealthy(String name) {
      ResourceDescriptor.setResourceHealthy(name);
   }

   public void unregisterResource(String name) throws SystemException {
      ResourceDescriptor.unregister(name);
   }

   public void unregisterResource(String name, boolean blocking) throws SystemException {
      ResourceDescriptor.unregister(name, blocking);
   }

   public void registerLoggingResourceTransactions(LoggingResource loggingResource) throws SystemException {
      this.registerLoggingResourceTransactions(loggingResource, true);
   }

   public void registerLoggingResourceTransactions(LoggingResource loggingResource, boolean callLLRBootException) throws SystemException {
      if (!isLLRInPartitionAllowed && PlatformHelper.getPlatformHelper().getPartitionName() != null) {
         String msg = "Registration of resource " + loggingResource.getName() + " failed";
         String cause = "LLR datasources can not be registered in a partition.";
         SystemException se = new SystemException(msg);
         se.initCause(new Exception(cause));
         throw se;
      } else {
         if (this.registeredLoggingResourceTransactionsList == null) {
            this.registeredLoggingResourceTransactionsList = new ArrayList();
         }

         if (!this.isJdbcTLogInitialized() && this.getJdbcTLogEnabled()) {
            this.registeredLoggingResourceTransactionsList.add(new RegisteredLoggingResourceTransactions(loggingResource));
         } else if (this.getDBPassiveModeState() == 1) {
            if (TxDebug.JTALLR.isDebugEnabled()) {
               TxDebug.JTALLR.debug("PASSIVE mode, not registering logging resource " + loggingResource);
            }

            this.registeredLoggingResourceTransactionsList.add(new RegisteredLoggingResourceTransactions(loggingResource));
         } else {
            RegisteredLoggingResourceTransactions rlrt = new RegisteredLoggingResourceTransactions(loggingResource);

            try {
               rlrt.registerLoggingResourceTransactions(callLLRBootException);
            } catch (Exception var6) {
               throw new SystemException("Failed to call registerLoggingResourceTransactions() " + var6.toString());
            }
         }

      }
   }

   private void registerMigratedLoggingResourceTransactions(MigratableLoggingResource loggingResource, String serverName) throws SystemException {
      this.registerMigratedLoggingResourceTransactions(loggingResource, serverName, true);
   }

   private void registerMigratedLoggingResourceTransactions(MigratableLoggingResource loggingResource, String serverName, boolean isSetLLRBootException) throws SystemException {
      String debugStr = "register migrated logging resource " + loggingResource + " for server " + serverName;
      if (TxDebug.JTALLR.isDebugEnabled()) {
         TxDebug.JTALLR.debug(debugStr);
      }

      byte[][] recoveredRecords;
      SystemException se;
      try {
         recoveredRecords = loggingResource.recoverXARecords(serverName);
      } catch (Throwable var8) {
         if (TxDebug.JTALLR.isDebugEnabled()) {
            TxDebug.JTALLR.debug(debugStr, var8);
         }

         if (isSetLLRBootException) {
            this.setLLRBootException(var8);
         }

         se = new SystemException(var8.toString());
         se.initCause(var8);
         throw se;
      }

      try {
         for(int j = 0; j < recoveredRecords.length; ++j) {
            this.registerLoggingResourceRecord(loggingResource, recoveredRecords[j], serverName);
         }
      } catch (Throwable var9) {
         if (TxDebug.JTALLR.isDebugEnabled()) {
            TxDebug.JTALLR.debug(debugStr, var9);
         }

         se = new SystemException(TXExceptionLogger.logCorruptedLLRRecordLoggable(loggingResource.toString(), var9.toString()).getMessage());
         se.initCause(var9);
         throw se;
      }

      MigratedTLog mtlog = ServerTransactionManagerImpl.MigratedTLog.get(serverName);
      if (mtlog != null && mtlog.rtMBean != null) {
         mtlog.rtMBean.reset(mtlog.getInitialTransactionTotalCount());
      }

      if (TxDebug.JTALLR.isDebugEnabled()) {
         TxDebug.JTALLR.debug(debugStr + " num-recovered=" + recoveredRecords.length);
      }

   }

   public void registerFailedPrimaryStore(Throwable t) {
      this.setPrimaryStoreBootException(t);
   }

   public void registerFailedLoggingResource(Throwable t) {
      this.setLLRBootException(t);
   }

   private void registerLoggingResourceRecord(LoggingResource loggingResource, byte[] record, String migratedServerName) throws IOException {
      InputStream baid = PlatformHelper.getPlatformHelper().newUnsyncByteArrayInputStream(record);
      ObjectInputStream ois = new ObjectInputStream(baid);
      StoreLogDataInputImpl ldis = new StoreLogDataInputImpl(ois);
      int version = ldis.readInt();
      if (version != 90) {
         throw new IOException("Unexpected version " + version + ", expected " + 90 + ".  There was either an unsupported attempt to read a record from a future version of WebLogic, or the record is corrupted.");
      } else {
         ServerTransactionImpl sti = new ServerTransactionImpl();
         sti.readExternal(ldis);
         sti.setLoggingResource(loggingResource);
         if (migratedServerName != null) {
            sti.setLocalProperty("weblogic.transaction.migrated.llr", migratedServerName);
            sti.onRecovery(this.getMigratedTransactionLogger(migratedServerName));
            this.incrementMigratedLLRRecoveringCount(migratedServerName);
         } else {
            sti.onRecovery((TransactionLogger)null);
         }

         if (TxDebug.JTALLR.isDebugEnabled()) {
            TxDebug.JTALLR.debug("logging resource " + loggingResource + " found transaction " + sti);
         }

      }
   }

   public void registerCoordinatorService(String serviceName, weblogic.transaction.CoordinatorService cs) {
      if (TxDebug.JTALLR.isDebugEnabled()) {
         TxDebug.JTALLR.debug("registered coordinator service " + serviceName + " " + cs);
      }

      coordinatorServices.put(serviceName, cs);
   }

   Object invokeCoordinatorService(String serviceName, Object data) throws RemoteException, SystemException {
      weblogic.transaction.CoordinatorService cs = (weblogic.transaction.CoordinatorService)coordinatorServices.get(serviceName);
      if (cs == null) {
         if (TxDebug.JTALLR.isDebugEnabled()) {
            TxDebug.JTALLR.debug("coordinator service " + serviceName + " not found");
         }

         return null;
      } else {
         try {
            Object retVal = cs.invokeCoordinatorService(serviceName, data);
            if (TxDebug.JTALLR.isDebugEnabled()) {
               TxDebug.JTALLR.debug("coordinator service " + serviceName + " returned " + retVal);
            }

            return retVal;
         } catch (SystemException var5) {
            if (TxDebug.JTALLR.isDebugEnabled()) {
               TxDebug.JTALLR.debug((String)("coordinator service " + serviceName + " failed"), (Throwable)var5);
            }

            throw var5;
         }
      }
   }

   void setTLog(TransactionLogger tlog) {
      this.tlog = tlog;
   }

   void setHLog(TransactionLogger hlog) {
      this.hlog = hlog;
   }

   void recover() throws IOException, LoggingResourceException {
      this.processRegisteredLoggingResourceTransactionsList();
      this.doRecover();
   }

   void doRecover() throws IOException {
      if (this.getTransactionLogger() == null) {
         try {
            this.setTLog(new StoreTransactionLoggerImpl(false, this.runtime));
         } catch (IOException var3) {
            this.runtime.healthEvent(new HealthEvent(1, this.serverName, "Unable to access transaction log '" + this.serverName + "'"));
            throw var3;
         }
      }

      try {
         this.setHLog(new StoreTransactionLoggerImpl(true, this.runtime));
      } catch (IOException var2) {
         this.runtime.healthEvent(new HealthEvent(1, this.serverName, "Unable to access transaction log '" + this.serverName + "'"));
         throw var2;
      }

      this.lastCheckpointTime = this.lastMigrationCheckpointTime = System.currentTimeMillis();
      XAResourceDescriptor.checkRecovery();
      JTARecoveryRuntime rtMBean = PlatformHelper.getPlatformHelper().getJTARecoveryRuntime(this.serverName);
      if (rtMBean != null && this.getTransactionLogger() != null) {
         rtMBean.reset(this.getTransactionLogger().getInitialRecoveredTransactionTotalCount() + this.llrCurrentRecoveredTransactionCount);
         rtMBean.resetUnlogged(XAResourceDescriptor.totalDeterminersXidsLengthForCoor(this.getLocalCoordinatorDescriptor()));
      }

   }

   private void incrementMigratedLLRRecoveringCount(String serverName) {
      MigratedTLog migratedTLog = this.getMigratedTLog(serverName);
      if (migratedTLog != null) {
         migratedTLog.incLLRRecoveringCount();
      }

   }

   void updateMigratedLLRCompletionStatistics(String serverName) {
      MigratedTLog migratedTLog = this.getMigratedTLog(serverName);
      if (migratedTLog != null) {
         migratedTLog.updateLLRCompletionStatistics();
      }

   }

   public static void registerMigratableRM(MigratableRM migratableRM) {
      m_migratableRM = migratableRM;
   }

   private void purgeMigratedTransactions(TransactionLogger tlog) {
      synchronized(this) {
         ArrayList preMigrationTxs = new ArrayList();
         Iterator it = this.txMap.values().iterator();

         while(it.hasNext()) {
            ServerTransactionImpl tx = (ServerTransactionImpl)it.next();
            if (tx.getTransactionLogger() == tlog) {
               it.remove();
               Transaction preMigrationTx = (Transaction)tx.getLocalProperty("weblogic.transaction.migrated.subordinateTransaction");
               if (preMigrationTx != null) {
                  preMigrationTxs.add(preMigrationTx);
               }
            }
         }

         it = preMigrationTxs.iterator();

         while(it.hasNext()) {
            this.add((TransactionImpl)it.next());
         }

      }
   }

   private void purgeMigratedLLRTransactions(String serverName) {
      synchronized(this) {
         ArrayList preMigrationTxs = new ArrayList();
         Iterator it = this.txMap.values().iterator();

         while(it.hasNext()) {
            ServerTransactionImpl tx = (ServerTransactionImpl)it.next();
            String llrServer = (String)tx.getLocalProperty("weblogic.transaction.migrated.llr");
            if (llrServer != null && llrServer.equals(serverName)) {
               it.remove();
               Transaction preMigrationTx = (Transaction)tx.getLocalProperty("weblogic.transaction.migrated.subordinateTransaction");
               if (preMigrationTx != null) {
                  preMigrationTxs.add(preMigrationTx);
               }
            }

            it = preMigrationTxs.iterator();

            while(it.hasNext()) {
               this.add((TransactionImpl)it.next());
            }
         }

      }
   }

   void dropAllTransactions() {
      synchronized(this) {
         Iterator it = this.txMap.values().iterator();

         while(true) {
            TransactionImpl tx;
            int status;
            do {
               if (!it.hasNext()) {
                  return;
               }

               tx = (TransactionImpl)it.next();
               it.remove();
               status = tx.getStatus();
            } while(status != 0 && (status != 7 || ((ServerTransactionImpl)tx).getDeterminer() != null));

            try {
               tx.rollback();
            } catch (Exception var7) {
            }
         }
      }
   }

   private void processRegisteredLoggingResourceTransactionsList() throws LoggingResourceException {
      if (this.registeredLoggingResourceTransactionsList != null) {
         Iterator registeredLoggingResourceTransactionsListIterator = this.registeredLoggingResourceTransactionsList.iterator();

         while(registeredLoggingResourceTransactionsListIterator.hasNext()) {
            ((RegisteredLoggingResourceTransactions)registeredLoggingResourceTransactionsListIterator.next()).registerLoggingResourceTransactions();
            registeredLoggingResourceTransactionsListIterator.remove();
         }

      }
   }

   void recover(String serverName) throws Exception {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("Recovering for '" + serverName + "'");
      }

      ServerTransactionManagerImpl.MigratedTLog.getOrCreate(serverName);
      this.registerAnyMigratedLLRResources(serverName, true);
   }

   void registerAnyMigratedLLRResources(String serverName, boolean isSetLLRBootException) throws SystemException {
      List rds = ResourceDescriptor.getAllResources();
      Iterator var4 = rds.iterator();

      while(var4.hasNext()) {
         Object rd = var4.next();
         boolean isNonXAResourceDescriptor = rd instanceof NonXAResourceDescriptor;
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("ServerTransactionManagerImpl.registerAnyMigratedLLRResources for '" + serverName + "'  rd:" + rd + " isNonXAResourceDescriptor:" + isNonXAResourceDescriptor);
         }

         if (isNonXAResourceDescriptor) {
            NonXAResourceDescriptor nxard = (NonXAResourceDescriptor)rd;
            NonXAResource nxar = nxard.getNonXAResource();
            boolean isMigratableLoggingResource = nxar instanceof MigratableLoggingResource;
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("ServerTransactionManagerImpl.registerAnyMigratedLLRResources for '" + serverName + "'  rd:" + rd + " isMigratableLoggingResource:" + isMigratableLoggingResource);
            }

            if (isMigratableLoggingResource) {
               MigratableLoggingResource mlr = (MigratableLoggingResource)nxar;
               mlr.migratableActivate(serverName);
               this.registerMigratedLoggingResourceTransactions(mlr, serverName, isSetLLRBootException);
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("ServerTransactionManagerImpl.registerAnyMigratedLLRResources for '" + serverName + "'  rd:" + rd + " isMigratableLoggingResource:" + isMigratableLoggingResource + " complete.");
               }
            }
         }
      }

   }

   void suspendRecovery(String serverName) {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("Suspending recovery for '" + serverName + "'");
      }

      ServerTransactionManagerImpl.MigratedTLog.release(serverName);
      List rds = ResourceDescriptor.getAllResources();
      Iterator rdIt = rds.iterator();

      while(rdIt.hasNext()) {
         Object rd = rdIt.next();
         if (rd instanceof NonXAResourceDescriptor) {
            NonXAResourceDescriptor nxard = (NonXAResourceDescriptor)rd;
            NonXAResource nxar = nxard.getNonXAResource();
            if (nxar instanceof MigratableLoggingResource) {
               MigratableLoggingResource mlr = (MigratableLoggingResource)nxar;
               mlr.migratableDeactivate(serverName);
            }
         }
      }

   }

   int getInitialRecoveredTransactionTotalCount(String serverName) {
      if (serverName.equals(this.getServerName())) {
         TransactionLogger tl = this.getTransactionLogger();
         return tl != null ? tl.getInitialRecoveredTransactionTotalCount() : 0;
      } else {
         MigratedTLog mtlog = ServerTransactionManagerImpl.MigratedTLog.get(serverName);
         return mtlog != null ? mtlog.getInitialTransactionTotalCount() : -1;
      }
   }

   int getRecoveredTransactionCompletionCount(String serverName) {
      if (serverName.equals(this.getServerName())) {
         TransactionLogger tl = this.getTransactionLogger();
         return (tl != null ? tl.getRecoveredTransactionCompletionCount() : 0) + this.llrCompletedRecoveredTransactionCount;
      } else {
         MigratedTLog mtlog = ServerTransactionManagerImpl.MigratedTLog.get(serverName);
         return mtlog != null ? mtlog.getTransactionCompletionCount() : -1;
      }
   }

   int getRecoveredUnloggedTransactionCount(String serverName) {
      CoordinatorDescriptor cd = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getOrCreateForMigration(serverName);
      return XAResourceDescriptor.totalDeterminersXidsLengthForCoor(cd);
   }

   int getRecoveredUnloggedTransactionCount(String serverName, String siteName) {
      return 0;
   }

   void incrementLLRCurrentRecoveredTransactionCount() {
      ++this.llrCurrentRecoveredTransactionCount;
   }

   void incrementLLRCompletedRecoveredTransactionCount() {
      ++this.llrCompletedRecoveredTransactionCount;
   }

   private MigratedTLog getMigratedTLog(String serverName) {
      return ServerTransactionManagerImpl.MigratedTLog.get(serverName);
   }

   private TransactionLogger getMigratedTransactionLogger(String serverName) {
      MigratedTLog mtl = ServerTransactionManagerImpl.MigratedTLog.get(serverName);
      return mtl != null ? mtl.tlog : null;
   }

   public TransactionLogger getTransactionLogger() {
      return this.tlog;
   }

   public Object getJTATransactionForThread(Thread thread) {
      TransactionImpl current = PlatformHelper.getPlatformHelper().getTransactionImplFromTxThreadLocal(txThreadLocal, thread);
      return current != null && current instanceof ServerTransactionImpl ? PlatformHelper.getPlatformHelper().createJTATransaction(current) : null;
   }

   public void registerBeginNotificationListener(BeginNotificationListener listener, Object handback) {
      if (listener != null) {
         ComponentInvocationContext cic = PlatformHelper.getPlatformHelper().getCurrentComponentInvocationContext();
         synchronized(this.beginNotificationListeners) {
            this.beginNotificationListeners.add(new BeginListener(listener, handback, cic));
            ++this.numRegisteredBeginNotificationListeners;
         }
      }
   }

   public void unregisterBeginNotificationListener(BeginNotificationListener listener) {
      if (listener != null) {
         synchronized(this.beginNotificationListeners) {
            int index = this.beginNotificationListeners.indexOf(new BeginListener(listener, (Object)null, (ComponentInvocationContext)null));
            if (index != -1) {
               this.beginNotificationListeners.remove(index);
               --this.numRegisteredBeginNotificationListeners;
            }
         }
      }
   }

   TransactionLogger getHeuristicLogger() {
      return this.hlog;
   }

   public CoordinatorDescriptor getLocalCoordinatorDescriptor() {
      return this.localCoordinatorDescriptor;
   }

   public String getLocalCoordinatorURL() {
      return this.localCoordinatorDescriptor == null ? null : this.localCoordinatorDescriptor.getCoordinatorURL();
   }

   final void setLocalCoordinatorDescriptor(CoordinatorDescriptor s) {
      this.localCoordinatorDescriptor = s;
   }

   final Coordinator getLocalCoordinator() {
      return this.localCoordinator;
   }

   final void setLocalCoordinator(Coordinator coordinator) {
      this.localCoordinator = coordinator;
   }

   final boolean getForgetHeuristics() {
      return forgetHeuristics;
   }

   final boolean getParallelXAEnabled() {
      return parallelXAEnabled;
   }

   final boolean isMemberOfCluster() {
      return memberOfCluster;
   }

   final String getParallelXADispatchPolicy() {
      return parallelXADispatchPolicy;
   }

   final int getMaxTransactions() {
      return maxTransactions;
   }

   final int getMaxUniqueNameStatistics() {
      return maxUniqueNameStatistics;
   }

   final int getBeforeCompletionIterationLimit() {
      return beforeCompletionIterationLimit;
   }

   final long getMaxTransactionsHealthIntervalMillis() {
      return maxTransactionsHealthIntervalMillis;
   }

   long getCheckpointIntervalSeconds() {
      return this.checkpointIntervalMillis / 1000L;
   }

   long getUnregisterResourceGracePeriodMillis() {
      return this.unregisterResourceGracePeriodMillis;
   }

   static void setMaxTransactionsHealthIntervalMillis(long max) {
      maxTransactionsHealthIntervalMillis = max;
   }

   public void setDeterminers(String[] determiners) {
      super.setDeterminers();
      ArrayList resourceDescriptorList = ResourceDescriptor.getResourceDescriptorList();
      Iterator var3 = resourceDescriptorList.iterator();

      while(var3.hasNext()) {
         Object aResourceDescriptorList = var3.next();
         ResourceDescriptor resourceDescriptor = (ResourceDescriptor)aResourceDescriptorList;
         boolean isADeterminer = false;
         if (TxDebug.JTA2PC.isDebugEnabled() && (determiners == null || determiners.length == 0)) {
            TxDebug.JTA2PC.debug(this + ".setDeterminer for " + resourceDescriptor.getName() + " [no determiners]");
         }

         String[] var7 = determiners;
         int var8 = determiners.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String determiner = var7[var9];
            if (determiner.equals(resourceDescriptor.getName())) {
               isADeterminer = true;
            }

            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug(this + ".setDeterminer for " + resourceDescriptor.getName() + " determiner:" + determiner + " isADeterminer match:" + isADeterminer);
            }
         }

         resourceDescriptor.setDeterminer(isADeterminer);
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug(this + ".setDeterminer for " + resourceDescriptor.getName() + " isADeterminer:" + isADeterminer);
         }
      }

   }

   String[] getDeterminersForDomainAndAllPartitions() {
      int determinerLength = 0;

      String[] allDeterminers;
      for(Iterator itr = this.partitionDeterminersMap.values().iterator(); itr.hasNext(); determinerLength += allDeterminers.length) {
         allDeterminers = (String[])((String[])itr.next());
      }

      allDeterminers = new String[determinerLength];
      int startPos = 0;
      Iterator itr2 = this.partitionDeterminersMap.values().iterator();

      while(itr2.hasNext()) {
         String[] determiners = (String[])((String[])itr2.next());
         if (determiners.length != 0) {
            System.arraycopy(determiners, 0, allDeterminers, startPos, determiners.length);
            startPos += determiners.length;
         }
      }

      return allDeterminers;
   }

   final JTARuntime getRuntime() {
      return this.runtime;
   }

   final JTAPartitionRuntime getPartitionRuntime(String partitionName) {
      return this.jtaPartitionRuntimes == null ? null : (JTAPartitionRuntime)this.jtaPartitionRuntimes.get(partitionName);
   }

   final void removePartitionRuntime(String partitionName) {
      if (this.jtaPartitionRuntimes != null) {
         this.jtaPartitionRuntimes.remove(partitionName);
      }

   }

   public final void setServerName(String name) {
      this.serverName = name;
   }

   public String getServerName() {
      return this.serverName;
   }

   void checkpoint() {
      if (this.getTransactionLogger() != null) {
         long reCheckpointInterval = this.checkpointIntervalMillis;
         ResourceDescriptor.refreshCheckpoint(reCheckpointInterval);
         ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).checkpointServers();
         this.getTransactionLogger().checkpoint();
         if (this.getHeuristicLogger() != null) {
            this.getHeuristicLogger().checkpoint();
         }

         this.lastCheckpointTime = System.currentTimeMillis();
      }
   }

   void checkpointMigratedTLogs() {
      ServerTransactionManagerImpl.MigratedTLog.checkpoint();
      this.lastMigrationCheckpointTime = System.currentTimeMillis();
   }

   public boolean isRunning() {
      return PlatformHelper.getPlatformHelper().isTransactionServiceRunning() && this.getDBPassiveModeState() == 0;
   }

   boolean isTxMapEmpty() {
      return this.txMap.size() == 0;
   }

   synchronized ConcurrentHashMap getTxMap() {
      return this.txMap;
   }

   public final void wakeUp() {
      if (this.getDBPassiveModeState() != 1) {
         super.wakeUp();
         long now = System.currentTimeMillis();
         if (PlatformHelper.getPlatformHelper().isTransactionServiceRunning() && PlatformHelper.getPlatformHelper().isServerRunning() && this.allCheckpointedLLRSAccountedFor()) {
            try {
               XAResourceDescriptor.checkRecovery();
            } catch (IOException var4) {
               TXLogger.logUnableToProcessDeterminer(var4);
            }

            XAResourceDescriptor.checkAllResourceHealth();
            if (now - this.lastCheckpointTime > this.checkpointIntervalMillis) {
               this.checkpoint();
            }
         }

         PlatformHelper.getPlatformHelper().doTimerLifecycleHousekeeping();
         if (now - this.lastMigrationCheckpointTime > migrationCheckpointIntervalMillis) {
            this.checkpointMigratedTLogs();
         }

         if (this.txMap.size() >= maxTransactions) {
            if (this.runtime != null) {
               this.txmapFull = true;
               this.runtime.healthEvent(new HealthEvent(3, (String)null, "Transaction map at capacity"));
            }
         } else if (this.txmapFull) {
            this.txmapFull = false;
            this.runtime.healthEvent(new HealthEvent(4, (String)null, "Transaction map below capacity"));
         }

         XAResourceDescriptor.checkSerializedEnlistmentsGC();
         this.lastWakeUpDuration = System.currentTimeMillis() - this.lastTimerFire;
      }
   }

   protected final void processTimedOutTransactions(List timedOutTransactions, int nowSec) {
      HashMap checkStatusTransactions = null;
      int cnt = 100;
      Iterator iter = timedOutTransactions.iterator();

      while(iter.hasNext()) {
         ServerTransactionImpl tx = (ServerTransactionImpl)iter.next();
         iter.remove();
         if (tx.isPrepared()) {
            Runnable otsER = (Runnable)tx.getLocalProperty("weblogic.transaction.otsReplayCompletionExecuteRequest");
            if (otsER == null) {
               if (tx.isCoordinatingTransaction()) {
                  tx.wakeUp(nowSec);
               } else {
                  if (checkStatusTransactions == null) {
                     checkStatusTransactions = new HashMap(1);
                  }

                  CoordinatorDescriptor coordinatorDescriptor = tx.getCoordinatorDescriptor();
                  ArrayList txs = (ArrayList)checkStatusTransactions.get(coordinatorDescriptor);
                  if (txs == null) {
                     txs = new ArrayList(10);
                     if (!this.isLocalCoordinator(coordinatorDescriptor)) {
                        checkStatusTransactions.put(coordinatorDescriptor, txs);
                     }
                  }

                  txs.add(tx);
               }
            } else {
               PlatformHelper.getPlatformHelper().scheduleWork(otsER);
            }
         } else {
            tx.wakeUp(nowSec);
         }

         if (cnt-- <= 0) {
            break;
         }
      }

      this.asyncCheckStatus(checkStatusTransactions);
   }

   private void asyncCheckStatus(final HashMap checkStatusTransactions) {
      if (checkStatusTransactions != null) {
         Runnable callback = new Runnable() {
            public void run() {
               ServerTransactionManagerImpl.this.checkStatus(checkStatusTransactions);
            }
         };
         PlatformHelper.getPlatformHelper().scheduleCheckStatusRequest(checkStatusTransactions, callback);
      }

   }

   private void checkStatus(HashMap checkStatusTransactions) {
      if (checkStatusTransactions != null) {
         CoordinatorDescriptor localScDesc = this.getLocalCoordinatorDescriptor();
         Iterator iter = checkStatusTransactions.entrySet().iterator();

         while(true) {
            Map.Entry entry;
            CoordinatorDescriptor coordinatorDescriptor;
            CoordinatorOneway co;
            do {
               if (!iter.hasNext()) {
                  return;
               }

               entry = (Map.Entry)iter.next();
               coordinatorDescriptor = (CoordinatorDescriptor)entry.getKey();
               co = JNDIAdvertiser.getCoordinator(coordinatorDescriptor, (PeerExchangeTransaction)null);
            } while(co == null);

            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TXLogger.logDebug("co.checkStatus coURL:" + coordinatorDescriptor);
            }

            try {
               ArrayList txs = (ArrayList)entry.getValue();
               int txsLen = txs.size();
               Xid[] xids = new Xid[txsLen];

               for(int i = 0; i < txsLen; ++i) {
                  xids[i] = ((TransactionImpl)txs.get(i)).getXID();
               }

               String ScUrl = localScDesc.getCoordinatorURL();
               String CoUrl = coordinatorDescriptor.getCoordinatorURL();
               String coServerUrl = CoordinatorDescriptor.getServerURL(CoUrl);
               PlatformHelper.getPlatformHelper().runAction(new CallCheckStatusAction(co, xids, ScUrl), coServerUrl, "co.CheckStatus");
            } catch (Exception var13) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TXLogger.logDebugTrace("co.checkStatus coURL:" + coordinatorDescriptor + " failure", var13);
               }
            }
         }
      }
   }

   public void addToListOfAckCommits(Xid xid, String scURL) {
      if (scURL != null) {
         TransactionImpl tx = (TransactionImpl)this.txMap.get(xid);
         if (tx != null) {
            synchronized(this.addToListOfAckCommitsLock) {
               Map ackCommitSCs = (Map)tx.getProperty("ackCommitSCs");
               if (ackCommitSCs == null) {
                  Map ackCommitSCs = new ConcurrentHashMap();
                  ackCommitSCs.put(scURL, true);
                  tx.setProperty("ackCommitSCs", (Serializable)ackCommitSCs);
               } else if (ackCommitSCs.get(scURL) == null) {
                  ackCommitSCs.put(scURL, true);
                  tx.setProperty("ackCommitSCs", (Serializable)ackCommitSCs);
               }
            }
         }
      }

   }

   public boolean listOfSCCommitsContains(Xid xid, String scURL) {
      TransactionImpl tx = (TransactionImpl)this.txMap.get(xid);
      if (tx == null) {
         return false;
      } else {
         Map ackCommitSCs = (Map)tx.getProperty("ackCommitSCs");
         if (ackCommitSCs == null) {
            return false;
         } else {
            return ackCommitSCs.get(scURL) != null;
         }
      }
   }

   private void setForgetHeuristics(boolean forget) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setForgetHeuristics(" + forget + ")");
      }

      forgetHeuristics = forget;
   }

   private void setParallelXAEnabled(boolean enabled) {
      parallelXAEnabled = enabled;
   }

   private void setMemberOfCluster(boolean member) {
      memberOfCluster = member;
   }

   private void setParallelXADispatchPolicy(String policy) {
      parallelXADispatchPolicy = policy;
   }

   private void setBeforeCompletionIterationLimit(int limit) {
      beforeCompletionIterationLimit = limit;
   }

   private void setMaxTransactions(int max) {
      maxTransactions = max;
   }

   private void setMaxUniqueNameStatistics(int max) {
      maxUniqueNameStatistics = max;
   }

   private void setMigrationCheckpointIntervalMillis(long max) {
      migrationCheckpointIntervalMillis = max;
   }

   private void setPurgeResourceFromCheckpointIntervalSeconds(int secs) {
      int purgeResourceInterval = secs;
      if (secs < this.getAbandonTimeoutSeconds()) {
         purgeResourceInterval = this.getAbandonTimeoutSeconds();
      }

      XAResourceDescriptor.setPurgeResourceFromCheckpointIntervalSeconds(purgeResourceInterval);
      ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).setPurgeFromCheckpointIntervalSeconds(purgeResourceInterval);
   }

   private void setCheckpointIntervalSeconds(int interval) {
      this.checkpointIntervalMillis = (long)interval * 1000L;
   }

   private void setUnregisterResourceGracePeriodMillis(int seconds) {
      this.unregisterResourceGracePeriodMillis = (long)seconds * 1000L;
   }

   private void setInteropMode(String mode) {
      if (mode.equals("default")) {
         interopMode = 0;
      } else {
         if (!mode.equals("performance")) {
            throw new AssertionError("Invalid interop mode: " + mode);
         }

         interopMode = 1;
      }

   }

   static int getInteropMode() {
      return interopMode;
   }

   static void setInteropModeToVal(int secInteropMode) {
      interopMode = secInteropMode;
   }

   private void configure(JTAConfig config) {
      this.setDefaultTimeoutSeconds(config.getTimeoutSeconds());
      this.setAbandonTimeoutSeconds(config.getAbandonTimeoutSeconds());
      this.setCompletionTimeoutSeconds(config.getCompletionTimeoutSeconds());
      this.setForgetHeuristics(config.getForgetHeuristics());
      this.setBeforeCompletionIterationLimit(config.getBeforeCompletionIterationLimit());
      this.setMaxTransactions(config.getMaxTransactions());
      this.setMaxUniqueNameStatistics(config.getMaxUniqueNameStatistics());
      config.addPropertyChangeListener(new ConfigurationListener(this));
      XAResourceDescriptor.setMaxXACallMillis(config.getMaxXACallMillis());
      XAResourceDescriptor.setMaxResourceUnavailableMillis(config.getMaxResourceUnavailableMillis());
      XAResourceDescriptor.setMaxResourceRequestsOnServer(config.getMaxResourceRequestsOnServer());
      this.setMigrationCheckpointIntervalMillis((long)(config.getMigrationCheckpointIntervalSeconds() * 1000));
      this.setPurgeResourceFromCheckpointIntervalSeconds(config.getPurgeResourceFromCheckpointIntervalSeconds());
      this.setCheckpointIntervalSeconds(config.getCheckpointIntervalSeconds());
      this.setParallelXAEnabled(config.isParallelXAEnabled());
      this.setParallelXADispatchPolicy(config.getParallelXADispatchPolicy());
      this.setTwoPhaseCommitEnabled(config.isTwoPhaseEnabled());
      this.setUnregisterResourceGracePeriodMillis(config.getUnregisterResourceGracePeriod());
      this.setInteropMode(config.getSecurityInteropMode());
      this.setClusterwideRecoveryEnabled(config.isClusterwideRecoveryEnabled());
      this.setTightlyCoupledTransactionsEnabled(config.isTightlyCoupledTransactionsEnabled());
      this.setTLOGWriteWhenDeterminerExistsEnabled(config.isTLOGWriteWhenDeterminerExistsEnabled());
      this.setShutdownGracePeriod(config.getShutdownGracePeriod());
      this.setSiteName(config.getSiteName());
      this.setRecoverySiteName(config.getRecoverySiteName());
      this.setPartitionDeterminers("GLOBAL", config.getDeterminers());
      this.setDeterminers(config.getDeterminers());
      this.setMaxRetrySecondsBeforeDeterminerFail(config.getMaxRetrySecondsBeforeDeterminerFail());
      this.setCrossDomainRecoveryRetryInterval(config.getCrossDomainRecoveryRetryInterval());
      this.setCrossSiteRecoveryRetryInterval(config.getCrossSiteRecoveryRetryInterval());
      this.setCrossSiteRecoveryLeaseExpiration(config.getCrossSiteRecoveryLeaseExpiration());
      this.setCrossSiteRecoveryLeaseUpdate(config.getCrossSiteRecoveryLeaseUpdate());
      if (config.isDBPassiveMode()) {
         this.setDBPassiveModeState(1);
      }

      this.setDBPassiveModeGracePeriodSeconds(config.getDBPassiveModeGracePeriodSeconds());
      this.setUsePublicAddressesForRemoteDomains(config.getUsePublicAddressesForRemoteDomains());
      this.setUseNonSecureAddressesForDomains(config.getUseNonSecureAddressesForDomains());
      this.jtaClusterConfig = config.getJTAClusterConfig();
      if (this.jtaClusterConfig != null) {
         ClusterConfigurationListener clusterConfigurationListener = new ClusterConfigurationListener(this);
         this.jtaClusterConfig.addPropertyChangeListener(clusterConfigurationListener);
         config.addPropertyChangeListener(clusterConfigurationListener);
         this.setMemberOfCluster(this.jtaClusterConfig.isMemberOfCluster());
         if (this.jtaClusterConfig.isMemberOfCluster()) {
            this.setDefaultTimeoutSeconds(this.jtaClusterConfig.getTimeoutSeconds());
            this.setAbandonTimeoutSeconds(this.jtaClusterConfig.getAbandonTimeoutSeconds());
            this.setCompletionTimeoutSeconds(this.jtaClusterConfig.getCompletionTimeoutSeconds());
            this.setForgetHeuristics(this.jtaClusterConfig.getForgetHeuristics());
            this.setBeforeCompletionIterationLimit(this.jtaClusterConfig.getBeforeCompletionIterationLimit());
            this.setMaxTransactions(this.jtaClusterConfig.getMaxTransactions());
            this.setMaxUniqueNameStatistics(this.jtaClusterConfig.getMaxUniqueNameStatistics());
            XAResourceDescriptor.setMaxXACallMillis(this.jtaClusterConfig.getMaxXACallMillis());
            XAResourceDescriptor.setMaxResourceUnavailableMillis(this.jtaClusterConfig.getMaxResourceUnavailableMillis());
            XAResourceDescriptor.setMaxResourceRequestsOnServer(this.jtaClusterConfig.getMaxResourceRequestsOnServer());
            this.setMigrationCheckpointIntervalMillis((long)(this.jtaClusterConfig.getMigrationCheckpointIntervalSeconds() * 1000));
            this.setPurgeResourceFromCheckpointIntervalSeconds(this.jtaClusterConfig.getPurgeResourceFromCheckpointIntervalSeconds());
            this.setCheckpointIntervalSeconds(this.jtaClusterConfig.getCheckpointIntervalSeconds());
            this.setParallelXAEnabled(this.jtaClusterConfig.isParallelXAEnabled());
            this.setParallelXADispatchPolicy(this.jtaClusterConfig.getParallelXADispatchPolicy());
            this.setTwoPhaseCommitEnabled(this.jtaClusterConfig.isTwoPhaseEnabled());
            this.setUnregisterResourceGracePeriodMillis(this.jtaClusterConfig.getUnregisterResourceGracePeriod());
            this.setInteropMode(this.jtaClusterConfig.getSecurityInteropMode());
            this.setClusterwideRecoveryEnabled(this.jtaClusterConfig.isClusterwideRecoveryEnabled());
            this.setTightlyCoupledTransactionsEnabled(this.jtaClusterConfig.isTightlyCoupledTransactionsEnabled());
            this.setTLOGWriteWhenDeterminerExistsEnabled(this.jtaClusterConfig.isTLOGWriteWhenDeterminerExistsEnabled());
            this.setPartitionDeterminers("GLOBAL", this.jtaClusterConfig.getDeterminers());
            this.setDeterminers(this.jtaClusterConfig.getDeterminers());
            this.setMaxRetrySecondsBeforeDeterminerFail(this.jtaClusterConfig.getMaxRetrySecondsBeforeDeterminerFail());
            this.setSiteName(this.jtaClusterConfig.getSiteName());
            this.setCrossDomainRecoveryRetryInterval(this.jtaClusterConfig.getCrossDomainRecoveryRetryInterval());
            this.setCrossSiteRecoveryRetryInterval(this.jtaClusterConfig.getCrossSiteRecoveryRetryInterval());
            this.setCrossSiteRecoveryLeaseExpiration(this.jtaClusterConfig.getCrossSiteRecoveryLeaseExpiration());
            this.setCrossSiteRecoveryLeaseUpdate(this.jtaClusterConfig.getCrossSiteRecoveryLeaseUpdate());
         }
      }

      TransactionLogJDBCStoreConfig transactionLogJDBCStoreConfig = config.getTransactionLogJDBCStoreConfig();
      transactionLogJDBCStoreConfig.addPropertyChangeListener(new ConfigurationListener(this));
      this.setJdbcTLogEnabled(transactionLogJDBCStoreConfig.isJdbcTLogEnabled());
      this.setJdbcTLogPrefixName(transactionLogJDBCStoreConfig.getJdbcTLogPrefixName());
      this.setJdbcTLogMaxRetrySecondsBeforeTLOGFail(transactionLogJDBCStoreConfig.getJdbcTLogMaxRetrySecondsBeforeTLOGFail());
      this.setJdbcTLogMaxRetrySecondsBeforeTXException(transactionLogJDBCStoreConfig.getJdbcTLogMaxRetrySecondsBeforeTXException());
      this.setJdbcTLogRetryIntervalSeconds(transactionLogJDBCStoreConfig.getJdbcTLogRetryIntervalSeconds());
      this.setJdbcTLogDataSource(transactionLogJDBCStoreConfig.getJdbcTLogDataSource());
      initialized = true;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      throw new IOException("Server side transaction managers are not serializable");
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      throw new IOException("Server side transaction managers are not serializable");
   }

   public static TransactionManagerImpl getTM() {
      return TransactionManagerImpl.getTransactionManager();
   }

   public final TransactionImpl createTransaction(Xid xid, int aTimeOutSec, int aTimeToLiveSec) throws SystemException {
      return this.createTransaction(xid, aTimeOutSec, aTimeToLiveSec, false);
   }

   public final TransactionImpl createTransaction(Xid xid, int aTimeOutSec, int aTimeToLiveSec, boolean useSSLProtocol) throws SystemException {
      return this.createTransaction(xid, aTimeOutSec, aTimeToLiveSec, useSSLProtocol, false);
   }

   public final TransactionImpl createTransaction(Xid xid, int aTimeOutSec, int aTimeToLiveSec, boolean useSSLProtocol, boolean isFromPropagationContext) throws SystemException {
      if (this.getNumTransactions() > this.getMaxTransactions()) {
         throw new SystemException("Exceeded maximum allowed transactions on server '" + this.getServerName() + "'");
      } else if (!PlatformHelper.getPlatformHelper().isTransactionServiceRunning()) {
         throw new SystemException("The server is being suspended or shut down.  Cannot create new transactions.");
      } else if (this.getDBPassiveModeState() != 0) {
         throw new SystemException("Transaction Service is in PASSIVE mode");
      } else {
         return new ServerTransactionImpl(xid, aTimeOutSec, aTimeToLiveSec, useSSLProtocol, isFromPropagationContext);
      }
   }

   public final TransactionImpl createImportedTransaction(Xid xid, Xid foreignXid, int timeOutSecs, int timeToLiveSecs) throws SystemException {
      if (this.getNumTransactions() > this.getMaxTransactions()) {
         throw new SystemException("Exceeded maximum allowed transactions on server '" + this.getServerName() + "'");
      } else if (!PlatformHelper.getPlatformHelper().isTransactionServiceRunning()) {
         throw new SystemException("The server is being suspended or shut down.  Cannot create new transactions.");
      } else if (this.getDBPassiveModeState() != 0) {
         throw new SystemException("Transaction Service is in PASSIVE mode");
      } else {
         TransactionImpl tx = new ServerTransactionImpl(xid, foreignXid, timeOutSecs, timeToLiveSecs);
         tx.setOwnerTransactionManager(getTM());
         tx.setCoordinatorDescriptor(this.getLocalCoordinatorDescriptor());
         return tx;
      }
   }

   public final void commit(Xid foreignXid) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".commit");
      }

      XAResourceHelper.throwXAException(-4, "Foreign transaction not recognized: " + XAResourceHelper.xidToString(foreignXid) + ".");
   }

   public final void rollback(Xid foreignXid) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".rollback");
      }

      XAResourceHelper.throwXAException(-4, "Foreign transaction not recognized: " + XAResourceHelper.xidToString(foreignXid) + ".");
   }

   public final void forget(Xid foreignXid) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".forget");
      }

      XAResourceHelper.throwXAException(-4, "Foreign transaction not recognized: " + XAResourceHelper.xidToString(foreignXid) + ".");
   }

   public Xid[] recoverForeignXids(int flags) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".recoverForeignXids");
      }

      if (flags == 1001001 || this.isClusterwideRecoveryEnabled() && flags != 1001002) {
         return this.gatherClusterRecoverXids("recover", (Xid)null);
      } else if (flags == 1001003) {
         List xidsOfDeterminers = new ArrayList();
         Iterator var12 = ResourceDescriptor.getAllResources().iterator();

         while(true) {
            ResourceDescriptor rd;
            Xid[] determinerXids;
            do {
               do {
                  do {
                     if (!var12.hasNext()) {
                        Object[] xidObjects = xidsOfDeterminers.toArray();
                        Xid[] xids = new Xid[xidObjects.length];

                        for(int i = 0; i < xidObjects.length; ++i) {
                           xids[i] = (Xid)xidObjects[i];
                        }

                        TxDebug.JTARecovery.debug(this + ".XAResource.recover returns determiner records xids.length:" + xids.length);
                        return xids;
                     }

                     Object o = var12.next();
                     rd = (ResourceDescriptor)o;
                  } while(!(rd instanceof XAResourceDescriptor));
               } while(!rd.isDeterminer());

               determinerXids = ((XAResourceDescriptor)rd).determinersXids;
               TxDebug.JTARecovery.debug(this + ".XAResource.recover DETERMINERXIDSCAN XidImpl determiner rd.getName():" + rd.getName() + " xids(length):" + (determinerXids == null ? "null" : determinerXids.length));
            } while(determinerXids == null);

            xidsOfDeterminers.add(new XidImpl(0, new byte[0], rd.getName().getBytes()));

            for(int i = 0; i > determinerXids.length; ++i) {
               TxDebug.JTARecovery.debug(this + ".XAResource.recover adding determiner Xid record determinerXids[" + i + "]:" + determinerXids[i]);
               xidsOfDeterminers.add(determinerXids[i]);
            }
         }
      } else {
         Xid[] xids = this.tmXARes.getIndoubtXids();
         if (xids == null) {
            xids = new Xid[0];
         }

         synchronized(ServerTransactionManagerImpl.MigratedTLog.migratedTLogs) {
            Set serverNames = ServerTransactionManagerImpl.MigratedTLog.migratedTLogs.keySet();
            if (serverNames.size() == 0) {
               return xids;
            } else {
               Xid[] xidsWithAnyMigrationEntries = new Xid[serverNames.size() + xids.length];
               Iterator iterator = serverNames.iterator();

               for(int i = 0; iterator.hasNext(); xidsWithAnyMigrationEntries[i++] = this.newMigrationXidForServerName((String)iterator.next())) {
               }

               System.arraycopy(xids, 0, xidsWithAnyMigrationEntries, serverNames.size(), xids.length);
               return xidsWithAnyMigrationEntries;
            }
         }
      }
   }

   private Xid newMigrationXidForServerName(String serverName) {
      byte[] serverNameBytes = serverName.getBytes();
      byte[] migrationTxId = new byte[MIGRATION_TXID_PREFIX.length + serverNameBytes.length];
      System.arraycopy(MIGRATION_TXID_PREFIX, 0, migrationTxId, 0, MIGRATION_TXID_PREFIX.length);
      System.arraycopy(serverNameBytes, 0, migrationTxId, MIGRATION_TXID_PREFIX.length, serverNameBytes.length);
      return new XidImpl(migrationTxId);
   }

   public Xid[] gatherClusterRecoverXids(String action, Xid xid) throws XAException {
      if (TxDebug.JTAXA.isDebugEnabled()) {
         TxDebug.JTAXA.debug("ServerTransactionManagerImpl.gatherClusterRecoverXids");
      }

      List xidList = new ArrayList();
      Map servers = PlatformHelper.getPlatformHelper().getClustersITMXAResources();
      List serversThatHaveMigrated = new ArrayList();
      Map serversThatHaveThrownExceptionDuringRecover = new HashMap();
      Iterator serverNameIterator = servers.entrySet().iterator();

      Xid[] xidsFromServer;
      XAResource xaResource;
      while(serverNameIterator.hasNext()) {
         Map.Entry entry = (Map.Entry)serverNameIterator.next();
         xidsFromServer = null;
         xaResource = null;

         try {
            PlatformHelper.ClusterStatus value = (PlatformHelper.ClusterStatus)entry.getValue();
            if (value.xaException != null) {
               throw value.xaException;
            }

            if (serversThatHaveMigrated.contains(entry.getKey())) {
               serversThatHaveThrownExceptionDuringRecover.remove(entry.getKey());
               return new Xid[0];
            }

            xaResource = value.xaResource;
            xidsFromServer = xaResource.recover(1001002);
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("ServerTransactionManagerImpl.gatherClusterRecoverXids entry.getKey()/servername:" + (String)entry.getKey() + "xidsFromServer.length:" + xidsFromServer.length);
            }

            List strings = this.processXidListAddMigratedServersToList(xidsFromServer);
            if (strings != null && strings.size() > 0) {
               serversThatHaveMigrated.addAll(strings);
            }
         } catch (XAException var13) {
            serversThatHaveThrownExceptionDuringRecover.put(entry.getKey(), var13);
         }

         if (xidsFromServer != null) {
            xidList.addAll(Arrays.asList(xidsFromServer).subList(0, xidsFromServer.length));
            if (this.checkForAndProcessXidForAction((String)entry.getKey(), action, xid, xidsFromServer, xaResource)) {
               return null;
            }
         }
      }

      if (!serversThatHaveThrownExceptionDuringRecover.isEmpty()) {
         serverNameIterator = serversThatHaveThrownExceptionDuringRecover.keySet().iterator();

         while(serverNameIterator.hasNext()) {
            xidsFromServer = null;
            xaResource = null;
            String clusterServerName = (String)serverNameIterator.next();

            try {
               xaResource = PlatformHelper.getPlatformHelper().refreshITMXAResourceReference(clusterServerName);
               if (xaResource != null) {
                  xidsFromServer = xaResource.recover(1001002);
               }

               serversThatHaveThrownExceptionDuringRecover.remove(clusterServerName);
            } catch (XAException var14) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "gatherClusterRecoverXids XAException from recover call clusterServerName:" + clusterServerName + " xaResource:" + xaResource + " XAException:" + var14);
               }
            }

            if (xidsFromServer != null) {
               xidList.addAll(Arrays.asList(xidsFromServer).subList(0, xidsFromServer.length));
               if (this.checkForAndProcessXidForAction(clusterServerName, action, xid, xidsFromServer, xaResource)) {
                  return null;
               }
            }
         }
      }

      Xid[] clusterXidList = new Xid[xidList.size()];

      for(int i = 0; i < xidList.size(); ++i) {
         clusterXidList[i] = (Xid)xidList.get(i);
      }

      if (!serversThatHaveThrownExceptionDuringRecover.isEmpty()) {
         Iterator xaExceptionIterator = serversThatHaveThrownExceptionDuringRecover.values().iterator();
         throw (XAException)xaExceptionIterator.next();
      } else if (xid != null) {
         XAException xaException = new XAException("Xid for " + action + " not found in cluster.  ");
         xaException.errorCode = -4;
         throw xaException;
      } else {
         return clusterXidList;
      }
   }

   private boolean checkForAndProcessXidForAction(String serverName, String action, Xid xid, Xid[] xidsFromServer, XAResource xaResource) throws XAException {
      if (!action.equals("recover")) {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("ServerTransactionManagerImpl.checkForAndProcessXidForAction action:" + action + " for serverName:" + serverName);
         }

         Xid[] var6 = xidsFromServer;
         int var7 = xidsFromServer.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Xid aXidsFromServer = var6[var8];
            if (aXidsFromServer.equals(xid)) {
               try {
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     TxDebug.JTAXA.debug("ServerTransactionManagerImpl.checkForAndProcessXidForAction found xid for action:" + action + " on serverName:" + serverName);
                  }

                  if (action.equals("commit")) {
                     xaResource.commit(xid, false);
                  } else if (action.equals("rollback")) {
                     xaResource.rollback(xid);
                  } else if (action.equals("forget")) {
                     xaResource.forget(xid);
                  }

                  return true;
               } catch (XAException var12) {
                  if (var12.errorCode == -4) {
                     XAException notaXAException = new XAException("XAException.XAER_NOTA returned from " + action + " despite the Xid being previously found on the server.  This may be due to migration or failback.  Rethrowing as XAException.XAER_RMFAIL to indicate recovery should be retried.");
                     notaXAException.errorCode = -7;
                     notaXAException.initCause(var12);
                     throw notaXAException;
                  }

                  throw var12;
               }
            }
         }
      }

      return false;
   }

   List processXidListAddMigratedServersToList(Xid[] xidArrayWithMigrationXids) {
      List listOfMigratedServers = new ArrayList();
      Xid[] var3 = xidArrayWithMigrationXids;
      int var4 = xidArrayWithMigrationXids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Xid xidArrayWithMigrationXid = var3[var5];
         byte[] txid = xidArrayWithMigrationXid.getGlobalTransactionId();
         String serverName = this.processXidList(txid);
         if (serverName == null) {
            return listOfMigratedServers;
         }

         listOfMigratedServers.add(serverName);
      }

      return listOfMigratedServers;
   }

   private String processXidList(byte[] txid) {
      for(int txidCounter = 0; txidCounter < txid.length; ++txidCounter) {
         if (txidCounter == MIGRATION_TXID_PREFIX.length) {
            return this.processMigrationXidForServerName(txid);
         }

         if (txid[txidCounter] != MIGRATION_TXID_PREFIX[txidCounter]) {
            return null;
         }
      }

      return null;
   }

   private String processMigrationXidForServerName(byte[] txid) {
      byte[] serverName = new byte[txid.length - MIGRATION_TXID_PREFIX.length];
      System.arraycopy(txid, MIGRATION_TXID_PREFIX.length, serverName, 0, txid.length - MIGRATION_TXID_PREFIX.length);
      return new String(serverName);
   }

   void addCheckpointedLLR(ResourceDescriptor rd) {
      synchronized(this.checkpointedLLRsLock) {
         this.checkpointedLLRs.add(rd);
      }
   }

   private boolean allCheckpointedLLRSAccountedFor() {
      if (this.allCheckpointedLLRsRegistered) {
         return true;
      } else {
         List unaccountedLLRs = this.getUnregisteredCheckpointedLLRs();
         if (unaccountedLLRs.size() == 0) {
            this.allCheckpointedLLRsRegistered = true;
            if (this.runtime != null) {
               this.runtime.healthEvent(new HealthEvent(9, (String)null, "All checkpointed LLR resources available"));
            }

            return true;
         } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < unaccountedLLRs.size(); ++i) {
               ResourceDescriptor rd = (ResourceDescriptor)unaccountedLLRs.get(i);
               if (sb.length() > 0) {
                  sb.append(", ");
               }

               sb.append(rd.getName());
            }

            TXLogger.logCheckpointedLLRResourcesNotAvailable(sb.toString());
            if (this.runtime != null) {
               this.runtime.healthEvent(new HealthEvent(8, (String)null, "LLR Resources {" + sb.toString() + "} are not available"));
            }

            return false;
         }
      }
   }

   private List getUnregisteredCheckpointedLLRs() {
      ArrayList unaccounted = new ArrayList();
      synchronized(this.checkpointedLLRsLock) {
         Iterator it = this.checkpointedLLRs.iterator();

         while(it.hasNext()) {
            ResourceDescriptor rd = (ResourceDescriptor)it.next();
            if (!rd.isRegistered()) {
               unaccounted.add(rd);
            }
         }

         return unaccounted;
      }
   }

   long getLastCheckpointTime() {
      return this.lastCheckpointTime;
   }

   long getCheckpointIntervalMillis() {
      return this.checkpointIntervalMillis;
   }

   public void partitionBeginNotification(BeginListener listener) throws NotSupportedException, SystemException {
      final BeginListener aListener = listener;
      ComponentInvocationContext acic = listener.componentInvocationContext;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("partition BeginNotification listener" + listener + " currentPartitionName:" + PlatformHelper.getPlatformHelper().getPartitionName() + " cic:" + acic);
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, acic, new Callable() {
            public Void call() throws NotSupportedException, SystemException {
               aListener.listener.beginNotification(aListener.handback);
               return null;
            }
         });
      } catch (ExecutionException var6) {
         IllegalStateException ise;
         if (var6.getCause() instanceof IllegalStateException) {
            ise = (IllegalStateException)var6.getCause();
            throw ise;
         } else if (var6.getCause() instanceof SecurityException) {
            SecurityException se = (SecurityException)var6.getCause();
            throw se;
         } else if (var6.getCause() instanceof NotSupportedException) {
            throw (NotSupportedException)var6.getCause();
         } else if (var6.getCause() instanceof SystemException) {
            throw (SystemException)var6.getCause();
         } else {
            ise = new IllegalStateException(var6.getCause());
            ise.initCause(var6.getCause());
            throw ise;
         }
      }
   }

   public void dump(JTAImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      xsw.writeStartElement("JTA");
      xsw.writeAttribute("defaultTimeout", String.valueOf(this.getDefaultTimeoutSeconds()));
      xsw.writeAttribute("abandonTimeout", String.valueOf(this.getAbandonTimeoutSeconds()));
      xsw.writeAttribute("completionTimeout", String.valueOf(this.getCompletionTimeoutSeconds()));
      xsw.writeAttribute("forgetHeuristics", String.valueOf(this.getForgetHeuristics()));
      xsw.writeAttribute("beforeCompletionIterationLimit", String.valueOf(this.getBeforeCompletionIterationLimit()));
      xsw.writeAttribute("maxTransactions", String.valueOf(this.getMaxTransactions()));
      xsw.writeAttribute("maxUniqueNameStatistics", String.valueOf(this.getMaxUniqueNameStatistics()));
      String tlogStoreName = "";
      if (this.getTransactionLogger() != null && this.getTransactionLogger() instanceof StoreTransactionLoggerImpl) {
         tlogStoreName = ((StoreTransactionLoggerImpl)this.getTransactionLogger()).getStore().getName();
      }

      xsw.writeAttribute("tlogStoreName", tlogStoreName);
      String hlogStoreName = "";
      if (this.getHeuristicLogger() != null && this.getHeuristicLogger() instanceof StoreTransactionLoggerImpl) {
         hlogStoreName = ((StoreTransactionLoggerImpl)this.getHeuristicLogger()).getStore().getName();
      }

      xsw.writeAttribute("hlogStoreName", hlogStoreName);
      xsw.writeAttribute("lastCheckpointTime", String.valueOf(this.lastCheckpointTime));
      xsw.writeAttribute("checkpointInterval", String.valueOf(this.checkpointIntervalMillis));
      xsw.writeAttribute("parallelXAEnabled", String.valueOf(parallelXAEnabled));
      xsw.writeAttribute("twoPhaseEnabled", String.valueOf(twoPhaseEnabled));
      xsw.writeStartElement("Health");
      xsw.writeAttribute("state", this.runtime.getHealthStateString());
      String[] reasons = this.runtime.getHealthStateReasonCodes();
      if (reasons != null && reasons.length > 0) {
         for(int i = 0; i < reasons.length; ++i) {
            xsw.writeStartElement("Reason");
            xsw.writeCharacters(reasons[i]);
            xsw.writeEndElement();
         }
      }

      xsw.writeEndElement();
      xsw.writeStartElement("TxMap");
      xsw.writeAttribute("currentCount", String.valueOf(this.txMap.size()));
      Iterator it = this.txMap.values().iterator();

      while(it.hasNext()) {
         ServerTransactionImpl stx = (ServerTransactionImpl)it.next();
         stx.dump(imageSource, xsw, (String)null);
      }

      xsw.writeEndElement();
      xsw.writeStartElement("TMXAResource");

      try {
         xsw.writeAttribute("timeout", String.valueOf(this.tmXARes.getTransactionTimeout()));
      } catch (XAException var10) {
      }

      xsw.writeStartElement("ForeignXids");
      xsw.writeAttribute("currentCount", String.valueOf(this.tmXARes.foreignXidMap.size()));
      it = this.tmXARes.foreignXidMap.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         xsw.writeStartElement("XidMapping");
         xsw.writeAttribute("foreignXid", entry.getKey().toString());
         xsw.writeAttribute("wlXid", entry.getValue().toString());
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
      xsw.writeStartElement("Servers");
      ServerCoordinatorDescriptor[] activeServers = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getActiveServers();
      if (activeServers != null) {
         xsw.writeAttribute("currentCount", String.valueOf(activeServers.length));

         for(int i = 0; i < activeServers.length; ++i) {
            ServerCoordinatorDescriptor scd = activeServers[i];
            scd.dump(imageSource, xsw);
         }
      }

      xsw.writeEndElement();
      ResourceDescriptor.dumpResources(imageSource, xsw);
      xsw.writeEndElement();
   }

   public void dumpPartition(JTAImageSource imageSource, XMLStreamWriter xsw, String partitionName) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      xsw.writeStartElement("JTA");
      int timeoutSeconds = PlatformHelper.getPlatformHelper().getTimeoutPartition(partitionName);
      if (timeoutSeconds == -1) {
         timeoutSeconds = this.getDefaultTimeoutSeconds();
      }

      xsw.writeAttribute("defaultTimeout", String.valueOf(timeoutSeconds));
      xsw.writeAttribute("abandonTimeout", String.valueOf(this.getAbandonTimeoutSeconds()));
      xsw.writeAttribute("completionTimeout", String.valueOf(this.getCompletionTimeoutSeconds()));
      xsw.writeAttribute("forgetHeuristics", String.valueOf(this.getForgetHeuristics()));
      xsw.writeAttribute("beforeCompletionIterationLimit", String.valueOf(this.getBeforeCompletionIterationLimit()));
      xsw.writeAttribute("maxTransactions", String.valueOf(this.getMaxTransactions()));
      xsw.writeAttribute("maxUniqueNameStatistics", String.valueOf(this.getMaxUniqueNameStatistics()));
      String tlogStoreName = "";
      if (this.getTransactionLogger() != null && this.getTransactionLogger() instanceof StoreTransactionLoggerImpl) {
         tlogStoreName = ((StoreTransactionLoggerImpl)this.getTransactionLogger()).getStore().getName();
      }

      xsw.writeAttribute("tlogStoreName", tlogStoreName);
      String hlogStoreName = "";
      if (this.getHeuristicLogger() != null && this.getHeuristicLogger() instanceof StoreTransactionLoggerImpl) {
         hlogStoreName = ((StoreTransactionLoggerImpl)this.getHeuristicLogger()).getStore().getName();
      }

      xsw.writeAttribute("hlogStoreName", hlogStoreName);
      xsw.writeAttribute("lastCheckpointTime", String.valueOf(this.lastCheckpointTime));
      xsw.writeAttribute("checkpointInterval", String.valueOf(this.checkpointIntervalMillis));
      xsw.writeAttribute("parallelXAEnabled", String.valueOf(parallelXAEnabled));
      xsw.writeAttribute("twoPhaseEnabled", String.valueOf(twoPhaseEnabled));
      xsw.writeStartElement("TxMap");
      Iterator itr = this.txMap.values().iterator();
      int mapSize = 0;

      while(itr.hasNext()) {
         ServerTransactionImpl stx = (ServerTransactionImpl)itr.next();
         Map global = stx.getProperties();
         if (partitionName != null && partitionName.equals(global.get("weblogic.transaction.partitionName"))) {
            ++mapSize;
         }
      }

      xsw.writeAttribute("currentCount", String.valueOf(mapSize));
      Iterator it = this.txMap.values().iterator();

      while(it.hasNext()) {
         ServerTransactionImpl stx = (ServerTransactionImpl)it.next();
         stx.dump(imageSource, xsw, partitionName);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   public void registerInterposedSynchronization(Synchronization sync, InterpositionTier tier) {
      if (this != getTransactionManager()) {
         ((ServerTransactionManagerImpl)getTransactionManager()).registerInterposedSynchronization(sync, tier);
      }

      TransactionImpl tx = this.getTxAssociatedWithThread();
      if (tx == null) {
         throw new IllegalStateException("Transaction does not exist");
      } else if (!(tx instanceof ServerTransactionImpl)) {
         throw new IllegalStateException("registerInterposedSynchronization(Synchronization,InterpositionLevel) must be called on Server Transaction");
      } else {
         ((ServerTransactionImpl)tx).registerInterposedSynchronization(sync, tier);
      }
   }

   public void registerInterposedSynchronization(Synchronization sync) {
      if (this != getTransactionManager()) {
         getTransactionManager().registerInterposedSynchronization(sync);
      } else {
         TransactionImpl tx = this.getTxAssociatedWithThread();
         if (tx == null) {
            throw new IllegalStateException("Transaction does not exist");
         } else if (!(tx instanceof ServerTransactionImpl)) {
            throw new IllegalStateException("registerInterposedSynchronization must be called on Server Transaction");
         } else {
            ((ServerTransactionImpl)tx).registerInterposedSynchronization(sync, InterpositionTier.JTA_INTERPOSED_SYNCHRONIZATION);
         }
      }
   }

   public void setOverrideTransactionTimeout(int timeout) {
      this.m_overideTransactionTimeout = timeout;
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }

   void setDBPassiveModeState(int pmstate) {
      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("JTA PassiveMode State: " + this.getDBPassiveModeStateName() + "->" + Constants.PASSIVE_MODE_STATE_NAMES[pmstate]);
      }

      this.passiveModeState.set(pmstate);
   }

   int getDBPassiveModeState() {
      return this.passiveModeState.get();
   }

   String getDBPassiveModeStateName() {
      return Constants.PASSIVE_MODE_STATE_NAMES[this.passiveModeState.get()];
   }

   void setDBPassiveModeGracePeriodSeconds(int seconds) {
      this.passiveModeGracePeriodSeconds.set(seconds);
   }

   int getPassiveModeGracePeriodSeconds() {
      return this.passiveModeGracePeriodSeconds.get();
   }

   void setUsePublicAddressesForRemoteDomains(Set domains) {
      this.usePublicAddressesForRemoteDomains = domains;
   }

   Set getUsePublicAddressesForRemoteDomains() {
      return this.usePublicAddressesForRemoteDomains;
   }

   boolean usePublicAddressesForRemoteDomain(String domain) {
      return this.usePublicAddressesForRemoteDomains == null ? false : this.usePublicAddressesForRemoteDomains.contains(domain);
   }

   void setUseNonSecureAddressesForDomains(Set domains) {
      this.useNonSecureAddressesForRemoteDomains = domains;
   }

   Set getUseNonSecureAddressesForDomains() {
      return this.useNonSecureAddressesForRemoteDomains;
   }

   boolean useNonSecureAddressesforDomain(String domain) {
      return this.useNonSecureAddressesForRemoteDomains == null ? false : this.useNonSecureAddressesForRemoteDomains.contains(domain);
   }

   static {
      String propVal = System.getProperty("weblogic.transaction.EnableInstrumentedTM");
      INSTR_ENABLED = propVal != null && propVal.equals("true");
      isLLRInPartitionAllowed = Boolean.valueOf(System.getProperty("weblogic.transaction.internal.Partition.LLR.Enabled", "false"));
   }

   class TransitionFromPassiveToActive implements Runnable {
      public void run() {
         ServerTransactionManagerImpl.this.setDBPassiveModeState(2);

         try {
            PlatformHelper.getPlatformHelper().activateTransactionRecoveryService();
            ServerTransactionManagerImpl.this.setDBPassiveModeState(0);
         } catch (SystemException var2) {
            if (TxDebug.JTALifecycle.isDebugEnabled()) {
               TxDebug.JTALifecycle.debug((String)"error transitioning PassiveMode state from passive to active.", (Throwable)var2);
            }

            ServerTransactionManagerImpl.this.setDBPassiveModeState(1);
         }

      }
   }

   class TransitionFromActiveToPassive implements Runnable {
      public void run() {
         ServerTransactionManagerImpl.this.setDBPassiveModeState(3);
         int waitSeconds = ServerTransactionManagerImpl.this.getPassiveModeGracePeriodSeconds();

         for(int iteration = 0; iteration < waitSeconds * 10 && ServerTransactionManagerImpl.this.getTxMap().size() != 0; ++iteration) {
            try {
               Thread.sleep(100L);
            } catch (InterruptedException var4) {
            }
         }

         PlatformHelper.getPlatformHelper().passivateTransactionRecoveryService();
         ServerTransactionManagerImpl.this.setDBPassiveModeState(1);
      }
   }

   private class CallCheckStatusAction implements PrivilegedExceptionAction {
      private CoordinatorOneway co = null;
      private Xid[] xids = null;
      private String coUrl = null;

      CallCheckStatusAction(CoordinatorOneway aco, Xid[] axids, String acoUrl) {
         this.co = aco;
         this.xids = axids;
         this.coUrl = acoUrl;
      }

      public Object run() throws Exception {
         this.co.checkStatus(this.xids, this.coUrl);
         return null;
      }
   }

   private class ClusterConfigurationListener implements PropertyChangeListener {
      private ServerTransactionManagerImpl tm;

      ClusterConfigurationListener(ServerTransactionManagerImpl aTm) {
         this.tm = aTm;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         String propertyName = evt.getPropertyName();
         if (ServerTransactionManagerImpl.this.isMemberOfCluster()) {
            Integer valxxx;
            if ("TimeoutSeconds".equals(propertyName)) {
               valxxx = (Integer)evt.getNewValue();
               if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getTimeoutSeconds()) {
                  return;
               }

               this.tm.setDefaultTimeoutSeconds(valxxx);

               try {
                  this.tm.setTransactionTimeout(valxxx);
               } catch (SystemException var9) {
               }
            } else if ("AbandonTimeoutSeconds".equals(propertyName)) {
               valxxx = (Integer)evt.getNewValue();
               if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getAbandonTimeoutSeconds()) {
                  return;
               }

               this.tm.setAbandonTimeoutSeconds(valxxx);
            } else {
               Boolean valxxxx;
               if ("ForgetHeuristics".equals(propertyName)) {
                  valxxxx = (Boolean)evt.getNewValue();
                  if (valxxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getForgetHeuristics()) {
                     return;
                  }

                  this.tm.setForgetHeuristics(valxxxx);
               } else if ("BeforeCompletionIterationLimit".equals(propertyName)) {
                  valxxx = (Integer)evt.getNewValue();
                  if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getBeforeCompletionIterationLimit()) {
                     return;
                  }

                  this.tm.setBeforeCompletionIterationLimit(valxxx);
               } else if ("MaxTransactions".equals(propertyName)) {
                  valxxx = (Integer)evt.getNewValue();
                  if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getMaxTransactions()) {
                     return;
                  }

                  this.tm.setMaxTransactions(valxxx);
               } else if ("MaxUniqueNameStatistics".equals(propertyName)) {
                  valxxx = (Integer)evt.getNewValue();
                  if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getMaxUniqueNameStatistics()) {
                     return;
                  }

                  this.tm.setMaxUniqueNameStatistics(valxxx);
               } else {
                  Long val;
                  if ("MaxTransactionsHealthIntervalMillis".equals(propertyName)) {
                     val = (Long)evt.getNewValue();
                     if (val != ServerTransactionManagerImpl.this.jtaClusterConfig.getMaxTransactionsHealthIntervalMillis()) {
                        return;
                     }

                     ServerTransactionManagerImpl var10000 = this.tm;
                     ServerTransactionManagerImpl.setMaxTransactionsHealthIntervalMillis(val);
                  } else if ("MaxXACallMillis".equals(propertyName)) {
                     val = (Long)evt.getNewValue();
                     if (val != ServerTransactionManagerImpl.this.jtaClusterConfig.getMaxXACallMillis()) {
                        return;
                     }

                     XAResourceDescriptor.setMaxXACallMillis(val);
                  } else if ("MaxResourceUnavailableMillis".equals(propertyName)) {
                     val = (Long)evt.getNewValue();
                     if (val != ServerTransactionManagerImpl.this.jtaClusterConfig.getMaxResourceUnavailableMillis()) {
                        return;
                     }

                     XAResourceDescriptor.setMaxResourceUnavailableMillis(val);
                  } else if ("MigrationCheckpointIntervalSeconds".equals(propertyName)) {
                     valxxx = (Integer)evt.getNewValue();
                     if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getMigrationCheckpointIntervalSeconds()) {
                        return;
                     }

                     ServerTransactionManagerImpl.this.setMigrationCheckpointIntervalMillis((long)(valxxx * 1000));
                  } else if ("PurgeResourceFromCheckpointIntervalSeconds".equals(propertyName)) {
                     valxxx = (Integer)evt.getNewValue();
                     if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getPurgeResourceFromCheckpointIntervalSeconds()) {
                        return;
                     }

                     ServerTransactionManagerImpl.this.setPurgeResourceFromCheckpointIntervalSeconds(valxxx);
                  } else if ("CheckpointIntervalSeconds".equals(propertyName)) {
                     valxxx = (Integer)evt.getNewValue();
                     if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getCheckpointIntervalSeconds()) {
                        return;
                     }

                     this.tm.setCheckpointIntervalSeconds(valxxx);
                  } else if ("SerializeEnlistmentsGCIntervalMillis".equals(propertyName)) {
                     val = (Long)evt.getNewValue();
                     if (val != ServerTransactionManagerImpl.this.jtaClusterConfig.getSerializeEnlistmentsGCIntervalMillis()) {
                        return;
                     }

                     XAResourceDescriptor.setSerializeEnlistmentsGCIntervalMillis(val);
                  } else if ("ParallelXAEnabled".equals(propertyName)) {
                     valxxxx = (Boolean)evt.getNewValue();
                     if (valxxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.isParallelXAEnabled()) {
                        return;
                     }

                     this.tm.setParallelXAEnabled(valxxxx);
                  } else {
                     String valx;
                     if ("ParallelXADispatchPolicy".equals(propertyName)) {
                        valx = (String)evt.getNewValue();
                        if (valx != ServerTransactionManagerImpl.this.jtaClusterConfig.getParallelXADispatchPolicy()) {
                           return;
                        }

                        this.tm.setParallelXADispatchPolicy((String)evt.getNewValue());
                     } else if ("UnregisterResourceGracePeriod".equals(propertyName)) {
                        valxxx = (Integer)evt.getNewValue();
                        if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getUnregisterResourceGracePeriod()) {
                           return;
                        }

                        this.tm.setUnregisterResourceGracePeriodMillis(valxxx);
                     } else if ("SecurityInteropMode".equals(propertyName)) {
                        valx = (String)evt.getNewValue();
                        if (valx != ServerTransactionManagerImpl.this.jtaClusterConfig.getSecurityInteropMode()) {
                           return;
                        }

                        this.tm.setInteropMode(valx);
                     } else if ("CompletionTimeoutSeconds".equals(propertyName)) {
                        valxxx = (Integer)evt.getNewValue();
                        if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getCompletionTimeoutSeconds()) {
                           return;
                        }

                        this.tm.setCompletionTimeoutSeconds(valxxx);
                     } else if ("ClusterwideRecoveryEnabled".equals(propertyName)) {
                        valxxxx = (Boolean)evt.getNewValue();
                        if (valxxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.isClusterwideRecoveryEnabled()) {
                           return;
                        }

                        this.tm.setClusterwideRecoveryEnabled(valxxxx);
                     } else if ("TLOGWriteWhenDeterminerExistsEnabled".equals(propertyName)) {
                        valxxxx = (Boolean)evt.getNewValue();
                        if (valxxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.isTLOGWriteWhenDeterminerExistsEnabled()) {
                           return;
                        }

                        this.tm.setTLOGWriteWhenDeterminerExistsEnabled(valxxxx);
                     } else if ("ShutdownGracePeriod".equals(propertyName)) {
                        valxxx = (Integer)evt.getNewValue();
                        if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getShutdownGracePeriod()) {
                           return;
                        }

                        this.tm.setShutdownGracePeriod(valxxx);
                     } else if ("RecoverySiteName".equals(propertyName)) {
                        valx = (String)evt.getNewValue();
                        if (valx != ServerTransactionManagerImpl.this.jtaClusterConfig.getRecoverySiteName()) {
                           return;
                        }

                        this.tm.setRecoverySiteName(valx);
                     } else if ("TightlyCoupledTransactionsEnabled".equals(propertyName)) {
                        valxxxx = (Boolean)evt.getNewValue();
                        if (valxxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.isTightlyCoupledTransactionsEnabled()) {
                           return;
                        }

                        this.tm.setTightlyCoupledTransactionsEnabled(valxxxx);
                     } else {
                        String[] valxx;
                        if ("Determiners".equals(propertyName)) {
                           valxx = (String[])((String[])evt.getNewValue());
                           if (!Arrays.equals(valxx, ServerTransactionManagerImpl.this.jtaClusterConfig.getDeterminers())) {
                              return;
                           }

                           this.tm.setPartitionDeterminers("GLOBAL", valxx);
                           this.tm.setDeterminers(this.tm.getDeterminersForDomainAndAllPartitions());
                        } else if ("SiteName".equals(propertyName)) {
                           valx = (String)evt.getNewValue();
                           if (valx != null && valx.length() == 0) {
                              valx = null;
                           }

                           this.tm.setSiteName(valx);
                        } else if ("MaxRetrySecondsBeforeDeterminerFail".equals(propertyName)) {
                           valxxx = (Integer)evt.getNewValue();
                           if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getMaxRetrySecondsBeforeDeterminerFail()) {
                              return;
                           }

                           this.tm.setMaxRetrySecondsBeforeDeterminerFail(valxxx);
                        } else if ("CrossDomainRecoveryRetryInterval".equals(propertyName)) {
                           valxxx = (Integer)evt.getNewValue();
                           if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getCrossDomainRecoveryRetryInterval()) {
                              return;
                           }

                           this.tm.setCrossDomainRecoveryRetryInterval(valxxx);
                        } else if ("CrossSiteRecoveryRetryInterval".equals(propertyName)) {
                           valxxx = (Integer)evt.getNewValue();
                           if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getCrossSiteRecoveryRetryInterval()) {
                              return;
                           }

                           this.tm.setCrossSiteRecoveryRetryInterval(valxxx);
                        } else if ("CrossSiteRecoveryLeaseExpiration".equals(propertyName)) {
                           valxxx = (Integer)evt.getNewValue();
                           if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getCrossSiteRecoveryLeaseExpiration()) {
                              return;
                           }

                           this.tm.setCrossSiteRecoveryLeaseExpiration(valxxx);
                        } else if ("CrossSiteRecoveryLeaseUpdate".equals(propertyName)) {
                           valxxx = (Integer)evt.getNewValue();
                           if (valxxx != ServerTransactionManagerImpl.this.jtaClusterConfig.getCrossSiteRecoveryLeaseExpiration()) {
                              return;
                           }

                           this.tm.setCrossSiteRecoveryLeaseExpiration(valxxx);
                        } else {
                           HashSet domainSet;
                           String[] var5;
                           int var6;
                           int var7;
                           String domain;
                           if ("UseNonSecureAddressesForDomains".equals(propertyName)) {
                              valxx = (String[])((String[])evt.getNewValue());
                              domainSet = new HashSet();
                              var5 = valxx;
                              var6 = valxx.length;

                              for(var7 = 0; var7 < var6; ++var7) {
                                 domain = var5[var7];
                                 domainSet.add(domain);
                              }

                              this.tm.setUseNonSecureAddressesForDomains(domainSet);
                              CoordinatorFactory.clearCachedEntries();
                           } else if ("UsePublicAddressesForRemoteDomains".equals(propertyName)) {
                              valxx = (String[])((String[])evt.getNewValue());
                              domainSet = new HashSet();
                              var5 = valxx;
                              var6 = valxx.length;

                              for(var7 = 0; var7 < var6; ++var7) {
                                 domain = var5[var7];
                                 domainSet.add(domain);
                              }

                              this.tm.setUsePublicAddressesForRemoteDomains(domainSet);
                              CoordinatorFactory.clearCachedEntries();
                           }
                        }
                     }
                  }
               }
            }

         }
      }
   }

   private class ConfigurationListener implements PropertyChangeListener {
      private ServerTransactionManagerImpl tm;

      ConfigurationListener(ServerTransactionManagerImpl aTm) {
         this.tm = aTm;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         String propertyName = evt.getPropertyName();
         if ("DBPassiveMode".equals(propertyName)) {
            boolean passivemode = (Boolean)evt.getNewValue();
            if (passivemode) {
               if (ServerTransactionManagerImpl.this.getDBPassiveModeState() == 0) {
                  PlatformHelper.getPlatformHelper().scheduleWork(ServerTransactionManagerImpl.this.new TransitionFromActiveToPassive());
               }
            } else if (ServerTransactionManagerImpl.this.getDBPassiveModeState() == 1) {
               PlatformHelper.getPlatformHelper().scheduleWork(ServerTransactionManagerImpl.this.new TransitionFromPassiveToActive());
            }
         } else if ("DBPassiveModeGracePeriodSeconds".equals(propertyName)) {
            ServerTransactionManagerImpl.this.setDBPassiveModeGracePeriodSeconds((Integer)evt.getNewValue());
         }

         if (!ServerTransactionManagerImpl.this.isMemberOfCluster()) {
            Integer valxxxx;
            if ("TimeoutSeconds".equals(propertyName)) {
               valxxxx = (Integer)evt.getNewValue();
               this.tm.setDefaultTimeoutSeconds(valxxxx);

               try {
                  this.tm.setTransactionTimeout(valxxxx);
               } catch (SystemException var9) {
               }
            } else if ("AbandonTimeoutSeconds".equals(propertyName)) {
               valxxxx = (Integer)evt.getNewValue();
               this.tm.setAbandonTimeoutSeconds(valxxxx);
            } else {
               Boolean val;
               if ("ForgetHeuristics".equals(propertyName)) {
                  val = (Boolean)evt.getNewValue();
                  this.tm.setForgetHeuristics(val);
               } else if ("BeforeCompletionIterationLimit".equals(propertyName)) {
                  valxxxx = (Integer)evt.getNewValue();
                  this.tm.setBeforeCompletionIterationLimit(valxxxx);
               } else if ("MaxTransactions".equals(propertyName)) {
                  valxxxx = (Integer)evt.getNewValue();
                  this.tm.setMaxTransactions(valxxxx);
               } else if ("MaxUniqueNameStatistics".equals(propertyName)) {
                  valxxxx = (Integer)evt.getNewValue();
                  this.tm.setMaxUniqueNameStatistics(valxxxx);
               } else {
                  Long valx;
                  if ("MaxTransactionsHealthIntervalMillis".equals(propertyName)) {
                     valx = (Long)evt.getNewValue();
                     ServerTransactionManagerImpl var10000 = this.tm;
                     ServerTransactionManagerImpl.setMaxTransactionsHealthIntervalMillis(valx);
                  } else if ("MaxXACallMillis".equals(propertyName)) {
                     valx = (Long)evt.getNewValue();
                     XAResourceDescriptor.setMaxXACallMillis(valx);
                  } else if ("MaxResourceUnavailableMillis".equals(propertyName)) {
                     valx = (Long)evt.getNewValue();
                     XAResourceDescriptor.setMaxResourceUnavailableMillis(valx);
                  } else if ("MigrationCheckpointIntervalSeconds".equals(propertyName)) {
                     valxxxx = (Integer)evt.getNewValue();
                     ServerTransactionManagerImpl.this.setMigrationCheckpointIntervalMillis((long)(valxxxx * 1000));
                  } else if ("PurgeResourceFromCheckpointIntervalSeconds".equals(propertyName)) {
                     valxxxx = (Integer)evt.getNewValue();
                     ServerTransactionManagerImpl.this.setPurgeResourceFromCheckpointIntervalSeconds(valxxxx);
                  } else if ("CheckpointIntervalSeconds".equals(propertyName)) {
                     valxxxx = (Integer)evt.getNewValue();
                     this.tm.setCheckpointIntervalSeconds(valxxxx);
                  } else if ("SerializeEnlistmentsGCIntervalMillis".equals(propertyName)) {
                     valx = (Long)evt.getNewValue();
                     XAResourceDescriptor.setSerializeEnlistmentsGCIntervalMillis(valx);
                  } else if ("ParallelXAEnabled".equals(propertyName)) {
                     val = (Boolean)evt.getNewValue();
                     this.tm.setParallelXAEnabled(val);
                  } else if ("ParallelXADispatchPolicy".equals(propertyName)) {
                     this.tm.setParallelXADispatchPolicy((String)evt.getNewValue());
                  } else if ("UnregisterResourceGracePeriod".equals(propertyName)) {
                     valxxxx = (Integer)evt.getNewValue();
                     this.tm.setUnregisterResourceGracePeriodMillis(valxxxx);
                  } else {
                     String valxx;
                     if ("SecurityInteropMode".equals(propertyName)) {
                        valxx = (String)evt.getNewValue();
                        this.tm.setInteropMode(valxx);
                     } else if ("CompletionTimeoutSeconds".equals(propertyName)) {
                        valxxxx = (Integer)evt.getNewValue();
                        this.tm.setCompletionTimeoutSeconds(valxxxx);
                     } else if ("ClusterwideRecoveryEnabled".equals(propertyName)) {
                        val = (Boolean)evt.getNewValue();
                        this.tm.setClusterwideRecoveryEnabled(val);
                     } else if ("TightlyCoupledTransactionsEnabled".equals(propertyName)) {
                        val = (Boolean)evt.getNewValue();
                        this.tm.setTightlyCoupledTransactionsEnabled(val);
                     } else if ("TLOGWriteWhenDeterminerExistsEnabled".equals(propertyName)) {
                        val = (Boolean)evt.getNewValue();
                        this.tm.setTLOGWriteWhenDeterminerExistsEnabled(val);
                     } else if ("ShutdownGracePeriod".equals(propertyName)) {
                        valxxxx = (Integer)evt.getNewValue();
                        this.tm.setShutdownGracePeriod(valxxxx);
                     } else if ("SiteName".equals(propertyName)) {
                        valxx = (String)evt.getNewValue();
                        if (valxx != null && valxx.length() == 0) {
                           valxx = null;
                        }

                        this.tm.setSiteName(valxx);
                     } else if ("RecoverySiteName".equals(propertyName)) {
                        valxx = (String)evt.getNewValue();
                        this.tm.setRecoverySiteName(valxx);
                     } else if ("MaxRetrySecondsBeforeTLOGFail".equals(propertyName)) {
                        valxxxx = (Integer)evt.getNewValue();
                        this.tm.setJdbcTLogMaxRetrySecondsBeforeTLOGFail(valxxxx);
                     } else if ("MaxRetrySecondsBeforeTXException".equals(propertyName)) {
                        valxxxx = (Integer)evt.getNewValue();
                        this.tm.setJdbcTLogMaxRetrySecondsBeforeTXException(valxxxx);
                     } else if ("RetryIntervalSeconds".equals(propertyName)) {
                        valxxxx = (Integer)evt.getNewValue();
                        this.tm.setJdbcTLogRetryIntervalSeconds(valxxxx);
                     } else {
                        String[] valxxx;
                        if ("Determiners".equals(propertyName)) {
                           valxxx = (String[])((String[])evt.getNewValue());
                           this.tm.setPartitionDeterminers("GLOBAL", valxxx);
                           this.tm.setDeterminers(this.tm.getDeterminersForDomainAndAllPartitions());
                        } else if ("MaxRetrySecondsBeforeDeterminerFail".equals(propertyName)) {
                           valxxxx = (Integer)evt.getNewValue();
                           this.tm.setMaxRetrySecondsBeforeDeterminerFail(valxxxx);
                        } else if ("CrossDomainRecoveryRetryInterval".equals(propertyName)) {
                           valxxxx = (Integer)evt.getNewValue();
                           this.tm.setCrossDomainRecoveryRetryInterval(valxxxx);
                        } else if ("CrossSiteRecoveryRetryInterval".equals(propertyName)) {
                           valxxxx = (Integer)evt.getNewValue();
                           this.tm.setCrossSiteRecoveryRetryInterval(valxxxx);
                        } else if ("CrossSiteRecoveryLeaseExpiration".equals(propertyName)) {
                           valxxxx = (Integer)evt.getNewValue();
                           this.tm.setCrossSiteRecoveryLeaseExpiration(valxxxx);
                        } else if ("CrossSiteRecoveryLeaseUpdate".equals(propertyName)) {
                           valxxxx = (Integer)evt.getNewValue();
                           this.tm.setCrossSiteRecoveryLeaseUpdate(valxxxx);
                        } else {
                           HashSet domainSet;
                           String[] var5;
                           int var6;
                           int var7;
                           String domain;
                           if ("UseNonSecureAddressesForDomains".equals(propertyName)) {
                              valxxx = (String[])((String[])evt.getNewValue());
                              domainSet = new HashSet();
                              var5 = valxxx;
                              var6 = valxxx.length;

                              for(var7 = 0; var7 < var6; ++var7) {
                                 domain = var5[var7];
                                 domainSet.add(domain);
                              }

                              this.tm.setUseNonSecureAddressesForDomains(domainSet);
                              CoordinatorFactory.clearCachedEntries();
                           } else if ("UsePublicAddressesForRemoteDomains".equals(propertyName)) {
                              valxxx = (String[])((String[])evt.getNewValue());
                              domainSet = new HashSet();
                              var5 = valxxx;
                              var6 = valxxx.length;

                              for(var7 = 0; var7 < var6; ++var7) {
                                 domain = var5[var7];
                                 domainSet.add(domain);
                              }

                              this.tm.setUsePublicAddressesForRemoteDomains(domainSet);
                              CoordinatorFactory.clearCachedEntries();
                           }
                        }
                     }
                  }
               }
            }

         }
      }
   }

   class BeginListener {
      BeginNotificationListener listener;
      Object handback;
      ComponentInvocationContext componentInvocationContext;

      BeginListener(BeginNotificationListener l, Object h, ComponentInvocationContext cic) {
         this.listener = l;
         this.handback = h;
         this.componentInvocationContext = cic;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else {
            if (o instanceof BeginListener) {
               BeginListener that = (BeginListener)o;
               if (this.listener == that.listener) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public static class MigratedTLog {
      String serverName;
      String siteName;
      private CoordinatorDescriptor cd;
      private StoreTransactionLoggerImpl tlog;
      private JTARecoveryRuntime rtMBean;
      boolean isNotFromThisSite;
      RecoveryCompletionListener recoveryCompletionListener;
      private int llrRecoveringCount;
      private int llrInitialRecoveredTransactionTotalCount;
      private int llrRecoveredTransactionCompletionCount;
      static volatile Map migratedTLogs = new HashMap();

      MigratedTLog(String serverName) throws Exception {
         this(serverName, (String)null, (RecoveryCompletionListener)null);
      }

      public MigratedTLog(String serverName, String siteName, RecoveryCompletionListener recoveryCompletionListener) throws Exception {
         this.isNotFromThisSite = siteName != null;
         this.serverName = serverName;
         this.siteName = siteName;
         this.recoveryCompletionListener = recoveryCompletionListener;
         ServerCoordinatorDescriptorManager coordinatorDescriptorManager = (ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager();
         this.cd = coordinatorDescriptorManager.getOrCreateForMigration(serverName);
         if (this.cd == null && !this.isNotFromThisSite) {
            throw new Exception("Cannot obtain ServerCoordinatorDescriptor");
         } else {
            if (this.cd != null && siteName != null) {
               this.cd.setSiteName(siteName);
            }

            if (!this.isNotFromThisSite) {
               TxDebug.JTARecovery.debug("MigratedTLog.MigratedTLog !isNotFromThisSite (ie is from this site)");
               this.tlog = new StoreTransactionLoggerImpl(this.cd.getServerName(), this.cd.getCoordinatorURL());
               this.rtMBean = PlatformHelper.getPlatformHelper().getJTARecoveryRuntime(serverName);
            } else {
               TxDebug.JTARecovery.debug("MigratedTLog.MigratedTLog isNotFromThisSite");
               this.tlog = new StoreTransactionLoggerImpl(serverName, "~", true);
               this.rtMBean = PlatformHelper.getPlatformHelper().manageCrossSiteJTARecoveryRuntime(siteName, serverName, true);
            }

            if (this.rtMBean != null && this.tlog != null) {
               this.rtMBean.reset(this.tlog.getInitialRecoveredTransactionTotalCount());
            }

            XAResourceDescriptor.getOrCreateForJMSMigratedTLog(this.cd);
            if (ServerTransactionManagerImpl.m_migratableRM != null) {
               ServerTransactionManagerImpl.m_migratableRM.createForMigration(this.cd.serverName + "_" + this.cd.domainName, this.tlog.getStore());
            }

            migratedTLogs.put((siteName == null ? "" : siteName + ".") + serverName, this);
         }
      }

      private static MigratedTLog get(String serverName) {
         synchronized(migratedTLogs) {
            return (MigratedTLog)migratedTLogs.get(serverName);
         }
      }

      private synchronized void incLLRRecoveringCount() {
         ++this.llrRecoveringCount;
         ++this.llrInitialRecoveredTransactionTotalCount;
      }

      private synchronized void updateLLRCompletionStatistics() {
         --this.llrRecoveringCount;
         ++this.llrRecoveredTransactionCompletionCount;
      }

      private synchronized int getLLRRecoveringCount() {
         return this.llrRecoveringCount;
      }

      private boolean hasRecoveringLLRTransactions() {
         return this.getLLRRecoveringCount() > 0;
      }

      private static MigratedTLog getOrCreate(String serverName) throws Exception {
         synchronized(migratedTLogs) {
            MigratedTLog mtlog = (MigratedTLog)migratedTLogs.get(serverName);
            if (mtlog == null) {
               MigratedTLog var10000;
               try {
                  var10000 = new MigratedTLog(serverName);
               } catch (Exception var5) {
                  if (TxDebug.JTAMigration.isDebugEnabled()) {
                     TxDebug.JTAMigration.debug((String)("Creation of MigratedTLog for server '" + serverName + "' failed"), (Throwable)var5);
                  }

                  throw var5;
               }

               return var10000;
            } else {
               return mtlog;
            }
         }
      }

      static synchronized void release(String serverName) {
         MigratedTLog mtlog = (MigratedTLog)migratedTLogs.remove(serverName);
         if (mtlog != null) {
            mtlog.checkpointForTLog();
            mtlog.cleanup();
         }

      }

      private static synchronized void checkpoint() {
         Iterator iter = migratedTLogs.values().iterator();

         while(true) {
            while(true) {
               MigratedTLog mtlog;
               do {
                  if (!iter.hasNext()) {
                     return;
                  }

                  mtlog = (MigratedTLog)iter.next();
               } while(!mtlog.checkpointForTLog());

               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("MigratedTLog.checkpoint: recovery for '" + mtlog.serverName + "' is done. Note that this recovery verification process takes place when the migration policy is set to shutdown-recovery (for example in a dynamic cluster) regardless of whether there are actually transactions that need to be recovered.");
               }

               if (ServerTransactionManagerImpl.INSTR_ENABLED && checkInstrServer("weblogic.transaction.failback.ignoreServer", mtlog.serverName)) {
                  TxDebug.JTAMigration.debug("***INSTR***: skip failback for " + mtlog.serverName);
               } else {
                  iter.remove();
                  mtlog.cleanup();
                  if (!mtlog.isNotFromThisSite) {
                     if (PlatformHelper.getPlatformHelper().allowAutoTRSFailback(mtlog.serverName)) {
                        PlatformHelper.getPlatformHelper().scheduleFailBack(mtlog.serverName);
                     } else {
                        if (TxDebug.JTAMigration.isDebugEnabled()) {
                           TxDebug.JTAMigration.debug("MigratedTLog.checkpoint: delaying auto-failback of " + mtlog.serverName);
                        }

                        PlatformHelper.getPlatformHelper().delayAutoTRSFailback(mtlog.serverName);
                     }
                  }
               }
            }
         }
      }

      private static final boolean checkInstrServer(String instr, String serverName) {
         String instrServers = System.getProperty(instr);
         if (instrServers != null && instrServers.length() != 0) {
            String[] result = instrServers.split("[,]");
            String[] var4 = result;
            int var5 = result.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String aResult = var4[var6];
               if (serverName.equals(aResult)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }

      private boolean checkpointForTLog() {
         this.tlog.checkpoint();
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("MigratedTLog.checkpointForTLog for '" + this.serverName + " tlog.hasTransactionLogRecords():" + this.tlog.hasTransactionLogRecords() + " hasRecoveringLLRTransactions():" + this.hasRecoveringLLRTransactions());
         }

         return !this.tlog.hasTransactionLogRecords() && !this.hasRecoveringLLRTransactions();
      }

      private void cleanup() {
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("MigratedTLog.cleanup for '" + this.serverName);
         }

         if (this.rtMBean != null && this.tlog != null) {
            this.rtMBean.setFinalTransactionCompletionCount(this.getTransactionCompletionCount());
         }

         this.purgeTxMap();
         XAResourceDescriptor.releaseMigratedTLog(this.cd);

         try {
            this.tlog.close();
            if (this.isNotFromThisSite) {
               TxDebug.JTARecovery.debug("MigratedTLog.cleanup for '" + this.serverName + " deactivate rtMBean:" + this.rtMBean + " and call recoveryCompletionListener.onCompletion()");
               this.rtMBean = PlatformHelper.getPlatformHelper().manageCrossSiteJTARecoveryRuntime(this.siteName, this.serverName, false);
               if (this.recoveryCompletionListener != null) {
                  this.recoveryCompletionListener.onCompletion();
               }

               TxDebug.JTARecovery.debug("MigratedTLog.cleanup for '" + this.serverName + " complete");
            }
         } catch (Exception var2) {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug((String)("MigratedTLog.cleanup error closing store for '" + this.serverName), (Throwable)var2);
            }
         }

      }

      private void purgeTxMap() {
         ((ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTM()).purgeMigratedTransactions(this.tlog);
         ((ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTM()).purgeMigratedLLRTransactions(this.serverName);
      }

      private int getInitialTransactionTotalCount() {
         return this.tlog.getInitialRecoveredTransactionTotalCount() + this.llrInitialRecoveredTransactionTotalCount;
      }

      private int getTransactionCompletionCount() {
         return this.tlog.getRecoveredTransactionCompletionCount() + this.llrRecoveredTransactionCompletionCount;
      }

      CoordinatorDescriptor getServerCoordinatorDescriptor() {
         return this.cd;
      }

      interface RecoveryCompletionListener {
         void onCompletion();
      }
   }

   class RegisteredLoggingResourceTransactions {
      LoggingResource loggingResource;

      public RegisteredLoggingResourceTransactions(LoggingResource loggingResource) {
         this.loggingResource = loggingResource;
      }

      public void registerLoggingResourceTransactions() throws LoggingResourceException {
         this.registerLoggingResourceTransactions(true);
      }

      public void registerLoggingResourceTransactions(boolean callLLRBootException) throws LoggingResourceException {
         String debugStr = "register logging resource " + this.loggingResource;
         ServerTransactionManagerImpl.this.llrCurrentRecoveredTransactionCount = 0;
         if (TxDebug.JTALLR.isDebugEnabled()) {
            TxDebug.JTALLR.debug(debugStr);
         }

         if (ServerTransactionManagerImpl.this.getTransactionLogger() == null) {
            try {
               ServerTransactionManagerImpl.this.setTLog(new StoreTransactionLoggerImpl(false, ServerTransactionManagerImpl.this.runtime));
            } catch (IOException var6) {
               ServerTransactionManagerImpl.this.runtime.healthEvent(new HealthEvent(1, ServerTransactionManagerImpl.this.serverName, "Unable to access transaction log '" + ServerTransactionManagerImpl.this.serverName + "'"));
               throw new LoggingResourceException();
            }
         }

         byte[][] recoveredRecords;
         LoggingResourceException se;
         try {
            recoveredRecords = this.loggingResource.recoverXARecords();
         } catch (Throwable var7) {
            if (TxDebug.JTALLR.isDebugEnabled()) {
               TxDebug.JTALLR.debug(debugStr, var7);
            }

            if (callLLRBootException) {
               ServerTransactionManagerImpl.this.setLLRBootException(var7);
            }

            se = new LoggingResourceException(var7.toString());
            se.initCause(var7);
            throw se;
         }

         try {
            for(int j = 0; j < recoveredRecords.length; ++j) {
               ServerTransactionManagerImpl.this.registerLoggingResourceRecord(this.loggingResource, recoveredRecords[j], (String)null);
            }
         } catch (Throwable var8) {
            if (TxDebug.JTALLR.isDebugEnabled()) {
               TxDebug.JTALLR.debug(debugStr, var8);
            }

            se = new LoggingResourceException(TXExceptionLogger.logCorruptedLLRRecordLoggable(this.loggingResource.toString(), var8.toString()).getMessage());
            se.initCause(var8);
            ServerTransactionManagerImpl.this.setLLRBootException(var8);
            throw se;
         }

         JTARecoveryRuntime rtMBean = PlatformHelper.getPlatformHelper().getJTARecoveryRuntime(ServerTransactionManagerImpl.this.serverName);
         if (rtMBean != null && ServerTransactionManagerImpl.this.getTransactionLogger() != null) {
            rtMBean.reset(ServerTransactionManagerImpl.this.getTransactionLogger().getInitialRecoveredTransactionTotalCount() + ServerTransactionManagerImpl.this.llrCurrentRecoveredTransactionCount);
         }

         NonXAResourceDescriptor.getOrCreate((NonXAResource)this.loggingResource);
         if (TxDebug.JTALLR.isDebugEnabled()) {
            TxDebug.JTALLR.debug(debugStr + " num-recovered=" + recoveredRecords.length);
         }

      }
   }
}
