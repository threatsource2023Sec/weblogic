package weblogic.protocol;

import java.net.InetAddress;
import weblogic.rmi.spi.Channel;

public interface ServerChannel extends Channel, Comparable {
   int INVALID_PORT = -1;
   String DEFAULT_CHANNEL_NAME = "Default";
   String DEFAULT_SECURE_CHANNEL_NAME = "DefaultSecure";
   String DEFAULT_ADMIN_CHANNEL = "DefaultAdministration";

   InetAddress getInetAddress();

   int getPort();

   Protocol getProtocol();

   String getAddress();

   String getPublicAddress();

   int getPublicPort();

   String getClusterAddress();

   String getChannelName();

   String getListenerKey();

   boolean supportsHttp();

   boolean hasPublicAddress();

   String getConfiguredProtocol();

   int getConnectTimeout();

   boolean isTunnelingEnabled();

   int getTunnelingClientTimeoutSecs();

   boolean getUseFastSerialization();

   boolean getTimeoutConnectionWithPendingResponses();

   int getLoginTimeoutMillis();

   int getAcceptBacklog();

   int getMaxBackoffBetweenFailures();

   boolean isTwoWaySSLEnabled();

   boolean isClientCertificateEnforced();

   int getMaxMessageSize();

   int getMaxConnectedClients();

   int getIdleConnectionTimeout();

   int getCompleteMessageTimeout();

   boolean isOutboundEnabled();

   boolean isOutboundPrivateKeyEnabled();

   String getProxyAddress();

   int getProxyPort();

   int getTunnelingClientPingSecs();

   boolean isHttpEnabledForThisProtocol();

   boolean isSDPEnabled();

   boolean isT3SenderQueueDisabled();

   String getPublicAddressResolvedIfNeeded();

   boolean getResolveDNSName();

   boolean isHostnameVerificationIgnored();

   String getHostnameVerifier();

   String[] getCiphersuites();

   String[] getExcludedCiphersuites();

   boolean isAllowUnencryptedNullCipher();

   String getInboundCertificateValidation();

   String getOutboundCertificateValidation();

   String getAssociatedVirtualTargetName();

   String getMinimumTLSProtocolVersion();

   boolean isSSLv2HelloEnabled();

   boolean isClientInitSecureRenegotiationAccepted();
}
