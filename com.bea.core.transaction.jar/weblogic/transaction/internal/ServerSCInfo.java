package weblogic.transaction.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.nonxa.NonXAException;

public final class ServerSCInfo extends SCInfo {
   private Map beforeSyncs = null;
   private Map afterSyncs = null;
   private static final boolean isPiggybackLocalCommitEnabledSysProp = Boolean.valueOf(System.getProperty("weblogic.transaction.isPiggybackLocalCommitEnabled", "false"));
   static boolean isPiggybackLocalCommitEnabled = false;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean isBeforeCompletionConductedAlready = false;
   private boolean isAfterCompletionConductedAlready = false;
   private static final boolean useTXThreadCLSyspropEnabled = Boolean.valueOf(System.getProperty("weblogic.transaction.useTXThreadCL", "true"));

   ServerSCInfo(ServerCoordinatorDescriptor aCoDesc) {
      super((CoordinatorDescriptor)aCoDesc);
   }

   ServerSCInfo(String aSCURL) {
      super((CoordinatorDescriptor)((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getOrCreate(aSCURL));
   }

   final void startPrePrepareAndChain(ServerTransactionImpl tx, int hopsLeft) throws AbortRequestedException {
      boolean restorePriorContextNeeded = false;
      this.setPrePreparing();
      if (this.isLocal()) {
         TransactionImpl saveTx = null;
         if (this.isSyncRegistered()) {
            boolean var10 = false;

            SystemException se;
            try {
               var10 = true;
               if (getTM().getTransaction() != tx) {
                  saveTx = getTM().internalResume(tx);
                  se = tx.enlistStaticallyEnlistedResources(true);
                  if (se != null) {
                     throw new AbortRequestedException("Unable to enlist static resources while processing beforeCompletion callbacks: " + se.toString());
                  }

                  restorePriorContextNeeded = true;
               }

               while(true) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, tx, "before completion iteration #" + tx.getBeforeCompletionIterationCount());
                  }

                  this.callBeforeCompletions(tx);
                  tx.finishedBeforeCompletionIteration();
                  if (tx.getBeforeCompletionIterationCount() > getTM().getBeforeCompletionIterationLimit()) {
                     tx.abort("Exceeded BeforeCompletionIteration limit -- " + getTM().getBeforeCompletionIterationLimit());
                  }

                  if (tx.isCancelled()) {
                     tx.abort();
                  }

                  if (!this.hasBeforeCompletions()) {
                     var10 = false;
                     break;
                  }
               }
            } finally {
               if (var10) {
                  if (restorePriorContextNeeded) {
                     tx.delistAllStaticResources(33554432, false);
                     getTM().internalResume(saveTx);
                     if (saveTx != null) {
                        SystemException se = tx.enlistStaticallyEnlistedResources(true);
                        if (se != null && TxDebug.JTA2PC.isDebugEnabled()) {
                           TxDebug.txdebug(TxDebug.JTA2PC, saveTx, "Error while attempting to enlist static resources after processing beforeCompletion callbacks for " + tx + ": exception=" + se);
                        }
                     }
                  }

               }
            }

            if (restorePriorContextNeeded) {
               tx.delistAllStaticResources(33554432, false);
               getTM().internalResume(saveTx);
               if (saveTx != null) {
                  se = tx.enlistStaticallyEnlistedResources(true);
                  if (se != null && TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, saveTx, "Error while attempting to enlist static resources after processing beforeCompletion callbacks for " + tx + ": exception=" + se);
                  }
               }
            }
         }

         tx.enforceCheckedTransaction();
         this.setPrePrepared();
         List scList = tx.getSCInfoList();

