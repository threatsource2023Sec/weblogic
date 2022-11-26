package weblogic.security.service;

import com.bea.common.classloader.service.ClassLoaderService;
import com.bea.common.engine.ServiceEngine;
import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineConfigFactory;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.jdkutils.ServletAccess;
import com.bea.common.security.jdkutils.ServletInfoV2Spi;
import com.bea.common.security.legacy.AuditServicesConfigHelper;
import com.bea.common.security.legacy.AuthenticationServicesConfigHelper;
import com.bea.common.security.legacy.AuthorizationServicesConfigHelper;
import com.bea.common.security.legacy.CertPathServicesConfigHelper;
import com.bea.common.security.legacy.ConfigHelperFactory;
import com.bea.common.security.legacy.CredentialMappingServicesConfigHelper;
import com.bea.common.security.legacy.IdentityServicesConfigHelper;
import com.bea.common.security.legacy.LegacyDomainInfo;
import com.bea.common.security.legacy.LoginSessionServiceConfigHelper;
import com.bea.common.security.legacy.SAML2SingleSignOnServicesConfigHelper;
import com.bea.common.security.legacy.SAMLSingleSignOnServiceConfigHelper;
import com.bea.common.security.legacy.SecurityProviderClassLoaderService;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.SecurityTokenServicesConfigHelper;
import com.bea.common.security.legacy.spi.SAMLSingleSignOnServiceConfigInfoSpi;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.common.security.service.AuthorizationService;
import com.bea.common.security.service.BulkAuthorizationService;
import com.bea.common.security.service.CertPathBuilderService;
import com.bea.common.security.service.CertPathValidatorService;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.JAASAuthenticationService;
import com.bea.common.security.service.LoginSession;
import com.bea.common.security.service.LoginSessionListener;
import com.bea.common.security.service.LoginSessionService;
import com.bea.common.security.service.NegotiateIdentityAsserterService;
import com.bea.common.security.service.PolicyDeploymentService;
import com.bea.common.security.service.RoleDeploymentService;
import com.bea.common.security.service.SAML2Service;
import com.bea.common.security.service.SAMLSingleSignOnService;
import com.bea.common.security.utils.LegacyEncryptorKey;
import com.bea.common.security.utils.ProviderMBeanInvocationHandler;
import com.bea.common.security.utils.SAML2ClassLoader;
import com.bea.common.security.utils.ThreadClassLoaderContextInvocationHandler;
import com.bea.security.css.CSSConfig;
import com.bea.security.css.CSSConfigurationException;
import com.bea.security.css.CSSDelegate;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.KeyException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import weblogic.core.base.api.AdminServerStatusService;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.FederationServicesMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.AuthenticatorMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.authorization.AdjudicatorMBean;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.RoleMapperMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.management.security.pk.CertPathBuilderMBean;
import weblogic.management.security.pk.CertPathProviderMBean;
import weblogic.management.security.pk.CertPathValidatorMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.internal.WLSAuthenticationServicesConfigHelper;
import weblogic.security.service.internal.WLSIdentityServiceImpl;
import weblogic.security.service.internal.WLSInternalServicesConfigHelper;
import weblogic.security.service.internal.WLSMiscellaneousServicesConfigHelper;
import weblogic.security.shared.LoggerWrapper;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.security.ServletAuthentication;

public class CSSWLSDelegateImpl implements CSSDelegate {
   static final String SERVLET_AUTHENTICATION_FILTER_SERVICE = "ServletAuthenticationFilterService";
   static final String WSPASSWORD_DIGEST_SERVICE = "WSPasswordDigestService";
   static final String USER_LOCKOUT_ADMINISTRATION_SERVICE = "UserLockoutAdministrationService";
   static final String USER_LOCKOUT_COORDINATION_SERVICE = "UserLockoutCoordinationService";
   static final String APPLICATION_VERSIONING_SERVICE = "ApplicationVersioningService";
   private static final String CSS_LIFECYCLE_IMPL_LOADER_NAME = "cssImplLoaderName";
   private static final String WLS_LIFECYCLE_IMPL_LOADER_NAME = "wlsImplLoaderName";
   private static final String SAML2_LOADER_NAME = "SAML2ClassLoader";
   private static final String SAML2_CM_NAME = "com.bea.security.saml2.providers.SAML2CredentialMapperProviderImpl";
   private static final String SAML2_IA_NAME = "com.bea.security.saml2.providers.SAML2IdentityAsserterProviderImpl";
   private static final String SAML2_CM_WRAPPER = "com.bea.security.saml2.cssservice.SAML2CredentialMapperWrapper";
   private static final String SAML2_IA_WRAPPER = "com.bea.security.saml2.cssservice.SAML2IdentityAsserterWrapper";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static LoggerWrapper debugLogger = LoggerWrapper.getInstance("SecurityRealm");
   private static ClassLoader saml2ClassLoader = null;
   ServiceEngine serviceEngine = null;
   Services services = null;
   private Map serviceNameMap = new HashMap();
   private RealmMBean originalRealmMBean = null;
   private Map servicesCache = new HashMap();

   CSSWLSDelegateImpl() {
   }

