package weblogic.rjvm.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.PartitionTable;
import weblogic.management.runtime.ServerConnectionRuntime;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rjvm.ConnectionManager;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMImpl;
import weblogic.rjvm.RJVMLogger;
import weblogic.rjvm.WebRjvmSupport;
import weblogic.security.service.ContextHandler;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.server.channels.ServerConnectionRuntimeImpl;
import weblogic.server.channels.SocketRuntimeImpl;
import weblogic.servlet.FutureServletResponse;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.collections.CircularQueue;
import weblogic.utils.io.Chunk;

class HTTPServerJVMConnection extends MsgAbbrevJVMConnection {
   private static final boolean DEBUG = false;
   private static final DebugLogger debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");
   private static final DebugLogger debugTunnelingConnection = DebugLogger.getDebugLogger("DebugTunnelingConnection");
   private static final DebugLogger debugTunnelingConnectionTimeout = DebugLogger.getDebugLogger("DebugTunnelingConnectionTimeout");
   private SocketRuntime sockRuntime;
   private ContextHandler contextHandler;
   private ServerChannel channel;
   private static final ConcurrentHashMap channelOpenSocksMap = new ConcurrentHashMap();
   private static long idCount = 0L;
   private static boolean enableMsgAck = true;
   private byte[] latestSentMessage = null;
   private int latestSentMessageSize = 0;
   private long latestReceiveRequestIdentifier = 0L;
   private long latestSendRequestIdentifier = 0L;
   final String sockID = getNextID();
   private final CircularQueue queue = new CircularQueue();
   private FutureServletResponse pending;
   private long lastRecv;
   private boolean closed;
   private int localPort;
   private InetAddress localAddress = null;

   private static void initialize(ServerChannel networkChannel) {
      int millis = networkChannel.getTunnelingClientPingSecs() * 1000;
      TimerManagerFactory.getTimerManagerFactory().getTimerManager("HTTPTunScavanger", "weblogic.kernel.System").schedule(new TunnelScavenger(networkChannel), (long)millis, (long)millis);
   }

   private static synchronized String getNextID() {
      return String.valueOf((long)(idCount++));
   }

   public static HTTPServerJVMConnection acceptJVMConnection(HttpServletRequest req, int abbrevTableSize, int headerLength, int peerChannelMaxMessageSize, HttpServletResponse rsp, String partitionUrl, String remotePartitionName) throws ProtocolException {
      ServerChannel networkChannel = WebRjvmSupport.getWebRjvmSupport().getChannel(req);
      if (!networkChannel.isTunnelingEnabled()) {
         throw new ProtocolException("HTTP tunneling is disabled");
      } else {
         SocketRuntime runtime = new SocketRuntimeImpl(WebRjvmSupport.getWebRjvmSupport().getSocketRuntime(req));
         String protocol = req.getScheme();
         if (partitionUrl == null) {
            partitionUrl = req.getRequestURL().toString();
         }

         Object newConnection;
         if ("https".equalsIgnoreCase(protocol)) {
            newConnection = new HTTPSServerJVMConnection(req, abbrevTableSize, headerLength, peerChannelMaxMessageSize, runtime, WebRjvmSupport.getWebRjvmSupport().getChannel(req), WebRjvmSupport.getWebRjvmSupport().getContextHandler(req), partitionUrl, remotePartitionName);
            if (ChannelHelper.isAdminChannel(networkChannel)) {
               ((HTTPServerJVMConnection)newConnection).setAdminQOS();
            }
         } else {
            if (!"http".equalsIgnoreCase(protocol)) {
               throw new ProtocolException("Unknown protocol: '" + protocol + '\'');
            }

            newConnection = new HTTPServerJVMConnection(abbrevTableSize, headerLength, peerChannelMaxMessageSize, runtime, WebRjvmSupport.getWebRjvmSupport().getChannel(req), WebRjvmSupport.getWebRjvmSupport().getContextHandler(req), partitionUrl, remotePartitionName);
         }

         ((HTTPServerJVMConnection)newConnection).setLocalPort(req.getServerPort());
         ((HTTPServerJVMConnection)newConnection).setProxied(true);
         if (rsp != null) {
            ((HTTPServerJVMConnection)newConnection).setLocalAddress(WebRjvmSupport.getWebRjvmSupport().getSocketLocalAddress(req));
         }

         ConcurrentHashMap openSocks = (ConcurrentHashMap)channelOpenSocksMap.get(networkChannel);
         if (openSocks == null) {
            synchronized(channelOpenSocksMap) {
               openSocks = (ConcurrentHashMap)channelOpenSocksMap.get(networkChannel);
               if (openSocks == null) {
                  initialize(networkChannel);
                  openSocks = new ConcurrentHashMap();
                  channelOpenSocksMap.put(networkChannel, openSocks);
               }
            }
         }

         openSocks.put(((HTTPServerJVMConnection)newConnection).sockID, newConnection);
         if (debugTunnelingConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("Opened connection - id: '" + ((HTTPServerJVMConnection)newConnection).sockID + '\'');
         }

         return (HTTPServerJVMConnection)newConnection;
      }
   }

