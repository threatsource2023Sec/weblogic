package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SSLMBean extends ConfigurationMBean {
   String IDENTITY_AND_TRUST_LOCATIONS_KEYSTORES = "KeyStores";
   String IDENTITY_AND_TRUST_LOCATIONS_FILES_OR_KEYSTORE_PROVIDERS = "FilesOrKeyStoreProviders";
   String BUILTIN_SSL_VALIDATION_ONLY = "BuiltinSSLValidationOnly";
   String BUILTIN_SSL_VALIDATION_AND_CERT_PATH_VALIDATORS = "BuiltinSSLValidationAndCertPathValidators";

   boolean isUseJava();

   void setUseJava(boolean var1);

   boolean isEnabled();

   void setEnabled(boolean var1) throws InvalidAttributeValueException;

   String[] getCiphersuites();

   void setCiphersuites(String[] var1) throws InvalidAttributeValueException;

   String[] getExcludedCiphersuites();

   void setExcludedCiphersuites(String[] var1);

   String getCertAuthenticator();

   void setCertAuthenticator(String var1) throws InvalidAttributeValueException;

   String getHostnameVerifier();

   void setHostnameVerifier(String var1) throws InvalidAttributeValueException;

   boolean isHostnameVerificationIgnored();

   void setHostnameVerificationIgnored(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getTrustedCAFileName();

   /** @deprecated */
   @Deprecated
   void setTrustedCAFileName(String var1) throws InvalidAttributeValueException;

   boolean isKeyEncrypted();

   void setKeyEncrypted(boolean var1) throws InvalidAttributeValueException;

   boolean isAcceptKSSDemoCertsEnabled();

   void setAcceptKSSDemoCertsEnabled(boolean var1);

   int getExportKeyLifespan();

   void setExportKeyLifespan(int var1) throws InvalidAttributeValueException;

   boolean isClientCertificateEnforced();

   void setClientCertificateEnforced(boolean var1);

   /** @deprecated */
   @Deprecated
   String getServerCertificateFileName();

   /** @deprecated */
   @Deprecated
   void setServerCertificateFileName(String var1);

   int getListenPort();

   void setListenPort(int var1);

   boolean isListenPortEnabled();

   /** @deprecated */
   @Deprecated
   String getServerCertificateChainFileName();

   /** @deprecated */
   @Deprecated
   void setServerCertificateChainFileName(String var1);

   int getCertificateCacheSize();

   void setCertificateCacheSize(int var1);

   boolean isHandlerEnabled();

   void setHandlerEnabled(boolean var1);

   int getLoginTimeoutMillis();

   void setLoginTimeoutMillis(int var1);

   /** @deprecated */
   @Deprecated
   String getServerKeyFileName();

   /** @deprecated */
   @Deprecated
   void setServerKeyFileName(String var1);

   boolean isTwoWaySSLEnabled();

   void setTwoWaySSLEnabled(boolean var1);

   String getServerPrivateKeyAlias();

   void setServerPrivateKeyAlias(String var1);

   String getServerPrivateKeyPassPhrase();

   void setServerPrivateKeyPassPhrase(String var1);

   byte[] getServerPrivateKeyPassPhraseEncrypted();

   void setServerPrivateKeyPassPhraseEncrypted(byte[] var1);

   boolean isSSLRejectionLoggingEnabled();

   void setSSLRejectionLoggingEnabled(boolean var1);

   String getIdentityAndTrustLocations();

   void setIdentityAndTrustLocations(String var1);

   String getInboundCertificateValidation();

   void setInboundCertificateValidation(String var1);

   String getOutboundCertificateValidation();

   void setOutboundCertificateValidation(String var1);

   void setAllowUnencryptedNullCipher(boolean var1);

   boolean isAllowUnencryptedNullCipher();

   boolean isUseServerCerts();

   void setUseServerCerts(boolean var1);

   void setJSSEEnabled(boolean var1);

   boolean isJSSEEnabled();

   void setUseClientCertForOutbound(boolean var1);

   boolean isUseClientCertForOutbound();

   void setClientCertAlias(String var1);

   String getClientCertAlias();

   String getClientCertPrivateKeyPassPhrase();

   void setClientCertPrivateKeyPassPhrase(String var1);

   byte[] getClientCertPrivateKeyPassPhraseEncrypted();

   void setClientCertPrivateKeyPassPhraseEncrypted(byte[] var1);

   String getOutboundPrivateKeyAlias();

   String getOutboundPrivateKeyPassPhrase();

   byte[] getOutboundPrivateKeyPassPhraseEncrypted();

   String getMinimumTLSProtocolVersion();

   void setMinimumTLSProtocolVersion(String var1) throws InvalidAttributeValueException;

   boolean isSSLv2HelloEnabled();

   void setSSLv2HelloEnabled(boolean var1);

   boolean isClientInitSecureRenegotiationAccepted();

   void setClientInitSecureRenegotiationAccepted(boolean var1);
}
