package weblogic.security.service;

import com.bea.common.engine.InvalidParameterException;
import com.bea.common.security.service.PolicyConsumerService;
import com.bea.common.security.service.PrincipalValidationService;
import com.bea.common.security.service.RoleConsumerService;
import com.bea.security.css.CSS;
import com.bea.security.utils.random.FastRandomData;
import com.bea.security.utils.random.SecureRandomData;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.provider.CommandLine;
import weblogic.management.security.ProviderMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.spi.SecurityProvider;
import weblogic.security.utils.KeyStoreInfo;
import weblogic.utils.LocatorUtilities;

public final class SecurityServiceManager extends SecurityManager {
   public static final String STORE_BOOT_IDENTITY = "weblogic.system.StoreBootIdentity";
   public static final String REMOVE_BOOT_IDENTITY = "weblogic.system.RemoveBootIdentity";
   static final String NODE_MANANGER_BOOT = "weblogic.system.NodeManagerBoot";
   public static final String defaultRealmName = "weblogicDEFAULT";
   public static final int COMPATIBILITY_ROLE_MAPPING = 0;
   public static final int APPLICATION_ROLE_MAPPING = 1;
   public static final int EXTERNALLY_DEFINED_ROLE_MAPPING = 2;

   private static SecurityServiceManagerDelegate getDelegate() {
      return SecurityServiceManager.DelegateInstanceMaker.SINGLETON;
   }

   private static SecurityServiceManagerDelegate2 getDelegate2() {
      return SecurityServiceManager.DelegateInstanceMaker.SINGLETON2;
   }

   public SecurityServiceManager(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
   }

   public static boolean isSecurityServiceInitialized() {
      return getDelegate().isSecurityServiceInitialized();
   }

   public static SecurityService getSecurityService(AuthenticatedSubject kernelID, String realmName, SecurityService.ServiceType type) throws InvalidParameterException, NotYetInitializedException {
      return getDelegate().getSecurityService(kernelID, realmName, type);
   }

   public static PrincipalAuthenticator getPrincipalAuthenticator(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return (PrincipalAuthenticator)getSecurityService(kernelID, realmName, SecurityService.ServiceType.AUTHENTICATION);
   }

   public static AuthorizationManager getAuthorizationManager(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return (AuthorizationManager)getSecurityService(kernelID, realmName, SecurityService.ServiceType.AUTHORIZE);
   }

   public static SecurityService getBulkAuthorizationManager(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return getSecurityService(kernelID, realmName, SecurityService.ServiceType.BULKAUTHORIZE);
   }

   public static RoleManager getRoleManager(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return (RoleManager)getSecurityService(kernelID, realmName, SecurityService.ServiceType.ROLE);
   }

   public static SecurityService getBulkRoleManager(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return getSecurityService(kernelID, realmName, SecurityService.ServiceType.BULKROLE);
   }

   public static SecurityService getSecurityTokenServiceManager(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return getSecurityService(kernelID, realmName, SecurityService.ServiceType.STSMANAGER);
   }

   public static boolean doesRealmExist(String realmName) throws InvalidParameterException, NotYetInitializedException {
      return getDelegate().doesRealmExist(realmName);
   }

   static boolean doesRealmExistInternal(String realmName) throws InvalidParameterException, NotYetInitializedException {
      return getDelegate().doesRealmExistInternal(realmName);
   }

   public static AuthenticatedSubject getASFromAU(AuthenticatedUser user) {
      return getDelegate2().getASFromAU(user);
   }

   public static AuthenticatedSubject getSealedSubjectFromWire(AuthenticatedSubject kernelId, AuthenticatedUser user) {
      return getDelegate2().getSealedSubjectFromWire(kernelId, user);
   }

   public static AuthenticatedSubject getASFromAUInServerOrClient(AuthenticatedUser user) {
      return getDelegate2().getASFromAUInServerOrClient(user);
   }

   public static AuthenticatedSubject getASFromWire(AuthenticatedSubject as) {
      return getDelegate2().getASFromWire(as);
   }

   public static AuthenticatedSubject sendASToWire(AuthenticatedSubject as) {
      return getDelegate2().sendASToWire(as);
   }

   public static AuthenticatedUser convertToAuthenticatedUser(AuthenticatedUser user) {
      return getDelegate2().convertToAuthenticatedUser(user);
   }

   public static boolean isFullAuthorizationDelegationRequired(String realmName, SecurityApplicationInfo appInfo) {
      return getDelegate().isFullAuthorizationDelegationRequired(realmName, appInfo);
   }

   public static AuthenticatedSubject getServerIdentity(AuthenticatedSubject kernelID) {
      return getDelegate2().getServerIdentity(kernelID);
   }

   public static boolean isTrustedServerIdentity(AuthenticatedSubject id) {
      return getDelegate2().isTrustedServerIdentity(id);
   }

