package weblogic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import weblogic.security.SSL.WeblogicSSLEngine;

public class JSSESocket extends WeblogicSSLSocket {
   private InputStream in;
   private OutputStream out;
   private JSSEFilterImpl filter;
   private SSLEngine sslEngine;
   private boolean firstByteReceived;
   private JSSESocketFactory factory;

   protected JSSESocket(Socket connectedSock) {
      super(connectedSock);
      this.firstByteReceived = false;
   }

   public JSSESocket(Socket connectedSock, JSSEFilterImpl filter) {
      this(connectedSock, filter, true);
   }

   protected JSSESocket(Socket connectedSock, JSSEFilterImpl filter, boolean isBindSSLEngineNow) {
      super(connectedSock);
      this.firstByteReceived = false;
      this.initJSSESocket(filter, isBindSSLEngineNow);
   }

   JSSESocket(Socket unconnectedSock, JSSESocketFactory factory) {
      super(unconnectedSock);
      this.firstByteReceived = false;
      this.factory = factory;
   }

   public InputStream getInputStream() {
      if (this.in == null) {
         this.initJSSESocket();
      }

      return this.in;
   }

   public OutputStream getOutputStream() {
      if (this.out == null) {
         this.initJSSESocket();
      }

      return this.out;
   }

   public JSSEFilterImpl getFilter() {
      if (this.filter == null) {
         this.initJSSESocket();
      }

      return this.filter;
   }

   private SSLEngine getSSLEngine() {
      if (this.sslEngine == null) {
         this.initJSSESocket();
      }

      return this.sslEngine;
   }

   private void initJSSESocket() {
      try {
         this.initJSSESocket(this.factory.getJSSEFilterImpl(this.getSocket()));
      } catch (IOException var2) {
         SocketLogger.logThrowable(var2);
      }

   }

   private void initJSSESocket(JSSEFilterImpl filter) {
      this.initJSSESocket(filter, true);
   }

   private void initJSSESocket(JSSEFilterImpl filter, boolean isBindSSLEngine) {
      this.filter = filter;
      if (isBindSSLEngine) {
         this.bindSSLEngine();
      }

      this.initInOut();
   }

   private void initInOut() {
      this.in = new JSSEInputStream(this.filter);
      this.out = new JSSEOutputStream(this.filter);
   }

   protected void bindSSLEngine() {
      this.sslEngine = this.filter.getSSLEngine();
      if (this.sslEngine instanceof WeblogicSSLEngine) {
         ((WeblogicSSLEngine)this.sslEngine).setAssociatedSSLSocket(this);
      }

   }

   public void addHandshakeCompletedListener(HandshakeCompletedListener arg0) {
      this.getFilter().addHandshakeCompletedListener(new HandshakeListenerImpl(arg0));
   }

   public boolean getEnableSessionCreation() {
      return this.getSSLEngine().getEnableSessionCreation();
   }

   public String[] getEnabledCipherSuites() {
      return this.getSSLEngine().getEnabledCipherSuites();
   }

   public String[] getEnabledProtocols() {
      return this.getSSLEngine().getEnabledProtocols();
   }

   public boolean getNeedClientAuth() {
      return this.getSSLEngine().getNeedClientAuth();
   }

   public SSLSession getSession() {
      return this.getSSLEngine().getSession();
   }

   public String[] getSupportedCipherSuites() {
      return this.getSSLEngine().getSupportedCipherSuites();
   }

   public String[] getSupportedProtocols() {
      return this.getSSLEngine().getSupportedProtocols();
   }

   public boolean getUseClientMode() {
      return this.getSSLEngine().getUseClientMode();
   }

   public boolean getWantClientAuth() {
      return this.getSSLEngine().getWantClientAuth();
   }

   public void removeHandshakeCompletedListener(HandshakeCompletedListener arg0) {
      this.getFilter().removeHandshakeCompletedListener(arg0);
   }

   public void setEnableSessionCreation(boolean arg0) {
      this.getSSLEngine().setEnableSessionCreation(arg0);
   }

   public void setEnabledCipherSuites(String[] arg0) {
      this.getSSLEngine().setEnabledCipherSuites(arg0);
   }

   public void setEnabledProtocols(String[] arg0) {
      this.getSSLEngine().setEnabledProtocols(arg0);
   }

   public void setNeedClientAuth(boolean arg0) {
      this.getSSLEngine().setNeedClientAuth(arg0);
   }

   public void setUseClientMode(boolean arg0) {
      this.getSSLEngine().setUseClientMode(arg0);
   }

   public void setWantClientAuth(boolean arg0) {
      this.getSSLEngine().setWantClientAuth(arg0);
   }

   public void startHandshake() throws IOException {
      this.getFilter().doHandshake();
   }

   public String getALPNProtocol() {
      return this.getSSLEngine() instanceof WeblogicSSLEngine ? ((WeblogicSSLEngine)this.getSSLEngine()).getALPNProtocol() : null;
   }

   public class HandshakeListenerImpl implements JSSEFilterImpl.HandshakeListener {
      private HandshakeCompletedListener listener;

      public HandshakeListenerImpl(HandshakeCompletedListener l) {
         this.listener = l;
      }

      public void handshakeDone(SSLSession session) {
         HandshakeCompletedEvent event = new HandshakeCompletedEvent(JSSESocket.this, session);
         this.listener.handshakeCompleted(event);
      }
   }

   private class JSSEOutputStream extends OutputStream {
      private JSSEFilterImpl filter;

      JSSEOutputStream(JSSEFilterImpl filter) {
         this.filter = filter;
      }

      public void write(int b) throws IOException {
         this.write(new byte[]{(byte)b}, 0, 1);
      }

      public void write(byte[] b, int off, int len) throws IOException {
         this.filter.write(b, off, len);
      }

      public void close() throws IOException {
         JSSESocket.this.close();
      }
   }

   private class JSSEInputStream extends InputStream {
      private JSSEFilterImpl filter;

      JSSEInputStream(JSSEFilterImpl filter) {
         this.filter = filter;
      }

      public int read() throws IOException {
         byte[] b = new byte[1];
         int nRead = this.read(b, 0, 1);
         return nRead == 1 ? b[0] : nRead;
      }

      public int read(byte[] buf, int offset, int length) throws IOException {
         try {
            int readBytes;
            for(readBytes = 0; readBytes == 0; readBytes = JSSESocket.this.getFilter().read(buf, offset, length)) {
            }

            if (!JSSESocket.this.firstByteReceived) {
               JSSESocket.this.firstByteReceived = true;
            }

            return readBytes;
         } catch (IOException var5) {
            if (JSSESocket.this.firstByteReceived) {
               return -1;
            } else {
               throw var5;
            }
         }
      }

      public void close() throws IOException {
         JSSESocket.this.close();
      }
   }
}
