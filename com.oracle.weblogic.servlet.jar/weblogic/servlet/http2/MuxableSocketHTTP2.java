package weblogic.servlet.http2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.SSLSocket;
import javax.servlet.http.HttpServletRequest;
import weblogic.management.DeploymentException;
import weblogic.protocol.ServerChannel;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.http2.frame.Frame;
import weblogic.servlet.http2.frame.FrameType;
import weblogic.servlet.http2.frame.GoAwayFrame;
import weblogic.servlet.http2.frame.PingFrame;
import weblogic.servlet.http2.frame.SettingsFrame;
import weblogic.servlet.http2.hpack.HpackEncoder;
import weblogic.servlet.internal.HTTPDebugLogger;
import weblogic.servlet.internal.HttpSocket;
import weblogic.servlet.internal.OnDemandContext;
import weblogic.servlet.internal.OnDemandManager;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.JSSEFilterImpl;
import weblogic.socket.JSSESocket;
import weblogic.socket.MaxMessageSizeExceededException;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.SocketMuxer;
import weblogic.socket.utils.JSSEUtils;
import weblogic.utils.io.Chunk;
import weblogic.work.WorkManagerFactory;

public class MuxableSocketHTTP2 extends AbstractMuxableSocket implements HTTP2Connection, HttpSocket {
   private static final byte[] PREFACE_BYTES;
   private State currentState;
   private int length = 0;
   private int readPos = 0;
   private final StreamManagerImpl sm = new StreamManagerImpl(this);
   private final PingManager pm = new PingManager();
   private final FrameHandler frameHandler;
   private final RemoteSettings remoteSettings;
   private final LocalSettings localSettings;
   private long sendWindowSize;
   private long recvWindowSize;
   private long sendWindowSizeForStream;
   private long recvWindowSizeForStream;
   private boolean isClosed = false;
   private boolean secure = false;
   private boolean isOnGoAway = false;
   private HpackEncoder hpackEncoder;

   public MuxableSocketHTTP2(Chunk head, Socket s, ServerChannel networkChannel, HttpServletRequest req, byte[] settingsFromRemote, boolean secure) throws IOException, HTTP2Exception {
      super(head, s, networkChannel);
      this.setSoTimeout(this.socket.getSoTimeout());
      this.secure = secure;
      this.currentState = MuxableSocketHTTP2.State.PREFACE;
      this.remoteSettings = new RemoteSettings(WebServerRegistry.getInstance().getWebAppContainerMBean().getHttp2Config());
      this.localSettings = new LocalSettings(WebServerRegistry.getInstance().getWebAppContainerMBean().getHttp2Config());
      if (settingsFromRemote != null) {
         if (settingsFromRemote.length % 6 != 0) {
            throw new ConnectionException(MessageManager.getMessage("frameType.checkPayloadSize", FrameType.SETTINGS, settingsFromRemote.length), 6);
         }

         for(int i = 0; i < settingsFromRemote.length / 6; ++i) {
            int id = ((settingsFromRemote[i * 6] & 255) << 8) + (settingsFromRemote[i * 6 + 1] & 255);
            long value = ((long)(settingsFromRemote[i * 6 + 2] & 255) << 24) + (long)((settingsFromRemote[i * 6 + 2 + 1] & 255) << 16) + (long)((settingsFromRemote[i * 6 + 2 + 2] & 255) << 8) + (long)(settingsFromRemote[i * 6 + 2 + 3] & 255);
            this.remoteSettings.set(id, value);
         }
      }

      this.sendWindowSize = 65536L;
      this.recvWindowSize = 65536L;
      this.sendWindowSizeForStream = (long)this.getRemoteSettings().getInitialWindowSize();
      this.recvWindowSizeForStream = (long)this.getLocalSettings().getInitialWindowSize();
      this.frameHandler = new FrameHandler(this);
      this.hpackEncoder = new HpackEncoder(this.getLocalSettings().getHeaderTableSize());
      if (req != null) {
         StreamImpl stream = new StreamImpl(1, this, this.sm, (ServletRequestImpl)req);
         if (this.sm.streams.putIfAbsent(1, stream) == null) {
            this.sm.activeRemoteStreams.set(1);
            this.sm.maxStreamId = 1;
            this.sm.setMaxProcessedStreamId(1);
         }
      }

   }

   public HpackEncoder getHpackEncoder() {
      return this.hpackEncoder;
   }