   static HTTPServerJVMConnection findByID(String id) {
      Iterator i = channelOpenSocksMap.values().iterator();

      ConcurrentHashMap openSocks;
      do {
         if (!i.hasNext()) {
            return null;
         }

         openSocks = (ConcurrentHashMap)i.next();
      } while(!openSocks.containsKey(id));

      return (HTTPServerJVMConnection)openSocks.get(id);
   }

   private static Chunk readPacket(HttpServletRequest req) throws IOException {
      Chunk head = Chunk.getChunk();
      InputStream is = req.getInputStream();
      Debug.assertion(is != null);

      try {
         int nread = Chunk.chunkFully(head, is);
         if (nread < 4) {
            throw new ProtocolException("Fewer than: '4' bytes read - nread: '" + nread + "', content-length: '" + req.getContentLength() + "', method: '" + req.getMethod() + "', uri: '" + req.getRequestURI() + "', path info: '" + req.getPathInfo() + "', query params: '" + req.getQueryString() + '\'');
         }
      } finally {
         is.close();
      }

      return head;
   }

   HTTPServerJVMConnection(int abbrevTableSize, int headerLength, int peerChannelMaxMessageSize, SocketRuntime sockRuntime, ServerChannel channel, ContextHandler contextHandler, String partitionUrl, String remotePartitionName) {
      this.channel = channel;
      this.contextHandler = contextHandler;

      String localPName;
      try {
         localPName = PartitionTable.getInstance().lookup(partitionUrl).getPartitionName();
      } catch (URISyntaxException var12) {
         if (debugMessaging.isDebugEnabled()) {
            RJVMLogger.logDebug("Partition URL lookup failed with: " + var12);
         }

         localPName = "DOMAIN";
      }

      this.init(abbrevTableSize, headerLength, peerChannelMaxMessageSize, localPName, partitionUrl, remotePartitionName);
      this.closed = false;
      this.lastRecv = System.currentTimeMillis();
      this.sockRuntime = sockRuntime;
      ServerConnectionRuntime runtime = new ServerConnectionRuntimeImpl(this, this, sockRuntime);
      ServerChannel c = this.getChannel();
      if (c instanceof ServerChannelImpl && ((ServerChannelImpl)c).getRuntime() != null) {
         ((ServerChannelImpl)c).getRuntime().addServerConnectionRuntime(runtime);
      }

      this.setDispatcher(ConnectionManager.create((RJVMImpl)null), false);
   }

   public final String toString() {
      return super.toString() + " - id: '" + this.sockID + "', closed: '" + this.closed + "', lastRecv: '" + this.lastRecv + '\'';
   }

   public final void setLocalAddress(InetAddress addr) {
      this.localAddress = addr;
   }

   public final InetAddress getLocalAddress() {
      return this.localAddress;
   }

   final void setLocalPort(int i) {
      this.localPort = i;
   }

   public final ServerChannel getChannel() {
      return this.channel;
   }

   public InetAddress getInetAddress() {
      return null;
   }

   public final ContextHandler getContextHandler() {
      return this.contextHandler;
   }

   public final int getLocalPort() {
      return this.localPort == 0 ? -1 : this.localPort;
   }

