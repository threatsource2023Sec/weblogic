package kodo.kernel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.collections.ReferenceMap;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.VersionLockManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.LockException;

public class SingleJVMExclusiveLockManager extends VersionLockManager {
   private static final Localizer s_loc = Localizer.forPackage(SingleJVMExclusiveLockManager.class);
   private static final Map _oidLockMap = Collections.synchronizedMap(new ReferenceMap(0, 2));
   private Set _locks = null;

   public SingleJVMExclusiveLockManager() {
      this.setVersionCheckOnReadLock(false);
      this.setVersionUpdateOnWriteLock(false);
   }

   protected void lockInternal(OpenJPAStateManager sm, int level, int timeout, Object sdata) {
      if (this.getLockLevel(sm) != 0) {
         super.lockInternal(sm, level, timeout, sdata);
      } else {
         Lock lock = this.getLock(sm);
         if (this._locks != null && this._locks.contains(lock)) {
            super.lockInternal(sm, level, timeout, sdata);
         } else {
            try {
               if (timeout < 0) {
                  lock.lock();
               } else if (!lock.tryLock((long)timeout, TimeUnit.MILLISECONDS)) {
                  throw new LockException(sm.getManagedInstance(), timeout);
               }

               if (this._locks == null) {
                  this._locks = new HashSet();
               }

               this._locks.add(lock);
               if (this.log.isTraceEnabled()) {
                  this.log.trace(s_loc.get("sjvm-acquired-lock", sm.getId(), lock));
               }

               super.lockInternal(sm, level, timeout, sdata);
            } catch (InterruptedException var7) {
               throw new LockException(sm.getManagedInstance(), timeout);
            }
         }
      }
   }

   private Lock getLock(OpenJPAStateManager sm) {
      Object id = sm.getId();
      synchronized(_oidLockMap) {
         Lock lock = (Lock)_oidLockMap.get(id);
         if (lock == null) {
            _oidLockMap.put(id, lock = this.newLock());
         }

         return lock;
      }
   }

   protected Lock newLock() {
      return new ReentrantLock();
   }

   public void endTransaction() {
      if (this._locks != null) {
         Iterator i = this._locks.iterator();

         while(i.hasNext()) {
            Lock lock = (Lock)i.next();
            if (this.log.isTraceEnabled()) {
               this.log.trace(s_loc.get("sjvm-released-lock", lock));
            }

            lock.unlock();
            i.remove();
         }
      }

   }
}
