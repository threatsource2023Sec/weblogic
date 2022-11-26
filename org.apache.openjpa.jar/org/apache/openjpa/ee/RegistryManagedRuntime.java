package org.apache.openjpa.ee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.xa.XAResource;

public class RegistryManagedRuntime implements ManagedRuntime {
   private String _registryName = "java:comp/TransactionSynchronizationRegistry";
   private TransactionManagerRegistryFacade _tm = null;

   public TransactionManager getTransactionManager() throws Exception {
      if (this._tm == null) {
         Context ctx = new InitialContext();

         try {
            this._tm = new TransactionManagerRegistryFacade((TransactionSynchronizationRegistry)ctx.lookup(this._registryName));
         } finally {
            ctx.close();
         }
      }

      return this._tm;
   }

   public void setRollbackOnly(Throwable cause) throws Exception {
      this.getTransactionManager().getTransaction().setRollbackOnly();
   }

   public Throwable getRollbackCause() throws Exception {
      return null;
   }

   public void setRegistryName(String registryName) {
      this._registryName = registryName;
   }

   public String getRegistryName() {
      return this._registryName;
   }

   public Object getTransactionKey() throws Exception, SystemException {
      return this._tm.getTransactionKey();
   }

   public static class TransactionManagerRegistryFacade implements TransactionManager, Transaction {
      private final TransactionSynchronizationRegistry _registry;

      public TransactionManagerRegistryFacade(TransactionSynchronizationRegistry registry) {
         this._registry = registry;
      }

      public Transaction getTransaction() throws SystemException {
         return this;
      }

      public void registerSynchronization(Synchronization sync) throws RollbackException, IllegalStateException, SystemException {
         this._registry.registerInterposedSynchronization(sync);
      }

      public void setRollbackOnly() throws IllegalStateException, SystemException {
         this._registry.setRollbackOnly();
      }

      public int getStatus() throws SystemException {
         return this._registry.getTransactionStatus();
      }

      public Object getTransactionKey() {
         return this._registry.getTransactionKey();
      }

      public void begin() throws NotSupportedException, SystemException {
         throw new NotSupportedException();
      }

      public void commit() throws RollbackException, HeuristicMixedException, SystemException, HeuristicRollbackException, SecurityException, IllegalStateException {
         throw new SystemException();
      }

      public void resume(Transaction tobj) throws InvalidTransactionException, IllegalStateException, SystemException {
         throw new SystemException();
      }

      public void rollback() throws IllegalStateException, SecurityException, SystemException {
         throw new SystemException();
      }

      public void setTransactionTimeout(int seconds) throws SystemException {
         throw new SystemException();
      }

      public Transaction suspend() throws SystemException {
         throw new SystemException();
      }

      public boolean delistResource(XAResource xaRes, int flag) throws IllegalStateException, SystemException {
         throw new SystemException();
      }

      public boolean enlistResource(XAResource xaRes) throws RollbackException, IllegalStateException, SystemException {
         throw new SystemException();
      }
   }
}
