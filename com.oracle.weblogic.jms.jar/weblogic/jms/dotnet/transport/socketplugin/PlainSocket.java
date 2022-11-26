package weblogic.jms.dotnet.transport.socketplugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import weblogic.jms.dotnet.transport.MarshalReadableFactory;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.dotnet.transport.TransportFactory;
import weblogic.jms.dotnet.transport.TransportPluginSPI;
import weblogic.jms.dotnet.transport.TransportThreadPool;

public class PlainSocket implements Runnable, TransportPluginSPI {
   static final int HEADER_SIZE = 32;
   static final int HEADER_SIZE_POS = 0;
   private InputStream is;
   private OutputStream os;
   private boolean started;
   private final Stats stats;
   private final Transport transport;
   private final MarshalReadableFactory marshalReadableFactory;
   private final TransportThreadPool pool;
   private Socket workerSocket;

   PlainSocket(Stats stats, MarshalReadableFactory mrf, TransportThreadPool pool) throws Exception {
      this.stats = stats;
      this.marshalReadableFactory = mrf;
      this.pool = pool;
      this.transport = TransportFactory.createTransport(this, pool);
      this.transport.addMarshalReadableFactory(mrf);
   }

   public String toString() {
      return "PlainSocket";
   }

   public static Transport startClient(String host, int port, MarshalReadableFactory mrf, TransportThreadPool pool) throws Exception {
      PlainSocket ps = new PlainSocket(new Stats("Client"), mrf, pool);
      ps.start(new Socket(host, port));
      return ps.transport;
   }

   public static void startServer(int port, TransportThreadPool pool) throws Exception {
      PlainSocketServer pss = new PlainSocketServer(port, (MarshalReadableFactory)null, pool);
      Thread t = new Thread(pss);
      t.setDaemon(true);
      t.start();
   }

   public MarshalWriter createMarshalWriter() {
      try {
         ChunkOutputStream bdos = new ChunkOutputStream(this.transport);
         bdos.reposition(32);
         return bdos;
      } catch (Exception var2) {
         throw new AssertionError(var2);
      }
   }

   void start(Socket socket) throws Exception {
      if (this.started) {
         throw new AssertionError();
      } else {
         this.workerSocket = socket;
         this.started = true;
         this.is = socket.getInputStream();
         this.os = socket.getOutputStream();
         Thread t = new Thread(this);
         t.setDaemon(true);
         t.start();
      }
   }

   public void run() {
      try {
         while(true) {
            ChunkInputStream bdis = this.recv();
            bdis.skip((long)(32 - bdis.getPosition()));

            try {
               this.transport.dispatch(bdis);
            } catch (Throwable var3) {
               var3.printStackTrace();
            }
         }
      } catch (Throwable var4) {
         this.transport.shutdown(new TransportError(var4));
      }
   }

   public synchronized void send(MarshalWriter mw) {
      ChunkOutputStream cdos = (ChunkOutputStream)mw;
      int sz = cdos.size();
      cdos.reposition(0);
      cdos.writeInt(sz);
      cdos.reposition(sz);

      try {
         this.os.write(cdos.getBuf(), 0, cdos.size());
         this.os.flush();
         this.stats.incSend((long)sz);
      } catch (Throwable var5) {
         this.transport.shutdown(new TransportError(var5));
      }

      cdos.internalClose();
   }

   private ChunkInputStream recv() throws Exception {
      ChunkInputStream bdis = new ChunkInputStream(this.transport);
      byte[] inbuf = bdis.getBuf();

      int pos;
      int numBytes;
      for(pos = 0; 4 - pos > 0; pos += numBytes) {
         numBytes = this.is.read(inbuf, pos, 4 - pos);
      }

      int len;
      for(numBytes = bdis.readInt() - 4; numBytes - pos + 4 > 0; pos += len) {
         len = this.is.read(inbuf, pos, numBytes - pos + 4);
      }

      this.stats.incRecv((long)numBytes);
      bdis.setCount(numBytes + 4);
      return bdis;
   }

   public long getScratchID() {
      return -1L;
   }

   public void terminateConnection() {
      try {
         if (this.workerSocket != null) {
            this.workerSocket.close();
         }
      } catch (IOException var2) {
      }

   }
}
