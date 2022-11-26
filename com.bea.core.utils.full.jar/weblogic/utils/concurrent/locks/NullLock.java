package weblogic.utils.concurrent.locks;

public final class NullLock implements Lock {
   public void lock() {
   }

   public boolean tryLock() {
      return true;
   }

   public void unlock() {
   }
}
