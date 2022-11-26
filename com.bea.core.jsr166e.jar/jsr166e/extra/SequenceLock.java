package jsr166e.extra;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SequenceLock implements Lock, Serializable {
   private static final long serialVersionUID = 7373984872572414699L;
   private final Sync sync;
   static final int DEFAULT_SPINS = Runtime.getRuntime().availableProcessors() > 1 ? 64 : 0;

   public SequenceLock() {
      this.sync = new Sync(DEFAULT_SPINS);
   }

   public SequenceLock(int spins) {
      this.sync = new Sync(spins);
   }

   public long getSequence() {
      return this.sync.getSequence();
   }

   public long awaitAvailability() {
      return this.sync.awaitAvailability();
   }

   public long tryAwaitAvailability(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
      return this.sync.tryAwaitAvailability(unit.toNanos(timeout));
   }

   public void lock() {
      this.sync.lock();
   }

   public void lockInterruptibly() throws InterruptedException {
      this.sync.acquireInterruptibly(1L);
   }

   public boolean tryLock() {
      return this.sync.tryAcquire(1L);
   }

   public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
      return this.sync.tryAcquireNanos(1L, unit.toNanos(timeout));
   }

   public void unlock() {
      this.sync.release(1L);
   }

   public Condition newCondition() {
      throw new UnsupportedOperationException();
   }

   public long getHoldCount() {
      return this.sync.getHoldCount();
   }

   public boolean isHeldByCurrentThread() {
      return this.sync.isHeldExclusively();
   }

   public boolean isLocked() {
      return this.sync.isLocked();
   }

   protected Thread getOwner() {
      return this.sync.getOwner();
   }

   public final boolean hasQueuedThreads() {
      return this.sync.hasQueuedThreads();
   }

   public final boolean hasQueuedThread(Thread thread) {
      return this.sync.isQueued(thread);
   }

   public final int getQueueLength() {
      return this.sync.getQueueLength();
   }

   protected Collection getQueuedThreads() {
      return this.sync.getQueuedThreads();
   }

   public String toString() {
      Thread o = this.sync.getOwner();
      return super.toString() + (o == null ? "[Unlocked]" : "[Locked by thread " + o.getName() + "]");
   }

   static final class Sync extends AbstractQueuedLongSynchronizer {
      private static final long serialVersionUID = 2540673546047039555L;
      final int spins;
      long holds;

      Sync(int spins) {
         this.spins = spins;
      }

      public final boolean isHeldExclusively() {
         return (this.getState() & 1L) != 0L && this.getExclusiveOwnerThread() == Thread.currentThread();
      }

      public final boolean tryAcquire(long acquires) {
         Thread current = Thread.currentThread();
         long c = this.getState();
         if ((c & 1L) == 0L) {
            if (this.compareAndSetState(c, c + 1L)) {
               this.holds = acquires;
               this.setExclusiveOwnerThread(current);
               return true;
            }
         } else if (current == this.getExclusiveOwnerThread()) {
            this.holds += acquires;
            return true;
         }

         return false;
      }

      public final boolean tryRelease(long releases) {
         if (Thread.currentThread() != this.getExclusiveOwnerThread()) {
            throw new IllegalMonitorStateException();
         } else if ((this.holds -= releases) == 0L) {
            this.setExclusiveOwnerThread((Thread)null);
            this.setState(this.getState() + 1L);
            return true;
         } else {
            return false;
         }
      }

      public final long tryAcquireShared(long unused) {
         return (this.getState() & 1L) == 0L ? 1L : (this.getExclusiveOwnerThread() == Thread.currentThread() ? 0L : -1L);
      }

      public final boolean tryReleaseShared(long unused) {
         return (this.getState() & 1L) == 0L;
      }

      public final Condition newCondition() {
         throw new UnsupportedOperationException();
      }

      final long getSequence() {
         return this.getState();
      }

      final void lock() {
         for(int k = this.spins; !this.tryAcquire(1L); --k) {
            if (k == 0) {
               this.acquire(1L);
               break;
            }
         }

      }

      final long awaitAvailability() {
         long s;
         while(((s = this.getState()) & 1L) != 0L && this.getExclusiveOwnerThread() != Thread.currentThread()) {
            this.acquireShared(1L);
            this.releaseShared(1L);
         }

         return s;
      }

      final long tryAwaitAvailability(long nanos) throws InterruptedException, TimeoutException {
         Thread current = Thread.currentThread();

         while(true) {
            long s = this.getState();
            if ((s & 1L) == 0L || this.getExclusiveOwnerThread() == current) {
               this.releaseShared(1L);
               return s;
            }

            if (!this.tryAcquireSharedNanos(1L, nanos)) {
               throw new TimeoutException();
            }

            nanos = 1L;
         }
      }

      final boolean isLocked() {
         return (this.getState() & 1L) != 0L;
      }

      final Thread getOwner() {
         return (this.getState() & 1L) == 0L ? null : this.getExclusiveOwnerThread();
      }

      final long getHoldCount() {
         return this.isHeldExclusively() ? this.holds : 0L;
      }

      private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
         s.defaultReadObject();
         this.holds = 0L;
         this.setState(0L);
      }
   }
}
