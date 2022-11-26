package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.AnyIdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.AuthenticatorMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;

public interface OracleIdentityCloudIntegratorMBean extends StandardInterface, DescriptorBean, AuthenticatorMBean, IdentityAsserterMBean, AnyIdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String getHost();

   void setHost(String var1) throws InvalidAttributeValueException;

   Integer getPort();

   void setPort(Integer var1) throws InvalidAttributeValueException;

   boolean isSSLEnabled();

   void setSSLEnabled(boolean var1) throws InvalidAttributeValueException;

   Integer getConnectTimeout();

   void setConnectTimeout(Integer var1) throws InvalidAttributeValueException;

   Integer getResponseReadTimeout();

   void setResponseReadTimeout(Integer var1) throws InvalidAttributeValueException;

   String getTenant();

   void setTenant(String var1) throws InvalidAttributeValueException;

   String[] getTenantNames();

   void setTenantNames(String[] var1) throws InvalidAttributeValueException;

   String getBasePath();

   void setBasePath(String var1) throws InvalidAttributeValueException;

   String[] getTenantHeaderNames();

   void setTenantHeaderNames(String[] var1) throws InvalidAttributeValueException;

   String[] getSupportedTypes();

   String[] getActiveTypes();

   void setActiveTypes(String[] var1) throws InvalidAttributeValueException;

   boolean getBase64DecodingRequired();

   boolean isTokenSecureTransportRequired();

   void setTokenSecureTransportRequired(boolean var1) throws InvalidAttributeValueException;

   boolean isTokenVirtualUserAllowed();

   void setTokenVirtualUserAllowed(boolean var1) throws InvalidAttributeValueException;

   String getTokenValidationLevel();

   void setTokenValidationLevel(String var1) throws InvalidAttributeValueException;

   String getIssuer();

   void setIssuer(String var1) throws InvalidAttributeValueException;

   String getJSONWebKeySetURI();

   void setJSONWebKeySetURI(String var1) throws InvalidAttributeValueException;

   int getTokenClockSkew();

   void setTokenClockSkew(int var1) throws InvalidAttributeValueException;

   String getClientTenant();

   void setClientTenant(String var1) throws InvalidAttributeValueException;

   String getClientId();

   void setClientId(String var1) throws InvalidAttributeValueException;

   String getClientSecret();

   void setClientSecret(String var1) throws InvalidAttributeValueException;

   Integer getAccessTokenTimeoutWindow();

   void setAccessTokenTimeoutWindow(Integer var1) throws InvalidAttributeValueException;

   boolean isSignaturePreferX509Certificate();

   void setSignaturePreferX509Certificate(boolean var1) throws InvalidAttributeValueException;

   String getUserNameTokenClaim();

   void setUserNameTokenClaim(String var1) throws InvalidAttributeValueException;

   String getUserIDTokenClaim();

   void setUserIDTokenClaim(String var1) throws InvalidAttributeValueException;

   String getGroupsTokenClaim();

   void setGroupsTokenClaim(String var1) throws InvalidAttributeValueException;

   String getTenantTokenClaim();

   void setTenantTokenClaim(String var1) throws InvalidAttributeValueException;

   String getAppRolesTokenClaim();

   void setAppRolesTokenClaim(String var1) throws InvalidAttributeValueException;

   String getUserAuthenticationAssertionAttribute();

   void setUserAuthenticationAssertionAttribute(String var1) throws InvalidAttributeValueException;

   String getUserNameResourceAttribute();

   void setUserNameResourceAttribute(String var1) throws InvalidAttributeValueException;

   String getUserIDResourceAttribute();

   void setUserIDResourceAttribute(String var1) throws InvalidAttributeValueException;

   String getClientIDResourceAttribute();

   void setClientIDResourceAttribute(String var1) throws InvalidAttributeValueException;

   boolean isCacheEnabled();

   void setCacheEnabled(boolean var1) throws InvalidAttributeValueException;

   Integer getCacheSize();

   void setCacheSize(Integer var1) throws InvalidAttributeValueException;

   Integer getCacheTTL();

   void setCacheTTL(Integer var1) throws InvalidAttributeValueException;

   boolean isTokenCacheEnabled();

   void setTokenCacheEnabled(boolean var1) throws InvalidAttributeValueException;

   String getTenantHostNameTemplate();

   void setTenantHostNameTemplate(String var1) throws InvalidAttributeValueException;

   String getAppNameFilterHeaderName();

   void setAppNameFilterHeaderName(String var1) throws InvalidAttributeValueException;

   boolean isOnlyUserTokenClaimsEnabled();

   void setOnlyUserTokenClaimsEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isAnyIdentityDomainEnabled();

   boolean isSyncFilterEnabled();

   void setSyncFilterEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isSyncFilterOnlyClientCertRequests();

   void setSyncFilterOnlyClientCertRequests(boolean var1) throws InvalidAttributeValueException;

   boolean isSyncFilterMatchCase();

   void setSyncFilterMatchCase(boolean var1) throws InvalidAttributeValueException;

   boolean isSyncFilterPreferHeader();

   void setSyncFilterPreferHeader(boolean var1) throws InvalidAttributeValueException;

   String[] getSyncFilterUserHeaderNames();

   void setSyncFilterUserHeaderNames(String[] var1) throws InvalidAttributeValueException;

   String getClientNameTokenClaim();

   void setClientNameTokenClaim(String var1) throws InvalidAttributeValueException;

   String getClientIDTokenClaim();

   void setClientIDTokenClaim(String var1) throws InvalidAttributeValueException;

   String getClientTenantTokenClaim();

   void setClientTenantTokenClaim(String var1) throws InvalidAttributeValueException;

   String getResourceTenantTokenClaim();

   void setResourceTenantTokenClaim(String var1) throws InvalidAttributeValueException;

   boolean isAudienceEnabled();

   void setAudienceEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isClientAsUserPrincipalEnabled();

   void setClientAsUserPrincipalEnabled(boolean var1) throws InvalidAttributeValueException;

   Integer getTenantDataFlushInterval();

   void setTenantDataFlushInterval(Integer var1) throws InvalidAttributeValueException;

   Integer getThreadLockTimeout();

   void setThreadLockTimeout(Integer var1) throws InvalidAttributeValueException;

   boolean isTenantDataReloadEnabled();

   void setTenantDataReloadEnabled(boolean var1) throws InvalidAttributeValueException;

   Integer getTenantDataReloadInterval();

   void setTenantDataReloadInterval(Integer var1) throws InvalidAttributeValueException;

   String[] getKnownTenantPrefixList();

   void setKnownTenantPrefixList(String[] var1) throws InvalidAttributeValueException;

   Integer getKnownTenantEmptyMetadataTTL();

   void setKnownTenantEmptyMetadataTTL(Integer var1) throws InvalidAttributeValueException;

   Integer getUnknownTenantEmptyMetadataTTL();

   void setUnknownTenantEmptyMetadataTTL(Integer var1) throws InvalidAttributeValueException;

   Integer getServerNotAvailableCounterInterval();

   void setServerNotAvailableCounterInterval(Integer var1) throws InvalidAttributeValueException;

   boolean isServerBackoffEnabled();

   void setServerBackoffEnabled(boolean var1) throws InvalidAttributeValueException;

   void clearMetadata(String var1);

   void clearPublicKey(String var1);

   boolean hasMetadataAvailable(String var1);

   boolean hasPublicKeyAvailable(String var1);

   void clearTenantData();

   String getName();

   void setClientSecretEncrypted(byte[] var1);

   byte[] getClientSecretEncrypted();
}
