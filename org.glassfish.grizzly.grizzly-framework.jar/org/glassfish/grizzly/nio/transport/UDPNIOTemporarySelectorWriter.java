package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorWriter;

public final class UDPNIOTemporarySelectorWriter extends TemporarySelectorWriter {
   public UDPNIOTemporarySelectorWriter(UDPNIOTransport transport) {
      super(transport);
   }

   protected long writeNow0(NIOConnection connection, SocketAddress dstAddress, WritableMessage message, WriteResult currentResult) throws IOException {
      return ((UDPNIOTransport)this.transport).write((UDPNIOConnection)connection, dstAddress, message, currentResult);
   }
}
