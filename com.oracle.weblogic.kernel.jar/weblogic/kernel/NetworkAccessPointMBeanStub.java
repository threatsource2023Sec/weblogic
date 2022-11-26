package weblogic.kernel;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;
import weblogic.management.configuration.KernelMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;

public class NetworkAccessPointMBeanStub extends MBeanStub implements NetworkAccessPointMBean {
   private String protocol;
   private String address;
   private String name;
   private boolean timeoutConnectionWithPendingResponses;
   private boolean sdpEnabled;
   private boolean resolveDNSName;
   private boolean t3SenderQueueEnabled;
   protected KernelMBean config;
   private int port;
   private boolean httpEnabledForThisProtocol;
   private boolean outboundEnabled;
   private boolean fastSerialization;

   NetworkAccessPointMBeanStub() {
      this.timeoutConnectionWithPendingResponses = false;
      this.port = -1;
      this.httpEnabledForThisProtocol = false;
      this.outboundEnabled = false;
      this.fastSerialization = false;
      this.initializeFromSystemProperties("weblogic.channels.");
   }

   public NetworkAccessPointMBeanStub(String protocol) {
      this(protocol, getKernelConfig());
   }

   private static KernelMBean getKernelConfig() {
      Kernel.ensureInitialized();
      return Kernel.getConfig();
   }

   public NetworkAccessPointMBeanStub(String protocol, KernelMBean config) {
      this.timeoutConnectionWithPendingResponses = false;
      this.port = -1;
      this.httpEnabledForThisProtocol = false;
      this.outboundEnabled = false;
      this.fastSerialization = false;
      this.protocol = protocol;
      this.config = config;
      this.name = "Default";
      this.initializeFromSystemProperties("weblogic." + protocol + ".");
   }

   public static NetworkAccessPointMBean createBootstrapStub() {
      NetworkAccessPointMBeanStub nap = new NetworkAccessPointMBeanStub("ADMIN".toLowerCase(), new KernelMBeanStub());
      nap.httpEnabledForThisProtocol = true;
      nap.outboundEnabled = true;
      nap.name = " Bootstrap";
      return nap;
   }

   public String getName() {
      return this.name;
   }

   public String getProtocol() {
      return this.protocol;
   }

   public void setProtocol(String protocol) throws InvalidAttributeValueException {
      this.protocol = protocol;
   }

   public String getListenAddress() {
      return this.address;
   }

   public void setListenAddress(String address) throws InvalidAttributeValueException {
      this.address = address;
   }

   public String getPublicAddress() {
      return this.getListenAddress();
   }

   public void setPublicAddress(String address) throws InvalidAttributeValueException {
   }

   public int getListenPort() {
      return this.port;
   }

   public void setListenPort(int port) {
      this.port = port;
   }

   public int getPublicPort() {
      return this.getListenPort();
   }

   public void setPublicPort(int port) {
   }

   public boolean getResolveDNSName() {
      return this.resolveDNSName;
   }

   public void setResolveDNSName(boolean enable) {
      this.resolveDNSName = enable;
   }

   public String getProxyAddress() {
      return null;
   }

   public void setProxyAddress(String address) {
   }

   public int getProxyPort() {
      return -1;
   }

   public void setProxyPort(int port) {
   }

   public boolean isHttpEnabledForThisProtocol() {
      return this.httpEnabledForThisProtocol;
   }

   public void setHttpEnabledForThisProtocol(boolean enabled) throws InvalidAttributeValueException {
   }

   public int getAcceptBacklog() {
      return 50;
   }

   public void setAcceptBacklog(int count) {
   }

   public int getMaxBackoffBetweenFailures() {
      return 10000;
   }

   public void setMaxBackoffBetweenFailures(int milliSeconds) {
   }

   public int getLoginTimeoutMillis() {
      return 5000;
   }

   public void setLoginTimeoutMillis(int timeout) {
   }

   public int getConnectTimeout() {
      return this.config.getConnectTimeout();
   }

   public void setConnectTimeout(int timeout) throws InvalidAttributeValueException, DistributedManagementException {
      this.config.setConnectTimeout(timeout);
   }

   public int getTunnelingClientPingSecs() {
      return 0;
   }

