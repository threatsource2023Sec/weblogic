package com.bea.core.repackaged.springframework.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface ListenableFuture extends Future {
   void addCallback(ListenableFutureCallback var1);

   void addCallback(SuccessCallback var1, FailureCallback var2);

   default CompletableFuture completable() {
      CompletableFuture completable = new DelegatingCompletableFuture(this);
      this.addCallback(completable::complete, completable::completeExceptionally);
      return completable;
   }
}
