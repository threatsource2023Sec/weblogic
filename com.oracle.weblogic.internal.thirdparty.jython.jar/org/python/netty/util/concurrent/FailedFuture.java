package org.python.netty.util.concurrent;

import org.python.netty.util.internal.PlatformDependent;

public final class FailedFuture extends CompleteFuture {
   private final Throwable cause;

   public FailedFuture(EventExecutor executor, Throwable cause) {
      super(executor);
      if (cause == null) {
         throw new NullPointerException("cause");
      } else {
         this.cause = cause;
      }
   }

   public Throwable cause() {
      return this.cause;
   }

   public boolean isSuccess() {
      return false;
   }

   public Future sync() {
      PlatformDependent.throwException(this.cause);
      return this;
   }

   public Future syncUninterruptibly() {
      PlatformDependent.throwException(this.cause);
      return this;
   }

   public Object getNow() {
      return null;
   }
}
