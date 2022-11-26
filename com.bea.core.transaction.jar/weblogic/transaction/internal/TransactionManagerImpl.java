package weblogic.transaction.internal;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionRolledbackException;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.rmi.spi.Interceptor;
import weblogic.transaction.InterposedTransactionManager;
import weblogic.transaction.ServerTransactionInterceptor;
import weblogic.transaction.TMXAResourceInterface;
import weblogic.transaction.TransactionInterceptor;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TransactionSystemException;
import weblogic.transaction.UserTransaction;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.nonxa.NonXAResource;

public abstract class TransactionManagerImpl implements Serializable, Interceptor, InterposedTransactionManager, UserTransaction, TransactionManager, ServerTransactionInterceptor, TransactionSynchronizationRegistry {
   private static final long serialVersionUID = 6667343049945344440L;
   protected static final Object singletonLock = new Object();
   public transient ConcurrentHashMap txMap = null;
   protected volatile int defaultTimeoutSec = 30;
   protected int abandonTimeoutSec = 86400;
   protected int completionTimeoutSec = 0;
   public List timedOutTransactions = Collections.synchronizedList(new ArrayList(250));
   protected boolean isTwoPhaseCommitEnabled = true;
   protected boolean isClusterwideRecoveryEnabled;
   protected boolean isTightlyCoupledTransactionsEnabled;
   protected boolean isTLOGWriteWhenDeterminerExistsEnabled;
   protected int shutdownGracePeriod;
   protected volatile String siteName;
   protected volatile String recoverySiteName;
   protected String[] determiners;
   public ConcurrentHashMap partitionDeterminersMap = new ConcurrentHashMap(1);
   protected int maxRetrySecondsBeforeDeterminerFail;
   protected int crossDomainRecoveryRetryInterval;
   protected int crossSiteRecoveryRetryInterval;
   protected int crossSiteRecoveryLeaseExpiration;
   protected int crossSiteRecoveryLeaseUpdate;
   protected boolean isJdbcTLogEnabled;
   protected String jdbcTLogPrefixName;
   protected int jdbcTLogMaxRetrySecondsBeforeTLOGFail;
   protected int jdbcTLogMaxRetrySecondsBeforeTXException;
   protected int jdbcTLogRetryIntervalSeconds;
   protected String jdbcTLogDataSource;
   protected boolean isJdbcTLogInitialized;
   private volatile List unRegisteredDeterminerList = new ArrayList();
   private boolean isAnyDeterminerUnregistered;
   protected static final byte[] MIGRATION_TXID_PREFIX = new byte[]{109, 105, 103, 114, 97, 116, 105, 111, 110};
   public static final String CLUSTERCOMMIT = "CLUSTERCOMMIT";
   public static final String CLUSTERROLLBACK = "CLUSTERROLLBACK";
   public static final String CLUSTERFORGET = "CLUSTERFORGET";
   protected long lastTimerFire;
   protected long lastWakeUpDuration;
   private static Throwable timerFailureReason;
   public static boolean initialized = false;
   protected static final Object txThreadLocal;
   protected TMXAResource tmXARes = null;
   public boolean isReturnTransactionThreadStateAwareITMXAResource = false;
   protected static volatile TransactionManagerImpl singleton;

   protected static void setTimerFailureReason(Throwable reason) {
      timerFailureReason = reason;
   }

   protected TxThreadProperty getThreadProp() {
      return (TxThreadProperty)PlatformHelper.getPlatformHelper().getTxThreadPropertyFromTxThreadLocal(txThreadLocal);
   }

   protected TransactionManagerImpl() {
      setTransactionManager(this);
   }

   public void begin() throws NotSupportedException, SystemException {
      this.internalBegin((String)null, 0);
   }

   public void begin(String name) throws NotSupportedException, SystemException {
      this.internalBegin(name, 0);
   }

   public void begin(int timeoutseconds) throws NotSupportedException, SystemException {
      this.internalBegin((String)null, timeoutseconds);
   }

   public void begin(String name, int timeoutseconds) throws NotSupportedException, SystemException {
      this.internalBegin(name, timeoutseconds);
   }

   public void begin(Map properties) throws NotSupportedException, SystemException {
      boolean isNamePropertySet = properties != null && properties.get("name") != null && properties.get("name") instanceof String;
      boolean isTimeoutPropertySet = properties != null && properties.get("transaction-timeout") != null && properties.get("transaction-timeout") instanceof Integer;
      this.internalBegin(isNamePropertySet ? (String)properties.get("name") : null, isTimeoutPropertySet ? (Integer)properties.get("transaction-timeout") : 0);
      boolean isCompletionTimeoutPropertySet = properties != null && properties.get("completion-timeout-seconds") != null && properties.get("completion-timeout-seconds") instanceof Integer;
      if (isCompletionTimeoutPropertySet) {
         ((TransactionImpl)this.getTransaction()).setProperty("completion-timeout-seconds", ((Integer)properties.get("completion-timeout-seconds")).toString());
      }

   }

   private int getTxnTimeoutSeconds() {
      int txTimeout = getTM().getDefaultTimeoutSeconds();
      int timeoutForPartition = PlatformHelper.getPlatformHelper().getTimeoutPartition();
      if (timeoutForPartition != -1) {
         txTimeout = timeoutForPartition;
      }

      return txTimeout;
   }

   protected void internalBegin(String name, int timeoutseconds) throws NotSupportedException, SystemException {
      if (this != getTransactionManager()) {
         getTransactionManager().begin(name, timeoutseconds);
      } else if (!this.isRunning()) {
         throw new SystemException("The server is being suspended or shut down.  Cannot begin new transactions.");
      } else {
         try {
            TxThreadProperty txp = this.getThreadProp();
            if (txp.current != null && !txp.current.isOver()) {
               if (!txp.isUserTransactionAllowed) {
                  throw new IllegalStateException("Another transaction is associated with this thread. Existing transaction " + txp.current);
               } else {
                  throw new NotSupportedException("Another transaction is associated with this thread. Existing transaction " + txp.current);
               }
            } else {
               int timeout;
               if (timeoutseconds > 0) {
                  timeout = timeoutseconds;
               } else if (txp.timeoutSec == 0) {
                  timeout = this.getTxnTimeoutSeconds();
               } else {
                  timeout = txp.timeoutSec;
               }

               TransactionImpl tx = this.createTransaction(getNewXID(), timeout, timeout);
               this.associateTxWithThread(tx);
               tx.setOwnerTransactionManager(getTM());
               if (name != null) {
                  tx.setName(name);
               }

            }
         } catch (IllegalStateException var6) {
            throw var6;
         } catch (NotSupportedException var7) {
            throw var7;
         } catch (SystemException var8) {
            throw var8;
         } catch (Exception var9) {
            TXLogger.logBeginUnexpectedException(PlatformHelper.getPlatformHelper().throwable2StackTrace(var9));
            throw new SystemException(var9.toString());
         }
      }
   }

   public final int getStatus() throws SystemException {
      if (this != getTransactionManager()) {
         return getTransactionManager().getStatus();
      } else {
         Transaction tx = this.getTxAssociatedWithThread();
         return tx == null ? 6 : tx.getStatus();
      }
   }

