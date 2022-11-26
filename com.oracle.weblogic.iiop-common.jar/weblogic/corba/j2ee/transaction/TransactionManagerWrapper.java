package weblogic.corba.j2ee.transaction;

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
import weblogic.transaction.ClientTransactionManager;

public class TransactionManagerWrapper implements UserTransaction, ClientTransactionManager, Transaction {
   private static final ThreadLocal currentTx = new ThreadLocal();

   public static TransactionManagerWrapper getTransactionManager() {
      return TransactionManagerWrapper.TMMaker.SINGLETON;
   }

   protected TransactionManagerImpl getTM() {
      TransactionManagerImpl tm = (TransactionManagerImpl)currentTx.get();
      if (tm == null) {
         tm = new TransactionManagerImpl();
         currentTx.set(tm);
      }

      return tm;
   }

   protected TransactionManagerWrapper() {
   }

   public void begin() throws NotSupportedException, SystemException {
      this.getTM().begin();
   }

   public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
      this.getTM().commit();
   }

   public int getStatus() throws SystemException {
      return this.getTM().getStatus();
   }

   public void rollback() throws IllegalStateException, SecurityException, SystemException {
      this.getTM().rollback();
   }

   public void setRollbackOnly() throws IllegalStateException, SystemException {
      this.getTM().setRollbackOnly();
   }

   public void setTransactionTimeout(int timeout) throws SystemException {
      this.getTM().setTransactionTimeout(timeout);
   }

   public void resume(Transaction tx) throws InvalidTransactionException, IllegalStateException, SystemException {
      if (currentTx.get() != null) {
         throw new IllegalStateException();
      } else {
         try {
            TransactionManagerImpl ut = (TransactionManagerImpl)tx;
            ut.resume(tx);
            currentTx.set(ut);
         } catch (ClassCastException var3) {
            throw new InvalidTransactionException();
         }
      }
   }

   public Transaction suspend() throws SystemException {
      if (currentTx.get() == null) {
         throw new SystemException("No transaction in progress");
      } else {
         Transaction tx = this.getTM().suspend();
         currentTx.set((Object)null);
         return tx;
      }
   }

   public void forceResume(Transaction tx) {
      try {
         if (tx == null) {
            return;
         }

         TransactionManagerImpl ut = (TransactionManagerImpl)tx;
         ut.forceResume(tx);
         currentTx.set(ut);
      } catch (ClassCastException var3) {
      }

   }

   public Transaction forceSuspend() {
      if (currentTx.get() == null) {
         return null;
      } else {
         Transaction tx = this.getTM().forceSuspend();
         currentTx.set((Object)null);
         return tx;
      }
   }

   public Transaction getTransaction() throws SystemException {
      return this.getTM().getTransaction();
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

   public String toString() {
      return super.toString() + " TransactionManagerWrapper";
   }

   private static final class TMMaker {
      private static final TransactionManagerWrapper SINGLETON = new TransactionManagerWrapper();
   }
}
