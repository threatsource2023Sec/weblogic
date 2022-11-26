package weblogic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public abstract class WeblogicSSLSocket extends SSLSocket {
   private final Socket socket;
   private boolean autoClose = true;

   public WeblogicSSLSocket(Socket s) {
      this.socket = s;
   }

   public void setAutoClose(boolean close) {
      this.autoClose = close;
   }

   public final InetAddress getInetAddress() {
      return this.socket.getInetAddress();
   }

   public final InetAddress getLocalAddress() {
      return this.socket.getLocalAddress();
   }

   public final int getPort() {
      return this.socket.getPort();
   }

   public final int getLocalPort() {
      return this.socket.getLocalPort();
   }

   public InputStream getInputStream() throws IOException {
      return this.socket.getInputStream();
   }

   public OutputStream getOutputStream() throws IOException {
      return this.socket.getOutputStream();
   }

   public final void setTcpNoDelay(boolean on) throws SocketException {
      this.socket.setTcpNoDelay(on);
   }

   public final boolean getTcpNoDelay() throws SocketException {
      return this.socket.getTcpNoDelay();
   }

   public final void setSoLinger(boolean on, int linger) throws SocketException {
      this.socket.setSoLinger(on, linger);
   }

   public final int getSoLinger() throws SocketException {
      return this.socket.getSoLinger();
   }

   public final void setSoTimeout(int timeout) throws SocketException {
      this.socket.setSoTimeout(timeout);
   }

   public final int getSoTimeout() throws SocketException {
      return this.socket.getSoTimeout();
   }

   public void close() throws IOException {
      super.close();
      if (this.autoClose) {
         this.socket.close();
      }

   }

   public final Socket getSocket() {
      return this.socket;
   }

   public final String toString() {
      return this.socket.toString();
   }

   public final void setSendBufferSize(int size) throws SocketException {
      this.socket.setSendBufferSize(size);
   }

   public final int getSendBufferSize() throws SocketException {
      return this.socket.getSendBufferSize();
   }

   public final void setReceiveBufferSize(int size) throws SocketException {
      this.socket.setReceiveBufferSize(size);
   }

   public final int getReceiveBufferSize() throws SocketException {
      return this.socket.getReceiveBufferSize();
   }

   public final void setKeepAlive(boolean keepAlive) throws SocketException {
      this.socket.setKeepAlive(keepAlive);
   }

   public final boolean getKeepAlive() throws SocketException {
      return this.socket.getKeepAlive();
   }

   public final void shutdownInput() throws IOException {
      this.socket.shutdownInput();
   }

   public final void shutdownOutput() throws IOException {
      this.socket.shutdownOutput();
   }

   public final void connect(SocketAddress endpoint) throws IOException {
      this.socket.connect(endpoint);
   }

   public final void connect(SocketAddress endpoint, int timeout) throws IOException {
      this.socket.connect(endpoint, timeout);
   }

   public final void bind(SocketAddress bindpoint) throws IOException {
      this.socket.bind(bindpoint);
   }

   public final SocketAddress getRemoteSocketAddress() {
      return this.socket.getRemoteSocketAddress();
   }

   public final SocketAddress getLocalSocketAddress() {
      return this.socket.getLocalSocketAddress();
   }

   public final SocketChannel getChannel() {
      return this.socket.getChannel();
   }

   public final void sendUrgentData(int data) throws IOException {
      this.socket.sendUrgentData(data);
   }

   public final void setOOBInline(boolean on) throws SocketException {
      this.socket.setOOBInline(on);
   }

   public final boolean getOOBInline() throws SocketException {
      return this.socket.getOOBInline();
   }

   public final void setTrafficClass(int tc) throws SocketException {
      this.socket.setTrafficClass(tc);
   }

   public final int getTrafficClass() throws SocketException {
      return this.socket.getTrafficClass();
   }

   public final void setReuseAddress(boolean on) throws SocketException {
      this.socket.setReuseAddress(on);
   }

   public final boolean getReuseAddress() throws SocketException {
      return this.socket.getReuseAddress();
   }

   public final boolean isConnected() {
      return this.socket.isConnected();
   }

   public final boolean isBound() {
      return this.socket.isBound();
   }

   public final boolean isClosed() {
      return this.socket.isClosed();
   }

   public final boolean isInputShutdown() {
      return this.socket.isInputShutdown();
   }

   public final boolean isOutputShutdown() {
      return this.socket.isOutputShutdown();
   }

   public abstract void addHandshakeCompletedListener(HandshakeCompletedListener var1);

   public abstract boolean getEnableSessionCreation();

   public abstract String[] getEnabledCipherSuites();

   public abstract String[] getEnabledProtocols();

   public abstract boolean getNeedClientAuth();

   public abstract SSLSession getSession();

   public abstract String[] getSupportedCipherSuites();

   public abstract String[] getSupportedProtocols();

   public abstract boolean getUseClientMode();

   public abstract boolean getWantClientAuth();

   public abstract void removeHandshakeCompletedListener(HandshakeCompletedListener var1);

   public abstract void setEnableSessionCreation(boolean var1);

   public abstract void setEnabledCipherSuites(String[] var1);

   public abstract void setEnabledProtocols(String[] var1);

   public abstract void setNeedClientAuth(boolean var1);

   public abstract void setUseClientMode(boolean var1);

   public abstract void setWantClientAuth(boolean var1);

   public abstract void startHandshake() throws IOException;

   public abstract String getALPNProtocol();
}
