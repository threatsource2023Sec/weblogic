package weblogic.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public final class WeblogicServerSocket extends ServerSocket {
   private final ServerSocket serverSocket;

   public WeblogicServerSocket(ServerSocket serverSocket) throws IOException {
      this.serverSocket = serverSocket;
   }

   public Socket accept() throws IOException {
      SocketChannel sc = this.serverSocket.getChannel().accept();
      if (sc == null) {
         return null;
      } else {
         sc.configureBlocking(false);
         return SocketMuxer.getMuxer().newWeblogicSocket(sc.socket());
      }
   }

   public void bind(SocketAddress endpoint) throws IOException {
      this.serverSocket.bind(endpoint);
   }

   public void bind(SocketAddress endpoint, int backlog) throws IOException {
      this.serverSocket.bind(endpoint, backlog);
   }

   public InetAddress getInetAddress() {
      return this.serverSocket.getInetAddress();
   }

   public int getLocalPort() {
      return this.serverSocket.getLocalPort();
   }

   public SocketAddress getLocalSocketAddress() {
      return this.serverSocket.getLocalSocketAddress();
   }

   public void close() throws IOException {
      this.serverSocket.close();
   }

   public ServerSocketChannel getChannel() {
      return this.serverSocket.getChannel();
   }

   public boolean isBound() {
      return this.serverSocket.isBound();
   }

   public boolean isClosed() {
      return this.serverSocket.isClosed();
   }

   public void setSoTimeout(int timeout) throws SocketException {
      this.serverSocket.setSoTimeout(timeout);
   }

   public int getSoTimeout() throws IOException {
      return this.serverSocket.getSoTimeout();
   }

   public void setReuseAddress(boolean on) throws SocketException {
      this.serverSocket.setReuseAddress(on);
   }

   public boolean getReuseAddress() throws SocketException {
      return this.serverSocket.getReuseAddress();
   }

   public String toString() {
      return this.serverSocket.toString();
   }

   public void setReceiveBufferSize(int size) throws SocketException {
      this.serverSocket.setReceiveBufferSize(size);
   }

   public int getReceiveBufferSize() throws SocketException {
      return this.serverSocket.getReceiveBufferSize();
   }
}
