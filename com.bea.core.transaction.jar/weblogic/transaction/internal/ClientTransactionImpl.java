package weblogic.transaction.internal;

import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public final class ClientTransactionImpl extends TransactionImpl {
   ClientTransactionImpl(Xid axid, int aTimeoutSec, int aTimeToLiveSec) {
      super(axid, aTimeoutSec, aTimeToLiveSec);
   }

   ClientTransactionImpl(Xid xid, Xid foreignXid, int timeOutSecs, int timeToLiveSecs) {
      super(xid, foreignXid, timeOutSecs, timeToLiveSecs);
   }

   public int internalPrepare() throws AbortRequestedException, RollbackException, SystemException, XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAGateway, this, "internalPrepare");
      }

      this.checkOwner();
      this.enforceCheckedTransaction();
      synchronized(this) {
         label162: {
            switch (this.getState()) {
               case 1:
                  if (!this.isMarkedRollback()) {
                     this.setPreparing();
                     break label162;
                  } else {
                     this.abort();
                  }
               case 9:
               case 10:
                  this.throwRollbackException();
                  break;
               case 2:
               case 3:
               case 5:
               default:
                  break label162;
               case 4:
               case 6:
               case 7:
               case 8:
               case 11:
               case 12:
            }

            throw new IllegalStateException("Cannot prepare imported transaction.  " + this.getXid().toString());
         }
      }

      int vote = 0;
      Coordinator co = (Coordinator)this.getCoordinator();
      if (co == null) {
         this.setUnknown();
         throw new SystemException("No coordinator is assigned.  Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
      } else {
         if (co instanceof Coordinator2) {
            try {
               vote = ((Coordinator2)co).xaPrepare(this.getRequestPropagationContext());
               this.setPrepared();
            } catch (RemoteException var10) {
               String msg = "Cannot prepare imported transaction " + this.getXid().toString() + ".  Unable to contact coordinator " + this.getCoordinatorURL() + ".";
               if (!isRecoverablePreInvokeFailure(var10)) {
                  throw new SystemException(msg);
               }

               this.setRolledBack();
               XAResourceHelper.throwXAException(101, msg, var10);
            } catch (XAException var11) {
               if (XAResourceHelper.isRollbackErrorCode(var11.errorCode)) {
                  this.setRolledBack();
               }

               throw var11;
            } finally {
               if (this.isPreparing()) {
                  this.setUnknown();
               }

            }
         } else {
            String msg = "Cannot prepare imported transaction " + this.getXid().toString() + ".  Server does not support importing transactions.";
            this.abort(msg);
         }

         return vote;
      }
   }

   private static boolean isRecoverablePreInvokeFailure(RemoteException e) {
      if (!(e instanceof UnknownHostException) && !(e instanceof ConnectException) && !(e instanceof ConnectIOException) && !(e instanceof NoSuchObjectException)) {
         return e instanceof MarshalException ? false : false;
      } else {
         return true;
      }
   }

   public void internalCommit(boolean onePhase) throws AbortRequestedException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAGateway, this, "internalCommit");
      }

      this.checkOwner();
      synchronized(this) {
         String msg;
         switch (this.getState()) {
            case 1:
               if (this.isMarkedRollback()) {
                  this.abort();
               } else if (onePhase) {
                  this.setCommitting();
               } else {
                  msg = "commit(onePhase=false).  Illegal State (Expected: prepared).  Please make sure that prepare is called if it is not a one phase commit.  " + this.getXid().toString();
                  XAResourceHelper.throwXAException(-6, msg);
               }
            case 2:
            case 3:
            case 5:
            default:
               break;
            case 6:
               if (!onePhase) {
                  this.setCommitting();
               } else {
                  msg = "commit(onePhase=true).  Illegal State (Expected: active).  Please make sure that commit is called with onePhase=false if it is not a one phase commit.  " + this.getXid().toString();
                  XAResourceHelper.throwXAException(-6, msg);
               }
               break;
            case 9:
            case 10:
               this.throwRollbackException();
            case 4:
            case 7:
            case 8:
            case 11:
            case 12:
               msg = "commit(onePhase=" + onePhase + ").  Illegal State (Expected: " + (onePhase ? "active" : "prepared") + ").  " + this.getXid().toString();
               throw new IllegalStateException(msg);
         }
      }

      Coordinator co = (Coordinator)this.getCoordinator();
      if (co == null) {
         this.setUnknown();
         throw new SystemException("No coordinator is assigned.  Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
      } else {
         try {
            if (onePhase) {
               co.commit(this.getRequestPropagationContext());
            } else if (!this.isReadOnly) {
               ((Coordinator2)co).xaCommit(this.getForeignXid());
            }

            this.setCommitted();
         } catch (RemoteException var15) {
            String msg = "Cannot commit imported transaction " + this.getXid().toString() + ".  Unable to contact coordinator " + this.getCoordinatorURL() + ".";
            if (!onePhase || !isRecoverablePreInvokeFailure(var15)) {
               throw new SystemException(msg);
            }

            this.setRolledBack();
            XAResourceHelper.throwXAException(101, msg, var15);
         } catch (RollbackException var16) {
            this.setRolledBack();
            throw var16;
         } catch (HeuristicMixedException var17) {
            this.setCommitted();
            throw var17;
         } catch (HeuristicRollbackException var18) {
            this.setCommitted();
            throw var18;
         } catch (SystemException var19) {
            throw var19;
         } catch (XAException var20) {
            if (XAResourceHelper.isRollbackErrorCode(var20.errorCode)) {
               this.setRolledBack();
            } else if (XAResourceHelper.isHeuristicErrorCode(var20.errorCode)) {
               this.setCommitted();
            }

            throw var20;
         } finally {
            if (this.isCommitting()) {
               this.setUnknown();
            }

         }

      }
   }

   public void internalForget() throws SystemException, XAException {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTAGateway, this, "internalForget");
      }

      this.checkOwner();
      Coordinator co = (Coordinator)this.getCoordinator();
      if (co == null) {
         this.setUnknown();
         throw new SystemException("No coordinator is assigned.  Make sure that the InterposedTransactionManager is looked up from a server's JNDI.");
      } else {
         try {
            if (!(co instanceof Coordinator2)) {
               String msg = "Cannot forget imported transaction " + this.getXid().toString() + ".  Coordinator does not support importing transactions.";
               throw new SystemException(msg);
            }

            try {
               ((Coordinator2)co).xaForget(this.getForeignXid());
            } catch (RemoteException var7) {
               String msg = "Cannot forget imported transaction " + this.getXid().toString() + ".  Unable to contact coordinator.";
               throw new SystemException(msg);
            }
         } finally {
            getTM().remove(this);
         }

      }
   }

   synchronized boolean setCoordinatorURL(String url) {
      CoordinatorDescriptor cd = CoordinatorDescriptor.getOrCreate(url);
      String handoffCoURL = this.updateCoordinatorDescriptor(url);
      if (handoffCoURL != null) {
         this.setLocalProperty("weblogic.client.handoffCoURL", handoffCoURL);
      }

      cd.setAdminCoordinatorURL(url);
      return this.setCoordinatorDescriptor(cd);
   }

   protected final synchronized void setRolledBack() {
      super.setRolledBack();
      getTM().remove(this);
   }

   private static TransactionManagerImpl getTM() {
      return TransactionManagerImpl.getTransactionManager();
   }
}
