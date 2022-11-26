package org.glassfish.grizzly.utils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class IdleTimeoutFilter extends BaseFilter {
   public static final Long FOREVER = Long.MAX_VALUE;
   public static final Long FOREVER_SPECIAL;
   public static final String IDLE_ATTRIBUTE_NAME = "connection-idle-attribute";
   private static final Attribute IDLE_ATTR;
   private final TimeoutResolver timeoutResolver;
   private final DelayedExecutor.DelayQueue queue;
   private final DelayedExecutor.Resolver resolver;
   private final FilterChainContext.CompletionListener contextCompletionListener;

   public IdleTimeoutFilter(DelayedExecutor executor, long timeout, TimeUnit timeoutUnit) {
      this(executor, timeout, timeoutUnit, (TimeoutHandler)null);
   }

   public IdleTimeoutFilter(DelayedExecutor executor, TimeoutResolver timeoutResolver) {
      this(executor, (TimeoutResolver)timeoutResolver, (TimeoutHandler)null);
   }

   public IdleTimeoutFilter(DelayedExecutor executor, long timeout, TimeUnit timeUnit, TimeoutHandler handler) {
      this(executor, (DelayedExecutor.Worker)(new DefaultWorker(handler)), (TimeoutResolver)(new IdleTimeoutResolver(convertToMillis(timeout, timeUnit))));
   }

   public IdleTimeoutFilter(DelayedExecutor executor, TimeoutResolver timeoutResolver, TimeoutHandler handler) {
      this(executor, (DelayedExecutor.Worker)(new DefaultWorker(handler)), (TimeoutResolver)timeoutResolver);
   }

   protected IdleTimeoutFilter(DelayedExecutor executor, DelayedExecutor.Worker worker, TimeoutResolver timeoutResolver) {
      this.contextCompletionListener = new ContextCompletionListener();
      if (executor == null) {
         throw new IllegalArgumentException("executor cannot be null");
      } else {
         this.timeoutResolver = timeoutResolver;
         this.resolver = new Resolver();
         this.queue = executor.createDelayQueue(worker, this.resolver);
      }
   }

   public NextAction handleAccept(FilterChainContext ctx) throws IOException {
      this.queue.add(ctx.getConnection(), FOREVER, TimeUnit.MILLISECONDS);
      this.queueAction(ctx);
      return ctx.getInvokeAction();
   }

   public NextAction handleConnect(FilterChainContext ctx) throws IOException {
      this.queue.add(ctx.getConnection(), FOREVER, TimeUnit.MILLISECONDS);
      this.queueAction(ctx);
      return ctx.getInvokeAction();
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      this.queueAction(ctx);
      return ctx.getInvokeAction();
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      this.queueAction(ctx);
      return ctx.getInvokeAction();
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      this.queue.remove(ctx.getConnection());
      return ctx.getInvokeAction();
   }

   public DelayedExecutor.Resolver getResolver() {
      return this.resolver;
   }

   public static DelayedExecutor createDefaultIdleDelayedExecutor() {
      return createDefaultIdleDelayedExecutor(1000L, TimeUnit.MILLISECONDS);
   }

   public static DelayedExecutor createDefaultIdleDelayedExecutor(long checkInterval, TimeUnit checkIntervalUnit) {
      ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
         public Thread newThread(Runnable r) {
            Thread newThread = new Thread(r);
            newThread.setName("Grizzly-IdleTimeoutFilter-IdleCheck");
            newThread.setDaemon(true);
            return newThread;
         }
      });
      return new DelayedExecutor(executor, checkInterval > 0L ? checkInterval : 1000L, checkIntervalUnit != null ? checkIntervalUnit : TimeUnit.MILLISECONDS);
   }

   public static void setCustomTimeout(Connection connection, long timeout, TimeUnit timeunit) {
      ((IdleRecord)IDLE_ATTR.get((AttributeStorage)connection)).setInitialTimeoutMillis(convertToMillis(timeout, timeunit));
   }

   protected void queueAction(FilterChainContext ctx) {
      Connection connection = ctx.getConnection();
      IdleRecord idleRecord = (IdleRecord)IDLE_ATTR.get((AttributeStorage)connection);
      if (IdleTimeoutFilter.IdleRecord.counterUpdater.getAndIncrement(idleRecord) == 0) {
         idleRecord.timeoutMillis = FOREVER;
      }

      ctx.addCompletionListener(this.contextCompletionListener);
   }

   private static long convertToMillis(long time, TimeUnit timeUnit) {
      return time >= 0L ? TimeUnit.MILLISECONDS.convert(time, timeUnit) : FOREVER;
   }

   static {
      FOREVER_SPECIAL = FOREVER - 1L;
      IDLE_ATTR = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("connection-idle-attribute", new NullaryFunction() {
         public IdleRecord evaluate() {
            return new IdleRecord();
         }
      });
   }

   private static final class DefaultWorker implements DelayedExecutor.Worker {
      private final TimeoutHandler handler;

      DefaultWorker(TimeoutHandler handler) {
         this.handler = handler;
      }

      public boolean doWork(Connection connection) {
         if (connection.isOpen()) {
            if (this.handler != null) {
               this.handler.onTimeout(connection);
            }

            connection.closeSilently();
         }

         return true;
      }
   }

   private static final class IdleRecord {
      private boolean isClosed;
      private volatile boolean isInitialSet;
      private long initialTimeoutMillis;
      private static final AtomicLongFieldUpdater timeoutMillisUpdater = AtomicLongFieldUpdater.newUpdater(IdleRecord.class, "timeoutMillis");
      private volatile long timeoutMillis;
      private static final AtomicIntegerFieldUpdater counterUpdater = AtomicIntegerFieldUpdater.newUpdater(IdleRecord.class, "counter");
      private volatile int counter;

      private IdleRecord() {
      }

      private long getInitialTimeoutMillis(long defaultTimeoutMillis) {
         return this.isInitialSet ? this.initialTimeoutMillis : defaultTimeoutMillis;
      }

      private void setInitialTimeoutMillis(long initialTimeoutMillis) {
         this.initialTimeoutMillis = initialTimeoutMillis;
         this.isInitialSet = true;
      }

      private void close() {
         this.isClosed = true;
         this.timeoutMillis = 0L;
      }

      // $FF: synthetic method
      IdleRecord(Object x0) {
         this();
      }
   }

   private static final class Resolver implements DelayedExecutor.Resolver {
      private Resolver() {
      }

      public boolean removeTimeout(Connection connection) {
         ((IdleRecord)IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage)connection)).close();
         return true;
      }

      public long getTimeoutMillis(Connection connection) {
         return ((IdleRecord)IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage)connection)).timeoutMillis;
      }

      public void setTimeoutMillis(Connection connection, long timeoutMillis) {
         ((IdleRecord)IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage)connection)).timeoutMillis = timeoutMillis;
      }

      // $FF: synthetic method
      Resolver(Object x0) {
         this();
      }
   }

   private static final class IdleTimeoutResolver implements TimeoutResolver {
      private final long defaultTimeoutMillis;

      IdleTimeoutResolver(long defaultTimeoutMillis) {
         this.defaultTimeoutMillis = defaultTimeoutMillis;
      }

      public long getTimeout(FilterChainContext ctx) {
         return ((IdleRecord)IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage)ctx.getConnection())).getInitialTimeoutMillis(this.defaultTimeoutMillis);
      }
   }

   private final class ContextCompletionListener implements FilterChainContext.CompletionListener {
      private ContextCompletionListener() {
      }

      public void onComplete(FilterChainContext ctx) {
         Connection connection = ctx.getConnection();
         IdleRecord idleRecord = (IdleRecord)IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage)connection);
         idleRecord.timeoutMillis = IdleTimeoutFilter.FOREVER_SPECIAL;
         if (idleRecord.isClosed || IdleTimeoutFilter.IdleRecord.counterUpdater.decrementAndGet(idleRecord) == 0) {
            long timeoutToSet;
            if (idleRecord.isClosed) {
               timeoutToSet = 0L;
               IdleTimeoutFilter.IdleRecord.counterUpdater.set(idleRecord, 0);
            } else {
               long timeout = IdleTimeoutFilter.this.timeoutResolver.getTimeout(ctx);
               timeoutToSet = timeout == IdleTimeoutFilter.FOREVER ? IdleTimeoutFilter.FOREVER : System.currentTimeMillis() + timeout;
            }

            IdleTimeoutFilter.IdleRecord.timeoutMillisUpdater.compareAndSet(idleRecord, IdleTimeoutFilter.FOREVER_SPECIAL, timeoutToSet);
         }

      }

      // $FF: synthetic method
      ContextCompletionListener(Object x1) {
         this();
      }
   }

   public interface TimeoutResolver {
      long getTimeout(FilterChainContext var1);
   }

   public interface TimeoutHandler {
      void onTimeout(Connection var1);
   }
}
