package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface TLSMBean extends ConfigurationMBean {
   String getName();

   void setName(String var1) throws InvalidAttributeValueException;

   String getUsage();

   void setUsage(String var1) throws InvalidAttributeValueException;

   String getIdentityPrivateKeyAlias();

   void setIdentityPrivateKeyAlias(String var1) throws InvalidAttributeValueException;

   String getIdentityPrivateKeyPassPhrase();

   void setIdentityPrivateKeyPassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getIdentityPrivateKeyPassPhraseEncrypted();

   void setIdentityPrivateKeyPassPhraseEncrypted(byte[] var1);

   String getIdentityKeyStoreFileName();

   void setIdentityKeyStoreFileName(String var1) throws InvalidAttributeValueException;

   String getIdentityKeyStorePassPhrase();

   void setIdentityKeyStorePassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getIdentityKeyStorePassPhraseEncrypted();

   void setIdentityKeyStorePassPhraseEncrypted(byte[] var1);

   String getIdentityKeyStoreType();

   void setIdentityKeyStoreType(String var1) throws InvalidAttributeValueException;

   boolean isHostnameVerificationIgnored();

   void setHostnameVerificationIgnored(boolean var1) throws InvalidAttributeValueException;

   String getHostnameVerifier();

   void setHostnameVerifier(String var1) throws InvalidAttributeValueException;

   boolean isTwoWaySSLEnabled();

   void setTwoWaySSLEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isClientCertificateEnforced();

   void setClientCertificateEnforced(boolean var1) throws InvalidAttributeValueException;

   String[] getCiphersuites();

   void setCiphersuites(String[] var1) throws InvalidAttributeValueException;

   boolean isAllowUnencryptedNullCipher();

   void setAllowUnencryptedNullCipher(boolean var1);

   String getTrustKeyStoreFileName();

   void setTrustKeyStoreFileName(String var1) throws InvalidAttributeValueException;

   String getTrustKeyStorePassPhrase();

   void setTrustKeyStorePassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getTrustKeyStorePassPhraseEncrypted();

   void setTrustKeyStorePassPhraseEncrypted(byte[] var1);

   String getTrustKeyStoreType();

   void setTrustKeyStoreType(String var1) throws InvalidAttributeValueException;

   String getInboundCertificateValidation();

   void setInboundCertificateValidation(String var1);

   String getOutboundCertificateValidation();

   void setOutboundCertificateValidation(String var1);

   String getMinimumTLSProtocolVersion();

   void setMinimumTLSProtocolVersion(String var1) throws InvalidAttributeValueException;

   boolean isSSLv2HelloEnabled();

   void setSSLv2HelloEnabled(boolean var1);
}
