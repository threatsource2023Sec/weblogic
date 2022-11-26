package org.python.netty.util.concurrent;

import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PromiseNotificationUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class PromiseNotifier implements GenericFutureListener {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PromiseNotifier.class);
   private final Promise[] promises;
   private final boolean logNotifyFailure;

   @SafeVarargs
   public PromiseNotifier(Promise... promises) {
      this(true, promises);
   }

   @SafeVarargs
   public PromiseNotifier(boolean logNotifyFailure, Promise... promises) {
      ObjectUtil.checkNotNull(promises, "promises");
      Promise[] var3 = promises;
      int var4 = promises.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Promise promise = var3[var5];
         if (promise == null) {
            throw new IllegalArgumentException("promises contains null Promise");
         }
      }

      this.promises = (Promise[])promises.clone();
      this.logNotifyFailure = logNotifyFailure;
   }

   public void operationComplete(Future future) throws Exception {
      InternalLogger internalLogger = this.logNotifyFailure ? logger : null;
      Promise[] var4;
      int var5;
      int var6;
      Promise p;
      if (future.isSuccess()) {
         Object result = future.get();
         var4 = this.promises;
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            p = var4[var6];
            PromiseNotificationUtil.trySuccess(p, result, internalLogger);
         }
      } else if (future.isCancelled()) {
         Promise[] var8 = this.promises;
         int var10 = var8.length;

         for(var5 = 0; var5 < var10; ++var5) {
            Promise p = var8[var5];
            PromiseNotificationUtil.tryCancel(p, internalLogger);
         }
      } else {
         Throwable cause = future.cause();
         var4 = this.promises;
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            p = var4[var6];
            PromiseNotificationUtil.tryFailure(p, cause, internalLogger);
         }
      }

   }
}
