package weblogic.transaction.internal;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.transaction.ChannelInterface;
import weblogic.transaction.TransactionLoggable;
import weblogic.transaction.TransactionLogger;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.loggingresource.MigratableLoggingResource;
import weblogic.transaction.nonxa.DataSourceEmulatedTwoPhaseResource;
import weblogic.transaction.nonxa.EmulatedTwoPhaseResource;
import weblogic.transaction.nonxa.NonXAResource;

public class ServerTransactionImpl extends TransactionImpl implements TransactionLoggable {
   private ServerSCInfo localSCInfo = null;
   private boolean retry;
   private boolean resourceCheckInProgress;
   private boolean completionTallied;
   private boolean completionPartitionTallied;
   private int beforeCompletionIterationCount = 0;
   private HeuristicsLog heuristicsLog;
   private TransactionLogger migratedTxLogger = null;
   private NonXAServerResourceInfo loggingResourceInfo;
   private LoggingResource loggingResource;
   private SystemException loggingResourceCommitFailure;
   private SystemException onePhaseResourceCommitFailure;
   private Object completionId;
   private boolean isRDONLYReturnFromDeterminer;
   private List listDB2ResourcesEnlisted = new ArrayList();
   private static boolean m_isTxUnknownAfterCompletionEnabled = Boolean.getBoolean("weblogic.transaction.tx_state_unknown_aftercompletion");
   private static final boolean m_isBlockingCommit = Boolean.getBoolean("weblogic.transaction.blocking.commit");
   private static final boolean m_isBlockingRollback = Boolean.getBoolean("weblogic.transaction.blocking.rollback");
   private static int m_pre_prepared_wait_seconds = 60;
   private boolean firstSSL = true;
   static String migratedResourceCoordinatorURL;
   private static final boolean isLocalCoordinatorAssignment;
   static final boolean isaadrprototype;

   protected ServerTransactionImpl() {
   }

   ServerTransactionImpl(Xid axid, int aTimeoutSec, int aTimeToLiveSec) {
      super(axid, aTimeoutSec, aTimeToLiveSec);
      this.init();
   }

   ServerTransactionImpl(Xid xid, Xid foreignXid, int timeOutSecs, int timeToLiveSecs) {
      super(xid, foreignXid, timeOutSecs, timeToLiveSecs);
      this.init();
   }

   ServerTransactionImpl(Xid axid, int aTimeoutSec, int aTimeToLiveSec, boolean useSSLProtocol) {
      super(axid, aTimeoutSec, aTimeToLiveSec);
      this.setSSLEnabled(useSSLProtocol);
      this.init();
   }

   ServerTransactionImpl(Xid axid, int aTimeoutSec, int aTimeToLiveSec, boolean useSSLProtocol, boolean isFromPropagationContext) {
      super(axid, aTimeoutSec, aTimeToLiveSec, isFromPropagationContext);
      this.setSSLEnabled(useSSLProtocol);
      this.init();
   }

   private void init() {
      ServerSCInfo localSCI = this.getOrCreateLocalSCInfo();
      Iterator it = ResourceDescriptor.getAllResources().iterator();

      while(it.hasNext()) {
         ResourceDescriptor rd = (ResourceDescriptor)it.next();
         if (rd.needsStaticEnlistment(false)) {
            ServerResourceInfo ri = null;
            if (rd instanceof XAResourceDescriptor) {
               ri = new XAServerResourceInfo(rd);
            } else {
               ri = new NonXAServerResourceInfo(rd);
            }

            this.addResourceInfo((ResourceInfo)ri);
            ((ServerResourceInfo)ri).addSC(localSCI);
         }
      }

   }

   ResourceInfo createResourceInfo(String resourceName, boolean enlistedElsewhere) {
      if (this.checkNonXAResourceProperty(resourceName)) {
         return new NonXAServerResourceInfo(resourceName);
      } else {
         String aliasFor = (String)this.getProperty("weblogic.transaction.resource.alias." + resourceName);
         return aliasFor != null ? new XAServerResourceInfo(aliasFor, resourceName, enlistedElsewhere) : new XAServerResourceInfo(resourceName, enlistedElsewhere);
      }
   }

   SCInfo createSCInfo(String scURL) {
      ServerSCInfo scinfo = new ServerSCInfo(scURL);
      scinfo.incrementCoordinatorRefCount();
      return scinfo;
   }

