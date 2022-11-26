package org.apache.openjpa.kernel;

import javax.transaction.NotSupportedException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;
import org.apache.openjpa.ee.AbstractManagedRuntime;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UserException;

class LocalManagedRuntime extends AbstractManagedRuntime implements ManagedRuntime, TransactionManager, Transaction {
   private static final Localizer _loc = Localizer.forPackage(LocalManagedRuntime.class);
   private Synchronization _broker = null;
   private Synchronization _factorySync = null;
   private boolean _active = false;
   private Throwable _rollbackOnly = null;

   public LocalManagedRuntime(Broker broker) {
      this._broker = broker;
   }

   public TransactionManager getTransactionManager() {
      return this;
   }

   public synchronized void begin() {
      if (this._active) {
         throw new InvalidStateException(_loc.get("active"));
      } else {
         this._active = true;
      }
   }

   public synchronized void commit() {
      if (!this._active) {
         throw new InvalidStateException(_loc.get("not-active"));
      } else {
         RuntimeException err = null;
         if (this._rollbackOnly == null) {
            try {
               this._broker.beforeCompletion();
               if (this._factorySync != null) {
                  this._factorySync.beforeCompletion();
               }
            } catch (RuntimeException var3) {
               this._rollbackOnly = var3;
               err = var3;
            }
         } else {
            err = (new StoreException(_loc.get("marked-rollback"))).setCause(this._rollbackOnly).setFatal(true);
         }

         if (this._rollbackOnly == null) {
            try {
               this._broker.afterCompletion(3);
               this.notifyAfterCompletion(3);
            } catch (RuntimeException var5) {
               if (err == null) {
                  err = var5;
               }
            }
         }

         if (this._active) {
            try {
               this.rollback();
            } catch (RuntimeException var4) {
               if (err == null) {
                  err = var4;
               }
            }
         }

         if (err != null) {
            throw err;
         }
      }
   }

   public synchronized void rollback() {
      if (!this._active) {
         throw new InvalidStateException(_loc.get("not-active"));
      } else {
         RuntimeException err = null;

         try {
            this._broker.afterCompletion(4);
         } catch (RuntimeException var3) {
            err = var3;
         }

         try {
            this.notifyAfterCompletion(4);
         } catch (RuntimeException var4) {
            if (err == null) {
               err = var4;
            }
         }

         if (err != null) {
            throw err;
         }
      }
   }

   private void notifyAfterCompletion(int status) {
      this._active = false;

      try {
         if (this._factorySync != null) {
            this._factorySync.afterCompletion(status);
         }
      } finally {
         this._rollbackOnly = null;
         this._factorySync = null;
      }

   }

   public synchronized void setRollbackOnly() {
      this.setRollbackOnly(new UserException());
   }

   public void setRollbackOnly(Throwable cause) {
      this._rollbackOnly = cause;
   }

   public Throwable getRollbackCause() {
      return this._rollbackOnly;
   }

   public synchronized int getStatus() {
      if (this._rollbackOnly != null) {
         return 1;
      } else {
         return this._active ? 0 : 6;
      }
   }

   public Transaction getTransaction() {
      return this;
   }

   public void resume(Transaction tobj) throws SystemException {
      throw new SystemException(NotSupportedException.class.getName());
   }

   public void setTransactionTimeout(int sec) throws SystemException {
      throw new SystemException(NotSupportedException.class.getName());
   }

   public Transaction suspend() throws SystemException {
      throw new SystemException(NotSupportedException.class.getName());
   }

   public boolean delistResource(XAResource xaRes, int flag) throws SystemException {
      throw new SystemException(NotSupportedException.class.getName());
   }

   public boolean enlistResource(XAResource xaRes) throws SystemException {
      throw new SystemException(NotSupportedException.class.getName());
   }

   public synchronized void registerSynchronization(Synchronization sync) {
      if (sync != this._broker) {
         if (this._factorySync != null) {
            throw new InternalException();
         } else {
            this._factorySync = sync;
         }
      }
   }
}