   public Object getService(String serviceName) throws ServiceInitializationException, ServiceNotFoundException {
      if (this.services == null) {
         throw new IllegalStateException(SecurityLogger.getSecurityServicesUnavailable());
      } else {
         Object service = this.servicesCache.get(serviceName);
         if (service != null) {
            return service;
         } else {
            Object baseService = this.services.getService(this.getServiceLongName(serviceName));
            LoggerService loggerService = (LoggerService)this.services.getService(LoggerService.SERVICE_NAME);
            if ("RoleDeploymentService".equals(serviceName)) {
               service = new WLSRoleDeploymentServiceWrapper((RoleDeploymentService)baseService, loggerService, this.originalRealmMBean);
            } else if ("PolicyDeploymentService".equals(serviceName)) {
               service = new WLSPolicyDeploymentServiceWrapper((PolicyDeploymentService)baseService, loggerService, this.originalRealmMBean);
            } else if ("BulkAuthorizationService".equals(serviceName)) {
               service = new WLSBulkAuthorizationServiceWrapper((BulkAuthorizationService)baseService, loggerService);
            } else if ("CertPathBuilderService".equals(serviceName)) {
               service = new WLSCertPathBuilderServiceWrapper((CertPathBuilderService)baseService, loggerService);
            } else if ("CertPathValidatorService".equals(serviceName)) {
               service = new WLSCertPathValidatorServiceWrapper((CertPathValidatorService)baseService, loggerService);
            } else if ("AuthorizationService".equals(serviceName)) {
               service = new WLSAuthorizationServiceWrapper((AuthorizationService)baseService, loggerService);
            } else if ("IdentityAssertionService".equals(serviceName)) {
               service = new WLSIdentityAssertionServiceWrapper((IdentityAssertionService)baseService, loggerService);
            } else if ("JAASAuthenticationService".equals(serviceName)) {
               service = new WLSJAASAuthenticationServiceWrapper((JAASAuthenticationService)baseService, loggerService);
            } else {
               service = baseService;
            }

            this.servicesCache.put(serviceName, service);
            return service;
         }
      }
   }

   public String getServiceLoggingName(String serviceName) throws ServiceNotFoundException {
      if (this.services == null) {
         throw new IllegalStateException(SecurityLogger.getSecurityServicesUnavailable());
      } else {
         return this.services.getServiceLoggingName(this.getServiceLongName(serviceName));
      }
   }

   public Object getServiceManagementObject(String serviceName) throws ServiceInitializationException, ServiceNotFoundException {
      if (this.services == null) {
         throw new IllegalStateException(SecurityLogger.getSecurityServicesUnavailable());
      } else {
         return this.services.getServiceManagementObject(this.getServiceLongName(serviceName));
      }
   }

   public void initialize(CSSConfig config, ClassLoader loader, IdentityService identity, LoggerService logger) throws CSSConfigurationException {
      assertNotUsingCommon();
   }

   public void shutdown() {
      assertNotUsingCommon();
   }

   void shutdownInternal(boolean restart) {
      this.servicesCache = null;
      this.services = null;
      if (!restart && this.originalRealmMBean != null) {
         ServletAccess.getInstance().unRegisterServletInfo(this.originalRealmMBean.getName());
      }

      if (this.serviceEngine != null) {
         this.serviceEngine.shutdown();
      }

   }

   void initialize(RealmMBean realmMBean) throws SecurityServiceException {
      this.initializeServiceEngine(realmMBean);
   }

   private static void assertNotUsingCommon() {
      throw new AssertionError("This code should not be called when using common security under WLS");
   }

   private String getServiceLongName(String shortName) {
      if (shortName != null) {
         String longName = (String)this.serviceNameMap.get(shortName);
         if (longName != null) {
            debugLogger.debug("getServiceLongName: Mapped '" + shortName + "' to '" + longName + "'");
            return longName;
         }
      }

      debugLogger.debug("getServiceLongName: No mapping found for '" + shortName + "'");
      return shortName;
   }