   public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
      if (this.isImportedTransaction()) {
         throw new SystemException("Cannot call commit on imported transaction directly.  Imported transactions should only be committed via XAResource.commit.  " + this.getXid().toString());
      } else {
         this.internalCommit();
      }
   }

   private void internalCommit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, SystemException {
      if (!this.isCoordinatingTransaction()) {
         this.setPrePreparing();
         super.commit();
      } else {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.commit()");
         }

         int vote = 3;

         try {
            if (!this.isImportedTransaction() || this.isForeignOnePhase() && !this.isCommitting()) {
               super.checkIfCommitPossible();
               this.globalPrePrepare();
               vote = this.globalPrepare();
               if (this.getLoggingResourceCommitFailure() != null) {
                  PlatformHelper.getPlatformHelper().registerFailedLLRTransactionLoggingResourceRetry(this);
                  throw this.getLoggingResourceCommitFailure();
               }
            }

            this.globalCommit(vote);
            if (this.getLoggingResourceCommitFailure() != null) {
               throw this.getLoggingResourceCommitFailure();
            }

            if (this.getOnePhaseResourceCommitFailure() != null) {
               throw this.getOnePhaseResourceCommitFailure();
            }

            short st = this.getHeuristicStatus(4);
            if (st != 0) {
               String str = this.getHeuristicErrorMessage();
               TXLogger.logHeuristicCompletion(this.toString(), str);
               if (this.isOnePhaseCommitPossible()) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, str);
                  }

                  throw new weblogic.transaction.RollbackException(str);
               }

               switch (st) {
                  case 1:
                     throw new HeuristicMixedException(str);
                  case 2:
                  default:
                     throw new HeuristicMixedException(str);
                  case 8:
                     throw new HeuristicRollbackException(str);
               }
            }

            if (!this.allSCsCommitted() || !this.allResourcesDone()) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "commit: timeout to client");
               }

               throw new SystemException("Timeout during commit processing");
            }

            this.setCommittedIfNotAbandoned();
         } catch (RollbackException var19) {
            try {
               this.globalRollback();
            } catch (Exception var17) {
            }

            this.setRolledBack();
            throw var19;
         } catch (HeuristicRollbackException var20) {
            this.setCommitted();
            throw var20;
         } catch (HeuristicMixedException var21) {
            if (this.allSCsCommitted() && this.allResourcesDone()) {
               this.setCommitted();
            }

            throw var21;
         } catch (SecurityException var22) {
            throw var22;
         } catch (IllegalStateException var23) {
            throw var23;
         } catch (AbortRequestedException var24) {
            try {
               this.globalRollback();
            } catch (Exception var18) {
            }

            this.throwRollbackException();
         } catch (SystemException var25) {
            if (this.getOnePhaseResourceCommitFailure() != null) {
               this.setUnknown();
            }

            throw var25;
         } catch (Exception var26) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "Unexpected Commit Exception:", var26);
            }

            short st = (short)this.getState();
            switch (st) {
               case 1:
               case 2:
               case 3:
               case 4:
               case 6:
                  this.globalRollback();
                  break;
               case 5:
               default:
                  this.setUnknown();
            }

            SystemException se = new SystemException(var26.toString());
            se.initCause(var26);
            throw se;
         } finally {
            this.getTM().suspend(this);
         }

      }
   }

   public void rollback() throws IllegalStateException, SystemException {
      if (this.isImportedTransaction()) {
         throw new SystemException("Cannot call rollback on imported transaction directly.  Imported transactions should only be rolled back via XAResource.rollback.  " + this.getXid().toString());
      } else {
         this.internalRollback();
      }
   }

   public void internalRollback() throws IllegalStateException, SystemException {
      try {
         switch (this.getStatus()) {
            case 2:
               if (this.isImportedTransaction() && this.isCoordinatingTransaction()) {
                  break;
               }
            case 3:
            case 8:
               this.throwIllegalStateException("Transaction can no longer be rolled back. " + this.getXid().toString());
               break;
            case 4:
            case 9:
               return;
            case 5:
            case 6:
            case 7:
         }

         this.setRollingBack();
         this.globalRollback();
         short st = this.getHeuristicStatus(8);
         if (st != 0) {
            String str = this.getHeuristicErrorMessage();
            TXLogger.logHeuristicCompletion(this.toString(), str);
            switch (st) {
               case 1:
                  throw new SystemException("Heuristic mixed: " + str);
               case 2:
                  throw new SystemException("Heuristic hazard: " + str);
               case 3:
               default:
                  throw new SystemException("Heuristic error: " + str);
               case 4:
                  throw new SystemException("Heuristic commit: " + str);
            }
         } else if (this.allSCsRolledBack() && this.getStatus() != 9) {
            this.setRolledBackIfNotAbandoned();
         } else if (this.allSCsRolledBack() && this.allResourcesDone()) {
            this.setRolledBackIfNotAbandoned();
         } else {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "rollback: timeout to client");
            }

            this.wakeUpAfterSeconds(this.getNormalizedTimeoutSeconds());
            throw new SystemException("Timeout during rollback processing");
         }
      } finally {
         this.getTM().suspend(this);
      }
   }

   public boolean enlistResource(XAResource xar) throws RollbackException, IllegalStateException, SystemException {
      if (xar instanceof IgnoreXAResource) {
         return true;
      } else if (xar instanceof DataSourceEmulatedTwoPhaseResource && ((DataSourceEmulatedTwoPhaseResource)xar).isOnePhaseCommit() && this.getResourceInfoList() != null && this.getResourceInfoList().size() > 0) {
         throw new RollbackException("An attempt was made to enlist a datasource with global-transactions-protocol set to OnePhaseCommit  when one or more resources have already been enlisted in the transaction.");
      } else {
         return this.enlistResource(xar, "");
      }
   }

   public boolean enlistResourceWithProperties(XAResource xar, Map properties) throws RollbackException, IllegalStateException, SystemException {
      if (properties != null && "transaction.enlistment.resource.type.weblogic.jms".equals(properties.get("transaction.enlistment.resource.type"))) {
         String[] determiners = this.getTM().getDeterminersForDomainAndAllPartitions();
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.enlistResourceWithProperties with ENLISTMENT_RESOURCE_TYPE_WEBLOGIC_JMS xar:" + xar + " determiners.length:" + (determiners == null ? "null" : determiners.length));
         }

         String[] var4 = determiners;
         int var5 = determiners.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String determiner = var4[var6];
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.enlistResourceWithProperties with ENLISTMENT_RESOURCE_TYPE_WEBLOGIC_JMS xar:" + xar + " determiner:" + determiner);
            }

            if (determiner.equals("WebLogic_JMS")) {
               ResourceDescriptor resourceDescriptor = XAResourceDescriptor.get(xar);
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.enlistResourceWithProperties with ENLISTMENT_RESOURCE_TYPE_WEBLOGIC_JMS xar:" + xar + " determiner:" + determiner + " resourceDescriptor:" + resourceDescriptor);
               }

               if (resourceDescriptor != null) {
                  resourceDescriptor.setDeterminer(true);
                  resourceDescriptor.setIsResourceCheckpointNeeded(true);
                  this.checkForAndSetDeterminerOrFirstResourceCommit(resourceDescriptor);
               }
            }
         }
      } else if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.enlistResourceWithProperties with no ENLISTMENT_RESOURCE_TYPE_WEBLOGIC_JMS xar:" + xar + " properties:" + properties);
      }

      boolean returnValue = this.enlistResource(xar);
      return returnValue;
   }

   private void checkNewResource(XAServerResourceInfo ri, String name) throws SystemException {
      if (this.getResourceInfo((String)name) != null) {
         ri.setEnlistedElsewhere();
      }

      ri.checkNewEnlistment();
   }

   public boolean enlistResource(XAResource xar, String resourceNameAlias) throws RollbackException, IllegalStateException, SystemException {
      if (resourceNameAlias.equals("")) {
         resourceNameAlias = null;
      }

      ResourceDescriptor rd = XAResourceDescriptor.get(xar);
      if (rd != null && (rd.isDeterminer() || rd.isFirstResourceCommit())) {
         if (rd.isFirstResourceCommit() && this.getProperty("weblogic.transaction.first.resource.commitServer") == null) {
            this.setProperty("weblogic.transaction.first.resource.commitServer", this.getLocalSCInfo().getServerName());
         }

         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, (rd.isDeterminer() ? "determiner" : "first resource commit") + " enlistment attempt rd.getName():" + rd.getName());
         }

         if (this.getProperty("weblogic.jdbc.llr") != null) {
            if (rd.isFirstResourceCommit()) {
               throw new RollbackException("Can not enlist first resource commit " + rd.getName() + " as a LLR datasource is already enlisted Xid:" + this.getXid().toString());
            }

            if (this.isLocalCoordinatorTheCoordinatorOfThisTx()) {
               throw new RollbackException("Can not enlist determiner resource commit " + rd.getName() + " as a LLR datasource is already enlisted Xid:" + this.getXid().toString());
            }
         }

         if (rd.isDeterminer() && this.getTM().isInUnRegisteredDeterminerList(rd.getName())) {
            throw new RollbackException("Can not enlist determiner resource " + rd.getName() + " as it has been unregistered from this server.  The determiner resource must be deployed to this server." + this.getXid().toString());
         }
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "enlistResource:" + xar);
      }

      TxDebug.txdebug(TxDebug.JTA2PC, this, "enlistResource xar.getClass().getName():" + xar.getClass().getName());
      if (rd != null && rd.isDB2()) {
         if (!this.listDB2ResourcesEnlisted.isEmpty()) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "enlistResource:" + xar + " rd.isDB2() listDB2ResourcesEnlisted.size() (prior to this latest one):" + this.listDB2ResourcesEnlisted.size());
            }

            this.setProperty("weblogic.transaction.multipleDB2ResourceOfSameRMEnlisted", true);
         }

         this.listDB2ResourcesEnlisted.add(xar);
         TxDebug.txdebug(TxDebug.JTA2PC, this, "enlistResource:" + xar + " rd.isDB2() listDB2ResourcesEnlisted.size():" + this.listDB2ResourcesEnlisted.size());
      }

      if (TxDebug.JTA2PCDetail.isDebugEnabled()) {
         TxDebug.txdebugStack(TxDebug.JTA2PCDetail, this, "enlistResource:" + xar);
      }

      if (this.isCancelled()) {
         this.throwRollbackException();
      }

      if (!this.isActive()) {
         this.throwIllegalStateException("Cannot enlist resource, transaction not active. " + this.getXid().toString());
      }

      if (xar instanceof EmulatedTwoPhaseResource) {
         Object assignableOnlyToEnlistingSCs = this.getProperty("weblogic.transaction.assignableOnlyToEnlistingSCs");
         HashSet assignableOnlyToEnlistingSCsSet = assignableOnlyToEnlistingSCs == null ? new HashSet() : (HashSet)assignableOnlyToEnlistingSCs;
         if (rd != null && rd.getName() != null) {
            assignableOnlyToEnlistingSCsSet.add(rd.getName());
         }

         this.setProperty("weblogic.transaction.assignableOnlyToEnlistingSCs", assignableOnlyToEnlistingSCsSet);
      }

      boolean isNewResource = false;

      try {
         XAServerResourceInfo ri;
         if (resourceNameAlias == null) {
            ri = this.getResourceInfo(xar);
            if (ri == null) {
               ri = new XAServerResourceInfo(xar);
               this.checkNewResource(ri, ri.getName());
               isNewResource = true;
            }
         } else {
            ri = this.getResourceInfo(xar, resourceNameAlias);
            if (ri == null) {
               ri = new XAServerResourceInfo(xar, resourceNameAlias);
               this.checkNewResource(ri, resourceNameAlias);
               isNewResource = true;
               this.setProperty("weblogic.transaction.resource.alias." + resourceNameAlias, ri.getResourceDescriptor().getName());
            }
         }

         XAResourceDescriptor xrd;
         if (rd instanceof XAResourceDescriptor) {
            xrd = (XAResourceDescriptor)rd;
            if (xrd.getName().equals(ri.getName()) && ri.isReRegistered()) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "enlistResource call  " + xar + ",  re registered= " + ri.isReRegistered() + " Substitute the old  Resource info with the new Resource info");
               }

               ri.setXAResource(xar);
               ri.rd = xrd;
            }
         }

         this.assignCoordinatorIfNecessary();
         this.checkForAndSetDeterminerOrFirstResourceCommit(rd);
         if (ri.enlist(this)) {
            if (isNewResource) {
               this.addResourceInfo(ri);
               if (rd instanceof XAResourceDescriptor) {
                  xrd = (XAResourceDescriptor)rd;
                  if (xrd.isCoordinatedLocally() && !xrd.isCheckpointed()) {
                     xrd.setIsResourceCheckpointNeeded(true);
                  }
               }

               ServerSCInfo localsci = this.getOrCreateLocalSCInfo();
               ri.addSC(localsci);
            } else {
               ri.setXAResource(xar);
            }
         }

         return true;
      } catch (IllegalStateException var9) {
         throw var9;
      } catch (SystemException var10) {
         throw var10;
      } catch (Exception var11) {
         this.setRollbackOnly("Unexpected exception in resource.xaStart(). Resource=" + xar, var11);

         try {
            this.rollback();
         } catch (Exception var8) {
         }

         this.throwRollbackException();
         return false;
      }
   }

   void checkForAndSetDeterminerOrFirstResourceCommit(ResourceDescriptor rd) throws RollbackException {
      if (rd instanceof XAResourceDescriptor) {
         String firstResourceToCommit = this.getFirstResourceToCommit();
         if (rd.isFirstResourceCommit()) {
            if (firstResourceToCommit != null && !firstResourceToCommit.equals(rd.getName())) {
               throw new RollbackException("Can not enlist first commit resource" + rd.getName() + " as a firstResourceCommit resource " + firstResourceToCommit + " already enlisted Xid:" + this.getXid().toString());
            }

            if (this.getDeterminer() != null) {
               throw new RollbackException("Can not enlist first commit resource" + rd.getName() + " as determiner resource " + this.getDeterminer() + " already enlisted Xid:" + this.getXid().toString());
            }

            this.setProperty("weblogic.transaction.first.resource.commit", rd.getName());
         }

         if (this.isLocalCoordinatorTheCoordinatorOfThisTx()) {
            if (rd.isDeterminer() && this.getDeterminer() == null) {
               if (firstResourceToCommit != null) {
                  throw new RollbackException("Can not enlist determiner resource " + rd.getName() + " as a firstResourceCommit resource " + firstResourceToCommit + " already enlisted Xid:" + this.getXid().toString());
               }

               this.setProperty("weblogic.transaction.determiner", rd.getName());
            }

         }
      }
   }

   boolean isLocalCoordinatorTheCoordinatorOfThisTx() {
      CoordinatorDescriptor cd = this.getCoordinatorDescriptor();
      return cd != null && this.getTM() != null ? cd.equals(this.getTM().getLocalCoordinatorDescriptor()) : false;
   }

   public boolean delistResource(XAResource xar, int flag) throws IllegalStateException, SystemException {
      return this.doDelistResource(xar, flag, false);
   }

   boolean doDelistResource(XAResource xar, int flag, boolean eagerDelist) throws SystemException {
      if (xar instanceof IgnoreXAResource) {
         throw new RuntimeException("delisting IgnoreXAResource " + xar);
      } else {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "delistResource" + xar + ", flag=" + flag);
         }

         if (this.isCancelled()) {
            this.throwIllegalStateException("Cannot delist resource, transaction has been rolled back. " + this.getXid().toString());
         }

         if (!this.isActive()) {
            throw new IllegalStateException("Cannot delist resource when transaction state is " + this.getStatusAsString() + ".  " + this.getXid().toString());
         } else {
            XAServerResourceInfo ri = this.getResourceInfo(xar);
            if (ri == null) {
               throw new SystemException("Resource was never enlisted");
            } else {
               ri.setXAResource(xar);

               try {
                  if (!eagerDelist) {
                     return ri.delayedDelist(this, flag);
                  } else {
                     ri.delist(this, flag, false);
                     return true;
                  }
               } catch (AbortRequestedException var6) {
                  return false;
               }
            }
         }
      }
   }

   public boolean delistResourceWithProperties(XAResource xar, int flags, Map delistmentProperties) throws IllegalStateException, SystemException {
      return delistmentProperties != null && delistmentProperties.get("weblogic.transaction.eager.end.on.delist") != null && delistmentProperties.get("weblogic.transaction.eager.end.on.delist") instanceof Boolean && (Boolean)delistmentProperties.get("weblogic.transaction.eager.end.on.delist") ? this.doDelistResource(xar, flags, true) : this.doDelistResource(xar, flags, false);
   }

   public void registerSynchronization(Synchronization sync) throws RollbackException, IllegalStateException, SystemException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "registerSync:" + sync + " " + sync.hashCode());
      }

      synchronized(this) {
         if (this.isCancelledUnsync()) {
            this.throwRollbackException();
         }

         if (!this.isActive()) {
            this.throwIllegalStateException("Cannot register Synchronization object, transaction not active.  " + this.getXid().toString());
         }

         if (this.isPrePrepared()) {
            this.setPrePreparing();
         }
      }

      this.assignCoordinatorIfNecessary();
      this.getOrCreateLocalSCInfo().addSync(this, sync);
   }

   public int getStatus() {
      if (this.isMarkedRollback()) {
         return 1;
      } else {
         short st = (short)this.getState();
         switch (st) {
            case 1:
            case 2:
            case 3:
               return 0;
            case 4:
            case 5:
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
            default:
               TXLogger.logUnknownTxState(this.getState());
               return 5;
         }
      }
   }

   public final String getStatusAsString() {
      Throwable rbrt = this.getRollbackReason();
      String rbr = rbrt == null ? "Unknown" : rbrt.toString();
      switch (this.getStatus()) {
         case 0:
            if (this.isPrePreparing()) {
               return "Active (PrePreparing)";
            } else {
               if (this.isPrePrepared()) {
                  return "Active (PrePrepaed)";
               }

               return "Active";
            }
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
            return this.isLogging() ? "Logging" : "Preparing";
         case 8:
            return "Committing";
         case 9:
            return "Rolling Back. [Reason=" + rbr + "]";
      }
   }

   public boolean enlistResource(NonXAResource nxar) throws RollbackException, IllegalStateException, SystemException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "enlistResource:" + nxar);
      }

      if (TxDebug.JTA2PCDetail.isDebugEnabled()) {
         TxDebug.txdebugStack(TxDebug.JTA2PCDetail, this, "enlistResource:" + nxar);
      }

      if (this.isCancelled()) {
         this.throwRollbackException();
      }

      if (!this.isActive()) {
         this.throwIllegalStateException("Cannot enlist resource, transaction not active. " + this.getXid().toString());
      }

      if (nxar instanceof LoggingResource) {
         this.setUseLLR(true);
         if (this.getDeterminer() != null) {
            throw new RollbackException("Can not enlist LLR datasource as a determiner resource is already enlisted " + this.getXid().toString());
         }

         if (this.getFirstResourceToCommit() != null) {
            throw new RollbackException("Can not enlist LLR datasource as a firstResourceCommit resource is already enlisted " + this.getXid().toString());
         }
      }

      if (this.isImportedTransaction()) {
         throw new UnsupportedOperationException("Cannot enlist non-XA resource because transaction " + this.getXid().toString() + " is subordinate to a foreign coordinator.");
      } else {
         boolean isNewResource = false;

         try {
            NonXAServerResourceInfo ri = this.getResourceInfo(nxar);
            if (ri == null) {
               ri = new NonXAServerResourceInfo(nxar);
               if (this.getResourceInfo((String)ri.getName()) != null) {
                  ri.setEnlistedElsewhere();
               }

               ri.checkNewEnlistment();
               isNewResource = true;
            }

            this.assignCoordinatorIfNecessary();
            if (isNewResource) {
               if (this.getNonXAResource() != null) {
                  throw new SystemException("Cannot enlist more than one Non XA Resource.  Attempt to enlist '" + ri.getName() + "' when '" + this.getNonXAResource().getName() + "' is already enlisted.");
               }

               this.addResourceInfo(ri);
               ServerSCInfo localsci = this.getOrCreateLocalSCInfo();
               ri.addSC(localsci);
               this.setProperty("weblogic.transaction.nonXAResource", ri.getName());
               this.setNonXAResource(ri);
            }

            return true;
         } catch (IllegalStateException var6) {
            throw var6;
         } catch (Exception var7) {
            this.setRollbackOnly("Unexpected exception in Non XA Resource enlistment. Resource=" + nxar, var7);

            try {
               this.rollback();
            } catch (Exception var5) {
            }

            this.throwRollbackException();
            return false;
         }
      }
   }

   public void writeExternal(DataOutput out) throws IOException {
      LogDataOutput encoder = (LogDataOutput)out;
      encoder.writeNonNegativeInt(3);
      encoder.writeProperties(this.getProperties());
      encoder.writeByteArray(this.getXID().getGlobalTransactionId());
      encoder.writeLong(this.getBeginTimeMillis());
      ArrayList resList = this.getResourceInfoList();
      int resLength = resList == null ? 0 : resList.size();
      encoder.writeNonNegativeInt(resLength);

      for(int i = 0; i < resLength; ++i) {
         ServerResourceInfo ri = (ServerResourceInfo)resList.get(i);
         encoder.writeAbbrevString(ri.getName());
      }

      List scList = this.getSCInfoList();
      int scLength = scList == null ? 0 : scList.size();
      encoder.writeNonNegativeInt(scLength);

      for(int i = 0; i < scLength; ++i) {
         SCInfo sci = (SCInfo)scList.get(i);
         encoder.writeAbbrevString(sci.getScUrl(this));
      }

   }

   public void readExternal(DataInput in) throws IOException {
      LogDataInput decoder = (LogDataInput)in;
      ServerTransactionManagerImpl tm = null;

      try {
         tm = this.getTM();
      } catch (Exception var14) {
      }

      int version = decoder.readNonNegativeInt();
      switch (version) {
         case 1:
            throw new InvalidObjectException("transaction log record: version 1 (6.0 beta) no longer supported");
         case 2:
         case 3:
            Map props = decoder.readProperties();
            this.addProperties(props);
            byte[] gtrid = decoder.readByteArray();
            if (gtrid == null) {
               throw new InvalidObjectException("transaction log record: null gtrid");
            } else if (gtrid.length > 64) {
               throw new InvalidObjectException("transaction log record: bad gtrid length " + gtrid.length);
            } else {
               XidImpl xid = XidImpl.create(gtrid);
               int timeout = tm != null ? tm.getTransactionTimeout() : 30;
               this.init(xid, timeout, timeout);
               if (version == 2) {
                  this.setBeginTimeMillis((long)decoder.readNonNegativeInt() * 1000L);
               } else {
                  this.setBeginTimeMillis(decoder.readLong());
               }

               int resLength = decoder.readNonNegativeInt();

               int scLength;
               for(scLength = 0; scLength < resLength; ++scLength) {
                  String rname = decoder.readAbbrevString();
                  if (rname == null || rname.equals("")) {
                     throw new InvalidObjectException("transaction log record: missing resource name");
                  }

                  this.getOrCreateResourceInfo(rname);
               }

               scLength = decoder.readNonNegativeInt();

               for(int i = 0; i < scLength; ++i) {
                  String scUrl = decoder.readAbbrevString();
                  if (scUrl == null || scUrl.equals("")) {
                     throw new InvalidObjectException("transaction log record: missing server URL");
                  }

                  if (tm != null) {
                     if (PlatformHelper.getPlatformHelper().isSSLURL(scUrl)) {
                        this.setSSLEnabled(true);
                     }

                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, this, "readExternal() useSecureURL=" + this.useSecureURL, TxDebug.JTA2PCStackTrace.isDebugEnabled() ? new Exception("DEBUG") : null);
                     }

                     ServerSCInfo sci = tm.getLocalCoordinatorDescriptor().representsCoordinatorURL(scUrl) ? this.getOrCreateLocalSCInfo() : (ServerSCInfo)this.createSCInfo(scUrl);
                     this.addSCIfNew(sci);
                  } else {
                     SCInfo sci = new SCInfo(scUrl);
                     this.addSC(sci);
                  }
               }

               if (TxDebug.JTARecovery.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTARecovery, this, "readExternal from log: " + this.toString());
               }

               return;
            }
         default:
            throw new InvalidObjectException("transaction log record: unrecognized version number " + version);
      }
   }

   public void onDisk(TransactionLogger tlog) {
      synchronized(this) {
         if (!this.isCancelledUnsync()) {
            this.setPreparedUnsync();
         }

         this.notify();
      }
   }

   public void onError(TransactionLogger tlog) {
      this.setRollbackOnly(new SystemException("Transaction could not be logged"));
      synchronized(this) {
         this.notify();
      }
   }

   public void onRecovery(TransactionLogger tlog) {
      boolean migratedTx = false;
      this.setRecoveredTransaction();
      if (this.isImportedTransaction() && !this.isForeignOnePhase()) {
         this.setPrepared();
      } else {
         this.setCommitting();
      }

      CoordinatorDescriptor localCoordinatorDescriptor = this.getTM().getLocalCoordinatorDescriptor();
      boolean isMigratedFromAnotherSite = tlog != null && "~".equals(tlog.getMigratedCoordinatorURL());
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTARecovery, this, "onRecovery: " + this.toString() + "about to set to setCoordinatorDescriptor getLoggingResource():" + this.getLoggingResource() + "tlog:" + tlog + " after getLocalCoordinatorDescriptor :localCoordinatorDescriptor:" + localCoordinatorDescriptor + " isMigratedFromAnotherSite:" + isMigratedFromAnotherSite);
      }

      this.setCoordinatorDescriptor(localCoordinatorDescriptor, isMigratedFromAnotherSite);
      this.retry = true;
      this.wakeUpAfterSeconds(60);
      this.setOwnerTransactionManager(this.getTM());
      String migratedFrom;
      if (this.getLoggingResource() != null) {
         this.getTM().incrementLLRCurrentRecoveredTransactionCount();
         migratedFrom = (String)this.getProperty("weblogic.transaction.nonXAResource");
         ServerResourceInfo sri = (ServerResourceInfo)this.getResourceInfo((String)migratedFrom);
         sri.setCommitted();
      }

      ServerResourceInfo ri;
      if (tlog != null) {
         migratedFrom = tlog.getMigratedCoordinatorURL();
         if (migratedFrom != null) {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTAMigration, this, "onRecovery for tx migrated from: " + migratedFrom);
            }

            TransactionImpl txMapTransaction = (TransactionImpl)this.getTM().txMap.get(this.getXID());
            if (txMapTransaction != null) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTAMigration, this, " onRecovery for tx migrated from: " + migratedFrom + " Transaction already in the TxMap, need to substitute  it");
               }

               this.setLocalProperty("weblogic.transaction.migrated.subordinateTransaction", txMapTransaction);
               migratedTx = true;
            }

            this.migratedTxLogger = tlog;
            SCInfo scinfo = this.getSCInfo(migratedFrom);
            ri = null;
            if (scinfo != null) {
               if (txMapTransaction != null) {
                  this.getAndSubstituteSCInfo(txMapTransaction.getSCInfoList());
                  this.localSCInfo = (ServerSCInfo)this.getSCInfo(this.getTM().getLocalCoordinatorURL());
               }

               this.getAndRemoveSCInfo(scinfo);
            } else if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTAMigration, this, "Cannot find SCInfo for: " + migratedFrom);
            }

            this.getOrCreateLocalSCInfo();
         }
      }

      if (isaadrprototype) {
         ArrayList resourceList = this.getResourceInfoList();
         if (resourceList != null) {
            String[] rmNames = new String[resourceList.size()];

            for(int i = 0; i < resourceList.size(); ++i) {
               ri = (ServerResourceInfo)resourceList.get(i);
               if (ri instanceof XAServerResourceInfo) {
                  String resName = ri.getName();
                  if (isaadrprototype) {
                     System.out.println("ServerTransactionImpl.onRecovery resName:" + resName);
                     resName = resName.substring(0, resName.indexOf("_"));
                     System.out.println("ServerTransactionImpl.onRecovery resName:" + resName);
                     resName = resName + "_" + PlatformHelper.getPlatformHelper().getDomainName();
                     System.out.println("ServerTransactionImpl.onRecovery resName:" + resName);
                     ri.setName(resName);
                  }

                  rmNames[i] = resName;
               }
            }

            this.assignResourcesToSelf(rmNames);
         }
      }

      this.getTM().add(this, migratedTx);
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTARecovery, this, "onRecovery: " + this.toString());
      }

   }

   Map getResourceNamesAndState() {
      if (this.getNumResources() == 0) {
         return new HashMap();
      } else {
         Map resNameAndStatus = new HashMap(this.getNumResources());
         ArrayList resList = this.getResourceInfoList();

         for(int i = 0; i < resList.size(); ++i) {
            ServerResourceInfo ri = (ServerResourceInfo)resList.get(i);
            resNameAndStatus.put(ri.getName(), ri.getStateAsString());
         }

         return resNameAndStatus;
      }
   }

   Map getServersAndState() {
      List scList = this.getSCInfoList();
      if (scList == null) {
         return new HashMap();
      } else {
         int numSCs = scList.size();
         Map serversAndStatus = new HashMap(scList.size());

         for(int i = 0; i < numSCs; ++i) {
            ServerSCInfo sci = (ServerSCInfo)scList.get(i);
            serversAndStatus.put(sci.getServerID(), sci.getStateAsString());
         }

         return serversAndStatus;
      }
   }

   void ackPrePrepare() {
      synchronized(this) {
         if (this.isPrePreparing()) {
            if (!this.allSCsPrePrepared()) {
               this.setRollbackOnlyUnsync(new SystemException("Internal error: Expected all SCs to be preprepared upon ack. Rolling back transaction"));
            } else {
               this.setPrePreparedUnsync();
            }

            this.notify();
         }

      }
   }

   void ackPrepare(String scURL, int vote) {
      if (!this.isCancelled()) {
         ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
         sci.setPrepared(vote);
         synchronized(this) {
            if (!this.getTM().getParallelXAEnabled()) {
               if (this.isCancelledUnsync() || this.isPreparing()) {
                  this.notify();
               }
            } else if (this.isCancelledUnsync() || this.isPreparing() && this.allSCsPrepared()) {
               this.notify();
            }

         }
      }
   }

   void ackCommit(String scURL) {
      this.addackOrNakCommitDebugMessage("\nackCommit scURL = [" + scURL + "]", scURL);
      ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
      sci.setCommitted();
      synchronized(this) {
         if (!this.isCancelledUnsync()) {
            if (this.isCommitting() && this.allSCsCommitted() && this.allResourcesDone()) {
               this.setCommittedUnsync();
               this.notify();
            }

         }
      }
   }

   void ackCommit(String scURL, String[] committedResources) {
      this.addackOrNakCommitDebugMessage("\n ackCommit scURL = [" + scURL + "], committedResources = [" + Arrays.toString(committedResources) + "]", scURL);
      ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
      if (committedResources != null) {
         for(int i = 0; i < committedResources.length; ++i) {
            ServerResourceInfo sri = (ServerResourceInfo)this.getResourceInfo((String)committedResources[i]);
            sri.setCommitted();
         }
      }

      if (sci != null) {
         sci.setCommitted();
      }

      this.addResourceCompletionState((short)4, scURL);
      synchronized(this) {
         if (!this.isCancelledUnsync()) {
            if (this.isCommitting() && this.allSCsCommitted() && this.allResourcesDone()) {
               this.setCommittedUnsync();
               this.notify();
            }

         }
      }
   }

   void nakCommit(String scURL, short completionState, String heuristicErrorMsg) {
      if (this.ackOrNakCommitMap != null && this.ackOrNakCommitMap.get(scURL) != null) {
         this.addackOrNakCommitDebugMessage("\n nakCommit scURL = [" + scURL + "], completionState = [" + completionState + "], heuristicErrorMsg = [" + heuristicErrorMsg + "]", scURL);
         synchronized(this) {
            if (this.allSCsCommitted() && this.allResourcesDone()) {
               this.setCommittedUnsync();
            }

            this.notify();
         }
      } else {
         ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
         sci.setCommitted();
         this.addResourceCompletionState(completionState, scURL);
         if (heuristicErrorMsg != null) {
            this.addHeuristicErrorMessage(heuristicErrorMsg);
         }

         synchronized(this) {
            if (this.allSCsCommitted() && this.allResourcesDone()) {
               this.setCommittedUnsync();
            }

            this.notify();
         }
      }
   }

   void nakCommit(String scURL, short completionState, String heuristicErrorMsg, String[] committedResources, String[] rolledbackResources) {
      if (this.ackOrNakCommitMap != null && this.ackOrNakCommitMap.get(scURL) != null) {
         this.addackOrNakCommitDebugMessage("\n nakCommit scURL = [" + scURL + "], completionState = [" + completionState + "], heuristicErrorMsg = [" + heuristicErrorMsg + "], committedResources = [" + Arrays.toString(committedResources) + "], rolledbackResources = [" + Arrays.toString(rolledbackResources) + "]", scURL);
      } else {
         ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
         int i;
         if (completionState == 16) {
            i = scURL.indexOf("~");
            TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit scURL:" + scURL + "scURLIndexForCrossDomainTxJMSMigration:" + i);
            String committedResourcesString;
            if (i > -1) {
               committedResourcesString = scURL.substring(i + 1, scURL.length());
               scURL = scURL.substring(0, i);
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit scURL after sub:" + scURL + " newSCURL:" + committedResourcesString);
               SCInfo addThis = this.getOrCreateSCInfo(committedResourcesString);
               List scInfoList = this.getSCInfoList();
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit remove old:" + scURL);
               scInfoList.add(addThis);
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit getAndSubstituteSCInfo add new:" + addThis);
               this.getAndSubstituteSCInfo(scInfoList);
            }

            this.setResourceNotFoundTrue();
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               committedResourcesString = "";
               if (committedResources != null) {
                  String[] var16 = committedResources;
                  int var18 = committedResources.length;

                  for(int var11 = 0; var11 < var18; ++var11) {
                     String committedResource = var16[var11];
                     committedResourcesString = committedResourcesString + committedResource + " ";
                  }
               }

               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit completionState==Constants.C_NOXARESOURCE");
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit committed resources:" + committedResourcesString);
               int i;
               if (committedResources != null) {
                  for(i = 0; i < committedResources.length; ++i) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit  committedResources[" + i + "]:" + committedResources[i]);
                  }
               }

               if (rolledbackResources != null) {
                  for(i = 0; i < rolledbackResources.length; ++i) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit  uncommitted resources[" + i + "]:" + rolledbackResources[i]);
                  }
               }
            }

         } else {
            sci.setCommitted();
            ServerResourceInfo sri;
            if (rolledbackResources != null) {
               for(i = 0; i < rolledbackResources.length; ++i) {
                  sri = (ServerResourceInfo)this.getResourceInfo((String)rolledbackResources[i]);
                  sri.setRolledBack();
               }
            }

            if (committedResources != null) {
               for(i = 0; i < committedResources.length; ++i) {
                  sri = (ServerResourceInfo)this.getResourceInfo((String)committedResources[i]);
                  sri.setCommitted();
               }
            }

            this.addResourceCompletionState(completionState, scURL);
            if (heuristicErrorMsg != null) {
               this.addHeuristicErrorMessage(heuristicErrorMsg);
            }

            synchronized(this) {
               if (this.allSCsCommitted() && this.allResourcesDone()) {
                  this.setCommittedUnsync();
               }

               this.notify();
            }
         }
      }
   }

   void ackRollback(String scURL) {
      ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
      sci.setRolledBack();
      synchronized(this) {
         if (this.allSCsRolledBack() && this.allResourcesDone()) {
            this.setRolledBack();
            this.notify();
         }

      }
   }

   void ackRollback(String scURL, String[] rolledbackResources) {
      ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
      if (rolledbackResources != null) {
         for(int i = 0; i < rolledbackResources.length; ++i) {
            ServerResourceInfo sri = (ServerResourceInfo)this.getResourceInfo((String)rolledbackResources[i]);
            if (sri != null) {
               sri.setRolledBack();
            }
         }
      }

      sci.setRolledBack();
      this.addResourceCompletionState((short)8, scURL);
      synchronized(this) {
         if (this.allSCsRolledBack() && this.allResourcesDone()) {
            this.setRolledBack();
            this.notify();
         }

      }
   }

   void nakRollback(String scURL, short completionState, String heuristicErrorMsg) {
      ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
      sci.setRolledBack();
      this.addResourceCompletionState(completionState, scURL);
      if (heuristicErrorMsg != null) {
         this.addHeuristicErrorMessage(heuristicErrorMsg);
      }

      this.setRolledBack();
      synchronized(this) {
         this.notify();
      }
   }

   void nakRollback(String scURL, short completionState, String heuristicErrorMsg, String[] committedResources, String[] rolledbackResources) {
      int i;
      if (completionState == 16) {
         this.setResourceNotFoundTrue();
         TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakRollback completionState==Constants.C_NOXARESOURCE");
         if (rolledbackResources != null) {
            for(i = 0; i < rolledbackResources.length; ++i) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakRollback  rolledback resources[" + i + "]:" + rolledbackResources[i]);
            }
         }

         if (committedResources != null) {
            for(i = 0; i < committedResources.length; ++i) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakCommit un rolled back resources:" + committedResources[i]);
            }
         }

         if (committedResources != null) {
            for(i = 0; i < committedResources.length; ++i) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.nakRollback  resources[" + i + "]:" + committedResources[i]);
            }
         }

      } else {
         this.addResourceCompletionState(completionState, scURL);
         if (heuristicErrorMsg != null) {
            this.addHeuristicErrorMessage(heuristicErrorMsg);
         }

         ServerResourceInfo sri;
         if (rolledbackResources != null) {
            for(i = 0; i < rolledbackResources.length; ++i) {
               sri = (ServerResourceInfo)this.getResourceInfo((String)rolledbackResources[i]);
               sri.setRolledBack();
            }
         }

         if (committedResources != null) {
            for(i = 0; i < committedResources.length; ++i) {
               sri = (ServerResourceInfo)this.getResourceInfo((String)committedResources[i]);
               sri.setCommitted();
            }
         }

         ServerSCInfo sci = (ServerSCInfo)this.getSCInfo(scURL);
         sci.setRolledBack();
         this.setRolledBack();
         synchronized(this) {
            this.notify();
         }
      }
   }

   boolean localCommit(String[] rmNames, boolean onePhase, boolean retry, Object callerId) {
      this.setCommitting();
      if (callerId != null && this.completionId == null) {
         this.completionId = callerId;
      }

      if (rmNames != null && rmNames.length != 0) {
         this.assignResourcesToSelf(rmNames);

         try {
            this.localCommit(onePhase, retry);
         } catch (AbortRequestedException var6) {
         }

         ServerSCInfo localSCI = this.getLocalSCInfo();
         return localSCI.isCommitted();
      } else {
         this.setCommitted();
         return true;
      }
   }

   final void localPrePrepareAndChain() throws AbortRequestedException {
      synchronized(this) {
         if (this.isCancelledUnsync()) {
            return;
         }

         if (this.isActive()) {
            this.setPrePreparing();
         } else {
            this.abortUnsync("Illegal State. (Expected: active).  " + this);
         }

         if (this.getSCInfoList() == null) {
            this.setPrePreparedUnsync();
            return;
         }
      }

      ServerSCInfo localSCI = this.getLocalSCInfo();
      ServerSCInfo sci = null;
      if (localSCI == null) {
         List scList = this.getSCInfoList();
         if (scList == null) {
            this.setPrePrepared();
            return;
         }

         sci = (ServerSCInfo)scList.get(0);
      } else {
         sci = localSCI;
      }

      sci.startPrePrepareAndChain(this, this.getTM().getBeforeCompletionIterationLimit());
      if (!this.isCoordinatingTransaction()) {
         this.setPrePrepared();
      }

   }

   final void localPrepare(String[] rmNames) {
      this.assignResourcesToSelf(rmNames);

      try {
         this.localPrepare();
      } catch (AbortRequestedException var3) {
      }

   }

   final boolean localRollback(String[] rmNames, Object callerId) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         StringBuffer sb = new StringBuffer(50);
         int len = rmNames == null ? 0 : rmNames.length;

         for(int i = 0; i < len; ++i) {
            sb.append(" " + rmNames[i]);
         }

         TxDebug.txdebug(TxDebug.JTA2PC, this, "localRollback: isResourceNotFound():" + this.isResourceNotFound() + " isAllResourcesAssigned:" + this.isAllResourcesAssigned() + "  resources:" + sb.toString());
      }

      if (this.isOver()) {
         return true;
      } else {
         this.setRollingBack();
         if (callerId != null && this.completionId == null) {
            this.completionId = callerId;
         }

         ServerSCInfo localSCI;
         if (!this.isCoordinatingTransaction() && this.isResourceNotFound() && this.isAllResourcesAssigned() && this.rmRolledbackCheck(rmNames)) {
            localSCI = this.getLocalSCInfo();
            localSCI.setRolledBack();
            this.setRolledBack();
         } else {
            this.assignResourcesToSelf(rmNames);
            this.localRollback();
         }

         localSCI = this.getLocalSCInfo();
         return localSCI.isRolledBack();
      }
   }

   public boolean rmRolledbackCheck(String[] rmNames) {
      if (rmNames == null) {
         return true;
      } else if (rmNames.length == 0) {
         return true;
      } else {
         ArrayList resourceList = this.getResourceInfoList();
         if (resourceList == null) {
            return false;
         } else {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "localRollback: rmRolledbackCheck " + this);
            }

            label46:
            for(int i = 0; i < rmNames.length; ++i) {
               String aRm = rmNames[i];

               for(int j = 0; j < resourceList.size(); ++j) {
                  ServerResourceInfo sRi = (ServerResourceInfo)resourceList.get(j);
                  if (aRm != null && aRm.equals(sRi.getName())) {
                     if (!sRi.isRolledBack()) {
                        return false;
                     }
                     continue label46;
                  }
               }

               return false;
            }

            return true;
         }
      }
   }

   final void forceLocalRollback() throws SystemException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "forceLocalRollback:");
      }

      synchronized(this) {
         switch (this.getState()) {
            case 7:
            case 8:
               TXLogger.logForceLocalRollbackInvalidState(this.getXid().toString(), this.toString());
               throw new SystemException("Unable to force a local rollback, tx state is " + this.getStateAsString(this.getState()));
         }

         this.setRollingBackUnsync();
      }

      this.assignLocalResourcesToSelf();
      this.getLocalSCInfo().forceLocalRollback(this, this.isOnePhaseCommitPossible());
      this.forceSetRolledBack();
   }

   final void forceGlobalRollback() throws SystemException, RemoteException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "forceLocalRollback:");
      }

      if (!this.isCoordinatingTransaction()) {
         CoordinatorOneway coo = this.getCoordinator();
         if (coo != null) {
            if (coo instanceof Coordinator3) {
               Coordinator3 co3 = (Coordinator3)coo;

               try {
                  PlatformHelper.getPlatformHelper().runAction(new ForceGlobalRollbackAction(co3, this.getXid()), this.getCoServerURL(), "co.forceGlobalRollback");
                  return;
               } catch (Exception var5) {
                  TXLogger.logForceGlobalRollbackCoordinatorError(this.getXid().toString(), this.getCoordinatorURL(), var5);
                  throw new SystemException("Error contacting coordinating server '" + this.getCoordinatorURL() + "' for forceGlobalRollback: " + var5);
               }
            }

            TXLogger.logForceGlobalRollbackCoordinatorVersion(this.getXid().toString());
            throw new SystemException("Coordinating server does not support manual transaction resolution.");
         }
      }

      synchronized(this) {
         switch (this.getState()) {
            case 7:
            case 8:
               TXLogger.logForceGlobalRollbackInvalidState(this.getXid().toString(), this.toString());
               throw new SystemException("Unable to force a global rollback, tx state is " + this.getStateAsString(this.getState()));
            default:
               this.setRollingBackUnsync();
         }
      }

      ServerSCInfo localSCI = this.getLocalSCInfo();
      List sciList = this.getSCInfoList();

      for(int i = 0; i < sciList.size(); ++i) {
         ServerSCInfo sci = (ServerSCInfo)sciList.get(i);
         if (sci != localSCI) {
            sci.forceLocalRollback(this, this.isOnePhaseCommitPossible());
         }
      }

      this.assignLocalResourcesToSelf();
      localSCI.forceLocalRollback(this, this.isOnePhaseCommitPossible());
      this.setRolledBack();
   }

   final void forceLocalCommit() throws SystemException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "forceLocalCommit:");
      }

      synchronized(this) {
         this.validateForceCommitState("force local commit");
         this.setCommitting();
      }

      this.assignLocalResourcesToSelf();
      this.getLocalSCInfo().forceLocalCommit(this);
      this.forceSetCommitted();
   }

   final void forceGlobalCommit() throws SystemException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "forceGlobalCommit:");
      }

      if (!this.isCoordinatingTransaction()) {
         CoordinatorOneway coo = this.getCoordinator();
         if (coo != null) {
            if (coo instanceof Coordinator3) {
               Coordinator3 co3 = (Coordinator3)coo;

               try {
                  PlatformHelper.getPlatformHelper().runAction(new ForceGlobalCommitAction(co3, this.getXid()), this.getCoServerURL(), "co.forceGlobalCommit");
                  return;
               } catch (Exception var5) {
                  TXLogger.logForceGlobalCommitCoordinatorError(this.getXid().toString(), this.getCoordinatorURL(), var5);
                  throw new SystemException("Error contacting coordinating server '" + this.getCoordinatorURL() + "' during forceGlobalCommit: " + var5);
               }
            }

            TXLogger.logForceGlobalRollbackCoordinatorVersion(this.getXid().toString());
            throw new SystemException("Coordinating server does not support manual transaction resolution.");
         }
      }

      synchronized(this) {
         this.validateForceCommitState("force global commit");
         this.setCommitting();
      }

      ServerSCInfo localSCI = this.getLocalSCInfo();
      List sciList = this.getSCInfoList();

      for(int i = 0; i < sciList.size(); ++i) {
         ServerSCInfo sci = (ServerSCInfo)sciList.get(i);
         if (sci != localSCI) {
            sci.forceLocalCommit(this);
         }
      }

      this.assignLocalResourcesToSelf();
      localSCI.forceLocalCommit(this);
      this.forceSetCommitted();
   }

   SystemException enlistNonThreadAffinityStaticResources(boolean enlistOOAlso) {
      if (!this.isActive()) {
         return null;
      } else {
         ArrayList resourceList = this.getResourceInfoList();
         if (resourceList == null) {
            return null;
         } else {
            this.assignCoordinatorIfNecessary();

            for(int i = 0; i < resourceList.size(); ++i) {
               ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);

               try {
                  if (!ri.enlistIfStaticAndNoThreadAffinityNeeded(this, enlistOOAlso)) {
                     String msg = " Unable to enlist resource '" + ri.getName() + "'";
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, this, msg);
                     }

                     SystemException se = new SystemException(msg);
                     this.setRollbackOnly(se);
                     return se;
                  }
               } catch (AbortRequestedException var8) {
                  String msg = "Unable to enlist resource '" + ri.getName() + "'";
                  Throwable rb = this.getRollbackReason();
                  if (rb != null) {
                     msg = msg + " " + rb.toString();
                  }

                  return new SystemException(msg);
               }
            }

            return null;
         }
      }
   }

   SystemException enlistStaticallyEnlistedResources(boolean enlistOOAlso) {
      if (!this.isActive()) {
         return null;
      } else {
         ArrayList resourceList = this.getResourceInfoList();
         if (resourceList == null) {
            return null;
         } else {
            this.assignCoordinatorIfNecessary();

            for(int i = 0; i < resourceList.size(); ++i) {
               ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);

               try {
                  if (!ri.enlistIfStatic(this, enlistOOAlso)) {
                     String msg = "Unable to enlist resource '" + ri.getName() + "'";
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, this, msg);
                     }

                     SystemException se = new SystemException(msg);
                     this.setRollbackOnly(se);
                     return se;
                  }
               } catch (AbortRequestedException var8) {
                  String msg = "Unable to enlist resource '" + ri.getName() + "'";
                  Throwable rb = this.getRollbackReason();
                  if (rb != null) {
                     msg = msg + " " + rb.toString();
                  }

                  return new SystemException(msg);
               }
            }

            return null;
         }
      }
   }

   SystemException enlistThreadAffinityResources(boolean enlistOOAlso) {
      if (!this.isActive()) {
         return null;
      } else {
         ArrayList resourceList = this.getResourceInfoList();
         if (resourceList == null) {
            return null;
         } else {
            this.assignCoordinatorIfNecessary();

            for(int i = 0; i < resourceList.size(); ++i) {
               ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);

               try {
                  if (!ri.enlistIfNeedThreadAffinity(this, enlistOOAlso)) {
                     String msg = "Unable to enlist THREAD_AFFINITY resource '" + ri.getName() + "'";
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, this, msg);
                     }

                     SystemException se = new SystemException(msg);
                     this.setRollbackOnly(se);
                     return se;
                  }
               } catch (AbortRequestedException var8) {
                  String msg = "Unable to enlist THREAD_AFFINITY resource '" + ri.getName() + "'";
                  Throwable rb = this.getRollbackReason();
                  if (rb != null) {
                     msg = msg + " " + rb.toString();
                  }

                  return new SystemException(msg);
               }
            }

            return null;
         }
      }
   }

   void delistAll(int flag) throws AbortRequestedException {
      this.delistAll(flag, false);
   }

   void delistAll(int flag, boolean forceDelist) throws AbortRequestedException {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList != null) {
         Iterator var4 = resourceList.iterator();

         while(var4.hasNext()) {
            Object aResourceList = var4.next();

            try {
               ServerResourceInfo ri = (ServerResourceInfo)aResourceList;
               if (ri != null) {
                  ri.delist(this, flag, forceDelist);
               }
            } catch (AbortRequestedException var7) {
               if (!forceDelist) {
                  throw var7;
               }
            }
         }

      }
   }

   void delistAllStaticResources(int flag, boolean forceDelist) throws AbortRequestedException {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList != null) {
         Iterator var4 = resourceList.iterator();

         while(var4.hasNext()) {
            Object aResourceList = var4.next();

            try {
               ServerResourceInfo ri = (ServerResourceInfo)aResourceList;
               ri.delistIfStatic(this, flag, forceDelist);
            } catch (AbortRequestedException var7) {
               if (!forceDelist) {
                  throw var7;
               }
            }
         }

      }
   }

   void delistAllThreadAffinityResources(int flag, boolean forceDelist) throws AbortRequestedException {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList != null) {
         for(int i = 0; i < resourceList.size(); ++i) {
            try {
               ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);
               ri.delistIfThreadAffinity(this, flag, forceDelist);
            } catch (AbortRequestedException var6) {
               if (!forceDelist) {
                  throw var6;
               }
            }
         }

      }
   }

   public PropagationContext getRequestPropagationContext() {
      try {
         this.delistAll(33554432);
      } catch (AbortRequestedException var2) {
      }

      return new PropagationContext(this);
   }

   public PropagationContext getResponsePropagationContext() {
      try {
         this.delistAll(33554432);
      } catch (AbortRequestedException var2) {
      }

      return new PropagationContext(this);
   }

   public void wakeUp(int nowSec) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("wakeUp runing: " + this);
      }

      try {
         if (this.isAbandoned(nowSec)) {
            return;
         }

         int timeoutSec = this.getNormalizedTimeoutSeconds();
         this.wakeUpAfterSeconds(timeoutSec);
         if (this.isMarkedRollback()) {
            this.asyncRollback();
            return;
         }

         switch (this.getState()) {
            case 1:
            case 2:
               synchronized(this) {
                  int st = this.getState();
                  if (st == 1 || st == 2) {
                     this.setRollbackOnlyUnsync(new TimedOutException(this));
                     this.setTxAsyncTimeout(true);
                  }
               }

               this.wakeUpAfterSeconds(10);
               break;
            case 3:
               if (!this.isCoordinatingTransaction() && !this.isRetry()) {
                  if (!this.isCoordinatingTransaction()) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, this, "Pre-prepared & waiting");
                     }

                     this.setRetry();
                  }
               } else {
                  this.setRollbackOnly(new TimedOutException("Timed out tx=" + this.getXID() + " waiting for pre-prepared call(exceeds PRE_PREPARED_WAIT_SECONDS=" + m_pre_prepared_wait_seconds + "), the transaction is in Pre-prepare"));
                  this.setTxAsyncTimeout(true);
               }
               break;
            case 4:
               synchronized(this) {
                  if (this.getState() == 4) {
                     this.setRollbackOnlyUnsync(new TimedOutException("Timed out tx=" + this.getXID() + " after " + this.getTimeoutSeconds() + " seconds"));
                     this.setTxAsyncTimeout(true);
                  }
                  break;
               }
            case 5:
               if (this.isRetry()) {
                  this.setRollbackOnly(new SystemException("Timed out while in 'Logging' state"));
                  this.setTxAsyncTimeout(true);
                  this.asyncRollback();
               } else {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "Logging, waiting");
                  }

                  this.setRetry();
                  this.wakeUpAfterSeconds(30);
               }
               break;
            case 6:
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "Prepared, waiting");
               }

               if (this.getLoggingResourceCommitFailure() != null) {
                  this.wakeUpAfterSeconds(60);
               } else {
                  this.wakeUpAfterSeconds(120);
               }
               break;
            case 7:
               if (this.isCoordinatingTransaction()) {
                  this.asyncRetryCommit();
               } else if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "Committing & waiting. Not coordinating tx");
               }

               this.wakeUpAfterSeconds(60);
            case 8:
               break;
            case 9:
               if (this.isCoordinatingTransaction()) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "Rolling back & retrying because coordinating tx");
                  }

                  this.asyncRetryRollback();
               } else if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "Rolling back & waiting. Not coordinating tx");
               }

               this.wakeUpAfterSeconds(60);
               break;
            case 10:
               if (!this.isImportedTransaction() || !this.isCoordinatingTransaction() || !this.hasHeuristics()) {
                  this.removeSelf();
               }
               break;
            case 11:
               this.removeSelf();
               break;
            default:
               String msg = "Forcibly aborting transaction found in an unexpected internal state" + PlatformHelper.getPlatformHelper().getEOLConstant() + " Details=" + this.toString();
               TxDebug.txdebug(TxDebug.JTA2PC, this, msg);
               this.setRolledBack();
               this.removeSelf();
         }
      } catch (Exception var9) {
         TXLogger.logUnexpectedTimerException(var9);
      }

   }

   String[] getCommittedResources() {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList == null) {
         return null;
      } else {
         int rsize = resourceList.size();
         int count = 0;

         for(int i = 0; i < rsize; ++i) {
            ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
            if (sri.isCommitted()) {
               ++count;
            }
         }

         if (count == 0) {
            return null;
         } else {
            String[] committedResources = new String[count];

            for(int i = 0; i < rsize; ++i) {
               ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
               if (sri.isCommitted()) {
                  --count;
                  committedResources[count] = sri.getName();
               }
            }

            return committedResources;
         }
      }
   }

   String[] getRolledbackResources() {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList == null) {
         return null;
      } else {
         int rsize = resourceList.size();
         int count = 0;

         for(int i = 0; i < rsize; ++i) {
            ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
            if (sri.isRolledBack()) {
               ++count;
            }
         }

         if (count == 0) {
            return null;
         } else {
            String[] rolledBackResources = new String[count];

            for(int i = 0; i < rsize; ++i) {
               ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
               if (sri.isRolledBack()) {
                  --count;
                  rolledBackResources[count] = sri.getName();
               }
            }

            return rolledBackResources;
         }
      }
   }

   public int getHeuristicErrorCode() {
      if (this.heuristicErrorCode == -1) {
         short state = (short)this.getState();
         short hstatus = false;
         short hstatus;
         if (state == 8) {
            hstatus = this.getHeuristicStatus(4);
            switch (hstatus) {
               case 0:
                  this.heuristicErrorCode = 0;
                  break;
               case 1:
                  this.heuristicErrorCode = 5;
                  break;
               case 2:
                  this.heuristicErrorCode = 8;
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               default:
                  break;
               case 8:
                  if (this.isForeignOnePhase()) {
                     this.heuristicErrorCode = 0;
                  } else {
                     this.heuristicErrorCode = 6;
                  }
            }
         } else if (state == 10) {
            hstatus = this.getHeuristicStatus(8);
            switch (hstatus) {
               case 0:
                  this.heuristicErrorCode = 0;
                  break;
               case 1:
                  this.heuristicErrorCode = 5;
                  break;
               case 2:
                  this.heuristicErrorCode = 8;
               case 3:
               default:
                  break;
               case 4:
                  this.heuristicErrorCode = 7;
            }
         }
      }

      return this.heuristicErrorCode;
   }

   void setHeuristicErrorCode(int errCode) {
      this.heuristicErrorCode = errCode;
   }

   public boolean hasHeuristics() {
      this.getHeuristicErrorCode();
      return super.hasHeuristics();
   }

   private void logHeuristics() {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug("ServerTransactionImpl.logHeuristics " + this);
      }

      this.heuristicsLog = new HeuristicsLog(this);
      boolean onDisk = this.heuristicsLog.store();
      if (!onDisk && TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this.heuristicsLog.toString());
      }

   }

   void setHeuristicsLog(HeuristicsLog heuristicsLog) {
      this.heuristicsLog = heuristicsLog;
   }

   protected void log() throws AbortRequestedException {
      synchronized(this) {
         if (this.isCancelledUnsync()) {
            return;
         }

         if (!this.isPreparing()) {
            this.abortUnsync("Illegal state(expected Preparing): " + this.getXid().toString());
         }

         if (this.getTM().getDBPassiveModeState() == 1 || this.getTM().getDBPassiveModeState() == 2) {
            this.abortUnsync("Transaction Service is in PASSIVE mode");
         }

         this.setLoggingUnsync();
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "about to store in log");
      }

      this.getTM().getTransactionLogger().store(this);
      synchronized(this) {
         while(!this.isCancelledUnsync() && !this.isPrepared()) {
            try {
               this.wait(1000L);
            } catch (InterruptedException var4) {
            }
         }
      }

      if (this.isCancelled()) {
         this.abort();
      }

   }

   private void localCommit(boolean onePhase, boolean retry) throws AbortRequestedException {
      ServerSCInfo localSCI = this.getLocalSCInfo();

      try {
         if (localSCI != null) {
            localSCI.startCommit(this, onePhase, retry);
         }
      } finally {
         if (!this.isCoordinatingTransaction()) {
            if (localSCI.isCommitted()) {
               this.setCommitted();
            } else if (localSCI.isRolledBack()) {
               this.setRolledBack();
            }
         }

      }

   }

   protected void localRollback() {
      this.check("TMBeforeRollback");

      try {
         this.delistAll(536870912, true);
      } catch (AbortRequestedException var5) {
      }

      ServerSCInfo localSCI = this.getLocalSCInfo();

      try {
         if (localSCI != null) {
            localSCI.startRollback(this, this.isOnePhaseCommitPossible());
         }
      } finally {
         if (!this.isCoordinatingTransaction()) {
            if (localSCI.isRolledBack()) {
               this.setRolledBack();
            } else if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "localRollback() did not finish");
            }
         }

      }

   }

   int getBeforeCompletionIterationCount() {
      return this.beforeCompletionIterationCount;
   }

   void finishedBeforeCompletionIteration() {
      ++this.beforeCompletionIterationCount;
   }

   public int internalPrepare() throws AbortRequestedException, RollbackException, SystemException, XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAGateway, this, "internalPrepare");
      }

      this.globalPrePrepare();
      return this.globalPrepare();
   }

   public void internalCommit(boolean onePhase) throws AbortRequestedException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, SystemException, IllegalStateException, XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAGateway, this, "internalCommit(onePhase=" + onePhase + ")");
      }

      this.setForeignOnePhase(onePhase);
      this.internalCommit();
   }

   public void internalForget() throws SystemException, XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAGateway, this, "internalForget");
      }

      this.checkOwner();
      this.removeSelf();
      if (this.heuristicsLog != null) {
         if (TxDebug.JTAGateway.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTAGateway, this, "releases Heuristics Log");
         }

         this.getTM().getHeuristicLogger().release(this.heuristicsLog);
      }

      this.heuristicsLog = null;
   }

   synchronized boolean setCoordinatorURL(String url) {
      CoordinatorDescriptor cd = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getOrCreate(url);
      this.updateCoordinatorDescriptor(url);
      return this.setCoordinatorDescriptor(cd);
   }

   boolean setCoordinatorDescriptor(Object id, ChannelInterface channel) {
      if (isLocalCoordinatorAssignment) {
         this.assignCoordinatorIfNecessary();
      }

      return super.setCoordinatorDescriptor(id, channel);
   }

   private void globalPrePrepare() throws AbortRequestedException {
      this.localPrePrepareAndChain();
      synchronized(this) {
         while(!this.isCancelledUnsync() && !this.allSCsPrePrepared()) {
            try {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  this.traceScState("waitForPrePrepareAcks");
               }

               this.wait(Math.max(0L, this.getTimeToLiveMillis()));
            } catch (InterruptedException var4) {
            }
         }

         if (!this.isCancelledUnsync()) {
            this.setPrePreparedUnsync();
         }
      }

      if (this.isCancelled()) {
         this.abort();
      }

   }

   boolean allSCsPrePrepared() {
      List scList = this.getSCInfoList();
      if (scList == null) {
         return true;
      } else {
         for(int i = 0; i < scList.size(); ++i) {
            ServerSCInfo sci = (ServerSCInfo)scList.get(i);
            if (!sci.isPrePrepared()) {
               return false;
            }
         }

         return true;
      }
   }

   private int globalPrepare() throws AbortRequestedException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "globalPrepare");
      }

      int vote = 0;
      String msg;
      synchronized(this) {
         if (this.isCancelledUnsync()) {
            this.abort();
         }

         if (this.isPrePrepared()) {
            this.setPreparing();
         } else {
            msg = "Illegal state (Expected: PrePrepared).  " + this.getXid().toString();
            this.abortUnsync(msg);
         }
      }

      boolean allResAssigned = this.assignLocalResourcesToSelf();
      if (!allResAssigned) {
         allResAssigned = this.assignNonLocalResourcesToOtherSCs();
      }

      if (!allResAssigned && !this.isRetry()) {
         msg = "Aborting prepare because the following resources could not be assigned: " + this.getUnassignedResources();
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, msg);
         }

         this.abort(msg);
      }

      this.delistAll(67108864);
      this.check("TMBeforePrepare");
      if (this.isOnePhaseCommitPossible()) {
         synchronized(this) {
            if (this.isCancelledUnsync()) {
               this.abort();
            }

            if (this.isPreparing()) {
               this.setPreparedUnsync();
            } else {
               String msg = "Illegal State (Expected: preparing).  " + this.getXid().toString();
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, msg);
               }

               this.abortUnsync(msg);
            }

            return vote;
         }
      } else {
         if (this.getTM().getTwoPhaseCommitEnabled()) {
            this.checkpointIfNecessary();
         }

         ServerSCInfo localSCI = this.getLocalSCInfo();
         List sciList = this.getSCInfoList();
         int i;
         ServerSCInfo nxaSC;
         if (!this.getTM().getParallelXAEnabled()) {
            this.noParallelXAPrepare(localSCI, sciList);
         } else {
            for(i = 0; i < sciList.size(); ++i) {
               nxaSC = (ServerSCInfo)sciList.get(i);
               if (nxaSC != localSCI) {
                  nxaSC.startPrepare(this);
               }
            }

            if (this.getDeterminer() == null) {
               this.localPrepare();
            } else {
               this.waitForNonLocalPrepareAcks(localSCI);
               this.localPrepare();
            }

            this.waitForPrepareAcks();
         }

         vote = 3;

         for(i = 0; i < sciList.size(); ++i) {
            nxaSC = (ServerSCInfo)sciList.get(i);
            if (nxaSC != localSCI && nxaSC.vote != 3) {
               vote = 0;
            }
         }

         if (localSCI.vote != 3) {
            vote = 0;
         }

         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "globalPrepare localSCI.vote==XAResource.XA_RDONLY:" + (localSCI.vote == 3) + " totalVote:" + (vote == 0));
         }

         NonXAServerResourceInfo nxa = (NonXAServerResourceInfo)this.getNonXAResource();
         if (nxa != null) {
            vote = 0;

            try {
               nxaSC = (ServerSCInfo)nxa.getSCAssignedTo();
               if (nxaSC == localSCI) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "committing local non-xa resource " + nxa.getName());
                  } else if (TxDebug.JTANonXA.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTANonXA, this, "committing local non-xa resource " + nxa.getName());
                  }

                  nxa.commit(this, false, false, true);
               } else {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "committing remote non-xa resource " + nxa.getName() + " on SC " + nxaSC.getName());
                  } else if (TxDebug.JTANonXA.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTANonXA, this, "committing remote non-xa resource " + nxa.getName() + " on SC " + nxaSC.getName());
                  }

                  nxaSC.nonXAResourceCommit(this.getXid(), false, nxa.getName());
                  if (this.getLoggingResourceCommitFailure() != null) {
                     return vote;
                  }

                  nxa.setCommitted();
               }
            } catch (Exception var8) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "Non-XA resource " + nxa.getName() + " commit failed", var8);
               } else if (TxDebug.JTANonXA.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTANonXA, this, "Non-XA resource " + nxa.getName() + " commit failed", var8);
               }

               this.abort("NonXAResource '" + nxa.getName() + "' commit failed: " + var8);
               return vote;
            }
         }

         if (this.isCancelled()) {
            return vote;
         } else {
            this.check("TMAfterPrepareBeforeTLog");
            this.isAllEmulated = this.isAllEmulatedTwoPhaseResources();
            this.logOrSetPrepared();
            this.check("TMAfterTLogBeforeCommit");
            return vote;
         }
      }
   }

   void logOrSetPrepared() throws AbortRequestedException {
      if (!this.isAllEmulated && this.isLogWriteNecessary()) {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("ServerTransactionImpl.globalPrepare isAllEmulated:" + this.isAllEmulated + " && isLogWriteNecessary():" + this.isLogWriteNecessary() + " this:" + this + "  Logging transaction.");
         }

         this.log();
      } else {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("ServerTransactionImpl.globalPrepare isAllEmulated:" + this.isAllEmulated + " && isLogWriteNecessary():" + this.isLogWriteNecessary() + " this:" + this + "  Not logging transaction.  Setting transaction state to prepared");
         }

         if (this.getDeterminer() == null) {
            this.setPrepared();
         } else if (this.getState() != 9 && this.getState() != 10 && !this.isMarkedRollback()) {
            this.setPrepared();
         }
      }

   }

   private void checkpointIfNecessary() {
      if (this.getTM().getDBPassiveModeState() != 0) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "Transaction Service is in PASSIVE state, no checkpoint");
         }

      } else {
         boolean checkpointNeeded = false;
         ArrayList resourceList = this.getResourceInfoList();
         if (resourceList != null) {
            Iterator var3 = resourceList.iterator();

            label43:
            while(true) {
               ServerResourceInfo ri;
               ResourceDescriptor rd;
               do {
                  do {
                     if (!var3.hasNext()) {
                        break label43;
                     }

                     Object aResourceList = var3.next();
                     ri = (ServerResourceInfo)aResourceList;
                     rd = ri.getResourceDescriptor();
                  } while(rd == null);

                  rd.setCoordinatedLocally();
               } while(!rd.getIsResourceCheckpointNeeded() && (!PlatformHelper.getPlatformHelper().isCheckpointLLR() || this.getNonXAResource() != ri || rd.isCheckpointed()));

               checkpointNeeded = true;
               rd.setIsResourceCheckpointNeeded(false);
            }
         }

         if (checkpointNeeded) {
            ResourceDescriptor.checkpointIfNecessary();
         }

         ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).checkpointIfNecessary();
      }
   }

   boolean assignLocalResourcesToSelf() {
      ServerSCInfo localSCI = this.getLocalSCInfo();
      if (localSCI == null) {
         return false;
      } else {
         ArrayList resourceList = this.getResourceInfoList();
         if (resourceList == null) {
            return true;
         } else {
            boolean allResAssigned = true;

            for(int i = 0; i < resourceList.size(); ++i) {
               ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);
               if (!ri.isCommitted() && !ri.isRolledBack()) {
                  if (this.isResourceNotFound()) {
                     SCInfo scInfo = ri.getSCAssignedTo();
                     String resourceNotFoundName = (String)this.getProperty("weblogic.transaction.resourceNotFoundName");
                     if (scInfo != null) {
                        CoordinatorDescriptor aCd = scInfo.getCoordinatorDescriptor();
                        if (ri.getResourceDescriptor() != null) {
                           String aResourceName = ri.getResourceDescriptor().getName();
                           if (aResourceName != null && aResourceName.equals(resourceNotFoundName)) {
                              ri.getResourceDescriptor().removeSC(aCd);
                           }
                        }
                     }
                  }

                  ri.assignResourceToSC((SCInfo)null);
                  if (!localSCI.handleResource(ri, this.isRetry(), this.getProperty("weblogic.transaction.assignableOnlyToEnlistingSCs"))) {
                     allResAssigned = false;
                  } else if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "assignLocalRes. localsc=" + localSCI.getScUrl() + ", res=" + ri.getName());
                  }
               }
            }

            return allResAssigned;
         }
      }
   }

   private void assignResourcesToSelf(String[] rmNames) {
      if (rmNames != null) {
         ServerSCInfo localSCI = this.getOrCreateLocalSCInfo();

         for(int i = 0; i < rmNames.length; ++i) {
            ServerResourceInfo ri = (ServerResourceInfo)this.getOrCreateResourceInfo(rmNames[i]);
            if (!ri.isCommitted() && !ri.isRolledBack()) {
               ri.assignResourceToSC((SCInfo)null);
               if (!localSCI.handleResource(ri, this.isRetry(), this.getProperty("weblogic.transaction.assignableOnlyToEnlistingSCs"))) {
                  String msg = "Resource " + rmNames[i] + " is not handled in server '" + this.getTM().getServerName() + "'";
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, msg);
                  }

                  ri.assignResourceToSC(localSCI);
               } else if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "assignResToSelf localsc=" + localSCI.getScUrl() + ", res=" + ri.getName());
               }
            }
         }

      }
   }

   boolean assignNonLocalResourcesToOtherSCs() {
      return this.assignNonLocalResourcesToOtherSCs(false);
   }

   private boolean assignNonLocalResourcesToOtherSCs(boolean isRollbacked) {
      this.setProperty("weblogic.transaction.allResourcesAssigned", (Serializable)null);
      ServerSCInfo localSCI = this.getLocalSCInfo();
      if (this.isRetry()) {
         this.rotateServerList();
      }

      List sciList = this.getSCInfoList();
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList == null) {
         return true;
      } else if (sciList == null) {
         return this.allResourcesAssigned(resourceList);
      } else {
         boolean scChanged = false;
         Iterator var6 = resourceList.iterator();

         while(true) {
            label233:
            while(true) {
               ServerResourceInfo sri;
               do {
                  do {
                     do {
                        if (!var6.hasNext()) {
                           if (scChanged) {
                              Object newScInfoList;
                              int i;
                              if (isRollbacked) {
                                 newScInfoList = this.getSCInfoList();

                                 for(i = 0; i < resourceList.size(); ++i) {
                                    sri = (ServerResourceInfo)resourceList.get(i);
                                    if (sri.isAssigned() && ((List)newScInfoList).indexOf(sri.getSCAssignedTo()) == -1) {
                                       ((List)newScInfoList).add(sri.getSCAssignedTo());
                                       if (TxDebug.JTANaming.isDebugEnabled()) {
                                          TxDebug.txdebug(TxDebug.JTANaming, this, "SCChange at globalRetryRollback adding sc:" + sri.getSCAssignedTo().getName());
                                       }
                                    }
                                 }
                              } else {
                                 newScInfoList = new CopyOnWriteArrayList();

                                 for(i = 0; i < resourceList.size(); ++i) {
                                    sri = (ServerResourceInfo)resourceList.get(i);
                                    if (sri.isAssigned() && ((List)newScInfoList).indexOf(sri.getSCAssignedTo()) == -1) {
                                       ((List)newScInfoList).add(sri.getSCAssignedTo());
                                       if (TxDebug.JTANaming.isDebugEnabled()) {
                                          TxDebug.txdebug(TxDebug.JTANaming, this, "SCChange adding sc:" + sri.getSCAssignedTo().getName());
                                       }
                                    }
                                 }
                              }

                              this.getAndSubstituteSCInfo((List)newScInfoList);
                           }

                           boolean isAllResourcesAssigned = this.allResourcesAssigned(resourceList);
                           if (isAllResourcesAssigned) {
                              this.setAllResourcesAssignedTrue();
                           }

                           return isAllResourcesAssigned;
                        }

                        Object aResourceList = var6.next();
                        sri = (ServerResourceInfo)aResourceList;
                     } while(sri.isCommitted());
                  } while(sri.isRolledBack());
               } while(sri.isAssigned());

               Iterator var9 = sciList.iterator();

               Object aSciList;
               ServerSCInfo sci;
               while(var9.hasNext()) {
                  aSciList = var9.next();
                  sci = (ServerSCInfo)aSciList;
                  if (sci != localSCI) {
                     if (!sci.isAccessible(this)) {
                        if (TxDebug.JTA2PC.isDebugEnabled()) {
                           TxDebug.txdebug(TxDebug.JTA2PC, this, "assignNonLocalResource: " + sci + " is not accessible. Skip assign resource to it");
                        }
                     } else if (sci.handleResource(sri, this.isRetry(), this.getProperty("weblogic.transaction.assignableOnlyToEnlistingSCs"), this.isResourceNotFound())) {
                        if (TxDebug.JTA2PC.isDebugEnabled()) {
                           TxDebug.txdebug(TxDebug.JTA2PC, this, "assignNonLocalResource: " + sri.getName() + " assigned to " + sci.getScUrl());
                        }
                        continue label233;
                     }
                  }
               }

               if (this.isResourceNotFound()) {
                  var9 = sciList.iterator();

                  while(var9.hasNext()) {
                     aSciList = var9.next();
                     sci = (ServerSCInfo)aSciList;
                     if (sci != localSCI && sci.isAccessible(this)) {
                        CoordinatorDescriptor aCoordinatorDescriptor = sci.getCoordinatorDescriptor();
                        String providerURL = PlatformHelper.getPlatformHelper().getTargetChannelURL(aCoordinatorDescriptor);
                        if (providerURL == null) {
                           providerURL = CoordinatorDescriptor.getServerURL(aCoordinatorDescriptor.getCoordinatorURL());
                        }

                        if (PlatformHelper.getPlatformHelper().resourceCheck(sri.getName(), sci.getServerName(), providerURL)) {
                           if (TxDebug.JTA2PC.isDebugEnabled()) {
                              TxDebug.txdebug(TxDebug.JTA2PC, this, "resourceCheck assignNonLocalResource: " + sri.getName() + " assigned to " + sci.getScUrl());
                           }

                           sri.assignResourceToSC(sci);
                           if (sri.getResourceDescriptor() != null) {
                              sri.getResourceDescriptor().addSC(sci.getCoordinatorDescriptor());
                           }
                           continue label233;
                        }
                     }
                  }
               }

               ServerCoordinatorDescriptor[] allSCs = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getServers(sri.getName());
               if (allSCs != null && allSCs.length > 0) {
                  if (!this.isResourceNotFound()) {
                     ServerSCInfo sci = new ServerSCInfo(allSCs[0]);
                     sri.assignResourceToSC(this.addSCIfNew(sci));
                     if (TxDebug.JTANaming.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTANaming, this, "found resource " + sri.getName() + " on " + sci.getScUrl(this) + " from ServerCoordinatorDescriptor cache");
                     }

                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, this, "assignNonLocalResource: " + sri.getName() + " assigned to new SC " + sci.getScUrl(this));
                     }
                     continue;
                  }

                  for(int i = 0; i < allSCs.length; ++i) {
                     String providerURL = PlatformHelper.getPlatformHelper().getTargetChannelURL(allSCs[i]);
                     if (providerURL == null) {
                        providerURL = CoordinatorDescriptor.getServerURL(allSCs[i].getCoordinatorURL());
                     }

                     if (PlatformHelper.getPlatformHelper().resourceCheck(sri.getName(), allSCs[i].getServerName(), providerURL)) {
                        ServerSCInfo sci = new ServerSCInfo(allSCs[i]);
                        if (sri.getSCAssignedTo() != sci && TxDebug.JTANaming.isDebugEnabled()) {
                           TxDebug.txdebug(TxDebug.JTANaming, this, "assignResourceToSC  prev=" + sri.getSCAssignedTo() + " new=" + sci + " sri=" + sri + "xid=" + this.getXid());
                        }

                        sri.assignResourceToSC(this.addSCIfNew(sci));
                        if (TxDebug.JTANaming.isDebugEnabled()) {
                           TxDebug.txdebug(TxDebug.JTANaming, this, "found resource " + sri.getName() + " on " + sci.getScUrl() + " from ServerCoordinatorDescriptor cache");
                        }

                        if (TxDebug.JTA2PC.isDebugEnabled()) {
                           TxDebug.txdebug(TxDebug.JTA2PC, this, "assignNonLocalResource: " + sri.getName() + " assigned to new SC " + sci.getScUrl());
                        }
                        break;
                     }
                  }
               }

               Collection exceptServerNames = new ArrayList();
               exceptServerNames.add(localSCI.getServerName());
               HashSet remoteCoordinators = new HashSet();
               Iterator var26 = sciList.iterator();

               ServerSCInfo sci;
               while(var26.hasNext()) {
                  sci = (ServerSCInfo)var26.next();
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTANaming, this, "except 1 sc name:" + sci.getServerName());
                  }

                  remoteCoordinators.add(sci.getCoordinatorDescriptor());
                  exceptServerNames.add(sci.getServerName());
               }

               if (allSCs != null) {
                  ServerCoordinatorDescriptor[] var27 = allSCs;
                  int var30 = allSCs.length;

                  for(int var14 = 0; var14 < var30; ++var14) {
                     ServerCoordinatorDescriptor scd = var27[var14];
                     if (TxDebug.JTANaming.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTANaming, this, "except 2 sc name:" + scd.getServerName());
                     }

                     remoteCoordinators.add(scd);
                     exceptServerNames.add(scd.getServerName());
                  }
               }

               ServerCoordinatorDescriptor scd;
               if (this.isResourceNotFound()) {
                  scd = (ServerCoordinatorDescriptor)PlatformHelper.getPlatformHelper().findServerInDomains(sri.getName(), exceptServerNames, remoteCoordinators);
               } else {
                  scd = (ServerCoordinatorDescriptor)PlatformHelper.getPlatformHelper().findServerInClusterByLocalJNDI(sri.getName(), exceptServerNames);
               }

               if (scd != null) {
                  sci = new ServerSCInfo(scd);
                  sci.incrementCoordinatorRefCount();
                  sri.assignResourceToSC(this.addSCIfNew(sci));
                  if (this.isResourceNotFound() && sri.getResourceDescriptor() != null) {
                     sri.getResourceDescriptor().addSC(scd);
                  }

                  if (this.isResourceNotFound()) {
                     scChanged = true;
                  }

                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTANaming, this, "found resource " + sri.getName() + " on " + sci.getScUrl(this) + " by checking servers in cluster");
                  }

                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "assignNonLocalResource: " + sri.getName() + " assigned to new SC " + sci.getScUrl(this) + " by checking servers in cluster");
                  }
               }
            }
         }
      }
   }

   private boolean allResourcesAssigned(ArrayList resourceList) {
      boolean allAssigned = true;

      for(int i = 0; i < resourceList.size(); ++i) {
         ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
         if (isaadrprototype && !sri.isAssigned()) {
            System.out.println("ServerTransactionImpl.allResourcesAssigned about to ");
            sri.assignResourceToSC(this.getOrCreateLocalSCInfo());
         }

         if (!sri.isAssigned()) {
            if (!this.isRetry()) {
               if (!sri.isRolledBack() && !sri.isCommitted()) {
                  TXLogger.logResourceNotAssigned(sri.getName(), this.getServerStringList());
               }
            } else if (sri instanceof XAServerResourceInfo) {
               if (this.isCommitting()) {
                  TXLogger.logResourceNotAssignedForCommitRetry(this.getXid().toString(), sri.getName(), this.getSecondsToAbandon());
               } else if (this.isRollingBack()) {
                  XAServerResourceInfo xasri = (XAServerResourceInfo)sri;
                  if (xasri.isPrepared() && !xasri.isReadOnly()) {
                     TXLogger.logResourceNotAssignedForRollbackRetry(this.getXid().toString(), sri.getName(), this.getSecondsToAbandon());
                  }
               }
            }

            allAssigned = false;
         }
      }

      return allAssigned;
   }

   private synchronized void rotateServerList() {
      List sciList = this.getSCInfoList();
      int sSize = sciList.size();
      if (sSize >= 2) {
         Object head = sciList.get(0);

         for(int i = 1; i < sSize; ++i) {
            sciList.set(i - 1, sciList.get(i));
         }

         sciList.set(sSize - 1, head);
      }
   }

   private String getServerStringList() {
      List sciList = this.getSCInfoList();
      StringBuffer sb = new StringBuffer(80);

      for(int i = 0; i < sciList.size(); ++i) {
         ServerSCInfo sci = (ServerSCInfo)sciList.get(i);
         sb.append(sci.getName() + " ");
      }

      return sb.toString();
   }

   private String getUnassignedResources() {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList == null) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < resourceList.size(); ++i) {
            ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);
            if (!ri.isAssigned()) {
               if (sb.length() > 0) {
                  sb.append(", ");
               }

               sb.append(ri.getName());
            }
         }

         return sb.toString();
      }
   }

   private void localPrepare() throws AbortRequestedException {
      if (this.isCancelled()) {
         this.abort();
      }

      this.setPreparing();
      ServerSCInfo localSCI = this.getLocalSCInfo();
      if (localSCI != null) {
         localSCI.startPrepare(this);
      }

      if (!this.isCoordinatingTransaction()) {
         this.setPrepared();
      }

   }

   void noParallelXAPrepare(ServerSCInfo localSCI, List sciList) throws AbortRequestedException {
      for(int i = 0; i < sciList.size(); ++i) {
         ServerSCInfo sci = (ServerSCInfo)sciList.get(i);
         if (sci != localSCI) {
            sci.startPrepare(this);
            this.waitForPrepareAck(sci);
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "noParallelXAPrepare sci prepareAck vote:" + (sci.vote == 0) + " sci:" + sci);
            }

            if (!this.getTM().getTwoPhaseCommitEnabled() && sci.vote == 0) {
               this.abort("XA_OK returned from subcoordinator prepare call when two-phase-enabled is set to false in configuration");
            }
         }
      }

      this.localPrepare();
      if (!this.getTM().getTwoPhaseCommitEnabled() && localSCI.vote == 0) {
         this.abort("XA_OK returned from coordinator prepare call when two-phase-enabled is set to false in configuration");
      }

   }

   private synchronized void waitForPrepareAcks() throws AbortRequestedException {
      while(!this.isCancelledUnsync() && !this.allSCsPrepared()) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            this.traceScState("waitForPrepareAcks:");
         }

         try {
            this.wait(Math.max(0L, this.getTimeToLiveMillis()));
         } catch (InterruptedException var2) {
         }
      }

      if (this.isCancelledUnsync()) {
         this.abort();
      }

   }

   private void waitForNonLocalPrepareAcks(ServerSCInfo localSCI) throws AbortRequestedException {
      while(!this.isCancelledUnsync() && !this.allNonLocalSCsPrepared(localSCI) && this.getTimeToLiveMillis() > 0L) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            this.traceScState("waitForNonLocalPrepareAcks:");
         }

         try {
            Thread.sleep(100L);
         } catch (InterruptedException var3) {
         }
      }

      if (this.isCancelledUnsync()) {
         this.abort();
      }

   }

   private boolean waitForFirstNonLocalCommitAck(ServerSCInfo sci) throws AbortRequestedException {
      long completionTimeoutMillis = this.getCompletionTimeoutSeconds() == Integer.MAX_VALUE ? 2147483647L : (long)(this.getCompletionTimeoutSeconds() * 1000);
      long endRetryPeriod = System.currentTimeMillis() + completionTimeoutMillis;

      while(!sci.isCommitted()) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            this.traceScState("waitForNonLocalCommitAck:");
         }

         if (System.currentTimeMillis() > endRetryPeriod) {
            return false;
         }

         try {
            Thread.sleep(100L);
         } catch (InterruptedException var7) {
         }
      }

      return true;
   }

   private boolean waitForNonLocalCommitAcks(ServerSCInfo localSCI) throws AbortRequestedException {
      long completionTimeoutMillis = this.getCompletionTimeoutSeconds() == Integer.MAX_VALUE ? 2147483647L : (long)(this.getCompletionTimeoutSeconds() * 1000);
      long endRetryPeriod = System.currentTimeMillis() + completionTimeoutMillis;

      while(!this.allNonLocalSCsCommitted(localSCI)) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            this.traceScState("waitForNonLocalCommitAcks:");
         }

         if (System.currentTimeMillis() > endRetryPeriod) {
            return false;
         }

         try {
            Thread.sleep(100L);
         } catch (InterruptedException var7) {
         }
      }

      return true;
   }

   synchronized void waitForPrepareAck(ServerSCInfo sci) throws AbortRequestedException {
      while(!this.isCancelledUnsync() && !sci.isPrepared()) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            this.traceScState("waitForPrepareAck SC:" + sci + " isUnSync:" + this.isCancelledUnsync() + " prep:" + sci.isPrepared() + " live:" + this.getTimeToLiveMillis());
         }

         try {
            this.wait(Math.max(0L, this.getTimeToLiveMillis()));
         } catch (InterruptedException var3) {
         }
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         this.traceScState("waitForPrepareAck exit SC:" + sci + " isUnsync:" + this.isCancelledUnsync() + " prep:" + sci.isPrepared() + " live:" + this.getTimeToLiveMillis());
      }

      if (this.isCancelledUnsync()) {
         this.abort();
      }

   }

   private void traceScState(String msg) {
      StringBuffer sb = new StringBuffer(100);
      sb.append(msg);
      List scList = this.getSCInfoList();

      for(int i = 0; i < scList.size(); ++i) {
         ServerSCInfo sci = (ServerSCInfo)scList.get(i);
         sb.append(" ").append(sci.getScUrl(this)).append("=>").append(sci.getStateAsString());
      }

      TxDebug.txdebug(TxDebug.JTA2PC, this, sb.toString());
   }

   private boolean allSCsPrepared() {
      List scList = this.getSCInfoList();
      if (scList == null) {
         return true;
      } else {
         Iterator var2 = scList.iterator();

         ServerSCInfo sci;
         do {
            if (!var2.hasNext()) {
               return true;
            }

            Object aScList = var2.next();
            sci = (ServerSCInfo)aScList;
         } while(sci.isPrepared());

         return false;
      }
   }

   private boolean allNonLocalSCsPrepared(ServerSCInfo localSCI) {
      List scList = this.getSCInfoList();
      if (scList == null) {
         return true;
      } else {
         Iterator var3 = scList.iterator();

         ServerSCInfo sci;
         do {
            if (!var3.hasNext()) {
               return true;
            }

            Object aScList = var3.next();
            sci = (ServerSCInfo)aScList;
         } while(sci == localSCI || sci.isPrepared());

         return false;
      }
   }

   private boolean allNonLocalSCsCommitted(ServerSCInfo localSCI) {
      List scList = this.getSCInfoList();
      if (scList == null) {
         return true;
      } else {
         Iterator var3 = scList.iterator();

         ServerSCInfo sci;
         do {
            if (!var3.hasNext()) {
               return true;
            }

            Object aScList = var3.next();
            sci = (ServerSCInfo)aScList;
         } while(sci == localSCI || sci.isCommitted());

         return false;
      }
   }

   private void globalCommit(int vote) throws AbortRequestedException {
      int normalizedTimeoutSeconds = this.getNormalizedTimeoutSeconds();
      boolean isCompletionTimeoutSecondsOverrideSet = this.getProperties().get("completion-timeout-seconds") != null;
      int completionTimeoutSeconds = this.getCompletionTimeoutSeconds();
      int retryCount = completionTimeoutSeconds / normalizedTimeoutSeconds;
      if (retryCount == 0) {
         retryCount = 1;
      }

      this.globalRetryCommit(this.isBlockingCommit() && !isCompletionTimeoutSecondsOverrideSet ? Integer.MAX_VALUE : retryCount, normalizedTimeoutSeconds, vote);
   }

   private void globalRetryCommit(int syncRetryCount, int retryInterval, int vote) throws AbortRequestedException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "globalRetryCommit");
      }

      synchronized(this) {
         if (this.isOver()) {
            return;
         }

         if (!this.isPrepared() && !this.isCommitting()) {
            TXLogger.logRetryCommitIllegalState(this.toString());
            this.setUnknownUnsync();
            return;
         }

         this.setCommitting();
      }

      List scList = this.getSCInfoList();
      if (scList == null) {
         this.setCommitted();
         this.check("TMAfterCommit");
      } else {
         if (this.isRetry()) {
            if (islookupresourceonretryEnabled && this.commitRetryCount != Integer.MAX_VALUE) {
               ++this.commitRetryCount;
            }

            if (islookupresourceonretryEnabled && this.commitRetryCount >= lookupresourceonretryCount) {
               this.setResourceNotFoundTrue();
            }

            boolean allResAssigned = this.assignLocalResourcesToSelf();
            if (!allResAssigned) {
               this.assignNonLocalResourcesToOtherSCs();
            }
         }

         this.isOnlyOneResourceInTx = this.isOnePhaseCommitPossible();
         this.isNoResourceInTx = this.getNumResources() == 0;
         this.onePhase = this.isOnlyOneResourceInTx || !this.isLogWriteNecessary() && !this.getTM().getParallelXAEnabled() && vote == 3;
         this.isAllEmulated = this.isAllEmulatedTwoPhaseResources();
         if (!this.getTM().getTwoPhaseCommitEnabled() && !this.onePhase && !this.isAllEmulated) {
            AbortRequestedException requestedException = new AbortRequestedException("An attempt was made to use two-phase commit while two-phase-enabled is set to false in configuration.  Number of resources=" + this.getNumResources() + ", parallel-xa-enabled=" + this.getTM().getParallelXAEnabled() + ", log write necessary=" + !this.isLogWriteNecessary());
            this.setRollbackReason(requestedException);
            throw requestedException;
         } else {
            NonXAServerResourceInfo nxa = (NonXAServerResourceInfo)this.getNonXAResource();
            ServerSCInfo localSCI = this.getLocalSCInfo();
            if (this.onePhase && nxa != null && nxa.getSCAssignedTo() != localSCI) {
               ServerSCInfo nxaSC = (ServerSCInfo)nxa.getSCAssignedTo();
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "committing remote non-xa resource " + nxa.getName() + " on SC " + nxaSC.getName());
               } else if (TxDebug.JTANonXA.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTANonXA, this, "committing remote non-xa resource " + nxa.getName() + " on SC " + nxaSC.getName());
               }

               try {
                  nxaSC.nonXAResourceCommit(this.getXid(), true, nxa.getName());
                  nxa.setCommitted();
                  nxaSC.setCommitted();
               } catch (SystemException var13) {
                  throw new AbortRequestedException(var13.getMessage());
               }

               this.localCommit(this.onePhase, this.isRetry());
            } else {
               for(int i = 0; i < syncRetryCount; ++i) {
                  if (i > 0) {
                     boolean allResAssigned = this.assignLocalResourcesToSelf();
                     if (!allResAssigned) {
                        this.assignNonLocalResourcesToOtherSCs();
                     }
                  }

                  String firstResourceCommitServer = (String)this.getProperty("weblogic.transaction.first.resource.commitServer");
                  if (firstResourceCommitServer != null) {
                     this.processFirstResourceCommit(scList, localSCI, this.onePhase, firstResourceCommitServer);
                  } else {
                     Iterator var9 = scList.iterator();

                     while(var9.hasNext()) {
                        Object aScList = var9.next();
                        ServerSCInfo sci = (ServerSCInfo)aScList;
                        if (sci != localSCI) {
                           sci.startCommit(this, this.onePhase, this.isRetry());
                        }
                     }

                     if (this.getDeterminer() == null) {
                        this.localCommit(this.onePhase, this.isRetry());
                     } else if (this.waitForNonLocalCommitAcks(localSCI)) {
                        this.localCommit(this.onePhase, this.isRetry());
                     }
                  }

                  if (this.allSCsCommitted() && this.allResourcesDone()) {
                     this.setCommitted();
                     this.check("TMAfterCommit");
                  } else {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        this.traceScState("globalRetryCommit");
                     }

                     synchronized(this) {
                        label158:
                        try {
                           if (!this.isOver()) {
                              this.setRetry();
                              if (retryInterval > 0) {
                                 this.wait((long)(retryInterval * 1000));
                              }
                              break label158;
                           }
                           break;
                        } catch (InterruptedException var14) {
                        }
                     }
                  }

                  if (this.isOver()) {
                     break;
                  }

                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "globalRetryCommit(syncRetryCount=" + syncRetryCount + ", retryInterval=" + retryInterval + ") i=" + i + ", allSCsCommitted()=" + this.allSCsCommitted() + ", allResourcesDone()=" + this.allResourcesDone());
                  }

                  this.setRetry();
               }
            }

         }
      }
   }

   void processFirstResourceCommit(List scList, ServerSCInfo localSCI, boolean onePhase, String firstResourceCommitServer) throws AbortRequestedException {
      Iterator var5 = scList.iterator();

      Object aScList;
      ServerSCInfo sci;
      while(var5.hasNext()) {
         aScList = var5.next();
         sci = (ServerSCInfo)aScList;
         if (sci.getName().equals(firstResourceCommitServer)) {
            if (sci == localSCI) {
               this.localCommit(onePhase, this.isRetry());
            } else {
               sci.startCommit(this, onePhase, this.isRetry());
               this.waitForFirstNonLocalCommitAck(sci);
            }
            break;
         }
      }

      var5 = scList.iterator();

      while(var5.hasNext()) {
         aScList = var5.next();
         sci = (ServerSCInfo)aScList;
         if (!sci.getName().equals(firstResourceCommitServer) && sci != localSCI) {
            sci.startCommit(this, onePhase, this.isRetry());
         }
      }

      if (!firstResourceCommitServer.equals(localSCI.getName())) {
         this.localCommit(onePhase, this.isRetry());
      }

   }

   private boolean allSCsCommitted() {
      List scList = this.getSCInfoList();
      if (scList == null) {
         return true;
      } else {
         Iterator var2 = scList.iterator();

         ServerSCInfo sci;
         do {
            if (!var2.hasNext()) {
               return true;
            }

            Object aScList = var2.next();
            sci = (ServerSCInfo)aScList;
         } while(sci.isCommitted());

         return false;
      }
   }

   private boolean allResourcesDone() {
      ArrayList resList = this.getResourceInfoList();
      if (resList == null) {
         return true;
      } else {
         Iterator var2 = resList.iterator();

         ServerResourceInfo sri;
         do {
            if (!var2.hasNext()) {
               return true;
            }

            Object aResList = var2.next();
            sri = (ServerResourceInfo)aResList;
         } while(sri.isCommitted() || sri.isRolledBack() || !(sri instanceof XAServerResourceInfo));

         return false;
      }
   }

   public void globalRollback() {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.globalRollback()");
      }

      this.setRollingBack();
      int i;
      if (!this.getTM().isLocalCoordinator(this.getCoordinatorDescriptor())) {
         boolean allResAssigned = this.assignLocalResourcesToSelf();
         this.localRollback();
         if (this.getCoordinatorDescriptor() == null) {
            this.setRolledBack();
         } else {
            try {
               CoordinatorOneway co = this.getCoordinator();
               if (co != null) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "asking Coordinator " + co + " to rollback");
                  }

                  PlatformHelper.getPlatformHelper().runAction(new StartRollbackAction(co, this.getRequestPropagationContext()), this.getCoServerURL(), "co.StartRollback");
               }
            } catch (Exception var7) {
               if (var7 instanceof RemoteException && TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, this, "failed to ask Coordinator to rollback got remote exception");
               }
            }

            List scList = this.getSCInfoList();
            if (scList != null) {
               for(i = 0; i < scList.size(); ++i) {
                  ServerSCInfo sci = (ServerSCInfo)scList.get(i);
                  if (sci != null) {
                     sci.setRolledBack();
                  }
               }
            }

            this.setRolledBack();
         }
      } else {
         int normalizedTimeoutSeconds = this.getNormalizedTimeoutSeconds();
         boolean isCompletionTimeoutSecondsOverrideSet = this.getProperties().get("completion-timeout-seconds") != null;
         i = this.getCompletionTimeoutSeconds();
         int retryCount = i / normalizedTimeoutSeconds;
         if (retryCount == 0) {
            retryCount = 1;
         }

         String transactionServiceHalt = PlatformHelper.getPlatformHelper().getTransactionServiceHalt();
         if ("true".equals(transactionServiceHalt)) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "ServerTransactionImpl.transactionServiceHalt is true and completionTimeoutSeconds is: " + i + " and isBlockingRollback is:" + this.isBlockingRollback());
            }

            if (i == Integer.MAX_VALUE || this.isBlockingRollback()) {
               int forceShutdownTimeoutSeconds = PlatformHelper.getPlatformHelper().getForcedShutdownTimeoutSeconds();
               retryCount = forceShutdownTimeoutSeconds / normalizedTimeoutSeconds;
               if (retryCount == 0) {
                  retryCount = 1;
               }

               this.globalRetryRollback(retryCount, normalizedTimeoutSeconds);
               return;
            }
         }

         this.globalRetryRollback(this.isBlockingRollback() && !isCompletionTimeoutSecondsOverrideSet ? Integer.MAX_VALUE : retryCount, normalizedTimeoutSeconds);
      }
   }

   protected final void setRolledBack() {
      this.unmarkRollback();
      synchronized(this) {
         if (this.isOver()) {
            return;
         }

         this.setState((byte)10);
      }

      this.afterRolledBackStateHousekeeping();
   }

   private void forceSetRolledBack() {
      this.unmarkRollback();
      synchronized(this) {
         if (this.getState() == 10) {
            return;
         }

         this.setState((byte)10);
      }

      this.afterRolledBackStateHousekeeping();
   }

   private void setRolledBackIfNotAbandoned() throws SystemException {
      this.setRolledBack();
      this.throwAbandonExceptionIfNeeded();
   }

   private final void afterRolledBackStateHousekeeping() {
      this.callAfterCompletions();
      this.decrementSCCoordinatorRefCounts();
      this.decrementResourceRefCounts();
      if (this.isImportedTransaction() && this.isCoordinatingTransaction() && this.hasHeuristics()) {
         this.logHeuristics();
         this.releaseLog();
      }

      this.tallyCompletion();
      if (this.printDebugMessagesOnCompletion()) {
         this.printDebugMessages();
      }

      if (!this.getDelayRemoveAfterRollback()) {
         this.removeSelf();
      } else {
         this.wakeUpAfterSeconds(10);
      }
   }

   private final void afterCommittedStateHousekeeping() {
      this.callAfterCompletions();
      this.decrementSCCoordinatorRefCounts();
      this.decrementResourceRefCounts();
      if (this.isImportedTransaction() && this.isCoordinatingTransaction() && this.hasHeuristics()) {
         this.logHeuristics();
         this.releaseLog();
      } else {
         this.removeSelf();
      }

      this.tallyCompletion();
      if (this.printDebugMessagesOnCompletion()) {
         this.printDebugMessages();
      }

   }

   private boolean printDebugMessagesOnCompletion() {
      if (!TxDebug.isDebugConditional) {
         return false;
      } else if (this.printDebugMessagesOnCompletion) {
         return true;
      } else {
         Serializable printdebug = (Boolean)this.getProperty("printDebugMessagesOnCompletion");
         return printdebug != null && printdebug instanceof Boolean ? (Boolean)printdebug : false;
      }
   }

   private void decrementSCCoordinatorRefCounts() {
      List scList = this.getSCInfoList();
      if (scList != null) {
         ServerSCInfo localSCI = this.getLocalSCInfo();

         for(int i = 0; i < scList.size(); ++i) {
            ServerSCInfo sci = (ServerSCInfo)scList.get(i);
            if (sci != localSCI) {
               sci.decrementCoordinatorRefCount();
            }
         }

      }
   }

   private void decrementResourceRefCounts() {
      ArrayList resList = this.getResourceInfoList();
      if (resList != null) {
         for(int i = 0; i < resList.size(); ++i) {
            ServerResourceInfo ri = (ServerResourceInfo)resList.get(i);
            ri.decrementTxRefCount();
         }

      }
   }

   protected final void setCommitted() {
      synchronized(this) {
         if (this.isOver()) {
            return;
         }

         this.setState((byte)8);
      }

      this.afterCommittedStateHousekeeping();
   }

   private void setCommittedUnsync() {
      if (!this.isOver()) {
         this.setState((byte)8);
         this.afterCommittedStateHousekeeping();
      }
   }

   private final void forceSetCommitted() {
      synchronized(this) {
         if (this.getState() == 8) {
            return;
         }

         this.setState((byte)8);
      }

      this.afterCommittedStateHousekeeping();
   }

   private void setCommittedIfNotAbandoned() throws SystemException {
      this.setCommitted();
      this.throwAbandonExceptionIfNeeded();
   }

   protected final synchronized void setUnknown() {
      this.setUnknownUnsync();
   }

   protected final void setUnknownUnsync() {
      if (this.isTxUnknownAfterCompletionCallEnabled()) {
         this.callAfterCompletions();
      }

      super.setUnknownUnsync();
      this.decrementSCCoordinatorRefCounts();
      this.decrementResourceRefCounts();
   }

   protected final synchronized void setAbandoned() {
      super.setAbandoned();
      this.decrementSCCoordinatorRefCounts();
      this.decrementResourceRefCounts();
   }

   private void throwAbandonExceptionIfNeeded() throws SystemException {
      if (this.isAbandoned()) {
         String msg = "Transaction abandoned after " + this.getAbandonTimeoutSeconds() + " seconds.";
         throw new SystemException(msg);
      }
   }

   private void callAfterCompletions() {
      ServerSCInfo localSCI = this.getLocalSCInfo();
      if (localSCI != null) {
         localSCI.callAfterCompletions(this.getStatus(), this);
      }

   }

   void releaseLog() {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "releaseLog");
      }

      if (!this.isOnePhaseCommitPossible()) {
         if (this.getLoggingResource() == null) {
            if (this.getTransactionLogger() != null) {
               this.getTransactionLogger().release(this);
            }
         } else {
            String migratedLLRServerName = (String)this.getLocalProperty("weblogic.transaction.migrated.llr");
            if (migratedLLRServerName != null) {
               LoggingResource lr = this.getLoggingResource();
               if (lr instanceof MigratableLoggingResource) {
                  ((MigratableLoggingResource)lr).deleteXARecord(this.getXid(), migratedLLRServerName);
                  this.getTM().updateMigratedLLRCompletionStatistics(migratedLLRServerName);
               }
            } else {
               this.getLoggingResource().deleteXARecord(this.getXid());
            }

            if (this.isRecoveredTransaction()) {
               this.getTM().incrementLLRCompletedRecoveredTransactionCount();
            }

         }
      }
   }

   private void releaseOTSLog() {
      TransactionLoggable log = (TransactionLoggable)this.getLocalProperty("weblogic.transaction.otsLogRecord");
      if (log != null) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, this, "releaseOTSLog, log=" + log);
         }

         this.getTransactionLogger().release(log);
      }

   }

   private void removeSelf() {
      this.getTM().remove(this);
      if (!this.isAllEmulated) {
         this.releaseLog();
      }

   }

   private synchronized void setResourceCheckInProgress(boolean inProgress) {
      this.resourceCheckInProgress = inProgress;
   }

   private void globalRetryRollback(int syncRetryCount, int retryInterval) {
      synchronized(this) {
         if (this.resourceCheckInProgress) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, this, "globalRetryRollback already in progress and waiting on resourceCheck");
            }

            return;
         }
      }

      if (islookupresourceonretryEnabled && this.rollbackRetryCount != Integer.MAX_VALUE) {
         ++this.rollbackRetryCount;
      }

      if (islookupresourceonretryEnabled && this.rollbackRetryCount >= lookupresourceonretryCount) {
         this.setResourceNotFoundTrue();
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, this, "globalRetryRollback");
      }

      synchronized(this) {
         if (this.isOver()) {
            return;
         }

         if (this.getState() != 9) {
            TXLogger.logRetryRollbackIllegalState(this.toString());
            this.setRollingBackUnsync();
         }
      }

      List scList = this.getSCInfoList();
      if (scList == null) {
         this.setRolledBack();
      } else {
         boolean allResAssigned = this.assignLocalResourcesToSelf();
         if (!allResAssigned) {
            this.setResourceCheckInProgress(true);

            try {
               this.assignNonLocalResourcesToOtherSCs(true);
            } finally {
               this.setResourceCheckInProgress(false);
            }

            if (this.isResourceNotFound()) {
               scList = this.getSCInfoList();
            }

            this.rotateServerList();
         }

         for(int i = 0; i < syncRetryCount; ++i) {
            if (i > 0) {
               allResAssigned = this.assignLocalResourcesToSelf();
               if (!allResAssigned) {
                  this.assignNonLocalResourcesToOtherSCs();
               }
            }

            ServerSCInfo localSCI = this.getLocalSCInfo();

            for(int j = 0; j < scList.size(); ++j) {
               ServerSCInfo sci = (ServerSCInfo)scList.get(j);
               if (sci != localSCI) {
                  sci.startRollback(this, this.isOnePhaseCommitPossible());
               }
            }

            this.localRollback();
            if (this.allSCsRolledBack() && this.allResourcesDone()) {
               this.setRolledBack();
            } else {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  this.traceScState("globalRetryRollback waiting");
               }

               synchronized(this) {
                  try {
                     if (this.isOver()) {
                        break;
                     }

                     this.setRetry();
                     if (retryInterval > 0) {
                        this.wait((long)(retryInterval * 1000));
                     }
                  } catch (InterruptedException var16) {
                  }
               }
            }

            if (this.isOver()) {
               break;
            }

            this.setRetry();
            if (i + 1 == syncRetryCount && this.getRollbackReasonCode() == 3 && !this.isResourceNotFound()) {
               this.setRolledBack();
            }
         }

         if (TxDebug.JTA2PC.isDebugEnabled()) {
            this.traceScState("globalRetryRollback returning");
         }

      }
   }

   private boolean allSCsRolledBack() {
      List scList = this.getSCInfoList();
      if (scList == null) {
         return true;
      } else {
         for(int i = 0; i < scList.size(); ++i) {
            ServerSCInfo sci = (ServerSCInfo)scList.get(i);
            if (!sci.isRolledBack()) {
               return false;
            }
         }

         return true;
      }
   }

   private void setRetry() {
      this.retry = true;
   }

   boolean isRetry() {
      return this.retry;
   }

   private final void setPrePreparing() {
      this.setState((byte)2);
   }

   private final boolean isPrePreparing() {
      return this.getState() == 2;
   }

   private boolean isPrePrepared() {
      return this.getState() == 3;
   }

   private final synchronized void setPrePrepared() {
      this.setPrePreparedUnsync();
   }

   private final void setPrePreparedUnsync() {
      this.setState((byte)3);
      if (!this.isCoordinatingTransaction()) {
         this.wakeUpAfterSeconds(m_pre_prepared_wait_seconds);
      }

   }

   protected final synchronized void setPrepared() {
      this.setPreparedUnsync();
   }

   void setPreparedUnsync() {
      this.setState((byte)6);
      this.wakeUpAfterSeconds(120);
   }

   private final void setLoggingUnsync() {
      this.setState((byte)5);
      this.wakeUpAfterSeconds(30);
   }

   private final boolean isLogging() {
      return this.getState() == 5;
   }

   boolean isCoordinatingTransaction() {
      CoordinatorDescriptor cd = this.getCoordinatorDescriptor();
      return cd == null ? this.getTM().equals(this.getOwnerTransactionManager()) : this.getTM().isLocalCoordinator(cd);
   }

   private void assignCoordinatorIfNecessary() {
      CoordinatorDescriptor coDesc = this.getCoordinatorDescriptor();
      if (coDesc == null) {
         this.setCoordinatorDescriptor(this.getTM().getLocalCoordinatorDescriptor());
      }
   }

   private ServerSCInfo addSCIfNew(ServerSCInfo newsci) {
      List scList = this.getSCInfoList();
      if (scList != null) {
         for(int i = 0; i < scList.size(); ++i) {
            SCInfo sci = (SCInfo)scList.get(i);
            if (sci.getCoordinatorDescriptor().equals(newsci.getCoordinatorDescriptor())) {
               return (ServerSCInfo)sci;
            }
         }
      }

      this.addSC(newsci);
      return newsci;
   }

   private synchronized ServerSCInfo getOrCreateLocalSCInfo() {
      if (this.localSCInfo == null) {
         ServerCoordinatorDescriptor scd = (ServerCoordinatorDescriptor)this.getTM().getLocalCoordinatorDescriptor();
         String localCoURL = scd.getCoordinatorURL();
         if (!scd.isChannelBased()) {
            String scURL;
            String localSSLURL;
            if (scd.isAdminPortEnabled()) {
               scURL = PlatformHelper.getPlatformHelper().findLocalAdminChannelURL(localCoURL);
               if (scURL != null) {
                  localSSLURL = PlatformHelper.getPlatformHelper().getAdminPort(scURL);
                  scd.setAdminCoordinatorURL(localCoURL, "admin", localSSLURL);
               }
            } else if (this.useSecureURL) {
               scURL = scd.getCoordinatorURL();
               if (scURL != null && !PlatformHelper.getPlatformHelper().isSSLURL(scURL)) {
                  localSSLURL = scd.getSSLCoordinatorURL();
                  if (this.firstSSL || localSSLURL == null) {
                     this.firstSSL = false;
                     localSSLURL = PlatformHelper.getPlatformHelper().findLocalSSLURL(localCoURL);
                     scd.setSSLCoordinatorURL(localSSLURL);
                  }

                  if (localSSLURL != null) {
                     scd.setNonSSLCoordinatorURL(scURL);
                     if (TxDebug.JTANaming.isDebugEnabled()) {
                        TxDebug.JTANaming.debug("ServerTransactionImpl.getOrCreateLocalSCInfo(), SWITCH using SSL so update coordinatorURL  scd " + scd + " " + (new Date()).toString() + " <" + Thread.currentThread().getName() + ">");
                     }
                  }
               }
            } else {
               scURL = scd.getCoordinatorURL();
               if (scURL != null && PlatformHelper.getPlatformHelper().isSSLURL(scURL)) {
                  scd.updateCoordinatorURL(scd.getNonSSLCoordinatorURL());
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("ServerTransactionImpl.getOrCreateLocalSCInfo(), SWITCH no longer using SSL so update coordinatorURL  scd " + scd + " " + (new Date()).toString() + " <" + Thread.currentThread().getName() + ">");
                  }
               }
            }
         }

         this.localSCInfo = new ServerSCInfo(scd);
         this.addSC(this.localSCInfo);
      }

      return this.localSCInfo;
   }

   ServerSCInfo getLocalSCInfo() {
      return this.localSCInfo;
   }

   private XAServerResourceInfo getResourceInfo(XAResource xares) {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList == null) {
         return null;
      } else {
         for(int i = 0; i < resourceList.size(); ++i) {
            ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);
            if (ri instanceof XAServerResourceInfo) {
               XAServerResourceInfo xari = (XAServerResourceInfo)ri;
               if (xari.isEquivalentResource(xares)) {
                  if (xari.isObjectOriented()) {
                     return xari.getSameResource(xares);
                  }

                  return xari;
               }
            }
         }

         return null;
      }
   }

   private XAServerResourceInfo getResourceInfo(XAResource xar, String resourceNameAlias) throws SystemException {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList == null) {
         return null;
      } else {
         for(int i = 0; i < resourceList.size(); ++i) {
            ResourceInfo ri = (ResourceInfo)resourceList.get(i);
            if (ri instanceof XAServerResourceInfo) {
               XAServerResourceInfo xari = (XAServerResourceInfo)ri;
               if (ri.getName().equals(resourceNameAlias)) {
                  if (xari.isAlias()) {
                     if (xari.isEquivalentResource(xar)) {
                        if (xari.isObjectOriented()) {
                           return xari.getSameResource(xar);
                        }

                        return xari;
                     }

                     throw new SystemException("Resource name collision.  Alias '" + resourceNameAlias + "' is already an alias for resource '" + xari.aliasOf());
                  }

                  throw new SystemException("Resource name collision.  Alias '" + resourceNameAlias + "' conflicts with resource participant '" + ri.getName() + "'.");
               }
            }
         }

         return null;
      }
   }

   private NonXAServerResourceInfo getResourceInfo(NonXAResource nxares) {
      ArrayList resourceList = this.getResourceInfoList();
      if (resourceList == null) {
         return null;
      } else {
         for(int i = 0; i < resourceList.size(); ++i) {
            ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);
            if (ri instanceof NonXAServerResourceInfo) {
               NonXAServerResourceInfo nxari = (NonXAServerResourceInfo)ri;
               if (nxari.isEquivalentResource(nxares)) {
                  return nxari;
               }
            }
         }

         return null;
      }
   }

   protected synchronized void addResourceInfo(ResourceInfo ri) {
      if (((ServerResourceInfo)ri).isObjectOriented()) {
         ServerResourceInfo curRI = (ServerResourceInfo)this.getResourceInfo((String)ri.getName());
         if (curRI == null) {
            super.addResourceInfoUnsync(ri);
         } else {
            curRI.add((ServerResourceInfo)ri);
         }
      } else {
         super.addResourceInfoUnsync(ri);
      }

   }

   private void asyncRetryRollback() {
      Runnable work = new Runnable() {
         public void run() {
            ServerTransactionImpl tx = ServerTransactionImpl.this;

            try {
               ServerTransactionImpl.this.getTM().internalResume(tx);
               tx.globalRetryRollback(1, 0);
            } catch (Exception var6) {
            } finally {
               ServerTransactionImpl.this.getTM().internalSuspend();
            }

         }

         public String toString() {
            return "Retry rollback request for tx: '" + ServerTransactionImpl.this + "'";
         }
      };
      PlatformHelper.getPlatformHelper().scheduleWork(work);
   }

   void asyncRetryCommit() {
      Runnable work = new Runnable() {
         public void run() {
            ServerTransactionImpl tx = ServerTransactionImpl.this;

            try {
               ServerTransactionImpl.this.getTM().internalResume(tx);
               tx.globalRetryCommit(1, 0, 3);
            } catch (Exception var6) {
            } finally {
               ServerTransactionImpl.this.getTM().internalSuspend();
            }

         }

         public String toString() {
            return "Retry commit request for tx: '" + ServerTransactionImpl.this + "'";
         }
      };
      PlatformHelper.getPlatformHelper().scheduleWork(work);
   }

   protected void abandon() {
      if (this.getState() == 12) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug("ServerTransactionImpl abandon() called again. A thread may be stuck. this:" + this);
         }

      } else {
         PlatformHelper.getPlatformHelper().scheduleWork(new Runnable() {
            public void run() {
               ServerTransactionImpl.this.abandonNow();
            }

            public String toString() {
               return "Abandon request for tx: '" + ServerTransactionImpl.this + "'";
            }
         });
      }
   }

   private void abandonNow() {
      if (this.isPrepared()) {
         this.globalRollback();
      } else if (this.isOver() && this.isCoordinatingTransaction() && this.hasHeuristics()) {
         try {
            this.internalForget();
         } catch (Exception var2) {
         }
      }

      this.setAbandoned();
      this.callAfterCompletions();
      this.removeSelf();
      this.releaseOTSLog();
      this.tallyCompletion();
   }

   short getHeuristicStatus(int expectedStatus) {
      short st = this.getCompletionState();
      if (st == 0) {
         return st;
      } else if (st == expectedStatus) {
         return 0;
      } else if ((st & 1) != 0) {
         return 1;
      } else if ((st & 2) != 0) {
         return 2;
      } else if (expectedStatus == 4 && (st & 8) != 0) {
         return (short)((st & 4) != 0 ? 1 : 8);
      } else if (expectedStatus == 8 && (st & 4) != 0) {
         return (short)((st & 8) != 0 ? 1 : 4);
      } else {
         TXLogger.logErrorComputingHeuristicStatus(expectedStatus, st);
         return 0;
      }
   }

   private boolean isOnePhaseCommitPossible() {
      if (this.isImportedTransaction() && !this.isForeignOnePhase()) {
         return false;
      } else {
         ArrayList resourceList = this.getResourceInfoList();
         int len = resourceList == null ? 0 : resourceList.size();
         return len <= 1;
      }
   }

   boolean isLogWriteNecessary() {
      if (this.isImportedTransaction()) {
         return true;
      } else {
         ArrayList resourceList = this.getResourceInfoList();
         int len = resourceList == null ? 0 : resourceList.size();
         if (len <= 1) {
            return false;
         } else if (this.getDeterminer() == null || this.getTM().isTLOGWriteWhenDeterminerExistsEnabled() || this.isRDONLYReturnFromDeterminer() && this.isThreeOrMoreResourcesEnlisted()) {
            if (TxDebug.JTAXA.isDebugEnabled() && this.getTM().isTLOGWriteWhenDeterminerExistsEnabled()) {
               TxDebug.JTAXA.debug("ServerTransactionImpl.isLogWriteNecessary true getDeterminer():" + this.getDeterminer() + " isTLOGWriteWhenDeterminerExistsEnabled():" + this.getTM().isTLOGWriteWhenDeterminerExistsEnabled() + " this:" + this);
            }

            if (this.getLoggingResource() != null) {
               return false;
            } else {
               int readOnlyCount = 0;
               if (this.getTM().getParallelXAEnabled()) {
                  for(int i = 0; i < len; ++i) {
                     ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
                     if (sri.isReadOnly()) {
                        ++readOnlyCount;
                     }
                  }

                  return len - readOnlyCount > 1;
               } else {
                  List sclist = this.getSCInfoList();
                  int scLength = sclist == null ? 0 : sclist.size();

                  int assignCount;
                  for(assignCount = 0; assignCount < scLength; ++assignCount) {
                     SCInfo sci = (SCInfo)sclist.get(assignCount);
                     if (!sci.getName().equals(this.localSCInfo.getName())) {
                        if (TxDebug.JTA2PC.isDebugEnabled()) {
                           TxDebug.txdebug(TxDebug.JTA2PC, this, "check vote at logWrite vote:" + (sci.vote == 0) + " sci:" + sci);
                        }

                        if (sci.vote == 0) {
                           return true;
                        }
                     }
                  }

                  assignCount = 0;

                  for(int i = 0; i < len; ++i) {
                     ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, this, "assign localSC:" + this.localSCInfo + " sri:" + sri + " assignTo:" + sri.getSCAssignedTo() + " readonly:" + sri.isReadOnly());
                     }

                     if (sri.isAssignedTo(this.localSCInfo)) {
                        ++assignCount;
                        if (sri.isReadOnly()) {
                           ++readOnlyCount;
                        }
                     }
                  }

                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, this, "assign log write:" + (assignCount - readOnlyCount > 1));
                  }

                  return assignCount - readOnlyCount > 1;
               }
            }
         } else {
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("ServerTransactionImpl.isLogWriteNecessary false as determiner is set. this:" + this);
            }

            return false;
         }
      }
   }

   boolean isAllEmulatedTwoPhaseResources() {
      ArrayList resourceList = this.getResourceInfoList();
      int len = resourceList == null ? 0 : resourceList.size();
      if (len == 0) {
         return false;
      } else {
         int emulatedCount = 0;

         for(int i = 0; i < len; ++i) {
            ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
            if (sri.isEmulatedTwoPhaseResource()) {
               ++emulatedCount;
            }
         }

         return emulatedCount == len;
      }
   }

   boolean isThreeOrMoreResourcesEnlisted() {
      ArrayList resourceList = this.getResourceInfoList();
      int len = resourceList == null ? 0 : resourceList.size();
      return len > 2;
   }

   private ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }

   private synchronized void tallyCompletion() {
      if (!this.completionTallied && this.isCoordinatingTransaction()) {
         this.completionTallied = true;
         this.getTM().getRuntime().tallyCompletion(this);
      }

      if (!this.completionPartitionTallied) {
         String partitionName = (String)this.getProperty("weblogic.transaction.partitionName");
         if ("DOMAIN".equals(partitionName)) {
            this.completionPartitionTallied = true;
         } else if (partitionName != null) {
            String serverName = (String)this.getProperty("weblogic.transaction.initiator");
            if (this.getTM().getServerName().equals(serverName)) {
               this.completionPartitionTallied = true;
               JTAPartitionRuntime jtaPartitionRuntime = this.getTM().getPartitionRuntime(partitionName);
               if (jtaPartitionRuntime != null) {
                  jtaPartitionRuntime.tallyCompletion(this);
               }
            }
         }
      }

   }

   TransactionLogger getTransactionLogger() {
      return this.migratedTxLogger != null ? this.migratedTxLogger : this.getTM().getTransactionLogger();
   }

   private void setRecoveredTransaction() {
      this.setLocalProperty("weblogic.transaction.recoveredTransaction", Boolean.TRUE);
   }

   boolean isRecoveredTransaction() {
      return this.getLocalProperty("weblogic.transaction.recoveredTransaction") != null;
   }

   void setLoggingResourceInfo(NonXAServerResourceInfo lr) {
      this.loggingResourceInfo = lr;
      this.loggingResource = lr.getLoggingResource();
   }

   void setLoggingResource(LoggingResource lr) {
      this.loggingResource = lr;
   }

   NonXAServerResourceInfo getLoggingResourceInfo() {
      return this.loggingResourceInfo;
   }

   LoggingResource getLoggingResource() {
      return this.loggingResource;
   }

   void setLoggingResourceCommitFailure(SystemException exc) {
      this.loggingResourceCommitFailure = exc;
   }

   void setOnePhaseResourceCommitFailure(SystemException exc) {
      this.onePhaseResourceCommitFailure = exc;
   }

   void setTxUnknownAfterCompletionCallEnabled(boolean exc) {
      m_isTxUnknownAfterCompletionEnabled = exc;
   }

   SystemException getLoggingResourceCommitFailure() {
      return this.loggingResourceCommitFailure;
   }

   SystemException getOnePhaseResourceCommitFailure() {
      return this.onePhaseResourceCommitFailure;
   }

   private final void validateForceCommitState(String op) throws SystemException {
      boolean invalidState = false;
      if (this.isMarkedRollback()) {
         TXLogger.logForceLocalCommitMarkedRollback(this.getXid().toString());
         throw new SystemException("Unable to " + op + ", tx is marked for rollback");
      } else {
         switch (this.getState()) {
            case 1:
            case 4:
            case 9:
            case 10:
               invalidState = true;
            case 2:
            case 3:
            case 5:
            case 7:
            case 8:
            default:
               break;
            case 6:
               if (this.isCoordinatingTransaction()) {
                  invalidState = true;
               }
         }

         if (invalidState) {
            TXLogger.logForceLocalCommitInvalidState(this.getXid().toString(), this.toString());
            throw new SystemException("Unable to " + op + ", tx state is " + this.getStateAsString(this.getState()));
         }
      }
   }

   public String getDeterminer() {
      return (String)this.getProperty("weblogic.transaction.determiner");
   }

   public String getFirstResourceToCommit() {
      return (String)this.getProperty("weblogic.transaction.first.resource.commit");
   }

   public void setRDONLYReturnFromDeterminer() {
      this.isRDONLYReturnFromDeterminer = true;
   }

   public boolean isRDONLYReturnFromDeterminer() {
      return this.isRDONLYReturnFromDeterminer;
   }

   void dump(JTAImageSource imageSource, XMLStreamWriter xsw, String partitionName) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      Map global = this.getProperties();
      if (partitionName == null || partitionName.equals(global.get("weblogic.transaction.partitionName"))) {
         xsw.writeStartElement("Transaction");
         xsw.writeAttribute("xid", this.getXid().toString());
         xsw.writeAttribute("state", this.getStateAsString(this.getState()));
         xsw.writeAttribute("status", this.getStatusAsString());
         String herr = this.getHeuristicErrorMessage();
         if (herr != null) {
            xsw.writeAttribute("heuristicError", herr);
         }

         xsw.writeAttribute("beginTime", String.valueOf(this.getBeginTimeMillis()));
         String coURL = this.getCoordinatorURL();
         if (coURL != null) {
            xsw.writeAttribute("coordinatorURL", coURL);
         }

         if (this.getOwnerTransactionManager() != null) {
            xsw.writeAttribute("ownerTM", this.getOwnerTransactionManager().toString());
         }

         Thread t = this.getActiveThread();
         if (t != null) {
            xsw.writeAttribute("activeThread", t.toString());
         }

         xsw.writeAttribute("repliesOwedMe", String.valueOf(this.getNumRepliesOwedMe()));
         xsw.writeAttribute("repliesOwedOthers", String.valueOf(this.getNumRepliesOwedOthers()));
         xsw.writeAttribute("retry", String.valueOf(this.retry));
         xsw.writeStartElement("GlobalProperties");
         if (global == null) {
            xsw.writeAttribute("currentCount", "0");
         } else {
            xsw.writeAttribute("currentCount", String.valueOf(global.size()));
            Iterator it = global.entrySet().iterator();

            while(it.hasNext()) {
               Map.Entry entry = (Map.Entry)it.next();
               xsw.writeStartElement("Property");
               xsw.writeAttribute("name", entry.getKey().toString());
               xsw.writeAttribute("value", entry.getValue().toString());
               xsw.writeEndElement();
            }
         }

         xsw.writeEndElement();
         xsw.writeStartElement("LocalProperties");
         Map local = this.getLocalProperties();
         if (local == null) {
            xsw.writeAttribute("currentCount", "0");
         } else {
            xsw.writeAttribute("currentCount", String.valueOf(local.size()));
            Iterator it = local.entrySet().iterator();

            while(it.hasNext()) {
               Map.Entry entry = (Map.Entry)it.next();
               xsw.writeStartElement("Property");
               xsw.writeAttribute("name", entry.getKey().toString());
               xsw.writeAttribute("value", entry.getValue().toString());
               xsw.writeEndElement();
            }
         }

         xsw.writeEndElement();
         xsw.writeStartElement("Servers");
         List scList = this.getSCInfoList();
         if (scList == null) {
            xsw.writeAttribute("currentCount", "0");
         } else {
            List copy = (List)((CopyOnWriteArrayList)scList).clone();
            xsw.writeAttribute("currentCount", String.valueOf(copy.size()));
            Iterator it = copy.iterator();

            while(it.hasNext()) {
               ServerSCInfo scinfo = (ServerSCInfo)it.next();
               scinfo.dump(imageSource, xsw);
            }
         }

         xsw.writeEndElement();
         xsw.writeStartElement("Resources");
         ArrayList resList = this.getResourceInfoList();
         if (resList == null) {
            xsw.writeAttribute("currentCount", "0");
         } else {
            ArrayList copy = (ArrayList)resList.clone();
            xsw.writeAttribute("currentCount", String.valueOf(copy.size()));
            Iterator it = copy.iterator();

            while(it.hasNext()) {
               ServerResourceInfo resinfo = (ServerResourceInfo)it.next();
               resinfo.dump(imageSource, xsw);
            }
         }

         xsw.writeEndElement();
         ResourceInfo nonXA = this.getNonXAResource();
         if (nonXA != null) {
            xsw.writeStartElement("NonXAResource");
            xsw.writeAttribute("name", nonXA.getName());
            xsw.writeEndElement();
         }

         xsw.writeEndElement();
      }
   }

   public void convertPre810JTSName() {
      ArrayList riList = this.getResourceInfoList();
      Iterator it = riList.iterator();

      while(it.hasNext()) {
         ResourceInfo ri = (ResourceInfo)it.next();
         if (ri.getName().equals("weblogic.jdbc.jts.Connection")) {
            ri.setName("weblogic.jdbc.wrapper.JTSXAResourceImpl");
         }
      }

   }

   void registerInterposedSynchronization(Synchronization sync, InterpositionTier tier) {
      ServerSCInfo localSCI = this.getLocalSCInfo();
      localSCI.registerInterposedSynchronization(this, sync, tier);
   }

   boolean isTxUnknownAfterCompletionCallEnabled() {
      return m_isTxUnknownAfterCompletionEnabled;
   }

   boolean isBlockingCommit() {
      return m_isBlockingCommit;
   }

   boolean isBlockingRollback() {
      return m_isBlockingRollback;
   }

   static {
      String val = System.getProperty("weblogic.transaction.pre_prepared_wait_seconds");
      if (val != null) {
         try {
            int i = Integer.parseInt(val);
            if (i > 0) {
               m_pre_prepared_wait_seconds = i;
            }
         } catch (NumberFormatException var2) {
         }
      }

      isLocalCoordinatorAssignment = Boolean.getBoolean("weblogic.transaction.local.coordinator.assignment");
      isaadrprototype = new Boolean(System.getProperty("weblogic.transaction.aadrprototype"));
   }

   private final class StartRollbackAction implements PrivilegedExceptionAction {
      private final CoordinatorOneway co;
      private final PropagationContext propCtx;

      StartRollbackAction(CoordinatorOneway aCo, PropagationContext aPropCtx) {
         this.co = aCo;
         this.propCtx = aPropCtx;
      }

      public Object run() throws Exception {
         this.co.startRollback(this.propCtx);
         return null;
      }
   }

   private final class ForceGlobalCommitAction implements PrivilegedExceptionAction {
      private final Coordinator3 co3;
      private final Xid xid;

      ForceGlobalCommitAction(Coordinator3 aCo3, Xid aXid) {
         this.co3 = aCo3;
         this.xid = aXid;
      }

      public Object run() throws Exception {
         this.co3.forceGlobalCommit(this.xid);
         return null;
      }
   }

   private final class ForceGlobalRollbackAction implements PrivilegedExceptionAction {
      private final Coordinator3 co3;
      private final Xid xid;

      ForceGlobalRollbackAction(Coordinator3 aCo3, Xid aXid) {
         this.co3 = aCo3;
         this.xid = aXid;
      }

      public Object run() throws Exception {
         this.co3.forceGlobalRollback(this.xid);
         return null;
      }
   }
}
