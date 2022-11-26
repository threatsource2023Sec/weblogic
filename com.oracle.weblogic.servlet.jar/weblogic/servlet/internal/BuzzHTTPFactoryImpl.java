package weblogic.servlet.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.AccessController;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.buzzmessagebus.BuzzHTTP;
import weblogic.buzzmessagebus.BuzzHTTPFactory;
import weblogic.buzzmessagebus.BuzzHTTPSupport;
import weblogic.buzzmessagebus.BuzzMessageBusLogger;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.utils.io.Chunk;

@Service
@Named
public final class BuzzHTTPFactoryImpl implements BuzzHTTPFactory {
   static volatile ServerChannel dummyBuzzChannel;

   public BuzzHTTP create(Chunk head, Socket s, BuzzHTTPSupport buzzHTTPSupport, boolean secure) throws IOException {
      return new BuzzMuxableSocketHTTP(head, s, buzzHTTPSupport, secure, this.getBuzzChannel());
   }

   private ServerChannel getBuzzChannel() throws IOException {
      if (dummyBuzzChannel != null) {
         return dummyBuzzChannel;
      } else {
         synchronized(this) {
            if (dummyBuzzChannel != null) {
               return dummyBuzzChannel;
            } else {
               dummyBuzzChannel = new ServerChannel() {
                  int completeMessageTimeout = -1;

                  public InetAddress getInetAddress() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getPort() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public Protocol getProtocol() {
                     return ProtocolHandlerHTTP.PROTOCOL_HTTP;
                  }

                  public String getAddress() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getPublicAddress() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getPublicPort() {
                     return 0;
                  }

                  public String getClusterAddress() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getChannelName() {
                     return "BuzzChannel";
                  }

                  public String getListenerKey() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean supportsHttp() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean hasPublicAddress() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getConfiguredProtocol() {
                     return ProtocolHandlerHTTP.PROTOCOL_HTTP.getProtocolName();
                  }

                  public int getConnectTimeout() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isTunnelingEnabled() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getTunnelingClientTimeoutSecs() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean getUseFastSerialization() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean getTimeoutConnectionWithPendingResponses() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getLoginTimeoutMillis() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getAcceptBacklog() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getMaxBackoffBetweenFailures() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isTwoWaySSLEnabled() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isClientCertificateEnforced() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getMaxMessageSize() {
                     return -1;
                  }

                  public int getMaxConnectedClients() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getIdleConnectionTimeout() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getCompleteMessageTimeout() {
                     if (this.completeMessageTimeout == -1) {
                        AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
                        ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
                        this.completeMessageTimeout = server.getCompleteMessageTimeout();
                     }

                     return this.completeMessageTimeout;
                  }

                  public boolean isOutboundEnabled() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isOutboundPrivateKeyEnabled() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getProxyAddress() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getProxyPort() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public int getTunnelingClientPingSecs() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isHttpEnabledForThisProtocol() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isSDPEnabled() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isT3SenderQueueDisabled() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getPublicAddressResolvedIfNeeded() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean getResolveDNSName() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isHostnameVerificationIgnored() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getHostnameVerifier() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String[] getCiphersuites() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String[] getExcludedCiphersuites() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isAllowUnencryptedNullCipher() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getInboundCertificateValidation() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getOutboundCertificateValidation() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public InetSocketAddress getPublicInetAddress() {
                     return KernelStatus.getBuzzSocketAddress();
                  }

                  public String getProtocolPrefix() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean supportsTLS() {
                     return false;
                  }

                  public String getAssociatedVirtualTargetName() {
                     return null;
                  }

                  public int compareTo(Object o) {
                     return 0;
                  }

                  public boolean isSSLv2HelloEnabled() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public String getMinimumTLSProtocolVersion() {
                     BuzzHTTPFactoryImpl.log();
                     throw new UnsupportedOperationException();
                  }

                  public boolean isClientInitSecureRenegotiationAccepted() {
                     return false;
                  }
               };
               return dummyBuzzChannel;
            }
         }
      }
   }

   private static void log() {
      BuzzMessageBusLogger.logDummyBuzzChannelUsage(new Exception());
   }
}
