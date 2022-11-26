package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WTCLocalTuxDomMBean extends ConfigurationMBean {
   void setAccessPoint(String var1) throws InvalidAttributeValueException;

   String getAccessPoint();

   void setAccessPointId(String var1) throws InvalidAttributeValueException;

   String getAccessPointId();

   void setSecurity(String var1) throws InvalidAttributeValueException;

   String getSecurity();

   void setConnectionPolicy(String var1) throws InvalidAttributeValueException;

   String getConnectionPolicy();

   void setConnPrincipalName(String var1) throws InvalidAttributeValueException;

   String getConnPrincipalName();

   void setRetryInterval(long var1) throws InvalidAttributeValueException;

   long getRetryInterval();

   void setMaxRetries(long var1) throws InvalidAttributeValueException;

   long getMaxRetries();

   void setBlockTime(long var1) throws InvalidAttributeValueException;

   long getBlockTime();

   void setNWAddr(String var1) throws InvalidAttributeValueException;

   String getNWAddr();

   void setCmpLimit(int var1) throws InvalidAttributeValueException;

   int getCmpLimit();

   void setMinEncryptBits(String var1) throws InvalidAttributeValueException;

   String getMinEncryptBits();

   void setMaxEncryptBits(String var1) throws InvalidAttributeValueException;

   String getMaxEncryptBits();

   void setInteroperate(String var1) throws InvalidAttributeValueException;

   String getInteroperate();

   void setKeepAlive(int var1) throws InvalidAttributeValueException;

   int getKeepAlive();

   void setKeepAliveWait(int var1) throws InvalidAttributeValueException;

   int getKeepAliveWait();

   String getUseSSL();

   void setUseSSL(String var1) throws InvalidAttributeValueException;

   String getKeyStoresLocation();

   void setKeyStoresLocation(String var1) throws InvalidAttributeValueException;

   String getIdentityKeyStoreFileName();

   void setIdentityKeyStoreFileName(String var1) throws InvalidAttributeValueException;

   String getIdentityKeyStorePassPhrase();

   void setIdentityKeyStorePassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getIdentityKeyStorePassPhraseEncrypted();

   void setIdentityKeyStorePassPhraseEncrypted(byte[] var1);

   String getPrivateKeyAlias();

   void setPrivateKeyAlias(String var1) throws InvalidAttributeValueException;

   String getPrivateKeyPassPhrase();

   void setPrivateKeyPassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getPrivateKeyPassPhraseEncrypted();

   void setPrivateKeyPassPhraseEncrypted(byte[] var1);

   String getTrustKeyStoreFileName();

   void setTrustKeyStoreFileName(String var1) throws InvalidAttributeValueException;

   String getTrustKeyStorePassPhrase();

   void setTrustKeyStorePassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getTrustKeyStorePassPhraseEncrypted();

   void setTrustKeyStorePassPhraseEncrypted(byte[] var1);

   void setSSLProtocolVersion(String var1) throws InvalidAttributeValueException;

   String getSSLProtocolVersion();
}
