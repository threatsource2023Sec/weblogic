package org.python.netty.util.concurrent;

import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class UnaryPromiseNotifier implements FutureListener {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(UnaryPromiseNotifier.class);
   private final Promise promise;

   public UnaryPromiseNotifier(Promise promise) {
      this.promise = (Promise)ObjectUtil.checkNotNull(promise, "promise");
   }

   public void operationComplete(Future future) throws Exception {
      cascadeTo(future, this.promise);
   }

   public static void cascadeTo(Future completedFuture, Promise promise) {
      if (completedFuture.isSuccess()) {
         if (!promise.trySuccess(completedFuture.getNow())) {
            logger.warn("Failed to mark a promise as success because it is done already: {}", (Object)promise);
         }
      } else if (completedFuture.isCancelled()) {
         if (!promise.cancel(false)) {
            logger.warn("Failed to cancel a promise because it is done already: {}", (Object)promise);
         }
      } else if (!promise.tryFailure(completedFuture.cause())) {
         logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, completedFuture.cause());
      }

   }
}
