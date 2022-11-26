package weblogic.server.channels;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import weblogic.kernel.T3SrvrLogger;
import weblogic.protocol.ServerChannel;
import weblogic.security.SecurityLogger;
import weblogic.security.SSL.SSLEngineFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.SSLCipherUtility;
import weblogic.security.utils.SSLContextManager;
import weblogic.socket.JSSEFilterImpl;
import weblogic.socket.JSSESocket;
import weblogic.socket.MuxableSocket;
import weblogic.socket.MuxableSocketDiscriminator;
import weblogic.socket.SNIFilter;
import weblogic.socket.SNISecureConfigFactory;
import weblogic.socket.SNISocket;

final class ServerSocketWrapperJSSE extends ServerSocketWrapper {
   private static final String SSL_LISTEN_THREAD_NAME = "ServerSocketWrapperJSSE";
   private static final boolean SNI_MODE_ENABLED = Boolean.getBoolean("weblogic.socket.ssl.enableSNIMode");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final SSLEngineFactory sslEngineFactory;
   private Map enabledProtocolsCache = new ConcurrentHashMap(2);
   private Map filteredCiphersuitesCache = new ConcurrentHashMap(2);
   private final SNISecureConfigFactory defaultSecureConfigFactory = new DefaultSNISecureConfigFactory();

   ServerSocketWrapperJSSE(ServerChannel[] channels) throws IOException {
      super(channels);
      this.port = channels[0].getPort();

      try {
         this.sslEngineFactory = SSLContextManager.getSSLEngineFactory(channels[0], kernelId);
      } catch (Exception var3) {
         T3SrvrLogger.logInconsistentSecurityConfig(var3);
         SecurityLogger.logNotListeningForSSLInfo(var3.toString());
         throw (IOException)(new IOException(var3.getMessage())).initCause(var3);
      }

      this.loginTimeout = channels[0].getLoginTimeoutMillis();
   }

   final String getName() {
      return "ServerSocketWrapperJSSE[" + this.getChannelName() + "]";
   }

   public MuxableSocket createMuxableSocketForRegister(Socket s) {
      JSSEFilterImpl filter = null;

      try {
         s.setSoTimeout(this.loginTimeout);
         Object jsseSock;
         if (SNI_MODE_ENABLED) {
            SNIFilter sniFilter = new SNIFilter(s, SNISecureConfigFactory.NULL_FACTORY, this.defaultSecureConfigFactory);
            SNISocket sniSocket = new SNISocket(s, sniFilter);
            sniFilter.setSNISocket(sniSocket);
            filter = sniFilter;
            jsseSock = sniSocket;
         } else {
            filter = new JSSEFilterImpl(s, this.defaultSecureConfigFactory.createSSLEngine(s, (String)null), false, this.channels[0].isClientInitSecureRenegotiationAccepted());
            jsseSock = new JSSESocket(s, (JSSEFilterImpl)filter);
         }

         MuxableSocketDiscriminator rs = new MuxableSocketDiscriminator((Socket)jsseSock, this.channels);
         ((JSSEFilterImpl)filter).setDelegate(rs);
         rs.setSocketFilter((MuxableSocket)filter);
      } catch (InterruptedIOException var6) {
         this.rejectCatastrophe(s, "Login timed out after: '" + this.loginTimeout + "' ms on socket: '" + socketInfo(s) + "'", var6);
      } catch (EOFException var7) {
         this.rejectCatastrophe(s, "Client closed socket '" + socketInfo(s) + "' before completing connection.", var7);
      } catch (IOException var8) {
         this.rejectCatastrophe(s, "Unable to read from socket: '" + socketInfo(s) + "'", var8);
      }

      return (MuxableSocket)filter;
   }

   private class DefaultSNISecureConfigFactory implements SNISecureConfigFactory {
      private DefaultSNISecureConfigFactory() {
      }

