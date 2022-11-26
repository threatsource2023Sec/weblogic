package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.asyncqueue.AsyncReadQueueRecord;
import org.glassfish.grizzly.nio.AbstractNIOAsyncQueueReader;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;

public final class TCPNIOAsyncQueueReader extends AbstractNIOAsyncQueueReader {
   public TCPNIOAsyncQueueReader(NIOTransport transport) {
      super(transport);
   }

   protected int read0(Connection connection, Buffer buffer, ReadResult currentResult) throws IOException {
      int oldPosition = buffer != null ? buffer.position() : 0;
      if ((buffer = ((TCPNIOTransport)this.transport).read(connection, buffer)) != null) {
         int readBytes = buffer.position() - oldPosition;
         currentResult.setMessage(buffer);
         currentResult.setReadSize(currentResult.getReadSize() + readBytes);
         currentResult.setSrcAddressHolder(((TCPNIOConnection)connection).peerSocketAddressHolder);
         return readBytes;
      } else {
         return 0;
      }
   }

   protected void addRecord(Connection connection, Buffer buffer, CompletionHandler completionHandler, Interceptor interceptor) {
      AsyncReadQueueRecord record = AsyncReadQueueRecord.create(connection, buffer, completionHandler, interceptor);
      ((TCPNIOConnection)connection).getAsyncReadQueue().offer(record);
   }

   protected final void onReadyToRead(Connection connection) throws IOException {
      NIOConnection nioConnection = (NIOConnection)connection;
      nioConnection.enableIOEvent(IOEvent.READ);
   }
}
