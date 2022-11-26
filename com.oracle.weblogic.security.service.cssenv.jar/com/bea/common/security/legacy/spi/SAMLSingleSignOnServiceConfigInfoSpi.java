package com.bea.common.security.legacy.spi;

import java.util.Properties;

public interface SAMLSingleSignOnServiceConfigInfoSpi {
   boolean isV1Config();

   boolean isV2Config();

   boolean isSourceSiteEnabled();

   boolean isITSArtifactEnabled();

   boolean isITSPostEnabled();

   String getSourceIdHex();

   byte[] getSourceIdBytes();

   String[] getIntersiteTransferURIs();

   boolean isITSRequiresSSL();

   String[] getAssertionRetrievalURIs();

   boolean isARSRequiresSSL();

   boolean isARSRequiresTwoWaySSL();

   String getAssertionStoreClassName();

   Properties getAssertionStoreProperties();

   String getSigningKeyAlias();

   String getSigningKeyPassPhrase();

   boolean isDestinationSiteEnabled();

   boolean isACSArtifactEnabled();

   boolean isACSPostEnabled();

   String[] getAssertionConsumerURIs();

   boolean isACSRequiresSSL();

   boolean isPOSTRecipientCheckEnabled();

   boolean isPOSTOneUseCheckEnabled();

   String getUsedAssertionCacheClassName();

   Properties getUsedAssertionCacheProperties();

   String getSSLClientIdentityAlias();

   String getSSLClientIdentityPassPhrase();

   void close();

   String[] getAllowedTargetHosts();
}
