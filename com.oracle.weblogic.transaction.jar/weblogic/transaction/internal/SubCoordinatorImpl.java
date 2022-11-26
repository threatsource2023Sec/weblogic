package weblogic.transaction.internal;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.PeerExchangeTransaction;
import weblogic.transaction.TransactionSystemException;
import weblogic.transaction.nonxa.NonXAException;

public class SubCoordinatorImpl implements SubCoordinator3, SubCoordinatorOneway3, Constants, NotificationBroadcaster, NotificationListener, SubCoordinatorRM, SubCoordinatorOneway4, SubCoordinatorOneway5, SubCoordinatorOneway6, SubCoordinatorOneway7 {
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private NotificationBroadcasterManager notificationBroadcaster;
   private static final boolean INSTR_ENABLED;
   private Map commitUsernameStats;
   private Map rollbackUsernameStats;

   public SubCoordinatorImpl() {
      if (INSTR_ENABLED) {
         this.commitUsernameStats = new HashMap();
         this.rollbackUsernameStats = new HashMap();
      }

      this.notificationBroadcaster = NotificationBroadcasterManager.getInstance();
      this.notificationBroadcaster.setSubCoordinator(this);
   }

   public void startPrePrepareAndChain(PropagationContext pctx, int dummy) {
      boolean replyToCoordinator = false;

      ServerTransactionImpl tx;
      try {
         tx = (ServerTransactionImpl)pctx.getTransaction();
      } catch (TransactionSystemException var9) {
         return;
      }

      CoordinatorOneway co = null;

      try {
         if (tx == null) {
            return;
         }

         co = this.getCoordinator((CoordinatorDescriptor)tx.getCoordinatorDescriptor(), (TransactionImpl)tx);
         if (co == null) {
            return;
         }

         tx.localPrePrepareAndChain();
         if (tx.allSCsPrePrepared()) {
            replyToCoordinator = true;
         }
      } catch (AbortRequestedException var8) {
         replyToCoordinator = true;
      }

      if (replyToCoordinator) {
         try {
            if (tx.isCancelled()) {
               tx.globalRollback();
            } else if (co != null) {
               if (tx.isCoordinatingTransaction()) {
                  tx.ackPrePrepare();
               } else {
                  try {
                     CoordinatorDescriptor cd = tx.getCoordinatorDescriptor();
                     String coURL = cd.getCoordinatorURL(tx.isSSLEnabled());
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.JTA2PC.debug("Send co.ackPrePrepare ..." + coURL + " sslEnabled:" + tx.isSSLEnabled() + " cd.getCoordinatorURL(tx.isSSLEnabled():" + cd.getCoordinatorURL(tx.isSSLEnabled()));
                     }

                     SecureAction.runAction(kernelID, new AckPrePrepareAction(co, tx.getRequestPropagationContext()), CoordinatorDescriptor.getServerURL(coURL), "co.ackPrePrepare");
                  } catch (Exception var10) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.JTA2PC.debug("ackPrePrepare FAILED", var10);
                     }
                  }
               }
            }
         } catch (Exception var11) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug("startPrePrepareAndChain FAILED", var11);
            }
         }
      }

   }

   public void startPrepare(Xid xid, String coURL, String[] rmNames, int ttlSec) {
      this.startPrepare(xid, coURL, rmNames, ttlSec, (Map)null);
   }

   public void startPrepare(Xid xid, String coURL, String[] rmNames, int ttlSec, Map properties) {
      int vote = false;

      ServerTransactionImpl tx;
      CoordinatorOneway co;
      int vote;
      try {
         tx = this.getOrCreateTransaction(xid, ttlSec);
         tx.setCoordinatorURL(coURL);
         co = this.getCoordinator((CoordinatorDescriptor)tx.getCoordinatorDescriptor(), (TransactionImpl)tx);
         if (co == null) {
            throw new SystemException("Could not obtain coordinator at " + coURL);
         }

         if (properties != null) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug("startPrepare: Add properties: " + properties);
            }

            tx.addProperties(properties);
         }

         if (INSTR_ENABLED) {
            tx.check("SCBeforePrepare", this.getTM().getLocalCoordinatorDescriptor().getServerName());
         }

         tx.localPrepare(rmNames);
         vote = tx.getLocalSCInfo().vote;
         if (INSTR_ENABLED) {
            tx.check("SCAfterPrepare", this.getTM().getLocalCoordinatorDescriptor().getServerName());
         }
      } catch (SystemException var13) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug("startPrepare FAILED", var13);
         }

         return;
      }

      try {
         if (co != null) {
            CoordinatorDescriptor cd;
            if (tx.isCancelled()) {
               try {
                  cd = tx.getCoordinatorDescriptor();
                  SecureAction.runAction(kernelID, new StartRollbackAction(co, tx.getRequestPropagationContext()), CoordinatorDescriptor.getServerURL(coURL), "co.startRollback");
               } catch (Exception var11) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.JTA2PC.debug("startRollback FAILED", var11);
                  }
               }
            } else if (tx.isPrepared()) {
               try {
                  cd = tx.getCoordinatorDescriptor();
                  SecureAction.runAction(kernelID, new AckPrepareAction(co, xid, this.getScUrl(), vote), CoordinatorDescriptor.getServerURL(coURL), "co.ackPrepare");
               } catch (Exception var10) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.JTA2PC.debug("ackPrepare FAILED", var10);
                  }
               }
            }
         }
      } catch (Exception var12) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug("startPrepare FAILED", var12);
         }
      }

   }

   public final void startCommit(Xid xid, String coURL, String[] rmNames, boolean onePhase, boolean retry) {
      this.startCommit(xid, coURL, rmNames, onePhase, retry, (AuthenticatedUser)null);
   }

   public final void startCommit(Xid xid, String coURL, String[] rmNames, boolean onePhase, boolean retry, AuthenticatedUser wireCallerId) {
      this.startCommit(xid, coURL, rmNames, onePhase, retry, wireCallerId, (Map)null);
   }

   public final void startCommit(Xid xid, String coURL, String[] rmNames, boolean onePhase, boolean retry, AuthenticatedUser wireCallerId, Map properties) {
      if (INSTR_ENABLED && !retry) {
         this.incrementUsernameStats(this.commitUsernameStats);
      }

      Object callerId = null;
      if (wireCallerId == null) {
         callerId = RmiSecurityFacade.getAnonymousSubject();
      } else {
         callerId = SecurityServiceManager.getASFromWire((AuthenticatedSubject)wireCallerId);
      }

      StringBuffer sb;
      int dummyTimeout;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         sb = new StringBuffer(50);
         dummyTimeout = rmNames == null ? 0 : rmNames.length;

         for(int i = 0; i < dummyTimeout; ++i) {
            sb.append(" " + rmNames[i]);
         }

         TxDebug.JTA2PC.debug("startCommit: " + xid + " for coordinator=" + coURL + " resources=" + sb);
      }

      sb = null;
      PeerInfo peerInfo = this.getPeerInfo(sb);
      if (this.isPeerInfoIsServer(peerInfo)) {
         if (this.isStranger()) {
            TXLogger.logUserNotAuthorizedForStartCommit(this.getUserName());
         } else {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug("startCommit: About to do start.commit");
            }

            dummyTimeout = this.getDefaultTimeoutSeconds();
            ServerTransactionImpl tx = null;

            try {
               CoordinatorOneway co = null;
               CoordinatorOneway2 co2 = null;

               try {
                  tx = this.getOrCreateTransaction(xid, dummyTimeout);
                  tx.setSSLEnabled(PlatformHelper.getPlatformHelper().isSSLURL(coURL));
                  co = this.getCoordinator(coURL, tx);
                  if (co == null) {
                     throw new SystemException("Could not obtain coordinator at " + coURL);
                  }

                  if (co instanceof CoordinatorOneway2) {
                     co2 = (CoordinatorOneway2)co;
                  }

                  if (properties != null) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.JTA2PC.debug("startCommit: Add properties: " + properties);
                     }

                     tx.addProperties(properties);
                  }

                  if (INSTR_ENABLED && !retry) {
                     tx.check("SCBeforeCommit", this.getTM().getLocalCoordinatorDescriptor().getServerName());
                  }

                  CoordinatorDescriptor cd;
                  if (!this.isLocalCommitTrue(rmNames, onePhase, retry, callerId, tx)) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.JTA2PC.debug("startCommit: local commit failed, not responding");
                     }

                     if (INSTR_ENABLED && !retry) {
                        tx.check("SCAfterCommit", this.getTM().getLocalCoordinatorDescriptor().getServerName());
                     }

                     String specialHeurMessageNoXAResource = "NO_XARESOURCE";
                     TxDebug.JTA2PC.debug("SubCoordinatorImpl.startCommit No Resource NakCommitAction this:" + this);
                     if (co2 != null) {
                        TxDebug.JTA2PC.debug("SubCoordinatorImpl.startCommit No Resource NakCommitAction co2 this:" + this);

                        try {
                           cd = tx.getCoordinatorDescriptor();
                           String[] allResources = this.getResourceInfoStrings(tx);
                           this.runNakCommitAction(xid, coURL, tx, co2, specialHeurMessageNoXAResource, cd, allResources);
                        } catch (Exception var18) {
                        }
                     } else if (co != null) {
                        TxDebug.JTA2PC.debug("SubCoordinatorImpl.startCommit No Resource NakCommitAction co this:" + this);

                        try {
                           cd = tx.getCoordinatorDescriptor();
                           this.runNakCommitActionForNoXAResource(tx, xid, coURL, co, specialHeurMessageNoXAResource, cd);
                        } catch (Exception var17) {
                        }
                     }

                     return;
                  }

                  if (INSTR_ENABLED && !retry) {
                     tx.check("SCAfterCommit", this.getTM().getLocalCoordinatorDescriptor().getServerName());
                  }

                  short heurStatus = tx.getHeuristicStatus(4);
                  if (heurStatus != 0 && !this.getTM().listOfSCCommitsContains(xid, this.getScUrl())) {
                     TXLogger.logHeuristicCompletion(tx.toString(), tx.getHeuristicErrorMessage());
                     if (co2 != null) {
                        try {
                           cd = tx.getCoordinatorDescriptor();
                           SecureAction.runAction(kernelID, new NakCommitAction((CoordinatorOneway)null, co2, xid, this.getScUrl(), (String)null, heurStatus, tx.getHeuristicErrorMessage(), tx.getCommittedResources(), tx.getRolledbackResources()), CoordinatorDescriptor.getServerURL(coURL), "co2.nakCommit");
                        } catch (Exception var20) {
                        }
                     } else if (co != null) {
                        try {
                           cd = tx.getCoordinatorDescriptor();
                           SecureAction.runAction(kernelID, new NakCommitAction(co, (CoordinatorOneway2)null, xid, this.getScUrl(), (String)null, heurStatus, tx.getHeuristicErrorMessage(), (String[])null, (String[])null), CoordinatorDescriptor.getServerURL(coURL), "co.nakCommit");
                        } catch (Exception var19) {
                        }
                     }
                  } else if (co2 != null) {
                     try {
                        cd = tx.getCoordinatorDescriptor();
                        SecureAction.runAction(kernelID, new AckCommitAction((CoordinatorOneway)null, co2, xid, this.getScUrl(), tx.getCommittedResources()), CoordinatorDescriptor.getServerURL(coURL), "co2.ackCommit");
                     } catch (Exception var22) {
                     }
                  } else if (co != null) {
                     try {
                        cd = tx.getCoordinatorDescriptor();
                        SecureAction.runAction(kernelID, new AckCommitAction(co, (CoordinatorOneway2)null, xid, this.getScUrl(), (String[])null), CoordinatorDescriptor.getServerURL(coURL), "co.ackCommit");
                     } catch (Exception var21) {
                     }
                  }
               } catch (SystemException var23) {
               }
            } catch (Exception var24) {
            }

         }
      } else {
         TXLogger.logUserNotAuthorizedForStartCommit(this.getUserName());
      }
   }

   void runNakCommitAction(Xid xid, String coURL, ServerTransactionImpl tx, CoordinatorOneway2 co2, String specialHeurMessageNoXAResource, CoordinatorDescriptor cd, String[] allResources) throws Exception {
      SecureAction.runAction(kernelID, new NakCommitAction((CoordinatorOneway)null, co2, xid, this.getScUrl(), ServerTransactionImpl.migratedResourceCoordinatorURL, (short)16, specialHeurMessageNoXAResource, tx.getCommittedResources(), allResources), CoordinatorDescriptor.getServerURL(coURL), "co2.nakCommit");
   }

   void runNakCommitActionForNoXAResource(ServerTransactionImpl tx, Xid xid, String coURL, CoordinatorOneway co, String specialHeurMessageNoXAResource, CoordinatorDescriptor cd) throws Exception {
      SecureAction.runAction(kernelID, new NakCommitAction(co, (CoordinatorOneway2)null, xid, this.getScUrl(), ServerTransactionImpl.migratedResourceCoordinatorURL, (short)16, specialHeurMessageNoXAResource, (String[])null, (String[])null), CoordinatorDescriptor.getServerURL(coURL), "co.nakCommit");
   }

   void runNakRollbackAction(Xid xid, String coURL, ServerTransactionImpl tx, CoordinatorOneway2 co2, String specialHeurMessageNoXAResource, CoordinatorDescriptor cd, String[] allResources) throws Exception {
      SecureAction.runAction(kernelID, new NakRollbackAction((CoordinatorOneway)null, co2, xid, this.getScUrl(), (short)16, specialHeurMessageNoXAResource, allResources, tx.getRolledbackResources()), CoordinatorDescriptor.getServerURL(coURL), "co2.nakRollback");
   }

   void runNakRollbackAction(Xid xid, String coURL, CoordinatorOneway co, String specialHeurMessageNoXAResource, CoordinatorDescriptor cd) throws Exception {
      SecureAction.runAction(kernelID, new NakRollbackAction(co, (CoordinatorOneway2)null, xid, this.getScUrl(), (short)16, specialHeurMessageNoXAResource, (String[])null, (String[])null), CoordinatorDescriptor.getServerURL(coURL), "co.nakRollback");
   }

   String[] getResourceInfoStrings(ServerTransactionImpl tx) {
      ArrayList resourceList = tx.getResourceInfoList();
      String[] allResources = new String[resourceList.size()];

      for(int i = 0; i < resourceList.size(); ++i) {
         ServerResourceInfo sri = (ServerResourceInfo)resourceList.get(i);
         allResources[i] = sri.getName();
      }

      return allResources;
   }

   boolean isLocalCommitTrue(String[] rmNames, boolean onePhase, boolean retry, Object callerId, ServerTransactionImpl tx) {
      return tx.localCommit(rmNames, onePhase, retry, callerId);
   }

   CoordinatorOneway getCoordinator(String coURL, ServerTransactionImpl tx) {
      return this.getCoordinator((CoordinatorDescriptor)ServerCoordinatorDescriptor.getOrCreate(coURL), (TransactionImpl)tx);
   }

   ServerTransactionImpl getOrCreateTransaction(Xid xid, int dummyTimeout) throws SystemException {
      return this.getTM().getOrCreateTransaction(xid, dummyTimeout, dummyTimeout);
   }

   int getDefaultTimeoutSeconds() {
      return this.getTM().getDefaultTimeoutSeconds();
   }

   boolean isPeerInfoIsServer(PeerInfo peerInfo) {
      return peerInfo != null && peerInfo.isServer();
   }

   boolean isStranger() {
      return ReceiveSecureAction.stranger(this.getHostID(), "startCommit");
   }

   PeerInfo getPeerInfo(PeerInfo peerInfo) {
      try {
         EndPoint endPoint = ServerHelper.getClientEndPoint();
         if (endPoint != null && endPoint instanceof PeerInfoable) {
            peerInfo = ((PeerInfoable)endPoint).getPeerInfo();
         }
      } catch (ServerNotActiveException var3) {
      }

      return peerInfo;
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources) {
      this.startRollback(xid, coURL, assignedResources, (AuthenticatedUser)null);
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources, AuthenticatedUser wireCallerId) {
      this.startRollback(xid, coURL, assignedResources, (Object)wireCallerId, (String[])null);
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources, Object wireCallerId) {
      this.startRollback(xid, coURL, assignedResources, (Object)wireCallerId, (String[])null);
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources, AuthenticatedUser wireCallerId, String[] knownResources) {
      this.startRollback(xid, coURL, assignedResources, (Object)wireCallerId, knownResources, (Map)null);
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources, Object wireCallerId, String[] knownResources) {
      this.startRollback(xid, coURL, assignedResources, (Object)wireCallerId, knownResources, (Map)null);
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources, AuthenticatedUser wireCallerId, String[] knownResources, Map properties) {
      this.startRollback(xid, coURL, assignedResources, (Object)wireCallerId, knownResources, properties, false);
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources, Object wireCallerId, String[] knownResources, Map properties) {
      this.startRollback(xid, coURL, assignedResources, wireCallerId, knownResources, properties, false);
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources, AuthenticatedUser wireCallerId, String[] knownResources, Map properties, boolean retry) {
      this.startRollback(xid, coURL, assignedResources, (Object)wireCallerId, knownResources, properties, retry);
   }

   public void startRollback(Xid xid, String coURL, String[] assignedResources, Object wireCallerId, String[] knownResources, Map properties, boolean retry) {
      boolean var10000 = this.getTM().getTransaction(xid) == null;
      if (INSTR_ENABLED && !retry) {
         this.incrementUsernameStats(this.rollbackUsernameStats);
      }

      Object callerId = null;
      if (wireCallerId == null) {
         callerId = SubjectUtils.getAnonymousSubject();
      } else {
         callerId = SecurityServiceManager.getASFromWire((AuthenticatedSubject)wireCallerId);
      }

      StringBuffer sb;
      int dummyTimeout;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         sb = new StringBuffer(50);
         dummyTimeout = assignedResources == null ? 0 : assignedResources.length;

         for(int i = 0; i < dummyTimeout; ++i) {
            if (i > 0) {
               sb.append(" ");
            }

            sb.append(assignedResources[i]);
         }

         TxDebug.JTA2PC.debug("startRollback: " + xid + " for coordinator=" + coURL + " resources=" + sb);
      }

      sb = null;
      PeerInfo peerInfo = this.getPeerInfo(sb);
      if (!this.isPeerInfoIsServer(peerInfo)) {
         TXLogger.logUserNotAuthorizedForStartRollback(this.getUserName());
      } else if (ReceiveSecureAction.stranger(this.getHostID(), "startRollback")) {
         TXLogger.logUserNotAuthorizedForStartRollback(this.getUserName());
      } else {
         try {
            dummyTimeout = this.getDefaultTimeoutSeconds();
            CoordinatorOneway co = null;
            CoordinatorOneway2 co2 = null;
            ServerTransactionImpl tx = this.getOrCreateTransaction(xid, dummyTimeout);

            CoordinatorDescriptor cd;
            try {
               co = this.getCoordinator(coURL, tx);
               if (co == null) {
                  throw new SystemException("Could not obtain coordinator at " + coURL);
               }

               if (co instanceof CoordinatorOneway2) {
                  co2 = (CoordinatorOneway2)co;
               }

               if (knownResources != null && knownResources.length > 0) {
                  ArrayList newAssignedResources = new ArrayList();
                  TreeSet coResources = new TreeSet();

                  for(int i = 0; i < knownResources.length; ++i) {
                     coResources.add(knownResources[i]);
                  }

                  ArrayList localResources = tx.getResourceInfoList();
                  if (localResources != null) {
                     localResources = (ArrayList)localResources.clone();
                     Iterator it = localResources.iterator();

                     while(it.hasNext()) {
                        ResourceInfo ri = (ResourceInfo)it.next();
                        if (!coResources.contains(ri.getName())) {
                           newAssignedResources.add(ri.getName());
                        }
                     }
                  }

                  if (newAssignedResources.size() > 0) {
                     if (assignedResources != null && assignedResources.length > 0) {
                        for(int i = 0; i < assignedResources.length; ++i) {
                           newAssignedResources.add(assignedResources[i]);
                        }
                     }

                     assignedResources = (String[])((String[])newAssignedResources.toArray(new String[newAssignedResources.size()]));
                  }
               }

               if (properties != null) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.JTA2PC.debug("startRollback: Add properties: " + properties);
                  }

                  tx.addProperties(properties);
               }

               if (INSTR_ENABLED && !retry) {
                  tx.check("SCBeforeRollback", this.getTM().getLocalCoordinatorDescriptor().getServerName());
               }

               if (!tx.localRollback(assignedResources, callerId)) {
                  if (TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.JTA2PC.debug("startRollback: local rollback failed, not responding");
                  }

                  if (INSTR_ENABLED && !retry) {
                     tx.check("SCAfterRollback", this.getTM().getLocalCoordinatorDescriptor().getServerName());
                  }

                  String specialHeurMessageNoXAResource = "NO_XARESOURCE";
                  TxDebug.JTA2PC.debug("SubCoordinatorImpl.startRollback No Resource NakRollbackAction this:" + this);
                  if (co2 != null) {
                     TxDebug.JTA2PC.debug("SubCoordinatorImpl.startRollback No Resource NakRollbackAction co2 this:" + this);

                     try {
                        cd = tx.getCoordinatorDescriptor();
                        String[] allResources = this.getResourceInfoStrings(tx);
                        this.runNakRollbackAction(xid, coURL, tx, co2, specialHeurMessageNoXAResource, cd, allResources);
                     } catch (Exception var21) {
                     }
                  } else if (co != null) {
                     TxDebug.JTA2PC.debug("SubCoordinatorImpl.startCommit No Resource NakRollbackAction co this:" + this);

                     try {
                        cd = tx.getCoordinatorDescriptor();
                        this.runNakRollbackAction(xid, coURL, co, specialHeurMessageNoXAResource, cd);
                     } catch (Exception var20) {
                     }
                  }

                  return;
               }

               if (INSTR_ENABLED && !retry) {
                  tx.check("SCAfterRollback", this.getTM().getLocalCoordinatorDescriptor().getServerName());
               }

               short heurStatus = tx.getHeuristicStatus(8);
               if (heurStatus == 0) {
                  if (co2 != null) {
                     try {
                        cd = tx.getCoordinatorDescriptor();
                        SecureAction.runAction(kernelID, new AckRollbackAction((CoordinatorOneway)null, co2, xid, this.getScUrl(), tx.getRolledbackResources()), CoordinatorDescriptor.getServerURL(coURL), "co2.ackRollback");
                     } catch (Exception var25) {
                        TxDebug.JTA2PC.debug("ackRollback remote exception: " + var25);
                     }
                  } else if (co != null) {
                     try {
                        cd = tx.getCoordinatorDescriptor();
                        SecureAction.runAction(kernelID, new AckRollbackAction(co, (CoordinatorOneway2)null, xid, this.getScUrl(), (String[])null), CoordinatorDescriptor.getServerURL(coURL), "co.ackRollback");
                     } catch (Exception var24) {
                        TxDebug.JTA2PC.debug("ackRollback remote exception: " + var24);
                     }
                  }
               } else {
                  String heuristicErrorMsg = tx.getHeuristicErrorMessage();
                  TXLogger.logHeuristicCompletion(tx.toString(), heuristicErrorMsg);
                  CoordinatorDescriptor cd;
                  if (co2 != null) {
                     try {
                        cd = tx.getCoordinatorDescriptor();
                        SecureAction.runAction(kernelID, new NakRollbackAction((CoordinatorOneway)null, co2, xid, this.getScUrl(), heurStatus, tx.getHeuristicErrorMessage(), tx.getCommittedResources(), tx.getRolledbackResources()), CoordinatorDescriptor.getServerURL(coURL), "co2.nakRollback");
                     } catch (Exception var23) {
                        TxDebug.JTA2PC.debug("nakRollback remote exception: " + var23);
                     }
                  } else {
                     try {
                        cd = tx.getCoordinatorDescriptor();
                        SecureAction.runAction(kernelID, new NakRollbackAction(co, (CoordinatorOneway2)null, xid, this.getScUrl(), heurStatus, tx.getHeuristicErrorMessage(), (String[])null, (String[])null), CoordinatorDescriptor.getServerURL(coURL), "co.nakRollback");
                     } catch (Exception var22) {
                        TxDebug.JTA2PC.debug("nakRollback remote exception: " + var22);
                     }
                  }
               }
            } catch (SystemException var27) {
               SystemException se = var27;
               if (co != null) {
                  try {
                     cd = tx.getCoordinatorDescriptor();
                     SecureAction.runAction(kernelID, new NakRollbackAction(co, (CoordinatorOneway2)null, xid, this.getScUrl(), (short)2, se.toString(), (String[])null, (String[])null), CoordinatorDescriptor.getServerURL(coURL), "co.nakRollback");
                  } catch (Exception var26) {
                     if (TxDebug.JTA2PC.isDebugEnabled()) {
                        TxDebug.JTA2PC.debug("nakRollback remote exception: " + var26);
                     }
                  }
               }
            }
         } catch (Exception var28) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug("startRollback remote exception: " + var28);
            }
         }

      }
   }

   public void startRollback(Xid[] xids) throws RemoteException {
      int len = xids == null ? 0 : xids.length;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("SC.startRollback: " + len + " XIDs");
      }

      if (ReceiveSecureAction.stranger(this.getHostID(), "sc.startRollback")) {
         TXLogger.logUserNotAuthorizedForRollback(this.getUserName());
      } else {
         ServerTransactionImpl tx = null;

         for(int i = 0; i < xids.length; ++i) {
            tx = (ServerTransactionImpl)this.getTM().getTransaction(xids[i]);
            if (tx != null) {
               tx.localRollback();
            }
         }

      }
   }

   public Xid[] recover(String resourceName, String coordinatorURL) throws SystemException {
      if (ReceiveSecureAction.stranger(this.getHostID(), "recover")) {
         TXLogger.logUserNotAuthorizedForRecover(this.getUserName());
         return null;
      } else {
         try {
            ServerSCInfo sci = new ServerSCInfo(this.getScUrl());
            return sci.recover(resourceName, ServerCoordinatorDescriptor.getOrCreate(coordinatorURL), (ResourceDescriptor)null);
         } catch (SystemException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new SystemException("Subcoordinator raised exception on recover: " + var5);
         }
      }
   }

   public void rollback(String resourceName, Xid[] xids) throws SystemException, RemoteException {
      PeerInfo peerInfo = null;
      peerInfo = this.getPeerInfo(peerInfo);
      if (this.isPeerInfoIsServer(peerInfo)) {
         if (ReceiveSecureAction.stranger(this.getHostID(), "rollback recovery")) {
            TXLogger.logUserNotAuthorizedForRollback(this.getUserName());
         } else {
            ServerSCInfo localSC = new ServerSCInfo(this.getScUrl());
            localSC.rollback(resourceName, xids);
         }
      } else {
         TXLogger.logUserNotAuthorizedForRollback(this.getUserName());
      }
   }

   public void commit(String resourceName, Xid[] xids) throws SystemException, RemoteException {
      PeerInfo peerInfo = null;
      peerInfo = this.getPeerInfo(peerInfo);
      if (this.isPeerInfoIsServer(peerInfo)) {
         if (ReceiveSecureAction.stranger(this.getHostID(), "commit recovery (determiner)")) {
            TXLogger.logUserNotAuthorizedForCommit(this.getUserName());
         } else {
            ServerSCInfo localSC = new ServerSCInfo(this.getScUrl());
            localSC.commit(resourceName, xids);
         }
      } else {
         TXLogger.logUserNotAuthorizedForCommit(this.getUserName());
      }
   }

   public void nonXAResourceCommit(Xid xid, boolean onePhase, String resource) throws SystemException, RemoteException {
      PeerInfo peerInfo = null;
      peerInfo = this.getPeerInfo(peerInfo);
      if (this.isPeerInfoIsServer(peerInfo)) {
         if (ReceiveSecureAction.stranger(this.getHostID(), "nonXAResourceCommit")) {
            TXLogger.logUserNotAuthorizedForNonXACommit(this.getUserName());
            throw new SystemException("Failed to commit non XA resource");
         } else {
            int dummyTimeout = this.getDefaultTimeoutSeconds();
            ServerTransactionImpl tx = null;
            tx = this.getOrCreateTransaction(xid, dummyTimeout);
            ServerResourceInfo sri = (ServerResourceInfo)tx.getResourceInfo(resource);
            if (sri instanceof NonXAServerResourceInfo) {
               try {
                  ((NonXAServerResourceInfo)sri).commit(tx, onePhase, false, true);
               } catch (NonXAException var9) {
                  if (TxDebug.JTANonXA.isDebugEnabled()) {
                     TxDebug.JTANonXA.debug("SC.nonXAResourceCommit: " + xid + ", failed", var9);
                  }

                  throw new RemoteException("NonXAResource commit failed: " + var9);
               }
            } else {
               throw new SystemException("Attempt to perform a non-XA resource commit on a resource that is not a NonXAResource");
            }
         }
      } else {
         TXLogger.logUserNotAuthorizedForNonXACommit(this.getUserName());
         throw new SystemException("Failed to commit non XA resource");
      }
   }

   public void forceLocalRollback(Xid xid) throws RemoteException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("SC.forceLocalRollback: " + xid);
      }

      if (ReceiveSecureAction.stranger(this.getHostID(), "forceLocalRollback")) {
         TXLogger.logUserNotAuthorizedForForceLocalRollback(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx == null) {
            TXLogger.logForceLocalRollbackNoTx(xid.toString());
         } else {
            try {
               tx.forceLocalRollback();
            } catch (SystemException var4) {
               throw new RemoteException("Unable to perform local rollback", var4);
            }
         }
      }
   }

   public void forceLocalCommit(Xid xid) throws RemoteException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("SC.forceLocalCommit: " + xid);
      }

      if (ReceiveSecureAction.stranger(this.getHostID(), "forceLocalCommit")) {
         TXLogger.logUserNotAuthorizedForForceLocalCommit(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx == null) {
            TXLogger.logForceLocalCommitNoTx(xid.toString());
         } else {
            try {
               tx.forceLocalCommit();
            } catch (SystemException var4) {
               throw new RemoteException("Unable to perform local rollback", var4);
            }
         }
      }
   }

   public Map getProperties(String rmName) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "getPoperties")) {
         TXLogger.logUserNotAuthorizedForGetProperties(this.getUserName());
         return null;
      } else {
         ResourceDescriptor rd = XAResourceDescriptor.get(rmName);
         return rd == null ? null : rd.getProperties();
      }
   }

   protected ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }

   protected HostID getHostID() {
      try {
         EndPoint endPoint = ServerHelper.getClientEndPoint();
         HostID hostid = endPoint.getHostID();
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("RMI call coming from = " + ((ServerIdentity)hostid).getDomainName());
         }

         return hostid;
      } catch (Exception var3) {
         return null;
      }
   }

   private CoordinatorOneway getCoordinator(CoordinatorDescriptor aCoDesc, TransactionImpl tx) {
      return JNDIAdvertiser.getCoordinator(aCoDesc, tx);
   }

   private String getScUrl() {
      return this.getTM().getLocalCoordinatorURL();
   }

   public void addNotificationListener(NotificationListener listener, Object handback) throws IllegalArgumentException {
      this.notificationBroadcaster.addNotificationListener(listener, handback);
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      this.notificationBroadcaster.removeNotificationListener(listener);
   }

   public void handleNotification(Notification notification, Object handback) {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("SubCoordinatorImpl.handleNotification() handback=" + handback + ", notification=" + notification + ")");
      }

      if (ReceiveSecureAction.stranger(this.getHostID(), "handleNotification")) {
         TXLogger.logUserNotAuthorizedForStartCommit(this.getUserName());
      } else {
         if (notification instanceof PropertyChangeNotification) {
            PropertyChangeNotification pcn = (PropertyChangeNotification)notification;
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("SubCoordinatorImpl.handleNotification(notification=" + notification + ", handback=" + handback + ")");
            }

            ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).handleNotification(pcn, (String)handback);
         }

      }
   }

   public Map getSubCoordinatorInfo(String coURL) throws RemoteException {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("SubCoordinatorImpl.getSubCoordinatorInfo(" + coURL + ")");
      }

      if (ReceiveSecureAction.stranger(this.getHostID(), "getSubCoordinatorInfo")) {
         if (TxDebug.JTANamingStackTrace.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTANaming, "SubCoordinatorImpl.getSubCoordinatorImpl.getSubCoordinatorInfo coURL:" + coURL + " getHostID():" + this.getHostID() + " getUserName():" + this.getUserName());
         }

         TXLogger.logUserNotAuthorizedForGetSubCoordinatorInfo(this.getUserName());
         return null;
      } else {
         ServerCoordinatorDescriptor localDescriptor = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getLocalCoordinatorDescriptor();
         if (PlatformHelper.getPlatformHelper().isSSLURL(coURL)) {
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("SubCoordinatorImpl.getSubCoordinatorInfo check to return coURL=" + coURL + " localCoordinator=" + localDescriptor + " localSSLCoordinatorURL=" + (localDescriptor == null ? "null" : localDescriptor.getSSLCoordinatorURL()));
            }

            if (localDescriptor == null) {
               return null;
            }

            if (localDescriptor.getSSLCoordinatorURL() == null) {
               return null;
            }
         }

         CoordinatorDescriptor peer = ServerCoordinatorDescriptor.getOrCreate(coURL);
         return localDescriptor != null ? SubCoordinatorInfo.createMap(localDescriptor) : null;
      }
   }

   long getCommitUsernameStat(String username) {
      if (this.commitUsernameStats == null) {
         return -1L;
      } else {
         synchronized(this.commitUsernameStats) {
            Long val = (Long)this.commitUsernameStats.get(username);
            return val == null ? 0L : val;
         }
      }
   }

   Map getCommitUsernameStats() {
      if (this.commitUsernameStats == null) {
         return null;
      } else {
         synchronized(this.commitUsernameStats) {
            return (Map)((HashMap)this.commitUsernameStats).clone();
         }
      }
   }

   long getRollbackUsernameStat(String username) {
      if (this.rollbackUsernameStats == null) {
         return -1L;
      } else {
         synchronized(this.rollbackUsernameStats) {
            Long val = (Long)this.rollbackUsernameStats.get(username);
            return val == null ? 0L : val;
         }
      }
   }

   Map getRollbackUsernameStats() {
      if (this.rollbackUsernameStats == null) {
         return null;
      } else {
         synchronized(this.rollbackUsernameStats) {
            return (Map)((HashMap)this.rollbackUsernameStats).clone();
         }
      }
   }

   protected String getUserName() {
      return SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(kernelID));
   }

   private void incrementUsernameStats(Map stats) {
      synchronized(stats) {
         String key = SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(kernelID));
         Long val = (Long)stats.get(key);
         if (val != null) {
            val = new Long(val + 1L);
         } else {
            val = new Long(1L);
         }

         stats.put(key, val);
      }
   }

   public void recoveryServiceMigrated(String domainName, String serverName, String migratedToCoURL) {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("SubCoordinatorImpl.recoveryServiceMigrated(): domain=" + domainName + ", server=" + serverName + ", toCoURL=" + migratedToCoURL);
      }

      if (ReceiveSecureAction.stranger(this.getHostID(), "migration")) {
         TXLogger.logUserNotAuthorizedForCheckStatus(this.getUserName());
      } else {
         Iterator it = this.getTM().getTransactions();
         List preparedXids = new ArrayList();

         while(it.hasNext()) {
            ServerTransactionImpl tx = (ServerTransactionImpl)it.next();
            if (tx.isPrepared()) {
               CoordinatorDescriptor txcoord = tx.getCoordinatorDescriptor();
               if (txcoord.getServerName().equals(serverName) && txcoord.getDomainName().equals(domainName)) {
                  preparedXids.add(tx.getXid());
               }
            }
         }

         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("SubCoordinatorImpl.recoveryServiceMigrated(): domainName=" + domainName + ", serverName=" + serverName + "; invoking checkStatus() xids=" + preparedXids + " on " + migratedToCoURL);
         }

         CoordinatorDescriptor cd = new CoordinatorDescriptor(migratedToCoURL);
         Object co = CoordinatorFactory.getCachedCoordinator(cd, (PeerExchangeTransaction)null);
         if (co instanceof CoordinatorOneway3) {
            CoordinatorOneway3 coOneway = (CoordinatorOneway3)co;

            try {
               coOneway.checkStatus(serverName, (Xid[])((Xid[])preparedXids.toArray(new Xid[preparedXids.size()])), this.getTM().getLocalCoordinatorURL());
            } catch (RemoteException var10) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("SubCoordinatorImpl.recoveryServiceMigrated(): error invoking checkStatus()", var10);
               }
            }
         }

      }
   }

   static {
      String propVal = System.getProperty("weblogic.transaction.EnableInstrumentedTM");
      INSTR_ENABLED = propVal != null && propVal.equals("true");
   }

   private class NakRollbackAction implements PrivilegedExceptionAction {
      private CoordinatorOneway co = null;
      private CoordinatorOneway2 co2 = null;
      private Xid xid = null;
      private String scUrl = null;
      private short heurStatus = 0;
      private String heuristicErrorMessage = null;
      private String[] committedResources = null;
      private String[] rolledbackResources = null;

      NakRollbackAction(CoordinatorOneway aco, CoordinatorOneway2 aco2, Xid axid, String ascUrl, short aheurStatus, String aheuristicErrorMessage, String[] acommittedResources, String[] arolledbackResources) {
         this.co = aco;
         this.co2 = aco2;
         this.xid = axid;
         this.scUrl = ascUrl;
         this.heurStatus = aheurStatus;
         this.heuristicErrorMessage = aheuristicErrorMessage;
         this.committedResources = acommittedResources;
         this.rolledbackResources = arolledbackResources;
      }

      public Object run() throws Exception {
         if (this.co2 != null) {
            this.co2.nakRollback(this.xid, SubCoordinatorImpl.this.getScUrl(), this.heurStatus, this.heuristicErrorMessage, this.committedResources, this.rolledbackResources);
         } else if (this.co != null) {
            this.co.nakRollback(this.xid, SubCoordinatorImpl.this.getScUrl(), this.heurStatus, this.heuristicErrorMessage);
         }

         return null;
      }
   }

   private class AckRollbackAction implements PrivilegedExceptionAction {
      private CoordinatorOneway co = null;
      private CoordinatorOneway2 co2 = null;
      private Xid xid;
      private String scUrl = null;
      private String[] rolledbackResources;

      AckRollbackAction(CoordinatorOneway aco, CoordinatorOneway2 aco2, Xid axid, String ascUrl, String[] arolledbackResources) {
         this.co = aco;
         this.co2 = aco2;
         this.xid = axid;
         this.scUrl = ascUrl;
         this.rolledbackResources = arolledbackResources;
      }

      public Object run() throws Exception {
         if (this.co2 != null) {
            this.co2.ackRollback(this.xid, SubCoordinatorImpl.this.getScUrl(), this.rolledbackResources);
         } else if (this.co != null) {
            this.co.ackRollback(this.xid, this.scUrl);
         }

         return null;
      }
   }

   private class NakCommitAction implements PrivilegedExceptionAction {
      private CoordinatorOneway co = null;
      private CoordinatorOneway2 co2 = null;
      private Xid xid = null;
      private String scUrl = null;
      private String scUrlCrossDomain = null;
      private short heurStatus = 0;
      private String heuristicErrorMessage = null;
      private String[] committedResources = null;
      private String[] rolledbackResources = null;

      NakCommitAction(CoordinatorOneway aco, CoordinatorOneway2 aco2, Xid axid, String ascUrl, String ascUrlCrossDomain, short aheurStatus, String aheuristicErrorMessage, String[] acommittedResources, String[] arolledbackResources) {
         this.co = aco;
         this.co2 = aco2;
         this.xid = axid;
         this.scUrl = ascUrl;
         this.scUrlCrossDomain = ascUrlCrossDomain;
         this.heurStatus = aheurStatus;
         this.heuristicErrorMessage = aheuristicErrorMessage;
         this.committedResources = acommittedResources;
         this.rolledbackResources = arolledbackResources;
      }

      public Object run() throws Exception {
         if (this.co2 != null) {
            String scURLWithNewURLIfAny = SubCoordinatorImpl.this.getScUrl() + (this.scUrlCrossDomain == null ? "" : "~" + this.scUrlCrossDomain);
            this.co2.nakCommit(this.xid, scURLWithNewURLIfAny, this.heurStatus, this.heuristicErrorMessage, this.committedResources, this.rolledbackResources);
         } else if (this.co != null) {
            this.co.nakCommit(this.xid, SubCoordinatorImpl.this.getScUrl(), this.heurStatus, this.heuristicErrorMessage);
         }

         return null;
      }
   }

   private class AckCommitAction implements PrivilegedExceptionAction {
      private CoordinatorOneway co = null;
      private CoordinatorOneway2 co2 = null;
      private Xid xid = null;
      private String scUrl = null;
      private String[] committedResources;

      AckCommitAction(CoordinatorOneway aco, CoordinatorOneway2 aco2, Xid axid, String ascUrl, String[] acommittedResources) {
         this.co = aco;
         this.co2 = aco2;
         this.xid = axid;
         this.scUrl = ascUrl;
         this.committedResources = acommittedResources;
      }

      public Object run() throws Exception {
         if (this.co2 != null) {
            this.co2.ackCommit(this.xid, SubCoordinatorImpl.this.getScUrl(), this.committedResources);
         } else if (this.co != null) {
            this.co.ackCommit(this.xid, this.scUrl);
         }

         return null;
      }
   }

   private class AckPrepareAction implements PrivilegedExceptionAction {
      private CoordinatorOneway co = null;
      private Xid xid = null;
      private String scUrl = null;
      private int vote = 0;

      AckPrepareAction(CoordinatorOneway aco, Xid axid, String ascUrl, int avote) {
         this.co = aco;
         this.xid = axid;
         this.scUrl = ascUrl;
         this.vote = avote;
      }

      public Object run() throws Exception {
         this.co.ackPrepare(this.xid, SubCoordinatorImpl.this.getScUrl(), this.vote);
         return null;
      }
   }

   private class StartRollbackAction implements PrivilegedExceptionAction {
      private CoordinatorOneway co = null;
      private PropagationContext propagationCtx = null;

      StartRollbackAction(CoordinatorOneway aco, PropagationContext propCtx) {
         this.co = aco;
         this.propagationCtx = propCtx;
      }

      public Object run() throws Exception {
         this.co.startRollback(this.propagationCtx);
         return null;
      }
   }

   private class AckPrePrepareAction implements PrivilegedExceptionAction {
      private CoordinatorOneway co = null;
      private PropagationContext propagationCtx = null;

      AckPrePrepareAction(CoordinatorOneway aco, PropagationContext propCtx) {
         this.co = aco;
         this.propagationCtx = propCtx;
      }

      public Object run() throws Exception {
         this.co.ackPrePrepare(this.propagationCtx);
         return null;
      }
   }
}
