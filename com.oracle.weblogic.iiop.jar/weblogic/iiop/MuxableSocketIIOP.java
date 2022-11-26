package weblogic.iiop;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.AccessController;
import javax.security.auth.login.LoginException;
import org.omg.CORBA.portable.ObjectImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.messages.MessageHeaderConstants;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.protocol.AsyncMessageSenderImpl;
import weblogic.protocol.AsyncOutgoingMessage;
import weblogic.protocol.ChannelImpl;
import weblogic.protocol.MessageSender;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.spi.Channel;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.auth.login.PasswordCredential;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.SocketMuxer;
import weblogic.utils.io.Chunk;

class MuxableSocketIIOP extends AbstractMuxableSocket implements MessageSender, MessageHeaderConstants {
   private static final int INITIAL_SO_TIMEOUT = 60000;
   private static final int CONNECT_TIMEOUT = Integer.getInteger("weblogic.iiop.connectTimeout", 0);
   private static final int MAX_MESSAGE_LENGTH = 134217728;
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private Runnable heartbeatPinger = new HeartbeatPinger();
   private static final ConnectAction LIVE_CONNECT_ACTION = new ConnectAction() {
      public Socket createSocket(MuxableSocketIIOP muxable, InetAddress address, int port) throws IOException {
         return muxable.createSocket(address, port);
      }

      public void register(MuxableSocketIIOPS muxable, Socket sslSocket, boolean isClient) throws IOException {
         muxable.register(sslSocket, isClient);
      }
   };
   static ConnectAction connectAction;
   private static final boolean DEBUG = false;
   private static final int CONNECT_MAX_RETRY = 1;
   private static final int BACKOFF_INTERVAL;
   private static boolean enabled;
   private boolean timeoutPingFailed = false;
   private AuthenticatedSubject subject = null;
   private static final AuthenticatedSubject kernelId;
   private final IIOPConnection connection = new IIOPConnection();
   private ListenPoint listenPoint;
   private Channel channel;

   static PrincipalAuthenticator getPrincipalAuthenticator(AuthenticatedSubject kernelId) {
      return RmiSecurityFacade.getPrincipalAuthenticator(kernelId, "weblogicDEFAULT");
   }

   void setSubject(AuthenticatedSubject sub) {
      this.subject = sub;
   }

   protected AuthenticatedSubject getSubject() {
      return this.subject;
   }

   private AuthenticatedSubject authenticateLocally(UserInfo ui) {
      if (ui instanceof DefaultUserInfoImpl) {
         DefaultUserInfoImpl info = (DefaultUserInfoImpl)ui;
         String username = info.getName();
         String password = info.getPassword();
         if (username != null && username.length() != 0) {
            PrincipalAuthenticator pa = getPrincipalAuthenticator(kernelId);

            try {
               SimpleCallbackHandler handler = new SimpleCallbackHandler(username, password);
               AuthenticatedSubject subject = pa.authenticate(handler, this);
               PasswordCredential passwordCred = new PasswordCredential(username, password);
               subject.getPrivateCredentials(kernelId).add(passwordCred);
               return subject;
            } catch (LoginException var9) {
               throw new SecurityException("User failed to be authenticated: " + var9.getMessage());
            }
         } else {
            return null;
         }
      } else {
         throw new SecurityException("Received bad UserInfo: " + ui.getClass().getName());
      }
   }

   protected static void p(String s) {
      System.err.println("<MuxableSocketIIOP:" + System.currentTimeMillis() + "> " + s);
   }

   public static void initialize() {
      enabled = IiopConfigurationFacade.isIiopEnabled();
   }