   public final boolean verifyReceiveRequestIdentifier(long reqId, FutureServletResponse rsp) throws IOException {
      if (!enableMsgAck) {
         return true;
      } else if (this.pending != null) {
         if (debugTunnelingConnection.isDebugEnabled()) {
            debugTunnelingConnection.debug("ASSERTION ERROR. DUPLICATE INCOMING REQUEST:" + reqId + ",existing id:" + this.latestReceiveRequestIdentifier);
         }

         throw new IOException("assertion error. pending response exists on same connection.");
      } else if (this.latestReceiveRequestIdentifier != reqId) {
         if (debugTunnelingConnection.isDebugEnabled()) {
            debugTunnelingConnection.debug("recv verified:" + this.latestReceiveRequestIdentifier + ",incoming:" + reqId);
         }

         this.latestSentMessage = null;
         this.latestSentMessageSize = 0;
         this.latestReceiveRequestIdentifier = reqId;
         return true;
      } else {
         if (debugTunnelingConnection.isDebugEnabled()) {
            debugTunnelingConnection.debug("recv verify failed. RESPONSE MESSAGE WAS LOST ON " + reqId + " RESENDING...");
         }

         int msgSize = this.latestSentMessageSize;
         byte[] msgBytes = this.latestSentMessage;
         if (msgSize != 0 && msgBytes != null) {
            rsp.setContentLength(msgSize);
            rsp.setHeader("WL-Result", "OK");
            rsp.getOutputStream().write(msgBytes);
         }

         return false;
      }
   }

   public final boolean verifySendRequestIdentifier(long reqId) {
      if (!enableMsgAck) {
         return true;
      } else {
         boolean verified = this.latestSendRequestIdentifier != reqId;
         if (debugTunnelingConnection.isDebugEnabled()) {
            if (verified) {
               debugTunnelingConnection.debug("send verified: " + this.latestSendRequestIdentifier + ",incoming:" + reqId);
            } else {
               debugTunnelingConnection.debug("send verify failed. RESPONSE MESSAGE WAS DUPLICATED ON " + reqId);
            }
         }

         return verified;
      }
   }

   public final void updateSendRequestIdentifier(long reqId) {
      if (enableMsgAck) {
         this.latestSendRequestIdentifier = reqId;
      }
   }

   final int getQueueCount() {
      return this.queue.size();
   }

   private synchronized void checkIsDead() {
      ServerChannel sc = this.getChannel();
      int interval = sc.getTunnelingClientTimeoutSecs() * 1000;
      if (interval != 0) {
         long now = System.currentTimeMillis();
         long delta = now - this.lastRecv;
         if ((long)interval < delta) {
            ConcurrentHashMap openSocks = (ConcurrentHashMap)channelOpenSocksMap.get(sc);
            if (this.pending == null) {
               openSocks.remove(this.sockID);
               this.closed = true;
               String reason = "Timed out HTTP tunneling connection: '" + this + "' because it had been unavailable for: '" + delta / 1000L + "' seconds, timeout of: '" + interval / 1000 + "' seconds.";
               this.gotExceptionReceiving(new IOException(reason));
               if (debugTunnelingConnectionTimeout.isDebugEnabled()) {
                  RJVMLogger.logDebug(reason);
               }

            } else {
               if (debugTunnelingConnectionTimeout.isDebugEnabled()) {
                  RJVMLogger.logDebug("Pinging HTTP tunneling connection: '" + this + "' because it had been idle for: '" + delta / 1000L + "' seconds, timeout of: '" + interval / 1000 + "' seconds.");
               }

               this.lastRecv = now;
               this.retryTunnelRequest();
            }
         }
      }
   }

   private synchronized void retryTunnelRequest() {
      FutureServletResponse usingResp = this.pending;
      this.pending = null;

      try {
         usingResp.setHeader("WL-Result", "RETRY");
         usingResp.getOutputStream().print("RETRY");
      } catch (IOException var11) {
      } finally {
         try {
            usingResp.send();
         } catch (IOException var10) {
         }

      }

   }

   final synchronized OutgoingMessage getNextMessage() {
      this.lastRecv = System.currentTimeMillis();
      return (OutgoingMessage)this.queue.remove();
   }

