package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.memory.Buffers;

public final class TCPNIOTransportFilter extends BaseFilter {
   private final TCPNIOTransport transport;

   TCPNIOTransportFilter(TCPNIOTransport transport) {
      this.transport = transport;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      TCPNIOConnection connection = (TCPNIOConnection)ctx.getConnection();
      boolean isBlocking = ctx.getTransportContext().isBlocking();
      Buffer inBuffer = (Buffer)ctx.getMessage();
      Buffer buffer;
      if (!isBlocking) {
         buffer = this.transport.read(connection, inBuffer);
      } else {
         GrizzlyFuture future = this.transport.getTemporarySelectorIO().getReader().read(connection, inBuffer);

         try {
            ReadResult result = (ReadResult)future.get();
            buffer = (Buffer)result.getMessage();
            future.recycle(true);
         } catch (ExecutionException var9) {
            Throwable cause = var9.getCause();
            if (cause instanceof IOException) {
               throw (IOException)cause;
            }

            throw new IOException(cause);
         } catch (InterruptedException var10) {
            throw new IOException(var10);
         }
      }

      if (buffer != null && buffer.position() != 0) {
         buffer.trim();
         ctx.setMessage(buffer);
         ctx.setAddressHolder(connection.peerSocketAddressHolder);
         return ctx.getInvokeAction();
      } else {
         return ctx.getStopAction();
      }
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      WritableMessage message = (WritableMessage)ctx.getMessage();
      if (message != null) {
         ctx.setMessage((Object)null);
         Connection connection = ctx.getConnection();
         FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
         CompletionHandler completionHandler = transportContext.getCompletionHandler();
         MessageCloner cloner = transportContext.getMessageCloner();
         transportContext.setCompletionHandler((CompletionHandler)null);
         transportContext.setMessageCloner((MessageCloner)null);
         if (!transportContext.isBlocking()) {
            this.transport.getAsyncQueueIO().getWriter().write(connection, (Object)null, message, completionHandler, cloner);
         } else {
            this.transport.getTemporarySelectorIO().getWriter().write(connection, (Object)null, message, completionHandler);
         }
      }

      return ctx.getInvokeAction();
   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      if (event.type() == TransportFilter.FlushEvent.TYPE) {
         Connection connection = ctx.getConnection();
         FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
         if (transportContext.getCompletionHandler() != null) {
            throw new IllegalStateException("TransportContext CompletionHandler must be null");
         }

         CompletionHandler completionHandler = ((TransportFilter.FlushEvent)event).getCompletionHandler();
         this.transport.getWriter(transportContext.isBlocking()).write(connection, (WritableMessage)Buffers.EMPTY_BUFFER, (CompletionHandler)completionHandler);
      }

      return ctx.getInvokeAction();
   }

   public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
      Connection connection = ctx.getConnection();
      if (connection != null) {
         connection.closeSilently();
      }

   }
}
