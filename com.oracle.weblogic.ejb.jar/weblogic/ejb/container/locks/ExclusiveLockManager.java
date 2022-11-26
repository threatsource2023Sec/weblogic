package weblogic.ejb.container.locks;

import java.util.concurrent.TimeUnit;
import javax.ejb.EJBException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.monitoring.EJBLockingRuntimeMBeanImpl;
import weblogic.ejb20.locks.LockTimedOutException;
import weblogic.logging.Loggable;
import weblogic.management.runtime.EJBLockingRuntimeMBean;

public final class ExclusiveLockManager implements LockManager {
   private static final DebugLogger debugLogger;
   private BeanInfo bi;
   private LockBucket[] buckets;
   private int bucketCount;
   private final EJBLockingRuntimeMBeanImpl mBean;

   private static int getBucketForPk(Object pk, int bucketCount) {
      return Math.abs(pk.hashCode() % bucketCount);
   }

   private int getBucketForPk(Object pk) {
      return getBucketForPk(pk, this.bucketCount);
   }

   public ExclusiveLockManager(EJBLockingRuntimeMBean mb) {
      this.mBean = (EJBLockingRuntimeMBeanImpl)mb;
   }

   public void setup(BeanInfo info) {
      this.bi = info;
      int maxBeansInCache = info.getCachingDescriptor().getMaxBeansInCache();
      this.setup(maxBeansInCache, this.bi.getEJBName());
   }

   private void setup(int maxBeansInCache, String ejbName) {
      this.bucketCount = maxBeansInCache / 10 + 1;
      if (this.bucketCount < 11) {
         this.bucketCount = 11;
      }

      this.buckets = new LockBucket[this.bucketCount];

      for(int i = 0; i < this.bucketCount; ++i) {
         this.buckets[i] = new LockBucket(ejbName, this.mBean);
      }

   }

   public Object getOwner(Object key) {
      int n = this.getBucketForPk(key);
      return this.buckets[n].getOwner(key);
   }

   public boolean lock(Object key, Object lockClient, int timeoutSeconds) throws LockTimedOutException {
      return this.fineLock(key, lockClient, TimeUnit.SECONDS.toNanos((long)timeoutSeconds));
   }

   public boolean fineLock(Object key, Object lockClient, long timeoutNanos) throws LockTimedOutException {
      int n = this.getBucketForPk(key);
      return this.buckets[n].lock(key, lockClient, timeoutNanos);
   }

   public void unlock(Object key, Object lockClient) {
      int n = this.getBucketForPk(key);
      this.buckets[n].unlock(key, lockClient);
   }

   private static void debug(String s) {
      debugLogger.debug("[ExclusiveLockManager] " + s);
   }

   static {
      debugLogger = EJBDebugService.lockingLogger;
   }

   private static final class LockWaiter {
      private final long waitNS;
      private final Object lockClient;
      private volatile boolean youThaMan;
      private boolean isTimedOut = false;
      public LockWaiter next;

      public LockWaiter(long waitTimeNanos, Object lockClient) {
         if (waitTimeNanos < 0L) {
            this.waitNS = 0L;
         } else {
            this.waitNS = waitTimeNanos;
         }

         this.lockClient = lockClient;
         this.youThaMan = false;
         this.next = null;
      }
   }

   private static final class LockEntry implements Cloneable {
      private final Object pk;
      private volatile Object owner;
      private LockWaiter waiters;
      public LockEntry next;

      public LockEntry(Object pk, Object owner, LockEntry next) {
         this.pk = pk;
         this.owner = owner;
         this.next = next;
      }

      public void addWaiter(LockWaiter lw) {
         if (this.waiters == null) {
            this.waiters = lw;
         } else {
            LockWaiter prev;
            for(prev = this.waiters; prev.next != null; prev = prev.next) {
            }

            prev.next = lw;
         }

      }

      public Object clone() throws CloneNotSupportedException {
         return super.clone();
      }

      public LockWaiter getNextValidWaiter() {
         for(; this.waiters != null; this.waiters = this.waiters.next) {
            if (!this.waiters.isTimedOut) {
               LockWaiter nextWaiter = this.waiters.next;
               LockWaiter retWaiter = this.waiters;
               this.waiters = nextWaiter;
               if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                  ExclusiveLockManager.debug("Returning Valid Waiter : " + retWaiter.lockClient);
               }

               return retWaiter;
            }

            if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
               ExclusiveLockManager.debug("Client : " + this.waiters.lockClient + " timedout ... after " + this.waiters.waitNS);
            }
         }

