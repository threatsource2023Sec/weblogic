package weblogic.jms.dotnet.transport.t3client;

import weblogic.jms.dotnet.transport.MarshalReadableFactory;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.dotnet.transport.TransportFactory;
import weblogic.jms.dotnet.transport.TransportPluginSPI;
import weblogic.jms.dotnet.transport.TransportThreadPool;

public class TransportSPIImpl implements Runnable, TransportPluginSPI {
   private final T3Connection t3conn;
   private Transport transport;
   private static final long HALFLONG = 4611686018427387903L;

   private TransportSPIImpl(T3Connection t3conn) {
      this.t3conn = t3conn;
   }

   public static Transport startClient(String host, int port, MarshalReadableFactory mrf, TransportThreadPool pool) throws Exception {
      T3Connection t3conn = new T3Connection(host, port, (T3PeerInfo)null, (byte)1);
      TransportSPIImpl impl = new TransportSPIImpl(t3conn);
      Transport transport = TransportFactory.createTransport(impl, pool);
      transport.addMarshalReadableFactory(mrf);
      synchronized(impl) {
         impl.transport = transport;
      }

      Thread t = new Thread(impl);
      t.setDaemon(true);
      t.start();
      long serviceId = transport.allocateServiceID();
      transport.registerService(serviceId, new ShutdownListener(t3conn));
      return transport;
   }

   public MarshalWriter createMarshalWriter() {
      MarshalWriterImpl mwi = new MarshalWriterImpl(this.transport);
      T3Connection var10000 = this.t3conn;
      return T3Connection.createOneWay(mwi);
   }

   public synchronized void send(MarshalWriter mw) {
      MarshalWriterImpl mwi = (MarshalWriterImpl)mw;
      Throwable t = null;

      try {
         this.t3conn.sendOneWay(mwi);
      } catch (Error var5) {
         t = var5;
      } catch (RuntimeException var6) {
         t = var6;
      } catch (Exception var7) {
         t = var7;
      }

      if (t != null) {
         this.transport.shutdown(new TransportError((Throwable)t));
      }

   }

   public void run() {
      try {
         while(true) {
            MarshalReaderImpl mri = this.t3conn.receiveOneWay(this.transport);

            try {
               this.transport.dispatch(mri);
            } catch (Throwable var3) {
            }
         }
      } catch (Throwable var4) {
         this.transport.shutdown(new TransportError(var4));
      }
   }

   public long getScratchID() {
      long diff = this.t3conn.getScratchID();
      long sCode = diff / 11L * 6L & 4699868874176267647L & 4611686018427387903L;
      return sCode;
   }

   public void terminateConnection() {
      this.t3conn.close();
   }
}
