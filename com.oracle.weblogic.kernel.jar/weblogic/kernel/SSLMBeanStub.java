package weblogic.kernel;

import weblogic.management.configuration.SSLMBean;

final class SSLMBeanStub extends MBeanStub implements SSLMBean {
   private boolean acceptKSSDemoCertsEnabled = false;
   private String minimumTLSVersion = null;
   private boolean SSLv2HelloEnabled = false;
   private boolean isTwoWaySSLEnabled = false;
   private boolean isAllowUnencryptedNullCipher = false;

   SSLMBeanStub() {
      this.initializeFromSystemProperties("weblogic.ssl.");
   }

   public boolean isUseJava() {
      return true;
   }

   public void setUseJava(boolean usejava) {
   }

   public String getMDAcceleration() {
      return "Native/Java";
   }

   public void setMDAcceleration(String accel) {
   }

   public String getRC4Acceleration() {
      return "Native/Java";
   }

   public void setRC4Acceleration(String accel) {
   }

   public String getRSAAcceleration() {
      return "Native/Java";
   }

   public void setRSAAcceleration(String accel) {
   }

   public boolean isEnabled() {
      return false;
   }

   public void setEnabled(boolean enable) {
   }

   public String[] getCiphersuites() {
      return null;
   }

   public void setCiphersuites(String[] ciphers) {
   }

   public String[] getExcludedCiphersuites() {
      return null;
   }

   public void setExcludedCiphersuites(String[] ciphers) {
   }

   public String getCertAuthenticator() {
      return null;
   }

   public void setCertAuthenticator(String classname) {
   }

   public String getTrustedCAFileName() {
      return "trusted-ca.pem";
   }

   public void setTrustedCAFileName(String fileName) {
   }

   public int getExportKeyLifespan() {
      return 500;
   }

   public void setExportKeyLifespan(int lifespan) {
   }

   public boolean isAcceptKSSDemoCertsEnabled() {
      return this.acceptKSSDemoCertsEnabled;
   }

   public void setAcceptKSSDemoCertsEnabled(boolean enabled) {
      this.acceptKSSDemoCertsEnabled = enabled;
   }

   public boolean isUseServerCerts() {
      return false;
   }

   public void setUseServerCerts(boolean enabled) {
   }

   public void setJSSEEnabled(boolean enableJSSE) {
   }

   public boolean isJSSEEnabled() {
      return false;
   }

   public boolean isClientCertificateEnforced() {
      return false;
   }

   public void setClientCertificateEnforced(boolean enforce) {
   }

   public String getServerCertificateFileName() {
      return "server-cert.der";
   }

   public void setServerCertificateFileName(String fileName) {
   }

   public int getListenPort() {
      return 7002;
   }

   public void setListenPort(int port) {
   }

   public boolean isListenPortEnabled() {
      return false;
   }

   public void setListenPortEnabled(boolean enable) {
   }

   public String getServerCertificateChainFileName() {
      return "server-certchain.pem";
   }

   public void setServerCertificateChainFileName(String fileName) {
   }

   public int getCertificateCacheSize() {
      return 3;
   }

   public void setCertificateCacheSize(int size) {
   }

   public boolean isHandlerEnabled() {
      return true;
   }

   public void setHandlerEnabled(boolean enable) {
   }

   public int getLoginTimeoutMillis() {
      return 25000;
   }

   public void setLoginTimeoutMillis(int millis) {
   }

   public String getServerKeyFileName() {
      return "server-key.der";
   }

   public void setServerKeyFileName(String fileName) {
   }

   /** @deprecated */
   @Deprecated
   public int getPeerValidationEnforced() {
      return 0;
   }

   /** @deprecated */
   @Deprecated
   public void setPeerValidationEnforced(int checkLevel) {
   }

   public boolean isKeyEncrypted() {
      return false;
   }

   public void setKeyEncrypted(boolean keyIsEncrypted) {
   }

   public String getHostnameVerifier() {
      return null;
   }

   public void setHostnameVerifier(String classname) {
   }

   public boolean isHostnameVerificationIgnored() {
      return false;
   }

   public void setHostnameVerificationIgnored(boolean ignoreFlag) {
   }

   public boolean isTwoWaySSLEnabled() {
      return false;
   }

   public void setTwoWaySSLEnabled(boolean isTwoWaySSLEnabled) {
      this.isTwoWaySSLEnabled = isTwoWaySSLEnabled;
   }

   public boolean isAllowUnencryptedNullCipher() {
      return this.isAllowUnencryptedNullCipher;
   }

   public void setAllowUnencryptedNullCipher(boolean isAllowUnencryptedNullCipher) {
      this.isAllowUnencryptedNullCipher = isAllowUnencryptedNullCipher;
   }

   public String getServerPrivateKeyAlias() {
      return null;
   }

   public void setServerPrivateKeyAlias(String alias) {
   }

   public String getServerPrivateKeyPassPhrase() {
      return null;
   }

   public void setServerPrivateKeyPassPhrase(String phrase) {
   }

   public byte[] getServerPrivateKeyPassPhraseEncrypted() {
      return null;
   }

   public void setServerPrivateKeyPassPhraseEncrypted(byte[] phraseEncrypted) {
   }

   public boolean isSSLRejectionLoggingEnabled() {
      return true;
   }

   public void setSSLRejectionLoggingEnabled(boolean enabled) {
   }

   public String getIdentityAndTrustLocations() {
      return null;
   }

   public void setIdentityAndTrustLocations(String locations) {
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

   public boolean isUseClientCertForOutbound() {
      return false;
   }

   public void setUseClientCertForOutbound(boolean enabled) {
   }

   public String getClientCertAlias() {
      return null;
   }

   public void setClientCertAlias(String alias) {
   }

   public String getClientCertPrivateKeyPassPhrase() {
      return null;
   }

   public void setClientCertPrivateKeyPassPhrase(String phrase) {
   }

   public byte[] getClientCertPrivateKeyPassPhraseEncrypted() {
      return null;
   }

   public void setClientCertPrivateKeyPassPhraseEncrypted(byte[] phraseEncrypted) {
   }

   public String getOutboundPrivateKeyAlias() {
      return null;
   }

   public String getOutboundPrivateKeyPassPhrase() {
      return null;
   }

   public byte[] getOutboundPrivateKeyPassPhraseEncrypted() {
      return null;
   }

   public boolean isSSLv2HelloEnabled() {
      return this.SSLv2HelloEnabled;
   }

   public void setSSLv2HelloEnabled(boolean SSLv2HelloEnabled) {
      this.SSLv2HelloEnabled = SSLv2HelloEnabled;
   }

   public boolean isClientInitSecureRenegotiationAccepted() {
      return false;
   }

   public void setClientInitSecureRenegotiationAccepted(boolean ClientInitSecureRenegotiationAccepted) {
   }

   public String getMinimumTLSProtocolVersion() {
      return this.minimumTLSVersion;
   }

   public void setMinimumTLSProtocolVersion(String minimumTLSProtocolVersion) {
      this.minimumTLSVersion = minimumTLSProtocolVersion;
   }
}
