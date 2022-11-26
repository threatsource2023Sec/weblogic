package net.shibboleth.utilities.java.support.httpclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.http.HttpHost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class TLSSocketFactory implements LayeredConnectionSocketFactory {
   public static final String CONTEXT_KEY_TLS_PROTOCOLS = "javasupport.TLSProtocols";
   public static final String CONTEXT_KEY_TLS_CIPHER_SUITES = "javasupport.TLSCipherSuites";
   public static final String CONTEXT_KEY_HOSTNAME_VERIFIER = "javasupport.HostnameVerifier";
   public static final String TLS = "TLS";
   public static final String SSL = "SSL";
   public static final String SSLV2 = "SSLv2";
   public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
   public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
   public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
   private final Logger log;
   private final SSLSocketFactory socketfactory;
   private final X509HostnameVerifier hostnameVerifier;
   private final String[] supportedProtocols;
   private final String[] supportedCipherSuites;

   public TLSSocketFactory(@Nonnull SSLContext sslContext) {
      this(sslContext, STRICT_HOSTNAME_VERIFIER);
   }

   public TLSSocketFactory(@Nonnull SSLContext sslContext, @Nullable X509HostnameVerifier verifier) {
      this((SSLSocketFactory)((SSLContext)Args.notNull(sslContext, "SSL context")).getSocketFactory(), (String[])null, (String[])null, verifier);
   }

   public TLSSocketFactory(@Nonnull SSLContext sslContext, @Nullable String[] protocols, @Nullable String[] cipherSuites, @Nullable X509HostnameVerifier verifier) {
      this(((SSLContext)Args.notNull(sslContext, "SSL context")).getSocketFactory(), protocols, cipherSuites, verifier);
   }

   public TLSSocketFactory(@Nonnull SSLSocketFactory factory, @Nullable X509HostnameVerifier verifier) {
      this((SSLSocketFactory)factory, (String[])null, (String[])null, verifier);
   }

   public TLSSocketFactory(@Nonnull SSLSocketFactory factory, @Nullable String[] protocols, @Nullable String[] cipherSuites, @Nullable X509HostnameVerifier verifier) {
      this.log = LoggerFactory.getLogger(TLSSocketFactory.class);
      this.socketfactory = (SSLSocketFactory)Args.notNull(factory, "SSL socket factory");
      this.supportedProtocols = protocols;
      this.supportedCipherSuites = cipherSuites;
      this.hostnameVerifier = verifier != null ? verifier : STRICT_HOSTNAME_VERIFIER;
   }

   @Nonnull
   protected SSLSocketFactory getSocketfactory() {
      return this.socketfactory;
   }

   @Nonnull
   protected X509HostnameVerifier getHostnameVerifier() {
      return this.hostnameVerifier;
   }

   @Nullable
   protected String[] getSupportedProtocols() {
      return this.supportedProtocols;
   }

   @Nullable
   protected String[] getSupportedCipherSuites() {
      return this.supportedCipherSuites;
   }

   protected void prepareSocket(@Nonnull SSLSocket socket, @Nullable HttpContext context) throws IOException {
   }

   @Nonnull
   public Socket createSocket(@Nullable HttpContext context) throws IOException {
      this.log.trace("In createSocket");
      return SocketFactory.getDefault().createSocket();
   }

   public Socket connectSocket(int connectTimeout, @Nullable Socket socket, @Nonnull HttpHost host, @Nonnull InetSocketAddress remoteAddress, @Nullable InetSocketAddress localAddress, @Nullable HttpContext context) throws IOException {
      this.log.trace("In connectSocket");
      Args.notNull(host, "HTTP host");
      Args.notNull(remoteAddress, "Remote address");
      Socket sock = socket != null ? socket : this.createSocket(context);
      if (localAddress != null) {
         sock.bind(localAddress);
      }

      try {
         if (connectTimeout > 0 && sock.getSoTimeout() == 0) {
            sock.setSoTimeout(connectTimeout);
         }

         sock.connect(remoteAddress, connectTimeout);
      } catch (IOException var11) {
         try {
            sock.close();
         } catch (IOException var10) {
         }

         throw var11;
      }

      if (sock instanceof SSLSocket) {
         SSLSocket sslsock = (SSLSocket)sock;
         sslsock.startHandshake();
         this.verifyHostname(sslsock, host.getHostName(), context);
         return sock;
      } else {
         return this.createLayeredSocket(sock, host.getHostName(), remoteAddress.getPort(), context);
      }
   }

   public Socket createLayeredSocket(@Nonnull Socket socket, @Nonnull @NotEmpty String target, int port, @Nullable HttpContext context) throws IOException {
      this.log.trace("In createLayeredSocket");
      SSLSocket sslsock = (SSLSocket)this.getSocketfactory().createSocket(socket, target, port, true);
      String[] contextProtocols = this.getListAttribute(context, "javasupport.TLSProtocols");
      String[] contextCipherSuites;
      if (contextProtocols != null) {
         sslsock.setEnabledProtocols(contextProtocols);
      } else if (this.getSupportedProtocols() != null) {
         sslsock.setEnabledProtocols(this.getSupportedProtocols());
      } else {
         contextCipherSuites = sslsock.getSupportedProtocols();
         List enabledProtocols = new ArrayList(contextCipherSuites.length);
         String[] arr$ = contextCipherSuites;
         int len$ = contextCipherSuites.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String protocol = arr$[i$];
            if (!protocol.startsWith("SSL")) {
               enabledProtocols.add(protocol);
            }
         }

         sslsock.setEnabledProtocols((String[])enabledProtocols.toArray(new String[enabledProtocols.size()]));
      }

      contextCipherSuites = this.getListAttribute(context, "javasupport.TLSCipherSuites");
      if (contextCipherSuites != null) {
         sslsock.setEnabledCipherSuites(contextCipherSuites);
      } else if (this.getSupportedCipherSuites() != null) {
         sslsock.setEnabledCipherSuites(this.getSupportedCipherSuites());
      }

      this.prepareSocket(sslsock, context);
      sslsock.startHandshake();
      this.logSocketInfo(sslsock);
      this.verifyHostname(sslsock, target, context);
      return sslsock;
   }

   private void logSocketInfo(SSLSocket socket) {
      SSLSession session = socket.getSession();
      if (this.log.isDebugEnabled()) {
         this.log.debug("Connected to: {}", socket.getRemoteSocketAddress());
         this.log.debug("Supported protocols: {}", socket.getSupportedProtocols());
         this.log.debug("Enabled protocols:   {}", socket.getEnabledProtocols());
         this.log.debug("Selected protocol:   {}", session.getProtocol());
         this.log.debug("Supported cipher suites: {}", socket.getSupportedCipherSuites());
         this.log.debug("Enabled cipher suites:   {}", socket.getEnabledCipherSuites());
         this.log.debug("Selected cipher suite:   {}", session.getCipherSuite());
      }

      if (this.log.isTraceEnabled()) {
         try {
            this.log.trace("Peer principal: {}", session.getPeerPrincipal());
            this.log.trace("Peer certificates: {}", session.getPeerCertificates());
            this.log.trace("Local principal: {}", session.getLocalPrincipal());
            this.log.trace("Local certificates: {}", session.getLocalCertificates());
         } catch (SSLPeerUnverifiedException var4) {
            this.log.warn("SSL exception enumerating peer certificates", var4);
         }
      }

   }

   @Nullable
   protected String[] getListAttribute(@Nullable HttpContext context, @Nonnull String contextKey) {
      if (context == null) {
         return null;
      } else {
         List values = new ArrayList(StringSupport.normalizeStringCollection((List)context.getAttribute(contextKey)));
         return values != null && !values.isEmpty() ? (String[])values.toArray(new String[values.size()]) : null;
      }
   }

   protected void verifyHostname(@Nonnull SSLSocket sslsock, @Nonnull String hostname, @Nullable HttpContext context) throws IOException {
      try {
         X509HostnameVerifier verifier = null;
         if (context != null) {
            verifier = (X509HostnameVerifier)context.getAttribute("javasupport.HostnameVerifier");
         }

         if (verifier == null) {
            verifier = this.getHostnameVerifier();
         }

         verifier.verify(hostname, sslsock);
      } catch (IOException var7) {
         try {
            sslsock.close();
         } catch (Exception var6) {
         }

         throw var7;
      }
   }
}
