package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.SAMLSingleSignOnServiceImpl;
import com.bea.common.security.legacy.SAMLSingleSignOnServiceConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.spi.SAMLSingleSignOnServiceConfigInfoSpi;
import com.bea.common.security.servicecfg.SAMLSingleSignOnServiceConfig;
import weblogic.management.security.RealmMBean;

public class SAMLSingleSignOnServiceConfigHelperImpl extends BaseServicesConfigImpl implements SAMLSingleSignOnServiceConfigHelper {
   private SAMLSingleSignOnServiceConfigCustomizerImpl samlSingleSignOnServiceCustomizer = new SAMLSingleSignOnServiceConfigCustomizerImpl(this.getSAMLSingleSignOnServiceName());

   public String getSAMLSingleSignOnServiceName() {
      return SAMLSingleSignOnServiceConfigHelper.class.getName() + "_" + this.getRealmMBean().getName();
   }

   public SAMLSingleSignOnServiceConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      super(realmMBean, providerHelper);
   }

   public SAMLSingleSignOnServiceConfigHelper.SAMLSingleSignOnServiceConfigCustomizer getSAMLSingleSignOnServiceCustomizer() {
      return this.samlSingleSignOnServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName) {
      RealmMBean realmMBean = this.getRealmMBean();
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(this.getSAMLSingleSignOnServiceName(), SAMLSingleSignOnServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      String credMappingServiceName = null;
      if (this.samlSingleSignOnServiceCustomizer.getSAMLSingleSignOnServiceConfigInfo().isSourceSiteEnabled()) {
         credMappingServiceName = CredentialMappingServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(credMappingServiceName);
      }

      String identityAssertionServiceName = null;
      if (this.samlSingleSignOnServiceCustomizer.getSAMLSingleSignOnServiceConfigInfo().isDestinationSiteEnabled()) {
         identityAssertionServiceName = IdentityAssertionServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(identityAssertionServiceName);
      }

      String identityServiceName = IdentityServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(identityServiceName);
      String sessionServiceName = SessionServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(sessionServiceName);
      String samlKeyServiceName = SAMLKeyServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(samlKeyServiceName);
      String storeService = StoreServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(storeService);
      SAMLSingleSignOnServiceConfig config = new ConfigImpl(auditServiceName, credMappingServiceName, identityAssertionServiceName, identityServiceName, sessionServiceName, samlKeyServiceName, this.samlSingleSignOnServiceCustomizer.getSAMLSingleSignOnServiceConfigInfo());
      serviceConfig.setConfig(config);
   }

   private static class ConfigImpl implements SAMLSingleSignOnServiceConfig {
      private String auditServiceName;
      private String credMappingServiceName;
      private String identityAssertionServiceName;
      private String identityServiceName;
      private String sessionServiceName;
      private String samlKeyServiceName;
      private SAMLSingleSignOnServiceConfigInfoSpi samlServiceConfigInfo;

      public ConfigImpl(String auditServiceName, String credMappingServiceName, String identityAssertionServiceName, String identityServiceName, String sessionServiceName, String samlKeyServiceName, SAMLSingleSignOnServiceConfigInfoSpi samlServiceConfigInfo) {
         this.auditServiceName = auditServiceName;
         this.credMappingServiceName = credMappingServiceName;
         this.identityAssertionServiceName = identityAssertionServiceName;
         this.identityServiceName = identityServiceName;
         this.sessionServiceName = sessionServiceName;
         this.samlKeyServiceName = samlKeyServiceName;
         this.samlServiceConfigInfo = samlServiceConfigInfo;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getCredMappingServiceName() {
         return this.credMappingServiceName;
      }

      public String getIdentityAssertionServiceName() {
         return this.identityAssertionServiceName;
      }

      public String getIdentityServiceName() {
         return this.identityServiceName;
      }

      public SAMLSingleSignOnServiceConfigInfoSpi getSAMLSingleSignOnServiceConfigInfo() {
         return this.samlServiceConfigInfo;
      }

      public String getSessionServiceName() {
         return this.sessionServiceName;
      }

      public String getSAMLKeyServiceName() {
         return this.samlKeyServiceName;
      }
   }

   private static class SAMLSingleSignOnServiceConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements SAMLSingleSignOnServiceConfigHelper.SAMLSingleSignOnServiceConfigCustomizer {
      private SAMLSingleSignOnServiceConfigInfoSpi samlSSOServiceConfig;

      private SAMLSingleSignOnServiceConfigCustomizerImpl(String serviceName) {
         super(serviceName);
      }

      public SAMLSingleSignOnServiceConfigInfoSpi getSAMLSingleSignOnServiceConfigInfo() {
         return this.samlSSOServiceConfig;
      }

      public void setSAMLSingleSignOnServiceConfigInfo(SAMLSingleSignOnServiceConfigInfoSpi samlSSOServiceConfig) {
         this.samlSSOServiceConfig = samlSSOServiceConfig;
      }

      // $FF: synthetic method
      SAMLSingleSignOnServiceConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }
}
