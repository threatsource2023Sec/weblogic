package org.python.modules._threading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

final class Mutex implements java.util.concurrent.locks.Lock {
   private final Sync sync = new Sync();

   public void lock() {
      this.sync.acquire(1);
   }

   public boolean tryLock() {
      return this.sync.tryAcquire(1);
   }

   public void unlock() {
      this.sync.release(1);
   }

   public java.util.concurrent.locks.Condition newCondition() {
      return this.sync.newCondition();
   }

   public boolean isLocked() {
      return this.sync.isHeldExclusively();
   }

   public boolean hasQueuedThreads() {
      return this.sync.hasQueuedThreads();
   }

   public void lockInterruptibly() throws InterruptedException {
      this.sync.acquireInterruptibly(1);
   }

   public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
      return this.sync.tryAcquireNanos(1, unit.toNanos(timeout));
   }

   public int getWaitQueueLength(java.util.concurrent.locks.Condition condition) {
      return condition instanceof AbstractQueuedSynchronizer.ConditionObject ? this.sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject)condition) : 0;
   }

   String getOwnerName() {
      Thread owner = this.sync.getOwner();
      return owner != null ? owner.getName() : null;
   }

   private static class Sync extends AbstractQueuedSynchronizer {
      private Sync() {
      }

      protected boolean isHeldExclusively() {
         return this.getState() == 1;
      }

      public boolean tryAcquire(int acquires) {
         assert acquires == 1;

         if (this.compareAndSetState(0, 1)) {
            this.setExclusiveOwnerThread(Thread.currentThread());
            return true;
         } else {
            return false;
         }
      }

      protected boolean tryRelease(int releases) {
         assert releases == 1;

         if (this.getState() == 0) {
            throw new IllegalMonitorStateException();
         } else {
            this.setExclusiveOwnerThread((Thread)null);
            this.setState(0);
            return true;
         }
      }

      AbstractQueuedSynchronizer.ConditionObject newCondition() {
         return new AbstractQueuedSynchronizer.ConditionObject(this);
      }

      Thread getOwner() {
         return this.getExclusiveOwnerThread();
      }

      // $FF: synthetic method
      Sync(Object x0) {
         this();
      }
   }
}
