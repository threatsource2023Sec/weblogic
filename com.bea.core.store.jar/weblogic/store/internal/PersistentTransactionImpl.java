package weblogic.store.internal;

import java.util.HashMap;
import java.util.Iterator;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.common.StoreDebug;

public abstract class PersistentTransactionImpl implements PersistentStoreTransaction, CompletionListener {
   private boolean completed;
   private HashMap locksHeld;

   public final void commit(CompletionRequest completionRequest) {
      this.commit(completionRequest, false);
   }

   private final void commit(CompletionRequest completionRequest, boolean isSync) {
      if (StoreDebug.storeIOLogical.isDebugEnabled()) {
         StoreDebug.storeIOLogical.debug("trying commit, ptx=" + this);
      }

      boolean needUnlockListener;
      synchronized(this) {
         this.complete();
         needUnlockListener = this.locksHeld != null;
      }

      if (!isSync && needUnlockListener) {
         completionRequest.addFirstListener(this);
      }

      this.commitInternal(completionRequest);
   }

   public final void commit() throws PersistentStoreException {
      boolean var11 = false;

      try {
         var11 = true;
         CompletionRequest cr = new CompletionRequest();
         this.commit(cr, true);
         this.waitForResult(cr);
         var11 = false;
      } finally {
         if (var11) {
            boolean isLocked;
            synchronized(this) {
               isLocked = this.locksHeld != null;
            }

            if (isLocked) {
               this.unlockAll();
            }

         }
      }

      boolean isLocked;
      synchronized(this) {
         isLocked = this.locksHeld != null;
      }

      if (isLocked) {
         this.unlockAll();
      }

   }

   private final void waitForResult(CompletionRequest cr) throws PersistentStoreException {
      try {
         cr.getResult();
      } catch (PersistentStoreException var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new AssertionError(var5);
      }
   }

   public final void rollback() throws PersistentStoreException {
      CompletionRequest cr = new CompletionRequest();
      this.rollback(cr);
      this.waitForResult(cr);
   }

   public final void rollback(CompletionRequest completionRequest) {
      if (StoreDebug.storeIOLogical.isDebugEnabled()) {
         StoreDebug.storeIOLogical.debug("trying rollback, ptx=" + this);
      }

      boolean needUnlockListener;
      synchronized(this) {
         this.complete();
         needUnlockListener = this.locksHeld != null;
      }

      if (needUnlockListener) {
         completionRequest.addFirstListener(this);
      }

      this.rollbackInternal(completionRequest);
   }

   public boolean hasPendingWork() {
      synchronized(this) {
         return !this.completed && this.hasPendingWorkInternal();
      }
   }

   private void complete() {
      if (this.completed) {
         throw new IllegalStateException("No transaction in process");
      } else {
         this.completed = true;
      }
   }

   public void lock(LockManager lockManager, Object key) {
      if (!this.hasLock(lockManager, key)) {
         lockManager.lock(this, key);
         this.newLock(lockManager, key);
      }
   }

   void lock(final LockManager lockManager, final Object key, final LockManager.Listener listener) {
      if (this.hasLock(lockManager, key)) {
         listener.onLock();
      } else {
         lockManager.lock(this, key, new LockManager.Listener() {
            public void onLock() {
               PersistentTransactionImpl.this.newLock(lockManager, key);
               listener.onLock();
            }
         });
      }
   }

   private synchronized boolean newLock(LockManager lockManager, Object key) {
      if (this.locksHeld == null) {
         this.locksHeld = new HashMap();
      }

      Object old = this.locksHeld.put(key, lockManager);
      if (old == null) {
         return true;
      } else if (old == lockManager) {
         return false;
      } else {
         this.locksHeld.put(key, old);
         throw new IllegalStateException("same key with multiple lockManagers");
      }
   }

   synchronized boolean hasLock(LockManager lockManager, Object key) {
      if (this.locksHeld == null) {
         return false;
      } else {
         Object old = this.locksHeld.get(key);
         if (old == null) {
            return false;
         } else if (old == lockManager) {
            return true;
         } else {
            throw new IllegalStateException("multiple lockManagers with same key");
         }
      }
   }

   void unlockAll() {
      while(true) {
         Object key;
         LockManager lockManager;
         synchronized(this) {
            if (this.locksHeld == null) {
               return;
            }

            Iterator iterator = this.locksHeld.keySet().iterator();
            if (!iterator.hasNext()) {
               return;
            }

            key = iterator.next();
            lockManager = (LockManager)this.locksHeld.get(key);
            iterator.remove();
         }

         lockManager.unlock(this, key);
      }
   }

   abstract void commitInternal(CompletionRequest var1);

   abstract void rollbackInternal(CompletionRequest var1);

   abstract boolean hasPendingWorkInternal();

   public String toString() {
      HashMap lockSnapshot = this.locksHeld;
      String str = super.toString() + "[ thread=" + Thread.currentThread();
      if (lockSnapshot == null) {
         return str + " ]";
      } else {
         synchronized(this) {
            return str + " locks=" + this.locksHeld.keySet() + " ]";
         }
      }
   }

   public void onCompletion(CompletionRequest completionRequest, Object result) {
      this.unlockAll();
   }

   public void onException(CompletionRequest completionRequest, Throwable reason) {
      this.unlockAll();
   }
}
