package weblogic.cache.locks;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class RWLockImpl implements Serializable, RWLock {
   private static final boolean DEBUG = false;
   private static final short MAX_READ_LOCKS = Short.MAX_VALUE;
   private static final long DEFAULT_MAX_LOCK_HOLD_TIME = 16000L;
   private volatile LockWaiter writeOwner;
   private volatile LinkedList readOwners;
   private transient LinkedList waitQueue;
   private transient long lastTimeoutCheck;
   private transient long maxLockHoldTime;

   public RWLockImpl() {
      this(16000L);
   }

   public RWLockImpl(long maxHoldTime) {
      this.readOwners = new LinkedList();
      this.waitQueue = new LinkedList();
      this.maxLockHoldTime = maxHoldTime;
   }

   public static void debug(String msg) {
      System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread() + "] " + msg);
   }

   public LockWaiter getNextValidWaiter() {
      Iterator it = this.waitQueue.listIterator();

      LockWaiter waiter;
      do {
         if (!it.hasNext()) {
            return null;
         }

         waiter = (LockWaiter)it.next();
         it.remove();
      } while(waiter.isTimedOut);

      return waiter;
   }

   public Object getExclusiveLockOwner() {
      return this.writeOwner == null ? null : this.writeOwner.getClient();
   }

   public boolean isSharedLockOwner(Object owner) {
      if (owner == null) {
         return false;
      } else {
         Iterator var2 = this.readOwners.iterator();

         LockWaiter waiter;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            waiter = (LockWaiter)var2.next();
         } while(!owner.equals(waiter.getClient()));

         return true;
      }
   }

   public short getSharedLockOwnerCount() {
      return (short)this.readOwners.size();
   }

   public boolean tryLock(LockMode mode) {
      return this.tryLock(mode, 0L);
   }

   public boolean tryLock(LockMode mode, long timeout) {
      return this.tryLock(Thread.currentThread(), mode, timeout);
   }

   public boolean tryLock(Object owner, LockMode mode, long timeout) {
      assert owner != null;

      this.checkLockTimeouts(System.currentTimeMillis());
      LockWaiter waiter = new LockWaiter(timeout, owner, mode);
      return mode == LockMode.LOCK_SHARED ? this.trySharedLock(waiter) : this.tryExclusiveLock(waiter);
   }

   private boolean grantReadLock(LockWaiter waiter) {
      if (this.readOwners.size() == 32767) {
         throw new IllegalMonitorStateException("No more read locks available");
      } else {
         this.readOwners.addLast(waiter);
         waiter.takeLock();
         return true;
      }
   }

   private boolean grantWriteLock(LockWaiter waiter) {
      this.writeOwner = waiter;
      waiter.takeLock();
      return true;
   }

   private boolean trySharedLock(LockWaiter waiter) {
      synchronized(this) {
         if (this.readOwners.contains(waiter)) {
            return true;
         }

         if (this.writeOwner == null || this.writeOwner != null && this.writeOwner.lockClient.equals(waiter.lockClient)) {
            return this.grantReadLock(waiter);
         }

         if (waiter.waitMS <= 0L) {
            return false;
         }

         this.waitQueue.addLast(waiter);
      }

      synchronized(waiter) {
         try {
            waiter.wait(waiter.waitMS);
         } catch (InterruptedException var7) {
            waiter.isTimedOut = true;
            return false;
         }

         if (waiter.lockGrantTimeSecs > 0L) {
            return true;
         }
      }

      this.checkLockTimeouts(System.currentTimeMillis());
      synchronized(waiter) {
         boolean lockGranted = waiter.lockGrantTimeSecs > 0L;
         waiter.isTimedOut = !lockGranted;
         return lockGranted;
      }
   }

   private boolean tryExclusiveLock(LockWaiter waiter) {
      synchronized(this) {
         if (this.writeOwner != null && this.writeOwner.equals(waiter)) {
            return true;
         }

         if (this.readOwners.size() == 0 && this.writeOwner == null) {
            this.grantWriteLock(waiter);
            return true;
         }

         if (waiter.waitMS <= 0L) {
            return false;
         }

         this.waitQueue.addLast(waiter);
      }

      synchronized(waiter) {
         try {
            waiter.wait(waiter.waitMS);
         } catch (InterruptedException var7) {
            waiter.isTimedOut = true;
            return false;
         }

         if (waiter.lockGrantTimeSecs > 0L) {
            return true;
         }
      }

      this.checkLockTimeouts(System.currentTimeMillis());
      synchronized(waiter) {
         boolean lockGranted = waiter.lockGrantTimeSecs > 0L;
         waiter.isTimedOut = !lockGranted;
         return lockGranted;
      }
   }

   private synchronized void checkLockTimeouts(long now) {
      if (this.lastTimeoutCheck + this.maxLockHoldTime / 2L <= now) {
         this.lastTimeoutCheck = now;
         Iterator itr = this.readOwners.iterator();

         while(itr.hasNext()) {
            LockWaiter readOwner = (LockWaiter)itr.next();
            if (readOwner.lockGrantTimeSecs + this.maxLockHoldTime > now) {
               return;
            }

            this.assertSharedOwnerValid(readOwner.lockClient);
            itr.remove();
            this.grantWriteLock();
         }

         if (this.writeOwner != null && this.writeOwner.lockGrantTimeSecs + this.maxLockHoldTime < now) {
            this.unlockExclusive(this.writeOwner.lockClient);
         }

      }
   }

   public void unlock(LockMode mode) {
      this.unlock(Thread.currentThread(), mode);
   }

   public void unlock(Object owner, LockMode mode) {
      assert owner != null;

      if (mode == LockMode.LOCK_SHARED) {
         this.unlockShared(owner);
      } else {
         this.unlockExclusive(owner);
      }

   }

   private synchronized void unlockShared(Object owner) {
      this.assertSharedOwnerValid(owner);
      LockWaiter client = new LockWaiter(owner, LockMode.LOCK_SHARED);
      if (!this.readOwners.remove(client)) {
         throw new IllegalMonitorStateException("Read lock is not held by the owner");
      } else {
         this.grantWriteLock();
      }
   }

   private void assertSharedOwnerValid(Object owner) {
      assert owner != null;

      if (this.writeOwner != null && !this.writeOwner.lockClient.equals(owner)) {
         throw new IllegalMonitorStateException("Exclusive lock held by another owner");
      }
   }

   private void grantWriteLock() {
      if (this.writeOwner == null && this.readOwners.size() == 0) {
         LockWaiter waiter = this.getNextValidWaiter();
         if (waiter == null) {
            return;
         }

         assert waiter.lockMode == LockMode.LOCK_EXCLUSIVE;

         this.grantWriteLock(waiter);
      }

   }

   private synchronized void unlockExclusive(Object owner) {
      assert owner != null;

      if (this.writeOwner == null) {
         throw new IllegalMonitorStateException("Exclusive lock is not locked");
      } else if (!this.writeOwner.lockClient.equals(owner)) {
         throw new IllegalMonitorStateException("Exclusive lock held by another owner");
      } else {
         this.writeOwner = null;
         if (this.readOwners.size() > 0) {
            this.grantSharedLockToWaiters();
         } else {
            LockWaiter waiter = this.getNextValidWaiter();
            if (waiter != null) {
               if (waiter.lockMode == LockMode.LOCK_EXCLUSIVE) {
                  this.grantWriteLock(waiter);
               } else {
                  this.grantReadLock(waiter);
                  this.grantSharedLockToWaiters();
               }
            }
         }
      }
   }

   private void grantSharedLockToWaiters() {
      Iterator it = this.waitQueue.listIterator();

      while(it.hasNext()) {
         LockWaiter waiter = (LockWaiter)it.next();
         if (waiter.isTimedOut) {
            it.remove();
         } else if (waiter.lockMode == LockMode.LOCK_SHARED) {
            it.remove();
            this.grantReadLock(waiter);
         }
      }

   }

   public synchronized void releaseAll() {
      this.writeOwner = null;
      this.readOwners.clear();
      Iterator it = this.waitQueue.listIterator();

      while(it.hasNext()) {
         LockWaiter waiter = (LockWaiter)it.next();
         synchronized(waiter) {
            waiter.notify();
         }
      }

   }

   private static final class LockWaiter implements Serializable {
      private final Object lockClient;
      private volatile long lockGrantTimeSecs;
      private final transient long waitMS;
      private final transient LockMode lockMode;
      private transient boolean isTimedOut;

      private LockWaiter(Object client, LockMode mode) {
         this(0L, client, mode);
      }

      public LockWaiter(long waitTime, Object lockClient, LockMode mode) {
         this.isTimedOut = false;
         this.waitMS = waitTime;
         this.lockClient = lockClient;
         this.lockGrantTimeSecs = 0L;
         this.isTimedOut = false;
         this.lockMode = mode;
      }

      public Object getClient() {
         return this.lockClient;
      }

      public synchronized void takeLock() {
         assert this.lockGrantTimeSecs == 0L;

         this.lockGrantTimeSecs = System.currentTimeMillis();
         this.notify();
      }

      public boolean equals(Object o) {
         if (o != null && o instanceof LockWaiter) {
            return this.lockClient.equals(((LockWaiter)o).lockClient) && this.lockMode.equals(((LockWaiter)o).lockMode);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.lockClient.hashCode();
      }

      public String toString() {
         return super.toString() + "|owner:" + this.lockClient + "|lockMode:" + this.lockMode + "|timeout:" + this.waitMS + "|timedout:" + this.isTimedOut + "|acquired:" + this.lockGrantTimeSecs;
      }

      // $FF: synthetic method
      LockWaiter(Object x0, LockMode x1, Object x2) {
         this(x0, x1);
      }
   }
}
