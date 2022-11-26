package weblogic.security.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.PolicyConsumerService;
import com.bea.common.security.service.PrincipalValidationService;
import com.bea.common.security.service.RoleConsumerService;
import com.bea.common.security.service.SAML2Service;
import com.bea.common.security.utils.ThreadClassLoaderContextInvocationHandler;
import com.bea.security.css.CSS;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyConfigurationFactory;
import javax.security.jacc.PolicyContextException;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RealmRuntimeMBean;
import weblogic.management.runtime.ServerSecurityRuntimeMBean;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authorization.AdjudicatorMBean;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.RoleMapperMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.management.security.pk.CertPathProviderMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.security.RealmRuntime;
import weblogic.security.SecurityInitializationException;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityRuntime;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.UserLockoutManagerRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.AuditorServiceImpl;
import weblogic.security.internal.BootProperties;
import weblogic.security.internal.ForceDDOnly;
import weblogic.security.internal.SecurityServicesImpl;
import weblogic.security.jacc.RoleMapperFactory;
import weblogic.security.jacc.simpleprovider.PolicyConfigurationFactoryImpl;
import weblogic.security.jacc.simpleprovider.RoleMapperFactoryImpl;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.service.internal.ApplicationVersioningService;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.SecurityProvider;
import weblogic.security.spi.ApplicationInfo.ComponentType;
import weblogic.security.utils.CertPathTrustManagerUtils;
import weblogic.security.utils.PartitionUtils;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.annotation.Secure;

@Service
@Secure
public class CommonSecurityServiceManagerDelegateImpl implements SecurityServiceManagerDelegate {
   private static boolean multipleRealmBootInitialize = false;
   private static ConcurrentHashMap realmsHashMap = null;
   private static ConcurrentHashMap shutdownRealmsHashMap = null;
   private static ConcurrentHashMap realmsLockMap = null;
   private static HashMap serviceProxiesHashMap = null;
   private static HashMap cssServiceProxiesHashMap = null;
   private static boolean initialized = false;
   private static String defaultConfiguredRealmName = null;
   private static final String defaultRealmName = "weblogicDEFAULT";
   private static final String JACC_POLICY_PROVIDER = "javax.security.jacc.policy.provider";
   private static final String JACC_POLICYCONFIGURATIONFACTORY_PROVIDER = "javax.security.jacc.PolicyConfigurationFactory.provider";
   private static final String ROLEMAPPERFACTORY_PROVIDER = "weblogic.security.jacc.RoleMapperFactory.provider";
   public static boolean JACC_POLICY_LOADED = false;
   private static LoggerWrapper jaccDebugLogger = LoggerWrapper.getInstance("SecurityJACC");
   private static LoggerWrapper debugLogger = LoggerWrapper.getInstance("SecurityRealm");
   public static final String OPSS_POLICY_PROVIDER = "oracle.security.jps.internal.policystore.JavaPolicyProvider";
   private static String JPS_STARTUP_CLASS = "oracle.security.jps.JpsStartup";
   public static boolean OPSS_POLICY_LOADED = false;
   private static String OPSS_LOAD_LOG = "DebugOPSSPolicyLoading";
   private static boolean opssLoadDebug;
   private static final String JPS_CFG_FILE_PROP = "oracle.security.jps.config";
   private static final String ORA_DOMAIN_CFG_DIR = "oracle.domain.config.dir";
   private static final AuthenticatedSubject kernelId;
   private static final String JAVA_SECURITY_MANAGER = "java.security.manager";
   private static final String JAVA_SECURITY_MANAGER_CLASS = "java.lang.SecurityManager";
   private static final String JAVA_SECURITY_POLICY = "java.security.policy";
   private static final String DEFAULT_JACC_SECURITY_POLICY_CONFIGURATION_FACTORY_PROVIDER_CLASSNAME = "weblogic.security.jacc.simpleprovider.PolicyConfigurationFactoryImpl";
   private static final String DEFAULT_JACC_SECURITY_POLICY_PROVIDER_CLASSNAME = "weblogic.security.jacc.simpleprovider.SimpleJACCPolicy";
   private static final String DEFAULT_ROLEMAPPERFACTORY_PROVIDER_CLASSNAME = "weblogic.security.jacc.simpleprovider.RoleMapperFactoryImpl";
   private static final Object[] ORDERED_MANAGER_KEY_LIST;
   private boolean consoleFullDelegation = false;
   private static final String FULL_DELEGATE_AUTHORIZATION = "weblogic.security.fullyDelegateAuthorization";
   private static final boolean FULL_DELEGATE_PROPERTY_ON_CMDLINE;
   private static final boolean FULL_DELEGATE_OVERRIDE_VALUE;

   public boolean isSecurityServiceInitialized() {
      return initialized;
   }

   private static boolean isRequiredSecurityService(SecurityService.ServiceType type) {
      return type == ServiceType.AUTHORIZE || type == ServiceType.AUTHENTICATION || type == ServiceType.ROLE;
   }

