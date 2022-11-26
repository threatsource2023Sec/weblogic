package weblogic.rjvm.http;

import com.bea.security.utils.random.SecureRandomData;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerURL;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rjvm.RJVMLogger;
import weblogic.rjvm.TransportUtils;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.io.Chunk;

public class HTTPClientJVMConnection extends MsgAbbrevJVMConnection implements Runnable {
   private static final boolean ASSERT = true;
   private static final boolean DEBUG = false;
   private static final String URL_EXTENSION = "/a.tun";
   private boolean closed;
   private String host;
   private int port;
   private final ServerChannel networkChannel;
   private final String partitionUrl;
   private String connectionID = null;
   private String cookie = null;
   private boolean isUpgraded = false;
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugConnection");
   private static final boolean usecontextPath = Boolean.getBoolean("weblogic.t3.useHttpContextPathToAccessPartition");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public final ServerChannel getChannel() {
      return this.networkChannel;
   }

   public InetAddress getInetAddress() {
      return null;
   }

   private static void drainStream(BufferedReader br) throws IOException {
      if (br != null) {
         int r;
         do {
            r = br.read();
         } while(r != -1);

         br.close();
      }

   }

   private static Chunk readPacket(InputStream is) throws IOException {
      Chunk head = Chunk.getChunk();

      try {
         int nread = Chunk.chunkFully(head, is);
         Debug.assertion(nread > 4);
      } finally {
         is.close();
      }

      return head;
   }

   HTTPClientJVMConnection(ServerChannel networkChannel, String partitionUrl) {
      this.networkChannel = networkChannel;
      this.partitionUrl = partitionUrl;
      this.closed = true;
   }

   URLConnection createURLConnection(URL u) throws IOException {
      return RJVMEnvironment.getEnvironment().createURLConnection(u, this.networkChannel);
   }

   public final String toString() {
      return super.toString() + " - id: '" + this.connectionID + "', host: '" + this.host + "', port: '" + this.port + " closed: '" + this.closed + '\'';
   }

   public final InetAddress getLocalAddress() {
      return null;
   }

   public final int getLocalPort() {
      return -1;
   }

   private String getRequestArgs() {
      return "?connectionID=" + this.connectionID + "&rand=" + SecureRandomData.getInstance().getRandomNonNegativeLong();
   }

   private void handleNullResponse(URLConnection conn) throws ProtocolException {
      throw new ProtocolException("Tunneling result unspecified - is the HTTP server at host: '" + this.host + "' and port: '" + this.port + "' a WebLogic Server?");
   }

   public final synchronized void connect(String host, InetAddress addr, int port, int connectTimeout) throws IOException {
      if (!this.closed) {
         throw new ProtocolException("Already connected");
      } else {
         this.port = port;
         this.host = host;
         URL u = this.getLoginURL(host, port);

         URLConnection conn;
         try {
            conn = this.createURLConnection(u);
         } catch (IOException var21) {
            RJVMLogger.logOpenFailed(var21);
            return;
         }

         conn.setConnectTimeout(connectTimeout);
         conn.setUseCaches(false);
         String s = null;
         int index0 = this.partitionUrl.indexOf("://");
         if (index0 != -1) {
            s = this.partitionUrl.substring(index0 + 3);
            index0 = s.indexOf(47);
            if (index0 != -1) {
               s = s.substring(0, index0);
            }
         }

         if (s != null) {
            conn.setRequestProperty("Host", s);
         }

         BufferedReader br = null;

         try {
            try {
               br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } catch (FileNotFoundException var19) {
               throw new IOException("Could not connect to http://" + host + ':' + port, var19);
            } catch (ConnectException var20) {
               ConnectException toThrow = new ConnectException("Could not connect to http://" + host + ':' + port);
               toThrow.initCause(var20);
               throw toThrow;
            }

            int i = 1;

            String hdr;
            while((hdr = conn.getHeaderFieldKey(i++)) != null) {
               if (hdr.equalsIgnoreCase("Set-Cookie")) {
                  hdr = conn.getHeaderField(i - 1);
                  int idx = hdr.indexOf(59);
                  if (idx != -1) {
                     hdr = hdr.substring(0, idx);
                  }

                  if (this.cookie == null) {
                     this.cookie = hdr;
                  } else {
                     this.cookie = this.cookie + "; " + hdr;
                  }
               }
            }

            String res = conn.getHeaderField("WL-Result");
            if (res == null) {
               this.handleNullResponse(conn);
            }

            if (!res.equals("OK")) {
               throw new ProtocolException("Tunneling result not OK, result: '" + res + '\'');
            }

            String version = conn.getHeaderField("WL-Version");
            if (version == null) {
               this.doDownGrade();
            }

            this.connectionID = conn.getHeaderField("Conn-Id");
            if (!this.isUpgraded && this.connectionID == null) {
               throw new ProtocolException("Tunneling could not ascertain a connection ID from the server");
            }

            this.readConnectionParams(br);
            this.closed = false;
         } finally {
            drainStream(br);
         }

      }
   }

   protected static String getURLPath(String urlString, String serviceName) {
      if (!usecontextPath) {
         return serviceName;
      } else {
         String result = serviceName;

         try {
            String path = (new ServerURL(urlString)).getFile();
            if (path != null && !path.isEmpty()) {
               result = path + serviceName;
            }
         } catch (MalformedURLException var4) {
         }

         return result;
      }
   }

