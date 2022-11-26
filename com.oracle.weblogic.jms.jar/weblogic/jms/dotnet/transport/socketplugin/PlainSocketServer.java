package weblogic.jms.dotnet.transport.socketplugin;

import java.net.ServerSocket;
import java.net.Socket;
import weblogic.jms.dotnet.transport.MarshalReadableFactory;
import weblogic.jms.dotnet.transport.TransportThreadPool;

class PlainSocketServer implements Runnable {
   private final int port;
   private final ServerSocket serverSocket;
   private final MarshalReadableFactory marshalReadableFactory;
   private final Stats stats = new Stats("Server");
   private final TransportThreadPool pool;

   PlainSocketServer(int port, MarshalReadableFactory mrf, TransportThreadPool pool) throws Exception {
      this.port = port;
      this.marshalReadableFactory = mrf;
      this.pool = pool;
      this.serverSocket = new ServerSocket(port);
   }

   public void run() {
      try {
         while(true) {
            Socket socket = this.serverSocket.accept();
            PlainSocket nc = new PlainSocket(this.stats, this.marshalReadableFactory, this.pool);
            nc.start(socket);
         }
      } catch (Throwable var3) {
         var3.printStackTrace();
      }
   }
}
