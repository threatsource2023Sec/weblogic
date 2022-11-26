package org.python.netty.util.internal;

import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.logging.InternalLogger;

public final class PromiseNotificationUtil {
   private PromiseNotificationUtil() {
   }

   public static void tryCancel(Promise p, InternalLogger logger) {
      if (!p.cancel(false) && logger != null) {
         Throwable err = p.cause();
         if (err == null) {
            logger.warn("Failed to cancel promise because it has succeeded already: {}", (Object)p);
         } else {
            logger.warn("Failed to cancel promise because it has failed already: {}, unnotified cause:", p, err);
         }
      }

   }

   public static void trySuccess(Promise p, Object result, InternalLogger logger) {
      if (!p.trySuccess(result) && logger != null) {
         Throwable err = p.cause();
         if (err == null) {
            logger.warn("Failed to mark a promise as success because it has succeeded already: {}", (Object)p);
         } else {
            logger.warn("Failed to mark a promise as success because it has failed already: {}, unnotified cause:", p, err);
         }
      }

   }

   public static void tryFailure(Promise p, Throwable cause, InternalLogger logger) {
      if (!p.tryFailure(cause) && logger != null) {
         Throwable err = p.cause();
         if (err == null) {
            logger.warn("Failed to mark a promise as failure because it has succeeded already: {}", p, cause);
         } else {
            logger.warn("Failed to mark a promise as failure because it has failed already: {}, unnotified cause: {}", p, ThrowableUtil.stackTraceToString(err), cause);
         }
      }

   }
}
