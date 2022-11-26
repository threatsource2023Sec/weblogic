package weblogic.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.util.Locale;
import javax.net.ssl.SSLSocket;
import weblogic.logging.Loggable;
import weblogic.management.runtime.ServerConnectionRuntime;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.security.net.ConnectionEvent;
import weblogic.security.net.ConnectionFilterService;
import weblogic.security.net.FilterException;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.socket.utils.JSSEUtils;
import weblogic.utils.LocatorUtilities;

public final class MuxableSocketDiscriminator extends AbstractMuxableSocket {
   private static final long serialVersionUID = -7142489085349898142L;
   private static final boolean DEBUG = false;
   private final ServerChannel[] channels;
   private int timeoutMillis;
   private ProtocolHandler claimedHandler;
   private ServerChannel claimedChannel;

   public MuxableSocketDiscriminator(Socket s, ProtocolHandler[] handlers, ServerChannel[] channels) throws IOException {
      super(channels[0]);
      throw new IOException("old constructor to be removed");
   }

   public MuxableSocketDiscriminator(Socket s, ServerChannel[] channels) throws IOException {
      super(channels[0]);
      this.connect(s);
      this.setSoTimeout(this.timeoutMillis = this.getSocket().getSoTimeout());
      this.channels = channels;
   }

   public String toString() {
      return super.toString() + " - number of bytes read: '" + this.availBytes + "'";
   }

   private boolean isSecure() {
      return this.getSocket() instanceof SSLSocket;
   }

