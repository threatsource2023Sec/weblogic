package weblogic.rjvm.t3;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.utils.PartitionUtils;
import weblogic.protocol.ChannelHelperBase;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.ConnectionManager;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMImpl;
import weblogic.rjvm.TransportUtils;
import weblogic.security.service.ContextHandler;
import weblogic.server.channels.ChannelService;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.AsyncOutputStream;
import weblogic.socket.Login;
import weblogic.socket.MaxMessageSizeExceededException;
import weblogic.socket.MuxableSocket;
import weblogic.socket.NIOConnection;
import weblogic.socket.SocketMuxer;
import weblogic.utils.StringUtils;
import weblogic.utils.io.Chunk;

public class MuxableSocketT3 extends AbstractMuxableSocket implements AsyncOutputStream {
   private static final long serialVersionUID = -3990131100112713491L;
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private static final boolean loginReplyWithRel10Content = Boolean.getBoolean("weblogic.protocol.t3.login.replyWithRel10Content");
   private static final String CONNECT_PARAMS;
   private static final String CONNECT_PARAMS_WITH_CHANNEL_MAX_SIZE;
   private static final String MALFORMED_FIRST_LINE = "Malformed first line\nAre you trying to connect to a standard port using SSL or vice versa?";
   private static final boolean ASSERT = false;
   private static final int INITIAL_SO_TIMEOUT = 60000;
   private static final int HEADER_SIZE_LIMIT = 512;
   private static final int CONNECT_MAX_RETRY = 1;
   private static final int CONNECT_BACKOFF_INTERVAL = 1000;
   private boolean bootstrapped;
   private final String partitionURL;
   private String remotePartitionName;
   private static final FlightRecorderManager flightRecorderManager;
   protected final T3MsgAbbrevJVMConnection connection;
   private Chunk sendHead;
   private IOException sendException;

   public MuxableSocketT3(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      super(head, s, networkChannel);
      this.connection = new T3MsgAbbrevJVMConnection();
      this.acceptConnect(s);
      this.connection.setDispatcher(ConnectionManager.create((RJVMImpl)null), false);
      if (Kernel.getConfig().isSocketBufferSizeAsChunkSize()) {
         this.getSocket().setSendBufferSize(Chunk.CHUNK_SIZE);
         this.getSocket().setReceiveBufferSize(Chunk.CHUNK_SIZE);
      }

      this.partitionURL = null;
   }

   /** @deprecated */
   @Deprecated
   protected MuxableSocketT3(ServerChannel networkChannel, String partitionURL) {
      this((ServerChannel)networkChannel, (String)null, (String)partitionURL);
   }

   protected MuxableSocketT3(ServerChannel networkChannel, String remotePartitionName, String partitionURL) {
      super(networkChannel);
      this.connection = new T3MsgAbbrevJVMConnection();
      if (remotePartitionName == null) {
         if (partitionURL == null) {
            throw new IllegalArgumentException("PartitionURL not specified");
         }

         this.remotePartitionName = null;
         this.partitionURL = partitionURL;
      } else {
         if (partitionURL != null) {
            throw new IllegalArgumentException("Ignoring partitionURL: " + partitionURL);
         }

         this.remotePartitionName = remotePartitionName;
         this.partitionURL = null;
      }

   }

   static ServerChannel getPreferredOutboundChannel(SocketAddress address, ServerChannel channel) {
      if (!channel.isOutboundEnabled()) {
         ServerChannel preferredChannel = ChannelService.findPreferredChannelForPeer(address);
         if (preferredChannel != null) {
            return preferredChannel;
         }
      }

      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("getPreferredOutboundChannel() SocketAddress = " + address + ", channel = " + channel);
         debugConnection.debug("getPreferredOutboundChannel() Returning channel = " + channel);
      }

