package com.oracle.jrf.concurrent.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class DefaultScheduledExecutorService extends DefaultExecutorService implements ScheduledExecutorService {
   private final ScheduledExecutorService delegate;

   public DefaultScheduledExecutorService(ScheduledExecutorService delegate) {
      super(delegate);
      this.delegate = delegate;
   }

   public ScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
      return this.delegate.schedule(this.wrap(command), delay, unit);
   }

   public ScheduledFuture schedule(Callable callable, long delay, TimeUnit unit) {
      return this.delegate.schedule(this.wrap(callable), delay, unit);
   }

   public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      return this.delegate.scheduleAtFixedRate(this.wrap(command), initialDelay, period, unit);
   }

   public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      return this.delegate.scheduleWithFixedDelay(this.wrap(command), initialDelay, delay, unit);
   }
}