   public void setTunnelingClientPingSecs(int secs) throws InvalidAttributeValueException {
   }

   public int getTunnelingClientTimeoutSecs() {
      return 0;
   }

   public void setTunnelingClientTimeoutSecs(int secs) throws InvalidAttributeValueException {
   }

   public boolean isTunnelingEnabled() {
      return false;
   }

   public void setTunnelingEnabled(boolean enabled) throws InvalidAttributeValueException {
   }

   public int getCompleteMessageTimeout() {
      return this.config.getCompleteMessageTimeout();
   }

   public void setCompleteMessageTimeout(int seconds) throws InvalidAttributeValueException, DistributedManagementException {
      this.config.setCompleteMessageTimeout(seconds);
   }

   public int getIdleConnectionTimeout() {
      return this.config.getIdleConnectionTimeout();
   }

   public void setIdleConnectionTimeout(int seconds) throws InvalidAttributeValueException, DistributedManagementException {
      this.config.setIdleConnectionTimeout(seconds);
   }

   public int getMaxMessageSize() {
      int i = this.config.getMaxMessageSize();
      if (i < 0) {
         i = Integer.MAX_VALUE;
      }

      return i;
   }

   public void setMaxMessageSize(int maxsize) throws InvalidAttributeValueException, DistributedManagementException {
      this.config.setMaxMessageSize(maxsize);
   }

   public boolean isOutboundEnabled() {
      return this.outboundEnabled;
   }

   public void setOutboundEnabled(boolean enabled) throws InvalidAttributeValueException {
   }

   public int getChannelWeight() {
      return 0;
   }

   public void setChannelWeight(int weight) throws InvalidAttributeValueException {
   }

   public String getClusterAddress() {
      return null;
   }

   public void setClusterAddress(String address) throws InvalidAttributeValueException {
   }

   public boolean isEnabled() {
      return true;
   }

   public void setEnabled(boolean enabled) throws InvalidAttributeValueException {
   }

   public int getMaxConnectedClients() {
      return Integer.MAX_VALUE;
   }

   public void setMaxConnectedClients(int count) throws InvalidAttributeValueException {
   }

   public boolean isTwoWaySSLEnabled() {
      return false;
   }

   public void setTwoWaySSLEnabled(boolean enabled) {
   }

   public boolean isChannelIdentityCustomized() {
      return false;
   }

   public void setChannelIdentityCustomized(boolean val) {
   }

   public String getCustomPrivateKeyAlias() {
      return null;
   }

   public void setCustomPrivateKeyAlias(String alias) {
   }

   public String getPrivateKeyAlias() {
      return this.getCustomPrivateKeyAlias();
   }

   public boolean isOutboundPrivateKeyEnabled() {
      return false;
   }

   public void setOutboundPrivateKeyEnabled(boolean enabled) {
   }

   public boolean getUseFastSerialization() {
      return "iiop".equals(this.protocol) ? Kernel.getConfig().getIIOP().getUseJavaSerialization() : this.fastSerialization;
   }

   public void setUseFastSerialization(boolean enabled) throws InvalidAttributeValueException {
      this.fastSerialization = enabled;
   }

   public String getCustomPrivateKeyPassPhrase() {
      return null;
   }

   public void setCustomPrivateKeyPassPhrase(String phrase) {
   }

   public String getPrivateKeyPassPhrase() {
      return this.getCustomPrivateKeyPassPhrase();
   }

   public byte[] getCustomPrivateKeyPassPhraseEncrypted() {
      return null;
   }

   public void setCustomPrivateKeyPassPhraseEncrypted(byte[] passwordEncrypted) {
   }

   public boolean isClientCertificateEnforced() {
      return false;
   }

   public void setClientCertificateEnforced(boolean enforce) {
   }

   public boolean getTimeoutConnectionWithPendingResponses() {
      return this.timeoutConnectionWithPendingResponses;
   }

   public void setTimeoutConnectionWithPendingResponses(boolean timeout) throws InvalidAttributeValueException, DistributedManagementException {
      this.timeoutConnectionWithPendingResponses = timeout;
   }

   public int getIdleIIOPConnectionTimeout() {
      return 0;
   }

