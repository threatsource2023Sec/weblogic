package weblogic.transaction.internal;

import java.io.Serializable;
import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.XIDFactory;
import weblogic.transaction.nonxa.EmulatedTwoPhaseResource;
import weblogic.utils.XAUtils;

final class XAServerResourceInfo extends ServerResourceInfo {
   public static final char WEBLOGIC_BRANCHQUAL_PREFIX_FOR_TIGHT_COUPLING = 'w';
   private static final String COMMIT_STRING = "commit";
   private static final String END_STRING = "end";
   private static final String FORGET_STRING = "forget";
   private static final String PREPARE_STRING = "prepare";
   private static final String ROLLBACK_STRING = "rollback";
   private static final String START_STRING = "start";
   private Object recoveryLock;
   private int lastDelistFlag;
   private XAResource xaResource;
   private boolean reRegistered;
   private int vote;
   private static final int NO_VOTE = -1;
   private Thread startThread;
   private static final byte STATE_STARTED = 2;
   private static final byte STATE_DELISTED = 3;
   private static final byte STATE_SUSPENDED = 4;
   private static final byte STATE_ENDED = 5;
   private static final byte STATE_PREPARED = 6;
   private static final boolean INSTR_ENABLED;
   private static final AuthenticatedSubject kernelID;
   private static final char[] DIGITS;
   static final long serialVersionUID = 8610286209661869699L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.transaction.internal.XAServerResourceInfo");
   static final DelegatingMonitor _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Start_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Prepare_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Commit_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Prepared_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_End_Before_High;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;

   XAServerResourceInfo(String aName) {
      super(aName);
      this.recoveryLock = new Object();
      this.lastDelistFlag = 33554432;
      this.reRegistered = false;
      this.vote = -1;
      this.rd = XAResourceDescriptor.getOrCreate(aName);
   }

   XAServerResourceInfo(String aName, boolean enlistedElsewhere) {
      this(aName);
      this.enlistedElsewhere = enlistedElsewhere;
   }

   XAServerResourceInfo(ResourceDescriptor ard) {
      super(ard);
      this.recoveryLock = new Object();
      this.lastDelistFlag = 33554432;
      this.reRegistered = false;
      this.vote = -1;
      this.setXAResource((XAResource)null);
   }

   XAServerResourceInfo(XAResource xares) {
      this.recoveryLock = new Object();
      this.lastDelistFlag = 33554432;
      this.reRegistered = false;
      this.vote = -1;
      this.setXAResource(xares);
      this.rd = XAResourceDescriptor.getOrCreate(xares);
      this.setName(this.rd.getName());
   }

   XAServerResourceInfo(String registrationName, String resourceNameAlias) {
      super(resourceNameAlias);
      this.recoveryLock = new Object();
      this.lastDelistFlag = 33554432;
      this.reRegistered = false;
      this.vote = -1;
      this.rd = XAResourceDescriptor.getOrCreate(registrationName);
      this.alias = true;
   }

   XAServerResourceInfo(String registrationName, String resourceNameAlias, boolean enlistedElsewhere) {
      this(registrationName, resourceNameAlias);
      this.enlistedElsewhere = enlistedElsewhere;
   }

   XAServerResourceInfo(XAResource xares, String resourceNameAlias) {
      super(resourceNameAlias);
      this.recoveryLock = new Object();
      this.lastDelistFlag = 33554432;
      this.reRegistered = false;
      this.vote = -1;
      this.setXAResource(xares);
      this.rd = XAResourceDescriptor.getOrCreate(xares);
      this.alias = true;
   }

   public String toString() {
      return "XAServerResourceInfo[" + this.getName() + "]=(" + super.toString() + ",xar=" + this.getXAResource() + ",re-Registered = " + this.reRegistered + ")";
   }

   boolean isPrepared() {
      return this.getState() == 6;
   }

   int getVote() {
      return this.vote;
   }

   boolean isEmulatedTwoPhaseResource() {
      return this.xaResource instanceof EmulatedTwoPhaseResource;
   }

   boolean isReadOnly() {
      return this.vote == 3;
   }

   boolean isReRegistered() {
      return this.reRegistered;
   }

