package org.glassfish.grizzly.nio.tmpselectors;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.nio.Selectors;

public class TemporarySelectorPool {
   private static final Logger LOGGER = Grizzly.logger(TemporarySelectorPool.class);
   public static final int DEFAULT_SELECTORS_COUNT = 32;
   private static final int MISS_THRESHOLD = 10000;
   private volatile int maxPoolSize;
   private final AtomicBoolean isClosed;
   private final Queue selectors;
   private final AtomicInteger poolSize;
   private final AtomicInteger missesCounter;
   private final SelectorProvider selectorProvider;

   public TemporarySelectorPool(SelectorProvider selectorProvider) {
      this(selectorProvider, 32);
   }

   public TemporarySelectorPool(SelectorProvider selectorProvider, int selectorsCount) {
      this.selectorProvider = selectorProvider;
      this.maxPoolSize = selectorsCount;
      this.isClosed = new AtomicBoolean();
      this.selectors = new ConcurrentLinkedQueue();
      this.poolSize = new AtomicInteger();
      this.missesCounter = new AtomicInteger();
   }

   public synchronized int size() {
      return this.maxPoolSize;
   }

   public synchronized void setSize(int size) throws IOException {
      if (!this.isClosed.get()) {
         this.missesCounter.set(0);
         this.maxPoolSize = size;
      }
   }

   public SelectorProvider getSelectorProvider() {
      return this.selectorProvider;
   }

   public Selector poll() throws IOException {
      Selector selector = (Selector)this.selectors.poll();
      if (selector != null) {
         this.poolSize.decrementAndGet();
      } else {
         try {
            selector = Selectors.newSelector(this.selectorProvider);
         } catch (IOException var3) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_CREATE_SELECTOR_EXCEPTION(), var3);
         }

         int missesCount = this.missesCounter.incrementAndGet();
         if (missesCount % 10000 == 0) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_MISSES_EXCEPTION(missesCount, this.maxPoolSize));
         }
      }

      return selector;
   }

   public void offer(Selector selector) {
      if (selector != null) {
         boolean wasReturned;
         if (this.poolSize.getAndIncrement() < this.maxPoolSize && (selector = this.checkSelector(selector)) != null) {
            this.selectors.offer(selector);
            wasReturned = true;
         } else {
            this.poolSize.decrementAndGet();
            if (selector == null) {
               return;
            }

            wasReturned = false;
         }

         if (this.isClosed.get()) {
            if (this.selectors.remove(selector)) {
               this.closeSelector(selector);
            }
         } else if (!wasReturned) {
            this.closeSelector(selector);
         }

      }
   }

   public synchronized void close() {
      Selector selector;
      if (!this.isClosed.getAndSet(true)) {
         while((selector = (Selector)this.selectors.poll()) != null) {
            this.closeSelector(selector);
         }
      }

   }

   private void closeSelector(Selector selector) {
      try {
         selector.close();
      } catch (IOException var3) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "TemporarySelectorFactory: error occurred when trying to close the Selector", var3);
         }
      }

   }

   private Selector checkSelector(Selector selector) {
      try {
         selector.selectNow();
         return selector;
      } catch (IOException var5) {
         LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_SELECTOR_FAILURE_EXCEPTION(), var5);

         try {
            return Selectors.newSelector(this.selectorProvider);
         } catch (IOException var4) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_CREATE_SELECTOR_EXCEPTION(), var4);
            return null;
         }
      }
   }
}
