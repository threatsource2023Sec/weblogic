package com.bea.core.repackaged.springframework.jca.work;

import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.spi.work.Work;

public class DelegatingWork implements Work {
   private final Runnable delegate;

   public DelegatingWork(Runnable delegate) {
      Assert.notNull(delegate, (String)"Delegate must not be null");
      this.delegate = delegate;
   }

   public final Runnable getDelegate() {
      return this.delegate;
   }

   public void run() {
      this.delegate.run();
   }

   public void release() {
   }
}
