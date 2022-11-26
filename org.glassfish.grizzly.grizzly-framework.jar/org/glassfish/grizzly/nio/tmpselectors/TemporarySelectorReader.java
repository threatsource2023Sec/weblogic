package org.glassfish.grizzly.nio.tmpselectors;

import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.grizzly.AbstractReader;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.nio.NIOConnection;

public abstract class TemporarySelectorReader extends AbstractReader {
   private static final int DEFAULT_BUFFER_SIZE = 8192;
   protected final TemporarySelectorsEnabledTransport transport;

   public TemporarySelectorReader(TemporarySelectorsEnabledTransport transport) {
      this.transport = transport;
   }

   public void read(Connection connection, Buffer message, CompletionHandler completionHandler, Interceptor interceptor) {
      this.read(connection, message, completionHandler, interceptor, connection.getReadTimeout(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
   }

   public void read(Connection connection, Buffer message, CompletionHandler completionHandler, Interceptor interceptor, long timeout, TimeUnit timeunit) {
      if (connection != null && connection instanceof NIOConnection) {
         NIOConnection nioConnection = (NIOConnection)connection;
         ReadResult currentResult = ReadResult.create(connection, message, (Object)null, 0);

         try {
            int readBytes = this.read0(nioConnection, interceptor, currentResult, message, timeout, timeunit);
            if (readBytes > 0) {
               if (completionHandler != null) {
                  completionHandler.completed(currentResult);
               }

               if (interceptor != null) {
                  interceptor.intercept(2, connection, currentResult);
               }
            } else {
               failure(new TimeoutException(), completionHandler);
            }
         } catch (IOException var11) {
            failure(var11, completionHandler);
         }

      } else {
         failure(new IllegalStateException("Connection should be NIOConnection and cannot be null"), completionHandler);
      }
   }

   private int read0(NIOConnection connection, Interceptor interceptor, ReadResult currentResult, Buffer buffer, long timeout, TimeUnit timeunit) throws IOException {
      boolean isCompleted = false;

      while(!isCompleted) {
         isCompleted = true;
         int readBytes = this.read0(connection, currentResult, buffer, timeout, timeunit);
         if (readBytes <= 0) {
            return -1;
         }

         if (interceptor != null) {
            isCompleted = (interceptor.intercept(1, (Object)null, currentResult) & 1) != 0;
         }
      }

      return currentResult.getReadSize();
   }

   protected final int read0(NIOConnection connection, ReadResult currentResult, Buffer buffer, long timeout, TimeUnit timeunit) throws IOException {
      Selector readSelector = null;
      SelectionKey key = null;
      SelectableChannel channel = connection.getChannel();
      long readTimeout = TimeUnit.MILLISECONDS.convert(timeout < 0L ? 0L : timeout, timeunit);

      try {
         int bytesRead = this.readNow0(connection, buffer, currentResult);
         if (bytesRead == 0) {
            readSelector = this.transport.getTemporarySelectorIO().getSelectorPool().poll();
            int code;
            if (readSelector == null) {
               code = bytesRead;
               return code;
            }

            key = channel.register(readSelector, 1);
            key.interestOps(key.interestOps() | 1);
            code = readSelector.select(readTimeout);
            key.interestOps(key.interestOps() & -2);
            if (code == 0) {
               int var14 = bytesRead;
               return var14;
            }

            bytesRead = this.readNow0(connection, buffer, currentResult);
         }

         if (bytesRead != -1) {
            return bytesRead;
         } else {
            throw new EOFException();
         }
      } finally {
         this.transport.getTemporarySelectorIO().recycleTemporaryArtifacts(readSelector, key);
      }
   }

   protected abstract int readNow0(NIOConnection var1, Buffer var2, ReadResult var3) throws IOException;

   protected Buffer acquireBuffer(Connection connection) {
      Transport connectionTransport = connection.getTransport();
      return connectionTransport.getMemoryManager().allocate(8192);
   }

   public TemporarySelectorsEnabledTransport getTransport() {
      return this.transport;
   }

   private static void failure(Throwable failure, CompletionHandler completionHandler) {
      if (completionHandler != null) {
         completionHandler.failed(failure);
      }

   }
}
