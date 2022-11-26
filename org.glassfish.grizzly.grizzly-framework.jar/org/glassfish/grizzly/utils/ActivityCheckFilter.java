package org.glassfish.grizzly.utils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class ActivityCheckFilter extends BaseFilter {
   private static final Logger LOGGER = Grizzly.logger(ActivityCheckFilter.class);
   public static final String ACTIVE_ATTRIBUTE_NAME = "connection-active-attribute";
   private static final Attribute IDLE_ATTR;
   private final long timeoutMillis;
   private final DelayedExecutor.DelayQueue queue;

   public ActivityCheckFilter(DelayedExecutor executor, long timeout, TimeUnit timeoutUnit) {
      this(executor, timeout, timeoutUnit, (TimeoutHandler)null);
   }

   public ActivityCheckFilter(DelayedExecutor executor, long timeout, TimeUnit timeoutUnit, TimeoutHandler handler) {
      this(executor, new DefaultWorker(handler), timeout, timeoutUnit);
   }

   protected ActivityCheckFilter(DelayedExecutor executor, DelayedExecutor.Worker worker, long timeout, TimeUnit timeoutUnit) {
      if (executor == null) {
         throw new IllegalArgumentException("executor cannot be null");
      } else {
         this.timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeoutUnit);
         this.queue = executor.createDelayQueue(worker, new Resolver());
      }
   }

   public NextAction handleAccept(FilterChainContext ctx) throws IOException {
      this.queue.add(ctx.getConnection(), this.timeoutMillis, TimeUnit.MILLISECONDS);
      return ctx.getInvokeAction();
   }

   public NextAction handleConnect(FilterChainContext ctx) throws IOException {
      this.queue.add(ctx.getConnection(), this.timeoutMillis, TimeUnit.MILLISECONDS);
      return ctx.getInvokeAction();
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      ((ActiveRecord)IDLE_ATTR.get((AttributeStorage)ctx.getConnection())).timeoutMillis = System.currentTimeMillis() + this.timeoutMillis;
      return ctx.getInvokeAction();
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      ((ActiveRecord)IDLE_ATTR.get((AttributeStorage)ctx.getConnection())).timeoutMillis = System.currentTimeMillis() + this.timeoutMillis;
      return ctx.getInvokeAction();
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      this.queue.remove(ctx.getConnection());
      return ctx.getInvokeAction();
   }

   public static DelayedExecutor createDefaultIdleDelayedExecutor() {
      return createDefaultIdleDelayedExecutor(1000L, TimeUnit.MILLISECONDS);
   }

   public static DelayedExecutor createDefaultIdleDelayedExecutor(long checkInterval, TimeUnit checkIntervalUnit) {
      ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
         public Thread newThread(Runnable r) {
            Thread newThread = new Thread(r);
            newThread.setName("Grizzly-ActiveTimeoutFilter-IdleCheck");
            newThread.setDaemon(true);
            return newThread;
         }
      });
      return new DelayedExecutor(executor, checkInterval > 0L ? checkInterval : 1000L, checkIntervalUnit != null ? checkIntervalUnit : TimeUnit.MILLISECONDS);
   }

   public long getTimeout(TimeUnit timeunit) {
      return timeunit.convert(this.timeoutMillis, TimeUnit.MILLISECONDS);
   }

   static {
      IDLE_ATTR = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("connection-active-attribute", new NullaryFunction() {
         public ActiveRecord evaluate() {
            return new ActiveRecord();
         }
      });
   }

   private static final class DefaultWorker implements DelayedExecutor.Worker {
      private final TimeoutHandler handler;

      DefaultWorker(TimeoutHandler handler) {
         this.handler = handler;
      }

      public boolean doWork(Connection connection) {
         if (this.handler != null) {
            this.handler.onTimeout(connection);
         }

         connection.closeSilently();
         return true;
      }
   }

   private static final class ActiveRecord {
      private volatile long timeoutMillis;

      private ActiveRecord() {
      }

      // $FF: synthetic method
      ActiveRecord(Object x0) {
         this();
      }
   }

   private static final class Resolver implements DelayedExecutor.Resolver {
      private Resolver() {
      }

      public boolean removeTimeout(Connection connection) {
         ((ActiveRecord)ActivityCheckFilter.IDLE_ATTR.get((AttributeStorage)connection)).timeoutMillis = 0L;
         return true;
      }

      public long getTimeoutMillis(Connection connection) {
         return ((ActiveRecord)ActivityCheckFilter.IDLE_ATTR.get((AttributeStorage)connection)).timeoutMillis;
      }

      public void setTimeoutMillis(Connection connection, long timeoutMillis) {
         ((ActiveRecord)ActivityCheckFilter.IDLE_ATTR.get((AttributeStorage)connection)).timeoutMillis = timeoutMillis;
      }

      // $FF: synthetic method
      Resolver(Object x0) {
         this();
      }
   }

   public interface TimeoutHandler {
      void onTimeout(Connection var1);
   }
}
