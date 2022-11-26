package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorReader;

public final class UDPNIOTemporarySelectorReader extends TemporarySelectorReader {
   public UDPNIOTemporarySelectorReader(UDPNIOTransport transport) {
      super(transport);
   }

   protected int readNow0(NIOConnection connection, Buffer buffer, ReadResult currentResult) throws IOException {
      return ((UDPNIOTransport)this.transport).read((UDPNIOConnection)connection, buffer, currentResult);
   }
}
