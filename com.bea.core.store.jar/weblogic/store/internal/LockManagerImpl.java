package weblogic.store.internal;

import java.util.HashMap;
import java.util.LinkedList;

public final class LockManagerImpl implements LockManager {
   private final HashMap locks = new HashMap();

   public void lock(Object owner, Object key) {
      assert owner != null;

      Lock l = this.getLock(owner, key);
      l.acquire(owner);
   }

   public void lock(Object owner, Object key, LockManager.Listener listener) {
      assert owner != null;

      Lock l = this.getLock(owner, key);
      l.acquire(owner, listener);
   }

   public void unlock(Object owner, Object key) {
      Lock l;
      synchronized(this.locks) {
         l = (Lock)this.locks.get(key);
         if (l == null) {
            throw new IllegalStateException("not owner");
         }
      }

      l.release(owner);
   }

   private Lock getLock(Object proposedOwner, Object key) {
      synchronized(this.locks) {
         Lock l = (Lock)this.locks.get(key);
         if (l == null) {
            l = new Lock(proposedOwner, key);
            this.locks.put(key, l);
         }

         l.useCount++;
         return l;
      }
   }

   final class Lock {
      private Object owner;
      private int recursionCnt;
      private int numSyncWaiters;
      private LinkedList asyncWaiters;
      private final Object key;
      private int useCount;

      private Lock(Object proposedOwner, Object argKey) {
         this.owner = proposedOwner;
         this.key = argKey;
      }

      public synchronized void acquire(Object owner) {
         while(this.owner != null && !this.owner.equals(owner)) {
            try {
               ++this.numSyncWaiters;
               this.wait();
            } catch (InterruptedException var6) {
            } finally {
               --this.numSyncWaiters;
            }
         }

         this.owner = owner;
         ++this.recursionCnt;
      }

      public void acquire(Object owner, LockManager.Listener listener) {
         synchronized(this) {
            if (this.owner != null && !this.owner.equals(owner)) {
               if (this.asyncWaiters == null) {
                  this.asyncWaiters = new LinkedList();
               }

               this.asyncWaiters.add(new AwaitingListener(owner, listener));
               return;
            }

            this.owner = owner;
            ++this.recursionCnt;
         }

         listener.onLock();
      }

      public void release(Object owner) {
         AwaitingListener awaitingListener;
         synchronized(this) {
            if (this.owner != owner) {
               throw new IllegalStateException("not owner (" + this.owner + " != " + owner);
            }

            --this.recursionCnt;
            if (this.recursionCnt > 0) {
               return;
            }

            this.owner = null;
            if (this.numSyncWaiters > 0) {
               this.notify();
               return;
            }

            if (this.asyncWaiters == null) {
               synchronized(LockManagerImpl.this.locks) {
                  --this.useCount;
                  if (this.useCount < 1) {
                     LockManagerImpl.this.locks.remove(this.key);
                  }
               }

               return;
            }

            awaitingListener = (AwaitingListener)this.asyncWaiters.removeFirst();
            if (this.asyncWaiters.isEmpty()) {
               this.asyncWaiters = null;
            }

            this.owner = awaitingListener.owner;
            ++this.recursionCnt;
         }

         awaitingListener.listener.onLock();
      }

      // $FF: synthetic method
      Lock(Object x1, Object x2, Object x3) {
         this(x1, x2);
      }

      private final class AwaitingListener {
         final Object owner;
         final LockManager.Listener listener;

         AwaitingListener(Object owner, LockManager.Listener listener) {
            this.owner = owner;
            this.listener = listener;
         }
      }
   }
}
