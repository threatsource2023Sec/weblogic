package weblogic.cache.locks;

public interface RWLock {
   Object getExclusiveLockOwner();

   boolean isSharedLockOwner(Object var1);

   short getSharedLockOwnerCount();

   boolean tryLock(LockMode var1);

   boolean tryLock(LockMode var1, long var2);

   boolean tryLock(Object var1, LockMode var2, long var3);

   void unlock(LockMode var1);

   void unlock(Object var1, LockMode var2);

   void releaseAll();
}
