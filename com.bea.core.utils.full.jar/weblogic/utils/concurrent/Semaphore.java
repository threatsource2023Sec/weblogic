package weblogic.utils.concurrent;

public class Semaphore {
   private int permits;
   private int locks = 0;
   private Object LOCK = new Object() {
   };

   public Semaphore(int permits) {
      this.permits = permits;
   }

   public void acquire() {
      if (this.permits >= 0) {
         synchronized(this.LOCK) {
            while(this.locks >= this.permits && this.permits >= 0) {
               try {
                  this.LOCK.wait();
               } catch (InterruptedException var4) {
               }
            }

            if (this.permits >= 0) {
               ++this.locks;
            }

         }
      }
   }

   public boolean tryAcquire() {
      if (this.permits < 0) {
         return true;
      } else {
         synchronized(this.LOCK) {
            if (this.locks >= this.permits && this.permits >= 0) {
               return false;
            } else {
               if (this.permits >= 0) {
                  ++this.locks;
               }

               return true;
            }
         }
      }
   }

   public void release() {
      if (this.permits >= 0) {
         synchronized(this.LOCK) {
            --this.locks;
            if (this.locks < 0) {
               this.locks = 0;
            }

            this.LOCK.notify();
         }
      }
   }

   public void changePermits(int permits) {
      synchronized(this.LOCK) {
         if (this.permits < 0 && permits >= 0 || this.permits >= 0 && permits < 0) {
            this.locks = 0;
         }

         this.permits = permits;
         this.LOCK.notifyAll();
      }
   }
}
