package org.python.netty.util.concurrent;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class ImmediateEventExecutor extends AbstractEventExecutor {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ImmediateEventExecutor.class);
   public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
   private static final FastThreadLocal DELAYED_RUNNABLES = new FastThreadLocal() {
      protected Queue initialValue() throws Exception {
         return new ArrayDeque();
      }
   };
   private static final FastThreadLocal RUNNING = new FastThreadLocal() {
      protected Boolean initialValue() throws Exception {
         return false;
      }
   };
   private final Future terminationFuture;

   private ImmediateEventExecutor() {
      this.terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());
   }

   public boolean inEventLoop() {
      return true;
   }

   public boolean inEventLoop(Thread thread) {
      return true;
   }

   public Future shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      return this.terminationFuture();
   }

   public Future terminationFuture() {
      return this.terminationFuture;
   }

   /** @deprecated */
   @Deprecated
   public void shutdown() {
   }

   public boolean isShuttingDown() {
      return false;
   }

   public boolean isShutdown() {
      return false;
   }

   public boolean isTerminated() {
      return false;
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) {
      return false;
   }

   public void execute(Runnable command) {
      if (command == null) {
         throw new NullPointerException("command");
      } else {
         if (!(Boolean)RUNNING.get()) {
            RUNNING.set(true);
            boolean var14 = false;

            Queue delayedRunnables;
            Runnable runnable;
            label143: {
               try {
                  var14 = true;
                  command.run();
                  var14 = false;
                  break label143;
               } catch (Throwable var18) {
                  logger.info("Throwable caught while executing Runnable {}", command, var18);
                  var14 = false;
               } finally {
                  if (var14) {
                     Queue delayedRunnables = (Queue)DELAYED_RUNNABLES.get();

                     Runnable runnable;
                     while((runnable = (Runnable)delayedRunnables.poll()) != null) {
                        try {
                           runnable.run();
                        } catch (Throwable var15) {
                           logger.info("Throwable caught while executing Runnable {}", runnable, var15);
                        }
                     }

                     RUNNING.set(false);
                  }
               }

               delayedRunnables = (Queue)DELAYED_RUNNABLES.get();

               while((runnable = (Runnable)delayedRunnables.poll()) != null) {
                  try {
                     runnable.run();
                  } catch (Throwable var16) {
                     logger.info("Throwable caught while executing Runnable {}", runnable, var16);
                  }
               }

               RUNNING.set(false);
               return;
            }

            delayedRunnables = (Queue)DELAYED_RUNNABLES.get();

            while((runnable = (Runnable)delayedRunnables.poll()) != null) {
               try {
                  runnable.run();
               } catch (Throwable var17) {
                  logger.info("Throwable caught while executing Runnable {}", runnable, var17);
               }
            }

            RUNNING.set(false);
         } else {
            ((Queue)DELAYED_RUNNABLES.get()).add(command);
         }

      }
   }

   public Promise newPromise() {
      return new ImmediatePromise(this);
   }

   public ProgressivePromise newProgressivePromise() {
      return new ImmediateProgressivePromise(this);
   }

   static class ImmediateProgressivePromise extends DefaultProgressivePromise {
      ImmediateProgressivePromise(EventExecutor executor) {
         super(executor);
      }

      protected void checkDeadLock() {
      }
   }

   static class ImmediatePromise extends DefaultPromise {
      ImmediatePromise(EventExecutor executor) {
         super(executor);
      }

      protected void checkDeadLock() {
      }
   }
}
