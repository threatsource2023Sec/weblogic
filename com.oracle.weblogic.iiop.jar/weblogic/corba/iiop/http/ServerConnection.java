package weblogic.corba.iiop.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.security.AccessController;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.ConnectionManager;
import weblogic.iiop.IIOPLoggerFacade;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.AsyncOutgoingMessage;
import weblogic.protocol.ChannelImpl;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.spi.Channel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.channels.SocketRuntimeImpl;
import weblogic.servlet.FutureServletResponse;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.collections.CircularQueue;
import weblogic.utils.io.Chunk;

class ServerConnection extends TunneledConnection {
   private static final boolean ASSERT = true;
   private static final DebugCategory debugTransport = Debug.getCategory("weblogic.iiop.transport");
   private static final DebugLogger debugIIOPTunneling = DebugLogger.getDebugLogger("DebugIIOPTunneling");
   private static ServerDebugMBean debugBean;
   private final CircularQueue queue = new CircularQueue();
   private FutureServletResponse pendingResponse;
   private HttpServletRequest pendingRequest;
   private long lastIdle;
   private Channel channel;

   private static ServerDebugMBean getDebugBean() {
      if (debugBean == null) {
         debugBean = ManagementService.getRuntimeAccess(getKernelId()).getServer().getServerDebug();
      }

      return debugBean;
   }

