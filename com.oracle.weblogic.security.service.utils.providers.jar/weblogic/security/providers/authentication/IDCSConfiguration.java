package weblogic.security.providers.authentication;

import java.net.URI;

public interface IDCSConfiguration {
   String[] EMPTY_STRING_ARRAY = new String[0];
   String X_USER_IDENTITY_SERVICE_GUID = "X-USER-IDENTITY-SERVICE-GUID";
   String X_USER_IDENTITY_DOMAIN_NAME = "X-USER-IDENTITY-DOMAIN-NAME";
   String X_RESOURCE_IDENTITY_SERVICE_GUID = "X-RESOURCE-IDENTITY-SERVICE-GUID";
   String X_RESOURCE_IDENTITY_DOMAIN_NAME = "X-RESOURCE-IDENTITY-DOMAIN-NAME";
   String X_RESOURCE_SERVICE_INSTANCE_IDENTITY_APPNAME = "X-RESOURCE-SERVICE-INSTANCE-IDENTITY-APPNAME";
   String DEFAULT_KNOWN_TENANT_PREFIX = "idcs-";
   String[] DEFAULT_TENANT_HEADER_NAMES = new String[]{"X-USER-IDENTITY-SERVICE-GUID", "X-USER-IDENTITY-DOMAIN-NAME", "X-RESOURCE-IDENTITY-SERVICE-GUID", "X-RESOURCE-IDENTITY-DOMAIN-NAME"};
   String[] DEFAULT_KNOWN_TENANT_PREFIX_LIST = new String[]{"idcs-"};
   String DEFAULT_APPNAME_FILTER_HEADER_NAME = "X-RESOURCE-SERVICE-INSTANCE-IDENTITY-APPNAME";
   String IDCS_ASSERTION_TYPE = "idcs_user_assertion";
   String IDCS_AUTHORIZATION_TYPE = "Authorization";
   String REMOTE_USER_TYPE = "REMOTE_USER";
   String IDCS_REMOTE_USER_TYPE = "IDCS_REMOTE_USER";

   String getIDCSConfigURI(String var1);

   String getAsserterEndpointURI(String var1);

   String getUserPasswordURI(String var1);

   String getUsersEndpointURI(String var1);

   boolean isClientAppRoleAsserterSupported(String var1);

   int getConnectTimeout();

   int getResponseReadTimeout();

   IDCSKeyManager getKeyManager(String var1);

   String getIssuer(String var1);

   String getJWKSetURI();

   long getTenantDataFlushInterval();

   long getKnownTenantEmptyMetadataTTL();

   long getUnknownTenantEmptyMetadataTTL();

   boolean isTenantDataReloadEnabled();

   long getTenantDataReloadInterval();

   IDCSTokenAssert.ValidationLevel getValidationLevel();

   int getClockSkew();

   boolean isPreferX509();

   boolean isTokenSecureTransportRequired();

   boolean isTokenVirtualUserAllowed();

   String getUserNameTokenClaim();

   String getUserIDTokenClaim();

   String getGroupsTokenClaim();

   String getTenantTokenClaim();

   String getAppRolesTokenClaim();

   String getClientNameTokenClaim();

   String getClientIDTokenClaim();

   String getClientTenantTokenClaim();

   String getResourceTenantTokenClaim();

   String getUserAuthenticationAssertionAttribute();

   String getUserNameResourceAttribute();

   String getUserIDResourceAttribute();

   String getClientIDResourceAttribute();

   String getClientTenant();

   String getClientId();

   char[] getClientSecret();

   long getAccessTokenTimeoutWindow();

   URI getTokenEndpointURI(String var1);

   OAuthAccessTokenManager getTokenManager();

   String[] getTenantHeaderNames();

   String getTenant();

   String[] getTenantNames();

   String[] getKnownTenantPrefixList();

   boolean isCacheEnabled();

   int getCacheSize();

   int getCacheTTL();

   boolean isTokenCacheEnabled();

   Object getTokenCache();

   String getAppNameFilterHeaderName();

   boolean isOnlyUserTokenClaimsEnabled();

   boolean isAudienceEnabled();

   boolean isClientAsUserPrincipalEnabled();

   IDCSFilterService getFilterService();

   boolean isSyncFilterEnabled();

   boolean isSyncFilterOnlyClientCertRequests();

   boolean isSyncFilterMatchCase();

   boolean isSyncFilterPreferHeader();

   String[] getSyncFilterUserHeaderNames();

   String[] getActiveTypes();

   long getThreadLockTimeout();

   IDCSProviderManager getProviderManager();

   IDCSTenantEnvironment getTenantEnvironment();

   IDCSAtnDelegate getDelegate();

   long getServerNotAvailableCounterInterval();

   boolean isServerBackoffEnabled();

   IDCSBackoffRetryCounter getBackoffRetryCounter();
}
