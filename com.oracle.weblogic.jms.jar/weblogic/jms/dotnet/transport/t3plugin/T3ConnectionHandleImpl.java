package weblogic.jms.dotnet.transport.t3plugin;

import java.io.IOException;
import weblogic.jms.dotnet.t3.server.spi.T3Connection;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionGoneEvent;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandle;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.dotnet.transport.TransportFactory;
import weblogic.utils.io.ChunkedDataInputStream;

public class T3ConnectionHandleImpl implements T3ConnectionHandle {
   private final Object lock = new Object();
   private final T3Connection client;
   private final Transport transport;
   private boolean isClosed;

   T3ConnectionHandleImpl(T3Connection client) {
      this.client = client;
      SPIImpl spi = new SPIImpl(this, client);
      ThreadPoolImpl tpi = new ThreadPoolImpl();
      this.transport = TransportFactory.createTransport(spi, tpi);
   }

   Transport getTransport() {
      return this.transport;
   }

   public void onPeerGone(T3ConnectionGoneEvent event) {
      IOException ioe = event.getReason();
      if (ioe == null) {
         ioe = new IOException("unknown");
      }

      synchronized(this.lock) {
         if (this.isClosed) {
            return;
         }

         this.isClosed = true;
      }

      this.transport.shutdown(new TransportError(ioe));
   }

   public void onMessage(ChunkedDataInputStream input) {
      MarshalReaderImpl mri = new MarshalReaderImpl(this.transport, input);
      this.transport.dispatch(mri);
   }

   void shutdown(TransportError te) {
      this.transport.shutdown(te);
      this.client.shutdown();
   }
}