   protected static AuthenticatedSubject getKernelId() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private void removeClosedConnections(String[] closedConnections) {
      String[] var2 = closedConnections;
      int var3 = closedConnections.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String sockId = var2[var4];
         ServerConnection old = TunneledConnectionManager.removeFromActiveConnections(sockId, this.getChannel());
         if (old != null) {
            old.closeClientClosedConnection(this);
         }
      }

   }

   static String acceptConnection(HttpServletRequest req) throws ProtocolException {
      ServerChannel networkChannel = ServletTunnelingSupport.getServletTunnelingSupport().getChannel(req);
      String hostAddress = ServletTunnelingSupport.getServletTunnelingSupport().getHostAddress(req);
      String clientClosedIDs = req.getHeader("WL-Client-closed-ids");
      SocketRuntime runtime = new SocketRuntimeImpl(ServletTunnelingSupport.getServletTunnelingSupport().getSocketRuntime(req));
      String protocol = req.getScheme();
      if (!networkChannel.isTunnelingEnabled()) {
         throw new ProtocolException("HTTP tunneling is disabled for channel " + networkChannel.getChannelName());
      } else if (!isSupportedProtocol(protocol)) {
         throw new ProtocolException("Unknown protocol: '" + protocol + "'");
      } else {
         return acceptConnection(networkChannel, hostAddress, clientClosedIDs, runtime, protocol);
      }
   }

   private static String acceptConnection(ServerChannel networkChannel, String hostAddress, String clientClosedIDs, SocketRuntime runtime, String protocol) {
      ServerConnection newConnection = new ServerConnection(networkChannel, runtime);
      removeClosedConnections(newConnection, clientClosedIDs);
      newConnection.setListenPoint(new ListenPoint(hostAddress, Integer.parseInt(newConnection.getConnectionId())));
      newConnection.channel = new ChannelImpl(hostAddress, Integer.parseInt(newConnection.getConnectionId()), protocol);
      TunneledConnectionManager.getOpenSocketsMap(networkChannel).put(newConnection.getConnectionId(), newConnection);
      debugLogConnectionAccepted(newConnection);
      return newConnection.getConnectionId();
   }

   private static void debugLogConnectionAccepted(ServerConnection newConnection) {
      if (debugConnectionIssues()) {
         IIOPLoggerFacade.logDebugTransport("Opened tunneled connection - id: '" + newConnection.getConnectionId() + "', keyed on: " + newConnection.getListenPoint(), new Object[0]);
      }

   }

   private static boolean debugConnectionIssues() {
      return debugTransport.isEnabled() || debugIIOPTunneling.isDebugEnabled() || getDebugBean().getDebugTunnelingConnection();
   }

   private static void removeClosedConnections(ServerConnection newConnection, String clientClosedIDs) {
      if (clientClosedIDs != null && clientClosedIDs.length() > 0) {
         StringTokenizer st = new StringTokenizer(clientClosedIDs, "!");
         String serverHash = st.nextToken();
         if (Utils.isIntendedDestination(serverHash)) {
            String[] closedConnections = TunnelUtils.splitStringToArray(st.nextToken(), "|");
            newConnection.removeClosedConnections(closedConnections);
         }
      }

   }

   private static boolean isSupportedProtocol(String protocol) {
      return "http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol);
   }

   private Chunk readPacket(HttpServletRequest req) throws IOException {
      InputStream is = req.getInputStream();
      Chunk head = Chunk.getChunk();

      try {
         int nread = Utils.copyFromStreamToChunk(is, head);
         this.incrementBytesReceived(nread);
         if (nread < 12) {
            throw this.createTooFewBytesReadException(req, nread);
         } else {
            return head;
         }
      } catch (InterruptedIOException var5) {
         if (this.debugTunneling()) {
            IIOPLoggerFacade.logDebugTransport("Problem reading tunneled packet - nread: '" + var5.bytesTransferred + "' content-length: '" + req.getContentLength() + "'" + var5.getMessage(), new Object[0]);
         }

         throw var5;
      }
   }

   private ProtocolException createTooFewBytesReadException(HttpServletRequest req, int nread) {
      return new ProtocolException("Fewer than: '12' bytes read - nread: '" + nread + "', content-length: '" + req.getContentLength() + "', method: '" + req.getMethod() + "', uri: '" + req.getRequestURI() + "', path info: '" + req.getPathInfo() + "', query params: '" + req.getQueryString() + "'");
   }

   private ServerConnection(ServerChannel networkChannel, SocketRuntime sockRuntime) {
      super(sockRuntime, networkChannel);
      this.recordLastMessageReceiptTime();
      this.addServerConnectionRuntime();
   }

   public String toString() {
      return super.toString() + " - id: '" + this.getConnectionId() + "', closed: '" + this.isClosed() + "'";
   }

   public final Channel getRemoteChannel() {
      return this.channel;
   }

   final int getQueueCount() {
      return this.queue.size();
   }

   private synchronized void closeClientClosedConnection(ServerConnection newConnection) {
      if (!this.isClosed()) {
         String reason = "Closing HTTP tunneling connection: '" + this + "' because  Client closed this connection and a new connection request: '" + newConnection + " came in.";
         ConnectionManager.handleExceptionReceiving(this, new IOException(reason));
         if (this.debugTunneling()) {
            IIOPLoggerFacade.logDebugTransport(reason, new Object[0]);
         }

      }
   }

   private boolean debugTunneling() {
      return debugTransport.isEnabled() || debugIIOPTunneling.isDebugEnabled();
   }

   protected void handleClientTimeout() {
      boolean noPendingTasks = false;
      synchronized(this) {
         if (this.pendingResponse == null) {
            this.closeConnection();
            noPendingTasks = true;
         }
      }

      if (noPendingTasks) {
         String reason = "Timed out HTTP tunneling connection: '" + this + "' because it had been unavailable for: '" + this.getMsecSinceLastMessageReceived() / 1000L + "' seconds, timeout of: '" + this.getClientTimeoutSecs() + "' seconds.";
         ConnectionManager.handleExceptionReceiving(this, new IOException(reason));
         if (debugTimeoutIssues()) {
            IIOPLoggerFacade.logDebugTransport(reason, new Object[0]);
         }

      } else {
         long idleInterval = (long)(this.idleConnectionTimeout() * 1000);
         synchronized(this) {
            long idleDelta = this.lastIdle != -1L ? getCurrentTimeMsec() - this.lastIdle : -1L;
            if (idleDelta > idleInterval) {
               this.close();
            } else {
               this.recordLastMessageReceiptTime();
               this.logRequestRetryPrompt();
               this.requestClientRetry();
            }
         }
      }
   }

   private void requestClientRetry() {
      try {
         this.pendingResponse.setHeader("WL-Result", "RETRY");
         this.pendingResponse.getOutputStream().print("RETRY");
         this.pendingResponse.getOutputStream().flush();
      } catch (IOException var10) {
      } finally {
         try {
            this.pendingResponse.send();
         } catch (IOException var9) {
         }

         this.pendingRequest = null;
         this.pendingResponse = null;
      }

   }

   private void logRequestRetryPrompt() {
      if (debugTimeoutIssues()) {
         IIOPLoggerFacade.logDebugTransport("Pinging HTTP tunneling connection: '" + this + "' because it had been idle for: '" + this.getMsecSinceLastMessageReceived() / 1000L + "' seconds, timeout of: '" + this.getClientTimeoutSecs() + "' seconds.", new Object[0]);
      }

   }

   private int idleConnectionTimeout() {
      return this.getChannel().getIdleConnectionTimeout();
   }

   protected int getClientTimeoutSecs() {
      return this.getChannel().getTunnelingClientTimeoutSecs();
   }

   private static boolean debugTimeoutIssues() {
      return debugTransport.isEnabled() || debugIIOPTunneling.isDebugEnabled() || getDebugBean().getDebugTunnelingConnectionTimeout();
   }

   final synchronized AsyncOutgoingMessage getNextMessage() {
      this.recordLastMessageReceiptTime();
      return (AsyncOutgoingMessage)this.queue.remove();
   }

   final synchronized void registerPending(HttpServletRequest req, FutureServletResponse rsp) throws IOException {
      if (this.isClosed()) {
         try {
            Utils.sendDeadResponse(rsp);
         } catch (IOException var5) {
         }
      }

      this.recordLastMessageReceiptTime();
      if (this.lastIdle == -1L) {
         this.lastIdle = this.getLastMessageReceivedTime();
      }

      if (this.pendingResponse != null && this.pendingRequest != null) {
         String conId = this.pendingRequest.getParameter("connectionID");
         String rand = this.pendingRequest.getParameter("rand");
         if (conId != null && rand != null && conId.equals(req.getParameter("connectionID")) && rand.equals(req.getParameter("rand"))) {
            this.pendingRequest = null;
            this.pendingResponse = null;
         }
      }

      Debug.assertion(this.pendingResponse == null);
      this.pendingRequest = req;
      this.pendingResponse = rsp;
   }

   public final synchronized void send(AsyncOutgoingMessage msg) throws IOException {
      if (this.isClosed()) {
         throw new IOException("ServerConnection closed");
      } else if (this.pendingResponse == null) {
         msg.enqueue();
         if (!this.queue.add(msg)) {
            this.close();
            throw new IOException();
         }
      } else {
         this.recordLastMessageReceiptTime();
         this.updateMessageSentStatistics(msg);

         try {
            msg.enqueue();
            Utils.sendResponse(this.pendingResponse, msg);
         } finally {
            try {
               this.pendingResponse.send();
            } finally {
               this.pendingRequest = null;
               this.pendingResponse = null;
            }
         }

      }
   }

   final void dispatch(HttpServletRequest req) throws IOException {
      if (this.isClosed()) {
         throw new IOException("Socket is closed");
      } else {
         try {
            this.recordLastMessageReceiptTime();
            this.lastIdle = -1L;
            this.incrementMessagedReceived();
            ConnectionManager.dispatch(this, this.readPacket(req));
         } catch (IOException var3) {
            if (this.debugTunneling()) {
               IIOPLoggerFacade.logDebugTransport("Problem dispatching tunneled message to: '" + this + "'", new Object[0]);
            }

            throw var3;
         }
      }
   }

   public final void close() {
      if (!this.isClosed()) {
         synchronized(this) {
            this.closeConnection();
            this.logConnectionClosed();
            if (this.pendingResponse != null) {
               this.sendDeadResponse();
            }

            this.removeServerConnectionRuntime();
         }
      }
   }

   private void sendDeadResponse() {
      try {
         Utils.sendDeadResponse(this.pendingResponse);
         this.pendingResponse.getOutputStream().flush();
      } catch (IOException var10) {
      } finally {
         try {
            this.pendingResponse.send();
         } catch (IOException var9) {
         }

         this.pendingRequest = null;
         this.pendingResponse = null;
      }

   }

   private void logConnectionClosed() {
      if (debugTimeoutIssues()) {
         IIOPLoggerFacade.logDebugTransport("Closing tunneled socket: '" + this + "'" + new Throwable("Stack trace"), new Object[0]);
      }

   }
}
