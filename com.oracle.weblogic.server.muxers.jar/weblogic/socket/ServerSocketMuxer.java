package weblogic.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import weblogic.socket.utils.SDPSocketUtils;

public abstract class ServerSocketMuxer extends SocketMuxer {
   protected ServerSocketMuxer() throws IOException {
   }

   public static ServerSocketMuxer getMuxer() {
      return (ServerSocketMuxer)SocketMuxer.SingletonMaker.singleton;
   }

   public ServerSocket newServerSocket(InetAddress listenAddress, int port, int acceptBacklog) throws IOException {
      ServerSocket ss = null;
      ss = ServerSocketChannel.open().socket();
      ss.getChannel().configureBlocking(true);
      if (listenAddress == null) {
         ss.bind(new InetSocketAddress(port), acceptBacklog);
      } else {
         ss.bind(new InetSocketAddress(listenAddress, port), acceptBacklog);
      }

      return new WeblogicServerSocket(ss);
   }

   public ServerSocket newSDPServerSocket(InetAddress listenAddress, int port, int acceptBacklog) throws IOException {
      ServerSocket ss = SDPSocketUtils.createSDPServerSocket();
      if (listenAddress == null) {
         ss.bind(new InetSocketAddress(port), acceptBacklog);
      } else {
         ss.bind(new InetSocketAddress(listenAddress, port), acceptBacklog);
      }

      return new WeblogicServerSocket(ss);
   }
}
