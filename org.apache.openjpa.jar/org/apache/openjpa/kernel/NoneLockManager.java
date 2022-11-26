package org.apache.openjpa.kernel;

public class NoneLockManager extends AbstractLockManager {
   public void lock(OpenJPAStateManager sm, int level, int timeout, Object context) {
      sm.setLock(Boolean.TRUE);
   }

   public void release(OpenJPAStateManager sm) {
      sm.setLock((Object)null);
   }

   public int getLockLevel(OpenJPAStateManager sm) {
      return 0;
   }
}
