package weblogic.server.channels;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.security.PrivilegedAction;
import java.util.HashMap;
import weblogic.kernel.KernelStatus;
import weblogic.management.provider.RuntimeAccess;
import weblogic.platform.VM;
import weblogic.protocol.ServerChannel;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerLogger;
import weblogic.socket.MuxableSocket;
import weblogic.socket.MuxableSocketDiscriminator;
import weblogic.socket.ServerSocketMuxer;
import weblogic.t3.srvr.SetUIDRendezvous;

public class ServerSocketWrapper {
   protected int port;
   private InetAddress listenAddress = null;
   private boolean magicThreadDumpsOn = false;
   private InetAddress magicAddress;
   protected ServerChannel[] channels;
   protected int loginTimeout;
   private ServerSocket serverSocket;
   private static HashMap boundErrorTable = new HashMap();

   protected final InetAddress getListenAddress() {
      return this.listenAddress;
   }

   ServerSocketWrapper(ServerChannel[] channels) {
      assert channels != null && channels.length > 0;

      this.channels = channels;
      this.listenAddress = channels[0].getInetAddress();
      this.loginTimeout = channels[0].getLoginTimeoutMillis();
      this.magicThreadDumpsOn = KernelStatus.isServer() ? getRuntimeAccess().getServer().getServerDebug().isMagicThreadDumpEnabled() : false;
      if (this.magicThreadDumpsOn) {
         try {
            String hostname = getRuntimeAccess().getServer().getServerDebug().getMagicThreadDumpHost();
            this.magicAddress = InetAddress.getByName(hostname);
         } catch (UnknownHostException var3) {
            this.magicThreadDumpsOn = false;
         }
      }

      this.port = this.channels[0].getPort();
   }