      public SSLEngine createSSLEngine(Socket s, String sniHost) throws SSLException {
         SSLEngine sslEngine = ServerSocketWrapperJSSE.this.sslEngineFactory.createSSLEngine(s.getInetAddress().getHostAddress(), s.getPort(), false);
         sslEngine.setUseClientMode(false);
         String[] cipherSuites = ServerSocketWrapperJSSE.this.channels[0].getCiphersuites();
         cipherSuites = SSLCipherUtility.removeNullCipherSuites(cipherSuites);
         String[] filteredCiphersuitesList;
         if (cipherSuites != null && cipherSuites.length > 0) {
            sslEngine.setEnabledCipherSuites(cipherSuites);
         } else {
            String[] excludedCiphersuites = ServerSocketWrapperJSSE.this.channels[0].getExcludedCiphersuites();
            filteredCiphersuitesList = null;
            if (excludedCiphersuites != null && excludedCiphersuites.length > 0) {
               String[] copyOfExcludedCiphersuites = (String[])Arrays.copyOf(excludedCiphersuites, excludedCiphersuites.length);
               Arrays.sort(copyOfExcludedCiphersuites);
               String keyx = Arrays.toString(copyOfExcludedCiphersuites);
               if (keyx != null && keyx.trim().length() > 0) {
                  String[] cachedCiphersuites = (String[])ServerSocketWrapperJSSE.this.filteredCiphersuitesCache.get(keyx);
                  if (cachedCiphersuites != null && cachedCiphersuites.length > 0) {
                     filteredCiphersuitesList = cachedCiphersuites;
                     if (SslSupportFacade.isDebugEnabled()) {
                        SslSupportFacade.sslSetupLogInfo("Using cached cipher suite list.");
                     }
                  } else {
                     String[] enabledCipherSuites = sslEngine.getEnabledCipherSuites();
                     filteredCiphersuitesList = SSLCipherUtility.removeExcludedCiphersuites(enabledCipherSuites, excludedCiphersuites);
                     if (filteredCiphersuitesList.length < enabledCipherSuites.length && SslSupportFacade.isDebugEnabled()) {
                        SslSupportFacade.sslSetupLogInfo("Excluded cipher suite names or patterns: [" + keyx + "]");
                        ServerSocketWrapperJSSE.this.filteredCiphersuitesCache.put(keyx, filteredCiphersuitesList);
                     }
                  }
               }

               if (filteredCiphersuitesList != null && filteredCiphersuitesList.length > 0) {
                  sslEngine.setEnabledCipherSuites(filteredCiphersuitesList);
               }
            }
         }

         String key = ServerSocketWrapperJSSE.this.channels[0].getMinimumTLSProtocolVersion() + "_" + ServerSocketWrapperJSSE.this.channels[0].isSSLv2HelloEnabled();
         filteredCiphersuitesList = (String[])ServerSocketWrapperJSSE.this.enabledProtocolsCache.get(key);
         if (filteredCiphersuitesList == null) {
            filteredCiphersuitesList = SslSupportFacade.getEnabledSslProtocols(sslEngine.getSupportedProtocols(), ServerSocketWrapperJSSE.this.channels[0].getMinimumTLSProtocolVersion(), ServerSocketWrapperJSSE.this.channels[0].isSSLv2HelloEnabled());
            if (filteredCiphersuitesList != null && filteredCiphersuitesList.length > 0) {
               ServerSocketWrapperJSSE.this.enabledProtocolsCache.put(key, filteredCiphersuitesList);
            }
         }

         sslEngine.setEnabledProtocols(filteredCiphersuitesList);
         SslSupportFacade.sslSetupLogInfo(ServerSocketWrapperJSSE.this.getName() + " - " + filteredCiphersuitesList.length + " SSL/TLS protocols enabled:");
         StringBuilder stringBuilder = new StringBuilder();

         for(int i = 0; i < filteredCiphersuitesList.length; ++i) {
            stringBuilder.append(filteredCiphersuitesList[i]);
            if (i + 1 < filteredCiphersuitesList.length) {
               stringBuilder.append(", ");
            }
         }

         if (SslSupportFacade.isDebugEnabled()) {
            String[] ciphers = sslEngine.getEnabledCipherSuites();
            SslSupportFacade.sslSetupLogInfo(ServerSocketWrapperJSSE.this.getName() + " " + ciphers.length + " cipher suites enabled:");

            for(int ix = 0; ix < ciphers.length; ++ix) {
               SslSupportFacade.sslSetupLogInfo(ciphers[ix]);
            }
         }

         sslEngine.setWantClientAuth(ServerSocketWrapperJSSE.this.channels[0].isTwoWaySSLEnabled());
         if (ServerSocketWrapperJSSE.this.channels[0].isClientCertificateEnforced()) {
            sslEngine.setNeedClientAuth(ServerSocketWrapperJSSE.this.channels[0].isClientCertificateEnforced());
         }

         return sslEngine;
      }

      // $FF: synthetic method
      DefaultSNISecureConfigFactory(Object x1) {
         this();
      }
   }
}
