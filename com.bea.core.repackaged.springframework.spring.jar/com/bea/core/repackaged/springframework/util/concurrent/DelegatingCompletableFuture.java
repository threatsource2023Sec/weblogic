package com.bea.core.repackaged.springframework.util.concurrent;

import com.bea.core.repackaged.springframework.util.Assert;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

class DelegatingCompletableFuture extends CompletableFuture {
   private final Future delegate;

   public DelegatingCompletableFuture(Future delegate) {
      Assert.notNull(delegate, (String)"Delegate must not be null");
      this.delegate = delegate;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      boolean result = this.delegate.cancel(mayInterruptIfRunning);
      super.cancel(mayInterruptIfRunning);
      return result;
   }
}
