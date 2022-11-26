package weblogic.security.service;

import com.bea.security.css.CSS;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.security.ProviderMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.SecurityProvider;

@Contract
public interface SecurityServiceManagerDelegate {
   boolean isSecurityServiceInitialized();

   SecurityService getSecurityService(AuthenticatedSubject var1, String var2, SecurityService.ServiceType var3) throws InvalidParameterException, NotYetInitializedException;

   SecurityService getSecurityServiceInternal(String var1, SecurityService.ServiceType var2) throws InvalidParameterException;

   SecurityProvider createSecurityProvider(ProviderMBean var1, Auditor var2);

   boolean doesRealmExist(String var1) throws InvalidParameterException, NotYetInitializedException;

   boolean doesRealmExistInternal(String var1) throws InvalidParameterException, NotYetInitializedException;

   boolean isFullAuthorizationDelegationRequired(String var1, SecurityApplicationInfo var2);

   void initialize(AuthenticatedSubject var1);

   void preInitialize(AuthenticatedSubject var1);

   void postInitialize(AuthenticatedSubject var1);

   void shutdown();

   /** @deprecated */
   @Deprecated
   String getDefaultRealmName();

   String getAdministrativeRealmName();

   String getContextSensitiveRealmName();

   String getRealmName(String var1);

   String getRealmName(String var1, ConfigurationMBean var2);

   void applicationDeleted(ConfigurationMBean var1);

   boolean isApplicationVersioningSupported(String var1);

   void applicationVersionCreated(ConfigurationMBean var1, ConfigurationMBean var2, String var3);

   void initJava2Security();

   boolean isJACCEnabled();

   DeploymentValidator getDeploymentValidator(AuthenticatedSubject var1, String var2, SecurityApplicationInfo var3);

   int getRoleMappingBehavior(String var1, SecurityApplicationInfo var2);

   CSS getCSS(AuthenticatedSubject var1, String var2);

   Object getCSSServiceInternal(String var1, String var2) throws InvalidParameterException;

   Object getCSSServiceProxy(String var1, String var2) throws InvalidParameterException;

   void initializeRealm(AuthenticatedSubject var1, String var2);

   void shutdownRealm(AuthenticatedSubject var1, String var2);

   void restartRealm(AuthenticatedSubject var1, String var2);

   boolean isRealmShutdown(String var1);
}
