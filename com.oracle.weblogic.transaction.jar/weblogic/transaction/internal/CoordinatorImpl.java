package weblogic.transaction.internal;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.EndPoint;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TransactionSystemException;

class CoordinatorImpl extends SubCoordinatorImpl implements Coordinator3, CoordinatorOneway3, Constants, CoordinatorService {
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void commit(PropagationContext pctx) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, SystemException {
      PeerInfo peerInfo = null;

      try {
         EndPoint endPoint = ServerHelper.getClientEndPoint();
         if (endPoint != null && endPoint instanceof PeerInfoable) {
            peerInfo = ((PeerInfoable)endPoint).getPeerInfo();
         }
      } catch (ServerNotActiveException var8) {
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("co.commit  is the call coming from a server: " + peerInfo.isServer());
      }

      if (peerInfo != null && peerInfo.isServer() && ReceiveSecureAction.stranger(this.getHostID(), "co.commit")) {
         TXLogger.logUserNotAuthorizedForCommit(this.getUserName());
      } else if (!TransactionService.isRunning()) {
         throw new SystemException("The server is being suspended or shut down.  No new transaction commit requests will be accepted.");
      } else {
         ServerTransactionImpl tx;
         try {
            tx = (ServerTransactionImpl)pctx.getTransaction();
         } catch (TransactionSystemException var7) {
            throw new SystemException(var7.getMessage());
         }

         if (tx == null) {
            throw new RollbackException("This transaction does not exist on the coordinating server.  It was probably rolled back and forgotten.");
         } else {
            if (!tx.isImportedTransaction()) {
               tx.setOwnerTransactionManager(this.getTM());
               tx.commit();
            } else {
               try {
                  tx.internalCommit(true);
               } catch (AbortRequestedException var5) {
               } catch (XAException var6) {
               }
            }

         }
      }
   }

