package weblogic.rjvm.t3.client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import weblogic.common.internal.VersionInfo;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.TransportUtils;
import weblogic.security.service.ContextHandler;
import weblogic.socket.AsyncOutputStream;
import weblogic.socket.BaseAbstractMuxableSocket;
import weblogic.socket.Login;
import weblogic.socket.MaxMessageSizeExceededException;
import weblogic.socket.NIOConnection;
import weblogic.socket.SocketMuxer;
import weblogic.utils.io.Chunk;

public class MuxableSocketT3 extends BaseAbstractMuxableSocket implements AsyncOutputStream {
   private static final long serialVersionUID = -3990131100112713491L;
   private static final String CONNECT_PARAMS;
   private static final boolean ASSERT = false;
   private static final int INITIAL_SO_TIMEOUT = 60000;
   private static final int CONNECT_MAX_RETRY = 1;
   private static final int CONNECT_BACKOFF_INTERVAL = 1000;
   private final String partitionURL;
   private final String CLIENT_PARTITION_NAME = "DOMAIN";
   protected final T3MsgAbbrevJVMConnection connection = new T3MsgAbbrevJVMConnection();
   private Chunk sendHead;
   private IOException sendException;

   protected MuxableSocketT3(ServerChannel networkChannel, String partitionURL) {
      super(networkChannel);
      this.partitionURL = partitionURL;
   }

   private void readConnectionParams(BufferedReader br) throws IOException {
      TransportUtils.BootstrapResult result = TransportUtils.readBootstrapParams(br);
      if (!result.isSuccess()) {
         this.rejectConnection(1, "Invalid parameter: " + result.getInvalidLine());
      }

      String partitionName = result.getPartitionName();
      if (partitionName == null) {
         partitionName = "DOMAIN";
      }

      this.connection.init(result.getAbbrevSize(), result.getHeaderLength(), result.getPeerChannelMaxMessageSize(), "DOMAIN", this.partitionURL, partitionName);
   }

   private Socket newSocketWithRetry(InetAddress address, int port, int connectionTimeout) throws IOException {
      int i = 0;

      while(true) {
         try {
            return this.createSocket(address, port, connectionTimeout);
         } catch (SocketException var8) {
            if (i == 1) {
               throw var8;
            }

            try {
               Thread.sleep((long)(Math.random() * (double)(1000 << i)));
            } catch (InterruptedException var7) {
            }

            ++i;
         }
      }
   }

   public OutputStream getOutputStream() {
      return this.getSocketOutputStream();
   }

   public final Chunk getOutputBuffer() {
      return this.sendHead;
   }

   public final void handleException(IOException ioe) {
      this.sendException = ioe;
   }

   public final void handleWrite(Chunk c) {
      this.sendHead = c.next;
      Chunk.releaseChunk(c);
   }

   protected final int getHeaderLength() {
      return 4;
   }

   protected final int getMessageLength() {
      int r0 = this.getHeaderByte(0) & 255;
      int r1 = this.getHeaderByte(1) & 255;
      int r2 = this.getHeaderByte(2) & 255;
      int r3 = this.getHeaderByte(3) & 255;
      return r0 << 24 | r1 << 16 | r2 << 8 | r3;
   }

   public final int getIdleTimeoutMillis() {
      return 0;
   }

   public final void dispatch(Chunk list) {
      this.connection.dispatch(list);
   }

   public final void hasException(Throwable t) {
      this.connection.gotExceptionReceiving(t);
      super.hasException(t);
   }

   public final boolean timeout() {
      this.connection.gotExceptionReceiving(new EOFException("Connection timed out"));
      return super.timeout();
   }

   public final void endOfStream() {
      this.connection.gotExceptionReceiving(new EOFException());
      super.endOfStream();
   }

   public void incrementBufferOffset(int i) throws MaxMessageSizeExceededException {
      super.incrementBufferOffset(i);
      if (i > 0 && this.getConnection().getDispatcher() != null) {
         this.getConnection().getDispatcher().messageReceived();
      }

   }

   private void rejectConnection(int code, String reason) throws IOException {
      Login.connectReply(this.getSocket(), code, reason);
      this.close();
      throw new IOException(reason);
   }

   public final MsgAbbrevJVMConnection getConnection() {
      return this.connection;
   }

