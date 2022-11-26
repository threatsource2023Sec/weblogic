package kodo.conf.descriptor;

public interface VersionLockManagerBean extends LockManagerBean {
   boolean getVersionCheckOnReadLock();

   void setVersionCheckOnReadLock(boolean var1);

   boolean getVersionUpdateOnWriteLock();

   void setVersionUpdateOnWriteLock(boolean var1);
}
