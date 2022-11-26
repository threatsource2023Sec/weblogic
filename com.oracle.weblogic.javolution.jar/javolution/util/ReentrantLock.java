package javolution.util;

public class ReentrantLock {
   private Thread _owner;
   private long _count;

   public void lock() {
      Thread var1 = Thread.currentThread();
      synchronized(this) {
         if (var1 == this._owner) {
            ++this._count;
         } else {
            try {
               while(this._owner != null) {
                  this.wait();
               }

               this._owner = var1;
               this._count = 1L;
            } catch (InterruptedException var5) {
               return;
            }
         }

      }
   }

   public boolean tryLock() {
      synchronized(this) {
         if (this._owner == null) {
            this.lock();
            return true;
         } else {
            return false;
         }
      }
   }

   public void unlock() {
      synchronized(this) {
         if (Thread.currentThread() == this._owner) {
            if (--this._count == 0L) {
               this._owner = null;
               this.notify();
            }

         } else {
            throw new IllegalMonitorStateException("Current thread does not hold this lock");
         }
      }
   }

   public Thread getOwner() {
      synchronized(this) {
         return this._owner;
      }
   }
}