   public SecurityService getSecurityService(AuthenticatedSubject kernelID, String realmName, SecurityService.ServiceType type) throws InvalidParameterException, NotYetInitializedException {
      if (!initialized) {
         throw new NotYetInitializedException(SecurityLogger.getSecServiceMgrNotYetInit());
      } else {
         SecurityManager.checkKernelIdentity(kernelID);
         SecurityService service = this.getSecurityServiceInternal(realmName, type);
         if (service == null && isRequiredSecurityService(type)) {
            throw new NotYetInitializedException(SecurityLogger.getSecServiceNotYetInit("" + type));
         } else {
            if (service != null) {
               SecurityService proxy = null;
               if (this.getContextSensitiveRealmName().equals(realmName)) {
                  proxy = (SecurityService)serviceProxiesHashMap.get(type);
                  if (proxy == null) {
                     proxy = this.generateWiredServiceProxy(defaultConfiguredRealmName, type, service);
                  }
               } else {
                  proxy = this.generateWiredServiceProxy(realmName, type, service);
               }

               if (proxy == null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("getSecurityService: Proxy lookup failed for " + realmName + " to " + service.toString());
                  }

                  throw new IllegalStateException("Unable to find or generate service proxy");
               }

               service = proxy;
            }

            return service;
         }
      }
   }

   public SecurityService getSecurityServiceInternal(String realmName, SecurityService.ServiceType type) throws InvalidParameterException {
      if (null == realmName) {
         throw new InvalidParameterException(SecurityLogger.getMustSpecifyRealm());
      } else if (null == type) {
         throw new InvalidParameterException(SecurityLogger.getMustSpecifySecServiceType());
      } else {
         String realmToUse = defaultConfiguredRealmName;
         if (!realmName.equals(this.getContextSensitiveRealmName())) {
            realmToUse = realmName;
         }

         RealmServices realmServices = (RealmServices)realmsHashMap.get(realmToUse);
         if (realmServices == null) {
            this.startRealm(realmToUse);
            realmServices = (RealmServices)realmsHashMap.get(realmToUse);
         }

         if (realmServices == null) {
            throw new InvalidParameterException(SecurityLogger.getRealmDoesNotExist(realmToUse));
         } else {
            SecurityService securityService = (SecurityService)realmServices.getServices().get(type);
            return securityService;
         }
      }
   }

   public SecurityProvider createSecurityProvider(ProviderMBean providerMBean, Auditor auditor) {
      String providerClassName = providerMBean.getProviderClassName();

      try {
         Class cls = Class.forName(providerClassName, true, providerMBean.getClass().getClassLoader());

         try {
            SecurityProvider provider = (SecurityProvider)cls.newInstance();
            RealmMBean realmBean = providerMBean.getRealm();
            String realm = "";
            realm = realmBean.getName();
            RealmServices realmServices = (RealmServices)realmsHashMap.get(realm);
            SecurityServicesImpl securityServices = (SecurityServicesImpl)realmServices.getServices().get(ServiceType.SECURITY_SERVICES);
            provider.initialize(providerMBean, securityServices);
            return provider;
         } catch (InstantiationException var10) {
            throw new ProviderException(SecurityLogger.getSecProvErrorCreationExc(providerClassName), var10);
         } catch (IllegalAccessException var11) {
            throw new ProviderException(SecurityLogger.getSecProvErrorCreationExc(providerClassName), var11);
         }
      } catch (ClassNotFoundException var12) {
         throw new ProviderException(SecurityLogger.getSecProvErrorNotFound(providerClassName), var12);
      }
   }

   public boolean doesRealmExist(String realmName) throws InvalidParameterException, NotYetInitializedException {
      if (!initialized) {
         throw new NotYetInitializedException(SecurityLogger.getSecServiceMgrNotYetInit());
      } else {
         return this.doesRealmExistInternal(realmName);
      }
   }

   public boolean doesRealmExistInternal(String realmName) throws InvalidParameterException, NotYetInitializedException {
      if (null == realmName) {
         throw new InvalidParameterException(SecurityLogger.getMustSpecifyRealm());
      } else {
         return realmsHashMap.containsKey(realmName);
      }
   }

   public boolean isFullAuthorizationDelegationRequired(String realmName, SecurityApplicationInfo appInfo) {
      if (ForceDDOnly.isForceDDOnly()) {
         return false;
      } else {
         String realmToUse;
         if (this.consoleFullDelegation && appInfo != null) {
            realmToUse = appInfo.getApplicationIdentifier();
            if (realmToUse != null && realmToUse.startsWith("consoleapp") && "DDOnly".equals(appInfo.getSecurityDDModel())) {
               return true;
            }
         }

         if (appInfo != null) {
            realmToUse = appInfo.getSecurityDDModel();
            if (!"Advanced".equals(realmToUse)) {
               boolean fullDelegation = false;
               if ("CustomRolesAndPolicies".equals(realmToUse)) {
                  fullDelegation = true;
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("isFullAuthorizationDelegationRequired: returning " + fullDelegation);
               }

               return fullDelegation;
            }
         }

         realmToUse = this.getActualRealmName(realmName);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("isFullAuthorizationDelegationRequired: returning realm setting from: " + realmToUse);
         }

         RealmMBean realmMbean = null;

         try {
            realmMbean = getRealmFromName(realmToUse);
         } catch (Exception var6) {
            throw new IllegalStateException(SecurityLogger.getFailureWithRealm(realmToUse), var6);
         }

         return FULL_DELEGATE_PROPERTY_ON_CMDLINE && realmMbean.isDefaultRealm() ? FULL_DELEGATE_OVERRIDE_VALUE : realmMbean.isFullyDelegateAuthorization();
      }
   }

   private RealmMBean getDefaultRealm() {
      RealmMBean defaultRealm = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().getDefaultRealm();
      if (defaultRealm != null) {
         return defaultRealm;
      } else {
         throw new SecurityServiceRuntimeException(SecurityLogger.getInvSecConfigNoDefaultRealm());
      }
   }

   private RealmServices initializeRealm(RealmMBean realmMbean, boolean startRealm) throws SecurityServiceException {
      String realmName = realmMbean.getName();

      try {
         realmMbean.validate();
      } catch (ErrorCollectionException var6) {
         throw new SecurityServiceRuntimeException(SecurityLogger.getSecRealmInvConfig(realmName), var6);
      }

      if (!startRealm) {
         return null;
      } else {
         ServerSecurityRuntimeMBean securityRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getServerSecurityRuntime();

         try {
            RealmRuntimeMBean realmRuntime = new RealmRuntime(realmName, securityRuntime);
            securityRuntime.addRealmRuntime(realmRuntime);
         } catch (ManagementException var7) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Can not initialize RealmRuntime for realm: " + realmName, var7);
            }

            throw new SecurityServiceRuntimeException(var7);
         }

         CSS css = this.InitializeServiceEngine(realmMbean);
         return new RealmServices(realmName, css);
      }
   }

   private void postInitializeRealm(RealmMBean realmMbean, RealmServices realmServices) throws SecurityServiceException {
      HashMap servicesHashMap = realmServices.getServices();
      AuditorServiceImpl auditorService = null;

      try {
         AuditService auditService = (AuditService)realmServices.getCSS().getService("AuditService");
         if (auditService.isAuditEnabled()) {
            Auditor auditor = new AuditorImpl(auditService);
            servicesHashMap.put(ServiceType.AUDIT, auditor);
            auditorService = new AuditorServiceImpl(auditor);
            servicesHashMap.put(ServiceType.AUDITOR_SERVICE, auditorService);
         }

         ApplicationVersioningService var23 = (ApplicationVersioningService)realmServices.getCSS().getService("ApplicationVersioningService");
      } catch (ServiceNotFoundException var19) {
      } catch (ServiceInitializationException var20) {
         throw new SecurityServiceException(var20);
      }

      SecurityServicesImpl securityServices = new SecurityServicesImpl(auditorService, realmServices.getRealmName());
      servicesHashMap.put(ServiceType.SECURITY_SERVICES, securityServices);
      CredentialManager credentialsManager = this.doCredentials(realmServices, realmMbean);
      if (credentialsManager != null) {
         servicesHashMap.put(ServiceType.CREDENTIALMANAGER, credentialsManager);
      }

      BulkRoleManager bulkRoleManager = this.doBulkRole(realmServices, realmMbean);
      if (bulkRoleManager != null) {
         servicesHashMap.put(ServiceType.BULKROLE, bulkRoleManager);
      }

      BulkAuthorizationManager bulkAtzManager = this.doBulkATZ(realmServices, realmMbean);
      if (bulkAtzManager != null) {
         servicesHashMap.put(ServiceType.BULKAUTHORIZE, bulkAtzManager);
      }

      RoleManager roleManager = this.doRole(realmServices, realmMbean);
      if (roleManager != null) {
         servicesHashMap.put(ServiceType.ROLE, roleManager);
      }

      AuthorizationManager atzManager = this.doATZ(realmServices, realmMbean);
      if (atzManager != null) {
         servicesHashMap.put(ServiceType.AUTHORIZE, atzManager);
      }

      PrincipalAuthenticator pa = this.doATN(realmServices, realmMbean);
      if (pa != null) {
         servicesHashMap.put(ServiceType.AUTHENTICATION, pa);
      }

      CertPathManager cpm = this.doCertPath(realmServices, realmMbean);
      if (cpm != null) {
         servicesHashMap.put(ServiceType.CERTPATH, cpm);
      }

      SecurityTokenServiceManager stsManager = this.doSTS(realmServices, realmMbean);
      if (stsManager != null) {
         servicesHashMap.put(ServiceType.STSMANAGER, stsManager);
      }

      SAML2Service realmRuntime;
      try {
         realmRuntime = (SAML2Service)realmServices.getCSS().getService("SingleSignOnService");
         if (realmRuntime != null) {
            SAML2ServiceWrapper saml2Wrapper = new SAML2ServiceWrapper((SAML2Service)Proxy.newProxyInstance(CSSWLSDelegateImpl.getSAML2ClassLoader(), realmRuntime.getClass().getInterfaces(), new ThreadClassLoaderContextInvocationHandler(CSSWLSDelegateImpl.getSAML2ClassLoader(), realmRuntime)));
            servicesHashMap.put(ServiceType.SAML2_SSO, saml2Wrapper);
         }
      } catch (ServiceNotFoundException var17) {
      } catch (ServiceInitializationException var18) {
      }

      if (servicesHashMap.size() == 0) {
         throw new SecurityServiceException(SecurityLogger.getFailedToInitRealm(realmServices.getRealmName()));
      } else {
         realmRuntime = null;
         RealmRuntimeMBean realmRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getServerSecurityRuntime().lookupRealmRuntime(realmMbean.getName());
         if (realmRuntime != null && pa != null) {
            try {
               UserLockoutManager ulm = pa.getUserLockoutManager();
               if (ulm.isLockoutEnabled()) {
                  UserLockoutManagerRuntime userLockoutManagerRuntime = new UserLockoutManagerRuntime(ulm, realmRuntime);
                  realmRuntime.setUserLockoutManagerRuntime(userLockoutManagerRuntime);
               }
            } catch (ManagementException var21) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Cannot set userLockoutManagerRuntime for realm: " + realmMbean.getName());
               }
            }
         }

      }
   }

   private CredentialManager doCredentials(RealmServices realmServices, RealmMBean mbean) {
      CredentialManager credentialManager = null;
      CredentialMapperMBean[] credentialMBeans = mbean.getCredentialMappers();
      if (null != credentialMBeans && 0 != credentialMBeans.length) {
         credentialManager = new CredentialManagerImpl(realmServices, credentialMBeans);
      }

      return credentialManager;
   }

   private RoleManager doRole(RealmServices realmServices, RealmMBean mbean) {
      RoleMapperMBean[] roleMBeans = mbean.getRoleMappers();
      if (null != roleMBeans && 0 != roleMBeans.length) {
         return new RoleManagerImpl(realmServices, roleMBeans);
      } else {
         throw new SecurityServiceRuntimeException(SecurityLogger.getNeedToConfigureOneRoleMapper());
      }
   }

   private BulkRoleManager doBulkRole(RealmServices realmServices, RealmMBean mbean) {
      RoleMapperMBean[] roleMBeans = mbean.getRoleMappers();
      if (null != roleMBeans && 0 != roleMBeans.length) {
         BulkRoleManagerImpl roleManager = new BulkRoleManagerImpl();
         roleManager.initialize(realmServices, roleMBeans);
         return roleManager;
      } else {
         throw new SecurityServiceRuntimeException(SecurityLogger.getNeedToConfigureOneRoleMapper());
      }
   }

   private AuthorizationManager doATZ(RealmServices realmServices, RealmMBean mbean) {
      AuthorizationManager authorizationManager = null;
      AuthorizerMBean[] atzMBeans = mbean.getAuthorizers();
      if (null != atzMBeans && 0 != atzMBeans.length) {
         AdjudicatorMBean adjMBean = mbean.getAdjudicator();
         ProviderMBean[] authorizationManagerMBeans;
         if (adjMBean != null) {
            authorizationManagerMBeans = new ProviderMBean[atzMBeans.length + 1];
            System.arraycopy(atzMBeans, 0, authorizationManagerMBeans, 0, atzMBeans.length);
            authorizationManagerMBeans[atzMBeans.length] = adjMBean;
         } else {
            authorizationManagerMBeans = new ProviderMBean[atzMBeans.length];
            System.arraycopy(atzMBeans, 0, authorizationManagerMBeans, 0, atzMBeans.length);
         }

         authorizationManager = new AuthorizationManagerImpl(realmServices, authorizationManagerMBeans);
         return authorizationManager;
      } else {
         throw new InvalidParameterException(SecurityLogger.getNeedToConfigureOneAtzMBean());
      }
   }

   private BulkAuthorizationManager doBulkATZ(RealmServices realmServices, RealmMBean mbean) {
      AuthorizerMBean[] atzMBeans = mbean.getAuthorizers();
      if (null != atzMBeans && 0 != atzMBeans.length) {
         AdjudicatorMBean adjMBean = mbean.getAdjudicator();
         ProviderMBean[] authorizationManagerMBeans;
         if (adjMBean != null) {
            authorizationManagerMBeans = new ProviderMBean[atzMBeans.length + 1];
            System.arraycopy(atzMBeans, 0, authorizationManagerMBeans, 0, atzMBeans.length);
            authorizationManagerMBeans[atzMBeans.length] = adjMBean;
         } else {
            authorizationManagerMBeans = new ProviderMBean[atzMBeans.length];
            System.arraycopy(atzMBeans, 0, authorizationManagerMBeans, 0, atzMBeans.length);
         }

         BulkAuthorizationManagerImpl authorizationManager = new BulkAuthorizationManagerImpl();
         authorizationManager.initialize(realmServices, authorizationManagerMBeans);
         return authorizationManager;
      } else {
         throw new InvalidParameterException(SecurityLogger.getNeedToConfigureOneAtzMBean());
      }
   }

   private PrincipalAuthenticator doATN(RealmServices realmServices, RealmMBean mbean) {
      AuthenticationProviderMBean[] atnMBeans = mbean.getAuthenticationProviders();
      if ((null == atnMBeans || 0 == atnMBeans.length) && debugLogger.isDebugEnabled()) {
         debugLogger.debug("Warning, PrincipalAuthenticator for realm " + realmServices.getRealmName() + " initializing without configuration");
      }

      return new PrincipalAuthenticatorImpl(realmServices, atnMBeans);
   }

   private CertPathManager doCertPath(RealmServices realmServices, RealmMBean mbean) {
      CertPathProviderMBean[] cpMBeans = mbean.getCertPathProviders();
      CertPathProviderMBean[] mergedCPMBeans = new CertPathProviderMBean[cpMBeans.length + 1];
      mergedCPMBeans[0] = mbean.getCertPathBuilder();

      for(int i = 0; i < cpMBeans.length; ++i) {
         mergedCPMBeans[i + 1] = cpMBeans[i];
      }

      return new CertPathManagerImpl(realmServices, mergedCPMBeans);
   }

   private SecurityTokenServiceManager doSTS(RealmServices realmServices, RealmMBean mBean) {
      SecurityTokenServiceManager stsManager = null;
      if (null != mBean) {
         stsManager = new SecurityTokenServiceManagerImpl(realmServices, mBean);
      }

      return stsManager;
   }

   private RealmServices loadRealm(String realmName, boolean startRealm) throws SecurityServiceException {
      if (realmsHashMap.containsKey(realmName)) {
         return null;
      } else {
         RealmMBean realm = getRealmFromName(realmName);
         return this.initializeRealm(realm, startRealm);
      }
   }

   private void postLoadRealm(String realmName) throws SecurityServiceException {
      if (realmsHashMap.containsKey(realmName)) {
         RealmMBean realm = getRealmFromName(realmName);
         RealmServices realmServices = (RealmServices)realmsHashMap.get(realmName);
         this.postInitializeRealm(realm, realmServices);
      }
   }

   private static RealmMBean getRealmFromName(String realmName) throws SecurityServiceException {
      RealmMBean realm = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().lookupRealm(realmName);
      if (realm == null) {
         throw new SecurityServiceException(SecurityLogger.getInvalidRealmName(realmName));
      } else {
         return realm;
      }
   }

   private void initializeRealms() {
      multipleRealmBootInitialize = Boolean.getBoolean("weblogic.security.multirealm.boot.initialize");
      realmsHashMap = new ConcurrentHashMap();
      shutdownRealmsHashMap = new ConcurrentHashMap();
      realmsLockMap = new ConcurrentHashMap();
      RealmMBean realmMbean = this.getDefaultRealm();
      if (realmMbean != null) {
         defaultConfiguredRealmName = realmMbean.getName();

         try {
            SecurityLogger.logPreInitializingUsingRealm(defaultConfiguredRealmName);
            RealmServices realmServices = this.loadRealm(defaultConfiguredRealmName, true);
            if (realmServices != null) {
               realmServices.setDefault();
               realmsHashMap.put(realmServices.getRealmName(), realmServices);
               realmsLockMap.put(realmMbean, new Object());
            }
         } catch (SecurityServiceException var7) {
            SecurityLogger.logLoadRealmFailed(defaultConfiguredRealmName, var7);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failed to preInitialize default security realm: " + defaultConfiguredRealmName);
            }

            throw new SecurityServiceRuntimeException(var7);
         }

         RealmMBean[] realms = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().getRealms();

         for(int i = 0; realms != null && i < realms.length; ++i) {
            String realmName = realms[i].getName();
            if (!defaultConfiguredRealmName.equals(realmName)) {
               try {
                  SecurityLogger.logPreInitializingUsingRealm(realmName);
                  RealmServices realmServices = this.loadRealm(realmName, multipleRealmBootInitialize);
                  if (realmServices != null) {
                     realmsHashMap.put(realmServices.getRealmName(), realmServices);
                     realmsLockMap.put(realms[i], new Object());
                  }
               } catch (SecurityServiceException var6) {
                  SecurityLogger.logLoadRealmFailed(realmName, var6);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Failed to preInitialize security realm: " + realmName);
                  }

                  throw new SecurityServiceRuntimeException(var6);
               }
            }
         }

      } else {
         throw new SecurityServiceRuntimeException(SecurityLogger.getNoRealmMBeanUnableToInit());
      }
   }

   private void postInitializeRealms() {
      if (defaultConfiguredRealmName != null) {
         try {
            SecurityLogger.logPostInitializingUsingRealm(defaultConfiguredRealmName);
            this.postLoadRealm(defaultConfiguredRealmName);
            serviceProxiesHashMap = this.generateServiceProxies();
            cssServiceProxiesHashMap = this.generateCSSServiceProxies();
            initialized = true;
         } catch (SecurityServiceException var6) {
            SecurityLogger.logLoadRealmFailed(defaultConfiguredRealmName, var6);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failed to postInitialize default security realm: " + defaultConfiguredRealmName);
            }
         }

         if (multipleRealmBootInitialize) {
            RealmMBean[] realms = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().getRealms();

            for(int i = 0; realms != null && i < realms.length; ++i) {
               String realmName = realms[i].getName();
               if (!defaultConfiguredRealmName.equals(realmName)) {
                  try {
                     SecurityLogger.logPostInitializingUsingRealm(realmName);
                     this.postLoadRealm(realmName);
                  } catch (SecurityServiceException var5) {
                     SecurityLogger.logLoadRealmFailed(realmName, var5);
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Failed to postInitialize security realm: " + realmName);
                     }

                     throw new SecurityServiceRuntimeException(var5);
                  }
               }
            }

         }
      } else {
         throw new SecurityServiceRuntimeException(SecurityLogger.getNoRealmMBeanUnableToInit());
      }
   }

   private void doBootAuthorization() {
      boolean storeBootIdentity = Boolean.getBoolean("weblogic.system.StoreBootIdentity");
      boolean removeBootIdentity = Boolean.getBoolean("weblogic.system.RemoveBootIdentity");
      boolean nodeManagerBoot = Boolean.getBoolean("weblogic.system.NodeManagerBoot");
      if (nodeManagerBoot) {
         removeBootIdentity = true;
      }

      PrincipalAuthenticator pa = (PrincipalAuthenticator)this.getSecurityServiceInternal(this.getContextSensitiveRealmName(), ServiceType.AUTHENTICATION);
      AuthorizationManager am = (AuthorizationManager)this.getSecurityServiceInternal(this.getAdministrativeRealmName(), ServiceType.AUTHORIZE);
      if (pa != null && am != null) {
         AuthenticatedSubject bootUser = null;
         String timeStamp1 = ManagementService.getPropertyService(kernelId).getTimestamp1();
         String timeStamp2 = ManagementService.getPropertyService(kernelId).getTimestamp2();
         String idd = ManagementService.getPropertyService(kernelId).getIdentityDomain();

         Loggable loggable;
         try {
            long retryCount = 0L;
            long retryNumber = 0L;
            long retryDelay = 500L;
            long retryMaxDelay = 0L;
            boolean authenticateSucceeded = false;
            SecurityConfigurationMBean secCfg = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration();
            if (secCfg != null) {
               retryCount = (long)secCfg.getBootAuthenticationRetryCount();
               retryMaxDelay = secCfg.getBootAuthenticationMaxRetryDelay();
            }

            do {
               try {
                  if (idd == null) {
                     bootUser = pa.authenticate(new SimpleCallbackHandler(timeStamp1, timeStamp2.toCharArray()), (ContextHandler)null);
                  } else {
                     bootUser = pa.authenticate(new SimpleCallbackHandler(timeStamp1, idd, timeStamp2.toCharArray()), (ContextHandler)null);
                  }

                  authenticateSucceeded = true;
               } catch (LoginServerNotAvailableException var23) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Login server not available ", var23);
                  }

                  ++retryNumber;
                  if (retryNumber > retryCount) {
                     throw var23;
                  }

                  SecurityLogger.logRetryBootAuthentication("" + retryNumber, "" + retryCount);

                  try {
                     Thread.sleep(retryDelay > retryMaxDelay ? retryMaxDelay : retryDelay);
                  } catch (Exception var22) {
                  }

                  retryDelay *= 2L;
                  if (retryDelay > retryMaxDelay) {
                     retryDelay = retryMaxDelay;
                  }
               }
            } while(!authenticateSucceeded);
         } catch (LoginException var24) {
            String startmode = System.getProperty("weblogic.management.startmode");
            if (startmode != null && startmode.compareTo("WinSvc") == 0) {
               loggable = SecurityLogger.logErrorBadPasswordRegisteredLoggable(timeStamp1);
               loggable.log();
               throw new SecurityInitializationException(loggable.getMessageText(), var24);
            }

            BootProperties bootProps = BootProperties.getBootProperties();
            Loggable loggable;
            if (bootProps == null && !nodeManagerBoot) {
               loggable = SecurityLogger.logAuthDeniedForUserLoggable(timeStamp1);
               loggable.log();
               throw new SecurityInitializationException(loggable.getMessageText(), var24);
            }

            BootProperties.unload(removeBootIdentity);
            loggable = SecurityLogger.logBootIdentityNotValidLoggable();
            loggable.log();
            throw new SecurityInitializationException(loggable.getMessageText(), var24);
         }

         if (!storeBootIdentity) {
            BootProperties.save();
         } else if (!removeBootIdentity) {
            BootProperties.output(ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration(), System.getProperty("weblogic.system.BootIdentityFile"), ManagementService.getPropertyService(kernelId).getTimestamp1(), ManagementService.getPropertyService(kernelId).getTimestamp2(), ManagementService.getPropertyService(kernelId).getIdentityDomain(), System.getProperty("weblogic.security.TrustKeyStore"), System.getProperty("weblogic.security.CustomTrustKeyStoreFileName"), System.getProperty("weblogic.security.CustomTrustKeyStoreType"), System.getProperty("weblogic.security.CustomTrustKeyStorePassPhrase"), System.getProperty("weblogic.security.JavaStandardTrustKeyStorePassPhrase"), System.getProperty("CustomIdentityKeyStoreFileName"), System.getProperty("CustomIdentityKeyStoreType"), System.getProperty("CustomIdentityKeyStorePassPhrase"), System.getProperty("CustomIdentityKeyStoreAlias"), System.getProperty("CustomIdentityPrivateKeyPassPhrase"));
         }

         BootProperties.unload(removeBootIdentity);
         if (ManagementService.getRuntimeAccess(kernelId).isAdminServer() || !ManagementService.getRuntimeAccess(kernelId).isAdminServerAvailable()) {
            String serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
            ServerResource resource = new ServerResource((String)null, serverName, "boot");
            if (!am.isAccessAllowed(bootUser, resource, new ResourceIDDContextWrapper(true))) {
               loggable = SecurityLogger.logUserNotPermittedToBootLoggable(SubjectUtils.getUsername(bootUser));
               loggable.log();
               throw new SecurityInitializationException(loggable.getMessageText());
            }
         }

      } else {
         throw new SecurityServiceRuntimeException(SecurityLogger.getSecurityServicesUnavailable());
      }
   }

   public void initialize(AuthenticatedSubject kernelID) {
      this.preInitialize(kernelID);
      this.postInitialize(kernelID);
   }

   public void preInitialize(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      this.setJAASConfiguration();
      checkOPSSPolicy();
      checkJACCCmdlineForConsistency();
      this.initJACCSecurity();
      if (null != realmsHashMap) {
         throw new SecurityServiceRuntimeException(SecurityLogger.getCanOnlyInitSecServiceOnce());
      } else {
         this.initializeRealms();
      }
   }

   public void postInitialize(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      this.postInitializeRealms();
      if (defaultConfiguredRealmName != null) {
         SecurityLogger.logInitializingUsingRealm(defaultConfiguredRealmName);
      } else {
         SecurityLogger.logInitializingUsingRealm("null");
      }

      this.consoleFullDelegation = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().isConsoleFullDelegationEnabled();
      CertPathTrustManagerUtils.start();
      this.doBootAuthorization();
      java.lang.SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         SecurityLogger.logInitializingUsingJavaSecurityManager();
         WLSPolicy wlsPolicy = new WLSPolicy();
         wlsPolicy.init();
      }

      if (this.isJACCEnabled()) {
         SecurityLogger.logInitializingUsingJACC();
      }

   }

   public void shutdown() {
      if (initialized) {
         Set realmKeySet = realmsHashMap.keySet();
         Iterator var2 = realmKeySet.iterator();

         while(true) {
            RealmServices realmServices;
            do {
               if (!var2.hasNext()) {
                  realmsHashMap = null;
                  return;
               }

               String realmName = (String)var2.next();
               realmServices = (RealmServices)realmsHashMap.remove(realmName);
            } while(realmServices == null);

            try {
               this.shutdownRealmServices(realmServices, false);
            } catch (Exception var6) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Failure during delegate shutdown for realm " + realmServices.getRealmName(), var6);
               }
            }
         }
      }
   }

   private void shutdownManagers(String realmName, HashMap servicesHashMap) {
      if (null != realmName && null != servicesHashMap) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Security Service is shutting down Managers in security realm " + realmName);
         }

         Auditor auditor = (Auditor)servicesHashMap.get(ORDERED_MANAGER_KEY_LIST[0]);

         for(int i = ORDERED_MANAGER_KEY_LIST.length - 1; i > 0; --i) {
            SecurityService securityService = (SecurityService)servicesHashMap.get(ORDERED_MANAGER_KEY_LIST[i]);
            if (securityService != null) {
               this.shutdownSecurityService(realmName, ORDERED_MANAGER_KEY_LIST[i].toString(), securityService, auditor);
               servicesHashMap.remove(ORDERED_MANAGER_KEY_LIST[i]);
            }
         }

         if (auditor != null) {
            this.shutdownSecurityService(realmName, ORDERED_MANAGER_KEY_LIST[0].toString(), (SecurityService)auditor, auditor);
         }

      } else {
         throw new SecurityServiceRuntimeException("Security Realm is in illegal state, failed to shutdown the security realm!");
      }
   }

   private void shutdownCSS(RealmServices realmServices, boolean restart) {
      if (realmServices == null) {
         throw new SecurityServiceRuntimeException("No RealmServices, failed to shutdown the security realm!");
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Security Service is shutting CSS in security realm " + realmServices.getRealmName());
         }

         CSSWLSDelegateImpl delegate = (CSSWLSDelegateImpl)realmServices.getCSS().getDelegate();
         delegate.shutdownInternal(restart);
      }
   }

   private void shutdownSecurityService(String realmName, String type, SecurityService securityService, Auditor auditor) {
      boolean auditShutdown = true;
      if (ServiceType.AUDIT.toString().equals(type)) {
         auditShutdown = false;
      }

      try {
         securityService.shutdown();
         String shutdownSuccess = "Security Service " + type + " is shutdown in security realm " + realmName;
         if (auditShutdown && auditor != null) {
            AuditSecurityManagementEventImpl securityManagementEvent = new AuditSecurityManagementEventImpl(AuditSeverity.SUCCESS, type, shutdownSuccess, (Exception)null);
            auditor.writeEvent(securityManagementEvent);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(shutdownSuccess);
         }
      } catch (RuntimeException var9) {
         String shutdownFailure = "Security Service " + type + " failed to shutdown in security realm " + realmName;
         if (auditShutdown && auditor != null) {
            AuditSecurityManagementEventImpl securityManagementEvent = new AuditSecurityManagementEventImpl(AuditSeverity.FAILURE, type, shutdownFailure, var9);
            auditor.writeEvent(securityManagementEvent);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(shutdownFailure, var9);
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public String getDefaultRealmName() {
      return defaultConfiguredRealmName;
   }

   public String getAdministrativeRealmName() {
      return defaultConfiguredRealmName;
   }

   public String getContextSensitiveRealmName() {
      return "weblogicDEFAULT";
   }

   public String getRealmName(String partitionName) {
      return PartitionUtils.getRealmName(partitionName, (ConfigurationMBean)null);
   }

   public String getRealmName(String partitionName, ConfigurationMBean proposedDomain) {
      return PartitionUtils.getRealmName(partitionName, proposedDomain);
   }

   public void applicationDeleted(ConfigurationMBean arg_mbean) {
      if (arg_mbean != null) {
         if (!(arg_mbean instanceof AppDeploymentMBean)) {
            throw new IllegalArgumentException("The applicationDeleted method requires the use of a AppDeployment mbean, not any other type of bean");
         } else {
            AppDeploymentMBean mbean = (AppDeploymentMBean)arg_mbean;
            String appIdentifier = mbean.getApplicationIdentifier();
            String partitionName = ApplicationVersionUtils.getPartitionName(appIdentifier);
            if ("DOMAIN".equals(partitionName)) {
               partitionName = null;
            }

            String realmToUse = this.getRealmName(partitionName);
            if (realmToUse == null) {
               realmToUse = defaultConfiguredRealmName;
            }

            RealmServices realmServices = (RealmServices)realmsHashMap.get(realmToUse);
            if (realmServices == null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("applicationDeleted: Realm is not running: " + realmToUse);
               }

            } else {
               RoleManager rm;
               AuthorizationManager am;
               ApplicationVersioningService appVerService;
               try {
                  rm = (RoleManager)realmServices.getServices().get(ServiceType.ROLE);
                  am = (AuthorizationManager)realmServices.getServices().get(ServiceType.AUTHORIZE);
                  appVerService = (ApplicationVersioningService)realmServices.getCSS().getService("ApplicationVersioningService");
               } catch (Exception var16) {
                  throw new IllegalStateException(SecurityLogger.getFailureWithRealm(realmToUse), var16);
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("applicationDeleted: Using realm: " + realmToUse);
               }

               SecurityApplicationInfo secAppInfo = new SecurityApplicationInfoImpl(mbean, ComponentType.APPLICATION, (String)null);

               try {
                  rm.deleteApplicationRoles(secAppInfo);
               } catch (Exception var15) {
               }

               try {
                  am.deleteApplicationPolicies(secAppInfo);
               } catch (Exception var14) {
               }

               if (mbean.getVersionIdentifier() != null) {
                  try {
                     appVerService.deleteApplicationVersion(appIdentifier);
                  } catch (Exception var13) {
                  }
               } else {
                  try {
                     appVerService.deleteApplication(appIdentifier);
                  } catch (Exception var12) {
                  }
               }

            }
         }
      }
   }

   public boolean isApplicationVersioningSupported(String realmName) {
      try {
         return this.getApplicationVersioningService(realmName).isApplicationVersioningSupported();
      } catch (Exception var3) {
         throw new SecurityServiceRuntimeException(SecurityLogger.getFailureWithRealm(realmName), var3);
      }
   }

   public void applicationVersionCreated(ConfigurationMBean mbean, ConfigurationMBean source, String realmName) {
      if (mbean != null) {
         if (!(mbean instanceof AppDeploymentMBean)) {
            throw new IllegalArgumentException("The applicationVersionCreated method requires the use of a AppDeployment mbean, not any other type of bean");
         } else {
            String appIdentifier = ((AppDeploymentMBean)mbean).getApplicationIdentifier();
            String sourceAppIdentifier = null;
            if (source != null) {
               if (!(source instanceof AppDeploymentMBean)) {
                  throw new IllegalArgumentException("The applicationVersionCreated method requires the use of a AppDeployment mbean, not any other type of bean");
               }

               sourceAppIdentifier = ((AppDeploymentMBean)source).getApplicationIdentifier();
            }

            try {
               this.getApplicationVersioningService(realmName).createApplicationVersion(appIdentifier, sourceAppIdentifier);
            } catch (Exception var7) {
               throw new SecurityServiceRuntimeException(SecurityLogger.getFailureWithRealm(realmName), var7);
            }
         }
      }
   }

   private ApplicationVersioningService getApplicationVersioningService(String realmName) throws ServiceInitializationException {
      String realmToUse = this.getActualRealmName(realmName);
      RealmServices realmServices = (RealmServices)realmsHashMap.get(realmToUse);
      if (realmServices == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getApplicationVersioningService: Realm is not running: " + realmToUse);
         }

         throw new IllegalStateException(SecurityLogger.getFailureWithRealm(realmToUse));
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getApplicationVersioningService: Using realm: " + realmToUse);
         }

         return (ApplicationVersioningService)realmServices.getCSS().getService("ApplicationVersioningService");
      }
   }

   private String getActualRealmName(String realmName) {
      if (realmName == null) {
         throw new InvalidParameterException(SecurityLogger.getMustSpecifyRealm());
      } else {
         String realmToUse = defaultConfiguredRealmName;
         if (realmName.equals(this.getContextSensitiveRealmName())) {
            String name = this.getRealmName(PartitionUtils.getPartitionName());
            if (name != null) {
               realmToUse = name;
            }
         } else {
            realmToUse = realmName;
         }

         return realmToUse;
      }
   }

   public void initJava2Security() {
      java.lang.SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         SecurityServiceManager.setJava2SecurityMode(true);
      }
   }

   private static void checkOPSSPolicy() {
      try {
         String jaccPolicy = System.getProperty("javax.security.jacc.policy.provider");
         if (jaccPolicy != null) {
            Policy exPolicy = Policy.getPolicy();
            if ("oracle.security.jps.internal.policystore.JavaPolicyProvider".equals(exPolicy.getClass().getCanonicalName())) {
               if (opssLoadDebug && debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Conflict between JACC Policy and OPSS Policy provider");
               }

               Loggable loggable = SecurityLogger.getPolicyLoadJACCConflictMessageLoggable("oracle.security.jps.internal.policystore.JavaPolicyProvider");
               loggable.log();
               throw new SecurityInitializationException(loggable.getMessageText());
            }
         }

      } catch (Throwable var3) {
         if (opssLoadDebug && debugLogger.isDebugEnabled()) {
            debugLogger.debug("Problem in checking OPSS security provider: " + var3.getMessage());
         }

         Loggable loggable = SecurityLogger.getLoadPolicyProviderErrorMessageLoggable("oracle.security.jps.internal.policystore.JavaPolicyProvider", var3.getMessage());
         loggable.log();
         throw new SecurityInitializationException(loggable.getMessageText(), var3);
      }
   }

   private static void startJPS() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
      Class clz = Class.forName(JPS_STARTUP_CLASS, true, Thread.currentThread().getContextClassLoader());
      Class[] argsClass = new Class[]{String.class};
      Constructor constructor = clz.getConstructor(argsClass);
      Object jpsStartup = constructor.newInstance("WEBLOGIC");
      Method method = clz.getMethod("start");
      method.invoke(jpsStartup);
   }

   public void initJACCSecurity() {
      checkJACCCmdlineForConsistency();
      String jaccPolicy = System.getProperty("javax.security.jacc.policy.provider");
      if (jaccPolicy != null && !this.isJACCEnabled()) {
         loadJACCPolicy();
      }

      String jaccPcf = System.getProperty("javax.security.jacc.PolicyConfigurationFactory.provider");
      if (jaccPcf != null) {
         checkJACCConfiguration();
      }

   }

   private static void matchJACCWLSClasses(String pcfClassName, String policyClassName, String roleMapperFactoryClassName, ErrorCollectionException errors) {
      boolean pcfOurs = false;
      if (pcfClassName != null) {
         pcfOurs = pcfClassName.equals("weblogic.security.jacc.simpleprovider.PolicyConfigurationFactoryImpl");
      }

      boolean policyOurs = false;
      if (policyClassName != null) {
         policyOurs = policyClassName.equals("weblogic.security.jacc.simpleprovider.SimpleJACCPolicy");
      }

      boolean roleMapOurs = false;
      if (roleMapperFactoryClassName != null) {
         roleMapOurs = roleMapperFactoryClassName.equals("weblogic.security.jacc.simpleprovider.RoleMapperFactoryImpl");
      }

      if ((pcfOurs || policyOurs) && (!pcfOurs || !policyOurs || !roleMapOurs)) {
         addError(errors, SecurityLogger.getJACCWebLogicClassesMustMatch());
      }

   }

   private static void addError(ErrorCollectionException errors, String error) {
      errors.add(new Exception(error));
   }

   private static void loadJACCPolicy() {
      if (JACC_POLICY_LOADED) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("SecurityServiceManagerDelegateImpl:loadJACCPolicy no policy loaded because it has been previously loaded.");
         }

      } else {
         String javaPolicy = System.getProperty("javax.security.jacc.policy.provider");
         if (javaPolicy != null) {
            Loggable loggable;
            try {
               Object obj = Class.forName(javaPolicy).newInstance();
               if (obj instanceof Policy) {
                  Policy policy = (Policy)obj;
                  Policy.setPolicy(policy);
                  policy.refresh();
                  JACC_POLICY_LOADED = true;
                  Loggable loggable = SecurityLogger.logJACCPolicyLoadedLoggable(javaPolicy);
                  loggable.log();
               } else {
                  loggable = SecurityLogger.logNotAPolicyObjectLoggable(javaPolicy);
                  loggable.log();
                  throw new SecurityInitializationException(loggable.getMessageText());
               }
            } catch (ClassNotFoundException var4) {
               loggable = SecurityLogger.logJACCPolicyProviderClassNotFoundLoggable(javaPolicy, var4);
               loggable.log();
               throw new SecurityInitializationException(loggable.getMessageText());
            } catch (IllegalAccessException var5) {
               loggable = SecurityLogger.logIllegalAccessLoggable(javaPolicy, var5);
               loggable.log();
               throw new SecurityInitializationException(loggable.getMessageText());
            } catch (InstantiationException var6) {
               loggable = SecurityLogger.logInstantiationExceptionLoggable(javaPolicy, var6);
               loggable.log();
               throw new SecurityInitializationException(loggable.getMessageText());
            }
         } else {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("SecurityServiceManagerDelegateImpl:loadJACCPolicy no policy loaded because javax.security.jacc.policy.provider was not specified.");
            }

         }
      }
   }

   private static void checkJACCConfiguration() {
      PolicyConfigurationFactory rmf = null;

      Loggable loggable;
      Loggable loggable;
      String rmfName;
      try {
         rmf = PolicyConfigurationFactory.getPolicyConfigurationFactory();
      } catch (ClassNotFoundException var6) {
         rmfName = System.getProperty("javax.security.jacc.PolicyConfigurationFactory.provider");
         loggable = SecurityLogger.logJACCPolicyConfigurationFactoryProviderClassNotFoundLoggable(rmfName == null ? "null" : rmfName, var6);
         loggable.log();
         throw new SecurityInitializationException(loggable.getMessageText());
      } catch (PolicyContextException var7) {
         loggable = SecurityLogger.logPolicyContextExceptionLoggable(var7);
         loggable.log();
         throw new SecurityInitializationException(loggable.getMessageText());
      }

      Loggable loggable;
      if (rmf != null && rmf instanceof PolicyConfigurationFactory) {
         loggable = SecurityLogger.logJACCPolicyConfigurationFactoryLoadedLoggable(rmf.getClass().getName());
         loggable.log();
         if (rmf instanceof PolicyConfigurationFactoryImpl && jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("SecurityServiceManagerDelegateImpl:checkJACCConfiguration. We found a weblogic.security.jacc.simpleprovider.PolicyConfigurationImpl and loaded it.");
         }

         rmf = null;

         RoleMapperFactory rmf;
         try {
            rmf = RoleMapperFactory.getRoleMapperFactory();
         } catch (ClassNotFoundException var4) {
            rmfName = System.getProperty("weblogic.security.jacc.RoleMapperFactory.provider");
            loggable = SecurityLogger.logJACCRoleMapperFactoryProviderClassNotFoundLoggable(rmfName == null ? "null" : rmfName, var4);
            loggable.log();
            throw new SecurityInitializationException(loggable.getMessageText());
         } catch (PolicyContextException var5) {
            loggable = SecurityLogger.logPolicyContextExceptionLoggable(var5);
            loggable.log();
            throw new SecurityInitializationException(loggable.getMessageText());
         }

         if (rmf != null && rmf instanceof RoleMapperFactory) {
            loggable = SecurityLogger.logJACCRoleMapperFactoryLoadedLoggable(rmf.getClass().getName());
            loggable.log();
            if (rmf instanceof RoleMapperFactoryImpl && jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("SecurityServiceManagerDelegateImpl:checkJACCConfiguration. We found a weblogic.security.jacc.simpleprovider.RoleMapperFactoryImpl and loaded it.");
            }

         } else {
            loggable = SecurityLogger.logRoleMapperFactoryProblemLoggable();
            loggable.log();
            throw new SecurityInitializationException(loggable.getMessageText());
         }
      } else {
         loggable = SecurityLogger.logPolicyConfigurationFactoryProblemLoggable();
         loggable.log();
         throw new SecurityInitializationException(loggable.getMessageText());
      }
   }

   public boolean isJACCEnabled() {
      return JACC_POLICY_LOADED;
   }

   public DeploymentValidator getDeploymentValidator(AuthenticatedSubject kernelID, String realmName, SecurityApplicationInfo appInfo) {
      return (DeploymentValidator)(!appInfo.isValidateDDSecurityData() ? new DeploymentValidatorUnknownImpl() : new DeploymentUtils(realmName, kernelID));
   }

   public int getRoleMappingBehavior(String realmName, SecurityApplicationInfo appInfo) {
      int roleMappingBehavior = 1;
      String realmToUse;
      if (appInfo != null) {
         realmToUse = appInfo.getSecurityDDModel();
         if (!"Advanced".equals(realmToUse)) {
            if ("CustomRoles".equals(realmToUse) || "CustomRolesAndPolicies".equals(realmToUse)) {
               roleMappingBehavior = 2;
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("getRoleMappingBehavior: returning " + roleMappingBehavior);
            }

            return roleMappingBehavior;
         }
      }

      realmToUse = this.getActualRealmName(realmName);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("getRoleMappingBehavior: using realm setting from: " + realmToUse);
      }

      RealmMBean realmMbean = null;

      try {
         realmMbean = getRealmFromName(realmToUse);
      } catch (Exception var7) {
         throw new IllegalStateException(SecurityLogger.getFailureWithRealm(realmToUse), var7);
      }

      if (!realmMbean.isCombinedRoleMappingEnabled()) {
         roleMappingBehavior = 0;
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("getRoleMappingBehavior: returning " + roleMappingBehavior);
      }

      return roleMappingBehavior;
   }

   private static void checkJACCCmdlineForConsistency() {
      ErrorCollectionException errors = new ErrorCollectionException();
      Loggable loggable = null;
      String jaccPcf = System.getProperty("javax.security.jacc.PolicyConfigurationFactory.provider");
      String jaccPolicy = System.getProperty("javax.security.jacc.policy.provider");
      String jaccRoleMapper = System.getProperty("weblogic.security.jacc.RoleMapperFactory.provider");
      if ("weblogic.security.jacc.simpleprovider.PolicyConfigurationFactoryImpl".equals(jaccPcf) && "weblogic.security.jacc.simpleprovider.SimpleJACCPolicy".equals(jaccPolicy) && jaccRoleMapper == null) {
         jaccRoleMapper = "weblogic.security.jacc.simpleprovider.RoleMapperFactoryImpl";
      }

      if (jaccPcf != null && jaccPolicy != null && jaccRoleMapper == null) {
         throw new SecurityInitializationException(SecurityLogger.getJACCWebLogicRoleMapperFactoryNotSupplied());
      } else {
         matchJACCWLSClasses(jaccPcf, jaccPolicy, jaccRoleMapper, errors);
         if (!errors.isEmpty()) {
            throw new SecurityInitializationException(SecurityLogger.getInconsistentSecurityConfiguration(), errors);
         }
      }
   }

   private CSS InitializeServiceEngine(RealmMBean realmMBean) throws SecurityServiceException {
      CSSWLSDelegateImpl delegate = new CSSWLSDelegateImpl();
      delegate.initialize(realmMBean);
      CSS css = CSS.getInstance();
      css.setDelegate(delegate);
      return css;
   }

   public CSS getCSS(AuthenticatedSubject kernelID, String realmName) {
      SecurityManager.checkKernelIdentity(kernelID);
      if (realmName == null) {
         return null;
      } else {
         RealmServices realmServices = (RealmServices)realmsHashMap.get(realmName);
         if (realmServices == null) {
            throw new InvalidParameterException(SecurityLogger.getRealmDoesNotExist(realmName));
         } else {
            return realmServices.getCSS();
         }
      }
   }

   public Object getCSSServiceInternal(String realmName, String serviceName) throws InvalidParameterException {
      RealmServices realmServices = (RealmServices)realmsHashMap.get(realmName);
      if (realmServices == null) {
         this.startRealm(realmName);
         realmServices = (RealmServices)realmsHashMap.get(realmName);
      }

      if (realmServices == null) {
         throw new InvalidParameterException(SecurityLogger.getRealmDoesNotExist(realmName));
      } else {
         try {
            return realmServices.getCSS().getService(serviceName);
         } catch (ServiceInitializationException var5) {
            throw new InvalidParameterException(SecurityLogger.getMustSpecifySecServiceType(), var5);
         }
      }
   }

   public Object getCSSServiceProxy(String serviceName, String realmName) throws InvalidParameterException {
      if (serviceName == null) {
         throw new InvalidParameterException(SecurityLogger.getMustSpecifySecServiceType());
      } else {
         Object result = null;
         if (realmName == null) {
            result = cssServiceProxiesHashMap.get(serviceName);
         } else {
            result = this.generateCSSWiredServiceProxy(realmName, serviceName);
         }

         if (result == null) {
            throw new InvalidParameterException(SecurityLogger.getMustSpecifySecServiceType());
         } else {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("getCSSServiceProxy: returned proxy for " + serviceName);
            }

            return result;
         }
      }
   }

   public void initializeRealm(AuthenticatedSubject kernelID, String realmName) {
      if (initialized) {
         SecurityManager.checkKernelIdentity(kernelID);
         if (realmName != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("initializeRealm: starting realm " + realmName);
            }

            this.startRealm(realmName);
         }
      }
   }

   public void shutdownRealm(AuthenticatedSubject kernelID, String realmName) {
      if (initialized) {
         SecurityManager.checkKernelIdentity(kernelID);
         if (realmName != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("shutdownRealm: shutting down realm " + realmName);
            }

            this.shutdownRealm(realmName);
         }
      }
   }

   public void restartRealm(AuthenticatedSubject kernelID, String realmName) {
      if (initialized) {
         SecurityManager.checkKernelIdentity(kernelID);
         if (realmName != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("restartRealm: re-starting realm " + realmName);
            }

            this.restartRealm(realmName);
         }
      }
   }

   public boolean isRealmShutdown(String realmName) {
      if (initialized && realmName != null) {
         return shutdownRealmsHashMap.containsKey(realmName);
      } else {
         return false;
      }
   }

   private void setJAASConfiguration() {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            Security.setProperty("login.configuration.provider", "com.bea.common.security.jdkutils.JAASConfiguration");
            return null;
         }
      });
   }

   private HashMap generateServiceProxies() {
      HashMap proxies = new HashMap();
      proxies.put(ServiceType.AUTHENTICATION, this.getSecurityServiceProxy(ServiceType.AUTHENTICATION, PrincipalAuthenticatorImpl.class));
      proxies.put(ServiceType.AUTHORIZE, this.getSecurityServiceProxy(ServiceType.AUTHORIZE, AuthorizationManagerImpl.class));
      proxies.put(ServiceType.ROLE, this.getSecurityServiceProxy(ServiceType.ROLE, RoleManagerImpl.class));
      proxies.put(ServiceType.CREDENTIALMANAGER, this.getSecurityServiceProxy(ServiceType.CREDENTIALMANAGER, CredentialManagerImpl.class));
      proxies.put(ServiceType.AUDIT, this.getSecurityServiceProxy(ServiceType.AUDIT, AuditorImpl.class));
      proxies.put(ServiceType.CERTPATH, this.getSecurityServiceProxy(ServiceType.CERTPATH, CertPathManagerImpl.class));
      proxies.put(ServiceType.SAML2_SSO, this.getSecurityServiceProxy(ServiceType.SAML2_SSO, SAML2ServiceWrapper.class));
      proxies.put(ServiceType.STSMANAGER, this.getSecurityServiceProxy(ServiceType.STSMANAGER, SecurityTokenServiceManagerImpl.class));
      proxies.put(ServiceType.BULKAUTHORIZE, this.getSecurityServiceProxy(ServiceType.BULKAUTHORIZE, BulkAuthorizationManagerImpl.class));
      proxies.put(ServiceType.BULKROLE, this.getSecurityServiceProxy(ServiceType.BULKROLE, BulkRoleManagerImpl.class));
      return proxies;
   }

   private SecurityService getSecurityServiceProxy(SecurityService.ServiceType type, Class service) {
      return (SecurityService)Proxy.newProxyInstance(this.getClass().getClassLoader(), service.getInterfaces(), new ServiceHandler.SecurityServiceHandler(type, this, debugLogger));
   }

   private HashMap generateCSSServiceProxies() {
      HashMap proxies = new HashMap();
      proxies.put("PrincipalValidationService", this.getCSSSecurityServiceProxy("PrincipalValidationService", PrincipalValidationService.class));
      proxies.put("PolicyConsumerService", this.getCSSSecurityServiceProxy("PolicyConsumerService", PolicyConsumerService.class));
      proxies.put("RoleConsumerService", this.getCSSSecurityServiceProxy("RoleConsumerService", RoleConsumerService.class));
      return proxies;
   }

   private Object getCSSSecurityServiceProxy(String serviceName, Class intf) {
      return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{intf}, new ServiceHandler.CSSServiceHandler(serviceName, this, debugLogger));
   }

   private SecurityService generateWiredServiceProxy(String realmName, SecurityService.ServiceType type, Object service) {
      RealmServices realmServices = (RealmServices)realmsHashMap.get(realmName);
      if (realmServices == null) {
         return (SecurityService)service;
      } else {
         SecurityService proxy = null;
         synchronized(realmServices.getProxyLock()) {
            proxy = (SecurityService)realmServices.getProxies().get(type);
            if (proxy == null) {
               proxy = (SecurityService)Proxy.newProxyInstance(this.getClass().getClassLoader(), service.getClass().getInterfaces(), new ServiceHandler.WiredServiceHandler(realmName, type, this, debugLogger));
               realmServices.getProxies().put(type, proxy);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("generateWiredServiceProxy: added proxy for " + realmName + " to " + service.toString());
               }
            }

            return proxy;
         }
      }
   }

   private Class getCSSServiceInterface(String serviceName) {
      switch (serviceName) {
         case "PolicyConsumerService":
            return PolicyConsumerService.class;
         case "RoleConsumerService":
            return RoleConsumerService.class;
         case "PrincipalValidationService":
            return PrincipalValidationService.class;
         default:
            return null;
      }
   }

   private Object generateCSSWiredServiceProxy(String realmName, String serviceName) {
      Object proxy = null;
      RealmServices realmServices = (RealmServices)realmsHashMap.get(realmName);
      if (realmServices != null) {
         synchronized(realmServices.getProxyLock()) {
            proxy = realmServices.getCSSProxies().get(serviceName);
            if (proxy == null) {
               Class intf = this.getCSSServiceInterface(serviceName);
               if (intf != null) {
                  proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{intf}, new ServiceHandler.CSSWiredServiceHandler(realmName, serviceName, this, debugLogger));
                  realmServices.getCSSProxies().put(serviceName, proxy);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("generateCSSWiredServiceProxy: added proxy for " + realmName + " to " + serviceName);
                  }
               }
            }
         }
      }

      return proxy;
   }

   private ClassLoader getThreadContextClassloader() {
      PrivilegedAction tclAction = new PrivilegedAction() {
         public ClassLoader run() {
            return Thread.currentThread().getContextClassLoader();
         }
      };
      return (ClassLoader)AccessController.doPrivileged(tclAction);
   }

   private void setThreadContextClassloader(final ClassLoader tcl) {
      PrivilegedAction tclAction = new PrivilegedAction() {
         public Object run() {
            Thread.currentThread().setContextClassLoader(tcl);
            return null;
         }
      };
      AccessController.doPrivileged(tclAction);
   }

   private void startRealm(String realmName) {
      RealmMBean realmMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().lookupRealm(realmName);
      if (realmMBean == null) {
         throw new SecurityServiceRuntimeException(SecurityLogger.getRealmDoesNotExist(realmName));
      } else {
         Object realmLock = this.getRealmLock(realmMBean);
         synchronized(realmLock) {
            RealmServices realmServices = (RealmServices)realmsHashMap.get(realmName);
            if (realmServices != null) {
               return;
            }

            SecurityLogger.logStartingRealm(realmName);
            RealmServices shutdownRealmServices = null;

            try {
               shutdownRealmServices = (RealmServices)shutdownRealmsHashMap.remove(realmName);
               realmServices = (RealmServices)AccessController.doPrivileged(new StartRealmInternalAction(realmMBean));
               if (shutdownRealmServices != null) {
                  realmServices.setCSSProxies(shutdownRealmServices.getCSSProxies());
                  realmServices.setProxies(shutdownRealmServices.getProxies());
                  realmServices.setProxyLock(shutdownRealmServices.getProxyLock());
                  if (shutdownRealmServices.isDefault()) {
                     realmServices.setDefault();
                  }
               }

               realmsHashMap.put(realmServices.getRealmName(), realmServices);
            } catch (Exception var9) {
               if (shutdownRealmServices != null) {
                  shutdownRealmsHashMap.put(realmName, shutdownRealmServices);
               }

               throw new SecurityServiceRuntimeException(var9);
            }
         }

         SecurityLogger.logCompletedStartingRealm(realmName);
      }
   }

   private RealmServices startRealmInternal(final RealmMBean realmMBean) throws SecurityServiceException {
      RealmServices newRealmServices = null;

      try {
         Object result = SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               ClassLoader tcl = CommonSecurityServiceManagerDelegateImpl.this.getThreadContextClassloader();
               CommonSecurityServiceManagerDelegateImpl.this.setThreadContextClassloader(this.getClass().getClassLoader());

               RealmServices var3;
               try {
                  RealmServices newRealmServices = CommonSecurityServiceManagerDelegateImpl.this.initializeRealm(realmMBean, true);
                  CommonSecurityServiceManagerDelegateImpl.this.postInitializeRealm(realmMBean, newRealmServices);
                  var3 = newRealmServices;
               } finally {
                  CommonSecurityServiceManagerDelegateImpl.this.setThreadContextClassloader(tcl);
               }

               return var3;
            }
         });
         newRealmServices = (RealmServices)result;
         return newRealmServices;
      } catch (Exception var8) {
         String realmName = realmMBean.getName();
         SecurityLogger.logLoadRealmFailed(realmName, var8);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Failed to start security realm: " + realmName, var8);
         }

         try {
            ServerSecurityRuntimeMBean securityRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getServerSecurityRuntime();
            RealmRuntimeMBean realmRuntime = securityRuntime.lookupRealmRuntime(realmName);
            securityRuntime.removeRealmRuntime(realmRuntime);
         } catch (ManagementException var7) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failed cleanup of security realm runtime: " + realmName, var7);
            }
         }

         throw new SecurityServiceException(SecurityLogger.getFailureWithRealm(realmName), var8);
      }
   }

   private void restartRealm(String realmName) {
      RealmMBean realmMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().lookupRealm(realmName);
      if (realmMBean == null) {
         throw new SecurityServiceRuntimeException(SecurityLogger.getRealmDoesNotExist(realmName));
      } else {
         final RealmServices oldRealmServices = null;
         RealmServices newRealmServices = null;
         Object realmLock = this.getRealmLock(realmMBean);
         synchronized(realmLock) {
            oldRealmServices = (RealmServices)realmsHashMap.get(realmName);
            if (oldRealmServices == null) {
               return;
            }

            ServerSecurityRuntimeMBean securityRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getServerSecurityRuntime();
            RealmRuntimeMBean oldRealmRuntime = securityRuntime.lookupRealmRuntime(realmName);
            if (oldRealmRuntime == null) {
               throw new SecurityServiceRuntimeException("RealmRuntimeMBean not found!");
            }

            try {
               securityRuntime.removeRealmRuntime(oldRealmRuntime);
            } catch (ManagementException var14) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Failed to unregister security realm runtime: " + realmName, var14);
               }

               throw new SecurityServiceRuntimeException(var14);
            }

            SecurityLogger.logRestartingRealm(realmName);

            try {
               newRealmServices = (RealmServices)AccessController.doPrivileged(new StartRealmInternalAction(realmMBean));
               if (oldRealmServices.isDefault()) {
                  newRealmServices.setDefault();
               }

               newRealmServices.setCSSProxies(oldRealmServices.getCSSProxies());
               newRealmServices.setProxies(oldRealmServices.getProxies());
               newRealmServices.setProxyLock(oldRealmServices.getProxyLock());
               realmsHashMap.put(realmName, newRealmServices);
            } catch (Exception var16) {
               try {
                  ((SecurityRuntime)securityRuntime).registerRestart(realmName, oldRealmRuntime);
                  ((RealmRuntime)oldRealmRuntime).registerRestart();
               } catch (ManagementException var15) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Failed re-register of security realm runtime: " + realmName, var15);
                  }
               }

               throw new SecurityServiceRuntimeException(var16);
            }
         }

         this.flushProxies(cssServiceProxiesHashMap.values().iterator());
         this.flushProxies(serviceProxiesHashMap.values().iterator());
         synchronized(newRealmServices.getProxyLock()) {
            this.flushProxies(newRealmServices.getProxies().values().iterator());
            this.flushProxies(newRealmServices.getCSSProxies().values().iterator());
         }

         SecurityLogger.logCompletedRestartingRealm(realmName);
         TimerListener shutdownListener = new TimerListener() {
            public final void timerExpired(Timer timer) {
               try {
                  CommonSecurityServiceManagerDelegateImpl.this.shutdownRealmServices(oldRealmServices, true);
               } catch (Exception var3) {
                  SecurityLogger.logShutdownRealmFailed(oldRealmServices.getRealmName(), var3);
                  if (CommonSecurityServiceManagerDelegateImpl.debugLogger.isDebugEnabled()) {
                     CommonSecurityServiceManagerDelegateImpl.debugLogger.debug("Failure during shutdown of retired realm from re-start " + oldRealmServices.getRealmName(), var3);
                  }
               }

            }
         };
         TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(shutdownListener, (long)(realmMBean.getRetireTimeoutSeconds() * 1000));
      }
   }

   private void flushProxies(Iterator it) {
      while(it.hasNext()) {
         Object obj = it.next();
         if (obj instanceof Proxy) {
            InvocationHandler hdlr = Proxy.getInvocationHandler(obj);
            if (hdlr instanceof ServiceHandler) {
               ((ServiceHandler)hdlr).flush();
            }
         }
      }

   }

   private void shutdownRealm(String realmName) {
      RealmMBean realmMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().lookupRealm(realmName);
      if (realmMBean == null) {
         try {
            Iterator var9 = realmsLockMap.keySet().iterator();

            while(var9.hasNext()) {
               RealmMBean realm = (RealmMBean)var9.next();
               if (realmName.equals(realm.getName())) {
                  realmsLockMap.remove(realm);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Removed the lifecycle lock on realm " + realmName);
                  }
                  break;
               }
            }
         } catch (Exception var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failed to remove lifecycle lock on realm " + realmName, var8);
            }
         }

         AccessController.doPrivileged(new ShutdownRealmInternalAction(realmName));
      } else {
         Object realmLock = this.getRealmLock(realmMBean);
         synchronized(realmLock) {
            RealmServices realmServices = (RealmServices)realmsHashMap.get(realmName);
            if (realmServices != null) {
               if (realmServices.isDefault()) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Disallow shutdown of administrative realm " + realmName);
                  }

               } else {
                  AccessController.doPrivileged(new ShutdownRealmInternalAction(realmName));
               }
            }
         }
      }
   }

   private void shutdownRealmInternal(String realmName) {
      RealmServices realmServices = (RealmServices)realmsHashMap.remove(realmName);
      if (realmServices != null) {
         this.flushProxies(cssServiceProxiesHashMap.values().iterator());
         this.flushProxies(serviceProxiesHashMap.values().iterator());
         synchronized(realmServices.getProxyLock()) {
            this.flushProxies(realmServices.getProxies().values().iterator());
            this.flushProxies(realmServices.getCSSProxies().values().iterator());
         }

         shutdownRealmsHashMap.put(realmName, realmServices);

         try {
            this.shutdownRealmServices(realmServices, false);
         } catch (Exception var7) {
            SecurityLogger.logShutdownRealmFailed(realmServices.getRealmName(), var7);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failure during shutdown of realm services for " + realmName, var7);
            }
         }

         try {
            ServerSecurityRuntimeMBean securityRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getServerSecurityRuntime();
            RealmRuntimeMBean realmRuntime = securityRuntime.lookupRealmRuntime(realmName);
            securityRuntime.removeRealmRuntime(realmRuntime);
         } catch (Exception var6) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failed at realm shutdown to unregister security realm runtime: " + realmName, var6);
            }
         }

      }
   }

   private void shutdownRealmServices(final RealmServices realmServices, final boolean restart) {
      synchronized(realmServices) {
         if (!realmServices.isShutdown()) {
            try {
               realmServices.cleanup();
               SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
                  public Object run() {
                     ClassLoader tcl = CommonSecurityServiceManagerDelegateImpl.this.getThreadContextClassloader();
                     CommonSecurityServiceManagerDelegateImpl.this.setThreadContextClassloader(this.getClass().getClassLoader());

                     Object var2;
                     try {
                        if (restart) {
                           SecurityLogger.logShutdownRetiredSecurityRealm(realmServices.getRealmName());
                        } else {
                           SecurityLogger.logShutdownSecurityRealm(realmServices.getRealmName());
                        }

                        CommonSecurityServiceManagerDelegateImpl.this.shutdownManagers(realmServices.getRealmName(), realmServices.getServices());
                        CommonSecurityServiceManagerDelegateImpl.this.shutdownCSS(realmServices, restart);
                        var2 = null;
                     } finally {
                        CommonSecurityServiceManagerDelegateImpl.this.setThreadContextClassloader(tcl);
                     }

                     return var2;
                  }
               });
            } finally {
               realmServices.shutdown();
            }

         }
      }
   }

   private Object getRealmLock(RealmMBean realmMBean) {
      synchronized(realmMBean) {
         Object realmLock = realmsLockMap.get(realmMBean);
         if (realmLock == null) {
            realmLock = new Object();
            realmsLockMap.put(realmMBean, realmLock);
         }

         return realmLock;
      }
   }

   static {
      opssLoadDebug = System.getProperty(OPSS_LOAD_LOG) != null;
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ORDERED_MANAGER_KEY_LIST = new Object[]{ServiceType.AUDIT, ServiceType.CREDENTIALMANAGER, ServiceType.BULKROLE, ServiceType.BULKAUTHORIZE, ServiceType.ROLE, ServiceType.AUTHORIZE, ServiceType.AUTHENTICATION, ServiceType.CERTPATH, ServiceType.STSMANAGER, ServiceType.SAML2_SSO};
      String tmpFullDelegateOverrideValue = null;
      boolean tmpFullDelegatePropertyOnCmdline = false;

      try {
         tmpFullDelegateOverrideValue = System.getProperty("weblogic.security.fullyDelegateAuthorization");
         if (tmpFullDelegateOverrideValue != null) {
            tmpFullDelegatePropertyOnCmdline = true;
         } else {
            tmpFullDelegatePropertyOnCmdline = false;
         }
      } catch (SecurityException var3) {
         tmpFullDelegatePropertyOnCmdline = false;
      }

      FULL_DELEGATE_PROPERTY_ON_CMDLINE = tmpFullDelegatePropertyOnCmdline;
      FULL_DELEGATE_OVERRIDE_VALUE = new Boolean(tmpFullDelegateOverrideValue);
   }

   private final class ShutdownRealmInternalAction implements PrivilegedAction {
      private final String realmName;

      private ShutdownRealmInternalAction(String realmName) {
         this.realmName = realmName;
      }

      public Object run() {
         CommonSecurityServiceManagerDelegateImpl.this.shutdownRealmInternal(this.realmName);
         return null;
      }

      // $FF: synthetic method
      ShutdownRealmInternalAction(String x1, Object x2) {
         this(x1);
      }
   }

   private final class StartRealmInternalAction implements PrivilegedExceptionAction {
      private final RealmMBean realmMBean;

      private StartRealmInternalAction(RealmMBean realmMBean) {
         this.realmMBean = realmMBean;
      }

      public RealmServices run() throws SecurityServiceException {
         return CommonSecurityServiceManagerDelegateImpl.this.startRealmInternal(this.realmMBean);
      }

      // $FF: synthetic method
      StartRealmInternalAction(RealmMBean x1, Object x2) {
         this(x1);
      }
   }
}
