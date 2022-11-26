package org.antlr.misc;

public class Barrier {
   protected int threshold;
   protected int count = 0;

   public Barrier(int t) {
      this.threshold = t;
   }

   public synchronized void waitForRelease() throws InterruptedException {
      ++this.count;
      if (this.count == this.threshold) {
         this.action();
         this.notifyAll();
      } else {
         while(this.count < this.threshold) {
            this.wait();
         }
      }

   }

   public void action() {
   }
}