   public boolean isMessageComplete() {
      int maxBytesReqd = 0;

      for(int i = 0; i < this.channels.length; ++i) {
         ProtocolHandler h = this.channels[i].getProtocol().getHandler();
         if (h.claimSocket(this.head)) {
            this.claimedChannel = this.channels[i];
            this.claimedHandler = h;
            break;
         }

         maxBytesReqd = Math.max(maxBytesReqd, h.getHeaderLength());
      }

      if (this.availBytes < maxBytesReqd) {
         return false;
      } else if (this.claimedChannel == null) {
         SocketLogger.logConnectionRejected(this.channels[0].getChannelName());
         SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), new ProtocolException("Incoming socket: '" + this.getSocket() + "' has unhandled protocol prefix"));
         return false;
      } else {
         return true;
      }
   }

   public void dispatch() {
      if (this.claimedChannel != null) {
         String realProtocolName = ProtocolManager.getRealProtocol(this.claimedHandler.getProtocol()).getProtocolName();

         try {
            this.maybeFilter(realProtocolName);
         } catch (FilterException var12) {
            this.onFilterException(var12, realProtocolName);
            return;
         }

         try {
            MuxableSocket newSocket = this.claimedHandler.createSocket(this.head, this.getSocket(), this.claimedChannel);
            ConnectionFilterService connectionService = (ConnectionFilterService)LocatorUtilities.getService(ConnectionFilterService.class);
            if (connectionService.getConnectionLoggerEnabled()) {
               SocketLogger.logInfoAcceptConnection(connectionService.getConnectionFilterEnabled(), this.getSocket().getInetAddress().toString(), this.getSocket().getPort(), this.getSocket().getLocalAddress().toString(), this.getSocket().getLocalPort(), this.claimedHandler.getProtocol().getProtocolName());
            }

            Object reRegisterMXClaim;
            if (this.isSecure()) {
               SSLSocket sslSock = (SSLSocket)((SSLSocket)this.getSocket());
               JSSESocket jsseSock = JSSEUtils.getJSSESocket(sslSock);
               if (jsseSock != null) {
                  JSSEFilterImpl filter = (JSSEFilterImpl)this.getSocketFilter();
                  newSocket.setSocketFilter(filter);
                  filter.setDelegate(newSocket);
                  reRegisterMXClaim = filter;
               } else {
                  SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSock);
                  if (sslIOCtx == null) {
                     throw new IOException("SSL transport layer closed the socket!");
                  }

                  SSLFilter sslf = (SSLFilter)sslIOCtx.getFilter();
                  newSocket.setSocketFilter(sslf);
                  sslf.setDelegate(newSocket);
                  sslf.activateNoRegister();
                  reRegisterMXClaim = sslf;
               }
            } else {
               SocketMuxer.getMuxer().reRegister(this.getSocketFilter(), newSocket);
               reRegisterMXClaim = newSocket;
            }

            this.setSocketFilter((MuxableSocket)reRegisterMXClaim);
            if (((MuxableSocket)reRegisterMXClaim).isMessageComplete()) {
               ((MuxableSocket)reRegisterMXClaim).dispatch();
            } else {
               SocketMuxer.getMuxer().read((MuxableSocket)reRegisterMXClaim);
            }
         } catch (IOException var9) {
            SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), var9);
         } catch (ThreadDeath var10) {
            throw var10;
         } catch (Throwable var11) {
            SocketLogger.logThrowable(var11);
            IOException ioe = new IOException("Exception in protocol discrimination");
            ioe.initCause(var11);
            SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), ioe);
         }

      }
   }

   public int getIdleTimeoutMillis() {
      return this.timeoutMillis;
   }

   public int getCompleteMessageTimeoutMillis() {
      return this.timeoutMillis;
   }

   public boolean timeout() {
      SocketLogger.logSocketIdleTimeout((long)(this.timeoutMillis / 1000), this.socket.getInetAddress().getHostAddress(), this.socket.getPort());
      return super.timeout();
   }

   private void maybeFilter(String protocol) throws FilterException {
      ConnectionFilterService connectionService = (ConnectionFilterService)LocatorUtilities.getService(ConnectionFilterService.class);
      if (connectionService.getConnectionFilterEnabled()) {
         if (connectionService.getCompatibilityConnectionFiltersEnabled()) {
            protocol = protocol.toUpperCase(Locale.ENGLISH);
            if (protocol.equals("IIOP")) {
               protocol = "GIOP";
            } else if (protocol.equals("IIOPS")) {
               protocol = "GIOPS";
            } else if (protocol.equals("COM")) {
               protocol = "DCOM";
            }
         }

         ConnectionEvent evt = new ConnectionEvent(this.getSocket(), protocol);
         connectionService.getConnectionFilter().accept(evt);
      }

   }

   private void onFilterException(FilterException fe, String protocol) {
      String localMessage = fe.getMessage();
      ConnectionFilterService connectionService = (ConnectionFilterService)LocatorUtilities.getService(ConnectionFilterService.class);
      Loggable log;
      if (connectionService.getConnectionLoggerEnabled()) {
         log = SocketLogger.logConnectionRejectedFilterExLoggable(this.socketInfo(protocol), fe);
         log.log();
      }

      log = SocketLogger.logConnectionRejectedFilterExLoggable("Socket", fe);
      localMessage = log.getMessage();
      boolean isHttpClient = false;
      byte protocolNum = this.claimedHandler.getProtocol().toByte();
      if (protocolNum == 1 || protocolNum == 3) {
         isHttpClient = true;
      }

      Login.rejectConnection(this.getSocket(), 1, localMessage, isHttpClient);
      SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), fe);
   }

   private String socketInfo(String protocol) {
      Socket socket = this.getSocket();
      if (socket == null) {
         return "";
      } else {
         StringBuilder str = new StringBuilder("Socket[");
         InetAddress addr = socket.getInetAddress();
         str.append(addr != null ? "addr=" + addr.getHostAddress() : "");
         str.append(",port=").append(socket.getPort());
         str.append(",localport=").append(socket.getLocalPort());
         str.append(",protocol=").append(protocol).append("]");
         return str.toString();
      }
   }

   protected void registerForRuntimeMonitoring(ServerChannel channel, ServerConnectionRuntime runtime) {
   }

   protected static void p(String s) {
      System.err.println("<MuxableSocketDiscriminator:" + System.currentTimeMillis() + "> " + s);
   }
}
