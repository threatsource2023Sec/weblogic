package kodo.jdbc.conf.descriptor;

import kodo.conf.descriptor.LockManagerBean;

public interface PessimisticLockManagerBean extends LockManagerBean {
   boolean getVersionCheckOnReadLock();

   void setVersionCheckOnReadLock(boolean var1);

   boolean getVersionUpdateOnWriteLock();

   void setVersionUpdateOnWriteLock(boolean var1);
}
