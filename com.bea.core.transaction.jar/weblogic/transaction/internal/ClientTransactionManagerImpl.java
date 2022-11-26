package weblogic.transaction.internal;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

public final class ClientTransactionManagerImpl extends TransactionManagerImpl {
   private static final long serialVersionUID = 5724698313161606087L;
   private String coordinatorURL;
   private Object coordinator;

   public ClientTransactionManagerImpl() {
      if (this.tmXARes == null) {
         this.tmXARes = new ClientTMXAResource();
      }

   }

   public String toString() {
      StringBuffer sb = (new StringBuffer()).append("ClientTM");
      if (this.getCoordinatorURL() != null) {
         sb.append("[").append(this.getCoordinatorURL()).append("]");
      }

      return sb.toString();
   }

   void setCoordinatorURL(String url) {
      this.coordinatorURL = url;
   }

   String getCoordinatorURL() {
      return this.coordinatorURL;
   }

   void setCoordinator(Object co) {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".setCoordinator(co=" + co + ")");
      }

      this.coordinator = co;
   }

   public Object getCoordinator() {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".getCoordinator returns " + this.coordinator);
      }

      return this.coordinator;
   }

   public boolean isRunning() {
      return true;
   }

   public void addToListOfAckCommits(Xid xid, String scURL) {
   }

   public final TransactionImpl createTransaction(Xid xid, int aTimeOutSec, int aTimeToLiveSec) throws SystemException {
      return this.createTransaction(xid, aTimeOutSec, aTimeToLiveSec, false);
   }

   public final TransactionImpl createTransaction(Xid xid, int aTimeOutSec, int aTimeToLiveSec, boolean useSSLProtocol) throws SystemException {
      return new ClientTransactionImpl(xid, aTimeOutSec, aTimeToLiveSec);
   }

   public final TransactionImpl createTransaction(Xid xid, int aTimeOutSec, int aTimeToLiveSec, boolean useSSLProtocol, boolean isFromPropagationContext) throws SystemException {
      return new ClientTransactionImpl(xid, aTimeOutSec, aTimeToLiveSec);
   }

   public final TransactionImpl createImportedTransaction(Xid xid, Xid foreignXid, int timeOutSecs, int timeToLiveSecs) throws SystemException {
      TransactionImpl tx = new ClientTransactionImpl(xid, foreignXid, timeOutSecs, timeToLiveSecs);
      tx.setOwnerTransactionManager(getTM());
      tx.setCoordinatorURL(this.getCoordinatorURL());
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".createImportedTransaction(xid=" + xid + ", foreignXid=" + foreignXid + ", timeOutSecs=" + timeOutSecs + ") returns " + tx);
      }

      return tx;
   }

   public final void commit(Xid foreignXid) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".commit(foreignXid=" + XAResourceHelper.xidToString(foreignXid));
      }

      if (this.getCoordinatorURL() == null) {
         XAResourceHelper.throwXAException(-3, "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
      }

      try {
         ((Coordinator2)this.coordinator).xaCommit(XidImpl.create(foreignXid));
      } catch (RemoteException var3) {
         XAResourceHelper.throwXAException(-3, "Cannot commit imported transaction: " + XAResourceHelper.xidToString(foreignXid) + ".  Unable to contact Coordinator.", var3);
      }

   }

   public final void rollback(Xid foreignXid) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".rollback(foreignXid=" + XAResourceHelper.xidToString(foreignXid));
      }

      if (this.getCoordinatorURL() == null) {
         XAResourceHelper.throwXAException(-3, "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
      }

      try {
         ((Coordinator2)this.coordinator).xaRollback(XidImpl.create(foreignXid));
      } catch (RemoteException var3) {
         XAResourceHelper.throwXAException(-3, "Cannot rollback imported transaction: " + XAResourceHelper.xidToString(foreignXid) + ".  Unable to contact Coordinator.", var3);
      }

   }

   public final void forget(Xid foreignXid) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".forget(foreignXid=" + XAResourceHelper.xidToString(foreignXid));
      }

      if (this.getCoordinatorURL() == null) {
         XAResourceHelper.throwXAException(-3, "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
      }

      try {
         ((Coordinator2)this.coordinator).xaForget(XidImpl.create(foreignXid));
      } catch (RemoteException var3) {
         XAResourceHelper.throwXAException(-3, "Cannot forget imported transactions.  Unable to contact Coordinator.", var3);
      }

   }

   public Xid[] gatherClusterRecoverXids(String commit, Xid fXid) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".gatherClusterRecoverXids (not applicable to ClientTransactionManagerImpl)");
      }

      XAException notSupportedXAException = new XAException(this + ".gatherClusterRecoverXids (not applicable to ClientTransactionManagerImpl)");
      notSupportedXAException.errorCode = -7;
      throw notSupportedXAException;
   }

   public Xid[] recoverForeignXids(int flags) throws XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug(this + ".recoverForeignXids");
      }

      if (this.getCoordinatorURL() == null) {
         XAResourceHelper.throwXAException(-3, "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
      }

      try {
         return ((Coordinator2)this.coordinator).xaRecover(flags);
      } catch (RemoteException var3) {
         XAResourceHelper.throwXAException(-3, "Cannot recover imported transactions.  Unable to contact Coordinator.", var3);
         return new Xid[0];
      }
   }

   public static TransactionManagerImpl getTM() {
      return TransactionManagerImpl.getTransactionManager();
   }

   public void setITMXAResourceProperty(final String key, final Serializable value) {
      InterposedTransactionManagerXAResource.setAfterStartAction(new Runnable() {
         public void run() {
            System.out.println("InterposedTransactionManagerXAResource afterStartAction.run...");
            TransactionManager transactionManager = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();

            try {
               Transaction transaction = (Transaction)transactionManager.getTransaction();
               if (transaction != null) {
                  transaction.setProperty(key, value);
                  System.out.println("InterposedTransactionManagerXAResource afterStartAction.run transaction.setProperty(" + key + ", " + value + ") complete...");
               } else {
                  System.out.println("InterposedTransactionManagerXAResource afterStartAction.run transaction is null");
               }
            } catch (SystemException var3) {
               var3.printStackTrace();
            }

         }
      });
   }

   public void setResourceHealthy(String name) {
   }

   static {
      TransactionHelper.setTransactionHelper(PlatformHelper.getPlatformHelper().getTransactionHelper());
   }

   protected final class ClientTMXAResource extends TransactionManagerImpl.TMXAResource {
      private static final long serialVersionUID = 3463476059605728366L;

      protected ClientTMXAResource() {
         super();
      }

      private String getCoordinatorURL() {
         return ClientTransactionManagerImpl.this.getCoordinatorURL();
      }

      public void start(Xid foreignXid, int flags) throws XAException {
         if (this.getCoordinatorURL() == null) {
            throwXAException(-3, this.getErrMsg("start") + "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
         }

         super.start(foreignXid, flags);
      }

      public void end(Xid foreignXid, int flags) throws XAException {
         if (this.getCoordinatorURL() == null) {
            throwXAException(-3, this.getErrMsg("end") + "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
         }

         super.end(foreignXid, flags);
      }

      public int prepare(Xid foreignXid) throws XAException {
         if (this.getCoordinatorURL() == null) {
            throwXAException(-3, this.getErrMsg("prepare") + "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
         }

         int vote = super.prepare(foreignXid, true);
         if (vote == 3) {
            this.commit(foreignXid, false, true);
            return 3;
         } else {
            return vote;
         }
      }

      public void commit(Xid foreignXid, boolean onePhase) throws XAException {
         if (this.getCoordinatorURL() == null) {
            throwXAException(-3, this.getErrMsg("commit") + "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
         }

         if (ClientTransactionManagerImpl.this.isClusterwideRecoveryEnabled()) {
            if (!(foreignXid instanceof XidImpl)) {
               foreignXid = new XidImpl((Xid)foreignXid);
            }

            ((XidImpl)foreignXid).setClusterwideRecoveryEnabled(true);
         }

         super.commit((Xid)foreignXid, onePhase, true);
      }

      public void rollback(Xid foreignXid) throws XAException {
         if (this.getCoordinatorURL() == null) {
            throwXAException(-3, this.getErrMsg("rollback") + "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
         }

         super.rollback(foreignXid, true);
      }

      public Xid[] recover(int flag) throws XAException {
         if (this.getCoordinatorURL() == null) {
            throwXAException(-3, this.getErrMsg("recover") + "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
         }

         return super.recover(flag);
      }

      public void forget(Xid foreignXid) throws XAException {
         if (this.getCoordinatorURL() == null) {
            throwXAException(-3, this.getErrMsg("forget") + "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
         }

         super.forget(foreignXid, true);
      }

      public boolean isSameRM(XAResource xares) throws XAException {
         if (this.getCoordinatorURL() == null) {
            throwXAException(-3, this.getErrMsg("isSameRM") + "Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
         }

         return xares == this || xares instanceof ClientTMXAResource && this.getCoordinatorURL().equals(((ClientTMXAResource)xares).getCoordinatorURL());
      }
   }
}
