package weblogic.rjvm.basic;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;

public class BasicServerChannel implements ServerChannel {
   private final Protocol protocol;

   public BasicServerChannel(Protocol protocol) {
      this.protocol = protocol;
   }

   public Protocol getProtocol() {
      return this.protocol;
   }

   public boolean supportsTLS() {
      return this.protocol.isSecure();
   }

   public String getProtocolPrefix() {
      return this.protocol.getProtocolName();
   }

   public String getAddress() {
      return null;
   }

   public String getChannelName() {
      return null;
   }

   public String getClusterAddress() {
      return null;
   }

   public InetAddress getInetAddress() {
      return null;
   }

   public String getListenerKey() {
      return null;
   }

   public int getPort() {
      return 0;
   }

   public String getPublicAddress() {
      return null;
   }

   public int getPublicPort() {
      return 0;
   }

   public InetSocketAddress getPublicInetAddress() {
      return null;
   }

   public boolean hasPublicAddress() {
      return false;
   }

   public boolean supportsHttp() {
      return false;
   }

   public int getAcceptBacklog() {
      return -1;
   }

   public int getCompleteMessageTimeout() {
      return -1;
   }

   public String getConfiguredProtocol() {
      return this.protocol.getProtocolName();
   }

   public int getConnectTimeout() {
      return -1;
   }

   public int getIdleConnectionTimeout() {
      return -1;
   }

   public int getLoginTimeoutMillis() {
      return -1;
   }

   public int getMaxBackoffBetweenFailures() {
      return -1;
   }

   public int getMaxConnectedClients() {
      return -1;
   }

   public int getMaxMessageSize() {
      return -1;
   }

   public String getProxyAddress() {
      return null;
   }

   public int getProxyPort() {
      return 0;
   }

   public boolean getTimeoutConnectionWithPendingResponses() {
      return false;
   }

   public int getTunnelingClientPingSecs() {
      return -1;
   }

   public int getTunnelingClientTimeoutSecs() {
      return -1;
   }

   public boolean getUseFastSerialization() {
      return false;
   }

   public boolean isClientCertificateEnforced() {
      return false;
   }

   public boolean isHttpEnabledForThisProtocol() {
      return false;
   }

   public boolean isOutboundEnabled() {
      return true;
   }

   public boolean isOutboundPrivateKeyEnabled() {
      return false;
   }

   public boolean isTunnelingEnabled() {
      return false;
   }

   public boolean isTwoWaySSLEnabled() {
      return false;
   }

   public boolean isSDPEnabled() {
      return false;
   }

   public boolean isT3SenderQueueDisabled() {
      return false;
   }

   public String getPublicAddressResolvedIfNeeded() {
      return null;
   }

   public boolean getResolveDNSName() {
      return false;
   }

   public boolean isHostnameVerificationIgnored() {
      return false;
   }

   public String getHostnameVerifier() {
      return null;
   }

   public String[] getCiphersuites() {
      return null;
   }

   public String[] getExcludedCiphersuites() {
      return null;
   }

   public boolean isAllowUnencryptedNullCipher() {
      return false;
   }

   public String getInboundCertificateValidation() {
      return null;
   }

   public String getOutboundCertificateValidation() {
      return null;
   }

   public String getAssociatedVirtualTargetName() {
      return null;
   }

   public int compareTo(Object o) {
      BasicServerChannel other = (BasicServerChannel)o;
      return this.protocol.getHandler().getPriority() - other.getProtocol().getHandler().getPriority();
   }

   public boolean isSSLv2HelloEnabled() {
      return false;
   }

   public String getMinimumTLSProtocolVersion() {
      return null;
   }

   public boolean isClientInitSecureRenegotiationAccepted() {
      return false;
   }
}
