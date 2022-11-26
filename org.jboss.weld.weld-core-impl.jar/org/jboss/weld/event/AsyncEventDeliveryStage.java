package org.jboss.weld.event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jboss.weld.util.ForwardingCompletionStage;

public class AsyncEventDeliveryStage extends ForwardingCompletionStage {
   private final Executor defaultExecutor;
   private final CompletionStage delegate;

   static AsyncEventDeliveryStage completed(Object event, Executor executor) {
      CompletableFuture delegate = new CompletableFuture();
      delegate.complete(event);
      return new AsyncEventDeliveryStage(delegate, executor);
   }

   AsyncEventDeliveryStage(Supplier supplier, Executor executor) {
      this((CompletionStage)CompletableFuture.supplyAsync(supplier, executor), executor);
   }

   AsyncEventDeliveryStage(CompletionStage delegate, Executor executor) {
      this.delegate = delegate;
      this.defaultExecutor = executor;
   }

   protected CompletionStage delegate() {
      return this.delegate;
   }

   public CompletionStage thenApply(Function fn) {
      return this.wrap(super.thenApply(fn));
   }

   public CompletionStage thenApplyAsync(Function fn) {
      return this.wrap(super.thenApplyAsync(fn, this.defaultExecutor));
   }

   public CompletionStage thenApplyAsync(Function fn, Executor executor) {
      return this.wrap(super.thenApplyAsync(fn, executor));
   }

   public CompletionStage thenAccept(Consumer action) {
      return this.wrap(super.thenAccept(action));
   }

   public CompletionStage thenAcceptAsync(Consumer action) {
      return this.wrap(super.thenAcceptAsync(action, this.defaultExecutor));
   }

   public CompletionStage thenAcceptAsync(Consumer action, Executor executor) {
      return this.wrap(super.thenAcceptAsync(action, executor));
   }

   public CompletionStage thenRun(Runnable action) {
      return this.wrap(super.thenRun(action));
   }

   public CompletionStage thenRunAsync(Runnable action) {
      return this.wrap(super.thenRunAsync(action, this.defaultExecutor));
   }

   public CompletionStage thenRunAsync(Runnable action, Executor executor) {
      return this.wrap(super.thenRunAsync(action, executor));
   }

   public CompletionStage thenCombine(CompletionStage other, BiFunction fn) {
      return this.wrap(super.thenCombine(other, fn));
   }

   public CompletionStage thenCombineAsync(CompletionStage other, BiFunction fn) {
      return this.wrap(super.thenCombineAsync(other, fn, this.defaultExecutor));
   }

   public CompletionStage thenCombineAsync(CompletionStage other, BiFunction fn, Executor executor) {
      return this.wrap(super.thenCombineAsync(other, fn, executor));
   }

   public CompletionStage thenAcceptBoth(CompletionStage other, BiConsumer action) {
      return this.wrap(super.thenAcceptBoth(other, action));
   }

   public CompletionStage thenAcceptBothAsync(CompletionStage other, BiConsumer action) {
      return this.wrap(super.thenAcceptBothAsync(other, action, this.defaultExecutor));
   }

   public CompletionStage thenAcceptBothAsync(CompletionStage other, BiConsumer action, Executor executor) {
      return this.wrap(super.thenAcceptBothAsync(other, action, executor));
   }

   public CompletionStage runAfterBoth(CompletionStage other, Runnable action) {
      return this.wrap(super.runAfterBoth(other, action));
   }

   public CompletionStage runAfterBothAsync(CompletionStage other, Runnable action) {
      return this.wrap(super.runAfterBothAsync(other, action, this.defaultExecutor));
   }

   public CompletionStage runAfterBothAsync(CompletionStage other, Runnable action, Executor executor) {
      return this.wrap(super.runAfterBothAsync(other, action, executor));
   }

   public CompletionStage applyToEither(CompletionStage other, Function fn) {
      return this.wrap(super.applyToEither(other, fn));
   }

   public CompletionStage applyToEitherAsync(CompletionStage other, Function fn) {
      return this.wrap(super.applyToEitherAsync(other, fn, this.defaultExecutor));
   }

   public CompletionStage applyToEitherAsync(CompletionStage other, Function fn, Executor executor) {
      return this.wrap(super.applyToEitherAsync(other, fn, executor));
   }

   public CompletionStage acceptEither(CompletionStage other, Consumer action) {
      return this.wrap(super.acceptEither(other, action));
   }

   public CompletionStage acceptEitherAsync(CompletionStage other, Consumer action) {
      return this.wrap(super.acceptEitherAsync(other, action, this.defaultExecutor));
   }

   public CompletionStage acceptEitherAsync(CompletionStage other, Consumer action, Executor executor) {
      return this.wrap(super.acceptEitherAsync(other, action, executor));
   }

   public CompletionStage runAfterEither(CompletionStage other, Runnable action) {
      return this.wrap(super.runAfterEither(other, action));
   }

   public CompletionStage runAfterEitherAsync(CompletionStage other, Runnable action) {
      return this.wrap(super.runAfterEitherAsync(other, action, this.defaultExecutor));
   }

   public CompletionStage runAfterEitherAsync(CompletionStage other, Runnable action, Executor executor) {
      return this.wrap(super.runAfterEitherAsync(other, action, executor));
   }

   public CompletionStage thenCompose(Function fn) {
      return this.wrap(super.thenCompose(fn));
   }

   public CompletionStage thenComposeAsync(Function fn) {
      return this.wrap(super.thenComposeAsync(fn, this.defaultExecutor));
   }

   public CompletionStage thenComposeAsync(Function fn, Executor executor) {
      return this.wrap(super.thenComposeAsync(fn, executor));
   }

   public CompletionStage exceptionally(Function fn) {
      return this.wrap(super.exceptionally(fn));
   }

   public CompletionStage whenComplete(BiConsumer action) {
      return this.wrap(super.whenComplete(action));
   }

   public CompletionStage whenCompleteAsync(BiConsumer action) {
      return this.wrap(super.whenCompleteAsync(action, this.defaultExecutor));
   }

   public CompletionStage whenCompleteAsync(BiConsumer action, Executor executor) {
      return this.wrap(super.whenCompleteAsync(action, executor));
   }

   public CompletionStage handle(BiFunction fn) {
      return this.wrap(super.handle(fn));
   }

   public CompletionStage handleAsync(BiFunction fn) {
      return this.wrap(super.handleAsync(fn, this.defaultExecutor));
   }

   public CompletionStage handleAsync(BiFunction fn, Executor executor) {
      return this.wrap(super.handleAsync(fn, executor));
   }

   private CompletionStage wrap(CompletionStage completionStage) {
      return new AsyncEventDeliveryStage(completionStage, this.defaultExecutor);
   }
}
