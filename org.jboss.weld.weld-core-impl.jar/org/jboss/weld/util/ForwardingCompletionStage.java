package org.jboss.weld.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ForwardingCompletionStage implements CompletionStage {
   protected abstract CompletionStage delegate();

   public CompletionStage thenApply(Function fn) {
      return this.delegate().thenApply(fn);
   }

   public CompletionStage thenApplyAsync(Function fn) {
      return this.delegate().thenApplyAsync(fn);
   }

   public CompletionStage thenApplyAsync(Function fn, Executor executor) {
      return this.delegate().thenApplyAsync(fn, executor);
   }

   public CompletionStage thenAccept(Consumer action) {
      return this.delegate().thenAccept(action);
   }

   public CompletionStage thenAcceptAsync(Consumer action) {
      return this.delegate().thenAcceptAsync(action);
   }

   public CompletionStage thenAcceptAsync(Consumer action, Executor executor) {
      return this.delegate().thenAcceptAsync(action, executor);
   }

   public CompletionStage thenRun(Runnable action) {
      return this.delegate().thenRun(action);
   }

   public CompletionStage thenRunAsync(Runnable action) {
      return this.delegate().thenRunAsync(action);
   }

   public CompletionStage thenRunAsync(Runnable action, Executor executor) {
      return this.delegate().thenRunAsync(action, executor);
   }

   public CompletionStage thenCombine(CompletionStage other, BiFunction fn) {
      return this.delegate().thenCombine(other, fn);
   }

   public CompletionStage thenCombineAsync(CompletionStage other, BiFunction fn) {
      return this.delegate().thenCombineAsync(other, fn);
   }

   public CompletionStage thenCombineAsync(CompletionStage other, BiFunction fn, Executor executor) {
      return this.delegate().thenCombineAsync(other, fn, executor);
   }

   public CompletionStage thenAcceptBoth(CompletionStage other, BiConsumer action) {
      return this.delegate().thenAcceptBoth(other, action);
   }

   public CompletionStage thenAcceptBothAsync(CompletionStage other, BiConsumer action) {
      return this.delegate().thenAcceptBothAsync(other, action);
   }

   public CompletionStage thenAcceptBothAsync(CompletionStage other, BiConsumer action, Executor executor) {
      return this.delegate().thenAcceptBothAsync(other, action, executor);
   }

   public CompletionStage runAfterBoth(CompletionStage other, Runnable action) {
      return this.delegate().runAfterBoth(other, action);
   }

   public CompletionStage runAfterBothAsync(CompletionStage other, Runnable action) {
      return this.delegate().runAfterBothAsync(other, action);
   }

   public CompletionStage runAfterBothAsync(CompletionStage other, Runnable action, Executor executor) {
      return this.delegate().runAfterBothAsync(other, action, executor);
   }

   public CompletionStage applyToEither(CompletionStage other, Function fn) {
      return this.delegate().applyToEither(other, fn);
   }

   public CompletionStage applyToEitherAsync(CompletionStage other, Function fn) {
      return this.delegate().applyToEitherAsync(other, fn);
   }

   public CompletionStage applyToEitherAsync(CompletionStage other, Function fn, Executor executor) {
      return this.delegate().applyToEitherAsync(other, fn, executor);
   }

   public CompletionStage acceptEither(CompletionStage other, Consumer action) {
      return this.delegate().acceptEither(other, action);
   }

   public CompletionStage acceptEitherAsync(CompletionStage other, Consumer action) {
      return this.delegate().acceptEitherAsync(other, action);
   }

   public CompletionStage acceptEitherAsync(CompletionStage other, Consumer action, Executor executor) {
      return this.delegate().acceptEitherAsync(other, action, executor);
   }

   public CompletionStage runAfterEither(CompletionStage other, Runnable action) {
      return this.delegate().runAfterEither(other, action);
   }

   public CompletionStage runAfterEitherAsync(CompletionStage other, Runnable action) {
      return this.delegate().runAfterEitherAsync(other, action);
   }

   public CompletionStage runAfterEitherAsync(CompletionStage other, Runnable action, Executor executor) {
      return this.delegate().runAfterEitherAsync(other, action, executor);
   }

   public CompletionStage thenCompose(Function fn) {
      return this.delegate().thenCompose(fn);
   }

   public CompletionStage thenComposeAsync(Function fn) {
      return this.delegate().thenComposeAsync(fn);
   }

   public CompletionStage thenComposeAsync(Function fn, Executor executor) {
      return this.delegate().thenComposeAsync(fn, executor);
   }

   public CompletionStage exceptionally(Function fn) {
      return this.delegate().exceptionally(fn);
   }

   public CompletionStage whenComplete(BiConsumer action) {
      return this.delegate().whenComplete(action);
   }

   public CompletionStage whenCompleteAsync(BiConsumer action) {
      return this.delegate().whenCompleteAsync(action);
   }

   public CompletionStage whenCompleteAsync(BiConsumer action, Executor executor) {
      return this.delegate().whenCompleteAsync(action, executor);
   }

   public CompletionStage handle(BiFunction fn) {
      return this.delegate().handle(fn);
   }

   public CompletionStage handleAsync(BiFunction fn) {
      return this.delegate().handleAsync(fn);
   }

   public CompletionStage handleAsync(BiFunction fn, Executor executor) {
      return this.delegate().handleAsync(fn, executor);
   }

   public CompletableFuture toCompletableFuture() {
      return this.delegate().toCompletableFuture();
   }
}