   public boolean isSecure() {
      return this.secure;
   }

   public StreamManager getStreamManager() {
      return this.sm;
   }

   public int getInitSendWindowSizeForStream() {
      return (int)this.sendWindowSizeForStream;
   }

   public void setInitSendWindowSizeForStream(int sendWindowSizeForStream) {
      this.sendWindowSizeForStream = (long)sendWindowSizeForStream;
   }

   public int getInitRecvWindowSizeForStream() {
      return (int)this.recvWindowSizeForStream;
   }

   public void setInitRecvWindowSizeForStream(int recvWindowSizeForStream) {
      this.recvWindowSizeForStream = (long)recvWindowSizeForStream;
   }

   public boolean hasSendWindow() {
      return this.sendWindowSize > 0L;
   }

   public int reserveSendWindowSize(int bytes) {
      int windowSize = false;
      synchronized(this) {
         while(this.sendWindowSize < 1L) {
            try {
               this.wait();
            } catch (InterruptedException var6) {
            }
         }

         int windowSize = (long)bytes > this.sendWindowSize ? (int)this.sendWindowSize : bytes;
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Reserving Send Window Size " + windowSize + " for Connection " + this + ", current send window size is " + this.sendWindowSize);
         }

         this.incrementSendWindowSize(-windowSize);
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("The left Send Window Size is " + this.sendWindowSize + " for Connection " + this);
         }