   public void setIdleIIOPConnectionTimeout(int seconds) throws InvalidAttributeValueException, DistributedManagementException {
   }

   public int getSSLListenPort() {
      return -1;
   }

   public void setSSLListenPort(int port) throws InvalidAttributeValueException {
   }

   public String getExternalDNSName() {
      return null;
   }

   public void setExternalDNSName(String externalDNSName) throws InvalidAttributeValueException {
   }

   public int getLoginTimeoutMillisSSL() {
      return 0;
   }

   public void setLoginTimeoutMillisSSL(int millis) throws InvalidAttributeValueException {
   }

   public int getCompleteT3MessageTimeout() {
      return 0;
   }

   public void setCompleteT3MessageTimeout(int seconds) throws InvalidAttributeValueException, DistributedManagementException {
   }

   public int getCompleteHTTPMessageTimeout() {
      return 0;
   }

   public void setCompleteHTTPMessageTimeout(int seconds) throws InvalidAttributeValueException, DistributedManagementException {
   }

   public int getCompleteCOMMessageTimeout() {
      return 0;
   }

   public void setCompleteCOMMessageTimeout(int seconds) throws InvalidAttributeValueException, DistributedManagementException {
   }

   public int getCompleteIIOPMessageTimeout() {
      return 0;
   }

   public void setCompleteIIOPMessageTimeout(int seconds) throws InvalidAttributeValueException, DistributedManagementException {
   }

   public void setCustomProperties(Properties properties) {
   }

   public Properties getCustomProperties() {
      return null;
   }

   public boolean isSDPEnabled() {
      return this.sdpEnabled;
   }

   public void setSDPEnabled(boolean enable) {
      this.sdpEnabled = enable;
   }

   public String getOutboundPrivateKeyAlias() {
      return null;
   }

   public String getOutboundPrivateKeyPassPhrase() {
      return null;
   }

   public String getCustomIdentityKeyStoreFileName() {
      return null;
   }

   public void setCustomIdentityKeyStoreFileName(String fileName) {
   }

   public String getCustomIdentityKeyStoreType() {
      return null;
   }

   public void setCustomIdentityKeyStoreType(String type) {
   }

   public String getCustomIdentityKeyStorePassPhrase() {
      return null;
   }

   public void setCustomIdentityKeyStorePassPhrase(String passPhrase) {
   }

   public byte[] getCustomIdentityKeyStorePassPhraseEncrypted() {
      return null;
   }

   public void setCustomIdentityKeyStorePassPhraseEncrypted(byte[] passPhraseEncrypted) {
   }

   public String getHostnameVerifier() {
      return null;
   }

   public void setHostnameVerifier(String classname) throws InvalidAttributeValueException {
   }

   public boolean isHostnameVerificationIgnored() {
      return false;
   }

   public void setHostnameVerificationIgnored(boolean ignoreFlag) throws InvalidAttributeValueException {
   }

   public String[] getCiphersuites() {
      return null;
   }

   public void setCiphersuites(String[] ciphers) throws InvalidAttributeValueException {
   }

   public String[] getExcludedCiphersuites() {
      return null;
   }

   public void setExcludedCiphersuites(String[] excludedCiphersuitesciphers) {
   }

   public void setAllowUnencryptedNullCipher(boolean enable) {
   }

   public boolean isAllowUnencryptedNullCipher() {
      return false;
   }

   public String getInboundCertificateValidation() {
      return null;
   }

   public void setInboundCertificateValidation(String validationStyle) {
   }

   public String getOutboundCertificateValidation() {
      return null;
   }

   public void setOutboundCertificateValidation(String validationStyle) {
   }

   public boolean isSSLv2HelloEnabled() {
      return false;
   }

   public void setSSLv2HelloEnabled(boolean ssLv2HelloEnabled) {
   }

   public boolean isClientInitSecureRenegotiationAccepted() {
      return false;
   }

   public void setClientInitSecureRenegotiationAccepted(boolean ClientInitSecureRenegotiationAccepted) {
   }

   public String getMinimumTLSProtocolVersion() {
      return null;
   }

   public void setMinimumTLSProtocolVersion(String minimumTLSProtocolVersion) throws InvalidAttributeValueException {
   }
}