      return channel;
   }

   static MuxableSocket createMuxableSocket(InetAddress address, int port, ServerChannel networkChannel, int connectionTimeout, String partitionUrl) throws IOException {
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("createMuxableSocket() for address = " + address + ", port = " + port + ", server channel = " + networkChannel + ", partition URL = " + partitionUrl);
      }

      ServerChannel channelToUse = getPreferredOutboundChannel(new InetSocketAddress(address.getHostAddress(), port), networkChannel);
      MuxableSocketT3 muxableSocketT3 = new MuxableSocketT3(channelToUse, partitionUrl);
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("createMuxableSocket() muxableSocketT3 " + muxableSocketT3 + " created with channel " + channelToUse);
      }

      int timeout = connectionTimeout > 0 ? connectionTimeout : channelToUse.getConnectTimeout() * 1000;
      Socket s = muxableSocketT3.newSocketWithRetry(address, port, timeout);
      if (channelToUse == networkChannel) {
         Protocol p = ProtocolManager.getProtocolByName("T3");
         ServerChannel newChannel = ChannelService.findPreferredChannelFromSocket(s, p, channelToUse);
         if (debugConnection.isDebugEnabled()) {
            debugConnection.debug("createMuxableSocket() preferred channel for new socket = " + newChannel);
         }

         if (!newChannel.equals(channelToUse)) {
            muxableSocketT3 = new MuxableSocketT3(newChannel, partitionUrl);
            if (debugConnection.isDebugEnabled()) {
               debugConnection.debug("createMuxableSocket() Passed in channel " + networkChannel + " does not match preferred network channel " + newChannel + ". Created a new muxableSocketT3 " + muxableSocketT3);
            }
         }
      }

      muxableSocketT3.connectSocket(s, connectionTimeout);
      return muxableSocketT3;
   }

   final void acceptConnect(Socket s) throws IOException {
      this.connect(s);
      this.setSoTimeout(60000);
   }

   private boolean validateMagic(String[] args) {
      if (args != null && args.length > 1) {
         if (!args[0].toLowerCase().startsWith("t3")) {
            return false;
         } else {
            for(int i = 1; i < args.length - 1; ++i) {
               if (args[i].startsWith("/") || args[i].contains("http")) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void readIncomingConnectionBootstrapMessage(Chunk head) throws IOException {
      String str = new String(head.buf);
      int pos = str.indexOf("\n\n");
      if (pos < 0) {
         this.rejectConnection(1, "Unknown data");
      }

      str = str.substring(0, pos);
      StringTokenizer st = new StringTokenizer(str, "\n");
      if (!st.hasMoreTokens()) {
         this.rejectConnection(1, "No version information");
      }

      String token = st.nextToken();
      String[] args = StringUtils.splitCompletely(token, " \t");
      if (!this.validateMagic(args)) {
         this.rejectConnection(1, "Malformed first line\nAre you trying to connect to a standard port using SSL or vice versa?");
      }

      String version = null;
      if (args.length == 2) {
         version = args[1];
      } else if (args.length > 3) {
         version = args[3];
      } else {
         this.rejectConnection(1, "Malformed first line\nAre you trying to connect to a standard port using SSL or vice versa?");
      }

      VersionInfo peerVersion = new VersionInfo(version);
      if (!VersionInfo.compatibleWith(peerVersion)) {
         this.rejectConnection(6, VersionInfo.rejectionReasonFor(peerVersion));
      }

      if (!st.hasMoreTokens()) {
         this.rejectConnection(1, "Invalid request; No Abbrev table size specified.");
      }

      token = st.nextToken();
      int abbrevSize = MsgAbbrevJVMConnection.ABBREV_TABLE_SIZE;
      int headerLen;
      if (token.charAt(0) == "AS".charAt(0) && token.charAt(1) == "AS".charAt(1)) {
         try {
            headerLen = Integer.parseInt(token.substring(token.indexOf(58) + 1, token.length()));
            abbrevSize = Math.min(abbrevSize, headerLen);
         } catch (Exception var18) {
            this.rejectConnection(1, "Invalid abbrev table size: " + token);
         }
      }

      if (!st.hasMoreTokens()) {
         this.rejectConnection(1, "Invalid request; No JVM Msg header size specified.");
      }

      token = st.nextToken();
      headerLen = 19;
      if (token.charAt(0) == "HL".charAt(0) && token.charAt(1) == "HL".charAt(1)) {
         try {
            headerLen = Integer.parseInt(token.substring(token.indexOf(58) + 1, token.length()));
         } catch (Exception var17) {
            this.rejectConnection(1, "Invalid jvm msg header size: " + token);
         }
      }

      int peerChannelMaxMessageSize = -1;
      String partitionUrl = null;
      String remotePartitionUrl = null;
      String localPName = null;
      String remotePName = null;

      while(true) {
         while(st.hasMoreTokens()) {
            token = st.nextToken();
            if (token.charAt(0) == "MS".charAt(0) && token.charAt(1) == "MS".charAt(1)) {
               try {
                  peerChannelMaxMessageSize = Integer.parseInt(token.substring(token.indexOf(58) + 1, token.length()));
               } catch (Exception var19) {
                  if (debugConnection.isDebugEnabled()) {
                     debugConnection.debug("Received invalid ChannelMaxMessageSize param: " + token);
                  }
               }

               if (debugConnection.isDebugEnabled()) {
                  debugConnection.debug("Received ChannelMaxMessageSize: " + peerChannelMaxMessageSize);
               }
            } else if (token.charAt(0) == "PU".charAt(0) && token.charAt(1) == "PU".charAt(1)) {
               partitionUrl = token.substring(token.indexOf(58) + 1, token.length());
            } else if (token.charAt(0) == "LU".charAt(0) && token.charAt(1) == "LU".charAt(1)) {
               remotePartitionUrl = token.substring(token.indexOf(58) + 1, token.length());
            } else if (token.charAt(0) == "LP".charAt(0) && token.charAt(1) == "LP".charAt(1)) {
               remotePName = token.substring(token.indexOf(58) + 1, token.length());
            } else if (token.charAt(0) == "PN".charAt(0) && token.charAt(1) == "PN".charAt(1)) {
               localPName = token.substring(token.indexOf(58) + 1, token.length());
            } else if (token.charAt(0) == "PX".charAt(0) && token.charAt(1) == "PX".charAt(1)) {
               boolean proxy = Boolean.valueOf(token.substring(token.indexOf(58) + 1, token.length()));
               if (debugConnection.isDebugEnabled()) {
                  debugConnection.debug("Received proxy param: " + proxy);
               }

               this.connection.setProxied(proxy);
            }
         }

         if (partitionUrl != null) {
            if (localPName != null) {
               this.rejectConnection(1, "Received partionURL: " + this.partitionURL + " and partitionName: " + localPName);
            }

            localPName = this.getPartitionNameByURL(partitionUrl);
         }

         if (remotePName == null) {
            if (localPName == null) {
               if (partitionUrl == null) {
                  partitionUrl = "t3://" + this.socket.getLocalAddress().getHostName() + ':' + this.socket.getLocalPort();
                  localPName = this.getPartitionNameByURL(partitionUrl);
                  remotePName = "DOMAIN";
                  if (debugConnection.isDebugEnabled()) {
                     debugConnection.debug("No partitionURL was specified. May be received request from peer < 12.2.1; Defaulting to : " + partitionUrl);
                  }
               }
            } else {
               remotePName = localPName;
            }
         }

         this.connection.init(abbrevSize, headerLen, peerChannelMaxMessageSize, localPName, remotePartitionUrl, remotePName);
         Login.connectReplyOK(this.getSocket(), this.prepareBootstrapMsgResponse(peerVersion).getBytes(), VersionInfo.theOne(), VersionInfo.useFiveDigitsFormatFor(peerVersion));
         return;
      }
   }

   private String prepareBootstrapMsgResponse(VersionInfo peerVersion) {
      StringBuilder bootstrapMsg = new StringBuilder(CONNECT_PARAMS);
      if (!isLoginReplyWithRel10Content() || !VersionInfo.is10OrOlder(peerVersion)) {
         bootstrapMsg.append("MS").append(':').append(this.getChannel().getMaxMessageSize()).append('\n').append("PN").append(':').append(this.connection.getLocalPartitionName()).append('\n');
      }

      bootstrapMsg.append('\n');
      return bootstrapMsg.toString();
   }

   static boolean isLoginReplyWithRel10Content() {
      return loginReplyWithRel10Content;
   }

   private String getPartitionNameByURL(String partitionURL) throws IOException {
      PartitionTableEntry entry = null;

      try {
         entry = PartitionTable.getInstance().lookup(partitionURL);
      } catch (URISyntaxException var4) {
         if (debugConnection.isDebugEnabled()) {
            debugConnection.debug("Got invalid partition URL: " + partitionURL, var4);
         }

         this.rejectConnection(1, "Received invalid partionURL: " + partitionURL);
      }

      String name = entry.getPartitionName();
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("PartitionURL: " + partitionURL + " is associated to partition name: " + name);
      }

      return name;
   }

   private void readServerConnectionParams(BufferedReader br) throws IOException {
      TransportUtils.BootstrapResult result = TransportUtils.readBootstrapParams(br);
      if (!result.isSuccess()) {
         this.rejectConnection(1, "Invalid parameter: " + result.getInvalidLine());
      }

      String remotePName = result.getPartitionName();
      String localPName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      if (remotePName == null) {
         remotePName = "DOMAIN";
      }

      this.connection.init(result.getAbbrevSize(), result.getHeaderLength(), result.getPeerChannelMaxMessageSize(), localPName, this.partitionURL, remotePName);
   }

   Socket newSocketWithRetry(InetAddress address, int port, int connectionTimeout) throws IOException {
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

   private boolean canReadFirstMessage() {
      int bytesInBuf = this.getAvailableBytes();
      boolean canRead = false;

      for(int i = 0; i < bytesInBuf - 1; ++i) {
         if (i > 512) {
            return false;
         }

         if (this.getHeaderByte(i) == 10 && this.getHeaderByte(i + 1) == 10) {
            canRead = true;
            break;
         }
      }

      return canRead;
   }

   protected int getHeaderLength() {
      return !this.bootstrapped ? 2 : 4;
   }

   protected int getMessageLength() {
      if (!this.bootstrapped) {
         return this.canReadFirstMessage() ? this.getAvailableBytes() : -1;
      } else {
         int r0 = this.getHeaderByte(0) & 255;
         int r1 = this.getHeaderByte(1) & 255;
         int r2 = this.getHeaderByte(2) & 255;
         int r3 = this.getHeaderByte(3) & 255;
         return r0 << 24 | r1 << 16 | r2 << 8 | r3;
      }
   }

   public final int getIdleTimeoutMillis() {
      return 0;
   }

   public final void dispatch(Chunk list) {
      if (!this.bootstrapped) {
         try {
            this.readIncomingConnectionBootstrapMessage(list);
            this.bootstrapped = true;
         } catch (IOException var3) {
            SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), var3);
         }
      } else {
         this.connection.dispatch(list);
      }

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

   public MsgAbbrevJVMConnection getConnection() {
      return this.connection;
   }

   public final void connect(InetAddress address, int port) throws IOException {
      this.connect(address, port, 0);
   }

   public final void connect(InetAddress address, int port, int connectionTimeout) throws IOException {
      Socket s = this.newSocketWithRetry(address, port, connectionTimeout);
      this.connectSocket(s, connectionTimeout);
   }

   protected final void connectSocket(Socket newSocket, int connectionTimeout) throws IOException {
      this.connect(newSocket);
      this.setSoTimeout(connectionTimeout > 0 ? connectionTimeout : '\uea60');
      OutputStream out = this.getSocketOutputStream();
      out.write(this.prepareBootstrapMsgRequest().getBytes());
      out.flush();
      BufferedReader br = new BufferedReader(new InputStreamReader(this.getSocketInputStream()));
      String firstLine = br.readLine();
      String result = Login.checkLoginSuccess(firstLine);
      if (result != null) {
         if (debugConnection.isDebugEnabled()) {
            debugConnection.debug("connectSocket() bootstrapped line = " + firstLine);
            debugConnection.debug("connectSocket() Charset used = " + Charset.defaultCharset().displayName());
         }

         this.close();
         throw new IOException(result);
      } else {
         String version = Login.getVersionString(firstLine);
         if (version == null) {
            this.connection.doDownGrade();
         }

         this.readServerConnectionParams(br);
         this.bootstrapped = true;
      }
   }

   private String prepareBootstrapMsgRequest() {
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      StringBuilder bootstrapMsg = (new StringBuilder(this.getRealProtocolName())).append(' ').append(VersionInfo.theOne().getReleaseVersion()).append('\n').append(CONNECT_PARAMS_WITH_CHANNEL_MAX_SIZE).append(this.getChannel().getMaxMessageSize()).append('\n').append("LP").append(':').append(partitionName).append('\n');
      if (this.remotePartitionName != null) {
         bootstrapMsg.append("PN").append(':').append(this.remotePartitionName).append('\n');
      } else if (this.partitionURL != null) {
         bootstrapMsg.append("PU").append(':').append(this.partitionURL).append('\n');
      }

      if (KernelStatus.isServer() && this.channel.getPublicPort() != -1) {
         String localPartitionUrl = ChannelHelperBase.createURL(this.channel);
         if (!ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().isGlobalRuntime()) {
            String server = PartitionUtils.getServerName();
            VirtualTargetMBean virtualTargetMBean = PartitionUtils.findRunningVirtualTarget(partitionName, server);
            if (virtualTargetMBean != null) {
               localPartitionUrl = PartitionUtils.getVirtualTargetListenURL(virtualTargetMBean, server, this.channel.getProtocolPrefix());
            }
         }

         if (debugConnection.isDebugEnabled()) {
            debugConnection.debug("prepareBootstrapMsgRequest() in partition " + partitionName + " setting LOCAL_PARTITION_URL to: " + localPartitionUrl);
         }

         bootstrapMsg.append("LU").append(':').append(localPartitionUrl).append('\n');
      }

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

   public boolean supportsScatteredRead() {
      return true;
   }

   public long read(NIOConnection connection) throws IOException {
      int maxBuffers = connection.getOptimalNumberOfBuffers();

      assert maxBuffers > 0;

      this.tail = Chunk.tail(this.head);
      int available = Chunk.CHUNK_SIZE - this.tail.end;
      int msgL = this.head.end < 4 ? Chunk.CHUNK_SIZE : this.getMessageLength();
      msgL -= this.availBytes;
      ByteBuffer[] list = new ByteBuffer[maxBuffers];
      boolean useTail = false;
      int i = 0;
      if (available > 0) {
         list[i++] = this.tail.getReadByteBuffer();
         useTail = true;
      }

      for(Chunk tmp = this.tail; i < maxBuffers && msgL > available; list[i++] = tmp.getReadByteBuffer()) {
         tmp.next = Chunk.getChunk();
         tmp = tmp.next;
         available += Chunk.CHUNK_SIZE - tmp.end;
      }

      long len = connection.getScatteringByteChannel().read(list, 0, i);
      int j = 0;
      if (useTail) {
         this.tail.end = list[j++].position();
      }

      while(j < i) {
         Chunk next = this.tail.next;
         if (list[j].position() == 0) {
            Chunk.releaseChunks(next);
            this.tail.next = null;
            break;
         }

         next.end = list[j].position();
         this.tail = next;
         ++j;
      }

      if (len > 0L) {
         this.availBytes = (int)((long)this.availBytes + len);
      }

      if (this.availBytes > this.maxMessageSize) {
         throw new MaxMessageSizeExceededException(this.availBytes, this.maxMessageSize, this.channel.getConfiguredProtocol());
      } else {
         return len;
      }
   }

   public boolean supportsGatheringWrite() {
      return true;
   }

   public long write(NIOConnection connection) throws IOException {
      List list = new ArrayList(connection.getOptimalNumberOfBuffers());

      for(Chunk tmp = this.sendHead; tmp != null; tmp = tmp.next) {
         ByteBuffer buf = tmp.getWriteByteBuffer();
         list.add(buf);
      }

      ByteBuffer[] arr = this.toArray(list);
      long written = connection.getGatheringByteChannel().write(arr, 0, arr.length);

      while(this.sendHead != null) {
         Chunk c = this.sendHead;
         this.sendHead = this.sendHead.next;
         Chunk.releaseChunk(c);
      }

      return written;
   }

   private ByteBuffer[] toArray(List list) {
      int len = list.size();
      ByteBuffer[] arr = new ByteBuffer[len];

      for(int i = 0; i < len; ++i) {
         arr[i] = (ByteBuffer)list.get(i);
      }

      return arr;
   }

   static {
      CONNECT_PARAMS = "AS:" + MsgAbbrevJVMConnection.ABBREV_TABLE_SIZE + '\n' + "HL" + ':' + 19 + '\n';
      CONNECT_PARAMS_WITH_CHANNEL_MAX_SIZE = CONNECT_PARAMS + "MS" + ':';
      flightRecorderManager = Factory.getInstance();
   }

   protected class T3MsgAbbrevJVMConnection extends MsgAbbrevJVMConnection {
      private T3MsgAbbrevJVMConnection() {
         MuxableSocketT3.this.addSenderStatistics(this);
      }

      public final InetAddress getLocalAddress() {
         return MuxableSocketT3.this.getSocket().getLocalAddress();
      }

      public final int getLocalPort() {
         return MuxableSocketT3.this.getSocket().getLocalPort();
      }

      public final InetAddress getInetAddress() {
         return MuxableSocketT3.this.getSocket().getInetAddress();
      }

      public final ServerChannel getChannel() {
         return MuxableSocketT3.this.getChannel();
      }

      public final ContextHandler getContextHandler() {
         return MuxableSocketT3.this;
      }

      public final void connect(String host, InetAddress address, int port, int connectTimeout) throws IOException {
         MuxableSocketT3.this.connect(address, port, connectTimeout);
      }

      public final void sendMsg(OutgoingMessage msg) throws IOException {
         if (MuxableSocketT3.this.isClosed()) {
            String details = MuxableSocketT3.this.getCloseDebugReasonString();
            MuxableSocketT3.flightRecorderManager.generateDebugEvent("Core", "Attempt to send message on closed socket: " + details, MuxableSocketT3.this.sendException, (Object)null);
            throw new IOException("Attempt to send message on closed socket: " + details);
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
