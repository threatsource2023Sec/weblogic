package weblogic.messaging.dispatcher;

import weblogic.utils.concurrent.atomic.AtomicFactory;
import weblogic.utils.concurrent.atomic.AtomicInteger;

public final class InvocableMonitor {
   private static final long WAIT_SLEEP_TIME = 500L;
   private final AtomicInteger counter = AtomicFactory.createAtomicInteger();
   private final InvocableMonitor invocableMonitor;
   private volatile boolean forceCompletion;

   public InvocableMonitor(InvocableMonitor invocableMonitor) {
      this.invocableMonitor = invocableMonitor;
   }

   public void increment() {
      if (this.invocableMonitor != null) {
         this.invocableMonitor.increment();
      }

      this.counter.incrementAndGet();
   }

   public void decrement() {
      if (this.invocableMonitor != null) {
         this.invocableMonitor.decrement();
      }

      this.counter.decrementAndGet();
   }

   public void forceInvocablesCompletion() {
      this.forceCompletion = true;
   }

   public void waitForInvocablesCompletion() {
      while(!this.forceCompletion && this.counter.get() > 0) {
         try {
            Thread.sleep(500L);
         } catch (InterruptedException var2) {
         }
      }

   }
}
