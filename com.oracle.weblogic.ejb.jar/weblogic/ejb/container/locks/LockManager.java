package weblogic.ejb.container.locks;

import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb20.locks.LockTimedOutException;

public interface LockManager {
   int NO_WAIT = 0;
   int INFINITE_WAIT = -1;

   void setup(BeanInfo var1);

   Object getOwner(Object var1);

   boolean lock(Object var1, Object var2, int var3) throws LockTimedOutException;

   boolean fineLock(Object var1, Object var2, long var3) throws LockTimedOutException;

   void unlock(Object var1, Object var2);
}
