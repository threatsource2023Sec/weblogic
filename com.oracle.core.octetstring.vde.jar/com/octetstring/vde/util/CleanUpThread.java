package com.octetstring.vde.util;

class CleanUpThread extends Thread {
   private ObjectPool pool;
   private long sleepTime;

   CleanUpThread(ObjectPool pool, long sleepTime) {
      this.setPriority(2);
      this.pool = pool;
      this.sleepTime = sleepTime;
   }

   public void run() {
      while(true) {
         try {
            sleep(this.sleepTime);
         } catch (InterruptedException var2) {
         }

         this.pool.cleanUp();
      }
   }
}