   final void writeMessage(OutgoingMessage msg, FutureServletResponse rsp) throws IOException {
      int length = msg.getLength();
      rsp.setContentType("application/octet-stream");
      ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
      msg.writeTo(baos);
      byte[] messageBytes = baos.toByteArray();
      rsp.setContentLength(length);
      rsp.setHeader("WL-Result", "OK");
      rsp.getOutputStream().write(messageBytes);
      if (enableMsgAck) {
         this.latestSentMessage = messageBytes;
         this.latestSentMessageSize = length;
      }

   }

   public final void connect(String host, InetAddress addr, int port, int connectTimeout) throws IOException {
      throw new UnsupportedOperationException("HTTPServerJVMConnection doesn't connect!");
   }

   final synchronized void registerPending(FutureServletResponse rsp) {
      if (this.closed) {
         try {
            Utils.sendDeadResponse(rsp);
         } catch (IOException var3) {
         }
      }

      this.lastRecv = System.currentTimeMillis();
      if (this.pending != null) {
         if (debugMessaging.isDebugEnabled()) {
            RJVMLogger.logDebug("Previous pending response for tunneling retry is not sent out yet. Send it now.");
         }

         this.retryTunnelRequest();
      }

      this.pending = rsp;
   }

   public final synchronized void sendMsg(OutgoingMessage msg) throws IOException {
      if (this.closed) {
         throw new IOException("HTTPServerJVMConnection closed");
      } else if (this.pending == null) {
         if (!this.queue.add(msg)) {
            this.close();
            throw new IOException();
         }
      } else {
         this.lastRecv = System.currentTimeMillis();
         FutureServletResponse usingResp = this.pending;
         this.pending = null;

         try {
            this.writeMessage(msg, usingResp);
         } finally {
            usingResp.send();
         }

      }
   }

   final void dispatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
      if (this.isClosed()) {
         throw new IOException("Socket is closed");
      } else {
         try {
            super.dispatch(readPacket(req));
         } catch (IOException var4) {
            throw var4;
         }
      }
   }

   final synchronized boolean isClosed() {
      return this.closed;
   }

   public final synchronized void close() {
      if (!this.closed) {
         this.closed = true;
         ConcurrentHashMap openSocks = (ConcurrentHashMap)channelOpenSocksMap.get(this.getChannel());
         openSocks.remove(this.sockID);
         if (debugTunnelingConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("Closing JVM socket: '" + this + '\'' + new Throwable("Stack trace"));
         }

         if (this.pending != null) {
            FutureServletResponse usingResp = this.pending;
            this.pending = null;

            try {
               Utils.sendDeadResponse(usingResp);
            } catch (IOException var12) {
            } finally {
               try {
                  usingResp.send();
               } catch (IOException var11) {
               }

            }
         }

         ServerChannel c = this.getChannel();
         if (c instanceof ServerChannelImpl && ((ServerChannelImpl)c).getRuntime() != null) {
            ((ServerChannelImpl)c).getRuntime().removeServerConnectionRuntime(this.sockRuntime);
         }

      }
   }

   static final class TunnelScavenger implements NakedTimerListener {
      ServerChannel networkChannel;
      ServerChannelImpl networkChannelimpl;

      TunnelScavenger(ServerChannel networkChannel) {
         this.networkChannel = networkChannel;
         if (networkChannel instanceof ServerChannelImpl) {
            this.networkChannelimpl = (ServerChannelImpl)networkChannel;
         }

      }

      public final void timerExpired(Timer t) {
         ConcurrentHashMap openSocks = (ConcurrentHashMap)HTTPServerJVMConnection.channelOpenSocksMap.get(this.networkChannel);
         Iterator i = openSocks.values().iterator();

         while(i.hasNext()) {
            HTTPServerJVMConnection connection = (HTTPServerJVMConnection)i.next();
            connection.checkIsDead();
         }

         if (openSocks.isEmpty() && this.networkChannelimpl != null && this.networkChannelimpl.getRuntime() == null) {
            HTTPServerJVMConnection.channelOpenSocksMap.remove(this.networkChannel);
            this.networkChannel = null;
            this.networkChannelimpl = null;
            t.cancel();
         }

      }
   }
}