         for(int i = 0; i < scList.size(); ++i) {
            ServerSCInfo sci = (ServerSCInfo)scList.get(i);
            if (!sci.isSyncRegistered()) {
               sci.setPrePrepared();
            } else if (!sci.isPrePrepared()) {
               sci.startPrePrepareAndChain(tx, hopsLeft);
               return;
            }
         }

      } else {
         SubCoordinatorOneway sc = this.getSubCoordinator(tx);
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerSCInfo.startPrePrepareAndChain sc:" + sc + " getSub1:" + this.getScUrl(tx) + " tx:" + tx + " coDesc" + this.getCoordinatorDescriptor());
         }

         if (sc == null) {
            tx.abort("SubCoordinator '" + this.getScUrl(tx) + "' not available");
         }

         try {
            tx.delistAll(33554432);
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "sc(" + this.getScUrl(tx) + ").startPrePrepareAndChain");
            }

            this.setPrePreparing();
            SecureAction.runAction(this.getKernelID(), new StartPrePrepareAndChain(tx.getRequestPropagationContext(), hopsLeft, sc), this.getScServerURL(), "sc.startPrePrepareAndChain");
         } catch (Exception var12) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "Failed sc(" + this.getScUrl(tx) + ").startPrePrepareAndChain", var12);
            }

            if (var12 instanceof RemoteException) {
               ResourceDescriptor.shunSC(this.getCoordinatorDescriptor());
            }

            tx.abort("Unable to contact server '" + this.getScUrl(tx) + "'", var12);
         }

      }
   }

   final void addSync(ServerTransactionImpl tx, Synchronization s) {
      this.addSync(tx, s, (InterpositionTier)null);
   }

   final void addSync(ServerTransactionImpl tx, Synchronization s, InterpositionTier tier) {
      Map syncs = this.getOrCreateBeforeSyncs();
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, tx, "Before ServerSCInfo.addSync, Number of Sync:" + syncs.size());
      }

      Iterator i = syncs.keySet().iterator();
      int isInList = 0;

      while(i.hasNext()) {
         RegisteredSync aftersync = (RegisteredSync)i.next();
         if (aftersync.sync == s) {
            ++isInList;
         }
      }

      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(this.getKernelID());
      ComponentInvocationContext componentInvocationContext = null;
      if (isInList == 0) {
         String partitionName = (String)tx.getProperty("weblogic.transaction.partitionName");
         if (partitionName != null && !partitionName.equals("DOMAIN")) {
            componentInvocationContext = PlatformHelper.getPlatformHelper().getCurrentComponentInvocationContext();
         }

         syncs.put(new RegisteredSync(s, subject, componentInvocationContext, tier), (Object)null);
         this.syncRegistered = true;
         if (this.isPrePrepared()) {
            this.setPrePreparing();
         }
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, tx, "After ServerSCInfo.addSync, Number of Sync:" + syncs.size() + " sync=" + s + " cic:" + componentInvocationContext);
      }

   }

   final void registerInterposedSynchronization(ServerTransactionImpl tx, Synchronization s, InterpositionTier tier) {
      ComponentInvocationContext componentInvocationContext = null;
      String partitionName = (String)tx.getProperty("weblogic.transaction.partitionName");
      if (partitionName != null && !partitionName.equals("DOMAIN")) {
         componentInvocationContext = PlatformHelper.getPlatformHelper().getCurrentComponentInvocationContext();
      }

      this.addSync(tx, s, tier);
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, tx, "ServerSCInfo.registerInterposedSynchronization on tier " + tier + " cic:" + componentInvocationContext);
      }

   }

   final void startPrepare(final ServerTransactionImpl tx) throws AbortRequestedException {
      if (this.isLocal()) {
         int numCommit = 0;
         ArrayList resourceList = tx.getResourceInfoList();
         if (resourceList == null) {
            this.setPrepared(3);
         } else {
            boolean isNoTLOGEnabled;
            int i;
            if (getTM().getParallelXAEnabled()) {
               ServerResourceInfo[] assignedXAResources = this.getAssignedXAResources(tx);
               if (assignedXAResources == null) {
                  this.setPrepared(3);
                  return;
               }

               this.orderResources(tx, assignedXAResources, "startPrepare");
               final boolean[] prepareOk = new boolean[assignedXAResources.length];
               final ServerCoordinatorDescriptor.Barrier barrier = new ServerCoordinatorDescriptor.Barrier(assignedXAResources.length - 1);
               if (assignedXAResources.length > 1) {
                  for(final int i = 1; i < assignedXAResources.length; ++i) {
                     final XAServerResourceInfo fsri = (XAServerResourceInfo)assignedXAResources[i];
                     Runnable runnable = new Runnable() {
                        public void run() {
                           try {
                              if (TxDebug.JTA2PC.isDebugEnabled()) {
                                 TxDebug.txdebug(TxDebug.JTA2PC, tx, "executing prepare for resource " + fsri.getName() + " in parallel thread " + Thread.currentThread());
                              }

                              if (fsri.prepare(tx) == 0) {
                                 prepareOk[i] = true;
                              }
                           } catch (AbortRequestedException var5) {
                           } finally {
                              barrier.signal();
                           }

                        }
                     };
                     if (!PlatformHelper.getPlatformHelper().executeIfIdleOnParallelXAWorkManager(runnable, getTM().getParallelXADispatchPolicy())) {
                        runnable.run();
                     }
                  }
               }

               isNoTLOGEnabled = tx.getDeterminer() != null;
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, tx, "startPrepare isNoTLOGEnabled=" + isNoTLOGEnabled);
               }

               if (isNoTLOGEnabled) {
                  barrier.await();
               }

               AbortRequestedException abortRequested = null;
               if (tx.isMarkedRollback() && isNoTLOGEnabled) {
                  throw new AbortRequestedException();
               }

               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, tx, "executing prepare for resource " + assignedXAResources[0].getName() + " in this thread " + Thread.currentThread());
               }

               try {
                  if (((XAServerResourceInfo)assignedXAResources[0]).prepare(tx) == 0) {
                     prepareOk[0] = true;
                  }
               } catch (AbortRequestedException var12) {
                  abortRequested = var12;
               }

               if (!isNoTLOGEnabled) {
                  barrier.await();
               }

               if (abortRequested != null) {
                  throw abortRequested;
               }

               if (tx.isMarkedRollback()) {
                  throw new AbortRequestedException();
               }

               for(i = 0; i < assignedXAResources.length; ++i) {
                  if (prepareOk[i]) {
                     this.setPrepared(0);
                     return;
                  }
               }

               this.setPrepared(3);
            } else {
               int resSize = 0;
               Iterator var17 = resourceList.iterator();

               while(var17.hasNext()) {
                  Object aResourceList = var17.next();
                  ServerResourceInfo ri = (ServerResourceInfo)aResourceList;
                  if (ri.isAssignedTo(this) && ri instanceof XAServerResourceInfo) {
                     ++resSize;
                  }
               }

               for(int i = 0; i < resourceList.size(); ++i) {
                  ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);
                  if (ri.isAssignedTo(this) && ri instanceof XAServerResourceInfo) {
                     isNoTLOGEnabled = tx.isLogWriteNecessary();
                     boolean isNotLastResourceOfRdOnlyTx = isNoTLOGEnabled || i != resSize - 1 || !tx.isCoordinatingTransaction();
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, tx, "ServerSCInfo.startPrepare noParallel isLog:" + isNoTLOGEnabled + " isNotLastResourceOfRdOnlyTx:" + isNotLastResourceOfRdOnlyTx + " i: " + i + " len:" + resSize + " isCoordinatingtx:" + tx.isCoordinatingTransaction());
                     }

                     if (isNotLastResourceOfRdOnlyTx) {
                        i = ((XAServerResourceInfo)ri).prepare(tx);
                        if (i == 0) {
                           ++numCommit;
                        }
                     }
                  }
               }

               this.setPrepared(numCommit > 0 ? 0 : 3);
            }

         }
      } else {
         String[] assignedResources = this.getAssignedResourceNames(tx);
         if (assignedResources == null) {
            this.setPrepared(3);
         }

         SubCoordinatorOneway sc = this.getSubCoordinator(tx);
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerSCInfo.startPrepare sc:" + sc + " getSub2:" + this.getScUrl(tx) + " tx:" + tx + " coDesc" + this.getCoordinatorDescriptor());
         }

         if (sc == null) {
            tx.abort("SubCoordinator '" + this.getScUrl(tx) + "' not available");
         }

         try {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "sc (" + this.getScUrl(tx) + ").startPrepare");
            }

            this.setPreparing();
            if (sc != null) {
               SecureAction.runAction(this.getKernelID(), new StartPrepareAction(sc, tx, assignedResources), this.getScServerURL(), "sc.startPrepare");
            }
         } catch (Exception var13) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "Failed sc (" + this.getScUrl(tx) + ").startPrepare", var13);
            }

            if (var13 instanceof RemoteException) {
               ResourceDescriptor.shunSC(this.getCoordinatorDescriptor());
            }

            tx.abort("Unable to contact server '" + this.getScUrl(tx) + "'", var13);
         }

      }
   }

   final void startCommit(final ServerTransactionImpl tx, final boolean onePhase, final boolean retry) throws AbortRequestedException {
      if (this.isLocal()) {
         ArrayList resourceList = tx.getResourceInfoList();
         if (resourceList == null) {
            this.setCommitted();
         } else {
            boolean allResourcesCommitted;
            if (getTM().getParallelXAEnabled()) {
               if (!onePhase) {
                  ServerResourceInfo[] assignedXAResources = this.getAssignedXAResources(tx);
                  if (assignedXAResources == null) {
                     this.setCommitted();
                     return;
                  }

                  this.orderResources(tx, assignedXAResources, "startCommit");
                  final boolean[] commitOk = new boolean[assignedXAResources.length];
                  final ServerCoordinatorDescriptor.Barrier barrier = new ServerCoordinatorDescriptor.Barrier(assignedXAResources.length - 1);
                  boolean isFirstResourceinCommit = tx.getFirstResourceToCommit() != null;
                  if (isFirstResourceinCommit) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.txdebug(TxDebug.JTA2PC, tx, "executing commit for first resource commit resource " + assignedXAResources[0].getName() + " in this thread " + Thread.currentThread());
                     }

                     commitOk[0] = ((XAServerResourceInfo)assignedXAResources[0]).commit(tx, onePhase, retry, false);
                  }

                  if (assignedXAResources.length > 1) {
                     for(final int i = 1; i < assignedXAResources.length; ++i) {
                        final XAServerResourceInfo fxasri = (XAServerResourceInfo)assignedXAResources[i];
                        commitOk[i] = true;
                        Runnable runnable = new Runnable() {
                           public void run() {
                              try {
                                 if (TxDebug.JTA2PC.isDebugEnabled()) {
                                    TxDebug.txdebug(TxDebug.JTA2PC, tx, "executing commit for resource " + fxasri.getName() + " in thread " + Thread.currentThread());
                                 }

                                 try {
                                    if (!fxasri.commit(tx, onePhase, retry, false)) {
                                       commitOk[i] = false;
                                    }
                                 } catch (AbortRequestedException var5) {
                                 }
                              } finally {
                                 if (TxDebug.JTA2PC.isDebugEnabled()) {
                                    TxDebug.txdebug(TxDebug.JTA2PC, tx, "about to barrier.signal, exiting commit for resource " + fxasri.getName() + " in thread " + Thread.currentThread());
                                 }

                                 barrier.signal();
                              }

                           }
                        };
                        if (!PlatformHelper.getPlatformHelper().executeIfIdleOnParallelXAWorkManager(runnable, getTM().getParallelXADispatchPolicy())) {
                           runnable.run();
                        }
                     }
                  }

                  boolean isNoTLOGEnabled = tx.getDeterminer() != null;
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, tx, "startCommit isNoTLOGEnabled=" + isNoTLOGEnabled);
                  }

                  if (isNoTLOGEnabled) {
                     barrier.await();
                  }

                  if (isNoTLOGEnabled) {
                     for(int i = 1; i < assignedXAResources.length; ++i) {
                        if (!commitOk[i]) {
                           return;
                        }
                     }
                  }

                  AbortRequestedException abortRequested = null;
                  if (!isFirstResourceinCommit && TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.txdebug(TxDebug.JTA2PC, tx, "executing commit for resource " + assignedXAResources[0].getName() + " in this thread " + Thread.currentThread());
                  }

                  try {
                     if (!isFirstResourceinCommit) {
                        commitOk[0] = ((XAServerResourceInfo)assignedXAResources[0]).commit(tx, onePhase, retry, false);
                     }
                  } catch (AbortRequestedException var16) {
                     abortRequested = var16;
                  }

                  if (!isNoTLOGEnabled) {
                     barrier.await();
                  }

                  if (abortRequested != null) {
                     throw abortRequested;
                  }

                  for(int i = 0; i < assignedXAResources.length; ++i) {
                     if (!commitOk[i]) {
                        return;
                     }
                  }

                  this.setCommitted();
               } else {
                  allResourcesCommitted = true;

                  for(int i = 0; i < resourceList.size(); ++i) {
                     ServerResourceInfo ri = (ServerResourceInfo)resourceList.get(i);
                     if (ri instanceof XAServerResourceInfo) {
                        XAServerResourceInfo xari = (XAServerResourceInfo)ri;
                        if (xari.isAssignedTo(this) && !xari.commit(tx, onePhase, retry, false)) {
                           allResourcesCommitted = false;
                        }
                     } else if (onePhase && ri instanceof NonXAServerResourceInfo) {
                        NonXAServerResourceInfo nxari = (NonXAServerResourceInfo)ri;
                        if (nxari.isAssignedTo(this)) {
                           try {
                              if (!tx.getLocalConnCommittedOrRollback()) {
                                 nxari.commit(tx, onePhase, false, this.isLocal());
                              } else {
                                 TxDebug.txdebug(TxDebug.JTANonXA, tx, "Forgoing commit on non-XAResource:" + nxari + " as it was committed or rolledback in synchronizatino ");
                              }
                           } catch (Exception var17) {
                              allResourcesCommitted = false;
                              if (TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled()) {
                                 TxDebug.txdebug(TxDebug.JTANonXA, tx, "sc (" + this.getScUrl(tx) + ").startCommit: NonXA error", var17);
                              }

                              throw new AbortRequestedException(var17.getMessage());
                           }
                        }
                     }
                  }

                  if (allResourcesCommitted) {
                     this.setCommitted();
                  }
               }
            } else {
               allResourcesCommitted = true;
               Iterator var25 = resourceList.iterator();

               while(var25.hasNext()) {
                  Object aResourceList = var25.next();
                  ServerResourceInfo ri = (ServerResourceInfo)aResourceList;
                  if (ri instanceof XAServerResourceInfo) {
                     XAServerResourceInfo xari = (XAServerResourceInfo)ri;
                     if (xari.isAssignedTo(this) && !xari.commit(tx, onePhase, retry, false)) {
                        allResourcesCommitted = false;
                     }
                  } else if (onePhase && ri instanceof NonXAServerResourceInfo) {
                     NonXAServerResourceInfo nxari = (NonXAServerResourceInfo)ri;
                     if (nxari.isAssignedTo(this)) {
                        try {
                           nxari.commit(tx, onePhase, false, this.isLocal());
                        } catch (Exception var18) {
                           allResourcesCommitted = false;
                           if (TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled()) {
                              TxDebug.txdebug(TxDebug.JTANonXA, tx, "sc (" + this.getScUrl(tx) + ").startCommit: NonXA error", var18);
                           }

                           throw new AbortRequestedException(var18.getMessage());
                        }
                     }
                  }
               }

               if (allResourcesCommitted) {
                  this.setCommitted();
               }
            }

         }
      } else {
         try {
            String[] assignedResources = this.getAssignedResourceNames(tx);
            if (assignedResources == null && this.isCommitted()) {
               return;
            }

            this.setCommitting();
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "sc (" + this.getScUrl(tx) + ").startCommit");
            }

            SubCoordinatorOneway sc = this.getSubCoordinator(tx);
            if (sc != null) {
               AuthenticatedSubject currentId = SecurityServiceManager.getCurrentSubject(this.getKernelID());
               SecureAction.runAction(this.getKernelID(), new StartCommitAction(sc, tx, assignedResources, onePhase, retry, currentId), this.getScServerURL(), "sc.startCommit");
            }
         } catch (Exception var19) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "Failed sc (" + this.getScUrl(tx) + ").startCommit", var19);
            }

            if (var19 instanceof RemoteException) {
               ResourceDescriptor.shunSC(this.getCoordinatorDescriptor());
            }
         }

      }
   }

   void orderResources(ServerTransactionImpl tx, ServerResourceInfo[] assignedXAResources, String debugString) {
      String resourceToOrderFirstInArray = tx.getDeterminer();
      if (resourceToOrderFirstInArray == null) {
         resourceToOrderFirstInArray = tx.getFirstResourceToCommit();
      }

      if (resourceToOrderFirstInArray != null) {
         for(int i = 0; i < assignedXAResources.length; ++i) {
            String name = assignedXAResources[i].getResourceDescriptor().getName();
            if (resourceToOrderFirstInArray.equals(name)) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, tx, "ServerSCInfo." + debugString + " found resource in assigned resources:" + resourceToOrderFirstInArray);
               }

               ServerResourceInfo tempSwapServerResourceInfo = assignedXAResources[0];
               assignedXAResources[0] = assignedXAResources[i];
               assignedXAResources[i] = tempSwapServerResourceInfo;
               break;
            }
         }

      }
   }

   final Xid[] recover(String resourceName, CoordinatorDescriptor aCoDesc, ResourceDescriptor resourceDescriptor) throws SystemException {
      return this.recover(resourceName, aCoDesc, resourceDescriptor, false);
   }

   final Xid[] recover(String resourceName, CoordinatorDescriptor aCoDesc, ResourceDescriptor resourceDescriptor, boolean isCrossSiteRecovery) throws SystemException {
      if (this.isLocal()) {
         XAServerResourceInfo ri = new XAServerResourceInfo(resourceName);
         Xid[] xids = ri.recover(aCoDesc, isCrossSiteRecovery);
         return xids;
      } else {
         SubCoordinator sc = (SubCoordinator)this.getSubCoordinator();

         try {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug("SC.Recover: " + resourceName + ", coordinatorURL=" + aCoDesc.getCoordinatorURL());
            }

            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerSCInfo.recover sc:" + sc + " getSub:" + this.getScUrl() + " coDesc" + this.getCoordinatorDescriptor() + " desc:" + aCoDesc.getCoordinatorURL());
            }

            if (sc == null) {
               throw new SystemException("Subcoordinator " + this.getScUrl() + " not available");
            } else {
               if (resourceDescriptor != null && sc instanceof SubCoordinatorRM) {
                  SubCoordinatorRM scrm = (SubCoordinatorRM)sc;
                  GetPropertiesAction getPropAction = new GetPropertiesAction(scrm, resourceName);
                  SecureAction.runAction(this.getKernelID(), getPropAction, this.getScServerURL(), "sc.getProperties");
                  Map rmProps = getPropAction.getProperties();
                  if (rmProps != null) {
                     resourceDescriptor.setProperties(rmProps);
                  }
               }

               RecoverAction recAction = new RecoverAction(sc, resourceName, aCoDesc.getCoordinatorURL());
               SecureAction.runAction(this.getKernelID(), recAction, this.getScServerURL(), "sc.recover");
               Xid[] ret = recAction.getXids();
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  int n = ret == null ? 0 : ret.length;
                  TxDebug.JTA2PC.debug("SC.Recover done: resourceName=" + resourceName + "." + n + " xids recovered");
               }

               return ret;
            }
         } catch (SystemException var9) {
            throw var9;
         } catch (RemoteException var10) {
            ResourceDescriptor.shunSC(this.getCoordinatorDescriptor());
            throw new SystemException("Trouble contacting subcoordinator: " + var10);
         } catch (Exception var11) {
            throw new SystemException("Trouble contacting subcoordinator: " + var11);
         }
      }
   }

   final void rollback(String resourceName, Xid[] xids) throws SystemException {
      if (this.isLocal()) {
         XAServerResourceInfo ri = new XAServerResourceInfo(resourceName);
         ri.rollback(xids);
      } else {
         SubCoordinator sc = (SubCoordinator)this.getSubCoordinator();
         if (sc != null) {
            try {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  StringBuilder sb = new StringBuilder(100);
                  sb.append("SC.Recover: ").append(resourceName);
                  if (xids == null) {
                     sb.append(" xids==null");
                  } else {
                     for(int i = 0; i < xids.length; ++i) {
                        if (i % 5 == 0) {
                           sb.append("\n");
                        }

                        sb.append(xids[i]).append(" ");
                     }
                  }

                  TxDebug.JTA2PC.debug(sb.toString());
               }

               SecureAction.runAction(this.getKernelID(), new RollbackAction(sc, resourceName, xids), this.getScServerURL(), "sc.rollback");
            } catch (Exception var6) {
               if (var6 instanceof RemoteException) {
                  ResourceDescriptor.shunSC(this.getCoordinatorDescriptor());
               }

               throw new SystemException("Trouble contacting server '" + this.getScUrl() + "': " + var6);
            }
         }
      }
   }

   final void commit(String resourceName, Xid[] xids) throws SystemException {
      if (this.isLocal()) {
         XAServerResourceInfo ri = new XAServerResourceInfo(resourceName);
         ri.commit(xids);
      } else {
         SubCoordinator sc = (SubCoordinator)this.getSubCoordinator();
         if (sc != null) {
            try {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  StringBuilder sb = new StringBuilder(100);
                  sb.append("SC.Recover: ").append(resourceName);

                  for(int i = 0; i < xids.length; ++i) {
                     if (i % 5 == 0) {
                        sb.append("\n");
                     }

                     sb.append(xids[i]).append(" ");
                  }

                  TxDebug.JTA2PC.debug(sb.toString());
               }

               SecureAction.runAction(this.getKernelID(), new CommitAction(sc, resourceName, xids), this.getScServerURL(), "sc.commit");
            } catch (Exception var6) {
               if (var6 instanceof RemoteException) {
                  ResourceDescriptor.shunSC(this.getCoordinatorDescriptor());
               }

               throw new SystemException("Trouble contacting server '" + this.getScUrl() + "': " + var6);
            }
         }
      }
   }

   final void forceLocalRollback(ServerTransactionImpl tx, boolean isOnePhaseTransaction) throws SystemException {
      if (this.isLocal()) {
         ArrayList resourceList = tx.getResourceInfoList();
         if (resourceList == null) {
            this.setRolledBack();
         } else {
            Iterator var9 = resourceList.iterator();

            while(var9.hasNext()) {
               Object aResourceList = var9.next();
               ServerResourceInfo ri = (ServerResourceInfo)aResourceList;
               if (ri.isAssignedTo(this)) {
                  ri.rollback(tx, true, isOnePhaseTransaction);
               }
            }

            this.setRolledBack();
         }
      } else {
         SubCoordinatorOneway sc = this.getSubCoordinator();
         if (!(sc instanceof SubCoordinatorOneway3)) {
            throw new SystemException("Unable to perform forceLocalRollback for Xid '" + tx.getXid() + "', the remote server '" + this.getScUrl(tx) + "' is an unsupported version");
         } else {
            SubCoordinatorOneway3 sco3 = (SubCoordinatorOneway3)sc;

            try {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.JTA2PC.debug("forced local rollback for remote SC " + this.getScUrl(tx));
               }

               SecureAction.runAction(this.getKernelID(), new ForceLocalRollbackAction(sco3, tx.getXid()), this.getScServerURL(), "sc.forceLocalRollback");
            } catch (Exception var7) {
               throw new SystemException("Error contacting server '" + this.getScUrl(tx) + "': " + var7);
            }
         }
      }
   }

   final void forceLocalCommit(ServerTransactionImpl tx) throws SystemException {
      if (this.isLocal()) {
         ArrayList resourceList = tx.getResourceInfoList();
         if (resourceList == null) {
            this.setCommitted();
         } else {
            Iterator var11 = resourceList.iterator();

            while(var11.hasNext()) {
               Object aResourceList = var11.next();
               ServerResourceInfo ri = (ServerResourceInfo)aResourceList;
               if (ri.isAssignedTo(this)) {
                  try {
                     if (ri instanceof XAServerResourceInfo) {
                        ((XAServerResourceInfo)ri).commit(tx, false, false, true);
                     } else {
                        try {
                           ((NonXAServerResourceInfo)ri).commit(tx, false, true, this.isLocal());
                        } catch (NonXAException var7) {
                        }
                     }
                  } catch (AbortRequestedException var8) {
                  }
               }
            }

            this.setCommitted();
         }
      } else {
         SubCoordinatorOneway sc = this.getSubCoordinator();
         if (!(sc instanceof SubCoordinatorOneway3)) {
            throw new SystemException("Unable to perform forceLocalCommit for Xid '" + tx.getXid() + "', the remote server '" + this.getScUrl(tx) + "' is an unsupported version");
         } else {
            SubCoordinatorOneway3 sco3 = (SubCoordinatorOneway3)sc;

            try {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.JTA2PC.debug("forced local rollback for remote SC " + this.getScUrl(tx));
               }

               PlatformHelper.getPlatformHelper().runSecureAction(new ForceLocalCommitAction(sco3, tx.getXid()), this.getScServerURL(), "sc.forceLocalCommit");
            } catch (Exception var9) {
               throw new SystemException("Trouble contacting server '" + this.getScUrl(tx) + "': " + var9);
            }
         }
      }
   }

   final void startRollback(final ServerTransactionImpl tx, final boolean isOnePhaseTransaction) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("ServerSCInfo.startRollback: " + tx);
      }

      boolean allMyResourcesRolledBack = true;
      if (this.isLocal()) {
         ArrayList resourceList = tx.getResourceInfoList();
         if (resourceList == null) {
            this.setRolledBack();
         } else {
            if (getTM().getParallelXAEnabled()) {
               ServerResourceInfo[] assignedResources = this.getAssignedResources(tx);
               if (assignedResources == null) {
                  this.setRolledBack();
                  return;
               }

               final ServerCoordinatorDescriptor.Barrier barrier = new ServerCoordinatorDescriptor.Barrier(assignedResources.length - 1);
               boolean isNoTLOGEnabled = tx.getDeterminer() != null;
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, tx, "startRollback isNoTLOGEnabled=" + isNoTLOGEnabled);
               }

               if (isNoTLOGEnabled) {
                  assignedResources[0].rollback(tx, false, isOnePhaseTransaction);
               }

               if (assignedResources.length > 1) {
                  for(int i = 1; i < assignedResources.length; ++i) {
                     final ServerResourceInfo fsri = assignedResources[i];
                     Runnable runnable = new Runnable() {
                        public void run() {
                           try {
                              if (TxDebug.JTA2PC.isDebugEnabled()) {
                                 TxDebug.txdebug(TxDebug.JTA2PC, tx, "executing rollback for resource " + fsri.getName() + " in thread " + Thread.currentThread());
                              }

                              fsri.rollback(tx, false, isOnePhaseTransaction);
                           } finally {
                              barrier.signal();
                           }

                        }
                     };
                     if (!PlatformHelper.getPlatformHelper().executeIfIdleOnParallelXAWorkManager(runnable, getTM().getParallelXADispatchPolicy())) {
                        runnable.run();
                     }
                  }
               }

               if (!isNoTLOGEnabled) {
                  assignedResources[0].rollback(tx, false, isOnePhaseTransaction);
               }

               barrier.await();
               ServerResourceInfo[] var20 = assignedResources;
               int var9 = assignedResources.length;

               for(int var21 = 0; var21 < var9; ++var21) {
                  ServerResourceInfo assignedResource = var20[var21];
                  if (!assignedResource.isRolledBack()) {
                     allMyResourcesRolledBack = false;
                  }
               }

               if (allMyResourcesRolledBack) {
                  this.setRolledBack();
               }
            } else {
               Iterator var15 = resourceList.iterator();

               while(var15.hasNext()) {
                  Object aResourceList = var15.next();
                  ServerResourceInfo ri = (ServerResourceInfo)aResourceList;
                  if (ri.isAssignedTo(this)) {
                     ri.rollback(tx, false, isOnePhaseTransaction);
                     if (!ri.isRolledBack()) {
                        allMyResourcesRolledBack = false;
                     }
                  }
               }

               if (allMyResourcesRolledBack) {
                  this.setRolledBack();
               }
            }

         }
      } else {
         try {
            String[] assignedResources;
            if (tx.isResourceNotFound() && tx.isAllResourcesAssigned()) {
               assignedResources = this.getAssignedResourceNames(tx, false);
            } else {
               assignedResources = this.getAssignedResourceNames(tx);
            }

            String[] knownResources = this.getResourceNames(tx);
            if (assignedResources == null && this.isRolledBack()) {
               return;
            }

            this.setRollingBack();
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "startRollback");
            }

            SubCoordinatorOneway sc = this.getSubCoordinator(tx);
            if (sc != null) {
               AuthenticatedSubject currentId = SecurityServiceManager.getCurrentSubject(this.getKernelID());
               PlatformHelper.getPlatformHelper().runSecureAction(new StartRollbackAction(sc, tx, assignedResources, currentId, knownResources), this.getScServerURL(), "sc.startRollback");
            }
         } catch (Exception var12) {
            if (var12 instanceof RemoteException) {
               ResourceDescriptor.shunSC(this.getCoordinatorDescriptor());
            }

            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "startRollback failure: ", var12);
            }
         }

      }
   }

   void nonXAResourceCommit(Xid xid, boolean onePhase, String resourceName) throws SystemException, AbortRequestedException {
      if (TxDebug.JTANaming.isDebugEnabled() && TxDebug.JTANamingStackTrace.isDebugEnabled()) {
         TxDebug.debugStack(TxDebug.JTANamingStackTrace, "ServerSCInfo.nonXAResourceCommit xid:" + xid + " subCoor:" + this.getSubCoordinator() + " instanceOf:" + (this.getSubCoordinator() instanceof SubCoordinator2));
      }

      SubCoordinator sc = (SubCoordinator)this.getSubCoordinator();
      if (!(sc instanceof SubCoordinator2)) {
         throw new SystemException("Remote SC " + this.getScUrl() + " does not support Non XA Resources");
      } else {
         SubCoordinator2 sc2 = (SubCoordinator2)sc;

         try {
            SecureAction.runAction(this.getKernelID(), new NonXAResourceCommitAction(sc2, xid, onePhase, resourceName), this.getScServerURL(), "sc.nonXAResourceCommit");
         } catch (Exception var7) {
            if (var7 instanceof AbortRequestedException) {
               throw (AbortRequestedException)var7;
            } else {
               throw new SystemException("Non XA Resource commit on SC '" + this.getScUrl() + "' failed: " + var7);
            }
         }
      }
   }

   final boolean handleResource(ServerResourceInfo ri, boolean retry, Object setOfAssignableOnlyToEnlistingSCs) {
      return this.handleResource(ri, retry, setOfAssignableOnlyToEnlistingSCs, false);
   }

   final boolean handleResource(ServerResourceInfo ri, boolean retry, Object setOfAssignableOnlyToEnlistingSCs, boolean isResourceNotFound) {
      if (setOfAssignableOnlyToEnlistingSCs != null && ((Set)setOfAssignableOnlyToEnlistingSCs).contains(ri.getResourceDescriptor().getName())) {
         ri.getResourceDescriptor().setAssignableOnlyToEnlistingSCs(true);
      }

      if (ri.isAccessibleAtAndAssignableTo(this, retry, isResourceNotFound)) {
         ri.assignResourceToSC(this);
         return true;
      } else {
         return false;
      }
   }

   void setCommitted() {
      this.setState((byte)7);
   }

   boolean isCommitted() {
      return this.getState() == 7;
   }

   private void setPrePrepared() {
      this.setState((byte)3);
   }

   void setPrepared(int vote) {
      this.setState((byte)5, vote);
   }

   void setRolledBack() {
      this.setState((byte)9);
   }

   boolean isPrePrepared() {
      return this.getState() == 3;
   }

   boolean isPrepared() {
      return this.getState() == 5;
   }

   boolean isRolledBack() {
      return this.getState() == 9;
   }

   synchronized void callAfterCompletions(int status, TransactionImpl tx) {
      if (!this.isAfterCompletionConductedAlready) {
         Map finalAfterSyncList = this.getFinalAfterSyncList();
         if (finalAfterSyncList != null) {
            TransactionImpl saveTx = null;
            boolean useTXThreadCL = this.checkCallBackWithTXThreadCL(tx);

            try {
               saveTx = getTM().internalSuspend();
               AuthenticatedSubject currentId = (AuthenticatedSubject)PlatformHelper.getPlatformHelper().getCurrentSubject();
               this.callAfterCompletionsForTier(finalAfterSyncList, currentId, useTXThreadCL, status, InterpositionTier.JTA_INTERPOSED_SYNCHRONIZATION);
               this.callAfterCompletionsForTier(finalAfterSyncList, currentId, useTXThreadCL, status, InterpositionTier.WLS_INTERNAL_SYNCHRONIZATION);
               this.callAfterCompletionsForTier(finalAfterSyncList, currentId, useTXThreadCL, status, (InterpositionTier)null);
            } finally {
               this.isAfterCompletionConductedAlready = true;
               getTM().internalResume(saveTx);
            }

         }
      }
   }

   private void callAfterCompletionsForTier(Map finalAfterSyncList, AuthenticatedSubject currentId, boolean useTXThreadCL, int status, InterpositionTier tier) {
      Iterator finalAfterSyncListIterator = finalAfterSyncList.keySet().iterator();

      while(finalAfterSyncListIterator.hasNext()) {
         Object next = finalAfterSyncListIterator.next();
         RegisteredSync asyncs = (RegisteredSync)next;
         if (asyncs.tier == tier) {
            ClassLoader oldCL = Thread.currentThread().getContextClassLoader();

            try {
               Synchronization s = asyncs.sync;
               ComponentInvocationContext cic = asyncs.componentInvocationContext;
               if (!useTXThreadCL) {
                  Thread.currentThread().setContextClassLoader(PlatformHelper.getPlatformHelper().getContextClassLoader(oldCL, s));
               } else {
                  ClassLoader contextClassLoader = (ClassLoader)finalAfterSyncList.get(next);
                  if (contextClassLoader != null) {
                     Thread.currentThread().setContextClassLoader(contextClassLoader);
                  }
               }

               AuthenticatedSubject regsyncId = asyncs.subject;
               this.doAfterCompletion(s, status, regsyncId, currentId, cic);
            } finally {
               Thread.currentThread().setContextClassLoader(oldCL);
            }
         }
      }

   }

   private void doAfterCompletion(Synchronization s, int status, AuthenticatedSubject regsyncId, AuthenticatedSubject currentId, ComponentInvocationContext cic) {
      try {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug("Synchronization[" + s + "].afterCompletion(status=" + status + ")  cic:" + cic);
         }

         if (regsyncId != null && !currentId.equals(regsyncId)) {
            SecurityServiceManager.runAs(this.getKernelID(), regsyncId, new CallAfterCompletionsAction(s, status, cic));
         } else if (cic == null) {
            s.afterCompletion(status);
         } else {
            this.partitionAfterCompletion(s, status, cic);
         }

         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug("Synchronization[" + s + "].afterCompletion(status=" + status + ") END");
         }
      } catch (OutOfMemoryError var7) {
         throw var7;
      } catch (ThreadDeath var8) {
         throw var8;
      } catch (Throwable var9) {
         TXLogger.logIgnoreAfterCompletionErr(s.toString(), var9);
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug("Synchronization[" + s + "].afterCompletion(status=" + status + ") FAILED", var9);
         }
      }

   }

   void incrementCoordinatorRefCount() {
      ((ServerCoordinatorDescriptor)this.getCoordinatorDescriptor()).incrementCoordinatorRefCount();
   }

   void decrementCoordinatorRefCount() {
      ((ServerCoordinatorDescriptor)this.getCoordinatorDescriptor()).decrementCoordinatorRefCount();
   }

   boolean isAccessible(ServerTransactionImpl tx) {
      return this.getSubCoordinator(tx) != null;
   }

   private boolean checkCallBackWithTXThreadCL(TransactionImpl tx) {
      if (useTXThreadCLSyspropEnabled) {
         return true;
      } else {
         Boolean useTXThreadCL = (Boolean)tx.getProperty("callBacksWithTXThreadClassLoader");
         return useTXThreadCL != null && useTXThreadCL;
      }
   }

   private String[] getAssignedResourceNames(TransactionImpl tx) {
      return this.getAssignedResourceNames(tx, true);
   }

   private String[] getAssignedResourceNames(TransactionImpl tx, boolean allResources) {
      ArrayList riList = tx.getResourceInfoList();
      if (riList == null) {
         return null;
      } else {
         int len = riList.size();
         int count = 0;

         for(int i = 0; i < len; ++i) {
            ServerResourceInfo ri = (ServerResourceInfo)riList.get(i);
            if (ri.isAssignedTo(this)) {
               if (!allResources) {
                  if (!ri.isRolledBack()) {
                     ++count;
                  }
               } else {
                  ++count;
               }
            }
         }

         if (count == 0) {
            return null;
         } else {
            ArrayList resourceNames = new ArrayList(5);
            Iterator var11 = riList.iterator();

            while(var11.hasNext()) {
               Object aRiList = var11.next();
               ServerResourceInfo ri = (ServerResourceInfo)aRiList;
               if (ri.isAssignedTo(this)) {
                  if (!allResources) {
                     if (!ri.isRolledBack()) {
                        resourceNames.add(ri.getName());
                     }
                  } else {
                     resourceNames.add(ri.getName());
                  }
               }
            }

            String[] rtn = new String[resourceNames.size()];
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, tx, "ServerSCInfo.assignedResourceNames resources: " + resourceNames);
            }

            return (String[])((String[])resourceNames.toArray(rtn));
         }
      }
   }

   private String[] getResourceNames(TransactionImpl tx) {
      ArrayList riList = tx.getResourceInfoList();
      if (riList == null) {
         return null;
      } else {
         ArrayList resourceNames = new ArrayList(5);
         Iterator var4 = riList.iterator();

         while(var4.hasNext()) {
            Object aRiList = var4.next();
            ServerResourceInfo ri = (ServerResourceInfo)aRiList;
            resourceNames.add(ri.getName());
         }

         String[] rtn = new String[resourceNames.size()];
         return (String[])((String[])resourceNames.toArray(rtn));
      }
   }

   private ServerResourceInfo[] getAssignedXAResources(TransactionImpl tx) {
      ArrayList riList = tx.getResourceInfoList();
      if (riList == null) {
         return null;
      } else {
         int len = riList.size();
         int count = 0;
         Iterator var5 = riList.iterator();

         while(var5.hasNext()) {
            Object aRiList1 = var5.next();
            ServerResourceInfo ri = (ServerResourceInfo)aRiList1;
            if (ri.isAssignedTo(this) && ri instanceof XAServerResourceInfo) {
               ++count;
            }
         }

         if (count == 0) {
            return null;
         } else {
            ArrayList resources = new ArrayList(5);
            Iterator var10 = riList.iterator();

            while(var10.hasNext()) {
               Object aRiList = var10.next();
               ServerResourceInfo ri = (ServerResourceInfo)aRiList;
               if (ri.isAssignedTo(this) && ri instanceof XAServerResourceInfo) {
                  resources.add(ri);
               }
            }

            ServerResourceInfo[] rtn = new ServerResourceInfo[resources.size()];
            return (ServerResourceInfo[])((ServerResourceInfo[])resources.toArray(rtn));
         }
      }
   }

   private ServerResourceInfo[] getAssignedResources(TransactionImpl tx) {
      ArrayList riList = tx.getResourceInfoList();
      if (riList == null) {
         return null;
      } else {
         int len = riList.size();
         int count = 0;

         for(int i = 0; i < len; ++i) {
            ServerResourceInfo ri = (ServerResourceInfo)riList.get(i);
            if (ri.isAssignedTo(this)) {
               ++count;
            }
         }

         if (count == 0) {
            return null;
         } else {
            ArrayList resources = new ArrayList(5);

            for(int i = 0; i < len; ++i) {
               ServerResourceInfo ri = (ServerResourceInfo)riList.get(i);
               if (ri.isAssignedTo(this)) {
                  resources.add(ri);
               }
            }

            ServerResourceInfo[] rtn = new ServerResourceInfo[resources.size()];
            return (ServerResourceInfo[])((ServerResourceInfo[])resources.toArray(rtn));
         }
      }
   }

   private SubCoordinatorOneway getSubCoordinator() {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("SeverSCInfo.getSubCoordinator threadInfo:" + Thread.currentThread().getName() + " coordinatorDescriptor:" + this.getCoordinatorDescriptor());
      }

      return JNDIAdvertiser.getSubCoordinator(this.getCoordinatorDescriptor(), (TransactionImpl)null);
   }

   private SubCoordinatorOneway getSubCoordinator(TransactionImpl tx) {
      return JNDIAdvertiser.getSubCoordinator(this.getCoordinatorDescriptor(), tx);
   }

   private void setPrePreparing() {
      this.setState((byte)2);
   }

   private void setPreparing() {
      this.setState((byte)4);
   }

   private void setCommitting() {
      this.setState((byte)6);
   }

   private void setRollingBack() {
      this.setState((byte)8);
   }

   final void callBeforeCompletions(TransactionImpl tx) throws AbortRequestedException {
      Map bs = this.getAndResetBeforeSyncs();
      if (bs != null) {
         AuthenticatedSubject currentId = SecurityServiceManager.getCurrentSubject(this.getKernelID());
         boolean useTXThreadCL = this.checkCallBackWithTXThreadCL(tx);

         try {
            this.callBeforeCompletionsForTier(tx, bs, currentId, useTXThreadCL, (InterpositionTier)null);
            this.callBeforeCompletionsForTier(tx, bs, currentId, useTXThreadCL, InterpositionTier.WLS_INTERNAL_SYNCHRONIZATION);
            this.callBeforeCompletionsForTier(tx, bs, currentId, useTXThreadCL, InterpositionTier.JTA_INTERPOSED_SYNCHRONIZATION);
         } finally {
            this.moveBeforeSyncSetToAfter(bs);
         }

      }
   }

   private void callBeforeCompletionsForTier(TransactionImpl tx, Map bs, AuthenticatedSubject currentId, boolean useTXThreadCL, InterpositionTier tier) throws AbortRequestedException {
      Iterator i = bs.keySet().iterator();

      while(i.hasNext() && !tx.isCancelled()) {
         Synchronization s = null;
         RegisteredSync bsyncs = (RegisteredSync)i.next();
         if (bsyncs.tier == tier) {
            ClassLoader oldCL = Thread.currentThread().getContextClassLoader();

            try {
               s = bsyncs.sync;
               ComponentInvocationContext cic = bsyncs.componentInvocationContext;
               if (!useTXThreadCL) {
                  Thread.currentThread().setContextClassLoader(s.getClass().getClassLoader());
               }

               AuthenticatedSubject regsyncId = bsyncs.subject;
               this.doBeforeCompletion(tx, s, regsyncId, currentId, cic);
            } finally {
               if (!useTXThreadCL) {
                  Thread.currentThread().setContextClassLoader(oldCL);
               }

            }
         }
      }

   }

   private void doBeforeCompletion(TransactionImpl tx, Synchronization s, Object regsyncId, AuthenticatedSubject currentId) throws AbortRequestedException {
      this.doBeforeCompletion(tx, s, regsyncId, currentId, (ComponentInvocationContext)null);
   }

   private void doBeforeCompletion(TransactionImpl tx, Synchronization s, Object regsyncId, AuthenticatedSubject currentId, ComponentInvocationContext cic) throws AbortRequestedException {
      String msg;
      try {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, tx, "Synchronization[" + s + "].beforeCompletion()  cic:" + cic);
         }

         if (regsyncId != null && !currentId.equals(regsyncId)) {
            SecurityServiceManager.runAs(this.getKernelID(), (AuthenticatedSubject)regsyncId, new CallBeforeCompletionsAction(s, tx, cic));
         } else {
            this.callBeforeCompletion(s, tx, cic);
         }

         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, tx, "Synchronization[" + s + "].beforeCompletion() END");
         }
      } catch (OutOfMemoryError var8) {
         msg = "OutOfMemoryError in beforeCompletion: sync=" + s;
         tx.abort(msg, var8);
      } catch (ThreadDeath var9) {
         msg = "ThreadDeath in beforeCompletion: sync=" + s;
         tx.abort(msg, var9);
      } catch (Throwable var10) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, tx, "Synchronization[" + s + "].beforeCompletion() FAILED", var10);
         }

         msg = "Unexpected exception in beforeCompletion: sync=" + s;
         tx.abort(msg, var10, true);
      }

   }

   private void callBeforeCompletion(Synchronization s, TransactionImpl tx, ComponentInvocationContext cic) {
      if ((isPiggybackLocalCommitEnabled || isPiggybackLocalCommitEnabledSysProp) && s instanceof weblogic.transaction.Synchronization) {
         boolean isLocalConnOnlyResourceEnlistedInTransaction = true;
         Map map = new HashMap();
         map.put("IS_LOCAL_CONNECTION_COMMIT_OR_ROLLBACK_ALLOWED_IN_BEFORECOMPLETION", isLocalConnOnlyResourceEnlistedInTransaction);
         tx.setLocalConnCommittedOrRollback(((weblogic.transaction.Synchronization)s).beforeCompletion(map));
      } else if (cic == null) {
         s.beforeCompletion();
      } else {
         this.partitionCallBeforeCompletion(s, cic);
      }

   }

   private Map getBeforeSyncs() {
      return this.beforeSyncs;
   }

   private synchronized Map getAndResetBeforeSyncs() {
      Map temp = this.beforeSyncs;
      this.beforeSyncs = null;
      return temp;
   }

   private Map getOrCreateBeforeSyncs() {
      if (this.beforeSyncs == null) {
         this.beforeSyncs = new LinkedHashMap();
      }

      return this.beforeSyncs;
   }

   private Map getAfterSyncs() {
      return this.afterSyncs;
   }

   private void setAfterSyncs(Map as) {
      this.afterSyncs = as;
   }

   private synchronized boolean isSyncInList(Synchronization sync) {
      Map as = this.getAfterSyncs();
      if (as == null) {
         return false;
      } else {
         Iterator it = as.keySet().iterator();

         RegisteredSync asyncs;
         do {
            if (!it.hasNext()) {
               return false;
            }

            asyncs = (RegisteredSync)it.next();
         } while(asyncs.sync != sync);

         return true;
      }
   }

   private synchronized void moveBeforeSyncSetToAfter(Map bs) {
      if (bs != null) {
         Map as = this.getAfterSyncs();
         if (as == null) {
            this.setAfterSyncs(bs);
         } else {
            Iterator it = bs.keySet().iterator();

            while(it.hasNext()) {
               RegisteredSync bsyncs = (RegisteredSync)it.next();
               if (!this.isSyncInList(bsyncs.sync)) {
                  as.put(bsyncs, (Object)null);
               }
            }

         }
      }
   }

   private synchronized Map getFinalAfterSyncList() {
      Map bs = this.getBeforeSyncs();
      this.beforeSyncs = null;
      Map as = this.getAfterSyncs();
      this.afterSyncs = null;
      if (bs == null) {
         return as;
      } else if (as == null) {
         return bs;
      } else {
         Iterator it = bs.keySet().iterator();

         while(it.hasNext()) {
            RegisteredSync bsyncs = (RegisteredSync)it.next();
            if (!this.isSyncInList(bsyncs.sync)) {
               as.put(bsyncs, (Object)null);
            }
         }

         return as;
      }
   }

   private boolean hasBeforeCompletions() {
      Map bs = this.getBeforeSyncs();
      return bs != null && bs.size() > 0;
   }

   private static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)TransactionManagerImpl.getTransactionManager();
   }

   private boolean isLocal() {
      return getTM().isLocalCoordinator(this.getCoordinatorDescriptor());
   }

   public void dump(JTAImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Server");
      xsw.writeAttribute("url", this.getScUrl());
      xsw.writeAttribute("syncRegistered", String.valueOf(this.syncRegistered));
      xsw.writeAttribute("state", this.getStateAsString());
      xsw.writeEndElement();
   }

   private AuthenticatedSubject getKernelID() {
      return kernelID;
   }

   Set getWlsInternalSyncs() {
      Set wlsInternalSyncs = new HashSet();
      Map bs = this.getBeforeSyncs();
      if (bs != null) {
         Iterator it = bs.keySet().iterator();

         while(it.hasNext()) {
            RegisteredSync rs = (RegisteredSync)it.next();
            if (rs.tier == InterpositionTier.WLS_INTERNAL_SYNCHRONIZATION) {
               wlsInternalSyncs.add(rs.sync);
            }
         }
      }

      Map as = this.getAfterSyncs();
      if (as != null) {
         Iterator it = as.keySet().iterator();

         while(it.hasNext()) {
            RegisteredSync rs = (RegisteredSync)it.next();
            if (rs.tier == InterpositionTier.WLS_INTERNAL_SYNCHRONIZATION) {
               wlsInternalSyncs.add(rs.sync);
            }
         }
      }

      return wlsInternalSyncs;
   }

   Set getJtaInterposedSyncs() {
      Set jtaInterposedSyncs = new HashSet();
      Map bs = this.getBeforeSyncs();
      if (bs != null) {
         Iterator it = bs.keySet().iterator();

         while(it.hasNext()) {
            RegisteredSync rs = (RegisteredSync)it.next();
            if (rs.tier == InterpositionTier.JTA_INTERPOSED_SYNCHRONIZATION) {
               jtaInterposedSyncs.add(rs.sync);
            }
         }
      }

      Map as = this.getAfterSyncs();
      if (as != null) {
         Iterator it = as.keySet().iterator();

         while(it.hasNext()) {
            RegisteredSync rs = (RegisteredSync)it.next();
            if (rs.tier == InterpositionTier.JTA_INTERPOSED_SYNCHRONIZATION) {
               jtaInterposedSyncs.add(rs.sync);
            }
         }
      }

      return jtaInterposedSyncs;
   }

   void partitionCallBeforeCompletion(Synchronization sync, ComponentInvocationContext cic) {
      final Synchronization async = sync;
      ComponentInvocationContext acic = cic;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("partition Synchronization[" + sync + "].beforeCompletion cic:" + cic);
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, acic, new Callable() {
            public Void call() {
               async.beforeCompletion();
               return null;
            }
         });
      } catch (ExecutionException var6) {
         throw new RuntimeException(var6.getCause());
      }
   }

   void partitionAfterCompletion(Synchronization sync, int status, ComponentInvocationContext cic) throws Exception {
      final Synchronization async = sync;
      final int astatus = status;
      ComponentInvocationContext acic = cic;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("partition Synchronization[" + sync + "].afterCompletion(" + status + ") cic:" + cic);
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, acic, new Callable() {
            public Void call() throws Exception {
               async.afterCompletion(astatus);
               return null;
            }
         });
      } catch (ExecutionException var9) {
         if (var9.getCause() instanceof Exception) {
            Exception ex = (Exception)var9.getCause();
            throw ex;
         }
      }

   }

   private class CallAfterCompletionsAction implements PrivilegedExceptionAction {
      private Synchronization sync;
      private int status;
      private ComponentInvocationContext cic;

      CallAfterCompletionsAction(Synchronization async, int aStatus, ComponentInvocationContext acic) {
         this.sync = async;
         this.status = aStatus;
         this.cic = acic;
      }

      public Object run() throws Exception {
         if (this.cic == null) {
            this.sync.afterCompletion(this.status);
            return null;
         } else {
            ServerSCInfo.this.partitionAfterCompletion(this.sync, this.status, this.cic);
            return null;
         }
      }
   }

   private class CallBeforeCompletionsAction implements PrivilegedExceptionAction {
      private Synchronization sync;
      private TransactionImpl transaction;
      private ComponentInvocationContext componentInvocationContext;

      CallBeforeCompletionsAction(Synchronization async, TransactionImpl tx, ComponentInvocationContext cic) {
         this.sync = async;
         this.transaction = tx;
         this.componentInvocationContext = cic;
      }

      public Object run() throws Exception {
         ServerSCInfo.this.callBeforeCompletion(this.sync, this.transaction, this.componentInvocationContext);
         return null;
      }
   }

   private class StartPrePrepareAndChain implements PrivilegedExceptionAction {
      SubCoordinatorOneway sc;
      PropagationContext pctx;
      int hopsLeft;

      StartPrePrepareAndChain(PropagationContext apctx, int ahopsLeft, SubCoordinatorOneway asc) {
         this.sc = asc;
         this.pctx = apctx;
         this.hopsLeft = ahopsLeft;
      }

      public Object run() throws Exception {
         this.sc.startPrePrepareAndChain(this.pctx, this.hopsLeft);
         return null;
      }
   }

   private static class NonXAResourceCommitAction implements PrivilegedExceptionAction {
      private SubCoordinator2 sc;
      private Xid xid;
      private boolean onePhase;
      private String name;

      NonXAResourceCommitAction(SubCoordinator2 aSC, Xid aXid, boolean aOnePhase, String aName) {
         this.sc = aSC;
         this.xid = aXid;
         this.onePhase = aOnePhase;
         this.name = aName;
      }

      public Object run() throws Exception {
         this.sc.nonXAResourceCommit(this.xid, this.onePhase, this.name);
         return null;
      }
   }

   private static class ForceLocalCommitAction implements PrivilegedExceptionAction {
      private SubCoordinatorOneway3 sc;
      private Xid xid;

      ForceLocalCommitAction(SubCoordinatorOneway3 aSC, Xid aXid) {
         this.sc = aSC;
         this.xid = aXid;
      }

      public Object run() throws Exception {
         this.sc.forceLocalCommit(this.xid);
         return null;
      }
   }

   private static class ForceLocalRollbackAction implements PrivilegedExceptionAction {
      private SubCoordinatorOneway3 sc;
      private Xid xid;

      ForceLocalRollbackAction(SubCoordinatorOneway3 aSC, Xid aXid) {
         this.sc = aSC;
         this.xid = aXid;
      }

      public Object run() throws Exception {
         this.sc.forceLocalRollback(this.xid);
         return null;
      }
   }

   private static class CommitAction implements PrivilegedExceptionAction {
      private SubCoordinator sc;
      private String resourceName;
      private Xid[] xids;

      CommitAction(SubCoordinator aSC, String aResourceName, Xid[] aXids) {
         this.sc = aSC;
         this.resourceName = aResourceName;
         this.xids = aXids;
      }

      public Object run() throws Exception {
         this.sc.commit(this.resourceName, this.xids);
         return null;
      }
   }

   private static class RollbackAction implements PrivilegedExceptionAction {
      private SubCoordinator sc;
      private String resourceName;
      private Xid[] xids;

      RollbackAction(SubCoordinator aSC, String aResourceName, Xid[] aXids) {
         this.sc = aSC;
         this.resourceName = aResourceName;
         this.xids = aXids;
      }

      public Object run() throws Exception {
         this.sc.rollback(this.resourceName, this.xids);
         return null;
      }
   }

   private class GetPropertiesAction implements PrivilegedExceptionAction {
      private SubCoordinatorRM scrm;
      private String resourceName;
      private Map rmProps;

      GetPropertiesAction(SubCoordinatorRM aSC, String aResourceName) {
         this.scrm = aSC;
         this.resourceName = aResourceName;
         this.rmProps = null;
      }

      public Object run() throws Exception {
         this.rmProps = this.scrm.getProperties(this.resourceName);
         return null;
      }

      public Map getProperties() {
         return this.rmProps;
      }
   }

   private class RecoverAction implements PrivilegedExceptionAction {
      private SubCoordinator sc;
      private String resourceName;
      private String coUrl = null;
      private Xid[] xids;

      RecoverAction(SubCoordinator aSC, String aResourceName, String acoUrl) {
         this.sc = aSC;
         this.resourceName = aResourceName;
         this.coUrl = acoUrl;
         this.xids = null;
      }

      public Object run() throws Exception {
         this.xids = this.sc.recover(this.resourceName, this.coUrl);
         return null;
      }

      public Xid[] getXids() {
         return this.xids;
      }
   }

   private static class StartRollbackAction implements PrivilegedExceptionAction {
      private SubCoordinatorOneway sc;
      private ServerTransactionImpl tx;
      private String[] assignedResources;
      private String[] knownResources;

      StartRollbackAction(SubCoordinatorOneway aSC, ServerTransactionImpl aTx, String[] aAssignedResources, Object aCallerId, String[] aKnownResources) {
         this.sc = aSC;
         this.tx = aTx;
         this.assignedResources = aAssignedResources;
         this.knownResources = aKnownResources;
      }

      public Object run() throws Exception {
         if (this.sc instanceof SubCoordinatorOneway6) {
            SubCoordinatorOneway6 sc6 = (SubCoordinatorOneway6)this.sc;
            sc6.startRollback(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, (AuthenticatedUser)null, this.knownResources, this.tx.getProperties(), this.tx.isRetry());
         } else if (this.sc instanceof SubCoordinatorOneway5) {
            SubCoordinatorOneway5 sc5 = (SubCoordinatorOneway5)this.sc;
            sc5.startRollback(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, (AuthenticatedUser)null, this.knownResources, this.tx.getProperties());
         } else if (this.sc instanceof SubCoordinatorOneway4) {
            SubCoordinatorOneway4 sc4 = (SubCoordinatorOneway4)this.sc;
            sc4.startRollback(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, (AuthenticatedUser)null, this.knownResources);
         } else if (this.sc instanceof SubCoordinatorOneway2) {
            SubCoordinatorOneway2 sc2 = (SubCoordinatorOneway2)this.sc;
            sc2.startRollback(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, (AuthenticatedUser)null);
         } else {
            this.sc.startRollback(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources);
         }

         return null;
      }
   }

   private static class StartCommitAction implements PrivilegedExceptionAction {
      private SubCoordinatorOneway sc;
      private ServerTransactionImpl tx;
      private String[] assignedResources;
      boolean onePhase;
      boolean retry;
      AuthenticatedSubject callerId;

      StartCommitAction(SubCoordinatorOneway aSC, ServerTransactionImpl aTx, String[] aAssignedResources, boolean aOnePhase, boolean aRetry, AuthenticatedSubject aCallerId) {
         this.sc = aSC;
         this.tx = aTx;
         this.assignedResources = aAssignedResources;
         this.onePhase = aOnePhase;
         this.retry = aRetry;
         this.callerId = null;
      }

      public Object run() throws Exception {
         if (this.sc instanceof SubCoordinatorOneway5) {
            SubCoordinatorOneway5 sc5 = (SubCoordinatorOneway5)this.sc;
            sc5.startCommit(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, this.onePhase, this.retry, this.callerId, this.tx.getProperties());
         } else if (this.sc instanceof SubCoordinatorOneway2) {
            SubCoordinatorOneway2 sc2 = (SubCoordinatorOneway2)this.sc;
            sc2.startCommit(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, this.onePhase, this.retry, this.callerId);
         } else {
            this.sc.startCommit(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, this.onePhase, this.retry);
         }

         return null;
      }
   }

   private static class StartPrepareAction implements PrivilegedExceptionAction {
      private SubCoordinatorOneway sc;
      private ServerTransactionImpl tx;
      private String[] assignedResources;

      StartPrepareAction(SubCoordinatorOneway aSC, ServerTransactionImpl aTx, String[] aAssignedResources) {
         this.sc = aSC;
         this.tx = aTx;
         this.assignedResources = aAssignedResources;
      }

      public Object run() throws Exception {
         if (this.sc instanceof SubCoordinatorOneway5) {
            SubCoordinatorOneway5 sc5 = (SubCoordinatorOneway5)this.sc;
            sc5.startPrepare(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, this.tx.getTimeoutSeconds(), this.tx.getProperties());
         } else {
            this.sc.startPrepare(this.tx.getXID(), this.tx.getCoordinatorURL(), this.assignedResources, this.tx.getTimeoutSeconds());
         }

         return null;
      }
   }

   private class RegisteredSync {
      private Synchronization sync;
      private AuthenticatedSubject subject;
      private ComponentInvocationContext componentInvocationContext;
      private InterpositionTier tier;

      protected RegisteredSync(Synchronization async, AuthenticatedSubject asubject, ComponentInvocationContext acic) {
         this(async, asubject, acic, (InterpositionTier)null);
      }

      protected RegisteredSync(Synchronization async, AuthenticatedSubject asubject, ComponentInvocationContext acic, InterpositionTier atier) {
         this.sync = async;
         this.subject = asubject;
         this.componentInvocationContext = acic;
         this.tier = atier;
      }
   }
}
