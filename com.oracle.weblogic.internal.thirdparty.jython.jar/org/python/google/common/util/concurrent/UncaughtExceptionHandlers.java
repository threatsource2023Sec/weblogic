package org.python.google.common.util.concurrent;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;

@GwtIncompatible
public final class UncaughtExceptionHandlers {
   private UncaughtExceptionHandlers() {
   }

   public static Thread.UncaughtExceptionHandler systemExit() {
      return new Exiter(Runtime.getRuntime());
   }

   @VisibleForTesting
   static final class Exiter implements Thread.UncaughtExceptionHandler {
      private static final Logger logger = Logger.getLogger(Exiter.class.getName());
      private final Runtime runtime;

      Exiter(Runtime runtime) {
         this.runtime = runtime;
      }

      public void uncaughtException(Thread t, Throwable e) {
         try {
            logger.log(Level.SEVERE, String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", t), e);
         } catch (Throwable var7) {
            System.err.println(e.getMessage());
            System.err.println(var7.getMessage());
         } finally {
            this.runtime.exit(1);
         }

      }
   }
}
