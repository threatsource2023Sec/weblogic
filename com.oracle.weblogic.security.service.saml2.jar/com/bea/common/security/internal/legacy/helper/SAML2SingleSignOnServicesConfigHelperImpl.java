package com.bea.common.security.internal.legacy.helper;

import com.bea.common.classloader.service.ClassLoaderService;
import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.LoginSessionServiceConfigHelper;
import com.bea.common.security.legacy.SAML2SingleSignOnServicesConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.spi.LegacyConfigInfoSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.common.security.servicecfg.SAML2ServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;

public class SAML2SingleSignOnServicesConfigHelperImpl extends BaseServicesConfigImpl implements SAML2SingleSignOnServicesConfigHelper {
   private SingleSignOnServicesConfigCustomizerImpl samlSingleSignOnServiceCustomizer = null;
   private LegacyConfigInfoSpi legacyConfigInfo = null;

   public String getSingleSignOnServicesName() {
      return SAML2SingleSignOnServicesConfigHelper.class.getName() + "_" + this.getRealmMBean().getName();
   }

   public SAML2SingleSignOnServicesConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper, LegacyConfigInfoSpi legacyConfigInfo) {
      super(realmMBean, providerHelper);
      this.samlSingleSignOnServiceCustomizer = new SingleSignOnServicesConfigCustomizerImpl(this.getSingleSignOnServicesName());
      this.legacyConfigInfo = legacyConfigInfo;
   }

   public SAML2SingleSignOnServicesConfigHelper.SAML2SingleSignOnServicesConfigCustomizer getSingleSignOnServicesCustomizer() {
      return this.samlSingleSignOnServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, LoginSessionServiceConfigHelper loginHelper) {
      RealmMBean realmMBean = this.getRealmMBean();
      String realmName = realmMBean.getName();
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(this.getSingleSignOnServicesName(), "com.bea.security.saml2.cssservice.SAML2ServiceLifeCycleImpl", true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      String credMappingServiceName = null;
      if (this.samlSingleSignOnServiceCustomizer.getSingleSignOnServicesConfig().isIdentityProviderEnabled()) {
         credMappingServiceName = CredentialMappingServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(credMappingServiceName);
      }

      String identityAssertionServiceName = null;
      if (this.samlSingleSignOnServiceCustomizer.getSingleSignOnServicesConfig().isServiceProviderEnabled()) {
         identityAssertionServiceName = IdentityAssertionServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(identityAssertionServiceName);
      }

      Class cmClass = null;
      Class iaClass = null;

      try {
         ClassLoaderService cls = (ClassLoaderService)serviceEngineConfig.getEnvironmentManagedService(ClassLoaderService.SERVICE_NAME);
         ClassLoader cl = cls.getClassLoader(lifecycleImplLoaderName);

         try {
            cmClass = cl.loadClass("com.bea.security.saml2.providers.SAML2CredentialMapperMBean");
         } catch (ClassNotFoundException var20) {
         }

         try {
            iaClass = cl.loadClass("com.bea.security.saml2.providers.SAML2IdentityAsserterMBean");
         } catch (ClassNotFoundException var19) {
         }
      } catch (ServiceNotFoundException var21) {
      }

      String cmName = null;
      if (cmClass != null) {
         CredentialMapperMBean[] cms = realmMBean.getCredentialMappers();
         if (cms != null) {
            for(int i = 0; i < cms.length; ++i) {
               if (cmClass.isAssignableFrom(cms[i].getClass())) {
                  cmName = CredentialMapperConfigHelper.getServiceName(cms[i]);
                  serviceConfig.addDependency(cmName);
                  break;
               }
            }
         }
      }

      String iaName = null;
      if (iaClass != null) {
         AuthenticationProviderMBean[] ias = realmMBean.getAuthenticationProviders();
         if (ias != null) {
            for(int i = 0; i < ias.length; ++i) {
               if (iaClass.isAssignableFrom(ias[i].getClass())) {
                  iaName = IdentityAsserterV2ConfigHelper.getServiceName((IdentityAsserterMBean)ias[i]);
                  serviceConfig.addDependency(iaName);
                  break;
               }
            }
         }
      }

      String identityServiceName = IdentityServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(identityServiceName);
      String sessionServiceName = loginHelper.getLoginSessionServiceName(realmMBean);
      serviceConfig.addDependency(sessionServiceName);
      String samlKeyServiceName = SAMLKeyServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(samlKeyServiceName);
      String storeServiceName = StoreServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(storeServiceName);
      SAML2ServiceConfig config = new SAML2ConfigImpl(auditServiceName, credMappingServiceName, cmName, identityAssertionServiceName, iaName, identityServiceName, this.samlSingleSignOnServiceCustomizer.getSingleSignOnServicesConfig(), samlKeyServiceName, sessionServiceName, storeServiceName, this.legacyConfigInfo.getDomainName(), realmName, this.legacyConfigInfo.getLegacyEncryptor());
      serviceConfig.setConfig(config);
   }

   private static class SAML2ConfigImpl implements SAML2ServiceConfig {
      private String auditServiceName;
      private String identityServiceName;
      private String sessionServiceName;
      private String credMappingServiceName;
      private String credMappingProviderServiceName;
      private String identityAssertionServiceName;
      private String identityAssertionProviderServiceName;
      private String samlKeyServiceName;
      private String storeServiceName;
      private String jaxpServiceName;
      private SingleSignOnServicesConfigSpi localConfig;
      private String domainName;
      private String realmName;
      private LegacyEncryptorSpi legacyEncryptor;

      public SAML2ConfigImpl(String auditServiceName, String credMappingServiceName, String credMappingProviderServiceName, String identityAssertionServiceName, String identityAssertionProviderServiceName, String identityServiceName, SingleSignOnServicesConfigSpi localConfig, String samlKeyServiceName, String sessionServiceName, String storeServiceName, String domainName, String realmName, LegacyEncryptorSpi legacyEncryptor) {
         this.auditServiceName = auditServiceName;
         this.credMappingServiceName = credMappingServiceName;
         this.credMappingProviderServiceName = credMappingProviderServiceName;
         this.identityAssertionServiceName = identityAssertionServiceName;
         this.identityAssertionProviderServiceName = identityAssertionProviderServiceName;
         this.identityServiceName = identityServiceName;
         this.localConfig = localConfig;
         this.samlKeyServiceName = samlKeyServiceName;
         this.sessionServiceName = sessionServiceName;
         this.storeServiceName = storeServiceName;
         this.domainName = domainName;
         this.realmName = realmName;
         this.legacyEncryptor = legacyEncryptor;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getCredMappingServiceName() {
         return this.credMappingServiceName;
      }

      public String getSAML2CredMapperProviderServiceName() {
         return this.credMappingProviderServiceName;
      }

      public String getIdentityAssertionServiceName() {
         return this.identityAssertionServiceName;
      }

      public String getSAML2IdentityAsserterProviderServiceName() {
         return this.identityAssertionProviderServiceName;
      }

      public String getSAMLKeyServiceName() {
         return this.samlKeyServiceName;
      }

      public String getStoreServiceName() {
         return this.storeServiceName;
      }

      public String getIdentityServiceName() {
         return this.identityServiceName;
      }

      public String getSessionServiceName() {
         return this.sessionServiceName;
      }

      public SingleSignOnServicesConfigSpi getLocalConfig() {
         return this.localConfig;
      }

      public String getDomainName() {
         return this.domainName;
      }

      public String getRealmName() {
         return this.realmName;
      }

      public LegacyEncryptorSpi getLegacyEncryptor() {
         return this.legacyEncryptor;
      }
   }

   private static class SingleSignOnServicesConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements SAML2SingleSignOnServicesConfigHelper.SAML2SingleSignOnServicesConfigCustomizer {
      private SingleSignOnServicesConfigSpi samlSSOServiceConfig;

      private SingleSignOnServicesConfigCustomizerImpl(String serviceName) {
         super(serviceName);
      }

      public SingleSignOnServicesConfigSpi getSingleSignOnServicesConfig() {
         return this.samlSSOServiceConfig;
      }

      public void setSingleSignOnServicesConfig(SingleSignOnServicesConfigSpi samlSSOServiceConfig) {
         this.samlSSOServiceConfig = samlSSOServiceConfig;
      }

      // $FF: synthetic method
      SingleSignOnServicesConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }
}