         return null;
      }
   }

   private static final class LockBucket {
      private LockEntry lockEntries;
      private final String ejbName;
      private final EJBLockingRuntimeMBeanImpl mBean;

      LockBucket(String ejbName, EJBLockingRuntimeMBeanImpl mb) {
         this.ejbName = ejbName;
         this.mBean = mb;
         this.lockEntries = null;
      }

      private static boolean eq(Object a, Object b) {
         return a == b || a.equals(b);
      }

      private LockEntry findEntryForPK(Object pk) {
         for(LockEntry l = this.lockEntries; l != null; l = l.next) {
            if (eq(pk, l.pk)) {
               return l;
            }
         }

         return null;
      }

      public synchronized Object getOwner(Object pk) {
         LockEntry l = this.findEntryForPK(pk);
         return l == null ? null : l.owner;
      }

      public boolean lock(Object pk, Object lockClient, long timeoutNanos) throws LockTimedOutException {
         this.mBean.incrementLockManagerAccessCount();
         LockWaiter lockWaiter;
         Loggable l;
         synchronized(this) {
            LockEntry le = this.findEntryForPK(pk);

            assert le == null || le.owner != null : "Lock Entry for pk: " + pk + " with lockClient: " + lockClient + " was unowned.";

            if (le == null) {
               le = new LockEntry(pk, lockClient, this.lockEntries);
               this.lockEntries = le;
               this.mBean.incrementLockEntriesCurrentCount();
               if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                  ExclusiveLockManager.debug("** LOCK ACQUIRE --> SUCCESSFUL -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " wait (nanoseconds): " + timeoutNanos);
               }

               return false;
            }

            if (eq(lockClient, le.owner)) {
               if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                  ExclusiveLockManager.debug("** LOCK ACQUIRE --> SUCCESSFUL (ALREADY OWNED) -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " wait (nanoseconds): " + timeoutNanos);
               }

               return true;
            }

            if (timeoutNanos == 0L) {
               if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                  ExclusiveLockManager.debug("** LOCK ACQUIRE --> FAILED (NO_WAIT) -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " wait (nanoseconds): " + timeoutNanos);
               }

               this.mBean.incrementTimeoutTotalCount();
               l = EJBLogger.loglockRequestTimeOutNSLoggable(this.ejbName, pk, lockClient, 0L);
               throw new LockTimedOutException(l.getMessage());
            }

            lockWaiter = new LockWaiter(timeoutNanos, lockClient);
            le.addWaiter(lockWaiter);
            if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
               ExclusiveLockManager.debug("** LOCK ACQUIRE --> WAITING -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " wait (nanoseconds): " + timeoutNanos);
            }
         }

         assert lockWaiter != null;

         synchronized(lockWaiter) {
            if (!lockWaiter.youThaMan) {
               this.mBean.incrementWaiterTotalCount();
               this.mBean.incrementWaiterCurrentCount();

               try {
                  lockWaiter.wait(lockWaiter.waitNS / 1000000L, (int)(lockWaiter.waitNS % 1000000L));
               } catch (InterruptedException var11) {
               }

               this.mBean.decrementWaiterCurrentCount();
            }

            if (!lockWaiter.youThaMan) {
               this.mBean.incrementTimeoutTotalCount();
               if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                  ExclusiveLockManager.debug("** LOCK TIME OUT AFTER WAITING -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " wait (nanoseconds): " + timeoutNanos);
               }

               lockWaiter.isTimedOut = true;
               l = EJBLogger.loglockRequestTimeOutNSLoggable(this.ejbName, pk, lockClient, timeoutNanos);
               throw new LockTimedOutException(l.getMessage());
            } else {
               if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                  ExclusiveLockManager.debug("** LOCK ACQUIRE (AFTER WAITING) -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " wait (nanoseconds): " + timeoutNanos);
               }

               return false;
            }
         }
      }

      public synchronized void unlock(Object pk, Object lockClient) {
         LockEntry prev = null;

         LockEntry le;
         for(le = this.lockEntries; le != null && le.pk != pk && !le.pk.equals(pk); le = le.next) {
            prev = le;
         }

         if (le == null) {
            Loggable l = EJBLogger.logunlockCouldNotFindPkLoggable(this.ejbName, pk, pk.getClass().getName());
            throw new EJBException(l.getMessageText());
         } else {
            if (le.waiters == null) {
               this.removeEntry(le, prev);
               if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                  ExclusiveLockManager.debug("** LOCK UNLOCK -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " waiters: NONE");
               }
            } else {
               LockWaiter lockWaiter = le.getNextValidWaiter();
               if (lockWaiter == null) {
                  this.removeEntry(le, prev);
                  if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                     ExclusiveLockManager.debug("** LOCK UNLOCK -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " waiters: NONE");
                  }
               } else {
                  le.owner = lockWaiter.lockClient;
                  synchronized(lockWaiter) {
                     lockWaiter.youThaMan = true;
                     lockWaiter.notify();
                  }

                  if (ExclusiveLockManager.debugLogger.isDebugEnabled()) {
                     ExclusiveLockManager.debug("** LOCK UNLOCK -- ejb-name: " + this.ejbName + " primary key: " + pk + " lockClient: " + lockClient + " waiters: YES new owner: " + lockWaiter.lockClient);
                  }
               }
            }

         }
      }

      private void removeEntry(LockEntry le, LockEntry prev) {
         this.mBean.decrementLockEntriesCurrentCount();
         if (prev == null) {
            this.lockEntries = le.next;
         } else {
            prev.next = le.next;
         }

      }
   }
}