   public void ackPrePrepare(PropagationContext pctx) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "ackPrePrepare")) {
         TXLogger.logUserNotAuthorizedForAckPrePrepare(this.getUserName());
      } else {
         ServerTransactionImpl tx;
         try {
            tx = (ServerTransactionImpl)pctx.getTransaction();
         } catch (TransactionSystemException var4) {
            return;
         }

         if (tx != null) {
            tx.ackPrePrepare();
         }
      }
   }

   public void ackPrepare(Xid xid, String scURL, int scVote) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "ackPrepare")) {
         TXLogger.logUserNotAuthorizedForAckPrepare(this.getUserName());
      } else {
         try {
            ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
            if (tx == null) {
               return;
            }

            tx.ackPrepare(scURL, scVote);
         } catch (Throwable var5) {
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.JTA2PC.debug("ackPrepare FAILED", var5);
            }
         }

      }
   }

   public void rollback(PropagationContext pctx) throws IllegalStateException, SystemException {
      PeerInfo peerInfo = null;

      try {
         EndPoint endPoint = ServerHelper.getClientEndPoint();
         if (endPoint != null && endPoint instanceof PeerInfoable) {
            peerInfo = ((PeerInfoable)endPoint).getPeerInfo();
         }
      } catch (ServerNotActiveException var6) {
      }

      if (peerInfo != null && peerInfo.isServer() && ReceiveSecureAction.stranger(this.getHostID(), "co.rollback")) {
         TXLogger.logUserNotAuthorizedForRollback(this.getUserName());
      } else {
         ServerTransactionImpl tx;
         try {
            tx = (ServerTransactionImpl)pctx.getTransaction();
         } catch (TransactionSystemException var5) {
            throw new SystemException(var5.getMessage());
         }

         if (tx != null) {
            if (!tx.isImportedTransaction()) {
               tx.rollback();
            } else {
               tx.internalRollback();
            }
         }

      }
   }

   public void checkStatus(Xid[] xids, String scURL) {
      if (!PlatformHelper.getPlatformHelper().isServerRunning()) {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.JTA2PC.debug("CO checkStatus: return because server is not in Running state, for SC=" + scURL);
         }

      } else {
         if (PlatformHelper.getPlatformHelper().isInCluster()) {
            TransactionRecoveryService localTRS = TransactionRecoveryService.getLocalTransactionRecoveryService();
            if (localTRS == null || !localTRS.isActive()) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.JTA2PC.debug("CO checkStatus: return because server TransactionRecoveryService is not active, for SC=" + scURL);
               }

               return;
            }
         }

         if (ReceiveSecureAction.stranger(this.getHostID(), "checkStatus")) {
            TXLogger.logUserNotAuthorizedForCheckStatus(this.getUserName());
         } else {
            this.doCheckStatus(xids, scURL);
         }
      }
   }

   public void checkStatus(String serverName, Xid[] xids, String scURL) throws RemoteException {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("CO checkStatus: serverName=" + serverName + ", xids=" + Arrays.toString(xids) + ", scURL=" + scURL);
      }

      if (!PlatformHelper.getPlatformHelper().isServerRunning()) {
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("CO checkStatus: return because local server is not in Running state: serverName=" + serverName + ", xids=" + Arrays.toString(xids) + ", scURL=" + scURL);
         }

      } else {
         TransactionRecoveryService trs;
         if (PlatformHelper.getPlatformHelper().isInCluster()) {
            trs = TransactionRecoveryService.getLocalTransactionRecoveryService();
            if (trs == null || !trs.isActive()) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("CO checkStatus: return because local server TransactionRecoveryService is not active: serverName=" + serverName + ", xids=" + Arrays.toString(xids) + ", scURL=" + scURL);
               }

               return;
            }
         }

         if (ReceiveSecureAction.stranger(this.getHostID(), "checkStatus() serverName=" + serverName)) {
            TXLogger.logUserNotAuthorizedForCheckStatus(this.getUserName());
         } else {
            try {
               trs = TransactionRecoveryService.get(serverName);
               if (trs != null && trs.isActive()) {
                  trs.checkStatusFrom(scURL);
                  this.doCheckStatus(xids, scURL);
               } else {
                  if (TxDebug.JTAMigration.isDebugEnabled()) {
                     TxDebug.JTAMigration.debug("CO checkStatus: return because migrated TransactionRecoveryService(" + serverName + ") is not active: serverName=" + serverName + ", xids=" + Arrays.toString(xids) + ", scURL=" + scURL);
                  }

               }
            } catch (Exception var6) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("CO checkStatus: error for migrated TransactionRecoveryService(" + serverName + ") : serverName=" + serverName + ", xids=" + Arrays.toString(xids) + ", scURL=" + scURL, var6);
               }

               RemoteException re = new RemoteException();
               re.initCause(var6);
               throw re;
            }
         }
      }
   }

   private void doCheckStatus(Xid[] xids, String scURL) {
      ServerTransactionImpl tx = null;
      ArrayList rbXids = null;
      int len = xids == null ? 0 : xids.length;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("checkStatus: for SC=" + scURL + " " + len + " XIDs: " + Arrays.toString(xids));
      }

      for(int i = 0; i < len; ++i) {
         tx = (ServerTransactionImpl)this.getTM().getTransaction(xids[i]);
         if (tx == null) {
            if (rbXids == null) {
               rbXids = new ArrayList(1);
            }

            rbXids.add(xids[i]);
         }
      }

      if (rbXids != null) {
         CoordinatorDescriptor cd = ServerCoordinatorDescriptor.getOrCreate(scURL);
         SubCoordinatorOneway sc = JNDIAdvertiser.getSubCoordinator(cd, tx);
         if (sc != null) {
            Xid[] rbXidsArray = new Xid[rbXids.size()];

            try {
               SecureAction.runAction(kernelID, new StartRollbackAction(sc, (Xid[])((Xid[])rbXids.toArray(rbXidsArray))), CoordinatorDescriptor.getServerURL(scURL), "sc.startRollback");
            } catch (Exception var10) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.JTA2PC.debug("checkStatus: sc.startRollback failure: ", var10);
               }
            }
         }
      }

   }

   public void ackCommit(Xid xid, String scURL) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "ackCommit")) {
         TXLogger.logUserNotAuthorizedForAckCommit(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx != null) {
            tx.ackCommit(scURL);
         }
      }
   }

   public void ackCommit(Xid xid, String scURL, String[] committedResources) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "ackCommit")) {
         TXLogger.logUserNotAuthorizedForAckCommit(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx != null) {
            tx.ackCommit(scURL, committedResources);
         }
      }
   }

   public void nakCommit(Xid xid, String scURL, short completionStatus, String heuristicErrorMsg) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "nakCommit")) {
         TXLogger.logUserNotAuthorizedForNakCommit(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx != null) {
            tx.nakCommit(scURL, completionStatus, heuristicErrorMsg);
         }
      }
   }

   public void nakCommit(Xid xid, String scURL, short completionStatus, String heuristicErrorMsg, String[] committedResources, String[] rolledbackResources) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "nakCommit")) {
         TXLogger.logUserNotAuthorizedForNakCommit(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx != null) {
            tx.nakCommit(scURL, completionStatus, heuristicErrorMsg, committedResources, rolledbackResources);
         }
      }
   }

   public void startRollback(PropagationContext pctx) throws RemoteException {
      if (ReceiveSecureAction.stranger(this.getHostID(), "startRollback")) {
         TXLogger.logUserNotAuthorizedForStartRollback(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)pctx.getTransaction();
         if (tx != null) {
            try {
               tx.rollback();
            } catch (Exception var4) {
            }
         }

      }
   }

   public void ackRollback(Xid xid, String scURL) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "ackRollback")) {
         TXLogger.logUserNotAuthorizedForAckRollback(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx != null) {
            tx.ackRollback(scURL);
         }
      }
   }

   public void ackRollback(Xid xid, String scURL, String[] rolledbackResources) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "ackRollback")) {
         TXLogger.logUserNotAuthorizedForAckRollback(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx != null) {
            tx.ackRollback(scURL, rolledbackResources);
         }
      }
   }

   public void nakRollback(Xid xid, String scURL, short completionStatus, String heuristicErrorMsg) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "nakRollback")) {
         TXLogger.logUserNotAuthorizedForNakRollback(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx != null) {
            tx.nakRollback(scURL, completionStatus, heuristicErrorMsg);
         }
      }
   }

   public void nakRollback(Xid xid, String scURL, short completionStatus, String heuristicErrorMsg, String[] committedResources, String[] rolledbackResources) {
      if (ReceiveSecureAction.stranger(this.getHostID(), "nakRollback")) {
         TXLogger.logUserNotAuthorizedForNakRollback(this.getUserName());
      } else {
         ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
         if (tx != null) {
            tx.nakRollback(scURL, completionStatus, heuristicErrorMsg, committedResources, rolledbackResources);
         }
      }
   }

   public Map getProperties() {
      Map props = new HashMap(5);
      props.put("propagationContextVersion", new Integer(PropagationContext.getVersion()));
      props.put("coordinatorURL", this.getTM().getLocalCoordinatorURL());
      String name = ManagementService.getRuntimeAccess(kernelID).getServerName();
      props.put("serverName", name);
      return props;
   }

   public int xaPrepare(PropagationContext pctx) throws XAException {
      ServerTransactionImpl tx = null;

      try {
         tx = (ServerTransactionImpl)pctx.getTransaction();
      } catch (TransactionSystemException var4) {
         XAResourceHelper.throwXAException(-3, "Cannot prepare imported transaction.", var4);
      }

      return tx != null ? this.getTM().getXAResource().prepare(tx.getForeignXid()) : 0;
   }

   public void xaCommit(Xid foreignXid) throws XAException {
      if (!TransactionService.isRunning()) {
         XAResourceHelper.throwXAException(-3, "The server is being suspend or shut down.  No new transaction commit requests will be accepted.");
      }

      this.getTM().getXAResource().commit(foreignXid, false);
   }

   public void xaRollback(Xid foreignXid) throws XAException {
      this.getTM().getXAResource().rollback(foreignXid);
   }

   /** @deprecated */
   @Deprecated
   public Xid[] xaRecover() throws XAException {
      return this.getTM().getXAResource().recover(25165824);
   }

   public Xid[] xaRecover(int flags) throws XAException {
      return this.getTM().getXAResource().recover(flags);
   }

   public void xaForget(Xid foreignXid) {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug("Coordinator.forget(foreignXid=" + XAResourceHelper.xidToString(foreignXid) + ")");
      }

      try {
         this.getTM().getXAResource().forget(foreignXid);
      } catch (XAException var3) {
      }

   }

   public void forceGlobalRollback(Xid xid) throws SystemException, RemoteException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("SC.forceGlobalRollback: " + xid);
      }

      PeerInfo peerInfo = null;

      try {
         EndPoint endPoint = ServerHelper.getClientEndPoint();
         if (endPoint != null && endPoint instanceof PeerInfoable) {
            peerInfo = ((PeerInfoable)endPoint).getPeerInfo();
         }
      } catch (ServerNotActiveException var4) {
      }

      if (peerInfo != null && peerInfo.isServer()) {
         if (ReceiveSecureAction.stranger(this.getHostID(), "forceGlobalRollback")) {
            TXLogger.logUserNotAuthorizedForForceGlobalRollback(this.getUserName());
         } else {
            ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
            if (tx == null) {
               TXLogger.logForceGlobalRollbackNoTx(xid.toString());
            } else {
               tx.forceGlobalRollback();
            }
         }
      }
   }

   public void forceGlobalCommit(Xid xid) throws SystemException, RemoteException {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug("SC.forceGlobalCommit: " + xid);
      }

      PeerInfo peerInfo = null;

      try {
         EndPoint endPoint = ServerHelper.getClientEndPoint();
         if (endPoint != null && endPoint instanceof PeerInfoable) {
            peerInfo = ((PeerInfoable)endPoint).getPeerInfo();
         }
      } catch (ServerNotActiveException var4) {
      }

      if (peerInfo != null && peerInfo.isServer()) {
         if (ReceiveSecureAction.stranger(this.getHostID(), "forceGlobalCommit")) {
            TXLogger.logUserNotAuthorizedForForceGlobalCommit(this.getUserName());
         } else {
            ServerTransactionImpl tx = (ServerTransactionImpl)this.getTM().getTransaction(xid);
            if (tx == null) {
               TXLogger.logForceGlobalCommitNoTx(xid.toString());
            } else {
               tx.forceGlobalCommit();
            }
         }
      }
   }

   public Object invokeCoordinatorService(String serviceName, Object data) throws RemoteException, SystemException {
      ServerTransactionManagerImpl tm = this.getTM();
      return tm.invokeCoordinatorService(serviceName, data);
   }

   private class StartRollbackAction implements PrivilegedExceptionAction {
      private SubCoordinatorOneway sc;
      private Xid[] xids;

      StartRollbackAction(SubCoordinatorOneway aSC, Xid[] aXids) {
         this.sc = aSC;
         this.xids = aXids;
      }

      public Object run() throws Exception {
         this.sc.startRollback(this.xids);
         return null;
      }
   }
}
