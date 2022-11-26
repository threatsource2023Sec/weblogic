package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorWriter;

public final class TCPNIOTemporarySelectorWriter extends TemporarySelectorWriter {
   public TCPNIOTemporarySelectorWriter(TCPNIOTransport transport) {
      super(transport);
   }

   protected long writeNow0(NIOConnection connection, SocketAddress dstAddress, WritableMessage message, WriteResult currentResult) throws IOException {
      return (long)((TCPNIOTransport)this.transport).write((TCPNIOConnection)connection, message, currentResult);
   }
}