         return windowSize;
      }
   }

   public void upgrade(MuxableSocket httpSocket) throws IOException {
      if (this.isSecure()) {
         SSLSocket sslSock = (SSLSocket)((SSLSocket)httpSocket.getSocket());
         JSSESocket jsseSock = JSSEUtils.getJSSESocket(sslSock);
         if (jsseSock != null) {
            JSSEFilterImpl filter = (JSSEFilterImpl)httpSocket.getSocketFilter();
            this.setSocketFilter(filter);
            filter.setDelegate(this);
         } else {
            SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSock);
            if (sslIOCtx == null) {
               throw new IOException("SSL transport layer closed the socket!");
            }

            SSLFilter sslf = (SSLFilter)sslIOCtx.getFilter();
            this.setSocketFilter(sslf);
            sslf.setDelegate(this);
            sslf.activateNoRegister();
         }
      } else {
         SocketMuxer.getMuxer().reRegister(httpSocket, this);
      }

   }

   public void incrementBufferOffset(int availBytes) throws MaxMessageSizeExceededException {
      this.availBytes += availBytes;
      Chunk var10000 = this.tail;
      var10000.end += availBytes;
   }

   public void receivedPing(PingFrame pingFrame) {
      this.pm.onReceiving(pingFrame);
   }

   public boolean isMessageComplete() {
      if (this.availBytes > 0) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("MuxableSocketHTTP2 isMessageComplete : availBytes=" + this.availBytes + ", readPos=" + this.readPos + ", frame type=" + this.getFrameType());
         }

         return this.isFrameComplete();
      } else {
         return false;
      }
   }

   private boolean isFrameComplete() {
      if (this.availBytes <= 1) {
         return false;
      } else if (this.currentState == MuxableSocketHTTP2.State.PREFACE) {
         this.length = 24;
         return this.availBytes >= 24;
      } else if (this.availBytes < 9) {
         return false;
      } else {
         int payloadSize = this.getPayloadSize();
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("MuxableSocketHTTP2 Validate Payload Size: payloadsize=" + payloadSize + ", frame type=" + this.getFrameType());
         }

         this.validatePayloadSize(payloadSize, this.getFrameType());
         this.length = payloadSize + 9;
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("MuxableSocketHTTP2 isFrameComplete : payloadsize=" + payloadSize + ", frame type=" + this.getFrameType() + ", availBytes=" + this.availBytes);
         }

         return this.availBytes >= this.length;
      }
   }

   public void dispatch() {
      if (this.secure && this.getSocketFilter() instanceof SSLFilter) {
         ((SSLFilter)this.getSocketFilter()).asyncOff();
      }

      do {
         byte[] payload = new byte[this.length];
         this.copyDataTo(payload, 0, this.length);
         this.availBytes -= this.length;
         this.releaseChunks(this.length);
         switch (this.currentState) {
            case PREFACE:
               if (Arrays.equals(payload, PREFACE_BYTES)) {
                  try {
                     this.onPreface();
                  } catch (HTTP2Exception var6) {
                     this.closeConnection(var6);
                  } catch (IOException var7) {
                     var7.printStackTrace();
                     this.closeConnection();
                  }

                  this.currentState = MuxableSocketHTTP2.State.SETTINGS;
               }
               break;
            case SETTINGS:
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("MuxableSocketHTTP2 Received : " + Arrays.toString(payload));
               }

               try {
                  this.frameHandler.parse(ByteBuffer.wrap(payload), FrameType.SETTINGS);
                  this.currentState = MuxableSocketHTTP2.State.FRAMES;
                  this.pm.sendPing();
                  if (!this.secure) {
                     Stream stream = this.sm.getStream(1);
                     if (stream == null) {
                        throw new ProtocalException("Can not find Stream which id=1 when http1.1 upgrade", 1);
                     }

                     stream.handleOnContainer();
                  }
               } catch (ConnectionException var3) {
                  this.logException(var3);
                  this.closeConnection(var3);
               } catch (HTTP2Exception var4) {
                  this.logException(var4);
               } catch (IOException var5) {
                  this.logException(var5);
                  this.closeConnection();
               }
               break;
            case FRAMES:
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("MuxableSocketHTTP2 Received : " + Arrays.toString(payload));
               }

               try {
                  this.frameHandler.parse(ByteBuffer.wrap(payload), (FrameType)null);
               } catch (HTTP2Exception var8) {
                  this.logException(var8);
                  if (var8 instanceof ConnectionException) {
                     this.closeConnection(var8);
                  }
               } catch (IOException var9) {
                  this.logException(var9);
                  this.closeConnection();
               } catch (Exception var10) {
                  this.logException(var10);
                  this.closeConnection(var10);
               }
         }
      } while(this.isMessageComplete());

      this.requeue();
   }

   private void logException(Exception e) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("MuxableSocketHTTP2 catching Exception: ", e);
      }

   }

   private void validatePayloadSize(int payloadSize, FrameType frameType) {
      if (frameType == FrameType.WINDOW_UPDATE && payloadSize != 4 || frameType == FrameType.PING && payloadSize != 8 || frameType == FrameType.SETTINGS && payloadSize % 6 != 0) {
         ConnectionException e = new ConnectionException(MessageManager.getMessage("frameType.checkPayloadSize", FrameType.WINDOW_UPDATE, payloadSize), 6);
         this.closeConnection(e);
      }

   }

   private void copyDataTo(byte[] payload, int offset, int len) {
      int readPosInChunk = this.readPos;
      int writePos = 0;
      int readCount = 0;
      Chunk current = null;
      if (readPosInChunk + offset > Chunk.CHUNK_SIZE) {
         current = this.head.next;
         readPosInChunk = readPosInChunk + offset - Chunk.CHUNK_SIZE;
      } else {
         current = this.head;
         readPosInChunk += offset;
      }

      while(readCount < len && current != null) {
         int bytesNeedToCopy = len - readCount;
         int bytesInChunk = Chunk.CHUNK_SIZE - readPosInChunk;
         int copyLen = bytesNeedToCopy <= bytesInChunk ? bytesNeedToCopy : bytesInChunk;
         System.arraycopy(current.buf, readPosInChunk, payload, writePos, copyLen);
         writePos += copyLen;
         readCount += copyLen;
         readPosInChunk = 0;
         current = current.next;
      }

   }

   private int getPayloadSize() {
      int length = 0;

      for(int offset = 0; offset < 3; ++offset) {
         length <<= 8;
         length ^= this.getByte(offset) & 255;
      }

      return length;
   }

   private FrameType getFrameType() {
      return FrameType.valueOf(this.getByte(3) & 255);
   }

   private byte getByte(int offset) {
      int pos = this.readPos + offset;
      Chunk c = this.head;
      int i = 0;

      for(int j = pos / Chunk.CHUNK_SIZE; i < j; ++i) {
         c = c.next;
      }

      return c.buf[pos % Chunk.CHUNK_SIZE];
   }

   private void onPreface() throws HTTP2Exception, IOException {
      Map settings = this.localSettings.getPendingSettings();
      SettingsFrame sFrame = new SettingsFrame(0, 0, settings, false);
      sFrame.setRemoteMaxFrameSize(this.getRemoteSettings().getMaxFrameSize());
      sFrame.send(this);
   }

   public void requeue() {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("MuxableSocketHTTP2 requeue: availBytes=" + this.availBytes + ", readPos=" + this.readPos);
      }

      if (this.availBytes == 0) {
         this.reset();
      }

      this.registerForReadEvent();
   }

   public InputStream getInputStream() {
      return this.getSocketInputStream();
   }

   public OutputStream getOutputStream() {
      return this.getSocketOutputStream();
   }

   public ServerChannel getServerChannel() {
      return this.channel;
   }

   public boolean handleOnDemandContext(ServletRequestImpl request, String uri) throws IOException {
      OnDemandManager odm = request.getConnection().getConnectionHandler().getHttpServer().getOnDemandManager();
      OnDemandContext odc = odm.lookupOnDemandContext(uri);
      if (odc == null) {
         return false;
      } else {
         try {
            if (odc.isDisplayRefresh()) {
               odm.loadOnDemandURI(odc, true);
               request.getConnection().getConnectionHandler().sendRefreshPage(uri, odc.updateProgressIndicator());
               return true;
            } else {
               this.handleSyncOnDemandLoad(odm, odc, uri, request);
               return true;
            }
         } catch (DeploymentException var6) {
            request.getConnection().getConnectionHandler().sendError(503);
            HTTPLogger.logDispatchError(var6);
            return true;
         }
      }
   }

   void handleSyncOnDemandLoad(final OnDemandManager odm, final OnDemandContext ctx, final String uri, final ServletRequestImpl request) {
      Runnable runnable = new Runnable() {
         public void run() {
            try {
               odm.loadOnDemandURI(ctx, false);
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("About to perform rest of servlet context processing for " + ctx.getAppName());
               }

               request.getConnection().getConnectionHandler().resolveServletContext(uri);
            } catch (IOException var2) {
               HTTPLogger.logDispatchError(var2);
               MuxableSocketHTTP2.this.closeConnection(var2);
            } catch (DeploymentException var3) {
               HTTPLogger.logDispatchError(var3);
               MuxableSocketHTTP2.this.closeConnection(var3);
            }

         }
      };
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Scheduling runnable for on demand load of " + ctx.getAppName());
      }

      WorkManagerFactory.getInstance().getDefault().schedule(runnable);
   }

   public void setHeadChunk(Chunk c) {
   }

   public Chunk getHeadChunk() {
      return null;
   }

   public void setSocketReadTimeout(int timeout) throws SocketException {
      this.setSoTimeout(timeout);
   }

   private void releaseChunks(int preDataFrameLen) {
      int len = this.readPos + preDataFrameLen;
      int i = 0;

      for(int j = len / Chunk.CHUNK_SIZE; i < j; ++i) {
         Chunk temp = this.head.next;
         Chunk.releaseChunk(this.head);
         this.head = temp;
      }

      this.readPos = len % Chunk.CHUNK_SIZE;
   }

   public RemoteSettings getRemoteSettings() {
      return this.remoteSettings;
   }

   public LocalSettings getLocalSettings() {
      return this.localSettings;
   }

   public void registerForReadEvent() {
      if (this.secure && this.getSocketFilter() instanceof SSLFilter) {
         ((SSLFilter)this.getSocketFilter()).asyncOff();
      }

      SocketMuxer.getMuxer().read(this.getSocketFilter());
   }

   public synchronized int incrementSendWindowSize(int windowSizeIncrement) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("The SendWindowSize for Conn " + this.toString() + " is " + this.sendWindowSize + " and the increment is " + windowSizeIncrement);
      }

      this.sendWindowSize += (long)windowSizeIncrement;
      if (this.sendWindowSize > 2147483647L) {
         this.closeConnection(new ProtocalException("A sender MUST NOT allow a flow-control window to exceed max octets", 3));
      }

      if (this.sendWindowSize > 0L && windowSizeIncrement > 0) {
         this.notifyAll();
      }

      return (int)this.sendWindowSize;
   }

   public synchronized int incrementRecvWindowSize(int windowSizeIncrement) {
      this.recvWindowSize += (long)windowSizeIncrement;
      if (this.recvWindowSize > 2147483647L) {
         this.closeConnection(new ProtocalException("A sender MUST NOT allow a flow-control window to exceed max octets", 3));
      }

      return (int)this.recvWindowSize;
   }

   public void closeConnection(Throwable throwable) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Closing current connection: " + this.toString());
         if (throwable != null) {
            HTTPDebugLogger.debug(throwable.getMessage());
         }
      }

      if (throwable == null) {
         SocketMuxer.getMuxer().deliverEndOfStream(this.getSocketFilter());
      } else {
         byte[] debugData = null;
         if (throwable.getMessage() != null) {
            debugData = throwable.getMessage().getBytes(StandardCharsets.UTF_8);
         }

         GoAwayFrame frame = new GoAwayFrame(0, 0, this.sm.getMaxProcessedStreamId(), ((HTTP2Exception)throwable).getError(), debugData);
         frame.setRemoteMaxFrameSize(this.getRemoteSettings().getMaxFrameSize());
         frame.send(this);
         if (((HTTP2Exception)throwable).getError() == 0) {
            SocketMuxer.getMuxer().deliverEndOfStream(this.getSocketFilter());
         } else {
            SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), throwable);
         }
      }

      Iterator var6 = this.sm.streams.values().iterator();

      while(var6.hasNext()) {
         Stream stream = (Stream)var6.next();

         try {
            stream.receivedReset(true);
         } catch (HTTP2Exception var5) {
         }
      }

      this.isClosed = true;
      this.reset();
   }

   protected void closeConnection() {
      this.closeConnection(new ConnectionException("No Error", 0));
   }

   public void setMaxProcessedStreamId(int streamId) {
      this.sm.setMaxProcessedStreamId(streamId);
   }

   private void reset() {
      this.length = 0;
      this.readPos = 0;
      Chunk.releaseChunks(this.head);
      this.resetData();
   }

   public boolean isOnGoAway() {
      return this.isOnGoAway;
   }

   public boolean isConnectionClosed() {
      return this.isClosed;
   }

   public void closeTimeoutStreams(int timeoutSec) {
      if (this.sm.hasAliveStreams()) {
         Iterator var2 = this.sm.getAliveStreams().iterator();

         while(var2.hasNext()) {
            Stream stream = (Stream)var2.next();
            if (System.currentTimeMillis() - stream.getLastAccessTime() > (long)timeoutSec) {
               stream.close();
            }
         }
      }

   }

   public synchronized void sendBytes(byte[] output) {
      try {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Sending Frame: " + Arrays.toString(output));
         }

         this.getSocket().getOutputStream().write(output);
         this.getSocket().getOutputStream().flush();
      } catch (IOException var3) {
         var3.printStackTrace();
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Exception occurs when send Bytes to the peer!", var3);
         }
      }

   }

   public void tryTeminateConnection() {
      if (!this.sm.hasAliveStreams()) {
         this.closeConnection();
      }

   }

   public int getIdleTimeoutMillis() {
      return 120000;
   }

   static {
      PREFACE_BYTES = "PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n".getBytes(StandardCharsets.ISO_8859_1);
   }

   private class PingManager {
      private final long pingInterval;
      private long lastPing;
      private long minRoundTripTime;
      private long payload;
      private long cur;
      private ConcurrentMap sentPings;

      private PingManager() {
         this.pingInterval = 10000000000L;
         this.lastPing = Long.MIN_VALUE;
         this.payload = 0L;
         this.cur = 0L;
         this.sentPings = new ConcurrentHashMap();
      }

      public void sendPing() {
         long now = System.nanoTime();
         if (now - this.lastPing > 10000000000L) {
            this.lastPing = now;
            this.sentPings.put(++this.payload, now);
            PingFrame ping = new PingFrame(8, 0, 0);
            ping.setPayload(this.payload);
            ping.send(MuxableSocketHTTP2.this);
         }

      }

      public void onReceiving(PingFrame frame) {
         if (frame.isAck()) {
            long data = frame.getPayloadAsLong();

            while(this.cur < data) {
               this.sentPings.remove(Long.valueOf((long)(this.cur++)));
            }

            Long sentTime = (Long)this.sentPings.get(this.payload);
            if (sentTime != null) {
               long roundTripTime = System.nanoTime() - sentTime;
               if (roundTripTime < this.minRoundTripTime) {
                  this.minRoundTripTime = roundTripTime;
               }
            }
         } else {
            ByteBuffer output = ByteBuffer.allocate(9 + frame.getPayloadSize());
            Frame.generateAck(output, FrameType.PING, 0, frame.getPayload());
            MuxableSocketHTTP2.this.sendBytes(output.array());
         }

      }

      // $FF: synthetic method
      PingManager(Object x1) {
         this();
      }
   }

   private final class StreamManagerImpl implements StreamManager {
      private final ConcurrentMap streams = new ConcurrentHashMap();
      private final AtomicInteger activeRemoteStreams = new AtomicInteger(0);
      private final AtomicInteger activeLocalStreams = new AtomicInteger(0);
      private final AtomicInteger newLocalStreamId = new AtomicInteger(2);
      private volatile int maxStreamId;
      private volatile int maxProcessedStreamId;
      private final HTTP2Connection conn;

      public StreamManagerImpl(HTTP2Connection conn) {
         this.conn = conn;
      }

      public void removeStream(Integer streamId) {
         Stream stream = (Stream)this.streams.remove(streamId);
         if (stream != null) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Removing stream: " + stream.toString());
            }

         }
      }

      public Stream getStream(Integer streamId) {
         return (Stream)this.streams.get(streamId);
      }

      public void incrementInitSendWindowSize(int size) {
         Iterator var2 = this.streams.values().iterator();

         while(var2.hasNext()) {
            Stream stream = (Stream)var2.next();

            try {
               if (!stream.isClosed()) {
                  stream.incrementInitSendWindowSize(size);
               }
            } catch (ConnectionException var5) {
               MuxableSocketHTTP2.this.closeConnection(var5);
            }
         }

      }

      public void incrementInitRecvWindowSize(int size) {
         Iterator var2 = this.streams.values().iterator();

         while(var2.hasNext()) {
            Stream stream = (Stream)var2.next();

            try {
               if (!stream.isClosed()) {
                  stream.incrementInitRecvWindowSize(size);
               }
            } catch (ConnectionException var5) {
               MuxableSocketHTTP2.this.closeConnection(var5);
            }
         }

      }

      public boolean hasAliveStreams() {
         Iterator var1 = this.streams.values().iterator();

         Stream stream;
         do {
            if (!var1.hasNext()) {
               return false;
            }

            stream = (Stream)var1.next();
         } while(stream.isClosed());

         return true;
      }

      public List getAliveStreams() {
         List list = new ArrayList();
         Iterator var2 = this.streams.values().iterator();

         while(var2.hasNext()) {
            Stream stream = (Stream)var2.next();
            if (!stream.isClosed()) {
               list.add(stream);
            }
         }

         return list;
      }

      public StreamImpl createLocalStream() throws StreamException {
         Integer streamId = this.newLocalStreamId.getAndAdd(2);
         if (this.streams.get(streamId) != null) {
            return null;
         } else {
            StreamImpl newStream = new StreamImpl(streamId, this.conn, this, (ServletRequestImpl)null);
            this.streams.put(streamId, newStream);
            return newStream;
         }
      }

      public boolean canCreate(Integer streamId) {
         return streamId > this.maxStreamId;
      }

      public boolean isRemoteStream(Integer streamId) {
         return streamId % 2 == 1;
      }

      public StreamImpl createRemoteStream(Integer streamId) throws HTTP2Exception {
         if (streamId % 2 != 1) {
            throw new ConnectionException(MessageManager.getMessage("stream.remoteStream.even", streamId), 1);
         } else if (streamId <= this.maxStreamId) {
            throw new ConnectionException(MessageManager.getMessage("stream.remoteStream.old", streamId, this.maxStreamId), 1);
         } else {
            StreamImpl newStream = new StreamImpl(streamId, this.conn, this, (ServletRequestImpl)null);
            this.streams.put(streamId, newStream);
            this.maxStreamId = streamId;
            return newStream;
         }
      }

      public void closeUnprocessedStreams(int lastStreamId) {
         if (this.maxStreamId > lastStreamId) {
            for(int i = lastStreamId; i <= this.maxStreamId; i += 2) {
               ((Stream)this.streams.get(i)).close();
            }

         }
      }

      public void setMaxProcessedStreamId(int streamId) {
         if (streamId > this.maxProcessedStreamId) {
            this.maxProcessedStreamId = streamId;
         }

      }

      public int getMaxProcessedStreamId() {
         return this.maxProcessedStreamId;
      }
   }

   private static enum State {
      PREFACE,
      SETTINGS,
      FRAMES;
   }
}
