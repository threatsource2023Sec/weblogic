package weblogic.corba.j2ee.transaction;

import java.io.ObjectStreamException;
import java.io.Serializable;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAResource;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosTransactions.Control;
import org.omg.CosTransactions.Coordinator;
import org.omg.CosTransactions.HeuristicHazard;
import org.omg.CosTransactions.HeuristicMixed;
import org.omg.CosTransactions.Inactive;
import org.omg.CosTransactions.PropagationContext;
import org.omg.CosTransactions.Terminator;
import org.omg.CosTransactions.TransactionFactory;
import org.omg.CosTransactions.TransactionFactoryHelper;
import org.omg.CosTransactions.Unavailable;
import weblogic.corba.j2ee.naming.ORBHelper;
import weblogic.corba.j2ee.naming.ORBInfo;
import weblogic.transaction.ClientTransactionManager;

public class TransactionManagerImpl implements UserTransaction, ClientTransactionManager, Transaction, Serializable {
   public static final String COS_TRANSACTION_FACTORY_SERVICE = "TransactionFactory";
   private TransactionFactory txFactory = null;
   private int timeout = 0;
   private Control control = null;
   private PropagationContext ctx = null;

   TransactionManagerImpl() {
   }

   public TransactionFactory getTxFactory() throws SystemException {
      try {
         ORBInfo orbinfo = ORBHelper.getORBHelper().getCurrent();
         if (orbinfo == null) {
            throw new SystemException("No default ORB selected for the current thread");
         }

         this.txFactory = TransactionFactoryHelper.narrow(orbinfo.getORB().resolve_initial_references("TransactionFactory"));
      } catch (InvalidName var2) {
         throw new SystemException("Could not access TransactionFactory");
      }

      return this.txFactory;
   }

   public void begin() throws NotSupportedException, SystemException {
      if (this.control != null) {
         throw new NotSupportedException("A transaction is already in progress");
      } else {
         try {
            this.control = this.getTxFactory().create(this.timeout);
            this.ctx = this.control.get_coordinator().get_txcontext();
            ORBHelper.getORBHelper().getCurrent().setTransaction(this);
         } catch (Unavailable var3) {
            throw new NotSupportedException(var3.getMessage());
         } catch (NO_IMPLEMENT var4) {
            throw new NotSupportedException(var4.getMessage());
         } catch (INVALID_TRANSACTION var5) {
            throw new SystemException(var5.getMessage());
         } catch (OBJECT_NOT_EXIST var6) {
            ORBInfo oi = ORBHelper.getORBHelper().getCurrent();
            ORBHelper.getORBHelper().clearORBFromCache(oi.getKey());
            throw new SystemException(var6.getMessage() + " The the remote server might have been shutdown or disconnected. Resetting the cached ORB. Retry the transaction after the remote server becomes available.");
         }
      }
   }

   public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
      try {
         if (this.control == null) {
            throw new IllegalStateException("No transaction in progress");
         }

         Terminator term = this.control.get_terminator();
         ORBHelper.getORBHelper().getCurrent().setTransaction((UserTransaction)null);
         term.commit(true);
      } catch (Unavailable var9) {
         throw new IllegalStateException("Transaction unavailable");
      } catch (HeuristicMixed var10) {
         throw new HeuristicMixedException();
      } catch (HeuristicHazard var11) {
         throw new HeuristicRollbackException();
      } catch (INVALID_TRANSACTION var12) {
         throw new IllegalStateException("No transaction in progress");
      } catch (TRANSACTION_ROLLEDBACK var13) {
         throw new RollbackException(var13.getMessage());
      } finally {
         this.control = null;
         this.ctx = null;
      }

   }

   public int getStatus() throws SystemException {
      if (this.control == null) {
         return 6;
      } else {
         try {
            Coordinator coord = this.control.get_coordinator();
            return Utils.ots2jtaStatus(coord.get_status());
         } catch (Unavailable var2) {
            throw new SystemException("Transaction unavailable");
         } catch (INVALID_TRANSACTION var3) {
            this.control = null;
            this.ctx = null;
            throw new SystemException("No transaction in progress");
         }
      }
   }

   public void rollback() throws IllegalStateException, SecurityException, SystemException {
      if (this.control == null) {
         throw new IllegalStateException("No transaction in progress");
      } else {
         try {
            Terminator term = this.control.get_terminator();
            ORBHelper.getORBHelper().getCurrent().setTransaction((UserTransaction)null);
            term.rollback();
         } catch (Unavailable var6) {
            throw new IllegalStateException("Transaction unavailable");
         } catch (INVALID_TRANSACTION var7) {
            throw new IllegalStateException("No transaction in progress");
         } finally {
            this.control = null;
            this.ctx = null;
         }

      }
   }

   public void setRollbackOnly() throws IllegalStateException, SystemException {
      if (this.control == null) {
         throw new SystemException("No transaction in progress");
      } else {
         try {
            Coordinator coord = this.control.get_coordinator();
            coord.rollback_only();
         } catch (INVALID_TRANSACTION var2) {
            this.control = null;
            this.ctx = null;
            throw new SystemException("No transaction in progress");
         } catch (Unavailable var3) {
            throw new SystemException("Transaction unavailable");
         } catch (Inactive var4) {
            throw new IllegalStateException("Inactive transaction");
         }
      }
   }

   public void setTransactionTimeout(int timeout) throws SystemException {
      this.timeout = timeout;
   }

   public void resume(Transaction tx) throws InvalidTransactionException, IllegalStateException, SystemException {
      try {
         ORBHelper.getORBHelper().getCurrent().setTransaction((TransactionManagerImpl)tx);
      } catch (ClassCastException var3) {
         throw new SystemException(var3.getMessage());
      }
   }

   public Transaction suspend() throws SystemException {
      if (this.control == null) {
         throw new SystemException("No transaction in progress");
      } else {
         ORBHelper.getORBHelper().getCurrent().setTransaction((UserTransaction)null);
         return this;
      }
   }

   public void forceResume(Transaction tx) {
      if (tx != null) {
         try {
            TransactionManagerImpl ut = (TransactionManagerImpl)tx;
            ORBHelper.getORBHelper().getCurrent().setTransaction(ut);
         } catch (SystemException var3) {
         }

      }
   }

   public Transaction forceSuspend() {
      if (this.control == null) {
         return null;
      } else {
         try {
            ORBHelper.getORBHelper().getCurrent().setTransaction((UserTransaction)null);
         } catch (SystemException var2) {
         }

         return this;
      }
   }

   public Transaction getTransaction() throws SystemException {
      return this.control == null ? null : this;
   }

   public void registerSynchronization(Synchronization sync) throws RollbackException, IllegalStateException, SystemException {
      throw new SystemException("Not implemented");
   }

   public boolean delistResource(XAResource xaRes, int flag) throws IllegalStateException, SystemException {
      throw new SystemException("Not implemented");
   }

   public boolean enlistResource(XAResource xaRes) throws RollbackException, IllegalStateException, SystemException {
      throw new SystemException("Not implemented");
   }

   public PropagationContext get_txcontext() {
      return this.ctx;
   }

   public Object writeReplace() throws ObjectStreamException {
      return this.txFactory;
   }

   public String toString() {
      return super.toString() + " TransactionManagerImpl()";
   }
}
