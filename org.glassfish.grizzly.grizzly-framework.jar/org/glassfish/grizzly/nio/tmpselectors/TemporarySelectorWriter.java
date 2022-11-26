package org.glassfish.grizzly.nio.tmpselectors;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.AbstractWriter;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.nio.NIOConnection;

public abstract class TemporarySelectorWriter extends AbstractWriter {
   protected final TemporarySelectorsEnabledTransport transport;

   public TemporarySelectorWriter(TemporarySelectorsEnabledTransport transport) {
      this.transport = transport;
   }

   public void write(Connection connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler completionHandler, MessageCloner messageCloner) {
      this.write(connection, dstAddress, message, completionHandler, (PushBackHandler)null, connection.getWriteTimeout(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
   }

   /** @deprecated */
   @Deprecated
   public void write(Connection connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
      this.write(connection, dstAddress, message, completionHandler, pushBackHandler, connection.getWriteTimeout(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
   }

   public void write(Connection connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler completionHandler, long timeout, TimeUnit timeunit) {
      this.write(connection, dstAddress, message, completionHandler, (PushBackHandler)null, timeout, timeunit);
   }

   public void write(Connection connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler completionHandler, PushBackHandler pushBackHandler, long timeout, TimeUnit timeunit) {
      if (message == null) {
         failure(new IllegalStateException("Message cannot be null"), completionHandler);
      } else if (connection != null && connection instanceof NIOConnection) {
         NIOConnection nioConnection = (NIOConnection)connection;
         WriteResult writeResult = WriteResult.create(connection, message, dstAddress, 0L);

         try {
            this.write0(nioConnection, dstAddress, message, writeResult, timeout, timeunit);
            if (pushBackHandler != null) {
               pushBackHandler.onAccept(connection, message);
            }

            if (completionHandler != null) {
               completionHandler.completed(writeResult);
            }

            message.release();
         } catch (IOException var12) {
            failure(var12, completionHandler);
         }

      } else {
         failure(new IllegalStateException("Connection should be NIOConnection and cannot be null"), completionHandler);
      }
   }

   protected long write0(NIOConnection connection, SocketAddress dstAddress, WritableMessage message, WriteResult currentResult, long timeout, TimeUnit timeunit) throws IOException {
      SelectableChannel channel = connection.getChannel();
      long writeTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
      SelectionKey key = null;
      Selector writeSelector = null;
      int attempts = 0;
      int bytesWritten = 0;

      try {
         synchronized(connection) {
            while(message.hasRemaining()) {
               long len = this.writeNow0(connection, dstAddress, message, currentResult);
               if (len > 0L) {
                  attempts = 0;
                  bytesWritten = (int)((long)bytesWritten + len);
               } else {
                  ++attempts;
                  if (writeSelector == null) {
                     writeSelector = this.transport.getTemporarySelectorIO().getSelectorPool().poll();
                     if (writeSelector == null) {
                        continue;
                     }

                     key = channel.register(writeSelector, 4);
                  } else {
                     writeSelector.selectedKeys().clear();
                  }

                  if (writeSelector.select(writeTimeout) == 0 && attempts > 2) {
                     throw new IOException("Client disconnected");
                  }
               }
            }

            return (long)bytesWritten;
         }
      } finally {
         this.transport.getTemporarySelectorIO().recycleTemporaryArtifacts(writeSelector, key);
      }
   }

   public TemporarySelectorsEnabledTransport getTransport() {
      return this.transport;
   }

   protected abstract long writeNow0(NIOConnection var1, SocketAddress var2, WritableMessage var3, WriteResult var4) throws IOException;

   private static void failure(Throwable failure, CompletionHandler completionHandler) {
      if (completionHandler != null) {
         completionHandler.failed(failure);
      }

   }

   public boolean canWrite(Connection connection) {
      return true;
   }

   public void notifyWritePossible(Connection connection, WriteHandler writeHandler) {
      try {
         writeHandler.onWritePossible();
      } catch (Throwable var4) {
         writeHandler.onError(var4);
      }

   }
}
