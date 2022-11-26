package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.asyncqueue.AsyncWriteQueueRecord;
import org.glassfish.grizzly.asyncqueue.RecordWriteResult;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.nio.AbstractNIOAsyncQueueWriter;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;

public final class UDPNIOAsyncQueueWriter extends AbstractNIOAsyncQueueWriter {
   public UDPNIOAsyncQueueWriter(NIOTransport transport) {
      super(transport);
   }

   protected RecordWriteResult write0(NIOConnection connection, AsyncWriteQueueRecord queueRecord) throws IOException {
      RecordWriteResult writeResult = queueRecord.getCurrentResult();
      if (queueRecord.remaining() == 0L) {
         return writeResult.lastWriteResult(0L, queueRecord.isUncountable() ? 1L : 0L);
      } else {
         WritableMessage outputMessage = (WritableMessage)queueRecord.getMessage();
         SocketAddress dstAddress = (SocketAddress)queueRecord.getDstAddress();
         long written = ((UDPNIOTransport)this.transport).write((UDPNIOConnection)connection, dstAddress, outputMessage, writeResult);
         return writeResult.lastWriteResult(written, written);
      }
   }

   protected final void onReadyToWrite(NIOConnection connection) throws IOException {
      connection.enableIOEvent(IOEvent.WRITE);
   }
}
