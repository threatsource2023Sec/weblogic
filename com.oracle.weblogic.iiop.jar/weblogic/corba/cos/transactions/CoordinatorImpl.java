package weblogic.corba.cos.transactions;

import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CosTransactions.Control;
import org.omg.CosTransactions.Coordinator;
import org.omg.CosTransactions.Inactive;
import org.omg.CosTransactions.NotSubtransaction;
import org.omg.CosTransactions.PropagationContext;
import org.omg.CosTransactions.RecoveryCoordinator;
import org.omg.CosTransactions.Resource;
import org.omg.CosTransactions.Status;
import org.omg.CosTransactions.SubtransactionAwareResource;
import org.omg.CosTransactions.SubtransactionsUnavailable;
import org.omg.CosTransactions.Synchronization;
import org.omg.CosTransactions.SynchronizationUnavailable;
import org.omg.CosTransactions.Unavailable;
import org.omg.CosTransactions._CoordinatorImplBase;
import weblogic.iiop.IIOPLogger;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.transaction.Transaction;
import weblogic.transaction.TxHelper;

public final class CoordinatorImpl extends _CoordinatorImplBase implements Activatable {
   protected static final boolean DEBUG = false;
   protected Xid xid;

   public CoordinatorImpl(Xid xid) {
      this.xid = xid;
   }

   public Status get_parent_status() {
      return this.get_status();
   }

   public Status get_status() {
      Transaction tx = this.getTx();
      if (tx == null) {
         return Status.StatusNoTransaction;
      } else {
         int status;
         try {
            status = tx.getStatus();
         } catch (SystemException var4) {
            throw new INVALID_TRANSACTION(var4.getMessage());
         }

         return OTSHelper.jta2otsStatus(status);
      }
   }

   public Status get_top_level_status() {
      return this.get_status();
   }

   public String get_transaction_name() {
      String name = "";
      Transaction tx = this.getTx();
      if (tx != null) {
         name = tx.getName();
      }

      return name;
   }

   public PropagationContext get_txcontext() throws Unavailable {
      Transaction tx = this.getTx();
      if (tx == null) {
         throw new Unavailable();
      } else {
         try {
            return OTSHelper.exportTransaction((Transaction)tx, 1).getPropagationContext();
         } catch (Inactive var3) {
            throw (Unavailable)(new Unavailable()).initCause(var3);
         } catch (Throwable var4) {
            throw new INVALID_TRANSACTION(var4.getMessage());
         }
      }
   }

   public int hash_top_level_tran() {
      return this.hash_transaction();
   }

   public int hash_transaction() {
      return this.xid.hashCode();
   }

   public boolean is_same_transaction(Coordinator coord) {
      return coord instanceof CoordinatorImpl && this.xid.equals(((CoordinatorImpl)coord).xid);
   }

   public boolean is_ancestor_transaction(Coordinator coord) {
      return this.is_same_transaction(coord);
   }

   public boolean is_descendant_transaction(Coordinator coord) {
      return this.is_same_transaction(coord);
   }

   public boolean is_related_transaction(Coordinator coord) {
      return this.is_same_transaction(coord);
   }

   public boolean is_top_level_transaction() {
      return true;
   }

   public RecoveryCoordinator register_resource(Resource res) throws Inactive {
      if (OTSHelper.isDebugEnabled()) {
         IIOPLogger.logDebugOTS("register_resource(" + res + ")");
      }

      Transaction tx = this.getTx();
      if (tx == null) {
         throw new Inactive();
      } else {
         if (!(res instanceof ResourceImpl)) {
            try {
               XAResource ftm = ForeignTransactionManager.registerResource(res, this.xid);
               tx.enlistResource(ftm);
               if (OTSHelper.isDebugEnabled()) {
                  IIOPLogger.logDebugOTS("register_resource(" + ftm.toString() + "): enlisted " + ftm);
               }
            } catch (RollbackException var4) {
               IIOPLogger.logOTSError("register_resource() failed", var4);
               throw new TRANSACTION_ROLLEDBACK("register_resource() failed" + var4.getMessage());
            } catch (SystemException var5) {
               IIOPLogger.logOTSError("register_resource() failed", var5);
               throw new INVALID_TRANSACTION("register_resource() failed: " + var5.getMessage());
            }
         }

         RecoveryCoordinatorImpl recovery = (RecoveryCoordinatorImpl)RecoveryCoordinatorImpl.getRecoveryCoordinator(this.xid, res);
         RecoveryCoordinatorReleaser.getReleaser().register(this.getTx(), recovery);
         return recovery;
      }
   }

   public Control create_subtransaction() throws SubtransactionsUnavailable, Inactive {
      throw new SubtransactionsUnavailable();
   }

   public void register_subtran_aware(SubtransactionAwareResource sar) throws Inactive, NotSubtransaction {
      throw new NotSubtransaction();
   }

   public void register_synchronization(Synchronization sync) throws Inactive, SynchronizationUnavailable {
      Transaction tx = this.getTx();
      if (tx == null) {
         throw new Inactive();
      } else {
         try {
            tx.registerSynchronization(new SynchronizationDispatcher(sync));
         } catch (RollbackException var4) {
            IIOPLogger.logOTSError("register_synchronization() failed", var4);
            throw new TRANSACTION_ROLLEDBACK(var4.getMessage());
         } catch (SystemException var5) {
            IIOPLogger.logOTSError("register_synchronization() failed", var5);
            throw new SynchronizationUnavailable();
         }
      }
   }

   public void rollback_only() throws Inactive {
      Transaction tx = this.getTx();
      if (tx == null) {
         throw new Inactive();
      } else {
         try {
            tx.setRollbackOnly();
         } catch (IllegalStateException var3) {
            IIOPLogger.logOTSError("rollback_only() failed", var3);
            throw new Inactive();
         } catch (SystemException var4) {
            IIOPLogger.logOTSError("rollback_only() failed", var4);
            throw new INVALID_TRANSACTION(var4.getMessage());
         }
      }
   }

   private Transaction getTx() {
      return (Transaction)TxHelper.getTransactionManager().getTransaction(this.xid);
   }

   public Object getActivationID() {
      return this.xid;
   }

   public Activator getActivator() {
      return CoordinatorImplFactory.getActivator();
   }

   protected static void p(String s) {
      System.err.println("<CoordinatorImpl> " + s);
   }
}