   XAServerResourceInfo getSameResource(XAResource xar) {
      if (xar == this.xaResource) {
         return this;
      } else if (this.extraResourceInfos != null && xar != null) {
         Iterator var2 = this.extraResourceInfos.iterator();

         XAServerResourceInfo ri;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            Object extraResourceInfo = var2.next();
            ri = (XAServerResourceInfo)extraResourceInfo;
         } while(xar != ri.xaResource);

         return ri;
      } else {
         return null;
      }
   }

   boolean enlistIfStatic(ServerTransactionImpl tx, boolean enlistOOAlso) throws AbortRequestedException {
      try {
         if (this.rd != null && this.rd.needsStaticEnlistment(enlistOOAlso)) {
            boolean rtn = this.enlist(tx);
            if (this.extraResourceInfos != null) {
               Iterator var8 = this.extraResourceInfos.iterator();

               while(var8.hasNext()) {
                  Object extraResourceInfo = var8.next();
                  ServerResourceInfo extraRI = (ServerResourceInfo)extraResourceInfo;
                  extraRI.enlist(tx);
               }
            }

            return rtn;
         }
      } catch (SystemException var7) {
         String msg = "Tx marked rollback due to unexpected internal exception";
         tx.abort(msg, var7);
      }

      return true;
   }

   boolean enlistIfStaticAndNoThreadAffinityNeeded(ServerTransactionImpl tx, boolean enlistOOAlso) throws AbortRequestedException {
      try {
         if (this.rd != null && this.rd.needsStaticEnlistment(enlistOOAlso) && !this.rd.needThreadAffinity()) {
            boolean rtn = this.enlist(tx);
            if (this.extraResourceInfos != null) {
               Iterator var8 = this.extraResourceInfos.iterator();

               while(var8.hasNext()) {
                  Object extraResourceInfo = var8.next();
                  ServerResourceInfo extraRI = (ServerResourceInfo)extraResourceInfo;
                  extraRI.enlist(tx);
               }
            }

            return rtn;
         }
      } catch (SystemException var7) {
         String msg = "Tx marked rollback due to unexpected internal exception";
         tx.abort(msg, var7);
      }

      return true;
   }

   boolean enlistIfNeedThreadAffinity(ServerTransactionImpl tx, boolean enlistOOAlso) throws AbortRequestedException {
      try {
         if (this.rd != null && this.rd.needThreadAffinity() && this.rd.needsStaticEnlistment(enlistOOAlso)) {
            boolean rtn = this.enlist(tx);
            if (this.extraResourceInfos != null) {
               Iterator var8 = this.extraResourceInfos.iterator();

               while(var8.hasNext()) {
                  Object extraResourceInfo = var8.next();
                  ServerResourceInfo extraRI = (ServerResourceInfo)extraResourceInfo;
                  extraRI.enlist(tx);
               }
            }

            return rtn;
         }
      } catch (SystemException var7) {
         String msg = "Tx marked rollback due to unexpected internal exception";
         tx.abort(msg, var7);
      }

      return true;
   }

   boolean enlist(ServerTransactionImpl tx) throws AbortRequestedException, SystemException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         txtrace(TxDebug.JTA2PC, tx, "enlist " + this.getName() + ", beforeState=" + this.getStateAsString());
      }

      ((XAResourceDescriptor)this.rd).serializedEnlist(tx, this.xaResource);
      if (!this.testAndSetBusy()) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            txtrace(TxDebug.JTA2PC, tx, "enlist " + this.getName() + " is busy");
         }

         ((XAResourceDescriptor)this.rd).serializedDelist(tx, this.xaResource);
         return false;
      } else {
         boolean startFailed = false;

         try {
            switch (this.getState()) {
               case 1:
                  int flags = this.isEnlistedElsewhere() ? 2097152 : 0;
                  this.xaStart(tx, flags);
               case 2:
                  break;
               case 3:
                  this.setStarted();
                  break;
               case 4:
                  this.xaStart(tx, 134217728);
                  break;
               case 5:
                  this.xaStart(tx, 2097152);
                  break;
               default:
                  tx.abort("Internal Error: Illegal State=" + this.getStateAsString());
            }
         } catch (AbortRequestedException var8) {
            startFailed = true;
            this.clearBusy();
            ((XAResourceDescriptor)this.rd).serializedDelist(tx, this.xaResource);
            throw var8;
         } catch (SystemException var9) {
            startFailed = true;
            this.clearBusy();
            ((XAResourceDescriptor)this.rd).serializedDelist(tx, this.xaResource);
            throw var9;
         } finally {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               txtrace(TxDebug.JTA2PC, tx, "enlist " + this.getName() + ", afterState=" + this.getStateAsString());
            }

            if (!startFailed) {
               this.clearBusy();
            }

         }

         return true;
      }
   }

   void delist(ServerTransactionImpl tx, int delistFlag, boolean forceDelist) throws AbortRequestedException {
      try {
         this.internalDelist(tx, delistFlag);
      } catch (AbortRequestedException var9) {
         if (!forceDelist) {
            throw var9;
         }
      }

      if (this.extraResourceInfos != null) {
         Iterator var4 = this.extraResourceInfos.iterator();

         while(var4.hasNext()) {
            Object extraResourceInfo = var4.next();
            XAServerResourceInfo ri = (XAServerResourceInfo)extraResourceInfo;

            try {
               ri.internalDelist(tx, delistFlag);
            } catch (AbortRequestedException var8) {
               if (!forceDelist) {
                  throw var8;
               }
            }
         }
      }

   }

   private void internalDelist(ServerTransactionImpl tx, int delistFlag) throws AbortRequestedException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         txtrace(TxDebug.JTA2PC, tx, "delist " + this.getName() + ", " + flagsToString(delistFlag) + ", beforeState=" + this.getStateAsString() + ", startThread=" + this.startThread);
      }

      if (delistFlag != 536870912 || this.startThread != null && this.startThread == Thread.currentThread() || this.getXAResourceDescriptor().getAsyncTimeoutDelist()) {
         try {
            switch (this.getState()) {
               case 2:
               case 3:
                  break;
               case 4:
                  if (delistFlag == 33554432 || delistFlag == 536870912 && !this.getXAResourceDescriptor().getAsyncTimeoutDelist()) {
                     return;
                  }
                  break;
               case 5:
                  return;
               case 6:
               case 7:
               default:
                  return;
               case 8:
                  if (delistFlag != 33554432 || this.startThread != Thread.currentThread()) {
                     return;
                  }
            }

            if (delistFlag == 33554432) {
               if (this.getXAResourceDescriptor().getDelistTMSUCCESSInsteadOfTMSUSPEND() && !this.getXAResourceDescriptor().getDelistTMSUCCESSAlways()) {
                  delistFlag = 67108864;
               } else {
                  delistFlag = this.getAndResetDelistFlag();
               }
            }

            try {
               this.end(tx, this.getXIDwithBranch((XidImpl)tx.getXID()), delistFlag);
               if (delistFlag != 67108864 && delistFlag != 536870912) {
                  if (delistFlag == 33554432 && this.getState() != 8) {
                     this.setSuspended();
                  }
               } else {
                  this.setEnded();
               }
            } catch (XAException var8) {
               if (tx.getTimeToLiveMillis() <= 0L) {
                  tx.setRollbackOnly(new TimedOutException(tx));
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     xatxtrace(TxDebug.JTAXA, tx, "setRollbackOnly called with a TimedOutException. ", var8);
                  }
               }

               String msg = "delist() failed on resource '" + this.getName() + "'. ";
               if (delistFlag == 536870912 && this.startThread != Thread.currentThread()) {
                  msg = msg + "If this XAResource is a JDBC DataSource, you may set 'callXAEndAtTxTimeout' as 'false' in your DataSource properties to work around this JDBC driver issue.";
               }

               if (!tx.isCancelled()) {
                  this.setEnded();
                  tx.abort(msg, var8);
               }
            }
         } finally {
            ((XAResourceDescriptor)this.rd).serializedDelist(tx, this.xaResource);
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               txtrace(TxDebug.JTA2PC, tx, "delist " + this.getName() + ", afterState" + this.getStateAsString());
            }

         }

      }
   }

   boolean delayedDelist(ServerTransactionImpl tx, int delistFlag) throws AbortRequestedException {
      int st = this.getState();
      switch (st) {
         case 1:
            tx.abort("Illegal state: Attempt to delist a resource that was never enlisted. Resource=" + this.getName());
            break;
         case 2:
            this.setDelisted(delistFlag);
            break;
         case 3:
            this.setDelisted(delistFlag);
            break;
         case 4:
            if (delistFlag == 33554432 || delistFlag == 536870912) {
               return true;
            }
         case 5:
            break;
         default:
            tx.abort("Internal Delist Error: Unknown state:" + st);
      }

      return true;
   }

   int prepare(ServerTransactionImpl tx) throws AbortRequestedException {
      if (this.isRolledBack()) {
         tx.abort("Resource '" + this.getName() + "' already rolled back");
      }

      if (!this.testAndSetBusy()) {
         tx.abort("Resource '" + this.getName() + "' is busy");
      }

      int var5;
      try {
         int vote = -1;
         XAException ex = null;
         Xid bXid = this.getXIDwithBranch((XidImpl)tx.getXID());
         if (this.getXAResourceDescriptor().getDelistTMSUCCESSAlways() && this.isSuspended()) {
            try {
               this.end(tx, bXid, 67108864);
            } catch (XAException var13) {
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  xatxtrace(TxDebug.JTAXA, tx, " end failed in prepare. ", var13);
               }
            }
         }

         if (INSTR_ENABLED) {
            tx.check("ResourceBeforePrepare", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
         }

         try {
            vote = this.prepare(tx, bXid);
         } catch (XAException var16) {
            if (var16.errorCode == -6) {
               try {
                  this.end(tx, bXid, 67108864);
               } catch (XAException var15) {
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     xatxtrace(TxDebug.JTAXA, tx, " end failed in prepare. ", var15);
                  }
               }

               try {
                  vote = this.prepare(tx, bXid);
               } catch (XAException var14) {
                  ex = var14;
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     xatxtrace(TxDebug.JTAXA, tx, " prepare failed. ", var14);
                  }
               }
            } else {
               ex = var16;
            }
         }

         if (ex != null) {
            if (!(ex instanceof ResourceAccessException)) {
               this.setVote(ex.errorCode);
            }

            tx.abort("Could not prepare resource '" + this.getName(), ex);
         }

         this.setPrepared(vote);
         if (INSTR_ENABLED) {
            tx.check("ResourceAfterPrepare", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
         }

         var5 = vote;
      } finally {
         this.clearBusy();
      }

      return var5;
   }

   private void checkAndClearBusyFlag(ServerTransactionImpl tx) {
      int retryCount = this.getBusyRetryCount();
      if (clearBusyCount > 0 && retryCount > clearBusyCount) {
         TXLogger.logClearedBusyFlag(this.xid.toString(), this.getName(), retryCount * tx.getNormalizedTimeoutSeconds());
         this.clearBusy();
      }

   }

   boolean commit(ServerTransactionImpl tx, boolean onePhase, boolean retry, boolean forced) throws AbortRequestedException {
      SCInfo scInfo = this.getSCAssignedTo();
      String scURL = scInfo != null ? scInfo.getScUrl() : null;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo.commit(), onePhase=" + onePhase + ",retry=" + retry));
      }

      boolean haveBusyFlag;
      if (!this.testAndSetBusy()) {
         this.checkAndClearBusyFlag(tx);
         if (!forced) {
            return false;
         }

         haveBusyFlag = false;

         for(int i = 0; i < 3; ++i) {
            try {
               Thread.sleep(500L);
            } catch (InterruptedException var55) {
            }

            if (this.testAndSetBusy()) {
               haveBusyFlag = true;
            }
         }

         if (!haveBusyFlag) {
            TXLogger.logForceCommitResourceBusy(tx.getXid().toString(), this.getName());
            return false;
         }
      }

      boolean allResAssigned;
      try {
         boolean done;
         XAException xaException;
         boolean onePhaseRollback;
         label1072: {
            if (this.isCommitted()) {
               if (forced) {
                  TXLogger.logForceCommitResourceCommitted(tx.getXid().toString(), this.getName());
               }

               haveBusyFlag = true;
               return haveBusyFlag;
            }

            xaException = null;
            onePhaseRollback = false;
            done = true;
            boolean hasHeuristic = false;
            Xid bXid = this.getXIDwithBranch((XidImpl)tx.getXID());
            boolean var46 = false;

            boolean var62;
            label1074: {
               ServerTransactionManagerImpl tm;
               label1075: {
                  label1076: {
                     label1043: {
                        try {
                           var46 = true;
                           if (this.getVote() != 3) {
                              this.commit(tx, bXid, onePhase, retry);
                           }

                           tx.addXAResourceCompletionState((short)4, scURL);
                           var62 = true;
                           var46 = false;
                           break label1074;
                        } catch (XAException var57) {
                           XAException xae = var57;
                           label1005:
                           switch (var57.errorCode) {
                              case -6:
                                 if (onePhase) {
                                    try {
                                       this.end(tx, bXid, 67108864);
                                    } catch (XAException var53) {
                                    }

                                    try {
                                       this.commit(tx, bXid, onePhase, retry);
                                       tx.addXAResourceCompletionState((short)4, scURL);
                                       allResAssigned = true;
                                       var46 = false;
                                       break label1043;
                                    } catch (XAException var56) {
                                       xae = var56;
                                    }
                                 }
                              default:
                                 xaException = xae;
                                 if (TxDebug.JTA2PC.isDebugEnabled()) {
                                    TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("DEBUGJTA ServerResourceInfo, xaException=" + xae));
                                 }

                                 if (onePhase && (100 <= xae.errorCode && xae.errorCode <= 107 || xae.getMessage() != null && xae.getMessage().contains("ORA-02091"))) {
                                    onePhaseRollback = true;
                                    tx.abort(xae);
                                 }

                                 switch (xae.errorCode) {
                                    case -7:
                                    case -3:
                                    case 4:
                                    case 200:
                                       if (onePhase) {
                                          tx.setState((byte)11);
                                          TXLogger.logOnePhaseCommitResourceError(bXid.toString(), this.getName(), xae);
                                          String excTxt = "One-phase transaction " + bXid.toString() + " for resource " + this.getName() + " is in an unknown state.";
                                          SystemException se = new SystemException(excTxt);
                                          se.initCause(xae);
                                          tx.setOnePhaseResourceCommitFailure(se);
                                       } else {
                                          tx.setResourceNotFoundTrue();
                                          allResAssigned = tx.assignLocalResourcesToSelf();
                                          if (!allResAssigned) {
                                             allResAssigned = tx.assignNonLocalResourcesToOtherSCs();
                                          }

                                          TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].commit tx: " + tx + " allResAssigned from assignNonLocalResourcesToOtherSCs:" + allResAssigned + " getSCInfoList().size():" + (this.getSCInfoList() == null ? null : this.getSCInfoList().size())));
                                       }

                                       done = false;
                                       break;
                                    case 5:
                                       tx.addXAResourceCompletionState((short)1, scURL);
                                       tx.addHeuristicErrorMessage("(" + this.getName() + ", HeuristicMixed, (" + xae.toString() + ")) ");
                                       hasHeuristic = true;
                                       if (onePhase) {
                                          onePhaseRollback = true;
                                       }
                                       break;
                                    case 6:
                                       tx.addXAResourceCompletionState((short)8, scURL);
                                       tx.addHeuristicErrorMessage("(" + this.getName() + ", HeuristicRollback, (" + xae.toString() + ")) ");
                                       hasHeuristic = true;
                                       if (onePhase) {
                                          onePhaseRollback = true;
                                       }
                                       break;
                                    case 7:
                                       tx.addXAResourceCompletionState((short)4, scURL);

                                       try {
                                          this.forget(tx, bXid);
                                       } catch (XAException var52) {
                                       }

                                       allResAssigned = true;
                                       var46 = false;
                                       break label1005;
                                    case 8:
                                       tx.addXAResourceCompletionState((short)2, scURL);
                                       tx.addHeuristicErrorMessage("(" + this.getName() + ", XAException.XA_HEURHAZ HeuristicHazard, (" + xae.toString() + ")) ");
                                       hasHeuristic = true;
                                       if (onePhase) {
                                          onePhaseRollback = true;
                                       }
                                       break;
                                    default:
                                       tx.addXAResourceCompletionState((short)2, scURL);
                                       tx.addHeuristicErrorMessage("(" + this.getName() + ", default HeuristicHazard, (" + xae.toString() + ")) ");
                                       if (onePhase) {
                                          onePhaseRollback = true;
                                       }
                                 }

                                 if (forced) {
                                    TXLogger.logForceCommitResourceError(bXid.toString(), this.getName(), xae);
                                 }

                                 allResAssigned = done;
                                 var46 = false;
                                 break label1075;
                              case -4:
                                 if (retry) {
                                    tx.addXAResourceCompletionState((short)4, scURL);
                                 } else {
                                    tx.addXAResourceCompletionState((short)2, scURL);
                                    tx.addHeuristicErrorMessage("(" + this.getName() + ", XAException.XAER_NOTA HeuristicHazard, ()) ");
                                    if (onePhase) {
                                       onePhaseRollback = true;
                                    } else {
                                       hasHeuristic = true;
                                    }
                                 }

                                 allResAssigned = true;
                                 var46 = false;
                                 break label1076;
                           }
                        } finally {
                           if (var46) {
                              if (hasHeuristic && !onePhase) {
                                 ServerTransactionManagerImpl tm = getTM();
                                 if (tm.getForgetHeuristics()) {
                                    try {
                                       this.forget(tx, bXid);
                                    } catch (XAException var47) {
                                    }
                                 } else {
                                    TXLogger.logForgetNotCalledOnCommitHeur(this.getName(), getTxId(tx, bXid));
                                 }
                              }

                              if (onePhaseRollback) {
                                 this.setRolledBack();
                              } else if (done) {
                                 this.setCommitted();
                              }

                              if (done) {
                                 this.rd.tallyCompletion(this, xaException);
                              }

                           }
                        }

                        if (hasHeuristic && !onePhase) {
                           tm = getTM();
                           if (tm.getForgetHeuristics()) {
                              try {
                                 this.forget(tx, bXid);
                              } catch (XAException var51) {
                              }
                           } else {
                              TXLogger.logForgetNotCalledOnCommitHeur(this.getName(), getTxId(tx, bXid));
                           }
                        }
                        break label1072;
                     }

                     if (hasHeuristic && !onePhase) {
                        tm = getTM();
                        if (tm.getForgetHeuristics()) {
                           try {
                              this.forget(tx, bXid);
                           } catch (XAException var49) {
                           }
                        } else {
                           TXLogger.logForgetNotCalledOnCommitHeur(this.getName(), getTxId(tx, bXid));
                        }
                     }

                     if (onePhaseRollback) {
                        this.setRolledBack();
                     } else if (done) {
                        this.setCommitted();
                     }

                     if (done) {
                        this.rd.tallyCompletion(this, xaException);
                     }

                     return allResAssigned;
                  }

                  if (hasHeuristic && !onePhase) {
                     tm = getTM();
                     if (tm.getForgetHeuristics()) {
                        try {
                           this.forget(tx, bXid);
                        } catch (XAException var50) {
                        }
                     } else {
                        TXLogger.logForgetNotCalledOnCommitHeur(this.getName(), getTxId(tx, bXid));
                     }
                  }

                  if (onePhaseRollback) {
                     this.setRolledBack();
                  } else if (done) {
                     this.setCommitted();
                  }

                  if (done) {
                     this.rd.tallyCompletion(this, xaException);
                  }

                  return allResAssigned;
               }

               if (hasHeuristic && !onePhase) {
                  tm = getTM();
                  if (tm.getForgetHeuristics()) {
                     try {
                        this.forget(tx, bXid);
                     } catch (XAException var48) {
                     }
                  } else {
                     TXLogger.logForgetNotCalledOnCommitHeur(this.getName(), getTxId(tx, bXid));
                  }
               }

               if (onePhaseRollback) {
                  this.setRolledBack();
               } else if (done) {
                  this.setCommitted();
               }

               if (done) {
                  this.rd.tallyCompletion(this, xaException);
               }

               return allResAssigned;
            }

            if (hasHeuristic && !onePhase) {
               ServerTransactionManagerImpl tm = getTM();
               if (tm.getForgetHeuristics()) {
                  try {
                     this.forget(tx, bXid);
                  } catch (XAException var54) {
                  }
               } else {
                  TXLogger.logForgetNotCalledOnCommitHeur(this.getName(), getTxId(tx, bXid));
               }
            }

            if (onePhaseRollback) {
               this.setRolledBack();
            } else if (done) {
               this.setCommitted();
            }

            if (done) {
               this.rd.tallyCompletion(this, xaException);
            }

            return var62;
         }

         if (onePhaseRollback) {
            this.setRolledBack();
         } else if (done) {
            this.setCommitted();
         }

         if (done) {
            this.rd.tallyCompletion(this, xaException);
         }
      } finally {
         this.clearBusy();
      }

      return allResAssigned;
   }

   final void rollback(ServerTransactionImpl tx, boolean forced, boolean isOnePhaseTransaction) {
      SCInfo scInfo = this.getSCAssignedTo();
      String scURL = scInfo != null ? scInfo.getScUrl() : null;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback(tx): " + tx));
      }

      if (!this.testAndSetBusy()) {
         this.checkAndClearBusyFlag(tx);
         if (!forced) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback(" + tx.getXid() + ") returning, busy flag set"));
            }

            return;
         }

         boolean haveBusyFlag = false;

         for(int i = 0; i < 3; ++i) {
            try {
               Thread.sleep(500L);
            } catch (InterruptedException var35) {
            }

            if (this.testAndSetBusy()) {
               haveBusyFlag = true;
            }
         }

         if (!haveBusyFlag) {
            TXLogger.logForceRollbackResourceBusy(tx.getXid().toString(), this.getName());
            return;
         }
      }

      if (this.isRolledBack()) {
         this.clearBusy();
         if (forced) {
            TXLogger.logForceRollbackResourceRolledBack(tx.getXid().toString(), this.getName());
         }

      } else {
         XAException xaException = null;
         boolean hasHeuristic = false;
         boolean done = false;
         Xid bXid = this.getXIDwithBranch((XidImpl)tx.getXID());
         boolean var28 = false;

         TransactionManagerImpl tm;
         label583: {
            label584: {
               label585: {
                  try {
                     var28 = true;
                     XAResource var10 = this.getXAResource();
                     if (var10 == null) {
                        if (TxDebug.JTA2PC.isDebugEnabled()) {
                           txtrace(TxDebug.JTA2PC, tx, "ServerResourceInfo[" + this.getName() + "].rollback quitting because no XAResource");
                           var28 = false;
                        } else {
                           var28 = false;
                        }
                        break label585;
                     }

                     int vote = this.getVote();

                     try {
                        if (vote != 0 && vote != -3 && vote != -7 && vote != -1) {
                           done = true;
                        } else {
                           done = this.rollback(tx, bXid);
                        }

                        if (done) {
                           tx.addXAResourceCompletionState((short)8, scURL);
                           var28 = false;
                        } else {
                           var28 = false;
                        }
                     } catch (XAException var37) {
                        label587: {
                           XAException xae = var37;
                           switch (var37.errorCode) {
                              case -6:
                                 try {
                                    this.end(tx, bXid, 536870912);
                                    this.rollback(tx, bXid);
                                    done = true;
                                    tx.addXAResourceCompletionState((short)8, scURL);
                                    var28 = false;
                                    break label587;
                                 } catch (XAException var36) {
                                    xae = var36;
                                    if (TxDebug.JTAXA.isDebugEnabled()) {
                                       xatxtrace(TxDebug.JTAXA, tx, "XA.rollback retry after XAER_PROTO error", var36);
                                    }
                                    break;
                                 }
                              case -4:
                                 if (vote != 0) {
                                    done = true;
                                    tx.addXAResourceCompletionState((short)8, scURL);
                                    var28 = false;
                                    break label583;
                                 }
                           }

                           xaException = xae;
                           switch (xae.errorCode) {
                              case 5:
                                 tx.addXAResourceCompletionState((short)1, scURL);
                                 tx.addHeuristicErrorMessage("(" + this.getName() + ", HeuristicMixed, (" + xae.toString() + ")) ");
                                 hasHeuristic = true;
                                 done = true;
                                 break;
                              case 6:
                                 tx.addXAResourceCompletionState((short)8, scURL);

                                 try {
                                    this.forget(tx, bXid);
                                 } catch (XAException var34) {
                                 }

                                 done = true;
                                 break;
                              case 7:
                                 tx.addXAResourceCompletionState((short)4, scURL);
                                 tx.addHeuristicErrorMessage("(" + this.getName() + ", HeuristicCommitt, (" + xae.toString() + ")) ");
                                 hasHeuristic = true;
                                 done = true;
                                 break;
                              case 8:
                                 tx.addXAResourceCompletionState((short)2, scURL);
                                 tx.addHeuristicErrorMessage("(" + this.getName() + ", HeuristicHazard, (" + xae.toString() + ")) ");
                                 hasHeuristic = true;
                                 done = true;
                                 break;
                              case 100:
                                 tx.addXAResourceCompletionState((short)8, scURL);
                                 done = true;
                                 break;
                              case 200:
                                 if (isOnePhaseTransaction) {
                                    done = true;
                                    tx.addXAResourceCompletionState((short)8, scURL);
                                    break;
                                 }
                              case -7:
                              case -3:
                                 if (TxDebug.JTA2PC.isDebugEnabled()) {
                                    xatxtrace(TxDebug.JTA2PC, tx, "XA.rollback ignoring exception due to transient RMERR/RMFAIL", xae);
                                 }
                                 break;
                              default:
                                 tx.addXAResourceCompletionState((short)2, scURL);
                                 tx.addHeuristicErrorMessage("(" + this.getName() + ", HeuristicHazard, (" + xae.toString() + ")) ");
                                 done = true;
                           }

                           if (forced) {
                              TXLogger.logForceRollbackResourceError(bXid.toString(), this.getName(), xae);
                              var28 = false;
                           } else {
                              var28 = false;
                           }
                        }
                     }
                     break label584;
                  } finally {
                     if (var28) {
                        if (hasHeuristic) {
                           TransactionManagerImpl tm = TransactionManagerImpl.getTransactionManager();
                           if (tm instanceof ServerTransactionManagerImpl) {
                              if (((ServerTransactionManagerImpl)tm).getForgetHeuristics()) {
                                 try {
                                    this.forget(tx, bXid);
                                 } catch (XAException var29) {
                                 }
                              } else {
                                 TXLogger.logForgetNotCalledOnRollbackHeur(this.getName(), getTxId(tx, bXid));
                              }
                           }
                        }

                        if (done) {
                           if (TxDebug.JTA2PC.isDebugEnabled()) {
                              TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback done"));
                           }

                           this.setRolledBack();
                           this.rd.tallyCompletion(this, xaException);
                        } else {
                           if (TxDebug.JTA2PC.isDebugEnabled()) {
                              TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback NOT DONE"));
                           }

                           if (TxDebug.isIsDebugConditionalResourceCommitRollbackException) {
                              tx.printDebugMessages();
                           }
                        }

                        this.clearBusy();
                     }
                  }

                  if (hasHeuristic) {
                     tm = TransactionManagerImpl.getTransactionManager();
                     if (tm instanceof ServerTransactionManagerImpl) {
                        if (((ServerTransactionManagerImpl)tm).getForgetHeuristics()) {
                           try {
                              this.forget(tx, bXid);
                           } catch (XAException var31) {
                           }
                        } else {
                           TXLogger.logForgetNotCalledOnRollbackHeur(this.getName(), getTxId(tx, bXid));
                        }
                     }
                  }

                  if (done) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback done"));
                     }

                     this.setRolledBack();
                     this.rd.tallyCompletion(this, xaException);
                  } else {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback NOT DONE"));
                     }

                     if (TxDebug.isIsDebugConditionalResourceCommitRollbackException) {
                        tx.printDebugMessages();
                     }
                  }

                  this.clearBusy();
                  return;
               }

               if (hasHeuristic) {
                  TransactionManagerImpl tm = TransactionManagerImpl.getTransactionManager();
                  if (tm instanceof ServerTransactionManagerImpl) {
                     if (((ServerTransactionManagerImpl)tm).getForgetHeuristics()) {
                        try {
                           this.forget(tx, bXid);
                        } catch (XAException var32) {
                        }
                     } else {
                        TXLogger.logForgetNotCalledOnRollbackHeur(this.getName(), getTxId(tx, bXid));
                     }
                  }
               }

               if (done) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback done"));
                  }

                  this.setRolledBack();
                  this.rd.tallyCompletion(this, xaException);
               } else {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback NOT DONE"));
                  }

                  if (TxDebug.isIsDebugConditionalResourceCommitRollbackException) {
                     tx.printDebugMessages();
                  }
               }

               this.clearBusy();
               return;
            }

            if (hasHeuristic) {
               TransactionManagerImpl tm = TransactionManagerImpl.getTransactionManager();
               if (tm instanceof ServerTransactionManagerImpl) {
                  if (((ServerTransactionManagerImpl)tm).getForgetHeuristics()) {
                     try {
                        this.forget(tx, bXid);
                     } catch (XAException var33) {
                     }
                  } else {
                     TXLogger.logForgetNotCalledOnRollbackHeur(this.getName(), getTxId(tx, bXid));
                  }
               }
            }

            if (done) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback done"));
               }

               this.setRolledBack();
               this.rd.tallyCompletion(this, xaException);
            } else {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback NOT DONE"));
               }

               if (TxDebug.isIsDebugConditionalResourceCommitRollbackException) {
                  tx.printDebugMessages();
               }
            }

            this.clearBusy();
            return;
         }

         if (hasHeuristic) {
            tm = TransactionManagerImpl.getTransactionManager();
            if (tm instanceof ServerTransactionManagerImpl) {
               if (((ServerTransactionManagerImpl)tm).getForgetHeuristics()) {
                  try {
                     this.forget(tx, bXid);
                  } catch (XAException var30) {
                  }
               } else {
                  TXLogger.logForgetNotCalledOnRollbackHeur(this.getName(), getTxId(tx, bXid));
               }
            }
         }

         if (done) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback done"));
            }

            this.setRolledBack();
            this.rd.tallyCompletion(this, xaException);
         } else {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].rollback NOT DONE"));
            }

            if (TxDebug.isIsDebugConditionalResourceCommitRollbackException) {
               tx.printDebugMessages();
            }
         }

         this.clearBusy();
      }
   }

   void rollback(Xid[] xids) throws SystemException {
      HashSet determinerXidsRemovalSet = new HashSet();

      try {
         XAResource xar = this.getXAResource();
         if (xar == null) {
            throw new SystemException("No XAResource object registered. Yet");
         }

         if (xids != null) {
            Xid[] var4 = xids;
            int var5 = xids.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Xid xid = var4[var6];
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.JTA2PC.debug((TransactionImpl)getTM().getTransaction(xid), "XAServerResourceInfo.rollback(Xid[] xids)Recovered and rolled back: " + xid + " isXidDeterminer:" + XAResourceDescriptor.isXidDeterminer(xid) + " on this XAServerResourceInfo:" + this);
               }

               try {
                  if (xid != null) {
                     if (this.rd.getPartitionName() == null) {
                        xar.rollback(xid);
                     } else {
                        this.partitionXaRollback(xar, xid);
                     }

                     if (this.rd.isDeterminer()) {
                        determinerXidsRemovalSet.add(xid);
                     }
                  }
               } catch (XAException var16) {
                  if (var16.errorCode == -4) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.JTA2PC.debug((TransactionImpl)getTM().getTransaction(xid), "XAServerResourceInfo.rollback(Xid[] xids) XAER_NOTA during rollback: " + xid + " isXidDeterminer:" + XAResourceDescriptor.isXidDeterminer(xid) + " on this XAServerResourceInfo:" + this);
                     }

                     if (this.rd.isDeterminer()) {
                        determinerXidsRemovalSet.add(xid);
                     }
                  } else {
                     TXLogger.logDebugTrace("XAServerResourceInfo.rollback(Xid[] xids)XAException during rollback", var16);
                     switch (var16.errorCode) {
                        case -7:
                           SystemException se = new SystemException("RM Failure attempting to rollback during recovery");
                           se.initCause(var16);
                           throw se;
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                           try {
                              if (getTM().getForgetHeuristics()) {
                                 if (this.rd.getPartitionName() == null) {
                                    xar.forget(xid);
                                 } else {
                                    this.partitionXaForget(xar, xid);
                                 }
                              } else {
                                 TXLogger.logForgetNotCalledOnRollbackHeur(this.getName(), xid.toString());
                              }
                           } catch (XAException var15) {
                           }
                     }
                  }
               }
            }

            return;
         }
      } catch (Exception var17) {
         if (var17 instanceof SystemException) {
            throw (SystemException)var17;
         }

         if (TxDebug.JTARecovery.isDebugEnabled()) {
            TxDebug.JTARecovery.debug((String)"recover: rollback(xids[])", (Throwable)var17);
         }

         throw new SystemException("Could not rollback. ex=" + var17);
      } finally {
         if (this.rd.isDeterminer()) {
            ((XAResourceDescriptor)this.rd).removeDeterminerXids(determinerXidsRemovalSet);
         }

      }

   }

   void commit(Xid[] xids) throws SystemException {
      HashSet determinerXidsRemovalSet = new HashSet();

      try {
         XAResource xar = this.getXAResource();
         if (xar == null) {
            throw new SystemException("No XAResource object registered. Yet");
         }

         if (xids == null) {
            return;
         }

         for(int i = 0; i < xids.length; ++i) {
            Xid xid = xids[i];

            try {
               if (xid != null) {
                  if (this.rd.getPartitionName() == null) {
                     xar.commit(xid, false);
                  } else {
                     this.partitionXaCommit(xar, xid);
                  }
               }

               if (this.rd.isDeterminer()) {
                  determinerXidsRemovalSet.add(xid);
               }

               TxDebug.JTA2PC.debug((TransactionImpl)getTM().getTransaction(xid), "XAServerResourceInfo.commit(Xid[] xids) Recovered and commit xar: " + xar + " xid:" + xid + "this:" + this);
            } catch (XAException var14) {
               if (var14.errorCode == -4) {
                  TxDebug.JTA2PC.debug((TransactionImpl)getTM().getTransaction(xid), "XAServerResourceInfo.commit(Xid[] xids) XAException XAER_NOTA during commit of xid:" + xid + ", continuing... isXidDeterminer:" + XAResourceDescriptor.isXidDeterminer(xid));
                  if (this.rd.isDeterminer()) {
                     determinerXidsRemovalSet.add(xid);
                  }
               } else {
                  TXLogger.logDebugTrace("XAException during commit", var14);
                  switch (var14.errorCode) {
                     case -7:
                        SystemException systemException = new SystemException("RM Failure attempting to commit during recovery");
                        systemException.initCause(var14);
                        throw systemException;
                     case 5:
                     case 6:
                     case 7:
                     case 8:
                        try {
                           if (getTM().getForgetHeuristics()) {
                              if (this.rd.getPartitionName() == null) {
                                 xar.forget(xid);
                              } else {
                                 this.partitionXaForget(xar, xid);
                              }
                           } else {
                              TXLogger.logForgetNotCalledOnCommitHeur(this.getName(), xid.toString());
                           }
                        } catch (XAException var13) {
                        }
                  }
               }
            }
         }
      } catch (Exception var15) {
         if (var15 instanceof SystemException) {
            throw (SystemException)var15;
         }

         if (TxDebug.JTARecovery.isDebugEnabled()) {
            TxDebug.JTARecovery.debug((String)"recover: commit(xids[])", (Throwable)var15);
         }

         throw new SystemException("Could not commit. ex=" + var15);
      } finally {
         ((XAResourceDescriptor)this.rd).removeDeterminerXids(determinerXidsRemovalSet);
      }

   }

   void setXAResource(XAResource xaRes) {
      this.xaResource = xaRes;
   }

   void setReRegistered(boolean isReRegistered) {
      this.reRegistered = isReRegistered;
   }

   boolean isEquivalentResource(XAResource xares) {
      XAResource xar = this.getXAResource();

      try {
         if (xares != null && xar != null) {
            boolean var10000;
            if (xar != xares) {
               label43: {
                  if (this.rd.getPartitionName() == null) {
                     if (xar.isSameRM(xares)) {
                        break label43;
                     }
                  } else if (this.partitionXaIsSameRM(xar, xares)) {
                     break label43;
                  }

                  var10000 = false;
                  return var10000;
               }
            }

            var10000 = true;
            return var10000;
         } else {
            return false;
         }
      } catch (Exception var4) {
         return false;
      }
   }

   Xid[] recover(CoordinatorDescriptor aCoDesc, boolean isCrossSiteRecovery) throws SystemException {
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("ServerResourceInfo[" + this.getName() + "].recover(coordinator=" + aCoDesc + ")");
      }

      synchronized(this.recoveryLock) {
         Xid[] var10000;
         try {
            List result = new LinkedList();
            int totalNumberOfXidsRecovered = 0;
            XAResource xar = this.getXAResource();
            if (xar == null) {
               throw new SystemException("No XAResource registered");
            }

            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("recover[" + this.getName() + "]:  recover(TMSTARTRSCAN)");
            }

            Xid[] xids = this.rd.getPartitionName() == null ? xar.recover(16777216) : this.partitionXaRecover(xar, 16777216);
            int len = xids == null ? 0 : xids.length;
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("recover[" + this.getName() + "]:  recover(TMSTARTRSCAN) returned " + len + " XIDs");
            }

            int i;
            while(len > 0) {
               totalNumberOfXidsRecovered += len;

               for(i = 0; i < len; ++i) {
                  if (belongsToCoordinator(xids[i], aCoDesc, isCrossSiteRecovery)) {
                     result.add(xids[i]);
                  }
               }

               if (TxDebug.JTAXA.isDebugEnabled()) {
                  TxDebug.JTAXA.debug("recover[" + this.getName() + "]:  recover(TMNOFLAGS)");
               }

               try {
                  xids = this.rd.getPartitionName() == null ? xar.recover(0) : this.partitionXaRecover(xar, 0);
               } catch (XAException var12) {
                  if (var12.errorCode == -6) {
                     xids = null;
                  }
               }

               len = xids == null ? 0 : xids.length;
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  TxDebug.JTAXA.debug("recover[" + this.getName() + "]:  recover(TMNOFLAGS) returned " + len + " XIDs");
               }
            }

            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("recover[" + this.getName() + "]:  recover(TMENDRSCAN)");
            }

            try {
               xids = this.rd.getPartitionName() == null ? xar.recover(8388608) : this.partitionXaRecover(xar, 8388608);
            } catch (XAException var11) {
            }

            len = xids == null ? 0 : xids.length;
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("recover[" + this.getName() + "]:  recover(TMENDRSCAN) returned " + len + " XIDs");
            }

            if (len > 0) {
               totalNumberOfXidsRecovered += len;

               for(i = 0; i < len; ++i) {
                  if (belongsToCoordinator(xids[i], aCoDesc, false)) {
                     result.add(xids[i]);
                  }
               }
            }

            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTAXA.debug("recover[" + this.getName() + "]: " + totalNumberOfXidsRecovered + " xids recovered, " + result.size() + " for coordinator " + aCoDesc);
            }

            if (result.size() == 0) {
               var10000 = null;
               return var10000;
            }

            var10000 = (Xid[])((Xid[])result.toArray(new Xid[0]));
         } catch (SystemException var13) {
            throw var13;
         } catch (Exception var14) {
            String msg = "XAResource.recover[" + this.getName() + "] failure: ";
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug((String)msg, (Throwable)var14);
            } else if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug((String)msg, (Throwable)var14);
            }

            if (var14 instanceof SystemException) {
               throw (SystemException)var14;
            }

            throw new SystemException(msg + var14.toString());
         }

         return var10000;
      }
   }

   protected String getStateAsString(int st) {
      switch (st) {
         case 1:
            return "new";
         case 2:
            return "started";
         case 3:
            return "delisted";
         case 4:
            return "suspended";
         case 5:
            return "ended";
         case 6:
            return "prepared";
         case 7:
            return "committed";
         case 8:
            return "rolledback";
         default:
            return "**** UNKNOWN STATE *** " + st;
      }
   }

   private void setPrepared(int aVote) {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         InstrumentationSupport.createDynamicJoinPoint(var2);
         InstrumentationSupport.process(var2);
         var2.resetPostBegin();
      }

      this.setState((byte)6);
      this.vote = aVote;
   }

   private void setVote(int aVote) {
      this.vote = aVote;
   }

   private void setStarted() {
      this.setState((byte)2);
   }

   private void setEnded() {
      this.setState((byte)5);
   }

   private void setDelisted(int delistFlag) {
      this.setState((byte)3);
      this.lastDelistFlag = delistFlag;
   }

   private void setSuspended() {
      this.setState((byte)4);
   }

   private int getAndResetDelistFlag() {
      XAResource xar = this.getXAResource();
      int retval;
      if (xar != null && xar instanceof weblogic.transaction.XAResource) {
         weblogic.transaction.XAResource wxar = (weblogic.transaction.XAResource)xar;
         retval = this.rd.getPartitionName() == null ? wxar.getDelistFlag() : this.partitionXaDelistFlag(wxar);
      } else {
         retval = this.lastDelistFlag;
         this.lastDelistFlag = 33554432;
      }

      return retval;
   }

   private XAResource getXAResource() {
      XAResourceDescriptor xard;
      if (this.rd != null && this.rd instanceof XAResourceDescriptor) {
         xard = (XAResourceDescriptor)this.rd;
         if (this.isReRegistered()) {
            this.xaResource = xard.getXAResource();
            return this.xaResource;
         }
      }

      if (this.xaResource != null) {
         return this.xaResource;
      } else {
         if (this.rd != null && this.rd instanceof XAResourceDescriptor) {
            xard = (XAResourceDescriptor)this.rd;
            this.xaResource = xard.getXAResource();
         }

         return this.xaResource;
      }
   }

   private XAResourceDescriptor getXAResourceDescriptor() {
      return (XAResourceDescriptor)this.rd;
   }

   private void xaStart(ServerTransactionImpl tx, int flags) throws SystemException, AbortRequestedException {
      XAException xae = null;
      Xid bXid = this.getXIDwithBranch((XidImpl)tx.getXID());

      try {
         this.start(tx, bXid, flags);
      } catch (XAException var8) {
         xae = var8;
         if (var8.errorCode == -8 && flags == 0) {
            try {
               this.start(tx, bXid, 2097152);
            } catch (XAException var7) {
               xae = var7;
            }
         }
      }

      if (xae == null) {
         this.setStarted();
      } else {
         String msg = "start() failed on resource '" + this.getName() + "': " + XAResourceHelper.xaErrorCodeToString(xae.errorCode) + "\n" + PlatformHelper.getPlatformHelper().throwable2StackTrace(xae);
         if (!this.getXAResourceDescriptor().needsStaticEnlistment(false)) {
            SystemException se = new SystemException(msg);
            se.errorCode = xae.errorCode;
            throw se;
         }

         if (tx.getTimeToLiveMillis() <= 0L) {
            tx.setRollbackOnly(new TimedOutException(tx));
         }

         tx.abort(msg, xae);
      }

   }

   private void start(ServerTransactionImpl tx, Xid xid, int flags) throws XAException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var11.argsCapture) {
            var11.args = InstrumentationSupport.toSensitive(4);
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.process(var11);
         var11.resetPostBegin();
      }

      XAResource xar = this.getXAResource();
      XAException xae = null;
      TransactionImpl saveTx = null;
      String dbgMsg = null;
      boolean var17 = false;

      label192: {
         String str;
         label193: {
            label194: {
               try {
                  var17 = true;
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     txtrace(TxDebug.JTAXA, tx, "XA.start(rm=" + this.getName() + ", xar=" + xar + ", flags=" + flagsToString(flags) + ")");
                  }

                  if (TxDebug.JTAXAStackTrace.isDebugEnabled()) {
                     TxDebug.debugStack(TxDebug.JTAXAStackTrace, tx + " XA.start(rm=" + this.getName() + "), xar=" + xar + ", flags=" + flagsToString(flags) + ")");
                  }

                  if (TxDebug.JTAResourceHealth.isDebugEnabled()) {
                     dbgMsg = PlatformHelper.getPlatformHelper().throwable2StackTrace(new Exception("DEBUG"));
                  }

                  this.checkForReRegistration(getTM().getLocalCoordinatorDescriptor());
                  this.getXAResourceDescriptor().setXAResourceTransactionTimeoutIfAppropriate(xar, tx.getTimeoutSeconds());
                  saveTx = getTM().internalSuspend();
                  this.getXAResourceDescriptor().startResourceUse(tx, dbgMsg);
                  if (this.rd.getPartitionName() == null) {
                     xar.start(this.getAppropriateXid(tx, xid, "start"), flags);
                  } else {
                     this.partitionXaStart(tx, xar, xid, flags);
                  }

                  this.startThread = Thread.currentThread();
                  var17 = false;
                  break label193;
               } catch (XAException var18) {
                  xae = var18;
                  var17 = false;
               } catch (OutOfMemoryError var19) {
                  throw var19;
               } catch (ThreadDeath var20) {
                  throw var20;
               } catch (Throwable var21) {
                  xae = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':" + var21.getMessage() + PlatformHelper.getPlatformHelper().throwable2StackTrace(var21));
                  xae.errorCode = -3;
                  xae.initCause(var21);
                  var17 = false;
                  break label194;
               } finally {
                  if (var17) {
                     getTM().internalResume(saveTx);
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        String str = "(rm=" + this.getName() + ", xar=" + xar;
                        if (xae != null) {
                           str = XAUtils.appendOracleXAResourceInfo(xae, str);
                           TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.start FAILED " + str, xae);
                        } else {
                           TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.start DONE " + str);
                        }
                     }

                     this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
                  }
               }

               getTM().internalResume(saveTx);
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  str = "(rm=" + this.getName() + ", xar=" + xar;
                  if (xae != null) {
                     str = XAUtils.appendOracleXAResourceInfo(xae, str);
                     TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.start FAILED " + str, xae);
                  } else {
                     TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.start DONE " + str);
                  }
               }

               this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
               break label192;
            }

            getTM().internalResume(saveTx);
            if (TxDebug.JTAXA.isDebugEnabled()) {
               str = "(rm=" + this.getName() + ", xar=" + xar;
               if (xae != null) {
                  str = XAUtils.appendOracleXAResourceInfo(xae, str);
                  TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.start FAILED " + str, xae);
               } else {
                  TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.start DONE " + str);
               }
            }

            this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
            break label192;
         }

         getTM().internalResume(saveTx);
         if (TxDebug.JTAXA.isDebugEnabled()) {
            str = "(rm=" + this.getName() + ", xar=" + xar;
            if (xae != null) {
               str = XAUtils.appendOracleXAResourceInfo(xae, str);
               TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.start FAILED " + str, xae);
            } else {
               TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.start DONE " + str);
            }
         }

         this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
      }

      if (xae != null) {
         throw xae;
      }
   }

   private void end(ServerTransactionImpl tx, Xid xid, int flags) throws XAException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var11.argsCapture) {
            var11.args = InstrumentationSupport.toSensitive(4);
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.process(var11);
         var11.resetPostBegin();
      }

      XAResource xar = this.getXAResource();
      XAException xae = null;
      TransactionImpl saveTx = null;
      String dbgMsg = null;
      boolean var17 = false;

      label187: {
         String str;
         label188: {
            label189: {
               try {
                  var17 = true;
                  saveTx = getTM().internalSuspend();
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     txtrace(TxDebug.JTAXA, tx, "XA.end(rm=" + this.getName() + ", xar=" + xar + ", flags=" + flagsToString(flags) + ")");
                  }

                  if (TxDebug.JTAXAStackTrace.isDebugEnabled()) {
                     TxDebug.debugStack(TxDebug.JTAXAStackTrace, tx + " XA.end(rm=" + this.getName() + "), xar=" + xar + ", flags=" + flagsToString(flags) + ")");
                  }

                  if (TxDebug.JTAResourceHealth.isDebugEnabled()) {
                     dbgMsg = PlatformHelper.getPlatformHelper().throwable2StackTrace(new Exception("DEBUG"));
                  }

                  this.getXAResourceDescriptor().startResourceUse(dbgMsg);
                  if (this.rd.getPartitionName() == null) {
                     xar.end(this.getAppropriateXid(tx, xid, "end"), flags);
                     var17 = false;
                  } else {
                     this.partitionXaEnd(tx, xar, xid, flags);
                     var17 = false;
                  }
                  break label188;
               } catch (XAException var18) {
                  xae = var18;
                  var17 = false;
               } catch (OutOfMemoryError var19) {
                  throw var19;
               } catch (ThreadDeath var20) {
                  throw var20;
               } catch (Throwable var21) {
                  xae = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':" + var21.getMessage() + PlatformHelper.getPlatformHelper().throwable2StackTrace(var21));
                  xae.errorCode = -3;
                  var17 = false;
                  break label189;
               } finally {
                  if (var17) {
                     this.startThread = null;
                     getTM().internalResume(saveTx);
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        String str = "(rm=" + this.getName() + ", xar=" + xar;
                        if (xae != null) {
                           str = XAUtils.appendOracleXAResourceInfo(xae, str);
                           TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.end FAILED " + str, xae);
                        } else {
                           TxDebug.JTAXA.debug((TransactionImpl)tx, (String)("XA.end DONE " + str));
                        }
                     }

                     this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
                  }
               }

               this.startThread = null;
               getTM().internalResume(saveTx);
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  str = "(rm=" + this.getName() + ", xar=" + xar;
                  if (xae != null) {
                     str = XAUtils.appendOracleXAResourceInfo(xae, str);
                     TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.end FAILED " + str, xae);
                  } else {
                     TxDebug.JTAXA.debug((TransactionImpl)tx, (String)("XA.end DONE " + str));
                  }
               }

               this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
               break label187;
            }

            this.startThread = null;
            getTM().internalResume(saveTx);
            if (TxDebug.JTAXA.isDebugEnabled()) {
               str = "(rm=" + this.getName() + ", xar=" + xar;
               if (xae != null) {
                  str = XAUtils.appendOracleXAResourceInfo(xae, str);
                  TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.end FAILED " + str, xae);
               } else {
                  TxDebug.JTAXA.debug((TransactionImpl)tx, (String)("XA.end DONE " + str));
               }
            }

            this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
            break label187;
         }

         this.startThread = null;
         getTM().internalResume(saveTx);
         if (TxDebug.JTAXA.isDebugEnabled()) {
            str = "(rm=" + this.getName() + ", xar=" + xar;
            if (xae != null) {
               str = XAUtils.appendOracleXAResourceInfo(xae, str);
               TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.end FAILED " + str, xae);
            } else {
               TxDebug.JTAXA.debug((TransactionImpl)tx, (String)("XA.end DONE " + str));
            }
         }

         this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
      }

      if (xae != null) {
         throw xae;
      }
   }

   private int prepare(ServerTransactionImpl tx, Xid xid) throws XAException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var11.argsCapture) {
            var11.args = InstrumentationSupport.toSensitive(3);
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.process(var11);
         var11.resetPostBegin();
      }

      XAResource xar = this.getXAResource();
      int rtn = 0;
      XAException xae = null;
      TransactionImpl saveTx = null;
      String dbgMsg = null;
      boolean var17 = false;

      label244: {
         String str;
         label245: {
            label246: {
               try {
                  var17 = true;
                  saveTx = getTM().internalSuspend();
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     txtrace(TxDebug.JTAXA, tx, "XA.prepare(rm=" + this.getName() + ", xar=" + xar);
                  }

                  if (TxDebug.JTAResourceHealth.isDebugEnabled()) {
                     dbgMsg = PlatformHelper.getPlatformHelper().throwable2StackTrace(new Exception("DEBUG"));
                  }

                  XAResourceDescriptor xaResourceDescriptor = this.getXAResourceDescriptor();
                  xaResourceDescriptor.startResourceUse(tx, dbgMsg);
                  if (this.rd.getPartitionName() == null) {
                     rtn = xar.prepare(this.getAppropriateXid(tx, xid, "prepare"));
                  } else {
                     rtn = this.partitionXaPrepare(tx, xar, xid);
                  }

                  if (rtn == 3) {
                     if (xaResourceDescriptor.isDeterminer()) {
                        if (xaResourceDescriptor.isDeterminerOfGivenTransaction(tx)) {
                           tx.setRDONLYReturnFromDeterminer();
                           var17 = false;
                        } else {
                           var17 = false;
                        }
                     } else {
                        var17 = false;
                     }
                  } else {
                     var17 = false;
                  }
                  break label245;
               } catch (XAException var18) {
                  xae = var18;
                  var17 = false;
                  break label246;
               } catch (OutOfMemoryError var19) {
                  throw var19;
               } catch (ThreadDeath var20) {
                  throw var20;
               } catch (Throwable var21) {
                  xae = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':" + var21.getMessage() + PlatformHelper.getPlatformHelper().throwable2StackTrace(var21));
                  xae.errorCode = -3;
                  var17 = false;
               } finally {
                  if (var17) {
                     getTM().internalResume(saveTx);
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        String str = "(rm=" + this.getName() + ", xar=" + xar;
                        if (xae != null) {
                           str = XAUtils.appendOracleXAResourceInfo(xae, str);
                           TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.prepare FAILED " + str, xae);
                        } else {
                           TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.prepare DONE:" + (rtn == 0 ? "ok" : "rdonly"), xae);
                        }
                     }

                     this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
                  }
               }

               getTM().internalResume(saveTx);
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  str = "(rm=" + this.getName() + ", xar=" + xar;
                  if (xae != null) {
                     str = XAUtils.appendOracleXAResourceInfo(xae, str);
                     TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.prepare FAILED " + str, xae);
                  } else {
                     TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.prepare DONE:" + (rtn == 0 ? "ok" : "rdonly"), xae);
                  }
               }

               this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
               break label244;
            }

            getTM().internalResume(saveTx);
            if (TxDebug.JTAXA.isDebugEnabled()) {
               str = "(rm=" + this.getName() + ", xar=" + xar;
               if (xae != null) {
                  str = XAUtils.appendOracleXAResourceInfo(xae, str);
                  TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.prepare FAILED " + str, xae);
               } else {
                  TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.prepare DONE:" + (rtn == 0 ? "ok" : "rdonly"), xae);
               }
            }

            this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
            break label244;
         }

         getTM().internalResume(saveTx);
         if (TxDebug.JTAXA.isDebugEnabled()) {
            str = "(rm=" + this.getName() + ", xar=" + xar;
            if (xae != null) {
               str = XAUtils.appendOracleXAResourceInfo(xae, str);
               TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.prepare FAILED " + str, xae);
            } else {
               TxDebug.txdebug(TxDebug.JTAXA, tx, "XA.prepare DONE:" + (rtn == 0 ? "ok" : "rdonly"), xae);
            }
         }

         this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
      }

      if (xae != null) {
         if (TxDebug.isIsDebugConditionalResourcePrepareException) {
            tx.printDebugMessages();
         }

         throw xae;
      } else {
         return rtn;
      }
   }

   private void commit(ServerTransactionImpl tx, Xid xid, boolean onePhase, boolean retry) throws XAException {
      LocalHolder var18;
      if ((var18 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var18.argsCapture) {
            var18.args = InstrumentationSupport.toSensitive(5);
         }

         InstrumentationSupport.createDynamicJoinPoint(var18);
         InstrumentationSupport.process(var18);
         var18.resetPostBegin();
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].commit(xid=" + xid + ",onePhase=" + onePhase + ",retry=" + retry + ")"));
         TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("ServerResourceInfo[" + this.getName() + "].commit " + tx));
      }

      if (INSTR_ENABLED) {
         tx.check("ResourceBeforeCommit", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
      }

      XAResource xar = this.getXAResource();
      XAException xae = null;
      TransactionImpl saveTx = null;
      String dbgMsg = null;
      boolean var25 = false;

      label439: {
         XAException xaException;
         String str;
         HashMap xaResourcesCommittedTimeMap;
         String firstCommitResource;
         Serializable xaResourcesCommittedTime;
         label440: {
            label441: {
               try {
                  var25 = true;
                  saveTx = getTM().internalSuspend();
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     TxDebug.JTA2PC.debug((TransactionImpl)tx, (String)("XAResource[" + this.getName() + "].commit(xid=" + xid + ",onePhase=" + onePhase + ")"));
                  }

                  if (TxDebug.JTAResourceHealth.isDebugEnabled()) {
                     dbgMsg = PlatformHelper.getPlatformHelper().throwable2StackTrace(new Exception("DEBUG"));
                  }

                  this.getXAResourceDescriptor().startResourceUse(dbgMsg);

                  try {
                     if (!tx.isReadOnly) {
                        if (this.rd.getPartitionName() == null) {
                           xar.commit(this.getAppropriateXid(tx, xid, "commit"), onePhase);
                           var25 = false;
                        } else {
                           this.partitionXaCommit(tx, xar, xid, onePhase);
                           var25 = false;
                        }
                     } else {
                        var25 = false;
                     }
                  } catch (XAException var26) {
                     if (!retry || var26.errorCode != -4 || this.getName().getBytes().length <= 60) {
                        throw var26;
                     }

                     if (this.rd.getPartitionName() == null) {
                        xar.commit(this.getXIDwithOldBranch((XidImpl)xid), onePhase);
                        var25 = false;
                     } else {
                        this.partitionXaCommit(xar, xid, onePhase);
                        var25 = false;
                     }
                  }
                  break label440;
               } catch (XAException var27) {
                  xae = var27;
                  var25 = false;
               } catch (OutOfMemoryError var28) {
                  throw var28;
               } catch (ThreadDeath var29) {
                  throw var29;
               } catch (Throwable var30) {
                  xae = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':" + var30.getMessage() + PlatformHelper.getPlatformHelper().throwable2StackTrace(var30));
                  xae.errorCode = -3;
                  var25 = false;
                  break label441;
               } finally {
                  if (var25) {
                     XAException xaException = null;
                     getTM().internalResume(saveTx);
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        String str = "(rm=" + this.getName() + ", xar=" + xar;
                        if (xae != null) {
                           str = XAUtils.appendOracleXAResourceInfo(xae, str);
                           xatxtrace(TxDebug.JTAXA, tx, "XA.commit FAILED " + str, xae);
                        } else {
                           txtrace(TxDebug.JTAXA, tx, "XA.commit DONE " + str);
                        }
                     }

                     if (tx.getProperty("weblogic.transaction.first.resource.commitServer") != null) {
                        Object xaResourcesCommittedTime = tx.getProperty("weblogic.transaction.resourceCommittedTime");
                        HashMap xaResourcesCommittedTimeMap = xaResourcesCommittedTime == null ? new HashMap() : (HashMap)xaResourcesCommittedTime;
                        String firstCommitResource = tx.getFirstResourceToCommit();
                        if (this.getName().equals(firstCommitResource) && xaResourcesCommittedTimeMap.size() > 0) {
                           xaException = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':getFirstCommitResource did not commit first, preceding comitted resources:" + xaResourcesCommittedTime);
                           xaException.errorCode = -3;
                        }

                        xaResourcesCommittedTimeMap.put(this.getName(), System.currentTimeMillis());
                        tx.setProperty("weblogic.transaction.resourceCommittedTime", xaResourcesCommittedTimeMap);
                     }

                     this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
                     if (INSTR_ENABLED) {
                        tx.check("ResourceAfterCommit", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
                     }

                     if (xae == null && xaException != null) {
                        ;
                     }

                  }
               }

               xaException = null;
               getTM().internalResume(saveTx);
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  str = "(rm=" + this.getName() + ", xar=" + xar;
                  if (xae != null) {
                     str = XAUtils.appendOracleXAResourceInfo(xae, str);
                     xatxtrace(TxDebug.JTAXA, tx, "XA.commit FAILED " + str, xae);
                  } else {
                     txtrace(TxDebug.JTAXA, tx, "XA.commit DONE " + str);
                  }
               }

               if (tx.getProperty("weblogic.transaction.first.resource.commitServer") != null) {
                  xaResourcesCommittedTime = tx.getProperty("weblogic.transaction.resourceCommittedTime");
                  xaResourcesCommittedTimeMap = xaResourcesCommittedTime == null ? new HashMap() : (HashMap)xaResourcesCommittedTime;
                  firstCommitResource = tx.getFirstResourceToCommit();
                  if (this.getName().equals(firstCommitResource) && xaResourcesCommittedTimeMap.size() > 0) {
                     xaException = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':getFirstCommitResource did not commit first, preceding comitted resources:" + xaResourcesCommittedTime);
                     xaException.errorCode = -3;
                  }

                  xaResourcesCommittedTimeMap.put(this.getName(), System.currentTimeMillis());
                  tx.setProperty("weblogic.transaction.resourceCommittedTime", xaResourcesCommittedTimeMap);
               }

               this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
               if (INSTR_ENABLED) {
                  tx.check("ResourceAfterCommit", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
               }

               if (xae == null && xaException != null) {
                  xae = xaException;
               }
               break label439;
            }

            xaException = null;
            getTM().internalResume(saveTx);
            if (TxDebug.JTAXA.isDebugEnabled()) {
               str = "(rm=" + this.getName() + ", xar=" + xar;
               if (xae != null) {
                  str = XAUtils.appendOracleXAResourceInfo(xae, str);
                  xatxtrace(TxDebug.JTAXA, tx, "XA.commit FAILED " + str, xae);
               } else {
                  txtrace(TxDebug.JTAXA, tx, "XA.commit DONE " + str);
               }
            }

            if (tx.getProperty("weblogic.transaction.first.resource.commitServer") != null) {
               xaResourcesCommittedTime = tx.getProperty("weblogic.transaction.resourceCommittedTime");
               xaResourcesCommittedTimeMap = xaResourcesCommittedTime == null ? new HashMap() : (HashMap)xaResourcesCommittedTime;
               firstCommitResource = tx.getFirstResourceToCommit();
               if (this.getName().equals(firstCommitResource) && xaResourcesCommittedTimeMap.size() > 0) {
                  xaException = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':getFirstCommitResource did not commit first, preceding comitted resources:" + xaResourcesCommittedTime);
                  xaException.errorCode = -3;
               }

               xaResourcesCommittedTimeMap.put(this.getName(), System.currentTimeMillis());
               tx.setProperty("weblogic.transaction.resourceCommittedTime", xaResourcesCommittedTimeMap);
            }

            this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
            if (INSTR_ENABLED) {
               tx.check("ResourceAfterCommit", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
            }

            if (xae == null && xaException != null) {
               xae = xaException;
            }
            break label439;
         }

         xaException = null;
         getTM().internalResume(saveTx);
         if (TxDebug.JTAXA.isDebugEnabled()) {
            str = "(rm=" + this.getName() + ", xar=" + xar;
            if (xae != null) {
               str = XAUtils.appendOracleXAResourceInfo(xae, str);
               xatxtrace(TxDebug.JTAXA, tx, "XA.commit FAILED " + str, xae);
            } else {
               txtrace(TxDebug.JTAXA, tx, "XA.commit DONE " + str);
            }
         }

         if (tx.getProperty("weblogic.transaction.first.resource.commitServer") != null) {
            xaResourcesCommittedTime = tx.getProperty("weblogic.transaction.resourceCommittedTime");
            xaResourcesCommittedTimeMap = xaResourcesCommittedTime == null ? new HashMap() : (HashMap)xaResourcesCommittedTime;
            firstCommitResource = tx.getFirstResourceToCommit();
            if (this.getName().equals(firstCommitResource) && xaResourcesCommittedTimeMap.size() > 0) {
               xaException = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':getFirstCommitResource did not commit first, preceding comitted resources:" + xaResourcesCommittedTime);
               xaException.errorCode = -3;
            }

            xaResourcesCommittedTimeMap.put(this.getName(), System.currentTimeMillis());
            tx.setProperty("weblogic.transaction.resourceCommittedTime", xaResourcesCommittedTimeMap);
         }

         this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
         if (INSTR_ENABLED) {
            tx.check("ResourceAfterCommit", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
         }

         if (xae == null && xaException != null) {
            xae = xaException;
         }
      }

      if (xae != null) {
         if (TxDebug.isIsDebugConditionalResourceCommitRollbackException) {
            tx.printDebugMessages();
         }

         throw xae;
      }
   }

   private boolean rollback(ServerTransactionImpl tx, Xid xid) throws XAException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         txtrace(TxDebug.JTA2PC, tx, "ServerResourceInfo[" + this.getName() + "].rollback(tx, xid)");
      }

      if (INSTR_ENABLED) {
         tx.check("ResourceBeforeRollback", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
      }

      XAResource xar = this.getXAResource();
      if (xar == null) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            txtrace(TxDebug.JTA2PC, tx, "ServerResourceInfo[" + this.getName() + "].rollback(tx, xid): no XAResource available");
         }

         return false;
      } else {
         XAException xae = null;
         TransactionImpl saveTx = null;
         String dbgMsg = null;
         boolean var15 = false;

         label256: {
            String str;
            label257: {
               label258: {
                  try {
                     var15 = true;
                     saveTx = getTM().internalSuspend();
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        txtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback()");
                     }

                     if (TxDebug.JTAResourceHealth.isDebugEnabled()) {
                        dbgMsg = PlatformHelper.getPlatformHelper().throwable2StackTrace(new Exception("DEBUG"));
                     }

                     this.getXAResourceDescriptor().startResourceUse(dbgMsg);
                     if (this.rd.getPartitionName() == null) {
                        xar.rollback(this.getAppropriateXid(tx, xid, "rollback"));
                        var15 = false;
                     } else {
                        this.partitionXaRollback(tx, xar, xid);
                        var15 = false;
                     }
                     break label257;
                  } catch (XAException var16) {
                     xae = var16;
                     var15 = false;
                  } catch (OutOfMemoryError var17) {
                     throw var17;
                  } catch (ThreadDeath var18) {
                     throw var18;
                  } catch (Throwable var19) {
                     xae = new XAException("XAResource[" + this.getName() + "]: Unexpected exception " + var19.getClass() + ": " + var19.getMessage() + PlatformHelper.getPlatformHelper().throwable2StackTrace(var19));
                     xae.errorCode = -3;
                     var15 = false;
                     break label258;
                  } finally {
                     if (var15) {
                        if (saveTx != null) {
                           getTM().internalResume(saveTx);
                        }

                        String str = "(rm=" + this.getName() + ", xar=" + xar;
                        if (TxDebug.JTAXA.isDebugEnabled()) {
                           if (xae != null) {
                              str = XAUtils.appendOracleXAResourceInfo(xae, str);
                              xatxtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback: FAILED " + str, xae);
                           } else {
                              txtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback: SUCCESS " + str);
                           }
                        }

                        this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
                        if (INSTR_ENABLED) {
                           tx.check("ResourceAfterRollback", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
                        }

                     }
                  }

                  if (saveTx != null) {
                     getTM().internalResume(saveTx);
                  }

                  str = "(rm=" + this.getName() + ", xar=" + xar;
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     if (xae != null) {
                        str = XAUtils.appendOracleXAResourceInfo(xae, str);
                        xatxtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback: FAILED " + str, xae);
                     } else {
                        txtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback: SUCCESS " + str);
                     }
                  }

                  this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
                  if (INSTR_ENABLED) {
                     tx.check("ResourceAfterRollback", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
                  }
                  break label256;
               }

               if (saveTx != null) {
                  getTM().internalResume(saveTx);
               }

               str = "(rm=" + this.getName() + ", xar=" + xar;
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  if (xae != null) {
                     str = XAUtils.appendOracleXAResourceInfo(xae, str);
                     xatxtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback: FAILED " + str, xae);
                  } else {
                     txtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback: SUCCESS " + str);
                  }
               }

               this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
               if (INSTR_ENABLED) {
                  tx.check("ResourceAfterRollback", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
               }
               break label256;
            }

            if (saveTx != null) {
               getTM().internalResume(saveTx);
            }

            str = "(rm=" + this.getName() + ", xar=" + xar;
            if (TxDebug.JTAXA.isDebugEnabled()) {
               if (xae != null) {
                  str = XAUtils.appendOracleXAResourceInfo(xae, str);
                  xatxtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback: FAILED " + str, xae);
               } else {
                  txtrace(TxDebug.JTAXA, tx, "XAResource[" + this.getName() + "].rollback: SUCCESS " + str);
               }
            }

            this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
            if (INSTR_ENABLED) {
               tx.check("ResourceAfterRollback", getTM().getLocalCoordinatorDescriptor().getServerName(), this.getName());
            }
         }

         if (xae != null) {
            if (TxDebug.isIsDebugConditionalResourceCommitRollbackException) {
               tx.printDebugMessages();
            }

            throw xae;
         } else {
            return true;
         }
      }
   }

   private void forget(ServerTransactionImpl tx, Xid xid) throws XAException {
      XAResource xar = this.getXAResource();
      XAException xae = null;
      TransactionImpl saveTx = null;
      String dbgMsg = null;
      boolean var15 = false;

      label158: {
         String str;
         label159: {
            label160: {
               try {
                  var15 = true;
                  saveTx = getTM().internalSuspend();
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     txtrace(TxDebug.JTAXA, tx, "XA.forget(rm=" + this.getName() + ", xar=" + xar);
                  }

                  if (TxDebug.JTAResourceHealth.isDebugEnabled()) {
                     dbgMsg = PlatformHelper.getPlatformHelper().throwable2StackTrace(new Exception("DEBUG"));
                  }

                  this.getXAResourceDescriptor().startResourceUse(dbgMsg);
                  if (this.rd.getPartitionName() == null) {
                     xar.forget(this.getAppropriateXid(tx, xid, "forget"));
                     var15 = false;
                  } else {
                     this.partitionXaForget(tx, xar, xid);
                     var15 = false;
                  }
                  break label159;
               } catch (XAException var16) {
                  xae = var16;
                  var15 = false;
               } catch (OutOfMemoryError var17) {
                  throw var17;
               } catch (ThreadDeath var18) {
                  throw var18;
               } catch (Throwable var19) {
                  xae = new XAException("Unexpected Exception for XAResource '" + this.getName() + "':" + var19.getMessage() + PlatformHelper.getPlatformHelper().throwable2StackTrace(var19));
                  xae.errorCode = -3;
                  var15 = false;
                  break label160;
               } finally {
                  if (var15) {
                     getTM().internalResume(saveTx);
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        String str = "(rm=" + this.getName() + ", xar=" + xar;
                        if (xae != null) {
                           str = XAUtils.appendOracleXAResourceInfo(xae, str);
                           xatxtrace(TxDebug.JTAXA, tx, "XA.forget FAILED " + str, xae);
                        } else {
                           txtrace(TxDebug.JTAXA, tx, "XA.forget DONE " + str);
                        }
                     }

                     this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
                  }
               }

               getTM().internalResume(saveTx);
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  str = "(rm=" + this.getName() + ", xar=" + xar;
                  if (xae != null) {
                     str = XAUtils.appendOracleXAResourceInfo(xae, str);
                     xatxtrace(TxDebug.JTAXA, tx, "XA.forget FAILED " + str, xae);
                  } else {
                     txtrace(TxDebug.JTAXA, tx, "XA.forget DONE " + str);
                  }
               }

               this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
               break label158;
            }

            getTM().internalResume(saveTx);
            if (TxDebug.JTAXA.isDebugEnabled()) {
               str = "(rm=" + this.getName() + ", xar=" + xar;
               if (xae != null) {
                  str = XAUtils.appendOracleXAResourceInfo(xae, str);
                  xatxtrace(TxDebug.JTAXA, tx, "XA.forget FAILED " + str, xae);
               } else {
                  txtrace(TxDebug.JTAXA, tx, "XA.forget DONE " + str);
               }
            }

            this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
            break label158;
         }

         getTM().internalResume(saveTx);
         if (TxDebug.JTAXA.isDebugEnabled()) {
            str = "(rm=" + this.getName() + ", xar=" + xar;
            if (xae != null) {
               str = XAUtils.appendOracleXAResourceInfo(xae, str);
               xatxtrace(TxDebug.JTAXA, tx, "XA.forget FAILED " + str, xae);
            } else {
               txtrace(TxDebug.JTAXA, tx, "XA.forget DONE " + str);
            }
         }

         this.getXAResourceDescriptor().endResourceUse(xae, dbgMsg);
      }

      if (xae != null) {
         throw xae;
      }
   }

   static boolean belongsToCoordinator(Xid axid, CoordinatorDescriptor aCo, boolean isCrossSiteRecovery) {
      if (axid.getFormatId() == XIDFactory.getFormatId()) {
         byte[] gtrid = axid.getGlobalTransactionId();
         byte[] aCoURLHash = aCo.getURLHash();
         if (hashEquals(gtrid, aCoURLHash, isCrossSiteRecovery ? CoordinatorDescriptor.getURLHash(getTM().getRecoverySiteName()) : aCo.getSiteNameHash())) {
            return true;
         } else {
            aCoURLHash = aCo.get60URLHash();
            return hashEquals(gtrid, aCoURLHash, (byte[])null);
         }
      } else {
         return false;
      }
   }

   Xid getAppropriateXid(ServerTransactionImpl tx, Xid xid, String xaCommandString) {
      boolean isTightlyCoupledInflowedTx = getTM().isTightlyCoupledTransactionsEnabled() && tx.getForeignXid() != null;
      if (!isTightlyCoupledInflowedTx) {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug((TransactionImpl)tx, (String)("XAServerResourceInfo.getAppropriateXid for this:" + this + " xaCommandString :" + xaCommandString + " isXidDeterminer :" + XAResourceDescriptor.isXidDeterminer(xid) + " for loosely coupled transaction xid :" + xid + " xid.getBranchQualifier():" + byteArrayToString(xid.getBranchQualifier())));
         }

         return xid;
      } else {
         Xid foreignXid = tx.getForeignXid();
         byte[] branchqual = xid.getBranchQualifier();
         byte[] branchqualWithPrefix = new byte[branchqual.length > 63 ? branchqual.length : branchqual.length + 1];
         branchqualWithPrefix[0] = 119;
         System.arraycopy(branchqual, 0, branchqualWithPrefix, 1, branchqual.length);
         Xid tightlyCoupledXid = new XidImpl(foreignXid.getFormatId(), foreignXid.getGlobalTransactionId(), branchqualWithPrefix);
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug((TransactionImpl)tx, (String)("XAServerResourceInfo.getAppropriateXid for " + xaCommandString + " for tightly coupled transaction xid :" + tightlyCoupledXid + "xid.getBranchQualifier():" + byteArrayToString(tightlyCoupledXid.getBranchQualifier())));
         }

         return tightlyCoupledXid;
      }
   }

   private static String byteArrayToString(byte[] barray) {
      if (barray == null) {
         return "";
      } else {
         char[] res = new char[barray.length * 2];
         int j = 0;
         byte[] var3 = barray;
         int var4 = barray.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte aBarray = var3[var5];
            res[j++] = DIGITS[(aBarray & 240) >>> 4];
            res[j++] = DIGITS[aBarray & 15];
         }

         return new String(res);
      }
   }

   static boolean hashEquals(byte[] gtrid, byte[] coURLHashBytes, byte[] siteNameHashBytes) {
      int expectedLength = XidImpl.getUIDLength() + coURLHashBytes.length + (siteNameHashBytes == null ? 0 : siteNameHashBytes.length);
      if (gtrid.length != expectedLength) {
         return false;
      } else {
         int pos;
         if (siteNameHashBytes == null) {
            for(pos = XidImpl.getUIDLength(); pos < XidImpl.getUIDLength() + coURLHashBytes.length; ++pos) {
               if (coURLHashBytes[pos - XidImpl.getUIDLength()] != gtrid[pos]) {
                  return false;
               }
            }
         } else {
            for(pos = XidImpl.getUIDLength() + coURLHashBytes.length; pos < XidImpl.getUIDLength() + coURLHashBytes.length + siteNameHashBytes.length; ++pos) {
               if (siteNameHashBytes[pos - (XidImpl.getUIDLength() + coURLHashBytes.length)] != gtrid[pos]) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private static String flagsToString(int flags) {
      switch (flags) {
         case 0:
            return "TMNOFLAGS";
         case 2097152:
            return "TMJOIN";
         case 33554432:
            return "TMSUSPEND";
         case 67108864:
            return "TMSUCCESS";
         case 134217728:
            return "TMRESUME";
         case 536870912:
            return "TMFAIL";
         case 1073741824:
            return "TMONEPHASE";
         default:
            return Integer.toHexString(flags).toUpperCase(Locale.ENGLISH);
      }
   }

   private static void txtrace(Object logger, ServerTransactionImpl tx, String msg) {
      PlatformHelper.getPlatformHelper().txtrace(logger, tx, msg);
   }

   private static void xatxtrace(Object logger, ServerTransactionImpl tx, String msg, XAException ex) {
      PlatformHelper.getPlatformHelper().xatxtrace(logger, tx, msg, ex);
   }

   void partitionXaForget(XAResource xar, Xid xid) throws XAException {
      final Xid axid = xid;
      final XAResource axar = xar;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, (ServerTransactionImpl)null, "partition xa forget xar:" + xar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException {
               axar.forget(axid);
               return null;
            }
         });
      } catch (ExecutionException var7) {
         if (var7.getCause() instanceof XAException) {
            XAException xae = (XAException)var7.getCause();
            throw xae;
         } else {
            IllegalStateException ise;
            if (var7.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var7.getCause();
               throw ise;
            } else if (var7.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var7.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var7.getCause());
               ise.initCause(var7.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionXaForget(ServerTransactionImpl tx, XAResource xar, Xid xid) throws XAException, OutOfMemoryError, ThreadDeath, Throwable {
      final ServerTransactionImpl atx = tx;
      final Xid axid = xid;
      final XAResource axar = xar;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, tx, "partition xa forget xar:" + xar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException, OutOfMemoryError, ThreadDeath {
               axar.forget(XAServerResourceInfo.this.getAppropriateXid(atx, axid, "forget"));
               return null;
            }
         });
      } catch (ExecutionException var9) {
         if (var9.getCause() instanceof XAException) {
            XAException xae = (XAException)var9.getCause();
            throw xae;
         } else if (var9.getCause() instanceof OutOfMemoryError) {
            throw var9.getCause();
         } else if (var9.getCause() instanceof ThreadDeath) {
            throw var9.getCause();
         } else if (var9.getCause() instanceof Throwable) {
            throw var9.getCause();
         } else {
            IllegalStateException ise;
            if (var9.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var9.getCause();
               throw ise;
            } else if (var9.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var9.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var9.getCause());
               ise.initCause(var9.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionXaStart(ServerTransactionImpl tx, XAResource xar, Xid xid, int flags) throws XAException, OutOfMemoryError, ThreadDeath, Throwable {
      final ServerTransactionImpl atx = tx;
      final Xid axid = xid;
      final XAResource axar = xar;
      final int aflags = flags;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, tx, "partition xa start xar:" + xar + " xid:" + xid + " flags:" + flags + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException {
               axar.start(XAServerResourceInfo.this.getAppropriateXid(atx, axid, "start"), aflags);
               return null;
            }
         });
      } catch (ExecutionException var11) {
         if (var11.getCause() instanceof XAException) {
            XAException xae = (XAException)var11.getCause();
            throw xae;
         } else if (var11.getCause() instanceof OutOfMemoryError) {
            throw var11.getCause();
         } else if (var11.getCause() instanceof ThreadDeath) {
            throw var11.getCause();
         } else if (var11.getCause() instanceof Throwable) {
            throw var11.getCause();
         } else {
            IllegalStateException ise;
            if (var11.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var11.getCause();
               throw ise;
            } else if (var11.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var11.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var11.getCause());
               ise.initCause(var11.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionXaEnd(ServerTransactionImpl tx, XAResource xar, Xid xid, int flags) throws XAException, OutOfMemoryError, ThreadDeath, Throwable {
      final ServerTransactionImpl atx = tx;
      final Xid axid = xid;
      final XAResource axar = xar;
      final int aflags = flags;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, tx, "partition xa end xar:" + xar + " xid:" + xid + " flag:" + flags + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException {
               axar.end(XAServerResourceInfo.this.getAppropriateXid(atx, axid, "end"), aflags);
               return null;
            }
         });
      } catch (ExecutionException var11) {
         if (var11.getCause() instanceof XAException) {
            XAException xae = (XAException)var11.getCause();
            throw xae;
         } else if (var11.getCause() instanceof OutOfMemoryError) {
            throw var11.getCause();
         } else if (var11.getCause() instanceof ThreadDeath) {
            throw var11.getCause();
         } else if (var11.getCause() instanceof Throwable) {
            throw var11.getCause();
         } else {
            IllegalStateException ise;
            if (var11.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var11.getCause();
               throw ise;
            } else if (var11.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var11.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var11.getCause());
               ise.initCause(var11.getCause());
               throw ise;
            }
         }
      }
   }

   int partitionXaPrepare(ServerTransactionImpl tx, XAResource xar, Xid xid) throws XAException, OutOfMemoryError, ThreadDeath, Throwable {
      final ServerTransactionImpl atx = tx;
      final Xid axid = xid;
      final XAResource axar = xar;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, tx, "partition xa prepare xar:" + xar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         return (Integer)ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Integer call() throws XAException {
               return axar.prepare(XAServerResourceInfo.this.getAppropriateXid(atx, axid, "prepare"));
            }
         });
      } catch (ExecutionException var9) {
         if (var9.getCause() instanceof XAException) {
            XAException xae = (XAException)var9.getCause();
            throw xae;
         } else if (var9.getCause() instanceof OutOfMemoryError) {
            throw var9.getCause();
         } else if (var9.getCause() instanceof ThreadDeath) {
            throw var9.getCause();
         } else if (var9.getCause() instanceof Throwable) {
            throw var9.getCause();
         } else {
            IllegalStateException ise;
            if (var9.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var9.getCause();
               throw ise;
            } else if (var9.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var9.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var9.getCause());
               ise.initCause(var9.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionXaRollback(ServerTransactionImpl tx, XAResource xar, Xid xid) throws XAException, OutOfMemoryError, ThreadDeath, Throwable {
      final ServerTransactionImpl atx = tx;
      final Xid axid = xid;
      final XAResource axar = xar;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, tx, "partition xa rollback xar:" + xar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException, OutOfMemoryError, ThreadDeath {
               axar.rollback(XAServerResourceInfo.this.getAppropriateXid(atx, axid, "rollback"));
               return null;
            }
         });
      } catch (ExecutionException var9) {
         if (var9.getCause() instanceof XAException) {
            XAException xae = (XAException)var9.getCause();
            throw xae;
         } else if (var9.getCause() instanceof OutOfMemoryError) {
            throw var9.getCause();
         } else if (var9.getCause() instanceof ThreadDeath) {
            throw var9.getCause();
         } else if (var9.getCause() instanceof Throwable) {
            throw var9.getCause();
         } else {
            IllegalStateException ise;
            if (var9.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var9.getCause();
               throw ise;
            } else if (var9.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var9.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var9.getCause());
               ise.initCause(var9.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionXaRollback(XAResource xar, Xid xid) throws XAException, OutOfMemoryError, ThreadDeath {
      final Xid axid = xid;
      final XAResource axar = xar;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, (ServerTransactionImpl)null, "partition xa rollback xar:" + xar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException, OutOfMemoryError, ThreadDeath {
               axar.rollback(axid);
               return null;
            }
         });
      } catch (ExecutionException var7) {
         if (var7.getCause() instanceof XAException) {
            XAException xae = (XAException)var7.getCause();
            throw xae;
         } else {
            IllegalStateException ise;
            if (var7.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var7.getCause();
               throw ise;
            } else if (var7.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var7.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var7.getCause());
               ise.initCause(var7.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionXaCommit(ServerTransactionImpl tx, XAResource xar, Xid xid, boolean onePhase) throws XAException, OutOfMemoryError, ThreadDeath, Throwable {
      final ServerTransactionImpl atx = tx;
      final Xid axid = xid;
      final XAResource axar = xar;
      final boolean aonePhase = onePhase;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, tx, "partition xa commit xar:" + xar + " xid:" + xid + " onePhase:" + onePhase + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException {
               axar.commit(XAServerResourceInfo.this.getAppropriateXid(atx, axid, "commit"), aonePhase);
               return null;
            }
         });
      } catch (ExecutionException var11) {
         if (var11.getCause() instanceof XAException) {
            XAException xae = (XAException)var11.getCause();
            throw xae;
         } else if (var11.getCause() instanceof OutOfMemoryError) {
            throw var11.getCause();
         } else if (var11.getCause() instanceof ThreadDeath) {
            throw var11.getCause();
         } else if (var11.getCause() instanceof Throwable) {
            throw var11.getCause();
         } else {
            IllegalStateException ise;
            if (var11.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var11.getCause();
               throw ise;
            } else if (var11.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var11.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var11.getCause());
               ise.initCause(var11.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionXaCommit(XAResource xar, Xid xid) throws XAException {
      final Xid axid = xid;
      final XAResource axar = xar;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, (ServerTransactionImpl)null, "partition xa commit xar:" + xar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException {
               axar.commit(axid, false);
               return null;
            }
         });
      } catch (ExecutionException var7) {
         if (var7.getCause() instanceof XAException) {
            XAException xae = (XAException)var7.getCause();
            throw xae;
         } else {
            IllegalStateException ise;
            if (var7.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var7.getCause();
               throw ise;
            } else if (var7.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var7.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var7.getCause());
               ise.initCause(var7.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionXaCommit(XAResource xar, Xid xid, boolean onePhase) throws XAException, OutOfMemoryError, ThreadDeath, Throwable {
      final Xid axid = xid;
      final XAResource axar = xar;
      final boolean aonePhase = onePhase;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, (ServerTransactionImpl)null, "partition xa commit xar:" + xar + " xid:" + xid + " onePhase:" + onePhase + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws XAException {
               axar.commit(XAServerResourceInfo.this.getXIDwithOldBranch((XidImpl)axid), aonePhase);
               return null;
            }
         });
      } catch (ExecutionException var9) {
         if (var9.getCause() instanceof XAException) {
            XAException xae = (XAException)var9.getCause();
            throw xae;
         } else if (var9.getCause() instanceof OutOfMemoryError) {
            throw var9.getCause();
         } else if (var9.getCause() instanceof ThreadDeath) {
            throw var9.getCause();
         } else if (var9.getCause() instanceof Throwable) {
            throw var9.getCause();
         } else {
            IllegalStateException ise;
            if (var9.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var9.getCause();
               throw ise;
            } else if (var9.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var9.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var9.getCause());
               ise.initCause(var9.getCause());
               throw ise;
            }
         }
      }
   }

   boolean partitionXaIsSameRM(XAResource xar, XAResource xares) throws XAException {
      final XAResource axar = xar;
      final XAResource axares = xares;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, (ServerTransactionImpl)null, "partition xa isSameRM xar:" + xar + " axares:" + xares + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         return (Boolean)ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Boolean call() throws XAException {
               Boolean isSame = axar.isSameRM(axares);
               return isSame;
            }
         });
      } catch (ExecutionException var7) {
         if (var7.getCause() instanceof XAException) {
            throw (XAException)var7.getCause();
         } else {
            IllegalStateException ise;
            if (var7.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var7.getCause();
               throw ise;
            } else if (var7.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var7.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var7.getCause());
               ise.initCause(var7.getCause());
               throw ise;
            }
         }
      }
   }

   Xid[] partitionXaRecover(XAResource xar, int xaFlag) throws XAException {
      final XAResource axar = xar;
      final int axaFlag = xaFlag;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, (ServerTransactionImpl)null, "partition xa recover xar:" + xar + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         return (Xid[])ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Xid[] call() throws XAException {
               Xid[] xids = axar.recover(axaFlag);
               return xids;
            }
         });
      } catch (ExecutionException var7) {
         if (var7.getCause() instanceof XAException) {
            throw (XAException)var7.getCause();
         } else {
            IllegalStateException ise;
            if (var7.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var7.getCause();
               throw ise;
            } else if (var7.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var7.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var7.getCause());
               ise.initCause(var7.getCause());
               throw ise;
            }
         }
      }
   }

   int partitionXaDelistFlag(weblogic.transaction.XAResource wxar) {
      final weblogic.transaction.XAResource awxar = wxar;
      if (TxDebug.JTAXA.isDebugEnabled()) {
         txtrace(TxDebug.JTAXA, (ServerTransactionImpl)null, "partition xa delistFlag xar:" + wxar + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         return (Integer)ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Integer call() {
               Integer retval = awxar.getDelistFlag();
               return retval;
            }
         });
      } catch (ExecutionException var5) {
         IllegalStateException ise;
         if (var5.getCause() instanceof IllegalStateException) {
            ise = (IllegalStateException)var5.getCause();
            throw ise;
         } else if (var5.getCause() instanceof SecurityException) {
            SecurityException se = (SecurityException)var5.getCause();
            throw se;
         } else {
            ise = new IllegalStateException(var5.getCause());
            ise.initCause(var5.getCause());
            throw ise;
         }
      }
   }

   static {
      _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Start_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JTA_Diagnostic_XA_Resourceinfo_Start_Before_High");
      _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Prepare_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JTA_Diagnostic_XA_Resourceinfo_Prepare_Before_High");
      _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Commit_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JTA_Diagnostic_XA_Resourceinfo_Commit_Before_High");
      _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Prepared_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JTA_Diagnostic_XA_Resourceinfo_Prepared_Before_High");
      _WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_End_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JTA_Diagnostic_XA_Resourceinfo_End_Before_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XAServerResourceInfo.java", "weblogic.transaction.internal.XAServerResourceInfo", "setPrepared", "(I)V", 1262, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Prepared_Before_High};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XAServerResourceInfo.java", "weblogic.transaction.internal.XAServerResourceInfo", "start", "(Lweblogic/transaction/internal/ServerTransactionImpl;Ljavax/transaction/xa/Xid;I)V", 1365, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Start_Before_High};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XAServerResourceInfo.java", "weblogic.transaction.internal.XAServerResourceInfo", "end", "(Lweblogic/transaction/internal/ServerTransactionImpl;Ljavax/transaction/xa/Xid;I)V", 1432, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_End_Before_High};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XAServerResourceInfo.java", "weblogic.transaction.internal.XAServerResourceInfo", "prepare", "(Lweblogic/transaction/internal/ServerTransactionImpl;Ljavax/transaction/xa/Xid;)I", 1485, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Prepare_Before_High};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XAServerResourceInfo.java", "weblogic.transaction.internal.XAServerResourceInfo", "commit", "(Lweblogic/transaction/internal/ServerTransactionImpl;Ljavax/transaction/xa/Xid;ZZ)V", 1546, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JTA_Diagnostic_XA_Resourceinfo_Commit_Before_High};
      String propVal = System.getProperty("weblogic.transaction.EnableInstrumentedTM");
      INSTR_ENABLED = propVal != null && propVal.equals("true");
      kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   }
}