   public final void connect(InetAddress address, int port) throws IOException {
      throw new UnsupportedOperationException("Use connect(InetAddress, int, int) instead");
   }

   public final void connect(InetAddress address, int port, int connectionTimeout) throws IOException {
      this.connect(this.newSocketWithRetry(address, port, connectionTimeout));
      this.setSoTimeout(connectionTimeout > 0 ? connectionTimeout : '\uea60');
      OutputStream out = this.getSocketOutputStream();
      out.write(this.prepareBootstrapMsg().getBytes());
      out.flush();
      BufferedReader br = new BufferedReader(new InputStreamReader(this.getSocketInputStream()));
      String firstLine = br.readLine();
      String result = Login.checkLoginSuccess(firstLine);
      if (result != null) {
         this.close();
         throw new IOException(result);
      } else {
         String version = Login.getVersionString(firstLine);
         if (version == null) {
            this.connection.doDownGrade();
         }

         this.readConnectionParams(br);
      }
   }

   private String prepareBootstrapMsg() {
      StringBuilder bootstrapMsg = (new StringBuilder(this.getRealProtocolName())).append(' ').append(VersionInfo.theOne().getReleaseVersion()).append('\n').append(CONNECT_PARAMS).append(this.getChannel().getMaxMessageSize()).append('\n').append("PU").append(':').append(this.partitionURL).append('\n').append("LP").append(':').append("DOMAIN").append('\n');
      if (!KernelStatus.isApplet()) {
         boolean proxy = Boolean.getBoolean("weblogic.rjvm.proxy");
         if (proxy) {
            this.connection.setProxied(true);
            bootstrapMsg.append("PX").append(":true\n");
         }
      }

      bootstrapMsg.append('\n');
      return bootstrapMsg.toString();
   }

   private String getRealProtocolName() {
      return ProtocolManager.getRealProtocol(this.getProtocol()).getProtocolName().toLowerCase(Locale.ENGLISH);
   }

   protected X509Certificate[] getJavaCertChain() {
      return null;
   }

   public boolean supportsGatheringWrite() {
      return false;
   }

   public long write(NIOConnection connection) throws IOException {
      throw new UnsupportedOperationException();
   }

   static {
      CONNECT_PARAMS = "AS:" + MsgAbbrevJVMConnection.ABBREV_TABLE_SIZE + '\n' + "HL" + ':' + 19 + '\n' + "MS" + ':';
   }

   protected class T3MsgAbbrevJVMConnection extends MsgAbbrevJVMConnection {
      private T3MsgAbbrevJVMConnection() {
      }

      public final InetAddress getLocalAddress() {
         return MuxableSocketT3.this.getSocket().getLocalAddress();
      }

      public final int getLocalPort() {
         return MuxableSocketT3.this.getSocket().getLocalPort();
      }

      public final InetAddress getInetAddress() {
         return MuxableSocketT3.this.socket == null ? null : MuxableSocketT3.this.socket.getInetAddress();
      }

      public final ServerChannel getChannel() {
         return MuxableSocketT3.this.getChannel();
      }

      public final ContextHandler getContextHandler() {
         return MuxableSocketT3.this;
      }

      public final void connect(InetAddress address, int port, int connectTimeout) throws IOException {
         this.connect(address.getCanonicalHostName(), address, port, connectTimeout);
      }

      public final void connect(String host, InetAddress address, int port, int connectTimeout) throws IOException {
         MuxableSocketT3.this.connect(address, port, connectTimeout);
      }

      public final void sendMsg(OutgoingMessage msg) throws IOException {
         if (MuxableSocketT3.this.isClosed()) {
            throw new IOException("Attempt to send message on closed socket");
         } else {
            MuxableSocketT3.this.sendHead = msg.getChunks();
            SocketMuxer.getMuxer().write(MuxableSocketT3.this);
            if (MuxableSocketT3.this.sendException != null) {
               throw MuxableSocketT3.this.sendException;
            }
         }
      }

      public final void close() {
         SocketMuxer.getMuxer().closeSocket(MuxableSocketT3.this.getSocketFilter());
      }

      public final X509Certificate[] getJavaCertChain() {
         return MuxableSocketT3.this.getJavaCertChain();
      }

      // $FF: synthetic method
      T3MsgAbbrevJVMConnection(Object x1) {
         this();
      }
   }
}
