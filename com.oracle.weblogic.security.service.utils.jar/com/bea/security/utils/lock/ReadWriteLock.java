package com.bea.security.utils.lock;

public class ReadWriteLock {
   private int readingThreadCount = 0;
   private int writeThreadCount = 0;
   private Thread currentWriteThread = null;

   public synchronized void readLock() {
      while(true) {
         try {
            if (this.writeThreadCount > 0 && !Thread.currentThread().equals(this.currentWriteThread)) {
               this.wait();
               continue;
            }
         } catch (InterruptedException var2) {
         }

         ++this.readingThreadCount;
         return;
      }
   }

   public synchronized void readUnLock() {
      --this.readingThreadCount;
      if (this.readingThreadCount == 0) {
         this.notifyAll();
      }

   }

   public synchronized void writeLock() {
      try {
         label22:
         while(true) {
            if (this.readingThreadCount <= 0) {
               while(true) {
                  if (this.writeThreadCount <= 0 || Thread.currentThread().equals(this.currentWriteThread)) {
                     break label22;
                  }

                  this.wait();
               }
            }

            this.wait();
         }
      } catch (InterruptedException var2) {
      }

      this.currentWriteThread = Thread.currentThread();
      ++this.writeThreadCount;
   }

   public synchronized void writeUnLock() {
      --this.writeThreadCount;
      if (this.writeThreadCount == 0) {
         this.currentWriteThread = null;
         this.notifyAll();
      }

   }
}
