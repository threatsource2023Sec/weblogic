package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import javax.management.JMException;
import weblogic.management.security.RealmContainer;
import weblogic.management.security.RealmMBean;

public interface SecurityConfigurationMBean extends ConfigurationMBean, RealmContainer {
   String REALM_BOOTSTRAP_VERSION_UNKNOWN = "unknown";
   String REALM_BOOTSTRAP_VERSION_1 = "1";
   String REALM_BOOTSTRAP_VERSION_CURRENT = "1";

   SecureModeMBean getSecureMode();

   JASPICMBean getJASPIC();

   RealmMBean createRealm(String var1) throws JMException;

   RealmMBean createRealm() throws JMException;

   void destroyRealm(RealmMBean var1);

   RealmMBean[] getRealms();

   RealmMBean lookupRealm(String var1);

   /** @deprecated */
   @Deprecated
   RealmMBean[] findRealms();

   /** @deprecated */
   @Deprecated
   RealmMBean findDefaultRealm();

   /** @deprecated */
   @Deprecated
   RealmMBean findRealm(String var1);

   RealmMBean getDefaultRealm();

   void setDefaultRealm(RealmMBean var1) throws InvalidAttributeValueException;

   byte[] getSalt();

   byte[] getEncryptedSecretKey();

   /** @deprecated */
   @Deprecated
   boolean isAnonymousAdminLookupEnabled();

   void setAnonymousAdminLookupEnabled(boolean var1);

   boolean isClearTextCredentialAccessEnabled();

   void setClearTextCredentialAccessEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isCredentialGenerated();

   void setCredentialGenerated(boolean var1);

   byte[] generateCredential();

   String getCredential();

   void setCredential(String var1) throws InvalidAttributeValueException;

   byte[] getCredentialEncrypted();

   void setCredentialEncrypted(byte[] var1) throws InvalidAttributeValueException;

   String getWebAppFilesCaseInsensitive();

   void setWebAppFilesCaseInsensitive(String var1) throws InvalidAttributeValueException;

   String getRealmBootStrapVersion();

   void setRealmBootStrapVersion(String var1);

   String getConnectionFilter();

   void setConnectionFilter(String var1) throws InvalidAttributeValueException;

   String[] getConnectionFilterRules();

   void setConnectionFilterRules(String[] var1);

   boolean getConnectionLoggerEnabled();

   void setConnectionLoggerEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean getCompatibilityConnectionFiltersEnabled();

   void setCompatibilityConnectionFiltersEnabled(boolean var1) throws InvalidAttributeValueException;

   String getNodeManagerUsername();

   void setNodeManagerUsername(String var1);

   String getNodeManagerPassword();

   void setNodeManagerPassword(String var1);

   byte[] getNodeManagerPasswordEncrypted();

   void setNodeManagerPasswordEncrypted(byte[] var1);

   boolean isPrincipalEqualsCaseInsensitive();

   void setPrincipalEqualsCaseInsensitive(boolean var1);

   boolean isPrincipalEqualsCompareDnAndGuid();

   void setPrincipalEqualsCompareDnAndGuid(boolean var1);

   boolean getDowngradeUntrustedPrincipals();

   void setDowngradeUntrustedPrincipals(boolean var1);

   boolean getEnforceStrictURLPattern();

   void setEnforceStrictURLPattern(boolean var1);

   boolean getEnforceValidBasicAuthCredentials();

   void setEnforceValidBasicAuthCredentials(boolean var1);

   boolean isConsoleFullDelegationEnabled();

   void setConsoleFullDelegationEnabled(boolean var1);

   RealmMBean getDefaultRealmInternal();

   void setDefaultRealmInternal(RealmMBean var1);

   String[] getExcludedDomainNames();

   void setExcludedDomainNames(String[] var1);

   boolean isCrossDomainSecurityEnabled();

   void setCrossDomainSecurityEnabled(boolean var1);

   byte[] getEncryptedAESSecretKey();

   CertRevocMBean getCertRevoc();

   boolean isUseKSSForDemo();

   void setUseKSSForDemo(boolean var1);

   String getAdministrativeIdentityDomain();

   void setAdministrativeIdentityDomain(String var1);

   boolean isIdentityDomainAwareProvidersRequired();

   void setIdentityDomainAwareProvidersRequired(boolean var1);

   boolean isIdentityDomainDefaultEnabled();

   void setIdentityDomainDefaultEnabled(boolean var1);

   int getNonceTimeoutSeconds();

   void setNonceTimeoutSeconds(int var1);

   String getName();

   boolean isRemoteAnonymousJNDIEnabled();

   void setRemoteAnonymousJNDIEnabled(boolean var1);

   int getBootAuthenticationRetryCount();

   void setBootAuthenticationRetryCount(int var1);

   long getBootAuthenticationMaxRetryDelay();

   void setBootAuthenticationMaxRetryDelay(long var1);

   boolean isRemoteAnonymousRMIT3Enabled();

   void setRemoteAnonymousRMIT3Enabled(boolean var1);

   boolean isRemoteAnonymousRMIIIOPEnabled();

   void setRemoteAnonymousRMIIIOPEnabled(boolean var1);
}