   public static AuthenticatedSubject seal(AuthenticatedSubject kernelID, AuthenticatedSubject as) {
      return getDelegate2().seal(kernelID, as);
   }

   public void initialize(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      getDelegate2().initializeConfiguration(kernelID);
      getDelegate2().initializeDeploymentCallbacks(kernelID);
      getDelegate().initialize(kernelID);
      getDelegate2().initializeServiceDelegate(kernelID, getDelegate());
   }

   public void preInitialize(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      getDelegate2().initializeConfiguration(kernelID);
      getDelegate2().initializeDeploymentCallbacks(kernelID);
      getDelegate().preInitialize(kernelID);
   }

   public void postInitialize(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      getDelegate().postInitialize(kernelID);
      getDelegate2().initializeServiceDelegate(kernelID, getDelegate());
   }

   public void stop() {
      getDelegate().shutdown();
   }

   /** @deprecated */
   @Deprecated
   public static boolean isAnonymousAdminLookupEnabled() {
      return getDelegate2().isAnonymousAdminLookupEnabled();
   }

   public static boolean getEnforceStrictURLPattern() {
      return getDelegate2().getEnforceStrictURLPattern();
   }

   public static boolean getEnforceValidBasicAuthCredentials() {
      return getDelegate2().getEnforceValidBasicAuthCredentials();
   }

   public static AuthenticatedSubject getCurrentSubjectForWire(AuthenticatedSubject kernelID) {
      return getDelegate2().getCurrentSubjectForWire(kernelID);
   }

   public static boolean isKernelIdentity(AuthenticatedSubject s) {
      return getDelegate2().isKernelIdentity(s);
   }

   public static boolean isServerIdentity(AuthenticatedSubject s) {
      return getDelegate2().isServerIdentity(s);
   }

   public static void checkKernelIdentity(AuthenticatedSubject s) {
      if (!isKernelIdentity(s)) {
         throw new NotAuthorizedRuntimeException(SecurityLogger.getSubjectIsNotTheKernelIdentity(s == null ? "<null>" : s.toString()));
      }
   }

   public static boolean isUserInRole(AuthenticatedSubject user, String role, Map userRoles) {
      return getDelegate2().isUserInRole(user, role, userRoles);
   }

   /** @deprecated */
   @Deprecated
   public static String getDefaultRealmName() {
      return getDelegate().getDefaultRealmName();
   }

   public static String getAdministrativeRealmName() {
      return getDelegate().getAdministrativeRealmName();
   }

   public static String getContextSensitiveRealmName() {
      return getDelegate().getContextSensitiveRealmName();
   }

   public static String getRealmName(String partitionName) {
      return getDelegate().getRealmName(partitionName);
   }

   public static String getRealmName(String partitionName, ConfigurationMBean proposedDomain) {
      return getDelegate().getRealmName(partitionName, proposedDomain);
   }

   public static SecurityProvider createSecurityProvider(ProviderMBean providerMBean, Auditor auditor) {
      return getDelegate().createSecurityProvider(providerMBean, auditor);
   }

   public static void applicationDeleted(ConfigurationMBean mbean) {
      checkKernelPermission();
      getDelegate().applicationDeleted(mbean);
   }

   public static boolean areWebAppFilesCaseInsensitive() {
      return getDelegate2().areWebAppFilesCaseInsensitive();
   }

   public static boolean isApplicationVersioningSupported(String realmName) {
      return getDelegate().isApplicationVersioningSupported(realmName);
   }

   public static void applicationVersionCreated(ConfigurationMBean mbean, ConfigurationMBean source, String realmName) {
      getDelegate().applicationVersionCreated(mbean, source, realmName);
   }

   public static KeyStoreInfo getServerIdentityKeyStore(AuthenticatedSubject kernelID) {
      return getDelegate2().getServerIdentityKeyStore(kernelID);
   }

   public static KeyStoreInfo[] getServerTrustKeyStores(AuthenticatedSubject kernelID) {
      return getDelegate2().getServerTrustKeyStores(kernelID);
   }

   public static void initJava2Security() {
      getDelegate().initJava2Security();
   }

   public static boolean isJACCEnabled() {
      return getDelegate().isJACCEnabled();
   }

   public static DeploymentValidator getDeploymentValidator(AuthenticatedSubject kernelID, String realmName, SecurityApplicationInfo appInfo) {
      return getDelegate().getDeploymentValidator(kernelID, realmName, appInfo);
   }

   public static int getRoleMappingBehavior(String realmName, SecurityApplicationInfo appInfo) {
      return getDelegate().getRoleMappingBehavior(realmName, appInfo);
   }

   public static byte[] getSecureRandomBytes(int howMany) {
      return SecureRandomData.getInstance().getRandomBytes(howMany);
   }

   public static boolean isCaseSensitiveUserNames() {
      return getDelegate2().isCaseSensitiveUserNames();
   }

