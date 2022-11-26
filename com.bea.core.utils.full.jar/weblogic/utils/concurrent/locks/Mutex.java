package weblogic.utils.concurrent.locks;

public final class Mutex implements Lock {
   private boolean locked = false;

   public synchronized void lock() {
      while(this.locked) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
            this.notify();
         }
      }

      this.locked = true;
   }

   public synchronized boolean tryLock() {
      if (this.locked) {
         return false;
      } else {
         this.locked = true;
         return true;
      }
   }

   public synchronized void unlock() {
      this.locked = false;
      this.notify();
   }
}
