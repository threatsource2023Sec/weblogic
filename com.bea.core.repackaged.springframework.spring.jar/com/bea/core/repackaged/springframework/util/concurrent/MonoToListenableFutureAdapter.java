package com.bea.core.repackaged.springframework.util.concurrent;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

public class MonoToListenableFutureAdapter implements ListenableFuture {
   private final MonoProcessor processor;
   private final ListenableFutureCallbackRegistry registry = new ListenableFutureCallbackRegistry();

   public MonoToListenableFutureAdapter(Mono mono) {
      Assert.notNull(mono, (String)"Mono must not be null");
      ListenableFutureCallbackRegistry var10002 = this.registry;
      var10002.getClass();
      Mono var10001 = mono.doOnSuccess(var10002::success);
      var10002 = this.registry;
      var10002.getClass();
      this.processor = var10001.doOnError(var10002::failure).toProcessor();
   }

   @Nullable
   public Object get() {
      return this.processor.block();
   }

   @Nullable
   public Object get(long timeout, TimeUnit unit) {
      Assert.notNull(unit, (String)"TimeUnit must not be null");
      Duration duration = Duration.ofMillis(TimeUnit.MILLISECONDS.convert(timeout, unit));
      return this.processor.block(duration);
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      if (this.isCancelled()) {
         return false;
      } else {
         this.processor.cancel();
         return this.processor.isCancelled();
      }
   }

   public boolean isCancelled() {
      return this.processor.isCancelled();
   }

   public boolean isDone() {
      return this.processor.isTerminated();
   }

   public void addCallback(ListenableFutureCallback callback) {
      this.registry.addCallback(callback);
   }

   public void addCallback(SuccessCallback success, FailureCallback failure) {
      this.registry.addSuccessCallback(success);
      this.registry.addFailureCallback(failure);
   }
}
