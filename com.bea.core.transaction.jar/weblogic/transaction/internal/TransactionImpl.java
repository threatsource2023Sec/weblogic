package weblogic.transaction.internal;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.transaction.ChannelInterface;
import weblogic.transaction.NestedException;
import weblogic.transaction.PeerExchangeTransaction;
import weblogic.transaction.Transaction;
import weblogic.transaction.nonxa.NonXAResource;

public abstract class TransactionImpl implements Transaction, PeerExchangeTransaction {
   protected boolean useSecureURL;
   public boolean isReadOnly;
   private XidImpl xid;
   private volatile byte state;
   protected static final byte STATE_ACTIVE = 1;
   protected static final byte STATE_PRE_PREPARING = 2;
   protected static final byte STATE_PRE_PREPARED = 3;
   protected static final byte STATE_PREPARING = 4;
   protected static final byte STATE_LOGGING = 5;
   protected static final byte STATE_PREPARED = 6;
   protected static final byte STATE_COMMITTING = 7;
   protected static final byte STATE_COMMITTED = 8;
   protected static final byte STATE_ROLLING_BACK = 9;
   protected static final byte STATE_ROLLED_BACK = 10;
   protected static final byte STATE_UNKNOWN = 11;
   protected static final byte STATE_ABANDONED = 12;
   private static final boolean IS_ISE_THROWN_DUE_TO_TIMEOUT_RB = Boolean.parseBoolean(System.getProperty("weblogic.transaction.ise.timeout.rb", "false"));
   private volatile boolean markedRollback;
   private short completionState;
   private Throwable rollbackReason;
   private boolean requestOverrideRollbackReason;
   private static final boolean ALLOW_OVERRIDE_SETROLLBACK_REASON;
   private String heuristicError;
   protected int heuristicErrorCode;
   private long beginTimeMillis;
   private int timeoutSec;
   private volatile boolean txAsyncTimeout;
   private static int abandonGraceTimeEndSec;
   private CoordinatorDescriptor coordinatorDescriptor;
   private HashMap globalProperties;
   private ConcurrentHashMap localProperties;
   private volatile ConcurrentHashMap volatileLocalProperties;
   private Thread activeThread;
   private TransactionManagerImpl ownerTM;
   private int wakeUpTimeSec;
   private volatile CopyOnWriteArrayList scInfoList;
   private ArrayList resourceInfoList;
   private int numRepliesOwedMe;
   private int numRepliesOwedOthers;
   private Object preferredHost;
   private ResourceInfo nonXAResource;
   private boolean delayRemoveAfterRollback;
   boolean onePhase;
   boolean isLLR;
   boolean isOnlyOneResourceInTx;
   boolean isNoResourceInTx;
   boolean isAllEmulated;
   private HashMap resources;
   static final boolean islookupresourceonretryEnabled;
   static final int lookupresourceonretryCount;
   int rollbackRetryCount;
   int commitRetryCount;
   boolean useLLR;
   private boolean localConnCommittedOrRollback;
   Deque debugMessages;
   volatile boolean printDebugMessagesOnCompletion;
   String ackCommitDebugMessages;
   Map ackOrNakCommitMap;
   private static final boolean INSTR_ENABLED;
   private static final Map GLOBAL_PROPERTIES;
   private static final Map LOCAL_PROPERTIES;

   protected TransactionImpl() {
      this.useSecureURL = false;
      this.isReadOnly = false;
      this.state = 1;
      this.requestOverrideRollbackReason = false;
      this.heuristicErrorCode = -1;
      this.preferredHost = null;
      this.delayRemoveAfterRollback = false;
      this.useLLR = false;
      this.localConnCommittedOrRollback = false;
      this.debugMessages = new ConcurrentLinkedDeque();
      this.printDebugMessagesOnCompletion = false;
      this.ackOrNakCommitMap = new ConcurrentHashMap();
   }

   protected TransactionImpl(Xid axid, int aTimeoutSec, int aTimeToLiveSec) {
      this(axid, aTimeoutSec, aTimeToLiveSec, false);
   }

   protected TransactionImpl(Xid axid, int aTimeoutSec, int aTimeToLiveSec, boolean isFromPropagationContext) {
      this.useSecureURL = false;
      this.isReadOnly = false;
      this.state = 1;
      this.requestOverrideRollbackReason = false;
      this.heuristicErrorCode = -1;
      this.preferredHost = null;
      this.delayRemoveAfterRollback = false;
      this.useLLR = false;
      this.localConnCommittedOrRollback = false;
      this.debugMessages = new ConcurrentLinkedDeque();
      this.printDebugMessagesOnCompletion = false;
      this.ackOrNakCommitMap = new ConcurrentHashMap();
      this.init(axid, aTimeoutSec, aTimeToLiveSec, isFromPropagationContext);
      this.getTM().add(this);
   }

   protected TransactionImpl(Xid xid, Xid foreignXid, int timeOutSecs, int timeToLiveSecs) {
      this.useSecureURL = false;
      this.isReadOnly = false;
      this.state = 1;
      this.requestOverrideRollbackReason = false;
      this.heuristicErrorCode = -1;
      this.preferredHost = null;
      this.delayRemoveAfterRollback = false;
      this.useLLR = false;
      this.localConnCommittedOrRollback = false;
      this.debugMessages = new ConcurrentLinkedDeque();
      this.printDebugMessagesOnCompletion = false;
      this.ackOrNakCommitMap = new ConcurrentHashMap();
      this.init(xid, timeOutSecs, timeToLiveSecs);
      this.setForeignXid(foreignXid);
      this.getTM().add(this);
   }

   protected void init(Xid axid, int aTimeoutSec, int aTimeToLiveSec) {
      this.init(axid, aTimeoutSec, aTimeToLiveSec, false);
   }

   protected void init(Xid axid, int aTimeoutSec, int aTimeToLiveSec, boolean isFromPropagationContext) {
      String partitionName = PlatformHelper.getPlatformHelper().getPartitionName();
      if (partitionName == null) {
         this.setProperty("weblogic.transaction.partitionName", "DOMAIN");
      } else {
         this.setProperty("weblogic.transaction.partitionName", partitionName);
         this.setProperty("weblogic.transaction.initiator", this.getTM().getServerName());
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "init(t/o=" + aTimeoutSec + ",ttl=" + aTimeToLiveSec + ") GLOBAL_PROPERTIES.isEmpty()=" + GLOBAL_PROPERTIES.isEmpty());
      }

      this.xid = (XidImpl)axid;
      this.timeoutSec = aTimeoutSec;
      this.txAsyncTimeout = false;
      if (!isFromPropagationContext) {
         this.setPropertiesFromMap(aTimeoutSec, aTimeToLiveSec, GLOBAL_PROPERTIES, "GLOBAL_PROPERTIES", true);
      }

      if (!isFromPropagationContext) {
         this.setPropertiesFromMap(aTimeoutSec, aTimeToLiveSec, LOCAL_PROPERTIES, "LOCAL_PROPERTIES", false);
      }

