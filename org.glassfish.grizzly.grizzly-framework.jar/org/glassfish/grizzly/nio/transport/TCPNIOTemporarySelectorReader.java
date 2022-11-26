package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorReader;

public final class TCPNIOTemporarySelectorReader extends TemporarySelectorReader {
   public TCPNIOTemporarySelectorReader(TCPNIOTransport transport) {
      super(transport);
   }

   protected final int readNow0(NIOConnection connection, Buffer buffer, ReadResult currentResult) throws IOException {
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
}