   private void initializeServiceEngine(RealmMBean realmMBean) throws SecurityServiceException {
      try {
         this.originalRealmMBean = realmMBean;
         realmMBean = this.wrapRealmMBean(realmMBean);
         ClassLoader engImplLoader = this.getClass().getClassLoader();
         ServiceEngineConfig serviceEngineConfig = ServiceEngineConfigFactory.getInstance(engImplLoader);
         serviceEngineConfig.addEnvironmentManagedServiceConfig(LoggerService.SERVICE_NAME, new CommonSecurityLoggerServiceImpl(), true);
         serviceEngineConfig.addEnvironmentManagedServiceConfig(ClassLoaderService.SERVICE_NAME, new ClassLoaderServiceImpl(), false);
         serviceEngineConfig.addEnvironmentManagedServiceConfig(SecurityProviderClassLoaderService.SERVICE_NAME, new SecurityProviderClassLoaderServiceImpl(), false);
         ClassLoader cssImplLoader = this.getClass().getClassLoader();
         LegacyDomainInfo configInfo = new LegacyDomainInfoImpl(ManagementService.getRuntimeAccess(kernelId), realmMBean);
         LegacyWebAppFilesCaseInsensitiveManager.setWebAppFilesCaseInsensitive(configInfo.getWebAppFilesCaseInsensitive());
         LegacyEnforceStrictURLPatternManager.setEnforceStrictURLPattern(SecurityServiceManager.getEnforceStrictURLPattern());
         ConfigHelperFactory helperFactory = ConfigHelperFactory.getInstance(cssImplLoader, realmMBean, configInfo);
         this.configAuditServices(helperFactory, realmMBean, serviceEngineConfig);
         this.configCredentialMappingServices(helperFactory, realmMBean, serviceEngineConfig);
         this.configCertPathServices(helperFactory, realmMBean, serviceEngineConfig);
         this.configAuthorizationServices(helperFactory, realmMBean, serviceEngineConfig);
         this.configAuthenticationServices(helperFactory, realmMBean, serviceEngineConfig, cssImplLoader);
         WLSIdentityServiceImpl identityService = this.configInternalServices(helperFactory, realmMBean, serviceEngineConfig, cssImplLoader);
         this.configSAMLSingleSignOnService(helperFactory, realmMBean, serviceEngineConfig);
         String loginSessionServiceName = this.configSAML2Services(helperFactory, realmMBean, serviceEngineConfig);
         this.addproviderstoNameMap(helperFactory, realmMBean);
         this.serviceEngine = serviceEngineConfig.startEngine();
         this.services = this.serviceEngine.getServices();
         identityService.initialize(helperFactory.getAuditServicesConfigHelper(realmMBean).getAuditServiceName(), helperFactory.getAuthenticationServicesConfigHelper(realmMBean).getPrincipalValidationServiceName(), this.services);

         try {
            ServletAccess.getInstance().registerServletInfo(realmMBean.getName(), new WLSServletInfo(this.services, realmMBean, helperFactory));
         } catch (ServiceInitializationException var12) {
            SecurityLogger.logStackTrace(var12);
         }

         if (loginSessionServiceName != null) {
            LoginSessionService sessionService = (LoginSessionService)this.services.getService(loginSessionServiceName);
            if (sessionService != null) {
               sessionService.addListener(new WLSServletSessionListener());
               debugLogger.debug("Registered WLSServletSessionListener()");
            } else {
               debugLogger.debug("Unable to get LoginSessionService - WLSServletSessionListener not registered");
            }
         } else {
            debugLogger.debug("No loginSessionServiceName - WLSServletSessionListener not registered");
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Calling SAML2ServletConfigHelper.setStaticServletInfoKey() with key '" + realmMBean.getName() + "'");
         }

         if (this.originalRealmMBean.isDefaultRealm()) {
            Class saml2ServletConfigHelperClass = Class.forName("com.bea.security.saml2.servlet.SAML2ServletConfigHelper", true, getSAML2ClassLoader());

            try {
               saml2ServletConfigHelperClass.getMethod("setStaticServletInfoKey", String.class).invoke((Object)null, realmMBean.getName());
            } catch (InvocationTargetException var11) {
               throw var11.getCause();
            }
         }

      } catch (Throwable var13) {
         SecurityLogger.logStackTrace(var13);
         throw new SecurityServiceException(var13);
      }
   }

   private void configAuditServices(ConfigHelperFactory helperFactory, RealmMBean realmMBean, ServiceEngineConfig serviceEngineConfig) {
      helperFactory.getAuditServicesConfigHelper(realmMBean).addToConfig(serviceEngineConfig, "cssImplLoaderName");
      AuditServicesConfigHelper auditHelper = helperFactory.getAuditServicesConfigHelper(realmMBean);
      this.serviceNameMap.put("AuditService", auditHelper.getAuditServiceName());
   }

   private void configCertPathServices(ConfigHelperFactory helperFactory, RealmMBean realmMBean, ServiceEngineConfig serviceEngineConfig) {
      CertPathProviderMBean[] certMBeans = realmMBean.getCertPathProviders();
      if (certMBeans != null && certMBeans.length > 0) {
         helperFactory.getCertPathServicesConfigHelper(realmMBean);
         CertPathServicesConfigHelper certPathHelper = helperFactory.getCertPathServicesConfigHelper(realmMBean);
         certPathHelper.addToConfig(serviceEngineConfig, "cssImplLoaderName");
         this.serviceNameMap.put("CertPathBuilderService", certPathHelper.getCertPathBuilderServiceName());
         this.serviceNameMap.put("CertPathValidatorService", certPathHelper.getCertPathValidatorServiceName());
      }

   }

   private void configCredentialMappingServices(ConfigHelperFactory helperFactory, RealmMBean realmMBean, ServiceEngineConfig serviceEngineConfig) {
      CredentialMapperMBean[] credMBeans = realmMBean.getCredentialMappers();
      if (credMBeans != null && credMBeans.length > 0) {
         CredentialMappingServicesConfigHelper credMapHelper = helperFactory.getCredentialMappingServicesConfigHelper(realmMBean);
         credMapHelper.addToConfig(serviceEngineConfig, "cssImplLoaderName");
         this.serviceNameMap.put("CredentialMappingService", credMapHelper.getCredentialMappingServiceName());
         SecurityTokenServicesConfigHelper secTokenHelper = helperFactory.getSecurityTokenServicesConfigHelper(realmMBean);
         secTokenHelper.addToConfig(serviceEngineConfig, "cssImplLoaderName");
         this.serviceNameMap.put("SecurityTokenService", secTokenHelper.getSecurityTokenServiceName());
      }

   }