   URL getLoginURL(String host, int port) throws MalformedURLException {
      String loginLine = URLEncoder.encode(this.getProtocol().getProtocolName() + " dummy WLREQS " + VersionInfo.theOne().getReleaseVersion() + " dummy ");
      String localPN = RmiInvocationFacade.getCurrentPartitionName(KERNEL_ID);
      String loginStr = KernelStatus.getTunellingURL(getURLPath(this.partitionUrl, "/bea_wls_internal/HTTPClntLogin")) + "/a.tun" + "?wl-login=" + loginLine + "&rand=" + SecureRandomData.getInstance().getRandomNonNegativeLong() + '&' + "AS" + '=' + ABBREV_TABLE_SIZE + '&' + "HL" + '=' + 19 + '&' + "MS" + '=' + this.networkChannel.getMaxMessageSize() + '&' + "PU" + '=' + this.partitionUrl + '&' + "LP" + '=' + localPN;
      return new URL(this.getProtocol().getProtocolName(), host, port, loginStr);
   }

   private void readConnectionParams(BufferedReader br) throws IOException {
      TransportUtils.BootstrapResult result = TransportUtils.readBootstrapParams(br);
      if (!result.isSuccess()) {
         throw new ProtocolException("Invalid parameter: " + result.getInvalidLine());
      } else {
         String remotePName = result.getPartitionName();
         if (remotePName == null) {
            remotePName = "DOMAIN";
         }

         String localPN = RmiInvocationFacade.getCurrentPartitionName(KERNEL_ID);
         this.init(result.getAbbrevSize(), result.getHeaderLength(), result.getPeerChannelMaxMessageSize(), localPN, this.partitionUrl, remotePName);
      }
   }

   public final synchronized void run() {
      while(true) {
         try {
            if (!this.closed) {
               this.receiveAndDispatch();
               continue;
            }
         } catch (ThreadDeath var7) {
            ThreadDeath td = var7;

            try {
               this.gotExceptionReceiving(td);
               this.close();
            } finally {
               ;
            }

            throw var7;
         } catch (Throwable var8) {
            if (logger.isDebugEnabled()) {
               RJVMLogger.logExecuteFailed(var8);
            }

            this.gotExceptionReceiving(var8);
            this.close();
         }

         return;
      }
   }

   public final void sendMsg(OutgoingMessage msg) throws IOException {
      if (this.closed) {
         throw new IOException("Connection closed");
      } else {
         URL u = new URL(this.getProtocol().getProtocolName(), this.host, this.port, KernelStatus.getTunellingURL(getURLPath(this.partitionUrl, "/bea_wls_internal/HTTPClntSend")) + "/a.tun" + this.getRequestArgs());
         final URLConnection conn = this.createURLConnection(u);
         conn.setUseCaches(false);
         conn.setRequestProperty("Content-Type", "application/octet-stream");
         if (this.cookie != null) {
            conn.setRequestProperty("Cookie", this.cookie);
         }

         BufferedReader br = null;

         try {
            conn.setDoOutput(true);
            OutputStream os = null;
            if (System.getSecurityManager() == null) {
               os = conn.getOutputStream();
            } else {
               try {
                  os = (OutputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                     public OutputStream run() throws Exception {
                        return conn.getOutputStream();
                     }
                  });
               } catch (PrivilegedActionException var12) {
                  Exception cause = var12.getException();
                  if (cause instanceof IOException) {
                     throw (IOException)var12.getException();
                  }

                  throw new RuntimeException(cause);
               }
            }

            msg.writeTo(os);
            os.flush();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String res = conn.getHeaderField("WL-Result");

            try {
               if (res == null) {
                  this.handleNullResponse(conn);
               }

               if (res != null && !res.equals("OK")) {
                  throw new ProtocolException("Tunneling result not OK, result: '" + res + "', id: '" + this.connectionID + '\'');
               }
            } catch (ProtocolException var13) {
               throw var13;
            }
         } finally {
            drainStream(br);
         }

      }
   }

   public final void close() {
      if (!this.closed) {
         this.closed = true;
      }
   }

   private synchronized void receiveAndDispatch() throws IOException {
      if (!this.closed) {
         while(true) {
            while(!this.closed) {
               URL u = new URL(this.getProtocol().getProtocolName(), this.host, this.port, KernelStatus.getTunellingURL(getURLPath(this.partitionUrl, "/bea_wls_internal/HTTPClntRecv")) + "/a.tun" + this.getRequestArgs());
               URLConnection conn = this.createURLConnection(u);
               conn.setUseCaches(false);
               if (this.cookie != null) {
                  conn.setRequestProperty("Cookie", this.cookie);
               }

               InputStream is = conn.getInputStream();
               String res = conn.getHeaderField("WL-Result");
               if (res == null) {
                  this.handleNullResponse(conn);
               }

               if (res != null && res.equals("RETRY")) {
                  is.close();
               } else {
                  if (res != null && !res.equals("OK")) {
                     throw new ProtocolException("Tunneling result not OK, result: '" + res + "', id: '" + this.connectionID + '\'');
                  }

                  super.dispatch(readPacket(is));
               }
            }

            return;
         }
      }
   }
}
