package weblogic.utils.concurrent.locks;

import weblogic.utils.concurrent.CompletionListener;
import weblogic.utils.concurrent.Future;
import weblogic.utils.concurrent.TimeoutException;

public interface LockManager {
   Object findOwner(Object var1);

   void lock(Object var1, Object var2);

   boolean tryLock(Object var1, Object var2, int var3) throws TimeoutException;

   Future tryLock(Object var1, Object var2, int var3, CompletionListener var4);

   void unlock(Object var1, Object var2);
}
