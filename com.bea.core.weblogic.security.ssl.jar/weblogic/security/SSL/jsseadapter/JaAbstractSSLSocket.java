package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.SocketException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import weblogic.security.SSL.WLSSSLNioSocket;
import weblogic.security.SSL.WeblogicSSLEngine;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.security.utils.SSLTrustValidator;
import weblogic.security.utils.SSLTruster;

abstract class JaAbstractSSLSocket extends SSLSocket implements WLSSSLNioSocket {
   private final JaSSLContext jaSSLContext;
   private SSLIOContext sslIoContext;
   private final JaSSLEngineRunner.Context sslEngineRunnerContext = new JaSSLEngineRunner.Context();
   private final ReadableByteChannel appReadableByteChannel;
   private final WritableByteChannel appWritableByteChannel;
   private final JaChannelInputStream appInStream;
   private final OutputStream appOutStream;

   public String[] getSupportedCipherSuites() {
      return this.sslEngineRunnerContext.getSslEngine().getSupportedCipherSuites();
   }

   public String[] getEnabledCipherSuites() {
      return this.sslEngineRunnerContext.getSslEngine().getEnabledCipherSuites();
   }

   public void setEnabledCipherSuites(String[] suites) {
      this.sslEngineRunnerContext.getSslEngine().setEnabledCipherSuites(suites);
   }

   public String[] getSupportedProtocols() {
      return this.sslEngineRunnerContext.getSslEngine().getSupportedProtocols();
   }

   public String[] getEnabledProtocols() {
      return this.sslEngineRunnerContext.getSslEngine().getEnabledProtocols();
   }

   public void setEnabledProtocols(String[] protocols) {
      this.sslEngineRunnerContext.getSslEngine().setEnabledProtocols(protocols);
   }

   public SSLSession getSession() {
      return this.sslEngineRunnerContext.getSslEngine().getSession();
   }

