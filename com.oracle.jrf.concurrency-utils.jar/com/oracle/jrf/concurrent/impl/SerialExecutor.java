package com.oracle.jrf.concurrent.impl;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

class SerialExecutor implements Executor {
   private final Queue tasks = new ArrayDeque();
   private final Executor delegate;
   private Runnable active;

   public SerialExecutor(Executor delegate) {
      this.delegate = delegate;
   }

   public synchronized void execute(final Runnable r) {
      this.tasks.offer(new Runnable() {
         public void run() {
            try {
               r.run();
            } finally {
               SerialExecutor.this.scheduleNext();
            }

         }
      });
      if (this.active == null) {
         this.scheduleNext();
      }

   }

   protected synchronized void scheduleNext() {
      if ((this.active = (Runnable)this.tasks.poll()) != null) {
         this.delegate.execute(this.active);
      }

   }
}