   public final void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
      if (this != getTransactionManager()) {
         getTransactionManager().commit();
      } else {
         Transaction tx = this.getTxAssociatedWithThread();
         if (tx == null) {
            throw new IllegalStateException("A transaction has not been begun in this thread");
         } else {
            tx.commit();
         }
      }
   }

   public final void rollback() throws IllegalStateException, SecurityException, SystemException {
      if (this != getTransactionManager()) {
         getTransactionManager().rollback();
      } else {
         Transaction tx = this.getTxAssociatedWithThread();
         if (tx == null) {
            throw new IllegalStateException("Transaction does not exist");
         } else {
            tx.rollback();
         }
      }
   }

   public final void setRollbackOnly() throws IllegalStateException {
      if (this != getTransactionManager()) {
         getTransactionManager().setRollbackOnly();
      } else {
         Transaction tx = this.getTxAssociatedWithThread();
         if (tx == null) {
            throw new IllegalStateException("Transaction does not exist");
         } else {
            try {
               tx.setRollbackOnly();
            } catch (SystemException var3) {
               throw new IllegalStateException(PlatformHelper.getPlatformHelper().throwable2StackTrace(var3));
            }
         }
      }
   }

   public Transaction suspend() throws SystemException {
      return (Transaction)(this != getTransactionManager() ? getTransactionManager().suspend() : this.internalSuspend());
   }

   public void resume(Transaction atx) throws InvalidTransactionException, IllegalStateException, SystemException {
      if (this != getTransactionManager()) {
         getTransactionManager().resume(atx);
      } else if (atx == null) {
         throw new InvalidTransactionException("Cannot resume a null transaction");
      } else if (!(atx instanceof TransactionImpl)) {
         throw new InvalidTransactionException("Cannot resume transaction: " + atx.getClass());
      } else {
         TransactionImpl tx = (TransactionImpl)atx;
         if (!tx.isOver() && !tx.isCancelled()) {
            if (!tx.isResumePossible()) {
               throw new InvalidTransactionException("Attempt to resume transaction after response(s) sent back to Coordinator");
            } else {
               TransactionImpl existingTx = this.getTxAssociatedWithThread();
               if (existingTx != null && !existingTx.equals(tx) && !existingTx.isOver() && !existingTx.isCancelled()) {
                  throw new IllegalStateException("Thread is already associated with another transaction" + PlatformHelper.getPlatformHelper().getEOLConstant() + "{" + existingTx.getXid().toString() + "}");
               } else {
                  this.internalResume(tx);
               }
            }
         } else {
            throw new InvalidTransactionException("Attempt to resume an inactive transaction: " + tx.getXid().toString());
         }
      }
   }

   public final void setTransactionTimeout(int seconds) throws SystemException {
      if (this != getTransactionManager()) {
         getTransactionManager().setTransactionTimeout(seconds);
      } else if (seconds < 0) {
         throw new SystemException("Invalid value " + seconds + " passed to setTransactionTimeout(int)");
      } else {
         TxThreadProperty txp = this.getThreadProp();
         txp.timeoutSec = seconds;
      }
   }

   public Transaction getTransaction(Xid xid) {
      if (xid == null) {
         return null;
      } else {
         if (this.txMap != null) {
            Transaction nativeTx = (Transaction)this.txMap.get(xid);
            if (nativeTx != null) {
               return nativeTx;
            }
         }

         XidImpl foreignXid = XidImpl.create(xid);
         TransactionImpl transaction = this.tmXARes.get(foreignXid);
         return transaction == null && getTM().isTightlyCoupledTransactionsEnabled ? this.tmXARes.getWithoutBranchqualEquals(foreignXid) : transaction;
      }
   }

   public void registerStaticResource(String name, XAResource xar) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void registerStaticResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void registerDynamicResource(String name, XAResource xar) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void registerDynamicResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void registerDynamicResource(String name, NonXAResource nxar) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void registerResource(String name, XAResource xar) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void registerResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void registerResource(String name, XAResource xar, boolean localResourceAssignment) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void registerResource(String name, XAResource xar, Hashtable properties, boolean localResourceAssignment) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be registered only in a server process");
   }

   public void unregisterResource(String name) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be unregistered only in a server process");
   }

   public void unregisterResource(String name, boolean blocking) throws SystemException {
      throw new SystemException("Resource '" + name + "'can be unregistered only in a server process");
   }

   public void registerLoggingResourceTransactions(LoggingResource lr) throws SystemException {
      throw new SystemException("Logging resource transactions can only be registered in a server process");
   }

   public void registerFailedLoggingResource(Throwable t) {
      throw new RuntimeException("Failed logging resources can only be registered in a server process");
   }

   public void registerCoordinatorService(String serviceName, weblogic.transaction.CoordinatorService cs) {
      throw new AssertionError("Coordinator services can only be registered in a server process");
   }

   public TransactionInterceptor getInterceptor() {
      return getTransactionManager();
   }

   public Transaction forceSuspend() {
      TransactionManagerImpl tm = getTransactionManager();
      return (Transaction)(this != tm ? tm.forceSuspend() : this.internalSuspend());
   }

   public void forceResume(Transaction atx) {
      TransactionManagerImpl tm = getTransactionManager();
      if (this != tm) {
         tm.forceResume(atx);
      } else {
         this.internalResume((TransactionImpl)atx);
      }
   }

   public void internalForceSuspend() {
   }

   public void setReturnTransactionThreadStateAwareITMXAResource(boolean isReturnTransactionThreadStateAwareITMXAResource) {
      this.isReturnTransactionThreadStateAwareITMXAResource = isReturnTransactionThreadStateAwareITMXAResource;
   }

   public XAResource getXAResource() {
      return (XAResource)(this.isReturnTransactionThreadStateAwareITMXAResource ? new InterposedTransactionManagerXAResource(this, this.tmXARes) : this.tmXARes);
   }

   public final Transaction getTransaction() {
      if (getTransactionManager() != this) {
         return getTransactionManager().getTransaction();
      } else {
         TxThreadProperty txp = this.getThreadProp();
         return txp == null ? null : txp.current;
      }
   }

   public final void setClusterwideRecoveryEnabled(boolean isClusterwideRecoveryEnabled) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setClusterwideRecoveryEnabled(isClusterwideRecoveryEnabled=" + isClusterwideRecoveryEnabled + ")");
      }

      this.isClusterwideRecoveryEnabled = isClusterwideRecoveryEnabled;
   }

   public Object sendRequest(Object endPoint) throws RemoteException {
      TransactionImpl tx = (TransactionImpl)this.getTransaction();
      if (tx == null) {
         return null;
      } else if (tx.getProperty("LOCAL_ENTITY_TX") != null) {
         return null;
      } else {
         boolean infectCoordinatorFirstTime = PlatformHelper.getPlatformHelper().sendRequest(endPoint);
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, "sendRequest " + tx + "), infectCoordinatorFirstTime:" + infectCoordinatorFirstTime);
         }

         if (tx.getCoordinatorDescriptor() == null) {
            throw new RemoteException("Message was not sent because coordinator cannot be assigned");
         } else if (!tx.isActive()) {
            throw new RemoteException("Message was not sent because transaction is not active. " + tx.getXid().toString());
         } else {
            tx.incrRepliesOwedMe();
            PropagationContext pctx = tx.getRequestPropagationContext();
            if (infectCoordinatorFirstTime) {
               pctx.infectCoordinatorFirstTime();
            }

            return pctx;
         }
      }
   }

   public final void receiveRequest(Object aTxContext) throws RemoteException {
      if (aTxContext != null) {
         TransactionImpl tx = getTransactionFromContext(aTxContext);
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAPropagate, "receiveRequest, tx=" + tx);
         }

         if (tx == null) {
            throw new TransactionRolledbackException("Current server is the coordinator and transaction is not found.  It was probably rolled back and forgotten already.");
         }

         if (tx.isCancelled() && !(tx.getRollbackReason() instanceof AppSetRollbackOnlyException)) {
            if (tx.getRollbackReason() == null) {
               throw new TransactionRolledbackException();
            }

            throw new TransactionRolledbackException(tx.getRollbackReason().getMessage());
         }

         this.internalSuspend();
         tx.incrRepliesOwedOthers();
      }

   }

   public final Object sendResponse(Object incomingContext) throws RemoteException {
      TransactionImpl tx = null;

      PropagationContext var4;
      try {
         PropagationContext pctx;
         if (incomingContext == null) {
            pctx = null;
            return pctx;
         }

         tx = getTransactionFromContext(incomingContext);
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, "sendResponse, tx=" + tx);
         }

         if (tx == null) {
            throw new TransactionRolledbackException("Current server is the coordinator and transaction is not found.  It was probably rolled back and forgotten already.");
         }

         pctx = tx.getResponsePropagationContext();
         if (!tx.decrRepliesOwedOthers()) {
            TransactionRolledbackException rbex = new TransactionRolledbackException("Cannot reply to caller when there are outstanding replies due to this server");
            tx.setRollbackOnly((Throwable)rbex);
            throw rbex;
         }

         var4 = pctx;
      } finally {
         if (tx != null) {
            this.suspend(tx);
         } else {
            tx = (TransactionImpl)this.getTransaction();
            if (tx != null) {
               if (TxDebug.JTAPropagate.isDebugEnabled()) {
                  TxDebug.JTAPropagate.debug("sendResponse, tx associated with thread:" + tx);
               }

               this.suspend(tx);
            }
         }

      }

      return var4;
   }

   public void receiveAsyncResponse(Object aTxContext) throws RemoteException {
      if (aTxContext != null) {
         TransactionImpl tx = getTransactionFromContext(aTxContext);
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAPropagate, "receiveAsyncResponse, tx=" + tx);
         }

         if (tx == null) {
            throw new TransactionRolledbackException("Current server is the coordinator and transaction is not found.  It was probably rolled back and forgotten already.");
         }

         tx.decrRepliesOwedMe();
      }

   }

   public void receiveResponse(Object aTxContext) throws RemoteException {
      if (aTxContext != null) {
         TransactionImpl tx = getTransactionFromContext(aTxContext);
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAPropagate, "receiveResponse, tx=" + tx);
         }

         if (tx == null) {
            throw new TransactionRolledbackException("Current server is the coordinator and transaction is not found.  It was probably rolled back and forgotten already.");
         }

         tx.decrRepliesOwedMe();
         this.internalResume(tx);
      }

   }

   public boolean isParticipant(String serverName) {
      weblogic.transaction.Transaction tx = (weblogic.transaction.Transaction)getTM().getTransaction();
      if (tx != null) {
         Set allParticipants = tx.getServerParticipantNames();
         if (allParticipants != null) {
            return allParticipants.contains(serverName);
         }
      }

      return false;
   }

   public boolean needsInterception() {
      weblogic.transaction.Transaction tx = (weblogic.transaction.Transaction)getTM().getTransaction();
      return tx != null;
   }

   public void dispatchRequest(Object aTxContext) throws RemoteException {
      if (aTxContext != null) {
         TransactionImpl tx = getTransactionFromContext(aTxContext);
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, "dispatchRequest, tx=" + tx);
         }

         if (tx == null) {
            return;
         }

         this.internalResume(tx);
         if (tx.isCancelled() && !(tx.getRollbackReason() instanceof AppSetRollbackOnlyException)) {
            if (tx.getRollbackReason() == null) {
               throw new TransactionRolledbackException();
            }

            throw new TransactionRolledbackException(tx.getRollbackReason().getMessage());
         }
      }

   }

   public int getTransactionTimeout(Object context) {
      return context instanceof PropagationContext ? ((PropagationContext)context).getTimeoutSecs() * 1000 : 0;
   }

   protected static final TransactionImpl getTransactionFromContext(Object aTxContext) throws TransactionSystemException {
      if (aTxContext instanceof PropagationContext) {
         return ((PropagationContext)aTxContext).getTransaction();
      } else if (aTxContext instanceof TransactionImpl) {
         return (TransactionImpl)getTM().getTransaction(((TransactionImpl)aTxContext).getXID());
      } else {
         throw new AssertionError("Context not a PropagationContext or Transaction: " + aTxContext.getClass().getName());
      }
   }

   public static boolean isInitialized() {
      return singleton != null && initialized;
   }

   public void suspend(Transaction tx) {
      Transaction txThread = this.getTxAssociatedWithThread();
      if (txThread != null && txThread == tx) {
         this.internalSuspend();
      }
   }

   public TransactionImpl internalSuspend() {
      return this.associateTxWithThread((TransactionImpl)null);
   }

   public TransactionImpl internalResume(TransactionImpl tx) {
      return this.associateTxWithThread(tx);
   }

   public final int getTransactionTimeout() {
      TxThreadProperty txp = this.getThreadProp();
      return txp.timeoutSec == 0 ? getTM().getDefaultTimeoutSeconds() : txp.timeoutSec;
   }

   public static void setTransactionManager(TransactionManagerImpl tm) {
      if (singleton == null) {
         synchronized(singletonLock) {
            if (singleton == null) {
               tm.init();
               singleton = tm;
            }
         }
      }

   }

   public synchronized boolean isXidInTxMap(Xid xid) {
      return this.txMap.containsKey(xid);
   }

   public CoordinatorDescriptor getLocalCoordinatorDescriptor() {
      return null;
   }

   public String getLocalCoordinatorURL() {
      return null;
   }

   public boolean isLocalCoordinator(CoordinatorDescriptor aCoDesc) {
      return false;
   }

   public boolean isLocalCoordinator(String scURL) {
      return false;
   }

   public int getNumTransactions() {
      return this.txMap.size();
   }

   public synchronized Iterator getTransactions() {
      try {
         ArrayList al = new ArrayList(this.txMap.values());
         return al.iterator();
      } catch (Exception var2) {
         return null;
      }
   }

   public synchronized Iterator getTransactions(String partitionName) {
      try {
         Iterator itr = this.txMap.values().iterator();
         int mapSize = 0;

         while(itr.hasNext()) {
            TransactionImpl tx = (TransactionImpl)itr.next();
            Map global = tx.getProperties();
            if (partitionName != null && partitionName.equals(global.get("weblogic.transaction.partitionName"))) {
               ++mapSize;
            }
         }

         ArrayList al = new ArrayList(mapSize);
         Iterator itr2 = this.txMap.values().iterator();

         while(itr2.hasNext()) {
            TransactionImpl tx = (TransactionImpl)itr2.next();
            Map global = tx.getProperties();
            if (partitionName != null && partitionName.equals(global.get("weblogic.transaction.partitionName"))) {
               al.add(tx);
            }
         }

         return al.iterator();
      } catch (Exception var8) {
         return null;
      }
   }

   public final void setDefaultTimeoutSeconds(int sec) {
      this.defaultTimeoutSec = sec;
   }

   public final int getDefaultTimeoutSeconds() {
      return this.defaultTimeoutSec;
   }

   public final void setAbandonTimeoutSeconds(int sec) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setAbandonTimeoutSeconds(sec=" + sec + ")");
      }

      this.abandonTimeoutSec = sec;
   }

   public final int getAbandonTimeoutSeconds() {
      return this.abandonTimeoutSec;
   }

   public final void setCompletionTimeoutSeconds(int sec) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setCompletionTimeoutSeconds(sec=" + sec + ")");
      }

      this.completionTimeoutSec = sec;
   }

   public final int getCompletionTimeoutSeconds() {
      return this.completionTimeoutSec;
   }

   public final void setTwoPhaseCommitEnabled(boolean twoPhaseCommitEnabled) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setTwoPhaseCommitEnabled(isTwoPhaseCommitEnabled=" + twoPhaseCommitEnabled + ")");
      }

      this.isTwoPhaseCommitEnabled = twoPhaseCommitEnabled;
   }

   public final boolean getTwoPhaseCommitEnabled() {
      return this.isTwoPhaseCommitEnabled;
   }

   public final void setJdbcTLogEnabled(boolean isJdbcTLogEnabled) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setJdbcTLogEnabled(isJdbcTLogEnabled=" + isJdbcTLogEnabled + ")");
      }

      this.isJdbcTLogEnabled = isJdbcTLogEnabled;
   }

   public final boolean getJdbcTLogEnabled() {
      return this.isJdbcTLogEnabled;
   }

   public final void setJdbcTLogPrefixName(String jdbcTLogPrefixName) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setJdbcTLogPrefixName(jdbcTLogPrefixName=" + jdbcTLogPrefixName + ")");
      }

      this.jdbcTLogPrefixName = jdbcTLogPrefixName;
   }

   public final String getJdbcTLogPrefixName() {
      return this.jdbcTLogPrefixName;
   }

   public final void setJdbcTLogMaxRetrySecondsBeforeTLOGFail(int jdbcTLogMaxRetrySecondsBeforeTLOGFail) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setJdbcTLogMaxRetrySecondsBeforeTLOGFail(jdbcTLogMaxRetrySecondsBeforeTLOGFail=" + jdbcTLogMaxRetrySecondsBeforeTLOGFail + ")");
      }

      this.jdbcTLogMaxRetrySecondsBeforeTLOGFail = jdbcTLogMaxRetrySecondsBeforeTLOGFail;
   }

   public final int getJdbcTLogMaxRetrySecondsBeforeTLOGFail() {
      return this.jdbcTLogMaxRetrySecondsBeforeTLOGFail;
   }

   public final void setJdbcTLogMaxRetrySecondsBeforeTXException(int jdbcTLogMaxRetrySecondsBeforeTXException) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setTwoPhaseCommitEnabled(jdbcTLogMaxRetrySecondsBeforeTXException=" + jdbcTLogMaxRetrySecondsBeforeTXException + ")");
      }

      this.jdbcTLogMaxRetrySecondsBeforeTXException = jdbcTLogMaxRetrySecondsBeforeTXException;
   }

   public final int getJdbcTLogMaxRetrySecondsBeforeTXException() {
      return this.jdbcTLogMaxRetrySecondsBeforeTXException;
   }

   public final void setJdbcTLogRetryIntervalSeconds(int jdbcTLogRetryIntervalSeconds) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setJdbcTLogRetryIntervalSeconds(jdbcTLogRetryIntervalSeconds=" + jdbcTLogRetryIntervalSeconds + ")");
      }

      this.jdbcTLogRetryIntervalSeconds = jdbcTLogRetryIntervalSeconds;
   }

   public final int getJdbcTLogRetryIntervalSeconds() {
      return this.jdbcTLogRetryIntervalSeconds;
   }

   public final void setJdbcTLogDataSource(String jdbcTLogDataSource) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setJdbcTLogDataSource(jdbcTLogDataSource=" + jdbcTLogDataSource + ")");
      }

      this.jdbcTLogDataSource = jdbcTLogDataSource;
   }

   public final String getJdbcTLogDataSource() {
      return this.jdbcTLogDataSource;
   }

   public final void setJdbcTLogInitialized(boolean isJdbcTLogInitialized) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setJdbcTLogInitialized(isJdbcTLogInitialized=" + isJdbcTLogInitialized + ")");
      }

      this.isJdbcTLogInitialized = isJdbcTLogInitialized;
   }

   public final boolean isClusterwideRecoveryEnabled() {
      return this.isClusterwideRecoveryEnabled;
   }

   public final boolean isTightlyCoupledTransactionsEnabled() {
      return this.isTightlyCoupledTransactionsEnabled;
   }

   public final void setTightlyCoupledTransactionsEnabled(boolean isTightlyCoupledTransactionsEnabled) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setTightlyCoupledTransactionsEnabled(isTightlyCoupledTransactionsEnabled=" + isTightlyCoupledTransactionsEnabled + ")");
      }

      this.isTightlyCoupledTransactionsEnabled = isTightlyCoupledTransactionsEnabled;
   }

   public final boolean isTLOGWriteWhenDeterminerExistsEnabled() {
      return this.isTLOGWriteWhenDeterminerExistsEnabled;
   }

   public final void setTLOGWriteWhenDeterminerExistsEnabled(boolean isTLOGWriteWhenDeterminerExistsEnabled) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setTLOGWriteWhenDeterminerExistsEnabled(isTLOGWriteWhenDeterminerExistsEnabled=" + isTLOGWriteWhenDeterminerExistsEnabled + ")");
      }

      this.isTLOGWriteWhenDeterminerExistsEnabled = isTLOGWriteWhenDeterminerExistsEnabled;
   }

   public final void setMaxRetrySecondsBeforeDeterminerFail(int maxRetrySecondsBeforeDeterminerFail) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setMaxRetrySecondsBeforeDeterminerFail(maxRetrySecondsBeforeDeterminerFail=" + maxRetrySecondsBeforeDeterminerFail + ")");
      }

      this.maxRetrySecondsBeforeDeterminerFail = maxRetrySecondsBeforeDeterminerFail;
   }

   public final int getMaxRetrySecondsBeforeDeterminerFail() {
      return this.maxRetrySecondsBeforeDeterminerFail;
   }

   public final void setCrossDomainRecoveryRetryInterval(int crossDomainRecoveryRetryInterval) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setCrossDomainRecoveryRetryInterval(crossDomainRecoveryRetryInterval=" + crossDomainRecoveryRetryInterval + ")");
      }

      this.crossDomainRecoveryRetryInterval = crossDomainRecoveryRetryInterval;
   }

   public final int getCrossDomainRecoveryRetryInterval() {
      return this.crossDomainRecoveryRetryInterval;
   }

   public final void setCrossSiteRecoveryRetryInterval(int crossSiteRecoveryRetryInterval) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setCrossSiteRecoveryRetryInterval(crossSiteRecoveryRetryInterval=" + crossSiteRecoveryRetryInterval + ")");
      }

      this.crossSiteRecoveryRetryInterval = crossSiteRecoveryRetryInterval;
   }

   public final int getCrossSiteRecoveryRetryInterval() {
      return this.crossSiteRecoveryRetryInterval;
   }

   public final void setCrossSiteRecoveryLeaseExpiration(int crossSiteRecoveryLeaseExpiration) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setCrossSiteRecoveryLeaseExpiration(crossSiteRecoveryLeaseExpiration=" + crossSiteRecoveryLeaseExpiration + ")");
      }

      this.crossSiteRecoveryLeaseExpiration = crossSiteRecoveryLeaseExpiration;
   }

   public final int getCrossSiteRecoveryLeaseUpdate() {
      return this.crossSiteRecoveryLeaseUpdate;
   }

   public final void setCrossSiteRecoveryLeaseUpdate(int crossSiteRecoveryLeaseUpdate) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setCrossSiteRecoveryLeaseUpdate(crossSiteRecoveryLeaseUpdate=" + crossSiteRecoveryLeaseUpdate + ")");
      }

      this.crossSiteRecoveryLeaseUpdate = crossSiteRecoveryLeaseUpdate;
   }

   public final int getCrossSiteRecoveryLeaseExpiration() {
      return this.crossSiteRecoveryLeaseExpiration;
   }

   public final int getShutdownGracePeriod() {
      return this.shutdownGracePeriod;
   }

   public final void setShutdownGracePeriod(int shutdownGracePeriod) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setShutdownGracePeriod(shutdownGracePeriod=" + shutdownGracePeriod + ")");
      }

      this.shutdownGracePeriod = shutdownGracePeriod;
   }

   public final String getSiteName() {
      return this.siteName;
   }

   public final void setSiteName(String siteName) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setSiteName(siteName=" + siteName + ")");
      }

      this.siteName = siteName;
   }

   public final String getRecoverySiteName() {
      return this.recoverySiteName;
   }

   public final void setRecoverySiteName(String recoverySiteName) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug(this + ".setRecoverySiteName(recoverySiteName=" + recoverySiteName + ")");
      }

      this.recoverySiteName = recoverySiteName;
   }

   public final String[] getDeterminers() {
      return this.determiners;
   }

   public void setPartitionDeterminers(String partitionName, String[] dtmnrs) {
      if (dtmnrs != null) {
         this.partitionDeterminersMap.put(partitionName, dtmnrs.clone());
      }

   }

   public void setDeterminers() {
      int detSize = 0;

      String[] newDeterminers;
      for(Iterator it = this.partitionDeterminersMap.values().iterator(); it.hasNext(); detSize += newDeterminers.length) {
         newDeterminers = (String[])((String[])it.next());
      }

      int startPos;
      if (detSize != 0) {
         newDeterminers = new String[detSize];
         Iterator it2 = this.partitionDeterminersMap.values().iterator();
         startPos = 0;

         while(it2.hasNext()) {
            String[] det = (String[])((String[])it2.next());
            int inLength = det.length;
            if (inLength != 0) {
               System.arraycopy(det, 0, newDeterminers, startPos, inLength);
               startPos += inLength;
            }
         }

         this.determiners = newDeterminers;
      } else {
         this.determiners = new String[0];
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         String dtmrs = "";
         String[] var9 = this.determiners;
         startPos = var9.length;

         for(int var10 = 0; var10 < startPos; ++var10) {
            String s = var9[var10];
            dtmrs = dtmrs + " " + s;
         }

         TxDebug.JTA2PC.debug(this + ".setDeterminers(determiners = " + dtmrs + ")");
      }

   }

   public final boolean isJdbcTLogInitialized() {
      return this.isJdbcTLogInitialized;
   }

   public String getServerName() {
      return null;
   }

   public boolean isTimerStarted() {
      return timerFailureReason == null;
   }

   public static Throwable getTimerFailureReason() {
      return timerFailureReason;
   }

   protected void processTimedOutTransactions(List timedOutTransactions, int nowSec) {
      int cnt = 100;
      Iterator iter = timedOutTransactions.iterator();

      while(iter.hasNext()) {
         TransactionImpl tx = (TransactionImpl)iter.next();
         iter.remove();
         tx.wakeUp(nowSec);
         if (cnt-- <= 0) {
            break;
         }
      }

   }

   protected void reset() {
      this.txMap = null;
   }

   protected static Xid getNewXID() {
      return XidImpl.create();
   }

   public final long getLastTimerFire() {
      return this.lastTimerFire;
   }

   public final long getTimeSinceLastTimerFire() {
      return System.currentTimeMillis() - this.lastTimerFire;
   }

   public final long getLastWakeUpDuration() {
      return this.lastWakeUpDuration;
   }

   protected final Object resetValue(Object currentValue) {
      getTM().internalForceSuspend();
      TxThreadProperty txp = (TxThreadProperty)currentValue;
      txp.timeoutSec = 0;
      txp.current = null;
      txp.preferredHostSetForTx = false;
      txp.preTxPreferredHost = null;
      txp.isUserTransactionAllowed = true;
      return currentValue;
   }

   public void setCDIOnTxThreadProperty(boolean isUserTransactionAllowed) {
      TxThreadProperty txp = this.getThreadProp();
      txp.isUserTransactionAllowed = isUserTransactionAllowed;
   }

   void add(TransactionImpl tx) {
      this.add(tx, false);
   }

   void add(TransactionImpl tx, boolean migratedTx) {
      Xid xid = tx.getXID();
      xid.hashCode();
      if (migratedTx || !tx.isCancelled() && tx.isImportedTransaction() || this.txMap.putIfAbsent(xid, tx) != null) {
         synchronized(this) {
            TransactionImpl old = (TransactionImpl)this.txMap.put(xid, tx);
            if (old != null && !migratedTx) {
               if (old == tx) {
                  TXLogger.logAddDuplicateTxToMap();
                  return;
               }

               SystemException se = new SystemException("Attempt to add transaction to TM when another existed. Both have been marked rollback");
               old.setRollbackOnly((Throwable)se);
               tx.setRollbackOnly((Throwable)se);
            }

            if (migratedTx) {
               this.txMap.put(tx.getXid(), tx);
               this.tmXARes.add(tx.getXid(), tx);
            } else if (!tx.isCancelled() && tx.isImportedTransaction()) {
               if (TxDebug.JTAGateway.isDebugEnabled()) {
                  TxDebug.JTAGateway.debug(this + ".add(" + tx + ")");
               }

               this.tmXARes.add(tx.getForeignXid(), tx);
            }

         }
      }
   }

   final void remove(TransactionImpl tx) {
      Xid xid = tx.getXID();
      xid.hashCode();
      boolean condition = tx.isImportedTransaction();
      if (!condition) {
         this.txMap.remove(xid);
      } else {
         synchronized(this) {
            this.txMap.remove(xid);
            if (condition) {
               if (TxDebug.JTAGateway.isDebugEnabled()) {
                  TxDebug.JTAGateway.debug(this + ".remove(" + tx + ")");
               }

               this.tmXARes.remove(tx.getForeignXid());
            }

         }
      }
   }

   Transaction getTransactionUnsync(Xid xid) {
      if (xid == null) {
         return null;
      } else {
         Transaction nativeTx = (Transaction)this.txMap.get(xid);
         return (Transaction)(nativeTx != null ? nativeTx : this.tmXARes.get(xid));
      }
   }

   public abstract TransactionImpl createTransaction(Xid var1, int var2, int var3) throws SystemException;

   public abstract TransactionImpl createTransaction(Xid var1, int var2, int var3, boolean var4) throws SystemException;

   public abstract TransactionImpl createTransaction(Xid var1, int var2, int var3, boolean var4, boolean var5) throws SystemException;

   public abstract TransactionImpl createImportedTransaction(Xid var1, Xid var2, int var3, int var4) throws SystemException;

   public abstract void commit(Xid var1) throws XAException;

   public abstract Xid[] gatherClusterRecoverXids(String var1, Xid var2) throws XAException;

   public abstract void rollback(Xid var1) throws XAException;

   public abstract void forget(Xid var1) throws XAException;

   public abstract Xid[] recoverForeignXids(int var1) throws XAException;

   public abstract boolean isRunning();

   public abstract void addToListOfAckCommits(Xid var1, String var2);

   protected TransactionImpl getTxAssociatedWithThread() {
      TxThreadProperty txp = this.getThreadProp();
      if (txp == null) {
         return null;
      } else {
         synchronized(txp) {
            return txp.current;
         }
      }
   }

   protected static void replaceTransactionManager(TransactionManagerImpl tm) {
      synchronized(singletonLock) {
         TransactionManagerImpl oldtm = singleton;
         if (oldtm != tm) {
            singleton = null;
            setTransactionManager(tm);
            if (oldtm != null) {
               oldtm.reset();
            }
         }

      }
   }

   public void init() {
      this.txMap = new ConcurrentHashMap(100);
      this.tmXARes = new TMXAResource();
      this.tmXARes.init();
      PlatformHelper.getPlatformHelper().registerRMITransactionInterceptor(this);
   }

   private TMXAResourceInterface getTMXAResource() {
      return (TMXAResourceInterface)getTM().getXAResource();
   }

   public void setSSLURLFromClientInfo(InterposedTransactionManager itm, Context initialContext) {
      PlatformHelper.getPlatformHelper().setSSLURLFromClientInfo((ClientTransactionManagerImpl)itm, initialContext);
   }

   private TransactionImpl associateTxWithThread(TransactionImpl tx) {
      TxThreadProperty txp = this.getThreadProp();
      if (txp == null) {
         return null;
      } else {
         TransactionImpl ret = txp.current;
         txp.current = tx;
         if (ret != null) {
            ret.setActiveThread((Thread)null);
         }

         PlatformHelper.getPlatformHelper().associateThreadPreferredHost(tx, txp);
         return ret;
      }
   }

   public void wakeUp() {
      long nowMillis = System.currentTimeMillis();
      this.lastTimerFire = nowMillis;
      int nowSec = (int)(nowMillis / 1000L);
      if (nowMillis % 1000L > 500L) {
         ++nowSec;
      }

      if (this.timedOutTransactions.isEmpty()) {
         synchronized(this) {
            Iterator var5 = this.txMap.values().iterator();

            while(var5.hasNext()) {
               TransactionImpl tx = (TransactionImpl)var5.next();
               if (tx.getWakeupTimeSeconds() <= nowSec) {
                  this.timedOutTransactions.add(tx);
               }
            }
         }
      }

      this.processTimedOutTransactions(this.timedOutTransactions, nowSec);
      this.lastWakeUpDuration = System.currentTimeMillis() - this.lastTimerFire;
   }

   public static TransactionManagerImpl getTM() {
      return getTransactionManager();
   }

   public Object getResource(Object key) {
      if (this != getTransactionManager()) {
         return getTransactionManager().getResource(key);
      } else {
         TransactionImpl tx = this.getTxAssociatedWithThread();
         if (tx == null) {
            throw new IllegalStateException("Transaction does not exist");
         } else if (key == null) {
            throw new NullPointerException("Null key");
         } else {
            return tx.getResource(key);
         }
      }
   }

   public boolean getRollbackOnly() {
      if (this != getTransactionManager()) {
         return getTransactionManager().getRollbackOnly();
      } else {
         TransactionImpl tx = this.getTxAssociatedWithThread();
         if (tx == null) {
            throw new IllegalStateException("Transaction does not exist");
         } else {
            return tx.isMarkedRollback();
         }
      }
   }

   public int getTransactionStatus() {
      if (this != getTransactionManager()) {
         return getTransactionManager().getTransactionStatus();
      } else {
         Transaction tx = this.getTxAssociatedWithThread();

         try {
            return tx == null ? 6 : tx.getStatus();
         } catch (Exception var3) {
            return 6;
         }
      }
   }

   public Object getTransactionKey() {
      if (this != getTransactionManager()) {
         return getTransactionManager().getTransactionKey();
      } else {
         Transaction tx = this.getTxAssociatedWithThread();
         return tx == null ? null : tx;
      }
   }

   public void putResource(Object key, Object value) {
      if (this != getTransactionManager()) {
         getTransactionManager().putResource(key, value);
      }

      TransactionImpl tx = this.getTxAssociatedWithThread();
      if (tx == null) {
         throw new IllegalStateException("Transaction does not exist");
      } else if (key == null) {
         throw new NullPointerException("Null key");
      } else {
         tx.putResource(key, value);
      }
   }

   public void registerInterposedSynchronization(Synchronization sync) {
      if (this != getTransactionManager()) {
         getTransactionManager().registerInterposedSynchronization(sync);
      } else {
         throw new IllegalStateException("registerInterposedSynchronization must be called on Server Transaction");
      }
   }

   public void addToUnRegisteredDeterminerList(String rdName) {
      TxDebug.JTAXA.debug("addToUnRegisteredDeterminerList:" + rdName);
      this.unRegisteredDeterminerList.add(rdName);
      this.isAnyDeterminerUnregistered = true;
   }

   public void removeFromUnRegisteredDeterminerList(String rdName) {
      TxDebug.JTAXA.debug("removeFromUnRegisteredDeterminerList:" + rdName);
      this.unRegisteredDeterminerList.remove(rdName);
      if (this.unRegisteredDeterminerList.isEmpty()) {
         this.isAnyDeterminerUnregistered = false;
      }

   }

   public boolean isInUnRegisteredDeterminerList(String rdName) {
      boolean isIt = this.unRegisteredDeterminerList.contains(rdName);
      String debugString = "isInUnRegisteredDeterminerList:" + rdName + " : " + isIt + " unRegisteredDeterminerList.size()" + this.unRegisteredDeterminerList.size();

      String anUnRegisteredDeterminerList;
      for(Iterator var4 = this.unRegisteredDeterminerList.iterator(); var4.hasNext(); debugString = debugString + anUnRegisteredDeterminerList + " ") {
         anUnRegisteredDeterminerList = (String)var4.next();
      }

      TxDebug.JTAXA.debug(debugString);
      return isIt;
   }

   public boolean isAnyDeterminerUnregistered() {
      return this.isAnyDeterminerUnregistered;
   }

   public static TransactionManagerImpl getTransactionManager() {
      TransactionManagerImpl result = singleton;
      if (result != null) {
         return result;
      } else {
         synchronized(singletonLock) {
            setTransactionManager(new ClientTransactionManagerImpl());
         }

         return singleton;
      }
   }

   static {
      try {
         if (!PlatformHelper.getPlatformHelper().isServer()) {
            Constructor cons = Class.forName("weblogic.transaction.internal.GenericTimer").getConstructor();
            cons.newInstance();
         }
      } catch (Throwable var1) {
         setTimerFailureReason(var1);
      }

      txThreadLocal = AuditableThreadLocalFactory.createThreadLocal(new TxThreadLocal());
      singleton = null;
   }

   public static class TxThreadProperty {
      public TransactionImpl current;
      public static final int UNSPECIFIED_TIMEOUTSEC = 0;
      public int timeoutSec;
      public boolean isUserTransactionAllowed = true;
      public boolean preferredHostSetForTx;
      public Object preTxPreferredHost;
   }

   public class TMXAResource extends XAResourceHelper implements TMXAResourceInterface {
      private static final long serialVersionUID = -8889585500926470087L;
      protected ConcurrentHashMap foreignXidMap = null;
      private int timeOutSecs;

      public TMXAResource() {
         this.timeOutSecs = TransactionManagerImpl.this.defaultTimeoutSec;
      }

      public void start(Xid foreignXid, int flags) throws XAException {
         Xid fXid = XidImpl.create(foreignXid);
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.start(foreignXid=" + fXid + ", flags=" + flagsToString(flags) + ")");
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.start(foreignXid=" + fXid + ", flags=" + flagsToString(flags) + ")");
         }

         TransactionImpl importedTx = null;

         try {
            try {
               importedTx = this.getOrCreate(fXid);
            } catch (SystemException var9) {
               throwXAException(-3, this.getErrMsg("start") + "Cannot create imported transaction, foreignXid=" + fXid + ".", var9);
            }

            if (importedTx.isCancelled()) {
               Throwable reason = importedTx.getRollbackReason();
               if (reason instanceof TimedOutException) {
                  throwXAException(106, this.getErrMsg("start"), reason);
               } else {
                  throwXAException(100, this.getErrMsg("start"), reason);
               }
            } else if (!importedTx.isStateActive()) {
               throwXAException(-3, this.getErrMsg("start") + "Illegal State (Expected: active).  " + importedTx.getXid().toString());
            }

            try {
               if (TransactionManagerImpl.this.isClusterwideRecoveryEnabled()) {
                  TransactionManagerImpl.this.forceResume(importedTx);
               } else {
                  TransactionManagerImpl.this.resume(importedTx);
               }
            } catch (InvalidTransactionException var6) {
               throwXAException(-3, this.getErrMsg("start") + "Cannot resume transaction " + importedTx.getXid().toString(), var6);
            } catch (IllegalStateException var7) {
               throwXAException(-3, this.getErrMsg("start") + "Cannot resume transaction " + importedTx.getXid().toString(), var7);
            } catch (SystemException var8) {
               throwXAException(-3, this.getErrMsg("start") + "Cannot resume transaction " + importedTx.getXid().toString(), var8);
            }

            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.start DONE, imported tx: " + importedTx);
            }

         } catch (XAException var10) {
            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug((String)(this.curTM() + ".XAResource.start FAILED, imported tx: " + importedTx), (Throwable)var10);
            }

            throw var10;
         }
      }

      public void end(Xid foreignXid, int flags) throws XAException {
         Xid fXid = XidImpl.create(foreignXid);
         boolean needTxInMap = false;
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.end(foreignXid=" + fXid + ", flags=" + flagsToString(flags) + ")");
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.end(foreignXid=" + fXid + ", flags=" + flagsToString(flags) + ")");
         }

         TransactionImpl importedTx = null;
         int txsts = 0;

         try {
            importedTx = this.get(fXid);
            Transaction tx;
            if (importedTx == null) {
               tx = TransactionManagerImpl.this.getTransaction();
               if (tx != null) {
                  try {
                     txsts = tx.getStatus();
                  } catch (SystemException var11) {
                     var11.printStackTrace();
                  }

                  if (txsts == 4 || txsts == 9 || txsts == 1) {
                     importedTx = (TransactionImpl)tx;
                     needTxInMap = true;
                  }
               }

               if (importedTx == null) {
                  throwXAException(-4, this.getErrMsg("end") + "Foreign transaction not recognized, foreignXid=" + fXid + ".");
               }
            }

            tx = TransactionManagerImpl.this.getTransaction();
            if (tx != null) {
               if (!tx.equals(importedTx)) {
                  throwXAException(-6, this.getErrMsg("end") + "Thread is currently associated with a different transaction.  Current transaction: " + tx + ".  Foreign transaction: " + importedTx.getXid().toString());
               }

               TransactionImpl mappedTx = (TransactionImpl)tx;
               if (needTxInMap) {
                  TransactionManagerImpl.this.txMap.put(mappedTx.getXID(), mappedTx);
                  mappedTx.setProperty("IMP_TX_STATUS_ROLLEDBACK", "true");
               }

               try {
                  TransactionManagerImpl.this.suspend();
               } catch (SystemException var10) {
                  var10.printStackTrace();
               }

               if (needTxInMap) {
                  TransactionManagerImpl.this.txMap.remove(mappedTx.getXID());
               }

               if (importedTx.isCancelled()) {
                  Throwable reason = importedTx.getRollbackReason();
                  throwXAException(reason instanceof TimedOutException ? 106 : 100, this.getErrMsg("end") + "Transaction is cancelled " + importedTx.getXid().toString(), reason);
               } else if (!importedTx.isStateActive()) {
                  throwXAException(-3, this.getErrMsg("start") + "Illegal State (Expected: active).  " + importedTx.getXid().toString());
               }
            }

            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.end DONE, imported tx: " + importedTx);
            }

         } catch (XAException var12) {
            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug((String)(this.curTM() + ".XAResource.end FAILED, imported tx: " + importedTx), (Throwable)var12);
            }

            throw var12;
         }
      }

      public int prepare(Xid foreignXid) throws XAException {
         return this.prepare(foreignXid, false);
      }

      public int prepare(Xid foreignXid, boolean isClientTMCaller) throws XAException {
         Xid fXid = XidImpl.create(foreignXid);
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.prepare(foreignXid=" + fXid + ")");
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.prepare(foreignXid=" + fXid + ")");
         }

         TransactionImpl importedTx = null;
         TransactionImpl saveTx = null;

         int vote;
         try {
            saveTx = TransactionManagerImpl.getTM().internalSuspend();
            importedTx = this.get(fXid);
            if (importedTx == null) {
               throwXAException(-4, "Foreign transaction not recognized, foreignXid=" + fXid + ".");
            }

            Throwable reason;
            try {
               vote = importedTx.internalPrepare();
               if (vote == 3) {
                  importedTx.isReadOnly = true;
                  if (TxDebug.JTAGateway.isDebugEnabled()) {
                     TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.prepare is readonly, issuing internal commit cleanup, imported tx: " + importedTx);
                  }

                  try {
                     if (!isClientTMCaller) {
                        this.commit(foreignXid, false);
                     }
                  } catch (Exception var29) {
                     System.out.println("TransactionManagerImpl$TMXAResource.prepare during cleanup commit of readonly e:" + var29);
                     var29.printStackTrace();
                     throw new XAException("Exception during cleanup commit of readonly transaction:" + var29);
                  }

                  if (TxDebug.JTAGateway.isDebugEnabled()) {
                     TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.prepare DONE, imported tx: " + importedTx);
                  }
               }

               int var41 = vote;
               return var41;
            } catch (AbortRequestedException var30) {
               try {
                  importedTx.globalRollback();
               } catch (Exception var28) {
               }

               reason = importedTx.getRollbackReason();
               if (reason instanceof TimedOutException) {
                  throwXAException(106, this.getErrMsg("prepare"), reason);
               } else {
                  throwXAException(100, this.getErrMsg("prepare"), reason);
               }
            } catch (RollbackException var31) {
               reason = importedTx.getRollbackReason();
               if (reason == null && var31 instanceof weblogic.transaction.RollbackException) {
                  reason = ((weblogic.transaction.RollbackException)var31).getNested();
                  importedTx.setRollbackReason(reason);
               }

               if (reason instanceof TimedOutException) {
                  throwXAException(106, this.getErrMsg("prepare"), var31);
               } else {
                  throwXAException(100, this.getErrMsg("prepare"), var31);
               }
            } catch (SystemException var32) {
               try {
                  importedTx.globalRollback();
               } catch (Exception var27) {
               }

               throwXAException(-3, this.getErrMsg("prepare"), var32);
            } catch (IllegalStateException var33) {
               try {
                  importedTx.globalRollback();
               } catch (Exception var26) {
               }

               throwXAException(-3, this.getErrMsg("prepare"), var33);
            } catch (SecurityException var34) {
               try {
                  importedTx.globalRollback();
               } catch (Exception var25) {
               }

               throwXAException(-3, this.getErrMsg("prepare"), var34);
            } catch (XAException var35) {
               throw var35;
            } catch (OutOfMemoryError var36) {
               throw var36;
            } catch (ThreadDeath var37) {
               throw var37;
            } catch (Throwable var38) {
               throwXAException(-3, this.getErrMsg("prepare"), var38);
            }

            vote = 0;
         } catch (XAException var39) {
            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug((String)(this.curTM() + ".XAResource.prepare FAILED, imported tx: " + importedTx), (Throwable)var39);
            }

            throw var39;
         } finally {
            TransactionManagerImpl.getTM().internalResume(saveTx);
         }

         return vote;
      }

      public void commit(Xid foreignXid, boolean onePhase) throws XAException {
         this.commit(foreignXid, onePhase, false);
      }

      public void commit(Xid foreignXid, boolean onePhase, boolean isClientTMCaller) throws XAException {
         XidImpl fXid = XidImpl.create(foreignXid);
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.commit(foreignXid=" + fXid + ", onePhase=" + onePhase + ")");
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.commit(foreignXid=" + fXid + ", onePhase=" + onePhase + ")");
         }

         TransactionImpl importedTx = null;
         TransactionImpl saveTx = null;

         try {
            saveTx = TransactionManagerImpl.getTM().internalSuspend();
            importedTx = this.get(fXid);
            if (importedTx == null) {
               if (onePhase) {
                  throwXAException(-4, "Foreign transaction not recognized, foreignXid=" + fXid + ".");
               } else {
                  try {
                     this.curTM().commit(fXid);
                  } catch (XAException var30) {
                     this.executeCommandAgainstClusterIfEnabled(onePhase, isClientTMCaller, fXid, var30, "CLUSTERCOMMIT");
                  }
               }
            } else {
               if (importedTx.hasHeuristics()) {
                  throwXAException(importedTx.getHeuristicErrorCode(), this.getErrMsg("commit") + importedTx.getHeuristicErrorMessage());
               }

               int errCodex;
               int resErrCode;
               Throwable reason;
               try {
                  importedTx.internalCommit(onePhase);
               } catch (AbortRequestedException var31) {
                  try {
                     importedTx.globalRollback();
                  } catch (Exception var29) {
                  }

                  reason = importedTx.getRollbackReason();
                  errCodex = 100;
                  if (onePhase) {
                     if (reason instanceof TimedOutException) {
                        errCodex = 106;
                     } else if (reason instanceof XAException) {
                        resErrCode = ((XAException)reason).errorCode;
                        if (isRollbackErrorCode(resErrCode)) {
                           errCodex = resErrCode;
                        }
                     }
                  }

                  throwXAException(errCodex, this.getErrMsg("commit"), reason);
               } catch (RollbackException var32) {
                  try {
                     importedTx.globalRollback();
                  } catch (Exception var28) {
                  }

                  reason = importedTx.getRollbackReason();
                  if (reason == null && var32 instanceof weblogic.transaction.RollbackException) {
                     reason = ((weblogic.transaction.RollbackException)var32).getNested();
                     importedTx.setRollbackReason(reason);
                  }

                  errCodex = 6;
                  if (onePhase) {
                     errCodex = 100;
                     if (reason instanceof TimedOutException) {
                        errCodex = 106;
                     } else if (reason instanceof XAException) {
                        resErrCode = ((XAException)reason).errorCode;
                        if (isRollbackErrorCode(resErrCode)) {
                           errCodex = resErrCode;
                        }
                     }
                  }

                  throwXAException(errCodex, this.getErrMsg("commit"), var32);
               } catch (HeuristicMixedException var33) {
                  throwXAException(5, this.getErrMsg("commit"), var33);
               } catch (HeuristicRollbackException var34) {
                  int errCode = onePhase ? 100 : 6;
                  throwXAException(errCode, this.getErrMsg("commit"), var34);
               } catch (SystemException var35) {
                  throwXAException(-3, this.getErrMsg("commit"), var35);
               } catch (IllegalStateException var36) {
                  throwXAException(-3, this.getErrMsg("commit"), var36);
               } catch (SecurityException var37) {
                  throwXAException(-3, this.getErrMsg("commit"), var37);
               } catch (XAException var38) {
                  throw var38;
               } catch (OutOfMemoryError var39) {
                  throw var39;
               } catch (ThreadDeath var40) {
                  throw var40;
               } catch (Throwable var41) {
                  throwXAException(-3, this.getErrMsg("commit"), var41);
               }
            }

            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.commit DONE, imported tx: " + importedTx);
            }
         } catch (XAException var42) {
            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug((String)(this.curTM() + ".XAResource.commit FAILED, imported tx: " + importedTx), (Throwable)var42);
            }

            throw var42;
         } finally {
            TransactionManagerImpl.getTM().internalResume(saveTx);
         }

      }

      public void rollback(Xid foreignXid) throws XAException {
         this.rollback(foreignXid, false);
      }

      public void rollback(Xid foreignXid, boolean isClientTMCaller) throws XAException {
         XidImpl fXid = XidImpl.create(foreignXid);
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.rollback(foreignXid=" + fXid + ")");
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.rollback(foreignXid=" + fXid + ")");
         }

         TransactionImpl importedTx = null;
         TransactionImpl saveTx = null;

         try {
            saveTx = TransactionManagerImpl.getTM().internalSuspend();
            importedTx = this.get(fXid);
            if (importedTx == null) {
               try {
                  this.curTM().rollback(fXid);
               } catch (XAException var16) {
                  this.executeCommandAgainstClusterIfEnabled(false, isClientTMCaller, fXid, var16, "CLUSTERROLLBACK");
               }
            } else {
               if (importedTx.hasHeuristics()) {
                  throwXAException(importedTx.getHeuristicErrorCode(), this.getErrMsg("rollback") + importedTx.getHeuristicErrorMessage());
               }

               try {
                  importedTx.internalRollback();
               } catch (SystemException var17) {
                  int errCode = -3;
                  if (importedTx.hasHeuristics()) {
                     errCode = importedTx.getHeuristicErrorCode();
                  }

                  throwXAException(errCode, this.getErrMsg("rollback"), var17);
               } catch (OutOfMemoryError var18) {
                  throw var18;
               } catch (ThreadDeath var19) {
                  throw var19;
               } catch (Throwable var20) {
                  throwXAException(-3, this.getErrMsg("rollback"), var20);
               }
            }

            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.rollback DONE, imported tx: " + importedTx);
            }
         } catch (XAException var21) {
            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug((String)(this.curTM() + ".XAResource.rollback FAILED, imported tx: " + importedTx), (Throwable)var21);
            }

            throw var21;
         } finally {
            TransactionManagerImpl.getTM().internalResume(saveTx);
         }

      }

      public Xid[] recover(int recoveryFlag) throws XAException {
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.recover(flag=" + flagsToString(recoveryFlag) + ")");
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.recover(flag=" + flagsToString(recoveryFlag) + ")");
         }

         Xid[] xids = null;

         try {
            if (recoveryFlag != 1001001 && recoveryFlag != 1001002 && recoveryFlag != 1001003 && (recoveryFlag & 16777216) != 16777216) {
               if (TxDebug.JTAGateway.isDebugEnabled()) {
                  TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.recover returns 0 xids.  TMSTARTRSCAN is not specified.");
               }

               xids = new Xid[0];
            } else {
               xids = this.curTM().recoverForeignXids(recoveryFlag);
            }
         } finally {
            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.recover returns " + (xids == null ? 0 : xids.length) + " xids.");
            }

         }

         return xids;
      }

      public void forget(Xid foreignXid) throws XAException {
         this.forget(foreignXid, false);
      }

      public void forget(Xid foreignXid, boolean isClientTMCaller) throws XAException {
         XidImpl fXid = XidImpl.create(foreignXid);
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.forget(foreignXid=" + fXid + ")");
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.forget(foreignXid=" + fXid + ")");
         }

         TransactionImpl importedTx = null;

         try {
            importedTx = this.get(fXid);
            if (importedTx == null) {
               try {
                  this.curTM().forget(fXid);
               } catch (XAException var11) {
                  this.executeCommandAgainstClusterIfEnabled(false, isClientTMCaller, fXid, var11, "CLUSTERFORGET");
               }
            } else {
               try {
                  importedTx.internalForget();
               } catch (SystemException var6) {
                  throwXAException(-3, this.getErrMsg("forget"), var6);
               } catch (XAException var7) {
                  throw var7;
               } catch (OutOfMemoryError var8) {
                  throw var8;
               } catch (ThreadDeath var9) {
                  throw var9;
               } catch (Throwable var10) {
                  throwXAException(-3, this.getErrMsg("forget"), var10);
               }
            }

            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.forget DONE, imported tx: " + importedTx);
            }

         } catch (XAException var12) {
            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug((String)(this.curTM() + ".XAResource.forget FAILED, imported tx: " + importedTx), (Throwable)var12);
            }

            throw var12;
         }
      }

      private void executeCommandAgainstClusterIfEnabled(boolean onePhase, boolean isClientTMCaller, XidImpl fXid, XAException xaException, String command) throws XAException {
         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + "XAException during " + command + " xaException:" + xaException + " fXid.isClusterCall():" + fXid.isClusterCall() + " fXid.isClusterwideRecoveryEnabled():" + fXid.isClusterwideRecoveryEnabled() + " isClusterwideRecoveryEnabled():" + TransactionManagerImpl.this.isClusterwideRecoveryEnabled());
         }

         if (isClientTMCaller || xaException.errorCode != -4 || fXid.isClusterCall() || fXid.isClusterwideRecoveryEnabled() == 0 || !TransactionManagerImpl.this.isClusterwideRecoveryEnabled() && fXid.isClusterwideRecoveryEnabled() != 1) {
            throw xaException;
         } else {
            if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
               TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + "commitAgainstClusterIfEnabled foreignXid=" + fXid + (command.equals("CLUSTERCOMMIT") ? ", onePhase=" + onePhase : "") + ") resulted in XAER_NOTA XAException.  Attempting to " + command + " against cluster...");
            }

            fXid.setClusterCall(true);
            this.curTM().gatherClusterRecoverXids(command, fXid);
         }
      }

      public boolean isSameRM(XAResource xares) throws XAException {
         if (this.curTM() != TransactionManagerImpl.getTM()) {
            return TransactionManagerImpl.getTM().getXAResource().isSameRM(xares);
         } else {
            boolean rtn = xares == this;
            if (TxDebug.JTAGateway.isDebugEnabled()) {
               TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.isSameRM(xares=" + xares + ") returns " + rtn);
            }

            if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
               TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.isSameRM(xares=" + xares + ") returns " + rtn);
            }

            return rtn;
         }
      }

      public boolean setTransactionTimeout(int seconds) throws XAException {
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.setTransactionTimeout(seconds=" + seconds + ")");
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.setTransactionTimeout(seconds=" + seconds + ")");
         }

         this.timeOutSecs = seconds <= 0 ? this.curTM().getDefaultTimeoutSeconds() : seconds;
         return true;
      }

      public int getTransactionTimeout() throws XAException {
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.JTAGateway.debug(this.curTM() + ".XAResource.getTransactionTimeout() returns " + this.timeOutSecs);
         }

         if (TxDebug.JTAGatewayDetail.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAGatewayDetail, this.curTM() + ".XAResource.getTransactionTimeout() returns " + this.timeOutSecs);
         }

         return this.timeOutSecs;
      }

      public String toString() {
         return this.curTM() + ".XAResource";
      }

      public boolean equals(Object obj) {
         try {
            return obj instanceof TMXAResource && this.isSameRM((TMXAResource)obj);
         } catch (XAException var3) {
            return false;
         }
      }

      public int hashCode() {
         return this.curTM().toString().hashCode();
      }

      private TransactionManagerImpl curTM() {
         return TransactionManagerImpl.this;
      }

      protected String getErrMsg(String xaOp) {
         return this.curTM() + ".XAResource." + xaOp + "() fails.  ";
      }

      protected void init() {
         this.foreignXidMap = new ConcurrentHashMap(50);
      }

      public void add(Xid foreignXid, TransactionImpl tx) {
         if (this.curTM() != TransactionManagerImpl.getTM()) {
            TransactionManagerImpl.this.getTMXAResource().add(foreignXid, tx);
         } else {
            this.foreignXidMap.put(foreignXid, tx);
         }
      }

      public void remove(Xid foreignXid) {
         if (this.curTM() != TransactionManagerImpl.getTM()) {
            TransactionManagerImpl.this.getTMXAResource().remove(foreignXid);
         } else {
            this.foreignXidMap.remove(foreignXid);
         }
      }

      public TransactionImpl get(Xid foreignXid) {
         if (this.curTM() != TransactionManagerImpl.getTM()) {
            return TransactionManagerImpl.this.getTMXAResource().get(foreignXid);
         } else {
            synchronized(TransactionManagerImpl.getTM()) {
               return (TransactionImpl)this.foreignXidMap.get(foreignXid);
            }
         }
      }

      public TransactionImpl getWithoutBranchqualEquals(Xid foreignXid) {
         if (this.curTM() != TransactionManagerImpl.getTM()) {
            return TransactionManagerImpl.this.getTMXAResource().get(foreignXid);
         } else {
            synchronized(TransactionManagerImpl.getTM()) {
               Set entries = this.foreignXidMap.entrySet();
               Iterator var4 = entries.iterator();

               Map.Entry next;
               Xid xid;
               do {
                  if (!var4.hasNext()) {
                     return (TransactionImpl)this.foreignXidMap.get(foreignXid);
                  }

                  next = (Map.Entry)var4.next();
                  xid = (Xid)next.getKey();
               } while(xid.getFormatId() != foreignXid.getFormatId() || !Arrays.equals(xid.getGlobalTransactionId(), foreignXid.getGlobalTransactionId()));

               return (TransactionImpl)next.getValue();
            }
         }
      }

      protected TransactionImpl getOrCreate(Xid foreignXid) throws SystemException, XAException {
         synchronized(TransactionManagerImpl.getTM()) {
            TransactionImpl tx = TransactionManagerImpl.this.getTMXAResource().get(foreignXid);
            if (tx == null) {
               tx = TransactionManagerImpl.this.createImportedTransaction(XidImpl.create(), foreignXid, this.getTransactionTimeout(), this.getTransactionTimeout());
            }

            return tx;
         }
      }

      public Xid[] getIndoubtXids() {
         if (this.curTM() != TransactionManagerImpl.getTM()) {
            return TransactionManagerImpl.this.getTMXAResource().getIndoubtXids();
         } else {
            ArrayList list = null;
            synchronized(TransactionManagerImpl.getTM()) {
               Collection values = this.foreignXidMap.values();
               if (values != null) {
                  Iterator var4 = values.iterator();

                  label40:
                  while(true) {
                     TransactionImpl tx;
                     do {
                        if (!var4.hasNext()) {
                           break label40;
                        }

                        tx = (TransactionImpl)var4.next();
                     } while(!tx.isPrepared() && !tx.hasHeuristics());

                     if (list == null) {
                        list = new ArrayList(5);
                     }

                     list.add(tx.getForeignXid());
                  }
               }
            }

            if (list != null) {
               Xid[] xids = new Xid[list.size()];
               list.toArray(xids);
               return xids;
            } else {
               return null;
            }
         }
      }
   }

   static class TxThreadLocal extends ThreadLocalInitialValue {
      protected final Object initialValue() {
         TxThreadProperty txp = new TxThreadProperty();
         txp.timeoutSec = 0;
         return txp;
      }
   }
}
