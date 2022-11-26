package org.glassfish.grizzly.filterchain;

import java.io.IOException;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Transport;

public class TransportFilter extends BaseFilter {
   private static final FlushEvent FLUSH_EVENT = new FlushEvent();

   public static FilterChainEvent createFlushEvent() {
      return FLUSH_EVENT;
   }

   public static FilterChainEvent createFlushEvent(CompletionHandler completionHandler) {
      return completionHandler == null ? FLUSH_EVENT : new FlushEvent(completionHandler);
   }

   public NextAction handleAccept(FilterChainContext ctx) throws IOException {
      Filter transportFilter0 = this.getTransportFilter0(ctx.getConnection().getTransport());
      return transportFilter0 != null ? transportFilter0.handleAccept(ctx) : null;
   }

   public NextAction handleConnect(FilterChainContext ctx) throws IOException {
      Filter transportFilter0 = this.getTransportFilter0(ctx.getConnection().getTransport());
      return transportFilter0 != null ? transportFilter0.handleConnect(ctx) : null;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Filter transportFilter0 = this.getTransportFilter0(ctx.getConnection().getTransport());
      return transportFilter0 != null ? transportFilter0.handleRead(ctx) : null;
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      Filter transportFilter0 = this.getTransportFilter0(ctx.getConnection().getTransport());
      return transportFilter0 != null ? transportFilter0.handleWrite(ctx) : null;
   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      Filter transportFilter0 = this.getTransportFilter0(ctx.getConnection().getTransport());
      return transportFilter0 != null ? transportFilter0.handleEvent(ctx, event) : null;
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      Filter transportFilter0 = this.getTransportFilter0(ctx.getConnection().getTransport());
      return transportFilter0 != null ? transportFilter0.handleClose(ctx) : null;
   }

   protected Filter getTransportFilter0(Transport transport) {
      return transport instanceof FilterChainEnabledTransport ? ((FilterChainEnabledTransport)transport).getTransportFilter() : null;
   }

   public static final class FlushEvent implements FilterChainEvent {
      public static final Object TYPE = FlushEvent.class;
      final CompletionHandler completionHandler;

      private FlushEvent() {
         this((CompletionHandler)null);
      }

      private FlushEvent(CompletionHandler completionHandler) {
         this.completionHandler = completionHandler;
      }

      public Object type() {
         return TYPE;
      }

      public CompletionHandler getCompletionHandler() {
         return this.completionHandler;
      }

      // $FF: synthetic method
      FlushEvent(CompletionHandler x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      FlushEvent(Object x0) {
         this();
      }
   }
}