   public static byte[] getFastRandomBytes(int howMany) {
      return FastRandomData.getInstance().getRandomBytes(howMany);
   }

   public static JMXPolicyConsumer getJMXPolicyConsumer(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      return new JMXPolicyConsumer(getPolicyConsumerServiceInternal(getDefaultRealmName()));
   }

   public static WSPolicyConsumer getWSPolicyConsumer(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      return new WSPolicyConsumer(getPolicyConsumerServiceInternal((String)null));
   }

   public static WSRoleConsumer getWSRoleConsumer(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      return new WSRoleConsumer(getRoleConsumerServiceInternal((String)null));
   }

   public static GenericPolicyConsumer getGenericPolicyConsumer(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      return new GenericPolicyConsumer(getPolicyConsumerServiceInternal((String)null));
   }

   public static GenericRoleConsumer getGenericRoleConsumer(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      return new GenericRoleConsumer(getRoleConsumerServiceInternal((String)null));
   }

   public static PolicyConsumerService getPolicyConsumerService(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      return getPolicyConsumerServiceInternal((String)null);
   }

   private static PolicyConsumerService getPolicyConsumerServiceInternal(String realmName) {
      PolicyConsumerService svc = null;

      try {
         svc = (PolicyConsumerService)getCSSServiceProxy("PolicyConsumerService", realmName);
         return svc;
      } catch (Exception var3) {
         throw new IllegalStateException(var3);
      }
   }

   public static RoleConsumerService getRoleConsumerService(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      return getRoleConsumerServiceInternal((String)null);
   }

   private static RoleConsumerService getRoleConsumerServiceInternal(String realmName) {
      RoleConsumerService svc = null;

      try {
         svc = (RoleConsumerService)getCSSServiceProxy("RoleConsumerService", realmName);
         return svc;
      } catch (Exception var3) {
         throw new IllegalStateException(var3);
      }
   }

   public static PrincipalValidationService getPrincipalValidationService(AuthenticatedSubject kernelID) {
      checkKernelIdentity(kernelID);
      PrincipalValidationService svc = null;

      try {
         svc = (PrincipalValidationService)getCSSServiceProxy("PrincipalValidationService", (String)null);
         return svc;
      } catch (Exception var3) {
         throw new IllegalStateException(var3);
      }
   }

   /** @deprecated */
   @Deprecated
   public static CSS getCSS(AuthenticatedSubject kernelID) {
      return getCSS(kernelID, getDefaultRealmName());
   }

   /** @deprecated */
   @Deprecated
   public static CSS getCSS(AuthenticatedSubject kernelID, String realmName) {
      return getDelegate().getCSS(kernelID, realmName);
   }

   private static Object getCSSServiceProxy(String serviceName, String realmName) throws InvalidParameterException {
      return getDelegate().getCSSServiceProxy(serviceName, realmName);
   }

   public static boolean isEmbeddedLdapNeeded(AuthenticatedSubject kernelID) {
      return getDelegate2().isEmbeddedLdapNeeded(kernelID);
   }

   public static void initializeRealm(AuthenticatedSubject kernelID, String realmName) {
      getDelegate().initializeRealm(kernelID, realmName);
   }

   public void shutdownRealm(AuthenticatedSubject kernelID, String realmName) {
      getDelegate().shutdownRealm(kernelID, realmName);
   }

   public void restartRealm(AuthenticatedSubject kernelID, String realmName) {
      getDelegate().restartRealm(kernelID, realmName);
   }

   public static boolean isRealmShutdown(String realmName) {
      return getDelegate().isRealmShutdown(realmName);
   }

   private static class DelegateInstanceMaker {
      private static final SecurityServiceManagerDelegate SINGLETON;
      private static final SecurityServiceManagerDelegate2 SINGLETON2;

      static {
         Object customDelegate = PluginUtils.createPlugin(SecurityServiceManagerDelegate.class, CommandLine.getCommandLine().getSubjectManagerClassPropertyName());

         try {
            if (customDelegate != null) {
               SINGLETON = (SecurityServiceManagerDelegate)customDelegate;
            } else {
               SINGLETON = (SecurityServiceManagerDelegate)AccessController.doPrivileged(new PrivilegedAction() {
                  public SecurityServiceManagerDelegate run() {
                     return (SecurityServiceManagerDelegate)LocatorUtilities.getService(SecurityServiceManagerDelegate.class);
                  }
               });
            }

            SINGLETON2 = (SecurityServiceManagerDelegate2)AccessController.doPrivileged(new PrivilegedAction() {
               public SecurityServiceManagerDelegate2 run() {
                  return (SecurityServiceManagerDelegate2)LocatorUtilities.getService(SecurityServiceManagerDelegate2.class);
               }
            });
         } catch (Throwable var2) {
            throw new IllegalStateException("Unable to instantiate SecurityServiceManagerDelegate2Impl", var2);
         }
      }
   }
}
