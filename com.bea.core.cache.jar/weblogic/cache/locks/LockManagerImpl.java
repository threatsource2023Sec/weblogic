package weblogic.cache.locks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.cache.CacheMap;

public class LockManagerImpl implements LockManager {
   private static final long MIN_WAIT_TIME = 500L;
   protected final Map reservedKeys;
   protected final CacheMap map;
   protected final RWLock guardLock;

   public LockManagerImpl(CacheMap map) {
      this.map = map;
      this.reservedKeys = new HashMap();
      this.guardLock = new RWLockImpl();
   }

   public boolean tryLock(Object key, long timeout) {
      return this.tryLock(key, Thread.currentThread(), LockMode.LOCK_EXCLUSIVE, timeout);
   }

   public boolean tryLocks(Set keys, long timeout) {
      return this.tryLocks(keys, Thread.currentThread(), LockMode.LOCK_EXCLUSIVE, timeout);
   }

   public boolean tryGuardLock(long timeout) {
      return this.tryGuardLock(Thread.currentThread(), LockMode.LOCK_EXCLUSIVE, timeout);
   }

   public void releaseLock(Object key) {
      this.releaseLock(key, Thread.currentThread(), LockMode.LOCK_EXCLUSIVE);
   }

   public void releaseLocks(Set keys) {
      this.releaseLocks(keys, Thread.currentThread(), LockMode.LOCK_EXCLUSIVE);
   }

   public void releaseGuardLock() {
      this.releaseGuardLock(Thread.currentThread(), LockMode.LOCK_EXCLUSIVE);
   }

   public void releaseLock(Object key, Object owner, LockMode mode) {
      RWLock lock;
      boolean reserved;
      synchronized(this.reservedKeys) {
         lock = (RWLock)this.reservedKeys.get(key);
         reserved = lock != null;
         if (!reserved) {
            lock = (RWLock)this.map.getEntry(key);
            if (lock == null) {
               return;
            }
         }
      }

      lock.unlock(owner, mode);
      if (reserved) {
         this.releaseReservedKey(key);
      }

   }

   public void releaseLocks(Set keys, Object owner, LockMode mode) {
      Iterator var4 = keys.iterator();

      while(var4.hasNext()) {
         Object key = var4.next();
         this.releaseLock(key, owner, mode);
      }

   }

   public void releaseGuardLock(Object owner, LockMode mode) {
      this.guardLock.unlock(owner, mode);
   }

   public boolean tryLock(Object key, Object owner, LockMode mode, long timeout) {
      boolean reservedNew = false;
      Object lock;
      synchronized(this.reservedKeys) {
         lock = (RWLock)this.reservedKeys.get(key);
         if (lock == null) {
            lock = (RWLock)this.map.getEntry(key);
            if (lock == null) {
               lock = new RWLockImpl();
               this.reservedKeys.put(key, lock);
               reservedNew = true;
            }
         }
      }

      if (((RWLock)lock).tryLock(owner, mode, timeout)) {
         return true;
      } else {
         if (reservedNew) {
            this.releaseReservedKey(key);
         }

         return false;
      }
   }

   public boolean tryLocks(Set keys, Object owner, LockMode mode, long timeout) {
      return this.tryLocks(keys, owner, mode, timeout, 0);
   }

   private boolean tryLocks(Set keys, Object owner, LockMode mode, long timeout, int attempt) {
      long timeRemaining = timeout;
      List grantedLocks = new ArrayList(keys.size());
      List reservedNew = null;
      Iterator var11 = keys.iterator();

      Object key;
      while(var11.hasNext()) {
         key = var11.next();
         Object lock;
         synchronized(this.reservedKeys) {
            lock = (RWLock)this.reservedKeys.get(key);
            if (lock == null) {
               lock = (RWLock)this.map.getEntry(key);
               if (lock == null) {
                  lock = new RWLockImpl();
                  this.reservedKeys.put(key, lock);
                  if (reservedNew == null) {
                     reservedNew = new ArrayList();
                  }

                  reservedNew.add(key);
               }
            }
         }

         long t0 = System.currentTimeMillis();
         if (!((RWLock)lock).tryLock(owner, mode, timeRemaining)) {
            break;
         }

         grantedLocks.add(lock);
         if (timeout > 0L) {
            timeRemaining -= System.currentTimeMillis() - t0;
            if (timeRemaining <= 0L) {
               break;
            }
         }
      }

      if (grantedLocks.size() == keys.size()) {
         return true;
      } else {
         var11 = grantedLocks.iterator();

         while(var11.hasNext()) {
            RWLock lock = (RWLock)var11.next();
            lock.unlock(owner, mode);
         }

         var11 = reservedNew.iterator();

         while(var11.hasNext()) {
            key = var11.next();
            this.releaseReservedKey(key);
         }

         return this.retryLocks(keys, owner, mode, timeRemaining, attempt);
      }
   }

   private boolean retryLocks(Set keys, Object owner, LockMode mode, long timeout, int attempt) {
      long sleepTime = (long)(2 ^ attempt) * 500L;
      if (timeout > sleepTime) {
         timeout -= sleepTime;

         try {
            Thread.sleep(sleepTime);
         } catch (InterruptedException var10) {
            return false;
         }

         ++attempt;
         return this.tryLocks(keys, owner, mode, timeout, attempt);
      } else {
         return false;
      }
   }

   public boolean tryGuardLock(Object owner, LockMode mode, long timeout) {
      return this.guardLock.tryLock(owner, mode, timeout);
   }

   public boolean releaseReservedKey(Object key) {
      RWLock lock = (RWLock)this.reservedKeys.get(key);
      if (lock == null) {
         return true;
      } else if (lock.tryLock(LockMode.LOCK_EXCLUSIVE)) {
         this.reservedKeys.remove(key);
         lock.unlock(LockMode.LOCK_EXCLUSIVE);
         return true;
      } else {
         return false;
      }
   }

   public boolean isLockOwner(Object key) {
      return this.isLockOwner((Object)key, Thread.currentThread(), LockMode.LOCK_EXCLUSIVE);
   }

   public boolean isLockOwner(Object key, Object owner, LockMode mode) {
      if (owner == null) {
         return false;
      } else {
         RWLock lock;
         synchronized(this.reservedKeys) {
            lock = (RWLock)this.reservedKeys.get(key);
            if (lock == null) {
               lock = (RWLock)this.map.getEntry(key);
               if (lock == null) {
                  return false;
               }
            }
         }

         return this.isLockOwner(lock, owner, mode);
      }
   }

   public boolean isGuardLockOwner() {
      return this.isGuardLockOwner(Thread.currentThread(), LockMode.LOCK_EXCLUSIVE);
   }

   public boolean isGuardLockOwner(Object owner, LockMode mode) {
      return owner != null && this.isLockOwner(this.guardLock, owner, mode);
   }

   private boolean isLockOwner(RWLock lock, Object owner, LockMode mode) {
      return mode == LockMode.LOCK_EXCLUSIVE ? owner.equals(lock.getExclusiveLockOwner()) : lock.isSharedLockOwner(owner);
   }
}
