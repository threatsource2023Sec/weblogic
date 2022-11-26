package weblogic.store.internal;

public interface LockManager {
   void lock(Object var1, Object var2);

   void lock(Object var1, Object var2, Listener var3);

   void unlock(Object var1, Object var2);

   public interface Listener {
      void onLock();
   }
}