      long sofar = (long)((aTimeoutSec - aTimeToLiveSec) * 1000);
      this.setBeginTimeMillis(System.currentTimeMillis() - sofar);
      this.wakeUpAfterSeconds(aTimeToLiveSec);
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "init(t/o=" + aTimeoutSec + ",ttl=" + aTimeToLiveSec + ")", TxDebug.JTA2PCStackTrace.isDebugEnabled() ? new Exception("DEBUG") : null);
      }

   }

   private void setPropertiesFromMap(int aTimeoutSec, int aTimeToLiveSec, Map map, String mapName, boolean isGlobal) {
      if (!map.isEmpty()) {
         Iterator var6 = map.keySet().iterator();

         while(true) {
            while(var6.hasNext()) {
               String next = (String)var6.next();
               String value = (String)map.get(next);
               next = next.replace("\"", "");
               value = value.replace("\"", "");
               if (!value.trim().equalsIgnoreCase("true") && !value.trim().equalsIgnoreCase("false")) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "init(t/o=" + aTimeoutSec + ",ttl=" + aTimeToLiveSec + ") " + mapName + " init next:" + next + " value:" + value);
                  }

                  if (isGlobal) {
                     this.setProperty(next, value);
                  } else {
                     this.setLocalProperty(next, value);
                  }
               } else {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "init(t/o=" + aTimeoutSec + ",ttl=" + aTimeToLiveSec + ") " + mapName + " init boolean next:" + next + " value:" + value);
                  }

                  if (isGlobal) {
                     this.setProperty(next, Boolean.valueOf(value));
                  } else {
                     this.setLocalProperty(next, Boolean.valueOf(value));
                  }
               }
            }

            return;
         }
      }
   }

   public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
      try {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "commit");
         }

         if (this.isImportedTransaction()) {
            throw new SystemException("Cannot call commit on imported transaction directly.  Imported transactions should only be committed via XAResource.commit.  " + this.getXid().toString());
         }

         this.checkIfCommitPossible();
         if (this.getCoordinatorDescriptor() == null) {
            this.setCommitted();
         } else {
            final Coordinator co = (Coordinator)this.getCoordinator();
            if (co == null) {
               throw new SystemException("Could not contact coordinator at " + this.getCoordinatorURL());
            }

            if (this.getState() == 1) {
               this.setCommitting();
            }

            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "co.commit");
            }

            if (PlatformHelper.getPlatformHelper().isServer()) {
               PlatformHelper.getPlatformHelper().runAction(new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     co.commit(TransactionImpl.this.getRequestPropagationContext());
                     return null;
                  }
               }, this.getCoServerURL(), "co.commit");
            } else {
               co.commit(this.getRequestPropagationContext());
            }

            this.setCommitted();
         }
      } catch (weblogic.transaction.RollbackException var21) {
         try {
            this.globalRollback();
         } catch (Exception var19) {
         }

         this.setRolledBack();
         Throwable rbReason = var21.getNested();
         if (rbReason != null) {
            this.setRollbackReason(rbReason);
         }

         throw var21;
      } catch (RollbackException var22) {
         try {
            this.globalRollback();
         } catch (Exception var18) {
         }

         this.setRolledBack();
         throw var22;
      } catch (HeuristicRollbackException var23) {
         this.setCommitted();
         this.addHeuristicErrorMessage(var23.getMessage());
         throw var23;
      } catch (HeuristicMixedException var24) {
         this.setCommitted();
         this.addHeuristicErrorMessage(var24.getMessage());
         throw var24;
      } catch (SecurityException var25) {
         this.setState((byte)1);
         throw var25;
      } catch (IllegalStateException var26) {
         throw var26;
      } catch (SystemException var27) {
         if (!PlatformHelper.getPlatformHelper().isServer()) {
            this.setUnknown();
         }

         throw var27;
      } catch (RemoteException var28) {
         if (PlatformHelper.getPlatformHelper().isServer()) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "Lost connection to server while delegated commit was in progress.  Ignoring because initiating server is not the coordinating server.  Remote Exception received=" + var28.toString());
            }

            throw new SystemException("Lost connection to server while commit was in progress, ignoring because initiating server is not coordinating server. Remote Exception received=" + var28.toString());
         }

         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "Lost connection to server while delegated commit was in progress.  Setting transaction state to unkown.  Remote Exception received=" + var28.toString());
         }

         this.setUnknown();
         throw new SystemException("Lost connection to server while commit was in progress, so the local transaction is now in an unknown state. Remote Exception received=" + var28.toString());
      } catch (AbortRequestedException var29) {
         try {
            this.globalRollback();
         } catch (Exception var20) {
         }

         this.throwRollbackException();
      } catch (Exception var30) {
         if (PlatformHelper.getPlatformHelper().isServer()) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "Unexpected exception while delegated commit was in progress.  Ignoring exception because initiating server is not the coordinating server.  Remote Exception received=" + var30.toString());
            }

            throw new SystemException("Unexpected exception while commit was in progress, ignoring because initiating server is not coordinating server. Remote Exception received=" + var30.toString());
         }

         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "Unexpected exception while delegated commit was in progress.  Setting transaction state to unkown.  Remote Exception received=" + var30.toString());
         }

         this.setUnknown();
         throw new SystemException("Unexpected exception while commit was in progress, so the local transaction is now in an unknown state. Remote Exception received=" + var30.toString());
      } finally {
         this.getTM().suspend(this);
      }

   }

   void checkIfDeterminerIsUnregisteredFromThisServer() throws RollbackException {
      if (this.getTM().isAnyDeterminerUnregistered()) {
         ArrayList resList = this.getResourceInfoList();
         if (resList != null) {
            Iterator var2 = resList.iterator();

            while(var2.hasNext()) {
               Object aResList = var2.next();
               ResourceInfo sri = (ResourceInfo)aResList;
               String name = sri.getName();
               boolean isMatch = name != null && this.getTM().isInUnRegisteredDeterminerList(name);
               if (isMatch) {
                  throw new RollbackException("Determiner resource " + name + " was undeployed from this server and must be redeployed back to this server or the cluster in order to participate in transactions involving this server. Transaction : " + this);
               }
            }
         }
      }

   }

   public void rollback() throws IllegalStateException, SystemException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "ClientTransactionImpl.rollback()");
      }

      if (this.isImportedTransaction()) {
         throw new SystemException("Cannot call rollback on imported transaction directly.  Imported transactions should only be rolled back via XAResource.rollback.  " + this.getXid().toString());
      } else {
         this.internalRollback();
      }
   }

   public void internalRollback() throws IllegalStateException, SystemException {
      try {
         switch (this.getState()) {
            case 2:
               if (this.isImportedTransaction()) {
                  break;
               }
            case 3:
            case 8:
               this.throwIllegalStateException("Transaction cannot be rolled back anymore, status=" + this.getStatusAsString());
               break;
            case 4:
            case 9:
               return;
            case 5:
            case 6:
            case 7:
         }

         if (this.getCoordinatorDescriptor() != null) {
            try {
               this.globalRollback();
               return;
            } catch (IllegalStateException var7) {
               throw var7;
            } catch (SystemException var8) {
               throw var8;
            } catch (Exception var9) {
               throw new SystemException(var9.toString());
            }
         }

         this.setRolledBack();
      } finally {
         this.getTM().suspend(this);
      }

   }

   public boolean enlistResource(XAResource xar) throws RollbackException, IllegalStateException, SystemException {
      throw new SystemException("You may enlist a resource only on a server");
   }

   public boolean enlistResource(XAResource xaRes, String resourceNameAlias) throws RollbackException, IllegalStateException, SystemException {
      throw new SystemException("You may enlist a resource only on a server");
   }

   public boolean enlistResourceWithProperties(XAResource xaRes, Map properties) throws RollbackException, IllegalStateException, SystemException {
      throw new SystemException("You may enlist a resource only on a server");
   }

   public boolean delistResource(XAResource xar, int flag) throws IllegalStateException, SystemException {
      throw new SystemException("You may enlist a resource only on a server");
   }

   public boolean delistResourceWithProperties(XAResource xaRes, int flags, Map delistmentProperties) throws IllegalStateException, SystemException {
      throw new SystemException("You may enlist a resource only on a server");
   }

   public boolean enlistResource(NonXAResource nxar) throws RollbackException, IllegalStateException, SystemException {
      throw new SystemException("You may enlist a resource only on a server");
   }

   public int getStatus() {
      if (this.isMarkedRollback()) {
         return 1;
      } else {
         short st = (short)this.getState();
         switch (st) {
            case 1:
               return 0;
            case 2:
            case 3:
            case 5:
            default:
               TXLogger.logUnknownGetStatusState(st);
               return 5;
            case 4:
               return 7;
            case 6:
               return 2;
            case 7:
               return 8;
            case 8:
               return 3;
            case 9:
               return 9;
            case 10:
               return 4;
            case 11:
               return 5;
            case 12:
               return 5;
         }
      }
   }

   public void registerSynchronization(Synchronization sync) throws RollbackException, IllegalStateException, SystemException {
      throw new SystemException("You may enlist a resource only on a server");
   }

   public void setRollbackOnly() throws IllegalStateException, SystemException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "TX[" + this.getXID() + "] setRollbackOnly()", TxDebug.JTA2PCStackTrace.isDebugEnabled() ? new Exception("DEBUG") : null);
      }

      synchronized(this) {
         this.setRollbackReason(new AppSetRollbackOnlyException("setRollbackOnly called on transaction"));
         switch (this.getStatus()) {
            case 0:
            case 5:
               this.markRollback();
               break;
            case 1:
            case 9:
               return;
            case 2:
            case 3:
            case 8:
               this.throwIllegalStateException("Cannot mark the transaction for rollback as it is committed.");
               break;
            case 4:
               if (IS_ISE_THROWN_DUE_TO_TIMEOUT_RB || this.getRollbackReasonCode() != 1) {
                  this.throwIllegalStateException("Cannot mark the transaction for rollback as it has been rolledback");
               }
            case 6:
            case 7:
         }

      }
   }

   public Xid getXID() {
      return this.xid;
   }

   public Xid getXid() {
      return this.xid;
   }

   public final void setName(String n) {
      this.setProperty("weblogic.transaction.name", n);
   }

   public final String getName() {
      return (String)this.getProperty("weblogic.transaction.name");
   }

   public String getStatusAsString() {
      Throwable rbrThr = this.getRollbackReason();
      String rbr = rbrThr == null ? "Unknown" : rbrThr.toString();
      switch (this.getStatus()) {
         case 0:
            return "Active";
         case 1:
            return "Marked rollback. [Reason=" + rbr + "]";
         case 2:
            return "Prepared";
         case 3:
            return "Committed";
         case 4:
            return "Rolled back. [Reason=" + rbr + "]";
         case 5:
            return "Unknown";
         case 6:
         default:
            return "****** UNKNOWN STATE **** : " + this.getStatus();
         case 7:
            return "Preparing";
         case 8:
            return "Committing";
         case 9:
            return "Rolling Back. [Reason=" + rbr + "]";
      }
   }

   public final synchronized void setRollbackOnly(Throwable t) {
      this.setRollbackOnlyUnsync(t);
   }

   final void setRollbackOnlyUnsync(Throwable t) {
      if (!this.isOver()) {
         this.setRollbackReason(t);
         this.markRollback();
      }
   }

   public final synchronized void setRollbackOnly(String msg, Throwable t) {
      Throwable nestedEx = new NestedException(msg, t);
      this.setRollbackOnlyUnsync(nestedEx);
   }

   public synchronized void setRollbackOnly(String msg) {
      Throwable nestedEx = new Exception(msg);
      this.setRollbackOnlyUnsync(nestedEx);
   }

   public final Throwable getRollbackReason() {
      if (this.rollbackReason instanceof NestedException) {
         NestedException ne = (NestedException)this.rollbackReason;
         return ne.getNestedException();
      } else {
         return this.rollbackReason;
      }
   }

   public synchronized void setProperty(String key, Serializable value) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "setProperty: " + key + "=" + value);
      }

      if (key != null) {
         Map props = this.getOrCreateGlobalProperties();
         if (key.equals("weblogic.transaction.name")) {
            if (props.get(key) == null) {
               if (value != null) {
                  String txname = value.toString();
                  if (txname != null && txname.length() > 0) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, this, "setName: " + txname);
                     }

                     props.put(key, txname);
                  }

               }
            }
         } else if (key.equals("weblogic.transaction.timeoutSeconds")) {
            if (value != null && value instanceof Integer) {
               int newTimeoutSec = (Integer)value;
               if (newTimeoutSec > this.timeoutSec) {
                  this.wakeUpAfterSeconds(this.getTimeToLiveSeconds() + newTimeoutSec - this.timeoutSec);
               }
            }

         } else if (key.equals("weblogic.transaction.nonXAResource")) {
            if (props.get(key) == null) {
               if (value != null) {
                  props.put(key, value);
               }
            }
         } else {
            if (value == null) {
               props.remove(key);
            } else {
               props.put(key, value);
            }

         }
      }
   }

   public synchronized void addProperties(Map moreprops) {
      if (moreprops != null) {
         Iterator i = moreprops.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();

            try {
               this.setProperty((String)me.getKey(), (Serializable)me.getValue());
            } catch (ClassCastException var5) {
               TXLogger.logTxPropertyTypeError();
            }
         }

      }
   }

   public synchronized Serializable getProperty(String key) {
      if (key.equals("weblogic.transaction.timeoutSeconds")) {
         return this.getTimeToLiveSeconds();
      } else {
         return key != null && this.globalProperties != null ? (Serializable)this.globalProperties.get(key) : null;
      }
   }

   public synchronized Map getProperties() {
      return this.globalProperties == null ? null : (Map)((Map)this.globalProperties.clone());
   }

   public void setLocalProperty(String key, Object value) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "setLocalProperty: " + (key == null ? "null" : key) + "=" + (value == null ? "null" : value.toString()));
      }

      if (key != null) {
         ConcurrentHashMap props = this.getOrCreateLocalProperties();
         if (value == null) {
            props.remove(key);
         } else {
            props.put(key, value);
         }

         if (key.equals("weblogic.transaction.otsTransactionExport") || key.equals("weblogic.transaction.local.coordinator.assignment")) {
            this.setCoordinatorDescriptor(this.getTM().getLocalCoordinatorDescriptor());
            props.remove(key);
         }

      }
   }

   public void addLocalProperties(Map moreprops) {
      if (moreprops != null) {
         Iterator i = moreprops.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();

            try {
               this.setLocalProperty((String)me.getKey(), me.getValue());
            } catch (ClassCastException var5) {
               TXLogger.logTxLocalPropertyTypeError();
            }
         }

      }
   }

   public Object getLocalProperty(String key) {
      return key != null && this.localProperties != null ? this.localProperties.get(key) : null;
   }

   public Map getLocalProperties() {
      return this.localProperties == null ? null : new HashMap(this.localProperties);
   }

   public final Object invokeCoordinatorService(String name, Object data) throws SystemException, RemoteException {
      return this.invokeCoordinatorService(name, data, false);
   }

   public final Object invokeCoordinatorService(String name, Object data, boolean useSSL) throws SystemException, RemoteException {
      if (this.getCoordinatorDescriptor() == null) {
         throw new SystemException("No assigned coordinator for transaction.");
      } else {
         Coordinator co = null;
         if (useSSL) {
            this.setSSLEnabled(useSSL);
            Serializable orgProt = null;

            try {
               orgProt = this.getProperty("weblogic.transaction.protocol");
               CoordinatorDescriptor cd = this.getCoordinatorDescriptor();
               if (cd != null) {
                  String sslUrl = PlatformHelper.getPlatformHelper().findLocalSSLURL(this.getCoordinatorURL());
                  if (sslUrl != null) {
                     this.getCoordinatorDescriptor().setSSLCoordinatorURL(sslUrl);
                     this.setProperty("weblogic.transaction.protocol", "t3s");
                  }
               }

               co = (Coordinator)this.getCoordinator();
            } finally {
               this.setProperty("weblogic.transaction.protocol", orgProt);
            }
         } else {
            co = (Coordinator)this.getCoordinator();
         }

         if (co == null) {
            throw new SystemException("Could not reach server at " + this.getCoordinatorURL() + " for service " + name);
         } else if (!(co instanceof CoordinatorService)) {
            throw new SystemException("Server at " + this.getCoordinatorURL() + " does not support service " + name);
         } else {
            return ((CoordinatorService)co).invokeCoordinatorService(name, data);
         }
      }
   }

   public final boolean isCoordinatorLocal() throws SystemException {
      CoordinatorDescriptor cd = this.getCoordinatorDescriptor();
      if (cd == null) {
         throw new SystemException("No assigned coordinator.");
      } else {
         return this.getTM().isLocalCoordinator(cd);
      }
   }

   public final boolean isCoordinatorAssigned() {
      return this.getCoordinatorDescriptor() != null;
   }

   public long getMillisSinceBegin() {
      return System.currentTimeMillis() - this.beginTimeMillis;
   }

   public long getTimeToLiveMillis() {
      return (long)this.getTimeToLiveSeconds() * 1000L;
   }

   public String getHeuristicErrorMessage() {
      return this.heuristicError;
   }

   public boolean isTimedOut() {
      return this.getRollbackReasonCode() == 1;
   }

   protected void setTxAsyncTimeout(boolean value) {
      this.loggerTransactionTimedOut();
      this.txAsyncTimeout = value;
   }

   public boolean isTxAsyncTimeout() {
      return this.txAsyncTimeout;
   }

   public int hashCode() {
      return this.xid.hashCode();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (obj instanceof TransactionImpl) {
         TransactionImpl tx = (TransactionImpl)obj;
         return tx.xid.equals(this.xid);
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      String name = this.getName();
      if (name != null) {
         sb.append("Name=" + name + ",");
      }

      sb.append("Xid=").append(this.getXID()).append("(").append(System.identityHashCode(this)).append(")").append(",Status=").append(this.getStatusAsString());
      if (this.hasHeuristics()) {
         sb.append(",HeuristicErrorCode=").append(XAResourceHelper.xaErrorCodeToString(this.getHeuristicErrorCode(), false));
      }

      sb.append(",numRepliesOwedMe=").append(this.numRepliesOwedMe).append(",numRepliesOwedOthers=").append(this.numRepliesOwedOthers).append(",seconds since begin=" + this.getMillisSinceBegin() / 1000L).append(",seconds left=" + this.getTimeToLiveSeconds()).append(",useSecure=" + this.useSecureURL);
      if (this.activeThread != null) {
         sb.append(",activeThread=" + this.activeThread);
      }

      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList != null) {
         for(int i = 0; i < resourceList.size(); ++i) {
            if (resourceList.get(i) != null) {
               sb.append(",").append(resourceList.get(i).toString());
            }
         }
      }

      synchronized(this) {
         List scList = this.getSCInfoList();
         if (scList != null) {
            Iterator var6 = scList.iterator();

            while(var6.hasNext()) {
               Object aScList = var6.next();
               sb.append(",").append(aScList);
            }
         }
      }

      if (this.globalProperties != null) {
         sb.append(",properties=(" + this.getProperties() + ")");
      }

      if (this.localProperties != null) {
         sb.append(",local properties=(" + this.getLocalProperties() + ")");
      }

      if (this.getOwnerTransactionManager() != null) {
         sb.append(",OwnerTransactionManager=" + this.getOwnerTransactionManager());
      }

      if (this.getCoordinatorDescriptor() != null) {
         sb.append(",CoordinatorURL=" + this.getCoordinatorURL());
      }

      sb.append(")");
      return sb.toString();
   }

   void setPreferredHost(Object pref) {
      if (this.preferredHost == null) {
         this.preferredHost = pref;
      }

   }

   Object getPreferredHost() {
      return this.preferredHost;
   }

   synchronized String[] getSCNames() {
      List scList = this.getSCInfoList();
      if (scList == null) {
         return null;
      } else {
         String[] scNames = new String[scList.size()];

         for(int i = 0; i < scList.size(); ++i) {
            SCInfo sci = (SCInfo)scList.get(i);
            scNames[i] = sci.getName();
         }

         return scNames;
      }
   }

   int getRollbackReasonCode() {
      Throwable t = this.getRollbackReason();
      if (t instanceof NestedException) {
         t = ((NestedException)t).getNestedException();
      }

      if (t == null) {
         return 2;
      } else if (t instanceof TimedOutException) {
         return 1;
      } else if (t instanceof weblogic.transaction.AppSetRollbackOnlyException) {
         return 2;
      } else if (t instanceof XAException) {
         return 3;
      } else {
         return t instanceof SystemException ? 4 : 2;
      }
   }

   public final synchronized void incrRepliesOwedMe() {
      ++this.numRepliesOwedMe;
      if (TxDebug.JTAPropagate.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAPropagate, this, "TransactionImpl.incrRepliesOwedMe(): numRepliesOwedMe=" + this.numRepliesOwedMe + ", numRepliesOwedOthers=" + this.numRepliesOwedOthers);
      }

   }

   public final synchronized void decrRepliesOwedMe() {
      if (this.numRepliesOwedMe > 0) {
         --this.numRepliesOwedMe;
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTAPropagate, this, "TransactionImpl.decrRepliesOwedMe(): numRepliesOwedMe=" + this.numRepliesOwedMe + ", numRepliesOwedOthers=" + this.numRepliesOwedOthers);
         }
      } else if (TxDebug.JTAPropagate.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAPropagate, this, "TransactionImpl.decrRepliesOwedMe(): numRepliesOwedMe cannot be negative; numRepliesOwedMe=" + this.numRepliesOwedMe + ", numRepliesOwedOthers=" + this.numRepliesOwedOthers);
      }

   }

   public final synchronized void incrRepliesOwedOthers() {
      ++this.numRepliesOwedOthers;
      if (TxDebug.JTAPropagate.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAPropagate, this, "TransactionImpl.incrRepliesOwedOthers(): numRepliesOwedMe=" + this.numRepliesOwedMe + ", numRepliesOwedOthers=" + this.numRepliesOwedOthers);
      }

   }

   public final synchronized boolean decrRepliesOwedOthers() {
      if (this.numRepliesOwedOthers > 0) {
         --this.numRepliesOwedOthers;
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTAPropagate, this, "TransactionImpl.decrRepliesOwedOthers(): numRepliesOwedMe=" + this.numRepliesOwedMe + ", numRepliesOwedOthers=" + this.numRepliesOwedOthers);
         }
      } else if (TxDebug.JTAPropagate.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAPropagate, this, "TransactionImpl.decrRepliesOwedOthers(): numRepliesOwedOthers cannot be negative; numRepliesOwedMe=" + this.numRepliesOwedMe + ", numRepliesOwedOthers=" + this.numRepliesOwedOthers);
      }

      if (this.numRepliesOwedOthers <= 0 && this.numRepliesOwedMe > 0 && this.getTM() != this.getOwnerTransactionManager()) {
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTAPropagate, this, "TransactionImpl.decrRepliesOwedOthers(): checked behavior violation; numRepliesOwedMe=" + this.numRepliesOwedMe + ", numRepliesOwedOthers=" + this.numRepliesOwedOthers + ", TM=" + this.getTM() + ", ownerTransactionManager=" + this.getOwnerTransactionManager());
         }

         return false;
      } else {
         return true;
      }
   }

   public final int getWakeupTimeSeconds() {
      return this.wakeUpTimeSec;
   }

   public final int getTimeToLiveSeconds() {
      long t = (long)this.wakeUpTimeSec - System.currentTimeMillis() / 1000L;
      int ret = (int)(t > 2147483647L ? 2147483647L : t);
      return ret;
   }

   int getNumRepliesOwedMe() {
      return this.numRepliesOwedMe;
   }

   int getNumRepliesOwedOthers() {
      return this.numRepliesOwedOthers;
   }

   void abort() throws AbortRequestedException {
      throw new AbortRequestedException();
   }

   void abort(String msg) throws AbortRequestedException {
      this.setRollbackOnly((Throwable)(new SystemException(msg)));
      this.abort();
   }

   void abortUnsync(String msg) throws AbortRequestedException {
      this.setRollbackOnlyUnsync(new SystemException(msg));
      this.abort();
   }

   void abort(Throwable t) throws AbortRequestedException {
      this.setRollbackOnly(t);
      this.abort();
   }

   void abort(String msg, Throwable t) throws AbortRequestedException {
      this.setRollbackOnly((Throwable)(new NestedException(msg, t)));
      this.abort();
   }

   void abort(String msg, Throwable t, boolean overrideReason) throws AbortRequestedException {
      if (ALLOW_OVERRIDE_SETROLLBACK_REASON) {
         this.requestOverrideRollbackReason = overrideReason;
      }

      this.abort(msg, t);
   }

   SCInfo createSCInfo(String scURL) {
      return new SCInfo(scURL);
   }

   synchronized SCInfo getOrCreateSCInfo(String scURL) {
      String ascURL = scURL;
      if (this.scInfoList != null) {
         for(int i = 0; i < this.scInfoList.size(); ++i) {
            SCInfo sci = (SCInfo)this.scInfoList.get(i);
            CoordinatorDescriptor cd = sci.getCoordinatorDescriptor();
            if (cd.representsCoordinatorURL(ascURL)) {
               return sci;
            }
         }
      }

      SCInfo sci = this.createSCInfo(ascURL);
      this.addSC(sci);
      return sci;
   }

   synchronized List getAndRemoveSCInfo(SCInfo migSci) {
      CoordinatorDescriptor migCoorDesc = migSci.getCoordinatorDescriptor();
      if (this.scInfoList != null) {
         for(int i = 0; i < this.scInfoList.size(); ++i) {
            SCInfo sci = (SCInfo)this.scInfoList.get(i);
            if (sci.getCoordinatorDescriptor().equals(migCoorDesc)) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "getAndRemoveSCInfo remove sci = " + sci);
               }

               this.scInfoList.remove(sci);
            }
         }
      }

      return this.scInfoList;
   }

   final List getSCInfoList() {
      if (this.isResourceNotFound()) {
         synchronized(this) {
            return this.scInfoList;
         }
      } else {
         return this.scInfoList;
      }
   }

   synchronized List getAndSubstituteSCInfo(List setSCInfoList) {
      SCInfo sciadd = null;
      this.scInfoList = new CopyOnWriteArrayList();
      if (setSCInfoList != null) {
         for(int i = 0; i < setSCInfoList.size(); ++i) {
            sciadd = (SCInfo)setSCInfoList.get(i);
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "getAndSubstituteSCInfoList add sciadd = " + sciadd);
            }

            this.scInfoList.add(sciadd);
         }
      }

      return this.scInfoList;
   }

   ArrayList getResourceInfoList() {
      return this.resourceInfoList;
   }

   public PropagationContext getRequestPropagationContext() {
      return new PropagationContext(this);
   }

   public PropagationContext getResponsePropagationContext() {
      return new PropagationContext(this);
   }

   public final synchronized boolean isCancelled() {
      return this.isCancelledUnsync();
   }

   final boolean isCancelledUnsync() {
      if (this.isMarkedRollback() && this.getProperty("DISABLE_TX_STATUS_CHECK") == null) {
         return true;
      } else {
         switch (this.getState()) {
            case 9:
            case 10:
               return true;
            default:
               return false;
         }
      }
   }

   public final boolean isOver() {
      switch (this.getState()) {
         case 8:
         case 10:
         case 11:
         case 12:
            return true;
         case 9:
         default:
            return false;
      }
   }

   final void markRollback() {
      this.markedRollback = true;
      this.wakeUpAfterSeconds(this.getNormalizedTimeoutSeconds());
      if (TxDebug.isIsDebugConditionalSetRollbackOnly) {
         this.printDebugMessages();
      }

      this.notify();
   }

   void addHeuristicErrorMessage(String heur) {
      if (this.heuristicError == null) {
         this.heuristicError = heur;
      } else {
         this.heuristicError = this.heuristicError + ", " + heur;
      }

   }

   void addackOrNakCommitDebugMessage(String debugmessage, String scURL) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         this.ackCommitDebugMessages = this.ackCommitDebugMessages + debugmessage;
         this.ackOrNakCommitMap.put(scURL, debugmessage);
      }

   }

   void addResourceCompletionState(short st, String scURL) {
      if (st == 4) {
         this.getTM().addToListOfAckCommits(this.xid, scURL);
      }

      this.addXAResourceCompletionState(st, scURL);
   }

   void addXAResourceCompletionState(short st, String scURL) {
      if ((st == 2 || st == 1) && this.getProperty("ackCommitSCs") != null && ((Map)this.getProperty("ackCommitSCs")).get(scURL) != null) {
         st = 4;
      }

      this.completionState |= st;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         StringBuffer sb = new StringBuffer(50);
         if ((this.completionState & 1) != 0) {
            sb.append(" C_HEURISTIC_MIXED");
         }

         if ((this.completionState & 2) != 0) {
            sb.append(" C_HEURISTIC_HAZARD");
         }

         if ((this.completionState & 4) != 0) {
            sb.append(" C_COMMITTED");
         }

         if ((this.completionState & 8) != 0) {
            sb.append(" C_ROLLEDBACK");
         }

         TxDebug.txdebug(TxDebug.JTA2PC, this, "setResourceOrSCCompletionState:" + sb.toString() + " scURL:" + scURL);
      }

   }

   void setTimeoutSeconds(int seconds) {
      this.timeoutSec = seconds;
   }

   public void setActiveThread(Thread t) {
      this.activeThread = t;
   }

   Thread getActiveThread() {
      return this.activeThread;
   }

   public void setOwnerTransactionManager(TransactionManagerImpl atm) {
      this.ownerTM = atm;
   }

   ResourceInfo createResourceInfo(String resourceName, boolean enlistedElsewhere) {
      return new ResourceInfo(resourceName);
   }

   ResourceInfo getOrCreateResourceInfo(String resourceName) {
      return this.getOrCreateResourceInfo(resourceName, false);
   }

   synchronized ResourceInfo getOrCreateResourceInfo(String resourceName, boolean enlistedElsewhere) {
      ResourceInfo ri = this.getResourceInfo(resourceName);
      if (ri == null) {
         ri = this.createResourceInfo(resourceName, enlistedElsewhere);
         this.addResourceInfoUnsync(ri);
      }

      return ri;
   }

   public final CoordinatorDescriptor getCoordinatorDescriptor() {
      return this.coordinatorDescriptor;
   }

   final String getCoordinatorURL() {
      return this.coordinatorDescriptor == null ? null : this.coordinatorDescriptor.getCoordinatorURL(this.useSecureURL);
   }

   final String getCoServerURL() {
      if (this.coordinatorDescriptor == null) {
         return null;
      } else {
         String coURL = this.coordinatorDescriptor.getCoordinatorURL(this.useSecureURL);
         CoordinatorDescriptor var10000 = this.coordinatorDescriptor;
         return CoordinatorDescriptor.getServerURL(coURL);
      }
   }

   abstract boolean setCoordinatorURL(String var1);

   final String updateCoordinatorDescriptor(String coUrl) {
      String handoffURL = null;
      if (this.coordinatorDescriptor != null && !this.getTM().isLocalCoordinator(coUrl) && this.coordinatorDescriptor.getServerID().equals(CoordinatorDescriptor.getServerID(coUrl)) && !this.coordinatorDescriptor.getCoordinatorURL().equals(coUrl)) {
         handoffURL = this.coordinatorDescriptor.getCoordinatorURL();
         this.coordinatorDescriptor.reinitialize(coUrl);
      }

      return handoffURL;
   }

   final synchronized boolean setCoordinatorDescriptor(CoordinatorDescriptor aCo) {
      return this.setCoordinatorDescriptor(aCo, false);
   }

   final synchronized boolean setCoordinatorDescriptor(CoordinatorDescriptor aCo, boolean isCrossSiteRecovery) {
      if (!this.isCoordinatorDescriptorAssignable()) {
         return false;
      } else if (aCo == null) {
         return false;
      } else {
         assert aCo.getServerName() != null && aCo.getDomainName() != null;

         if (PlatformHelper.getPlatformHelper().isServer() && this.getTM().isLocalCoordinator(aCo)) {
            this.coordinatorDescriptor = this.getTM().getLocalCoordinatorDescriptor();
         } else {
            this.coordinatorDescriptor = aCo;
         }

         String siteName = this.getTM().getSiteName();
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            String coURLHash = new String(aCo.getURLHash());
            TxDebug.txdebug(TxDebug.JTA2PC, this, "setCoordinatorURL =>" + aCo.getCoordinatorURL() + " URLHash:" + coURLHash + " site-name:" + siteName);
         }

         if (siteName != null && !siteName.trim().equals("")) {
            if (this.coordinatorDescriptor.getSiteNameHash() == null) {
               this.coordinatorDescriptor.setSiteName(siteName);
            }

            if (!isCrossSiteRecovery) {
               this.xid.setCoordinatorURL(this.coordinatorDescriptor.getURLHash(), this.coordinatorDescriptor.getSiteNameHash());
            }
         } else {
            this.xid.setCoordinatorURL(this.coordinatorDescriptor.getURLHash());
         }

         return true;
      }
   }

   boolean setCoordinatorDescriptor(String coordinatorURL) {
      if (this.isCoordinatorDescriptorAssignable()) {
         CoordinatorDescriptor cd = CoordinatorDescriptor.getOrCreate(coordinatorURL);
         if (cd != null) {
            return this.setCoordinatorDescriptor(cd);
         }
      }

      return false;
   }

   boolean setCoordinatorDescriptor(Object id, ChannelInterface channel) {
      this.setSSLEnabled(PlatformHelper.getPlatformHelper().isSSLEnabled(id, channel));
      if (this.isCoordinatorDescriptorAssignable()) {
         CoordinatorDescriptor cd = CoordinatorDescriptor.getOrCreate(id, channel);
         if (cd != null) {
            return this.setCoordinatorDescriptor(cd);
         }
      }

      return false;
   }

   public boolean isSSLEnabled() {
      return this.useSecureURL;
   }

   public void setSSLEnabled(boolean enabled) {
      this.useSecureURL = enabled;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "setSSLEnabled(" + enabled + ")", TxDebug.JTA2PCStackTrace.isDebugEnabled() ? new Exception("DEBUG") : null);
      }

   }

   public final boolean isActive() {
      switch (this.getState()) {
         case 1:
         case 2:
         case 3:
            return true;
         default:
            return false;
      }
   }

   final boolean isCommittingOrRollingback() {
      return this.isCommitting() || this.isRollingBack();
   }

   public final boolean isStateActive() {
      return this.getState() == 1;
   }

   final int getTimeoutSeconds() {
      return this.timeoutSec;
   }

   int getNormalizedTimeoutSeconds() {
      int timeoutSec = this.getTimeoutSeconds();
      if (timeoutSec < 10) {
         timeoutSec = 10;
      } else if (timeoutSec > 60) {
         timeoutSec = 60;
      }

      return timeoutSec;
   }

   final int getAbandonTimeoutSeconds() {
      int tmAbandonSec = this.getTM().getAbandonTimeoutSeconds();
      int timeoutSec = this.getTimeoutSeconds();
      return tmAbandonSec < timeoutSec ? timeoutSec : tmAbandonSec;
   }

   int getCompletionTimeoutSeconds() {
      String tmTimeout = (String)this.getProperty("completion-timeout-seconds");
      int tmCompletionSec = 0;
      if (tmTimeout != null) {
         try {
            tmCompletionSec = Integer.parseInt(tmTimeout);
         } catch (NumberFormatException var4) {
            TXLogger.logTxCompletionTimeoutSecondsError();
         }

         if (tmCompletionSec < -1) {
            TXLogger.logTxCompletionTimeoutSecondsError();
            tmCompletionSec = 0;
         }
      } else {
         tmCompletionSec = this.getTM().getCompletionTimeoutSeconds();
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "completion-timeout =" + tmCompletionSec);
      }

      int tmAbandonSec = this.getTM().getAbandonTimeoutSeconds();
      if (tmCompletionSec == 0) {
         return 2 * this.getNormalizedTimeoutSeconds();
      } else if (tmCompletionSec == -1) {
         return Integer.MAX_VALUE;
      } else {
         return tmAbandonSec < tmCompletionSec ? tmAbandonSec : tmCompletionSec;
      }
   }

   public void wakeUp(int nowSec) {
      try {
         if (this.isAbandoned(nowSec)) {
            return;
         }

         this.wakeUpAfterSeconds(this.getNormalizedTimeoutSeconds());
         if (this.isMarkedRollback()) {
            try {
               this.setRollbackOnly((Throwable)(new TimedOutException(this.getXid().toString())));
               this.asyncRollback();
            } catch (Exception var3) {
            }
         }

         switch (this.getState()) {
            case 1:
               this.setRollbackOnly((Throwable)(new TimedOutException(this)));
               break;
            case 2:
            case 3:
            case 5:
            default:
               this.setRolledBack();
               this.getTM().remove(this);
               TXLogger.logWakeupStateErrorForceRB(this.toString());
            case 4:
            case 6:
            case 7:
            case 9:
               break;
            case 8:
               TXLogger.logWakeupForCommittedTx(this.toString());
               break;
            case 10:
            case 11:
               this.getTM().remove(this);
         }
      } catch (Exception var4) {
      }

   }

   final byte getState() {
      return this.state;
   }

   static void setAbandonGraceTimeEndSec(int sec) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("setAbandonGraceTimeEndSec(" + sec + ")");
      }

      abandonGraceTimeEndSec = sec;
   }

   final void setNonXAResource(ResourceInfo nxar) {
      this.nonXAResource = nxar;
   }

   final ResourceInfo getNonXAResource() {
      return this.nonXAResource;
   }

   public Xid getForeignXid() {
      return (Xid)this.getProperty("weblogic.transaction.foreignXid");
   }

   void setForeignXid(Xid foreignXid) {
      this.setProperty("weblogic.transaction.foreignXid", XidImpl.create(foreignXid));
   }

   public boolean isImportedTransaction() {
      return this.getProperty("weblogic.transaction.foreignXid") != null;
   }

   void setForeignOnePhase(boolean onePhase) {
      if (onePhase) {
         this.setProperty("weblogic.transaction.foreignOnePhase", "true");
      } else {
         this.setProperty("weblogic.transaction.foreignOnePhase", (Serializable)null);
      }

   }

   boolean isForeignOnePhase() {
      return this.getProperty("weblogic.transaction.foreignOnePhase") != null;
   }

   public int getHeuristicErrorCode() {
      return this.heuristicErrorCode;
   }

   public boolean hasHeuristics() {
      return this.heuristicErrorCode != -1 && this.heuristicErrorCode != 0;
   }

   protected final boolean isCoordinatorDescriptorAssignable() {
      return this.coordinatorDescriptor == null || this.isOTSExport();
   }

   protected final void setXID(XidImpl axid) {
      this.xid = axid;
   }

   protected final void unmarkRollback() {
      this.markedRollback = false;
   }

   protected final short getCompletionState() {
      return this.completionState;
   }

   protected final int getNumResources() {
      return this.resourceInfoList != null ? this.resourceInfoList.size() : 0;
   }

   public final boolean isMarkedRollback() {
      return this.markedRollback;
   }

   protected synchronized SCInfo getSCInfo(String scURL) {
      if (this.scInfoList != null) {
         for(int i = 0; i < this.scInfoList.size(); ++i) {
            SCInfo sci = (SCInfo)this.scInfoList.get(i);
            if (sci.getCoordinatorDescriptor().representsCoordinatorURL(scURL)) {
               return sci;
            }
         }
      }

      return null;
   }

   public void globalRollback() throws SystemException, IllegalStateException {
      this.localRollback();
      if (this.getCoordinatorURL() != null) {
         try {
            final Coordinator co = (Coordinator)this.getCoordinator();
            if (co == null) {
               throw new SystemException("Could not contact coordinator at " + this.getCoordinatorURL());
            }

            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "co.rollback");
            }

            try {
               if (PlatformHelper.getPlatformHelper().isServer()) {
                  PlatformHelper.getPlatformHelper().runAction(new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        co.rollback(TransactionImpl.this.getRequestPropagationContext());
                        return null;
                     }
                  }, this.getCoServerURL(), "co.rollback");
               } else {
                  co.rollback(this.getRequestPropagationContext());
               }
            } catch (Exception var3) {
               if (var3 instanceof SystemException) {
                  throw (SystemException)var3;
               }

               if (var3 instanceof IllegalStateException) {
                  throw (IllegalStateException)var3;
               }

               if (var3 instanceof RemoteException) {
                  throw (RemoteException)var3;
               }

               throw new AssertionError(var3);
            }
         } catch (RemoteException var4) {
            String msg = "Coordinator.rollback. Unable to contact coordinator";
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "co.rollback failed", var4);
            }

            throw new SystemException(msg + ": " + var4);
         }
      }

   }

   protected void localRollback() {
      this.setRolledBack();
   }

   public void setRollbackReason(Throwable t) {
      if (t != null && (this.requestOverrideRollbackReason && this.rollbackReason != null && this.rollbackReason instanceof weblogic.transaction.AppSetRollbackOnlyException || this.rollbackReason == null)) {
         this.rollbackReason = t;
      }

   }

   protected final String getRollbackReasonMessage() {
      String unknown = "Unknown reason";
      String nestedMsg = null;
      if (this.rollbackReason == null) {
         return unknown;
      } else {
         String msg = this.rollbackReason.getMessage();
         if (this.rollbackReason instanceof NestedException) {
            NestedException ne = (NestedException)this.rollbackReason;
            Throwable t = ne.getNestedException();
            if (t != null) {
               nestedMsg = t.getMessage();
            }
         }

         if (nestedMsg != null) {
            if (msg != null) {
               msg = msg + PlatformHelper.getPlatformHelper().getEOLConstant();
            }

            msg = msg + nestedMsg;
         }

         return msg != null ? msg : unknown;
      }
   }

   protected final void setState(byte newState) {
      if (TxDebug.JTA2PC.isDebugEnabled() && this.state != newState) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "TX[" + this.getXID() + "] " + this.getStateAsString(this.state) + "-->" + this.getStateAsString(newState), TxDebug.JTA2PCStackTrace.isDebugEnabled() ? new Exception("DEBUG") : null);
      }

      this.state = newState;
   }

   protected final synchronized void checkIfCommitPossible() throws RollbackException, AbortRequestedException, IllegalStateException {
      switch (this.getStatus()) {
         case 4:
         case 9:
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "TransactionImpl.checkIfCommitPossible(): " + this.getStatusAsString());
            }

            this.setRollbackReason(new SystemException("The transaction has been rolled back"));
            this.throwRollbackException();
         case 1:
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "TransactionImpl.checkIfCommitPossible(): " + this.getStatusAsString());
            }

            this.setRollbackReason(new SystemException("The transaction has been marked rollback"));
            throw new AbortRequestedException();
         default:
            this.checkOwner();
            if (!this.isActive()) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "TransactionImpl.checkIfCommitPossible(): not active");
               }

               this.throwIllegalStateException("Cannot commit transaction");
            }

            this.enforceCheckedTransaction();
            this.checkIfDeterminerIsUnregisteredFromThisServer();
      }
   }

   protected void checkOwner() throws SecurityException {
      if (this.getTM() != this.getOwnerTransactionManager()) {
         String msg = "Attempt to commit in a different server from the one in which this transaction was begun.  " + this.getXid().toString();
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "TransactionImpl.checkIfCommitPossible(): " + msg);
         }

         throw new SecurityException(msg);
      }
   }

   protected void enforceCheckedTransaction() throws AbortRequestedException {
      if (this.numRepliesOwedMe != 0) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "TransactionImpl.checkIfCommitPossible(): " + this.numRepliesOwedMe + " replies outstanding");
         }

         String trailer = this.numRepliesOwedMe == 1 ? "is one such request" : "are " + this.numRepliesOwedMe + " such requests";
         this.abort("Commit can be issued only when there are no requests awaiting responses. Currently there " + trailer);
      }

   }

   public final synchronized boolean isResumePossible() {
      if (this.getTM() == this.getOwnerTransactionManager()) {
         return true;
      } else {
         return this.numRepliesOwedOthers > 0;
      }
   }

   protected final boolean isAbandoned() {
      return this.getState() == 12;
   }

   protected boolean isAbandoned(int nowSec) {
      if (nowSec < abandonGraceTimeEndSec) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug("Still within transaction abandon grace period.  Will start abandoning transactions after " + (abandonGraceTimeEndSec - nowSec) + " seconds.  " + this);
         }

         return false;
      } else {
         int abortSec = this.getAbandonTimeoutSeconds();
         int beginSec = (int)(this.getBeginTimeMillis() / 1000L);
         if (nowSec - beginSec > abortSec) {
            TXLogger.logAbandoningTx(nowSec - beginSec, this.toString());
            this.abandon();
            return true;
         } else {
            return false;
         }
      }
   }

   protected void abandon() {
      this.setAbandoned();
      this.getTM().remove(this);
   }

   protected int getSecondsToAbandon() {
      int timeToAbandon = this.getAbandonTimeoutSeconds() - (int)((System.currentTimeMillis() - this.getBeginTimeMillis()) / 1000L);
      int abandonGrace = abandonGraceTimeEndSec - (int)(System.currentTimeMillis() / 1000L);
      return Math.max(timeToAbandon, abandonGrace);
   }

   protected final void throwRollbackException() throws RollbackException {
      Throwable rr = this.getRollbackReason();
      String msg = this.getRollbackReasonMessage();
      throw new weblogic.transaction.RollbackException(msg, rr);
   }

   protected final void throwIllegalStateException(String msg) {
      StringBuffer sb = new StringBuffer(100);
      sb.append(msg).append(". xid=").append(this.getXID()).append(", status=").append(this.getStatusAsString());
      msg = sb.toString();
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("Illegal State exception" + msg);
      }

      throw new IllegalStateException(sb.toString());
   }

   final CoordinatorOneway getCoordinator() {
      CoordinatorDescriptor coDesc = this.getCoordinatorDescriptor();
      return coDesc == null ? null : CoordinatorFactory.getCoordinator(coDesc, this);
   }

   protected final void setPreparing() {
      this.setState((byte)4);
   }

   protected final boolean isPreparing() {
      return this.getState() == 4;
   }

   protected void setPrepared() {
      this.setState((byte)6);
   }

   public final boolean isPrepared() {
      return this.getState() == 6;
   }

   protected final void setCommitting() {
      this.setState((byte)7);
   }

   protected final boolean isCommitting() {
      return this.getState() == 7;
   }

   protected void setCommitted() {
      this.getTM().remove(this);
      synchronized(this) {
         if (!this.isOver()) {
            this.setState((byte)8);
         }

      }
   }

   protected final synchronized void setRollingBack() {
      this.setRollingBackUnsync();
   }

   protected final void setRollingBackUnsync() {
      this.unmarkRollback();
      this.setState((byte)9);
   }

   protected final boolean isRollingBack() {
      return this.getState() == 9;
   }

   protected synchronized void setRolledBack() {
      this.getTM().remove(this);
      this.unmarkRollback();
      this.wakeUpAfterSeconds(10);
      if (!this.isOver()) {
         this.setState((byte)10);
      }

   }

   protected synchronized void setUnknown() {
      this.setUnknownUnsync();
   }

   protected void setUnknownUnsync() {
      this.unmarkRollback();
      if (!this.isOver()) {
         this.setState((byte)11);
      }

   }

   protected synchronized void setAbandoned() {
      this.unmarkRollback();
      if (!this.isOver()) {
         this.setState((byte)12);
      }

   }

   protected synchronized void addResourceInfo(ResourceInfo ri) {
      this.addResourceInfoUnsync(ri);
   }

   protected void addResourceInfoUnsync(ResourceInfo ri) {
      if (this.resourceInfoList == null) {
         this.resourceInfoList = new ArrayList(2);
      }

      this.resourceInfoList.add(ri);
      if (this.checkNonXAResourceProperty(ri.getName())) {
         this.setNonXAResource(ri);
      }

      ri.incrementTxRefCount();
   }

   protected boolean checkNonXAResourceProperty(String name) {
      String nxarName = (String)this.getProperty("weblogic.transaction.nonXAResource");
      return nxarName != null && nxarName.equals(name);
   }

   protected final synchronized void addSC(SCInfo sci) {
      if (this.scInfoList == null) {
         this.scInfoList = new CopyOnWriteArrayList();
      }

      this.scInfoList.add(sci);
   }

   protected final void asyncRollback() {
      Runnable work = new Runnable() {
         public void run() {
            try {
               TransactionImpl.this.globalRollback();
            } catch (Exception var2) {
            }

         }
      };
      PlatformHelper.getPlatformHelper().scheduleWork(work);
   }

   protected final void wakeUpAfterSeconds(int interval) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         String msg = this.toString() + " wakeUpAfterSeconds(" + interval + ")";
         if (TxDebug.JTA2PCStackTrace.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTA2PC, msg);
         } else {
            TxDebug.JTA2PC.debug(msg);
         }
      }

      long t = System.currentTimeMillis() / 1000L + (long)interval;
      this.wakeUpTimeSec = (int)(t > 2147483647L ? 2147483647L : t);
   }

   protected final long getBeginTimeMillis() {
      return this.beginTimeMillis;
   }

   protected final void setBeginTimeMillis(long t) {
      this.beginTimeMillis = t;
   }

   protected final ResourceInfo getResourceInfo(String resourceName) {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList == null) {
         return null;
      } else {
         for(int i = 0; i < resourceList.size(); ++i) {
            ResourceInfo ri = (ResourceInfo)resourceList.get(i);
            if (ri.getName().equals(resourceName)) {
               return ri;
            }
         }

         return null;
      }
   }

   protected TransactionManagerImpl getOwnerTransactionManager() {
      return this.ownerTM;
   }

   void setDelayRemoveAfterRollback(boolean b) {
      this.delayRemoveAfterRollback = b;
   }

   protected boolean getDelayRemoveAfterRollback() {
      return this.delayRemoveAfterRollback;
   }

   public abstract int internalPrepare() throws AbortRequestedException, RollbackException, SystemException, XAException;

   public abstract void internalCommit(boolean var1) throws AbortRequestedException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, XAException;

   public abstract void internalForget() throws SystemException, XAException;

   private TransactionManagerImpl getTM() {
      return TransactionManagerImpl.getTransactionManager();
   }

   private Map getOrCreateGlobalProperties() {
      if (this.globalProperties == null) {
         this.globalProperties = new HashMap();
      }

      return this.globalProperties;
   }

   private ConcurrentHashMap getOrCreateLocalProperties() {
      if (this.localProperties == null) {
         this.localProperties = this.createLocalProperties();
      }

      return this.localProperties;
   }

   private ConcurrentHashMap createLocalProperties() {
      if (this.volatileLocalProperties == null) {
         synchronized(this) {
            if (this.volatileLocalProperties == null) {
               this.volatileLocalProperties = new ConcurrentHashMap();
            }
         }
      }

      return this.volatileLocalProperties;
   }

   protected String getStateAsString(int st) {
      switch (st) {
         case 1:
            return "active";
         case 2:
            return "pre_preparing";
         case 3:
            return "pre_prepared";
         case 4:
            return "preparing";
         case 5:
            return "logging";
         case 6:
            return "prepared";
         case 7:
            return "committing";
         case 8:
            return "committed";
         case 9:
            return "rolling back";
         case 10:
            return "rolled back";
         case 11:
         default:
            return "UNKNOWN";
         case 12:
            return "abandoned";
      }
   }

   private boolean isOTSExport() {
      return this.getLocalProperty("weblogic.transaction.otsTransactionExport") != null;
   }

   public Object getResource(Object key) {
      return this.resources == null ? null : this.resources.get(key);
   }

   public Object putResource(Object key, Object value) {
      if (this.resources == null) {
         this.resources = new HashMap();
      }

      return this.resources.put(key, value);
   }

   void check(String location) {
      this.check(location, (String)null, (String)null);
   }

   void check(String location, String server) {
      this.check(location, server, (String)null);
   }

   void check(String location, String server, String resource) {
      if (INSTR_ENABLED) {
         System.out.println("*** Instrumented location:" + location + " server:" + server + " resource:" + resource);

         try {
            String prop = location + "SleepMillis";
            if (server != null) {
               prop = prop + "." + server;
               if (resource != null) {
                  prop = prop + "." + resource;
               }
            }

            Integer millis = (Integer)this.getProperty(prop);
            if (millis != null && millis > 0) {
               System.out.println("*** Instrumented '" + prop + "'=" + millis + " xid=" + this.getXid() + " start sleeping.");

               try {
                  Thread.currentThread();
                  Thread.sleep((long)millis);
               } catch (InterruptedException var8) {
               }

               System.out.println("*** Instrumented '" + prop + "'=" + millis + " xid=" + this.getXid() + " done sleeping.");
            }

            prop = location + "Exit";
            if (server != null) {
               prop = prop + "." + server;
               if (resource != null) {
                  prop = prop + "." + resource;
               }
            }

            Boolean doExit = (Boolean)this.getProperty(prop);
            if (doExit != null && doExit) {
               System.out.println("*** Instrumented '" + prop + "' xid=" + this.getXid());
               Runtime.getRuntime().halt(-1);
            }

            prop = location + "Invoke";
            if (server != null) {
               prop = prop + "." + server;
               if (resource != null) {
                  prop = prop + "." + resource;
               }
            }

            Runnable runnable = (Runnable)this.getProperty(prop);
            if (runnable != null) {
               System.out.println("*** Instrumented '" + prop + "'=" + runnable + ", xid=" + this.getXid() + " invoking Runnable.");
               runnable.run();
            }
         } catch (ClassCastException var9) {
            var9.printStackTrace();
         }

      }
   }

   public void checkLLR(String location) throws SystemException {
      if (INSTR_ENABLED) {
         Boolean doThrow = (Boolean)this.getProperty(location);
         if (doThrow != null && doThrow) {
            System.out.println("*** Instrumented '" + location + "' xid=" + this.getXid());
            throw new SystemException(location);
         }
      }
   }

   public void checkLLRRetry() throws SystemException {
      if (INSTR_ENABLED) {
         Integer retry = (Integer)this.getProperty("LLRFailRetry");
         if (retry != null) {
            Boolean throwBeforeLogWrite = (Boolean)this.getProperty("LLRFailBeforeLogWrite");
            Boolean throwAfterLogWrite = (Boolean)this.getProperty("LLRFailAfterLogWrite");
            Boolean throwBeforeCommit = (Boolean)this.getProperty("LLRFailBeforeCommit");
            Boolean throwAfterCommit = (Boolean)this.getProperty("LLRFailAfterCommit");
            if (throwBeforeLogWrite != null || throwAfterLogWrite != null || throwBeforeCommit != null || throwAfterCommit != null) {
               int retryCount = 0;
               String countProp = "LLRFailRetry.count";
               Integer count = (Integer)this.getProperty(countProp);
               if (count != null) {
                  retryCount = count;
               }

               if (retryCount < retry) {
                  this.setProperty(countProp, retryCount + 1);
                  System.out.println("*** Instrumented 'LLRFailRetry' xid=" + this.getXid() + " retry=" + retryCount);
                  throw new SystemException("LLRFailRetry, retry count " + retryCount);
               }
            }
         }
      }
   }

   void loggerTransactionTimedOut() {
      TXLogger.logTransactionTimedOut(this.getXID().toString(), this.getMillisSinceBegin() / 1000L);
   }

   public synchronized Set getServerParticipantNames() {
      if (this.scInfoList == null) {
         return null;
      } else {
         Set serverParticipantNames = new HashSet();

         for(int i = 0; i < this.scInfoList.size(); ++i) {
            String serverName = ((SCInfo)this.scInfoList.get(i)).getCoordinatorDescriptor().getServerName();
            if (!serverParticipantNames.contains(serverName)) {
               serverParticipantNames.add(serverName);
            }
         }

         return serverParticipantNames;
      }
   }

   void setUseLLR(boolean aUseLLR) {
      this.useLLR = aUseLLR;
   }

   boolean getUseLLR() {
      return this.useLLR;
   }

   void setLocalConnCommittedOrRollback(boolean localConnCommittedOrRollback) {
      this.localConnCommittedOrRollback = localConnCommittedOrRollback;
   }

   boolean getLocalConnCommittedOrRollback() {
      return this.localConnCommittedOrRollback;
   }

   boolean isResourceNotFound() {
      return this.getProperty("weblogic.transaction.resourceNotFound") != null && this.getProperty("weblogic.transaction.resourceNotFound").equals(true);
   }

   void setResourceNotFoundTrue() {
      this.setProperty("weblogic.transaction.resourceNotFound", true);
   }

   boolean isAllResourcesAssigned() {
      return this.getProperty("weblogic.transaction.allResourcesAssigned") != null && this.getProperty("weblogic.transaction.allResourcesAssigned").equals(true);
   }

   void setAllResourcesAssignedTrue() {
      this.setProperty("weblogic.transaction.allResourcesAssigned", true);
   }

   void addDebugMessage(String s) {
      this.debugMessages.add(s);
   }

   public void printDebugMessages() {
      System.out.println("Printing debug messages for condition(s) defined from thread " + Thread.currentThread() + "...");
      synchronized(this.debugMessages) {
         Iterator var2 = this.debugMessages.iterator();

         while(true) {
            if (!var2.hasNext()) {
               this.debugMessages.clear();
               break;
            }

            Object debugMessage = var2.next();
            System.out.println(debugMessage);
         }
      }

      this.printDebugMessagesOnCompletion = true;
      this.setProperty("printDebugMessagesOnCompletion", Boolean.TRUE);
   }

   static {
      String propVal = System.getProperty("weblogic.transaction.allowOverrideSetRollbackReason");
      ALLOW_OVERRIDE_SETROLLBACK_REASON = propVal == null ? false : propVal.equalsIgnoreCase("true");
      abandonGraceTimeEndSec = (int)(System.currentTimeMillis() / 1000L) + 3600;
      islookupresourceonretryEnabled = new Boolean(System.getProperty("weblogic.transaction.internal.lookupresourceonretry", "true"));
      lookupresourceonretryCount = Integer.parseInt(System.getProperty("weblogic.transaction.internal.lookupresourceonretrycount", "3"));
      GLOBAL_PROPERTIES = new HashMap();
      LOCAL_PROPERTIES = new HashMap();
      propVal = System.getProperty("weblogic.transaction.EnableInstrumentedTM");
      INSTR_ENABLED = propVal != null && propVal.equals("true");
      propVal = System.getProperty("weblogic.transaction.global.properties");
      List nameValuePairs;
      Iterator var2;
      String nameValuePair;
      int equalsLocation;
      String key;
      String value;
      if (propVal != null) {
         nameValuePairs = Arrays.asList(propVal.split("\\s*,\\s*"));
         var2 = nameValuePairs.iterator();

         while(var2.hasNext()) {
            nameValuePair = (String)var2.next();
            equalsLocation = nameValuePair.indexOf("=");
            key = nameValuePair.substring(0, equalsLocation);
            value = nameValuePair.substring(equalsLocation + 1, propVal.length());
            GLOBAL_PROPERTIES.put(key, value);
         }
      }

      propVal = System.getProperty("weblogic.transaction.local.properties");
      if (propVal != null) {
         nameValuePairs = Arrays.asList(propVal.split("\\s*,\\s*"));
         var2 = nameValuePairs.iterator();

         while(var2.hasNext()) {
            nameValuePair = (String)var2.next();
            equalsLocation = nameValuePair.indexOf("=");
            key = nameValuePair.substring(0, equalsLocation);
            value = nameValuePair.substring(equalsLocation + 1, propVal.length());
            LOCAL_PROPERTIES.put(key, value);
         }
      }

   }
}
