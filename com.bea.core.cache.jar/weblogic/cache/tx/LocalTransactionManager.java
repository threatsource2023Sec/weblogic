package weblogic.cache.tx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

public class LocalTransactionManager implements TransactionManager {
   private static final int DEFAULT_TIMEOUT = 600;
   private static final LocalTransactionManager _instance = new LocalTransactionManager();
   private final ThreadLocal _transaction = new ThreadLocal();
   private final ThreadLocal _timeout = new ThreadLocal();
   private final Timer _timeoutTimer = new Timer(true);

   public static LocalTransactionManager getInstance() {
      return _instance;
   }

   public void begin() throws NotSupportedException {
      if (this._transaction.get() != null) {
         throw new NotSupportedException();
      } else {
         LocalTransaction trans = new LocalTransaction();
         TimerTask task = new TimeoutTask(trans);
         trans.setTimeoutTask(task);
         this._transaction.set(trans);
         Integer timeout = (Integer)this._timeout.get();
         int delay = timeout == null ? 600 : timeout;
         this._timeoutTimer.schedule(task, (long)(delay * 1000));
      }
   }

   public void commit() throws RollbackException {
      this.assertTransaction().commit();
   }

   public void rollback() {
      this.assertTransaction().rollback();
   }

   public void setRollbackOnly() {
      this.assertTransaction().setRollbackOnly();
   }

   public int getStatus() {
      LocalTransaction t = (LocalTransaction)this._transaction.get();
      return t == null ? 6 : t.getStatus();
   }

   public Transaction getTransaction() {
      return (Transaction)this._transaction.get();
   }

   public Transaction suspend() throws SystemException {
      throw new SystemException();
   }

   public void resume(Transaction t) throws SystemException {
      throw new SystemException();
   }

   public void setTransactionTimeout(int timeout) throws SystemException {
      if (timeout < 0) {
         throw new SystemException();
      } else {
         if (timeout == 0) {
            this._timeout.remove();
         } else {
            this._timeout.set(timeout);
         }

      }
   }

   private LocalTransaction assertTransaction() {
      LocalTransaction t = (LocalTransaction)this._transaction.get();
      if (t == null) {
         throw new IllegalStateException();
      } else {
         return t;
      }
   }

   private class LocalTransaction implements Transaction {
      private int _status;
      private List _syncs;
      private TimerTask _timeoutTask;

      private LocalTransaction() {
         this._status = 0;
      }

      public void setTimeoutTask(TimerTask timeoutTask) {
         this._timeoutTask = timeoutTask;
      }

      public void commit() throws RollbackException {
         this.cancelTimeout();
         this.assertStatus();

         try {
            if (this._syncs != null) {
               if (this._status != 1) {
                  this._status = 8;
               }

               for(int i = 0; i < this._syncs.size() && this._status != 1; ++i) {
                  ((Synchronization)this._syncs.get(i)).beforeCompletion();
               }
            }

            if (this._status == 1) {
               this._status = 4;
               throw new RollbackException();
            }

            this._status = 3;
         } catch (RuntimeException var5) {
            this._status = 4;
            throw (RollbackException)(new RollbackException()).initCause(var5);
         } finally {
            this.afterCompletion();
         }

      }

      public void rollback() {
         this.cancelTimeout();
         this.assertStatus();
         this._status = 4;
         this.afterCompletion();
      }

      private void cancelTimeout() {
         if (this._timeoutTask != null) {
            this._timeoutTask.cancel();
         }

      }

      private void assertStatus() {
         if (this._status != 0 && this._status != 1) {
            throw new IllegalStateException();
         }
      }

      private void afterCompletion() {
         RuntimeException err = null;
         int status = this._status;
         if (this._syncs != null) {
            Iterator var3 = this._syncs.iterator();

            while(var3.hasNext()) {
               Synchronization sync = (Synchronization)var3.next();

               try {
                  sync.afterCompletion(status);
               } catch (RuntimeException var6) {
                  if (err == null) {
                     err = var6;
                  }
               }
            }
         }

         LocalTransactionManager.this._transaction.remove();
         if (err != null) {
            throw err;
         }
      }

      public void setRollbackOnly() {
         this._status = 1;
      }

      public int getStatus() {
         return this._status;
      }

      public void registerSynchronization(Synchronization sync) {
         this.assertStatus();
         if (this._syncs == null) {
            this._syncs = new ArrayList(3);
         }

         this._syncs.add(sync);
      }

      public boolean enlistResource(XAResource rsrc) throws SystemException {
         throw new SystemException();
      }

      public boolean delistResource(XAResource rsrc, int flags) throws SystemException {
         throw new SystemException();
      }

      // $FF: synthetic method
      LocalTransaction(Object x1) {
         this();
      }
   }

   private static class TimeoutTask extends TimerTask {
      private final LocalTransaction _trans;

      public TimeoutTask(LocalTransaction trans) {
         this._trans = trans;
      }

      public void run() {
         this._trans.setRollbackOnly();
      }
   }
}
