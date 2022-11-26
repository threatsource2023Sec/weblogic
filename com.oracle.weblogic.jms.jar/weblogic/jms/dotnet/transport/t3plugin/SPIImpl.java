package weblogic.jms.dotnet.transport.t3plugin;

import weblogic.jms.dotnet.t3.server.spi.T3Connection;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.dotnet.transport.TransportPluginSPI;

class SPIImpl implements TransportPluginSPI {
   private final T3ConnectionHandleImpl chi;
   private final T3Connection t3Conn;
   private static final long HALFLONG = 4611686018427387903L;

   SPIImpl(T3ConnectionHandleImpl chi, T3Connection t3Conn) {
      this.t3Conn = t3Conn;
      this.chi = chi;
   }

   public MarshalWriter createMarshalWriter() {
      try {
         return new MarshalWriterImpl(this.chi.getTransport(), this.t3Conn.getRequestStream());
      } catch (Throwable var2) {
         return new MarshalWriterImpl(this.chi.getTransport(), var2);
      }
   }

   public void send(MarshalWriter mw) {
      Throwable t = null;
      MarshalWriterImpl mwi = (MarshalWriterImpl)mw;
      t = mwi.getThrowable();
      if (t == null) {
         try {
            this.t3Conn.send(mwi.getChunkedDataOutputStream());
            return;
         } catch (Throwable var5) {
            t = var5;
         }
      } else {
         mwi.closeInternal();
      }

      this.chi.shutdown(new TransportError(t));
   }

   public long getScratchID() {
      long diff = this.t3Conn.getRJVMId().getDifferentiator();
      long sCode = diff / 11L * 6L & 4699868874176267647L & 4611686018427387903L;
      return sCode;
   }

   public void terminateConnection() {
      this.t3Conn.shutdown();
   }
}