   private void configAuthenticationServices(ConfigHelperFactory helperFactory, RealmMBean realmMBean, ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader) {
      AuthenticationServicesConfigHelper atnCfg = helperFactory.getAuthenticationServicesConfigHelper(realmMBean);
      WLSAuthenticationServicesConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, "cssImplLoaderName", "wlsImplLoaderName", realmMBean, atnCfg);
      this.serviceNameMap.put("ChallengeIdentityAssertionService", atnCfg.getChallengeIdentityAssertionServiceName());
      this.serviceNameMap.put("IdentityAssertionService", atnCfg.getIdentityAssertionServiceName());
      this.serviceNameMap.put("JAASAuthenticationService", atnCfg.getJAASAuthenticationServiceName());
      this.serviceNameMap.put("ImpersonationService", atnCfg.getIdentityImpersonationServiceName());
      this.serviceNameMap.put("SPNEGOSingleSignOnService", atnCfg.getNegotiateIdentityAsserterServiceName());
      this.serviceNameMap.put("PrincipalValidationService", atnCfg.getPrincipalValidationServiceName());
      this.serviceNameMap.put("ServletAuthenticationFilterService", WLSAuthenticationServicesConfigHelper.getServletAuthenticationFilterServiceName(realmMBean));
      this.serviceNameMap.put("WSPasswordDigestService", WLSAuthenticationServicesConfigHelper.getWSPasswordDigestServiceName(realmMBean));
      this.serviceNameMap.put("UserLockoutAdministrationService", WLSAuthenticationServicesConfigHelper.getUserLockoutAdministrationServiceName(realmMBean));
      this.serviceNameMap.put("UserLockoutCoordinationService", WLSAuthenticationServicesConfigHelper.getUserLockoutCoordinationServiceName(realmMBean));
   }

   private void configAuthorizationServices(ConfigHelperFactory helperFactory, RealmMBean realmMBean, ServiceEngineConfig serviceEngineConfig) {
      AuthorizerMBean[] atzMBeans = realmMBean.getAuthorizers();
      RoleMapperMBean[] roleMapperMBeans = realmMBean.getRoleMappers();
      AdjudicatorMBean adjudicator = realmMBean.getAdjudicator();
      if (atzMBeans != null && atzMBeans.length > 0 || roleMapperMBeans != null && roleMapperMBeans.length > 0 || adjudicator != null) {
         AuthorizationServicesConfigHelper authHelper = helperFactory.getAuthorizationServicesConfigHelper(realmMBean);
         authHelper.addToConfig(serviceEngineConfig, "cssImplLoaderName");
         this.serviceNameMap.put("AuthorizationService", authHelper.getAuthorizationServiceName());
         this.serviceNameMap.put("RoleMappingService", authHelper.getRoleMappingServiceName());
         this.serviceNameMap.put("IsProtectedResourceService", authHelper.getIsProtectedResourceServiceName());
         this.serviceNameMap.put("BulkAuthorizationService", authHelper.getBulkAuthorizationServiceName());
         this.serviceNameMap.put("BulkRoleMappingService", authHelper.getBulkRoleMappingServiceName());
         this.serviceNameMap.put("PolicyConsumerService", authHelper.getPolicyConsumerServiceName());
         this.serviceNameMap.put("RoleConsumerService", authHelper.getRoleConsumerServiceName());
         this.serviceNameMap.put("PolicyDeploymentService", authHelper.getPolicyDeploymentServiceName());
         this.serviceNameMap.put("RoleDeploymentService", authHelper.getRoleDeploymentServiceName());
      }

   }

   private WLSIdentityServiceImpl configInternalServices(ConfigHelperFactory helperFactory, RealmMBean realmMBean, ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader) throws Exception {
      WLSIdentityServiceImpl identityService = new WLSIdentityServiceImpl();
      WLSMiscellaneousServicesConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, "wlsImplLoaderName", realmMBean, identityService);
      this.serviceNameMap.put("IdentityService", helperFactory.getIdentityServicesConfigHelper(realmMBean).getIdentityServiceName());
      this.serviceNameMap.put("ApplicationVersioningService", WLSMiscellaneousServicesConfigHelper.getApplicationVersioningServiceName(realmMBean));
      WLSInternalServicesConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, "cssImplLoaderName", "wlsImplLoaderName", realmMBean, kernelId);
      return identityService;
   }

   private String configSAML2Services(ConfigHelperFactory helperFactory, RealmMBean realmMBean, ServiceEngineConfig serviceEngineConfig) throws Throwable {
      String loginSessionServiceName = null;
      SingleSignOnServicesConfigSpi saml2ServiceMBean = null;
      ServerMBean sBean = ManagementService.getRuntimeAccess(kernelId).getServer();
      if (sBean != null) {
         try {
            Method m = sBean.getClass().getMethod("getSingleSignOnServices");
            if (m != null) {
               saml2ServiceMBean = (SingleSignOnServicesConfigSpi)m.invoke(sBean);
            }
         } catch (NoSuchMethodException var10) {
         } catch (IllegalAccessException var11) {
         } catch (InvocationTargetException var12) {
            throw var12.getCause();
         }
      }

      if (saml2ServiceMBean != null && this.shouldConfigureSAML2Service(realmMBean)) {
         SAML2SingleSignOnServicesConfigHelper samlHelper = helperFactory.getSAML2SingleSignOnServicesConfigHelper(realmMBean);
         SAML2SingleSignOnServicesConfigHelper.SAML2SingleSignOnServicesConfigCustomizer samlCustomizer = samlHelper.getSingleSignOnServicesCustomizer();
         samlCustomizer.setSingleSignOnServicesConfig(saml2ServiceMBean);
         LoginSessionServiceConfigHelper loginHelper = helperFactory.getLoginSessionServiceConfigHelper(realmMBean);
         loginSessionServiceName = loginHelper.getLoginSessionServiceName(realmMBean);
         samlHelper.addToConfig(serviceEngineConfig, "SAML2ClassLoader", loginHelper);
         loginHelper.addToConfig(serviceEngineConfig, "cssImplLoaderName", realmMBean);
         this.serviceNameMap.put("SingleSignOnService", samlHelper.getSingleSignOnServicesName());
         this.serviceNameMap.put("LoginSessionService", loginSessionServiceName);
      }

      return loginSessionServiceName;
   }

   private void configSAMLSingleSignOnService(ConfigHelperFactory helperFactory, RealmMBean realmMBean, ServiceEngineConfig serviceEngineConfig) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      FederationServicesMBean fsMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getFederationServices();
      if (this.shouldConfigureSAMLService(fsMBean, realmMBean)) {
         SAMLSingleSignOnServiceConfigHelper samlHelper = helperFactory.getSAMLSingleSignOnServiceConfigHelper(realmMBean);
         SAMLSingleSignOnServiceConfigHelper.SAMLSingleSignOnServiceConfigCustomizer samlCustomizer = samlHelper.getSAMLSingleSignOnServiceCustomizer();
         samlCustomizer.setSAMLSingleSignOnServiceConfigInfo((SAMLSingleSignOnServiceConfigInfoSpi)Delegator.getProxy(SAMLSingleSignOnServiceConfigInfoSpi.class, Class.forName("weblogic.security.internal.SAMLSingleSignOnServiceConfigInfoImpl").newInstance()));
         samlHelper.addToConfig(serviceEngineConfig, "cssImplLoaderName");
         this.serviceNameMap.put("SAMLSingleSignOnService", samlHelper.getSAMLSingleSignOnServiceName());
      }

   }

   private void addproviderstoNameMap(ConfigHelperFactory helperFactory, RealmMBean realmMBean) {
      SecurityProviderConfigHelper providerHelper = helperFactory.getSecurityProviderConfigHelper();
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "AdjudicatorProvider_", AdjudicatorMBean.class, new ProviderMBean[]{realmMBean.getAdjudicator()});
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "AuditorProvider_", AuditorMBean.class, realmMBean.getAuditors());
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "AuthenticatorProvider_", AuthenticatorMBean.class, realmMBean.getAuthenticationProviders());
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "IdentityAsserterProvider_", IdentityAsserterMBean.class, realmMBean.getAuthenticationProviders());
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "AuthorizerProvider_", AuthorizerMBean.class, realmMBean.getAuthorizers());
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "RoleMapperProvider_", RoleMapperMBean.class, realmMBean.getRoleMappers());
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "CredentialMapperProvider_", CredentialMapperMBean.class, realmMBean.getCredentialMappers());
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "CertPathBuilderProvider_", CertPathBuilderMBean.class, realmMBean.getCertPathProviders());
      this.addProvidersToNameMap(providerHelper, this.serviceNameMap, "CertPathValidatorProvider_", CertPathValidatorMBean.class, realmMBean.getCertPathProviders());
   }

   private void addProvidersToNameMap(SecurityProviderConfigHelper helper, Map map, String prefix, Class mbeanType, ProviderMBean[] mbeans) {
      for(int i = 0; i < mbeans.length; ++i) {
         if (mbeans[i] == null) {
            debugLogger.debug("addProvidersToNameMap: Saw null MBean in '" + prefix + "' ProviderMBean array");
         } else if (mbeanType.isAssignableFrom(mbeans[i].getClass())) {
            debugLogger.debug("addProvidersToNameMap: Mapping provider shortname '" + prefix + mbeans[i].getName() + "' to longname '" + helper.getServiceName(mbeans[i]) + "'");
            map.put(prefix + mbeans[i].getName(), helper.getServiceName(mbeans[i]));
         } else {
            debugLogger.debug("addProvidersToNameMap: Provider '" + mbeans[i].getName() + "' is not assignable to " + mbeanType.getName() + ", not mapping with prefix " + prefix);
         }
      }

   }

   private RealmMBean wrapRealmMBean(final RealmMBean bean) {
      return (RealmMBean)Proxy.newProxyInstance(CommonSecurityServiceManagerDelegateImpl.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
               int i;
               if ("getAuthenticationProviders".equals(method.getName())) {
                  AuthenticationProviderMBean[] provs = (AuthenticationProviderMBean[])((AuthenticationProviderMBean[])method.invoke(bean, args));
                  AuthenticationProviderMBean[] wrapped = null;
                  if (provs != null) {
                     wrapped = new AuthenticationProviderMBean[provs.length];

                     for(i = 0; i < provs.length; ++i) {
                        wrapped[i] = (AuthenticationProviderMBean)CSSWLSDelegateImpl.this.wrapProviderMBean(provs[i]);
                     }
                  }

                  return wrapped;
               } else if (!"getCredentialMappers".equals(method.getName())) {
                  return method.invoke(bean, args);
               } else {
                  CredentialMapperMBean[] provsx = (CredentialMapperMBean[])((CredentialMapperMBean[])method.invoke(bean, args));
                  CredentialMapperMBean[] wrappedx = null;
                  if (provsx != null) {
                     wrappedx = new CredentialMapperMBean[provsx.length];

                     for(i = 0; i < provsx.length; ++i) {
                        wrappedx[i] = (CredentialMapperMBean)CSSWLSDelegateImpl.this.wrapProviderMBean(provsx[i]);
                     }
                  }

                  return wrappedx;
               }
            } catch (InvocationTargetException var7) {
               throw var7.getCause();
            }
         }
      });
   }

   private boolean shouldConfigureSAMLService(FederationServicesMBean fsMBean, RealmMBean realmMBean) {
      boolean v2Provider = false;
      ProviderMBean[] iaMBeans = realmMBean.getAuthenticationProviders();

      for(int i = 0; iaMBeans != null && i < iaMBeans.length; ++i) {
         if ("weblogic.security.providers.saml.SAMLIdentityAsserterV2MBeanImpl".equals(iaMBeans[i].getClass().getName())) {
            v2Provider = true;
         }
      }

      ProviderMBean[] cmMBeans = realmMBean.getCredentialMappers();

      for(int i = 0; cmMBeans != null && i < cmMBeans.length; ++i) {
         if ("weblogic.security.providers.saml.SAMLCredentialMapperV2MBeanImpl".equals(cmMBeans[i].getClass().getName())) {
            v2Provider = true;
         }
      }

      if (!v2Provider) {
         return false;
      } else {
         String[] serviceURIs = fsMBean.getIntersiteTransferURIs();
         if (serviceURIs != null && serviceURIs.length > 0) {
            return true;
         } else {
            serviceURIs = fsMBean.getAssertionConsumerURIs();
            return serviceURIs != null && serviceURIs.length > 0;
         }
      }
   }

   private boolean shouldConfigureSAML2Service(RealmMBean realmMBean) {
      ProviderMBean[] iaMBeans = realmMBean.getAuthenticationProviders();

      for(int i = 0; iaMBeans != null && i < iaMBeans.length; ++i) {
         if ("com.bea.security.saml2.cssservice.SAML2IdentityAsserterWrapper".equals(iaMBeans[i].getProviderClassName())) {
            return true;
         }
      }

      ProviderMBean[] cmMBeans = realmMBean.getCredentialMappers();

      for(int i = 0; cmMBeans != null && i < cmMBeans.length; ++i) {
         if ("com.bea.security.saml2.cssservice.SAML2CredentialMapperWrapper".equals(cmMBeans[i].getProviderClassName())) {
            return true;
         }
      }

      return false;
   }

   private ProviderMBean wrapProviderMBean(ProviderMBean bean) {
      if ("com.bea.security.saml2.providers.SAML2CredentialMapperProviderImpl".equals(bean.getProviderClassName())) {
         return (ProviderMBean)Proxy.newProxyInstance(getSAML2ClassLoader(), bean.getClass().getInterfaces(), new ProviderMBeanInvocationHandler(bean, "com.bea.security.saml2.cssservice.SAML2CredentialMapperWrapper", getSAML2ClassLoader()));
      } else {
         return "com.bea.security.saml2.providers.SAML2IdentityAsserterProviderImpl".equals(bean.getProviderClassName()) ? (ProviderMBean)Proxy.newProxyInstance(getSAML2ClassLoader(), bean.getClass().getInterfaces(), new ProviderMBeanInvocationHandler(bean, "com.bea.security.saml2.cssservice.SAML2IdentityAsserterWrapper", getSAML2ClassLoader())) : bean;
      }
   }

   static ClassLoader getSAML2ClassLoader() {
      ClassLoader parent = CommonSecurityServiceManagerDelegateImpl.class.getClassLoader();
      Class var1 = CommonSecurityServiceManagerDelegateImpl.class;
      synchronized(CommonSecurityServiceManagerDelegateImpl.class) {
         if (saml2ClassLoader == null) {
            try {
               saml2ClassLoader = new SAML2ClassLoader(parent, true);
            } catch (Exception var4) {
               throw new IllegalStateException(var4);
            }
         }

         return saml2ClassLoader;
      }
   }

   private static class WLSServletSessionListener implements LoginSessionListener {
      public WLSServletSessionListener() {
      }

      public boolean sessionCreated(LoginSession session, Object context) {
         if (context instanceof HttpServletRequest) {
            ServletAuthentication.runAs(session.getIdentity().getSubject(), (HttpServletRequest)context);
            return true;
         } else if (context instanceof Map) {
            Map mapContext = (Map)context;
            HttpServletRequest request = (HttpServletRequest)mapContext.get("HttpServletRequest");
            Map data = (Map)mapContext.get("AssociatedData");
            Map associatedData = Collections.unmodifiableMap(data);
            ServletAuthentication.runAs(session.getIdentity().getSubject(), associatedData, request);
            return true;
         } else {
            return false;
         }
      }

      public void sessionTerminated(LoginSession session, int reason) {
      }
   }

   private static class WLSServletInfo implements ServletInfoV2Spi {
      private LoggerService loggerService = null;
      private NegotiateIdentityAsserterService negotiateService = null;
      private SAMLSingleSignOnService samlSSOService = null;
      private SAML2Service saml2Service;
      private LoginSessionService loginSessionService;
      private IdentityService identityService = null;

      public WLSServletInfo(Services services, RealmMBean realmMBean, ConfigHelperFactory helperFactory) throws ServiceInitializationException {
         try {
            this.loggerService = (LoggerService)services.getService(LoggerService.SERVICE_NAME);
         } catch (ServiceNotFoundException var12) {
         }

         try {
            this.negotiateService = (NegotiateIdentityAsserterService)services.getService(helperFactory.getAuthenticationServicesConfigHelper(realmMBean).getNegotiateIdentityAsserterServiceName());
         } catch (ServiceNotFoundException var11) {
         }

         try {
            SAMLSingleSignOnServiceConfigHelper samlSSOServiceConfigHelper = helperFactory.getSAMLSingleSignOnServiceConfigHelper(realmMBean);
            if (samlSSOServiceConfigHelper != null) {
               this.samlSSOService = (SAMLSingleSignOnService)services.getService(samlSSOServiceConfigHelper.getSAMLSingleSignOnServiceName());
            }
         } catch (ServiceNotFoundException var10) {
         }

         String name;
         try {
            SAML2SingleSignOnServicesConfigHelper helper = helperFactory.getSAML2SingleSignOnServicesConfigHelper(realmMBean);
            if (helper != null) {
               name = helper.getSingleSignOnServicesName();
               if (name != null) {
                  this.saml2Service = (SAML2Service)services.getService(name);
                  if (this.saml2Service != null) {
                     this.saml2Service = (SAML2Service)Proxy.newProxyInstance(CSSWLSDelegateImpl.getSAML2ClassLoader(), this.saml2Service.getClass().getInterfaces(), new ThreadClassLoaderContextInvocationHandler(CSSWLSDelegateImpl.getSAML2ClassLoader(), this.saml2Service));
                  }
               }
            }
         } catch (ServiceNotFoundException var8) {
         } catch (ServiceInitializationException var9) {
            CSSWLSDelegateImpl.debugLogger.debug("Unable to get SAML2Service - SAML2Service Unavailable");
            throw var9;
         }

         try {
            LoginSessionServiceConfigHelper loginHelper = helperFactory.getLoginSessionServiceConfigHelper(realmMBean);
            if (loginHelper != null) {
               name = loginHelper.getLoginSessionServiceName(realmMBean);
               if (name != null) {
                  this.loginSessionService = (LoginSessionService)services.getService(name);
               }
            }
         } catch (ServiceNotFoundException var7) {
         }

         try {
            IdentityServicesConfigHelper identityHelper = helperFactory.getIdentityServicesConfigHelper(realmMBean);
            if (identityHelper != null) {
               name = identityHelper.getIdentityServiceName();
               if (name != null) {
                  this.identityService = (IdentityService)services.getService(name);
               }
            }
         } catch (ServiceNotFoundException var6) {
         }

      }

      public Object getLogger(String name) {
         return this.loggerService != null ? this.loggerService.getLogger(name) : null;
      }

      public Object getNegotiateFilterService() {
         return this.negotiateService;
      }

      public Object getSAMLServletFilterService() {
         return this.samlSSOService;
      }

      public Object getSAML2ServletFilterService() {
         return this.saml2Service;
      }

      public Object getIdentityService() {
         return this.identityService;
      }

      public Object getLoginSessionService() {
         return this.loginSessionService;
      }
   }

   private static class SecurityProviderClassLoaderServiceImpl implements SecurityProviderClassLoaderService {
      private SecurityProviderClassLoaderServiceImpl() {
      }

      public ClassLoader getClassLoader(ProviderMBean providerMBean) {
         return !"com.bea.security.saml2.providers.SAML2CredentialMapperProviderImpl".equals(providerMBean.getProviderClassName()) && !"com.bea.security.saml2.providers.SAML2IdentityAsserterProviderImpl".equals(providerMBean.getProviderClassName()) && !"com.bea.security.saml2.cssservice.SAML2CredentialMapperWrapper".equals(providerMBean.getProviderClassName()) && !"com.bea.security.saml2.cssservice.SAML2IdentityAsserterWrapper".equals(providerMBean.getProviderClassName()) ? providerMBean.getClass().getClassLoader() : CSSWLSDelegateImpl.getSAML2ClassLoader();
      }

      // $FF: synthetic method
      SecurityProviderClassLoaderServiceImpl(Object x0) {
         this();
      }
   }

   private static class LegacyDomainInfoImpl implements LegacyDomainInfo {
      private LegacyEncryptorKey key;
      private String domainName;
      private boolean inProductionMode;
      private boolean inSecureMode;
      private String domainDir;
      private boolean webAppFilesCaseInsensitive;
      private String serverName;
      private byte[] domainSecret;
      private byte[] domainSecretKey;
      private byte[] domainSecretAESKey;
      private byte[] domainSecretKeySalt;
      private char[] domainSecretKeyPW = new char[]{'0', 'x', 'c', 'c', 'b', '9', '7', '5', '5', '8', '9', '4', '0', 'b', '8', '2', '6', '3', '7', 'c', '8', 'b', 'e', 'c', '3', 'c', '7', '7', '0', 'f', '8', '6', 'f', 'a', '3', 'a', '3', '9', '1', 'a', '5', '6'};
      private int domainSecretKeyVersion = 1;
      private RealmMBean realmMBean;

      LegacyDomainInfoImpl(RuntimeAccess runtime, RealmMBean realmMBean) {
         this.realmMBean = realmMBean;
         DomainMBean domain = runtime.getDomain();
         this.domainName = domain.getName();
         this.inProductionMode = domain.isProductionModeEnabled();
         this.inSecureMode = domain.getSecurityConfiguration().getSecureMode().isSecureModeEnabled();
         this.domainDir = domain.getRootDirectory();
         this.webAppFilesCaseInsensitive = SecurityServiceManager.areWebAppFilesCaseInsensitive();
         this.serverName = runtime.getServer().getName();
         SecurityConfigurationMBean secConfig = domain.getSecurityConfiguration();
         this.domainSecret = secConfig.getCredential().getBytes();
         this.domainSecretKey = secConfig.getEncryptedSecretKey();
         this.domainSecretAESKey = secConfig.getEncryptedAESSecretKey();
         this.domainSecretKeySalt = secConfig.getSalt();
         Object nonFIPS140Ctx = null;

         try {
            Class JSafeImpl = Class.forName("weblogic.security.internal.encryption.JSafeEncryptionServiceImpl");
            Method getNonFIPS140Ctx = JSafeImpl.getMethod("getNonFIPS140Ctx", (Class[])null);
            nonFIPS140Ctx = getNonFIPS140Ctx.invoke((Object)null, (Object[])null);
         } catch (LinkageError var9) {
         } catch (Exception var10) {
         }

         try {
            this.key = new LegacyEncryptorKey(this.domainSecretKeyPW, this.domainSecretKeySalt, this.domainSecretKey, this.domainSecretAESKey, nonFIPS140Ctx);
         } catch (KeyException var8) {
            SecurityLogger.logStackTrace(var8);
            throw new AssertionError("Failed to setup LegacyEncryptor: " + var8.getMessage());
         }
      }

      public LegacyEncryptorKey getLegacyEncryptorKey() {
         return this.key;
      }

      public String getDomainName() {
         return this.domainName;
      }

      public boolean getProductionModeEnabled() {
         return this.inProductionMode;
      }

      public boolean getSecureModeEnabled() {
         return this.inSecureMode;
      }

      public String getRootDirectory() {
         return this.domainDir;
      }

      public boolean getWebAppFilesCaseInsensitive() {
         return this.webAppFilesCaseInsensitive;
      }

      public String getServerName() {
         return this.serverName;
      }

      public byte[] getDomainCredential() {
         return this.domainSecret;
      }

      public boolean getManagementModificationsSupported() {
         AdminServerStatusService asss = (AdminServerStatusService)GlobalServiceLocator.getServiceLocator().getService(AdminServerStatusService.class, new Annotation[0]);
         return !this.needAdminServer() || asss.isAdminServerAvailable();
      }

      private boolean needAdminServer() {
         return this.realmMBean.getRDBMSSecurityStore() == null;
      }
   }

   private static class ClassLoaderServiceImpl implements ClassLoaderService {
      private ClassLoaderServiceImpl() {
      }

      public ClassLoader getClassLoader(String classLoaderName) {
         if ("cssImplLoaderName".equals(classLoaderName)) {
            return this.getClass().getClassLoader();
         } else if ("wlsImplLoaderName".equals(classLoaderName)) {
            return this.getClass().getClassLoader();
         } else if ("SAML2ClassLoader".equals(classLoaderName)) {
            return CSSWLSDelegateImpl.getSAML2ClassLoader();
         } else {
            throw new AssertionError("Unknown class loader name : " + classLoaderName);
         }
      }

      // $FF: synthetic method
      ClassLoaderServiceImpl(Object x0) {
         this();
      }
   }
}
