package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;

public interface FederationServicesMBean extends ConfigurationMBean {
   boolean isSourceSiteEnabled();

   void setSourceSiteEnabled(boolean var1) throws InvalidAttributeValueException;

   String getSourceSiteURL();

   void setSourceSiteURL(String var1) throws InvalidAttributeValueException;

   String getSourceIdHex();

   String getSourceIdBase64();

   String[] getIntersiteTransferURIs();

   void setIntersiteTransferURIs(String[] var1) throws InvalidAttributeValueException;

   boolean isITSRequiresSSL();

   void setITSRequiresSSL(boolean var1) throws InvalidAttributeValueException;

   String[] getAssertionRetrievalURIs();

   void setAssertionRetrievalURIs(String[] var1) throws InvalidAttributeValueException;

   boolean isARSRequiresSSL();

   void setARSRequiresSSL(boolean var1) throws InvalidAttributeValueException;

   boolean isARSRequiresTwoWaySSL();

   void setARSRequiresTwoWaySSL(boolean var1) throws InvalidAttributeValueException;

   String getAssertionStoreClassName();

   void setAssertionStoreClassName(String var1) throws InvalidAttributeValueException;

   Properties getAssertionStoreProperties();

   void setAssertionStoreProperties(Properties var1) throws InvalidAttributeValueException;

   String getSigningKeyAlias();

   void setSigningKeyAlias(String var1) throws InvalidAttributeValueException;

   String getSigningKeyPassPhrase();

   void setSigningKeyPassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getSigningKeyPassPhraseEncrypted();

   void setSigningKeyPassPhraseEncrypted(byte[] var1) throws InvalidAttributeValueException;

   boolean isDestinationSiteEnabled();

   void setDestinationSiteEnabled(boolean var1) throws InvalidAttributeValueException;

   String[] getAssertionConsumerURIs();

   void setAssertionConsumerURIs(String[] var1) throws InvalidAttributeValueException;

   boolean isACSRequiresSSL();

   void setACSRequiresSSL(boolean var1) throws InvalidAttributeValueException;

   boolean isPOSTRecipientCheckEnabled();

   void setPOSTRecipientCheckEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isPOSTOneUseCheckEnabled();

   void setPOSTOneUseCheckEnabled(boolean var1) throws InvalidAttributeValueException;

   String getUsedAssertionCacheClassName();

   void setUsedAssertionCacheClassName(String var1) throws InvalidAttributeValueException;

   Properties getUsedAssertionCacheProperties();

   void setUsedAssertionCacheProperties(Properties var1) throws InvalidAttributeValueException;

   String getSSLClientIdentityAlias();

   void setSSLClientIdentityAlias(String var1) throws InvalidAttributeValueException;

   String getSSLClientIdentityPassPhrase();

   void setSSLClientIdentityPassPhrase(String var1) throws InvalidAttributeValueException;

   byte[] getSSLClientIdentityPassPhraseEncrypted();

   void setSSLClientIdentityPassPhraseEncrypted(byte[] var1) throws InvalidAttributeValueException;

   String[] getAllowedTargetHosts();

   void setAllowedTargetHosts(String[] var1) throws InvalidAttributeValueException;
}
