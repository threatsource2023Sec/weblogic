package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import weblogic.protocol.ServerChannel;
import weblogic.security.service.ContextHandler;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.SecondChanceCacheMap;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public final class VirtualConnection {
   public static final String X509_CERTIFICATE = "javax.servlet.request.X509Certificate";
   public static final String CIPHER_SUITE = "javax.servlet.request.cipher_suite";
   public static final String KEY_SIZE = "javax.servlet.request.key_size";
   public static final String SSL_SESSION_ID = "javax.servlet.request.ssl_session_id";
   public static final String SSL_SESSION = "weblogic.servlet.request.sslsession";
   private static final String NETWORK_CHANNEL_HTTP_PORT = "weblogic.servlet.network_channel.port";
   private static final String NETWORK_CHANNEL_HTTPS_PORT = "weblogic.servlet.network_channel.sslport";
   private final ServletRequestImpl request;
   private final AbstractHttpConnectionHandler muxableSocket;
   private final boolean internalDispatch;
   private Socket socket;
   private int socketFD = -1;
   private InetAddress proxyHost;
   private InetAddress peer;
   private String remoteAddr;
   private String remoteHost;
   private int remotePort = -1;
   private byte[] x509ProxyClientCert;
   private boolean certExtracted;
   private final ArrayList perimeterAuthClientCert = new ArrayList(5);
   private final ArrayList perimeterAuthClientCertType = new ArrayList(5);
   private Object origCert = null;
   private boolean certsFromProxy;
   private boolean secure;
   private boolean ssl;
   private boolean isClosed;

   VirtualConnection(ServletRequestImpl req, AbstractHttpConnectionHandler ms) {
      this.request = req;
      this.muxableSocket = ms;
      this.internalDispatch = this.muxableSocket == null;
      if (!this.internalDispatch) {
         this.initNetworkChannelPorts(this.muxableSocket.getChannel());
         this.socket = this.muxableSocket.getSocket();
      }

   }

   void init() {
      if (!this.internalDispatch && this.muxableSocket.isSecure()) {
         this.initSSLAttributes((SSLSocket)this.getSocket());
      }

   }

   int getLocalPort() {
      if (this.internalDispatch) {
         return 0;
      } else {
         ServerChannel ch = this.muxableSocket.getChannel();
         return ch.getPublicInetAddress().getPort();
      }
   }

   String getLocalAddr() {
      if (this.internalDispatch) {
         return null;
      } else {
         ServerChannel ch = this.muxableSocket.getChannel();
         return ch.getPublicInetAddress().getAddress().getHostAddress();
      }
   }

   String getLocalName() {
      if (this.internalDispatch) {
         return null;
      } else {
         ServerChannel ch = this.muxableSocket.getChannel();
         return ch.getPublicInetAddress().getHostName();
      }
   }

   public int getSocketFD() {
      return this.socketFD;
   }

   public void setSocketFD(int fd) {
      this.socketFD = fd;
   }

   boolean isSSL() {
      return this.ssl;
   }

   boolean isSecure() {
      return this.secure;
   }

   boolean isClosed() {
      return this.isClosed;
   }

   public Socket getSocket() {
      return this.muxableSocket.getSocket();
   }

   public AbstractHttpConnectionHandler getConnectionHandler() {
      return this.muxableSocket;
   }

   public ServerChannel getChannel() {
      return this.muxableSocket.getChannel();
   }

   public ContextHandler getContextHandler() {
      return (ContextHandler)this.muxableSocket.getRawConnection();
   }

   public ArrayList getPerimeterAuthClientCert() {
      return this.perimeterAuthClientCert;
   }

   public ArrayList getPerimeterAuthClientCertType() {
      return this.perimeterAuthClientCertType;
   }

   public byte[] getX509ProxyClientCert() {
      return this.x509ProxyClientCert;
   }

   void setX509ProxyClientCert(byte[] x509ProxyClientCert) {
      this.x509ProxyClientCert = x509ProxyClientCert;
   }

   void reset() {
      this.perimeterAuthClientCert.clear();
      this.perimeterAuthClientCertType.clear();
      this.x509ProxyClientCert = null;
      this.certExtracted = false;
      this.proxyHost = null;
      this.remoteAddr = null;
      this.remoteHost = null;
      this.remotePort = -1;
      this.peer = null;
      this.secure = this.ssl;
      if (this.certsFromProxy) {
         this.certsFromProxy = false;
         this.request.setAttribute("javax.servlet.request.X509Certificate", this.origCert);
      }

   }

   private void initNetworkChannelPorts(ServerChannel networkChannel) {
      if (networkChannel.supportsTLS()) {
         this.request.setAttribute("weblogic.servlet.network_channel.sslport", new Integer(networkChannel.getPublicPort()));
      } else {
         this.request.setAttribute("weblogic.servlet.network_channel.port", new Integer(networkChannel.getPublicPort()));
      }

   }

   private void initSSLAttributes(SSLSocket sslSocket) {
      this.ssl = this.secure = true;
      Object[] obj = WebAppSecurity.getProvider().getSSLAttributes(sslSocket);
      if (obj != null && obj.length != 0) {
         if (obj[0] != null && obj[0] instanceof SSLSession) {
            this.request.setAttribute("weblogic.servlet.request.sslsession", obj[0]);
            this.request.setAttribute("javax.servlet.request.ssl_session_id", ((SSLSession)obj[0]).getId());
         }

         if (obj[1] != null && obj[1] instanceof String) {
            this.request.setAttribute("javax.servlet.request.cipher_suite", obj[1]);
         }

         if (obj[2] != null && obj[2] instanceof Integer) {
            this.request.setAttribute("javax.servlet.request.key_size", obj[2]);
         }

         if (obj[3] != null) {
            this.request.setAttribute("javax.servlet.request.X509Certificate", obj[3]);
         }

      }
   }

   void initCerts() {
      if (this.request.getContext().getConfigManager().isClientCertProxyEnabled()) {
         this.initProxyClientCert();
      } else {
         this.setX509ProxyClientCert((byte[])null);
      }

   }

   private void initProxyClientCert() {
      if (!this.certExtracted) {
         this.certExtracted = true;
         if (this.x509ProxyClientCert != null) {
            try {
               UnsyncByteArrayInputStream bais = new UnsyncByteArrayInputStream(this.x509ProxyClientCert);
               byte[] decodedCert = (new BASE64Decoder()).decodeBuffer(bais);
               InputStream is = new UnsyncByteArrayInputStream(decodedCert);
               X509Certificate[] x509Chain = new X509Certificate[1];
               CertificateFactory cf = CertificateFactory.getInstance("X.509");
               x509Chain[0] = (X509Certificate)cf.generateCertificate(is);
               this.saveOrigCert();
               this.request.setAttribute("javax.servlet.request.X509Certificate", x509Chain);
            } catch (Exception var6) {
               HTTPLogger.logIgnoringClientCert("WL-Proxy-Client-Cert", var6);
               this.x509ProxyClientCert = null;
            }
         }

      }
   }

   void processProxyHeader(String name, byte[] valueBytes) {
      int namelen = name.length();
      switch (namelen) {
         case 12:
            if (ServletRequestImpl.eq(name, "WL-Proxy-SSL", 12)) {
               String value = StringUtils.getString(valueBytes);
               if ("true".equalsIgnoreCase(value)) {
                  if (this.muxableSocket.getHttpServer().isWeblogicPluginEnabled()) {
                     this.secure = true;
                  } else {
                     this.secure = false;
                  }
               } else if ("false".equalsIgnoreCase(value)) {
                  this.secure = false;
               }
            }
            break;
         case 18:
            if (ServletRequestImpl.eq(name, "WL-Proxy-Client-IP", 18)) {
               try {
                  this.proxyHost = InetAddress.getByName(StringUtils.getString(valueBytes));
               } catch (UnknownHostException var5) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("Failed to process the client header WL-Proxy-Client-IP:" + StringUtils.getString(valueBytes), var5);
                  }
               }
            }
            break;
         case 20:
            if (ServletRequestImpl.eq(name, "WL-Proxy-Client-Cert", 20)) {
               this.setX509ProxyClientCert(valueBytes);
            }
      }

      if (namelen > 16 && ServletRequestImpl.eq(name, "WL-Proxy-Client-", 16) && valueBytes != null && valueBytes.length > 0) {
         this.getPerimeterAuthClientCertType().add(name.substring(16));
         this.getPerimeterAuthClientCert().add(valueBytes);
      }

   }

   private void saveOrigCert() {
      this.certsFromProxy = true;
      this.origCert = this.request.getAttribute("javax.servlet.request.X509Certificate");
   }

   String getRemoteAddr() {
      if (this.remoteAddr != null) {
         return this.remoteAddr;
      } else if (this.internalDispatch) {
         return null;
      } else {
         if (this.muxableSocket.getHttpServer().isWeblogicPluginEnabled()) {
            this.peer = this.proxyHost;
         }

         if (this.peer == null && (this.peer = this.socket.getInetAddress()) == null) {
            return null;
         } else {
            InetAddressCacheRecord record = VirtualConnection.InetAddressCacheRecord.getInstance(this.peer);
            this.remoteAddr = record.getHostAddress();
            return this.remoteAddr;
         }
      }
   }

   String getRemoteHost() {
      if (this.remoteHost != null) {
         return this.remoteHost;
      } else {
         this.getRemoteAddr();
         if (this.remoteAddr == null) {
            return "";
         } else {
            if (this.peer == null) {
               try {
                  this.peer = InetAddress.getByName(this.remoteAddr);
               } catch (UnknownHostException var2) {
                  return this.getRemoteAddr();
               }
            }

            InetAddressCacheRecord record = VirtualConnection.InetAddressCacheRecord.getInstance(this.peer);
            this.remoteHost = record.getHostName();
            return this.remoteHost;
         }
      }
   }

   public int getRemotePort() {
      if (this.remotePort != -1) {
         return this.remotePort;
      } else if (this.internalDispatch) {
         return -1;
      } else {
         if (this.remotePort == -1) {
            this.remotePort = this.socket.getPort();
         }

         return this.remotePort;
      }
   }

   boolean isInternalDispatch() {
      return this.internalDispatch;
   }

   void deliverHasException(IOException ioe) {
      this.muxableSocket.closeConnection(ioe);
      this.isClosed = true;
   }

   void requeue() {
      this.muxableSocket.requeue();
   }

   void close() {
      this.muxableSocket.closeConnection((IOException)null);
      this.isClosed = true;
   }

   static final class InetAddressCacheRecord {
      private static final SecondChanceCacheMap cache = new SecondChanceCacheMap(317);
      private final InetAddress address;
      private String remoteHost;
      private String remoteIP;

      static InetAddressCacheRecord getInstance(InetAddress peer) {
         InetAddressCacheRecord record = (InetAddressCacheRecord)cache.get(peer);
         if (record == null) {
            record = new InetAddressCacheRecord(peer);
            cache.put(peer, record);
         }

         return record;
      }

      InetAddressCacheRecord(InetAddress a) {
         this.address = a;
      }

      String getHostName() {
         if (this.remoteHost == null) {
            this.remoteHost = this.address.getHostName();
         }

         return this.remoteHost;
      }

      String getHostAddress() {
         if (this.remoteIP == null) {
            this.remoteIP = this.address.getHostAddress();
         }

         return this.remoteIP;
      }
   }
}
