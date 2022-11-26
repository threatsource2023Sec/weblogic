package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

abstract class JaAbstractLayeredSSLSocket extends JaAbstractSSLSocket {
   private final Socket socket;
   private final boolean autoClose;

   public void connect(SocketAddress endpoint) throws IOException {
      this.socket.connect(endpoint);
   }

   public void connect(SocketAddress endpoint, int timeout) throws IOException {
      this.socket.connect(endpoint, timeout);
   }

   public void bind(SocketAddress bindpoint) throws IOException {
      this.socket.bind(bindpoint);
   }

   public InetAddress getInetAddress() {
      return this.socket.getInetAddress();
   }

   public InetAddress getLocalAddress() {
      return this.socket.getLocalAddress();
   }

   public int getPort() {
      return this.socket.getPort();
   }

   public int getLocalPort() {
      return this.socket.getLocalPort();
   }

   public SocketAddress getRemoteSocketAddress() {
      return this.socket.getRemoteSocketAddress();
   }

   public SocketAddress getLocalSocketAddress() {
      return this.socket.getLocalSocketAddress();
   }

   public SocketChannel getChannel() {
      return this.socket.getChannel();
   }

   public void setTcpNoDelay(boolean on) throws SocketException {
      this.socket.setTcpNoDelay(on);
   }

   public boolean getTcpNoDelay() throws SocketException {
      return this.socket.getTcpNoDelay();
   }

   public void setSoLinger(boolean on, int linger) throws SocketException {
      this.socket.setSoLinger(on, linger);
   }

   public int getSoLinger() throws SocketException {
      return this.socket.getSoLinger();
   }

   public void sendUrgentData(int data) throws IOException {
      this.socket.sendUrgentData(data);
   }

   public void setOOBInline(boolean on) throws SocketException {
      this.socket.setOOBInline(on);
   }

   public boolean getOOBInline() throws SocketException {
      return this.socket.getOOBInline();
   }

   public void setSoTimeout(int timeout) throws SocketException {
      this.socket.setSoTimeout(timeout);
   }

   public int getSoTimeout() throws SocketException {
      return this.socket.getSoTimeout();
   }

   public void setSendBufferSize(int size) throws SocketException {
      this.socket.setSendBufferSize(size);
   }

   public int getSendBufferSize() throws SocketException {
      return this.socket.getSendBufferSize();
   }

   public void setReceiveBufferSize(int size) throws SocketException {
      this.socket.setReceiveBufferSize(size);
   }

   public int getReceiveBufferSize() throws SocketException {
      return this.socket.getReceiveBufferSize();
   }

   public void setKeepAlive(boolean on) throws SocketException {
      this.socket.setKeepAlive(on);
   }

   public boolean getKeepAlive() throws SocketException {
      return this.socket.getKeepAlive();
   }

   public void setTrafficClass(int tc) throws SocketException {
      this.socket.setTrafficClass(tc);
   }

   public int getTrafficClass() throws SocketException {
      return this.socket.getTrafficClass();
   }

   public void setReuseAddress(boolean on) throws SocketException {
      this.socket.setReuseAddress(on);
   }

   public boolean getReuseAddress() throws SocketException {
      return this.socket.getReuseAddress();
   }

   public void close() throws IOException {
      try {
         super.close();
      } catch (Exception var2) {
      }

      if (this.autoClose) {
         this.socket.close();
      }

   }

   public void shutdownInput() throws IOException {
      try {
         super.shutdownInput();
      } catch (Exception var2) {
      }

      this.socket.shutdownInput();
   }

   public void shutdownOutput() throws IOException {
      try {
         super.shutdownOutput();
      } catch (Exception var2) {
      }

      this.socket.shutdownOutput();
   }

   public boolean isConnected() {
      return this.socket.isConnected();
   }

   public boolean isBound() {
      return this.socket.isBound();
   }

   public boolean isClosed() {
      return this.socket.isClosed();
   }

   public boolean isInputShutdown() {
      return this.socket.isInputShutdown();
   }

   public boolean isOutputShutdown() {
      return this.socket.isOutputShutdown();
   }

   public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
      this.socket.setPerformancePreferences(connectionTime, latency, bandwidth);
   }

   public int hashCode() {
      return this.socket.hashCode();
   }

   public boolean equals(Object obj) {
      return this.socket.equals(obj);
   }

   public String toString() {
      return this.socket.toString();
   }

   JaAbstractLayeredSSLSocket(Socket socket, JaSSLContext jaSSLContext, boolean autoClose) throws IOException {
      super(jaSSLContext);
      if (null == socket) {
         throw new IllegalArgumentException("Expected non-null Socket.");
      } else {
         this.socket = socket;
         this.autoClose = autoClose;
      }
   }
}