   static void disable() {
      enabled = false;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public MuxableSocketIIOP(Chunk headChunk, Socket s, ServerChannel nc) throws IOException {
      super(headChunk, s, nc);
      this.setSoTimeout(60000);
      this.listenPoint = new ListenPoint(this.getSocket().getInetAddress().getHostAddress(), this.getSocket().getPort());
      this.channel = new ChannelImpl(this.getSocket().getInetAddress().getHostAddress(), this.getSocket().getPort(), ProtocolHandlerIIOP.PROTOCOL_IIOP.getProtocolName());
   }

   protected MuxableSocketIIOP(ServerChannel networkChannel) {
      super(networkChannel);
   }

   public final void connect(InetAddress address, int port) throws IOException {
      super.connect(connectAction.createSocket(this, address, port));
      this.setSoTimeout(60000);
      this.listenPoint = new ListenPoint(address.getHostAddress(), port, this.getSocket().getLocalPort());
      this.channel = new ChannelImpl(address.getHostAddress(), port, ProtocolHandlerIIOP.PROTOCOL_IIOP.getProtocolName());
   }

   protected final Socket createSocket(InetAddress address, int port) throws IOException {
      int i = 0;

      while(true) {
         try {
            return this.newSocket(address, port);
         } catch (SocketException var7) {
            if (i == 1) {
               throw var7;
            }

            try {
               Thread.sleep((long)(Math.random() * (double)(BACKOFF_INTERVAL << i)));
            } catch (InterruptedException var6) {
            }

            ++i;
         }
      }
   }

   private Socket newSocket(InetAddress host, int port) throws IOException {
      return super.createSocket(host, port, CONNECT_TIMEOUT);
   }

   public final int getIdleTimeoutMillis() {
      int timeout = super.getIdleTimeoutMillis();
      if (EndPointManager.hasPendingResponses((Connection)this.connection)) {
         if (this.connection.getChannel().getTimeoutConnectionWithPendingResponses()) {
            timeout *= IiopConfigurationFacade.getPendingResponseTimeout();
         } else {
            timeout = 0;
         }
      }

      if (timeout == 0 && this.connection.getHeartbeatStub() != null) {
         timeout = IiopConfigurationFacade.getKeepAliveTimeout();
      }

      return timeout;
   }

   public final void dispatch(Chunk list) {
      ConnectionManager.dispatch(this.connection, list);
   }

   protected int getMessageLength() {
      int byteOrder = this.getHeaderByte(6) & 1;
      int b1 = this.getHeaderByte(8) & 255;
      int b2 = this.getHeaderByte(9) & 255;
      int b3 = this.getHeaderByte(10) & 255;
      int b4 = this.getHeaderByte(11) & 255;
      int len;
      if (byteOrder == 0) {
         len = (b1 << 24 | b2 << 16 | b3 << 8 | b4) + 12;
      } else {
         len = (b4 << 24 | b3 << 16 | b2 << 8 | b1) + 12;
      }

      assert len >= 0 && len < 134217728;

      return len;
   }

   protected int getHeaderLength() {
      return 12;
   }

   public final void hasException(Throwable t) {
      boolean sendExceptionUp = false;
      if (this.getSocket() != null) {
         sendExceptionUp = true;
      }

      if (sendExceptionUp) {
         new ConnectionShutdownHandler(this.connection, t);
      }

   }

   public final void endOfStream() {
      boolean sendExceptionUp = false;
      if (this.getSocket() != null) {
         sendExceptionUp = true;
      }

      if (sendExceptionUp) {
         EOFException eofException = new EOFException("endOfStream called by muxer connected to " + this.getSocket().getInetAddress());
         if (debugIIOPDetail.isDebugEnabled()) {
            debugIIOPDetail.debug(eofException.getMessage(), eofException);
         }

         IIOPLoggerFacade.logDebugTransport("Received and undelivered data:\n%s", new Object[]{this.getChunk()});
         new ConnectionShutdownHandler(this.connection, eofException, false);
      }

   }

   public boolean isDead() {
      return !this.getSocketFilter().getSocketInfo().touch();
   }

   public final boolean timeout() {
      new ConnectionShutdownHandler(this.connection, new EOFException("Idle connection was timed out"));
      return false;
   }

   public final boolean requestTimeout() {
      if (this.connection.getHeartbeatStub() != null && !this.timeoutPingFailed) {
         IiopConfigurationFacade.runAsynchronously(this.heartbeatPinger);
         return false;
      } else {
         return true;
      }
   }

   private void updateHeartbeatStatus() {
      try {
         this.sendHeartbeatPing();
      } catch (Throwable var2) {
         IIOPLoggerFacade.logHeartbeatPeerClosed();
         this.timeoutPingFailed = true;
      }

   }

   private void sendHeartbeatPing() {
      this.timeoutPingFailed = this.isRemoteHeartbeatStubMissing();
      if (this.timeoutPingFailed) {
         IIOPLoggerFacade.logHeartbeatPeerClosed();
      } else if (IIOPLoggerFacade.isTransportDebugEnabled()) {
         IIOPLoggerFacade.logDebugTransport("Heartbeat sent successfully to: %", new Object[]{this.connection.getHeartbeatStub()});
      }

   }

   private boolean isRemoteHeartbeatStubMissing() {
      return ((ObjectImpl)this.connection.getHeartbeatStub())._non_existent();
   }

   public String toString() {
      return super.toString() + ", key = " + this.listenPoint + ", raw socket = " + this.getSocket();
   }

   public Connection getConnection() {
      return this.connection;
   }

   public final void send(OutgoingMessage msg) throws IOException {
      OutputStream sos = this.getSocketOutputStream();
      if (this.isClosed()) {
         throw new EOFException("Attempt to send message on closed socket");
      } else {
         try {
            this.getSocketFilter().getSocketInfo().touch();
         } catch (Throwable var4) {
         }

         msg.writeTo(sos);
      }
   }

   protected final void cleanup() {
      super.cleanup();
      SocketMuxer.getMuxer().deliverEndOfStream(this.getSocketFilter());
      SocketMuxer.getMuxer().finishExceptionHandling(this.getSocketFilter());
   }

   public AuthenticatedSubject getUser() {
      return this.subject != null ? this.subject : RmiSecurityFacade.getAnonymousSubject();
   }

   public void authenticate(UserInfo ui) throws SecurityException {
      if (ui != null) {
         if (ui instanceof AuthenticatedUser) {
            this.subject = RmiSecurityFacade.getASFromAUInServerOrClient((AuthenticatedUser)ui);
         } else {
            this.subject = this.authenticateLocally(ui);
         }

      }
   }

   protected boolean isSecure() {
      return false;
   }

   static {
      connectAction = LIVE_CONNECT_ACTION;
      BACKOFF_INTERVAL = IiopConfigurationFacade.getBackoffInterval();
      enabled = false;
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   interface ConnectAction {
      Socket createSocket(MuxableSocketIIOP var1, InetAddress var2, int var3) throws IOException;

      void register(MuxableSocketIIOPS var1, Socket var2, boolean var3) throws IOException;
   }

   private class IIOPConnection extends Connection {
      private final AsyncMessageSenderImpl sender;
      private Object txContext;

      private IIOPConnection() {
         this.txContext = null;
         this.sender = new AsyncMessageSenderImpl(MuxableSocketIIOP.this);
         MuxableSocketIIOP.this.addSenderStatistics(this.sender);
      }

      public final EndPoint createEndPoint() {
         return IiopConfigurationFacade.createEndPoint(this);
      }

      public Object getTxContext() {
         return this.txContext;
      }

      public void setTxContext(Object tx) {
         this.txContext = tx;
      }

      public void authenticate(UserInfo ui) throws SecurityException {
         MuxableSocketIIOP.this.authenticate(ui);
      }

      public AuthenticatedSubject getUser() {
         return MuxableSocketIIOP.this.getUser();
      }

      public final void send(AsyncOutgoingMessage msg) throws IOException {
         this.sender.send(msg);
      }

      public final ServerChannel getChannel() {
         return MuxableSocketIIOP.this.getChannel();
      }

      public final boolean isClosed() {
         return MuxableSocketIIOP.this.isClosed();
      }

      public final void close() {
         MuxableSocketIIOP.this.close();
      }

      public final ListenPoint getListenPoint() {
         return MuxableSocketIIOP.this.listenPoint;
      }

      public final Channel getRemoteChannel() {
         return MuxableSocketIIOP.this.channel;
      }

      public final ContextHandler getContextHandler() {
         return MuxableSocketIIOP.this;
      }

      public final void setListenPoint(ListenPoint newListenPoint) {
         MuxableSocketIIOP.this.listenPoint = newListenPoint;
         MuxableSocketIIOP.this.channel = new ChannelImpl(MuxableSocketIIOP.this.listenPoint.getAddress(), MuxableSocketIIOP.this.listenPoint.getPort(), ProtocolHandlerIIOP.PROTOCOL_IIOP.getProtocolName());
      }

      protected boolean isSecure() {
         return MuxableSocketIIOP.this.isSecure();
      }

      // $FF: synthetic method
      IIOPConnection(Object x1) {
         this();
      }
   }

   private class HeartbeatPinger implements Runnable {
      private HeartbeatPinger() {
      }

      public void run() {
         MuxableSocketIIOP.this.updateHeartbeatStatus();
      }

      // $FF: synthetic method
      HeartbeatPinger(Object x1) {
         this();
      }
   }
}
