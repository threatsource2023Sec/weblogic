package weblogic.nodemanager.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Level;
import weblogic.nodemanager.NodeManagerTextTextFormatter;

class Listener {
   protected NMServer server;
   protected InetAddress host;
   protected int port;
   protected int backlog;
   protected ServerSocket serverSocket;
   protected Channel inheritedChannel;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   Listener(NMServer server, Channel channel) throws IOException {
      this.server = server;
      NMServerConfig config = server.getConfig();
      String name = config.getListenAddress();
      if (name != null) {
         this.host = InetAddress.getByName(name);
      }

      this.port = config.getListenPort();
      this.backlog = config.getListenBacklog();
      this.inheritedChannel = channel;
      if (channel != null) {
         this.inheritedChannel = channel;

         assert this.inheritedChannel instanceof ServerSocketChannel;

         ServerSocketChannel sc = (ServerSocketChannel)this.inheritedChannel;
         this.serverSocket = sc.socket();
         NMServer.nmLog.info(nmText.getInheritedSocket(this.serverSocket.toString()));
      }

   }

   public void init() throws IOException {
      if (this.inheritedChannel == null) {
         if (this.host != null) {
            this.serverSocket = new ServerSocket(this.port, this.backlog, this.host);
         } else {
            this.serverSocket = new ServerSocket(this.port, this.backlog);
         }
      }

   }

   public void run() throws IOException {
      NMServer.nmLog.info(this.host != null ? nmText.getPlainListenerStartedHost(Integer.toString(this.port), this.host.toString()) : nmText.getPlainListenerStarted(Integer.toString(this.port)));

      while(true) {
         while(true) {
            try {
               Socket s = this.serverSocket.accept();
               if (s == null) {
                  NMServer.nmLog.log(Level.ALL, "ServerSocket: " + this.serverSocket + " returned null from accept!");
               } else {
                  NMServer.nmLog.log(Level.ALL, "Accepted connection from " + s.getRemoteSocketAddress());
                  Handler handler = new Handler(this.server, s);
                  Thread t = new Thread(handler);
                  t.start();
               }
            } catch (IOException var4) {
               NMServer.nmLog.warning(nmText.getFailedConnection(Integer.toString(this.port), this.host.toString()) + var4);
            }
         }
      }
   }
}
