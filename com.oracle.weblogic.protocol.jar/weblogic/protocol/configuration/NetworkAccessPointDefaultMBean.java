package weblogic.protocol.configuration;

import java.security.AccessController;
import javax.management.InvalidAttributeValueException;
import weblogic.kernel.NetworkAccessPointMBeanStub;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.Protocol;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class NetworkAccessPointDefaultMBean extends NetworkAccessPointMBeanStub {
   private final Protocol p;
   private final String name;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public NetworkAccessPointDefaultMBean(Protocol protocol, ServerMBean config) {
      super(protocol.getProtocolName(), config);
      this.p = protocol;
      this.name = this.p.getQOS() == 103 ? "DefaultAdministration" : (this.p.isSecure() ? "DefaultSecure" : "Default");
   }

   private final ServerMBean getConfig() {
      return (ServerMBean)this.config;
   }

   public String getName() {
      return this.name;
   }

   public String getListenAddress() {
      return this.getConfig().getListenAddress();
   }

   public String getPublicAddress() {
      String addr = this.getConfig().getExternalDNSName();
      return addr == null ? this.getListenAddress() : addr;
   }

   public boolean getResolveDNSName() {
      return this.getConfig().getResolveDNSName();
   }

   public int getListenPort() {
      if (this.p.getQOS() == 103) {
         return this.getConfig().getAdministrationPort();
      } else {
         return this.p.isSecure() ? this.getConfig().getSSL().getListenPort() : this.getConfig().getListenPort();
      }
   }

   public int getPublicPort() {
      return this.getListenPort();
   }

   public boolean isHttpEnabledForThisProtocol() {
      return this.getConfig().isHttpdEnabled();
   }

   public int getAcceptBacklog() {
      return this.getConfig().getAcceptBacklog();
   }

   public int getMaxBackoffBetweenFailures() {
      return this.getConfig().getMaxBackoffBetweenFailures();
   }

   public int getLoginTimeoutMillis() {
      return (this.p.getQOS() == 103 || this.p.isSecure()) && this.getConfig().getSSL() != null ? this.getConfig().getSSL().getLoginTimeoutMillis() : this.getConfig().getLoginTimeoutMillis();
   }

   public int getTunnelingClientPingSecs() {
      return this.getConfig().getTunnelingClientPingSecs();
   }

   public int getTunnelingClientTimeoutSecs() {
      return this.getConfig().getTunnelingClientTimeoutSecs();
   }

   public boolean isTunnelingEnabled() {
      return this.getConfig().isTunnelingEnabled();
   }

   public boolean isOutboundEnabled() {
      return this.p.getQOS() == 103 ? true : this.getConfig().isOutboundEnabled();
   }

   public boolean isOutboundPrivateKeyEnabled() {
      return this.getConfig().isOutboundPrivateKeyEnabled();
   }

   public int getChannelWeight() {
      return 50;
   }

   public String getClusterAddress() {
      String addr = this.getConfig().getCluster() != null ? this.getConfig().getCluster().getClusterAddress() : null;
      return addr == null ? this.getPublicAddress() : addr;
   }

   public boolean isEnabled() {
      if (this.p.getQOS() == 103) {
         return ManagementService.getRuntimeAccess(kernelId).getDomain().isAdministrationPortEnabled();
      } else if (this.p.isSecure()) {
         if (this.getConfig().getSSL() == null) {
            return false;
         } else {
            return this.getConfig().getSSL().isEnabled() && this.getConfig().getSSL().isListenPortEnabled();
         }
      } else {
         return this.getConfig().isListenPortEnabled();
      }
   }

   public void setEnabled(boolean enabled) throws InvalidAttributeValueException {
   }

   public int getMaxConnectedClients() {
      return Integer.MAX_VALUE;
   }

   public void setMaxConnectedClients(int count) throws InvalidAttributeValueException {
   }

   public boolean isTwoWaySSLEnabled() {
      return this.p.isSecure() && this.getConfig().getSSL() != null ? this.getConfig().getSSL().isTwoWaySSLEnabled() : false;
   }

   public void setTwoWaySSLEnabled(boolean enabled) {
   }

   public boolean isChannelIdentityCustomized() {
      return false;
   }

   public void setChannelIdentityCustomized(boolean val) {
   }

   public String getCustomPrivateKeyAlias() {
      return this.p.isSecure() && this.getConfig().getSSL() != null ? this.getConfig().getSSL().getServerPrivateKeyAlias() : null;
   }

   public void setCustomPrivateKeyAlias(String alias) {
   }

   public String getCustomPrivateKeyPassPhrase() {
      return this.p.isSecure() && this.getConfig().getSSL() != null ? this.getConfig().getSSL().getServerPrivateKeyPassPhrase() : null;
   }

   public void setCustomPrivateKeyPassPhrase(String phrase) {
   }

   public boolean isClientCertificateEnforced() {
      return this.p.isSecure() && this.getConfig().getSSL() != null ? this.getConfig().getSSL().isClientCertificateEnforced() : false;
   }

   public void setClientCertificateEnforced(boolean enforce) {
   }

   public String getExternalDNSName() {
      return this.getPublicAddress();
   }

   public String getOutboundPrivateKeyAlias() {
      return this.p.isSecure() && this.getConfig().getSSL() != null ? this.getConfig().getSSL().getOutboundPrivateKeyAlias() : null;
   }

   public String getOutboundPrivateKeyPassPhrase() {
      return this.p.isSecure() && this.getConfig().getSSL() != null ? this.getConfig().getSSL().getOutboundPrivateKeyPassPhrase() : null;
   }

   public String getCustomIdentityKeyStoreFileName() {
      return this.getConfig().getCustomIdentityKeyStoreFileName();
   }

   public void setCustomIdentityKeyStoreFileName(String fileName) {
   }

   public String getCustomIdentityKeyStoreType() {
      return this.getConfig().getCustomIdentityKeyStoreType();
   }

   public void setCustomIdentityKeyStoreType(String type) {
   }

   public String getCustomIdentityKeyStorePassPhrase() {
      return this.getConfig().getCustomIdentityKeyStorePassPhrase();
   }

   public void setCustomIdentityKeyStorePassPhrase(String passPhrase) {
   }

   public byte[] getCustomIdentityKeyStorePassPhraseEncrypted() {
      return this.getConfig().getCustomIdentityKeyStorePassPhraseEncrypted();
   }

   public void setCustomIdentityKeyStorePassPhraseEncrypted(byte[] passPhraseEncrypted) {
   }

   public String getHostnameVerifier() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().getHostnameVerifier() : null;
   }

   public void setHostnameVerifier(String classname) throws InvalidAttributeValueException {
   }

   public boolean isHostnameVerificationIgnored() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().isHostnameVerificationIgnored() : false;
   }

   public void setHostnameVerificationIgnored(boolean ignoreFlag) throws InvalidAttributeValueException {
   }

   public String[] getCiphersuites() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().getCiphersuites() : null;
   }

   public void setCiphersuites(String[] ciphers) throws InvalidAttributeValueException {
   }

   public String[] getExcludedCiphersuites() {
      String[] cs = null;
      if (this.getConfig().getSSL() != null) {
         cs = this.getConfig().getSSL().getExcludedCiphersuites();
      }

      return cs;
   }

   public void setExcludedCiphersuites(String[] excludedCiphersuites) {
   }

   public void setAllowUnencryptedNullCipher(boolean enable) {
   }

   public boolean isAllowUnencryptedNullCipher() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().isAllowUnencryptedNullCipher() : false;
   }

   public String getInboundCertificateValidation() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().getInboundCertificateValidation() : null;
   }

   public void setInboundCertificateValidation(String validationStyle) {
   }

   public String getOutboundCertificateValidation() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().getOutboundCertificateValidation() : null;
   }

   public void setOutboundCertificateValidation(String validationStyle) {
   }

   public String getMinimumTLSProtocolVersion() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().getMinimumTLSProtocolVersion() : null;
   }

   public void setMinimumTLSProtocolVersion(String minimumTLSProtocolVersion) throws InvalidAttributeValueException {
   }

   public boolean isSSLv2HelloEnabled() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().isSSLv2HelloEnabled() : false;
   }

   public void setSSLv2HelloEnabled(boolean ssLv2HelloEnabled) {
   }

   public boolean isClientInitSecureRenegotiationAccepted() {
      return this.getConfig().getSSL() != null ? this.getConfig().getSSL().isClientInitSecureRenegotiationAccepted() : false;
   }

   public void setClientInitSecureRenegotiationAccepted(boolean ClientInitSecureRenegotiationAccepted) {
   }
}