   private static RuntimeAccess getRuntimeAccess() {
      return (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
   }

   protected ServerSocket newServerSocket() throws IOException {
      ServerSocket ss;
      if (this.channels[0].isSDPEnabled()) {
         ss = ServerSocketMuxer.getMuxer().newSDPServerSocket(this.listenAddress, this.port, this.channels[0].getAcceptBacklog());
      } else {
         ss = ServerSocketMuxer.getMuxer().newServerSocket(this.listenAddress, this.port, this.channels[0].getAcceptBacklog());
      }

      return ss;
   }

   protected String getChannelName() {
      return ((ServerChannelImpl)this.channels[0]).getRealName();
   }

   protected final void rejectCatastrophe(Socket socket, String s, IOException e) {
      if (this.isDebugEnabled()) {
         ListenThreadDebugLogger.debug(s, e);
      }

      try {
         socket.close();
      } catch (IOException var5) {
      }

   }

   protected MuxableSocket createMuxableSocketForRegister(Socket s) {
      MuxableSocket rs = null;

      try {
         s.setSoTimeout(this.loginTimeout);
         rs = new MuxableSocketDiscriminator(s, this.channels);
      } catch (IOException var4) {
         this.rejectCatastrophe(s, "Can't establish socket: '" + socketInfo(s) + "'", var4);
      }

      return rs;
   }

   protected static final String socketInfo(Socket socket) {
      if (socket == null) {
         return "null";
      } else {
         InetAddress addr = socket.getInetAddress();
         return socket.toString() + " - address: '" + (addr != null ? addr.getHostAddress() : null) + "', port: '" + socket.getPort() + "', localport: '" + socket.getLocalPort() + "'";
      }
   }

   void logOpened() {
      StringBuffer protocols = new StringBuffer();

      for(int i = 0; i < this.channels.length; ++i) {
         if (i != 0) {
            protocols.append(", ");
         }

         protocols.append(this.channels[i].getProtocol().getProtocolName());
      }

      if (this.channels[0].isSDPEnabled()) {
         protocols.append(" using SDP for I/O");
      }

      ServerLogger.logChannelOpen(this.listenAddress == null ? "IP_ANY" : this.listenAddress.getHostAddress(), this.port, this.getChannelName(), protocols.toString());
   }

   void bind() throws IOException {
      this.createAndSetServerSocket();
      ServerSocketChannel sc = this.serverSocket.getChannel();
      if (sc == null) {
         throw new IllegalArgumentException("SocketChannel not available");
      } else {
         try {
            sc.configureBlocking(false);
         } catch (IOException var5) {
         }

         if (KernelStatus.isServer()) {
            String listenAddress = getRuntimeAccess().getServer().getListenAddress();
            if (listenAddress == null || listenAddress.equals("")) {
               try {
                  checkForMultipleAddressBind(this.serverSocket.getInetAddress().getHostName());
               } catch (UnknownHostException var4) {
                  ServerLogger.logUncheckedAddress(this.serverSocket.getInetAddress().getHostName(), var4.getMessage());
               }
            }
         }

      }
   }

   MuxableSocket acceptForDispatch() {
      MuxableSocket ms = null;

      try {
         Socket connSocket = this.serverSocket.accept();
         if (connSocket == null) {
            return null;
         }

         if (KernelStatus.isServer() && !this.checkDumpThreads(connSocket)) {
            return null;
         }

         ms = this.createMuxableSocketForRegister(connSocket);
      } catch (IOException var4) {
         if (this.isDebugEnabled()) {
            ListenThreadDebugLogger.debug("IOException while accepting a new connection. Ignoring it.");
         }
      }

      return ms;
   }

   private boolean checkDumpThreads(Socket sock) {
      if (this.magicThreadDumpsOn && sock.getInetAddress().equals(this.magicAddress)) {
         this.dumpThreads(sock);
         if (getRuntimeAccess().getServer().getServerDebug().getMagicThreadDumpBackToSocket()) {
            try {
               sock.close();
            } catch (IOException var3) {
               if (this.isDebugEnabled()) {
                  ListenThreadDebugLogger.debug("Problem dumping threads back to socket", var3);
               }
            }

            return false;
         }
      }

      return true;
   }

   private void dumpThreads(Socket sock) {
      if (getRuntimeAccess().getServer().getServerDebug().getMagicThreadDumpBackToSocket()) {
         if (this.isDebugEnabled()) {
            ListenThreadDebugLogger.debug("Dumping threads to " + sock);
         }

         try {
            OutputStream os = sock.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter writer = new PrintWriter(bw);
            VM.getVM().threadDump(writer);
            writer.flush();
         } catch (IOException var6) {
            if (this.isDebugEnabled()) {
               ListenThreadDebugLogger.debug("Problem dumping threads back to socket", var6);
            }
         }
      }

   }

   private void createAndSetServerSocket() throws IOException {
      PrivilegedAction action = new PrivilegedAction() {
         public Object run() {
            try {
               ServerSocketWrapper.this.serverSocket = ServerSocketWrapper.this.newServerSocket();
               return null;
            } catch (IOException var2) {
               return var2;
            }
         }
      };
      IOException ioe;
      if (KernelStatus.isServer() && this.port <= 1024) {
         ioe = (IOException)SetUIDRendezvous.doPrivileged(action);
      } else {
         ioe = (IOException)action.run();
      }

      if (ioe != null) {
         throw ioe;
      }
   }

   private static synchronized void checkForMultipleAddressBind(String bindHostName) throws UnknownHostException {
      if (bindHostName != null && boundErrorTable.get(bindHostName) == null) {
         InetAddress[] addressArray = InetAddress.getAllByName(bindHostName);
         if (addressArray.length > 1) {
            StringBuffer buf = new StringBuffer();

            for(int i = 0; i < addressArray.length; ++i) {
               buf.append(addressArray[i].getHostAddress());
               if (i != addressArray.length - 1) {
                  buf.append(", ");
               }
            }

            String addressString = buf.toString();
            ServerLogger.logHostMapsToMultipleAddress(bindHostName, addressString);
         }

         boundErrorTable.put(bindHostName, bindHostName);
      }
   }

   public String toString() {
      StringBuffer b = new StringBuffer();

      for(int i = 0; i < this.channels.length; ++i) {
         b.append(this.channels[i].toString()).append(", ");
      }

      return b.toString();
   }

   private boolean isDebugEnabled() {
      return ListenThreadDebugLogger.isDebugEnabled();
   }

   void registerForAccept(Selector selector) throws ClosedChannelException {
      if (this.serverSocket == null) {
         throw new IllegalArgumentException("SocketChannel not available, must be bound first");
      } else {
         ServerSocketChannel sc = this.serverSocket.getChannel();
         if (this.isDebugEnabled()) {
            ListenThreadDebugLogger.debug("register ServerSocketWrapper" + this.serverSocket);
         }

         sc.register(selector, 16, this);
      }
   }

   void close() {
      if (this.serverSocket != null) {
         ServerSocketChannel sc = this.serverSocket.getChannel();
         if (sc.isOpen()) {
            if (this.isDebugEnabled()) {
               ListenThreadDebugLogger.debug("close socket=" + this);
            }

            try {
               sc.close();
            } catch (Exception var3) {
               if (this.isDebugEnabled()) {
                  ListenThreadDebugLogger.debug("close channel error for socket=" + this, var3);
               }
            }
         }

      }
   }

   int getLocalPort() {
      return this.serverSocket == null ? -1 : this.serverSocket.getLocalPort();
   }
}