   public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
      SSLEngine engine = this.sslEngineRunnerContext.getSslEngine();
      if (engine instanceof WeblogicSSLEngine) {
         ((WeblogicSSLEngine)engine).addHandshakeCompletedListener(handshakeCompletedListener);
      } else {
         throw new UnsupportedOperationException("HandshakeCompletedListener is only supported when using WeblogicSSLEngine.");
      }
   }

   public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
      SSLEngine engine = this.sslEngineRunnerContext.getSslEngine();
      if (engine instanceof WeblogicSSLEngine) {
         ((WeblogicSSLEngine)engine).removeHandshakeCompletedListener(handshakeCompletedListener);
      } else {
         throw new UnsupportedOperationException("HandshakeCompletedListener is only supported when using WeblogicSSLEngine.");
      }
   }

   public void startHandshake() throws IOException {
      SSLEngine sslEngine = this.sslEngineRunnerContext.getSslEngine();
      if (HandshakeStatus.NOT_HANDSHAKING == sslEngine.getHandshakeStatus() || HandshakeStatus.FINISHED == sslEngine.getHandshakeStatus()) {
         this.sslEngineRunnerContext.getSslEngine().beginHandshake();
      }

      byte[] sessId = sslEngine.getSession().getId();
      boolean initialHandshake = null == sessId || 0 == sessId.length;
      if (!initialHandshake) {
         if (JaLogger.isLoggable(Level.FINEST)) {
            String peerHost = this.getInetAddress().getHostAddress();
            int peerPort = this.getPort();
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "startHandshake() called for rehandshaking, not blocking on socket to peer {0}:{1}.", peerHost, peerPort);
         }

      } else {
         SocketChannel sockChan = this.getChannel();
         boolean origBlocking = null == sockChan ? true : sockChan.isBlocking();
         boolean sockChanBlockingModeChanged = false;
         if (null != sockChan && !sockChan.isBlocking()) {
            sockChan.configureBlocking(true);
            sockChanBlockingModeChanged = true;
            if (JaLogger.isLoggable(Level.FINER)) {
               JaLogger.log(Level.FINER, JaLogger.Component.SSLSOCKET, "Socket Channel {0} temporarily changed to blocking.", sockChan);
            }
         }

         JaSSLEngineRunner.RunnerResult result;
         while(JaSSLEngineRunner.RunnerResult.OK != (result = JaSSLEngineRunner.wrap(this.sslEngineRunnerContext))) {
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "Trying to complete handshake on socket channel {0}, last result={1}.", sockChan, result);
            }

            Thread.currentThread();
            Thread.yield();
         }

         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "Completed initial handshake on socket channel {0}, last result={1}.", sockChan, result);
         }

         if (sockChanBlockingModeChanged && null != sockChan) {
            sockChan.configureBlocking(origBlocking);
            if (JaLogger.isLoggable(Level.FINER)) {
               JaLogger.log(Level.FINER, JaLogger.Component.SSLSOCKET, "Socket Channel {0} restored to blocking mode={1}.", sockChan, origBlocking);
            }
         }

      }
   }

   public void setUseClientMode(boolean b) {
      this.sslEngineRunnerContext.getSslEngine().setUseClientMode(b);
   }

   public boolean getUseClientMode() {
      return this.sslEngineRunnerContext.getSslEngine().getUseClientMode();
   }

   public void setNeedClientAuth(boolean b) {
      this.sslEngineRunnerContext.getSslEngine().setNeedClientAuth(b);
   }

   public boolean getNeedClientAuth() {
      return this.sslEngineRunnerContext.getSslEngine().getNeedClientAuth();
   }

   public void setWantClientAuth(boolean b) {
      this.sslEngineRunnerContext.getSslEngine().setWantClientAuth(b);
   }

   public boolean getWantClientAuth() {
      return this.sslEngineRunnerContext.getSslEngine().getWantClientAuth();
   }

   public void setEnableSessionCreation(boolean b) {
      this.sslEngineRunnerContext.getSslEngine().setEnableSessionCreation(b);
   }

   public boolean getEnableSessionCreation() {
      return this.sslEngineRunnerContext.getSslEngine().getEnableSessionCreation();
   }

   public InputStream getInputStream() throws IOException {
      return this.appInStream;
   }

   public OutputStream getOutputStream() throws IOException {
      return this.appOutStream;
   }

   public void close() throws IOException {
      try {
         JaSSLEngineRunner.close(this.sslEngineRunnerContext, false);
      } finally {
         if (this.sslIoContext != null) {
            SSLIOContextTable.removeContext(this.sslIoContext);
         }

         super.close();
      }

   }

   public void shutdownInput() throws IOException {
      try {
         JaSSLEngineRunner.close(this.sslEngineRunnerContext, false);
      } catch (Exception var2) {
      }

      super.shutdownInput();
   }

   public void shutdownOutput() throws IOException {
      try {
         JaSSLEngineRunner.closeOutbound(this.sslEngineRunnerContext);
      } catch (Exception var2) {
      }

      super.shutdownOutput();
   }

   public int hashCode() {
      return super.hashCode();
   }

   public boolean equals(Object obj) {
      return super.equals(obj);
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   public String toString() {
      return super.toString();
   }

   public ReadableByteChannel getReadableByteChannel() {
      return this.appReadableByteChannel;
   }

   public WritableByteChannel getWritableByteChannel() {
      return this.appWritableByteChannel;
   }

   public SelectableChannel getSelectableChannel() {
      return this.getChannel();
   }

   JaSSLEngineRunner.Context getSslEngineRunnerContext() {
      return this.sslEngineRunnerContext;
   }

   WeblogicSSLEngine createSslEngine(JaSSLParameters sslParameters) throws SSLException {
      String peerHost = this.getInetAddress().getHostAddress();
      int peerPort = this.getPort();
      WeblogicSSLEngine sslEngine = this.jaSSLContext.createSSLEngine(peerHost, peerPort);
      sslParameters.setUnencryptedNullCipherEnabled(this.jaSSLContext.isUnencryptedNullCipherEnabled());
      sslParameters.configureSslEngine(sslEngine);
      SSLTruster truster = this.jaSSLContext.getTrustManager();
      if (truster instanceof SSLTrustValidator) {
         SSLTrustValidator sslValidator = (SSLTrustValidator)truster;
         sslEngine.setNeedClientAuth(sslValidator.isPeerCertsRequired());
      }

      return sslEngine;
   }

   void init(SocketChannel socketChannel, JaSSLParameters sslParameters) throws IOException {
      if (null == socketChannel) {
         throw new IllegalArgumentException("Expected non-null SocketChannel.");
      } else {
         this.appInStream.setSelectableChannel(socketChannel);
         JaChannelInputStream netInStream = new JaChannelInputStream(socketChannel);
         netInStream.setSelectableChannel(socketChannel);
         this.sslIoContext = new SSLIOContext(netInStream, socketChannel.socket().getOutputStream(), this);
         InputStream muxerIS = this.sslIoContext.getMuxerIS();
         SSLIOContextTable.addContext(this.sslIoContext);
         ReadableByteChannel networkReadableByteChannel = Channels.newChannel(muxerIS);
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "{0}[{1}] Accessing network using SocketChannel.", this.getClass().getName(), this.hashCode());
         }

         this.init((ReadableByteChannel)networkReadableByteChannel, (WritableByteChannel)socketChannel, (JaSSLParameters)sslParameters);
      }
   }

   void init(JaSSLParameters sslParameters) throws IOException {
      InputStream netInStream = super.getInputStream();
      OutputStream netOutStream = super.getOutputStream();
      this.init(sslParameters, netInStream, netOutStream);
   }

   void init(JaSSLParameters sslParameters, InputStream netInStream, OutputStream netOutStream) throws IOException {
      this.sslIoContext = new SSLIOContext(netInStream, netOutStream, this);
      InputStream muxerIS = this.sslIoContext.getMuxerIS();
      SSLIOContextTable.addContext(this.sslIoContext);
      ReadableByteChannel networkReadableByteChannel = Channels.newChannel(muxerIS);
      WritableByteChannel networkWritableByteChannel = Channels.newChannel(netOutStream);
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "{0}[{1}] Accessing network using InputStream and OutputStream.", this.getClass().getName(), this.hashCode());
      }

      this.init(networkReadableByteChannel, networkWritableByteChannel, sslParameters);
   }

   void init(ReadableByteChannel networkReadableByteChannel, WritableByteChannel networkWritableByteChannel, JaSSLParameters sslParameters) throws IOException {
      if (null == sslParameters) {
         throw new IllegalArgumentException("Expected non-null JaSSLParameters.");
      } else {
         try {
            this.setTcpNoDelay(true);
         } catch (SocketException var5) {
            throw new ProtocolException(var5.getMessage());
         }

         WeblogicSSLEngine sslEngine = this.createSslEngine(sslParameters);
         sslEngine.setAssociatedSSLSocket(this);
         this.sslEngineRunnerContext.init(sslEngine, networkReadableByteChannel, networkWritableByteChannel, this.sslIoContext);
      }
   }

   JaAbstractSSLSocket(JaSSLContext jaSSLContext) throws IOException {
      this.appReadableByteChannel = new JaApplicationReadableByteChannel(this.sslEngineRunnerContext);
      this.appWritableByteChannel = new JaApplicationWritableByteChannel(this.sslEngineRunnerContext);
      this.appInStream = new JaChannelInputStream(this.appReadableByteChannel);
      this.appOutStream = new JaApplicationOutputStream(this);
      if (null == jaSSLContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext.");
      } else {
         this.jaSSLContext = jaSSLContext;
      }
   }

   JaAbstractSSLSocket(JaSSLContext jaSSLContext, String host, int port) throws IOException {
      super(host, port);
      this.appReadableByteChannel = new JaApplicationReadableByteChannel(this.sslEngineRunnerContext);
      this.appWritableByteChannel = new JaApplicationWritableByteChannel(this.sslEngineRunnerContext);
      this.appInStream = new JaChannelInputStream(this.appReadableByteChannel);
      this.appOutStream = new JaApplicationOutputStream(this);
      if (null == jaSSLContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext.");
      } else {
         this.jaSSLContext = jaSSLContext;
      }
   }

   JaAbstractSSLSocket(JaSSLContext jaSSLContext, InetAddress address, int port) throws IOException {
      super(address, port);
      this.appReadableByteChannel = new JaApplicationReadableByteChannel(this.sslEngineRunnerContext);
      this.appWritableByteChannel = new JaApplicationWritableByteChannel(this.sslEngineRunnerContext);
      this.appInStream = new JaChannelInputStream(this.appReadableByteChannel);
      this.appOutStream = new JaApplicationOutputStream(this);
      if (null == jaSSLContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext.");
      } else {
         this.jaSSLContext = jaSSLContext;
      }
   }

   JaAbstractSSLSocket(JaSSLContext jaSSLContext, String host, int port, InetAddress clientAddress, int clientPort) throws IOException {
      super(host, port, clientAddress, clientPort);
      this.appReadableByteChannel = new JaApplicationReadableByteChannel(this.sslEngineRunnerContext);
      this.appWritableByteChannel = new JaApplicationWritableByteChannel(this.sslEngineRunnerContext);
      this.appInStream = new JaChannelInputStream(this.appReadableByteChannel);
      this.appOutStream = new JaApplicationOutputStream(this);
      if (null == jaSSLContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext.");
      } else {
         this.jaSSLContext = jaSSLContext;
      }
   }

   JaAbstractSSLSocket(JaSSLContext jaSSLContext, InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException {
      super(address, port, clientAddress, clientPort);
      this.appReadableByteChannel = new JaApplicationReadableByteChannel(this.sslEngineRunnerContext);
      this.appWritableByteChannel = new JaApplicationWritableByteChannel(this.sslEngineRunnerContext);
      this.appInStream = new JaChannelInputStream(this.appReadableByteChannel);
      this.appOutStream = new JaApplicationOutputStream(this);
      if (null == jaSSLContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext.");
      } else {
         this.jaSSLContext = jaSSLContext;
      }
   }
}
