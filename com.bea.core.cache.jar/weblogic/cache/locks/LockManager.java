package weblogic.cache.locks;

import java.util.Set;

public interface LockManager {
   boolean tryLock(Object var1, long var2);

   boolean tryLocks(Set var1, long var2);

   boolean tryGuardLock(long var1);

   boolean tryLock(Object var1, Object var2, LockMode var3, long var4);

   boolean tryLocks(Set var1, Object var2, LockMode var3, long var4);

   boolean tryGuardLock(Object var1, LockMode var2, long var3);

   void releaseLock(Object var1);

   void releaseLocks(Set var1);

   void releaseGuardLock();

   void releaseLock(Object var1, Object var2, LockMode var3);

   void releaseLocks(Set var1, Object var2, LockMode var3);

   void releaseGuardLock(Object var1, LockMode var2);

   boolean isLockOwner(Object var1);

   boolean isLockOwner(Object var1, Object var2, LockMode var3);

   boolean isGuardLockOwner();

   boolean isGuardLockOwner(Object var1, LockMode var2);
}
