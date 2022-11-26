package org.glassfish.grizzly.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public final class SilentConnectionFilter extends BaseFilter {
   private static final Logger LOGGER = Grizzly.logger(SilentConnectionFilter.class);
   public static final long UNLIMITED_TIMEOUT = -1L;
   public static final long UNSET_TIMEOUT = 0L;
   private static final String ATTR_NAME = SilentConnectionFilter.class.getName() + ".silent-connection-attr";
   private static final Attribute silentConnectionAttr;
   private final long timeoutMillis;
   private final DelayedExecutor.DelayQueue queue;

   public SilentConnectionFilter(DelayedExecutor executor, long timeout, TimeUnit timeunit) {
      this.timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
      this.queue = executor.createDelayQueue(new DelayedExecutor.Worker() {
         public boolean doWork(Connection connection) {
            connection.closeSilently();
            return true;
         }
      }, new Resolver());
   }

   public long getTimeout(TimeUnit timeunit) {
      return timeunit.convert(this.timeoutMillis, TimeUnit.MILLISECONDS);
   }

   public NextAction handleAccept(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      this.queue.add(connection, this.timeoutMillis, TimeUnit.MILLISECONDS);
      return ctx.getInvokeAction();
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      this.queue.remove(connection);
      return ctx.getInvokeAction();
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      this.queue.remove(connection);
      return ctx.getInvokeAction();
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      this.queue.remove(ctx.getConnection());
      return ctx.getInvokeAction();
   }

   static {
      silentConnectionAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(ATTR_NAME);
   }

   private static final class Resolver implements DelayedExecutor.Resolver {
      private Resolver() {
      }

      public boolean removeTimeout(Connection connection) {
         return SilentConnectionFilter.silentConnectionAttr.remove((AttributeStorage)connection) != null;
      }

      public long getTimeoutMillis(Connection connection) {
         Long timeout = (Long)SilentConnectionFilter.silentConnectionAttr.get((AttributeStorage)connection);
         return timeout != null ? timeout : -1L;
      }

      public void setTimeoutMillis(Connection connection, long timeoutMillis) {
         SilentConnectionFilter.silentConnectionAttr.set((AttributeStorage)connection, timeoutMillis);
      }

      // $FF: synthetic method
      Resolver(Object x0) {
         this();
      }
   }
}
