package com.ziclix.python.sql.pipe;

import com.ziclix.python.sql.util.Queue;
import com.ziclix.python.sql.util.QueueClosedException;

abstract class PipeRunner extends Thread {
   protected int counter = 0;
   protected Queue queue;
   protected Throwable exception;

   public PipeRunner(Queue queue) {
      this.queue = queue;
      this.exception = null;
   }

   public int getCount() {
      return this.counter;
   }

   public void run() {
      try {
         this.pipe();
      } catch (QueueClosedException var2) {
         return;
      } catch (Throwable var3) {
         this.exception = var3.fillInStackTrace();
         this.queue.close();
      }

   }

   protected abstract void pipe() throws InterruptedException;

   public boolean threwException() {
      return this.exception != null;
   }

   public Throwable getException() {
      return this.exception;
   }
}
