package org.glassfish.grizzly.nio;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.GracefulShutdownListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ShutdownContext;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.localization.LogMessages;

class GracefulShutdownRunner implements Runnable {
   private static final Logger LOGGER = Grizzly.logger(GracefulShutdownRunner.class);
   private final NIOTransport transport;
   private final Set shutdownListeners;
   private final ExecutorService shutdownService;
   private final long gracePeriod;
   private final TimeUnit timeUnit;

   GracefulShutdownRunner(NIOTransport transport, Set shutdownListeners, ExecutorService shutdownService, long gracePeriod, TimeUnit timeUnit) {
      this.transport = transport;
      this.shutdownListeners = shutdownListeners;
      this.shutdownService = shutdownService;
      this.gracePeriod = gracePeriod;
      this.timeUnit = timeUnit;
   }

   public void run() {
      int listenerCount = this.shutdownListeners.size();
      final CountDownLatch shutdownLatch = new CountDownLatch(listenerCount);
      final Map contexts = new HashMap(listenerCount);
      if (this.gracePeriod <= 0L) {
         Iterator var4 = this.shutdownListeners.iterator();

         while(var4.hasNext()) {
            GracefulShutdownListener l = (GracefulShutdownListener)var4.next();
            ShutdownContext ctx = this.createContext(contexts, l, shutdownLatch);
            l.shutdownRequested(ctx);
         }
      } else {
         this.shutdownService.execute(new Runnable() {
            public void run() {
               Iterator var1 = GracefulShutdownRunner.this.shutdownListeners.iterator();

               while(var1.hasNext()) {
                  GracefulShutdownListener l = (GracefulShutdownListener)var1.next();
                  ShutdownContext ctx = GracefulShutdownRunner.this.createContext(contexts, l, shutdownLatch);
                  l.shutdownRequested(ctx);
               }

            }
         });
      }

      boolean var32 = false;

      ReentrantReadWriteLock.WriteLock lock;
      label524: {
         try {
            Iterator var40;
            GracefulShutdownListener l;
            try {
               var32 = true;
               if (this.gracePeriod <= 0L) {
                  shutdownLatch.await();
                  var32 = false;
               } else {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_GRACEFULSHUTDOWN_MSG(this.transport.getName() + '[' + Integer.toHexString(this.hashCode()) + ']', this.gracePeriod, this.timeUnit));
                  }

                  boolean result = shutdownLatch.await(this.gracePeriod, this.timeUnit);
                  if (result) {
                     var32 = false;
                  } else {
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_GRACEFULSHUTDOWN_EXCEEDED(this.transport.getName() + '[' + Integer.toHexString(this.hashCode()) + ']'));
                     }

                     if (contexts.isEmpty()) {
                        var32 = false;
                     } else {
                        var40 = contexts.values().iterator();

                        while(var40.hasNext()) {
                           l = (GracefulShutdownListener)var40.next();
                           l.shutdownForced();
                        }

                        var32 = false;
                     }
                  }
               }
               break label524;
            } catch (InterruptedException var36) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.warning(LogMessages.WARNING_GRIZZLY_GRACEFULSHUTDOWN_INTERRUPTED());
               }
            }

            if (!contexts.isEmpty()) {
               var40 = contexts.values().iterator();

               while(var40.hasNext()) {
                  l = (GracefulShutdownListener)var40.next();
                  l.shutdownForced();
               }

               var32 = false;
            } else {
               var32 = false;
            }
         } finally {
            if (var32) {
               ReentrantReadWriteLock.WriteLock lock = this.transport.getState().getStateLocker().writeLock();
               lock.lock();

               try {
                  if (this.transport.shutdownService == this.shutdownService) {
                     this.transport.finalizeShutdown();
                  }
               } finally {
                  lock.unlock();
               }

            }
         }

         lock = this.transport.getState().getStateLocker().writeLock();
         lock.lock();

         try {
            if (this.transport.shutdownService == this.shutdownService) {
               this.transport.finalizeShutdown();
            }

            return;
         } finally {
            lock.unlock();
         }
      }

      lock = this.transport.getState().getStateLocker().writeLock();
      lock.lock();

      try {
         if (this.transport.shutdownService == this.shutdownService) {
            this.transport.finalizeShutdown();
         }
      } finally {
         lock.unlock();
      }

   }

   private ShutdownContext createContext(final Map contexts, GracefulShutdownListener listener, final CountDownLatch shutdownLatch) {
      ShutdownContext ctx = new ShutdownContext() {
         boolean isNotified;

         public Transport getTransport() {
            return GracefulShutdownRunner.this.transport;
         }

         public synchronized void ready() {
            if (!this.isNotified) {
               this.isNotified = true;
               contexts.remove(this);
               shutdownLatch.countDown();
            }

         }
      };
      contexts.put(ctx, listener);
      return ctx;
   }
}
