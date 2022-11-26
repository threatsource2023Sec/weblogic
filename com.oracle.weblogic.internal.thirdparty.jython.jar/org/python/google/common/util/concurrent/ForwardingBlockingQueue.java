package org.python.google.common.util.concurrent;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.collect.ForwardingQueue;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@CanIgnoreReturnValue
@GwtIncompatible
public abstract class ForwardingBlockingQueue extends ForwardingQueue implements BlockingQueue {
   protected ForwardingBlockingQueue() {
   }

   protected abstract BlockingQueue delegate();

   public int drainTo(Collection c, int maxElements) {
      return this.delegate().drainTo(c, maxElements);
   }

   public int drainTo(Collection c) {
      return this.delegate().drainTo(c);
   }

   public boolean offer(Object e, long timeout, TimeUnit unit) throws InterruptedException {
      return this.delegate().offer(e, timeout, unit);
   }

   public Object poll(long timeout, TimeUnit unit) throws InterruptedException {
      return this.delegate().poll(timeout, unit);
   }

   public void put(Object e) throws InterruptedException {
      this.delegate().put(e);
   }

   public int remainingCapacity() {
      return this.delegate().remainingCapacity();
   }

   public Object take() throws InterruptedException {
      return this.delegate().take();
   }
}
